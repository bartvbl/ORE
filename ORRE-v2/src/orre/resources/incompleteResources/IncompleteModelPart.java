package orre.resources.incompleteResources;

import java.nio.FloatBuffer;

import orre.gl.vao.VBOFormat;
import orre.resources.IncompleteResourceObject;
import orre.resources.loaders.obj.StoredModelPart;

public class IncompleteModelPart implements IncompleteResourceObject<IncompleteModelPart> {
	private BlueprintMaterial material;
	private final UnpackedGeometryBuffer geometryBuffer;
	
	public final String name;
	private StoredModelPart destinationPart;

	public IncompleteModelPart(String name, int numVertices, VBOFormat bufferDataFormatType) {
		this.geometryBuffer = new UnpackedGeometryBuffer(bufferDataFormatType, numVertices);
		this.name = name;
	}
		
	public void setMaterial(BlueprintMaterial newMaterial) {
		this.material = newMaterial;
	}
	public void addVertex(FloatBuffer vertex) {
		this.geometryBuffer.addVertex(vertex); 
	}
	
	public void setBufferDataFormat(VBOFormat dataType) {
		geometryBuffer.setBufferDataFormat(dataType);
	}

	public void setDestinationPart(StoredModelPart part) {
		this.destinationPart = part;
	}
	
	public String toString() {
		return "ModelPart " + name;
	}

}
