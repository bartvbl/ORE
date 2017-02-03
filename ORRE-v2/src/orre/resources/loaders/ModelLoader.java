package orre.resources.loaders;

import java.util.List;

import nu.xom.Document;
import nu.xom.Element;
import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.ResourceQueue;
import orre.resources.data.OBJBlueprintModel;
import orre.resources.incompleteResources.IncompleteModelPart;
import orre.resources.loaders.models.ModelPartTreeBuilder;
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

	public static OBJBlueprintModel loadModel(Resource file) throws Exception
	{
		Document modelXMLDocument = XMLLoader.readXML(file.fileLocation);
		Element rootElement = modelXMLDocument.getRootElement();
		OBJBlueprintModel model = new OBJBlueprintModel(file.name);
		ModelPartTreeBuilder.generatePartTree(model, rootElement);
		List<IncompleteModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts);
		return model;
	}
	
	private static List<IncompleteModelPart> loadOBJFile(OBJBlueprintModel model, Element rootElement) throws Exception {
		
		Element modelFileElement = rootElement.getFirstChildElement("modelFile");
		List<IncompleteModelPart> parts = OBJLoader.load(modelFileElement.getAttributeValue("src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(OBJBlueprintModel model, List<IncompleteModelPart> parts) {
		for(IncompleteModelPart part : parts) {
			model.linkGeometryPartToModelPart(part);
		}
	}
	
	@Override
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		OBJBlueprintModel model = (OBJBlueprintModel) object;
		for(IncompleteModelPart part : model.parts) {
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
