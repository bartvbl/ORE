package openrr.test;

import orre.util.XMLDocument;
import org.dom4j.Node;
import java.util.List;

import orre.gl.Texture;
import openrr.test.Button;
import java.util.ArrayList;

public class Main {
	
	static XMLDocument doc = new XMLDocument("res/defaultGUI.xml");
	static ArrayList<Button> buttons = new ArrayList<Button>();
	
	public static void main (String args[]) {
		System.out.println("Texture In");
		Texture test = new Texture("res/raider.bmp");
		System.out.println("Texture Out");
		loadGUI();
		//System.out.println(buttons.get(0).stateImages.get(0)+" "+buttons.get(0).stateImages.get(1)+buttons.get(0).stateImages.get(2)+buttons.get(0).stateImages.get(4));
		while (true) {
			for (Button button : buttons) {
				System.out.println(button.image);
				button.draw();
			}
		}
		
	}
	
	
	public static void loadGUI() {
		List<Node> menuNodes = doc.document.selectNodes("/ORRDefaultGUI/menus/*");
		for (Node menuNode : menuNodes) {
			List<Node> itemNodes = menuNode.selectNodes(menuNode.getPath()+"/*");
			int i = 0;
			for (Node itemNode : itemNodes) {
				buttons.add(new Button(Integer.parseInt(itemNode.valueOf("@x")),Integer.parseInt(itemNode.valueOf("@y")), new int[] {800,600}, itemNode.valueOf("@align")));
				buttons.get(i).loadImages(itemNode.valueOf("@path"), itemNode.valueOf("@fileName"), itemNode.valueOf("@hoverPath"));
				i++;
			}
		}
	}
	
	
	public String searchNode(String name) {
		String path;
		int nodeSize=doc.getNumChilds("/ORRDefaultSounds/sounds");
		for (int i=0; i<nodeSize; i++) {
			if (doc.getAttribute("/ORRDefaultSounds/sounds/sound["+(i+1)+"]","name").equals(name)) {
				path = "src/res/sounds/"+doc.getAttribute("/ORRDefaultSounds/sounds/sound["+(i+1)+"]","src");
				return path;
			}
		}
		return "";
	}

}
