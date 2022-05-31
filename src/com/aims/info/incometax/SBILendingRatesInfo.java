/*
 * Created on Nov 19, 2009
 */
package com.aims.info.incometax;

/**
 * @author Srilakshmi E
 */
public class SBILendingRatesInfo {
	private String sbiratecd;
	private String advcd;
	private String fyearcd;
	private String status;
	private String sbiratedetcd;
	private String fromyear;
	private String toyear;
	private String fromamount;
	private String toamount;
	private String taxpercent;
	private String[] taxpercentdetails;
	
		
	/**
	 * @return Returns the advcd.
	 */
	public String getAdvcd() {
		return advcd;
	}
	/**
	 * @param advcd The advcd to set.
	 */
	public void setAdvcd(String advcd) {
		this.advcd = advcd;
	}
	/**
	 * @return Returns the fromamount.
	 */
	public String getFromamount() {
		return fromamount;
	}
	/**
	 * @param fromamount The fromamount to set.
	 */
	public void setFromamount(String fromamount) {
		this.fromamount = fromamount;
	}
	/**
	 * @return Returns the fromyear.
	 */
	public String getFromyear() {
		return fromyear;
	}
	/**
	 * @param fromyear The fromyear to set.
	 */
	public void setFromyear(String fromyear) {
		this.fromyear = fromyear;
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
	 * @return Returns the sbiratecd.
	 */
	public String getSbiratecd() {
		return sbiratecd;
	}
	/**
	 * @param sbiratecd The sbiratecd to set.
	 */
	public void setSbiratecd(String sbiratecd) {
		this.sbiratecd = sbiratecd;
	}
	/**
	 * @return Returns the sbiratedetcd.
	 */
	public String getSbiratedetcd() {
		return sbiratedetcd;
	}
	/**
	 * @param sbiratedetcd The sbiratedetcd to set.
	 */
	public void setSbiratedetcd(String sbiratedetcd) {
		this.sbiratedetcd = sbiratedetcd;
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
	 * @return Returns the taxpercent.
	 */
	public String getTaxpercent() {
		return taxpercent;
	}
	/**
	 * @param taxpercent The taxpercent to set.
	 */
	public void setTaxpercent(String taxpercent) {
		this.taxpercent = taxpercent;
	}
	/**
	 * @return Returns the toamount.
	 */
	public String getToamount() {
		return toamount;
	}
	/**
	 * @param toamount The toamount to set.
	 */
	public void setToamount(String toamount) {
		this.toamount = toamount;
	}
	/**
	 * @return Returns the toyear.
	 */
	public String getToyear() {
		return toyear;
	}
	/**
	 * @param toyear The toyear to set.
	 */
	public void setToyear(String toyear) {
		this.toyear = toyear;
	}
	/**
	 * @return Returns the taxpercentdetails.
	 */
	public String[] getTaxpercentdetails() {
		return taxpercentdetails;
	}
	/**
	 * @param taxpercentdetails The taxpercentdetails to set.
	 */
	public void setTaxpercentdetails(String[] taxpercentdetails) {
		this.taxpercentdetails = taxpercentdetails;
	}
}
