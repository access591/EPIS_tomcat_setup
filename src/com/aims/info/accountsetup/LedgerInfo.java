package com.aims.info.accountsetup;

import java.io.Serializable;
import java.util.ArrayList;

public class LedgerInfo implements Serializable{

	String ledgercd = "";
	String ledgernm = "";
	String ledgerdesc = "";
	String opbalance="" ;
	String balancetype = "";	
	String status = "";
	String groupacccd="";
	String acctypecd="";
	String acctypenm="";
	
	String stationCd="";
	String stationName="";
	String usercd="";
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	ArrayList ledgerList = new ArrayList();
	

	public String getBalancetype() {
		return balancetype;
	}
	public void setBalancetype(String balancetype) {
		this.balancetype = balancetype;
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
	public String getLedgercd() {
		return ledgercd;
	}
	public void setLedgercd(String ledgercd) {
		this.ledgercd = ledgercd;
	}
	public String getLedgerdesc() {
		return ledgerdesc;
	}
	public void setLedgerdesc(String ledgerdesc) {
		this.ledgerdesc = ledgerdesc;
	}
	public String getLedgernm() {
		return ledgernm;
	}
	public void setLedgernm(String ledgernm) {
		this.ledgernm = ledgernm;
	}
	
	public ArrayList getLedgerList() {
		return ledgerList;
	}
	public void setLedgerList(ArrayList ledgerList) {
		this.ledgerList = ledgerList;
	}
	public void setOpbalance(String opbalance) {
		this.opbalance = opbalance;
	}
	public String getOpbalance() {
		return opbalance;
	}
	public String getStationCd() {
		return stationCd;
	}
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * @return Returns the groupacccd.
	 */
	public String getGroupacccd() {
		return groupacccd;
	}
	/**
	 * @param groupacccd The groupacccd to set.
	 */
	public void setGroupacccd(String groupacccd) {
		this.groupacccd = groupacccd;
	}
	/**
	 * @return Returns the acctypecd.
	 */
	public String getAcctypecd() {
		return acctypecd;
	}
	/**
	 * @param acctypecd The acctypecd to set.
	 */
	public void setAcctypecd(String acctypecd) {
		this.acctypecd = acctypecd;
	}
	/**
	 * @return Returns the acctypenm.
	 */
	public String getAcctypenm() {
		return acctypenm;
	}
	/**
	 * @param acctypenm The acctypenm to set.
	 */
	public void setAcctypenm(String acctypenm) {
		this.acctypenm = acctypenm;
	}
	
	

}
