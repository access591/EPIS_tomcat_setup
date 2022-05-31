 /**
  * File       : FinancialService.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.services.rpfc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.bean.rpfc.CompartiveReportAaiDataBean;
import com.epis.bean.rpfc.CompartiveReportBean;
import com.epis.bean.rpfc.CompartiveReportForm3DataBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeValidateInfo;
import com.epis.bean.rpfc.FinaceDataAvailable;
import com.epis.bean.rpfc.FinacialDataBean;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.bean.rpfc.form3Bean;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.dao.rpfc.FinancialDAO;
import com.epis.dao.rpfc.FinancialReportDAO;

import com.epis.utilities.Pagenation;


public class FinancialService {
	
	Log log = new Log(PensionService.class);

	FinancialDAO financialDAO=new FinancialDAO();
	Pagenation paging = new Pagenation();
	CommonUtil common = new CommonUtil();
	 FinancialReportDAO finReportDAO = new FinancialReportDAO();

	 public Map getPensionYearList(){
			Map yearMapList=new LinkedHashMap();
			yearMapList=financialDAO.getYearPension();
			return yearMapList;
			
		}
		public ArrayList getCpfData(String pfid,String year,String month,String Region,String cpfacno){
		    ArrayList a =null;
		    a=finReportDAO.getCpfData(pfid,year,month,Region);
			return a;
		}
		public Map getPensionAirportList(){
			Map airportMapList=new LinkedHashMap();
			airportMapList=financialDAO.getPensionAirportList();
			return airportMapList;
			
		}
		
		public SearchInfo getFinancialData(String monthYear,String unitCode,int gridLength){
			String finSelectedDt="";
			ArrayList financialList=new ArrayList();
			SearchInfo searchInfo = new SearchInfo();
			int startIndex = 0,count=0;
			log.info("============(getFinancialData)===========");
			try {
				finSelectedDt=common.converDBToAppFormat(monthYear,"yyyyMM","MMM-yy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startIndex = 1;
			Pagenation finsearpaging = new Pagenation();
			financialList=financialDAO.financialDataAll(finSelectedDt,startIndex,unitCode);
			count=financialDAO.financialDataCountAll(finSelectedDt,unitCode);
			log.info("============(getFinancialData)======count====="+count);
			BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
			bottomGridNavigationInfo = finsearpaging.searchPagination(count,
					startIndex,gridLength);
			log.info("============startIndex(getFinancialData)===========" + startIndex);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(financialList);
			searchInfo.setTotalRecords(count);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			return searchInfo;
		}
		public EmployeeValidateInfo getFinancialLoadEditDetails(String cpfaccno,String unitCode,String effectiveDate){
			String finSelectedDt="";
			EmployeeValidateInfo financialLodBean=new EmployeeValidateInfo();
			
			financialLodBean=financialDAO.getPensionValidateDetails(cpfaccno,unitCode,effectiveDate);
			return financialLodBean;
			
		}
		public int updateFinancialDetails(String cpfaccno,String unitCode,String valPF,String valPension,String valTotal,String pfStatury,String pfDedcution,String userName,String effectiveDate){
			int updateVal=0;
			
			
			
			updateVal=financialDAO.updatePensionValidateDetails(cpfaccno,unitCode,valPF,valPension,valTotal,pfStatury,pfDedcution,userName,effectiveDate);
			return updateVal;
			
		}
		
		public void addFinancialDetals(EmpMasterBean bean){
			
			financialDAO.addFinancialDetals(bean);
			
		}
		
		public SearchInfo searchFinanceData(FinacialDataBean databean,
				SearchInfo searchInfo,int gridLength) throws Exception {
			int startIndex = 0;
			ArrayList hindiData = new ArrayList();
			System.out.println("searchInfo.getNavigation()"
					+ searchInfo.getNavigation());
			startIndex = 1;
			hindiData = financialDAO.SearchFinanceAllMonths(databean, startIndex);
			int totalRecords = financialDAO.totalDataForAllMonths(databean);
			log.info("recordlist " +hindiData);
	        log.info("totalRecords " +totalRecords);
			BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
			bottomGridNavigationInfo = paging.searchPagination(totalRecords,startIndex,gridLength);
			System.out.println("startIndex(searchPensionData)" + startIndex);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(hindiData);
			searchInfo.setTotalRecords(totalRecords);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			
		//	hindiData = financialDAO.SearchFinanceAllMonths(databean,);
			
			log.info("recordlist " +hindiData);
	    
		
			searchInfo.setSearchList(hindiData);
			
			return searchInfo;

		}
		
		public SearchInfo searchNavigationFinanceData(FinacialDataBean databean,
				SearchInfo searchInfo,int gridLength) throws Exception {
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
			int rowCount = searchInfo.getTotalRecords();
			System.out.println("rowCount" + rowCount + "startIndex" + startIndex
					+ "navButton" + navButton);
			startIndex = paging.getPageIndex(navButton, startIndex, rowCount,gridLength);
			hindiData = financialDAO.SearchFinanceAll(databean, startIndex);
			bottomGridNavigationInfo = paging.navigationPagination(rowCount,
					startIndex, false, false,gridLength);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(hindiData);
			searchInfo.setTotalRecords(rowCount);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			return searchInfo;

		}
		public SearchInfo navigationFinancialData(String monthYear,String unitCode,
				SearchInfo searchInfo,int gridLength) {
			int startIndex=0;
			String navButton = "",finSelectedDt="";
			ArrayList hindiData = new ArrayList();
			BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
			if (searchInfo.getNavButton() != null) {
				navButton = searchInfo.getNavButton();
			}
			if (new Integer(searchInfo.getStartIndex()) != null) {
				startIndex = searchInfo.getStartIndex();
			}
			int rowCount = searchInfo.getTotalRecords();

			try {
				finSelectedDt=common.converDBToAppFormat(monthYear,"yyyyMM","MMM-yy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Pagenation finpaging = new Pagenation();
			startIndex = finpaging.getPageIndex(navButton, startIndex, rowCount,gridLength);
			log.info("rowCount=========navigationFinancialData=========" + rowCount + "startIndex" + startIndex
					+ "navButton" + navButton);
			hindiData = financialDAO.financialDataAll(finSelectedDt,startIndex,unitCode);
			bottomGridNavigationInfo = finpaging.navigationPagination(rowCount,
					startIndex, false, false,gridLength);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(hindiData);
			searchInfo.setTotalRecords(rowCount);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			return searchInfo;

		}
		public EmployeeValidateInfo getLoadFinancialEditDetails(String cpfaccno,String pensionno,
				String unitCD,String effectiveDate,String employeeNo,String designation,String employeeName,String region){
			EmployeeValidateInfo validateBean=new EmployeeValidateInfo();
			validateBean=financialDAO.getFinanceEditDetails(cpfaccno,pensionno,unitCD,effectiveDate,employeeNo,designation,employeeName,region);
			return validateBean;
		}
		public int financialUpdateDetails(EmployeeValidateInfo validateUpdateInfo,String airportCD,String effectiveDate,String pensionno,String cpfacno,String employeeName,String userID,String computerName,String region){
			int count=0;
			EmployeeValidateInfo validateBean=new EmployeeValidateInfo();
			count=financialDAO.updateFinanceDetail(validateUpdateInfo,airportCD,effectiveDate,pensionno,cpfacno,employeeName,userID,computerName,region);
			return count;
		}
		
		public ArrayList getFinanceListAll(String fromDate,String toDate) throws InvalidDataException{
			ArrayList list=new ArrayList();
			fromDate=common.converDBToAppFormat(fromDate,"dd/MM/yyyy","dd-MMM-yyyy");
			
			toDate=common.converDBToAppFormat(toDate,"dd/MM/yyyy","dd-MMM-yyyy");
			log.info("getFinanceListAll=================="+fromDate+"toDate"+toDate);
			list=financialDAO.getFinaanceDataAllReport(fromDate,toDate);
			return list;
			
		}

		public ArrayList financeReportByEachEmp(String cpfaccno,String employeeno){
			ArrayList financialrptList=new ArrayList();
			financialrptList=financialDAO.financeReportByEachEmp(cpfaccno,employeeno);
			return financialrptList;
		}
		
		public ArrayList financeValidateReport(String monthYear,String unitCode){
			ArrayList financialrptList=new ArrayList();
			String finSelectedDt="";
			try {
				finSelectedDt=common.converDBToAppFormat(monthYear,"yyyyMM","MMM-yy");
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			financialrptList=financialDAO.financeValidateReport(finSelectedDt,unitCode);
			
			return financialrptList;
		}
		public ArrayList getFinancalInfo(String region){
			ArrayList financialPersonalList=new ArrayList();
			financialPersonalList=financialDAO.getFinancalInfo(region);
			return financialPersonalList;
		}

		public ArrayList financeReportMissingMonthsByEachEmp(String cpfaccno,String employeeno,String region){
		ArrayList financialMissingList=new ArrayList();
		log.info("financeReportMissingMonthsByEachEmp=======Service====employeeNo========"+employeeno);
		financialMissingList=financialDAO.financeReportMissingMonthsByEachEmp(cpfaccno,employeeno,region);
		return financialMissingList;
	}
		public SearchInfo getFinancialReport(String region,String flag,int gridLength){
			int startIndex = 0,count=0;
			ArrayList financialPersonalList=new ArrayList();
			SearchInfo searchInfo = new SearchInfo();
			startIndex = 1;
			Pagenation finsearpaging = new Pagenation();
			financialPersonalList=financialDAO.getFinancalInfo(region,flag,startIndex,gridLength);
			count=financialDAO.getFinancalInfoCount(region);
			log.info("============(getFinancalReport)======count====="+count);
			BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
			bottomGridNavigationInfo = finsearpaging.searchPagination(count,
					startIndex,gridLength);
			log.info("============startIndex(getFinancalReport)===========" + startIndex);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(financialPersonalList);
			searchInfo.setTotalRecords(count);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			return searchInfo;
		}
		
		public SearchInfo getnavigationFinancialReport(String region,String flag,int gridLength,SearchInfo searchInfo) {
			int startIndex=0;
			String navButton = "",finSelectedDt="";
			ArrayList financialReportList = new ArrayList();
			BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
			if (searchInfo.getNavButton() != null) {
				navButton = searchInfo.getNavButton();
			}
			if (new Integer(searchInfo.getStartIndex()) != null) {
				startIndex = searchInfo.getStartIndex();
			}
			int rowCount = searchInfo.getTotalRecords();

		
			Pagenation finpaging = new Pagenation();
			startIndex = finpaging.getPageIndex(navButton, startIndex, rowCount,gridLength);
			log.info("rowCount=========getnavigationFinancialReport=========" + rowCount + "startIndex" + startIndex
					+ "navButton" + navButton);
			financialReportList = financialDAO.getFinancalInfo(region,flag,startIndex,gridLength);
			bottomGridNavigationInfo = finpaging.navigationPagination(rowCount,
					startIndex, false, false,gridLength);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(financialReportList);
			searchInfo.setTotalRecords(rowCount);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
			return searchInfo;
		}
		
		public ArrayList getPensionAirportList(String region,String date){
			ArrayList airportList=new ArrayList();
			airportList=financialDAO.getPensionAirportList(region,date);
			return airportList;
			
		}
	    public ArrayList financeForm3Report(String airportCode,String date,String region,String retriedDate,String sortingOrder,String empName,String empNameFlag){
	        ArrayList form3tList=new ArrayList();
	        form3tList=financialDAO.financeForm3Report(airportCode,date,region,retriedDate,sortingOrder,empName,empNameFlag);
	        return form3tList;
	    }
		public ArrayList financeDataAvailableCheck(String airportCode,String date,String region,String retriedDate,String sortingOrder){
			ArrayList form3tList=new ArrayList();
			//form3tList=financialDAO.financeForm3Report(airportCode,date,region,retriedDate,sortingOrder);
	        form3tList=financialDAO.financeForm3Report(airportCode,date,region,retriedDate,sortingOrder,"","");
			return form3tList;
		}
		
		public int[]  financeForm6BCountData(String airportCode,String date,String region,String prevDate){
			return financialDAO.financeForm6BCountData(airportCode,date,region,prevDate);
		}
		
		public int addRemittanceInfo(form3Bean formbean){
			//ArrayList form6BList=new ArrayList();
			int i = financialDAO.addRemittanceInfo(formbean);
			return i;
		}
		
		/*public ArrayList financeForm45Report(String airportCode,String date1,String date2,String region){
			ArrayList form45tList=new ArrayList();
			form45tList=financialDAO.financeForm45Report(airportCode,date1,date2,region);
			return form45tList;
		}*/
		
		 ArrayList pensionList=new ArrayList();
			ArrayList AAIList=new ArrayList();
			
		public CompartiveReportBean getPensionValidateData(String region){		

			pensionList=financialDAO.getPensionValidateData(region);			
			AAIList=financialDAO.getAAIPensionData(region); 
			CompartiveReportBean compartiveReport=new CompartiveReportBean();
			CompartiveReportForm3DataBean compartiveReportform3info=null;
			CompartiveReportAaiDataBean compartiveReportAaiinfo=null;
			log.info(".........getPensionValidateData in service ..........."+pensionList.size());		
			log.info(".........getAAIPensionData in service ..........."+AAIList.size());
			
			ArrayList commonList=new ArrayList();
			ArrayList commonAAIList=new ArrayList();
			ArrayList commonPensionList=new ArrayList();
			ArrayList temppensionList=new ArrayList(pensionList.size());
			temppensionList.addAll(pensionList);
			ArrayList tempAAIList=new ArrayList(AAIList.size());
			tempAAIList.addAll(AAIList);	
			String flag="";
			if((pensionList.size()>0) || (AAIList.size()>0))
			   {
				     if(pensionList.size()>AAIList.size()){
		                  log.info("pensionList is greater");
						  flag="pension";

						  for(int i=0;i<pensionList.size();i++)
					      {	
						      for(int j=0;j<AAIList.size();j++)
						      {    
								   compartiveReportform3info=new CompartiveReportForm3DataBean();
								   compartiveReportAaiinfo=new CompartiveReportAaiDataBean();
								   compartiveReportform3info=(CompartiveReportForm3DataBean)pensionList.get(i);
								   compartiveReportAaiinfo=(CompartiveReportAaiDataBean)AAIList.get(j);
	                            	if(( compartiveReportform3info.getForm3CPFaccno()).equals(compartiveReportAaiinfo.getAaiCPFaccno()))
							        {   commonList.add(pensionList.get(i));
										commonAAIList.add(AAIList.get(j));
	                                    AAIList.remove(AAIList.get(j));	
										temppensionList.remove(pensionList.get(i));	
							        }															
							  }
	          			  }	
						 log.info(".............commonList size.................."+commonList.size());
						 log.info(".............AAIList size.................."+AAIList.size());
						 log.info(".............temppensionList size.................."+temppensionList.size());
						 compartiveReport.setCommonList(commonList);
	                     compartiveReport.setAaiList(AAIList);
					     compartiveReport.setCommonAaiList(commonAAIList);
					     compartiveReport.setForm3List(temppensionList);
						 compartiveReport.setFlag(flag);
		 }	 else if(AAIList.size()>pensionList.size()){
						  log.info("AAIList is greater");
						   flag="aai";

						   for(int i=0;i<AAIList.size();i++)
					      {	
						      for(int j=0;j<pensionList.size();j++)
						      {    
								   compartiveReportform3info=new CompartiveReportForm3DataBean();
								   compartiveReportAaiinfo=new CompartiveReportAaiDataBean();
								   compartiveReportform3info=(CompartiveReportForm3DataBean)pensionList.get(j);
								   compartiveReportAaiinfo=(CompartiveReportAaiDataBean)AAIList.get(i);
	                            	if(( compartiveReportform3info.getForm3CPFaccno()).equals(compartiveReportAaiinfo.getAaiCPFaccno()))
							        {																		

									    commonList.add(AAIList.get(i));
										commonPensionList.add(pensionList.get(j));
	                                    pensionList.remove(pensionList.get(j));	
										tempAAIList.remove(AAIList.get(i));
								   }															
							  }
							  
						  }	
						  log.info(".............commonList size.................."+commonList.size());
						  log.info(".............commonPensionList size.................."+commonPensionList.size());
						  log.info(".............pensionList size.................."+pensionList.size());
						 log.info(".............tempAAIList size.................."+tempAAIList.size());

						 compartiveReport.setCommonList(commonList);
	                     compartiveReport.setAaiList(tempAAIList);
					     compartiveReport.setCommonAaiList(commonPensionList);
					     compartiveReport.setForm3List(pensionList);
						  compartiveReport.setFlag(flag);



					 }else{
						  flag="equal";
						  log.info("Both lists are equal");

						  
						  for(int i=0;i<pensionList.size();i++)
					      {	
						      for(int j=0;j<AAIList.size();j++)
						      {    
								   compartiveReportform3info=new CompartiveReportForm3DataBean();
								   compartiveReportAaiinfo=new CompartiveReportAaiDataBean();

								   compartiveReportform3info=(CompartiveReportForm3DataBean)pensionList.get(i);
								  compartiveReportAaiinfo=(CompartiveReportAaiDataBean)AAIList.get(j);
	                                 
	  
									if(( compartiveReportform3info.getForm3CPFaccno()).equals(compartiveReportAaiinfo.getAaiCPFaccno()))
							        {		
																	

									    commonList.add(pensionList.get(i));
										commonAAIList.add(AAIList.get(j));
	                                    AAIList.remove(AAIList.get(j));	
										temppensionList.remove(pensionList.get(i));	

							        }															
							  }
							  
						  }
						  
					 compartiveReport.setCommonList(commonList);
	                 compartiveReport.setAaiList(AAIList);
					 compartiveReport.setCommonAaiList(commonAAIList);
					 compartiveReport.setForm3List(temppensionList);
					 compartiveReport.setFlag(flag);
					 }				 

			   }
			   return compartiveReport;
		}
		
	    public ArrayList financeForm4Report(String airportCode,String date1,String date2,String region,String sortingOrder,String reportType){
	        ArrayList form45tList=new ArrayList();
	       form45tList=financialDAO.financeForm4ReportByDJ(airportCode,date1,date2,region);
	       // form45tList=financialDAO.financeForm45Report(airportCode,date1,date2,region,sortingOrder,reportType);
	    
	        return form45tList;
	    }
	    public ArrayList financeForm5Report(String airportCode,String date1,String date2,String region,String sortingOrder,String reportType){
	        ArrayList form45tList=new ArrayList();
	        
	        //form45tList=financialDAO.financeForm5ReportByDJ(airportCode,date1,date2,region);
	        form45tList=financialDAO.financeForm45Report(airportCode,date1,date2,region,sortingOrder,reportType);
	        log.info("financeForm5Report LIst size"+form45tList.size());
	        return form45tList;
	    }
		public ArrayList getAirportListByForm45Report(String region){
			ArrayList airportList=new ArrayList();
			airportList=financialDAO.getAirportListByForm45Report(region);
			return airportList;
			
		}
		
		public ArrayList financeForm6AReport(String airportCode,String date,String region,String sortingOrder){
			ArrayList form6AList=new ArrayList();
			form6AList=financialDAO.financeForm6AReport(airportCode,date,region,sortingOrder);
			return form6AList;
		}

	public ArrayList financeForm6BReport(String airportCode,String date,String region,String sortingOrder){
			ArrayList form6BList=new ArrayList();
			form6BList=financialDAO.financeForm6BReport(airportCode,date,region,sortingOrder);
			return form6BList;
		}


		public ArrayList financeForm6Report(String airportCode,String date,String region,String sortingOrder){
			ArrayList form6List=new ArrayList();
			form6List=financialDAO.financeForm6Report(airportCode,date,region,sortingOrder);
			return form6List;
		}
		
		public FinaceDataAvailable financeDataAvailableCheck(){
			//ArrayList finacedataAvailableList=new ArrayList();
			FinaceDataAvailable finaceBean=new FinaceDataAvailable();
			finaceBean=financialDAO.financeDataAvailableCheck();
			return finaceBean;
		}
	    public ArrayList getMappedUniqueNolist(){
	        
	        ArrayList mappedList= financialDAO.getMappedUniqueNolist();
	        return mappedList;
	        
	    }
	    public ArrayList financeDupForm3Report(String date,String retriedDate){
	        ArrayList form3tList=new ArrayList();
	         form3tList=financialDAO.duplicateForm3Report(date,retriedDate);
	       
	        return form3tList;
	    }
	    public ArrayList financeForm3ReportAll(String date,String region,String retriedDate,String sortingOrder,String empName,String empNameFlag){
	        ArrayList form3tList=new ArrayList();
	        form3tList=financialDAO.financeForm3ReportAll(date,region,retriedDate,sortingOrder,empName,empNameFlag);
	        return form3tList;
	    }
	    
	    public int[]  financeRpfcFormsCountData(String airportCode,String date,String region,String prevDate){
			return financialDAO.financeRpfcFormsCountData(airportCode,date,region,prevDate);
		}
	    public ArrayList exportPFIDSReport(String frmyear,String frmMonth,String region){
	        ArrayList form3tList=new ArrayList();
	        form3tList=financialDAO.exportPFIDSReport(frmyear,frmMonth, region);
	        return form3tList;
	    }
	    
	    public SearchInfo financeDataSearch(EmpMasterBean empSearch,SearchInfo searchInfo,int gridLength,String empNameCheck,String reporttype,String pfidfrom){
	    	ArrayList financeDataList=new ArrayList();
	    	int startIndex = 1;
	    	String allRecordsFlag=empSearch.getAllRecordsFlag();
	    	String unmappedFlag=empSearch.getUnmappedFlag();
			String navButton = "";
			if (searchInfo.getNavButton() != null) {
				navButton = searchInfo.getNavButton();
			}

			if (new Integer(searchInfo.getStartIndex()) != null) {
				startIndex = searchInfo.getStartIndex();
			}
		    int totalRecords = financialDAO.totalCountPersonal(empSearch,gridLength,startIndex,empNameCheck,"total",unmappedFlag,allRecordsFlag,pfidfrom);
		   // int totalUnmappedRecords= financialDAO.totalCountPersonal(empSearch,gridLength,startIndex,empNameCheck,"",unmappedFlag);
		     startIndex = paging.getPageIndex(navButton, startIndex, totalRecords,gridLength);
			financeDataList=financialDAO.financeDataSearch(empSearch,gridLength,startIndex,empNameCheck,unmappedFlag,reporttype,allRecordsFlag,pfidfrom);
			
	    	BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
	    	bottomGridNavigationInfo = paging.searchPagination(totalRecords,startIndex, gridLength);
			searchInfo.setStartIndex(startIndex);
			searchInfo.setSearchList(financeDataList);
			
			
			searchInfo.setTotalRecords(totalRecords);
			searchInfo.setBottomGrid(bottomGridNavigationInfo);
	    	
	    	return searchInfo;
	     }
	    public int financePfidUpdate(String cpfaccno,String employeeCode,String region,String pfid){
	    	int count=financialDAO.financePfidUpdate(cpfaccno,employeeCode,region,pfid);
	    	FinancialReportService finReportService = new FinancialReportService();
	    	int insertedRec=finReportService.preProcessAdjOB(pfid);
	    	return count;
	    }
	    
	    public void updateMappingFlag(String pfid,String cpfaccno,String region,String userName,String ipaddress){
	    	
	    	financialDAO.updateMappingFlag(pfid,cpfaccno,region,userName,ipaddress);
	    }
	 public void updateMapping(String pfid,String cpfaccno,String region,String username,String ipaddress){
	    	
	    	financialDAO.updateMapping(pfid,cpfaccno,region,username,ipaddress);
	    }
	    
	    public ArrayList searchDeviationData(FinacialDataBean databean,
				SearchInfo searchInfo,int gridLength) throws Exception {
			int startIndex = 0;
			ArrayList hindiData = new ArrayList();
			System.out.println("searchInfo.getNavigation()"
					+ searchInfo.getNavigation());
			startIndex = 1;
			hindiData = financialDAO.SearchDeviationData(databean, startIndex);
			log.info("recordlist " +hindiData.size());    
			searchInfo.setSearchList(hindiData);		
			searchInfo.setSearchList(hindiData);		
			return hindiData;
		}
		
		public ArrayList deviationReport(String pfidString,String region,String airportcode,String frmSelectedDts,String flag,String employeeName,String sortedColumn,String pensionNo){
	        ArrayList AAIEPFLst=new ArrayList();
	        AAIEPFLst=financialDAO.deviationReport(pfidString,region,airportcode, frmSelectedDts, flag,employeeName, sortedColumn, pensionNo);
	        return AAIEPFLst;
	   }
		
		public FinacialDataBean searcTransferData(FinacialDataBean databean,
				SearchInfo searchInfo,int gridLength) throws Exception {	
			FinacialDataBean finacialDataBean = new FinacialDataBean();	
			finacialDataBean = financialDAO.searcTransferData(databean);			
			return finacialDataBean;
		}
		
		public ArrayList searchYearlyEmpCount(FinacialDataBean databean) throws Exception {	
			ArrayList empCountList = new ArrayList();	
			empCountList = financialDAO.searchYearlyEmpCount(databean);			
			return empCountList;
		}
		
		public ArrayList transferReport(String region,String airportcode,String frmSelectedDts,String sortedColumn,String pensionNo){
	        ArrayList AAIEPFLst=new ArrayList();
	        AAIEPFLst=financialDAO.transferReport(region,airportcode, frmSelectedDts, sortedColumn, pensionNo);
	        return AAIEPFLst;
	   }
		

		
	public void updateTransferData(String pensionno,String seperationreason,String sepetaionDt,String airportcode,String region,String fromairportcode,String fromregion,String status){
	            financialDAO.updateTransferData(pensionno,seperationreason,sepetaionDt,airportcode,region,fromairportcode,fromregion,status);
	       
	}
	public ArrayList rpfcForm5Report(String date1,String date2,String region,String pensionNo,String airportCode,String sortingOrder){
	    ArrayList form45tList=new ArrayList();
	    
	    //form45tList=financialDAO.financeForm5ReportByDJ(airportCode,date1,date2,region);
	    form45tList=financialDAO.rpfcForm5ReportByDJ(date1,date2,region,pensionNo,airportCode,sortingOrder);
	    
	    log.info("financeForm5Report LIst size"+form45tList.size());
	    return form45tList;
	}
	public ArrayList rpfcForm4Report(String airportCode,String date1,String date2,String region,String pensionno,String sortingOrder){
	    ArrayList form4tList=new ArrayList();
	    form4tList=financialDAO.rpfcForm4ReportByDJ(airportCode,date1,date2,region,pensionno,sortingOrder);
	    return form4tList;
	}
	public void ProcessforAdjOb(String Pensionno){
		finReportDAO.ProcessforAdjOb(Pensionno,true);
		
	}


	public ArrayList saveCpfAdjustMents(String emoluments,String cpf,String vpf,String principle,String interest,String pensionno,String month,String year,String pccontrib,String transmonthyear,String computername,String userName,String region ,String advAmount,String loan_sub_amt,String loan_cont_amt,String cpfacno){
		ArrayList a=null;
		a=finReportDAO.saveCpfAdjustMents(emoluments,cpf, vpf,principle,interest,pensionno,month,year,pccontrib,transmonthyear,computername,userName,region,advAmount,loan_sub_amt,loan_cont_amt,cpfacno);
		return a;
	}
	  public void formsDisable(String pfid,String formsstatus,String pcreportstatus,String userName,String password){
	       	financialDAO.formsDisable(pfid,formsstatus,pcreportstatus,userName,password);
	 }
	  public ArrayList getArrearsData(String pensionno){
			ArrayList empList=null;
			empList=financialDAO.getArrearsData(pensionno);
			return empList;
		}
		public ArrayList getArrearsTransactionData(String pensionno,
				String dateOfAnnuation, String flag) {
			ArrayList transactionData = null;
			transactionData = financialDAO.getArrearsTransactionData(pensionno,
					dateOfAnnuation, flag);
			return transactionData;
		}
		public void addArrearsData(String pensionno, String monthyear,
				String dueArrearamt, String dueEmoluments) {
			financialDAO.addArrearsData(pensionno, monthyear, dueArrearamt,
					dueEmoluments);

		}
		public void updateArearData(String pensionno, String arreartotal,
				String arreardate) {
			financialDAO.updateArearData(pensionno, arreartotal, arreardate);
		}
		public ArrayList getArrearsInfo(String pensionno) {
			ArrayList arrearInfo = null;
			arrearInfo = financialDAO.getArrearsInfo(pensionno);
			return arrearInfo;
		}
}
