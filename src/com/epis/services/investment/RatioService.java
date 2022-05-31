package com.epis.services.investment;

import java.util.HashMap;
import java.util.List;
import com.epis.bean.investment.RatioBean;
import com.epis.dao.investment.ArrangersDAO;
import com.epis.dao.investment.RatioDAO;

public class RatioService {
private RatioService(){
		
	}
   private static final RatioService ratioService= new RatioService();
   public static RatioService getInstance(){
	return ratioService;   
   }
   
   public List searchCategory() throws Exception{
		return ArrangersDAO.getInstance().searchCategory();
	}
   
   public List searchRatio(String validdate) throws Exception{
		return RatioDAO.getInstance().searchRatio(validdate);
	}
	public void saveRatio(RatioBean rbean) throws Exception{
		RatioDAO.getInstance().saveRatio(rbean);
	}
	public void editRatio(RatioBean rbean) throws Exception{
		RatioDAO.getInstance().editRatio(rbean);
	}
	public void deleteRatio(String ratiocd) throws Exception {
		RatioDAO.getInstance().deleteRatio(ratiocd);
	}

	public HashMap getCategories() throws Exception {
		return  RatioDAO.getInstance().getCategories();
	}

	public RatioBean getCurrentRatio() throws Exception {
		RatioBean rbean =new RatioBean();
		rbean = RatioDAO.getInstance().getCurrentRatio();
		return rbean;
	}

}
