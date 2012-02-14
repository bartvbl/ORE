package openrr.test;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;

import orre.util.XMLDocument;



public class MenuDefinitionFileLoader {
	public static ArrayList<Menu> getMenuNodes(XMLDocument doc, int[] screenSize)
	{
		List<Node> menuNodes = doc.document.selectNodes("/ORRDefaultGUI/menus/*");
		ArrayList<Menu> menus = new ArrayList<Menu>();
		System.out.println("found " + menuNodes.size() + " menus.");
		for(Node menuNode : menuNodes)
		{
			Menu currentMenu = parseMenuNode(menuNode, screenSize);
			menus.add(currentMenu);
		}
		return menus;
	}

	private static Menu parseMenuNode(Node menuNode, int[] screenSize) {
		Menu newMenu = new Menu(Integer.parseInt(menuNode.valueOf("@x")), Integer.parseInt(menuNode.valueOf("@y")),
				screenSize, menuNode.valueOf("@bg"), menuNode.valueOf("@align"));
		getAndParseContainers(menuNode, newMenu, screenSize);
		setInitialMenuState(menuNode, newMenu);
		return newMenu;
	}
	
	private static void setInitialMenuState(Node menuNode, Menu newMenu) {
		if (!menuNode.valueOf("@startState").equals("none")) {
			int state;
			if (menuNode.valueOf("@startState").equals("open")) {
				state = Menu.OPEN;
			} else {
				state = Menu.CLOSED;
			}
			newMenu.setAnimationVals(state,Integer.parseInt(menuNode.valueOf("@animFrames")),Integer.parseInt(menuNode.valueOf("@animDist")));
		}
	}

	private static void getAndParseContainers(Node menuNode, Menu newMenu, int[] screenSize) {
		List<Node> containerNodes = getAllChildNodes(menuNode);
		System.out.println("\tfound " + containerNodes.size() + " container nodes.");
		for(Node containerNode : containerNodes)
		{
			System.out.println("\t\tparsing container node");
			Container newContainer = parseContainerNode(containerNode, screenSize);
			newMenu.addContainer(newContainer);
		}
	}

	private static Container parseContainerNode(Node containerNode,
			int[] screenSize) {
		Container newContainer = new Container(Integer.parseInt(containerNode.valueOf("@x")), Integer.parseInt(containerNode.valueOf("@y")), 
				Integer.parseInt(containerNode.valueOf("@w")), Integer.parseInt(containerNode.valueOf("@h")), 
				screenSize, containerNode.valueOf("@align"));
		parseContainerItems(newContainer, containerNode, screenSize);
		return newContainer;
	}

	private static void parseContainerItems(Container newContainer,
			Node containerNode, int[] screenSize) {
		List<Node> itemNodes = getAllChildNodes(containerNode);
		for(Node itemNode : itemNodes)
		{
			String pathPrefix = containerNode.valueOf("@pathPrefix");
			parseItemNode(newContainer, itemNode, screenSize, pathPrefix);
		}
	}

	private static void parseItemNode(Container container, Node itemNode,
			int[] screenSize, String pathPrefix) {
		if (itemNode.valueOf("@type").equals("image")) {
			ImageButton newButton = new ImageButton(Integer.parseInt(itemNode.valueOf("@x")),Integer.parseInt(itemNode.valueOf("@y")), screenSize, itemNode.valueOf("@align"));
			newButton.loadImages(pathPrefix, itemNode.valueOf("@fileName"), itemNode.valueOf("@hoverPath"));
			container.addChild(newButton);
		}
		
		System.out.println("\t\t\tcreating button");
	}

	private static List<Node> getAllChildNodes(Node parentNode)
	{
		return parentNode.selectNodes("*");
	}
}
