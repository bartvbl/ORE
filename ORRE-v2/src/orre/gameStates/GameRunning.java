package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameObject;
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
	private Camera defaultCamera;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ScriptInterpreter interpreter)
	{
		super(main, eventDispatcher, cache, interpreter);
		
	}
	public void initialize()
	{
		
	}
	
	public void executeFrame(long frameNumber) {
		this.gameWorld.tick();
		RenderPass.render(sceneRoot);
		gameWorld.services.inputService.updateMouseTargetLocation();
	}
	
	public void set() {
		System.out.println("game has started.");
		
		this.sceneRoot = new ContainerNode();
		
		ContainerNode mapContentsRoot = new ContainerNode();
		this.gameWorld = new GameWorld(this.resourceCache, interpreter);
		defaultCamera = new Camera();
		ShaderNode shader = ((Shader) resourceCache.getResource(ResourceType.shader, "phong").content).createSceneNode();
		sceneRoot.addChild(shader);
		gameWorld.services.cameraService.setCurrentCamera(defaultCamera, gameWorld);
		int cameraController = gameWorld.spawnGameObject(GameObjectType.CAMERA_CONTROLLER);
		gameWorld.dispatchMessage(new Message<Camera>(MessageType.ASSUME_CAMERA_CONTROL, defaultCamera), cameraController);
		gameWorld.spawnGameObject(GameObjectType.GUI);
	}
	
	public void unset() {
		gameWorld.services.shutdown();
	}
}
