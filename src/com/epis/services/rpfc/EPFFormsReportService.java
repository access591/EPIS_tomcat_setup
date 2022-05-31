package com.epis.services.rpfc;

import java.util.ArrayList;

import com.epis.dao.rpfc.FinancialReportDAO;

import com.epis.bean.rpfc.epfforms.AAIEPFReportBean;
import com.epis.dao.rpfc.EPFFormsReportDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Log;

public class EPFFormsReportService {
	Log log = new Log(EPFFormsReportService.class);
	EPFFormsReportDAO epfformsReportDAO=new EPFFormsReportDAO();
	CommonUtil commonUtil=new CommonUtil();
	FinancialReportDAO finReportDAO=new FinancialReportDAO();
	public ArrayList AAIEPFForm2Report(String pfidString,String region,String airportcode,String frmSelectedDts,String flag,String employeeName,String sortedColumn,String pensionNo){
        ArrayList AAIEPFLst=new ArrayList();
        AAIEPFLst=epfformsReportDAO.AAIEPFForm2Report(pfidString,region,airportcode, frmSelectedDts, flag,employeeName, sortedColumn, pensionNo);
        return AAIEPFLst;
	}
	public ArrayList loadEPFForm3Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
		ArrayList form3List=new ArrayList();
		form3List=epfformsReportDAO.getEPFForm3Report(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
		return form3List;
	}
	public ArrayList loadEPFMissingTransPFIDs(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
		ArrayList form3List=new ArrayList();
		form3List=epfformsReportDAO.getMissingTransPFIDs(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
		return form3List;
	}
	
	public AAIEPFReportBean AAIEPFForm1Report(String pfidString,String region,String airportcode,String frmSelectedDts,String flag,String employeeName,String sortedColumn,String pensionNo){
		AAIEPFReportBean AAIEPFreportbean=new AAIEPFReportBean();
		AAIEPFreportbean=epfformsReportDAO.AAIEPFForm1Report(pfidString,region,airportcode, frmSelectedDts, flag,employeeName, sortedColumn, pensionNo);
	    return AAIEPFreportbean;
	}
	public AAIEPFReportBean AAIEPFForm8Report(String pfidString,String region,String airportcode,String frmSelectedDts,String flag,String employeeName,String sortedColumn,String pensionNo){
		 AAIEPFReportBean AAIEPFBean=new AAIEPFReportBean();
		 AAIEPFBean=epfformsReportDAO.AAIEPFForm8Report(pfidString,region,airportcode, frmSelectedDts, flag,employeeName, sortedColumn, pensionNo);
	        return AAIEPFBean;
	}
	
	public ArrayList loadEPFForm5Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
		ArrayList form3List=new ArrayList();
		form3List=epfformsReportDAO.getEPFForm5Report(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
		return form3List;
	}
	public ArrayList loadEPFForm6Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
		ArrayList form6List=new ArrayList();
		form6List=epfformsReportDAO.getEPFForm6Report(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
		return form6List;
	}
	public ArrayList loadEPFForm7Report(String region,String airprotcode,String frmSelctedYear,String sortingOrder){
		ArrayList form7List=new ArrayList();
		form7List=epfformsReportDAO.getEPFForm7Report(region, airprotcode, frmSelctedYear, sortingOrder);
		return form7List;
	}

	public ArrayList loadEPFForm6AReport(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
		ArrayList form3List=new ArrayList();
		form3List=epfformsReportDAO.getForm6AReport(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
		return form3List;
	}


	public ArrayList loadEPFForm11Report(String range,String region,String airprotcode,String empName,String empNameFlag,String frmSelctedYear,String sortingOrder,String frmPensionno){
			ArrayList form11List=new ArrayList();
			finReportDAO.finalUpdates();
			form11List=epfformsReportDAO.getEPFForm11Report(range, region, airprotcode, empName, empNameFlag, frmSelctedYear, sortingOrder,frmPensionno);
			return form11List;
		}
}
