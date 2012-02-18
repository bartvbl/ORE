package orre.resources.loaders.obj;

import java.util.List;

import org.dom4j.Node;

import orre.resources.FileToLoad;
import orre.resources.data.BlueprintModel;
import orre.resources.partiallyLoadables.PartiallyLoadableModel;
import orre.util.XMLDocument;

public class ModelLoader {
	public static PartiallyLoadableModel loadModel(FileToLoad file)
	{
		System.out.println("loading model: " + file.pathPrefix + file.nodeFile.valueOf("@src"));
		XMLDocument modelXMLDocument = new XMLDocument(file.pathPrefix + file.nodeFile.valueOf("@src"));
		List<StoredModelPart> topLevelParts = ModelPartTreeBuilder.generatePartTree(modelXMLDocument);
		BlueprintModel model = createBlueprintModel(topLevelParts);
		loadOBJFile(model, modelXMLDocument);
		return null;
	}

	private static BlueprintModel createBlueprintModel(List<StoredModelPart> topLevelParts) {
		BlueprintModel model = new BlueprintModel();
		for(StoredModelPart currentPart : topLevelParts) {
			model.addTopLevelPart(currentPart);
		}
		return model;
	}
	
	private static void loadOBJFile(BlueprintModel model, XMLDocument modelXMLDocument) {
		Node objFileToLoad = modelXMLDocument.getSingleNode("/ORRModel/modelFile");
		OBJLoader.load(objFileToLoad.valueOf("@src"));
	}
}
