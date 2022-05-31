package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.JournalVoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.JournalVoucherDAO;
 
public class JournalVoucherService {
	private JournalVoucherService() {
	}

	private static final JournalVoucherService service = new JournalVoucherService();

	public static JournalVoucherService getInstance() {
		return service;
	}

	public List search(JournalVoucherInfo info) throws EPISException {
		return JournalVoucherDAO.getInstance().search(info);
	}

	public void save(JournalVoucherInfo info) throws EPISException {
		JournalVoucherDAO.getInstance().save(info);
	}

	public JournalVoucherInfo getJournalVoucher(JournalVoucherInfo info) throws EPISException {
		return JournalVoucherDAO.getInstance().getJournalVoucher(info);
	}

	public void update(JournalVoucherInfo info) throws EPISException {
		JournalVoucherDAO.getInstance().update(info);
	}
	
	public void deleteApprovals(String keynos,String loginid,String loginunit) throws Exception {
		JournalVoucherDAO.getInstance().deleteApprovals(keynos,loginid,loginunit);	
	}
	public JournalVoucherInfo SaveApproval(JournalVoucherInfo info) throws EPISException {
		return JournalVoucherDAO.getInstance().SaveApproval(info);	
	}
}
