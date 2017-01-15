package orre.resources.loaders;

import java.util.List;

import nu.xom.Document;
import nu.xom.Element;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.ResourceQueue;
import orre.resources.data.OBJBlueprintModel;
import orre.resources.loaders.models.ModelPartTreeBuilder;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.XMLLoader;

public class ModelLoader implements ResourceTypeLoader {
	@Override
	public Finalizable loadResource(Resource source, ResourceQueue queue) throws Exception {
		return loadModel(source, queue);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.model;
	}

	public static OBJBlueprintModel loadModel(Resource file, ResourceQueue queue) throws Exception
	{
		Document modelXMLDocument = XMLLoader.readXML(file.fileLocation);
		Element rootElement = modelXMLDocument.getRootElement();
		OBJBlueprintModel model = new OBJBlueprintModel(file.name);
		ModelPartTreeBuilder.generatePartTree(model, rootElement);
		List<PartiallyLoadableModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts);
		addPartsToFinalizationQueue(parts, queue);
		return model;
	}
	
	private static List<PartiallyLoadableModelPart> loadOBJFile(OBJBlueprintModel model, Element rootElement) throws Exception {
		
		Element modelFileElement = rootElement.getFirstChildElement("modelFile");
		List<PartiallyLoadableModelPart> parts = OBJLoader.load(modelFileElement.getAttributeValue("src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(OBJBlueprintModel model, List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			model.linkGeometryPartToModelPart(part);
		}
	}
	
	private static void addPartsToFinalizationQueue(List<PartiallyLoadableModelPart> parts, ResourceQueue queue) {
		for(PartiallyLoadableModelPart part : parts) {
			queue.enqueueResourceForFinalization(part);
		}
	}
}
