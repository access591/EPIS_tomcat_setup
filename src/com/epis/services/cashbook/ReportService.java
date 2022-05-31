package com.epis.services.cashbook;

import java.util.List;
import java.util.Map;

import com.epis.bean.cashbook.PaymentReceiptBean;
import com.epis.bean.cashbook.PaymentReceiptDtBean;
import com.epis.bean.cashbook.ReportBean;
import com.epis.bean.cashbookDummy.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.ReportDAO;

public class ReportService {

	private ReportService() {
	}

	private static final ReportService service = new ReportService();

	public static ReportService getInstance() {
		return service;
	}
	public PaymentReceiptBean getPaymentandReceiptReport(PaymentReceiptDtBean dtbean)throws EPISException
	{
		return ReportDAO.getInstance().getPaymentandReceiptReport(dtbean);
	}
	
	public Map getDailyStatement(ReportBean bean) throws EPISException {		
		return ReportDAO.getInstance().getDailyStatement(bean);	
	}
	
	public List getBanks() throws EPISException {		
		return ReportDAO.getInstance().getBanks();	
	}

	public Map getIncomeAndExpenditure(ReportBean bean) throws EPISException {
		return ReportDAO.getInstance().getIncomeAndExpenditure(bean);	
	}

	public Map getBalanceSheet(ReportBean bean) throws EPISException {
		return ReportDAO.getInstance().getBalanceSheet(bean);		
	}

	public String getReserveAmount(String year, String trustType, String month) throws EPISException {
		return ReportDAO.getInstance().getReserveAmount(year,trustType,month);	
	}

	public Map getScheduleReport(ReportBean bean) throws Exception {
		return ReportDAO.getInstance().getScheduleReport(bean);	
	}
	public List getRejectedLoans(VoucherInfo info)throws EPISException 
	{
		return ReportDAO.getInstance().getRejectedLoans(info);
	}

}
