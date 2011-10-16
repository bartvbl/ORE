package orre.sceneGraph;

import java.util.ArrayList;

public class SimpleSceneNode extends EmptySceneNode{
	protected float rotationX, rotationY, rotationZ;
	protected float x, y, z;
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	
	public float getRenderRadius()
	{
		return this.renderRadius;
	}
	
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
}
