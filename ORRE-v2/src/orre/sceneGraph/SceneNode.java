package orre.sceneGraph;

public interface SceneNode {
	public void translate(float x, float y, float z);
	public void rotate(float x, float y, float z);
	public void setLocation(float x, float y, float z);
	public void setRotation(float rotationX, float rotationY, float rotationZ);
	
	public void render();
	
	public void addChild(SceneNode node);
	public void removeChild(SceneNode node);
	public void destroy();
	
	public void setVisibility(boolean isVisible);
}
