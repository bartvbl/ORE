package orre.resources.loaders.obj;

import java.util.List;

import orre.gl.vao.VBOFormat;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class OBJStatsContext {
	private int totalVertices = 0;
	private int totalTexCoords = 0;
	private int totalNormals = 0;
	
	private ModelPartStatTracker modelPartTracker;
	
	private boolean bufferDataTypeHasBeenSet = false;
	private VBOFormat dataFormat = VBOFormat.VERTICES_TEXTURES_NORMALS;
	
	public OBJStatsContext() {
		this.modelPartTracker = new ModelPartStatTracker();
	}
	
	public void setModelPart(String name) {
		this.modelPartTracker.setModelPart(name);
	}

	public void registerFace() {
		this.modelPartTracker.registerFace();
	}
	public void registerVertex() {
		this.totalVertices++;
	}
	public void registerTexCoord() {
		this.totalTexCoords++;
	}
	public void registerNormal() {
		this.totalNormals++;
	}

	public boolean bufferDataTypeHasBeenSet() {
		return this.bufferDataTypeHasBeenSet;
	}
	public void setBufferDataFormat(VBOFormat dataFormat) {
		this.dataFormat = dataFormat;
		this.bufferDataTypeHasBeenSet = true;
	}
	public VBOFormat getBufferDataFormat() {
		return this.dataFormat;
	}

	public int getTotalVertices() {
		return this.totalVertices;
	}
	public int getTotalTexCoords() {
		return this.totalTexCoords;
	}
	public int getTotalNormals() {
		return this.totalNormals;
	}

	public List<PartiallyLoadableModelPart> generateModelParts() {
		return this.modelPartTracker.generateModelParts(this.dataFormat);
	}
}
