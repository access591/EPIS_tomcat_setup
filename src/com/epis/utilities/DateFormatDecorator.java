 package com.epis.utilities;

import java.util.StringTokenizer;

import javax.servlet.jsp.PageContext;


import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DateFormatDecorator implements DisplaytagColumnDecorator {
	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum media) throws DecoratorException {
		try {
			if (columnValue != null) {
					String column =  columnValue.toString();
					columnValue = (Object)convertOracleDate(column.substring(0,column.indexOf(" ")));
			}
			return columnValue;
		} catch (Exception nfe) {
		}
		return columnValue;
	}
	
	public String convertOracleDate(String oracleDt) throws Exception
	{
		/*
			This function takes a date string and returns it in a different format.
			This is useful where we want to convert a date that is retrieved from oracle using 
			resultset.getDate("dateField") function.  That will be of the form YYYY-MON-DD.
			This function will convert the oracle date to the format "DD/Mon/YYYY", since that
			is the format being used to display dates on AIMS screens.
		*/
		String returnDt= new String("");

		if (oracleDt == null || oracleDt.equals(""))
			return "";

		StringTokenizer st = new StringTokenizer(oracleDt, "-");
		String dateArr[]=new String[3];
		String monthArr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		int tokenCount=0;
		while (st.hasMoreTokens()) {
			dateArr[tokenCount]=st.nextToken();
			tokenCount++;
		}
		returnDt=dateArr[2]+"/"+monthArr[Integer.parseInt(dateArr[1])-1]+"/"+dateArr[0];

		return returnDt;
	}
}