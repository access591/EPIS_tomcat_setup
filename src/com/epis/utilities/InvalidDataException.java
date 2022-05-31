/**
  * File       : InvalidDataException.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities;

import java.io.Serializable;

/**
 * @author sureshkumarr
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InvalidDataException extends Exception implements Serializable {
	

	/**
	 * 
	 */
	public InvalidDataException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param arg0
	 */
	public InvalidDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
