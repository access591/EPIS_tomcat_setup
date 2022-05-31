package com.aims.info.payrollprocess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.TreeSet;

/**
 * @author naveenm
 *
 */
public class EmployeePaymentsInfo implements Serializable,RandomAccess {

	/**
	 * 
	 */
	private static final long serialVersionUID = 941529303699843798L;
	
	private String employeepaymentcd="";    

	private String paymentDate="";
	
	private String empno="";	 
	private String emplnumber="";	  
	private String empname="";
	private String disciplineCd="";
	private String staffCategoryCd="";
	private String designationCd="";
	
	private String modeOfPay="";
	
	private String checkNo="";
	private String bank="";
	private String chqDate="";
	
	private String baccNo="";
	private String bankName=""; 
	private String transferDate="";
	
	private String paymentAgainst="";
	private String status="";
	private String advanceName="";
	
	private String usercd;
	
	/* Advances */
	private String empadvcd="";
	private String advcd="";
	private double principalAmount=0.0;
	private double recoveredAmount=0.0;
	private double balanceAmount=0.0;
	private double paidAmount=0.0;	
	private double paidAmountOld=0.0;
	private String principalstatus="";
	private String intereststatus="";
	
	/* Advances Interest */
	private String empadvcdi="";
	private String advcdi="";
	private double principalAmounti=0.0;
	private double recoveredAmounti=0.0;
	private double balanceAmounti=0.0;
	private double paidAmounti=0.0;	
	private double paidAmountOldi=0.0;
	private String principalstatusi="";
	private String intereststatusi="";
	
	private List advanceList=new ArrayList();
	private List advanceInterestList=new ArrayList();
	private TreeSet advanceListT;
	
	/* Supplementary Advances  */	
	private String suplmasterId="";
	private String suplDetailId="";
	private String suplAccountCode="";
	private String suplAccountDesc="";
	private String suplpaymenttype="";
	private String suplaccountdebit="";
	private double suplAccountAmount=0.0;
	private double suplTransferredAmount=0.0;	
	private double suplRecoveredAmount=0.0;
	private double suplPaidAmountOld=0.0;
	private double suplPaidAmount=0.0;
	
	
	private List suplAdvanceList=new ArrayList();
	private TreeSet suplAdvanceListT;
	
	
	/* Salary Recoveries */
	private String earndeducd="";
	private String earndedunm="";
	private String earntype="";
	private double amount=0.0;
	private List salrecoveriesList=new ArrayList();
	private TreeSet salrecoveriesListT;
	
	
	public TreeSet getSalrecoveriesListT() {
		return salrecoveriesListT;
	}
	public void setSalrecoveriesListT(TreeSet salrecoveriesListT) {
		this.salrecoveriesListT = salrecoveriesListT;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getDesignationCd() {
		return designationCd;
	}
	public void setDesignationCd(String designationCd) {
		this.designationCd = designationCd;
	}
	public String getDisciplineCd() {
		return disciplineCd;
	}
	public void setDisciplineCd(String disciplineCd) {
		this.disciplineCd = disciplineCd;
	}
	public String getEmplnumber() {
		return emplnumber;
	}
	public void setEmplnumber(String emplnumber) {
		this.emplnumber = emplnumber;
	}
	public String getEmployeepaymentcd() {
		return employeepaymentcd;
	}
	public void setEmployeepaymentcd(String employeepaymentcd) {
		this.employeepaymentcd = employeepaymentcd;
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
	public String getModeOfPay() {
		return modeOfPay;
	}
	public void setModeOfPay(String modeOfPay) {
		this.modeOfPay = modeOfPay;
	}
	public String getPaymentAgainst() {
		return paymentAgainst;
	}
	public void setPaymentAgainst(String paymentAgainst) {
		this.paymentAgainst = paymentAgainst;
	}
	public String getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(String staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}	
	public List getAdvanceList() {
		return advanceList;
	}
	public void setAdvanceList(List advanceList) {
		this.advanceList = advanceList;
	}
	public TreeSet getAdvanceListT() {
		return advanceListT;
	}
	public void setAdvanceListT(TreeSet advanceListT) {
		this.advanceListT = advanceListT;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAdvcd() {
		return advcd;
	}
	public void setAdvcd(String advcd) {
		this.advcd = advcd;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getEmpadvcd() {
		return empadvcd;
	}
	public void setEmpadvcd(String empadvcd) {
		this.empadvcd = empadvcd;
	}
	public double getPrincipalAmount() {
		return principalAmount;
	} 
	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}
	public double getRecoveredAmount() {
		return recoveredAmount;
	}
	public void setRecoveredAmount(double recoveredAmount) {
		this.recoveredAmount = recoveredAmount;
	}
	public List getSalrecoveriesList() {
		return salrecoveriesList;
	}
	public void setSalrecoveriesList(List salrecoveriesList) {
		this.salrecoveriesList = salrecoveriesList;
	}
	public String getEarndeducd() {
		return earndeducd;
	}
	public void setEarndeducd(String earndeducd) {
		this.earndeducd = earndeducd;
	}
	public String getEarndedunm() {
		return earndedunm;
	}
	public void setEarndedunm(String earndedunm) {
		this.earndedunm = earndedunm;
	}
	public String getEarntype() {
		return earntype;
	}
	public void setEarntype(String earntype) {
		this.earntype = earntype;
	}
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdvanceName() {
		return advanceName;
	}
	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}
	public String getChqDate() {
		return chqDate;
	}
	public void setChqDate(String chqDate) {
		this.chqDate = chqDate;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getIntereststatus() {
		return intereststatus;
	}
	public void setIntereststatus(String intereststatus) {
		this.intereststatus = intereststatus;
	}
	public String getPrincipalstatus() {
		return principalstatus;
	}
	public void setPrincipalstatus(String principalstatus) {
		this.principalstatus = principalstatus;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getBaccNo() {
		return baccNo;
	}
	public void setBaccNo(String baccNo) {
		this.baccNo = baccNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public double getPaidAmountOld() {
		return paidAmountOld;
	}
	public void setPaidAmountOld(double paidAmountOld) {
		this.paidAmountOld = paidAmountOld;
	}
	public double getSuplAccountAmount() {
		return suplAccountAmount;
	}
	public void setSuplAccountAmount(double suplAccountAmount) {
		this.suplAccountAmount = suplAccountAmount;
	}
	public String getSuplAccountCode() {
		return suplAccountCode;
	}
	public void setSuplAccountCode(String suplAccountCode) {
		this.suplAccountCode = suplAccountCode;
	}
	public String getSuplAccountDesc() {
		return suplAccountDesc;
	}
	public void setSuplAccountDesc(String suplAccountDesc) {
		this.suplAccountDesc = suplAccountDesc;
	}
	public List getSuplAdvanceList() {
		return suplAdvanceList;
	}
	public void setSuplAdvanceList(List suplAdvanceList) {
		this.suplAdvanceList = suplAdvanceList;
	}
	public TreeSet getSuplAdvanceListT() {
		return suplAdvanceListT;
	}
	public void setSuplAdvanceListT(TreeSet suplAdvanceListT) {
		this.suplAdvanceListT = suplAdvanceListT;
	}
	public String getSuplDetailId() {
		return suplDetailId;
	}
	public void setSuplDetailId(String suplDetailId) {
		this.suplDetailId = suplDetailId;
	}
	public String getSuplmasterId() {
		return suplmasterId;
	}
	public void setSuplmasterId(String suplmasterId) {
		this.suplmasterId = suplmasterId;
	}
	public double getSuplPaidAmount() {
		return suplPaidAmount;
	}
	public void setSuplPaidAmount(double suplPaidAmount) {
		this.suplPaidAmount = suplPaidAmount;
	}	
	public String getSuplpaymenttype() {
		return suplpaymenttype;
	}
	public void setSuplpaymenttype(String suplpaymenttype) {
		this.suplpaymenttype = suplpaymenttype;
	}
	public String getSuplaccountdebit() {
		return suplaccountdebit;
	}
	public void setSuplaccountdebit(String suplaccountdebit) {
		this.suplaccountdebit = suplaccountdebit;
	}
	public List getAdvanceInterestList() {
		return advanceInterestList;
	}
	public void setAdvanceInterestList(List advanceInterestList) {
		this.advanceInterestList = advanceInterestList;
	}
	public String getAdvcdi() {
		return advcdi;
	}
	public void setAdvcdi(String advcdi) {
		this.advcdi = advcdi;
	}
	public double getBalanceAmounti() {
		return balanceAmounti;
	}
	public void setBalanceAmounti(double balanceAmounti) {
		this.balanceAmounti = balanceAmounti;
	}
	public String getEmpadvcdi() {
		return empadvcdi;
	}
	public void setEmpadvcdi(String empadvcdi) {
		this.empadvcdi = empadvcdi;
	}
	public String getIntereststatusi() {
		return intereststatusi;
	}
	public void setIntereststatusi(String intereststatusi) {
		this.intereststatusi = intereststatusi;
	}
	public double getPaidAmounti() {
		return paidAmounti;
	}
	public void setPaidAmounti(double paidAmounti) {
		this.paidAmounti = paidAmounti;
	}
	public double getPaidAmountOldi() {
		return paidAmountOldi;
	}
	public void setPaidAmountOldi(double paidAmountOldi) {
		this.paidAmountOldi = paidAmountOldi;
	}
	public double getPrincipalAmounti() {
		return principalAmounti;
	}
	public void setPrincipalAmounti(double principalAmounti) {
		this.principalAmounti = principalAmounti;
	}
	public String getPrincipalstatusi() {
		return principalstatusi;
	}
	public void setPrincipalstatusi(String principalstatusi) {
		this.principalstatusi = principalstatusi;
	}
	public double getRecoveredAmounti() {
		return recoveredAmounti;
	}
	public void setRecoveredAmounti(double recoveredAmounti) {
		this.recoveredAmounti = recoveredAmounti;
	}
	public double getSuplTransferredAmount() {
		return suplTransferredAmount;
	}
	public void setSuplTransferredAmount(double suplTransferredAmount) {
		this.suplTransferredAmount = suplTransferredAmount;
	}
	public double getSuplRecoveredAmount() {
		return suplRecoveredAmount;
	}
	public void setSuplRecoveredAmount(double suplRecoveredAmount) {
		this.suplRecoveredAmount = suplRecoveredAmount;
	}
	public double getSuplPaidAmountOld() {
		return suplPaidAmountOld;
	}
	public void setSuplPaidAmountOld(double suplPaidAmountOld) {
		this.suplPaidAmountOld = suplPaidAmountOld;
	}
}
