package openrr.test;

import java.util.ArrayList;
import java.util.HashMap;

public class Button extends InteractiveElement {
	
	protected String state;
	
	public Button(int[] posData, String startState, EventDispatcher eventDispatcher, Frame parent) {
		super(posData, eventDispatcher, parent);
		setState(state);
	}
	
	public void setState(String newState) {
		state = newState;
	}
	
	public String getState() {
		return state;
	}
	
	public void press() {
		setState("p");
	}
			
	public void hover() {
		setState("h");
	}
		
	public void release() {
		setState("n");
	}
}
