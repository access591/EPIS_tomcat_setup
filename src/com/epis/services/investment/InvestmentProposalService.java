/**
 * 
 */


/**
 * @author anilkumark
 *
 */
package com.epis.services.investment;

import java.util.List;

import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.bean.investment.InvestmentProposalBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.dao.investment.InvestmentProposalDAO;


public class InvestmentProposalService {
	private InvestmentProposalService(){
		
	}
   private static final InvestmentProposalService proposalService= new InvestmentProposalService();
   public static InvestmentProposalService getInstance(){
	return proposalService;   
   }
   
   
	public void saveInvestmentProposal(InvestmentProposalBean tbean) throws Exception{
		InvestmentProposalDAO.getInstance().saveInvestmentProposal(tbean);
	}
	public List searchInvestmentProposal(InvestmentProposalBean propBean) throws Exception{
		return InvestmentProposalDAO.getInstance().searchInvestmentProposal(propBean);
	}
	public List getProposal() throws Exception{
		return InvestmentProposalDAO.getInstance().getProposal();
	}
	public List getProposal(String mode) throws Exception{
		return InvestmentProposalDAO.getInstance().getProposal(mode);
	}
	
	
	public void editInvestmentProposal(InvestmentProposalBean tbean) throws Exception{
		InvestmentProposalDAO.getInstance().editInvestmentProposal(tbean);
	}
	public InvestmentProposalBean findProposal(String refNo,String compare) throws Exception{
		return InvestmentProposalDAO.getInstance().findProposal(refNo,compare);
	}
	public void deleteInvestmentProposal(String refnos)throws ServiceNotAvailableException,EPISException {
		InvestmentProposalDAO.getInstance().deleteInvestmentProposal(refnos);
	}
	public void approvalUpdate(InvestProposalAppBean tbean)throws ServiceNotAvailableException,EPISException
	{
		InvestmentProposalDAO.getInstance().approvalUpdate(tbean);
	}


	public List getInvestmentDetails(String refNo,String mode) throws EPISException {
		InvestmentProposalDAO dao = InvestmentProposalDAO.getInstance();
		List list = dao.getInvestmentDetails(refNo,mode);
		finYear = dao.getFinYear();
		return list;
	}
	String finYear;
	public String getFinYear(){
		return finYear;
	}

}

