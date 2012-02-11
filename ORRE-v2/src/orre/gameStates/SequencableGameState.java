package orre.gameStates;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.gameStates.GameState.State;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	protected final EventDispatcher globalEventDispatcher;
	private final State stateName;
	private AbstractGameState nextState;

	public SequencableGameState(GameMain main, EventDispatcher eventDispatcher, State stateName) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.stateName = stateName;
	}
	
	public abstract void executeFrame(long frameNumber);
	
	public void setNextState(AbstractGameState nextState)
	{
		this.nextState = nextState;
	}
	
	protected void finish()
	{
		this.globalEventDispatcher.dispatchEvent(new Event(GlobalEventType.CHANGE_GAME_STATE, this.nextState));
	}
	
}
