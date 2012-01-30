package orre.gameStates;

import java.util.ArrayList;

import orre.core.GameMain;
import orre.events.EventDispatcher;
import orre.modules.Module;
import orre.scene.Scene;

public class MainMenu extends GameState {

	public MainMenu(GameMain main) {
		super(main);	
	}
	protected void executeFrame(long frameNumber) {
		this.main.setGameState(GameState.MAIN_MENU);
	}

	protected void unloadState() {}

	protected void doPreload() {
		
	}

	protected void doPostLoad() {
		
	}

}
