package orre.util;

import java.util.ArrayList;

public class ConcurrentQueue<DataType> {
	private ArrayList<DataType> queue = new ArrayList<DataType>();
	
	public synchronized void enqueue(DataType object)
	{
		this.queue.add(object);
	}
	
	public synchronized DataType dequeue()
	{
		if(this.queue.isEmpty())
		{
			return null;
		}
		return this.queue.remove(0);
	}
	
	public synchronized boolean isEmpty()
	{
		return this.queue.isEmpty();
	}
}
