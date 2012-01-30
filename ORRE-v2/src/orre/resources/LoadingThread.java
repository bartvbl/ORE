package orre.resources;

import java.util.List;

import org.dom4j.Node;

public class LoadingThread extends Thread{
	private ResourceLoader loaderMain;
	private List<Node> nodesToLoad;
	private ResourceCache cache;

	public LoadingThread(ResourceLoader loaderMain, List<Node> nodesToLoad, ResourceCache cache)
	{
		this.loaderMain = loaderMain;
		this.nodesToLoad = nodesToLoad;
		this.cache = cache;
	}
	
	public void run()
	{
		for(Node node : this.nodesToLoad)
		{
			if(node.getName().equals(""))
			{
				
			}
		}
	}
}
