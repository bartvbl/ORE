package orre.sceneGraph;

public class StaticMesh3D  extends SimpleSceneNode implements SceneNode 
{
	
	public void render() 
	{
		
		this.renderChildren();
	}
	
	public void destroy() 
	{
		
	}

}
