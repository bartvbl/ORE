package orre.sceneGraph;

public class StaticGeometryNode  extends SimpleSceneNode implements SceneNode 
{
	
	public void render() 
	{
		
		this.renderChildren();
	}
	
	public void destroy() 
	{
		
	}

}
