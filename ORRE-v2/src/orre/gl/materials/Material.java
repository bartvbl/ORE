package orre.gl.materials;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.texture.Texture;
import orre.rendering.RenderState;
import orre.rendering.ShaderProperty;
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
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		if(diffuseTexture != null) {
			state.shaders.setPropertyb(ShaderProperty.TEXTURES_ENABLED, true);
			state.shaders.setPropertyi(ShaderProperty.TEXTURE, this.diffuseTexture.id);			
		} else {
			state.shaders.setPropertyb(ShaderProperty.TEXTURES_ENABLED, false);
		}
		
		state.shaders.setPropertyf(ShaderProperty.MATERIAL_SHININESS, shininess);
		state.shaders.setProperty4f(ShaderProperty.MATERIAL_AMBIENT, this.ambientColour);
		state.shaders.setProperty4f(ShaderProperty.MATERIAL_DIFFUSE, this.diffuseColour);
		state.shaders.setProperty4f(ShaderProperty.MATERIAL_SPECULAR, this.specularColour);
		state.shaders.setProperty4f(ShaderProperty.MATERIAL_EMISSION, this.emissionColour);
	}
	
	@Override
	public void render(RenderState state) 
	{
	}
	
	@Override
	public void postRender(RenderState state) {
		state.transformations.popMatrix();
	}

}
