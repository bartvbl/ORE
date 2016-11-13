package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameStates.GameState;
import orre.rendering.RenderState;
import orre.resources.ResourceCache;
import orre.scripting.ScriptInterpreter;

public class PauseMenu extends GameState {

	public PauseMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ScriptInterpreter interpreter) {
		super(main, eventDispatcher, cache, interpreter);
	}

	@Override
	public void executeFrame(long frameNumber, RenderState state) {
	}

	@Override
	public void set() {
	}

	@Override
	public void unset() {
		
		
	}



}
