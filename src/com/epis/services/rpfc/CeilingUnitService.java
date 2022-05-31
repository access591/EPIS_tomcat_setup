 /**
  * File       : CeilingUnitService.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.services.rpfc;

import java.util.ArrayList;

import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.bean.rpfc.CeilingUnitBean;
import com.epis.bean.rpfc.FinancialYearBean;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.dao.rpfc.CeilingUnitDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;
import com.epis.utilities.Pagenation;
import com.epis.utilities.DBUtility;

public class CeilingUnitService {

    Log log= new Log(PensionService.class);

	CeilingUnitDAO CeilingUnitDAO = new CeilingUnitDAO();

	CommonUtil common=new CommonUtil();
	Pagenation paging=new Pagenation();
	DBUtility commonDB = new DBUtility();
	
    public void addCeilingRecord(CeilingUnitBean bean) throws  Exception{
	log.info("CeilingUnitService:addCeilingRecord-- Entering Method");	
		CeilingUnitDAO.addCeilingRecord(bean);
	log.info("CeilingUnitService:addCeilingRecord-- Leaving Method");	
		//return result;		
	}

	 public void addInterestRecord(CeilingUnitBean bean) throws  Exception{
	log.info("CeilingUnitService:addInterestRecord-- Entering Method");	
		CeilingUnitDAO.addInterestRecord(bean);
	log.info("CeilingUnitService:addInterestRecord-- Leaving Method");	
		//return result;		
	}

	public void addUnitRecord(CeilingUnitBean bean) throws  Exception{
	log.info("addUnitRecord:addUnitRecord-- Entering Method");	
		CeilingUnitDAO.addUnitRecord(bean);
	log.info("addUnitRecord:addUnitRecord-- Leaving Method");	
		//return result;		
	}


	public String checkWeDate(CeilingUnitBean bean) throws  Exception{
          String sb="";
		  sb=CeilingUnitDAO.checkWeDate(bean);
		  //System.out.println(".................................."+sb);
		  return sb;
	}

	public String checkInterstWeDate(CeilingUnitBean bean) throws  Exception{
          String sb="";
		  sb=CeilingUnitDAO.checkInterstWeDate(bean);
		  //System.out.println(".................................."+sb);
		  return sb;
	}

    public String checkUnitCode(CeilingUnitBean bean) throws  Exception{
          String sb="";
		  sb=CeilingUnitDAO.checkUnitCode(bean);
		  //System.out.println(".................................."+sb);
		  return sb;
	}

	public SearchInfo searchCeilingData(CeilingUnitBean databean,SearchInfo searchInfo,int gridLength)throws Exception{
		
		int startIndex = 0;
		ArrayList hindiData=new ArrayList();		
		startIndex=1;
		hindiData=CeilingUnitDAO.SearchCeilingAll(databean,startIndex);

		int totalRecords=CeilingUnitDAO.totalData(databean);

		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();

		bottomGridNavigationInfo=paging.searchPagination(totalRecords,startIndex,gridLength);

		System.out.println("startIndex(searchCeilingData)"+startIndex);

		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;
		
	}

	

	public SearchInfo searchUnitData(CeilingUnitBean databean,SearchInfo searchInfo,int gridLength)throws Exception{
		int startIndex = 0;

		ArrayList hindiData=new ArrayList();
		System.out.println("searchInfo.getNavigation()"+searchInfo.getNavigation());
		startIndex=1;
		
		hindiData=CeilingUnitDAO.SearchUnitAll(databean,startIndex,gridLength);

		int totalRecords=CeilingUnitDAO.totalRecords(databean);

		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();

		bottomGridNavigationInfo=paging.searchPagination(totalRecords,startIndex,gridLength);

		System.out.println("startIndex(searchCeilingData)"+startIndex);

		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;
		
	}
	public SearchInfo searchNavigationCeilingData(CeilingUnitBean databean,SearchInfo searchInfo,int gridLength)throws Exception{
		int startIndex = 1;
		String navButton="";
		ArrayList hindiData=new ArrayList();
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}
		if (new Integer(searchInfo.getStartIndex())!= null) {
			startIndex = searchInfo.getStartIndex();
		}
		int rowCount=searchInfo.getTotalRecords();
		System.out.println("rowCount"+rowCount+"startIndex"+startIndex+"navButton"+navButton);
		startIndex = paging.getPageIndex(navButton,startIndex,rowCount,gridLength);

		hindiData=CeilingUnitDAO.SearchCeilingAll(databean,startIndex);

		bottomGridNavigationInfo=paging.navigationPagination(rowCount,startIndex,false,false,gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;
		
	}

	public SearchInfo searchNavigationUnitData(CeilingUnitBean databean,SearchInfo searchInfo,int gridLength)throws Exception{
		int startIndex = 1;
		String navButton="";
		ArrayList hindiData=new ArrayList();
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		if (searchInfo.getNavButton() != null) {
			navButton = searchInfo.getNavButton();
		}
		if (new Integer(searchInfo.getStartIndex())!= null) {
			startIndex = searchInfo.getStartIndex();
		}
		int rowCount=searchInfo.getTotalRecords();
		System.out.println("rowCount"+rowCount+"startIndex"+startIndex+"navButton"+navButton);
		startIndex = paging.getPageIndex(navButton,startIndex,rowCount,gridLength);

		hindiData=CeilingUnitDAO.SearchUnitAll(databean,startIndex,gridLength);

		bottomGridNavigationInfo=paging.navigationPagination(rowCount,startIndex,false,false,gridLength);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;
		
	}	
	
public CeilingUnitBean	editCeilingMaster(int ceilingcd)throws Exception{
		
	CeilingUnitBean  editbean = null;
	editbean =CeilingUnitDAO.editCeilingMaster(ceilingcd);
	System.out.println("--------------------back to service---------------------------------------");
	return editbean;
	}

public CeilingUnitBean	editUnitMaster(String unitcd,String region)throws Exception{
		
	CeilingUnitBean  editbean = null;
	editbean =CeilingUnitDAO.editUnitMaster(unitcd,region);
	System.out.println("--------------------back to service---------------------------------------");
	return editbean;
}

public int updateCeilingMaster(CeilingUnitBean bean)throws Exception{
		int count=0;
	count=CeilingUnitDAO.updateCeilingMaster(bean);
	return count;
	}

public int updateUnitMaster(CeilingUnitBean bean)throws Exception{
		int count=0;
	count=CeilingUnitDAO.updateUnitMaster(bean);
	return count;
	}
/*
 * Financial Year
 */

public ArrayList getFinancialYear() throws  Exception{
ArrayList alist=new ArrayList();
alist=CeilingUnitDAO.getFinancialYear();
return alist;
}

public void addFinancialYear(FinancialYearBean bean) throws  Exception{
log.info("addUnitRecord:addFinancialYear-- Entering Method");	
	CeilingUnitDAO.addFinancialYear(bean);
log.info("addUnitRecord:addFinancialYear-- Leaving Method");		
}
public SearchInfo searchFinancialYearData(SearchInfo searchInfo,int gridLength)throws Exception{
	
	int startIndex = 0;
	ArrayList hindiData=new ArrayList();		
	startIndex=1;
	hindiData=CeilingUnitDAO.searchFinancialYearData(startIndex,gridLength);

	int totalRecords=CeilingUnitDAO.totalFinancialYearData();	
	BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
	bottomGridNavigationInfo=paging.searchPagination(totalRecords,startIndex,gridLength);
	System.out.println("startIndex(searchCeilingData)"+startIndex);

	searchInfo.setStartIndex(startIndex);
	searchInfo.setSearchList(hindiData);
	searchInfo.setTotalRecords(totalRecords);
	searchInfo.setBottomGrid(bottomGridNavigationInfo);

	return searchInfo;
}
public FinancialYearBean  editFinancialYear(int financialId)throws Exception{

FinancialYearBean  editbean = null;
editbean =CeilingUnitDAO.editFinancialYear(financialId);	
return editbean;
}
public int updateFinancialYear(FinancialYearBean bean)throws Exception{
int count=0;
count=CeilingUnitDAO.updateFinancialYear(bean);
return count;
}
public SearchInfo searchNavigationFinancialYear(SearchInfo searchInfo,int gridLength)throws Exception{	
int startIndex = 1;
String navButton="";
ArrayList hindiData=new ArrayList();
BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
if (searchInfo.getNavButton() != null) {
	navButton = searchInfo.getNavButton();
}
if (new Integer(searchInfo.getStartIndex())!= null) {
	startIndex = searchInfo.getStartIndex();
}
int rowCount=searchInfo.getTotalRecords();
System.out.println("rowCount"+rowCount+"startIndex"+startIndex+"navButton"+navButton);
startIndex = paging.getPageIndex(navButton,startIndex,rowCount,gridLength);

hindiData=CeilingUnitDAO.searchFinancialYearData(startIndex,gridLength);

bottomGridNavigationInfo=paging.navigationPagination(rowCount,startIndex,false,false,gridLength);
searchInfo.setStartIndex(startIndex);
searchInfo.setSearchList(hindiData);
searchInfo.setTotalRecords(rowCount);
searchInfo.setBottomGrid(bottomGridNavigationInfo);
return searchInfo;

}

}
