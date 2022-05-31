package com.epis.services;

import java.util.ArrayList;

import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.CommonDAO;

public class CommonService {
	CommonDAO commonDAO=new CommonDAO();
    public ArrayList  loadPersonalTransInfo(String employeeName,String region,String pensionNo,String cpfaccno,String monthYear){
		ArrayList list=new ArrayList();
		list=commonDAO.loadPersonalTransInfo(employeeName,region,pensionNo,cpfaccno,monthYear);
		return list;
	}

    public AdvanceBasicBean  checkSanctionDate(String pensionNo){
		return commonDAO.checkSanctionDate(pensionNo);
		
	}
    public String  lookforMonth(String pensionNo ,String currentDate) throws EPISException{
		String monthInfo=commonDAO.lookforMonth(pensionNo,currentDate);
		return monthInfo;
	}
    public String  lookforMonth(String pensionNo ,String currentDate,boolean flag) throws EPISException{
		String monthInfo=commonDAO.lookforMonth(pensionNo,currentDate,flag);
		return monthInfo;
	}
    public String  loadNomineeDetails(String employeeName,String region,String pensionNo,String monthYear){
		String nomineeInfo=commonDAO.loadNomineeDetails(employeeName,region,pensionNo,monthYear);
		return nomineeInfo;
	}
	public ArrayList  loadPersonalTransactionInfo(String employeeName,String region,String pensionNo,String cpfaccno,String monthYear){
		ArrayList list=new ArrayList();
		list=commonDAO.loadPersonalTransactionInfo(employeeName,region,pensionNo,cpfaccno,monthYear);
		return list;
	}
	public ArrayList  loadNomineeInfo(String employeeName,String region,String pensionNo,String monthYear){
		ArrayList list=new ArrayList();
		list=commonDAO.loadNomineeInfo(employeeName,region,pensionNo,monthYear);
		return list;
	}
	public AdvanceBasicBean  loadNoteSheetInfo(String pensionNo,String sanctionNo){
		return commonDAO.loadNoteSheetInfo(pensionNo,sanctionNo);
		
	}
	public ArrayList loadStations(AdvanceBasicBean advanceBean){
		return commonDAO.loadStations(advanceBean);
	}
	public ArrayList  loadDeathCaseNomineeInfo(String pensionNo,String transId){
		ArrayList list=new ArrayList();
		list=commonDAO.loadDeathCaseNomineeInfo(pensionNo,transId);
		return list;
	}
}
