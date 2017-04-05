package orre.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	public static String[] readFileContents(File src) throws IOException {
		FileReader fileReader = new FileReader(src);
		byte[] fileContent = Files.readAllBytes(Paths.get(src));
		String fileString = new String(fileContent, StandardCharsets.UTF_8);
		fileReader.close();
		return fileString.split(System.lineSeparator());
	}
}
