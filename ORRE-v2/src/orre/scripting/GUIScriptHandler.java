package orre.scripting;

import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;

public class GUIScriptHandler {

	private static GameWorld gameWorld;
	
	public void setCurrentWorld(GameWorld world) {
		gameWorld = world;
	}
	
	private static int getGUIID() {
		return gameWorld.getOnlyGameObject(GameObjectType.GUI);
	}
	
	public static void show(String menuName) {
		gameWorld.dispatchMessage(new Message<String>(MessageType.SHOW_MENU, menuName), getGUIID());
	}
	
	public static void hide(String menuName) {
		gameWorld.dispatchMessage(new Message<String>(MessageType.HIDE_MENU, menuName), getGUIID());
	}
	
	public static void animate(String menuName, String animationName) {
		
	}

}
