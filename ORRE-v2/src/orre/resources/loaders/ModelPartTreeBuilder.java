package orre.resources.loaders;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Node;

import orre.util.Logger;
import orre.util.XMLDocument;

public class ModelPartTreeBuilder {
	private static final String PHYSICAL_PART = "part";
	private static final String SHADER_PART = "shaderPart";

	public static List<StoredModelPart> generatePartTree(XMLDocument modelFile) {
		List<Node> parts = XMLDocument.getAllChildNodes(modelFile.getSingleNode("/ORRModel/partStructure"));
		ArrayList<StoredModelPart> partList = new ArrayList<StoredModelPart>();
		for(Node modelNode : parts)
		{
			partList.add(parseTreeNode(modelFile, modelNode));
		}
		return partList;
	}

	private static StoredModelPart parseTreeNode(XMLDocument modelFile, Node modelNode) {
		ModelPartType partType = getPartType(modelNode);
		String partName = modelNode.valueOf("@nameInModelFile");
		StoredModelPart newPart = new StoredModelPart(partType, partName);
		parseChildNodes(modelFile, newPart, modelNode);
		return newPart;
	}

	private static ModelPartType getPartType(Node modelNode) {
		String partName = modelNode.getName();
		if(partName.equals(PHYSICAL_PART)){
			return ModelPartType.PHYSICAL;
		} else if(partName.equals(SHADER_PART)){
			return ModelPartType.VIRTUAL;
		}
		Logger.log("Invalid part type detected in model file. Default assumed. (" + modelNode.getPath() + ")", Logger.LogType.WARNING);
		return ModelPartType.PHYSICAL;
	}
	
	private static void parseChildNodes(XMLDocument modelFile, StoredModelPart newPart, Node modelNode) {
		List<Node> childList = modelNode.selectNodes("*");
		for(Node node : childList) {
			newPart.addChild(parseTreeNode(modelFile, node));
		}
	}
}
