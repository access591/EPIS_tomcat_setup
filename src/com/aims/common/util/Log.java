 /**
  * File       : Log.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.aims.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.log4j.Logger;




public class Log implements Constants {
	Logger logger=null;
	Properties prop=new Properties();	
	public Log(Class className){     	
		logger = Logger.getLogger(className.getClass());
		CommonUtil.loadLogFile(Constants.LOG_PROPERTIES_FILE_NAME,className);
		
	}
	
	public  void debug(String message){
		logger.debug(message);
	}
	public  void info(String message){
		logger.info(message);
	}
	public   void warn(String message){
		logger.warn(message);
	}
	public  void error(String message){
		logger.error(message);
	}
	public  void fatal(String message){
		logger.fatal(message);
	}
	public void printStackTrace(Throwable ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		logger.error("\n" + sw.toString());
	}
}
