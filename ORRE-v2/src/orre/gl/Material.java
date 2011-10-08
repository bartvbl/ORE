package orre.gl;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class Material extends SimpleSceneNode implements SceneNode {
	private float[] defaultLightAmbient = {3f, 3f, 3f, 1f};
	private float[] defaultLightDiffuse = {3f, 3f, 3f, 1.0f};
	private float[] defaultLightSpecular = {9f, 9f, 9f, 1.0f};
	private float[] defaultLightPosition = {0f, 1f, 0f, 0f};
	
	private Texture texture;
	
	public Material(Texture texture)
	{
		this.texture = texture;
	}
	
	public void render() 
	{
		// TODO write out material settings here
		this.renderChildren();
	}
	
	public void destroy() 
	{
		
	}
}
