package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gl.renderer.RenderPass;
import orre.gl.renderer.RenderState;
import orre.gl.shaders.ShaderNode;
import orre.gui.LoadingScreenDrawer;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;
import orre.resources.ResourceService;
import orre.resources.ResourceType;
import orre.resources.loaders.ShaderLoader;
import orre.resources.partiallyLoadables.Shader;
import orre.sceneGraph.ContainerNode;

public class LoadingScreen extends SequencableGameState {

	private ResourceService resourceService;
	private ShaderNode defaultShaderNode;
	private LoadingScreenDrawer loadingScreen;
	private ContainerNode sceneRoot;
	private Shader defaultShader;

	public LoadingScreen(GameMain main, GlobalEventDispatcher globalEventDispatcher, GameStateName stateName, ResourceService resourceService) {
		super(main, globalEventDispatcher, stateName, resourceService);
		this.resourceService = resourceService;
	}
	
	@Override
	public void set() {
		this.sceneRoot = new ContainerNode();
		this.defaultShader = (Shader) resourceService.getResource(ResourceType.shader, "default").content;
		this.defaultShaderNode = defaultShader.createSceneNode();
		sceneRoot.addChild(defaultShaderNode);
		
		if(loadingScreen == null) {
			throw new RuntimeException("Loading screen does not have a screen drawer!");
		}
		defaultShaderNode.addChild(loadingScreen);
	}
	
	
	@Override
	public void unset() {
		defaultShader.destroy();
	}

	@Override
	public void executeFrame(long frameNumber, RenderState state) {
		RenderPass.render(sceneRoot, state);
		
		if(this.resourceService.isCurrentQueueEmpty())
		{
			this.finish();
		}
		return;
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		if(this.loadingScreen != null && this.defaultShaderNode != null) {
			this.defaultShaderNode.removeChild(loadingScreen);
			this.defaultShaderNode.addChild(screenDrawer);
		}
		this.loadingScreen = screenDrawer;
	}
}
