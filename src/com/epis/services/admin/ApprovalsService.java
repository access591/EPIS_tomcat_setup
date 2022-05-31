package com.epis.services.admin;

import java.util.List;
import com.epis.bean.admin.ApprovalsBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.admin.ApprovalsDAO;


public class ApprovalsService {

	private ApprovalsService() {
	}

	private static final ApprovalsService service = new ApprovalsService();

	public static ApprovalsService getInstance() {
		return service;
	}
	
	public List getApprovalList() throws Exception {
		return ApprovalsDAO.getInstance().getApprovalList();
	}
	public List searchApproval(String approvalCode,String approvalName) throws ServiceNotAvailableException, EPISException{
		return ApprovalsDAO.getInstance().searchApproval(approvalCode,approvalName);
	}
	public void saveApproval(ApprovalsBean apbean) throws ServiceNotAvailableException, EPISException{
		ApprovalsDAO.getInstance().saveApproval(apbean);
	}
	public void editApproval(ApprovalsBean apbean) throws ServiceNotAvailableException, EPISException{
		ApprovalsDAO.getInstance().editApproval(apbean);
	}
	public ApprovalsBean findApproval(String approvalCode) throws ServiceNotAvailableException, EPISException {
		ApprovalsBean apbean =new ApprovalsBean();
		apbean = ApprovalsDAO.getInstance().findApproval(approvalCode);
	return apbean;
	}
	public void deleteApproval(String approvalcds) throws ServiceNotAvailableException, EPISException {
		ApprovalsDAO.getInstance().deleteApproval(approvalcds);
	}
}
