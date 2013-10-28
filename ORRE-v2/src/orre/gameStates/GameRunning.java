package orre.gameStates;

import openrr.map.Map;
import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.properties.Flashlight;
import orre.gl.lighting.Light;
import orre.gl.renderer.RenderPass;
import orre.resources.ResourceCache;
import orre.sceneGraph.Camera;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private EmptySceneNode sceneRoot;
	private Camera defaultCamera;
	private Flashlight flashLight;
	private Map map;
	
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
		gameWorld.services.inputService.updateMouseTargetLocation();
		flashLight.tick();
	}
	
	public void set() {
		System.out.println("game has started.");
		this.map = resourceCache.getMap();
		this.sceneRoot = new EmptySceneNode();
		EmptySceneNode mapContentsRoot = new EmptySceneNode();
		this.gameWorld = new GameWorld(mapContentsRoot, mapContentsRoot, map);
		SceneNode mapNode = map.createSceneNode();
		sceneRoot.addChild(mapNode);
		mapNode.addChild(mapContentsRoot);
		defaultCamera = new Camera();
		gameWorld.services.cameraService.setCurrentCamera(defaultCamera);
		int cameraController = gameWorld.spawnGameObject(GameObjectType.CAMERA_CONTROLLER);
		gameWorld.dispatchMessage(new Message<Camera>(MessageType.ASSUME_CAMERA_CONTROL, defaultCamera), cameraController);
		GameObject object = new GameObject(GameObjectType.LIGHT, gameWorld);
		flashLight = new Flashlight(object);
	}
	
	public void unset() {
			
	}
}
