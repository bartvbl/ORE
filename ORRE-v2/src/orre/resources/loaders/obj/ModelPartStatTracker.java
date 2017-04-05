package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import orre.gl.vao.VBOFormat;
import orre.resources.incompleteResources.IncompleteGeometryBuffer;
import orre.resources.incompleteResources.IncompleteGeometryMaterialCombo;
import orre.resources.incompleteResources.IncompleteModelPart;

public class ModelPartStatTracker {
	private int verticesInCurrentPart = 0;
	private String currentPartName = "";
	
	private ArrayList<String> partNames = new ArrayList<String>();
	private ArrayList<Integer> verticesInPart = new ArrayList<Integer>();
	
	public void setModelPart(String name) {
		if(this.verticesInPart.size() != 0) {			
			int index = partNames.indexOf(this.currentPartName);
			this.verticesInPart.set(index, this.verticesInCurrentPart);
		}
		
		int index = partNames.indexOf(name);
		
		if(index != -1) {
			String currentPartName = this.partNames.get(index);
			this.currentPartName = currentPartName;
			this.verticesInCurrentPart = this.verticesInPart.get(index);			
		} else {
			// Create a new part
			this.partNames.add(name);
			this.verticesInCurrentPart = 0;
			this.verticesInPart.add(0);
			this.currentPartName = name;
		}
	}
	
	public void registerFace() {
		this.verticesInCurrentPart++;
	}
	
	public HashMap<String, IncompleteGeometryMaterialCombo> generateModelParts(VBOFormat dataFormat) {
		int index = partNames.indexOf(this.currentPartName);
		this.verticesInPart.set(index, this.verticesInCurrentPart);
		
		HashMap<String, IncompleteGeometryMaterialCombo> partMap = new HashMap<String, IncompleteGeometryMaterialCombo>();
		
		for(int i = 0; i < this.partNames.size(); i++) {
			IncompleteGeometryBuffer buffer = new IncompleteGeometryBuffer(dataFormat, this.verticesInPart.get(i));
			
			partMap.put(this.partNames.get(i), new IncompleteGeometryMaterialCombo(buffer, material));
			
		}
		return partMap;
	}
}
