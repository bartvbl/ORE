package orre.util;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Node;



public class XMLDocument {
	public Document document;
	
	public XMLDocument(String src)
	{
		try
		{
			this.loadXMLFile(src);
		} catch(DocumentException e){System.out.println("failed to load document: " + src + ", " + e.getMessage());}
	}
	public int getNumChilds(String path)
	{
		@SuppressWarnings("unchecked")
		List<Node> nodes = document.selectNodes(path);
		return nodes.size();
	}
	public String getAttribute(String nodePath, String attributeName)
	{
		
		Node node = document.selectSingleNode(nodePath);
		if(node == null)
		{
			System.out.println("Warning: the attribute " + attributeName + " at " + nodePath + " could not be found.");
			return null;
		}
		return node.valueOf("@" + attributeName);
	}
	public String getTextNode(String path)
	{
		Node node = document.selectSingleNode(path);
		return node.getText();
	}
	public boolean nodeExists(String path)
	{
		if(this.document.selectSingleNode(path) != null)
		{
			return true;
		} else {
			return false;
		}
	}
	
	public static List<Node> getAllChildNodes(Node parentNode)
	{
		return parentNode.selectNodes("*");
	}
	
	public boolean nodeExists(String path, String attribute)
	{
		Node node = this.document.selectSingleNode(path);
		if(node != null)
		{
			if(node.valueOf("@" + attribute) != null)
			{
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public String getNode(String path, String value)
	{
		try
		{
			Node node = this.document.selectSingleNode(path);
			return node.valueOf(value);
		}
		catch(Exception e)
		{
			System.out.println("error reading XML document: " + e.getMessage() + ", path: " + path + ", value: " + value);
		}
		return null;
	}
	public List<Node> getNodesByPath(String path)
	{
		return this.document.selectNodes(path);
	}
	
	private void loadXMLFile(String src) throws DocumentException
	{
		SAXReader xmlReader = new SAXReader();
	    this.document = xmlReader.read(src);
	}
}
