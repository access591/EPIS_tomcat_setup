package com.epis.services.advances;

import java.util.ArrayList;
import java.util.List;

import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.advances.NotificationsDAO;

public class NotificationService {
	NotificationsDAO  notificationDAO = new NotificationsDAO();
	
	public NotificationService() {
	}

	private static final NotificationService service = new NotificationService();

	public static NotificationService getInstance() {
		return service;
	}

	public List getNotifications(String queryType, AdvanceSearchBean searchBean) throws EPISException {
		return NotificationsDAO.getInstance().getNotifications(queryType,  searchBean);
	}
	 
	public String getNewAdvanceRequest(AdvanceSearchBean advancebean) throws EPISException {
		return NotificationsDAO.getInstance().getNewAdvanceRequest(advancebean);
	}

	public ArrayList searchAdvanceForNotifications(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		 
		searchList = notificationDAO.searchAdvanceForNotifications(advanceSearchBean);
		return searchList;
	}
	public ArrayList searchAdvanceInCashBookEntries(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;		 
		searchList = notificationDAO.searchAdvanceInCashBookEntries(advanceSearchBean);
		return searchList;
	}
}
