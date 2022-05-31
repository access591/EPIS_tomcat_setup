package com.epis.service.cashbookDummy;

import java.util.List;
import java.util.Map;

import com.epis.bean.cashbookDummy.BankBook;
import com.epis.bean.cashbookDummy.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbookDummy.VoucherDAO;
import com.epis.utilities.Log;

public class VoucherService {

	Log log = new Log(VoucherService.class);
	VoucherDAO dao = new VoucherDAO();
	
	public String addVoucherRecord(VoucherInfo info) throws  Exception{
		log.info("VoucherService : addVoucherRecord   entering method");
		String keyNo = dao.addVoucherRecord(info);		
		log.info("VoucherService : addVoucherRecord   leaving method");
		return keyNo;
	}

	public List searchRecords(VoucherInfo info,String type) throws Exception {
		log.info("VoucherService : searchRecords   entering method");
		List dataList = dao.searchRecords(info,type);		
		log.info("VoucherService : searchRecords   leaving method");
		return dataList;
	}

	public VoucherInfo getReport(VoucherInfo info) throws Exception {
		log.info("VoucherService : getReport   entering method");
		info = dao.getReport(info);		
		log.info("VoucherService : getReport   leaving method");
		return info;
	}
	public List getMultipleVoucherData(VoucherInfo info)throws Exception{
		log.info("the Multiple voucher:getMultipleVoucherData entering method");
		List multiplevouchers=dao.getMultipleVoucherData(info);
		log.info("VoucherService :getMultipleVoucherData leaving method");
		return multiplevouchers;
	}

	public List getBankBook(VoucherInfo info) throws Exception {
		log.info("VoucherService : getBankBook   entering method");
		List book = dao.getBankBook(info);		
		log.info("VoucherService : getBankBook   leaving method");
		return book;
	}
	
	public void updateApprovalVoucher(VoucherInfo info) throws Exception {
		log.info("VoucherService : updateApprovalVoucher   entering method");
		dao.updateApprovalVoucher(info);		
		log.info("VoucherService : updateApprovalVoucher   leaving method");
		
	}

	public void updateVoucherRecord(VoucherInfo info) throws Exception {
		log.info("VoucherService : updateVoucherRecord   entering method");
		dao.updateVoucherRecord(info);		
		log.info("VoucherService : updateVoucherRecord   leaving method");
	}

	public void deleteVoucher(String codes) throws Exception {
		log.info("VoucherService : deleteVoucher   entering method");
		dao.deleteVoucher(codes);		
		log.info("VoucherService : deleteVoucher   leaving method");
	}

	public List getLedgerAccount(VoucherInfo info) throws Exception {
		log.info("VoucherService : getLedgerAccount   entering method");
		List dataList = dao.getLedgerAccount(info);		
		log.info("VoucherService : getLedgerAccount   leaving method");
		return dataList;
	}

	public BankBook getOpenBalance(String accountNo,String fromdate) throws Exception {
		log.info("VoucherService : getOpenBalance   entering method");
		BankBook book = dao.getOpenBalance(accountNo,fromdate);		
		log.info("VoucherService : getOpenBalance   leaving method");
		return book;
	}
	public String getappVoucherDate()throws Exception
	{
		return dao.getappVoucherDate();
	}

	public Map getGenLedger(VoucherInfo info) throws Exception {
		log.info("VoucherService : getGenLedger   entering method");
		Map ledger = dao.getGenLedger(info);		
		log.info("VoucherService : getGenLedger   leaving method");
		return ledger;
	}

	public Map getTrialBalance(VoucherInfo info) throws Exception {
		log.info("VoucherService : getGenLedger   entering method");
		Map trailBal = dao.getTrialBalance(info);		
		log.info("VoucherService : getGenLedger   leaving method");
		return trailBal;
	}

	public List ePaymentSearch(VoucherInfo info) throws Exception {
		log.info("VoucherService : ePaymentSearch   entering method");
		List voucherList = dao.ePaymentSearch(info);		
		log.info("VoucherService : ePaymentSearch   leaving method");
		return voucherList;
	}

	public void ePaymentTransfers(String keynos) throws Exception {
		log.info("VoucherService : ePaymentTransfers   entering method");
		dao.ePaymentTransfers(keynos);		
		log.info("VoucherService : ePaymentTransfers   leaving method");
	}
	
	public Map getAccountHeadsOpeningBalance(VoucherInfo info) throws Exception {
		log.info("VoucherService : getAccountHeadsOpeningBalance   entering method");
		Map accountingHeads = dao.getAccountHeadsOpeningBalance(info);		
		log.info("VoucherService : getAccountHeadsOpeningBalance   leaving method");
		return accountingHeads;
	}

	public void updateNotiVoucher(String transid,String keyno) throws EPISException {
		dao.updateNotiVoucher(transid,keyno);		
	}
}
