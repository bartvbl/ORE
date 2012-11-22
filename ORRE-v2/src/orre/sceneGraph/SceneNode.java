package orre.sceneGraph;

import java.util.ArrayList;

public interface SceneNode {
//	public void translate(float x, float y, float z);
//	public void rotate(float x, float y, float z);
//	public void setLocation(float x, float y, float z);
//	public void setRotation(float rotationX, float rotationY, float rotationZ);
	
	public void preRender();
	public void render();
	public void postRender();
	
	public void setParent(SceneNode parent);
	public SceneNode getParent();
	public boolean hasParent();
	
	public void addChild(SceneNode node);
	public void removeChild(SceneNode node);
	public ArrayList<SceneNode> getChildren();
	
	public void destroy();
	
	public void setVisibility(boolean isVisible);
	public float getRenderRadius();
	
}
