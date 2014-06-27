package orre.gl.materials;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.shaders.ActiveShader;
import orre.gl.texture.Texture;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;
import static org.lwjgl.opengl.GL11.*;

public class Material extends ContainerNode implements SceneNode, AbstractMaterial {
	public final String name;
	private float[] ambientColour;
	private float[] diffuseColour;
	private float[] specularColour;
	private float[] emissionColour;
	private float shininess;
	private float alpha;
	private Texture ambientTexture = null;
	private Texture diffuseTexture = null;
	private Texture specularTexture = null;
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public Material(String name)
	{
		this.name = name;
		this.ambientColour = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
		this.diffuseColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.specularColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.emissionColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.alpha = 1f;
	}
	
	public void setAmbientTexture(Texture texture)
	{
		this.ambientTexture = texture;
	}
	public void setDiffuseTexture(Texture texture)
	{
		this.diffuseTexture = texture;
	}
	public void setSpecularTexture(Texture texture)
	{
		this.specularTexture = texture;
	}
	
	@Override
	public void setAmbientColour(float[] colour)
	{
		colour = formatColour(colour);
		this.ambientColour = colour;
	}
	@Override
	public void setDiffuseColour(float[] colour)
	{
		colour = formatColour(colour);
		this.diffuseColour = colour;
	}
	
	public void setDiffuseColour(double[] colour) {
		setDiffuseColour(new float[]{(float) colour[0], (float) colour[1], (float) colour[2], (float) colour[3]});
	}
	
	@Override
	public void setSpecularColour(float[] colour)
	{
		colour = formatColour(colour);
		this.specularColour = colour;
	}
	
	public void setSpecularColour(double[] colour) {
		setSpecularColour(new float[]{(float) colour[0], (float) colour[1], (float) colour[2], (float) colour[3]});
	}
	
	public void setEmissionColour(float[] colour)
	{
		colour = formatColour(colour);
		this.emissionColour = colour;
	}
	
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
	
	private float[] formatColour(float[] colour)
	{
		if(colour.length == 3)
		{
			return new float[]{colour[0], colour[1], colour[2], 1f};
		}
		return colour;
	}
	
	@Override
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	

	private FloatBuffer fillColourBuffer(float[] colour) {
		this.colourBuffer.put(colour[0]);
		this.colourBuffer.put(colour[1]);
		this.colourBuffer.put(colour[2]);
		this.colourBuffer.put(colour[3] * alpha);
		this.colourBuffer.rewind();
		return this.colourBuffer;
	}
	
	@Override
	public void destroy(){}
	
	@Override
	public Material clone() {
		Material material = new Material(this.name);
		material.setAlpha(this.alpha);
		material.setAmbientColour(this.ambientColour);
		material.setDiffuseColour(this.diffuseColour);
		material.setSpecularColour(this.specularColour);
		material.setEmissionColour(this.emissionColour);
		material.setShininess(this.shininess);
		if(this.ambientTexture != null) {material.setAmbientTexture(this.ambientTexture.clone());}
		if(this.diffuseTexture != null) {material.setDiffuseTexture(this.diffuseTexture.clone());}
		if(this.specularTexture != null) {material.setSpecularTexture(this.specularTexture.clone());}
		return material;
	}
	
	@Override
	public String toString() {
		return "Material " + this.name;
	}
	
	@Override
	public void preRender() {
		glPushMatrix();
		this.bindTexture();
		glMaterialf(GL_FRONT, GL_SHININESS, shininess);
		glMaterial(GL_FRONT, GL_AMBIENT, this.fillColourBuffer(this.ambientColour));
		glMaterial(GL_FRONT, GL_DIFFUSE, this.fillColourBuffer(this.diffuseColour));
		glMaterial(GL_FRONT, GL_SPECULAR, this.fillColourBuffer(this.specularColour));
		glMaterial(GL_FRONT, GL_EMISSION, this.fillColourBuffer(this.emissionColour));
	}
	
	private void bindTexture() {
		if(this.diffuseTexture != null) {
			glEnable(GL_TEXTURE_2D);
			this.diffuseTexture.bind();
			ActiveShader.setUniformValue1f("texturesEnabled", 1.0f);
		} else {
			glDisable(GL_TEXTURE_2D);
			ActiveShader.setUniformValue1f("texturesEnabled", 0.0f);
		}
	}
	
	@Override
	public void render() 
	{
	}
	
	@Override
	public void postRender() {
		glPopMatrix();
	}

}
