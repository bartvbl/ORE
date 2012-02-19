package orre.resources.data;

import java.util.ArrayList;

import orre.resources.loaders.obj.StoredModelPart;

public class BlueprintModel {
	private ArrayList<StoredModelPart> topLevelNodeList = new ArrayList<StoredModelPart>();
	
	public BlueprintModel()
	{
		
	}

	public void addTopLevelPart(StoredModelPart currentPart) {
		this.topLevelNodeList.add(currentPart);
	}
}
