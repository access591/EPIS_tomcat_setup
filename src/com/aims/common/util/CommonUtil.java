 /**
  * File       : CommonUtil.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.aims.common.util;

import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.PropertyConfigurator;

import com.aims.info.payrollprocess.YearBean;

public class CommonUtil implements Constants{
	static Log log = new Log(CommonUtil.class);
	static Properties props = new Properties();
	public static String convertDate(java.util.Date dt){
		String MY_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";
		String convert_date = new SimpleDateFormat(MY_DATE_FORMAT).format(dt);
		return convert_date;
	}
	public static Date getStringtoDate(String dt){
	
	    SimpleDateFormat from = new SimpleDateFormat("dd/MMM/yyyy");
	    SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
	    String str = "";
	    java.sql.Date date = null;
	    try {
	    	str = to.format(from.parse(dt));
	   	    date = Date.valueOf(str);
	        System.out.println("----"+date);
	    }
	    catch(ParseException e) {
	       e.printStackTrace();
	    }
		return date;
	}
	public static Date getStringtoDate1(String dt){
		
	    SimpleDateFormat from = new SimpleDateFormat("dd/MM/yyyy");
	    SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
	    String str = "";
	    java.sql.Date date = null;
	    try {
	    	str = to.format(from.parse(dt));
	   	    date = Date.valueOf(str);
	        System.out.println("----"+date);
	    }
	    catch(ParseException e) {
	       e.printStackTrace();
	    }
		return date;
	}
	public static String getDatetoString(Date dt){
		
	   // SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat to = new SimpleDateFormat("dd/MMM/yyyy");
	    String convertDate = "";
	    

	    	convertDate=to.format(dt);
	    	System.out.println("Date is"+convertDate);
	       
	    
		return convertDate;
	}
	public static Properties getPropsFile(String filepath){
		System.out.println(""+filepath);
		ClassLoader loader = ClassLoader.getSystemClassLoader();
	    InputStream inStream = null;
	    try{
	    	inStream = loader.getResourceAsStream(filepath);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		System.out.println("input stream is--- "+inStream);
		try{
			props.load(inStream);
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	}
	public static Properties getPropsFile(String filepath,Class className){
		System.out.println(""+filepath+"ClassName"+className);
		ClassLoader loader = className.getClassLoader();
	    InputStream inStream = null;
	    try{
	    	inStream = loader.getResourceAsStream(filepath);
	    	props.load(inStream);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return props;
	}
	public static Properties loadLogFile(String filepath,Class className){
		String filePath="";
	
		try{
			props=CommonUtil.getPropsFile(Constants.APPLICATION_CONFIG_PROPERTIES_FILE_NAME,className);
			filePath=props.getProperty("log.filepath");
			props.clone();
		    props=CommonUtil.getPropsFile(filepath,className);
			props.setProperty("info_file_path",filePath);
		 	PropertyConfigurator.configure(props);
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	} 
	public static String checkName(String tablename,String namecolumn,String namecolumnval){
		String sql = "select count(*) from "+tablename+" where "+namecolumn+" = '"+namecolumnval+"'";
		return sql;
	}
	public static String checkName(String tablename,String namecolumn,String namecolumnval,String cdcolumn,int cdcolumnval){
		String sql = "select count(*) from "+tablename+" where "+namecolumn+" = '"+namecolumnval+"' and "+cdcolumn+" <>"+cdcolumnval;
		return sql;
	}
	public static int returnIndexVal(String str){
		int indx = 0;
		if(str.lastIndexOf("Exception:")!=-1)
			indx = str.lastIndexOf("Exception:")+10;
		return indx;
	}
	public static List getYearList() {
		Calendar cal = new GregorianCalendar();
		int curyear = cal.get(Calendar.YEAR);
		
		ArrayList yearlist = new ArrayList();
		for(int i=4;i>=1;i--){
			YearBean yearbean = new YearBean();
			//log.info("curyear in for "+(curyear-i));
			yearbean.setYear((curyear-i)+"");
			yearlist.add(yearbean);
		}
		YearBean curyearbean = new YearBean();
		curyearbean.setYear(curyear+"");
		yearlist.add(curyearbean);
		YearBean yearbean1 = new YearBean();
		yearbean1.setYear((curyear+1)+"");
		yearlist.add(yearbean1);
		return yearlist;
	}
	public static String escapeString(String in){
		String out = in;
		if(!out.equals("")){
			out = out.replaceAll("'","''");
			out = StringEscapeUtils.escapeJava(out);
		}
		return out;
	}
	public static String unescapeString(String str){
		
		String s = StringEscapeUtils.unescapeJava(str);
		
		return s;
	}
	public static double getLeastValue(double v1,double v2){
		double hval = 0.0;
		if(v1>v2)
			hval = v2;
		else
			hval = v1;
		return hval;
	}
}
