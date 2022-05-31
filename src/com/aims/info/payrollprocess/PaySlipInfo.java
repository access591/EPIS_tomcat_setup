/*
 * Created on Jul 14, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.info.payrollprocess;

import java.io.Serializable;

/**
 * @author srilakshmie
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaySlipInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3211163668669791835L;
	private String earnDeduCd;
	private String earnDeduName;
	private String amount;
	private String arrAmount;
	private String recAmount;
	private String earnamount;
	private String empNo;
	private String emplnumber;
	private String empName;
	private String empDesg;
	private String month;
	private String year;
	private String empAllFlag;
	private String type;
	private String shortName;
	private String station;
	private String discipline;
	private String staffCategory;
	private String payrollmonthid;
	private String paystopid;
	private String fyearcd;
	private String emppaystopcd;
	private String adjustments;
	private String paybillcd = "";
	private String paybill = "";
	private String projectedAmount = "";
	private String actualAmount = "";
	private String empsaladjcd;
    private String payrollMonthName="";
    private String adjustmentsArr;
    private String adjustmentsRec;
    private String mode = "";
    
    private String aprAmount = "";
    private String mayAmount = "";
    private String junAmount = "";
    private String julAmount = "";
    private String augAmount = "";
    private String sepAmount = "";
    private String octAmount = "";
    private String novAmount = "";
    private String decAmount = "";
    private String janAmount = "";
    private String febAmount = "";
    private String marAmount = "";
    
    private double aprEarnAmount = 0.0;
    private double mayEarnAmount = 0.0;
    private double junEarnAmount = 0.0;
    private double julEarnAmount = 0.0;
    private double augEarnAmount = 0.0;
    private double sepEarnAmount = 0.0;
    private double octEarnAmount = 0.0;
    private double novEarnAmount = 0.0;
    private double decEarnAmount = 0.0;
    private double janEarnAmount = 0.0;
    private double febEarnAmount = 0.0;
    private double marEarnAmount = 0.0;
    
    private double aprDeduAmount = 0.0;
    private double mayDeduAmount = 0.0;
    private double junDeduAmount = 0.0;
    private double julDeduAmount = 0.0;
    private double augDeduAmount = 0.0;
    private double sepDeduAmount = 0.0;
    private double octDeduAmount = 0.0;
    private double novDeduAmount = 0.0;
    private double decDeduAmount = 0.0;
    private double janDeduAmount = 0.0;
    private double febDeduAmount = 0.0;
    private double marDeduAmount = 0.0;
    
    private double aprAdvAmount = 0.0;
    private double mayAdvAmount = 0.0;
    private double junAdvAmount = 0.0;
    private double julAdvAmount = 0.0;
    private double augAdvAmount = 0.0;
    private double sepAdvAmount = 0.0;
    private double octAdvAmount = 0.0;
    private double novAdvAmount = 0.0;
    private double decAdvAmount = 0.0;
    private double janAdvAmount = 0.0;
    private double febAdvAmount = 0.0;
    private double marAdvAmount = 0.0;
    
    private double aprNetAmount = 0.0;
    private double mayNetAmount = 0.0;
    private double junNetAmount = 0.0;
    private double julNetAmount = 0.0;
    private double augNetAmount = 0.0;
    private double sepNetAmount = 0.0;
    private double octNetAmount = 0.0;
    private double novNetAmount = 0.0;
    private double decNetAmount = 0.0;
    private double janNetAmount = 0.0;
    private double febNetAmount = 0.0;
    private double marNetAmount = 0.0;
    
    private String advcd = "";
    private String advname = "";
    private double loanAmount = 0.0;
    private double loanRecAmount = 0.0;
    private double balAmount = 0.0;
    
    private String suppDate = "";
    private String suppAccount = "";
    private String suppDesc = "";
    private double suppDrAmount = 0.0;
    private double suppCrAmount = 0.0;
    private double suppDrTotal = 0.0;
    private double suppCrTotal = 0.0;
	
	public String getAdjustmentsArr() {
		return adjustmentsArr;
	}
	public void setAdjustmentsArr(String adjustmentsArr) {
		this.adjustmentsArr = adjustmentsArr;
	}
	public String getAdjustmentsRec() {
		return adjustmentsRec;
	}
	public void setAdjustmentsRec(String adjustmentsRec) {
		this.adjustmentsRec = adjustmentsRec;
	}
	/**
	 * @return Returns the emplnumber.
	 */
	public String getEmplnumber() {
		return emplnumber;
	}
	/**
	 * @param empsaladjcd The emplnumber to set.
	 */
	public void setEmplnumber(String emplnumber) {
		this.emplnumber = emplnumber;
	}
	
	/**
	 * @return Returns the empsaladjcd.
	 */
	public String getEmpsaladjcd() {
		return empsaladjcd;
	}
	/**
	 * @param empsaladjcd The empsaladjcd to set.
	 */
	public void setEmpsaladjcd(String empsaladjcd) {
		this.empsaladjcd = empsaladjcd;
	}
	
	
	/**
	 * @return Returns the actualAmount.
	 */
	public String getActualAmount() {
		return actualAmount;
	}
	/**
	 * @param actualAmount The actualAmount to set.
	 */
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	/**
	 * @return Returns the projectedAmount.
	 */
	public String getProjectedAmount() {
		return projectedAmount;
	}
	/**
	 * @param projectedAmount The projectedAmount to set.
	 */
	public void setProjectedAmount(String projectedAmount) {
		this.projectedAmount = projectedAmount;
	}
	/**
	 * @return Returns the paybill.
	 */
	public String getPaybill() {
		return paybill;
	}
	/**
	 * @param paybill The paybill to set.
	 */
	public void setPaybill(String paybill) {
		this.paybill = paybill;
	}
	/**
	 * @return Returns the paybillcd.
	 */
	public String getPaybillcd() {
		return paybillcd;
	}
	/**
	 * @param paybillcd The paybillcd to set.
	 */
	public void setPaybillcd(String paybillcd) {
		this.paybillcd = paybillcd;
	}
	/**
	 * @return Returns the adjustments.
	 */
	public String getAdjustments() {
		return adjustments;
	}
	/**
	 * @param adjustments The adjustments to set.
	 */
	public void setAdjustments(String adjustments) {
		this.adjustments = adjustments;
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
	 * @return Returns the paystopid.
	 */
	public String getPaystopid() {
		return paystopid;
	}
	/**
	 * @param paystopid The paystopid to set.
	 */
	public void setPaystopid(String paystopid) {
		this.paystopid = paystopid;
	}
	/**
	 * @return Returns the payrollmonthid.
	 */
	public String getPayrollmonthid() {
		return payrollmonthid;
	}
	/**
	 * @param payrollmonthid The payrollmonthid to set.
	 */
	public void setPayrollmonthid(String payrollmonthid) {
		this.payrollmonthid = payrollmonthid;
	}
	/**
	 * @return Returns the discipline.
	 */
	public String getDiscipline() {
		return discipline;
	}
	/**
	 * @param discipline The discipline to set.
	 */
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	/**
	 * @return Returns the staffCategory.
	 */
	public String getStaffCategory() {
		return staffCategory;
	}
	/**
	 * @param staffCategory The staffCategory to set.
	 */
	public void setStaffCategory(String staffCategory) {
		this.staffCategory = staffCategory;
	}
	/**
	 * @return Returns the station.
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station The station to set.
	 */
	public void setStation(String station) {
		this.station = station;
	}
	private String paytransid;
	/**
	 * @return Returns the shortName.
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName The shortName to set.
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	/**
	 * @return Returns the empAllFlag.
	 */
	public String getEmpAllFlag() {
		return empAllFlag;
	}
	/**
	 * @param empAllFlag The empAllFlag to set.
	 */
	public void setEmpAllFlag(String empAllFlag) {
		this.empAllFlag = empAllFlag;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
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
	 * @return Returns the earnDeduCd.
	 */
	public String getEarnDeduCd() {
		return earnDeduCd;
	}
	/**
	 * @param earnDeduCd The earnDeduCd to set.
	 */
	public void setEarnDeduCd(String earnDeduCd) {
		this.earnDeduCd = earnDeduCd;
	}
	/**
	 * @return Returns the earnDeduName.
	 */
	public String getEarnDeduName() {
		return earnDeduName;
	}
	/**
	 * @param earnDeduName The earnDeduName to set.
	 */
	public void setEarnDeduName(String earnDeduName) {
		this.earnDeduName = earnDeduName;
	}
	/**
	 * @return Returns the empDesg.
	 */
	public String getEmpDesg() {
		return empDesg;
	}
	/**
	 * @param empDesg The empDesg to set.
	 */
	public void setEmpDesg(String empDesg) {
		this.empDesg = empDesg;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return Returns the empNo.
	 */
	public String getEmpNo() {
		return empNo;
	}
	/**
	 * @param empNo The empNo to set.
	 */
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
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
	 * @return Returns the emppaystopcd.
	 */
	public String getEmppaystopcd() {
		return emppaystopcd;
	}
	/**
	 * @param emppaystopcd The emppaystopcd to set.
	 */
	public void setEmppaystopcd(String emppaystopcd) {
		this.emppaystopcd = emppaystopcd;
	}
	public String getEarnamount() {
		return earnamount;
	}
	public void setEarnamount(String earnamount) {
		this.earnamount = earnamount;
	}
	/**
	 * @return Returns the payrollMonthName.
	 */
	public String getPayrollMonthName() {
		return payrollMonthName;
	}
	/**
	 * @param payrollMonthName The payrollMonthName to set.
	 */
	public void setPayrollMonthName(String payrollMonthName) {
		this.payrollMonthName = payrollMonthName;
	}
	public String getArrAmount() {
		return arrAmount;
	}
	public void setArrAmount(String arrAmount) {
		this.arrAmount = arrAmount;
	}
	public String getRecAmount() {
		return recAmount;
	}
	public void setRecAmount(String recAmount) {
		this.recAmount = recAmount;
	}
	public String getAprAmount() {
		return aprAmount;
	}
	public void setAprAmount(String aprAmount) {
		this.aprAmount = aprAmount;
	}
	public String getAugAmount() {
		return augAmount;
	}
	public void setAugAmount(String augAmount) {
		this.augAmount = augAmount;
	}
	public String getDecAmount() {
		return decAmount;
	}
	public void setDecAmount(String decAmount) {
		this.decAmount = decAmount;
	}
	public String getFebAmount() {
		return febAmount;
	}
	public void setFebAmount(String febAmount) {
		this.febAmount = febAmount;
	}
	public String getJanAmount() {
		return janAmount;
	}
	public void setJanAmount(String janAmount) {
		this.janAmount = janAmount;
	}
	public String getJulAmount() {
		return julAmount;
	}
	public void setJulAmount(String julAmount) {
		this.julAmount = julAmount;
	}
	public String getJunAmount() {
		return junAmount;
	}
	public void setJunAmount(String junAmount) {
		this.junAmount = junAmount;
	}
	public String getMarAmount() {
		return marAmount;
	}
	public void setMarAmount(String marAmount) {
		this.marAmount = marAmount;
	}
	public String getMayAmount() {
		return mayAmount;
	}
	public void setMayAmount(String mayAmount) {
		this.mayAmount = mayAmount;
	}
	public String getNovAmount() {
		return novAmount;
	}
	public void setNovAmount(String novAmount) {
		this.novAmount = novAmount;
	}
	public String getOctAmount() {
		return octAmount;
	}
	public void setOctAmount(String octAmount) {
		this.octAmount = octAmount;
	}
	public String getSepAmount() {
		return sepAmount;
	}
	public void setSepAmount(String sepAmount) {
		this.sepAmount = sepAmount;
	}
	public double getAprAdvAmount() {
		return aprAdvAmount;
	}
	public void setAprAdvAmount(double aprAdvAmount) {
		this.aprAdvAmount = aprAdvAmount;
	}
	public double getAprDeduAmount() {
		return aprDeduAmount;
	}
	public void setAprDeduAmount(double aprDeduAmount) {
		this.aprDeduAmount = aprDeduAmount;
	}
	public double getAprEarnAmount() {
		return aprEarnAmount;
	}
	public void setAprEarnAmount(double aprEarnAmount) {
		this.aprEarnAmount = aprEarnAmount;
	}
	public double getAugAdvAmount() {
		return augAdvAmount;
	}
	public void setAugAdvAmount(double augAdvAmount) {
		this.augAdvAmount = augAdvAmount;
	}
	public double getAugDeduAmount() {
		return augDeduAmount;
	}
	public void setAugDeduAmount(double augDeduAmount) {
		this.augDeduAmount = augDeduAmount;
	}
	public double getAugEarnAmount() {
		return augEarnAmount;
	}
	public void setAugEarnAmount(double augEarnAmount) {
		this.augEarnAmount = augEarnAmount;
	}
	public double getDecAdvAmount() {
		return decAdvAmount;
	}
	public void setDecAdvAmount(double decAdvAmount) {
		this.decAdvAmount = decAdvAmount;
	}
	public double getDecDeduAmount() {
		return decDeduAmount;
	}
	public void setDecDeduAmount(double decDeduAmount) {
		this.decDeduAmount = decDeduAmount;
	}
	public double getDecEarnAmount() {
		return decEarnAmount;
	}
	public void setDecEarnAmount(double decEarnAmount) {
		this.decEarnAmount = decEarnAmount;
	}
	public double getFebAdvAmount() {
		return febAdvAmount;
	}
	public void setFebAdvAmount(double febAdvAmount) {
		this.febAdvAmount = febAdvAmount;
	}
	public double getFebDeduAmount() {
		return febDeduAmount;
	}
	public void setFebDeduAmount(double febDeduAmount) {
		this.febDeduAmount = febDeduAmount;
	}
	public double getFebEarnAmount() {
		return febEarnAmount;
	}
	public void setFebEarnAmount(double febEarnAmount) {
		this.febEarnAmount = febEarnAmount;
	}
	public double getJanAdvAmount() {
		return janAdvAmount;
	}
	public void setJanAdvAmount(double janAdvAmount) {
		this.janAdvAmount = janAdvAmount;
	}
	public double getJanDeduAmount() {
		return janDeduAmount;
	}
	public void setJanDeduAmount(double janDeduAmount) {
		this.janDeduAmount = janDeduAmount;
	}
	public double getJanEarnAmount() {
		return janEarnAmount;
	}
	public void setJanEarnAmount(double janEarnAmount) {
		this.janEarnAmount = janEarnAmount;
	}
	public double getJulAdvAmount() {
		return julAdvAmount;
	}
	public void setJulAdvAmount(double julAdvAmount) {
		this.julAdvAmount = julAdvAmount;
	}
	public double getJulDeduAmount() {
		return julDeduAmount;
	}
	public void setJulDeduAmount(double julDeduAmount) {
		this.julDeduAmount = julDeduAmount;
	}
	public double getJulEarnAmount() {
		return julEarnAmount;
	}
	public void setJulEarnAmount(double julEarnAmount) {
		this.julEarnAmount = julEarnAmount;
	}
	public double getJunAdvAmount() {
		return junAdvAmount;
	}
	public void setJunAdvAmount(double junAdvAmount) {
		this.junAdvAmount = junAdvAmount;
	}
	public double getJunDeduAmount() {
		return junDeduAmount;
	}
	public void setJunDeduAmount(double junDeduAmount) {
		this.junDeduAmount = junDeduAmount;
	}
	public double getJunEarnAmount() {
		return junEarnAmount;
	}
	public void setJunEarnAmount(double junEarnAmount) {
		this.junEarnAmount = junEarnAmount;
	}
	public double getMarAdvAmount() {
		return marAdvAmount;
	}
	public void setMarAdvAmount(double marAdvAmount) {
		this.marAdvAmount = marAdvAmount;
	}
	public double getMarDeduAmount() {
		return marDeduAmount;
	}
	public void setMarDeduAmount(double marDeduAmount) {
		this.marDeduAmount = marDeduAmount;
	}
	public double getMarEarnAmount() {
		return marEarnAmount;
	}
	public void setMarEarnAmount(double marEarnAmount) {
		this.marEarnAmount = marEarnAmount;
	}
	public double getMayAdvAmount() {
		return mayAdvAmount;
	}
	public void setMayAdvAmount(double mayAdvAmount) {
		this.mayAdvAmount = mayAdvAmount;
	}
	public double getMayDeduAmount() {
		return mayDeduAmount;
	}
	public void setMayDeduAmount(double mayDeduAmount) {
		this.mayDeduAmount = mayDeduAmount;
	}
	public double getMayEarnAmount() {
		return mayEarnAmount;
	}
	public void setMayEarnAmount(double mayEarnAmount) {
		this.mayEarnAmount = mayEarnAmount;
	}
	public double getNovAdvAmount() {
		return novAdvAmount;
	}
	public void setNovAdvAmount(double novAdvAmount) {
		this.novAdvAmount = novAdvAmount;
	}
	public double getNovDeduAmount() {
		return novDeduAmount;
	}
	public void setNovDeduAmount(double novDeduAmount) {
		this.novDeduAmount = novDeduAmount;
	}
	public double getNovEarnAmount() {
		return novEarnAmount;
	}
	public void setNovEarnAmount(double novEarnAmount) {
		this.novEarnAmount = novEarnAmount;
	}
	public double getOctAdvAmount() {
		return octAdvAmount;
	}
	public void setOctAdvAmount(double octAdvAmount) {
		this.octAdvAmount = octAdvAmount;
	}
	public double getOctDeduAmount() {
		return octDeduAmount;
	}
	public void setOctDeduAmount(double octDeduAmount) {
		this.octDeduAmount = octDeduAmount;
	}
	public double getOctEarnAmount() {
		return octEarnAmount;
	}
	public void setOctEarnAmount(double octEarnAmount) {
		this.octEarnAmount = octEarnAmount;
	}
	public double getSepAdvAmount() {
		return sepAdvAmount;
	}
	public void setSepAdvAmount(double sepAdvAmount) {
		this.sepAdvAmount = sepAdvAmount;
	}
	public double getSepDeduAmount() {
		return sepDeduAmount;
	}
	public void setSepDeduAmount(double sepDeduAmount) {
		this.sepDeduAmount = sepDeduAmount;
	}
	public double getSepEarnAmount() {
		return sepEarnAmount;
	}
	public void setSepEarnAmount(double sepEarnAmount) {
		this.sepEarnAmount = sepEarnAmount;
	}
	public double getAprNetAmount() {
		return aprNetAmount;
	}
	public void setAprNetAmount(double aprNetAmount) {
		this.aprNetAmount = aprNetAmount;
	}
	public double getAugNetAmount() {
		return augNetAmount;
	}
	public void setAugNetAmount(double augNetAmount) {
		this.augNetAmount = augNetAmount;
	}
	public double getDecNetAmount() {
		return decNetAmount;
	}
	public void setDecNetAmount(double decNetAmount) {
		this.decNetAmount = decNetAmount;
	}
	public double getFebNetAmount() {
		return febNetAmount;
	}
	public void setFebNetAmount(double febNetAmount) {
		this.febNetAmount = febNetAmount;
	}
	public double getJanNetAmount() {
		return janNetAmount;
	}
	public void setJanNetAmount(double janNetAmount) {
		this.janNetAmount = janNetAmount;
	}
	public double getJulNetAmount() {
		return julNetAmount;
	}
	public void setJulNetAmount(double julNetAmount) {
		this.julNetAmount = julNetAmount;
	}
	public double getJunNetAmount() {
		return junNetAmount;
	}
	public void setJunNetAmount(double junNetAmount) {
		this.junNetAmount = junNetAmount;
	}
	public double getMarNetAmount() {
		return marNetAmount;
	}
	public void setMarNetAmount(double marNetAmount) {
		this.marNetAmount = marNetAmount;
	}
	public double getMayNetAmount() {
		return mayNetAmount;
	}
	public void setMayNetAmount(double mayNetAmount) {
		this.mayNetAmount = mayNetAmount;
	}
	public double getNovNetAmount() {
		return novNetAmount;
	}
	public void setNovNetAmount(double novNetAmount) {
		this.novNetAmount = novNetAmount;
	}
	public double getOctNetAmount() {
		return octNetAmount;
	}
	public void setOctNetAmount(double octNetAmount) {
		this.octNetAmount = octNetAmount;
	}
	public double getSepNetAmount() {
		return sepNetAmount;
	}
	public void setSepNetAmount(double sepNetAmount) {
		this.sepNetAmount = sepNetAmount;
	}
	public String getAdvcd() {
		return advcd;
	}
	public void setAdvcd(String advcd) {
		this.advcd = advcd;
	}
	public String getAdvname() {
		return advname;
	}
	public void setAdvname(String advname) {
		this.advname = advname;
	}
	public double getBalAmount() {
		return balAmount;
	}
	public void setBalAmount(double balAmount) {
		this.balAmount = balAmount;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getLoanRecAmount() {
		return loanRecAmount;
	}
	public void setLoanRecAmount(double loanRecAmount) {
		this.loanRecAmount = loanRecAmount;
	}
	public String getSuppAccount() {
		return suppAccount;
	}
	public void setSuppAccount(String suppAccount) {
		this.suppAccount = suppAccount;
	}
	public double getSuppDrAmount() {
		return suppDrAmount;
	}
	public void setSuppDrAmount(double suppDrAmount) {
		this.suppDrAmount = suppDrAmount;
	}
	public double getSuppCrAmount() {
		return suppCrAmount;
	}
	public void setSuppCrAmount(double suppCrAmount) {
		this.suppCrAmount = suppCrAmount;
	}
	public String getSuppDate() {
		return suppDate;
	}
	public void setSuppDate(String suppDate) {
		this.suppDate = suppDate;
	}
	public String getSuppDesc() {
		return suppDesc;
	}
	public void setSuppDesc(String suppDesc) {
		this.suppDesc = suppDesc;
	}
	public double getSuppDrTotal() {
		return suppDrTotal;
	}
	public void setSuppDrTotal(double suppDrTotal) {
		this.suppDrTotal = suppDrTotal;
	}
	public double getSuppCrTotal() {
		return suppCrTotal;
	}
	public void setSuppCrTotal(double suppCrTotal) {
		this.suppCrTotal = suppCrTotal;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
