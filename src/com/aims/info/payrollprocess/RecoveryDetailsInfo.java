/*
 * Created on Sep 21, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.payrollprocess;

/**
 * @author subbaraod
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecoveryDetailsInfo {
	
	private String grantedAmt;
	private String recoveredAmt;
	private String totInst;
	private String currentInst;
	private String advName;
    private String emi;
    
    private String balAmount = "";
    
    

	/**
	 * @return Returns the balAmount.
	 */
	public String getBalAmount() {
		return balAmount;
	}
	/**
	 * @param balAmount The balAmount to set.
	 */
	public void setBalAmount(String balAmount) {
		this.balAmount = balAmount;
	}
	/**
	 * @return Returns the emi.
	 */
	public String getEmi() {
		return emi;
	}
	/**
	 * @param emi The emi to set.
	 */
	public void setEmi(String emi) {
		this.emi = emi;
	}
	/**
	 * @return Returns the currentInst.
	 */
	public String getCurrentInst() {
		return currentInst;
	}
	/**
	 * @param currentInst The currentInst to set.
	 */
	public void setCurrentInst(String currentInst) {
		this.currentInst = currentInst;
	}
	/**
	 * @return Returns the grantedAmt.
	 */
	public String getGrantedAmt() {
		return grantedAmt;
	}
	/**
	 * @param grantedAmt The grantedAmt to set.
	 */
	public void setGrantedAmt(String grantedAmt) {
		this.grantedAmt = grantedAmt;
	}
	/**
	 * @return Returns the recoveredAmt.
	 */
	public String getRecoveredAmt() {
		return recoveredAmt;
	}
	/**
	 * @param recoveredAmt The recoveredAmt to set.
	 */
	public void setRecoveredAmt(String recoveredAmt) {
		this.recoveredAmt = recoveredAmt;
	}
	/**
	 * @return Returns the totInst.
	 */
	public String getTotInst() {
		return totInst;
	}
	/**
	 * @param totInst The totInst to set.
	 */
	public void setTotInst(String totInst) {
		this.totInst = totInst;
	}
	
	/**
	 * @return Returns the advName.
	 */
	public String getAdvName() {
		return advName;
	}
	/**
	 * @param advName The advName to set.
	 */
	public void setAdvName(String advName) {
		this.advName = advName;
	}
}
