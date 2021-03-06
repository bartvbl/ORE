package orre.gl.lighting;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import orre.sceneGraph.SceneNode;
import orre.gl.renderer.RenderState;
import orre.gl.renderer.ShaderProperty;
import orre.sceneGraph.CoordinateNode;

public class Light extends CoordinateNode implements SceneNode {
	private float[] ambientLight =  new float[]{0.1f, 0.1f, 0.1f, 1.0f};
	private float[] diffuseLight =  new float[]{1f, 1f, 1f, 1f};
	private float[] specularLight = new float[]{1f, 1f, 1f, 1f};
	private float[] position = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
	private float specularStrength = 10f;
	
	private float attenuation_constant = 0.7f;
	private float attenuation_linear = 0.05f;
	private float attenuation_quadratic = 0.01f;
	
	private float[] zero = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
	
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public Light() {
		
	}
	
	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		
		Matrix4f MVMatrix = new Matrix4f();
		Matrix4f model = state.transformations.peekMatrix();
		Matrix4f view = state.transformations.getViewMatrix();
		Matrix4f.mul(view, model, MVMatrix);
		
		Vector4f originalPosition = new Vector4f(position[0], position[1], position[2], position[3]);
		Vector4f transformedPosition = new Vector4f(0, 0, 0, 1);
		Matrix4f.transform(model, originalPosition, transformedPosition);
		float[] transformed = new float[]{transformedPosition.x, transformedPosition.y, transformedPosition.z, transformedPosition.w};
		
		state.shaders.setProperty4f(ShaderProperty.LIGHT_POSITION, transformed);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_AMBIENT, ambientLight);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_DIFFUSE, diffuseLight);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_SPECULAR, specularLight);
		state.shaders.setPropertyf(ShaderProperty.LIGHT_SPECULAR_STRENGTH, specularStrength);
		state.shaders.setPropertyf(ShaderProperty.ATTENUATION_CONSTANT, attenuation_constant);
		state.shaders.setPropertyf(ShaderProperty.ATTENUATION_LINEAR, attenuation_linear);
		state.shaders.setPropertyf(ShaderProperty.ATTENUATION_QUADRATIC, attenuation_quadratic);
		
		state.transformations.popMatrix();
	}
	
	@Override
	public void postRender(RenderState state) {
		
	}
	
	public void setPosition(double x, double y, double z) {
		this.position[0] = (float) x;
		this.position[1] = (float) y;
		this.position[2] = (float) z;
	}
	
	public void setAmbientLight(float[] ambientLight) 
	{
		this.ambientLight = ambientLight;
	}
	
	public void setDiffuseLight(float[] diffuseLight)
	{
		this.diffuseLight = diffuseLight;
	}
	
	public void setSpecularLight(float[] specularLight)
	{
		this.specularLight = specularLight;
	}

	@Override
	public void destroy() {
		
	}
	
	@Override
	public String toString() {
		return "Light";
	}
}
