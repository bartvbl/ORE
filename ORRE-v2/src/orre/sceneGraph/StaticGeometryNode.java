package orre.sceneGraph;

public class StaticGeometryNode  extends CoordinateNode implements SceneNode 
{
	
	public void render() 
	{
		
		this.renderChildren();
	}
	
	public void destroy() 
	{
		
	}

}
