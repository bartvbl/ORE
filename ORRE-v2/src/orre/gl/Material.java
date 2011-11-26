package orre.gl;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class Material extends SimpleSceneNode implements SceneNode {
	public final String name;
	
	private Texture texture;
	
	public Material(String name)
	{
		this.name = name;
	}
	
	public void setAmbientTexture(Texture texture)
	{
		
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
