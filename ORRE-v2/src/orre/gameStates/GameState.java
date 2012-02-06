package orre.gameStates;

import orre.core.GameMain;
import orre.events.ConcurrentEventDispatcher;
import orre.events.EventDispatcher;
import orre.resources.ResourceCache;
import orre.scene.Scene;
import orre.threads.ThreadManager;

public abstract class GameState implements AbstractGameState {
	public static final int MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME = 10;
	
	protected ThreadManager threadManager;
	protected Scene sceneGraph;
	protected ConcurrentEventDispatcher eventDispatcher;
	protected ResourceCache resourceCache;
	
	protected final GameMain main;
	protected final EventDispatcher globalEventDispatcher;
	
	public GameState(GameMain main, EventDispatcher eventDispatcher)
	{
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
	}
	
//	private void initializeModules()
//	{
//		if(this.currentState != State.LOADING)
//		{
//			Logger.log("a gameState must have the loading state to initialize", Logger.LogType.ERROR);
//			return;
//		}
//		ArrayList<Module> moduleList = this.initializeMainThreadModules(this.eventDispatcher, this.sceneGraph);
//		if(moduleList == null)
//		{
//			Logger.log("a child clas of GameState must return a non-null list of modules on an InitializeModules() call", Logger.LogType.ERROR);
//			return;
//		}
//		ThreadManager threadManager = new ThreadManager(moduleList);
//		
//		ArrayList<ArrayList<Module>> workerThreadModuleCueList = this.initializeWorkerThreadModules(this.eventDispatcher, this.sceneGraph);
//		ArrayList<ArrayList<Module>> continuousThreadModuleCueList = this.initializeContinuousThreadModules(this.eventDispatcher, this.sceneGraph);
//		
//		for(ArrayList<Module> currentModuleCue : workerThreadModuleCueList)
//		{
//			threadManager.createSyncedWorkerThread(currentModuleCue);
//		}
//		for(ArrayList<Module> currentModuleCue : continuousThreadModuleCueList)
//		{
//			threadManager.createContinuousThread(currentModuleCue, GameState.MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME);
//		}
//		
//		this.threadManager = threadManager;
//	}
}
