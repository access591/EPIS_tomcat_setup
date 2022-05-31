package com.aims.info.payrollprocess;

public class PayrollProcessInfo {

	int paytransid ;
	String processingtype;
	String payrollmonthid;
	String claimtype;
	int staffctgrycd;
	int disciplinecd;
	String transstatus;
	
	String usernm;
	int financialyearcd;
	String processstatus;
	String deleteflag;
	String station;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getClaimtype() {
		return claimtype;
	}
	public void setClaimtype(String claimtype) {
		this.claimtype = claimtype;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public int getDisciplinecd() {
		return disciplinecd;
	}
	public void setDisciplinecd(int disciplinecd) {
		this.disciplinecd = disciplinecd;
	}
	public int getFinancialyearcd() {
		return financialyearcd;
	}
	public void setFinancialyearcd(int financialyearcd) {
		this.financialyearcd = financialyearcd;
	}
	public String getPayrollmonthid() {
		return payrollmonthid;
	}
	public void setPayrollmonthid(String payrollmonthid) {
		this.payrollmonthid = payrollmonthid;
	}
	
	public String getProcessstatus() {
		return processstatus;
	}
	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}
	public int getPaytransid() {
		return paytransid;
	}
	public void setPaytransid(int paytransid) {
		this.paytransid = paytransid;
	}
	public String getProcessingtype() {
		return processingtype;
	}
	public void setProcessingtype(String processingtype) {
		this.processingtype = processingtype;
	}
	public int getStaffctgrycd() {
		return staffctgrycd;
	}
	public void setStaffctgrycd(int staffctgrycd) {
		this.staffctgrycd = staffctgrycd;
	}
	public String getTransstatus() {
		return transstatus;
	}
	public void setTransstatus(String transstatus) {
		this.transstatus = transstatus;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	

}
