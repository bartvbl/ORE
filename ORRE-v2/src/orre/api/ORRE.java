package orre.api;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventType;
import orre.resources.ResourceTypeLoader;
import orre.util.FatalExceptionHandler;

public class ORRE {
	private final GameMain main;
	private static boolean isDebugEnabled = false;

	public static ORRE init(String[] args, GameSettings settings) {
		readDebugFlag(args);
		setSwingSettings();
		GameMain game = new GameMain(settings);
		game.initialize();
		return new ORRE(game);
	}

	private static void readDebugFlag(String[] args) {
		for(String arg : args) {
			if(arg.equals("debug_dump")) {
				//debugger for openGL
				System.out.println("Debugging enabled!");
				System.setProperty("org.lwjgl.util.Debug", "true");
				System.loadLibrary("opengl32");
			} else if(arg.equalsIgnoreCase("debug_mode")) {
				isDebugEnabled = true;
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
		if(!isDebugEnabled) {			
			try {
				main.mainLoop();
			} catch(Exception e) {
				e.printStackTrace();
				FatalExceptionHandler.exitWithErrorMessage(e.getMessage());
			}
		} else {
			// Ensure exceptions are caught when they occur
			main.mainLoop();
		}
	}
	
	public void registerResourceLoader(ResourceTypeLoader loader, String[] extensions) {
		main.api_dispatchGlobalEvent(new GlobalEvent<ResourceTypeLoader>(GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER, loader));
	}
}
