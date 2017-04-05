package orre.resources.loaders;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nu.xom.Document;
import nu.xom.Element;
import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.ResourceQueue;
import orre.resources.incompleteResources.IncompleteBlueprintModel;
import orre.resources.incompleteResources.IncompleteModelPart;
import orre.resources.loaders.obj.ModelPartTreeBuilder;
import orre.util.Logger;
import orre.util.XMLLoader;
import orre.util.Logger.LogType;

public class ModelLoader implements ResourceTypeLoader {
	@Override
	public IncompleteResourceObject<?> readResource(Resource source) throws Exception {
		return loadModel(source);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.model;
	}

	public static IncompleteBlueprintModel loadModel(Resource file) throws Exception
	{
		Document modelXMLDocument = XMLLoader.readXML(file.fileLocation);
		Element rootElement = modelXMLDocument.getRootElement();
		IncompleteBlueprintModel model = new IncompleteBlueprintModel(file.name);
		// Maps parts defined in the model tree structure to those specified in the OBJ file.
		HashMap<String, String> partNameLinkMap = new HashMap<String, String>();
		ModelPartTreeBuilder.populatePartTree(model, rootElement, partNameLinkMap);
		List<IncompleteModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts, partNameLinkMap);
		return model;
	}
	
	private static List<IncompleteModelPart> loadOBJFile(IncompleteBlueprintModel model, Element rootElement) throws Exception {
		
		Element modelFileElement = rootElement.getFirstChildElement("modelFile");
		List<IncompleteModelPart> parts = OBJLoader.load(modelFileElement.getAttributeValue("src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(IncompleteBlueprintModel model, List<IncompleteModelPart> parts, HashMap<String, String> partNameLinkMap) {
		Set<String> modelPartys = model.modelParts.keySet();
		for(IncompleteModelPart part : parts) {
			if(!partNameLinkMap.containsKey(part.name)) {
				throw new RuntimeException("The part from the loaded OBJ file named \"" + part.name = "\" ");
			}
			String modelPartName = partNameLinkMap.get(part)
		}
	}
	
	@Override
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		IncompleteBlueprintModel model = (IncompleteBlueprintModel) object;
		for(IncompleteModelPart part : model.modelParts) {
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
	}
}
