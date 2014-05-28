package orre.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class XMLLoader {
	public static Document readXML(File location) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(location);
			return XMLLoader.readXML(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document readXML(InputStream inputStream) {
		Builder builder = new Builder();
		try {
			return builder.build(inputStream);
		}
		catch (ValidityException e) { e.printStackTrace(); }
		catch (ParsingException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		return null;
	}
}
