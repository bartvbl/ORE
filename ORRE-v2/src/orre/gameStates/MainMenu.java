package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gl.renderer.RenderState;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;
import orre.resources.ResourceType;
import orre.scripting.ScriptInterpreter;

public class MainMenu extends GameState {

	public MainMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService, ScriptInterpreter interpreter) {
		super(main, eventDispatcher, resourceService, interpreter);
		Resource mainCache = new Resource(new File("res/reslist.xml"), ResourceType.resourceList, "mainCacheList");
		eventDispatcher.dispatchEvent(new GlobalEvent<Resource>(GlobalEventType.REGISTER_AVAILABLE_RESOURCE_LIST, mainCache));
		
	}
	
	@Override
	public void executeFrame(long frameNumber, RenderState state) {
		
	}

	@Override
	public void set() {
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent<GameStateName>(GlobalEventType.CHANGE_GAME_STATE, GameStateName.GAME_LOADING));
	}
	@Override
	public void unset() {}
}
