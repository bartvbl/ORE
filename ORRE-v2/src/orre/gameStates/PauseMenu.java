package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameStates.GameState;
import orre.gl.renderer.RenderState;
import orre.resources.ResourceService;
import orre.scripting.ScriptInterpreter;

public class PauseMenu extends GameState {

	public PauseMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService, ScriptInterpreter interpreter) {
		super(main, eventDispatcher, resourceService, interpreter);
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
