package com.aims.service;

import java.util.List;

import com.epis.utilities.ApplicationException;
import com.epis.utilities.Log;
import com.aims.info.payrollprocess.EmpPaySlipInfo;
import com.aims.info.payrollprocess.JournalVoucherInfo;
import com.aims.info.payrollprocess.MonthlyPayrollInfo;
import com.aims.info.payrollprocess.PaySlipInfo;
import com.aims.info.payrollprocess.PayrollProcessInfo;
import com.aims.info.payrollprocess.SearchMonthlyPayroll;
import com.aims.info.payrollprocess.SearchPayrollProcessInfo;
import com.aims.dao.PayrollProcessDAO;
import com.aims.dao.StaffConfigurationDAO;



public class PayrollProcessService {

	Log log = new Log(PayrollProcessService.class);
	
	private static PayrollProcessService payrollprocessServiceInstance=new PayrollProcessService();
	
	public static PayrollProcessService getInstance(){
		return payrollprocessServiceInstance;
	}
	public void saveMonthlyPayroll(MonthlyPayrollInfo payrollinfo)throws ApplicationException{
		PayrollProcessDAO.getInstance().saveMonthlyPayroll(payrollinfo);
	}
	public SearchMonthlyPayroll searchsearchMonthlyPayroll(SearchMonthlyPayroll monthlypayroll){
		return PayrollProcessDAO.getInstance().searchsearchMonthlyPayroll(monthlypayroll);
	}
	public List searchPayrollProcess(SearchPayrollProcessInfo payprocessinfo){
		return PayrollProcessDAO.getInstance().searchPayrollProcess(payprocessinfo);
	}
	public List loadDisciplines(){
		return StaffConfigurationDAO.getInstance().getStaffDisciplines();
	}
	
	public List viewPaySlip(PaySlipInfo psinfo){
		return PayrollProcessDAO.getInstance().viewPaySlip(psinfo);
	}
	public List viewPayAbstractReport(PaySlipInfo psinfo,javax.servlet.http.HttpServletRequest request){
		return PayrollProcessDAO.getInstance().viewPayAbstractReport(psinfo, request);
	}
	public void payProcess(PayrollProcessInfo ppinfo) throws ApplicationException{
		try{
		PayrollProcessDAO.getInstance().payProcess(ppinfo);
		}catch(Exception e){
			log.info("------service clas-----payProcess Exception---- "+e.getMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	public List getPayrollMonths(){
		return PayrollProcessDAO.getInstance().getPayrollMonths();
	}
	public String getPayMonths(PayrollProcessInfo ppinfo){
		return PayrollProcessDAO.getInstance().getPayMonths(ppinfo);
	}
	public JournalVoucherInfo getJounralVoucher(String paytransid){
		return PayrollProcessDAO.getInstance().getJounralVoucher(paytransid);
	}
	public EmpPaySlipInfo getPayStopEarnDedcs(String cd){
		return PayrollProcessDAO.getInstance().getPayStopEarnDedcs(cd);
	}
	public void addEmpPayStop(EmpPaySlipInfo info) throws ApplicationException{
		PayrollProcessDAO.getInstance().addEmpPayStop(info);
	}
	public List searchEmpPayStop(String cd){
		return PayrollProcessDAO.getInstance().searchEmpPayStop(cd);
	}
	public EmpPaySlipInfo findByPayStopId(String id){
		return PayrollProcessDAO.getInstance().findByPayStopId(id);
	}
	public void updateEmpPayStop(List l,String status,String empname,String usercd,String paystopcd) throws ApplicationException{
		try{
			PayrollProcessDAO.getInstance().updateEmpPayStop(l,status,empname,usercd,paystopcd);
		}catch(Exception e){
			throw new ApplicationException(e.getMessage());
		}
	}
	public JournalVoucherInfo getPayJounralVoucher(String staffcategorycd,String payrollmonthid,String discplinecd,String station){
		return PayrollProcessDAO.getInstance().getPayJounralVoucher(staffcategorycd,payrollmonthid,discplinecd,station,"");
	}
	public JournalVoucherInfo getPayJounralVoucher(String staffcategorycd,String payrollmonthid,String discplinecd,String station,String ledgerCd){
		return PayrollProcessDAO.getInstance().getPayJounralVoucher(staffcategorycd,payrollmonthid,discplinecd,station,ledgerCd);
	}
	public List viewPBRReport(PaySlipInfo psinfo,javax.servlet.http.HttpServletRequest request){
		return PayrollProcessDAO.getInstance().viewPBRReport(psinfo, request);
	}
	public JournalVoucherInfo getSummaryJV(String staffcategorycd,String payrollmonthid,String discplinecd,String station,String ledgerCd){
		return PayrollProcessDAO.getInstance().getSummaryJV(staffcategorycd,payrollmonthid,discplinecd,station,ledgerCd);
	}
}
