package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameStates.GameState;
import orre.resources.ResourceCache;
import orre.scripting.ScriptInterpreter;

public class PauseMenu extends GameState {

	public PauseMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ScriptInterpreter interpreter) {
		super(main, eventDispatcher, cache, interpreter);
	}

	public void executeFrame(long frameNumber) {
	}

	public void set() {
	}

	@Override
	public void unset() {
		
		
	}



}
