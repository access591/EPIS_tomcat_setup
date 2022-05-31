 /**
  * File       : PropertyHelper.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities.security;

import java.io.IOException;
import java.util.Properties;


import com.epis.utilities.Log;
import com.epis.utilities.security.PropertyException;



/**
 * @author nagarjunreddyt
 *
 */
public class PropertyHelper {
	static Log log = new Log(PropertyHelper.class);
	
	public static String getProperty(String propName)throws PropertyException{
		
        String propVal = null;
        Properties props = new Properties();
        try{
        	Class className = PropertyHelper.class;
            props.load((className.getClassLoader()).getResourceAsStream("aims/resource/crypto.properties"));
            propVal = props.getProperty(propName);
        }catch (IOException _IOEx) {
            log.printStackTrace(_IOEx);
            throw new PropertyException("8001","Properties file [ crypto.properties ] not found");
        }catch (Exception _Ex) {
            //System.out.println("Exception - Check Properties file [ crypto.properties ]"+_Ex.getMessage());
            log.printStackTrace(_Ex);
            throw new PropertyException("1004"," Unknown exception while reading crypto properties");
        }

        return propVal;
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("AES "+getProperty("AES.Key"));
            System.out.println("DES "+getProperty("DES.Key"));    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
