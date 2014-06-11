package orre.devTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Property;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.sceneGraph.SceneNode;

public class GameInfoWindow {
	public static void showDebugInfo(GameWorld world, ResourceCache cache) {
		JFrame window = new JFrame("Scene");
		JTabbedPane mainTabPane = new JTabbedPane();
		window.add(mainTabPane);
		showSceneGraph(world.rootNode, mainTabPane);
		showGameWorld(world.debugonly_getAllGameOjects(), mainTabPane);
		showResourceCache(cache, mainTabPane);
		window.setSize(300, 500);
		window.setLocation(100, 100);
		window.setVisible(true);
	}

	public static void showSceneGraph(SceneNode rootNode, JTabbedPane parent) {
		JTree sceneTree = new JTree();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(sceneTree);
		parent.add("Scene graph", scrollPane);
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootNode);
		DefaultTreeModel model = new DefaultTreeModel(node);
		sceneTree.setModel(model);
		populateTree(node, rootNode);
		for(int i = 0; i < sceneTree.getRowCount(); i++) {
			sceneTree.expandRow(i);
		}
		
	}

	private static void populateTree(DefaultMutableTreeNode node, SceneNode sceneNode) {
		for(SceneNode child : sceneNode.getChildren()) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
			node.add(childNode);
			populateTree(childNode, child);
		}
	}
	
	private static void showGameWorld(Collection<GameObject> gameObjects, JTabbedPane mainTabPane) {
		JScrollPane scrollPane = new JScrollPane();
		JPanel informationPanel = new JPanel();
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, informationPanel);
		mainTabPane.add("Game world", splitter);
		JTree worldTree = new JTree();
		scrollPane.setViewportView(worldTree);
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		worldTree.setModel(model);
		for(GameObject object : gameObjects) {
			DefaultMutableTreeNode objectNode = new DefaultMutableTreeNode(object.id + ": " + object.type);
			ArrayList<Property> properties = object.debugonly_getAllProperties();
			for(Property property : properties) {
				DefaultMutableTreeNode propertyNode = new DefaultMutableTreeNode(property.type);
				objectNode.add(propertyNode);
			}
			rootNode.add(objectNode);
		}
		worldTree.expandRow(0);
	}
	
	private static void showResourceCache(ResourceCache cache, JTabbedPane mainTabPane) {
		JScrollPane scrollPane = new JScrollPane();
		JPanel informationPanel = new JPanel();
		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, informationPanel);
		mainTabPane.add("Resource cache", splitter);
		JTree worldTree = new JTree();
		scrollPane.setViewportView(worldTree);
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		worldTree.setModel(model);
		HashMap<Enum<?>, HashMap<String, Resource>> resourceMap = cache.debugonly_getResourceMap();
		for(Enum<?> type : resourceMap.keySet()) {
			DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(type);
			for(String name : resourceMap.get(type).keySet()) {
				DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(name);
				typeNode.add(nameNode);
			}
			rootNode.add(typeNode);
		}
		worldTree.expandRow(0);
	}
}
