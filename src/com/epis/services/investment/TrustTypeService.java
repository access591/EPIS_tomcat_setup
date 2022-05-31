package com.epis.services.investment;

import java.util.List;

import javax.servlet.jsp.JspException;
import com.epis.bean.investment.TrustTypeBean;
import com.epis.dao.investment.TrustTypeDAO;

public class TrustTypeService {
	private TrustTypeService(){
		
	}
   private static final TrustTypeService trustTypeService= new TrustTypeService();
   public static TrustTypeService getInstance(){
	return trustTypeService;   
   }
   public List searchTrustType(String trusttype) throws Exception{
		return TrustTypeDAO.getInstance().searchTrust(trusttype);
	}
	public void saveTrustType(TrustTypeBean tbean) throws Exception{
		TrustTypeDAO.getInstance().saveTrustType(tbean);
	}
	public void editTrustType(TrustTypeBean tbean) throws Exception{
		TrustTypeDAO.getInstance().editTrustType(tbean);
	}
	public TrustTypeBean findTrustType(String trustcd) throws Exception {
		TrustTypeBean tbean =new TrustTypeBean();
	  tbean = TrustTypeDAO.getInstance().findTrustType(trustcd);
	return tbean;
	}
	public void deleteTrustType(String trustcds) throws Exception {
		TrustTypeDAO.getInstance().deleteTrustType(trustcds);
	}
	 public List getTrustTypes() throws Exception{
			return TrustTypeDAO.getInstance().getTrustTypes();
		}
	 public List getTrustTypes(String mode)throws Exception{
		 return TrustTypeDAO.getInstance().getTrustTypes(mode);
	 }

}
