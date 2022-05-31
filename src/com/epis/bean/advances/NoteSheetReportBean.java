package com.epis.bean.advances;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

public class NoteSheetReportBean extends RequestBean implements Serializable{
	

	public NoteSheetReportBean(HttpServletRequest request){
		super(request);
		}
	
	public NoteSheetReportBean(){
	}
}
