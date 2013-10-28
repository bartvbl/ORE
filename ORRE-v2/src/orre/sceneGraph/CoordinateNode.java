package orre.sceneGraph;

import java.util.ArrayList;

import orre.geom.Point3D;

public class CoordinateNode extends ContainerNode{
	protected double rotationX, rotationY, rotationZ;
	protected double x, y, z;
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	
	public float getRenderRadius()
	{
		return this.renderRadius;
	}
	
	public Point3D getLocation() {
		return new Point3D(x, y, z);
	}
	
	public void translate(double x, double y, double z) 
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void rotate(double x, double y, double z) 
	{
		this.rotationX += x;
		this.rotationY += y;
		this.rotationZ += z;
	}

	public void setLocation(double x, double y, double z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setRotation(double rotationX, double rotationY, double rotationZ) 
	{
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
}