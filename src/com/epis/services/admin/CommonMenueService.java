

/**
 * @author anilkumark
 *
 */
package com.epis.services.admin;

import java.util.*;


import com.epis.dao.investment.InvestmentProposalDAO;
import com.epis.dao.admin.CommonMenueDAO;
import com.epis.bean.admin.ScreenBean;

public class CommonMenueService {
	private CommonMenueService(){
		
	}
   private static final CommonMenueService screenService= new CommonMenueService();
   public static CommonMenueService getInstance(){
	return screenService;   
   }
   
   
	
	public Map getMenueItems(String username,String module,String modulecd) throws Exception{
		return CommonMenueDAO.getInstance().getMenueItems(username,module,modulecd);
	}
	

}

