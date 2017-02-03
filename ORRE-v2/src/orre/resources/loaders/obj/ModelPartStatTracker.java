package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.List;

import orre.gl.vao.VBOFormat;
import orre.resources.incompleteResources.IncompleteModelPart;

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
	
	public List<IncompleteModelPart> generateModelParts(VBOFormat dataFormat) {
		this.updateCurrentPartRecord();
		
		ArrayList<IncompleteModelPart> modelParts = new ArrayList<IncompleteModelPart>();
		for(int i = 0; i < this.partNames.size(); i++) {
			modelParts.add(new IncompleteModelPart(this.partNames.get(i), this.verticesInPart.get(i)*OBJConstants.VERTICES_PER_FACE, dataFormat));
		}
		return modelParts;
	}
}
