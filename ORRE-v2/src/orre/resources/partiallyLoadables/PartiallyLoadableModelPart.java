package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;

import orre.geom.vbo.VBOFormat;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.loaders.obj.StoredModelPart;
import orre.util.Logger;
import orre.util.Logger.LogType;

public class PartiallyLoadableModelPart implements Finalizable {
	private BlueprintMaterial material;
	private final UnpackedGeometryBuffer geometryBuffer;
	
	public final String name;
	private StoredModelPart destinationPart;

	public PartiallyLoadableModelPart(String name, int numVertices, VBOFormat bufferDataFormatType) {
		this.geometryBuffer = new UnpackedGeometryBuffer(bufferDataFormatType, numVertices);
		this.name = name;
	}
		
	public void setMaterial(BlueprintMaterial newMaterial) {
		this.material = newMaterial;
	}
	public void addVertex(DoubleBuffer vertex) {
		this.geometryBuffer.addVertex(vertex); 
	}
	
	@Override
	public Resource finalizeResource() {
		if(this.destinationPart == null) {
			Logger.log("missing part in model: " + name, LogType.ERROR);
			return null;
		}
		if(this.material != null) {
			this.material.finalizeResource();
		}
		this.geometryBuffer.finalizeResource();
		this.destinationPart.addBufferCombo(this.material, this.geometryBuffer.convertToGeometryBuffer());
		return null;
	}

	public void setBufferDataFormat(VBOFormat dataType) {
		geometryBuffer.setBufferDataFormat(dataType);
	}

	public void setDestinationPart(StoredModelPart part) {
		this.destinationPart = part;
	}

}
