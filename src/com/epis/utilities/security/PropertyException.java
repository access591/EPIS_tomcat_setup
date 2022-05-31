 /**
  * File       : PropertyException.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities.security;


/**
 * @author nagarjunreddyt
 *
 */
public class PropertyException extends EncryptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337731453337902481L;

	/**
	 * @param code
	 * @param msg
	 */
	public PropertyException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * @param code
	 * @param msg
	 * @param cause
	 */
	public PropertyException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

}
