package orre.gl.materials;

import orre.gl.texture.Texture;

public interface AbstractMaterial {
	
	public void setAmbientColour(float[] colour);
	public void setDiffuseColour(float[] colour);
	public void setSpecularColour(float[] colour);
	
	public void setMaterialAsColourMaterial(boolean isColourMaterial);
	
	public void setAlpha(float alpha);
}
