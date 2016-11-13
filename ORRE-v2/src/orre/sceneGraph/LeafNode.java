package orre.sceneGraph;

import java.util.ArrayList;

import orre.rendering.RenderState;

public class LeafNode implements SceneNode {
	private static final ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	private boolean isVisible = true;

	@Override
	public void preRender(RenderState state) {
	}

	@Override
	public void render() {
	}

	@Override
	public void postRender(RenderState state) {
	}

	@Override
	public void addChild(SceneNode node) {
	}

	@Override
	public void removeChild(SceneNode node) {
	}

	@Override
	public ArrayList<SceneNode> getChildren() {
		return children;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void setVisibility(boolean isVisible) {
		this.isVisible  = isVisible;
	}

	@Override
	public float getRenderRadius() {
		return 0;
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}

}
