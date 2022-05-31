/**
 * File       : EmpPaySlipInfo.java
 * Date       : 29/07/2009
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */

package com.aims.info.payrollprocess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.aims.info.staffconfiguration.EmployeeInfo;

/**
 * @author Srilakshmi E
 */

public class EmpPaySlipInfo implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3197546188243049095L;
	private String empNo;
	private String month;
	private String year;
	private EmployeeInfo empInfo; 
	private List earnList;
	private List deducList;
	private List perkList;
	private List recList;
	private TreeSet earnListT;
	private TreeSet deducListT;
	private TreeSet perkListT;
	private TreeSet recListT;
	private double earnTotal = 0.0;
	private double deducTotal = 0.0;
	private double perkTotal = 0.0;
	private double recTotal = 0.0;
	private String grossAmt;
	private double deducAmt = 0.0;
	private double perkAmt = 0.0;
	private double netAmt  = 0.0;
	private double arrTotal = 0.0;
	private double recoTotal  = 0.0;
	
	private String strEarnTotal = "";
	
	private String payrollmonthid;
	private List rdinfoList;
	private String usercd;
	
	private String paystopcd;
	private String emppaystopcd;	
	private String empname;
	
	private String status;
	
	private double itrecamt;
	private int count;
	
	private double miscperkTotal = 0.0;
	private double miscperkAmt = 0.0;
	private List miscperkList;
	
	private double etot;
	private double adjustments;
	private List advList = new ArrayList();
	private double advtotal = 0;
	
	
	
	private String hpp = "";
	private String eol = "";
	private String uaa = "";
	private String netamtinwords = "";
	private String bankName = "";
	private String empsaladjcd;
	
	private Map earnList1;
	private Map deducList1;
	private Map perkList1;
	private Map recList1;
	private double pc =0.0;
	private double pf =0.0;
	
	
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
	 * @return Returns the bankName.
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName The bankName to set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return Returns the netamtinwords.
	 */
	public String getNetamtinwords() {
		return netamtinwords;
	}
	/**
	 * @param netamtinwords The netamtinwords to set.
	 */
	public void setNetamtinwords(String netamtinwords) {
		this.netamtinwords = netamtinwords;
	}
	/**
	 * @return Returns the eol.
	 */
	public String getEol() {
		return eol;
	}
	/**
	 * @param eol The eol to set.
	 */
	public void setEol(String eol) {
		this.eol = eol;
	}
	/**
	 * @return Returns the hpp.
	 */
	public String getHpp() {
		return hpp;
	}
	/**
	 * @param hpp The hpp to set.
	 */
	public void setHpp(String hpp) {
		this.hpp = hpp;
	}
	/**
	 * @return Returns the uaa.
	 */
	public String getUaa() {
		return uaa;
	}
	/**
	 * @param uaa The uaa to set.
	 */
	public void setUaa(String uaa) {
		this.uaa = uaa;
	}
	/**
	 * @return Returns the advtotal.
	 */
	public double getAdvtotal() {
		return advtotal;
	}
	/**
	 * @param advtotal The advtotal to set.
	 */
	public void setAdvtotal(double advtotal) {
		this.advtotal = advtotal;
	}
	/**
	 * @return Returns the advList.
	 */
	public List getAdvList() {
		return advList;
	}
	/**
	 * @param advList The advList to set.
	 */
	public void setAdvList(List advList) {
		this.advList = advList;
	}
	/**
	 * @return Returns the adjustments.
	 */
	public double getAdjustments() {
		return adjustments;
	}
	/**
	 * @param adjustments The adjustments to set.
	 */
	public void setAdjustments(double adjustments) {
		this.adjustments = adjustments;
	}
	/**
	 * @return Returns the etot.
	 */
	public double getEtot() {
		return etot;
	}
	/**
	 * @param etot The etot to set.
	 */
	public void setEtot(double etot) {
		this.etot = etot;
	}
	/**
	 * @return Returns the miscperkList.
	 */
	public List getMiscperkList() {
		return miscperkList;
	}
	/**
	 * @param miscperkList The miscperkList to set.
	 */
	public void setMiscperkList(List miscperkList) {
		this.miscperkList = miscperkList;
	}
	/**
	 * @return Returns the miscperkAmt.
	 */
	public double getMiscperkAmt() {
		return miscperkAmt;
	}
	/**
	 * @param miscperkAmt The miscperkAmt to set.
	 */
	public void setMiscperkAmt(double miscperkAmt) {
		this.miscperkAmt = miscperkAmt;
	}
	/**
	 * @return Returns the miscperkTotal.
	 */
	public double getMiscperkTotal() {
		return miscperkTotal;
	}
	/**
	 * @param miscperkTotal The miscperkTotal to set.
	 */
	public void setMiscperkTotal(double miscperkTotal) {
		this.miscperkTotal = miscperkTotal;
	}
	/**
	 * @return Returns the count.
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count The count to set.
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return Returns the usercd.
	 */
	public String getUsercd() {
		return usercd;
	}
	/**
	 * @param usercd The usercd to set.
	 */
	public void setUsercd(String usercd) {
		this.usercd = usercd;
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
	 * @return Returns the deducTotal.
	 */
	public double getDeducTotal() {
		return deducTotal;
	}
	/**
	 * @param deducTotal The deducTotal to set.
	 */
	public void setDeducTotal(double deducTotal) {
		this.deducTotal = deducTotal;
	}
	/**
	 * @return Returns the earnTotal.
	 */
	public double getEarnTotal() {
		return earnTotal;
	}
	/**
	 * @param earnTotal The earnTotal to set.
	 */
	public void setEarnTotal(double earnTotal) {
		this.earnTotal = earnTotal;
	}
	/**
	 * @return Returns the perkTotal.
	 */
	public double getPerkTotal() {
		return perkTotal;
	}
	/**
	 * @param perkTotal The perkTotal to set.
	 */
	public void setPerkTotal(double perkTotal) {
		this.perkTotal = perkTotal;
	}
	/**
	 * @return Returns the recTotal.
	 */
	public double getRecTotal() {
		return recTotal;
	}
	/**
	 * @param recTotal The recTotal to set.
	 */
	public void setRecTotal(double recTotal) {
		this.recTotal = recTotal;
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
	 * @return Returns the deducAmt.
	 */
	public double getDeducAmt() {
		return deducAmt;
	}
	/**
	 * @param deducAmt The deducAmt to set.
	 */
	public void setDeducAmt(double deducAmt) {
		this.deducAmt = deducAmt;
	}
	/**
	 * @return Returns the deducList.
	 */
	public List getDeducList() {
		return deducList;
	}
	/**
	 * @param deducList The deducList to set.
	 */
	public void setDeducList(List deducList) {
		this.deducList = deducList;
	}
	/**
	 * @return Returns the earnList.
	 */
	public List getEarnList() {
		return earnList;
	}
	/**
	 * @param earnList The earnList to set.
	 */
	public void setEarnList(List earnList) {
		this.earnList = earnList;
	}
	/**
	 * @return Returns the empInfo.
	 */
	public EmployeeInfo getEmpInfo() {
		return empInfo;
	}
	/**
	 * @param empInfo The empInfo to set.
	 */
	public void setEmpInfo(EmployeeInfo empInfo) {
		this.empInfo = empInfo;
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

	public String getGrossAmt() {
		return grossAmt;
	}
	public void setGrossAmt(String grossAmt) {
		this.grossAmt = grossAmt;
	}
	/**
	 * @return Returns the netAmt.
	 */
	public double getNetAmt() {
		return netAmt;
	}
	/**
	 * @param netAmt The netAmt to set.
	 */
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	
	
	
	/**
	 * @return Returns the rdinfoList.
	 */
	public List getRdinfoList() {
		return rdinfoList;
	}
	/**
	 * @param rdinfoList The rdinfoList to set.
	 */
	public void setRdinfoList(List rdinfoList) {
		this.rdinfoList = rdinfoList;
	}
	
	/**
	 * @return Returns the perkList.
	 */
	public List getPerkList() {
		return perkList;
	}
	/**
	 * @param perkList The perkList to set.
	 */
	public void setPerkList(List perkList) {
		this.perkList = perkList;
	}
	/**
	 * @return Returns the recList.
	 */
	public List getRecList() {
		return recList;
	}
	/**
	 * @param recList The recList to set.
	 */
	public void setRecList(List recList) {
		this.recList = recList;
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
	/**
	 * @return Returns the paystopcd.
	 */
	public String getPaystopcd() {
		return paystopcd;
	}
	/**
	 * @param paystopcd The paystopcd to set.
	 */
	public void setPaystopcd(String paystopcd) {
		this.paystopcd = paystopcd;
	}
	
	/**
	 * @return Returns the empname.
	 */
	public String getEmpname() {
		return empname;
	}
	/**
	 * @param empname The empname to set.
	 */
	public void setEmpname(String empname) {
		this.empname = empname;
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
	 * @return Returns the itrecamt.
	 */
	public double getItrecamt() {
		return itrecamt;
	}
	/**
	 * @param itrecamt The itrecamt to set.
	 */
	public void setItrecamt(double itrecamt) {
		this.itrecamt = itrecamt;
	}
	
	/**
	 * @return Returns the perkAmt.
	 */
	public double getPerkAmt() {
		return perkAmt;
	}
	/**
	 * @param perkAmt The perkAmt to set.
	 */
	public void setPerkAmt(double perkAmt) {
		this.perkAmt = perkAmt;
	}
	/**
	 * @return Returns the deducList1.
	 */
	public Map getDeducList1() {
		return deducList1;
	}
	/**
	 * @param deducList1 The deducList1 to set.
	 */
	public void setDeducList1(Map deducList1) {
		this.deducList1 = deducList1;
	}
	/**
	 * @return Returns the earnList1.
	 */
	public Map getEarnList1() {
		return earnList1;
	}
	/**
	 * @param earnList1 The earnList1 to set.
	 */
	public void setEarnList1(Map earnList1) {
		this.earnList1 = earnList1;
	}
	/**
	 * @return Returns the perkList1.
	 */
	public Map getPerkList1() {
		return perkList1;
	}
	/**
	 * @param perkList1 The perkList1 to set.
	 */
	public void setPerkList1(Map perkList1) {
		this.perkList1 = perkList1;
	}
	/**
	 * @return Returns the recList1.
	 */
	public Map getRecList1() {
		return recList1;
	}
	/**
	 * @param recList1 The recList1 to set.
	 */
	public void setRecList1(Map recList1) {
		this.recList1 = recList1;
	}
	/**
	 * @return Returns the deducListT.
	 */
	public TreeSet getDeducListT() {
		return deducListT;
	}
	/**
	 * @param deducListT The deducListT to set.
	 */
	public void setDeducListT(TreeSet deducListT) {
		this.deducListT = deducListT;
	}
	/**
	 * @return Returns the earnListT.
	 */
	public TreeSet getEarnListT() {
		return earnListT;
	}
	/**
	 * @param earnListT The earnListT to set.
	 */
	public void setEarnListT(TreeSet earnListT) {
		this.earnListT = earnListT;
	}
	/**
	 * @return Returns the perkListT.
	 */
	public TreeSet getPerkListT() {
		return perkListT;
	}
	/**
	 * @param perkListT The perkListT to set.
	 */
	public void setPerkListT(TreeSet perkListT) {
		this.perkListT = perkListT;
	}
	/**
	 * @return Returns the recListT.
	 */
	public TreeSet getRecListT() {
		return recListT;
	}
	/**
	 * @param recListT The recListT to set.
	 */
	public void setRecListT(TreeSet recListT) {
		this.recListT = recListT;
	}
	public double getArrTotal() {
		return arrTotal;
	}
	public void setArrTotal(double arrTotal) {
		this.arrTotal = arrTotal;
	}
	public double getRecoTotal() {
		return recoTotal;
	}
	public void setRecoTotal(double recoTotal) {
		this.recoTotal = recoTotal;
	}
	public double getPc() {
		return pc;
	}
	public void setPc(double pc) {
		this.pc = pc;
	}
	public double getPf() {
		return pf;
	}
	public void setPf(double pf) {
		this.pf = pf;
	}
	public String getStrEarnTotal() {
		return strEarnTotal;
	}
	public void setStrEarnTotal(String strEarnTotal) {
		this.strEarnTotal = strEarnTotal;
	}
}
