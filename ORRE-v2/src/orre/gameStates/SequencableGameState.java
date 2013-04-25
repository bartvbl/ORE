package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameStates.GameState.State;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	protected final GlobalEventDispatcher globalEventDispatcher;
	private final State stateName;
	private GameState.State nextState;

	public SequencableGameState(GameMain main, GlobalEventDispatcher eventDispatcher, State stateName) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.stateName = stateName;
	}
	
	public abstract void executeFrame(long frameNumber);
	
	public void setNextState(State mainMenu)
	{
		this.nextState = mainMenu;
	}
	
	protected void finish()
	{
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent(GlobalEventType.CHANGE_GAME_STATE, this.nextState));
	}
	
}
