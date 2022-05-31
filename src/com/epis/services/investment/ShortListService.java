package com.epis.services.investment;

import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.ShortListDAO;

public class ShortListService {
private ShortListService(){
		
	}
   private static final ShortListService shortService= new ShortListService();
   public static ShortListService getInstance(){
	return shortService;   
   }
   public List searchShortList(QuotationBean qbean) throws Exception{
		return ShortListDAO.getInstance().searchShortList(qbean);
	}
public HashMap showShortList(String letterno,String remarks) throws Exception {
	return ShortListDAO.getInstance().showShortList(letterno,remarks);
}
public HashMap showShortList(String letterno)throws Exception{
	return ShortListDAO.getInstance().showShortList(letterno);
}
public void saveShortList(QuotationBean bean) throws Exception {
	 ShortListDAO.getInstance().saveShortList(bean);
}
public List getShortListLetterNos() throws ServiceNotAvailableException, EPISException {
	return ShortListDAO.getInstance().getShortListLetterNos();
}
public void updateSLFlag(QuotationBean bean) throws EPISException {
	ShortListDAO.getInstance().updateSLFlag(bean);
}

}
