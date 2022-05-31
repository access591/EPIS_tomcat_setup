 /**
  * File       : EncryptionException.java
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
public class EncryptionException extends EncryptException {

    
	private static final long serialVersionUID = 5132785415547011933L;

	 
	/**
     * @param code
     * @param msg
     * @param cause
     */
    public EncryptionException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    /**
     * @param code
     * @param msg
     */
    public EncryptionException(String code, String msg) {
        super(code, msg);
    }

    
}
