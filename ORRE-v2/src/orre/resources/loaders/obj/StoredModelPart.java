package orre.resources.loaders.obj;

import java.util.ArrayList;

import orre.geom.mesh.ModelPart;
import orre.gl.vao.GeometryNode;
import orre.resources.incompleteResources.BlueprintMaterial;

public class StoredModelPart {
	
	public final ModelPartType partType;
	public final String nameInModel;
	public final String name;
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<GeometryNode> geometryBuffers = new ArrayList<GeometryNode>();
	private double pivotX, pivotY, pivotZ;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;
	
	private ArrayList<StoredModelPart> childList = new ArrayList<StoredModelPart>();

	public StoredModelPart(ModelPartType partType, String nameInModel, String name)
	{
		this.partType = partType;
		this.nameInModel = nameInModel;
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
		ModelPart part = new ModelPart(name);
		part.setPivotLocation(pivotX, pivotY, pivotZ);
		part.setScale(scaleX, scaleY, scaleZ);
		for(int i = 0; i < this.materials.size(); i++) {
			part.addMaterialAndGeometryBufferCombo(this.materials.get(i).convertToMaterial(), this.geometryBuffers.get(i), this.nameInModel);
		}
		return part;
	}

	public void setScale(float scaleX, float scaleY, float scaleZ) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
}
