package com.aims.info.staffconfiguration; 

import java.io.*;

public class EmployeePersonalInfo  implements Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7988662225407238355L;
	/**
	 * 
	 */
	private String personalId;
	private String empNo;
	private String empDob="";
	private String empDoj="";
	/*private String empDor="";
	private String empDol="";*/
	
	private String seperationReason;
	private String dateofseperation;
	
	private String workafterretire;
	private String retirefrmpension;
	
	private String dateofretirement;//todo:autocaluclated date of retirement based on dateof birth
	
	
	
	private String maritalStat="";
	private String marriageDt="";
	private String nationality="";
	private String religion;
	private String permentAddress;
	private String city;
	private String state;
	private String zipCd;
	private String currentAddress;
	private String city1;
	private String state1;
	private String zipCd1; 
	private String mailId;
	private String contactNo1;
	private String contactNo2;
	private String deleteFlag;
	private String  isaddrsame;
	private String employeeName;
	private String usercd;
	
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	/**
	 * @return Returns the employeeName.
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName The employeeName to set.
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	
	/**
	 * @return Returns the contactNo1.
	 */
	public String getContactNo1() {
		return contactNo1;
	}
	/**
	 * @param contactNo1 The contactNo1 to set.
	 */
	public void setContactNo1(String contactNo1) {
		this.contactNo1 = contactNo1;
	}
	/**
	 * @return Returns the contactNo2.
	 */
	public String getContactNo2() {
		return contactNo2;
	}
	/**
	 * @param contactNo2 The contactNo2 to set.
	 */
	public void setContactNo2(String contactNo2) {
		this.contactNo2 = contactNo2;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getMaritalStat() {
		return maritalStat;
	}
	public void setMaritalStat(String maritalStat) {
		this.maritalStat = maritalStat;
	}
	
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPermentAddress() {
		return permentAddress;
	}
	public void setPermentAddress(String permentAddress) {
		this.permentAddress = permentAddress;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState1() {
		return state1;
	}
	public void setState1(String state1) {
		this.state1 = state1;
	}
	public String getZipCd() {
		return zipCd;
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getZipCd1() {
		return zipCd1;
	}
	public void setZipCd1(String zipCd1) {
		this.zipCd1 = zipCd1;
	}
	
	/**
	 * @return Returns the personalId.
	 */
	public String getPersonalId() {
		return personalId;
	}
	/**
	 * @param personalId The personalId to set.
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return Returns the empDob.
	 */
	public String getEmpDob() {
		return empDob;
	}
	/**
	 * @param empDob The empDob to set.
	 */
	public void setEmpDob(String empDob) {
		this.empDob = empDob;
	}
	/**
	 * @return Returns the empDoj.
	 */
	public String getEmpDoj() {
		return empDoj;
	}
	/**
	 * @param empDoj The empDoj to set.
	 */
	public void setEmpDoj(String empDoj) {
		this.empDoj = empDoj;
	}
	
	/**
	 * @return Returns the empNo.
	 */
	public String getEmpNo() {
		return empNo;
	}
	/**
	 * @param empNo The empNo to set.
	 */
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	/**
	 * @return Returns the marriageDt.
	 */
	public String getMarriageDt() {
		return marriageDt;
	}
	/**
	 * @param marriageDt The marriageDt to set.
	 */
	public void setMarriageDt(String marriageDt) {
		this.marriageDt = marriageDt;
	}
	public String getIsaddrsame() {
		return isaddrsame;
	}
	public void setIsaddrsame(String isaddrsame) {
		this.isaddrsame = isaddrsame;
	}
	
	
	/**
	 * @return Returns the dateofretirement.
	 */
	public String getDateofretirement() {
		return dateofretirement;
	}
	/**
	 * @param dateofretirement The dateofretirement to set.
	 */
	public void setDateofretirement(String dateofretirement) {
		this.dateofretirement = dateofretirement;
	}
	/**
	 * @return Returns the dateofseperation.
	 */
	public String getDateofseperation() {
		return dateofseperation;
	}
	/**
	 * @param dateofseperation The dateofseperation to set.
	 */
	public void setDateofseperation(String dateofseperation) {
		this.dateofseperation = dateofseperation;
	}
	/**
	 * @return Returns the seperationReason.
	 */
	public String getSeperationReason() {
		return seperationReason;
	}
	/**
	 * @param seperationReason The seperationReason to set.
	 */
	public void setSeperationReason(String seperationReason) {
		this.seperationReason = seperationReason;
	}
	/**
	 * @return Returns the workafterretire.
	 */
	public String getWorkafterretire() {
		return workafterretire;
	}
	/**
	 * @param workafterretire The workafterretire to set.
	 */
	public void setWorkafterretire(String workafterretire) {
		this.workafterretire = workafterretire;
	}
	
	/**
	 * @return Returns the retirefrmpension.
	 */
	public String getRetirefrmpension() {
		return retirefrmpension;
	}
	/**
	 * @param retirefrmpension The retirefrmpension to set.
	 */
	public void setRetirefrmpension(String retirefrmpension) {
		this.retirefrmpension = retirefrmpension;
	}
}
