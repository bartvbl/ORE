package orre.resources.incompleteResources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import orre.gl.materials.BlueprintMaterial;
import orre.gl.materials.Material;
import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceType;
import orre.resources.loaders.TextureLoader;
import orre.resources.loaders.obj.OBJLoadingContext;

public class IncompleteBlueprintMaterial implements IncompleteResourceObject<IncompleteBlueprintMaterial> {
	public final String name;
	
	public IncompleteTexture ambientTexture = null;
	public IncompleteTexture diffuseTexture = null;
	public IncompleteTexture specularTexture = null;
	
	public float[] ambientColour = new float[]{0, 0, 0, 1};
	public float[] diffuseColour = new float[]{0, 0, 0, 1};
	public float[] specularColour = new float[]{0, 0, 0, 1};
	public float[] emissionColour = new float[]{0, 0, 0, 1};
	
	public float alpha;
	public float shininess;
	
	public IncompleteBlueprintMaterial(String name)
	{
		this.name = name;
	}
	
	public void setAmbientTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.ambientTexture = TextureLoader.readTextureFromFile(new Resource(new File(context.getContainingDirectory().getPath() + "/" +  src), ResourceType.texture, "Material texture"));
	}
	
	public void setDiffuseTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.diffuseTexture = TextureLoader.readTextureFromFile(new Resource(new File(context.getContainingDirectory().getPath() + "/" +  src), ResourceType.texture, "Material texture"));
	}
	
	public void setSpecularTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.specularTexture = TextureLoader.readTextureFromFile(new Resource(new File(context.getContainingDirectory().getPath() + "/" +  src), ResourceType.texture, "Material texture"));
	}
	
//	@Override
//	public Resource finalizeResource() {
//		if(isFinalized){return null;};
//		if(this.ambientTexture != null) {			
//			this.ambientTexture.finalizeResource();
//			this.material.setAmbientTexture(this.ambientTexture.getTexture());
//		}
//		if(this.diffuseTexture != null) {	
//			this.diffuseTexture.finalizeResource();
//			this.material.setDiffuseTexture(this.diffuseTexture.getTexture());
//		}
//		if(this.specularTexture != null) {	
//			this.specularTexture.finalizeResource();
//			this.material.setSpecularTexture(this.specularTexture.getTexture());
//		}
//		this.isFinalized = true;
//		return null;
//	}
}
