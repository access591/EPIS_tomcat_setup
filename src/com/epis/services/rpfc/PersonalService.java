package com.epis.services.rpfc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeAdlPensionInfo;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.NomineeBean;
import com.epis.bean.rpfc.NomineeForm2Info;
import com.epis.bean.rpfc.RPFCForm9Bean;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.bean.rpfc.SignatureMappingBean;
import com.epis.dao.rpfc.CommonDAO;
import com.epis.dao.rpfc.PersonalDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.Pagenation;

public class PersonalService {
    Log log = new Log(PersonalService.class);

    PersonalDAO personalDAO = new PersonalDAO();

    Pagenation paging = new Pagenation();

    CommonUtil commonUtil = new CommonUtil();

    CommonDAO commonDAO = new CommonDAO();
    /*
	 * Below Code done by Saibaba
	 * */
    public int updatePension(EmpMasterBean bean)throws InvalidDataException
    {
    	int count=0;
    	try{
    		count=personalDAO.updatePension(bean);
    	}
    	catch(InvalidDataException ide)
    	{
    		throw ide;
    	}
    	return count;
    }
	  public ArrayList nomineeRequestResult(String psfNo,String reqDate)
	    {
	    	ArrayList arl=null;
	    	try{
	    		arl=personalDAO.nomineeRequestResult(psfNo,reqDate);
	    	}
	    	catch(Exception ide)
	    	{
	    		log.error(ide.getMessage());
	    	}
	    	return arl;
	    }
	    public ArrayList getNomineeStatusList(String pfid)
	    {
	    	ArrayList arl=null;
	    	try{
	    		arl=personalDAO.getNomineeStatusList(pfid);
	    	}
	    	catch(Exception e)
	    	{
	    		log.error(e.getMessage());
	    	}
	    	return arl;
	    }
	    public EmpMasterBean empPensionEdit( String pfid,String flag)
	    throws Exception {
	    			EmpMasterBean editbean = null;
	    			editbean = personalDAO.empPensionEdit(pfid,flag);
	    			return editbean;
	    }
	    public int nomineeRequestUpdate(EmpMasterBean bean) {
	    	int count=0;
	    	try{
	    		count=personalDAO.nomineeRequestUpdate(bean);
	    	}catch(Exception e){
	    		log.error(e.getMessage());
	    	}
	    	return 0;
	    }
	    /*End of Nominee Screen done by saibaba*/
    public String autoProcPersonalInfo(String selectedDt, String retriedDate,
			String region, String airportCode, String userName, String IPAddress) {
		String message = "", regionMessage = "";
		int regionSize = 0, OrinialSize = 0;
		boolean chkMnthYearFlag = false;
		HashMap regionMap = new HashMap();
		/*
		 * HashMap tempRegionMap = new HashMap(); if
		 * (region.equals("NO-SELECT")) { regionMap = commonUtil.getRegion();
		 * tempRegionMap = commonUtil.getRegion(); } else { regionMap.put("1",
		 * region); tempRegionMap.put("1", region); }
		 * 
		 * regionSize = tempRegionMap.size(); Set set =
		 * tempRegionMap.entrySet(); Iterator iter = set.iterator(); OrinialSize
		 * = regionSize; while (iter.hasNext()) { Map.Entry me = (Map.Entry)
		 * iter.next(); log.info("Region========" + me.getKey() + "==" +
		 * me.getValue()); region = me.getValue().toString(); chkMnthYearFlag =
		 * personalDAO.checkRefMnthYear(selectedDt, region); if (chkMnthYearFlag
		 * == false) { regionMap.remove(me.getKey()); regionSize--; }
		 * 
		 * }
		 */

		log.info("OrinialSize======" + OrinialSize + "After Find out"
				+ regionSize);

		try {

			message = personalDAO.autoProcessingPersonalInfo(selectedDt,
					retriedDate, regionMap, airportCode, userName, IPAddress);
		} catch (IOException e) {
			log.printStackTrace(e);
		}

		return message;
	}

	public SearchInfo searchPersonalMaster(EmployeePersonalInfo bean,
			SearchInfo searchInfo, boolean flag, int gridLength,
			String sortingColumn, String page) throws Exception {
		log.info("===========Enter searchPersonalMaster====================");
		int startIndex = 0;
		ArrayList empInfo = new ArrayList();
		startIndex = 1;
		empInfo = personalDAO.searchPersonal(bean, startIndex, gridLength,
				sortingColumn, "non-count", page);
		log.info("emp list size " + empInfo.size());
		int totalRecords = personalDAO.totalCountPersonal(bean, "count",
				sortingColumn, page);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(empInfo);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		log.info("=============EndsearchPersonalMaster====================");
		return searchInfo;

	}
	public String  loadNomineeDetails(String employeeName,String region,String pensionNo,String monthYear){
		String nomineeInfo=personalDAO.loadNomineeDetails(employeeName,region,pensionNo,monthYear);
		return nomineeInfo;
	}
	public SearchInfo navigationPersonalData(EmployeePersonalInfo empBean,
			SearchInfo searchInfo, int gridLength, String sortingColumn,
			String page) throws Exception {
		int startIndex = 1;
		String navButton = "";
		ArrayList hindiData = new ArrayList();
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}
		if (new Integer(searchInfo.getStartIndex()) != null) {
			startIndex = searchInfo.getStartIndex();
		}
		if (sortingColumn.equals("")) {
			sortingColumn = "employeename";
		}
		int rowCount = personalDAO.totalCountPersonal(empBean, "count",
				sortingColumn, page);
		System.out.println("rowCount" + rowCount + "startIndex" + startIndex
				+ "navButton" + navButton);
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount,
				gridLength);
		hindiData = personalDAO.searchPersonal(empBean, startIndex, gridLength,
				sortingColumn, "non-count", page);
		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public ArrayList personalReport(EmployeePersonalInfo empBean,
			String sortingColumn) {
		ArrayList personalReport = new ArrayList();
		personalReport = personalDAO.personalReport(empBean, "non-count", "",
				sortingColumn);
		return personalReport;

	}

	public ArrayList getDepartmentList() {
		ArrayList departmentList = commonDAO.getDepartmentList();
		return departmentList;
	}

	public ArrayList getDesginationList() {
		ArrayList departmentList = commonDAO.getDesignationList();
		return departmentList;
	}

	public EmpMasterBean empPersonalEdit(String cpfacno, String empName,
			boolean flag, String empCode, String region, String pfid)
			throws Exception {
		EmpMasterBean editbean = null;
		editbean = personalDAO.empPersonalEdit(cpfacno, empName, flag, empCode,
				region, pfid);
		return editbean;
	}

	public int updatePensionMaster(EmpMasterBean bean, String flag)
			throws InvalidDataException {

		int count = 0;
		String empLevel = "";
		EmpMasterBean desegBean = new EmpMasterBean();
		empLevel = bean.getEmpLevel();
		desegBean = personalDAO.getDesegnation(empLevel);
		log.info("bean.getDesegnation().trim()" + bean.getDesegnation());
		log.info("desegBean " + desegBean);
		/*
		 * if (!desegBean.getDesegnation().trim().equals(
		 * bean.getDesegnation().trim())) { throw new InvalidDataException( "Emp
		 * Level with respective Desegnation is not equal"); }
		 */
		System.out.println("flag=(updatePensionMaster)=" + flag);

		try {
			count = personalDAO.personalUpdate(bean, flag);
		} catch (InvalidDataException ide) {
			throw ide;
			// throw new InvalidDataException("PensionNumber Already Exist");
		}
		return count;
	}

	public ArrayList checkPersonalInfo(EmployeePersonalInfo personalInfo,
			boolean empFlag, boolean dobFlag) {
		ArrayList list = new ArrayList();
		list = personalDAO.checkPersonalDtOfBirthInfo(personalInfo, empFlag,
				dobFlag);
		return list;
	}

	public String addPersonalInfo(EmployeePersonalInfo personalInfo,
			EmpMasterBean bean, String userName, String ipAddress)
			throws Exception {
		String addRecord = "";
		try {
			addRecord = personalDAO.addPersonalInfo(personalInfo,bean,
					userName, ipAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return addRecord;
	}
	public NomineeForm2Info form2Report(EmployeePersonalInfo personalInfo) {
		ArrayList personalList = new ArrayList();
		ArrayList nomineeList = new ArrayList();
		ArrayList familyList = new ArrayList();
		NomineeForm2Info form2Bean = new NomineeForm2Info();
		personalList = personalDAO.personalReport(personalInfo, "non-count",
				"", "PENSIONNO");
		form2Bean.setPersonalList(personalList);
		nomineeList = personalDAO.form2NomineeReport(personalInfo);
		form2Bean.setNomineeList(nomineeList);
		familyList = personalDAO.form2FamilyReport(personalInfo);
		form2Bean.setFamilyList(familyList);
		return form2Bean;

	}

	public RPFCForm9Bean form9Report(EmployeePersonalInfo personalInfo) {
		RPFCForm9Bean personalList = new RPFCForm9Bean();
		personalList = personalDAO.rpfcForm9Report(personalInfo, "non-count",
				"", "PENSIONNO");
		return personalList;
	}

	public String autoUpdatePersonalInfo(String selectedDt, String retriedDate,
			String region, String airportCode, String userName, String IPAddress) {
		String message = "", regionMessage = "";
		int regionSize = 0, OrinialSize = 0;
		boolean chkMnthYearFlag = false;
		HashMap regionMap = new HashMap();

		log.info("OrinialSize======" + OrinialSize + "After Find out"
				+ regionSize);

		try {

			message = personalDAO.autoUpdateProcessingPersonalInfo(selectedDt,
					retriedDate, regionMap, airportCode, userName, IPAddress);
		} catch (IOException e) {
			log.printStackTrace(e);
		}

		return message;
	}

	public int updatePFIDTrans(String range, String region,
			String selectedYear, String empflag, String empName,
			String pensionno) {
		String infoOnUpdtedRec = "", uploadFilePath = "", fileName = "", dispRange = "";
		int cntr = 0;
		FileWriter fw = null;
		ResourceBundle bundle = ResourceBundle
				.getBundle("aims.resource.DbProperties");
		uploadFilePath = bundle.getString("upload.folder.path");
		File filePath = new File(uploadFilePath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		if (region.equals("NO-SELECT")) {
			region = "";
		}
		if (range.equals("NO-SELECT")) {
			dispRange = "";
		} else {
			dispRange = range;
		}
		fileName = "//PFIDInformation" + region + "-" + dispRange + ".txt";
		try {
			fw = new FileWriter(new File(filePath + fileName));
			personalDAO.updatePFIDTransTbl(range, region, empflag, empName,
					pensionno, fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cntr;
	}

	public ArrayList form10DInfo(String pensionNo) {
		ArrayList form10DList = new ArrayList();
		form10DList = personalDAO.getForm10DInfo(pensionNo);
		return form10DList;
	}

	public int updateForm10DInfo(EmpMasterBean bean,
			EmployeeAdlPensionInfo empAdlPensionInfo)
			throws InvalidDataException {

		int count = 0;

		try {
			count = personalDAO.updateForm10D(bean, empAdlPensionInfo);
		} catch (InvalidDataException ide) {
			throw ide;
			// throw new InvalidDataException("PensionNumber Already Exist");
		}
		return count;
	}

	public EmpMasterBean empForm10DPersonalEdit(String cpfacno, String empName,
			String region, String pfid) throws Exception {
		EmpMasterBean editbean = null;
		editbean = personalDAO.empForm10DPersonalEdit(cpfacno, empName, region,
				pfid);
		return editbean;
	}

	public EmployeeAdlPensionInfo getPensionForm10DDtls(String pfid) {
		EmployeeAdlPensionInfo addPensionInfo = new EmployeeAdlPensionInfo();
		addPensionInfo = personalDAO.getPensionForm10DDtls(pfid);
		return addPensionInfo;

	}
//	New Method
	public void getImage( String path, String userID) throws Exception {
		personalDAO.getImage(path,userID);
	}
	
	// New method
	public void uploadSignature(SignatureMappingBean signBean,String screenType)throws Exception{	 	 
		personalDAO.uploadSignature(signBean,screenType);	  
	}
	
//	 New method
	public void saveScreenSignDetails(SignatureMappingBean signBean,String screenType)throws Exception{	 	 
		personalDAO.saveScreenSignDetails(signBean,screenType);	  
	}
	 
	
//New Method
	public ArrayList editSignatures(String userID)throws Exception{	 
		ArrayList searchList = new ArrayList();
		searchList=personalDAO.editSignatures(userID);
		return searchList;
	}
	public ArrayList searchSignatures(String userID){
		ArrayList searchList = new ArrayList(); 	  
		searchList=personalDAO.searchSignatures(userID); 
		return searchList;
	} 


}
