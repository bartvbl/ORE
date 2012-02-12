package orre.resources.loaders;

import org.dom4j.Node;

import orre.resources.FileToLoad;
import orre.resources.partiallyLoadables.PartiallyLoadableModel;
import orre.util.XMLDocument;

public class ModelLoader {
	public static PartiallyLoadableModel loadModel(FileToLoad file)
	{
		XMLDocument modelFile = new XMLDocument(file.pathPrefix + file.nodeFile.valueOf("@src"));
		generatePartTree(modelFile);
		//generate tree
		//optimise tree
		//load OBJ('s)
		//link OBJ parts to tree nodes
		return null;
	}
	
	private static ModelPart generatePartTree(XMLDocument modelFile)
	{
		//modelFile.get
		return null;
	}
}
