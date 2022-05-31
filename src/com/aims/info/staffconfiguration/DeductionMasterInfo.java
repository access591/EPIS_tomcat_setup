/**
  * File       : DeductionMasterInfo.java
  * Date       : 09/06/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.info.staffconfiguration;

import java.util.ArrayList;

public class DeductionMasterInfo  implements java.io.Serializable {


    // Fields    

  
	 private Integer deductionCd;
     private String deductionName;
     private String deductionDesc;
     private Integer disciplineCd;
     
     private String printToPayslip;
     private Integer printOrder;
     private Integer accTypeCd;
     private String ledgerCd;
     private String ledgernm;
     
     private String isReimbursed;
     private Integer staffCategoryCd;
     private String calcType;
     private Integer percentVal;
     private Integer fixedAmt;
     private Integer minAmt;
     private Integer maxAmt;
     private String deletedFlag;
     private String fyearcd;
     private ArrayList acclist;
     private ArrayList categorylist;
     private ArrayList disciplinelist;
     
     private String disciplineNm;
     private String staffCategoryName;
     private Integer editDeductionCd;


    // Constructors

    public Integer getEditDeductionCd() {
		return editDeductionCd;
	}


	public void setEditDeductionCd(Integer editDeductionCd) {
		this.editDeductionCd = editDeductionCd;
	}


	/** default constructor */
    public DeductionMasterInfo() {
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


	public Integer getDeductionCd() {
		return deductionCd;
	}


	public void setDeductionCd(Integer deductionCd) {
		this.deductionCd = deductionCd;
	}


	public String getDeductionDesc() {
		return deductionDesc;
	}


	public void setDeductionDesc(String deductionDesc) {
		this.deductionDesc = deductionDesc;
	}


	public String getDeductionName() {
		return deductionName;
	}


	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}


	public Integer getFixedAmt() {
		return fixedAmt;
	}


	public void setFixedAmt(Integer fixedAmt) {
		this.fixedAmt = fixedAmt;
	}


	public String getFyearcd() {
		return fyearcd;
	}


	public void setFyearcd(String fyearcd) {
		this.fyearcd = fyearcd;
	}


	


	public String getIsReimbursed() {
		return isReimbursed;
	}


	public void setIsReimbursed(String isReimbursed) {
		this.isReimbursed = isReimbursed;
	}


	
	public Integer getMaxAmt() {
		return maxAmt;
	}


	public void setMaxAmt(Integer maxAmt) {
		this.maxAmt = maxAmt;
	}


	public Integer getMinAmt() {
		return minAmt;
	}


	public void setMinAmt(Integer minAmt) {
		this.minAmt = minAmt;
	}


	public Integer getPercentVal() {
		return percentVal;
	}


	public void setPercentVal(Integer percentVal) {
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


	public void setLedgerCd(String ledgerCd) {
		this.ledgerCd = ledgerCd;
	}


	public String getLedgerCd() {
		return ledgerCd;
	}

	
}