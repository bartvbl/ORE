package orre.gameStates;

import openrr.map.Map;
import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameWorld;
import orre.gl.renderer.RenderPass;
import orre.resources.ResourceCache;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private EmptySceneNode sceneRoot;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, GameStateName stateName)
	{
		super(main, eventDispatcher, cache, stateName);
		
	}
	public void initialize()
	{
		
	}
	
	public void executeFrame(long frameNumber) {
		RenderPass.render(sceneRoot);
	}
	
	public void set() {
		System.out.println("game has started.");
		Map map = resourceCache.getMap();
		this.sceneRoot = new EmptySceneNode();
		EmptySceneNode mapContentsRoot = new EmptySceneNode();
		SceneNode mapNode = map.createSceneNode();
		sceneRoot.addChild(mapNode);
		mapNode.addChild(mapContentsRoot);
		this.gameWorld = new GameWorld(mapContentsRoot, map);
	}
	
	public void unset() {
			
	}
}
