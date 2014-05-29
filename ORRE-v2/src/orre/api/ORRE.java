package orre.api;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventType;
import orre.resources.ResourceTypeLoader;

public class ORRE {
	private final GameMain main;

	public static ORRE init(String[] args, String gameName) {
		readDebugFlag(args);
		setSwingSettings();
		GameMain game = new GameMain(gameName);
		game.initialize();
		return new ORRE(game);
	}

	private static void readDebugFlag(String[] args) {
		for(String arg : args) {
			if(arg.equals("debug")) {
				//debugger for openGL
				System.setProperty("org.lwjgl.util.Debug", "true");
				System.loadLibrary("opengl32");
			}
		}
	}

	private static void setSwingSettings() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	private ORRE(GameMain game) {
		this.main = game;
	}
	
	public void run() {
		main.mainLoop();
	}
	
	public void registerResourceLoader(ResourceTypeLoader loader, String[] extensions) {
		main.api_dispatchGlobalEvent(new GlobalEvent<ResourceTypeLoader>(GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER, loader));
	}
	
	public void registerGameObjectType() {
		
	}
	
	public void registerPropertyType() {
		
	}
}
