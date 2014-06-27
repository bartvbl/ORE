package orre.resources.loaders;

import java.io.File;
import java.io.IOException;
import lib.ldd.data.GeometryWithMaterial;
import lib.ldd.data.Material;
import lib.ldd.data.Mesh;
import lib.ldd.lif.AssetsFilePaths;
import lib.ldd.lif.LIFFile;
import lib.ldd.lif.LIFReader;
import lib.ldd.lxfml.LXFMLReader;
import orre.lxf.LXFBlueprintModel;
import orre.lxf.LXFBlueprintPart;
import orre.resources.Finalizable;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.ResourceQueue;
import orre.resources.partiallyLoadables.BlueprintMaterial;

public class LXFMLLoader implements ResourceTypeLoader {
	private static final File assetsFile = new File("res/Assets.lif");
	
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		checkDBFileAvailability();
		LIFReader dbReader = openDBReader();
		
		Mesh mesh = LXFMLReader.readLXFMLFile(source.location, dbReader);
		String modelName = source.location.getName();
		modelName = modelName.substring(0, modelName.lastIndexOf('.'));
		return convertMesh(mesh, modelName, queue);
	}

	private LXFBlueprintModel convertMesh(Mesh mesh, String modelName, ResourceQueue queue) {
		LXFBlueprintModel model = new LXFBlueprintModel(modelName);
		int partCounter = 0;
		for(GeometryWithMaterial group : mesh.contents) {
			BlueprintMaterial material = convertMaterial(group.material);
			LXFBlueprintPart[] parts = new LXFBlueprintPart[group.geometry.length];
			for(int i = 0; i < group.geometry.length; i++) {
				parts[i] = new LXFBlueprintPart(group.geometry[i], "part " + partCounter);
				partCounter++;
				queue.enqueueResourceForFinalization(parts[i]);
			}
			queue.enqueueResourceForFinalization(material);
			model.addMaterialGroup(material, parts);
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
