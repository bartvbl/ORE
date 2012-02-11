package orre.gameStates;

import orre.core.GameMain;
import orre.events.EventDispatcher;
import orre.gameStates.GameState.State;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	protected final EventDispatcher globalEventDispatcher;
	private final State stateName;
	private State nextState;

	public SequencableGameState(GameMain main, EventDispatcher eventDispatcher, State stateName) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.stateName = stateName;
	}
	
	public abstract void executeFrame(long frameNumber);
	
	public void setNextState(GameState.State nextState)
	{
		this.nextState = nextState;
	}
	
	protected void finish()
	{
		
	}
	
}
