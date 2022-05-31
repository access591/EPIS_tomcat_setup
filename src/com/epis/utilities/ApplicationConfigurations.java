/*
 * Copyright © 2009 Navayuga Infotech, All Rights Reserved.
 *
 * NAVAYUGA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.epis.utilities;

import java.util.ResourceBundle;

/**
 * Class ApplicationConfigurations is used to retreive the application configurations 
 * from the properties file.
 * 
 * @version 1.00 28 May 2010
 * @author Jaya Sree
 */

public class ApplicationConfigurations {

	private static ResourceBundle bundle = null;

	// This provides a logging object that you can create and destroy as you.
	private static Log log = new Log(ApplicationConfigurations.class);

	// While Loading the Configuration Properties file to be loaded.
	static {
		try {
			//Gets a resource bundle
			bundle = ResourceBundle
					.getBundle("com.epis.resource.Configurations");
		} catch (Exception exp) {
			log.error("Error:" + exp.getMessage());
		}
	}

	/**
	 * Method search Description This method is used to return the property
	 * value that are given in the Configuration file by sending the property
	 * name.
	 * 
	 * @param propName
	 * @return propValue
	 */
	public static String getProperty(String propName) {
		String propValue = "";
		if (bundle != null) {
			propValue = bundle.getString(propName);
		}
		return propValue;
	}
}
