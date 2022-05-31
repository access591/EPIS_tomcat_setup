package com.epis.services.advances;

import java.util.ArrayList;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.advances.AdvanceBasicReportBean;
import com.epis.bean.advances.AdvanceCPFForm2Bean;
import com.epis.bean.advances.AdvanceEditBean;
import com.epis.bean.advances.AdvancePFWFormBean;
import com.epis.bean.advances.AdvanceSearchBean;
import com.epis.bean.advances.EmpBankMaster;
import com.epis.common.exception.EPISException;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.dao.advances.CPFPTWAdvanceDAO;



public class CPFPTWAdvanceService {
	String userName = "", computerName = "";
	Log log = new Log(CPFPTWAdvanceService.class);
	public CPFPTWAdvanceService() {

	}

	public CPFPTWAdvanceService(String frmSelUserName, String frmSelCmpName) {
		this.userName = frmSelUserName;
		this.computerName = frmSelCmpName;
	}

	CPFPTWAdvanceDAO ptwAdvanceDAO =null;

	public String addAdvanceInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankMaster) throws EPISException {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.addCPFPTWAdvanceInfo(advanceBean, bankMaster);

		return message;
	}

	public EmpBankMaster employeeBankInfo(String pensionNo) {
		EmpBankMaster bankMaster = new EmpBankMaster();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		bankMaster = ptwAdvanceDAO.loadEmployeeBankInfo(pensionNo);
		return bankMaster;
	}

	public ArrayList searchAdvance(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchAdvance(advanceSearchBean);
		return searchList;
	}

	public ArrayList advanceReport(String pensionNo, String transactionID) {
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.advanceReport(pensionNo, transactionID);
		return reportList;
	}

	public ArrayList loadCPFAdvanceForm2(String pensionNo,
			String transactionID, String formType, String transactionDate)
			throws InvalidDataException {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.getCPFAdvanceForm2Info(pensionNo,
				transactionID, formType, transactionDate);
		return reportList;
	}

	public int updateCPFAdvanceForm2(AdvanceCPFForm2Bean updateForm2Bean,
			String frmName, String frmFlag) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.updateCPFAdvanceForm2Info(
				updateForm2Bean, frmName, frmFlag);
		return totalRcrdUpdted;
	}

	public int updateCPFAdvanceForm3(AdvancePFWFormBean updateForm4Bean,
			String emoluments, String subscriptionAmt, String contributionAmt,
			String cpfFund, String amountRecmded, String mnthsEmoluments,
			String flag, String narration,String firstInsSubAmnt,String firstInsConrtiAmnt) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.updatePFWAdvanceForm3Info(
				updateForm4Bean, emoluments, subscriptionAmt, contributionAmt,
				cpfFund, amountRecmded, mnthsEmoluments, flag, narration,firstInsSubAmnt,firstInsConrtiAmnt);
		return totalRcrdUpdted;
	}

	public int updatePFWForm4Verification(AdvancePFWFormBean updateForm4Bean,
			String emoluments, String subscriptionAmt, String contributionAmt,
			String cpfFund, String amountRecmded, String mnthsEmoluments,
			String flag, String approvedSubAmt, String empshare,
			String advnceapplid,String firstInsSubAmnt,String firstInsConrtiAmnt) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.updatePFWForm4Verification(
				updateForm4Bean, emoluments, subscriptionAmt, contributionAmt,
				cpfFund, amountRecmded, mnthsEmoluments, flag, approvedSubAmt,
				empshare, advnceapplid,firstInsSubAmnt,firstInsConrtiAmnt);
		return totalRcrdUpdted;
	}

	public int updateCPFAdvanceForm4(AdvancePFWFormBean updateForm4Bean) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO
				.updatePFWAdvanceForm4Info(updateForm4Bean);
		return totalRcrdUpdted;
	}

	// ----------------------------------advanceForm2Report() Added by Suneetha
	// V on 07/10/2009--------------------------------------------------
	public ArrayList advanceForm2Report(String pensionNo, String transactionID) {
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.advanceForm2Report(pensionNo, transactionID);
		return reportList;
	}

	// ----------------------------------End :
	// advanceForm2Report()--------------------------------------------------
	// ----------------------------------advanceForm3Report() Added by Suneetha
	// V on 07/10/2009--------------------------------------------------
	public ArrayList advanceForm3Report(String pensionNo, String transactionID,
			String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		try {
			reportList = ptwAdvanceDAO.getPFWAdvanceForm3Info(pensionNo,
					transactionID, frmName);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportList;
	}

	// ----------------------------------End :
	// advanceForm3Report()--------------------------------------------------
	// ----------------------------------NoteSheet Master Added by Suneetha V on
	// 16/10/2009--------------------------
	/*
	 * public String addNoteSheet(AdvanceBasicBean advanceBean,EmpBankMaster
	 * bankMaster){ String message="";
	 * message=ptwAdvanceDAO.addNoteSheet(advanceBean,bankMaster); message="Your
	 * Advance Request"+message+"Added Successfully"; return message; }
	 */
	public ArrayList searchNoteSheet(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchNoteSheet(advanceSearchBean);
		return searchList;
	}

	public ArrayList noteSheetReport(String pensionNo, String sanctionNo) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.noteSheetReport(pensionNo, sanctionNo);
		return reportList;
	}

	public ArrayList sanctionOrder(String pensionNo, String sanctionNo,
			String frmFlag, String frmSanctionDate) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.sanctionOrder(pensionNo, sanctionNo,
				frmFlag, frmSanctionDate);
		return reportList;
	}

	public String updateNoteSheet(AdvanceBasicBean advanceBean, String frmName,
			String frmFlag) {
		String message = "";
		ptwAdvanceDAO=new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		log.info("CPFPTWADVANCES Services updateNoteSheet======================userName"+userName+"computerName"+computerName);
		message = ptwAdvanceDAO.updateNoteSheet(advanceBean, frmName, frmFlag);
		message = "Your Advance Request" + message + "Updated Successfully";
		return message;
	}

	public ArrayList consSanctionOrder(String region, String fromDate,
			String toDate, String seperationreason, String station,
			String trust, String frmName) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO=new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.consSanctionOrder(region, fromDate, toDate,
				seperationreason, station, trust, frmName);
		return reportList;
	}

	public int updatePFWAdvanceForm2(AdvanceCPFForm2Bean updateForm2Bean) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.updatePFWAdvanceForm2(updateForm2Bean);
		return totalRcrdUpdted;
	}

	public ArrayList advancesReport(String region, String fromDate,
			String toDate, String advanceType, String purposeType,
			String trust, String station, String userProfile, String userUnitCode, String userId,String accountType,String menuAccesFrom) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.advancesReport(region, fromDate, toDate,
				advanceType, purposeType, trust, station, userProfile, userUnitCode, userId,accountType,menuAccesFrom);
		return reportList;
	}

	public ArrayList summarizedAdvancesReport(String region, String fromDate,
			String toDate, String advanceType, String purposeType,
			String trust, String station, String userProfile, String userUnitCode, String userId,String advanceReportType) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		log.info("-----In Service--summarizedAdvancesReport----");
		reportList = ptwAdvanceDAO.summarizedAdvancesReport(region, fromDate, toDate,
				advanceType, purposeType, trust, station, userProfile, userUnitCode, userId);
		return reportList;
	}
	// ----------------------------------End : NoteSheet
	

	public ArrayList advanceSanctionOrder(String pensionNo,
			String transactionID, String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		try {
			basicBean = ptwAdvanceDAO.advanceSanctionOrder(pensionNo,
					transactionID, frmName);

			if (basicBean.getPurposeType().toUpperCase().equals("MARRIAGE")) {
				basicReportBean = ptwAdvanceDAO.loadPFWMarriage(transactionID,
						basicReportBean);
			}

			if (basicBean.getPurposeType().toUpperCase().equals("HBA")) {
				basicReportBean = ptwAdvanceDAO.loadPFWHBA(transactionID,
						basicReportBean);
			}
			if (basicBean.getPurposeType().toUpperCase().equals("HE")) {
				basicBean.setPurposeType("Higher Education");
				basicReportBean = ptwAdvanceDAO.loadPFWHigherEducation(
						transactionID, basicReportBean);
			}
			reportList.add(basicBean);
			reportList.add(basicReportBean);

		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportList;
	}

	public int updateCPFVerification(AdvanceCPFForm2Bean updateForm2Bean) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.updateCPFVerification(updateForm2Bean);
		return totalRcrdUpdted;
	}

	public AdvanceEditBean loadAdvancesEditInfo(String transID, String PensionNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.loadAdvancesEditInfo(transID, PensionNo);

	}

	public ArrayList updateAdvacneInfo(AdvanceEditBean advanceBean,
			EmpBankMaster bankMaster) {
		ArrayList advanceList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		advanceList = ptwAdvanceDAO.updateAdvacneInfo(advanceBean, bankMaster);
		return advanceList;
	}

	public String updateAdvanceNextInfo(AdvanceEditBean advanceBean,
			EmpBankMaster bankMaster, String wthDrwFlag) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updateAdvanceNextInfo(advanceBean, bankMaster,
				wthDrwFlag);
		return message;
	}

	public String loadWithDrawalDetails(String transID, String chkWthDrawInfo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.getWithDrawalDetails(transID, chkWthDrawInfo);
	}

	public int deleteAdvances(AdvanceSearchBean advanceSearchBean) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.deleteAdvances(advanceSearchBean);
	}

	public ArrayList checkListApprove(String pensionNo, String transactionID,
			String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		try {
			reportList = ptwAdvanceDAO.checkListApprove(pensionNo,
					transactionID, frmName);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportList;
	}

	public int savePFWCheckList(AdvancePFWFormBean updateForm4Bean,
			String emoluments, String hbaDrwnFromAAI, String wthdrwlInfo,
			String subscriptionAmt, String contributionAmt,
			String lodDocumentsInfo, String purposeOptionType, String flag) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.savePFWCheckList(updateForm4Bean,
				emoluments, hbaDrwnFromAAI, wthdrwlInfo, subscriptionAmt,
				contributionAmt, lodDocumentsInfo, purposeOptionType, flag);
		return totalRcrdUpdted;
	}

	public ArrayList advanceCheckListApprove(String pensionNo,
			String transactionID, String formType, String transactionDate)
			throws InvalidDataException {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.advanceCheckListApprove(pensionNo,
				transactionID, formType, transactionDate);
		return reportList;
	}

	public int saveCPFCheckList(AdvanceCPFForm2Bean updateForm2Bean) {
		int totalRcrdUpdted = 0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		totalRcrdUpdted = ptwAdvanceDAO.saveCPFCheckList(updateForm2Bean);
		return totalRcrdUpdted;
	}

	// New Method -------

	public ArrayList searchFinalSettlement(AdvanceSearchBean advanceSearchBean,
			String flag) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchFinalSettlement(advanceSearchBean,
				flag);
		return searchList;
	}

	public ArrayList searchPrintSanctionOrder(
			AdvanceSearchBean advanceSearchBean, String flag) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchPrintSanctionOrder(advanceSearchBean,
				flag);
		return searchList;
	}

	public ArrayList searchPrintPFWSanctionOrder(
			AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchPrintPFWSanctionOrder(advanceSearchBean);
		return searchList;
	}
	// New Method -------

	public String addFinalSettlementInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankMaster) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.addFinalSettlementInfo(advanceBean, bankMaster);

		return message;
	}

	// New Method -------

	public ArrayList finalSettlementVerificationApproval(String pensionNo,
			String sanctionNo, String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceBasicReportBean basicBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.finalSettlementVerificationApproval(
				pensionNo, sanctionNo, frmName);
		return reportList;
	}

	public String addFinalSettlementVerificationInfo(
			AdvanceBasicBean advanceBean) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.addFinalSettlementVerificationInfo(advanceBean);

		return message;
	}

	public String detailsOfPosting(String pensionNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.detailsOfPosting(pensionNo);
	}

	public int deleteFinalSettlement(String PensionNo, String SanctionNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.deleteFinalSettlement(PensionNo, SanctionNo);
	}

	public void updateNoteSheetSanctionDate(AdvanceSearchBean advanceSearchBean) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		int n = ptwAdvanceDAO.updateNoteSheetSanctionDate(advanceSearchBean);

	}

	public void updatePFWSanctionDate(AdvanceSearchBean advanceSearchBean) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		int n = ptwAdvanceDAO.updatePFWSanctionDate(advanceSearchBean);

	}

	public EmpBankMaster employeeBankInfo(String pensionNo, String transId) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		EmpBankMaster bankMaster = new EmpBankMaster();
		bankMaster = ptwAdvanceDAO.loadEmployeeBankInfo(pensionNo, transId);
		return bankMaster;
	}
	public ArrayList employeeBankInfoList(String pensionNo, String transId) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		ArrayList bankMasterList = new ArrayList();
		bankMasterList = ptwAdvanceDAO.loadEmployeeBankInfoList(pensionNo, transId);
		return bankMasterList;
	}

	public String updateFinalSettlementInfo(AdvanceBasicBean advanceBean) {
		
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updateFinalSettlementInfo(advanceBean);

		return message;
	}

	// New Method

	public AdvanceBasicReportBean loadNoteSheetPersonalDetails(String PensionNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.loadNoteSheetPersonalDetails(PensionNo);

	}

	public AdvanceBasicReportBean loadNoteSheetOtherDetails(String PensionNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.loadNoteSheetOtherDetails(PensionNo);

	}

	public String addFinalSettlementArrearInfo(AdvanceBasicBean advanceBean,
			EmpBankMaster bankMaster) {
		
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.addFinalSettlementArrearInfo(advanceBean,
				bankMaster);

		return message;
	}

	// New Method

	public String updateNoteSheetArrear(AdvanceBasicBean advanceBean,
			String frmName, String frmFlag) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updateNoteSheetArrear(advanceBean, frmName,
				frmFlag);
		message = "Your Advance Request" + message + "Updated Successfully";
		return message;
	}

	public ArrayList MISReport(String region, String fromDate, String toDate,
			String subPurposeType, String purposeType, String trust,
			String station) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.MISReport(region, fromDate, toDate,
				subPurposeType, purposeType, trust, station);
		return reportList;
	}

	public String updateFinalSettlementArrearInfo(AdvanceBasicBean advanceBean) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updateFinalSettlementArrearInfo(advanceBean);

		return message;
	}

	public ArrayList finalPaymentRegister(String region, String fromDate,
			String toDate, String seperationreason, String trust, String station,String arearType) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.finalPaymentRegister(region, fromDate,
				toDate, seperationreason, trust, station,arearType);
		return reportList;
	}

	public ArrayList pfwSummaryReport(String region, String fromDate,
			String toDate, String station) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
	reportList = ptwAdvanceDAO.pfwSummaryReport(region, fromDate, toDate,
				station);
		return reportList;
	}
	public ArrayList searchRecordsToDelete(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchRecordsToDelete(advanceSearchBean);
		return searchList;
	}
	
	//New Method	 	
	public int deleteRecords(AdvanceSearchBean advanceSearchBean) {	
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		int count = ptwAdvanceDAO.deleteRecords(advanceSearchBean);
		return  count;
	}
	public String updateBankInfo(String pensionNo,String transId,String bankFlag,String paymentFlag,
			EmpBankMaster bankMaster) {
		
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updateBankInfo(pensionNo, transId, bankFlag, paymentFlag,
				bankMaster);

		return message;
	}
	public String addNomineeBankDet(String pensionNo,String transId,EmpBankMaster bankMaster) {
		
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.addNomineeBankDet(pensionNo, transId,bankMaster);

		return message;
	}
	
	//New Method
	public ArrayList nomineeBankInfo(String pensionNo, String transId) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		ArrayList nomineeSearchList = new ArrayList();
		nomineeSearchList = ptwAdvanceDAO.nomineeBankInfo(pensionNo, transId);
		return nomineeSearchList;
	}
//New Method
	 
	public int deleteNomineeBankDetails(String pensionNo, String nsSanctionNo,String nomineeSerialNo) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName); 
		int result = 0;
		result = ptwAdvanceDAO.deleteNomineeBankDetails(pensionNo, nsSanctionNo, nomineeSerialNo);
		return result;
	}
	public int deleteFinalSettlement(AdvanceBasicBean advanceBean) {
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		return ptwAdvanceDAO.deleteFinalSettlement(advanceBean);
	}
	 
	public void  addEmployeePostingDet(String pensionNo,String nsSanctionNo,String postingRegion,String postingStation ) { 
		  ptwAdvanceDAO.addEmployeePostingDet(pensionNo, nsSanctionNo,postingRegion,postingStation);  
	}
	public void  deleteEmployeePostingDetails(String pensionNo,String nsSanctionNo ) { 
		  ptwAdvanceDAO.deleteEmployeePostingDetails(pensionNo, nsSanctionNo);  
	}
	public String getAdvanceApprovedDetails(String fromDate,String toDate,String userUnitCode,String region,String userProfile,String accountType){
		  return ptwAdvanceDAO.getAdvanceApprovedDetails(fromDate, toDate,userUnitCode, region, userProfile, accountType);  
	}
	public ArrayList rejectedCPFReport(String region, String fromDate,
			String toDate, String advanceType, String purposeType,
			String trust, String station, String userProfile, String userUnitCode, String userId,String accountType,String menuAccesFrom) {
		ArrayList reportList = new ArrayList();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		reportList = ptwAdvanceDAO.rejectedCPFReport(region, fromDate, toDate,
				advanceType, purposeType, trust, station, userProfile, userUnitCode, userId,accountType,menuAccesFrom);
		return reportList;
	}
	public ArrayList loadPFWRevisedDetails(String pensionNo, String transactionID,String purposeType) { 
		 
		ArrayList reportList = new ArrayList();
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		basicBean = ptwAdvanceDAO.loadPFWRevisedDetails(pensionNo, transactionID, purposeType);

		if (basicBean.getPurposeType().toUpperCase().equals("MARRIAGE")) {
			basicReportBean = ptwAdvanceDAO.loadPFWMarriage(transactionID,
					basicReportBean);
		}

		if (basicBean.getPurposeType().toUpperCase().equals("HBA")) {
			basicReportBean = ptwAdvanceDAO.loadPFWHBA(transactionID,
					basicReportBean);
		}
		if (basicBean.getPurposeType().toUpperCase().equals("HE")) {
			basicBean.setPurposeType("Higher Education");
			basicReportBean = ptwAdvanceDAO.loadPFWHigherEducation(
					transactionID, basicReportBean);
		}
		reportList.add(basicBean);
		reportList.add(basicReportBean);
		return reportList; 
		
	}
	
	public String  updatePFWRevisedDetails(AdvanceCPFForm2Bean updatePFWRevisedBean ,EmpBankMaster bankMaster) {
		String message = "";
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		message = ptwAdvanceDAO.updatePFWRevisedDetails(updatePFWRevisedBean,bankMaster);
		return message;
	}
	
	public ArrayList advanceSanctionOrderRev(String pensionNo,
			String transactionID, String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		try {
			basicBean = ptwAdvanceDAO.advanceSanctionOrderRev(pensionNo,
					transactionID, frmName);

			/*if (basicBean.getPurposeType().toUpperCase().equals("MARRIAGE")) {
				basicReportBean = ptwAdvanceDAO.loadPFWMarriage(transactionID,
						basicReportBean);
			}*/

			if (basicBean.getPurposeType().toUpperCase().equals("HBA")) {
				basicReportBean = ptwAdvanceDAO.loadPFWHBAREV(transactionID,
						basicReportBean);
			}
			/*if (basicBean.getPurposeType().toUpperCase().equals("HE")) {
				basicBean.setPurposeType("Higher Education");
				basicReportBean = ptwAdvanceDAO.loadPFWHigherEducation(
						transactionID, basicReportBean);
			}*/
			reportList.add(basicBean);
			reportList.add(basicReportBean);

		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportList;
	}
	public ArrayList searchAdvancesRev(AdvanceSearchBean advanceSearchBean) {
		ArrayList searchList = null;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		searchList = ptwAdvanceDAO.searchAdvancesRev(advanceSearchBean);
		return searchList;
	}
	public ArrayList  editAdvancesRevised(String pensionNo,String 	transactionNo,String purposeType,String frmName) {
		ArrayList reportList = new ArrayList();
		AdvanceCPFForm2Bean basicBean = new AdvanceCPFForm2Bean();
		AdvanceBasicReportBean basicReportBean = new AdvanceBasicReportBean();
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
		basicBean = ptwAdvanceDAO.editAdvancesRevised(pensionNo, transactionNo, purposeType,frmName);

		 
		if (basicBean.getPurposeType().toUpperCase().equals("HBA")) {
			basicReportBean = ptwAdvanceDAO.loadPFWHBAREV(transactionNo,
					basicReportBean);
		}
		 
		reportList.add(basicBean);
		reportList.add(basicReportBean);
		return reportList; 
	}
	public int isPFWPandemic(String pfid){
		
		int count=0;
		ptwAdvanceDAO = new CPFPTWAdvanceDAO(this.userName,
				this.computerName);
	 count=ptwAdvanceDAO.isPFWPandemic(pfid);
	 return count;
	}
	
}
