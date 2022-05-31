package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.GenerateBankLetterBean;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.GenerateBankLetterDAO;
import com.epis.utilities.Log;

public class GenerateBankLetterService {
	Log log  = new Log(GenerateBankLetterService.class);
	private final static GenerateBankLetterService bankletterservice=new GenerateBankLetterService();
	private GenerateBankLetterService()
	{
		
	}
	public static GenerateBankLetterService getInstance()
	{
		return bankletterservice;
	}
	public List searchGenerateBankLetter(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		return GenerateBankLetterDAO.getInstance().searchGenerateBankLetter(bean); 
	}
	public List getApprovalLetters()throws ServiceNotAvailableException, EPISException
	{
		return GenerateBankLetterDAO.getInstance().getApprovalLetters();
	}
	public void saveGenerateBankLetter(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		GenerateBankLetterDAO.getInstance().saveGenerateBankLetter(bean);
	}
	public void updatequotationData(QuotationBean qbean)throws ServiceNotAvailableException, EPISException
	{
		GenerateBankLetterDAO.getInstance().updatequotationData(qbean);
	}
	public GenerateBankLetterBean generateBankLetterReport(GenerateBankLetterBean bean)throws ServiceNotAvailableException, EPISException
	{
		return GenerateBankLetterDAO.getInstance().generateBankLetterReport(bean);
	}
	public QuotationBean bankLetterDetails(GenerateBankLetterBean bean)throws ServiceNotAvailableException,EPISException
	{
		return GenerateBankLetterDAO.getInstance().bankLetterDetails(bean);
	}
	public void updateQuotationData(QuotationBean qbean)throws ServiceNotAvailableException, EPISException
	{
		GenerateBankLetterDAO.getInstance().updateQuotationLetterData(qbean);
	}

}
