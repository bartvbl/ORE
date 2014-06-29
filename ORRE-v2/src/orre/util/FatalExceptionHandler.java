package orre.util;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class FatalExceptionHandler {
	public static void exitWithErrorMessage(String message) {
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.destroy();
		JOptionPane.showMessageDialog(null, message, "Fatal Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
