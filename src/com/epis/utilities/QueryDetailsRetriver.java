/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.utilities;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epis.utilities.ApplicationConfigurations;

/**
 * Class QueryDetailsRetriver is used to retrieve query using the querycode.
 * 
 * @version 1.00 28 May 2010
 * @author Jaya Sree
 */
public class QueryDetailsRetriver extends DefaultHandler {

	String code;

	StringBuffer textBuffer;

	String query;

	File xmlFile;

	boolean queryDetailsFound = false;

	public QueryDetailsRetriver(String code) {
		this.code = code;
		this.xmlFile = new File(ApplicationConfigurations
				.getProperty("queries.file"));
	}

	/**
	 * Method getQuery
	 * 
	 * @return Query
	 */
	public String getQuery() {
		try {
			// Create new Instance of the Parser.
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			//Parse the content of the file specified as XML
			parser.parse(xmlFile, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return query;
	}

	/**
	 * Method startElement
	 * Description Called when the starting of the Element is reached. For Example if we
	 * have Tag called <Query-Details> ... </Query-Details>, then this method is 
	 * called when <Query-Details> tag is Encountered while parsing the Current 
	 * XML File. The AttributeList Parameter has the list of all Attributes 
	 * declared for the Current Element in the XML File.
	 */
	public void startElement(String nameSpaceURI, String sName, String qName,
			Attributes atts) throws SAXException {
		if (!queryDetailsFound) {
			if (qName.equals("Query-Details")) {
				for (int i = 0; i < atts.getLength(); i++) {
					if (atts.getQName(i).equals("code")
							&& atts.getValue(i).equals(code)) {
						queryDetailsFound = true;
					}
				}
			}
		}
	}

	/**
	 * Method endElement
	 * Description Called when the Ending of the current Element is reached. For example in
	 * the above explanation, this method is called when </Query-Details> tag is reached.
	 */
	public void endElement(String nameSpaceURI, String sName, String qName)
			throws SAXException {
		if (queryDetailsFound) {
			if (qName.equals("Query"))
				query = textBuffer.toString().trim();
		}
		textBuffer = new StringBuffer();
		if (qName.equals("Query-Details")) {
			queryDetailsFound = false;
		}
	}

	/**
	 * Method characters
	 * Description While Parsing the XML file, if extra characters like space or enter
	 * Character are encountered then this method is called. If you don't want
	 * to do anything special with these characters, then you can normally leave
	 * this method blank.
	 */

	public void characters(char buff[], int offSet, int len)
			throws SAXException {
		if (queryDetailsFound) {
			String s = new String(buff, offSet, len);
			if (textBuffer == null)
				textBuffer = new StringBuffer(s);
			else
				textBuffer.append(s);
		}
	}
}
