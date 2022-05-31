package com.aims.info.staffconfiguration;

import java.util.List;

public class EmpElectricityInfo {	
	
	private String empelectricitycd;
	private String empno;
	private String emplnumber;
	private String empname;
	private String fyearcd;
	private String payMonthId;
	private String fromMonthId;
	private  String toMonthId;	
	private String recordId;
	private double unitsFrom;
	private double unitsTo;
	private double unitsConsumed;
	private double chargeAmt;
	private double unitRate;
	private double meterRent;
	private double extraCharge;
	private String type;
	private String userCd;
	private String station;
	private String staffCategoryCd;
	private String staffCategoryName;
	private List employeeList;
	private String[] payDetails; 
	private String amount;
	private String rate;
	private String transactionnewid;
	public String getTransactionnewid() {
		return transactionnewid;
	}
	public void setTransactionnewid(String transactionnewid) {
		this.transactionnewid = transactionnewid;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public List getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getEmpElectricityCd() {
		return empelectricitycd;
	}
	public void setEmpElectricityCd(String empelectricitycd) {
		this.empelectricitycd = empelectricitycd;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	
	/**
	 * @return Returns the emplnumber.
	 */
	public String getEmplnumber() {
		return emplnumber;
	}
	/**
	 * @param emplnumber The emplnumber to set.
	 */
	public void setEmplnumber(String emplnumber) {
		this.emplnumber = emplnumber;
	}
	public String getFyearcd() {
		return fyearcd;
	}
	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}
	public String getFromMonthId() {
		return fromMonthId;
	}
	public void setFromMonthId(String fromMonthId) {
		this.fromMonthId = fromMonthId;
	}
	
	public String getToMonthId() {
		return toMonthId;
	}
	public void setToMonthId(String toMonthId) {
		this.toMonthId = toMonthId;
	}
	public String getPayMonthId() {
		return payMonthId;
	}
	public void setPayMonthId(String payMonthId) {
		this.payMonthId = payMonthId;
	}
	public double getUnitsFrom() {
		return unitsFrom;
	}
	public void setUnitsFrom(double unitsFrom) {
		this.unitsFrom = unitsFrom;
	}
	
	public double getUnitsTo() {
		return unitsTo;
	}
	public void setUnitsTo(double unitsTo) {
		this.unitsTo = unitsTo;
	}
	
	public double getUnitsConsumed() {
		return unitsConsumed;
	}
	public void setUnitsConsumed(double unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}
	
	public double getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(double unitRate) {
		this.unitRate = unitRate;
	}
	
	public double getMeterRent() {
		return meterRent;
	}
	public void setMeterRent(double meterRent) {
		this.meterRent = meterRent;
	}
	
	public double getExtraCharge() {
		return extraCharge;
	}
	public void setExtraCharge(double extraCharge) {
		this.extraCharge = extraCharge;
	}
	
	public double getChargeAmt() {
		return chargeAmt;
	}
	public void setChargeAmt(double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}
	public String getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(String staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	public String getStaffCategoryName() {
		return staffCategoryName;
	}
	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
	}
	
	public String[] getPayDetails() {
		return payDetails;
	}
	/**
	 * @param payDetails The payDetails to set.
	 */
	public void setPayDetails(String[] payDetails) {
		this.payDetails = payDetails;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
