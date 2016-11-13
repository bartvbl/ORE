package orre.sceneGraph;

import java.util.ArrayList;

import orre.rendering.RenderState;

public interface SceneNode {
	public void preRender(RenderState state);
	public void render();
	public void postRender(RenderState state);
	
	public void addChild(SceneNode node);
	public void removeChild(SceneNode node);
	public ArrayList<SceneNode> getChildren();
	
	public void destroy();
	
	public void setVisibility(boolean isVisible);
	public boolean isVisible();

	public float getRenderRadius();
	
}
