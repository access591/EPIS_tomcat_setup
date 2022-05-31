package com.aims.info.staffconfiguration;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchDesignationInfo implements Serializable   
{
	 private String searchdesignationnm="";
	 private String searchdesignationcd="";
	 private String searchdesignationdesc="";
	 private String searchcadrenm="";
	 private String searchcadrecd="";
	 private String status="";
	 private String searchdesgcode = "";
	/**
	 * @return Returns the searchcadrecd.
	 */
	public String getSearchcadrecd() {
		return searchcadrecd;
	}
	/**
	 * @param searchcadrecd The searchcadrecd to set.
	 */
	public void setSearchcadrecd(String searchcadrecd) {
		this.searchcadrecd = searchcadrecd;
	}
	/**
	 * @return Returns the searchcadrenm.
	 */
	public String getSearchcadrenm() {
		return searchcadrenm;
	}
	/**
	 * @param searchcadrenm The searchcadrenm to set.
	 */
	public void setSearchcadrenm(String searchcadrenm) {
		this.searchcadrenm = searchcadrenm;
	}
	/**
	 * @return Returns the searchdesignationcd.
	 */
	public String getSearchdesignationcd() {
		return searchdesignationcd;
	}
	/**
	 * @param searchdesignationcd The searchdesignationcd to set.
	 */
	public void setSearchdesignationcd(String searchdesignationcd) {
		this.searchdesignationcd = searchdesignationcd;
	}
	/**
	 * @return Returns the searchdesignationdesc.
	 */
	public String getSearchdesignationdesc() {
		return searchdesignationdesc;
	}
	/**
	 * @param searchdesignationdesc The searchdesignationdesc to set.
	 */
	public void setSearchdesignationdesc(String searchdesignationdesc) {
		this.searchdesignationdesc = searchdesignationdesc;
	}
	/**
	 * @return Returns the searchdesignationnm.
	 */
	public String getSearchdesignationnm() {
		return searchdesignationnm;
	}
	/**
	 * @param searchdesignationnm The searchdesignationnm to set.
	 */
	public void setSearchdesignationnm(String searchdesignationnm) {
		this.searchdesignationnm = searchdesignationnm;
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
	 * @return Returns the searchdesgcode.
	 */
	public String getSearchdesgcode() {
		return searchdesgcode;
	}
	/**
	 * @param searchdesgcode The searchdesgcode to set.
	 */
	public void setSearchdesgcode(String searchdesgcode) {
		this.searchdesgcode = searchdesgcode;
	}
}
