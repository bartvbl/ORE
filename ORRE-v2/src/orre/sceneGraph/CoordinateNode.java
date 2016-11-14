package orre.sceneGraph;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.geom.Axis;
import orre.geom.Point3D;
import orre.rendering.RenderState;

import static org.lwjgl.opengl.GL11.*;

public class CoordinateNode extends ContainerNode {
	protected double rotationX, rotationY, rotationZ;
	protected double x, y, z;
	protected double pivotX, pivotY, pivotZ;
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	
	public CoordinateNode(String name) {
		super(name);
	}
	
	public CoordinateNode() {}

	@Override
	public float getRenderRadius()
	{
		return this.renderRadius;
	}
	
	public Point3D getLocation() {
		return new Point3D(x, y, z);
	}
	
	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		state.transformations.translate((float) (x - pivotX), (float) (y - pivotY), (float) (z - pivotZ));
		state.transformations.rotate((float) rotationZ, Axis.z.vector);
		state.transformations.rotate((float) rotationY, Axis.y.vector);
		state.transformations.rotate((float) rotationX, Axis.x.vector);
		state.transformations.translate((float) (pivotX), (float) (pivotY), (float) (pivotZ));
	}
	
	@Override
	public void postRender(RenderState state) {
		state.transformations.popMatrix();
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
	
	public void setPivotLocation(double x, double y, double z) {
		this.pivotX = x;
		this.pivotY = y;
		this.pivotZ = z;
	}

	public void setRotation(double rotationX, double rotationY, double rotationZ) 
	{
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public void setRotationX(double rotation) {
		this.rotationX = rotation;
	}
	
	public void setRotationY(double rotation) {
		this.rotationY = rotation;
	}
	
	public void setRotationZ(double rotation) {
		this.rotationZ = rotation;
	}
	
	@Override
	public String toString() {
		return "Coordinate node " + (name == null ? "" : name);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public double getRotationX() {
		return rotationX;
	}
	
	public double getRotationY() {
		return rotationY;
	}
	
	public double getRotationZ() {
		return rotationZ;
	}

}
