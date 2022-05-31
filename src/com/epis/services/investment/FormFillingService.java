package com.epis.services.investment;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.admin.Bean;
import com.epis.bean.investment.FormFillingAppBean;
import com.epis.bean.investment.FormFillingBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.FormFillingDAO;
import com.epis.utilities.UtilityBean;

public class FormFillingService {
	private FormFillingService()
	{
		
	}
	private static final FormFillingService formfillingservice=new FormFillingService();
	
	public static FormFillingService getInstance()
	{
		return formfillingservice;
	}
	public String getFinYear(String refNo)
	{
		return FormFillingDAO.getInstance().getFinYear(refNo);
	}
	public List searchFormFill(FormFillingBean bean)throws ServiceNotAvailableException, EPISException
	{
		return FormFillingDAO.getInstance().searchFormFilling(bean);
	}
	public List getStatueOfTaxOption()
	{
		List list=new ArrayList();
		Bean bean=new Bean();
		bean.setName("Exempted");
		bean.setCode("E");
		Bean bean1=new Bean();
		bean1.setName("Non-Exempted");
		bean1.setCode("N");
		list.add(bean);
		list.add(bean1);
		return list;
		
	}
	public List getProposal(String mode)throws ServiceNotAvailableException,EPISException
	{
		return FormFillingDAO.getInstance().getProposal(mode);
	}
	public void saveFormFilling(FormFillingBean bean)throws ServiceNotAvailableException, EPISException
	{
		FormFillingDAO.getInstance().saveFormFilling(bean);
	}
	public FormFillingBean findFormFill(FormFillingBean bean)throws ServiceNotAvailableException,EPISException
	{
		return FormFillingDAO.getInstance().findFormFill(bean);
	}
	public void updateFormFill(FormFillingBean bean)throws ServiceNotAvailableException,EPISException
	{
		FormFillingDAO.getInstance().updateFormFill(bean);
	}
	public void deleteFormFilling(String formCd)throws ServiceNotAvailableException,EPISException
	{
		FormFillingDAO.getInstance().deleteFormFilling(formCd);
	}
	public String getproposalRefno(String formCd)throws ServiceNotAvailableException,EPISException
	{
		return FormFillingDAO.getInstance().getproposalRefno(formCd);
	}
	public FormFillingBean findapprovalformFilling(String formCd)throws ServiceNotAvailableException,EPISException
	{
		return FormFillingDAO.getInstance().findapprovalformFilling(formCd);
	}
	public void approvalUpdate(FormFillingAppBean bean)throws ServiceNotAvailableException,EPISException
	{
		FormFillingDAO.getInstance().approvalUpdate(bean);
	}
	public List getAccountTypes()
	{
		List list=new ArrayList();
		UtilityBean bean=new UtilityBean();
		bean.setKey("C");
		bean.setValue("Current Account");
		UtilityBean bean1=new UtilityBean();
		
		bean1.setKey("S");
		bean1.setValue("Saving Account");
		list.add(bean);
		list.add(bean1);
		return list;
		
	}
	
}
