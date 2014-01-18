package orre.ai.pathFinding;

import java.util.ArrayList;

public class PathFactory {
	public static Path generateSearchedFailedPath(Node startingNode) {
		ArrayList<State<?>> stateList = new ArrayList<State<?>>();
		stateList.add(startingNode.getState());
		return new Path(stateList, false);
	}
	
	public static Path generateSearchPath(Node startingNode, Node destinationNode) {
		ArrayList<State<?>> path = new ArrayList<State<?>>();
		Node currentNode = destinationNode;
		while(!currentNode.isEqualTo(startingNode)){
			path.add(0, currentNode.getState());
			currentNode = currentNode.getPredecessor();
		}
		path.add(0, startingNode.getState());
		return new Path(path, true);
	}
}
