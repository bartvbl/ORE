package orre.resources.incompleteResources;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import orre.geom.mesh.ModelPart;
import orre.gl.vao.GeometryNode;
import orre.gl.vao.VBOFormat;
import orre.resources.IncompleteResourceObject;
import orre.resources.loaders.obj.ModelPartType;

public class IncompleteModelPart implements IncompleteResourceObject<IncompleteModelPart> {
	private final ArrayList<IncompleteModelPart> childList = new ArrayList<IncompleteModelPart>();
	private ArrayList<IncompleteBlueprintMaterial> materials = new ArrayList<IncompleteBlueprintMaterial>();
	private ArrayList<IncompleteGeometryBuffer> geometryBuffers = new ArrayList<IncompleteGeometryBuffer>();
	
	public final String name;
	public final ModelPartType partType;		
	
	private float pivotX;
	private float pivotY;
	private float pivotZ;
	
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	public IncompleteModelPart(String name, ModelPartType type) {
		this.name = name;
		this.partType = type;
	}
		
	public String toString() {
		return "Incomplete model part " + name;
	}
	
	public void addChild(IncompleteModelPart child)
	{
		this.childList.add(child);
	}
	
	public ArrayList<IncompleteModelPart> getChildren()
	{
		return this.childList;
	}
	
	public void setPivotLocation(float x, float y, float z) {
		this.pivotX = x;
		this.pivotY = y;
		this.pivotZ = z;
	}
	
	public void setScale(float scaleX, float scaleY, float scaleZ) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}

	public void addBufferCombo(IncompleteBlueprintMaterial blueprintMaterial, IncompleteGeometryBuffer geometryBuffer) {
		this.materials.add(blueprintMaterial);
		this.geometryBuffers.add(geometryBuffer);
	}

	public ModelPart complete() {
		ModelPart part = new ModelPart(name);
		part.setPivotLocation(pivotX, pivotY, pivotZ);
		part.setScale(scaleX, scaleY, scaleZ);
		for(int i = 0; i < this.materials.size(); i++) {
			part.addMaterialAndGeometryBufferCombo(this.materials.get(i).convertToMaterial(), this.geometryBuffers.get(i), this.nameInModel);
		}
		return part;
	}

}
