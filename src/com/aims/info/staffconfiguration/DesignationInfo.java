package com.aims.info.staffconfiguration;

import java.io.Serializable;

public class DesignationInfo implements Serializable {
	Integer designationCd=null;
	String designatioNm="";
	String designationDesc="";
	String status="";
	String cadreCd="";
	String cadreNm="";
	String desgCode = "";
	String usercd="";
	
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	public String getCadreCd() {
		return cadreCd;
	}
	public void setCadreCd(String cadreCd) {
		this.cadreCd = cadreCd;
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
	public Integer getDesignationCd() {
		return designationCd;
	}
	public void setDesignationCd(Integer designationCd) {
		this.designationCd = designationCd;
	}
	public String getDesignationDesc() {
		return designationDesc;
	}
	public void setDesignationDesc(String designationDesc) {
		this.designationDesc = designationDesc;
	}
	public String getDesignatioNm() {
		return designatioNm;
	}
	public void setDesignatioNm(String designatioNm) {
		this.designatioNm = designatioNm;
	}
	public String getCadreNm() {
		return cadreNm;
	}
	public void setCadreNm(String cadreNm) {
		this.cadreNm = cadreNm;
	}
	

	/**
	 * @return Returns the desgCode.
	 */
	public String getDesgCode() {
		return desgCode;
	}
	/**
	 * @param desgCode The desgCode to set.
	 */
	public void setDesgCode(String desgCode) {
		this.desgCode = desgCode;
	}
}
