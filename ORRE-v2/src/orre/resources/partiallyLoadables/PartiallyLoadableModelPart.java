package orre.resources.partiallyLoadables;

import java.util.ArrayList;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.Finalizable;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableModelPart extends Finalizable {
	private ArrayList<BlueprintMaterial> materials = new ArrayList<BlueprintMaterial>();
	private ArrayList<UnpackedGeometryBuffer> geometryBuffers = new ArrayList<UnpackedGeometryBuffer>();
	
	public final String name;
	private BufferDataFormatType bufferDataFormat;

	public PartiallyLoadableModelPart(String name, BufferDataFormatType bufferDataFormatType) {
		this.bufferDataFormat = bufferDataFormatType;
		this.name = name;
	}
		
	public void addMaterial(BlueprintMaterial currentMaterial) {
		this.materials.add(currentMaterial);
		this.geometryBuffers.add(new UnpackedGeometryBuffer(this.bufferDataFormat));
	}
	public void addVertex(float[] vertex) {
		UnpackedGeometryBuffer buffer = this.geometryBuffers.get(this.geometryBuffers.size()-1);
		buffer.addVertex(vertex); 
	}
	
	public void finalizeResource() {
		for(int i = this.geometryBuffers.size()-1; i >= 0 ; i--) {
			if(geometryBuffers.get(i).isEmpty()){
				this.materials.remove(i);
				this.geometryBuffers.remove(i);
			} else {
				this.materials.get(i).finalizeResource();
				this.geometryBuffers.get(i).finalizeResource();
			}
		}
		//remember to check if the buffer is empty before finalizing it. It may not have been filled with anything when parsing the OBJ file.
	}

	public SceneNode createSceneNode() {return null;}
	public void addToCache() {}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		this.bufferDataFormat = dataType;
	}

}
