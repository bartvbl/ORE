package orre.gameStates;

import java.io.File;

import openrr.map.Map;
import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.properties.Flashlight;
import orre.geom.mesh.Mesh3D;
import orre.gl.lighting.Light;
import orre.gl.renderer.RenderPass;
import orre.gl.shaders.ShaderNode;
import orre.gl.shaders.UniformNode;
import orre.gui.Corner;
import orre.gui.GUI;
import orre.gui.nodes.ButtonNode;
import orre.gui.nodes.GUIBaseNode;
import orre.gui.nodes.GUIRootNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.sceneGraph.Camera;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private ContainerNode sceneRoot;
	private Camera defaultCamera;
	private Flashlight flashLight;
	private Map map;
	private GUI gui;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache)
	{
		super(main, eventDispatcher, cache);
		
	}
	public void initialize()
	{
		
	}
	
	public void executeFrame(long frameNumber) {
		this.gameWorld.tick();
		flashLight.tick();
		this.gui.update();
		RenderPass.render(sceneRoot);
		gameWorld.services.inputService.updateMouseTargetLocation();
	}
	
	public void set() {
		boolean shaderEnabled = true;
		System.out.println("game has started.");
		this.map = (Map) resourceCache.getResource(ResourceType.MAP_FILE, "MAP").content;
		
		this.sceneRoot = new ContainerNode();
		
		ContainerNode mapContentsRoot = new ContainerNode();
		this.gameWorld = new GameWorld(sceneRoot, mapContentsRoot, map, this.resourceCache);
		GameObject object = new GameObject(GameObjectType.LIGHT, gameWorld);
		flashLight = new Flashlight(object);
		defaultCamera = new Camera();
		SceneNode mapNode = map.createSceneNode();
		ShaderNode shader = new ShaderNode(new File("res/shaders/phong.vert"), new File("res/shaders/phong.frag"));
		sceneRoot.addChild(flashLight.light);
		if(shaderEnabled) {
			sceneRoot.addChild(shader);
		} else {
			sceneRoot.addChild(mapNode);
		}
		shader.addChild(mapNode);
		mapNode.addChild(mapContentsRoot);
		gameWorld.services.cameraService.setCurrentCamera(defaultCamera, gameWorld);
		int cameraController = gameWorld.spawnGameObject(GameObjectType.CAMERA_CONTROLLER);
		gameWorld.spawnGameObject(GameObjectType.ORE);
		gameWorld.spawnGameObject(GameObjectType.ROCK_RAIDER);
		gameWorld.dispatchMessage(new Message<Camera>(MessageType.ASSUME_CAMERA_CONTROL, defaultCamera), cameraController);
		
		this.gui = new GUI(sceneRoot);
	}
	
	public void unset() {
		gameWorld.services.shutdown();
	}
}
