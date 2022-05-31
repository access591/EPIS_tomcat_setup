package com.epis.services.investment;
import java.util.ArrayList;
import java.util.List;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.bean.investment.InvestmentMadeBean;
import com.epis.dao.investment.InvestmentMadeDao;

public class InvestmentMadeService {
	
	private InvestmentMadeService()
	{
		
	}
	private static final InvestmentMadeService investmentmadeservice=new InvestmentMadeService();
	public static InvestmentMadeService getInstance()
	{
		return investmentmadeservice;
	}
	public List searchinvestMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentMadeDao.getInstance().searchinvestMade(bean);
	}
	public void addinvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentMadeDao.getInstance().addinvestmentMade(bean);
	}
	public InvestmentMadeBean findInvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		return InvestmentMadeDao.getInstance().findInvestmentMade(bean);
	}
	public void updateinvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentMadeDao.getInstance().updateinvestmentMade(bean);
	}
	public void deleteInvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentMadeDao.getInstance().deleteInvestmentMade(bean);
	}

}
