/**
 * 
 */
package com.epis.action.cashbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.epis.bean.admin.UserBean;
import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.cashbook.FundsTransferInfo;
import com.epis.dao.admin.UserDAO;
import com.epis.services.cashbook.BankMasterService;
import com.epis.services.cashbook.FundsTransferService;
import com.epis.utilities.DBUtility;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

/**
 * @author jayasreek
 *
 */
public class FundsTransfer extends DispatchAction {
	
	/**
	 * Method search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo();
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			request.setAttribute("records",FundsTransferService.getInstance().search(info));			
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.getInputForward();
	}
	
	/**
	 * Method approvalSearch
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward approvalSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo(request);
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			info.setApproval("N");				
			request.setAttribute("records",FundsTransferService.getInstance().search(info));
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.getInputForward();
	}
	
	public ActionForward finalApprovalSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo();
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			info.setApproval("Y");
			info.setFinalApproval("N");
			request.setAttribute("records",FundsTransferService.getInstance().search(info));			
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}finally{
			saveErrors(session,errors);
		}		
		return mapping.getInputForward();
	}
	
	/**
	 * Method fwdtoNew
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdtoNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {	
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));			
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		return mapping.findForward("new");
	}
	
	/**
	 * Method save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = mapping.findForward("search");
		try {
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));	
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo(request);
			BeanUtils.copyProperties(info,dyna);			
			if(info.getKeyno() !=null && !"".equals(info.getKeyno())){
				FundsTransferService.getInstance().update(info);
			}else{
				FundsTransferService.getInstance().save(info);
			}
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
			forward = mapping.findForward("new");
		}finally{
			saveErrors(request,errors);
		}
		return forward;
	}
	
	/**
	 * Method fwdtoEdit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdtoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {	
			request.setAttribute("banks",BankMasterService.getInstance().search(new BankMasterInfo()));
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo(request);
			info.setKeyno(request.getParameter("code"));
			BeanUtils.copyProperties(dyna,FundsTransferService.getInstance().getRecord(info));
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		return mapping.findForward("new");
	}
	
	/**
	 * Method fwdtoapproval
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward fwdtoapproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SQLHelper sql=new SQLHelper();
		try {	
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo(request);
			BeanUtils.copyProperties(info,dyna);
			
			UserBean user = UserDAO.getInstance().getUser(info.getLoginUserId());
			String path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			
			info.setAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
			info.setAppDispalyName(user.getDisplayName());
			info.setAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
			request.setAttribute("info",FundsTransferService.getInstance().getApprovalRecord(info));
			UserDAO.getInstance().getImage(path,user.getUserName());
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		return mapping.findForward("approval");
	}
	
	public ActionForward SaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("inside Save Approval");
		FundsTransferInfo info = new FundsTransferInfo(request);
		try {	
			log.info("inside try block");
					
			
			info.setKeyno(request.getParameter("keyno"));						
			info=FundsTransferService.getInstance().SaveApproval(info);
			request.setAttribute("info",info);			
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}	
		String path=mapping.findForward("search").getPath()+"&&keyno="+info.getKeyno();
		System.out.println("Mapping "+path);
		return new ActionForward(path);
	
	}
	
	public ActionForward fwdtofinapproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SQLHelper sql=new SQLHelper();
		try {	
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			FundsTransferInfo info = new FundsTransferInfo(request);
			BeanUtils.copyProperties(info,dyna);
			
			UserBean user = UserDAO.getInstance().getUser(info.getApprovedBy());
			String 	path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			info.setAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
			info.setAppDispalyName(user.getDisplayName());			
			info.setAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
			try {
				UserDAO.getInstance().getImage(path,user.getUserName());
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!StringUtility.checknull(info.getFinalApprovedBy()).equals(""))
				user = UserDAO.getInstance().getUser(info.getFinalApprovedBy());
			else
				user = UserDAO.getInstance().getUser(info.getLoginUserId());
			path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();			
			info.setFinAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());			
			info.setFinAppDispalyName(user.getDisplayName());			
			info.setFinAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
	
			request.setAttribute("info",FundsTransferService.getInstance().getApprovalRecord(info));
			UserDAO.getInstance().getImage(path,user.getUserName());
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		return mapping.findForward("approval");
	}
	public ActionForward SaveFinalApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SQLHelper sql=new SQLHelper();		
		FundsTransferInfo info = new FundsTransferInfo(request);
		try {		
			
			info.setKeyno(request.getParameter("keyno"));						
			info=FundsTransferService.getInstance().SaveFinalApproval(info);
			info.setFinalApproval("Y");
			UserBean user = UserDAO.getInstance().getUser(info.getApprovedBy());
			String 	path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();
			
			info.setAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
			info.setAppDispalyName(user.getDisplayName());			
			info.setAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
			try {
				UserDAO.getInstance().getImage(path,user.getUserName());
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user = UserDAO.getInstance().getUser(info.getLoginUserId());
			path = request.getSession().getServletContext().getRealPath("/");
			path +="uploads/SIGNATORY/"+user.getEsignatoryName();			
			info.setFinAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());			
			info.setFinAppDispalyName(user.getDisplayName());			
			info.setFinAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
	
			request.setAttribute("info",info);
			
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		String path=mapping.findForward("search").getPath()+"&&keyno="+info.getKeyno()+"&&approvedBy="+info.getApprovedBy();
		System.out.println("Mapping "+path);
		return new ActionForward(path);
	
	}
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SQLHelper sql=new SQLHelper();
		try {	
				DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
				FundsTransferInfo info = new FundsTransferInfo(request);
				UserBean user = null;
				String 	path = "";
				BeanUtils.copyProperties(info,dyna);
				info = FundsTransferService.getInstance().getApprovalRecord(info);
				if(!StringUtility.checknull(info.getApprovedBy()).equals("")){
					user = UserDAO.getInstance().getUser(info.getApprovedBy());
					path = request.getSession().getServletContext().getRealPath("/");
					path +="uploads/SIGNATORY/"+user.getEsignatoryName();
					info.setAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());
					info.setAppDispalyName(user.getDisplayName());			
					info.setAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
					try {
						UserDAO.getInstance().getImage(path,user.getUserName());
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(!StringUtility.checknull(info.getFinalApprovedBy()).equals("")){
					user = UserDAO.getInstance().getUser(info.getFinalApprovedBy());
					path = request.getSession().getServletContext().getRealPath("/");
					path +="uploads/SIGNATORY/"+user.getEsignatoryName();			
					info.setFinAppSignPath("uploads/SIGNATORY/"+user.getEsignatoryName());			
					info.setFinAppDispalyName(user.getDisplayName());			
					info.setFinAppDesignation(sql.getDescription("employee_personal_info","DESEGNATION","PENSIONNO",user.getEmployeeId(),DBUtility.getConnection()));
				}
				request.setAttribute("info",info);
			} catch (Exception e) {
				ActionErrors errors = new ActionErrors();
				errors.add("",new ActionMessage("errors",e.getMessage()));
				saveErrors(request,errors);
		}					
		return mapping.findForward("report");
	}
	
	public ActionForward deleteApprovals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String keynos = request.getParameter("keynos");		
		FundsTransferInfo info = new FundsTransferInfo(request);
		 keynos = keynos.substring(0, keynos.length() - 1);
		 FundsTransferService.getInstance().deleteApprovals(keynos,info.getLoginUserId());
		return mapping.findForward("search");
	
	}
	
	
}
