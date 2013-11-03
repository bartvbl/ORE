package orre.ai.pathFinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	
	public void relax(Node successor) {
		if(successor.getDistanceFromStart() < this.distanceFromStart) {
			this.setDistanceFromStart(successor.getDistanceFromStart());
		}
	}

	public int compareTo(Node otherNode) {
		return Double.compare(this.fValue, otherNode.getHeuristicValue());
	}

	public boolean isEqualTo(Node otherNode) {
		return this.state.isEqualTo(otherNode.getState());
	}
	
	public boolean equals(Object otherObject) {
		if(!(otherObject instanceof Node)) return false;
		return this.isEqualTo(((Node)otherObject));
	}
	
	public String toString() {
		return this.state.toString();
	}
	
	
}
