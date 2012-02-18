package orre.resources.loaders.obj;

import java.util.ArrayList;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String name;
	
	private ArrayList<StoredModelPart> childList = new ArrayList<StoredModelPart>();

	public StoredModelPart(ModelPartType partType, String name)
	{
		this.partType = partType;
		this.name = name;
	}
	
	public void addChild(StoredModelPart child)
	{
		this.childList.add(child);
	}
	
	public ArrayList<StoredModelPart> getChilds()
	{
		return this.childList;
	}
}
