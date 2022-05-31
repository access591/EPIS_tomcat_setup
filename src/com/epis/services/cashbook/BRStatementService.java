package com.epis.services.cashbook;

import java.util.List;

import com.epis.bean.cashbook.BRStatementInfo;
import com.epis.common.exception.EPISException;
import com.epis.dao.cashbook.BRStatementDAO;


public class BRStatementService {
	private BRStatementService() {
	}

	private static final BRStatementService service = new BRStatementService();

	public static BRStatementService getInstance() {
		return service;
	}

	public List getColumns() throws Exception {
		return BRStatementDAO.getInstance().getColumns() ;
	}

	public BRStatementInfo getMappedColumns(BRStatementInfo info) throws Exception {
		return BRStatementDAO.getInstance().getMappedColumns(info) ;
	}

	public void save(BRStatementInfo info) throws EPISException {
		BRStatementDAO.getInstance().save(info) ;
	}
	public void saveFile(BRStatementInfo info,String path) throws Exception {
		 BRStatementDAO.getInstance().saveFile(info,path) ;
	}
	public List showDetails(String description,String accountNo) throws Exception {		
		List voucherList = BRStatementDAO.getInstance().showDetails(description,accountNo);		
		return voucherList;
	}
	public String saveDetails(String keyno,String voucherNo,String accountno) {
		return BRStatementDAO.getInstance().saveDetails(keyno,voucherNo,accountno);
	}
	public String savePartyDetails(String rowvalue,String amounttype)
	{
		return BRStatementDAO.getInstance().savePartyDetails(rowvalue,amounttype);
	}
	
	public List showReport(String fromDate,String toDate,String accountNo) throws Exception {		
		List dataList = BRStatementDAO.getInstance().showReport(fromDate,toDate,accountNo);		
		return dataList;
	}
	public List unreconcileReport(String fromDate,String toDate,String accountNo) throws Exception {		
		List dataList = BRStatementDAO.getInstance().unreconcileReport(fromDate,toDate,accountNo);		
		return dataList;
	}
	public List unreconcileVoucherReport(String fromDate,String toDate,String accountNo)throws Exception{
		List dataList = BRStatementDAO.getInstance().unreconcileVoucherReport(fromDate,toDate,accountNo);		
		return dataList;
	}
}
