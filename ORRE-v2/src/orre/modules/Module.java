package orre.modules;

import orre.events.ConcurrentEventDispatcher;
import orre.scene.Scene;

public class Module {
	public static final int MODULE_SOUND 			= 0;
	public static final int MODULE_RENDERER2D 		= 1;
	public static final int MODULE_RENDERER3D 		= 2;
	public static final int MODULE_ANIMATION 		= 3;
	public static final int MODULE_GUI 				= 4;
	public static final int MODULE_AI 				= 5;
	public static final int MODULE_GAME_MANAGER 	= 6;
	
	public static final int TOTAL_NUMBER_OF_MODULES = 7;
	
	protected final ConcurrentEventDispatcher eventDispatcher;
	protected final Scene sceneGraph;
	 
	public Module(ConcurrentEventDispatcher eventDispatcher, Scene sceneGraph)
	{
		this.eventDispatcher = eventDispatcher;
		this.sceneGraph = sceneGraph;
	}
	
	public void execute(){}
	public void initialize(){}
	public void onStateEnter(){}
	public void onStateRelease(){}
}
