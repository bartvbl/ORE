package orre.gameStates;

import org.lwjgl.input.Keyboard;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.gameStates.GameState;

public class PauseMenu extends GameState {

	public PauseMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
	}

	public void executeFrame(long frameNumber) {
	}

	public void set() {
	}

	@Override
	public void unset() {
		// TODO Auto-generated method stub
		
	}



}
