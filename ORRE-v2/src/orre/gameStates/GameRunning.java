package orre.gameStates;

import java.util.ArrayList;

import orre.core.GameMain;
import orre.events.ConcurrentEventDispatcher;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.modules.Input;
import orre.modules.Module;
import orre.resources.FileToLoad;
import orre.resources.ResourceFile;
import orre.scene.Scene;

public class GameRunning extends GameState {
	public GameRunning(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName)
	{
		super(main, eventDispatcher, stateName);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
	}
	public void initialize()
	{
		
	}
	protected ArrayList<Module> initializeModules(ConcurrentEventDispatcher eventDispatcher, Scene scene)
	{
		ArrayList<Module> moduleList = new ArrayList<Module>();
		moduleList.add(new Input(eventDispatcher, scene));
		return moduleList;
	}
	
	public void executeFrame(long frameNumber) {
		
	}
	
	@Override
	public void set() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unset() {
		// TODO Auto-generated method stub
		
	}
}
