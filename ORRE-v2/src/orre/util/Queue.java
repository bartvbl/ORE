package orre.util;

import java.util.ArrayList;

public class Queue<DataType> {
	private ArrayList<DataType> queue = new ArrayList<DataType>();
	
	public void enqueue(DataType object)
	{
		this.queue.add(object);
	}
	
	public DataType dequeue()
	{
		if(this.queue.isEmpty())
		{
			return null;
		}
		return this.queue.remove(0);
	}
	
	public boolean isEmpty()
	{
		return this.queue.isEmpty();
	}
}
