package orre.resources.loaders;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import lib.ldd.data.GeometryWithMaterial;
import lib.ldd.data.Material;
import lib.ldd.data.Mesh;
import lib.ldd.data.VBOContents;
import lib.ldd.lif.AssetsFilePaths;
import lib.ldd.lif.LIFFile;
import lib.ldd.lif.LIFReader;
import lib.ldd.lxfml.LXFMLReader;
import orre.lxf.LXFBlueprintModel;
import orre.lxf.LXFBlueprintPart;
import orre.resources.Resource;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.incompleteResources.IncompleteBlueprintMaterial;
import orre.resources.incompleteResources.IncompleteBlueprintModel;
import orre.resources.ResourceQueue;

public class LXFMLLoader implements ResourceTypeLoader {
	private static final File assetsFile = new File("res/Assets.lif");
	private static final Matrix4f geometryConversionMatrix = new Matrix4f();
	
	static {
		//LXFML models flip axis
		geometryConversionMatrix.rotate((float) (Math.PI / 2d), new Vector3f(1, 0, 0));
		//Scale to map size
		geometryConversionMatrix.scale(new Vector3f(0.08f, 0.08f, 0.08f));
	}
	
	@Override
	public IncompleteBlueprintModel readResource(Resource source) throws Exception {
		checkDBFileAvailability();
		LIFReader dbReader = openDBReader();
		
		Mesh mesh = LXFMLReader.readLXFMLFile(source.fileLocation, dbReader);
		String modelName = source.name;
		return convertMesh(mesh, modelName);
	}

	private IncompleteBlueprintModel convertMesh(Mesh mesh, String modelName) {
		IncompleteBlueprintModel model = new IncompleteBlueprintModel(modelName);
		int partCounter = 0;
		Arrays.sort(mesh.contents, new Comparator<GeometryWithMaterial>() {

			@Override
			public int compare(GeometryWithMaterial left, GeometryWithMaterial right) {
				boolean leftIsSolid = left.material.alpha == 255;
				boolean rightIsSolid = right.material.alpha == 255;
				
				// Both solid or both translucent
				if((leftIsSolid && rightIsSolid) || (!leftIsSolid && !rightIsSolid)) {
					return 0;
				}
				// Only left translucent
				if(!leftIsSolid) {
					return -1;
				}
				if(!rightIsSolid) {
					return 1;
				}
				return 0;
			}
			
		});
		for(GeometryWithMaterial group : mesh.contents) {
			IncompleteBlueprintMaterial material = convertMaterial(group.material);
			LXFBlueprintPart[] parts = new LXFBlueprintPart[group.geometry.length];
			for(int i = 0; i < group.geometry.length; i++) {
				VBOContents contents = group.geometry[i].transform(geometryConversionMatrix);
				parts[i] = new LXFBlueprintPart(contents, "part " + partCounter);
				partCounter++;
				queue.enqueue(parts[i]);
			}
			queue.enqueueResourceForFinalization(material);
			model.addMaterialGroup(material, parts);
		}
		return model;
	}

	private IncompleteBlueprintMaterial convertMaterial(Material material) {
		IncompleteBlueprintMaterial converted = new IncompleteBlueprintMaterial("Brick material " + material.id);
		float red 	= (material.red)   / 255f;
		float green = (material.green) / 255f;
		float blue 	= (material.blue)  / 255f;
		float alpha = (material.alpha) / 255f;
		float[] colour = new float[]{red, green, blue, alpha};
		float[] black = new float[]{0, 0, 0, 1};
		float[] white = new float[]{1, 1, 1, 1};
		
		converted.setAmbientColour(black);
		converted.setDiffuseColour(colour);
		converted.setSpecularColour(white);
		if(alpha < 1.0f) {
			converted.setDiffuseColour(black);
			converted.setEmissionColour(colour);
		}
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
