package orre.gl.materials;

public interface AbstractMaterial {
	
	public void setAmbientColour(float[] colour);
	public void setDiffuseColour(float[] colour);
	public void setSpecularColour(float[] colour);
	
	
	public void setAlpha(float alpha);
}
