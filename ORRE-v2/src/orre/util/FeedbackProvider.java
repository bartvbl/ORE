package orre.util;

import javax.swing.JOptionPane;

public class FeedbackProvider {
	private static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private static void showConfirmMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showLoadOBJFileNotFoundMessage(String src)
	{
		showErrorMessage("Failed to load OBJ file. the file  \n\"" + src + "\"\nwas not found.");
	}

	public static void showLoadOBJFileFailedMessage(String src) {
		showErrorMessage("Loading of the OBJ file \n\"" + src + "\"\n failed. It may be incomplete or corrupted.");
	}
}
