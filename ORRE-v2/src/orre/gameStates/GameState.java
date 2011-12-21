package orre.gameStates;

import java.util.ArrayList;

import orre.events.EventDispatcher;
import orre.events.EventType;
import orre.modules.Module;
import orre.resources.ResourceCache;
import orre.scene.Scene;
import orre.sceneGraph.SceneGraph;
import orre.threads.ThreadManager;

public class GameState {
	public static final int STARTUP 		= 0;
	public static final int MAIN_MENU 		= 1;
	public static final int PAUSE_MENU 		= 2;
	public static final int GAME 			= 3;
	public static final int MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME = 10;
	
	private static enum State {INACTIVE, LOADING, ACTIVE};
	private State currentState = State.INACTIVE;
	
	protected ThreadManager threadManager;
	protected Scene sceneGraph;
	protected EventDispatcher eventDispatcher;
	
	
	public GameState()
	{
		
	}
	
	protected ArrayList<Module> initializeMainThreadModules(EventDispatcher eventDispatcher, Scene scene){return null;}
	protected ArrayList<ArrayList<Module>> initializeWorkerThreadModules(EventDispatcher eventDispatcher, Scene scene) {return null;}
	protected ArrayList<ArrayList<Module>> initializeContinuousThreadModules(EventDispatcher eventDispatcher, Scene scene) {return null;}
	
	protected void executeLoadingFrame(long frameNumber) {}
	protected void executeFrame(long frameNumber){}
	protected void unloadState() {}
	
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
	
	protected void loadingFinished(ResourceCache resourceCache)
	{
		if(resourceCache == null)
		{
			System.out.println("ERROR: the resource cahce produced by the game state loader can not be null");
			return;
		}
		this.currentState = State.ACTIVE;
		this.initializeModules();
		
	}
	
	private void initializeModules()
	{
		if(this.currentState != State.LOADING)
		{
			System.out.println("ERROR: a gameState must have the loading state to initialize");
			return;
		}
		ArrayList<Module> moduleList = this.initializeMainThreadModules(this.eventDispatcher, this.sceneGraph);
		if(moduleList == null)
		{
			System.out.println("ERROR: a child clas of GameState must return a non-null list of modules on an InitializeModules() call");
			return;
		}
		ThreadManager threadManager = new ThreadManager(moduleList);
		
		ArrayList<ArrayList<Module>> workerThreadModuleCueList = this.initializeWorkerThreadModules(this.eventDispatcher, this.sceneGraph);
		ArrayList<ArrayList<Module>> continuousThreadModuleCueList = this.initializeContinuousThreadModules(this.eventDispatcher, this.sceneGraph);
		
		for(ArrayList<Module> currentModuleCue : workerThreadModuleCueList)
		{
			threadManager.createSyncedWorkerThread(currentModuleCue);
		}
		for(ArrayList<Module> currentModuleCue : continuousThreadModuleCueList)
		{
			threadManager.createContinuousThread(currentModuleCue, GameState.MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME);
		}
		
		this.threadManager = threadManager;
	}
	
	private void forwardGameEvents() 
	{
		
	}
}
