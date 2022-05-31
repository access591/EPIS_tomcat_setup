package com.epis.services.investment;

import java.util.ArrayList;
import java.util.List;

import com.epis.bean.admin.Bean;
import com.epis.bean.investment.QuotationRequestBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.QuotationRequestDAO;

public class QuotationRequestService {
	private QuotationRequestService(){
		
	}
	private static final QuotationRequestService quotationRequestService=new QuotationRequestService();
	public static QuotationRequestService getInstance()
	{
		return quotationRequestService;
	}
	public QuotationRequestBean findQuotationRequest(String letterno)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().findQuotationRequest(letterno);
	}
	public List searchQuotationRequests(QuotationRequestBean bean)throws ServiceNotAvailableException, EPISException
	{
		return QuotationRequestDAO.getInstance().searchQuotationRequests(bean);
	}
	public String saveQuotationRequest(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().saveQuotationRequest(bean);
	}
	public void editQuotationRequest(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		QuotationRequestDAO.getInstance().editQuotationRequest(bean);
	}
	public List getQuotationRequests()throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().getQuotationRequests();
	}
	public List getApprovedList()
	{
		
		Bean bean1=new Bean();
		bean1.setName("Accepted");
		bean1.setCode("A");
		Bean bean2=new Bean();
		bean2.setName("Rejected");
		bean2.setCode("R");
		
	    List list=new ArrayList();
	    list.add(bean1);
	    list.add(bean2);
	    
	    return list;
	    
	}
	public List getPeriods()
	{
		Bean bean=null;
		List list=new ArrayList();
		for(int i=2011; i<=2050; i++)
		{
			bean=new Bean();
			bean.setCode(i+"");
			bean.setName(i+"");
			list.add(bean);
		}
		return list;
	}
	public List getMarketTypeList()
	{
		
		Bean bean1=new Bean();
		bean1.setName("Primary");
		bean1.setCode("P");
		Bean bean2=new Bean();
		bean2.setName("Secondary");
		bean2.setCode("S");
		
		Bean bean4=new Bean();
		bean4.setName("RBI");
		bean4.setCode("R");
		Bean bean5=new Bean();
		bean5.setName("OpenBid");
		bean5.setCode("O");
		Bean bean6=new Bean();
		bean6.setName("Primary and Security");
		bean6.setCode("PS");
	    List list=new ArrayList();
	    list.add(bean1);
	    list.add(bean2);
	    list.add(bean4);
	    list.add(bean5);
	    list.add(bean6);
	    
	    return list;
	}
	public List getMarketTypeList(String mode)
	{
		
		Bean bean1=new Bean();
		bean1.setName("Primary");
		bean1.setCode("P");
		Bean bean2=new Bean();
		bean2.setName("Secondary");
		bean2.setCode("S");
		
		Bean bean4=new Bean();
		bean4.setName("RBI");
		bean4.setCode("R");
		Bean bean5=new Bean();
		bean5.setName("OpenBid");
		bean5.setCode("O");
		Bean bean6=new Bean();
		bean6.setName("Primary and Security");
		bean6.setCode("PS");
		Bean bean7=new Bean();
		bean7.setName("RBI OIL BONDS");
		bean7.setCode("RB");
	    List list=new ArrayList();
	    if(mode.equals("psuprimary"))
	    {
	    	list.add(bean1);
	    	
	    }
	    else if(mode.equals("rbiauction"))
	    {
	    	list.add(bean4);
	    	list.add(bean7);
	    }
	    else if(mode.equals("rbiauctionManual"))
	    {
	    	list.add(bean1);
	    	list.add(bean2);
	    	list.add(bean4);
	    	list.add(bean7);
	    }
	    else{
	    
	    list.add(bean2);
	    list.add(bean5);
	    list.add(bean6);
	    }
	    
	    return list;
	}
	public List getArrangers()throws ServiceNotAvailableException, EPISException
	{
		return QuotationRequestDAO.getInstance().getArrangers();
	}
	public void deleteQuotationRequest(String letternos)throws ServiceNotAvailableException,EPISException
	{
		 QuotationRequestDAO.getInstance().deleteQuotationRequest(letternos);
	}
	public QuotationRequestBean verifyArrangersMailing(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		 return QuotationRequestDAO.getInstance().verifyArrangersMailing(bean);
	}
	public void getBankDetails(String accountno)throws ServiceNotAvailableException, EPISException
	{
		 QuotationRequestDAO.getInstance().getBankDetails(accountno);
	}
	public QuotationRequestBean generateQuotationRequestReport(QuotationRequestBean bean)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().generateQuotationRequestReport(bean);
	}
	public String getDesignation(String pensionNo)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().getDesignation(pensionNo);
	}
	public List getSelectedArrangers(String letterNo)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().getSelectedArrangers(letterNo);
	}
	public String getLoginId(String letterNo)throws ServiceNotAvailableException,EPISException
	{
		return QuotationRequestDAO.getInstance().getLoginId(letterNo);
	}
	

}
