package orre.resources.loaders;

import java.io.File;
import java.io.IOException;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import lib.ldd.data.GeometryWithMaterial;
import lib.ldd.data.Material;
import lib.ldd.data.Mesh;
import lib.ldd.data.VBOContents;
import lib.ldd.lif.AssetsFilePaths;
import lib.ldd.lif.LIFFile;
import lib.ldd.lif.LIFReader;
import lib.ldd.lxfml.LXFMLReader;
import orre.geom.vbo.BufferDataFormatType;
import orre.resources.Finalizable;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.obj.ModelPartType;
import orre.resources.loaders.obj.StoredModelPart;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class LXFMLLoader implements ResourceTypeLoader {
	private static final File assetsFile = new File("res/Assets.lif");
	
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		System.out.println("Loading LXFML file..");
		checkDBFileAvailability();
		LIFReader dbReader = openDBReader();
		
		Mesh mesh = LXFMLReader.readLXFMLFile(source.location, dbReader);
		String modelName = source.location.getName();
		modelName = modelName.substring(0, modelName.lastIndexOf('.'));
		return convertMesh(mesh, modelName, queue);
	}

	private BlueprintModel convertMesh(Mesh mesh, String modelName, ResourceQueue queue) {
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

	private BlueprintMaterial convertMaterial(Material material) {
		BlueprintMaterial converted = new BlueprintMaterial("Brick material " + material.id);
		float red 	= (material.red)   / 255f;
		float green = (material.green) / 255f;
		float blue 	= (material.blue)  / 255f;
		float alpha = (material.alpha) / 255f;
		converted.setDiffuseColour(new float[]{red, green, blue, alpha});
		return converted;
	}
	
	private PartiallyLoadableModelPart convertGeometryGroup(VBOContents geometryGroup, String name) {
		int vertexCount = geometryGroup.vertices.length / 3;
		DoubleBuffer vertexBuffer = BufferUtils.createDoubleBuffer(geometryGroup.vertices.length + geometryGroup.normals.length);
		PartiallyLoadableModelPart part = new PartiallyLoadableModelPart(name, vertexCount, BufferDataFormatType.VERTICES_AND_NORMALS);
		for(int i = 0; i < geometryGroup.vertices.length; i+=3) {
			vertexBuffer.put(geometryGroup.vertices[i]).put(geometryGroup.vertices[i+1]).put(geometryGroup.vertices[i+2]);
			vertexBuffer.put(geometryGroup.normals[i]).put(geometryGroup.normals[i+1]).put(geometryGroup.normals[i+2]);
		}
		return part;
	}

	private void checkDBFileAvailability() throws IOException {
		if(!assetsFile.exists()) {
			throw new IOException("The Assets.lif file was not found.");
		}
	}

	private LIFReader openDBReader() throws IOException {
		checkDBFileAvailability();
		try {
			LIFReader assetsReader = LIFReader.openLIFFile(assetsFile);
			LIFFile dbFile = assetsReader.getFileAt(AssetsFilePaths.dbFile);
			return assetsReader.readInternalLIFFile(dbFile);
		} catch (IOException e) {
			throw new IOException("Failed to load Assets.lif file", e);
		}
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.lxfmlModel;
	}

}
