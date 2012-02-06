package orre.gameStates;

public interface AbstractGameState {
	public void set();
	public void unset();
	public void executeFrame(long frameNumber);
	
}
