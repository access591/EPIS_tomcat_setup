
package com.epis.bean.investment;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvestmentReportsBean {
	private String securityCategory;
	private String securityCategoryId;
	private String securityName;
	private List securityList;
	private String trustType;
	private String date;
	private String fromDate;
	private String toDate;
	private String modeOfInvestment;
	private String securityNames;
	private String interestRate;
	private String investmentMode;
	private String purchaePrice;
	private String investDate;
	private String interestMonth;
	private String maturityMonth;
	private String maturityYear;
	private String securityproposalNo;
	private String finYear;
	
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getSecurityproposalNo() {
		return securityproposalNo;
	}
	public void setSecurityproposalNo(String securityproposalNo) {
		this.securityproposalNo = securityproposalNo;
	}
	public String getMaturityMonth() {
		return maturityMonth;
	}
	public void setMaturityMonth(String maturityMonth) {
		this.maturityMonth = maturityMonth;
	}
	public String getMaturityYear() {
		return maturityYear;
	}
	public void setMaturityYear(String maturityYear) {
		this.maturityYear = maturityYear;
	}
	public String getInterestMonth() {
		return interestMonth;
	}
	public void setInterestMonth(String interestMonth) {
		this.interestMonth = interestMonth;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	/**
	 * @return Returns the securityName.
	 */
	public String getSecurityName() {
		return securityName;
	}
	/**
	 * @param securityName The securityName to set.
	 */
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	/**
	 * @return Returns the securityList.
	 */
	public List getSecurityList() {
		return securityList;
	}
	/**
	 * @param securityList The securityList to set.
	 */
	public void setSecurityList(List securityList) {
		this.securityList = securityList;
	}
	
	public String getSecurityCategory() {
		return securityCategory;
	}
	public void setSecurityCategory(String securityCategory) {
		this.securityCategory = securityCategory;
	}
	public String getSecurityCategoryId() {
		return securityCategoryId;
	}
	public void setSecurityCategoryId(String securityCategoryId) {
		this.securityCategoryId = securityCategoryId;
	}
	/**
	 * @return Returns the modeOfInvestment.
	 */
	public String getModeOfInvestment() {
		return modeOfInvestment;
	}
	/**
	 * @param modeOfInvestment The modeOfInvestment to set.
	 */
	public void setModeOfInvestment(String modeOfInvestment) {
		this.modeOfInvestment = modeOfInvestment;
	}
	/**
	 * @return Returns the securityNames.
	 */
	public String getSecurityNames() {
		return securityNames;
	}
	/**
	 * @param securityNames The securityNames to set.
	 */
	public void setSecurityNames(String securityNames) {
		this.securityNames = securityNames;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public String getInterestRate() {
		return interestRate;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	/**
	 * @return Returns the investmentMode.
	 */
	public String getInvestmentMode() {
		return investmentMode;
	}
	/**
	 * @param investmentMode The investmentMode to set.
	 */
	public void setInvestmentMode(String investmentMode) {
		this.investmentMode = investmentMode;
	}
	/**
	 * @return Returns the purchaePrice.
	 */
	public String getPurchaePrice() {
		return purchaePrice;
	}
	/**
	 * @param purchaePrice The purchaePrice to set.
	 */
	public void setPurchaePrice(String purchaePrice) {
		this.purchaePrice = purchaePrice;
	}
	/**
	 * @return Returns the investDate.
	 */
	public String getInvestDate() {
		return investDate;
	}
	/**
	 * @param investDate The investDate to set.
	 */
	public void setInvestDate(String investDate) {
		this.investDate = investDate;
	}
	

}
