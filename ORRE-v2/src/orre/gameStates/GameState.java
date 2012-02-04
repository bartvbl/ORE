package orre.gameStates;

import orre.core.GameMain;
import orre.events.EventDispatcher;
import orre.gui.LoadingScreenDrawer;
import orre.resources.ResourceFile;
import orre.resources.ResourceLoader;
import orre.scene.Scene;
import orre.threads.ThreadManager;
import orre.util.Logger;

public abstract class GameState {
	public static final int STARTUP 		= 0;
	public static final int MAIN_MENU 		= 1;
	public static final int PAUSE_MENU 		= 2;
	public static final int GAME 			= 3;
	public static final int MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME = 10;
	
	private static enum State {INACTIVE, LOADING, ACTIVE};
	private State currentState = State.INACTIVE;
	private ResourceLoader resourceLoader = new ResourceLoader();
	
	protected ThreadManager threadManager;
	protected Scene sceneGraph;
	protected EventDispatcher eventDispatcher;
	protected final GameMain main;
	
	public GameState(GameMain main)
	{
		this.main = main;
	}
	
	protected abstract void doPreload();
	protected abstract void doPostLoad();
	protected abstract void executeFrame(long frameNumber);
	protected abstract void unloadState();
	
	public void tick(long frameNumber)
	{
		if(this.currentState == State.ACTIVE)
		{
			this.executeFrame(frameNumber);
			return;
		} else if(this.currentState == State.LOADING)
		{
			this.resourceLoader.update();
			if(this.resourceLoader.isFinished())
			{
				this.currentState = State.ACTIVE;
				this.doPostLoad();
				System.out.println("loading finished");
			}
			return;
		}
		Logger.log("tried calling an inactive game state.", Logger.LogType.ERROR);
	}
	
	public void set()
	{
		if(this.currentState != State.INACTIVE)
		{
			Logger.log("can not set game state: state is already active.", Logger.LogType.ERROR);
			return;
		}
		this.currentState = State.LOADING;
		this.doPreload();
	}
	
	public void unset()
	{
		if(this.currentState != State.ACTIVE)
		{
			Logger.log("can not unset game state: state is not active, or has not finished loading.", Logger.LogType.ERROR);
			return;
		}
		this.unloadState();
		this.currentState = State.INACTIVE;
	}
	
	protected void enqueueResourceFileToBeLoaded(String src, ResourceFile resourceListFile)
	{
		this.resourceLoader.enqueueResourceFileToBeLoaded(src, resourceListFile);
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
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
