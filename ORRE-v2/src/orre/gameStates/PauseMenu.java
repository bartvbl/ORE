package orre.gameStates;

import org.lwjgl.input.Keyboard;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameStates.GameState;
import orre.resources.ResourceCache;

public class PauseMenu extends GameState {

	public PauseMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, GameStateName stateName) {
		super(main, eventDispatcher, cache, stateName);
	}

	public void executeFrame(long frameNumber) {
	}

	public void set() {
	}

	@Override
	public void unset() {
		
		
	}



}
