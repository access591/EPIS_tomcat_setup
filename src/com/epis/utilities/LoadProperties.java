/**
  * File       : LoadProperties.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities;

import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
	static Properties props = new Properties();
	
	public static Properties loadFile(String fileName){
		Log log= new Log(LoadProperties.class);
		//log.info("LoadProperties :loadFile() enter method");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    InputStream inStream = null;
	    try{
	    	inStream = loader.getResourceAsStream(fileName);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	//	log.info("input stream is--- "+inStream);
		try{
			props.load(inStream);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	//	log.info("LoadProperties :loadFile() Leaving method");
		return props;
	}

}
