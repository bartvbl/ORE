package orre.sceneGraph;

public class Camera extends SimpleSceneNode implements SceneNode 
{
	public Camera()
	{
		this.children = null;
	}
	
	public void render() 
	{
		
	}

	public void addChild(SceneNode node) 
	{
		System.out.println("WARNING: ignored attempt to add child SceneNode to Camera class; the Camera class can not have child SceneNodes");
	}

	public void removeChild(SceneNode node) 
	{
		System.out.println("WARNING: ignored attempt to remove child SceneNode from Camera class; the Camera class can not have child SceneNodes");
	}

	public void destroy() 
	{
		
	}

	public void setVisibility(boolean isVisible) 
	{
		System.out.println("WARNING: cameras can not be hidden");
	}

	public float getRenderRadius() {
		return 0;
	}
}
