package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.SecurityCategoryBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.SecurityCategoryDAO;

public class SecurityCategoryService {
private SecurityCategoryService(){
		
	}
   private static final SecurityCategoryService categoryService= new SecurityCategoryService();
   public static SecurityCategoryService getInstance(){
	return categoryService;   
   }
   public List searchCategory(String category) throws Exception{
		return SecurityCategoryDAO.getInstance().searchCategory(category);
	}
	public void saveCategory(SecurityCategoryBean scbean) throws Exception{
		SecurityCategoryDAO.getInstance().saveCategory(scbean);
	}
	public void editCategory(SecurityCategoryBean scbean) throws Exception{
		SecurityCategoryDAO.getInstance().editCategory(scbean);
	}
	public SecurityCategoryBean findSecurityCategory(String sccd) throws Exception {
		SecurityCategoryBean sbean =new SecurityCategoryBean();
		sbean = SecurityCategoryDAO.getInstance().findSecurityCategory(sccd);
	return sbean;
	}
	public void deleteCategory(String trustcds) throws Exception {
		SecurityCategoryDAO.getInstance().deleteCategory(trustcds);
	}
	public List getSecurityCategories()throws Exception
	{
		return SecurityCategoryDAO.getInstance().getSecurityCategories();
	}
	public List getSecurityCategories(String mode)throws Exception
	{
		return SecurityCategoryDAO.getInstance().getSecurityCategories(mode);
	}
	public List getAccountHeads() throws ServiceNotAvailableException, EPISException {
		return SecurityCategoryDAO.getInstance().getAccountHeads();
	}


}
