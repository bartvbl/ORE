package orre.threads;

import java.util.ArrayList;

import orre.modules.Module;

public class ModuleCue {
	private ArrayList<Module> moduleCue = new ArrayList<Module>();
	private int currentModuleIndex = 0;

	public ModuleCue(ArrayList<Module> modules)
	{
		if(modules == null)
		{
			System.out.println("WARNING: a module cue could not initialize, as the entered module list was null");
			return;
		}
		if(modules.size() == 0)
		{
			System.out.println("WARNING: a module cue could not initialize, as no modules were present in the entered module list");
			return;
		}
		
		this.moduleCue = modules;
	}

	public Module getNextModuleForExecution()
	{
		if(!cueIsFinished())
		{
			return this.moduleCue.get(currentModuleIndex);
		} else {
			return null;
		}
	}
	
	public boolean cueIsFinished()
	{
		return (this.moduleCue.size() == (this.currentModuleIndex + 1));
	}
	
	public void reset()
	{
		this.currentModuleIndex = 0;
	}
}
