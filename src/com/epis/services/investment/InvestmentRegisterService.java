package com.epis.services.investment;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.bean.investment.InvestmentRegisterBean;
import com.epis.dao.investment.InvestmentRegisterDao;
import java.util.List;

public class InvestmentRegisterService {
	private InvestmentRegisterService()
	{
		
	}
	private static final InvestmentRegisterService investmentregisterservice=new InvestmentRegisterService();
	public static InvestmentRegisterService  getInstance()
	{
		return investmentregisterservice;
	}
	public List searchInvestmentRegister(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentRegisterDao.getInstance().searchInvestmentRegister(bean);
	}
	public List getLetterNumbers(String mode)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentRegisterDao.getInstance().getLetterNumbers(mode);
	}
	public InvestmentRegisterBean getRegisterDetails(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentRegisterDao.getInstance().getRegisterDetails(bean);
	}
	public void addInvestmentRegister(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentRegisterDao.getInstance().addInvestmentRegister(bean);
	}
	public void addInvestmentRegisterManual(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException,Exception
	{
		
		int recordcount=InvestmentRegisterDao.getInstance().checksecurityProposalRefno(bean);
		if(recordcount!=0)
		{
			throw new Exception("ProposalNO Already Exists with"+bean.getSecurityName()+"-"+bean.getRefNo());
		}
		
		InvestmentRegisterDao.getInstance().addInvestmentRegisterManual(bean);
	}
	public InvestmentRegisterBean generateInvestmentRegisterReport(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentRegisterDao.getInstance().generateInvestmentRegisterReport(bean);
	}
	public int updateInvestmentISIN(InvestmentRegisterBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentRegisterDao.getInstance().updateInvestmentISIN(bean);
	}
}
