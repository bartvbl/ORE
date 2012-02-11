package orre.gameStates;

import java.util.ArrayList;

import orre.core.GameMain;
import orre.events.ConcurrentEventDispatcher;
import orre.events.EventDispatcher;
import orre.modules.Input;
import orre.modules.Module;
import orre.scene.Scene;

public class GameRunning extends GameState {
	public GameRunning(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName)
	{
		super(main, eventDispatcher, stateName);
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
