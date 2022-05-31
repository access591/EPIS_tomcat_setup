package com.aims.info.staffconfiguration;
import java.sql.Date;
import java.util.ArrayList;

public class EmployeeEarningInfo {
	private Integer earningId;
	private Integer staffCategoryCd;
	private Date effectiveDt;
	private Date endingDt;
	private Float grossSalary;
	private String deleteFlag;
	private ArrayList earnings;
	private ArrayList perks;
	private String userCd;
	 private String calcType;
     private Double percentVal;
     private Double fixedAmt;
     private Double minAmt;
     private Double maxAmt;
     private Double amount;
     private Integer earningCd;
     private String earningName;
     private String empNo;
     private String emplnumber;
     private String empName;
     private String status;
     private String type;
     private String empDesignation;
     private String isBasicSal;
     private String depends;
     private String dependsstr;
     private String hraOption;
     private Integer configEarningcd;
    private String petrol;
    private String shortname;
    private String desgCode;
    
    
    public String vconcd;
    
    private String desgcode;
	
	private String petrolrate;
	private String gsliscd;
	
	private String gslisamt;
	private String month;
	
    private String cadrecod;
    
    private String cd;
    private String quarterType;
    
	public String getQuarterType() {
		return quarterType;
	}
	public void setQuarterType(String quarterType) {
		this.quarterType = quarterType;
	}
	/**
	 * @return Returns the cd.
	 */
	public String getCd() {
		return cd;
	}
	/**
	 * @param cd The cd to set.
	 */
	public void setCd(String cd) {
		this.cd = cd;
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
	/**
	 * @return Returns the desgcode.
	 */
	public String getDesgcode() {
		return desgcode;
	}
	/**
	 * @param desgcode The desgcode to set.
	 */
	public void setDesgcode(String desgcode) {
		this.desgcode = desgcode;
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
	 * @return Returns the desgCode.
	 */
	public String getDesgCode() {
		return desgCode;
	}
	/**
	 * @param desgCode The desgCode to set.
	 */
	public void setDesgCode(String desgCode) {
		this.desgCode = desgCode;
	}
	/**
	 * @return Returns the shortname.
	 */
	public String getShortname() {
		return shortname;
	}
	/**
	 * @param shortname The shortname to set.
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
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
	public Integer getConfigEarningcd() {
		return configEarningcd;
	}
	public void setConfigEarningcd(Integer configEarningcd) {
		this.configEarningcd = configEarningcd;
	}
	public String getHraOption() {
		return hraOption;
	}
	public void setHraOption(String hraOption) {
		this.hraOption = hraOption;
	}
	public String getDepends() {
		return depends;
	}
	public void setDepends(String depends) {
		this.depends = depends;
	}
	public String getDependsstr() {
		return dependsstr;
	}
	public void setDependsstr(String dependsstr) {
		this.dependsstr = dependsstr;
	}
	public String getIsBasicSal() {
		return isBasicSal;
	}
	public void setIsBasicSal(String isBasicSal) {
		this.isBasicSal = isBasicSal;
	}
	public String getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}
     private Integer slno;
 	
	
	public Integer getSlno() {
		return slno;
	}
	public void setSlno(Integer slno) {
		this.slno = slno;
	}
	public Integer getEarningCd() {
		return earningCd;
	}
	public void setEarningCd(Integer earningCd) {
		this.earningCd = earningCd;
	}
	public String getEarningName() {
		return earningName;
	}
	public void setEarningName(String earningName) {
		this.earningName = earningName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	/**
	 * @return Returns the amount.
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getUserCd() {
		return userCd;
	}
	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public Date getEffectiveDt() {
		return effectiveDt;
	}
	public void setEffectiveDt(Date effectiveDt) {
		this.effectiveDt = effectiveDt;
	}
	
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public Date getEndingDt() {
		return endingDt;
	}
	public void setEndingDt(Date endingDt) {
		this.endingDt = endingDt;
	}
	
	public Float getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(Float grossSalary) {
		this.grossSalary = grossSalary;
	}
	
	
	public Integer getEarningId() {
		return earningId;
	}
	public void setEarningId(Integer earningId) {
		this.earningId = earningId;
	}


	public ArrayList getEarnings() {
		return earnings;
	}
	public void setEarnings(ArrayList earnings) {
		this.earnings = earnings;
	}
	public Integer getStaffCategoryCd() {
		return staffCategoryCd;
	}
	public void setStaffCategoryCd(Integer staffCategoryCd) {
		this.staffCategoryCd = staffCategoryCd;
	}
	public String getCalcType() {
		return calcType;
	}
	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}
	
	
	public Double getFixedAmt() {
		return fixedAmt;
	}
	public void setFixedAmt(Double fixedAmt) {
		this.fixedAmt = fixedAmt;
	}
	public Double getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(Double maxAmt) {
		this.maxAmt = maxAmt;
	}
	public Double getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(Double minAmt) {
		this.minAmt = minAmt;
	}
	public Double getPercentVal() {
		return percentVal;
	}
	public void setPercentVal(Double percentVal) {
		this.percentVal = percentVal;
	}
	public ArrayList getPerks() {
		return perks;
	}
	public void setPerks(ArrayList perks) {
		this.perks = perks;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}
}
