package orre.sceneGraph;

import java.util.ArrayList;

public class EmptySceneNode {
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	
	public float getRenderRadius()
	{
		return this.renderRadius;
	}
	
	public void translate(float x, float y, float z) {}
	public void rotate(float x, float y, float z) {}
	public void setLocation(float x, float y, float z) {}
	public void setRotation(float rotationX, float rotationY, float rotationZ) {}

	public void addChild(SceneNode node) 
	{
		this.children.add(node);
	}

	public void removeChild(SceneNode node) 
	{
		this.children.remove(node);
	}

	public void setVisibility(boolean isVisible) 
	{
		this.visible = isVisible;
	}
	
	protected void renderChildren()
	{
		for(SceneNode child : this.children)
		{
			child.render();
		}
	}
}

