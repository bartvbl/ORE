package orre.resources;

import org.dom4j.Node;

public class FileToLoad {
	public final Node nodeFile;
	public final ResourceFile fileType;
	public String pathPrefix = "";

	public FileToLoad(Node nodeFile, ResourceFile fileType)
	{
		this.nodeFile = nodeFile;
		this.fileType = fileType;
	}
}
