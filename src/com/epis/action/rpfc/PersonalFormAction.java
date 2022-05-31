package com.epis.action.rpfc;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;


import com.epis.action.advances.ForwardParameters;
import com.epis.bean.admin.Bean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.EmployeeAdlPensionInfo;
import com.epis.bean.rpfc.EmployeePersonalInfo;
import com.epis.bean.rpfc.NomineeForm2Info;
import com.epis.bean.rpfc.RPFCForm9Bean;
import com.epis.bean.rpfc.SearchInfo;
import com.epis.bean.rpfc.SignatureMappingBean;
import com.epis.dao.CommonDAO;
import com.epis.dao.rpfc.PersonalDAO;
import com.epis.info.login.LoginInfo;
import com.epis.services.rpfc.FinancialReportService;
import com.epis.services.rpfc.FinancialService;
import com.epis.services.rpfc.PensionService;
import com.epis.services.rpfc.PersonalService;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class PersonalFormAction extends CommonRPFCAction{
	CommonUtil commonUtil=new CommonUtil();
	CommonDAO commonDAO=new CommonDAO();
	PensionService ps = new PensionService();
	PersonalDAO personalDAO=new PersonalDAO();
	FinancialService fs = new FinancialService();
	PersonalService personalService = new PersonalService();
	Log log = new Log(PersonalFormAction.class);
	
	public ActionForward loadForm10SubmitForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String frmPensionNo="",frmRegion="";
		ArrayList airportList=new ArrayList();
		ArrayList departmentList=new ArrayList();
		HttpSession httpsession = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) httpsession.getAttribute("user");

		if (logInfo.getProfile().equals("M")) {
			frmPensionNo = logInfo.getPensionNo();
			frmRegion= logInfo.getRegion();
		} else {
			if(request.getParameter("pfid")!=null){
				frmPensionNo=request.getParameter("pfid");
			}else{
				return loadForm10D(mapping,form,request,response);
			}
			
		}
		EmployeeAdlPensionInfo adlPensionInfo = new EmployeeAdlPensionInfo();
		EmpMasterBean editBean = new EmpMasterBean();
		try {
			editBean = personalService.empForm10DPersonalEdit("",
					"", "", frmPensionNo);
			adlPensionInfo = personalService.getPensionForm10DDtls(frmPensionNo);
			departmentList = ps.getDepartmentList();
			if(logInfo.getProfile().equals("M") || logInfo.getProfile().equals("U")){
				airportList.add(logInfo.getUnitName());
			}else{
				airportList = commonUtil.getAirports(frmRegion);
			}
			
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		request.setAttribute("EditBean", editBean);
		request.setAttribute("adlPensionInfo", adlPensionInfo);
		request.setAttribute("AirportList", airportList);
		request.setAttribute("DepartmentList", departmentList);

		return mapping.findForward("loadform10D");
	}
	public ActionForward form10dUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction : form1dUpdate");
		EmpMasterBean bean = new EmpMasterBean();
		EmployeeAdlPensionInfo addtionalPensionInfo = new EmployeeAdlPensionInfo();
		String flag = "", empName = "";
		String startIndex = "", totalData = "";
		String editFrom = "";
		String arrearInfo = "";

		if (request.getParameter("setEmpSerialNo") != null) {
			bean.setEmpSerialNo(request.getParameter("setEmpSerialNo")
					.trim().toString());
		}
		if (request.getParameter("select_fhname") != null) {
			bean.setFhFlag(request.getParameter("select_fhname").trim()
					.toString());
		}

		startIndex = request.getParameter("startIndex");
		totalData = request.getParameter("totalData");

		// New Patameters added while Changing employee information from one
		// region to other
		if (request.getParameter("region") != null) {
			bean
			.setRegion(request.getParameter("region").trim()
					.toString());
		} else {
			bean.setRegion("");
		}

		if (request.getParameter("editFrom") != null) {
			editFrom = request.getParameter("editFrom");
		}
		if (request.getParameter("flagData") != null) {
			flag = request.getParameter("flagData");
		} else {
			flag = "true";
		}
		if (request.getParameter("empName") != null) {
			bean.setEmpName(request.getParameter("empName").trim()
					.toString());
		} else {
			bean.setEmpName("");
		}

		if (request.getParameter("employeeCode") != null) {
			bean.setEmpNumber(request.getParameter("employeeCode").trim()
					.toString());
		} else {
			bean.setEmpNumber("");
		}

		if (request.getParameter("airPortCode") != null) {
			bean.setStation(request.getParameter("airPortCode").toString());
		} else {
			bean.setStation("");
		}
		if (request.getParameter("desegnation") != null) {
			bean.setDesegnation(request.getParameter("desegnation")
					.toString());
		} else {
			bean.setDesegnation("");
		}
		log.info("old cpfacno " + request.getParameter("cpfacno"));

		if (request.getParameter("cpfacno") != null) {
			bean.setCpfAcNo(request.getParameter("cpfacno").toString());
		} else {
			bean.setCpfAcNo("");
		}
		log.info("new cpfacno " + request.getParameter("cpfacnoNew"));
		if (request.getParameter("cpfacnoNew") != null) {
			bean.setNewCpfAcNo(request.getParameter("cpfacnoNew")
					.toString());
		} else {
			bean.setNewCpfAcNo("");
		}
		if (request.getParameter("pfid") != null) {
			bean.setPfid(request.getParameter("pfid").toString());
		} else {
			bean.setPfid("");
		}
		if (request.getParameter("employeeCode") != null) {
			bean.setEmpNumber(request.getParameter("employeeCode").trim()
					.toString());
		} else {
			bean.setEmpNumber("");
		}
		if (request.getParameter("airportSerialNumber") != null) {
			bean.setAirportSerialNumber(request.getParameter(
			"airportSerialNumber").trim().toString());
		} else {
			bean.setAirportSerialNumber("");
		}

		if (request.getParameter("dateofbirth") != null) {
			bean.setDateofBirth(request.getParameter("dateofbirth"));
		} else {
			bean.setDateofBirth("");
		}

		if (request.getParameter("dateofjoining") != null) {
			bean.setDateofJoining(request.getParameter("dateofjoining")
					.toString());
		} else {
			bean.setDateofJoining("");
		}
		if (request.getParameter("reason") != null) {
			bean.setSeperationReason(request.getParameter("reason")
					.toString());
		} else {
			bean.setSeperationReason("");
		}
		if (request.getParameter("Other") != null) {
			bean.setOtherReason(request.getParameter("Other").toString());
		} else {
			bean.setOtherReason("");
		}

		if (request.getParameter("seperationDate") != null) {
			bean.setDateofSeperationDate(request.getParameter(
			"seperationDate").toString());
		} else {
			bean.setSeperationReason("");
		}

		if (request.getParameter("emailId") != null) {
			bean.setEmailId(request.getParameter("emailId").toString());
		} else {
			bean.setEmailId("");
		}

		if (request.getParameter("region") != null) {
			bean
			.setRegion(request.getParameter("region".trim()
					.toString()));
		} else {
			bean.setRegion("");
		}

		if (request.getParameter("paddress") != null) {
			bean.setPermanentAddress(request.getParameter("paddress")
					.toString().trim());
		} else {
			bean.setPermanentAddress("");
		}
		if (request.getParameter("taddress") != null) {
			bean.setTemporatyAddress(request.getParameter("taddress")
					.toString().trim());
		} else {
			bean.setTemporatyAddress("");
		}

		if (request.getParameter("wetherOption") != null) {
			bean.setWetherOption(request.getParameter("wetherOption")
					.toString().trim());
		} else {
			bean.setWetherOption("");
		}
		String heightInFeet = "", heightInInches = "";
		if (request.getParameter("height_feet") != null) {
			heightInFeet = request.getParameter("height_feet");
		} else {
			heightInFeet = "";
		}
		if (request.getParameter("height_inches") != null) {
			heightInInches = request.getParameter("height_inches");
		} else {
			heightInInches = "";
		}
		bean.setHeightWithInches(heightInFeet + "." + heightInInches);
		if (request.getParameter("nationality") != null) {
			bean.setNationality(request.getParameter("nationality")
					.toString().trim());
		} else {
			bean.setNationality("");
		}

		if (request.getParameter("sex") != null) {
			bean.setSex(request.getParameter("sex").toString().trim());
		} else {
			bean.setSex("");
		}
		if (request.getParameter("mstatus") != null) {
			bean.setMaritalStatus(request.getParameter("mstatus")
					.toString().trim());
		} else {
			bean.setMaritalStatus("");
		}
		if (request.getParameter("fhname") != null) {
			bean
			.setFhName(request.getParameter("fhname").toString()
					.trim());
		} else {
			bean.setFhName("");
		}
		if (request.getParameter("select_fhname") != null) {
			bean
			.setFhFlag(request.getParameter("fhname").toString()
					.trim());
		} else {
			bean.setFhFlag("");
		}
		if (request.getParameter("quantum_option") != null) {
			addtionalPensionInfo.setQuantum1By3Option(request.getParameter(
			"quantum_option").toString().trim());
		} else {
			addtionalPensionInfo.setQuantum1By3Option("");
		}

		if (request.getParameter("quantumamount") != null) {
			addtionalPensionInfo.setQuantum1By3Amount(request.getParameter(
			"quantumamount").toString().trim());
		} else {
			addtionalPensionInfo.setQuantum1By3Amount("");
		}
		if (request.getParameter("select_return_captial") != null) {
			addtionalPensionInfo.setOptionReturnCaptial(request
					.getParameter("select_return_captial").toString()
					.trim());
		} else {
			addtionalPensionInfo.setOptionReturnCaptial("");
		}
		if (request.getParameter("deathofmember_date") != null) {
			addtionalPensionInfo.setMemberDeathDate(request.getParameter(
			"deathofmember_date").toString().trim());
		} else {
			addtionalPensionInfo.setMemberDeathDate("");
		}
		if (request.getParameter("select_saving_account") != null) {
			addtionalPensionInfo.setPaymentInfoType(request.getParameter(
			"select_saving_account").toString().trim());
		} else {
			addtionalPensionInfo.setPaymentInfoType("");
		}

		if (request.getParameter("savingbankingname") != null) {
			addtionalPensionInfo.setNameofPaymentBranch(request
					.getParameter("savingbankingname").toString().trim());
		} else {
			addtionalPensionInfo.setNameofPaymentBranch("");
		}
		if (request.getParameter("savingaccpostaladdr") != null) {
			addtionalPensionInfo.setAddressofPayementBranch(request
					.getParameter("savingaccpostaladdr").toString().trim());
		} else {
			addtionalPensionInfo.setAddressofPayementBranch("");
		}
		if (request.getParameter("savingbankpincode") != null) {
			addtionalPensionInfo.setPaymentBranchPincode(request
					.getParameter("savingbankpincode").toString().trim());
		} else {
			addtionalPensionInfo.setPaymentBranchPincode("");
		}
		if (request.getParameter("claimRefNominee") != null) {
			addtionalPensionInfo.setClaimNomineeRefName(request
					.getParameter("claimRefNominee").toString().trim());
		} else {
			addtionalPensionInfo.setClaimNomineeRefName("");
		}
		if (request.getParameter("claimRefNomineeRel") != null) {
			addtionalPensionInfo.setClaimNomineeRefRelation(request
					.getParameter("claimRefNomineeRel").toString().trim());
		} else {
			addtionalPensionInfo.setClaimNomineeRefRelation("");
		}
		if (request.getParameter("scheme_cert_rec") != null) {
			addtionalPensionInfo.setSchemeCertificateRecEncl(request
					.getParameter("scheme_cert_rec").toString().trim());
		} else {
			addtionalPensionInfo.setSchemeCertificateRecEncl("");
		}
		if (request.getParameter("possession_member") != null) {
			addtionalPensionInfo.setPossesionMember(request.getParameter(
			"possession_member").toString().trim());
		} else {
			addtionalPensionInfo.setPossesionMember("");
		}
		if (request.getParameter("nomineePostalAddr") != null) {
			addtionalPensionInfo.setNomineePostalAddrss(request
					.getParameter("nomineePostalAddr").toString().trim());
		} else {
			addtionalPensionInfo.setNomineePostalAddrss("");
		}
		if (request.getParameter("nomineePostalPincode") != null) {
			addtionalPensionInfo.setNomineePincode(request.getParameter(
			"nomineePostalPincode").toString().trim());
		} else {
			addtionalPensionInfo.setNomineePincode("");
		}

		if (request.getParameter("select_ppo_issue_by") != null) {
			addtionalPensionInfo.setPponoIssuedBy(request.getParameter(
			"select_ppo_issue_by").toString().trim());
		} else {
			addtionalPensionInfo.setPponoIssuedBy("");
		}
		if (request.getParameter("select_pension_drwn_1995") != null) {
			addtionalPensionInfo.setPensionDrwnFrom1995(request
					.getParameter("select_pension_drwn_1995").toString()
					.trim());
		} else {
			addtionalPensionInfo.setPensionDrwnFrom1995("");
		}

		if (request.getParameter("select_pension_drwn_1995") != null) {
			addtionalPensionInfo.setPensionDrwnFrom1995(request
					.getParameter("select_pension_drwn_1995").toString()
					.trim());
		} else {
			addtionalPensionInfo.setPensionDrwnFrom1995("");
		}

		if (request.getParameter("early_Pension_flag") != null) {
			addtionalPensionInfo.setEarlyPensionTaken(request.getParameter(
			"early_Pension_flag").toString().trim());
		} else {
			addtionalPensionInfo.setEarlyPensionTaken("N");
		}

		if (request.getParameter("earlypension_date") != null) {
			addtionalPensionInfo.setEarlyPensionDate(request.getParameter(
			"earlypension_date").toString().trim());
		} else {
			addtionalPensionInfo.setEarlyPensionDate("");
		}
		if (request.getParameter("earlypension_form10Ddate") != null) {
			addtionalPensionInfo.setEpForm10DSubDate(request.getParameter(
			"earlypension_form10Ddate").toString().trim());
		} else {
			addtionalPensionInfo.setEpForm10DSubDate("");
		}

		/* for Nominee Details */
		if (request.getParameterValues("Nname") != null) {
			String nomineeRowID[] = request
			.getParameterValues("nomineerowid");
			String Nname[] = request.getParameterValues("Nname");
			String empOldNname[] = request
			.getParameterValues("empOldNname");
			String Naddress[] = request.getParameterValues("Naddress");
			String Ndob[] = request.getParameterValues("Ndob");
			String Nrelation[] = request.getParameterValues("Nrelation");
			String guardianname[] = request
			.getParameterValues("guardianname");
			String gaddress[] = request.getParameterValues("gaddress");
			String savingaccount[] = request
			.getParameterValues("saving_acc_no");
			String returnCaptial[] = request
			.getParameterValues("option_flag");
			StringBuffer nomineeRow = new StringBuffer();
			log.info("Nominee Length" + Nname.length + "Return Of Captial"
					+ returnCaptial.length + "Saving Account"
					+ savingaccount.length);
			String nAddress = "", nDob = "", nRowid = "", nRelation = "", nGuardianname = "", nAccountNo = "", gardaddressofNaminee = "";
			String returnCaptialFlag = "";
			String empOldNomineeName = "";
			boolean returnCaptialLenFlag = false;
			for (int i = 0; i < Nname.length; i++) {

				if (i == returnCaptial.length) {
					returnCaptialLenFlag = true;
				}
				if (!Nname[i].equals("")) {
					nomineeRow.append(Nname[i].toString() + "@");
					if (Naddress[i].equals("")) {
						nAddress = "XXX";
					} else {
						nAddress = Naddress[i].toString();
					}
					if (Nrelation[i].equals("")) {
						nRelation = "XXX";
					} else {
						nRelation = Nrelation[i].toString();
					}

					if (Ndob[i].equals("")) {
						nDob = "XXX";
					} else {
						nDob = Ndob[i].toString();
					}
					if (guardianname[i].equals("")) {
						nGuardianname = "XXX";
					} else {
						nGuardianname = guardianname[i].toString();
					}
					if (gaddress[i].equals("")) {
						gardaddressofNaminee = "XXX";
					} else {
						gardaddressofNaminee = gaddress[i].toString()
						.trim();
					}

					if (empOldNname[i].equals("")) {
						empOldNomineeName = "XXX";
					} else {
						empOldNomineeName = empOldNname[i].toString()
						.trim();
					}
					if (returnCaptialLenFlag == false) {
						if (!"".equals(returnCaptial[i])) {
							returnCaptialFlag = returnCaptial[i].toString();
						} else {
							returnCaptialFlag = "XXX";

						}
					} else {
						returnCaptialFlag = "XXX";
					}

					if (!"".equals(savingaccount[i])) {
						nAccountNo = savingaccount[i].toString();
					} else {
						nAccountNo = "XXX";

					}

					if (!nomineeRowID[i].equals("")) {
						nRowid = nomineeRowID[i].toString();
					} else {
						nRowid = "XXX";
					}
					nomineeRow.append(nAddress + "@");
					nomineeRow.append(nDob + "@");
					nomineeRow.append(nRelation + "@");
					nomineeRow.append(nGuardianname + "@");
					nomineeRow.append(gardaddressofNaminee + "@");
					nomineeRow.append(empOldNomineeName + "@");
					nomineeRow.append(returnCaptialFlag + "@");
					nomineeRow.append(nAccountNo + "@");
					nomineeRow.append(nRowid);
					nomineeRow.append("***");
					log.info("Nominee" + nomineeRow);
				}

			}
			String schmeInfo = "";
			if (request.getParameter("frm_schemeinfo") != null) {
				schmeInfo = request.getParameter("frm_schemeinfo");
				ArrayList schemeList = new ArrayList();
				if (!schmeInfo.equals("")) {
					String[] extractSchmeInfo = schmeInfo.split(":");
					log.info("SearchServelt::form10dUpdate"
							+ extractSchmeInfo.length + schmeInfo);
					if (extractSchmeInfo.length != 0) {

						for (int schme = 0; schme < extractSchmeInfo.length; schme++) {

							schemeList.add(extractSchmeInfo[schme]);
						}
						bean.setSchemeList(schemeList);
					}
				} else {
					bean.setSchemeList(schemeList);
				}

			}
			if (request.getParameter("frm_documentinfo") != null) {
				addtionalPensionInfo.setDocumentInfo(request
						.getParameter("frm_documentinfo"));
			} else {
				addtionalPensionInfo.setDocumentInfo("");
			}

			log.info("form10dUpdate::Nominee data " + nomineeRow.toString()
					+ "schmeInfo data " + schmeInfo + "DocumentInfo"
					+ request.getParameter("frm_documentinfo"));

			if (nomineeRow.toString() != null) {
				bean.setNomineeRow(nomineeRow.toString());
			} else {
				bean.setNomineeRow("");
			}

		}
		HttpSession httpsession = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) httpsession.getAttribute("user");
		
		String userName = logInfo.getUserName();
		String computerName = getIPAddress(request);
		bean.setUserName(userName);
		bean.setComputerName(computerName);

		int count = 0;

		try {
			count = personalService.updateForm10DInfo(bean,
					addtionalPensionInfo);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
			// TODO Auto-generated catch block

		}
		String cpfacno = bean.getCpfAcNo();
		log.info("form10dUpdate::PensionNo" + bean.getEmpSerialNo()
				+ "flag==" + flag + "name=" + empName + "count" + count);
/*		"./psearch?method=form10Dpersonalnav&strtindx="
				+ startIndex
				+ "&total"
				+ totalData
				+ "&page=form10D";*/

		request.setAttribute("successinfo","Success");
		request.setAttribute("successMessage","Updated successfully.");
		request.setAttribute("type","success");
		request.setAttribute("sucessPath","jsp/common/cleftbody.jsp");
		return mapping.findForward("successform10D");
	}
	public ActionForward loadForm10D(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction::loadForm10D");
		loadPrimarilyInfo(request, "loadForm10D");
		return mapping.findForward("loadform10DSearch");
		
	}
	public ActionForward form10DsearchPersonal(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String airportCode = "", employeeName = "", desegnation = "";
		ArrayList regionList = new ArrayList();
		String rgnName = "", sortingColumn = "", page = "";
		HashMap map = new HashMap();
		String[] regionLst = null;
		EmployeePersonalInfo empBean = new EmployeePersonalInfo();
		if (request.getParameter("airPortCode") != null) {
			empBean.setAirportCode(request.getParameter("airPortCode")
					.toString().trim());
			if (empBean.getAirportCode().equals("NO-SELECT")) {
				empBean.setAirportCode("");
			}
		}
		if (request.getParameter("empName") != null) {
			empBean.setEmployeeName(request.getParameter("empName")
					.toString().trim());
		}
		if (request.getParameter("frm_sortingColumn") != null) {
			sortingColumn = request.getParameter("frm_sortingColumn");
		}
		if (request.getParameter("pensionNO") != null) {
			empBean.setPensionNo(commonUtil.getSearchPFID(request
					.getParameter("pensionNO").toString().trim()));
		} else {
			empBean.setPensionNo("");
		}
		if (request.getParameter("page") != null) {
			page = request.getParameter("page").toString();
		}

		String dateOfBirth = "";
		if (request.getParameter("frm_dateOfBirth") != null
				|| request.getParameter("frm_dateOfBirth") != "") {
			empBean.setDateOfBirth(request.getParameter("frm_dateOfBirth")
					.toString().trim());
		} else {
			empBean.setDateOfBirth("");
		}
		log.info("Date Of Birth====" + empBean.getDateOfBirth());
		if (request.getParameter("region") != null) {
			empBean.setRegion(request.getParameter("region").toString()
					.trim());
			if (empBean.getRegion().equals("NO-SELECT")) {
				empBean.setRegion("");
			}
		} else {
			empBean.setRegion("");
		}
		if (request.getParameter("doj") != null) {
			empBean.setDateOfJoining(request.getParameter("doj").toString()
					.trim());
		} else {
			empBean.setDateOfJoining("");
		}
		if (request.getParameter("cpfaccno") != null) {
			empBean.setCpfAccno(request.getParameter("cpfaccno").toString()
					.trim());
		} else {
			empBean.setCpfAccno("");
		}
		if (request.getParameter("employeeNo") != null) {
			empBean.setEmployeeNumber(request.getParameter("employeeNo")
					.toString().trim());
		} else {
			empBean.setEmployeeNumber("");
		}

		// code added for empname blanks search
		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		EmployeePersonalInfo personalBean=new EmployeePersonalInfo();
		ArrayList dataList=new ArrayList();
		boolean flag = true;
		try {

			searchBean = personalService.searchPersonalMaster(empBean,
					getSearchInfo, flag, 10, sortingColumn, page);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		loadPrimarilyInfo(request,"form10DSearch");
		request.setAttribute("searchBean", searchBean);
		searchBean = (SearchInfo) request.getAttribute("searchBean");
		dataList = searchBean.getSearchList();
		request.setAttribute("SearchInfoList", dataList);
		request.setAttribute("searchInfo", empBean);
		return mapping.findForward("loadform10DSearch");
	}
	public ActionForward frmForm10DReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();
		ArrayList form10DList = new ArrayList();
		if (request.getParameter("frm_dateOfBirth") != null) {
			personalInfo.setDateOfBirth(request
					.getParameter("frm_dateOfBirth"));
		} else {
			personalInfo.setDateOfBirth("---");
		}
		if (request.getParameter("cpfacno") != null) {
			personalInfo.setCpfAccno(request.getParameter("cpfacno"));
		} else {
			personalInfo.setCpfAccno("");
		}
		if (request.getParameter("name") != null) {
			personalInfo.setEmployeeName(request.getParameter("name"));
		} else {
			personalInfo.setEmployeeName("");
		}
		if (request.getParameter("empCode") != null) {
			personalInfo.setEmployeeNumber(request.getParameter("empCode"));
		} else {
			personalInfo.setEmployeeNumber("");
		}
		if (request.getParameter("region") != null) {
			personalInfo.setRegion(request.getParameter("region"));
		} else {
			personalInfo.setRegion("");
		}
		if (request.getParameter("frm_PensionNo") != null) {
			personalInfo
			.setPensionNo(request.getParameter("frm_PensionNo"));
		} else {
			personalInfo.setPensionNo("");
		}

		form10DList = personalService.form10DInfo(personalInfo
				.getPensionNo());
		request.setAttribute("form10DList", form10DList);
		return mapping.findForward("form10DReport");
	}
	

	public ActionForward loadPerMstr(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		loadPrimarilyInfo(request,"loadPersonalSearch");
		return mapping.findForward("loadPersonalSearch");
	}
	public ActionForward searchPersonal(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	
		ArrayList dataList=new ArrayList();
		
		String  sortingColumn = "", page = "";
		
		EmployeePersonalInfo empBean = new EmployeePersonalInfo();
		if (request.getParameter("airPortCode") != null) {
			empBean.setAirportCode(request.getParameter("airPortCode")
					.toString().trim());
			if (empBean.getAirportCode().equals("NO-SELECT")) {
				empBean.setAirportCode("");
			}
		}
		if (request.getParameter("empName") != null) {
			empBean.setEmployeeName(request.getParameter("empName")
					.toString().trim());
		}
		if (request.getParameter("frm_sortingColumn") != null) {
			sortingColumn = request.getParameter("frm_sortingColumn");
		}
		if (request.getParameter("pensionNO") != null) {
			empBean.setPensionNo(commonUtil.getSearchPFID(request
					.getParameter("pensionNO").toString().trim()));
		} else {
			empBean.setPensionNo("");
		}
		if (request.getParameter("page") != null) {
			page = request.getParameter("page").toString();
		}

		String dateOfBirth = "";
		if (request.getParameter("frm_dateOfBirth") != null
				|| request.getParameter("frm_dateOfBirth") != "") {
			empBean.setDateOfBirth(request.getParameter("frm_dateOfBirth")
					.toString().trim());
		} else {
			empBean.setDateOfBirth("");
		}
		log.info("Date Of Birth====" + empBean.getDateOfBirth());
		if (request.getParameter("region") != null) {
			empBean.setRegion(request.getParameter("region").toString()
					.trim());
			if (empBean.getRegion().equals("NO-SELECT")) {
				empBean.setRegion("");
			}
		} else {
			empBean.setRegion("");
		}
		if (request.getParameter("doj") != null) {
			empBean.setDateOfJoining(request.getParameter("doj").toString()
					.trim());
		} else {
			empBean.setDateOfJoining("");
		}
		if (request.getParameter("cpfaccno") != null) {
			empBean.setCpfAccno(request.getParameter("cpfaccno").toString()
					.trim());
		} else {
			empBean.setCpfAccno("");
		}
		if (request.getParameter("employeeNo") != null) {
			empBean.setEmployeeNumber(request.getParameter("employeeNo")
					.toString().trim());
		} else {
			empBean.setEmployeeNumber("");
		}

		// code added for empname blanks search
		SearchInfo getSearchInfo = new SearchInfo();
		SearchInfo searchBean = new SearchInfo();
		boolean flag = true;
		try {

			searchBean = personalService.searchPersonalMaster(empBean,
					getSearchInfo, flag, 10, sortingColumn, page);
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		loadPrimarilyInfo(request,"");
		
		request.setAttribute("searchBean", searchBean);
		searchBean = (SearchInfo) request.getAttribute("searchBean");
		dataList = searchBean.getSearchList();
		request.setAttribute("SearchInfoList", dataList);
		request.setAttribute("searchInfo", empBean);
		String returnPath="";
		if (page != "") {
			returnPath="editPFCard";
		} else {
			returnPath="personalMaster";
		}
		return mapping.findForward(returnPath);
	}
	public ActionForward personalEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String empName = "", empCode = "", region = "";
		String editFrom = "";
		String empSerailNumber1 = "", dateofbirth1 = "", dob1 = "", view = "";
		String employeeSearchName = "";
		String arrearInfo = "", cpfacno = "";
		String pfid = "";
		
		if (request.getParameter("pfid") != null) {
			pfid = request.getParameter("pfid");
		}
		if (request.getParameter("cpfacno") != null) {
			cpfacno = request.getParameter("cpfacno");
		}
	
		String startIndex = "", totalData = "";
		startIndex = request.getParameter("startIndex");
		totalData = request.getParameter("totalData");
		
		if (request.getParameter("view") != null) {
			view = request.getParameter("view");
		}
		if (request.getParameter("arrearInfo") != null) {
			arrearInfo = request.getParameter("arrearInfo").toString();
		} else {
			arrearInfo = "";
		}
		if (request.getParameter("empSerailNumber") != null) {
			empSerailNumber1 = request.getParameter("empSerailNumber");
		//	session.setAttribute("empSerailNumber1", empSerailNumber1);
		}
		if (request.getParameter("dateofbirth") != null) {
			dateofbirth1 = request.getParameter("dateofbirth");
			//session.setAttribute("dateofbirth1", dateofbirth1);
		}
		if (request.getParameter("empName") != null) {
			employeeSearchName = request.getParameter("empName");
			//session.setAttribute("employeeSearchName", employeeSearchName);
		}
		if (request.getParameter("dob1") != null) {
			dob1 = request.getParameter("dob1");
			//session.setAttribute("dob1", dob1);
		}
	
		if (request.getParameter("name") != null) {
			empName = request.getParameter("name");
		}
		if (request.getParameter("empCode") != null) {
			empCode = request.getParameter("empCode");
		}
		if (request.getParameter("region") != null) {
			region = request.getParameter("region").trim();
		}
		if (request.getParameter("editFrom") != null) {
			editFrom = request.getParameter("editFrom");
		}

		boolean flag;
		if (request.getParameter("flag") != null
				&& request.getParameter("flag").equals("invflag")) {
			flag = false;

		} else {
			flag = true;
		}
		if (request.getParameter("recordExist") != null) {
			request.setAttribute("recordExist", request.getParameter(
			"recordExist").toString());
		}
		 
		ArrayList airportList = new ArrayList();
		ArrayList departmentList = new ArrayList();
		EmpMasterBean editBean = new EmpMasterBean();
		try {
			editBean = personalService.empPersonalEdit(cpfacno, empName,
					flag, empCode, region.trim(), pfid);

			airportList = commonUtil.getAirports(region);
			departmentList = ps.getDepartmentList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		request.setAttribute("EditBean", editBean);
		request.setAttribute("AirportList", airportList);
		request.setAttribute("DepartmentList", departmentList);
		request.setAttribute("flag", Boolean.toString(flag));
		request.setAttribute("startIndex", startIndex);
		request.setAttribute("TotalData", totalData);
		request.setAttribute("editFrom", editFrom);
		request.setAttribute("ArrearInfo", arrearInfo);
		loadPrimarilyInfo(request,"personalMasterEdit");
		
		String nomineeInfo =  editBean.getNomineeRow();
		 
		if(nomineeInfo.length()>0){
			log.info("nominee informatio in PersonalFormAction============"+nomineeInfo);
			
			request.setAttribute("nomineeInfo", nomineeInfo.substring(0,nomineeInfo.length()-1));
		}
		String familyInfo =  editBean.getFamilyRow();
		 
		if(familyInfo.length()>0){
			log.info("familyInfo informatio in PersonalFormAction============"+nomineeInfo);
			
			request.setAttribute("familyInfo", familyInfo.substring(0,familyInfo.length()-1));
		}
		 
		String returnPath="";
		if (!view.equals("true")) {
			returnPath="personalMasterEdit";
		} else {
			returnPath="personalMasterView";
		}
		 
		return mapping.findForward(returnPath);
	}
	public ActionForward frmForm2Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();

		if (request.getParameter("frm_dateOfBirth") != null) {
			personalInfo.setDateOfBirth(request
					.getParameter("frm_dateOfBirth"));
		} else {
			personalInfo.setDateOfBirth("---");
		}
		if (request.getParameter("cpfacno") != null) {
			personalInfo.setCpfAccno(request.getParameter("cpfacno"));
		} else {
			personalInfo.setCpfAccno("");
		}
		if (request.getParameter("name") != null) {
			personalInfo.setEmployeeName(request.getParameter("name"));
		} else {
			personalInfo.setEmployeeName("");
		}
		if (request.getParameter("empCode") != null) {
			personalInfo.setEmployeeNumber(request.getParameter("empCode"));
		} else {
			personalInfo.setEmployeeNumber("");
		}
		if (request.getParameter("region") != null) {
			personalInfo.setRegion(request.getParameter("region"));
		} else {
			personalInfo.setRegion("");
		}
		if (request.getParameter("frm_PensionNo") != null) {
			personalInfo
			.setPensionNo(request.getParameter("frm_PensionNo"));
		} else {
			personalInfo.setPensionNo("");
		}
		NomineeForm2Info form2Report = new NomineeForm2Info();
		form2Report = personalService.form2Report(personalInfo);
		request.setAttribute("form2Report", form2Report);
		return mapping.findForward("viewForm2Report");
	}
	public ActionForward frmForm9Report(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeePersonalInfo personalInfo = new EmployeePersonalInfo();

		if (request.getParameter("frm_dateOfBirth") != null) {
			personalInfo.setDateOfBirth(request
					.getParameter("frm_dateOfBirth"));
		} else {
			personalInfo.setDateOfBirth("---");
		}
		if (request.getParameter("cpfacno") != null) {
			personalInfo.setCpfAccno(request.getParameter("cpfacno"));
		} else {
			personalInfo.setCpfAccno("");
		}
		if (request.getParameter("name") != null) {
			personalInfo.setEmployeeName(request.getParameter("name"));
		} else {
			personalInfo.setEmployeeName("");
		}
		if (request.getParameter("empCode") != null) {
			personalInfo.setEmployeeNumber(request.getParameter("empCode"));
		} else {
			personalInfo.setEmployeeNumber("");
		}
		if (request.getParameter("region") != null) {
			personalInfo.setRegion(request.getParameter("region"));
		} else {
			personalInfo.setRegion("");
		}
		if (request.getParameter("frm_PensionNo") != null) {
			personalInfo
			.setPensionNo(request.getParameter("frm_PensionNo"));
		} else {
			personalInfo.setPensionNo("");
		}
		RPFCForm9Bean personaList = new RPFCForm9Bean();
		personaList = personalService.form9Report(personalInfo);
		request.setAttribute("form9Report", personaList);

		return mapping.findForward("viewForm9Report");
	}

	 public ActionForward personalUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
			log.info("PersonalFormAction:: personalUpdate() ");
			EmpMasterBean bean = new EmpMasterBean();
			String flag = "", empName = "";
			String startIndex = "", totalData = "",frmNomineeDets="",frmFamilyDets="",oldRegion="",oldStation="";
			String editFrom = "";
			String arrearInfo = "";
			int nmCount=0;

			//log.info("arrear info"+request.getAttribute("ArrearInfo").toString
			// ());
			if (request.getParameter("ArrearInfo") != null) {
				arrearInfo = request.getParameter("ArrearInfo").toString();
			} else {
				arrearInfo = "";
			}
			if (request.getParameter("setEmpSerialNo") != null) {
				bean.setEmpSerialNo(request.getParameter("setEmpSerialNo")
						.trim().toString());
			}
			if (request.getParameter("select_fhname") != null) {
				bean.setFhFlag(request.getParameter("select_fhname").trim()
						.toString());
			}
			log.info("arrear info" + arrearInfo.toString());
			request.setAttribute("ArrearInfo", arrearInfo);
			startIndex = request.getParameter("startIndex");
			totalData = request.getParameter("totalData");		
			 
			String airportWithRegion = request
			.getParameter("airportWithRegion");
			String region1 = airportWithRegion.substring(airportWithRegion
					.indexOf("-") + 1, airportWithRegion.length());
			bean.setNewRegion(region1);
				
			if (request.getParameter("oldAirportCode") != null) {
				oldStation=request.getParameter("oldAirportCode").trim();
			}
			if (request.getParameter("region") != null) {
				oldRegion=request.getParameter("region")
						.trim().toString();
			}
			 
			// New Patameters added while Changing employee information from one
			// region to other
			if (request.getParameter("select_region") != null) {
				bean.setChangedRegion(request.getParameter("select_region")
						.trim().toString());
			}  
			 
			 
			if (request.getParameter("airPortCode") != null) {
				bean.setChangedStation(request.getParameter("airPortCode")
						.trim().toString());
			}  
			
			if(!bean.getChangedStation().equals("")){
				bean.setStation(bean.getChangedStation());
			}else{
				bean.setStation(oldStation);
			}
			
			
			if(!bean.getChangedRegion().equals("")){
				bean.setRegion(bean.getChangedRegion());
			}else{
				bean.setRegion(oldRegion);
			}
			
			 
			if (request.getParameter("editFrom") != null) {
				editFrom = request.getParameter("editFrom");
			}
			if (request.getParameter("flagData") != null) {
				flag = request.getParameter("flagData");
			} else {
				flag = "true";
			}
			if (request.getParameter("empName") != null) {
				bean.setEmpName(request.getParameter("empName").trim()
						.toString());
			} else {
				bean.setEmpName("");
			}
			if (request.getParameter("empOldName") != null) {
				bean.setEmpOldName(request.getParameter("empOldName").trim()
						.toString());
			} else {
				bean.setEmpOldName("");
			}
			if (request.getParameter("empOldNumber") != null) {
				bean.setEmpOldNumber(request.getParameter("empOldNumber")
						.trim().toString());
			} else {
				bean.setEmpOldNumber("");
			}

			
			if (request.getParameter("desegnation") != null) {
				bean.setDesegnation(request.getParameter("desegnation")
						.toString());
			} else {
				bean.setDesegnation("");
			}
			log.info("old cpfacno " + request.getParameter("cpfacno"));

			if (request.getParameter("cpfacno") != null) {
				bean.setCpfAcNo(request.getParameter("cpfacno").toString());
			} else {
				bean.setCpfAcNo("");
			}
			log.info("new cpfacno " + request.getParameter("cpfacnoNew"));
			if (request.getParameter("cpfacnoNew") != null) {
				bean.setNewCpfAcNo(request.getParameter("cpfacnoNew")
						.toString());
			} else {
				bean.setNewCpfAcNo("");
			}
			
			
			if(! bean.getNewCpfAcNo().trim().equals("")){
				bean.setNewCpfAcNo(bean.getNewCpfAcNo());
			}else{
				bean.setNewCpfAcNo( bean.getCpfAcNo().trim());
			}		 
			
			if (request.getParameter("pfid") != null) {
				bean.setPfid(request.getParameter("pfid").toString());
			} else {
				bean.setPfid("");
			}
			 ;
			
			if (request.getParameter("employeeCode") != null) {
				bean.setEmpNumber(request.getParameter("employeeCode").trim()
						.toString());
			} else {
				bean.setEmpNumber("");
			}
			if (request.getParameter("airportSerialNumber") != null) {
				bean.setAirportSerialNumber(request.getParameter(
				"airportSerialNumber").trim().toString());
			} else {
				bean.setAirportSerialNumber("");
			}
			if (request.getParameter("emplevel") != null) {
				bean.setEmpLevel(request.getParameter("emplevel").toString());
			} else {
				bean.setEmpLevel("");
			}
			if (request.getParameter("dateofbirth") != null) {
				bean.setDateofBirth(request.getParameter("dateofbirth"));
			} else {
				bean.setDateofBirth("");
			}

			if (request.getParameter("dateofjoining") != null) {
				bean.setDateofJoining(request.getParameter("dateofjoining")
						.toString());
			} else {
				bean.setDateofJoining("");
			}
			if (request.getParameter("reason") != null) {
				bean.setSeperationReason(request.getParameter("reason")
						.toString());
			} else {
				bean.setSeperationReason("");
			}
			if (request.getParameter("Other") != null) {
				bean.setOtherReason(request.getParameter("Other").toString());
			} else {
				bean.setOtherReason("");
			}

			if (request.getParameter("seperationDate") != null) {
				bean.setDateofSeperationDate(request.getParameter(
				"seperationDate").toString());
			} else {
				bean.setSeperationReason("");
			}

			if (request.getParameter("emailId") != null) {
				bean.setEmailId(request.getParameter("emailId").toString());
			} else {
				bean.setEmailId("");
			}

			if (request.getParameter("remarks") != null) {
				bean.setRemarks(request.getParameter("remarks").toString());
			} else {
				bean.setRemarks("");
			}
			if (request.getParameter("optionform") != null) {
				bean.setOptionForm(request.getParameter("optionform")
						.toString());
			} else {
				bean.setOptionForm("");
			}

			if (request.getParameter("division") != null) {

				bean.setDivision(request.getParameter("division").toString());
			} else {
				bean.setDivision("");
			}
			if (request.getParameter("department") != null) {
				bean.setDepartment(request.getParameter("department")
						.toString());
			} else {
				bean.setDepartment("");
			}

			if (request.getParameter("paddress") != null) {
				bean.setPermanentAddress(request.getParameter("paddress")
						.toString().trim());
			} else {
				bean.setPermanentAddress("");
			}
			if (request.getParameter("taddress") != null) {
				bean.setTemporatyAddress(request.getParameter("taddress")
						.toString().trim());
			} else {
				bean.setTemporatyAddress("");
			}
			if (request.getParameter("wetherOption") != null) {
				bean.setWetherOption(request.getParameter("wetherOption")
						.toString().trim());
			} else {
				bean.setWetherOption("");
			}

			if (request.getParameter("sex") != null) {
				bean.setSex(request.getParameter("sex").toString().trim());
			} else {
				bean.setSex("");
			}
			if (request.getParameter("mstatus") != null) {
				bean.setMaritalStatus(request.getParameter("mstatus")
						.toString().trim());
			} else {
				bean.setMaritalStatus("");
			}
			if (request.getParameter("fhname") != null) {
				bean
				.setFhName(request.getParameter("fhname").toString()
						.trim());
			} else {
				bean.setFhName("");
			}
			if (request.getParameter("dateOfAnnuation") != null) {
				bean.setDateOfAnnuation(request.getParameter("dateOfAnnuation")
						.toString().trim());
			} else {
				bean.setDateOfAnnuation("");
			}
			if (request.getParameter("pensionNumber") != null) {
				bean.setPensionNumber(request.getParameter("pensionNumber")
						.toString().trim());
				log.info("pension number is " + bean.getPensionNumber());
			} else {
				bean.setPensionNumber("");
			}

			  
			
			if (request.getParameter("frm_family_Details") != null) {
				frmFamilyDets = request.getParameter("frm_family_Details");
			}
			 
			if (!frmFamilyDets.equals("")) {
				bean.setFamilyRow(frmFamilyDets);
			} else {
				bean.setFamilyRow("");
			}
			 
			bean.setPensionNumber(bean.getEmpSerialNo());
			if (request.getParameter("frm_nominees") != null) {
				frmNomineeDets = request.getParameter("frm_nominees");
			}
			
			if (!frmNomineeDets.equals("")) {
				bean.setNomineeRow(frmNomineeDets);
			} else {
				bean.setNomineeRow("");
			}
			 
			if (request.getParameter("equalshare") != null) {
				bean.setEqualShare(request.getParameter("equalshare"));
			 
			}
			
			if (request.getParameter("form1") != null) {
				bean.setForm2Nomination(request.getParameter("form1").toString().trim());
			 
			}else{
				bean.setForm2Nomination("");
			}
			 
			
			HttpSession session = request.getSession(true);
			LoginInfo logInfo = new LoginInfo();
			logInfo = (LoginInfo) session.getAttribute("user");
			
			String userName = logInfo.getUserName();
			String computerName = getIPAddress(request);
			bean.setUserName(userName);
			bean.setComputerName(computerName);

			/* endof Nominee Details */

			int count = 0;
			String message = "";
			String frmPensionno="",frmCpfacno="";
			frmPensionno=bean.getEmpSerialNo();
			
			frmCpfacno = bean.getCpfAcNo();
			try {
				count = personalService.updatePensionMaster(bean, flag);
			} catch (InvalidDataException e) {
				log.printStackTrace(e);
				// TODO Auto-generated catch block
				message = e.getMessage();

			}
		
			
			ForwardParameters fwdParams = new ForwardParameters();
			log.info("=======cpfacno" + frmCpfacno + "flag==" + flag + "name="
					+ empName + "count" + count);
			if (count == 0) {
				ActionForward forward = mapping.findForward("forFailCase");
				if (flag.equals("false")) {
					flag = "invflag";
				}
				Map params = new HashMap();

				
				params.put("cpfacno", frmCpfacno);
				params.put("pfid", frmPensionno);
				fwdParams.add(params);
				return fwdParams.forward(forward);

			}  else {
				ActionForward forward = mapping.findForward("gotopersonalsearch");
				Map params = new HashMap();
				params.put("pensionNO", frmPensionno);
				params.put("frm_dateOfBirth", "");
				params.put("region", "");
				params.put("doj", "");
				params.put("cpfaccno", "");
				params.put("employeeNo", "");
				params.put("empName", "");
				params.put("airPortCode", "");
				params.put("frm_sortingColumn", "PENSIONNO");
				
				fwdParams.add(params);			 
				return fwdParams.forward(forward);
			
			}

			
		
		}
	public ActionForward financeDataMappingSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		loadPrimarilyInfo(request,"gotodatamapping");
		return mapping.findForward("gotodatamapping");
	}
	public ActionForward financeDataSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList financeDatalist = new ArrayList();
		String empNameCheck = "", unmappedFlag = "";
		EmpMasterBean empSerach = new EmpMasterBean();

		if (request.getParameter("pfid") != null) {
			empSerach.setPfid(commonUtil.getSearchPFID1(request
					.getParameter("pfid").toString().trim()));
		}
		if (request.getParameter("employeeName") != null) {
			empSerach.setEmpName(request.getParameter("employeeName")
					.toString().trim());
		}
		log.info("empname " + empSerach.getEmpName());
		if (request.getParameter("region") != null) {
			empSerach.setRegion(request.getParameter("region").toString()
					.trim());
		}
		if (request.getParameter("cpfaccno") != null) {
			empSerach.setCpfAcNo(request.getParameter("cpfaccno")
					.toString().trim());
		}
		if (request.getParameter("employeeCode") != null) {
			empSerach.setEmpNumber(request.getParameter("employeeCode")
					.toString().trim());
		}
		if (request.getParameter("empNameCheak") != null) {
			empNameCheck = request.getParameter("empNameCheak").toString()
			.trim();
			empSerach.setEmpNameCheak(empNameCheck);
		}
		if (request.getParameter("unmappedFlag") != null) {
			empSerach.setUnmappedFlag(request.getParameter("unmappedFlag")
					.toString().trim());
			request.setAttribute("unmappedFlag", empSerach
					.getUnmappedFlag());
		}
		if (request.getParameter("allRecordsFlag") != null) {
			empSerach.setAllRecordsFlag(request.getParameter("allRecordsFlag")
					.toString().trim());
		}
		log.info(" flag is " + empSerach.getAllRecordsFlag());
		if (request.getParameter("reportType") != null) {
			empSerach.setReportType(request.getParameter("reportType")
					.toString().trim());
			request.setAttribute("reportType", empSerach.getReportType());
		}
		if (request.getParameter("select_airport") != null) {
			empSerach.setStation(request.getParameter("select_airport")
					.toString().trim());
		}
		if(request.getParameter("pfidfrom") != null){
			empSerach.setPfidfrom(request.getParameter("pfidfrom"));
		}else{
			empSerach.setPfidfrom("");	
		}
		 if (request.getParameter("pcverified") != null) {
				empSerach.setPcverified(request.getParameter("pcverified")
						.toString().trim());
		}

		SearchInfo getSearchInfo = new SearchInfo();
		getSearchInfo = fs.financeDataSearch(empSerach, getSearchInfo, 100,empNameCheck, empSerach.getReportType(),empSerach.getPfidfrom());
		request.setAttribute("searchBean", getSearchInfo);
		request.setAttribute("empSerach", empSerach);
		request.setAttribute("financeDatalist", getSearchInfo
				.getSearchList());
		request.setAttribute("empNameChecked", empNameCheck);
		String returnPath="";
		if (request.getParameter("reportType") != null
				&& request.getParameter("reportType") != "") {
			returnPath="financeDataMappingReport";
		}else if (request.getParameter("allRecordsFlag").equals("true") ) {
			returnPath="verificationPCReport";
		}else{
			returnPath="gotodatamapping";
		}
		return mapping.findForward(returnPath);
	}
	public ActionForward mappingUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String pfid = "", cpfaccno = "", region = "",mappingRec="";
		log.info("Mapped items  "
				+ request.getParameterValues("mappingUpdate"));

		FinancialReportService finReportService = new FinancialReportService();
		HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) session.getAttribute("user");
		if(request.getParameter("frm_mappedRec")!=null){
			mappingRec=request.getParameter("frm_mappedRec");
		}
		String userName = logInfo.getUserName();
		String ipAddress = getIPAddress(request);
		String mappingAtList[]=mappingRec.split("@");
		String getMappedAtList="";
		for(int i=0;i<mappingAtList.length;i++){
			getMappedAtList=mappingAtList[i];
			String mappingSepList[]=getMappedAtList.split(",");
			pfid=mappingSepList[6];
			cpfaccno = mappingSepList[1];
			region = mappingSepList[4];
			fs.updateMapping(pfid, cpfaccno, region, userName,ipAddress);
			finReportService.preProcessAdjOB(pfid);
		}
		return mapping.findForward("financeDataMapping");
	}
	public ActionForward updateMappingFlag(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String pfid = "", cpfaccno = "", region = "",mappingRec="";
		log.info("Mapped items  "
				+ request.getParameterValues("mappingUpdate"));

		FinancialReportService finReportService = new FinancialReportService();
		HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) session.getAttribute("user");
		if(request.getParameter("frm_mappedRec")!=null){
			mappingRec=request.getParameter("frm_mappedRec");
		}
		String userName = logInfo.getUserName();
		String ipAddress = getIPAddress(request);
		String mappingAtList[]=mappingRec.split("@");
		String getMappedAtList="";
		for(int i=0;i<mappingAtList.length;i++){
			getMappedAtList=mappingAtList[i];
			String mappingSepList[]=getMappedAtList.split(",");
			pfid=mappingSepList[6];
			cpfaccno = mappingSepList[1];
			region = mappingSepList[4];
			fs.updateMappingFlag(pfid, cpfaccno, region,userName,ipAddress);

			log.info("PersonalForm Action:::updateMappingFlag====pfid================"
					+ pfid);
			finReportService.preProcessAdjOB(pfid);
		}
		

		return mapping.findForward("financeDataMapping");
	}	

	

	public ActionForward chkEmpPersonal(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList checkList = new ArrayList();
		EmployeePersonalInfo personal = new EmployeePersonalInfo();
		if (request.getParameter("frmEmpName") != null) {
			personal.setEmployeeName(request.getParameter("frmEmpName")
					.toString());
		} else {
			personal.setEmployeeName("");
		}
		try {
			if (request.getParameter("frmDob") != null) {

				personal.setDateOfBirth(commonUtil.converDBToAppFormat(
						request.getParameter("frmDob").toString(),
						"dd/MM/yyyy", "dd-MMM-yyyy"));
			} else {
				personal.setDateOfBirth("");
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("Employeename" + request.getParameter("frmEmpName")
				+ "DOB" + request.getParameter("frmDob"));
		boolean empflag = false, dobflag = false;
		String reqMessage = "";
		if (request.getParameter("empflag") != null) {
			if (request.getParameter("empflag").equals("true")) {
				empflag = true;
				reqMessage = "Employees list with same Employee Name";
			}
		}
		if (request.getParameter("dobflag") != null) {
			if (request.getParameter("dobflag").equals("true")) {
				dobflag = true;
				reqMessage = "Employees list with same Date of Birth";
			}
		}
		request.setAttribute("title", reqMessage);

		checkList = personalService.checkPersonalInfo(personal, empflag,
				dobflag);
		request.setAttribute("personalChkList", checkList);
	
	
		return mapping.findForward("chkPersonalSearch");
	}
	public ActionForward addToPersonal(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmployeePersonalInfo personal = new EmployeePersonalInfo();
		
		String frmNomineeDets="",frmFamilyDets="";
		EmpMasterBean bean=new EmpMasterBean();
		int nmCount=0;
		if (request.getParameter("select_fhname") != null) {
			personal.setFhFlag(request.getParameter("select_fhname")
					.toString().trim());
		} else {
			personal.setFhFlag("");
		}

		if (request.getParameter("empName") != null) {
			personal.setEmployeeName(request.getParameter("empName")
					.toString());
		} else {
			personal.setEmployeeName("");
		}

		if (request.getParameter("select_airport") != null) {
			personal.setAirportCode(request.getParameter("select_airport")
					.toString());
		} else {
			personal.setAirportCode("");
		}
		if (request.getParameter("desegnation") != null) {
			personal.setDesignation(request.getParameter("desegnation")
					.toString());
		} else {
			personal.setDesignation("");
		}
		if (request.getParameter("cpfacno") != null) {
			personal
			.setCpfAccno(request.getParameter("cpfacno").toString());
		} else {
			personal.setCpfAccno("");
		}
		if (request.getParameter("employeeCode") != null) {
			personal.setEmployeeNumber(request.getParameter("employeeCode")
					.toString());
		} else {
			personal.setEmployeeNumber("");
		}

		if (request.getParameter("airportSerialNumber") != null) {
			personal.setAirportSerialNumber(request.getParameter(
			"airportSerialNumber").toString());
		} else {
			personal.setAirportSerialNumber("");
		}
		if (request.getParameter("emplevel") != null) {
			log.info("Personal Desgination Level"
					+ request.getParameter("emplevel").toString());
			String empDesgLevel[] = request.getParameter("emplevel").split(
			" - ");
			log.info("Emp Level" + empDesgLevel[0] + "Desgnation"
					+ empDesgLevel[1]);
			personal.setEmpDesLevel(empDesgLevel[0]);
			personal.setDesignation(empDesgLevel[1]);
		} else {
			personal.setEmpDesLevel("");
		}

		 
			if (request.getParameter("dateofbirth") != null) {
				personal.setDateOfBirth(request.getParameter("dateofbirth"));
			}
			if (request.getParameter("dateofjoining") != null) {
				personal.setDateOfJoining(request.getParameter("dateofjoining"));
			} else {
				personal.setDateOfJoining("");
			}
			if (request.getParameter("dateOfAnnuation") != null) {
				personal.setDateOfAnnuation(request.getParameter("dateOfAnnuation"));

			} else {
				personal.setDateOfAnnuation("");
			}
		 
			 
		if (request.getParameter("reason") != null) {
			personal.setSeperationReason(request.getParameter("reason")
					.toString());
		} else {
			personal.setSeperationReason("");
		}

		if (request.getParameter("seperationDate") != null) {
			 
				personal.setSeperationDate(request.getParameter("seperationDate"));
			 
		} else {
			personal.setSeperationDate("");
		}

		if (request.getParameter("wetherOption") != null) {
			personal.setWetherOption(request.getParameter(
					"wetherOption".trim()).toString());
		} else {
			personal.setWetherOption("");
		}
		if (request.getParameter("form1") != null) {
			personal.setForm2Nominee(request.getParameter("form1")
					.toString().trim());
		} else {
			personal.setForm2Nominee("");
		}

		if (request.getParameter("remarks") != null) {
			personal.setRemarks(request.getParameter("remarks").toString()
					.trim());
		} else {
			personal.setRemarks("");
		}

		if (request.getParameter("paddress") != null) {
			personal.setPerAddress(request.getParameter("paddress")
					.toString().trim());
		} else {
			personal.setPerAddress("");
		}
		if (request.getParameter("taddress") != null) {
			personal.setTempAddress(request.getParameter("taddress")
					.toString().trim());
		} else {
			personal.setTempAddress("");
		}
		if (request.getParameter("sex") != null) {
			personal.setGender(request.getParameter("sex").toString()
					.trim());
		} else {
			personal.setGender("");
		}
		if (request.getParameter("mstatus") != null) {
			personal.setMaritalStatus(request.getParameter("mstatus")
					.toString().trim());
		} else {
			personal.setMaritalStatus("");
		}
		if (request.getParameter("fhname") != null) {
			personal.setFhName(request.getParameter("fhname").toString()
					.trim());
		} else {
			personal.setFhName("");
		}
		if (request.getParameter("Other") != null) {
			personal.setOtherReason(request.getParameter("Other")
					.toString().trim());
		} else {
			personal.setOtherReason("");
		}
		if (request.getParameter("division") != null) {
			personal.setDivision(request.getParameter("division")
					.toString().trim());
		} else {
			personal.setDivision("");
		}
		if (request.getParameter("department") != null) {
			personal.setDepartment(request.getParameter("department")
					.toString().trim());
			if (personal.getDepartment().equals("NO-SELECT")) {
				personal.setDepartment("");
			}
		} else {
			personal.setDepartment("");
		}
		if (request.getParameter("emailId") != null) {
			personal.setEmailID(request.getParameter("emailId").toString()
					.trim());
		} else {
			personal.setEmailID("");
		}
		if (request.getParameter("region") != null) {
			personal.setRegion(request.getParameter("region").toString()
					.trim());
		} else {
			personal.setRegion("");
		}
		if (request.getParameter("equalShare") != null) {
			personal.setEmpNomineeSharable(request.getParameter(
			"equalShare").toString().trim());
		} else {
			personal.setEmpNomineeSharable("");
		}

		 
		if (request.getParameter("frm_nominees") != null) {
			frmNomineeDets = request.getParameter("frm_nominees");
		}
		bean.setPensionNumber(bean.getEmpSerialNo());
		 
		
		if (!frmNomineeDets.equals("")) {
			bean.setNomineeRow(frmNomineeDets);
		} else {
			bean.setNomineeRow("");
		}
		 
		if (request.getParameter("equalshare") != null) {
			bean.setEqualShare(request.getParameter("equalshare"));
		 
		}

		if (request.getParameter("frm_family_Details") != null) {
			frmFamilyDets = request.getParameter("frm_family_Details");
		}
				
		if (!frmFamilyDets.equals("")) {
			bean.setFamilyRow(frmFamilyDets);
		} else {
			bean.setFamilyRow("");
		}
		
		 

		HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) session.getAttribute("user");
		
		String userName = logInfo.getUserName();
		String computerName = getIPAddress(request);
		String uniqueID = "";
		try {
			uniqueID = personalService.addPersonalInfo(personal,bean,userName, computerName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "";
		if (!uniqueID.equals("")) {
			message = "Employee PF ID Credentials " + uniqueID
			+ " added Sucessfully";
		} else {
			message = "Fail! To add Employee PF ID Credentials";
		}

		request.setAttribute("mesg", message);
		
		return mapping.findForward("loadAddPersonalAdd");
		
	}
	public ActionForward loadAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String maxCpfacno = "", recordExist = "";
		ArrayList airportList = new ArrayList();
		ArrayList departmentList = new ArrayList();
		ArrayList desginationList = new ArrayList();

		ArrayList regionList = new ArrayList();
		String rgnName = "";
		HashMap map = new HashMap();
		String[] regionLst = null;
		// ArrayList airportList1=null;
		loadPrimarilyInfo(request,"rpfcpersonalAdd");
		String message = "";
		if (request.getAttribute("mesg") != null) {
			message = (String) request.getAttribute("mesg");
			request.setAttribute("mesg", message);
		}
		log.info("Regions Name" + map.size());
		departmentList = personalService.getDepartmentList();
		desginationList = personalService.getDesginationList();

		log.info("departmentList " + departmentList.size());
		request.setAttribute("desginationList", desginationList);
		request.setAttribute("DepartmentList", departmentList);
		request.setAttribute("regionMap", map);
		
		return mapping.findForward("rpfcpersonalAdd");
	
	}
	public ActionForward loadVerificationReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		loadPrimarilyInfo(request,"verificationPCReport");
		return mapping.findForward("verificationPCReport");
		
	}	
	public ActionForward nomineeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ArrayList nomineeRequest=null;
		String psfNo=StringUtility.checknull(request.getParameter("pensionNo"));
		String reqDate=StringUtility.checknull(request.getParameter("requestDate"));
		log.info("nomineeSearch::the psfno is"+psfNo+"reqDate"+reqDate);

		try{
			nomineeRequest=personalService.nomineeRequestResult(psfNo,reqDate);
		}catch (Exception e) {
			log.printStackTrace(e);
		}
		request.setAttribute("nomineeRequest",nomineeRequest);
		return mapping.findForward("loadNomineeRequest");
	}
	public ActionForward pensionEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String flag=request.getParameter("flag");
		log.info("PersonalFormAction:::pensionEdit"+flag);
		String pfid = "";
		HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		
		if(request.getParameter("pfid")!=null){
			pfid = request.getParameter("pfid").toString();
		}else{
			logInfo = (LoginInfo) session.getAttribute("user");
			pfid=logInfo.getPensionNo();
			
		}
		log.info("PersonalFormAction:::pensionEdit pfid "+pfid);
		EmpMasterBean editBean = new EmpMasterBean();
		try {
			editBean = personalService.empPensionEdit(pfid,flag);

			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String nomineeRow=editBean.getNomineeRow();
		
        ArrayList nomineeList = commonUtil.getTheList(nomineeRow,
                "***");
        int size=nomineeList.size();
        log.info("PersonalFormAction:::the size is"+size);
        request.setAttribute("size",new Integer(size));
		request.setAttribute("EditBean",editBean);
		String displayPage="";
		if(flag.equals("true"))
			displayPage="loadregionalEdit";
		else
			displayPage="loadmemberEdit";
		
		return mapping.findForward(displayPage);
	}
	public ActionForward nomineerequestUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		EmpMasterBean bean = new EmpMasterBean();
		if (request.getParameterValues("nomineeAccept") != null)
		{
			String nomineeAccept[]=request.getParameterValues("nomineeAccept");
			String serialNo[]=request.getParameterValues("srno");
			String empsrno[]=request.getParameterValues("empsrno");
			String accept="",Sno="",pensionNo="";
			StringBuffer nomineeRequestRow=new StringBuffer();
			for(int i=0; i<nomineeAccept.length;i++)
			{
				if (nomineeAccept[i].equals("")) {
					accept = "XXX";
				} else {
					accept = nomineeAccept[i].toString();
				}
				if (serialNo[i].equals("")) {
					Sno = "XXX";
				} else {
					Sno = serialNo[i].toString();
				}
				if (empsrno[i].equals("")) {
					pensionNo = "XXX";
				} else {
					pensionNo = empsrno[i].toString();
				}
				
				nomineeRequestRow.append(accept + "@");
				nomineeRequestRow.append(Sno + "@");
				nomineeRequestRow.append(pensionNo+"@");
				nomineeRequestRow.append("***");
			}
			if (nomineeRequestRow.toString() != null) {
				bean.setNomineeRow(nomineeRequestRow.toString());
			} else {
				bean.setNomineeRow("");
			}
		}
		int count=0;
		try{
			count=personalService.nomineeRequestUpdate(bean);
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
		}
		return nomineeApprovalStatus(mapping,form,request,response);
	}
	public ActionForward pensionUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::pensionUpdate");
		EmpMasterBean bean = new EmpMasterBean();
		bean.setEmpSerialNo(request.getParameter("empSerialNo"));
		bean.setNewCpfAcNo(request.getParameter("cpfNo"));
		log.info("thessfoodod"+bean.getNewCpfAcNo());
		bean.setRegion(request.getParameter("region"));
		

		if (request.getParameter("equalShare") != null) {
			bean.setEmpNomineeSharable(request.getParameter("equalShare")
					.toString().trim());
		} else
			bean.setEmpNomineeSharable("");

		

		
		/* for Nominee Details */
		if (request.getParameterValues("Nname") != null) {
			String Nname[] = request.getParameterValues("Nname");
			String empOldNname[] = request
					.getParameterValues("empOldNname");
			String Naddress[] = request.getParameterValues("Naddress");
			String Ndob[] = request.getParameterValues("Ndob");
			String Nrelation[] = request.getParameterValues("Nrelation");
			String guardianname[] = request
					.getParameterValues("guardianname");
			String gaddress[] = request.getParameterValues("gaddress");
			String totalshare[] = request.getParameterValues("totalshare");
			String serialNo[]=request.getParameterValues("srno");
			String pensionNo[]=request.getParameterValues("empsrno");
			String editStatus[]=request.getParameterValues("editStatus");
			log.info("tPersonalFormAction:::pensionUpdate"+Nname.length+"nrelationlength"+Nrelation.length);
			StringBuffer nomineeRow = new StringBuffer();
			
			
			String nAddress = "", nDob = "", nRelation = "", nGuardianname = "", nTotalshare = "", gardaddressofNaminee = "";
			String empOldNomineeName = "",nserialNo="",npensionNo="",neditStatus="";
			
			for (int i = 0; i < Nname.length; i++) {
				if (!Nname[i].equals("")) {
					log.info("the serial no is"+serialNo[i].toString());
					log.info("the totalshare of "+totalshare[i].toString());
					nomineeRow.append(Nname[i].toString() + "@");
					if (Naddress[i].equals("")) {
						nAddress = "XXX";
					} else {
						nAddress = Naddress[i].toString();
					}
					if (Nrelation[i].equals("")) {
						nRelation = "XXX";
					} else {
						nRelation = Nrelation[i].toString();
					}

					if (Ndob[i].equals("")) {
						nDob = "XXX";
					} else {
						nDob = Ndob[i].toString();
					}
					if (guardianname[i].equals("")) {
						nGuardianname = "XXX";
					} else {
						nGuardianname = guardianname[i].toString();
					}
					if (gaddress[i].equals("")) {
						gardaddressofNaminee = "XXX";
					} else {
						gardaddressofNaminee = gaddress[i].toString()
								.trim();
					}

					if (empOldNname[i].equals("")) {
						empOldNomineeName = "XXX";
					} else {
						empOldNomineeName = empOldNname[i].toString()
								.trim();
					}
					
					if(serialNo[i].equals(""))
					{
						nserialNo="XXX";
					}
					else
					{
						nserialNo=serialNo[i].toString();
					}
					if(pensionNo[i].equals(""))
					{
						npensionNo="XXX";
					}
					else
					{
						npensionNo=pensionNo[i].toString();
					}
					if(editStatus[i].equals(""))
					{
						neditStatus="XXX";
					}
					else
					{
						neditStatus=editStatus[i].toString();
					}
					if (totalshare[i].equals("")) {
						nTotalshare = "XXX";
					} else {
						nTotalshare = totalshare[i].toString();

					     
					}
					log.info("the serial no is"+nserialNo);
					nomineeRow.append(nAddress + "@");
					nomineeRow.append(nDob + "@");
					nomineeRow.append(nRelation + "@");
					nomineeRow.append(nGuardianname + "@");
					nomineeRow.append(gardaddressofNaminee + "@");
					nomineeRow.append(empOldNomineeName + "@");
					nomineeRow.append(nserialNo + "@");
					nomineeRow.append(npensionNo + "@");
					nomineeRow.append(neditStatus + "@");
					nomineeRow.append(nTotalshare);
					nomineeRow.append("***");
					log.info("this calling... upto naming...");
				}
				

			}

			log.info("Nominee data final... " + nomineeRow.toString());

			if (nomineeRow.toString() != null) {
				bean.setNomineeRow(nomineeRow.toString());
			} else {
				bean.setNomineeRow("");
			}

		}
		int count = 0;
		String message = "";
		try {
			count = personalService.updatePension(bean);
		} catch (InvalidDataException e) {
			log.printStackTrace(e);
			message = e.getMessage();

		}

		return nomineeApprovalStatus(mapping,form,request,response);
	}
	public ActionForward nomineeApprovalStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::nomineeApprovalStatus");
		String pfid = "";
		HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		
		if(request.getParameter("pfid")!=null)
		{
			pfid = request.getParameter("pfid").toString();
		}
		else
		{
			logInfo = (LoginInfo) session.getAttribute("user");
			pfid=logInfo.getPensionNo();
			
		}
		log.info("PersonalFormAction:::nomineeApprovalStatus pfid is"+pfid);
		ArrayList nomineeStatusList=null;
		try{
			nomineeStatusList=personalService.getNomineeStatusList(pfid);
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		request.setAttribute("requestStatus",nomineeStatusList);
		return mapping.findForward("success");
	}
	public void formsDisable(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String pensionno= "", cpfaccno = "", region = "",formsstatus="",pcreportstatus="",pensionclaimstatus="";
		log.info("delete items  "
				+ request.getParameterValues("mappingflag"));
		String[] unmappedRecords = {};
		String[] deleteTransactions = {};
		if (request.getParameterValues("mappingflag") != null) {
			unmappedRecords = (String[]) request
			.getParameterValues("mappingflag");
		}
        if(request.getParameter("formsstatus")!=null){
        	formsstatus=request.getParameter("formsstatus");
        }
        if(request.getParameter("pensionno")!=null){
        	pensionno=request.getParameter("pensionno");
        }
        if(request.getParameter("pcreportstatus")!=null){
        	pcreportstatus=request.getParameter("pcreportstatus");
        }
        
        if(request.getParameter("pensionclaimstatus")!=null){
        	pensionclaimstatus=request.getParameter("pensionclaimstatus");
        }
        
        HttpSession session = request.getSession(true);
		LoginInfo logInfo = new LoginInfo();
		logInfo = (LoginInfo) session.getAttribute("user");
		FinancialReportService finReportService = new FinancialReportService();
		String userName = logInfo.getUserName();
		String ipAddress = getIPAddress(request);
		fs.formsDisable(pensionno,formsstatus,pcreportstatus,userName,ipAddress);
		
        StringBuffer sb=new StringBuffer();
         
        if(formsstatus!=""){
            sb.append("Form 6-7-8 Status Changed to "+formsstatus);
            }if(pensionclaimstatus!=""){
            sb.append("PensionClaim Process Status Changed to "+pensionclaimstatus);
            }if(pcreportstatus!=""){
            sb.append("PCReport Verified Status Changed to "+pcreportstatus);	
            }
         
		response.setContentType("text/xml");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(sb.toString());
		out.write(sb.toString());
		
	}
		
//	New Method 
	public ActionForward loadSignatureMapping(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::loadSignatureMapping");
		 String path="",filepath="";
		 ArrayList searchList= new ArrayList();
		 SignatureMappingBean signBean = new SignatureMappingBean();
		 Map userMap=new  LinkedHashMap();
		 Iterator userListIterator = null;
		 userMap = commonUtil.getUsersList();		 
		 Set userset = userMap.entrySet();
		 userListIterator = userset.iterator();
	     request.setAttribute("userListIterator", userListIterator);
	     request.setAttribute("ScreenType","NEW");
	    	 if(request.getParameter("flag").equals("search")){
	    		 path="searchsignaturemapping";
	    	 }else{
	    	 path="loadsignaturemapping";
	     }
	    	 request.setAttribute("signMapping",signBean);	    	  
	     log.info("--path----------"+path);
		return mapping.findForward(path);
	}


//	New Method 
	public ActionForward saveSignatureMapping(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::saveSignatureMapping");
		 String  path="",userName="",activity="",screenType="";
		 String[] screenInfo;
		int result=0,length=0; 
		Map userMap=new  LinkedHashMap();
		 Iterator userListIterator = null;
		 SignatureMappingBean screenNamesBean= null;
		  List screenDetails= new ArrayList();
		 userMap = commonUtil.getUsersList();		 
		 Set userset = userMap.entrySet();
		 userListIterator = userset.iterator();
	     request.setAttribute("userListIterator", userListIterator);
	     
		try{
			DynaValidatorForm dynaForm = (DynaValidatorForm)form;
			SignatureMappingBean signBean= new SignatureMappingBean();
			BeanUtils.copyProperties(signBean,dynaForm);
		  
			
			screenInfo = signBean.getScreenPermissionInfo();
			 length = screenInfo.length;			
			for (int i = 0; i < length; i++) {			 
				StringTokenizer st = new StringTokenizer(screenInfo[i], "|");
				if (st.hasMoreTokens()) {
					screenNamesBean = new SignatureMappingBean();
					screenNamesBean.setScreenName(st.nextToken());
					screenNamesBean.setFromDate(st.nextToken());
					screenNamesBean.setToDate(st.nextToken());
					screenNamesBean.setScreenCode(st.nextToken());
					screenNamesBean.setModuleCode(st.nextToken());
					 	 
				}
				screenDetails.add(screenNamesBean);
			}
			signBean.setScreenPermissionDetails(screenDetails);
			
					 
		if(request.getParameter("activity")!=null){
			if(request.getParameter("activity").equals("true")){ 
				activity="Y";
			}else{
				activity="N";
			} 
			signBean.setActivity(activity);
		} 
		 if(request.getParameter("username")!=null){
			userName=request.getParameter("username");
			signBean.setUserName(userName);
		}
		 
		log.info("--screentype---"+request.getParameter("screentype"));  
		screenType=request.getParameter("screentype");   
		 personalService.uploadSignature(signBean,screenType);
		 personalService.saveScreenSignDetails(signBean,screenType);
		 path="searchsignaturemapping";
		 
		}catch (Exception e) {
			// TODO: handle exception
		}
		     
		return mapping.findForward(path);
	}


//	New Method 
	public ActionForward searchSignatureMapping(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::loadSignatureMapping");
		String userID="";
		ArrayList searchList = new ArrayList();
		if(request.getParameter("userid")!=null){
			userID=request.getParameter("userid");
		}		 
		searchList=personalService.searchSignatures(userID);
		if(searchList.size()>0){
			request.setAttribute("searchList",searchList);
		}
		return mapping.findForward("searchsignaturemapping");
	}	
		

//New Method
	public ActionForward editSignatureMapping(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("PersonalFormAction:::editSignatureMapping");
		 String path="",filepath="";		 
		StringBuffer screenDetails=null;
		 Map userMap=new  LinkedHashMap();
		 Iterator userListIterator = null;
		 userMap = commonUtil.getUsersList();		 
		 Set userset = userMap.entrySet();
		 userListIterator = userset.iterator();
	     request.setAttribute("userListIterator", userListIterator);
		 ArrayList searchList= new ArrayList();
		 SignatureMappingBean signBean = new SignatureMappingBean();
		 SignatureMappingBean screenInfoBean=null;
		 Bean comBean = null;
		 List ScreenInfo = new ArrayList();
		 ArrayList screenDetList=new ArrayList();
		 try{
	    	 searchList=personalService.editSignatures(request.getParameter("userid"));	    	  
	    	 signBean=(SignatureMappingBean)searchList.get(0);
	    	 if(signBean.getModuleName()!=null){
	    		 signBean.setType("Module");
	    	 }
	    	  
	    	 screenDetList=personalDAO.getScreenSignDetails(request.getParameter("userid"));
	    	// log.info("-----in Action size-------"+screenDetList.size());
				for(int i=0;i<screenDetList.size();i++){
					screenInfoBean = (SignatureMappingBean)screenDetList.get(i);
					screenDetails = new StringBuffer(StringUtility.checknull(screenInfoBean.getScreenName()));
					screenDetails.append("|").append(StringUtility.checknull(screenInfoBean.getFromDate())).append("|").append(StringUtility.checknull(screenInfoBean.getToDate()));
					screenDetails.append("|").append(StringUtility.checknull(screenInfoBean.getScreenCode())).append("|").append(StringUtility.checknull(screenInfoBean.getModuleCode()));
					  
					comBean = new Bean(screenDetails.toString(),screenDetails.toString());
					ScreenInfo.add(comBean);
				}
				
				request.setAttribute("ScreenInfo",ScreenInfo);
				if(screenDetList.size()==0){
				request.setAttribute("ScreenType","NEW");
				}else{
					request.setAttribute("ScreenType","EDIT");
				}
	    	 
	    	 request.setAttribute("signMapping",signBean);
				if(signBean.getSignatureName()!=null){
						filepath = getServlet().getServletContext().getRealPath("/")+"/uploads/dbf/"+signBean.getSignatureName();
						personalService.getImage(filepath,signBean.getUserID());
				}
		 }catch (Exception e) {
					// TODO: handle exception
				}				
		 	 
		
		 		if(screenDetList.size()==0){
					request.setAttribute("ScreenType","NEW");
					signBean.setUserID(request.getParameter("userid"));
					signBean.setType("NO-SELECT");
					}else{
						request.setAttribute("ScreenType","EDIT");
						
					}
		 	 
		 		request.setAttribute("signMapping",signBean);
				path="loadsignaturemapping";
				 //log.info("--in path ---------- "+path+screenDetList.size()+"------"+request.getAttribute("ScreenType"));
				 return mapping.findForward(path);	
	     }
	
}
