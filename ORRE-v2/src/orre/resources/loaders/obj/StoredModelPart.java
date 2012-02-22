package orre.resources.loaders.obj;

import java.util.ArrayList;

import orre.geom.vbo.GeometryBuffer;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;

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

	public void addBufferCombo(BlueprintMaterial blueprintMaterial, GeometryBuffer convertToGeometryBuffer) {
		// TODO Auto-generated method stub
		
	}
}
