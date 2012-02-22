package orre.resources.loaders.obj;

import java.util.ArrayList;

import orre.geom.mesh.ModelPart;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;
import orre.sceneGraph.SceneNode;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String name;
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<GeometryBuffer> geometryBuffers = new ArrayList<GeometryBuffer>();
	
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

	public void addBufferCombo(BlueprintMaterial blueprintMaterial, GeometryBuffer geometryBuffer) {
		this.materials.add(blueprintMaterial);
		this.geometryBuffers.add(geometryBuffer);
	}

	public SceneNode createSceneNode() {
		ModelPart part = new ModelPart();
		for(int i = 0; i < this.materials.size(); i++) {
			part.addMaterialAndGeometryBufferCombo(this.materials.get(i).convertToMaterial(), this.geometryBuffers.get(i));
		}
		for(StoredModelPart child : this.childList) {
			part.addChild(child.createSceneNode());
		}
		return part;
	}
}
