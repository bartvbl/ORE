package orre;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import orre.core.GameMain;
import orre.util.Logger;

public class Main {
	public static void main(String[] args) 
	{
		setSwingSettings();
		new Logger();
		GameMain game = new GameMain();
		game.initialize();
		game.mainLoop();
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
}
