package com.epis.action.cashbook;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

import com.epis.bean.RequestBean;
import com.epis.bean.admin.Bean;
import com.epis.bean.admin.RegionBean;
import com.epis.bean.admin.UnitBean;
import com.epis.bean.cashbook.JournalVoucherDetails;
import com.epis.bean.cashbook.JournalVoucherInfo;

import com.epis.services.admin.RegionService;
import com.epis.services.admin.UnitService;
import com.epis.services.cashbook.AccountingCodeService;
import com.epis.services.cashbook.JournalVoucherService;
import com.epis.services.investment.TrustTypeService;

import com.epis.utilities.DBUtility;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class JournalVoucher extends DispatchAction {

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
		ActionErrors errors = (ActionErrors)session.getAttribute("org.apache.struts.action.ERROR");
		try {
			if(errors == null)
				errors = new ActionErrors();			
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;	
			JournalVoucherInfo info = new JournalVoucherInfo(request);
			BeanUtils.copyProperties(info,dyna);
			request.setAttribute("records", JournalVoucherService.getInstance().search(info));			
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(session,errors);
		return mapping.getInputForward();
	}
	
	public ActionForward approvalSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			JournalVoucherInfo info = new JournalVoucherInfo(request);
			BeanUtils.copyProperties(info,dyna);
			info.setApproval("N");	
			log.info("approval in action "+info.getApproval());
			request.setAttribute("records",JournalVoucherService.getInstance().search(info));
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
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {					
			RequestBean info = new RequestBean(request);
			List stations = new ArrayList();
			if("C".equals(info.getLoginProfile())){
				List  region = RegionService.getInstance().getRegionList();
				int regSize = region.size();
				for(int i=0;i<regSize; i++){
					RegionBean regBean = (RegionBean)region.get(i);
					stations.add(new Bean(regBean.getRegionCode(),regBean.getRegionName()));					
				}				
			}else if("R".equals(info.getLoginProfile())){
				List  unit = UnitService.getInstance().searchUnit("","","",info.getLoginRegion());
				int unitSize = unit.size();
				for(int i=0;i<unitSize; i++){
					UnitBean unitBean = (UnitBean)unit.get(i);
					stations.add(new Bean(unitBean.getUnitCode(),unitBean.getUnitName()));					
				}				
			} 
			
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for(int i=finYears.length-1;i>=0;i--){
				finYear.add(new Bean(finYears[i],finYears[i]));				
			}
			request.setAttribute("finYear",finYear);
			request.setAttribute("toArray",new ArrayList());
			request.setAttribute("byArray",new ArrayList());
			request.setAttribute("trustTypes",TrustTypeService.getInstance().getTrustTypes());
			request.setAttribute("accountHeads",AccountingCodeService.getInstance().getAccountingHeads());
			request.setAttribute("stations", stations);
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(session,errors);
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
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		StringTokenizer recs = null;
		JournalVoucherDetails details = null;
		List recordsList = null;
		JournalVoucherInfo info = new JournalVoucherInfo(request);
		String gotoPage = "new";
		try{
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;			
			BeanUtils.copyProperties(info,dyna);
			String[] records = (String[])dyna.get("byRecords");			
			int size = records.length;
			recordsList = new ArrayList();
			for(int i=0;i<size;i++){
				recs = new StringTokenizer(records[i],"|");
				details = new JournalVoucherDetails();
				details.setAccountCode(recs.hasMoreTokens()?recs.nextToken():"");
				details.setDescription(recs.hasMoreTokens()?recs.nextToken():"");
				details.setDebit(recs.hasMoreTokens()?recs.nextToken():"");
				recordsList.add(details);
			}
			info.setDebitList(recordsList);
			records = (String[])dyna.get("toRecords");
			size = records.length;
			recordsList = new ArrayList();
			for(int i=0;i<size;i++){
				recs = new StringTokenizer(records[i],"|");
				details = new JournalVoucherDetails();
				details.setAccountCode(recs.hasMoreTokens()?recs.nextToken():"");
				details.setDescription(recs.hasMoreTokens()?recs.nextToken():"");
				details.setCredit(recs.hasMoreTokens()?recs.nextToken():"");
				recordsList.add(details);
			}
			info.setCreditList(recordsList);
			if("".equals(StringUtility.checknull(info.getKeyNo()))){
				JournalVoucherService.getInstance().save(info);
			}else {
				JournalVoucherService.getInstance().update(info);
				gotoPage = "search";
			}
		}catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}		
		saveErrors(session,errors);
		return mapping.findForward(gotoPage);
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
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {		
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;	
			JournalVoucherInfo info = new JournalVoucherInfo(request);
			BeanUtils.copyProperties(info,dyna);
			info = JournalVoucherService.getInstance().getJournalVoucher(info);						
			List stations = new ArrayList();
			if("C".equals(info.getLoginProfile())){
				List  region = RegionService.getInstance().getRegionList();
				int regSize = region.size();
				for(int i=0;i<regSize; i++){
					RegionBean regBean = (RegionBean)region.get(i);
					stations.add(new Bean(regBean.getRegionCode(),regBean.getRegionName()));					
				}				
			}else if("R".equals(info.getLoginProfile())){
				List  unit = UnitService.getInstance().searchUnit("","","",info.getLoginRegion());
				int unitSize = unit.size();
				for(int i=0;i<unitSize; i++){
					UnitBean unitBean = (UnitBean)unit.get(i);
					stations.add(new Bean(unitBean.getUnitCode(),unitBean.getUnitName()));					
				}				
			} 
			info.setStationName(StringUtility.checknull(info.getUnitCode())+StringUtility.checknull(info.getRegion()));
			String[] finYears = StringUtility.getFinYearsTill("2009");
			List finYear = new ArrayList();
			for(int i=finYears.length-1;i>=0;i--){
				finYear.add(new Bean(finYears[i],finYears[i]));				
			}
			
			request.setAttribute("finYear",finYear);
			request.setAttribute("toArray",info.getCreditList());
			request.setAttribute("byArray",info.getDebitList());
			request.setAttribute("trustTypes",TrustTypeService.getInstance().getTrustTypes());
			request.setAttribute("accountHeads",AccountingCodeService.getInstance().getAccountingHeads());
			request.setAttribute("stations", stations);
			BeanUtils.copyProperties(dyna,info);
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(session,errors);
		return mapping.findForward("new");
	}
	
	/**
	 * Method getReport
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {		
			DynaValidatorActionForm dyna = (DynaValidatorActionForm) form;	
			JournalVoucherInfo info = new JournalVoucherInfo(request);
			BeanUtils.copyProperties(info,dyna);
			info = JournalVoucherService.getInstance().getJournalVoucher(info);
			SQLHelper sql = new SQLHelper();			
			info.setStationName("".equals(StringUtility.checknull(info.getUnitCode()))?sql.getDescription("employee_region_master","REGIONNAME","REGIONCD",info.getRegion(),DBUtility.getConnection()):sql.getDescription("employee_unit_master","UNITNAME","UNITCODE",info.getUnitCode(),DBUtility.getConnection()));
			request.setAttribute("info",info);
		} catch (Exception e) {
			errors.add("",new ActionMessage("errors",e.getMessage()));
		}
		saveErrors(session,errors);
		return mapping.findForward("report");
	}
	
	public ActionForward deleteApprovals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String keynos = request.getParameter("keynos");		
		JournalVoucherInfo info = new JournalVoucherInfo(request);
		 keynos = keynos.substring(0, keynos.length() - 1);
		 JournalVoucherService.getInstance().deleteApprovals(keynos,info.getLoginUserId(),info.getLoginUnitCode());
		return mapping.findForward("search");
	
	}
	
	public ActionForward fwdtoapproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//SQLHelper sql=new SQLHelper();
		try {	
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			JournalVoucherInfo info = new JournalVoucherInfo(request);
			BeanUtils.copyProperties(info,dyna);
			log.info("key no in approval is"+info.getKeyNo());
			info = JournalVoucherService.getInstance().getJournalVoucher(info);
			SQLHelper sql = new SQLHelper();			
			info.setStationName("".equals(StringUtility.checknull(info.getUnitCode()))?sql.getDescription("employee_region_master","REGIONNAME","REGIONCD",info.getRegion(),DBUtility.getConnection()):sql.getDescription("employee_unit_master","UNITNAME","UNITCODE",info.getUnitCode(),DBUtility.getConnection()));
			request.setAttribute("info",info);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}					
		return mapping.findForward("new");
	}
	
	public ActionForward SaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("inside Save Approval");
		JournalVoucherInfo info = new JournalVoucherInfo(request);
		try {	
			DynaValidatorActionForm dyna = (DynaValidatorActionForm)form;
			
			BeanUtils.copyProperties(info,dyna);
			info.setFinYear(StringUtility.checknull(request.getParameter("fyear")));			
			info=JournalVoucherService.getInstance().SaveApproval(info);
			request.setAttribute("info",info);			
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("",new ActionMessage("errors",e.getMessage()));
			saveErrors(request,errors);
		}	
		String path=mapping.findForward("search").getPath()+"&&keyNo="+info.getKeyNo();
		System.out.println("Mapping "+path);
		return new ActionForward(path);
	
	}
	
}
