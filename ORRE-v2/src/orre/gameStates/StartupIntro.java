package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.resources.ResourceCache;

public class StartupIntro extends SequencableGameState implements AbstractGameState {

	public StartupIntro(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache) {
		super(main, eventDispatcher, GameStateName.STARTUP_INTRO);
	}

	@Override
	public void set() {

	}

	@Override
	public void unset() {

	}

	@Override
	public void executeFrame(long frameNumber) {
		this.finish();
	}

}
