/**
 * File       : AdvancesRuleInfo.java
 * Date       : 29/07/2009
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */

package com.aims.info.staffconfiguration;

import java.io.Serializable;

/**
 * @author Srilakshmi E
 *
 */
public class AdvancesRuleInfo implements Serializable {
	
	private String advRuleName;
	private String advRuleCd;
	private String advCd;
	private String status;
	private String stfCtgry;
	private String stfCadre;
	private String minSrvcPrd;
	private String basicSal;
	private String amount;
	private String percent;
	private String recvOfInrst;
	private String noofInstlmnts;
	private String noofadvPyble;
	private String excdLoanAmt;
	private String excdLoanIntrst;
	private String usercd;
	
	/**
	 * @return Returns the usercd.
	 */
	public String getUsercd() {
		return usercd;
	}
	/**
	 * @param usercd The usercd to set.
	 */
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	/**
	 * @return Returns the advRuleCd.
	 */
	public String getAdvRuleCd() {
		return advRuleCd;
	}
	/**
	 * @param advRuleCd The advRuleCd to set.
	 */
	public void setAdvRuleCd(String advRuleCd) {
		this.advRuleCd = advRuleCd;
	}
	/**
	 * @return Returns the advRuleName.
	 */
	public String getAdvRuleName() {
		return advRuleName;
	}
	/**
	 * @param advRuleName The advRuleName to set.
	 */
	public void setAdvRuleName(String advRuleName) {
		this.advRuleName = advRuleName;
	}
	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the basicSal.
	 */
	public String getBasicSal() {
		return basicSal;
	}
	/**
	 * @param basicSal The basicSal to set.
	 */
	public void setBasicSal(String basicSal) {
		this.basicSal = basicSal;
	}
	/**
	 * @return Returns the excdLoanAmt.
	 */
	public String getExcdLoanAmt() {
		return excdLoanAmt;
	}
	/**
	 * @param excdLoanAmt The excdLoanAmt to set.
	 */
	public void setExcdLoanAmt(String excdLoanAmt) {
		this.excdLoanAmt = excdLoanAmt;
	}
	/**
	 * @return Returns the excdLoanIntrst.
	 */
	public String getExcdLoanIntrst() {
		return excdLoanIntrst;
	}
	/**
	 * @param excdLoanIntrst The excdLoanIntrst to set.
	 */
	public void setExcdLoanIntrst(String excdLoanIntrst) {
		this.excdLoanIntrst = excdLoanIntrst;
	}
	/**
	 * @return Returns the minSrvcPrd.
	 */
	public String getMinSrvcPrd() {
		return minSrvcPrd;
	}
	/**
	 * @param minSrvcPrd The minSrvcPrd to set.
	 */
	public void setMinSrvcPrd(String minSrvcPrd) {
		this.minSrvcPrd = minSrvcPrd;
	}
	/**
	 * @return Returns the noofadvPyble.
	 */
	public String getNoofadvPyble() {
		return noofadvPyble;
	}
	/**
	 * @param noofadvPyble The noofadvPyble to set.
	 */
	public void setNoofadvPyble(String noofadvPyble) {
		this.noofadvPyble = noofadvPyble;
	}
	/**
	 * @return Returns the noofInstlmnts.
	 */
	public String getNoofInstlmnts() {
		return noofInstlmnts;
	}
	/**
	 * @param noofInstlmnts The noofInstlmnts to set.
	 */
	public void setNoofInstlmnts(String noofInstlmnts) {
		this.noofInstlmnts = noofInstlmnts;
	}
	/**
	 * @return Returns the percent.
	 */
	public String getPercent() {
		return percent;
	}
	/**
	 * @param percent The percent to set.
	 */
	public void setPercent(String percent) {
		this.percent = percent;
	}
	/**
	 * @return Returns the recvOfInrst.
	 */
	public String getRecvOfInrst() {
		return recvOfInrst;
	}
	/**
	 * @param recvOfInrst The recvOfInrst to set.
	 */
	public void setRecvOfInrst(String recvOfInrst) {
		this.recvOfInrst = recvOfInrst;
	}
	/**
	 * @return Returns the stfCtgry.
	 */
	public String getStfCtgry() {
		return stfCtgry;
	}
	/**
	 * @param stfCtgry The stfCtgry to set.
	 */
	public void setStfCtgry(String stfCtgry) {
		this.stfCtgry = stfCtgry;
	}
	/**
	 * @return Returns the stfCadre.
	 */
	public String getStfCadre() {
		return stfCadre;
	}
	/**
	 * @param stfCadre The stfCadre to set.
	 */
	public void setStfCadre(String stfCadre) {
		this.stfCadre = stfCadre;
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
	 * @return Returns the advCd.
	 */
	public String getAdvCd() {
		return advCd;
	}
	/**
	 * @param advCd The advCd to set.
	 */
	public void setAdvCd(String advCd) {
		this.advCd = advCd;
	}
}
