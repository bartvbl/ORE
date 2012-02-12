package orre.resources.loaders;

import orre.resources.FileToLoad;
import orre.resources.partiallyLoadables.PartiallyLoadableModel;
import orre.util.XMLDocument;

public class ModelLoader {
	public static PartiallyLoadableModel loadModel(FileToLoad file)
	{
		XMLDocument modelFile = new XMLDocument(file.pathPrefix + file.nodeFile.valueOf("@src"));
		//generate tree
		//optimise tree
		//load OBJ('s)
		//link OBJ parts to tree nodes
		return null;
	}
}
