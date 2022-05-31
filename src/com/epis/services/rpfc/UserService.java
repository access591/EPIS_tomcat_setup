 /**
  * File       : UserService.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.services.rpfc;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.epis.bean.rpfc.BottomGridNavigationInfo;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.bean.rpfc.UserBean;
import com.epis.dao.rpfc.UserDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.Pagenation;

public class UserService {

	Log log = new Log(PensionService.class);

	UserDAO UserDAO = new UserDAO();

	CommonUtil common = new CommonUtil();

	Pagenation paging = new Pagenation();

	DBUtility commonDB = new DBUtility();

	public void addNewUser(UserBean bean) throws InvalidDataException {
		log.info("UserService:addNewUser Entering Method");
		String flag = UserDAO.validateUser(bean);
		if (flag == "yes") {
			throw new InvalidDataException("User Name already exists");
		}
		UserDAO.addNewUserRecord(bean);
		log.info("UserService:addNewUserRecord Leaving Method");
		//return result;		
	}

	public SearchInfo searchNewUserData(UserBean databean,
			SearchInfo searchInfo, int gl) throws Exception {
		int startIndex = 0;
		ArrayList hindiData = new ArrayList();
		startIndex = 1;
		hindiData = UserDAO.SearchNewUserAll(databean, startIndex, gl);
		int totalRecords = UserDAO.totalUserData(databean);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gl);
		System.out.println("startIndex(searchUserData)" + startIndex);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;
	}

	public SearchInfo searchNavigationUserData(UserBean databean,
			SearchInfo searchInfo, int gl) throws Exception {
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
		System.out.println("rowCount................" + rowCount
				+ "startIndex................" + startIndex
				+ "navButton................." + navButton);
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount, gl);
		hindiData = UserDAO.SearchNewUserAll(databean, startIndex, gl);
		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gl);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public ArrayList getAirports() throws Exception {
		ArrayList al = UserDAO.getAirportNames();
		return al;
	}

	public ArrayList getUserNames() throws Exception {
		ArrayList al = UserDAO.getUserNames();
		return al;
	}

	/*public ArrayList getEmployee(UserBean bean) throws Exception {
		ArrayList al = UserDAO.getEmployee(bean);
		return al;
	}*/

/*	public ArrayList getDetails(UserBean bean) throws Exception {
		ArrayList al = UserDAO.getDetails(bean);
		return al;
	}*/

	public UserBean editUserMaster(int unitcd, String username)
			throws Exception {
		UserBean editbean = null;
		editbean = UserDAO.editUserMaster(unitcd, username);
		return editbean;
	}

	public int updateUserMaster(UserBean bean) throws Exception {
		int count = 0;
		count = UserDAO.updateUserMaster(bean);
		return count;
	}

	public boolean checkUser(String username, String password) throws Exception {
		boolean result = UserDAO.checkUser(username, password);
		return result;
	}

	
	public int checkExpiryDate(String username, String password)
			throws Exception {
		int result = UserDAO.checkExpiryDate(username, password);
		return result;
	}

	

	public int checkUserId(String username, String password) throws Exception {
		int result = UserDAO.checkUserId(username, password);
		return result;
	}

	/* public String  validateEmployee(UserBean  bean) throws  Exception{
	 String user=UserDAO.validateEmployee(bean);
	 return user;
	 }*/

	public String checkOldPassword(UserBean bean) throws Exception {
		String result = UserDAO.checkOldPassword(bean);
		return result;
	}

	public void confirmPwd(UserBean bean) throws Exception {
		UserDAO.confirmPwd(bean);
	}

	public void addLoginHistory(String username, String userType,
			Timestamp logintime, String computername, String region) {
		try {
			UserDAO.addLoginHistory(username, userType, logintime,
					computername, region);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
	}

	/*
	 * Group
	 */

	public void addNewGroup(UserBean bean) throws InvalidDataException {
		log.info("UserService:addNewGroup-- Entering Method");

		String flag = UserDAO.validateGroup(bean);
		if (flag == "yes") {
			throw new InvalidDataException("Group Name already exists");
		}
		UserDAO.addNewGroupRecord(bean);
		log.info("UserService:addNewGroup-- Leaving Method");
		//return result;		
	}

	public SearchInfo searchUserGroup(UserBean databean, SearchInfo searchInfo,
			int gl) throws Exception {

		int startIndex = 0;
		ArrayList hindiData = new ArrayList();
		startIndex = 1;
		hindiData = UserDAO.SearchNewGroupAll(databean, startIndex, gl);

		int totalRecords = UserDAO.totalGroupData(databean);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gl);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;
	}

	public SearchInfo searchNavigationGroupData(UserBean databean,
			SearchInfo searchInfo, int gl) throws Exception {

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
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount, gl);
		hindiData = UserDAO.SearchNewGroupAll(databean, startIndex, gl);

		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gl);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public UserBean editGroupMaster(int groupid, String groupname)
			throws Exception {
		UserBean editbean = null;
		editbean = UserDAO.editGroupMaster(groupid, groupname);
		return editbean;
	}

	public int updateGroupMaster(UserBean bean) throws Exception {
		int count = 0;
		count = UserDAO.updateGroupMaster(bean);
		return count;
	}

	public SearchInfo getGroupNames(SearchInfo searchInfo) throws Exception {
		ArrayList al = UserDAO.getGroupNames();
		searchInfo.setSearchList(al);
		return searchInfo;
	}

	/*
	 *  Group Assignment
	 */
	public void addNewGroupAssignment(UserBean bean, ArrayList al)
			throws Exception {
		log.info("UserService:addNewGroupAssignment-- Entering Method");
		UserDAO.addNewGroupAssignmentRecord(bean, al);
		log.info("UserService:addNewGroupAssignment-- Leaving Method");
		//return result;		
	}

	public void deleteAllUsers(UserBean bean) throws Exception {
		log.info("UserService:deleteAllUsers-- Entering Method");
		UserDAO.deleteAllUsers(bean);
		log.info("UserService:deleteAllUsers-- Leaving Method");
		//return result;		
	}

	public SearchInfo getAssignedUserNames(UserBean bean, SearchInfo searchInfo)
			throws Exception {
		ArrayList al = UserDAO.getAssignedUserNames(bean);
		searchInfo.setSearchList(al);
		return searchInfo;
	}

	public SearchInfo getExistingUserNames(SearchInfo searchInfo)
			throws Exception {
		ArrayList al = UserDAO.getExistingUserNames();
		searchInfo.setSearchList(al);
		return searchInfo;
	}

	public SearchInfo getRegions(UserBean bean, SearchInfo searchInfo)
			throws Exception {
		ArrayList al = UserDAO.getRegions(bean);
		searchInfo.setSearchList(al);
		return searchInfo;
	}

	public String checkRegion(UserBean dbBeans) throws Exception {
		String str = UserDAO.checkRegion(dbBeans);
		return str;
	}

	public void addNewRegion(UserBean bean) throws InvalidDataException {
		log.info("UserService:addNewRegion-- Entering Method");
		/*String flag = UserDAO.validateRegion(bean);
		if (flag == "yes") {
			throw new InvalidDataException("Region Name already exists");
		}*/
		UserDAO.addNewRegionRecord(bean);
		log.info("UserService:addNewRegion-- Leaving Method");
		//return result;		
	}

	public SearchInfo searchNavigationRegionData(UserBean databean,
			SearchInfo searchInfo, int gl) throws Exception {

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
		System.out.println("rowCount................" + rowCount
				+ "startIndex................" + startIndex
				+ "navButton................." + navButton);
		startIndex = paging.getPageIndex(navButton, startIndex, rowCount, gl);
		hindiData = UserDAO.SearchRegionAll(databean, startIndex, gl);
		bottomGridNavigationInfo = paging.navigationPagination(rowCount,
				startIndex, false, false, gl);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(rowCount);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);
		return searchInfo;

	}

	public SearchInfo searchRegion(UserBean databean, SearchInfo searchInfo,
			int gl) throws Exception {

		int startIndex = 0;
		ArrayList hindiData = new ArrayList();
		startIndex = 1;
		hindiData = UserDAO.SearchRegionAll(databean, startIndex, gl);

		int totalRecords = UserDAO.totalRegionData(databean);
		BottomGridNavigationInfo bottomGridNavigationInfo = new BottomGridNavigationInfo();
		bottomGridNavigationInfo = paging.searchPagination(totalRecords,
				startIndex, gl);
		searchInfo.setStartIndex(startIndex);
		searchInfo.setSearchList(hindiData);
		searchInfo.setTotalRecords(totalRecords);
		searchInfo.setBottomGrid(bottomGridNavigationInfo);

		return searchInfo;
	}

	public UserBean editRegionMaster(int regionid, String regionname)
			throws Exception {
		UserBean editbean = null;
		editbean = UserDAO.editRegionMaster(regionid, regionname);
		return editbean;
	}

	public int updateRegionMaster(UserBean bean) throws Exception {
		int count = 0;
		count = UserDAO.updateRegionMaster(bean);
		return count;
	}

	public ArrayList getAllAaiCategories() throws Exception {
		ArrayList al = UserDAO.getAllAaiCategories();
		return al;
	}

	public ArrayList getRegionData(UserBean bean) throws Exception {
		ArrayList al = UserDAO.getRegionData(bean);
		log.info("in getRegion getAaiCategory" + bean.getAaiCategory());
		return al;
	}
public void updatePasswordFlag(String userName,String password)throws Exception {
	UserDAO.updatePasswordFlag(userName,password);
}

}
