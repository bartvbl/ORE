package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gl.renderer.RenderPass;
import orre.gl.shaders.ShaderNode;
import orre.gui.LoadingScreenDrawer;
import orre.rendering.RenderState;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;
import orre.resources.ResourceType;
import orre.resources.UnloadedResource;
import orre.resources.loaders.ShaderLoader;
import orre.resources.partiallyLoadables.Shader;
import orre.sceneGraph.ContainerNode;

public class LoadingScreen extends SequencableGameState {

	private ResourceLoader resourceLoader;
	private ShaderNode defaultShaderNode;
	private LoadingScreenDrawer loadingScreen;
	private ContainerNode sceneRoot;
	private Shader defaultShader;

	public LoadingScreen(GameMain main, GlobalEventDispatcher globalEventDispatcher, GameStateName stateName, ResourceCache cache, ResourceLoader loader) {
		super(main, globalEventDispatcher, stateName, cache);
		this.resourceLoader = loader;
	}
	
	@Override
	public void set() {
		this.sceneRoot = new ContainerNode();
		this.defaultShader = ShaderLoader.loadShader(new UnloadedResource(ResourceType.shader, new File("res/shaders/default"), ""));
		defaultShader.finalizeResource();
		this.defaultShaderNode = defaultShader.createSceneNode();
		sceneRoot.addChild(defaultShaderNode);
		
		if(loadingScreen == null) {
			throw new RuntimeException("Loading screen does not have a screen drawer!");
		}
		defaultShaderNode.addChild(loadingScreen);
	}
	
	
	@Override
	public void unset() {}

	@Override
	public void executeFrame(long frameNumber, RenderState state) {
		RenderPass.render(sceneRoot, state);
		
		this.resourceLoader.update();
		if(this.resourceLoader.isFinished())
		{
			this.resourceLoader = null;
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
