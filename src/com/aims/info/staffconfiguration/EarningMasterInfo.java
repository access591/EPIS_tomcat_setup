/**
  * File       : EarningMasterInfo.java
  * Date       : 09/06/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;

import java.util.ArrayList;

public class EarningMasterInfo  implements java.io.Serializable {


    // Fields    

  
	 private Integer earningCd;
     private String earningName;
     private String earningDesc;
     private Integer disciplineCd;
     private String isBasicSal;
     private String printToPayslip;
     private Integer printOrder;
     private Integer accTypeCd;
     private String ledgerCd;
     private String ledgernm;
     private String isOtherAllowance;
     private String isTaxable;
     private String isReimbursed;
     private Integer staffCategoryCd;
     private String calcType;
     private Double percentVal;
     private Double fixedAmt;
     private Double minAmt;
     private Double maxAmt;
     private String deletedFlag;
     private String fyearcd;
     private ArrayList acclist;
     private ArrayList categorylist;
     private ArrayList disciplinelist;
     
     private String disciplineNm;
     private String staffCategoryName;
     private Integer editEarningCd;
     private String userCd;
     private String type;
     private String depends;
     private String dependsstr;
     private Integer configEarningcd;
     private String select;
     private String shortName;
     private String hraOption;
     private String quarterType;
	
	public String getQuarterType() {
		return quarterType;
	}
	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}
	public String getHraOption() {
		return hraOption;
	}
	public void setHraOption(String hraOption) {
		this.hraOption = hraOption;
	}
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
	 * @return Returns the dependsstr.
	 */
	public String getDependsstr() {
		return dependsstr;
	}
	/**
	 * @param dependsstr The dependsstr to set.
	 */
	public void setDependsstr(String dependsstr) {
		this.dependsstr = dependsstr;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
    // Constructors

    public Integer getEditEarningCd() {
		return editEarningCd;
	}


	public void setEditEarningCd(Integer editEarningCd) {
		this.editEarningCd = editEarningCd;
	}


	/** default constructor */
    public EarningMasterInfo() {
    }


	public Integer getAccTypeCd() {
		return accTypeCd;
	}


	public void setAccTypeCd(Integer accTypeCd) {
		this.accTypeCd = accTypeCd;
	}


	public String getCalcType() {
		return calcType;
	}


	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}


	public String getDeletedFlag() {
		return deletedFlag;
	}


	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}


	public Integer getDisciplineCd() {
		return disciplineCd;
	}


	public void setDisciplineCd(Integer disciplineCd) {
		this.disciplineCd = disciplineCd;
	}


	public Integer getEarningCd() {
		return earningCd;
	}


	public void setEarningCd(Integer earningCd) {
		this.earningCd = earningCd;
	}


	public String getEarningDesc() {
		return earningDesc;
	}


	public void setEarningDesc(String earningDesc) {
		this.earningDesc = earningDesc;
	}


	public String getEarningName() {
		return earningName;
	}


	public void setEarningName(String earningName) {
		this.earningName = earningName;
	}


	

	public String getFyearcd() {
		return fyearcd;
	}


	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}


	public String getIsBasicSal() {
		return isBasicSal;
	}


	public void setIsBasicSal(String isBasicSal) {
		this.isBasicSal = isBasicSal;
	}


	public String getIsOtherAllowance() {
		
		return isOtherAllowance;
	}


	public void setIsOtherAllowance(String isOtherAllowance) {
		this.isOtherAllowance = isOtherAllowance;
	}


	public String getIsReimbursed() {
		return isReimbursed;
	}


	public void setIsReimbursed(String isReimbursed) {
		this.isReimbursed = isReimbursed;
	}


	public String getIsTaxable() {
		return isTaxable;
	}


	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}


	


	public String getLedgerCd() {
		return ledgerCd;
	}


	public void setLedgerCd(String ledgerCd) {
		this.ledgerCd = ledgerCd;
	}


	


	/**
	 * @return Returns the fixedAmt.
	 */
	public Double getFixedAmt() {
		return fixedAmt;
	}
	/**
	 * @param fixedAmt The fixedAmt to set.
	 */
	public void setFixedAmt(Double fixedAmt) {
		this.fixedAmt = fixedAmt;
	}
	/**
	 * @return Returns the maxAmt.
	 */
	public Double getMaxAmt() {
		return maxAmt;
	}
	/**
	 * @param maxAmt The maxAmt to set.
	 */
	public void setMaxAmt(Double maxAmt) {
		this.maxAmt = maxAmt;
	}
	/**
	 * @return Returns the minAmt.
	 */
	public Double getMinAmt() {
		return minAmt;
	}
	/**
	 * @param minAmt The minAmt to set.
	 */
	public void setMinAmt(Double minAmt) {
		this.minAmt = minAmt;
	}
	/**
	 * @return Returns the percentVal.
	 */
	public Double getPercentVal() {
		return percentVal;
	}
	/**
	 * @param percentVal The percentVal to set.
	 */
	public void setPercentVal(Double percentVal) {
		this.percentVal = percentVal;
	}
	public Integer getPrintOrder() {
		return printOrder;
	}


	public void setPrintOrder(Integer printOrder) {
		this.printOrder = printOrder;
	}


	public String getPrintToPayslip() {
		return printToPayslip;
	}


	public void setPrintToPayslip(String printToPayslip) {
		this.printToPayslip = printToPayslip;
	}


	public Integer getStaffCategoryCd() {
		return staffCategoryCd;
	}


	public void setStaffCategoryCd(Integer staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}


	public ArrayList getAcclist() {
		return acclist;
	}


	public void setAcclist(ArrayList acclist) {
		this.acclist = acclist;
	}


	public ArrayList getCategorylist() {
		return categorylist;
	}


	public void setCategorylist(ArrayList categorylist) {
		this.categorylist = categorylist;
	}


	public ArrayList getDisciplinelist() {
		return disciplinelist;
	}


	public void setDisciplinelist(ArrayList disciplinelist) {
		this.disciplinelist = disciplinelist;
	}


	


	public String getDisciplineNm() {
		return disciplineNm;
	}


	public void setDisciplineNm(String disciplineNm) {
		this.disciplineNm = disciplineNm;
	}


	public String getStaffCategoryName() {
		return staffCategoryName;
	}


	public void setStaffCategoryName(String staffCategoryName) {
		this.staffCategoryName = staffCategoryName;
	}


	public String getLedgernm() {
		return ledgernm;
	}


	public void setLedgernm(String ledgernm) {
		this.ledgernm = ledgernm;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return Returns the depends.
	 */
	public String getDepends() {
		return depends;
	}
	/**
	 * @param depends The depends to set.
	 */
	public void setDepends(String depends) {
		this.depends = depends;
	}
	
	
	/**
	 * @return Returns the select.
	 */
	public String getSelect() {
		return select;
	}
	/**
	 * @param select The select to set.
	 */
	public void setSelect(String select) {
		this.select = select;
	}
	public Integer getConfigEarningcd() {
		return configEarningcd;
	}
	public void setConfigEarningcd(Integer configEarningcd) {
		this.configEarningcd = configEarningcd;
	}
}