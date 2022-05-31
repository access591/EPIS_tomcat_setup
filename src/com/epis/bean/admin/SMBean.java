package com.epis.bean.admin;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class SMBean extends RequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subModuleCode="";
	private String moduleCode="";
	private String subModuleName="";
	private String sortingOrder="";
	
	public String getSortingOrder() {
		return sortingOrder;
	}
	public void setSortingOrder(String sortingOrder) {
		this.sortingOrder = sortingOrder;
	}
	public SMBean(HttpServletRequest request){
		super(request);
		}
	public SMBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SMBean(String subModuleCode,String moduleCode,String subModuleName,String sortingOrder) {
		this.subModuleCode=subModuleCode;
		this.moduleCode=moduleCode;
		this.subModuleName=subModuleName;
		this.sortingOrder=sortingOrder;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getSubModuleCode() {
		return subModuleCode;
	}
	public void setSubModuleCode(String subModuleCode) {
		this.subModuleCode = subModuleCode;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public String toString(){
		return ((new StringBuffer("").append("ModuleCode : ").append(moduleCode).append("\n").append("Sub Module Code : ").append(subModuleCode).append("\n").append("Sub Module Name : ").append(subModuleName)).toString());
	}
	
	
}
