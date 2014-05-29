package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.loaders.obj.StoredModelPart;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableModelPart implements Finalizable {
	private BlueprintMaterial material;
	private final UnpackedGeometryBuffer geometryBuffer;
	
	public final String name;
	private StoredModelPart destinationPart;

	public PartiallyLoadableModelPart(String name, int numVertices, BufferDataFormatType bufferDataFormatType) {
		this.geometryBuffer = new UnpackedGeometryBuffer(bufferDataFormatType, numVertices);
		this.name = name;
	}
		
	public void setMaterial(BlueprintMaterial newMaterial) {
		this.material = newMaterial;
	}
	public void addVertex(DoubleBuffer vertex) {
		this.geometryBuffer.addVertex(vertex); 
	}
	
	public Resource finalizeResource() {
		if(this.destinationPart == null) {
			System.out.println("ERROR: missing part in OBJ file: " + name);
		}
		if(this.material != null) {
			this.material.finalizeResource();
		}
		this.geometryBuffer.finalizeResource();
		this.destinationPart.addBufferCombo(this.material, this.geometryBuffer.convertToGeometryBuffer());
		return null;
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		geometryBuffer.setBufferDataFormat(dataType);
	}

	public void setDestinationPart(StoredModelPart part) {
		this.destinationPart = part;
	}

}
