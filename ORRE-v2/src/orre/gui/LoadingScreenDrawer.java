package orre.gui;

import orre.rendering.RenderState;
import orre.resources.ResourceLoader;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public abstract class LoadingScreenDrawer extends ContainerNode implements SceneNode {
	private final ResourceLoader loader;

	public LoadingScreenDrawer(ResourceLoader loader) {
		this.loader = loader;
	}
	
	protected abstract void draw(double progress, RenderState state);
	
	@Override
	public void render(RenderState state) {
		draw(loader.getProgress(), state);
	}
}
