package com.aims.info.staffconfiguration;
import java.sql.Date;

public class EmployeeInfo implements java.io.Serializable{
	private String empNo;
	private String empFirstName;
	private String empSurName;
	private String empLastName;
	private String employeename;
	private String cpfNo;
	private String bankId = "";
	private String paymentType;
	private String cadreCd; // for Gprof
	private String oldCadreCd;
	private String cadreNm;
	private Integer designationCd;
	private String designationNm;
	private String stafftypeName;
	private Integer disciplineCd;
	private Date effectiveDt;
	private Date endingDt;
	private String gender;
	private String pan;
	private String employementType;
	private String reportingTo;
	private Date lastupdatedDt;
	private String deleteFlag;
	private Integer staffCategoryCd;
	private String staffCategoryNm;
	private Integer id;
	private String empName;
	private String empDescipline;
	private String empDesignation;
	private String staffTypeCd;
	private String status;
	private String payscale;
	private String bacno;
	private String basicsal;
	private double basic=0.0;
	private double sp=0.0;
	private double dp=0.0;
	private double vda=0.0;
	private String hraOption;
	private String oldHraOption;
	private String regioncd;
	private String station;
	private String groupCd;
	private String gsliscd;
	private String oldgsliscd;
	private String gslisnm;
	private String contactno1;
	private String contactno2;
	private String quarterType;
	private String emplnumber;
	private String pftaxoption;
	private String pensionoption;
	private String pensionno;
	
	private String petrol;
	private String oldpetrol;
	
	private String proftax;
	private String upda;
	private String pftrust;
	private String plino;
	
	private String scode;
	private String cadrecod;
	
	private String desgCode;
	
	private String petrolrate;
	private String vconcd;
	
	private String gslisamt;
	
	
	
	private String dateofbirth;
	private String dateofjoin;
	
	
	private String eol = "";
	private String hpl ="";
	private String uaa = "";
	
	
	private String netAmt = "";
	
	private String days = "";
	
    private String payslipno = "";
    
    private String water;
    private String cons;
    private String hrr;
    private String usercd;
    private String empname="";
    private String empno="";
    
    
    private String stationCd="";
    private String medicalcardno="";
    
    public String getMedicalcardno() {
		return medicalcardno;
	}
	public void setMedicalcardno(String medicalcardno) {
		this.medicalcardno = medicalcardno;
	}
	
	public String getStationCd() {
		return stationCd;
	}
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getUsercd() {
		return usercd;
	}
	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}
	/**
	 * @return Returns the payslipno.
	 */
	public String getPayslipno() {
		return payslipno;
	}
	/**
	 * @param payslipno The payslipno to set.
	 */
	public void setPayslipno(String payslipno) {
		this.payslipno = payslipno;
	}
	/**
	 * @return Returns the days.
	 */
	public String getDays() {
		return days;
	}
	/**
	 * @param days The days to set.
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * @return Returns the netAmt.
	 */
	public String getNetAmt() {
		return netAmt;
	}
	/**
	 * @param netAmt The netAmt to set.
	 */
	public void setNetAmt(String netAmt) {
		this.netAmt = netAmt;
	}
	/**
	 * @return Returns the eol.
	 */
	public String getEol() {
		return eol;
	}
	/**
	 * @param eol The eol to set.
	 */
	public void setEol(String eol) {
		this.eol = eol;
	}
	/**
	 * @return Returns the hpl.
	 */
	public String getHpl() {
		return hpl;
	}
	/**
	 * @param hpl The hpl to set.
	 */
	public void setHpl(String hpl) {
		this.hpl = hpl;
	}
	/**
	 * @return Returns the uaa.
	 */
	public String getUaa() {
		return uaa;
	}
	/**
	 * @param uaa The uaa to set.
	 */
	public void setUaa(String uaa) {
		this.uaa = uaa;
	}
	/**
	 * @return Returns the dateofbirth.
	 */
	public String getDateofbirth() {
		return dateofbirth;
	}
	/**
	 * @param dateofbirth The dateofbirth to set.
	 */
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	/**
	 * @return Returns the dateofjoin.
	 */
	public String getDateofjoin() {
		return dateofjoin;
	}
	/**
	 * @param dateofjoin The dateofjoin to set.
	 */
	public void setDateofjoin(String dateofjoin) {
		this.dateofjoin = dateofjoin;
	}
	/**
	 * @return Returns the oldHraOption.
	 */
	public String getOldHraOption() {
		return oldHraOption;
	}
	/**
	 * @param oldHraOption The oldHraOption to set.
	 */
	public void setOldHraOption(String oldHraOption) {
		this.oldHraOption = oldHraOption;
	}
	/**
	 * @return Returns the oldCadreCd.
	 */
	public String getOldCadreCd() {
		return oldCadreCd;
	}
	/**
	 * @param oldCadreCd The oldCadreCd to set.
	 */
	public void setOldCadreCd(String oldCadreCd) {
		this.oldCadreCd = oldCadreCd;
	}
	/**
	 * @return Returns the gslisamt.
	 */
	public String getGslisamt() {
		return gslisamt;
	}
	/**
	 * @param gslisamt The gslisamt to set.
	 */
	public void setGslisamt(String gslisamt) {
		this.gslisamt = gslisamt;
	}
	/**
	 * @return Returns the vconcd.
	 */
	public String getVconcd() {
		return vconcd;
	}
	/**
	 * @param vconcd The vconcd to set.
	 */
	public void setVconcd(String vconcd) {
		this.vconcd = vconcd;
	}
	/**
	 * @return Returns the petrolrate.
	 */
	public String getPetrolrate() {
		return petrolrate;
	}
	/**
	 * @param petrolrate The petrolrate to set.
	 */
	public void setPetrolrate(String petrolrate) {
		this.petrolrate = petrolrate;
	}
	/**
	 * @return Returns the oldgsliscd.
	 */
	public String getOldgsliscd() {
		return oldgsliscd;
	}
	/**
	 * @param oldgsliscd The oldgsliscd to set.
	 */
	public void setOldgsliscd(String oldgsliscd) {
		this.oldgsliscd = oldgsliscd;
	}
	/**
	 * @return Returns the oldpetrol.
	 */
	public String getOldpetrol() {
		return oldpetrol;
	}
	/**
	 * @param oldpetrol The oldpetrol to set.
	 */
	public void setOldpetrol(String oldpetrol) {
		this.oldpetrol = oldpetrol;
	}
	/**
	 * @return Returns the desgcode.
	 */
	public String getDesgCode() {
		return desgCode;
	}
	/**
	 * @param desgcode The desgcode to set.
	 */
	public void setDesgCode(String desgCode) {
		this.desgCode = desgCode;
	}
	/**
	 * @return Returns the employeename.
	 */
	public String getEmployeename() {
		return employeename;
	}
	/**
	 * @param employeename The employeename to set.
	 */
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	/**
	 * @return Returns the pensionno.
	 */
	public String getPensionno() {
		return pensionno;
	}
	/**
	 * @param pensionno The pensionno to set.
	 */
	public void setPensionno(String pensionno) {
		this.pensionno = pensionno;
	}
	/**
	 * @return Returns the pensionoption.
	 */
	public String getPensionoption() {
		return pensionoption;
	}
	/**
	 * @param pensionoption The pensionoption to set.
	 */
	public void setPensionoption(String pensionoption) {
		this.pensionoption = pensionoption;
	}
	/**
	 * @return Returns the pftaxoption.
	 */
	public String getPftaxoption() {
		return pftaxoption;
	}
	/**
	 * @param pftaxoption The pftaxoption to set.
	 */
	public void setPftaxoption(String pftaxoption) {
		this.pftaxoption = pftaxoption;
	}
	/**
	 * @return Returns the regionCd.
	 */
	public String getRegioncd() {
		return regioncd;
	}
	/**
	 * @param regionCd The regionCd to set.
	 */
	public void setRegioncd(String regioncd) {
		this.regioncd = regioncd;
	}
	/**
	 * @return Returns the stationCd.
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param stationCd The stationCd to set.
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return Returns the bacno.
	 */
	public String getBacno() {
		return bacno;
	}
	/**
	 * @param bacno The bacno to set.
	 */
	public void setBacno(String bacno) {
		this.bacno = bacno;
	}
	/**
	 * @return Returns the staffTypeCd.
	 */
	public String getStaffTypeCd() {
		return staffTypeCd;
	}
	/**
	 * @param staffTypeCd The staffTypeCd to set.
	 */
	public void setStaffTypeCd(String staffTypeCd) {
		this.staffTypeCd = staffTypeCd;
	}
	/**
	 * @return Returns the empDescipline.
	 */
	public String getEmpDescipline() {
		return empDescipline;
	}
	/**
	 * @param empDescipline The empDescipline to set.
	 */
	public void setEmpDescipline(String empDescipline) {
		this.empDescipline = empDescipline;
	}
	/**
	 * @return Returns the empDesignation.
	 */
	public String getEmpDesignation() {
		return empDesignation;
	}
	/**
	 * @param empDesignation The empDesignation to set.
	 */
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
	/**
	 * @return Returns the bankId.
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param bankId The bankId to set.
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCadreCd() {
		return cadreCd;
	}
	public void setCadreCd(String cadreCd) {
		this.cadreCd = cadreCd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCpfNo() {
		return cpfNo;
	}
	public void setCpfNo(String cpfNo) {
		this.cpfNo = cpfNo;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getDesignationCd() {
		return designationCd;
	}
	public void setDesignationCd(Integer designationCd) {
		this.designationCd = designationCd;
	}
	public Integer getDisciplineCd() {
		return disciplineCd;
	}
	public void setDisciplineCd(Integer disciplineCd) {
		this.disciplineCd = disciplineCd;
	}
	public Date getEffectiveDt() {
		return effectiveDt;
	}
	public void setEffectiveDt(Date effectiveDt) {
		this.effectiveDt = effectiveDt;
	}
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	public String getEmployementType() {
		return employementType;
	}
	public void setEmployementType(String employementType) {
		this.employementType = employementType;
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
	public String getEmpSurName() {
		return empSurName;
	}
	public void setEmpSurName(String empSurName) {
		this.empSurName = empSurName;
	}
	public Date getEndingDt() {
		return endingDt;
	}
	public void setEndingDt(Date endingDt) {
		this.endingDt = endingDt;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getLastupdatedDt() {
		return lastupdatedDt;
	}
	public void setLastupdatedDt(Date lastupdatedDt) {
		this.lastupdatedDt = lastupdatedDt;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public String getStafftypeName() {
		return stafftypeName;
	}
	public void setStafftypeName(String stafftypeName) {
		this.stafftypeName = stafftypeName;
	}
	public Integer getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(Integer staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	/**
	 * @return Returns the staffCategoryNm.
	 */
	public String getStaffCategoryNm() {
		return staffCategoryNm;
	}
	/**
	 * @param staffCategoryNm The staffCategoryNm to set.
	 */
	public void setStaffCategoryNm(String staffCategoryNm) {
		this.staffCategoryNm = staffCategoryNm;
	}
	/**
	 * @return Returns the cadreNm.
	 */
	public String getCadreNm() {
		return cadreNm;
	}
	/**
	 * @param cadreNm The cadreNm to set.
	 */
	public void setCadreNm(String cadreNm) {
		this.cadreNm = cadreNm;
	}
	/**
	 * @return Returns the designationNm.
	 */
	public String getDesignationNm() {
		return designationNm;
	}
	/**
	 * @param designationNm The designationNm to set.
	 */
	public void setDesignationNm(String designationNm) {
		this.designationNm = designationNm;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayscale() {
		return payscale;
	}
	public void setPayscale(String payscale) {
		this.payscale = payscale;
	}
	/**
	 * @return Returns the basicsal.
	 */
	public String getBasicsal() {
		return basicsal;
	}
	/**
	 * @param basicsal The basicsal to set.
	 */
	public void setBasicsal(String basicsal) {
		this.basicsal = basicsal;
	}
	/**
	 * @return Returns the basic.
	 */
	public double getBasic() {
		return basic;
	}
	/**
	 * @param basic The basic to set.
	 */
	public void setBasic(double basic) {
		this.basic = basic;
	}
	/**
	 * @return Returns the dp.
	 */
	public double getDp() {
		return dp;
	}
	/**
	 * @param dp The dp to set.
	 */
	public void setDp(double dp) {
		this.dp = dp;
	}
	/**
	 * @return Returns the sp.
	 */
	public double getSp() {
		return sp;
	}
	/**
	 * @param sp The sp to set.
	 */
	public void setSp(double sp) {
		this.sp = sp;
	}
	/**
	 * @return Returns the vda.
	 */
	public double getVda() {
		return vda;
	}
	/**
	 * @param vda The vda to set.
	 */
	public void setVda(double vda) {
		this.vda = vda;
	}
	public String getHraOption() {
		return hraOption;
	}
	public void setHraOption(String hraOption) {
		this.hraOption = hraOption;
	}
	
	
	
	/**
	 * @return Returns the groupCd.
	 */
	public String getGroupCd() {
		return groupCd;
	}
	/**
	 * @param groupCd The groupCd to set.
	 */
	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}
	
	
	/**
	 * @return Returns the gsliscd.
	 */
	public String getGsliscd() {
		return gsliscd;
	}
	/**
	 * @param gsliscd The gsliscd to set.
	 */
	public void setGsliscd(String gsliscd) {
		this.gsliscd = gsliscd;
	}
	/**
	 * @return Returns the gslisnm.
	 */
	public String getGslisnm() {
		return gslisnm;
	}
	/**
	 * @param gslisnm The gslisnm to set.
	 */
	public void setGslisnm(String gslisnm) {
		this.gslisnm = gslisnm;
	}
	
	/**
	 * @return Returns the contactno1.
	 */
	public String getContactno1() {
		return contactno1;
	}
	/**
	 * @param contactno1 The contactno1 to set.
	 */
	public void setContactno1(String contactno1) {
		this.contactno1 = contactno1;
	}
	/**
	 * @return Returns the contactno2.
	 */
	public String getContactno2() {
		return contactno2;
	}
	/**
	 * @param contactno2 The contactno2 to set.
	 */
	public void setContactno2(String contactno2) {
		this.contactno2 = contactno2;
	}
	
	/**
	 * @return Returns the emplnumber.
	 */
	public String getEmplnumber() {
		return emplnumber;
	}
	/**
	 * @param emplnumber The emplnumber to set.
	 */
	public void setEmplnumber(String emplnumber) {
		this.emplnumber = emplnumber;
	}
	
	/**
	 * @return Returns the petrol.
	 */
	public String getPetrol() {
		return petrol;
	}
	/**
	 * @param petrol The petrol to set.
	 */
	public void setPetrol(String petrol) {
		this.petrol = petrol;
	}
	
	/**
	 * @return Returns the proftax.
	 */
	public String getProftax() {
		return proftax;
	}
	/**
	 * @param proftax The proftax to set.
	 */
	public void setProftax(String proftax) {
		this.proftax = proftax;
	}
	
	/**
	 * @return Returns the pftrust.
	 */
	public String getPftrust() {
		return pftrust;
	}
	/**
	 * @param pftrust The pftrust to set.
	 */
	public void setPftrust(String pftrust) {
		this.pftrust = pftrust;
	}
	/**
	 * @return Returns the plino.
	 */
	public String getPlino() {
		return plino;
	}
	/**
	 * @param plino The plino to set.
	 */
	public void setPlino(String plino) {
		this.plino = plino;
	}
	/**
	 * @return Returns the upda.
	 */
	public String getUpda() {
		return upda;
	}
	/**
	 * @param upda The upda to set.
	 */
	public void setUpda(String upda) {
		this.upda = upda;
	}
	
	
	/**
	 * @return Returns the scode.
	 */
	public String getScode() {
		return scode;
	}
	/**
	 * @param scode The scode to set.
	 */
	public void setScode(String scode) {
		this.scode = scode;
	}
	
	/**
	 * @return Returns the cadrecod.
	 */
	public String getCadrecod() {
		return cadrecod;
	}
	/**
	 * @param cadrecod The cadrecod to set.
	 */
	public void setCadrecod(String cadrecod) {
		this.cadrecod = cadrecod;
	}
	public String getQuarterType() {
		return quarterType;
	}
	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}
	public String getCons() {
		return cons;
	}
	public void setCons(String cons) {
		this.cons = cons;
	}
	public String getHrr() {
		return hrr;
	}
	public void setHrr(String hrr) {
		this.hrr = hrr;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
}
