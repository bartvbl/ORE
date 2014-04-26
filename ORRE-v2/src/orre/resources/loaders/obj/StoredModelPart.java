package orre.resources.loaders.obj;

import java.util.ArrayList;

import orre.geom.mesh.ModelPart;
import orre.geom.vbo.GeometryNode;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;
import orre.sceneGraph.SceneNode;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String name;
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<GeometryNode> geometryBuffers = new ArrayList<GeometryNode>();
	private double pivotX, pivotY, pivotZ;
	
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
	
	public ArrayList<StoredModelPart> getChildren()
	{
		return this.childList;
	}
	
	public void setPivotLocation(double x, double y, double z) {
		this.pivotX = x;
		this.pivotY = y;
		this.pivotZ = z;
	}

	public void addBufferCombo(BlueprintMaterial blueprintMaterial, GeometryNode geometryBuffer) {
		this.materials.add(blueprintMaterial);
		this.geometryBuffers.add(geometryBuffer);
	}

	public ModelPart createSceneNode() {
		ModelPart part = new ModelPart();
		for(int i = 0; i < this.materials.size(); i++) {
			part.setPivotLocation(pivotX, pivotY, pivotZ);
			part.addMaterialAndGeometryBufferCombo(this.materials.get(i).convertToMaterial(), this.geometryBuffers.get(i), this.name);
		}
		return part;
	}
}
