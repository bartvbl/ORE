package orre.resources.loaders.obj;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;

import orre.gl.vao.VertexBuffer;
import orre.resources.incompleteResources.IncompleteBlueprintMaterial;
import orre.resources.incompleteResources.IncompleteGeometryMaterialCombo;
import orre.resources.incompleteResources.IncompleteModelPart;

public class OBJLoadingContext {
	private String currentLine;
	private IncompleteBlueprintMaterial currentMaterial;
	
	private final HashMap<String, IncompleteBlueprintMaterial> materials;
	private final HashMap<String, String> partMaterialNameMap;
	private final HashMap<String, IncompleteGeometryMaterialCombo> modelParts;
	
	private VertexBuffer temporaryVertesBuffer;
	private File containingDirectory;
	private IncompleteModelPart currentModelPart = null;
	
	public OBJLoadingContext(File containingDirectory, OBJStatsContext statsContext)
	{
		this.temporaryVertesBuffer = new VertexBuffer(statsContext.getTotalVertices(), statsContext.getTotalTexCoords(), statsContext.getTotalNormals(), statsContext.getBufferDataFormat());
		this.materials = new HashMap<String, IncompleteBlueprintMaterial>(5);
		this.modelParts = statsContext.generateModelParts();
		this.partMaterialNameMap = new HashMap<String, String>();
		this.containingDirectory = containingDirectory;
	}

	public void setCurrentLine(String line) {
		this.currentLine = line;
	}
	public String getCurrentLine() {
		return this.currentLine;
	}
	
	public void addMaterial(IncompleteBlueprintMaterial material) {
		this.materials.put(material.name, material);
	}
	public void setMaterial(String materialName) {
		this.currentMaterial = this.materials.get(materialName);
		partMaterialNameMap.put(currentModelPart.name, materialName);
	}
	public IncompleteBlueprintMaterial getCurrentMaterial() {
		return this.currentMaterial;
	}
	
	public void setCurrentModelPart(String partName) {
		for(IncompleteModelPart part : this.modelParts) {
			if(part.name.equals(partName)) {
				this.currentModelPart = part;
				part.setMaterial(this.currentMaterial);
				return;
			}
		}
	}
	
	public File getContainingDirectory() {
		return this.containingDirectory;
	}
	
	public VertexBuffer getBuffergenerator() {
		return this.temporaryVertesBuffer;
	}

	public void addVertexToCurrentModelPart(FloatBuffer vertex) {
		this.currentModelPart.addVertex(vertex);
	}

	public void destroy() {
		this.modelParts = null;
		this.materials = null;
		this.currentMaterial = null;
		this.currentModelPart = null;
	}
}