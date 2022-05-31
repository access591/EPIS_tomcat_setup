package com.epis.services.investment;

import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.bean.investment.QuotationAppBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.ConfirmationFromCompanyDao;
import com.epis.dao.investment.InvestApprovalDAO;
import com.epis.dao.investment.InvestmentRegisterDao;

public class InvestApprovalService {
private InvestApprovalService(){
		
	}
   private static final InvestApprovalService investService= new InvestApprovalService();
   public static InvestApprovalService getInstance(){
	return investService;   
   }
   public List searchInvestApproval(QuotationBean qbean) throws Exception{
		return InvestApprovalDAO.getInstance().searchInvestApproval(qbean);
	}
public HashMap showInvestApproval(QuotationBean qbean) throws Exception {
	return InvestApprovalDAO.getInstance().showInvestApproval(qbean);
}
public HashMap getApprovalReport(QuotationBean qbean)throws Exception{
	return InvestApprovalDAO.getInstance().getApprovalReport(qbean);
}
public void saveInvestApproval(QuotationBean bean) throws Exception {
	InvestApprovalDAO.getInstance().saveInvestApproval(bean);
}
public QuotationBean getInvestRegister(String letterno) throws Exception {
	return InvestApprovalDAO.getInstance().getInvestRegister(letterno);
}
public void addInvestRegister(QuotationBean qbean) throws Exception {
 InvestApprovalDAO.getInstance().addInvestRegister(qbean);
}
public List getApprovalLetters() throws EPISException {
	return InvestApprovalDAO.getInstance().getApprovalLetters();
}
public List getAppLetterNos() throws EPISException, ServiceNotAvailableException {
	return InvestApprovalDAO.getInstance().getAppLetterNos();
}
public List getCashBookSecurities() throws ServiceNotAvailableException, EPISException {
	return InvestApprovalDAO.getInstance().getCashBookSecurities();
}
public InvestmentRegisterBean generateInvestmentRegisterReport(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
{
	return InvestApprovalDAO.getInstance().generateInvestmentRegisterReport(bean);
}
public QuotationBean findapprovalconfirmation(String quotationCd)throws EPISException,ServiceNotAvailableException
{
	return InvestApprovalDAO.getInstance().findapprovalconfirmation(quotationCd);
}
public void approvalUpdate(QuotationAppBean bean)throws EPISException,ServiceNotAvailableException
{
	InvestApprovalDAO.getInstance().approvalUpdate(bean);
}

}
