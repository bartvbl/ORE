package orre.gameStates;

import orre.core.GameMain;
import orre.events.EventDispatcher;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	protected final EventDispatcher globalEventDispatcher;

	public SequencableGameState(GameMain main, EventDispatcher eventDispatcher) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
	}
	
	protected abstract void executeFrame(long frameNumber);
	
	protected void setNextState(AbstractGameState nextState)
	{
		
	}
	
	protected void finish()
	{
		
	}
	
}
