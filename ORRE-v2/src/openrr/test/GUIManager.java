package openrr.test;

import java.util.ArrayList;

public class GUIManager {
	
	private ArrayList<? extends GUIElement> children;
	
	public GUIManager() {
//		ImageButton x = new ImageButton(new int[] {0,0,27,28}, 0, "res/images/menus/back.bmp");
//		children =  new ArrayList<GUIElement>();
//		children.append(x);
	}
	
	public void draw() {
		for (GUIElement child : children) {
//			child.draw();
		}
	}

}
