/**
 * File       : EmpMasterBean.java
 * Date       : 08/28/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2008-2009) by the Navayuga Infotech, all rights reserved.
 * 
 */
package com.epis.bean.rpfc;




import java.util.ArrayList;

import com.epis.utilities.Log;

public class EmpMasterBean {
	Log log = new Log(EmpMasterBean.class);
	String srno = "", airportSerialNumber = "", empNumber = "", cpfAcNo = "",newCpfAcNo = "",unmappedFlag="false";
    String optionForm="",reportType="",empName = "", desegnation = "", empLevel = "",cpf = "", dateofBirth = "", dateofJoining = "";
	String seperationReason = "", dateofSeperationDate = "",whetherOptionA = "",wetherOption = "";
	String remarks = "", station = "", fhName = "", whetherOptionB = "", whetherOptionNO = "", form2Nomination = "";
	String sex = "", maritalStatus = "", permanentAddress = "",fMemberName = "", pensionNumber = "", dateOfAnnuation = "";
	String emoluments = "", basic = "", specialBasic = "", dailyAllowance = "",pfAdvance = "", advDrawn = "", partFinal = "";
	String fDateofBirth = "", frelation = "", nomineeName = "",nomineeAddress = "", familyRow = "";
	String nomineeDob = "", nomineeRelation = "", nameofGuardian = "",totalShare = "", temporatyAddress = "", nomineeRow = "",unitCode = "", department = "", division = "";
	String aaiPf = "", aaiPension = "", aaiTotal = "", otherReason = "",employeePF = "", employeeVPF = "", employeeTotal = "";
	String emailId = "", gaddress = "", empNomineeSharable = "",fromDate = "", region = "", empOldName = "", lastActive = "";
	String dateofApt = "", wdob = "", wdoj = "", wname = "", wdoa = "",pfid = "",principal = "", interest = "";
	String computerName = "", recordVerified = "", empSerialNo = "",userName = "", empOldNumber = "",schemeInfo="";
	String changedRegion = "", changedStation = "", totalTrans = "",empNameCheak = "", fhFlag = "",stationWithRegion = "", newRegion = "";
	String heightWithInches="",nationality="",allRecordsFlag="",pfidfrom="",pcverified="",equalShare="";
	String monthYear="",arrearAmount="",claimsprocess="";
	ArrayList schemeList=new ArrayList();
	public String getClaimsprocess() {
		return claimsprocess;
	}

	public void setClaimsprocess(String claimsprocess) {
		this.claimsprocess = claimsprocess;
	}

	public String getArrearAmount() {
		return arrearAmount;
	}

	public void setArrearAmount(String arrearAmount) {
		this.arrearAmount = arrearAmount;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public String getEmpNameCheak() {
		return empNameCheak;
	}

	public void setEmpNameCheak(String empNameCheak) {
		this.empNameCheak = empNameCheak;
	}

	public String getRecordVerified() {
		return recordVerified;
	}

	public void setRecordVerified(String recordVerified) {
		this.recordVerified = recordVerified;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getEmpOldNumber() {
		return empOldNumber;
	}

	public void setEmpOldNumber(String empOldNumber) {
		this.empOldNumber = empOldNumber;
	}

	public String getWdob() {
		return wdob;
	}

	public void setWdob(String wdob) {
		this.wdob = wdob;
	}

	public String getWdoj() {
		return wdoj;
	}

	public void setWdoj(String wdoj) {
		this.wdoj = wdoj;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	public String getDateofApt() {
		return dateofApt;
	}

	public void setDateofApt(String dateofApt) {
		this.dateofApt = dateofApt;
	}

	public String getEmpNomineeSharable() {
		return empNomineeSharable;
	}

	public void setEmpNomineeSharable(String empNomineeSharable) {
		this.empNomineeSharable = empNomineeSharable;
	}

	public String getGaddress() {
		return gaddress;
	}

	public void setGaddress(String gaddress) {
		this.gaddress = gaddress;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getAaiPension() {
		return aaiPension;
	}

	public void setAaiPension(String aaiPension) {
		this.aaiPension = aaiPension;
	}

	public String getAaiPf() {
		return aaiPf;
	}

	public void setAaiPf(String aaiPf) {
		this.aaiPf = aaiPf;
	}

	public String getAaiTotal() {
		return aaiTotal;
	}

	public void setAaiTotal(String aaiTotal) {
		this.aaiTotal = aaiTotal;
	}

	public String getFDateofBirth() {
		return fDateofBirth;
	}

	public void setFDateofBirth(String dateofBirth) {
		fDateofBirth = dateofBirth;
	}

	public String getFMemberName() {
		return fMemberName;
	}

	public void setFMemberName(String memberName) {
		fMemberName = memberName;
	}

	public String getFrelation() {
		return frelation;
	}

	public void setFrelation(String frelation) {
		this.frelation = frelation;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNameofGuardian() {
		return nameofGuardian;
	}

	public void setNameofGuardian(String nameofGuardian) {
		this.nameofGuardian = nameofGuardian;
	}

	public String getNomineeAddress() {
		return nomineeAddress;
	}

	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}

	public String getNomineeDob() {
		return nomineeDob;
	}

	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeRelation() {
		return nomineeRelation;
	}

	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTemporatyAddress() {
		return temporatyAddress;
	}

	public void setTemporatyAddress(String temporatyAddress) {
		this.temporatyAddress = temporatyAddress;
	}

	public String getTotalShare() {
		return totalShare;
	}

	public void setTotalShare(String totalShare) {
		this.totalShare = totalShare;
	}

	public String getAirportSerialNumber() {
		return airportSerialNumber;
	}

	public void setAirportSerialNumber(String airportSerialNumber) {
		this.airportSerialNumber = airportSerialNumber;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getDateofJoining() {
		return dateofJoining;
	}

	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
	}

	public String getDateofSeperationDate() {
		return dateofSeperationDate;
	}

	public void setDateofSeperationDate(String dateofSeperationDate) {
		this.dateofSeperationDate = dateofSeperationDate;
	}

	public String getDesegnation() {
		return desegnation;
	}

	public void setDesegnation(String desegnation) {
		this.desegnation = desegnation;
	}

	public String getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(String empLevel) {
		this.empLevel = empLevel;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	public String getForm2Nomination() {
		return form2Nomination;
	}

	public void setForm2Nomination(String form2Nomination) {
		this.form2Nomination = form2Nomination;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeperationReason() {
		return seperationReason;
	}

	public void setSeperationReason(String seperationReason) {
		this.seperationReason = seperationReason;
	}

	public String getSrno() {
		return srno;
	}

	public void setSrno(String srno) {
		this.srno = srno;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getWhetherOptionA() {
		return whetherOptionA;
	}

	public void setWhetherOptionA(String whetherOptionA) {
		this.whetherOptionA = whetherOptionA;
	}

	public String getWhetherOptionB() {
		return whetherOptionB;
	}

	public void setWhetherOptionB(String whetherOptionB) {
		this.whetherOptionB = whetherOptionB;
	}

	public String getWhetherOptionNO() {
		return whetherOptionNO;
	}

	public void setWhetherOptionNO(String whetherOptionNO) {
		this.whetherOptionNO = whetherOptionNO;
	}

	public String getCpfAcNo() {
		return cpfAcNo;
	}

	public void setCpfAcNo(String cpfAcNo) {
		this.cpfAcNo = cpfAcNo;
	}

	/*public ArrayList retriveComparativeWestByAll(boolean flag) {
	 log.info("EmpMasterBean:retriveComparativeWestByAll-- Entering Method");
	 ArrayList searchData = new ArrayList();
	 EmpMasterBean data = null;
	 Connection con = null;
	 String sqlQuery = "";
	 //String sqlQueryMinrowId = "";
	 
	 sqlQuery = "select ei.cpfacno as cpfacno,ei.airportcode as airportcode, to_char(ei.dateofbirth,'dd-Mon-yyyy') as eidob,to_char(ei.dateofjoining,'dd-Mon-yyyy') as eidoj,ei.employeename as einame,ew.dateofbirth as wdob,ew.dateofjoining as wdoj,ew.dateofapt as wdoa,ew.employeename as wname from employee_westinfo ew,employee_info ei where ew.cpfacno=ei.cpfacno and ei.region='West Region' order by ew.cpfacno ";
	 
	 System.out.println(" Query is(retriveByAll)" + sqlQuery);
	 try {
	 con = commonDB.getConnection();
	 Statement st = con.createStatement();
	 ResultSet rs = st.executeQuery(sqlQuery);
	 while (rs.next()) {
	 data = new EmpMasterBean();
	 
	 if (rs.getString("cpfacno") != null) {
	 data.setCpfAcNo(rs.getString("cpfacno"));
	 } else {
	 data.setCpfAcNo("---");
	 }
	 
	 if (rs.getString("airportcode") != null) {
	 data.setStation(rs.getString("airportcode"));
	 } else {
	 data.setStation("---");
	 }
	 if (rs.getString("eidob") != null) {
	 data.setDateofBirth(rs.getString("eidob"));
	 } else {
	 data.setDateofBirth("---");
	 }
	 if (rs.getString("eidoj") != null) {
	 data.setDateofJoining(rs.getString("eidoj"));
	 } else {
	 data.setDateofJoining("---");
	 }
	 if (rs.getString("einame") != null) {
	 data.setEmpName(rs.getString("einame"));
	 } else {
	 data.setEmpName("---");
	 }

	 if (rs.getString("wdob") != null) {
	 data.setWdob(commonUtil.converDBToAppFormat(rs
	 .getDate("wdob")));
	 } else {
	 data.setWdob("---");
	 }
	 if (rs.getString("wdoj") != null) {
	 data.setWdoj(commonUtil.converDBToAppFormat(rs
	 .getDate("wdoj")));
	 } else {
	 data.setWdoj("---");
	 }
	 
	 if (rs.getString("wdoa") != null) {
	 data.setWdoa(commonUtil.converDBToAppFormat(rs
	 .getDate("wdoa")));
	 } else {
	 data.setWdoa("---");
	 }
	 
	 

	 if (rs.getString("wname") != null) {
	 data.setWname(rs.getString("wname"));
	 } else {
	 data.setWname("---");
	 }
	 searchData.add(data);
	 }
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 } catch (Exception e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 log.info("EmpMasterBean:retriveComparativeWestByAll-");
	 return searchData;

	 }*/



	public String getWetherOption() {
		return wetherOption;
	}

	public void setWetherOption(String wetherOption) {
		this.wetherOption = wetherOption;
	}

	public String getFhName() {
		return fhName;
	}

	public void setFhName(String fhName) {
		this.fhName = fhName;
	}

	public String getFamilyRow() {
		return familyRow;
	}

	public void setFamilyRow(String familyRow) {
		this.familyRow = familyRow;
	}

	public String getNomineeRow() {
		return nomineeRow;
	}

	public void setNomineeRow(String nomineeRow) {
		this.nomineeRow = nomineeRow;
	}

	public String getPensionNumber() {
		return pensionNumber;
	}

	public void setPensionNumber(String pensionNumber) {
		this.pensionNumber = pensionNumber;
	}

	public String getNewCpfAcNo() {
		return newCpfAcNo;
	}

	public void setNewCpfAcNo(String newCpfAcNo) {
		this.newCpfAcNo = newCpfAcNo;
	}

	public String getDateOfAnnuation() {
		return dateOfAnnuation;
	}

	public void setDateOfAnnuation(String dateOfAnnuation) {
		this.dateOfAnnuation = dateOfAnnuation;
	}

	public String getEmoluments() {
		return emoluments;
	}

	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}

	public String getEmployeePF() {
		return employeePF;
	}

	public void setEmployeePF(String employeePF) {
		this.employeePF = employeePF;
	}

	public String getEmployeeTotal() {
		return employeeTotal;
	}

	public void setEmployeeTotal(String employeeTotal) {
		this.employeeTotal = employeeTotal;
	}

	public String getEmployeeVPF() {
		return employeeVPF;
	}

	public void setEmployeeVPF(String employeeVPF) {
		this.employeeVPF = employeeVPF;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAdvDrawn() {
		return advDrawn;
	}

	public void setAdvDrawn(String advDrawn) {
		this.advDrawn = advDrawn;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getDailyAllowance() {
		return dailyAllowance;
	}

	public void setDailyAllowance(String dailyAllowance) {
		this.dailyAllowance = dailyAllowance;
	}

	public String getPartFinal() {
		return partFinal;
	}

	public void setPartFinal(String partFinal) {
		this.partFinal = partFinal;
	}

	public String getPfAdvance() {
		return pfAdvance;
	}

	public void setPfAdvance(String pfAdvance) {
		this.pfAdvance = pfAdvance;
	}

	public String getSpecialBasic() {
		return specialBasic;
	}

	public void setSpecialBasic(String specialBasic) {
		this.specialBasic = specialBasic;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEmpOldName() {
		return empOldName;
	}

	public void setEmpOldName(String empOldName) {
		this.empOldName = empOldName;
	}

	public String getLastActive() {
		return lastActive;
	}

	public void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}

	public String getWdoa() {
		return wdoa;
	}

	public void setWdoa(String wdoa) {
		this.wdoa = wdoa;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpSerialNo() {
		return empSerialNo;
	}

	public void setEmpSerialNo(String empSerialNo) {
		this.empSerialNo = empSerialNo;
	}

	public String getStationWithRegion() {
		return stationWithRegion;
	}

	public void setStationWithRegion(String stationWithRegion) {
		this.stationWithRegion = stationWithRegion;
	}

	public String getNewRegion() {
		return newRegion;
	}

	public void setNewRegion(String newRegion) {
		this.newRegion = newRegion;
	}

	public String getFhFlag() {
		return fhFlag;
	}

	public void setFhFlag(String fhFlag) {
		this.fhFlag = fhFlag;
	}

	public String getChangedStation() {
		return changedStation;
	}

	public void setChangedStation(String changedStation) {
		this.changedStation = changedStation;
	}

	public String getChangedRegion() {
		return changedRegion;
	}

	public void setChangedRegion(String changedRegion) {
		this.changedRegion = changedRegion;
	}

	public String getTotalTrans() {
		return totalTrans;
	}

	public void setTotalTrans(String totalTrans) {
		this.totalTrans = totalTrans;
	}

	public String getPfid() {
		return pfid;
	}

	public void setPfid(String pfid) {
		this.pfid = pfid;
	}

	public String getOptionForm() {
		return optionForm;
	}

	public void setOptionForm(String optionForm) {
		this.optionForm = optionForm;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getUnmappedFlag() {
		return unmappedFlag;
	}

	public void setUnmappedFlag(String unmappedFlag) {
		this.unmappedFlag = unmappedFlag;
	}

	public ArrayList getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(ArrayList schemeList) {
		this.schemeList = schemeList;
	}

	public String getSchemeInfo() {
		return schemeInfo;
	}

	public void setSchemeInfo(String schemeInfo) {
		this.schemeInfo = schemeInfo;
	}

	public String getHeightWithInches() {
		return heightWithInches;
	}

	public void setHeightWithInches(String heightWithInches) {
		this.heightWithInches = heightWithInches;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAllRecordsFlag() {
		return allRecordsFlag;
	}

	public void setAllRecordsFlag(String allRecordsFlag) {
		this.allRecordsFlag = allRecordsFlag;
	}

	public String getPcverified() {
		return pcverified;
	}

	public void setPcverified(String pcverified) {
		this.pcverified = pcverified;
	}

	public String getPfidfrom() {
		return pfidfrom;
	}

	public void setPfidfrom(String pfidfrom) {
		this.pfidfrom = pfidfrom;
	}

	public String getEqualShare() {
		return equalShare;
	}

	public void setEqualShare(String equalShare) {
		this.equalShare = equalShare;
	}



}
