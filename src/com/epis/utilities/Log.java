package com.epis.utilities;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Log {
	 Logger logger=null;
	 File filePath = null;
	static Properties props=new Properties();	
	     public Log(Class className){     	
	     logger = Logger.getLogger(className.getClass());
	     final String LOG_PROPERTIES_FILE_NAME = "com/epis/resource/log4j.properties";
	     props =loadLogFile(LOG_PROPERTIES_FILE_NAME,className);
	     //props =getPropsFile(LOG_PROPERTIES_FILE_NAME, className);
	     PropertyConfigurator.configure(props);
	    }
	     
	     public static Properties loadLogFile(String propertyFile,Class className){
	 		//String filePath="";
	 		try{
				ResourceBundle bundle = ResourceBundle
				.getBundle("com.epis.resource.Configurations");
		          String logPath = bundle.getString("logger.file.path");
	 			File filePath = new File(logPath);
	 			if (!filePath.exists()){
	 				filePath.mkdirs();
	 			}
	 			props.clone();
	 		    props=getPropsFile(propertyFile,className);
	 			props.setProperty("info_file_path",filePath.toString());
	 		 //	PropertyConfigurator.configure(props);
	 		}catch(Exception e){
	 			e.printStackTrace();
	 		}
	 		return props;
	 	} 
	 	public static Properties getPropsFile(String filepath,Class className){		
			
		    try{
		    	ClassLoader loader = className.getClassLoader();
			    InputStream inStream = null;
			    try{
			    	inStream = loader.getResourceAsStream(filepath);
			    	props.load(inStream);
			    }catch(Exception e){
			    	e.printStackTrace();
			    }
				
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
			return props;
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
