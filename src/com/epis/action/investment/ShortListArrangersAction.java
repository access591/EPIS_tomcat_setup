package com.epis.action.investment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;

import com.epis.services.investment.ShortListService;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class ShortListArrangersAction extends DispatchAction {
	Log log = new Log(ShortListArrangersAction.class);

	public ActionForward showShortListAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServiceNotAvailableException,
			EPISException {
		List shortlettersList = ShortListService.getInstance()
				.getShortListLetterNos();
		request.setAttribute("shortlettersList", shortlettersList);
		return mapping.findForward("showshortnew");
	}

	public ActionForward showShortList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm compstmt = (DynaValidatorForm) form;
		HttpSession session = request.getSession();
		ActionForward forward = null;
		String letterno = StringUtility.checknull(compstmt
				.getString("letterNo"));
		String remarks = StringUtility.checknull(compstmt.getString("remarks"));
		try {
			String slFlag = StringUtility.checknull(request
					.getParameter("slFlag"));
			QuotationBean bean = new QuotationBean(request);
			bean.setLetterNo(letterno);
			bean.setRemarks(remarks);
			if ("Y".equals(slFlag)) {
				ShortListService.getInstance().updateSLFlag(bean);
				forward = mapping.findForward("shortsearch");
			} else {
				HashMap arranger = ShortListService.getInstance()
						.showShortList(letterno, remarks);
				String hashkey = "";
				HashMap security = null;

				List arrangers = new ArrayList();
				List securities = new ArrayList();
				SortedSet arrsort = new TreeSet(arranger.keySet());
				Iterator iterator = (arrsort).iterator();
				while (iterator.hasNext()) {
					hashkey = iterator.next().toString();
					arrangers.add(hashkey);
					security = (HashMap) arranger.get(hashkey);
					SortedSet secsort = new TreeSet(security.keySet());
					Iterator iter = secsort.iterator();
					while (iter.hasNext()) {
						hashkey = (String) iter.next();
						if (!securities.contains(hashkey)) {
							securities.add(hashkey);
						}
					}
				}
				int arrSize = arrangers.size() + 3;
				int secSize = securities.size() + 1;
				String[][] comp = new String[secSize][arrSize];
				comp[0][0] = "Sr. No.";
				comp[0][1] = "Security Name";
				comp[0][arrSize - 1] = "Remarks";
				for (int i = 2; i < arrSize - 1; i++) {
					comp[0][i] = (String) arrangers.get(i - 2);
				}
				for (int i = 1; i < secSize; i++) {
					comp[i][0] = String.valueOf(i);
					comp[i][1] = (String) securities.get(i - 1);
				}
				Map sec = null;
				QuotationBean qbean = null;
				for (int i = 2; i < arrSize - 1; i++) {
					for (int j = 1; j < secSize; j++) {
						if (arranger.containsKey(comp[0][i])) {
							sec = (HashMap) arranger.get(comp[0][i]);
							if (sec.containsKey(comp[j][1])) {
								qbean = (QuotationBean) sec.get(comp[j][1]);
								comp[j][i] = qbean.getPurchasePrice()
										+ "/"
										+ qbean.getYtm()
										+ "<input type=checkbox  name='shortlist'  value='"
										+ comp[0][i] + "-" + comp[j][1] + "'>";
								if (qbean.getRemarks() != null)
									comp[j][arrSize - 1] = qbean.getRemarks();
							}
						}
					}
				}
				session.setAttribute("arrSize", new Integer(arrSize));
				session.setAttribute("secSize", new Integer(secSize));
				session.setAttribute("compArray", comp);
				StringBuffer buffer = new StringBuffer(mapping.findForward(
						"showshortstmt").getPath());
				buffer.append("?letterNo=" + letterno);
				forward = (new ActionForward(buffer.toString(), true));
			}
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("trust", new ActionMessage("trust.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}

		return forward;
	}
	public ActionForward getShortListedReport(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception
	{
		DynaValidatorForm compstmt = (DynaValidatorForm) form;
		HttpSession session = req.getSession();
		ActionForward forward = mapping.findForward("shortReport");
		String letterno = StringUtility.checknull(compstmt
				.getString("letterNo"));
		QuotationBean bean=new QuotationBean(req);
		bean.setLetterNo(letterno);
		
		try{
			HashMap arranger = ShortListService.getInstance()
			.showShortList(letterno);
	String hashkey = "";
	HashMap security = null;

	List arrangers = new ArrayList();
	List securities = new ArrayList();
	SortedSet arrsort = new TreeSet(arranger.keySet());
	Iterator iterator = (arrsort).iterator();
	while (iterator.hasNext()) {
		hashkey = iterator.next().toString();
		arrangers.add(hashkey);
		security = (HashMap) arranger.get(hashkey);
		SortedSet secsort = new TreeSet(security.keySet());
		Iterator iter = secsort.iterator();
		while (iter.hasNext()) {
			hashkey = (String) iter.next();
			if (!securities.contains(hashkey)) {
				securities.add(hashkey);
			}
		}
	}
	int arrSize = arrangers.size() + 3;
	int secSize = securities.size() + 1;
	String[][] comp = new String[secSize][arrSize];
	comp[0][0] = "Sr. No.";
	comp[0][1] = "Security Name";
	comp[0][arrSize - 1] = "Remarks";
	for (int i = 2; i < arrSize - 1; i++) {
		comp[0][i] = (String) arrangers.get(i - 2);
	}
	for (int i = 1; i < secSize; i++) {
		comp[i][0] = String.valueOf(i);
		comp[i][1] = (String) securities.get(i - 1);
	}
	Map sec = null;
	QuotationBean qbean = null;
	for (int i = 2; i < arrSize - 1; i++) {
		for (int j = 1; j < secSize; j++) {
			if (arranger.containsKey(comp[0][i])) {
				sec = (HashMap) arranger.get(comp[0][i]);
				if (sec.containsKey(comp[j][1])) {
					qbean = (QuotationBean) sec.get(comp[j][1]);
					comp[j][i] = qbean.getPurchasePrice()
							+ "/"
							+ qbean.getYtm()
							+ "("+qbean.getShortlisted()+")";
					if (qbean.getRemarks() != null)
						comp[j][arrSize - 1] = qbean.getRemarks();
				}
			}
		}
	}
	session.setAttribute("arrSize", new Integer(arrSize));
	session.setAttribute("secSize", new Integer(secSize));
	session.setAttribute("compArray", comp);
	req.setAttribute("quotationbean",bean);
	
		}
		catch(Exception e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add("trust", new ActionMessage("trust.Errors", e
					.getMessage()));
			saveErrors(req, errors);
			forward = mapping.getInputForward();
		}
		
		return forward;
	}

	public ActionForward saveShortList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuotationBean bean = new QuotationBean(request);
		UtilityBean util = null;
		ActionForward forward = mapping.findForward("shortsearch");
		try {
			String element = "";
			StringTokenizer strtokens = null;
			String arrshort = request.getParameter("shortarrangers");
			StringTokenizer tokens = new StringTokenizer(arrshort, ",");
			ArrayList array = new ArrayList();
			while (tokens.hasMoreTokens()) {
				element = (String) tokens.nextElement();
				strtokens = new StringTokenizer(element, "-");
				while (strtokens.hasMoreTokens()) {
					util = new UtilityBean();
					String arrangers = (String) strtokens.nextElement();
					String securities = (String) strtokens.nextElement();
					util.setKey(arrangers);
					util.setValue(securities);
				}
				array.add(util);
			}
			bean.setShortListArrangres(array);
			bean.setLetterNo(StringUtility.checknull(request
					.getParameter("letterNo")));
			ShortListService.getInstance().saveShortList(bean);

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("trust", new ActionMessage("trust.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward searchShortlist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = mapping.findForward("showshortsearch");
		try {
			List shortList = null;
			DynaValidatorForm dyna = (DynaValidatorForm) form;
			QuotationBean bean = new QuotationBean();
			bean.setLetterNo(StringUtility
					.checknull(dyna.getString("letterNo")));
			bean.setStatus(StringUtility.checknull(dyna.getString("status")));
			shortList = ShortListService.getInstance().searchShortList(bean);
			request.setAttribute("shortList", shortList);
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add("trust", new ActionMessage("trust.Errors", e
					.getMessage()));
			saveErrors(request, errors);
			forward = mapping.getInputForward();
		}
		return forward;
	}

}
