package com.epis.bean.rpfc;

import java.util.ArrayList;

public class EmployeePensionCardInfo {
	String monthyear="",shnMnthYear="",emoluments="",emppfstatury="",empvpf="",principal="",interest="",emptotal="",advancePFWPaid="";
	String empNet="",aaiPF="",pfDrawn="",aaiNet="",pensionContribution="",station="",empCPF="",empNetCummulative="",aaiCummulative="",AdvancesAmount="";
	String obFlag="",cbFlag="",aaiContrCumlative="",grandCummulative="",grandAAICummulative="",pensionNo="",region="",Form7Narration="",emolumentMonths="";
	String fpfFund="",arrearEmpNetCummulative="",arrearAaiCummulative="",grandArrearEmpNetCummulative="",grandArrearAAICummulative="";
	String transArrearFlag="",originalEmoluments="",pfcardNarration="",mergerflag="",supflag="",mergerremarks="",dispMonthyear="",oringalArrearAmnt="",oringalArrearContri="";
	String emolumentsDiff = "",leavedata="",cpfTotDiff="",pFTotDiff="",pensionContriTotDiff="",empSubTotDiff="",aaiContriDiff ="",reportYear ="",PFWSubscri ="",PFWContri = "",cpfAccno ="";
	String pensionContriAmnt ="",pensionContriArrearAmnt="",aftrFinstlmntAAINetTot="",aftrFinstlmntEmpNetTot="",aftrFinstlmntPCNetTot="",monthsCntAfterFinstlmnt="",dataAfterFinalsettlemnt="false";
	String empSub="",empSubInterest="",adjEmpSubInterest="",aaiContri="",aaiContriInterest="",adjAaiContriInterest="",pensionTotal="",pensionInterest="",adjPensionInterest="",approvedStage="",SWRemarks="",editedDate="";
	String  form2Status="",frozen="",form2id="",pfcardNarrationFlag="",junkflag="",specialEmolumentsFlag="",form4flag="";

	String originalEmv="",originalpensionContri="",pensioncontrib="",freshopflag="",emoluments_b="";


	public String getEmoluments_b() {
		return emoluments_b;
	}
	public void setEmoluments_b(String emolumentsB) {
		emoluments_b = emolumentsB;
	}
	public String getPensioncontrib() {
		return pensioncontrib;
	}
	public void setPensioncontrib(String pensioncontrib) {
		this.pensioncontrib = pensioncontrib;
	}
	public String getFreshopflag() {
		return freshopflag;
	}
	public void setFreshopflag(String freshopflag) {
		this.freshopflag = freshopflag;
	}
	public String getOriginalEmv() {
		return originalEmv;
	}
	public void setOriginalEmv(String originalEmv) {
		this.originalEmv = originalEmv;
	}
	public String getOriginalpensionContri() {
		return originalpensionContri;
	}
	public void setOriginalpensionContri(String originalpensionContri) {
		this.originalpensionContri = originalpensionContri;
	}
	public String getSpecialEmolumentsFlag() {
		return specialEmolumentsFlag;
	}
	public void setSpecialEmolumentsFlag(String specialEmolumentsFlag) {
		this.specialEmolumentsFlag = specialEmolumentsFlag;
	}
	String additionalContri="0",netEPF="0";
	public String getForm4flag() {
		return form4flag;
	}
	public void setForm4flag(String form4flag) {
		this.form4flag = form4flag;
	}
	boolean isYearFlag=false;

	public String getJunkflag() {
		return junkflag;
	}
	public void setJunkflag(String junkflag) {
		this.junkflag = junkflag;
	}
	public String getAdditionalContri() {
		return additionalContri;
	}
	public void setAdditionalContri(String additionalContri) {
		this.additionalContri = additionalContri;
	}
	public String getNetEPF() {
		return netEPF;
	}
	public void setNetEPF(String netEPF) {
		this.netEPF = netEPF;
	}
	String jkhs="";
	public String getJkhs() {
		return jkhs;
	}
	public void setJkhs(String jkhs) {
		this.jkhs = jkhs;
	}
	public boolean isYearFlag() {
		return isYearFlag;
	}
	public void setYearFlag(boolean isYearFlag) {
		this.isYearFlag = isYearFlag;
	}
	public String getSupflag() {
		return supflag;
	}
	public void setSupflag(String supflag) {
		this.supflag = supflag;
	}
	public String getOringalArrearAmnt() {
		return oringalArrearAmnt;
	}
	public String getPfcardNarration() {
		return pfcardNarration;
	}
	public void setPfcardNarration(String pfcardNarration) {
		this.pfcardNarration = pfcardNarration;
	}
	public void setOringalArrearAmnt(String oringalArrearAmnt) {
		this.oringalArrearAmnt = oringalArrearAmnt;
	}
	public String getOringalArrearContri() {
		return oringalArrearContri;
	}
	public void setOringalArrearContri(String oringalArrearContri) {
		this.oringalArrearContri = oringalArrearContri;
	}
	public String getDispMonthyear() {
		return dispMonthyear;
	}
	public void setDispMonthyear(String dispMonthyear) {
		this.dispMonthyear = dispMonthyear;
	}
	String dueemoluments="",duepensionamount="";
	public String getDueemoluments() {
		return dueemoluments;
	}
	public void setDueemoluments(String dueemoluments) {
		this.dueemoluments = dueemoluments;
	}
	public String getDuepensionamount() {
		return duepensionamount;
	}
	public void setDuepensionamount(String duepensionamount) {
		this.duepensionamount = duepensionamount;
	}
	public String getOriginalEmoluments() {
		return originalEmoluments;
	}
	public String getMergerflag() {
		return mergerflag;
	}
	public void setMergerflag(String mergerflag) {
		this.mergerflag = mergerflag;
	}
	public String getMergerremarks() {
		return mergerremarks;
	}
	public void setMergerremarks(String mergerremarks) {
		this.mergerremarks = mergerremarks;
	}
	public void setOriginalEmoluments(String originalEmoluments) {
		this.originalEmoluments = originalEmoluments;
	}
	public String getTransArrearFlag() {
		return transArrearFlag;
	}
	public void setTransArrearFlag(String transArrearFlag) {
		this.transArrearFlag = transArrearFlag;
	}
	public String getFpfFund() {
		return fpfFund;
	}
	public String getArrearEmpNetCummulative() {
		return arrearEmpNetCummulative;
	}
	public void setArrearEmpNetCummulative(String arrearEmpNetCummulative) {
		this.arrearEmpNetCummulative = arrearEmpNetCummulative;
	}
	public String getArrearAaiCummulative() {
		return arrearAaiCummulative;
	}
	public void setArrearAaiCummulative(String arrearAaiCummulative) {
		this.arrearAaiCummulative = arrearAaiCummulative;
	}
	public String getGrandArrearEmpNetCummulative() {
		return grandArrearEmpNetCummulative;
	}
	public void setGrandArrearEmpNetCummulative(String grandArrearEmpNetCummulative) {
		this.grandArrearEmpNetCummulative = grandArrearEmpNetCummulative;
	}
	public String getGrandArrearAAICummulative() {
		return grandArrearAAICummulative;
	}
	public void setGrandArrearAAICummulative(String grandArrearAAICummulative) {
		this.grandArrearAAICummulative = grandArrearAAICummulative;
	}
	public void setFpfFund(String fpfFund) {
		this.fpfFund = fpfFund;
	}
	public String getEmolumentMonths() {
		return emolumentMonths;
	}
	public void setEmolumentMonths(String emolumentMonths) {
		this.emolumentMonths = emolumentMonths;
	}
	public String getForm7Narration() {
		return Form7Narration;
	}
	public void setForm7Narration(String form7Narration) {
		Form7Narration = form7Narration;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPensionNo() {
		return pensionNo;
	}
	public void setPensionNo(String pensionNo) {
		this.pensionNo = pensionNo;
	}
	ArrayList obList=new ArrayList();

	public String getAdvancesAmount() {
		return AdvancesAmount;
	}
	public void setAdvancesAmount(String advancesAmount) {
		AdvancesAmount = advancesAmount;
	}
	public String getGrandCummulative() {
		return grandCummulative;
	}
	public void setGrandCummulative(String grandCummulative) {
		this.grandCummulative = grandCummulative;
	}
	public ArrayList getObList() {
		return obList;
	}
	public void setObList(ArrayList obList) {
		this.obList = obList;
	}
	public String getObFlag() {
		return obFlag;
	}
	public void setObFlag(String obFlag) {
		this.obFlag = obFlag;
	}

	public String getAaiCummulative() {
		return aaiCummulative;
	}
	public void setAaiCummulative(String aaiCummulative) {
		this.aaiCummulative = aaiCummulative;
	}

	public String getEmpNetCummulative() {
		return empNetCummulative;
	}
	public void setEmpNetCummulative(String empNetCummulative) {
		this.empNetCummulative = empNetCummulative;
	}
	public String getEmpCPF() {
		return empCPF;
	}
	public void setEmpCPF(String empCPF) {
		this.empCPF = empCPF;
	}
	public String getAaiNet() {
		return aaiNet;
	}
	public void setAaiNet(String aaiNet) {
		this.aaiNet = aaiNet;
	}
	public String getAaiPF() {
		return aaiPF;
	}
	public void setAaiPF(String aaiPF) {
		this.aaiPF = aaiPF;
	}
	public String getAdvancePFWPaid() {
		return advancePFWPaid;
	}
	public void setAdvancePFWPaid(String advancePFWPaid) {
		this.advancePFWPaid = advancePFWPaid;
	}
	public String getEmoluments() {
		return emoluments;
	}
	public void setEmoluments(String emoluments) {
		this.emoluments = emoluments;
	}
	public String getEmpNet() {
		return empNet;
	}
	public void setEmpNet(String empNet) {
		this.empNet = empNet;
	}
	public String getEmppfstatury() {
		return emppfstatury;
	}
	public void setEmppfstatury(String emppfstatury) {
		this.emppfstatury = emppfstatury;
	}
	public String getEmptotal() {
		return emptotal;
	}
	public void setEmptotal(String emptotal) {
		this.emptotal = emptotal;
	}
	public String getEmpvpf() {
		return empvpf;
	}
	public void setEmpvpf(String empvpf) {
		this.empvpf = empvpf;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getMonthyear() {
		return monthyear;
	}
	public void setMonthyear(String monthyear) {
		this.monthyear = monthyear;
	}
	public String getPensionContribution() {
		return pensionContribution;
	}
	public void setPensionContribution(String pensionContribution) {
		this.pensionContribution = pensionContribution;
	}
	public String getPfDrawn() {
		return pfDrawn;
	}
	public void setPfDrawn(String pfDrawn) {
		this.pfDrawn = pfDrawn;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getShnMnthYear() {
		return shnMnthYear;
	}
	public void setShnMnthYear(String shnMnthYear) {
		this.shnMnthYear = shnMnthYear;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getCbFlag() {
		return cbFlag;
	}
	public void setCbFlag(String cbFlag) {
		this.cbFlag = cbFlag;
	}
	public String getAaiContrCumlative() {
		return aaiContrCumlative;
	}
	public void setAaiContrCumlative(String aaiContrCumlative) {
		this.aaiContrCumlative = aaiContrCumlative;
	}
	public String getGrandAAICummulative() {
		return grandAAICummulative;
	}
	public void setGrandAAICummulative(String grandAAICummulative) {
		this.grandAAICummulative = grandAAICummulative;
	}
	public String getAaiContriDiff() {
		return aaiContriDiff;
	}
	public void setAaiContriDiff(String aaiContriDiff) {
		this.aaiContriDiff = aaiContriDiff;
	}
	public String getCpfTotDiff() {
		return cpfTotDiff;
	}
	public void setCpfTotDiff(String cpfTotDiff) {
		this.cpfTotDiff = cpfTotDiff;
	}
	public String getEmolumentsDiff() {
		return emolumentsDiff;
	}
	public void setEmolumentsDiff(String emolumentsDiff) {
		this.emolumentsDiff = emolumentsDiff;
	}
	public String getEmpSubTotDiff() {
		return empSubTotDiff;
	}
	public void setEmpSubTotDiff(String empSubTotDiff) {
		this.empSubTotDiff = empSubTotDiff;
	}
	public String getPensionContriTotDiff() {
		return pensionContriTotDiff;
	}
	public void setPensionContriTotDiff(String pensionContriTotDiff) {
		this.pensionContriTotDiff = pensionContriTotDiff;
	}
	public String getPFTotDiff() {
		return pFTotDiff;
	}
	public void setPFTotDiff(String totDiff) {
		pFTotDiff = totDiff;
	}
	public String getPFWContri() {
		return PFWContri;
	}
	public void setPFWContri(String contri) {
		PFWContri = contri;
	}
	public String getPFWSubscri() {
		return PFWSubscri;
	}
	public void setPFWSubscri(String subscri) {
		PFWSubscri = subscri;
	}
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	public String getCpfAccno() {
		return cpfAccno;
	}
	public void setCpfAccno(String cpfAccno) {
		this.cpfAccno = cpfAccno;
	}
	public String getLeavedata() {
		return leavedata;
	}
	public void setLeavedata(String leavedata) {
		this.leavedata = leavedata;
	}
	public String getPensionContriAmnt() {
		return pensionContriAmnt;
	}
	public void setPensionContriAmnt(String pensionContriAmnt) {
		this.pensionContriAmnt = pensionContriAmnt;
	}
	public String getPensionContriArrearAmnt() {
		return pensionContriArrearAmnt;
	}
	public void setPensionContriArrearAmnt(String pensionContriArrearAmnt) {
		this.pensionContriArrearAmnt = pensionContriArrearAmnt;
	}
	public String getAftrFinstlmntAAINetTot() {
		return aftrFinstlmntAAINetTot;
	}
	public void setAftrFinstlmntAAINetTot(String aftrFinstlmntAAINetTot) {
		this.aftrFinstlmntAAINetTot = aftrFinstlmntAAINetTot;
	}
	public String getAftrFinstlmntEmpNetTot() {
		return aftrFinstlmntEmpNetTot;
	}
	public void setAftrFinstlmntEmpNetTot(String aftrFinstlmntEmpNetTot) {
		this.aftrFinstlmntEmpNetTot = aftrFinstlmntEmpNetTot;
	}
	public String getAftrFinstlmntPCNetTot() {
		return aftrFinstlmntPCNetTot;
	}
	public void setAftrFinstlmntPCNetTot(String aftrFinstlmntPCNetTot) {
		this.aftrFinstlmntPCNetTot = aftrFinstlmntPCNetTot;
	}
	public String getMonthsCntAfterFinstlmnt() {
		return monthsCntAfterFinstlmnt;
	}
	public void setMonthsCntAfterFinstlmnt(String monthsCntAfterFinstlmnt) {
		this.monthsCntAfterFinstlmnt = monthsCntAfterFinstlmnt;
	}
	public String getDataAfterFinalsettlemnt() {
		return dataAfterFinalsettlemnt;
	}
	public void setDataAfterFinalsettlemnt(String dataAfterFinalsettlemnt) {
		this.dataAfterFinalsettlemnt = dataAfterFinalsettlemnt;
	}
	public String getAaiContri() {
		return aaiContri;
	}
	public void setAaiContri(String aaiContri) {
		this.aaiContri = aaiContri;
	}
	public String getAaiContriInterest() {
		return aaiContriInterest;
	}
	public void setAaiContriInterest(String aaiContriInterest) {
		this.aaiContriInterest = aaiContriInterest;
	}
	public String getEmpSub() {
		return empSub;
	}
	public void setEmpSub(String empSub) {
		this.empSub = empSub;
	}
	public String getEmpSubInterest() {
		return empSubInterest;
	}
	public void setEmpSubInterest(String empSubInterest) {
		this.empSubInterest = empSubInterest;
	}
	public String getPensionInterest() {
		return pensionInterest;
	}
	public void setPensionInterest(String pensionInterest) {
		this.pensionInterest = pensionInterest;
	}
	public String getPensionTotal() {
		return pensionTotal;
	}
	public void setPensionTotal(String pensionTotal) {
		this.pensionTotal = pensionTotal;
	}
	public String getApprovedStage() {
		return approvedStage;
	}
	public void setApprovedStage(String approvedStage) {
		this.approvedStage = approvedStage;
	}
	public String getAdjAaiContriInterest() {
		return adjAaiContriInterest;
	}
	public void setAdjAaiContriInterest(String adjAaiContriInterest) {
		this.adjAaiContriInterest = adjAaiContriInterest;
	}
	public String getAdjEmpSubInterest() {
		return adjEmpSubInterest;
	}
	public void setAdjEmpSubInterest(String adjEmpSubInterest) {
		this.adjEmpSubInterest = adjEmpSubInterest;
	}
	public String getAdjPensionInterest() {
		return adjPensionInterest;
	}
	public void setAdjPensionInterest(String adjPensionInterest) {
		this.adjPensionInterest = adjPensionInterest;
	}
	public String getForm2Status() {
		return form2Status;
	}
	public void setForm2Status(String form2Status) {
		this.form2Status = form2Status;
	}
	public String getSWRemarks() {
		return SWRemarks;
	}
	public void setSWRemarks(String remarks) {
		SWRemarks = remarks;
	}
	public String getEditedDate() {
		return editedDate;
	}
	public void setEditedDate(String editedDate) {
		this.editedDate = editedDate;
	}
	public String getFrozen() {
		return frozen;
	}
	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}
	public String getForm2id() {
		return form2id;
	}
	public void setForm2id(String form2id) {
		this.form2id = form2id;
	}
	public String getPfcardNarrationFlag() {
		return pfcardNarrationFlag;
	}
	public void setPfcardNarrationFlag(String pfcardNarrationFlag) {
		this.pfcardNarrationFlag = pfcardNarrationFlag;
	}
	 
	 

}
