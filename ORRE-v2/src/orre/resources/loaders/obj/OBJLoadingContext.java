package orre.resources.loaders.obj;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import orre.geom.vbo.BufferDataFormatType;
import orre.gl.Material;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;

public class OBJLoadingContext {
	private String currentLine;
	private Material currentMaterial;
	private HashMap<String, Material> materials;
	private BufferDataFormatType formatType;
	private ArrayList<PartiallyLoadableModelPart> modelParts;
	public final GeometryBufferGenerator geometryBufferGenerator;
	private File containingDirectory;
	
	public OBJLoadingContext(File containingDirectory)
	{
		this.materials = new HashMap<String, Material>(5);
		this.modelParts = new ArrayList<PartiallyLoadableModelPart>();
		this.geometryBufferGenerator = new GeometryBufferGenerator();
		this.containingDirectory = containingDirectory;
	}

	public void setLine(String line) {
		this.currentLine = line;
	}
	public String getLine() {
		return this.currentLine;
	}
	
	public void addMaterial(Material material) {
		this.materials.put(material.name, material);
	}
	public void setMaterial(String materialName) {
		this.currentMaterial = this.materials.get(materialName);
	}
	public Material getCurrentMaterial() {
		return this.currentMaterial;
	}
	
	public File getContainingDirectory() {
		return this.containingDirectory;
	}
	
	public ArrayList<PartiallyLoadableModelPart> getModelParts() {
		return this.modelParts;
	}
}
