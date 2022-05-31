 package com.epis.utilities;


import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class StringFormatDecorator implements DisplaytagColumnDecorator {
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		if(columnValue==null || "".equals(((String)columnValue).trim())){
			columnValue="--";
		}
		return columnValue; 
	}
}
