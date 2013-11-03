package orre.ai.pathFinding;

import java.util.List;

public class Path {
	private final List<State<?>> path;
	private int indexOfCurrentStep = 0;
	
	public Path(List<State<?>> path) {
		this.path = path;
	}
	
	public boolean hasFinished() {
		return indexOfCurrentStep == this.path.size() - 1;
	}
	
	public State<?> getNextState() {
		if(hasFinished()) {
			return this.path.get(indexOfCurrentStep - 1);
		}
		this.indexOfCurrentStep++;
		return this.path.get(indexOfCurrentStep);
	}
	
	public void backToStart() {
		this.indexOfCurrentStep = 0;
	}
	
	public State<?> getStartingState() {
		return this.path.get(0);
	}
}
