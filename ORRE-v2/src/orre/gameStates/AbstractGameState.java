package orre.gameStates;

import orre.rendering.RenderState;

public interface AbstractGameState {
	public void set();
	public void unset();
	public void executeFrame(long frameNumber, RenderState state);
	
}
