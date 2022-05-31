package com.epis.bean.investment;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.epis.bean.RequestBean;

public class ArrangersBean extends RequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String arrangerCd;
	private String arrangerName;
	private String regOffAddr;
	private String categoryCd;
	private String categoryId[];
	private String regPhoneNo;
	private String regFaxNo;
	private String delhiOffAddr;
	private String delhiOffPhNo;
	private String delhiOffFaxNo;
	private String nameOfHeadOfDelhiOff;
	private String delhiHeadOffMobileNo;
	private String delhiHeadOffPhNo;
	private String pramotorname;
	private String pramotorContactNo; 
	private String pramotorEmail;
	private String networthAmount;
	private String networthAsOn;
	private String email;
	private String regwithsebi;
	private String sebivaliddate;
	private String regwithbse;
	private String bsevaliddate;
	private String regwithnse;
	private String nsevaliddate;
	private String regwithrbi;
	private String rbivaliddate;
	private String remarks;
	private String status;
	private String categoryIds;
	private List categorylist;
	private String[] categoryArray;
	
	public ArrangersBean(HttpServletRequest request){
		super(request);
		}
	public ArrangersBean(){
		}
	
	public String[] getCategoryArray() {
		return categoryArray;
	}
	public void setCategoryArray(String[] categoryArray) {
		this.categoryArray = categoryArray;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getArrangerCd() {
		return arrangerCd;
	}
	public void setArrangerCd(String arrangerCd) {
		this.arrangerCd = arrangerCd;
	}
	public String getArrangerName() {
		return arrangerName;
	}
	public void setArrangerName(String arrangerName) {
		this.arrangerName = arrangerName;
	}
	public String getBsevaliddate() {
		return bsevaliddate;
	}
	public void setBsevaliddate(String bsevaliddate) {
		this.bsevaliddate = bsevaliddate;
	}
	public String getDelhiHeadOffPhNo() {
		return delhiHeadOffPhNo;
	}
	public void setDelhiHeadOffPhNo(String delhiHeadOffPhNo) {
		this.delhiHeadOffPhNo = delhiHeadOffPhNo;
	}
	public String getDelhiOffAddr() {
		return delhiOffAddr;
	}
	public void setDelhiOffAddr(String delhiOffAddr) {
		this.delhiOffAddr = delhiOffAddr;
	}
	public String getDelhiOffFaxNo() {
		return delhiOffFaxNo;
	}
	public void setDelhiOffFaxNo(String delhiOffFaxNo) {
		this.delhiOffFaxNo = delhiOffFaxNo;
	}
	public String getDelhiOffPhNo() {
		return delhiOffPhNo;
	}
	public void setDelhiOffPhNo(String delhiOffPhNo) {
		this.delhiOffPhNo = delhiOffPhNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNameOfHeadOfDelhiOff() {
		return nameOfHeadOfDelhiOff;
	}
	public void setNameOfHeadOfDelhiOff(String nameOfHeadOfDelhiOff) {
		this.nameOfHeadOfDelhiOff = nameOfHeadOfDelhiOff;
	}
	public String getNetworthAmount() {
		return networthAmount;
	}
	public void setNetworthAmount(String networthAmount) {
		this.networthAmount = networthAmount;
	}
	public String getNetworthAsOn() {
		return networthAsOn;
	}
	public void setNetworthAsOn(String networthAsOn) {
		this.networthAsOn = networthAsOn;
	}
	public String getNsevaliddate() {
		return nsevaliddate;
	}
	public void setNsevaliddate(String nsevaliddate) {
		this.nsevaliddate = nsevaliddate;
	}
	public String getPramotorContactNo() {
		return pramotorContactNo;
	}
	public void setPramotorContactNo(String pramotorContactNo) {
		this.pramotorContactNo = pramotorContactNo;
	}
	public String getPramotorEmail() {
		return pramotorEmail;
	}
	public void setPramotorEmail(String pramotorEmail) {
		this.pramotorEmail = pramotorEmail;
	}
	public String getPramotorname() {
		return pramotorname;
	}
	public void setPramotorname(String pramotorname) {
		this.pramotorname = pramotorname;
	}
	public String getRbivaliddate() {
		return rbivaliddate;
	}
	public void setRbivaliddate(String rbivaliddate) {
		this.rbivaliddate = rbivaliddate;
	}
	public String getRegFaxNo() {
		return regFaxNo;
	}
	public void setRegFaxNo(String regFaxNo) {
		this.regFaxNo = regFaxNo;
	}
	public String getRegOffAddr() {
		return regOffAddr;
	}
	public void setRegOffAddr(String regOffAddr) {
		this.regOffAddr = regOffAddr;
	}
	public String getRegPhoneNo() {
		return regPhoneNo;
	}
	public void setRegPhoneNo(String regPhoneNo) {
		this.regPhoneNo = regPhoneNo;
	}
	public String getRegwithbse() {
		return regwithbse;
	}
	public void setRegwithbse(String regwithbse) {
		this.regwithbse = regwithbse;
	}
	public String getRegwithnse() {
		return regwithnse;
	}
	public void setRegwithnse(String regwithnse) {
		this.regwithnse = regwithnse;
	}
	public String getRegwithrbi() {
		return regwithrbi;
	}
	public void setRegwithrbi(String regwithrbi) {
		this.regwithrbi = regwithrbi;
	}
	public String getRegwithsebi() {
		return regwithsebi;
	}
	public void setRegwithsebi(String regwithsebi) {
		this.regwithsebi = regwithsebi;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSebivaliddate() {
		return sebivaliddate;
	}
	public void setSebivaliddate(String sebivaliddate) {
		this.sebivaliddate = sebivaliddate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategoryCd() {
		return categoryCd;
	}
	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}
	public String[] getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String[] categoryId) {
		this.categoryId = categoryId;
	}
	public String getDelhiHeadOffMobileNo() {
		return delhiHeadOffMobileNo;
	}
	public void setDelhiHeadOffMobileNo(String delhiHeadOffMobileNo) {
		this.delhiHeadOffMobileNo = delhiHeadOffMobileNo;
	}
	public List getCategorylist() {
		return categorylist;
	}
	public void setCategorylist(List categorylist) {
		this.categorylist = categorylist;
	}
	
}
