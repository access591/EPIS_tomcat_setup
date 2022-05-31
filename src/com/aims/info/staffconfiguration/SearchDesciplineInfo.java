//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.util.ArrayList;



/** 
 * MyEclipse Struts
 * Creation date: 08-06-2007
 * 
 * XDoclet definition:
 * @struts.form name="searchstafftype"
 */
public class SearchDesciplineInfo implements Serializable {

	  //private String fromDate ;
	  //private String toDate ;
	  private Integer searchdisciplineCd=null;
	  private String searchdisciplineNm;
	  private String searchdisciplineDesc="";
	  private String status="";
	/**
	 * @return Returns the searchdisciplineCd.
	 */
	public Integer getSearchdisciplineCd() {
		return searchdisciplineCd;
	}
	/**
	 * @param searchdisciplineCd The searchdisciplineCd to set.
	 */
	public void setSearchdisciplineCd(Integer searchdisciplineCd) {
		this.searchdisciplineCd = searchdisciplineCd;
	}
	/**
	 * @return Returns the searchdisciplineDesc.
	 */
	public String getSearchdisciplineDesc() {
		return searchdisciplineDesc;
	}
	/**
	 * @param searchdisciplineDesc The searchdisciplineDesc to set.
	 */
	public void setSearchdisciplineDesc(String searchdisciplineDesc) {
		this.searchdisciplineDesc = searchdisciplineDesc;
	}
	/**
	 * @return Returns the searchdisciplineNm.
	 */
	public String getSearchdisciplineNm() {
		return searchdisciplineNm;
	}
	/**
	 * @param searchdisciplineNm The searchdisciplineNm to set.
	 */
	public void setSearchdisciplineNm(String searchdisciplineNm) {
		this.searchdisciplineNm = searchdisciplineNm;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	  
	
	
}

