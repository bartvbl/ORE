package orre.resources.loaders.obj;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import orre.geom.vbo.BufferDataFormatType;
import orre.gl.materials.Material;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class OBJLoadingContext {
	private String currentLine;
	private BlueprintMaterial currentMaterial;
	private HashMap<String, BlueprintMaterial> materials;
	private List<PartiallyLoadableModelPart> modelParts;
	private TemporaryVertexBuffer geometryBufferGenerator;
	private File containingDirectory;
	private PartiallyLoadableModelPart currentModelPart = null;
	
	public OBJLoadingContext(File containingDirectory, OBJStatsContext statsContext)
	{
		this.geometryBufferGenerator = new TemporaryVertexBuffer(statsContext.getTotalVertices(), statsContext.getTotalTexCoords(), statsContext.getTotalNormals(), statsContext.getBufferDataFormat());
		this.materials = new HashMap<String, BlueprintMaterial>(5);
		this.modelParts = statsContext.generateModelParts();
		this.containingDirectory = containingDirectory;
	}

	public void setCurrentLine(String line) {
		this.currentLine = line;
	}
	public String getCurrentLine() {
		return this.currentLine;
	}
	
	public void addMaterial(BlueprintMaterial material) {
		this.materials.put(material.name, material);
	}
	public void setMaterial(String materialName) {
		this.currentMaterial = this.materials.get(materialName);
		if(this.currentModelPart != null) {
			this.currentModelPart.setMaterial(this.currentMaterial);
		}
	}
	public BlueprintMaterial getCurrentMaterial() {
		return this.currentMaterial;
	}
	
	public void setCurrentModelPart(String partName) {
		for(PartiallyLoadableModelPart part : this.modelParts) {
			if(part.name.equals(partName)) {
				this.currentModelPart = part;
				part.setMaterial(this.currentMaterial);
				return;
			}
		}
	}
	public List<PartiallyLoadableModelPart> getModelParts() {
		return this.modelParts;
	}
	
	public File getContainingDirectory() {
		return this.containingDirectory;
	}
	
	public TemporaryVertexBuffer getBuffergenerator() {
		return this.geometryBufferGenerator;
	}

	public void addVertexToCurrentModelPart(float[] vertex) {
		this.currentModelPart.addVertex(vertex);
	}

	public void destroy() {
		this.geometryBufferGenerator = null;
		this.modelParts = null;
		this.materials = null;
		this.currentMaterial = null;
		this.currentModelPart = null;
	}
}