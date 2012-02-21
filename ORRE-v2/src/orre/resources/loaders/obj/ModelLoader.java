package orre.resources.loaders.obj;

import java.util.List;

import org.dom4j.Node;

import orre.resources.FileToLoad;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.partiallyLoadables.PartiallyLoadableModel;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.util.XMLDocument;

public class ModelLoader {
	public static PartiallyLoadableModel loadModel(FileToLoad file, ResourceQueue queue)
	{
		XMLDocument modelXMLDocument = new XMLDocument(file.pathPrefix + file.nodeFile.valueOf("@src"));
		List<StoredModelPart> topLevelParts = ModelPartTreeBuilder.generatePartTree(modelXMLDocument);
		BlueprintModel model = createBlueprintModel(topLevelParts);
		List<PartiallyLoadableModelPart> parts = loadOBJFile(model, modelXMLDocument);
		linkPartsToPartTree(model, parts);
		addPartsToFinalizationQueue(parts, queue);
		return new PartiallyLoadableModel();
	}

	private static BlueprintModel createBlueprintModel(List<StoredModelPart> topLevelParts) {
		BlueprintModel model = new BlueprintModel();
		for(StoredModelPart currentPart : topLevelParts) {
			model.addTopLevelPart(currentPart);
		}
		return model;
	}
	
	private static List<PartiallyLoadableModelPart> loadOBJFile(BlueprintModel model, XMLDocument modelXMLDocument) {
		Node objFileToLoad = modelXMLDocument.getSingleNode("/ORRModel/modelFile");
		List<PartiallyLoadableModelPart> parts = OBJLoader.load(objFileToLoad.valueOf("@src"));
		return parts;
	}
	
	private static void linkPartsToPartTree(BlueprintModel model, List<PartiallyLoadableModelPart> parts) {
		for(PartiallyLoadableModelPart part : parts) {
			
		}
	}
	
	private static void addPartsToFinalizationQueue(List<PartiallyLoadableModelPart> parts, ResourceQueue queue) {
		for(PartiallyLoadableModelPart part : parts) {
			queue.enqueueResourceForFinalization(part);
		}
	}
}
