package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gl.renderer.RenderPass;
import orre.gl.shaders.ShaderNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.resources.partiallyLoadables.Shader;
import orre.sceneGraph.Camera;
import orre.sceneGraph.ContainerNode;
import orre.scripting.ScriptInterpreter;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private ContainerNode sceneRoot;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ScriptInterpreter interpreter)
	{
		super(main, eventDispatcher, cache, interpreter);
		
	}
	public void initialize()
	{
		
	}
	
	@Override
	public void executeFrame(long frameNumber) {
		this.gameWorld.tick();
		RenderPass.render(this.sceneRoot);
	}
	
	@Override
	public void set() {
		System.out.println("game has started.");
		
		this.sceneRoot = new ContainerNode();
		
		ContainerNode cameraContainer = new ContainerNode();
		sceneRoot.addChild(cameraContainer);
		ShaderNode shader = ((Shader) resourceCache.getResource(ResourceType.shader, "phong").content).createSceneNode();
		sceneRoot.addChild(shader);
		this.gameWorld = new GameWorld(this.resourceCache, interpreter, shader, sceneRoot, cameraContainer);
		gameWorld.spawnGameObject(GameObjectType.CAMERA_CONTROLLER);
		gameWorld.spawnGameObject(GameObjectType.DEV_TOOLS);
		gameWorld.spawnGameObject(GameObjectType.GUI);
	}
	
	@Override
	public void unset() {
		gameWorld.services.shutdown();
	}
}
