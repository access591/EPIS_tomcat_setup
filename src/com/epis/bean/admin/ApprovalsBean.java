package com.epis.bean.admin;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class ApprovalsBean extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String approvalCode="";
	private String approvalName="";
	private String description="";
	
	public ApprovalsBean(HttpServletRequest request){
		super(request);
		}
	public ApprovalsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApprovalsBean(String approvalCode,String approvalName,String description) {
		this.approvalCode=approvalCode;
		this.approvalName=approvalName;
		this.description=description;
	}
	
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public String getApprovalName() {
		return approvalName;
	}
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString(){
		return ("Option Code:"+this.approvalCode+"###Option Name:"+this.approvalName+"###Description:"+this.description);
	}
}
