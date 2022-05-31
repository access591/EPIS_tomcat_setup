package com.epis.services.investment;

import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.ComparativeDAO;

public class ComparativeService {
	
private ComparativeService(){
		
	}
   private static final ComparativeService comparativeService= new ComparativeService();
   public static ComparativeService getInstance(){
	return comparativeService;   
   }
   public List searchComparative(QuotationBean qbean) throws Exception{
		return ComparativeDAO.getInstance().searchComparative(qbean);
	}
public HashMap saveComparativeStatus(QuotationBean bean) throws Exception {
	return ComparativeDAO.getInstance().saveComparativeStatus(bean);
}
public List getComparativeLetterNos() throws ServiceNotAvailableException, EPISException {
	return ComparativeDAO.getInstance().getComparativeLetterNos();
}
public void updateCompFlag(QuotationBean bean) throws EPISException {
	ComparativeDAO.getInstance().updateCompFlag(bean);
}
public QuotationBean getMarketType(String letterNo)throws ServiceNotAvailableException, EPISException
{
	return ComparativeDAO.getInstance().getMarketType(letterNo);
}
	

}
