/**
  * File       : StaffConfigurationService.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */

package com.aims.service;

import java.util.*;

import com.epis.utilities.ApplicationException;
import com.epis.utilities.Log;
import com.aims.dao.PayrollProcessDAO;
import com.aims.dao.StaffConfigurationDAO;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.quarter.QuarterMasterInfo;
import com.aims.info.staffconfiguration.CadreInfo;
import com.aims.info.staffconfiguration.DAHikeInfo;
import com.aims.info.staffconfiguration.DesignationInfo;
import com.aims.info.staffconfiguration.EarningMasterInfo;
import com.aims.info.staffconfiguration.EmpDependentsInfo;
import com.aims.info.staffconfiguration.EmpRecoveriesInfo;
import com.aims.info.staffconfiguration.EmployeeDeductionsInfo;
import com.aims.info.staffconfiguration.EmployeeEarningInfo;
import com.aims.info.staffconfiguration.EmployeeIncomeTaxInfo;
import com.aims.info.staffconfiguration.EmployeeInfo;
import com.aims.info.staffconfiguration.EmployeePerksInfo;
import com.aims.info.staffconfiguration.EmployeePersonalInfo;
import com.aims.info.staffconfiguration.GroupInfo;
import com.aims.info.staffconfiguration.PayStopConfigInfo;
import com.aims.info.staffconfiguration.PayscaleMasterInfo;
import com.aims.info.staffconfiguration.SeachCadreInfo;
import com.aims.info.staffconfiguration.SearchDesciplineInfo;
import com.aims.info.staffconfiguration.SearchDesignationInfo;
import com.aims.info.staffconfiguration.SearchEarningInfo;
import com.aims.info.staffconfiguration.SearchEmpInfo;
import com.aims.info.staffconfiguration.SearchGroupInfo;
import com.aims.info.staffconfiguration.SearchStaffCategoryInfo;
import com.aims.info.staffconfiguration.SearchStaffTypeInfo;
import com.aims.info.staffconfiguration.StaffCategoryInfo;
import com.aims.info.staffconfiguration.StaffDiscipline;
import com.aims.info.staffconfiguration.StaffTypeInfo;


public class StaffConfigurationService {
Log log = new Log(StaffConfigurationService.class);
	private StaffConfigurationService(){
		
	}
	private static StaffConfigurationService staffTypeServiceInstance=new StaffConfigurationService();
	public static StaffConfigurationService getInstance(){
		return staffTypeServiceInstance;
	}
	
	public List getAllDesciplines(){
		return StaffConfigurationDAO.getInstance().getAllDesciplines();
	}

	public void insertStaffType(StaffTypeInfo stafftype) throws ApplicationException{
		StaffConfigurationDAO.getInstance().insertStaffType(stafftype);
	}
	public List searchStaffTypes(SearchStaffTypeInfo stafftypeinfo){
		return  StaffConfigurationDAO.getInstance().searchStaffTypes(stafftypeinfo);
		 
	}
	
	public void deleteStaffTypes(String stafftypestr){
		StaffConfigurationDAO.getInstance().deleteStaffTypes(stafftypestr);
	}
	public List editStaffTypes(int stafftypecd){
		return StaffConfigurationDAO.getInstance().editStaffTypes(stafftypecd);
	}
	public int  updateStaffTypes(int cd,String name,String desc,String status) throws ApplicationException{
		return StaffConfigurationDAO.getInstance().updateStaffTypes(cd,name,desc,status);
	}
	
	public void saveDescipline(StaffDiscipline staffdesinfo) throws ApplicationException{
		System.out.println("inside Service");
		StaffConfigurationDAO.getInstance().saveDescipline(staffdesinfo);
		//return securityId;
	}
	public void updateDescipline(int desciplineCd,String desciplineName,String desciplineDesc,String status) throws ApplicationException{
		System.out.println("inside Service");
		StaffConfigurationDAO.getInstance().updateDescipline(desciplineCd,desciplineName,desciplineDesc,status);
		//return securityId;
	}
	
	public List searchDescTypes(SearchDesciplineInfo staffdescinfo){
		return StaffConfigurationDAO.getInstance().searchDescTypes(staffdescinfo);
		
	}
	public StaffDiscipline editDescTypes(String disciplineCd){
		StaffDiscipline list = StaffConfigurationDAO.getInstance().editDescTypes(disciplineCd);
		return list;
	}
	public void deleteDesciplineType(String desciplineCd){
		StaffConfigurationDAO.getInstance().deleteDesciplineTypes(desciplineCd);
	}
	
	public List selectStafftypeCd()
	{
		List l=StaffConfigurationDAO.getInstance().selectStafftypeCd();
		return l;
	}
	
	public void insertStaffCategory(StaffCategoryInfo staffcategory) throws ApplicationException{
		StaffConfigurationDAO.getInstance().insertStaffCategory(staffcategory);
	}
	public List searchStaffCategory(SearchStaffCategoryInfo staffcategoryinfo){
		return  StaffConfigurationDAO.getInstance().searchStaffCategory(staffcategoryinfo);
		 
	}

	public List editStaffCategory(int staffcategorycd){
		return StaffConfigurationDAO.getInstance().editStaffCategory(staffcategorycd);
	}
	public int  updateStaffCategory(int cd,String name,String desc,String status,String usercd){
		return StaffConfigurationDAO.getInstance().updateStaffCategory(cd,name,desc,status,usercd);
	}
	public void saveStaffCadre(CadreInfo ci) throws ApplicationException{
		StaffConfigurationDAO.getInstance().saveStaffCadre(ci);
	}
	public List searchCadre(SeachCadreInfo searchCadreInfo){
		return StaffConfigurationDAO.getInstance().searchCadre(searchCadreInfo);
		
	}
	public CadreInfo findByCadrecd(String cadrecd){
		return StaffConfigurationDAO.getInstance().findByCadrecd(cadrecd);
	}
	public void updateCadre(CadreInfo ci){
		 StaffConfigurationDAO.getInstance().updateCadre(ci);
	}
	/*public void deleteCadre(String cadrecd){
		StaffConfigurationDAO.getInstance().deleteCadre(cadrecd);
	}*/
	public List getAllStaffGroups(){
		List l=StaffConfigurationDAO.getInstance().getAllStaffGroups();
		return l;
	}
	public List getAllStaffCategories(){
		List l=StaffConfigurationDAO.getInstance().getAllStaffCategories();
		return l;
	}
	
	
	
	public List searchGroupTypes(SearchGroupInfo staffgroupinfo){
		return StaffConfigurationDAO.getInstance().searchGroupTypes(staffgroupinfo);
		
	}
	
	public void deleteGroupType(String groupCd){
		StaffConfigurationDAO.getInstance().deleteGroupType(groupCd);
	}
	public GroupInfo editGroupTypes(String groupCd){
		GroupInfo list = StaffConfigurationDAO.getInstance().editGroupTypes(groupCd);
		return list;
	}
	
	public void groupUpdate(int groupcd,String groupName,String groupdesc,String status){
		System.out.println("inside Service");
		StaffConfigurationDAO.getInstance().groupUpdate(groupcd,groupName,groupdesc,status);
	}
	public void insertGroup(GroupInfo grouptype)throws ApplicationException, ApplicationException {   
		StaffConfigurationDAO.getInstance().insertGroup(grouptype);
	}
	
/**   * for Employee info ************ **/
	
	public List selectDesciplineCd(String stafftypecd)
	{
		List l=StaffConfigurationDAO.getInstance().selectDesciplineCd(stafftypecd);
		return l;
	}
	
	public List selectBank()
	{
		List l=StaffConfigurationDAO.getInstance().selectBank();
		return l;
	}
	
	public List selectCadreCd(String staffCategoryCd){
		List l=StaffConfigurationDAO.getInstance().selectCadreCd(staffCategoryCd);
		return l;
	}
	
	public List selectDesegnationCd(String cadreCd)
	{
		List l=StaffConfigurationDAO.getInstance().selectDesegnationCd(cadreCd);
		return l;
	}
	
	public String addEmpinfo(EmployeeInfo empinfo){
		
		return StaffConfigurationDAO.getInstance().addEmpinfo(empinfo);
	}
	
	public List searchEmpinfo(SearchEmpInfo empinfo){
		return StaffConfigurationDAO.getInstance().searchEmpinfo(empinfo);
		
	}
	public EmployeeInfo editEmpinfo(String lpcNo){
		return StaffConfigurationDAO.getInstance().editEmpinfo(lpcNo);
	}
	
	public void updateEmpInfo(EmployeeInfo empinfo){
		System.out.println("inside Service");
		//StaffConfigurationDAO.getInstance().updateFinYear(finyearCd,finyearName,finyearDesc,fromDate,toDate);
		StaffConfigurationDAO.getInstance().updateEmpInfo(empinfo);
	}
	
	public void deleteEmpinfo(String lpcNo){
		StaffConfigurationDAO.getInstance().deleteEmpinfo(lpcNo);
	}
	
//	-------------------------Earnings----------------
	public void insertEarning(EarningMasterInfo earning) throws ApplicationException{
			StaffConfigurationDAO.getInstance().insertEarning(earning);
	}
	public SearchEarningInfo searchEarnings(SearchEarningInfo earninginfo){
		SearchEarningInfo list = StaffConfigurationDAO.getInstance().searchEarnings(earninginfo);
		return list;
	}
	
	public List getEarningNames(EarningMasterInfo earning){
		return StaffConfigurationDAO.getInstance().getEarningNames(earning);
	}
	
	public EarningMasterInfo showAddEarning(){
		return StaffConfigurationDAO.getInstance().showAddEarning();
	}
	public EarningMasterInfo showEditEarning(int earncd){
		return StaffConfigurationDAO.getInstance().showEditEarning(earncd);
	}
	public void updateEarning(EarningMasterInfo earnings) throws ApplicationException{
			StaffConfigurationDAO.getInstance().updateEarning(earnings);
	}
	public void deleteEarning(String earncds){
		StaffConfigurationDAO.getInstance().deleteEarning(earncds);
	}
	
	
public EmployeeEarningInfo editEmpEarnings(String empNo,String eardedType,String perRecType, int staffCategoryCd)throws  ApplicationException{
	EmployeeEarningInfo earninfo=null;
	earninfo=StaffConfigurationDAO.getInstance().editEmpEarnings(empNo,eardedType,perRecType,staffCategoryCd);
	if(earninfo==null)
	{
	throw new ApplicationException("");	
	}
	return StaffConfigurationDAO.getInstance().editEmpEarnings(empNo,eardedType,perRecType,staffCategoryCd);
}

public EmployeeInfo getEmpInfo(String lpcNo)throws ApplicationException{
	return StaffConfigurationDAO.getInstance().getEmpInfo(lpcNo);
}
		
	/******* Employee Deductions ************/
	public void addEmpDeductionInfo(EmployeeDeductionsInfo empdeductioninfo){
		System.out.println("inside Service");
		//StaffConfigurationDAO.getInstance().updateFinYear(finyearCd,finyearName,finyearDesc,fromDate,toDate);
		StaffConfigurationDAO.getInstance().addEmpDeductionInfo(empdeductioninfo);
	}
	
	/** EmployeePersonal Info **/
	public void addEmpPersonalinfo(EmployeePersonalInfo emppersonalinfo){
			
			StaffConfigurationDAO.getInstance().addEmpPersonalinfo(emppersonalinfo);
		}

	public EmployeePersonalInfo editEmpPersonalinfo(String empNo){
		return StaffConfigurationDAO.getInstance().editEmpPersonalinfo(empNo);
	}
	public void updateEmpPersonalInfo(EmployeePersonalInfo emppersonalinfo){
		System.out.println("inside Service");
		//StaffConfigurationDAO.getInstance().updateFinYear(finyearCd,finyearName,finyearDesc,fromDate,toDate);
		StaffConfigurationDAO.getInstance().updateEmpPersonalInfo(emppersonalinfo);
	}

	public void addEmpDependentsinfo(EmpDependentsInfo empdependentsinfo){
		
		StaffConfigurationDAO.getInstance().addEmpDependentsinfo(empdependentsinfo);
	}
	/** End EmployeePersonal Info **/
	/*
	 * Screen Name:Payscale
	 * Created By:Name:Srilakshmi
	 * Created Date:
	 */
	public void insertPayscale(PayscaleMasterInfo payscale) throws ApplicationException{
		
			StaffConfigurationDAO.getInstance().insertPayscale(payscale);
	}
	public List searchPayscales(PayscaleMasterInfo payscaleinfo){
		List list = StaffConfigurationDAO.getInstance().searchPayscales(payscaleinfo);
		return list;
	}
	/*public PayscaleMasterInfo showAddPayscale(){
		return StaffConfigurationDAO.getInstance().showAddPayscale();
	}
	public PayscaleMasterInfo loadCadres(int groupcd){
		return StaffConfigurationDAO.getInstance().loadCadres(groupcd);
	}*/
	public PayscaleMasterInfo showEditPayscale(String paycd){
		return StaffConfigurationDAO.getInstance().showEditPayscale(paycd);
	}
	public void updatePayscale(PayscaleMasterInfo psinfo){
		StaffConfigurationDAO.getInstance().updatePayscale(psinfo);
	}
	/*public void deletePayscale(String psinfo){
		StaffConfigurationDAO.getInstance().deletePayscale(psinfo);
	}*/
	/*
	 * Screen Name:Designation
	 * Created By:Name:Suresh Kumar 
	 * Created Date:19-Sep-07
	 */
	public List getAllCadres() throws Exception{
		List cadreslist=null;
		try {
			cadreslist = StaffConfigurationDAO.getInstance().getListOfCadres();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception getAllCadres() in Service>>>>>>>>>>>>"+ e.getMessage());
		}
		return cadreslist;
	}
	public List getStaffCadres4Desig() throws Exception{
		return StaffConfigurationDAO.getInstance().getStaffCadres4Desig();
	}
	public void saveDesignation(DesignationInfo addDesignation) throws ApplicationException{
		 try {
			StaffConfigurationDAO.getInstance().saveDesignation(addDesignation);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	public void updateDesignation(DesignationInfo editDesignation) throws ApplicationException{
		 try {
			StaffConfigurationDAO.getInstance().updateDesignation(editDesignation);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	public void deleteDesignation(String designationCode){
		 try {
			StaffConfigurationDAO.getInstance().deleteDesignation(designationCode);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception designationCode() in Service>>>>>>>>>>>>"+ e.getMessage());
		}
	}
	public DesignationInfo findByDesignationCd(DesignationInfo getDesignationInfo){
		DesignationInfo findDesignationInfo=new DesignationInfo();
		 try {
			 findDesignationInfo=StaffConfigurationDAO.getInstance().findByDesignationCode(getDesignationInfo);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveDesignation() in Service>>>>>>>>>>>>"+ e.getMessage());
		}
		return findDesignationInfo;
	}
	
	public List searchDesignation(SearchDesignationInfo getDesignationInfo){		
		List designationList=null;
		 try {
			 designationList= StaffConfigurationDAO.getInstance().searchDesignation(getDesignationInfo);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception saveDesignation() in Service>>>>>>>>>>>>"+ e.getMessage());
		}
		return designationList; 
	}
	/*
	 * Screen Name:Employee Information,Employee Personal Information,Deduction Master,Earning Master
	 * Created By:Name:Nagarjuna Reddy
	 * Created Date:
	 */
	public EmployeeDeductionsInfo editEmpDeductions(int empNo){
		return StaffConfigurationDAO.getInstance().editEmpDeductions(empNo);
	}
	
	public int getDeductionId(int lpcNo){
		 
		return StaffConfigurationDAO.getInstance().getDeductionId(lpcNo);
	}
	
	public int getEarningId(int lpcNo)throws ApplicationException{
		
		int earningId=StaffConfigurationDAO.getInstance().getEarningId(lpcNo);
		log.info("Earningid id in service"+earningId);
		if(earningId>0 ){
			log.info("inside if");
			throw new  ApplicationException();
		}

		return StaffConfigurationDAO.getInstance().getDeductionId(lpcNo);
	}
	
	public int getPersonalId(String lpcNo){
		return StaffConfigurationDAO.getInstance().getPersonalId(lpcNo);
	}
	
	public List editDependentsinfo(String empNo){
		return StaffConfigurationDAO.getInstance().editDependentsinfo(empNo);
	}
	public void updateEmpDependentsinfo(EmpDependentsInfo empdependentsinfo){
		System.out.println("inside Service");
		StaffConfigurationDAO.getInstance().updateEmpDependentsinfo(empdependentsinfo);
	}
	public EmpRecoveriesInfo editEmpRecoveries(int empNo){
		return StaffConfigurationDAO.getInstance().editEmpRecoveries(empNo);
	}
	public void addEmpDeductionInfo(EmployeeDeductionsInfo empdeductioninfo,EmpRecoveriesInfo emprecoveries){
		System.out.println("inside Service");
		//StaffConfigurationDAO.getInstance().updateFinYear(finyearCd,finyearName,finyearDesc,fromDate,toDate);
		StaffConfigurationDAO.getInstance().addEmpDeductionInfo(empdeductioninfo,emprecoveries);
	}
	public void updateEmpDeductions(EmployeeDeductionsInfo empdeductioninfo,EmpRecoveriesInfo emprecoveriesinfo){
		System.out.println("inside Service");
		//StaffConfigurationDAO.getInstance().updateFinYear(finyearCd,finyearName,finyearDesc,fromDate,toDate);
		StaffConfigurationDAO.getInstance().updateEmpDeductions(empdeductioninfo,emprecoveriesinfo);
	}
	
	public List getEarningInfo(String empno, int categoryCd)throws ApplicationException{
		return StaffConfigurationDAO.getInstance().getEarningInfo(empno,categoryCd);
		
	}
	
	  public void addEmpEarningsInfo(EmployeeEarningInfo empdependentsinfo){
			
			StaffConfigurationDAO.getInstance().addEmpEarningsInfo(empdependentsinfo);
		}
	  
	  public EmployeePerksInfo editPerks(int empNo)throws  ApplicationException{
			EmployeePerksInfo perksinfo=null;
			perksinfo=StaffConfigurationDAO.getInstance().editPerks(empNo);
			if(perksinfo==null)
			{
			throw new ApplicationException("");	
			}
			return StaffConfigurationDAO.getInstance().editPerks(empNo);
		}
	  
		public void updateEarningsInfo(EmployeeEarningInfo empearninginfo){
			System.out.println("inside Service");
			StaffConfigurationDAO.getInstance().updateEarningsInfo(empearninginfo);
		}
		public List getDeductionsInfo(int categoryCd)throws ApplicationException{
		return StaffConfigurationDAO.getInstance().getDeductionsInfo(categoryCd);
		
	}

		/**
		 * @return
		 */
		public List getEmpNo() {
			return StaffConfigurationDAO.getInstance().getEmpNo();
		}

		/**
		 * @return
		 */
		public boolean checkData(String empno) {
			return StaffConfigurationDAO.getInstance().checkData(empno);
		}

		public PayscaleMasterInfo getBasicPayRange(String empno)throws  ApplicationException {
			return 	StaffConfigurationDAO.getInstance().getBasicPayRange(empno);
			
		}

		public List getAllEarnDedTypes(int categoryCd) {
			return StaffConfigurationDAO.getInstance().getAllEarnDedTypes(categoryCd);
		}

		public void savePayStopConfig(PayStopConfigInfo pay) throws ApplicationException {
			StaffConfigurationDAO.getInstance().savePayStopConfig(pay);
		}

		public boolean checkStaffData(int categoryCd) {
			return StaffConfigurationDAO.getInstance().checkStaffData(categoryCd);
		}

		public PayStopConfigInfo getEditTypes(int categoryCd) {
			return StaffConfigurationDAO.getInstance().getEditTypes(categoryCd);
		}

		public void updatePayStopConfig(PayStopConfigInfo pay) throws ApplicationException {
			StaffConfigurationDAO.getInstance().updatePayStopConfig(pay);
		}

		public void checkTaxData(EmployeeIncomeTaxInfo info) throws Exception {
			 StaffConfigurationDAO.getInstance().checkTaxData(info);
		}
		public List getCCSEmployees(String staffctgry){
			return StaffConfigurationDAO.getInstance().getCCSEmployees(staffctgry);
		}
		public void updateEmpCcs(List emplist) throws ApplicationException{
			StaffConfigurationDAO.getInstance().updateEmpCcs(emplist);
		}
		public String getDaPercentage(String staffctgry) {
			return StaffConfigurationDAO.getInstance().getDaPercentage(staffctgry);
		}
		public List getPayrollMonths4DAHike() {
			return StaffConfigurationDAO.getInstance().getPayrollMonths4DAHike();
		}
		public void saveDaPercentage(DAHikeInfo info) throws ApplicationException {
			StaffConfigurationDAO.getInstance().saveDaPercentage(info);
		}
		public void processDaPercentage(DAHikeInfo info) throws ApplicationException {
			StaffConfigurationDAO.getInstance().processDaPercentage(info);
		}
		public List searchDaPercentage(String cd) {
			return StaffConfigurationDAO.getInstance().searchDaPercentage(cd);
		}
		public DAHikeInfo showDAFormEdit(String cd) {
			return StaffConfigurationDAO.getInstance().showDAFormEdit(cd);
		}
		public void updateDaPercentage(DAHikeInfo info) throws ApplicationException {
			StaffConfigurationDAO.getInstance().updateDaPercentage(info);
		}

		public void checkITRecData(EmployeeIncomeTaxInfo ainfo)  throws Exception {
			StaffConfigurationDAO.getInstance().checkITRecData(ainfo);
		}
		public List getGSLISCadres(String staffctgry){
			return StaffConfigurationDAO.getInstance().getGSLISCadres(staffctgry);
		}
		public List getEmpEarndedValues(String empno,String type){
			return StaffConfigurationDAO.getInstance().getEmpEarndedValues(empno,type);
		}

		public void saveEmpEditTransDetails(List l,String type,String empno,String hraopt,String petrol,String desgcode){
			StaffConfigurationDAO.getInstance().saveEmpEditTransDetails(l,type,empno,hraopt,petrol,desgcode);
		}
		public List getDesignations(){
			return StaffConfigurationDAO.getInstance().getDesignations();
		}
		public void addEmpSeperationInfo(EmployeePersonalInfo emppersonalinfo) throws ApplicationException {
			StaffConfigurationDAO.getInstance().addEmpSeperationInfo(emppersonalinfo);
		}
		public List showEmpSeperationInfo(EmployeePersonalInfo emppersonalinfo) throws ApplicationException {
			return StaffConfigurationDAO.getInstance().showEmpSeperationInfo(emppersonalinfo);
		}
		public List getSalaryHeads(String cd,String empno) {
			return StaffConfigurationDAO.getInstance().getSalaryHeads(cd,empno);
		}
		public List getEmployeesforSalHeads(String[] cds,String eno,String paybillcd) {
			return StaffConfigurationDAO.getInstance().getEmployeesforSalHeads(cds,eno,paybillcd);
		}
		public String saveEmpSalHeadAmts(String cd,String amt,String ucd,String earncd,String empno) {
			return StaffConfigurationDAO.getInstance().saveEmpSalHeadAmts(cd,amt,ucd,earncd,empno);
		}
		public Map getEmployeesforSalHeadsForm(String fyear,String empno,String[] earndeduCd) {
			return StaffConfigurationDAO.getInstance().getEmployeesforSalHeadsForm(fyear,empno,earndeduCd);
		}
		public List getPaybillNames(){
			return StaffConfigurationDAO.getInstance().getPaybillNames();
		}
		public List getEmployeePerks(String empno){
			return StaffConfigurationDAO.getInstance().getEmployeePerks(empno);
		}
		public void savePerks(List plist)throws ApplicationException{
			 StaffConfigurationDAO.getInstance().savePerks(plist);
		}
		public void processPerks(String payrollmonthid,String station,String fyearcd)throws ApplicationException{
			StaffConfigurationDAO.getInstance().processPerks(payrollmonthid,station,fyearcd); 
		}
		public List searchPerksPrcess(String payrollmonthid,String station){
			return StaffConfigurationDAO.getInstance().searchPerksPrcess(payrollmonthid,station);
		}
		public List viewPerksPaySlip(PaySlipInfo psinfo){
			return StaffConfigurationDAO.getInstance().viewPerksPaySlip(psinfo);
		}

		public List getPaybillStationWise(String substationCd) {
			return StaffConfigurationDAO.getInstance().getPaybillStationWise(substationCd);
		}

		public List getQuarterType() {
			return StaffConfigurationDAO.getInstance().getQuarterType();
			
		}

		public EmployeeInfo getWaterRec(String quarter) {
			return StaffConfigurationDAO.getInstance().getWaterRec(quarter);
		}
		public ArrayList getPersonalInfo(String type)
		{
			return StaffConfigurationDAO.getInstance().getPersonalInfo(type);
		}
		public List getReportValues(String values,String type,String no)
		{
			return StaffConfigurationDAO.getInstance().getReportValues(values,type,no);
		}
		public StringBuffer ajaxEmployeeCodeDuplicate(String employeeCode) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxEmployeeCodeDuplicate(employeeCode);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		public StringBuffer ajaxEmployeeDesigDuplicate(String employeeCode) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxEmployeeDesigDuplicate(employeeCode);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		public StringBuffer ajaxLedgerCdDuplicate(String ledgCode,String groupacccd,String acctypecd) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxLedgerCdDuplicate(ledgCode,groupacccd,acctypecd);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		public StringBuffer ajaxEarningDuplicate(String earningName) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxEarningDuplicate(earningName);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		
		public StringBuffer ajaxCategoryNameDuplicate(String CategoryName) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxCategoryNameDuplicate(CategoryName);
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		public StringBuffer ajaxCaderDuplicate(String caderName) throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxCaderDuplicate(caderName);
			}catch (Exception e) {
				throw new Exception(e.getMessage()); 
			}
		}
		public int getBasicSalFlag(){
			return StaffConfigurationDAO.getInstance().getBasicSalFlag();
		}

		public StringBuffer ajaxBasicFlagFlags() throws Exception {
			try {
				return StaffConfigurationDAO.getInstance().ajaxBasicFlagFlags();
			}catch (Exception e) {
				throw new Exception(e.getMessage()); 
			}
		}
}
