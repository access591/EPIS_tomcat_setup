/*
 * Created on Jul 30, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.payrollprocess;

import java.io.Serializable;

/**
 * @author subbaraod
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PayProcessInfo implements Serializable{
  private String paytransid = "";
  private String ledgercd = "";
  private double amount = 0.0;
  private String EARNDEDUCD;
  private String earndednm = "";
  private String type;
/**
 * @return Returns the earndednm.
 */
public String getEarndednm() {
	return earndednm;
}
/**
 * @param earndednm The earndednm to set.
 */
public void setEarndednm(String earndednm) {
	this.earndednm = earndednm;
}
 
/**
 * @return Returns the amount.
 */
public double getAmount() {
	return amount;
}
/**
 * @param amount The amount to set.
 */
public void setAmount(double amount) {
	this.amount = amount;
}
/**
 * @return Returns the eARNDEDUCD.
 */
public String getEARNDEDUCD() {
	return EARNDEDUCD;
}
/**
 * @param earndeducd The eARNDEDUCD to set.
 */
public void setEARNDEDUCD(String earndeducd) {
	EARNDEDUCD = earndeducd;
}
/**
 * @return Returns the ledgercd.
 */
public String getLedgercd() {
	return ledgercd;
}
/**
 * @param ledgercd The ledgercd to set.
 */
public void setLedgercd(String ledgercd) {
	this.ledgercd = ledgercd;
}
/**
 * @return Returns the paytransid.
 */
public String getPaytransid() {
	return paytransid;
}
/**
 * @param paytransid The paytransid to set.
 */
public void setPaytransid(String paytransid) {
	this.paytransid = paytransid;
}
/**
 * @return Returns the type.
 */
public String getType() {
	return type;
}
/**
 * @param type The type to set.
 */
public void setType(String type) {
	this.type = type;
}
}
