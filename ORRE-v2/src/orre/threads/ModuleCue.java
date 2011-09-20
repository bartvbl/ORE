package orre.threads;

import java.util.Arrays;
import java.util.Stack;

import orre.modules.Module;

public class ModuleCue {
	private Stack<Module> moduleStack = new Stack<Module>();
	private Module[] modules;
	private boolean[] dependencies = new boolean[Module.TOTAL_NUMBER_OF_MODULES];
	
	public ModuleCue(Module[] modules)
	{
		this.modules = modules;
		Arrays.fill(dependencies, false);
	}
	
	public Module getNextModuleForExecution()
	{
		for(Module currentModule : this.modules)
		{
			if(this.checkDependencies(currentModule))
			{
				return currentModule;
			}
		}
		return null;
	}
	public void markModuleAsCompleted(int moduleID)
	{
		this.dependencies[moduleID] = true;
	}
	public boolean cueIsFinished()
	{
		return moduleStack.isEmpty();
	}
	public void reset()
	{
		Arrays.fill(this.dependencies, false);
		for(Module i:modules)
		{
			this.moduleStack.add(i);
		}
	}
	private boolean checkDependencies(Module module)
	{
		int[] dependencies = module.getDependentModules();
		for(int currentModuleID : dependencies)
		{
			if(this.dependencies[currentModuleID] == false)
			{
				return false;
			}
		}
		return true;
	}
}
