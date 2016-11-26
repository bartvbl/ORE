package orre.gl.lighting;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.Sphere;
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
	private float[] zero = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
	
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	private float height = 3;
	
	public Light() {
		
	}
	
	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
			height += 0.1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
			height -= 0.1;
		}
		
		Matrix4f MVP = state.transformations.peekMatrix();
		Vector4f originalPosition = new Vector4f(position[0], position[1], position[2] + height, position[3]);
		Vector4f transformedPosition = new Vector4f(0, 0, 0, 1);
		Matrix4f.transform(MVP, originalPosition, transformedPosition);
		float[] transformed = new float[]{transformedPosition.x, transformedPosition.y, transformedPosition.z, transformedPosition.w};
		
		state.shaders.setProperty4f(ShaderProperty.LIGHT_POSITION, transformed);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_AMBIENT, ambientLight);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_DIFFUSE, diffuseLight);
		state.shaders.setProperty4f(ShaderProperty.LIGHT_SPECULAR, specularLight);
		
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
