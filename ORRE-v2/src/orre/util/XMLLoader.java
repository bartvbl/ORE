package orre.util;

import java.io.File;
import java.io.IOException;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class XMLLoader {
	public static Document readMapXML(String src) {
		Builder builder = new Builder();
		try {
			return builder.build(new File(src));
		}
		catch (ValidityException e) { e.printStackTrace(); }
		catch (ParsingException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		return null;
	}
}
