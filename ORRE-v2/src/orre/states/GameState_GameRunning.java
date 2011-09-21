package orre.states;

import java.util.ArrayList;

import orre.modules.Module;

public class GameState_GameRunning extends GameState {
	public GameState_GameRunning(ArrayList<Module> moduleList)
	{
		super(moduleList);
	}
	public void initialize()
	{
		
	}
	public void execute(long gameFrame)
	{
		this.threadManager.tick();
	}
	
}
