package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.NotificationsDAO;

public class NotificationService {

	private NotificationService() {
	}

	private static final NotificationService service = new NotificationService();

	public static NotificationService getInstance() {
		return service;
	}

	public List getNotifications(String queryType) throws EPISException {
		return NotificationsDAO.getInstance().getNotifications(queryType);
	}
	public List getPendingAmt(String queryType)throws EPISException{
		return NotificationsDAO.getInstance().getPendingAmt(queryType);
	}

	public VoucherInfo getVoucherDetails(VoucherInfo info) throws EPISException {
		return NotificationsDAO.getInstance().getVoucherDetails(info);
	}

	public List getNotifications(com.epis.bean.cashbookDummy.VoucherInfo editInfo) throws EPISException {
		return NotificationsDAO.getInstance().getNotifications(editInfo);
	}
}
