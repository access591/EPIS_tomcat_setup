//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.util.ArrayList;



/** 
 * MyEclipse Struts
 * Creation date: 21-08-07
 * 
 * XDoclet definition:
 * @struts.form name=""
 */
public class SearchGroupInfo implements Serializable {

	  //private String fromDate ;
	  //private String toDate ;
	  private Integer searchgroupCd=null;
	  private String searchgroupNm;
	  private String searchgroupDesc="";	
	  private String status="";
	/**
	 * @return Returns the searchgroupCd.
	 */
	public Integer getSearchgroupCd() {
		return searchgroupCd;
	}
	/**
	 * @param searchgroupCd The searchgroupCd to set.
	 */
	public void setSearchgroupCd(Integer searchgroupCd) {
		this.searchgroupCd = searchgroupCd;
	}
	/**
	 * @return Returns the searchgroupDesc.
	 */
	public String getSearchgroupDesc() {
		return searchgroupDesc;
	}
	/**
	 * @param searchgroupDesc The searchgroupDesc to set.
	 */
	public void setSearchgroupDesc(String searchgroupDesc) {
		this.searchgroupDesc = searchgroupDesc;
	}
	/**
	 * @return Returns the searchgroupNm.
	 */
	public String getSearchgroupNm() {
		return searchgroupNm;
	}
	/**
	 * @param searchgroupNm The searchgroupNm to set.
	 */
	public void setSearchgroupNm(String searchgroupNm) {
		this.searchgroupNm = searchgroupNm;
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

