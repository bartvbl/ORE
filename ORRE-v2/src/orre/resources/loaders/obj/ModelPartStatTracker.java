package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.List;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class ModelPartStatTracker {
	private int verticesInCurrentPart = 0;
	private String currentPartName = "";
	
	private ArrayList<String> partNames = new ArrayList<String>();
	private ArrayList<Integer> verticesInPart = new ArrayList<Integer>();
	
	public void setModelPart(String name) {
		if(this.verticesInPart.size() != 0) {			
			this.updateCurrentPartRecord();
		}
		int index = this.getPartIndexByName(name);
		if(index != -1) {
			String currentPartName = this.partNames.get(index);
			this.currentPartName = currentPartName;
			this.verticesInCurrentPart = this.verticesInPart.get(index);			
		} else {
			this.createNewPart(name);
			this.currentPartName = name;
		}
	}
	
	private void updateCurrentPartRecord() {
		int index = this.getPartIndexByName(this.currentPartName);
		this.verticesInPart.set(index, this.verticesInCurrentPart);
	}
	
	private int getPartIndexByName(String name) {
		for(int i = 0; i < partNames.size(); i++) {
			String currentPartName = this.partNames.get(i);
			if(currentPartName.equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private void createNewPart(String name) {
		this.partNames.add(name);
		this.verticesInCurrentPart = 0;
		this.verticesInPart.add(0);
	}

	public void registerFace() {
		this.verticesInCurrentPart++;
	}
	
	public List<PartiallyLoadableModelPart> generateModelParts(BufferDataFormatType dataFormat) {
		this.updateCurrentPartRecord();
		
		ArrayList<PartiallyLoadableModelPart> modelParts = new ArrayList<PartiallyLoadableModelPart>();
		for(int i = 0; i < this.partNames.size(); i++) {
			modelParts.add(new PartiallyLoadableModelPart(this.partNames.get(i), this.verticesInPart.get(i)*OBJConstants.VERTICES_PER_FACE, dataFormat));
		}
		return modelParts;
	}
}
