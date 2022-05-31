package com.epis.services.investment;

import java.util.ArrayList;
import java.util.List;

import com.epis.bean.investment.ConfirmationFromCompany;
import com.epis.bean.investment.ConfirmationFromCompanyAppBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.ConfirmationFromCompanyDao;
import com.epis.utilities.UtilityBean;

public class ConfirmationFromCompanyService {
	
	private ConfirmationFromCompanyService()
	{
		
	}
	private static final ConfirmationFromCompanyService confirmservice=new ConfirmationFromCompanyService();
	public static ConfirmationFromCompanyService getInstance()
	{
		return confirmservice;
	}
	public List searchConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		return ConfirmationFromCompanyDao.getInstance().searchConfirmationCompany(bean);
	}
	
	public List getconfirmationList()
	{
		List list=new ArrayList();
		UtilityBean bean=new UtilityBean();
		bean.setValue("Yes");
		bean.setKey("Y");
		UtilityBean bean1=new UtilityBean();
		bean1.setValue("NO");
		bean1.setKey("N");
		list.add(bean);
		list.add(bean1);
		
		return list;
	}
	public List getModeofInterestList()
	{
			
			
			List list=new ArrayList();
			UtilityBean bean=new UtilityBean();
			bean.setValue("Yearly");
			bean.setKey("Y");
			UtilityBean bean1=new UtilityBean();
			bean1.setValue("Half yearly");
			bean1.setKey("H");
			list.add(bean);
			list.add(bean1);
			
			return list;
			
	}
	public List getpurchasePriceOption()
	{
		List list=new ArrayList();
		UtilityBean bean=new UtilityBean();
		bean.setValue("Premium Paid Per Unit");
		bean.setKey("P");
		
		UtilityBean bean1=new UtilityBean();
		bean1.setValue("Discount Paid Per Unit");
		bean1.setKey("D");
		list.add(bean);
		list.add(bean1);
		return list;
	}
	public void saveConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		ConfirmationFromCompanyDao.getInstance().saveConfirmationCompany(bean);
	}
	public void updateConfirmation(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		ConfirmationFromCompanyDao.getInstance().updateConfirmation(bean);
	}
	public ConfirmationFromCompany findConfirmationCompany(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		return ConfirmationFromCompanyDao.getInstance().findConfirmationCompany(letterNo);
	}
	public void editConfirmationCompany(ConfirmationFromCompany bean)throws EPISException,ServiceNotAvailableException
	{
		ConfirmationFromCompanyDao.getInstance().editConfirmationCompany(bean);
	}
	public void deleteConfirmationCompany(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		ConfirmationFromCompanyDao.getInstance().deleteConfirmationCompany(letterNo);
	}
	public String getproposalRefno(String letterNO)throws EPISException,ServiceNotAvailableException
	{
		return ConfirmationFromCompanyDao.getInstance().getproposalRefno(letterNO);
	}
	public ConfirmationFromCompany findapprovalconfirmation(String letterNo)throws EPISException,ServiceNotAvailableException
	{
		return ConfirmationFromCompanyDao.getInstance().findapprovalconfirmation(letterNo);
	}
	public void approvalUpdate(ConfirmationFromCompanyAppBean bean)throws EPISException,ServiceNotAvailableException
	{
		 ConfirmationFromCompanyDao.getInstance().approvalUpdate(bean);
	}

}
