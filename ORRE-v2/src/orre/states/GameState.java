package orre.states;

import orre.modules.Module;
import orre.sceneGraph.SceneGraphRootNode;
import orre.threads.ThreadManager;

public class GameState {
	public static final int STARTUP 		= 0;
	public static final int MAIN_MENU 		= 1;
	public static final int PAUSE_MENU 		= 2;
	public static final int GAME 			= 3;
	
	protected ThreadManager threadManager;
	protected SceneGraphRootNode sceneGraphRootNode;
	
	public GameState(Module[] moduleList)
	{
		ThreadManager threadManager = new ThreadManager(moduleList);
		this.threadManager = threadManager;
	}
	
	public void execute(long frameNumber){}
	public void initialize(){}
	public void destroy() {}
	
	public void set(){}
	public void unset(){}
}
