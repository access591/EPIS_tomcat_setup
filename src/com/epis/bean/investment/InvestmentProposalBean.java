/**
 * 
 */
package com.epis.bean.investment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epis.bean.RequestBean;

/**
 * @author anilkumark
 * 
 */
public class InvestmentProposalBean extends RequestBean {
	
	public InvestmentProposalBean(HttpServletRequest request) {
		super(request);
	}

	public InvestmentProposalBean() {
		
	}

	private String refNo;
	private String trustType;
	private String securityCat;
	private String amountInv;
	private String remarks;
	private String subject;
	private String proposaldate;
	private String marketType;
	private String userId;
	private Map approvals; 
	private String flags;
	private String hasQrs;
	private String appDate;
	private String mode;
	private String status;
	private String updatedFlag;
	private String securityName;
	private String settlementDate;
	private String appCount;
	private List securityDetails;
	
	public String getAppCount() {
		return appCount;
	}

	public void setAppCount(String appCount) {
		this.appCount = appCount;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getUpdatedFlag() {
		return updatedFlag;
	}

	public void setUpdatedFlag(String updatedFlag) {
		this.updatedFlag = updatedFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getHasQrs() {
		return hasQrs;
	}

	public void setHasQrs(String hasQrs) {
		this.hasQrs = hasQrs;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAmountInv() {
		return amountInv;
	}

	public void setAmountInv(String amountInv) {
		this.amountInv = amountInv;
	}
	
	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getProposaldate() {
		return proposaldate;
	}

	public void setProposaldate(String proposaldate) {
		this.proposaldate = proposaldate;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSecurityCat() {
		return securityCat;
	}

	public void setSecurityCat(String securityCat) {
		this.securityCat = securityCat;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public Map getApprovals() {
		return approvals;
	}

	public void setApprovals(Map approvals) {
		this.approvals = approvals;
	}

	public List getSecurityDetails() {
		return securityDetails;
	}

	public void setSecurityDetails(List securityDetails) {
		this.securityDetails = securityDetails;
	}
	
}
