package orre.modules;

import orre.events.Event;

public class Module {
	public static final int MODULE_SOUND 			= 0;
	public static final int MODULE_RENDERER2D 		= 1;
	public static final int MODULE_RENDERER3D 		= 2;
	public static final int MODULE_ANIMATION 		= 3;
	public static final int MODULE_GUI 				= 4;
	public static final int MODULE_AI 				= 5;
	public static final int MODULE_GAME_MANAGER 	= 6;
	
	public static final int TOTAL_NUMBER_OF_MODULES = 7;
	public final boolean isExecutedContinuously;
	
	private int[] dependentModuleList = new int[0];
	private final int moduleID;
	private final TaskCue taskCue = new TaskCue();
	
	//TODO: pass in references to event system, scene graph and 
	public Module(int moduleID, boolean isExecutedContinuously)
	{
		this.moduleID = moduleID;
		this.isExecutedContinuously = isExecutedContinuously;
	}
	
	public void execute(){}
	public void initialize(){}
	public void onStateEnter(){}
	public void onStateRelease(){}
	
	public int[] getDependentModules()
	{
		return this.dependentModuleList;
	}
	public int getModuleID()
	{
		return this.moduleID;
	}
	public void addTask(Event<?> taskRequestEvent)
	{
		this.taskCue.addEventToCue(taskRequestEvent);
	}
	
	protected void addDependentmodule(int moduleID)
	{
		int[] oldList = this.dependentModuleList;
		this.dependentModuleList = new int[oldList.length + 1];
		for(int i : oldList)
		{
			this.dependentModuleList[i] = oldList[i];
		}
		this.dependentModuleList[this.dependentModuleList.length] = moduleID;
	}
	protected Event<?> getNextTask()
	{
		return this.taskCue.getNext();
	}
	
}
