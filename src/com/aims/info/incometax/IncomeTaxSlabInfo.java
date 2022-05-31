
package com.aims.info.incometax;

/**
 * @author naveenk
 *
 */

public class IncomeTaxSlabInfo {
	private String slabCd;

	private String assesseType;

	private String taxYear;

	private String fromAmount;

	private String toAmount;

	private String taxPercent;

	private String[] taxDetails;

	private String userCd;

	private String fromDate;

	private String toDate;
	
	private String taxSlabDtCd;
	
	private String mode;
	
	private String fixedAmt;
	
	private String deductionAmt;

	private String status;
	
	private String finYear;

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	/**
	 * @return Returns the assesseType.
	 */
	public String getAssesseType() {
		return assesseType;
	}

	/**
	 * @param assesseType
	 *            The assesseType to set.
	 */
	public void setAssesseType(String assesseType) {
		this.assesseType = assesseType;
	}

	/**
	 * @return Returns the fromAmount.
	 */
	public String getFromAmount() {
		return fromAmount;
	}

	/**
	 * @param fromAmount
	 *            The fromAmount to set.
	 */
	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}

	/**
	 * @return Returns the slabCd.
	 */
	public String getSlabCd() {
		return slabCd;
	}

	/**
	 * @param slabCd
	 *            The slabCd to set.
	 */
	public void setSlabCd(String slabCd) {
		this.slabCd = slabCd;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the taxPercent.
	 */
	public String getTaxPercent() {
		return taxPercent;
	}

	/**
	 * @param taxPercent
	 *            The taxPercent to set.
	 */
	public void setTaxPercent(String taxPercent) {
		this.taxPercent = taxPercent;
	}

	/**
	 * @return Returns the taxYear.
	 */
	public String getTaxYear() {
		return taxYear;
	}

	/**
	 * @param taxYear
	 *            The taxYear to set.
	 */
	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}

	/**
	 * @return Returns the toAmount.
	 */
	public String getToAmount() {
		return toAmount;
	}

	/**
	 * @param toAmount
	 *            The toAmount to set.
	 */
	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	/**
	 * @return Returns the taxDetails.
	 */
	public String[] getTaxDetails() {
		return taxDetails;
	}

	/**
	 * @param taxDetails
	 *            The taxDetails to set.
	 */
	public void setTaxDetails(String[] taxDetails) {
		this.taxDetails = taxDetails;
	}

	/**
	 * @return Returns the userCd.
	 */
	public String getUserCd() {
		return userCd;
	}

	/**
	 * @param userCd
	 *            The userCd to set.
	 */
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the taxSlabDtCd.
	 */
	public String getTaxSlabDtCd() {
		return taxSlabDtCd;
	}

	/**
	 * @param taxSlabDtCd The taxSlabDtCd to set.
	 */
	public void setTaxSlabDtCd(String taxSlabDtCd) {
		this.taxSlabDtCd = taxSlabDtCd;
	}

	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDeductionAmt() {
		return deductionAmt;
	}

	public void setDeductionAmt(String deductionAmt) {
		this.deductionAmt = deductionAmt;
	}

	public String getFixedAmt() {
		return fixedAmt;
	}

	public void setFixedAmt(String fixedAmt) {
		this.fixedAmt = fixedAmt;
	}

}
