 package com.epis.utilities;

import java.text.DecimalFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class NumberFormatDecorator implements DisplaytagColumnDecorator {
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		DecimalFormat df = new DecimalFormat("######.##");
		try  {
			if ( columnValue != null ){
				columnValue = df.format( columnValue );
			}
			return columnValue;
		}catch ( Exception nfe ){
			
		}
		return columnValue; 
	}
}
