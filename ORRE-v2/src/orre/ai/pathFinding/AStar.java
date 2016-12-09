package orre.ai.pathFinding;

import java.util.ArrayList;
import java.util.List;

public class AStar {
	private NodeQueue openNodes;
	private List<Node> closedNodes;
	
	private Node startingNode;
	private Node destinationNode;
	
	public Path findPath(State<?> startingState, State<?> destinationState) {
		this.reset();
		this.destinationNode = new Node(destinationState, destinationState);
		this.startingNode = new Node(startingState, destinationState);
		this.initializeStartingNode();
		return this.performAStarSearch();
	}
	
	private void initializeStartingNode() {
		this.startingNode.setDistanceFromStart(0);
		this.openNodes.add(this.startingNode);
	}
	
	private Path performAStarSearch() {
		while(!this.openNodes.isEmpty()) {
			Node currentNode = this.openNodes.getNodeWithLowestheuristic();
			this.closedNodes.add(currentNode);
			if(goalHasBeenReached(currentNode)) {
				return PathFactory.generateSearchPath(startingNode, currentNode);
			} else {				
				this.visitCurrentNode(currentNode);
			}
		}
		return PathFactory.generateSearchedFailedPath(this.startingNode);
	}
	
	private void visitCurrentNode(Node currentNode) {
		List<Node> successors = currentNode.getSuccessors();
		for(Node successor : successors) {
			if(this.closedNodes.contains(successor)) continue;
			if(this.openNodes.contains(successor)) {
				Node registeredSuccessor = this.openNodes.getEquivalentNode(successor);
				registeredSuccessor.relax(currentNode);
			} else {
				this.openNodes.add(successor);
			}
		}
	}

	private boolean goalHasBeenReached(Node currentNode) {
		return currentNode.isGoalState(this.destinationNode);
	}

	private void reset() {
		this.openNodes = new NodeQueue();
		this.closedNodes = new ArrayList<Node>();
	}
}
