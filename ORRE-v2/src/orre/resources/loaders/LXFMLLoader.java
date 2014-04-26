package orre.resources.loaders;

import java.io.File;
import java.io.IOException;

import lib.ldd.data.GeometryWithMaterial;
import lib.ldd.data.Material;
import lib.ldd.data.Mesh;
import lib.ldd.data.VBOContents;
import lib.ldd.lif.AssetsFilePaths;
import lib.ldd.lif.LIFFile;
import lib.ldd.lif.LIFReader;
import lib.ldd.lxfml.LXFMLReader;
import orre.geom.mesh.Mesh3D;
import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryNode;
import orre.resources.FileToLoad;
import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.obj.ModelPartType;
import orre.resources.loaders.obj.StoredModelPart;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class LXFMLLoader {
	private static final File assetsFile = new File("res/Assets.lif");
	private static final LIFReader dbReader = openDBReader();
	private static IOException assetsFileError = null;

	public static BlueprintModel load(FileToLoad file, ResourceQueue queue) throws IOException {
		checkDBFileAvailability();
		
		File fileLocation = new File(file.getPath());
		Mesh mesh = LXFMLReader.readLXFMLFile(fileLocation, dbReader);
		String modelName = fileLocation.getName();
		modelName = modelName.substring(0, modelName.lastIndexOf('.'));
		return convertMesh(mesh, modelName, queue);
	}

	private static BlueprintModel convertMesh(Mesh mesh, String modelName, ResourceQueue queue) {
		BlueprintModel model = new BlueprintModel(modelName);
		for(GeometryWithMaterial group : mesh.contents) {
			BlueprintMaterial material = convertMaterial(group.material);
			StoredModelPart part = new StoredModelPart(ModelPartType.PHYSICAL, "Brick group " + group.material.id);
			model.addTopLevelPart(part);
			for(VBOContents geometryGroup : group.geometry) {
				PartiallyLoadableModelPart buffer = convertGeometryGroup(geometryGroup, "Group " + group.material.id);
				buffer.setMaterial(material);
				buffer.setDestinationPart(part);
			}
		}
		return model;
	}

	private static BlueprintMaterial convertMaterial(Material material) {
		BlueprintMaterial converted = new BlueprintMaterial("Brick material " + material.id);
		float red 	= ((float) material.red)   / 255f;
		float green = ((float) material.green) / 255f;
		float blue 	= ((float) material.blue)  / 255f;
		float alpha = ((float) material.alpha) / 255f;
		converted.setDiffuseColour(new float[]{red, green, blue, alpha});
		return converted;
	}
	
	private static PartiallyLoadableModelPart convertGeometryGroup(VBOContents geometryGroup, String name) {
		int vertexCount = geometryGroup.vertices.length / 3;
		PartiallyLoadableModelPart part = new PartiallyLoadableModelPart(name, vertexCount, BufferDataFormatType.VERTICES_AND_NORMALS);
		for(int i = 0; i < geometryGroup.vertices.length; i++) {
			
		}
		return null;
	}

	private static void checkDBFileAvailability() throws IOException {
		if(dbReader == null) {
			throw new IOException("Failed to load Assets.lif file", assetsFileError);
		}
	}

	private static LIFReader openDBReader() {
		try {
			LIFReader assetsReader = LIFReader.openLIFFile(assetsFile);
			LIFFile dbFile = assetsReader.getFileAt(AssetsFilePaths.dbFile);
			return assetsReader.readInternalLIFFile(dbFile);
		} catch (IOException e) {
			assetsFileError  = e;
			return null;
		}
	}

}
