package orre.util;

import nu.xom.Element;

public class XMLUtils {
	public static boolean hasAttribute(Element element, String attribute) {
		String value = element.getAttributeValue(attribute);
		return ((value != null) && (!value.equals("")));
	}
}
