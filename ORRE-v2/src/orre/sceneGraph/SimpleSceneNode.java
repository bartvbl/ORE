package orre.sceneGraph;

import java.util.ArrayList;

public class SimpleSceneNode {
	protected float rotationX, rotationY, rotationZ;
	protected float x, y, z;
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	
	public void translate(float x, float y, float z) 
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void rotate(float x, float y, float z) 
	{
		this.rotationX += x;
		this.rotationY += y;
		this.rotationZ += z;
	}

	public void setLocation(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setRotation(float rotationX, float rotationY, float rotationZ) 
	{
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
	}

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
}
