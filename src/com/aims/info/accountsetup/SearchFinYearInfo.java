//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package com.aims.info.accountsetup;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;



/** 
 * MyEclipse Struts
 * Creation date: 28-08-07
 * 
 * XDoclet definition:
 * @struts.form name=""
 */
public class SearchFinYearInfo implements Serializable {

	  //private String fromDate ;
	  //private String toDate ;
	  private String searchfinyearCd;
	  private String searchfinyearName;
	  private String searchfinyearDesc="";
	  private String searchfromDate;
	  private String searchtoDate;
	  private String status;
	/**
	 * @return Returns the searchfinyearCd.
	 */
	public String getSearchfinyearCd() {
		return searchfinyearCd;
	}
	/**
	 * @param searchfinyearCd The searchfinyearCd to set.
	 */
	public void setSearchfinyearCd(String searchfinyearCd) {
		this.searchfinyearCd = searchfinyearCd;
	}
	/**
	 * @return Returns the searchfinyearDesc.
	 */
	public String getSearchfinyearDesc() {
		return searchfinyearDesc;
	}
	/**
	 * @param searchfinyearDesc The searchfinyearDesc to set.
	 */
	public void setSearchfinyearDesc(String searchfinyearDesc) {
		this.searchfinyearDesc = searchfinyearDesc;
	}
	/**
	 * @return Returns the searchfinyearName.
	 */
	public String getSearchfinyearName() {
		return searchfinyearName;
	}
	/**
	 * @param searchfinyearName The searchfinyearName to set.
	 */
	public void setSearchfinyearName(String searchfinyearName) {
		this.searchfinyearName = searchfinyearName;
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
	
	
	 
	

	/**
	 * @return Returns the searchfromDate.
	 */
	public String getSearchfromDate() {
		return searchfromDate;
	}
	/**
	 * @param searchfromDate The searchfromDate to set.
	 */
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	/**
	 * @return Returns the searchtoDate.
	 */
	public String getSearchtoDate() {
		return searchtoDate;
	}
	/**
	 * @param searchtoDate The searchtoDate to set.
	 */
	public void setSearchtoDate(String searchtoDate) {
		this.searchtoDate = searchtoDate;
	}
}

