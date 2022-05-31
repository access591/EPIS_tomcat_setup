package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.FundsTransferInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.FundsTransferDAO;

public class FundsTransferService {
	private FundsTransferService() {
	}

	private static final FundsTransferService service = new FundsTransferService();

	public static FundsTransferService getInstance() {
		return service;
	}

	public List search(FundsTransferInfo info) throws EPISException {
		return FundsTransferDAO.getInstance().search(info);	
	}

	public void save(FundsTransferInfo info) throws EPISException {
		FundsTransferDAO.getInstance().save(info);	
	}

	public FundsTransferInfo getRecord(FundsTransferInfo info) throws EPISException {
		return FundsTransferDAO.getInstance().getRecord(info);
	}

	public void update(FundsTransferInfo info) throws EPISException {
		FundsTransferDAO.getInstance().update(info);	
	}

	public FundsTransferInfo getApprovalRecord(FundsTransferInfo info) throws EPISException {
		return FundsTransferDAO.getInstance().getApprovalRecord(info);	
	}
	public FundsTransferInfo SaveApproval(FundsTransferInfo info) throws EPISException {
		return FundsTransferDAO.getInstance().SaveApproval(info);	
	}

	public FundsTransferInfo SaveFinalApproval(FundsTransferInfo info) throws EPISException {
		return FundsTransferDAO.getInstance().SaveFinalApproval(info);	
	}
	public void deleteApprovals(String keynos,String loginid) throws Exception {
		 FundsTransferDAO.getInstance().deleteApprovals(keynos,loginid);	
	}
}
