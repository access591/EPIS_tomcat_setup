
 /**
  * File       : PasswordService.java
  * Date       : 27/09/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */






package com.aims.common.util;                  





import java.io.UnsupportedEncodingException;       //Packages for Encryption


import java.security.MessageDigest;


import java.security.NoSuchAlgorithmException;


//import sun.misc.BASE64Encoder;
import java.util.Base64;




/**


 * @author 


 *


 * TODO To change the template for this generated type comment go to


 * Window - Preferences - Java - Code Style - Code Templates


 */





public final class PasswordService 


{


    


	private static PasswordService instance;


	


	//Constructor


	public PasswordService()          


    {


	


    }





	public synchronized String encrypt(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException


	{


		MessageDigest md = null;


		md = MessageDigest.getInstance("SHA"); 


		md.update(plaintext.getBytes("UTF-8")); 


	    byte raw[] = md.digest(); 


	    //String hash = (new BASE64Encoder()).encode(raw); 
	    
	    String hash = Base64.getEncoder().encodeToString(raw);


	    return hash;


	}


	


	public static synchronized PasswordService getInstance() 


	{


		if(instance == null)


			return new PasswordService();


	    else


	    	return instance;


	}








} //End of PasswordService


