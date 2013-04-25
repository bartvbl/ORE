package orre.gameStates;

import openrr.map.Map;
import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gl.renderer.RenderPass;
import orre.resources.ResourceCache;
import orre.sceneGraph.Camera;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private EmptySceneNode sceneRoot;
	private Camera defaultCamera;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache)
	{
		super(main, eventDispatcher, cache);
		
	}
	public void initialize()
	{
		
	}
	
	public void executeFrame(long frameNumber) {
		this.gameWorld.tick();
		RenderPass.render(sceneRoot);
	}
	
	public void set() {
		System.out.println("game has started.");
		Map map = resourceCache.getMap();
		map.buildAll();
		this.sceneRoot = new EmptySceneNode();
		EmptySceneNode mapContentsRoot = new EmptySceneNode();
		SceneNode mapNode = map.createSceneNode();
		sceneRoot.addChild(mapNode);
		mapNode.addChild(mapContentsRoot);
		this.gameWorld = new GameWorld(mapContentsRoot, map);
		defaultCamera = new Camera();
		gameWorld.services.cameraService.setCurrentCamera(defaultCamera);
		defaultCamera.translate(0, 0, 100);
		int cameraController = gameWorld.spawnGameObject(GameObjectType.KEYBOARD_CAMERA_CONTROLLER);
		gameWorld.dispatchMessage(new Message<Camera>(MessageType.ASSUME_CAMERA_CONTROL, defaultCamera), cameraController);
	}
	
	public void unset() {
			
	}
}
