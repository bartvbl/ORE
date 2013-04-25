package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	protected final GlobalEventDispatcher globalEventDispatcher;
	private final GameStateName stateName;
	private GameStateName nextState;

	public SequencableGameState(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.stateName = stateName;
	}
	
	public abstract void executeFrame(long frameNumber);
	
	public void setNextState(GameStateName mainMenu)
	{
		this.nextState = mainMenu;
	}
	
	protected void finish()
	{
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent(GlobalEventType.CHANGE_GAME_STATE, this.nextState));
	}
	
}
