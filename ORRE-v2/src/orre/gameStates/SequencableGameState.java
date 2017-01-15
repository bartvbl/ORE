package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gl.renderer.RenderState;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;

public abstract class SequencableGameState implements AbstractGameState {
	private GameMain main;
	private final GameStateName stateName;
	private GameStateName nextState;

	protected final GlobalEventDispatcher globalEventDispatcher;
	protected final ResourceService resourceService;

	public SequencableGameState(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceService resourceService) {
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.stateName = stateName;
		this.resourceService = resourceService;
	}
	
	@Override
	public abstract void executeFrame(long frameNumber, RenderState state);
	
	public void setNextState(GameStateName mainMenu)
	{
		this.nextState = mainMenu;
	}
	
	protected void finish()
	{
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent(GlobalEventType.CHANGE_GAME_STATE, this.nextState));
	}
	
}
