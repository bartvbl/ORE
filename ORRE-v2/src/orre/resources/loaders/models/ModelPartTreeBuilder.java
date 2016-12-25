package orre.resources.loaders.models;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;
import orre.resources.data.OBJBlueprintModel;
import orre.resources.loaders.obj.ModelPartType;
import orre.resources.loaders.obj.StoredModelPart;

public class ModelPartTreeBuilder {
	private static final String PHYSICAL_PART = "part";
	private static final String VIRTUAL_PART = "bone";
	private static final String EMPTY_PART = "null";

	public static void generatePartTree(OBJBlueprintModel model, Element rootElement) {
		Element partStructureElement = rootElement.getFirstChildElement("partStructure");
		Elements partElements = partStructureElement.getChildElements();
		for(int i = 0; i < partElements.size(); i++)
		{
			Element currentElement = partElements.get(i);
			StoredModelPart modelPart = parseTreeNode(currentElement, model);
			model.addTopLevelPart(modelPart);
		}
	}

	private static StoredModelPart parseTreeNode(Element nodeElement, OBJBlueprintModel model) {
		ModelPartType partType = getPartType(nodeElement);
		String partName = nodeElement.getAttributeValue("name");
		String modelFilePartName = nodeElement.getAttributeValue("nameInModelFile");
		StoredModelPart newPart = new StoredModelPart(partType, modelFilePartName, partName);
		model.registerPart(newPart);
		if(partType == ModelPartType.PHYSICAL) {			
			parsePivotLocation(newPart, nodeElement);
			parseScale(newPart, nodeElement);
		}
		if(partType == ModelPartType.BONE) {
			parsePivotLocation(newPart, nodeElement);
		}
		parseChildNodes(newPart, nodeElement, model);
		return newPart;
	}

	private static void parseScale(StoredModelPart newPart, Element nodeElement) {
		float scaleX = 1;
		float scaleY = 1;
		float scaleZ = 1;

		if(hasAttribute(nodeElement, "scaleX")) {
			scaleX = Float.parseFloat(nodeElement.getAttributeValue("scaleX"));
		}
		if(hasAttribute(nodeElement, "scaleY")) {
			scaleY = Float.parseFloat(nodeElement.getAttributeValue("scaleY"));
		}
		if(hasAttribute(nodeElement, "scaleZ")) {
			scaleZ = Float.parseFloat(nodeElement.getAttributeValue("scaleZ"));
		}
		
		newPart.setScale(scaleX, scaleY, scaleZ);
	}

	private static boolean hasAttribute(Element nodeElement, String attributeName) {
		return nodeElement.getAttribute(attributeName) != null;
	}
	
	private static void parsePivotLocation(StoredModelPart newPart, Element nodeElement) {
		String pivotXAttribute = nodeElement.getAttributeValue("pivotX");
		String pivotYAttribute = nodeElement.getAttributeValue("pivotY");
		String pivotZAttribute = nodeElement.getAttributeValue("pivotZ");

		double pivotX = Double.parseDouble(pivotXAttribute);
		double pivotY = Double.parseDouble(pivotYAttribute);
		double pivotZ = Double.parseDouble(pivotZAttribute);
		
		newPart.setPivotLocation(pivotX, pivotY, pivotZ);
	}

	private static ModelPartType getPartType(Element modelNode) {
		String partType = modelNode.getAttributeValue("type");
		partType = partType == null ? EMPTY_PART : partType;
		if(partType.equals(PHYSICAL_PART)){
			return ModelPartType.PHYSICAL;
		} else if(partType.equals(VIRTUAL_PART)){
			return ModelPartType.BONE;
		} else if(partType.equals(EMPTY_PART)){
			return ModelPartType.NULL;
		}
		return ModelPartType.PHYSICAL;
	}
	
	private static void parseChildNodes(StoredModelPart newPart, Element nodeElement, OBJBlueprintModel model) {
		Elements childElements = nodeElement.getChildElements();
		for(int i = 0; i < childElements.size(); i++) {
			newPart.addChild(parseTreeNode(childElements.get(i), model));
		}
	}
}
