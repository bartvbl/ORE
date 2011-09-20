package orre.modules;

import java.util.ArrayList;

public class Module {
	public static final int MODULE_SOUND 			= 0;
	public static final int MODULE_RENDERER2D 		= 1;
	public static final int MODULE_RENDERER3D 		= 2;
	public static final int MODULE_ANIMATION 		= 3;
	public static final int MODULE_GUI 				= 4;
	public static final int MODULE_AI 				= 5;
	public static final int MODULE_GAME_MANAGER 	= 6;
	
	public static final int TOTAL_NUMBER_OF_MODULES = 7;
	
	private int[] dependentModuleList = new int[0];
	private int moduleID = -1;
	
	//TODO: pass in references to event system, scene graph and 
	public Module(int moduleID)
	{
		this.moduleID = moduleID;
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
	
}
