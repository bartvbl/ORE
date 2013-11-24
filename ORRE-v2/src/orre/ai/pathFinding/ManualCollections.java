package orre.ai.pathFinding;

import java.util.ArrayList;
import java.util.List;

public class ManualCollections {
	public static boolean contains(List<Node> openNodes, Node successor) {
		State successorState = successor.getState();
		for(Node currentNode : openNodes) {
			if(successorState.isEqualTo(currentNode.getState())){
				return true;
			}
		}
		return false;
	}
	
	public static Node min(ArrayList<Node> queue) {
		int index = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < queue.size(); i++) {
			Node state = queue.get(i);
			double heuristic = state.getDistanceFromStart() + state.getHeuristicValue();
			if(heuristic < min) {
				index = i;
				min = heuristic;
			}
		}
		if(index == -1) return null;
		return queue.get(index);
	}

	public static boolean contains(NodeQueue openNodes, Node successor) {
		for(Node node : openNodes.queue) {
			if(successor.isEqualTo(node)) return true;
		}
		return false;
	}
}
