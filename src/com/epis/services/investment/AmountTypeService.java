package com.epis.services.investment;

import java.util.ArrayList;
import java.util.List;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.bean.investment.AmountTypeBean;
import com.epis.bean.admin.Bean;
import com.epis.dao.investment.AmountTypeDao;




public class AmountTypeService {
	
	private AmountTypeService()
	{
		
	}
	private static final AmountTypeService amounttypeservice=new AmountTypeService();

	public static AmountTypeService getInstance()
	{
		return amounttypeservice;
		
	}
	public List getAmountTypeList()
	{
		Bean bean1=new Bean();
		bean1.setName("Interest Amount");
		bean1.setCode("I");
		Bean bean2=new Bean();
		bean2.setName("Maturity Amount");
		bean2.setCode("M");
		Bean bean3=new Bean();
		bean3.setName("Redumption Amount");
		bean3.setCode("R");
		List list=new ArrayList();
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		return list;
	}
	public List PartyDetails()throws ServiceNotAvailableException,EPISException
	{
		return AmountTypeDao.getInstance().PartyDetails();
	}
	public String updatePartyDetails(AmountTypeBean bean)throws ServiceNotAvailableException,EPISException
	{
		return  AmountTypeDao.getInstance().updatePartyDetails(bean);
		
	}
	public List getSecurityList()throws ServiceNotAvailableException,EPISException
	{
		return AmountTypeDao.getInstance().getSecurityList();
		
	}
	public void savePartyDetails(AmountTypeBean bean)throws ServiceNotAvailableException,EPISException
	{
		 AmountTypeDao.getInstance().savePartyDetails(bean);
		
	}
	
	

}
