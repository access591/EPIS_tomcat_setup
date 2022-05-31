package com.aims.info.staffconfiguration;

public class EmpDependentsInfo {

	private String empNo;
	private String dependentName;
	private Integer age;
	private String deleteFlag;
	private String relation;
	private Integer noOfDependents;
	private Integer dependentId;
	
	
	public Integer getDependentId() {
		return dependentId;
	}
	public void setDependentId(Integer dependentId) {
		this.dependentId = dependentId;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDependentName() {
		return dependentName;
	}
	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public Integer getNoOfDependents() {
		return noOfDependents;
	}
	public void setNoOfDependents(Integer noOfDependents) {
		this.noOfDependents = noOfDependents;
	}

	
}
