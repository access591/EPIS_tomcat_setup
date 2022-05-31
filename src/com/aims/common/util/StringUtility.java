/*
 * Created on Aug 22, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.common.util;

/**
 * @author subbaraod
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringUtility {
	 private StringUtility()
	    {
	    }

	    public static StringBuffer replace(char[]  string, char[] replace,String charletter)
	    {
	        StringBuffer sb = new StringBuffer();
	        boolean found;
	       
	        for (int i = 0; i < string.length; i++)
	        {
	        	
	            if(string[i] == replace[0])
	            {
	                found = true;
	             
	                for(int j = 0; j < replace.length; j++)
	                {
	                    if(!(string[i + j] == replace[j]))
	                    {
	                       found = false;
	                       break;
	                    }
	                }
	                
	                if(found)
	                {
	                    sb.append(charletter);
	                    i = i + (replace.length - 1);
	                    continue;
	                }
	            }
	            sb.append(string[i]);
	        }
	        
	        return sb;
	    }
	    public static StringBuffer replaces(char[]  string, char[] replace,String charletter)
	    {
	        StringBuffer sb = new StringBuffer();
	        boolean found;
	        int j=0;
	        for (int i = 0; i < string.length; i++)
	        {
	        	
	        	for(int k = 0; k < replace.length; k++){
	        		if(string[i] == replace[k])
		            {
	        		
		                    sb.append(charletter);
		                    if(i!=(string.length-1)){
		                    	i++;
		                    }
		                  
		                }
	        		
		        }
	        	 sb.append(string[i]); 
	        	}
	            
	        
	        return sb;
	    }
	    public static boolean StringCount(char[]  string, char[] replace){
		       int count=0,word=0;
		       boolean countFlag=false;
		        for (int i = 0; i < string.length; i++){
		            if(string[i]== replace[0]){
		            	count++;
		            }else{
		            	if(string[i]!=' '){
		            		word++;
		            	}
		            }
		        }
		       if(word==0){
		    	   countFlag=true;
		       }
		       
		     return countFlag;  
		    }
	    
	    public static int StringCountVal(char[]  string, char[] replace){
		       int count=0,word=0;
		       boolean countFlag=false;
		        for (int i = 0; i < string.length; i++){
		            if(string[i]== replace[0]){
		            	count++;
		            }else{
		            	if(string[i]!=' '){
		            		word++;
		            	}
		            }
		        }
		       if(word==0){
		    	   countFlag=true;
		       }
		       
		     return word;  
		    }
	    
	    public static int delimeterCount(char[]  string, char[] replace){
		       int count=0,word=0;
		       boolean countFlag=false;
		        for (int i = 0; i < string.length; i++){
		            if(string[i]== replace[0]){
		            	count++;
		            }
		        }
		     
		  
		     return count;  
		    }
	    public static String checknull(String string) {
			if (string == null)
				string = "";
			return string.trim();
		}
	    public static String reportChecknull(String string)
	    {
	    	if (string == null || string.equals("null"))
				string = "--";
			return string.trim();
	    }
		
		public static String checknull(String string,String append) {
			if (string == null)
				string = "" + append.trim();
			return string.trim() + append.trim();
		}
}
