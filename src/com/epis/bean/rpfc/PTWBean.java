package com.epis.bean.rpfc;

import java.io.Serializable;

public class PTWBean implements Serializable{
String ptwPurpose="",ptwDate="",ptwAmount="",remarks="";
String partAmount="";
int advPaid=0;



public int getAdvPaid() {
	return advPaid;
}

public void setAdvPaid(int advPaid) {
	this.advPaid = advPaid;
}

public String getPartAmount() {
	return partAmount;
}

public void setPartAmount(String partAmount) {
	this.partAmount = partAmount;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getPtwAmount() {
	return ptwAmount;
}

public void setPtwAmount(String ptwAmount) {
	this.ptwAmount = ptwAmount;
}

public String getPtwDate() {
	return ptwDate;
}

public void setPtwDate(String ptwDate) {
	this.ptwDate = ptwDate;
}

public String getPtwPurpose() {
	return ptwPurpose;
}

public void setPtwPurpose(String ptwPurpose) {
	this.ptwPurpose = ptwPurpose;
}
}
