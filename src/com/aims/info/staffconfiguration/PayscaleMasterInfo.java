/**
  * File       : PayscaleMasterInfo.java
  * Date       : 09/13/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;

import java.util.ArrayList;

public class PayscaleMasterInfo  implements java.io.Serializable {

	private String paysclid;
	private String minimumScale;
    private String increment;
    private String nextScale;
    private String fyearcd;
    private String nextIncrement;
    private String maximumScale;
    private ArrayList cadreList;
    private ArrayList stafftypeList;
    private String payScale;
    private String staffCategoryCd;
    private String cadreCd;
    private String status;
	private String cadreName;
	private String usercd;
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	private String designationCd;
	private String designationNm;
	private String desgcode;
	
	
    
	/**
	 * @return Returns the desgcode.
	 */
	public String getDesgcode() {
		return desgcode;
	}
	/**
	 * @param desgcode The desgcode to set.
	 */
	public void setDesgcode(String desgcode) {
		this.desgcode = desgcode;
	}
	/**
	 * @return Returns the cadreName.
	 */
	public String getCadreName() {
		return cadreName;
	}
	/**
	 * @param cadreName The cadreName to set.
	 */
	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}
	
	/**
	 * @return Returns the cadreCd.
	 */
	public String getCadreCd() {
		return cadreCd;
	}
	/**
	 * @param cadreCd The cadreCd to set.
	 */
	public void setCadreCd(String cadreCd) {
		this.cadreCd = cadreCd;
	}
	/**
	 * @return Returns the cadreList.
	 */
	public ArrayList getCadreList() {
		return cadreList;
	}
	/**
	 * @param cadreList The cadreList to set.
	 */
	public void setCadreList(ArrayList cadreList) {
		this.cadreList = cadreList;
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
	 * @return Returns the fyearcd.
	 */
	public String getFyearcd() {
		return fyearcd;
	}
	/**
	 * @param fyearcd The fyearcd to set.
	 */
	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}
	
	/**
	 * @return Returns the increment.
	 */
	public String getIncrement() {
		return increment;
	}
	/**
	 * @param increment The increment to set.
	 */
	public void setIncrement(String increment) {
		this.increment = increment;
	}
	/**
	 * @return Returns the maximumScale.
	 */
	public String getMaximumScale() {
		return maximumScale;
	}
	/**
	 * @param maximumScale The maximumScale to set.
	 */
	public void setMaximumScale(String maximumScale) {
		this.maximumScale = maximumScale;
	}
	/**
	 * @return Returns the minimumScale.
	 */
	public String getMinimumScale() {
		return minimumScale;
	}
	/**
	 * @param minimumScale The minimumScale to set.
	 */
	public void setMinimumScale(String minimumScale) {
		this.minimumScale = minimumScale;
	}
	/**
	 * @return Returns the nextIncrement.
	 */
	public String getNextIncrement() {
		return nextIncrement;
	}
	/**
	 * @param nextIncrement The nextIncrement to set.
	 */
	public void setNextIncrement(String nextIncrement) {
		this.nextIncrement = nextIncrement;
	}
	/**
	 * @return Returns the nextScale.
	 */
	public String getNextScale() {
		return nextScale;
	}
	/**
	 * @param nextScale The nextScale to set.
	 */
	public void setNextScale(String nextScale) {
		this.nextScale = nextScale;
	}
	/**
	 * @return Returns the payScale.
	 */
	public String getPayScale() {
		return payScale;
	}
	/**
	 * @param payScale The payScale to set.
	 */
	public void setPayScale(String payScale) {
		this.payScale = payScale;
	}
	
	/**
	 * @return Returns the staffCategoryCd.
	 */
	public String getStaffCategoryCd() {
		return staffCategoryCd;
	}
	/**
	 * @param staffCategoryCd The staffCategoryCd to set.
	 */
	public void setStaffCategoryCd(String staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	/**
	 * @return Returns the stafftypeList.
	 */
	public ArrayList getStafftypeList() {
		return stafftypeList;
	}
	/**
	 * @param stafftypeList The stafftypeList to set.
	 */
	public void setStafftypeList(ArrayList stafftypeList) {
		this.stafftypeList = stafftypeList;
	}
	/**
	 * @return Returns the paysclid.
	 */
	public String getPaysclid() {
		return paysclid;
	}
	/**
	 * @param paysclid The paysclid to set.
	 */
	public void setPaysclid(String paysclid) {
		this.paysclid = paysclid;
	}
	
	/**
	 * @return Returns the designationCd.
	 */
	public String getDesignationCd() {
		return designationCd;
	}
	/**
	 * @param designationCd The designationCd to set.
	 */
	public void setDesignationCd(String designationCd) {
		this.designationCd = designationCd;
	}
	
	/**
	 * @return Returns the designationNm.
	 */
	public String getDesignationNm() {
		return designationNm;
	}
	/**
	 * @param designationNm The designationNm to set.
	 */
	public void setDesignationNm(String designationNm) {
		this.designationNm = designationNm;
	}
}