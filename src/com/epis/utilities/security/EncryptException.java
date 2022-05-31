 /**
  * File       : EncryptException.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities.security;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author nagarjunreddyt
 *
 */
public abstract class EncryptException extends Exception {
	
	private static final long serialVersionUID = 3695288342030798587L;
	private final String mCode;

	/**
	 * @param String code
	 * @param String msg
	 */
	protected EncryptException(String code, String msg) {
		super(msg);
		mCode = code;
	}

	
	/**
	 * @param String code
	 * @param String msg
	 * @param Throwable cause
	 */
	protected EncryptException(String code, String msg, Throwable cause) {
		super(msg, cause);
		mCode = code;
	}

	/**
	 * @return error Code
	 */
	public final String getCode() {
		return mCode;
	}

	/** * Returns the exceptions stack trace in a string. */
	public final String getStackTraceString() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		printStackTrace(pw);
		return sw.toString();
	}
}
