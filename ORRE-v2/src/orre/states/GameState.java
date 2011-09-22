package orre.states;

import java.util.ArrayList;

import orre.events.EventDispatcher;
import orre.events.EventType;
import orre.modules.Module;
import orre.sceneGraph.SceneGraph;
import orre.threads.ThreadManager;

public class GameState {
	public static final int STARTUP 		= 0;
	public static final int MAIN_MENU 		= 1;
	public static final int PAUSE_MENU 		= 2;
	public static final int GAME 			= 3;
	
	private static enum State {INACTIVE, LOADING, ACTIVE};
	private State currentState = State.INACTIVE;
	
	protected ThreadManager threadManager;
	protected SceneGraph sceneGraph;
	protected EventDispatcher eventDispatcher;
	
	
	public GameState(ArrayList<Module> moduleList)
	{
		
		ThreadManager threadManager = new ThreadManager(moduleList);
		this.threadManager = threadManager;
	}
	
	public void tick(long frameNumber)
	{
		this.forwardGameEvents();
		if(this.currentState == State.ACTIVE)
		{
			this.executeFrame(frameNumber);
			return;
		} else if(this.currentState == State.LOADING)
		{
			this.executeLoadingFrame(frameNumber);
			return;
		}
		System.out.println("ERROR: tried calling an inactive game state.");
	}
	
	protected void executeLoadingFrame(long frameNumber) {}
	protected void executeFrame(long frameNumber){}
	protected void unloadState() {}
	
	public void set()
	{
		if(this.currentState != State.INACTIVE)
		{
			System.out.println("ERROR: can not set game state: state is already active.");
			return;
		}
		this.currentState = State.LOADING;
	}
	
	public void unset()
	{
		if(this.currentState != State.ACTIVE)
		{
			System.out.println("ERROR: can not unset game state: state is not active, or has not finished loading.");
			return;
		}
		this.unloadState();
		this.currentState = State.INACTIVE;
	}
	
	protected void loadingFinished()
	{
		this.currentState = State.ACTIVE;
	}
	
	private void forwardGameEvents() 
	{
		this.eventDispatcher.GetEventsByEventType(EventType.GAME_CHANGE_GAMESTATE);
	}
}
