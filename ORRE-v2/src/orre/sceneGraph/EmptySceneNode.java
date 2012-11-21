package orre.sceneGraph;

import java.util.ArrayList;

public class EmptySceneNode implements SceneNode {
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	protected boolean parentHasBeenRegistered = false;
	protected SceneNode parent = null;
	
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
		node.setParent(this);
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

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
	}

	public void setParent(SceneNode parent) {
		this.parent = parent;
		this.parentHasBeenRegistered = true;
	}

	public ArrayList<SceneNode> getChildren() {
		return this.children;
	}
}

