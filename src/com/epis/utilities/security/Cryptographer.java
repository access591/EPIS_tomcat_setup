 /**
  * File       : Cryptographer.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities.security;


import java.io.UnsupportedEncodingException;

import com.epis.utilities.Log;

/**
 * @author nagarjunreddyt
 *
 */
public abstract class Cryptographer {
	static Log log = new Log(Cryptographer.class);
	
    public abstract String doEncrypt(String pInput) throws EncryptionException;
    public abstract String doDecrypt(String pInput) throws EncryptionException;
    
    public static byte[] generateKeySpc(String keyString) throws EncryptionException{
        byte[] keyBytes= new byte[16];
        try {
            byte[] b= keyString.getBytes("UTF-8");
            int len= b.length; 
            if (len > keyBytes.length) len = keyBytes.length;
        }catch (UnsupportedEncodingException _UnEncEx) {
            log.printStackTrace(_UnEncEx);
            throw new EncryptionException("9003", "Exception while generating key specs for encryption");
        } catch (Exception e) {
            log.printStackTrace(e);
            throw new EncryptionException("1003", "Unknown exception while generating key specs for encryption ");
        }
        return keyBytes;
    }
}
