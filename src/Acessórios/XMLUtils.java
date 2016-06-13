/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acessórios;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Rafael
 */
public class XMLUtils {
    
    /**
	 * Returns a list of elements having a given tag
	 */
	public static List<Element> getElements(Element element, String tagName)
	{
		Node node = element.getFirstChild();
		List<Element> elements = new ArrayList<Element>();

		while (node != null)
		{
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& node.getNodeName().compareTo(tagName) == 0)
				elements.add((Element) node);

			node = node.getNextSibling();
		}

		return elements;
	}
    
    public static Element getSingleElement(Element element, String tagName)
	{
		Node node = element.getFirstChild();

		while (node != null)
		{
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& node.getNodeName().compareTo(tagName) == 0)
				return (Element) node;

			node = node.getNextSibling();
		}

		return null;
	}

	

	/**
	 * Loads an optional string attribute from a XML element
	 */
	public static String getStringAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);
		return (value != null) ? value : "";
	}

	/**
	 * Loads an optional integer attribute from a XML element
	 */
	public static int getIntAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);

		if (value == null)
			return 0;

		if (value.length() == 0)
			return 0;

		return Integer.parseInt(value);
	}

	/**
	 * Loads an optional double attribute from a XML element
	 */
	public static double getDoubleAttribute(Element element, String name)
	{
		String value = element.getAttribute(name);

		if (value == null)
			return 0.0;

		if (value.length() == 0)
			return 0.0;

		return Double.parseDouble(value);
	}

	/**
	 * Loads an optional datetime attribute from a XML element
	 */
//	public static DateTime getDateAttribute(Element element, String name)
//	{
//		String value = element.getAttribute(name);
//
//		if (value == null)
//			return null;
//
//		if (value.length() == 0)
//			return null;
//
//		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
//		return dtf.parseDateTime(value);
//	}

	/**
	 * Loads an optional string element value from a XML element
	 */
	public static String getStringNode(Element element, String name)
	{
		return getStringNode(element, name, "");
	}

	/**
	 * Loads an optional string element value from a XML element
	 */
	public static String getStringNode(Element element, String name, String _default)
	{
		Element child = getSingleElement(element, name);

		if (child == null)
			return _default;

		String value = child.getTextContent();

		if (value == null)
			return _default;

		return value;
	}

	/**
	 * Loads an optional integer element value from a XML element
	 */
	public static int getIntNode(Element element, String name)
	{
		return getIntNode(element, name, 0);
	}

	/**
	 * Loads an optional integer element value from a XML element
	 */
	public static int getIntNode(Element element, String name, int _default)
	{
		Element child = getSingleElement(element, name);

		if (child == null)
			return _default;

		String value = child.getTextContent();

		if (value == null)
			return _default;

		if (value.length() == 0)
			return _default;

		return Integer.parseInt(value);
	}

	/**
	 * Loads an optional Boolean element value from a XML element
	 */
	public static boolean getBooleanNode(Element element, String name, boolean _default)
	{
		Element child = getSingleElement(element, name);

		if (child == null)
			return _default;

		String value = child.getTextContent();

		if (value == null)
			return _default;

		if (value.compareTo("S") == 0)
			return true;

		if (value.compareTo("N") == 0)
			return false;

		return _default;
	}

	/**
	 * Loads an optional double element value from a XML element
	 */
	public static double getDoubleNode(Element element, String name)
	{
		Element child = getSingleElement(element, name);

		if (child == null)
			return 0.0;

		String value = child.getTextContent();

		if (value == null)
			return 0.0;

		if (value.length() == 0)
			return 0.0;

		return Double.parseDouble(value);
	}

	/**
	 * Loads an optional datetime element value from a XML element
	 */
//	public static DateTime getDateNode(Element element, String name)
//	{
//		Element child = getSingleElement(element, name);
//
//		if (child == null)
//			return null;
//
//		String value = child.getTextContent();
//
//		if (value == null)
//			return null;
//
//		if (value.length() == 0)
//			return null;
//
//		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
//		return dtf.parseLocalDate(value).toDateTimeAtStartOfDay();
//	}

	/**
	 * Loads an optional datetime element value from a XML element
	 */
//	public static DateTime getDateTimeNode(Element element, String name)
//	{
//		Element child = getSingleElement(element, name);
//
//		if (child == null)
//			return null;
//
//		String value = child.getTextContent();
//
//		if (value == null)
//			return null;
//
//		if (value.length() == 0)
//			return null;
//		
//		value = value.replace('T', ' ').replace("Z", "");
//
//		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//		return dtf.parseDateTime(value);
//	}
}
