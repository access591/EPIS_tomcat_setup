package com.aims.info.accountsetup;

import java.io.Serializable;
import java.util.ArrayList;

public class SubLedgerInfo implements Serializable{

	String subledgercd = "";
	String subledgernm = "";
	String subledgerdesc = "";
	double opbalance = 0.0;
	String balancetype = "";
	String ledgercd = "";
	String status = "";
	String accTypeCd="";
	String ledgername="";
	//ArrayList subledgerList = new ArrayList();
	
	
	/**
	 * @return Returns the accTypeCd.
	 */
	public String getAccTypeCd() {
		return accTypeCd;
	}
	/**
	 * @param accTypeCd The accTypeCd to set.
	 */
	public void setAccTypeCd(String accTypeCd) {
		this.accTypeCd = accTypeCd;
	}
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
	
	public String getSubledgercd() {
		return subledgercd;
	}
	public void setSubledgercd(String subledgercd) {
		this.subledgercd = subledgercd;
	}
	public String getSubledgerdesc() {
		return subledgerdesc;
	}
	public void setSubledgerdesc(String subledgerdesc) {
		this.subledgerdesc = subledgerdesc;
	}
	public String getSubledgernm() {
		return subledgernm;
	}
	public void setSubledgernm(String subledgernm) {
		this.subledgernm = subledgernm;
	}
	public double getOpbalance() {
		return opbalance;
	}
	public void setOpbalance(double opbalance) {
		this.opbalance = opbalance;
	}
	
	/**
	 * @return Returns the ledgername.
	 */
	public String getLedgername() {
		return ledgername;
	}
	/**
	 * @param ledgername The ledgername to set.
	 */
	public void setLedgername(String ledgername) {
		this.ledgername = ledgername;
	}
	
	
	

}
