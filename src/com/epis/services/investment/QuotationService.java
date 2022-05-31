package com.epis.services.investment;

import java.sql.SQLException;
import java.util.List;
import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.QuotationDAO;


public class QuotationService {

	private QuotationService() {
	}

	private static final QuotationService service = new QuotationService();

	public static QuotationService getInstance() {
		return service;
	}
	public List searchQuotation(String letterNo,String SecurityName,String trust,String SecurityCategory) throws ServiceNotAvailableException, EPISException{
		return QuotationDAO.getInstance().searchQuotation( letterNo, SecurityName, trust, SecurityCategory);
	}
	public void saveQuotation(QuotationBean qbean) throws ServiceNotAvailableException, EPISException,SQLException{
		QuotationDAO.getInstance().saveQuotation(qbean);
	}
	public void editQuotation(QuotationBean qbean) throws ServiceNotAvailableException, EPISException{
		QuotationDAO.getInstance().editQuotation(qbean);
	}
	public QuotationBean findQuotation(String quotationCode) throws ServiceNotAvailableException, EPISException {
		QuotationBean qbean =new QuotationBean();
		qbean = QuotationDAO.getInstance().findQuotation(quotationCode);
	return qbean;
	}
	public void deleteQuotation(String quotationcds) throws ServiceNotAvailableException, EPISException {
		QuotationDAO.getInstance().deleteQuotation(quotationcds);
	}
	public List gettypeOfCalls()
	{
		return QuotationDAO.getInstance().gettypeOfCalls();
	}
	public List getinvestmentTypes()
	{
		return QuotationDAO.getInstance().getinvestmentTypes();
	}
	public List getGuarenteTypes()
	{
		return QuotationDAO.getInstance().getGuarenteTypes();
	}
	public List getinterestDates()
	{
		return QuotationDAO.getInstance().getinterestDates();
	}
	public List getinterestMonths()
	{
		return QuotationDAO.getInstance().getinterestMonths();
	}
	public List getInvestmentModes()
	{
		return QuotationDAO.getInstance().getInvestmentModes();
	}
	public List getPutCallAnnualize()
	{
		return QuotationDAO.getInstance().getPutCallAnnualize();
	}
	public List getPurchasePriceOption()
	{
		return QuotationDAO.getInstance().getPurchasePriceOption();
	}
	public List getLetterNumbers()throws ServiceNotAvailableException,EPISException
	{
		return QuotationDAO.getInstance().getLetterNumbers();
	}
}
