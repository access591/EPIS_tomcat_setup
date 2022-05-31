package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.ArrangersBean;
import com.epis.dao.investment.ArrangersDAO;

public class ArrangersService {
	private ArrangersService(){
		
	}
   private static final ArrangersService arrangerService= new ArrangersService();
   public static ArrangersService getInstance(){
	return arrangerService;   
   }
   
   public List searchCategory() throws Exception{
		return ArrangersDAO.getInstance().searchCategory();
	}
   public List searchArrangers(ArrangersBean abean) throws Exception{
		return ArrangersDAO.getInstance().searchArrangers(abean);
	}
	public void saveArrangers(ArrangersBean abean) throws Exception{
		ArrangersDAO.getInstance().saveArrangers(abean);
	}
	public void editArrangers(ArrangersBean abean) throws Exception{
		ArrangersDAO.getInstance().editArrangers(abean);
	}
	public ArrangersBean findArrangers(String arrangercd) throws Exception {
		ArrangersBean tbean =new ArrangersBean();
	  tbean = ArrangersDAO.getInstance().findArrangers(arrangercd);
	return tbean;
	}
	public void deleteArrangers(String arrangercds) throws Exception {
		ArrangersDAO.getInstance().deleteArrangers(arrangercds);
	}
}
