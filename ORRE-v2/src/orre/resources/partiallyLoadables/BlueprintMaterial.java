package orre.resources.partiallyLoadables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import orre.gl.materials.AbstractMaterial;
import orre.gl.materials.Material;
import orre.resources.Resource;
import orre.resources.ResourceType;
import orre.resources.Finalizable;
import orre.resources.loaders.TextureLoader;
import orre.resources.loaders.obj.OBJLoadingContext;

public class BlueprintMaterial implements Finalizable, AbstractMaterial {
	public final String name;
	private PartiallyLoadableTexture ambientTexture = null;
	private PartiallyLoadableTexture diffuseTexture = null;
	private PartiallyLoadableTexture specularTexture = null;
	private boolean isFinalized = false;
	
	private Material material;
	
	public BlueprintMaterial(String name)
	{
		this.name = name;
		this.material = new Material(name);
	}
	
	@Override
	public void setAmbientColour(float[] colour) {
		this.material.setAmbientColour(colour);
	}
	@Override
	public void setDiffuseColour(float[] colour) {
		this.material.setDiffuseColour(colour);
	}
	@Override
	public void setSpecularColour(float[] colour) {
		this.material.setSpecularColour(colour);
	}
	public void setEmissionColour(float[] colour) {
		this.material.setEmissionColour(colour);
	}
	
	public void setShininess(float shininess) {
		this.material.setShininess(shininess);
	}
	
	@Override
	public void setAlpha(float alpha) {
		this.material.setAlpha(alpha);
	}
	
	public void setAmbientTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.ambientTexture = TextureLoader.partiallyLoadTextureFromFile(new Resource(new File(context.getContainingDirectory().getPath() + "/" +  src), ResourceType.texture, "Material texture"));
	}
	public void setDiffuseTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.diffuseTexture = TextureLoader.partiallyLoadTextureFromFile(new Resource(ResourceType.texture, "Material texture", new File(context.getContainingDirectory().getPath() + "/" +  src)));
	}
	public void setSpecularTexture(String src, OBJLoadingContext context) throws FileNotFoundException, IOException, Exception {
		this.specularTexture = TextureLoader.partiallyLoadTextureFromFile(new Resource(ResourceType.texture, "Material texture", new File(context.getContainingDirectory().getPath() + "/" +  src)));
	}
	
	@Override
	public Resource finalizeResource() {
		if(isFinalized){return null;};
		if(this.ambientTexture != null) {			
			this.ambientTexture.finalizeResource();
			this.material.setAmbientTexture(this.ambientTexture.getTexture());
		}
		if(this.diffuseTexture != null) {	
			this.diffuseTexture.finalizeResource();
			this.material.setDiffuseTexture(this.diffuseTexture.getTexture());
		}
		if(this.specularTexture != null) {	
			this.specularTexture.finalizeResource();
			this.material.setSpecularTexture(this.specularTexture.getTexture());
		}
		this.isFinalized = true;
		return null;
	}

	public Material convertToMaterial() {
		return this.material.clone();
	}
}
