package com.epis.bean.investment;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;


public class InvestProposalAppBean extends RequestBean{

	private String approved;
	private String date;
	private String approvedBy;
	private String remarks;
	private String signPath;
	private String displayName;
	private String designation;	
	private String refNo;
	private String approvalLevel;
	
	public InvestProposalAppBean(){
		
	}
	
	public InvestProposalAppBean(HttpServletRequest req){
		super(req);
	}
	
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSignPath() {
		return signPath;
	}
	public void setSignPath(String signPath) {
		this.signPath = signPath;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

}
