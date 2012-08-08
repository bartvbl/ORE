package orre.util;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

public class FatalExceptionHandler {
	public static void exitWithErrorMessage(String message) {
		Display.destroy();
		JOptionPane.showMessageDialog(null, message, "Fatal Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
