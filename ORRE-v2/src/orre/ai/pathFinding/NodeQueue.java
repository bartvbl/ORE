package orre.ai.pathFinding;

import java.util.ArrayList;
import java.util.Collections;


public class NodeQueue {
	public ArrayList<Node> queue;

	public NodeQueue() {
		this.queue = new ArrayList<Node>();
	}
	
	public void add(Node node) {
		this.queue.add(node);
	}
	
	public Node getEquivalentNode(Node element) {
		for(Node node : queue) {
			if(node.isEqualTo(element)) return node;
		}
		return this.queue.get(this.queue.indexOf(element));
	}
	
	public Node getNodeWithLowestheuristic() {
		Node node = ManualCollections.min(this.queue);
		queue.remove(node);
		return node;
	}
	
	public boolean contains(Node node) {
		return ManualCollections.contains(queue, node);
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	public int size() {
		return this.queue.size();
	}
	
	public String toString() {
		return "(" + this.size() + ") " + queue.toString();
	}
}
