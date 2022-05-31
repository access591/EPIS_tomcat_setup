package com.aims.info.accountsetup;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchLedgerInfo implements Serializable{

	String searchledgercd = "";
	String searchledgernm = "";
	String searchledgerdesc = "";
	String searchopbalance = "";
	String searchbalancetype = "";
	String searchgroupacccd = "";
	String searchgroupaccnm = "";
	String searchacctypecd = "";
	String searchacctypenm = "";
	String status = "";
	String append;
	/**
	 * @return Returns the searchacctypecd.
	 */
	public String getSearchacctypecd() {
		return searchacctypecd;
	}
	/**
	 * @param searchacctypecd The searchacctypecd to set.
	 */
	public void setSearchacctypecd(String searchacctypecd) {
		this.searchacctypecd = searchacctypecd;
	}
	/**
	 * @return Returns the searchacctypenm.
	 */
	public String getSearchacctypenm() {
		return searchacctypenm;
	}
	/**
	 * @param searchacctypenm The searchacctypenm to set.
	 */
	public void setSearchacctypenm(String searchacctypenm) {
		this.searchacctypenm = searchacctypenm;
	}
	/**
	 * @return Returns the searchbalancetype.
	 */
	public String getSearchbalancetype() {
		return searchbalancetype;
	}
	/**
	 * @param searchbalancetype The searchbalancetype to set.
	 */
	public void setSearchbalancetype(String searchbalancetype) {
		this.searchbalancetype = searchbalancetype;
	}
	/**
	 * @return Returns the searchgroupacccd.
	 */
	public String getSearchgroupacccd() {
		return searchgroupacccd;
	}
	/**
	 * @param searchgroupacccd The searchgroupacccd to set.
	 */
	public void setSearchgroupacccd(String searchgroupacccd) {
		this.searchgroupacccd = searchgroupacccd;
	}
	/**
	 * @return Returns the searchgroupaccnm.
	 */
	public String getSearchgroupaccnm() {
		return searchgroupaccnm;
	}
	/**
	 * @param searchgroupaccnm The searchgroupaccnm to set.
	 */
	public void setSearchgroupaccnm(String searchgroupaccnm) {
		this.searchgroupaccnm = searchgroupaccnm;
	}
	/**
	 * @return Returns the searchledgercd.
	 */
	public String getSearchledgercd() {
		return searchledgercd;
	}
	/**
	 * @param searchledgercd The searchledgercd to set.
	 */
	public void setSearchledgercd(String searchledgercd) {
		this.searchledgercd = searchledgercd;
	}
	/**
	 * @return Returns the searchledgerdesc.
	 */
	public String getSearchledgerdesc() {
		return searchledgerdesc;
	}
	/**
	 * @param searchledgerdesc The searchledgerdesc to set.
	 */
	public void setSearchledgerdesc(String searchledgerdesc) {
		this.searchledgerdesc = searchledgerdesc;
	}
	/**
	 * @return Returns the searchledgernm.
	 */
	public String getSearchledgernm() {
		return searchledgernm;
	}
	/**
	 * @param searchledgernm The searchledgernm to set.
	 */
	public void setSearchledgernm(String searchledgernm) {
		this.searchledgernm = searchledgernm;
	}
	/**
	 * @return Returns the searchopbalance.
	 */
	public String getSearchopbalance() {
		return searchopbalance;
	}
	/**
	 * @param searchopbalance The searchopbalance to set.
	 */
	public void setSearchopbalance(String searchopbalance) {
		this.searchopbalance = searchopbalance;
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

	public String getAppend() {
		return (searchledgercd + " - " +searchledgernm );
	}


}
