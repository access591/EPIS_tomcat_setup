/**
 * 
 */


/**
 * @author anilkumark
 *
 */
package com.epis.services.admin;

import java.util.List;

import javax.servlet.jsp.JspException;
import com.epis.bean.investment.InvestmentProposalBean;
import com.epis.dao.investment.InvestmentProposalDAO;
import com.epis.dao.investment.TrustTypeDAO;
import com.epis.dao.admin.ScreenDAO;
import com.epis.bean.admin.ScreenBean;

public class ScreenService {
	private ScreenService(){
		
	}
   private static final ScreenService screenService= new ScreenService();
   public static ScreenService getInstance(){
	return screenService;   
   }
   
   
	public void saveScreen(ScreenBean sbean) throws Exception{
		ScreenDAO.getInstance().saveScreen(sbean);
	}
	public List searchScreen(String screenName,String submodule,String module) throws Exception{
		return ScreenDAO.getInstance().searchScreen(screenName,submodule,module);
	}
	public List getProposal() throws Exception{
		return InvestmentProposalDAO.getInstance().getProposal();
	}
	
	
	public void editScreen(ScreenBean sbean) throws Exception{
		ScreenDAO.getInstance().editScreen(sbean);
	}
	public ScreenBean findScreen(String screencd) throws Exception{
		ScreenBean sbean =new ScreenBean();
		sbean = ScreenDAO.getInstance().findScreen(screencd);
	return sbean;
	}
	public void deleteScreen(String screencds) throws Exception{
		ScreenDAO.getInstance().deleteScreen(screencds);
	}

}

