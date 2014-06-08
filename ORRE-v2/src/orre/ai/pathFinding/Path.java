package orre.ai.pathFinding;

import java.util.List;

public class Path {
	private final List<State<?>> path;
	public final boolean isFoundPath;
	private int indexOfCurrentStep = 0;
	
	public Path(List<State<?>> path, boolean isFoundPath) {
		this.path = path;
		this.isFoundPath = isFoundPath;
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

	public int getStepCount() {
		return path.size();
	}
}
