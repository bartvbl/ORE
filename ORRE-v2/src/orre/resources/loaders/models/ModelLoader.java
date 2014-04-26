package orre.resources.loaders.models;

import java.util.List;

import nu.xom.Document;
import nu.xom.Element;
import orre.resources.FileToLoad;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.obj.OBJLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.XMLLoader;

public class ModelLoader {
	public static BlueprintModel loadModel(FileToLoad file, ResourceQueue queue) throws Exception
	{
		Document modelXMLDocument = XMLLoader.readXML(file.getPath());
		Element rootElement = modelXMLDocument.getRootElement();
		BlueprintModel model = new BlueprintModel(file.name);
		ModelPartTreeBuilder.generatePartTree(model, rootElement);
		List<PartiallyLoadableModelPart> parts = loadOBJFile(model, rootElement);
		linkPartsToPartTree(model, parts);
		addPartsToFinalizationQueue(parts, queue);
		return model;
	}
	
	private static List<PartiallyLoadableModelPart> loadOBJFile(BlueprintModel model, Element rootElement) throws Exception {
		
		Element modelFileElement = rootElement.getFirstChildElement("modelFile");
		List<PartiallyLoadableModelPart> parts = OBJLoader.load(modelFileElement.getAttributeValue("src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(BlueprintModel model, List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			model.linkGeometryPartToModelPart(part.name, part);
		}
	}
	
	private static void addPartsToFinalizationQueue(List<PartiallyLoadableModelPart> parts, ResourceQueue queue) {
		for(PartiallyLoadableModelPart part : parts) {
			queue.enqueueResourceForFinalization(part);
			
		}
	}
}
