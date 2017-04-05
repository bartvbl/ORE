package orre.resources.loaders.obj;

import java.util.HashMap;

import orre.gl.vao.VBOFormat;
import orre.resources.incompleteResources.IncompleteGeometryMaterialCombo;

public class OBJStatsContext {
	public final ModelPartStatTracker modelParts;
	
	private boolean bufferDataTypeHasBeenSet = false;
	private VBOFormat dataFormat = VBOFormat.VERTICES_TEXTURES_NORMALS;
	
	public OBJStatsContext() {
		this.modelParts = new ModelPartStatTracker();
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

	public HashMap<String, IncompleteGeometryMaterialCombo> generateModelParts() {
		return this.modelParts.generateModelParts(this.dataFormat);
	}
}
