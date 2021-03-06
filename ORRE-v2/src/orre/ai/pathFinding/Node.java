package orre.ai.pathFinding;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
	private final State state;
	private double distanceFromStart;
	private double fValue;
	private Node previousState;
	private State destinationState;
	
	public Node(State state, State destinationState) {
		this.state = state;
		this.destinationState = destinationState;;
		this.distanceFromStart = Double.MAX_VALUE;
		this.fValue = Double.MAX_VALUE;
		this.previousState = null;
	}
	
	@Override
	public int hashCode() {
		System.out.println("HashCode used");
		return state.hashCode();
	}
	
	public void setDistanceFromStart(double distance) {
		this.distanceFromStart = distance;
		this.fValue = this.distanceFromStart + this.state.estimateHeuristicTo(destinationState);
	}
	
	public double getDistanceFromStart() {
		return this.distanceFromStart;
	}
	
	public double getHeuristicValue(){
		return this.fValue;
	}
	
	public State getState() {
		return this.state;
	}
	
	public Node getPredecessor() {
		return this.previousState;
	}
	
	public List<Node> getSuccessors() {
		List<State> successors = this.state.getSuccessors();
		ArrayList<Node> nodeList = new ArrayList<Node>();
		for(State successor : successors) {
			Node successorNode = new Node(successor, destinationState);
			successorNode.setDistanceFromStart(this.distanceFromStart + this.state.getDistanceToSuccessor(successor));
			successorNode.previousState = this;
			nodeList.add(successorNode);
		}
		return nodeList;
	}
	
	public void relax(Node predecessor) {
		double distanceThroughPredecessor = predecessor.getDistanceFromStart() + predecessor.state.getDistanceToSuccessor(this.state);
		if(distanceThroughPredecessor < this.distanceFromStart) {
			this.setDistanceFromStart(distanceThroughPredecessor);
			this.setPredecessor(predecessor);
		}
	}

	private void setPredecessor(Node node) {
		this.previousState = node;
	}

	@Override
	public int compareTo(Node otherNode) {
		return Double.compare(this.fValue, otherNode.getHeuristicValue());
	}

	public boolean isEqualTo(Node otherNode) {
		return this.state.isEqualTo(otherNode.getState());
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(!(otherObject instanceof Node)) return false;
		return this.isEqualTo(((Node)otherObject));
	}
	
	@Override
	public String toString() {
		return this.state.toString();
	}

	public boolean isGoalState(Node goal) {
		return this.state.isEqualToGoalState(goal.state);
	}
	
	
}
