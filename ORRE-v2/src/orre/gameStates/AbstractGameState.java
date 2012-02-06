package orre.gameStates;

public interface AbstractGameState {
	public void set();
	public void unset();
	public void tick(long frameNumber);
	
}
