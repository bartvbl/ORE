package orre.scripting;

import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gui.AnimateMenuCommand;

public class GUIScriptHandler {

	private static GameWorld gameWorld;
	
	public void setCurrentWorld(GameWorld world) {
		gameWorld = world;
	}
	
	private static int getGUIID() {
		return gameWorld.getOnlyGameObject(GameObjectType.GUI);
	}
	
	public static void show(final String menuName) {
		ScriptAPI.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				gameWorld.dispatchMessage(new Message<String>(MessageType.SHOW_MENU, menuName), getGUIID());
			}
		});
	}
	
	public static void hide(final String menuName) {
		ScriptAPI.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				gameWorld.dispatchMessage(new Message<String>(MessageType.HIDE_MENU, menuName), getGUIID());
			}
		});
	}
	
	public static void animate(final String menuName, final String animationName) {
		ScriptAPI.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				gameWorld.dispatchMessage(new Message<AnimateMenuCommand>(MessageType.ANIMATE_MENU, new AnimateMenuCommand(menuName, animationName)), getGUIID());
			}
		});
	}

}
