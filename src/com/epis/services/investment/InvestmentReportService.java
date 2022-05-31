
package com.epis.services.investment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.epis.bean.investment.RatioBean;
//import com.epis.dao.investment.ArrangersDAO;
//import com.epis.dao.investment.RatioDAO;
import com.epis.bean.investment.InvestmentReportsBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.InvestmentReportDAO;

public class InvestmentReportService {
private InvestmentReportService(){
		
	}
   private static final InvestmentReportService reportService= new InvestmentReportService();
   public static InvestmentReportService getInstance(){
	return reportService;   
   }
   
   public List InterestReceivedStatement (InvestmentReportsBean bean) throws Exception{
		return InvestmentReportDAO.getInstance().InterestReceivedStatement(bean);
	}
   public List getinterestMonths()
   {
	   return InvestmentReportDAO.getInstance().getinterestMonths();
   }
   public List getMaturityYears()
   {
	   return InvestmentReportDAO.getInstance().getMaturityYears();
   }
   public List InvestmentRegisterReport(InvestmentReportsBean bean)throws Exception
   {
	   return InvestmentReportDAO.getInstance().InvestmentRegisterReport(bean);
   }
   public List InvestmentgroupRegisterReport(InvestmentReportsBean bean)throws Exception
   {
	   return InvestmentReportDAO.getInstance().InvestmentgroupRegisterReport(bean);
   }
   public List InterestReceivedRegister(InvestmentReportsBean bean)throws Exception
   {
	   return InvestmentReportDAO.getInstance().InterestReceivedRegister(bean);
   }
   
   public List SecurityNames (String validdate) throws Exception{
		return InvestmentReportDAO.getInstance().SecurityNames(validdate);
	}
   public List groupNames()throws Exception
   {
	   return InvestmentReportDAO.getInstance().groupNames();
   }

public List getTrustInvestmentMadeDetails(InvestmentReportsBean bean) throws Exception {
	return InvestmentReportDAO.getInstance().getTrustInvestmentMadeDetails(bean);
}
public List getInvStReportData(String year,String Security,String trust,String fromDate,String toDate) throws ServiceNotAvailableException, EPISException{
	return InvestmentReportDAO.getInstance().getInvStReportData(year,Security,trust,fromDate,toDate);
}

public List SecurityCategories() throws Exception {
	return InvestmentReportDAO.getInstance().SecurityCategories();
}

public List InvestmentStatisticsDetails(InvestmentReportsBean bean) throws Exception {
	return InvestmentReportDAO.getInstance().InvestmentStatisticsDetails(bean);
}
public List getCurrentRatio() throws Exception{
	return InvestmentReportDAO.getInstance().getCurrentRatio();
}
public Map getAmountInvested(String Security,String trust,String fromDate,String toDate) throws Exception{
	return InvestmentReportDAO.getInstance().getAmountInvested(Security,trust,fromDate,toDate);
}

public List getYTMInvest(InvestmentReportsBean ibean) throws Exception {
	return InvestmentReportDAO.getInstance().getYTMInvest(ibean);
}

public List getRedumptionBonds(InvestmentReportsBean ibean) throws Exception {
	return InvestmentReportDAO.getInstance().getRedumptionBonds(ibean);
}

public List trustTotalInvestment(InvestmentReportsBean ibean) throws Exception {
	return InvestmentReportDAO.getInstance().trustTotalInvestment(ibean);
}
public List getInterestDetails(String year,String Security,String trust,String fromDate,String toDate) throws ServiceNotAvailableException, EPISException{
	return InvestmentReportDAO.getInstance().getInterestDetails(year,Security,trust,fromDate,toDate);
}
public Map getFileDetails(String Security,String trust,String fromDate,String toDate) throws Exception{
	return InvestmentReportDAO.getInstance().getFileDetails(Security,trust,fromDate,toDate);
}

}
