package orre.util;

import java.util.ArrayList;

public class Stack<DataType> {
	private ArrayList<DataType> stack = new ArrayList<DataType>();
	
	public void push(DataType item) {
		this.stack.add(item);
	}
	
	public DataType pop() {
		if(isEmpty()) return null;
		return this.stack.remove(this.stack.size() - 1);
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public DataType peek() {
		if(isEmpty()) return null;
		return this.stack.get(this.stack.size() - 1);
	}

	public void set(DataType item) {
		if(isEmpty()) {
			throw new RuntimeException("Can not change value on top of empty stack");
		}
		stack.set(stack.size() - 1, item);
	}
	
	public String toString() {
		String string = "{";
		for(int i = 0; i < stack.size(); i++) {
			string += "{" + stack.get(i) + "}";
		}
		return string + "}";
	}
}
