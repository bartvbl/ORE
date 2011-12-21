package orre.gameStates;

import java.util.ArrayList;

import orre.events.EventDispatcher;
import orre.modules.Input;
import orre.modules.Module;
import orre.scene.Scene;

public class GameRunning extends GameState {
	public GameRunning()
	{
		
	}
	public void initialize()
	{
		
	}
	protected ArrayList<Module> initializeModules(EventDispatcher eventDispatcher, Scene scene)
	{
		ArrayList<Module> moduleList = new ArrayList<Module>();
		moduleList.add(new Input(eventDispatcher, scene));
		return moduleList;
	}
	public void execute(long gameFrame)
	{
		this.threadManager.tick();
	}
	
}
