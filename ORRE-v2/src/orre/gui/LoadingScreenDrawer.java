package orre.gui;

import orre.gl.renderer.RenderState;
import orre.resources.ResourceLoader;
import orre.resources.ResourceService;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public abstract class LoadingScreenDrawer extends ContainerNode implements SceneNode {
	private final ResourceService resourceService;

	public LoadingScreenDrawer(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	protected abstract void draw(double progress, RenderState state);
	
	@Override
	public void render(RenderState state) {
		draw(resourceService.getCurrentBatchProgress(), state);
	}
}
