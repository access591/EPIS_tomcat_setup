package com.epis.dao.advances;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epis.bean.admin.UserBean;
import com.epis.bean.advances.CPFPFWTransBean;
import com.epis.bean.advances.CPFPFWTransInfoBean;
import com.epis.common.exception.EPISException;
import com.epis.dao.admin.UserDAO;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;

public class CPFPFWTransInfo implements Constants{
	Log log = new Log(CPFPFWTransInfo.class);
	String userID,userStation,userRegion,userName,userSignName="";
	DBUtility commonDB = new DBUtility();
	CommonUtil commonUtil=new CommonUtil();
	public CPFPFWTransInfo(){
		super();
	}

	public CPFPFWTransInfo(String userID, String userName,String userStation, String userRegion,String userSigDispName) {
		super();
		// TODO Auto-generated constructor stub
		this.userID = userID;
		this.userName=userName;
		this.userStation = userStation;
		this.userRegion = userRegion;
		this.userSignName=userSigDispName;
	 
	}
//	By Radha On 10-Feb-2012 for updation of designation in inputter level
	public int createCPFPFWTrans(String pensionNo,String cpfpfwTransID,String transType,String cpfpfwTransCD,String designation) throws EPISException{
		int transCode=0,inserted=0;
		String sqlQuery="",transDescr="";
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		transDescr=transInfo[1];
		Connection con=null;
		Statement st=null;
		
		try {
			con = commonDB.getConnection();
			
			if(this.checkTransInfo(con,pensionNo,cpfpfwTransID,transCode).equals("X")){
				//Update
				sqlQuery="UPDATE EPIS_ADVANCES_TRANSACTIONS SET APPROVEDBY='"+this.userID+"',APRVDSIGNNAME='"+this.userSignName+"',APPROVEDDATE=SYSDATE WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD="+transCode;
			}else{
				//Insert
				sqlQuery="INSERT INTO EPIS_ADVANCES_TRANSACTIONS(TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,PURPOSETYPE,APPROVEDBY,APPROVEDDATE,APRVDSIGNNAME,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID,DESIGNATION) VALUES (EPISADVTRANSID.NEXTVAL,SYSDATE,'"+transCode+"','"+transDescr+"','"+transType+"','"+this.userID+"',SYSDATE,'"+this.userSignName+"','"+this.userStation+"','"+this.userRegion+"',"+pensionNo+","+cpfpfwTransID+",'"+designation+"')";
			}
			st=con.createStatement();
			log.info("createCPFPFWTrans================="+sqlQuery);
			inserted=st.executeUpdate(sqlQuery);
		}catch(EPISException se){
			throw se;
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(null, st, con);
		}
		return inserted;
	}
	//Regarding PFW
	//	Modified By Radha On 25-Jul-2011 for saving designation in transaction table
	public int createCPFPFWTrans(CPFPFWTransInfoBean transInfoBean,String pensionNo,String cpfpfwTransID,String designation,String transType,String cpfpfwTransCD) throws EPISException{
		int transCode=0,inserted=0;
		String sqlQuery="",transDescr="";
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		transDescr=transInfo[1];
		Connection con=null;
		Statement st=null;
		
		try {
			con = commonDB.getConnection();
			
			if(this.checkTransInfo(con,pensionNo,cpfpfwTransID,transCode).equals("X")){
				//Update
				sqlQuery="UPDATE EPIS_ADVANCES_TRANSACTIONS SET APPROVEDBY='"+this.userID+"',APRVDSIGNNAME='"+this.userSignName+"',APPROVEDDATE=SYSDATE,SUBSCRIPTIONAMOUNT="+transInfoBean.getSubscriptionAmt()+",CONTRIBUTIONAMOUNT="+transInfoBean.getContributionAmt()+",CPFFUND="+transInfoBean.getCpfFund()+",APPROVEDAMOUNT="+transInfoBean.getApprovedAmt()+",APPROVEDSUBSCRIPTIONAMT="+transInfoBean.getApprovedSubscrAmnt()+",APPROVEDCONTRIBUTIONAMT="+transInfoBean.getApprovedcontriAmnt()+
						" WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD="+transCode;
			}else{
				//Insert
				sqlQuery="INSERT INTO EPIS_ADVANCES_TRANSACTIONS(TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,PURPOSETYPE,SUBSCRIPTIONAMOUNT,CONTRIBUTIONAMOUNT,CPFFUND,APPROVEDAMOUNT,APPROVEDSUBSCRIPTIONAMT,APPROVEDCONTRIBUTIONAMT,APPROVEDBY,APPROVEDDATE,APRVDSIGNNAME,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID,DESIGNATION) VALUES " +
						"(EPISADVTRANSID.NEXTVAL,SYSDATE,'"+transCode+"','"+transDescr+"','"+transType+"','"+transInfoBean.getSubscriptionAmt()+"','"+transInfoBean.getContributionAmt()+"','"+transInfoBean.getCpfFund()+"','"+transInfoBean.getApprovedAmt()+"','"+transInfoBean.getApprovedSubscrAmnt()+"','"+transInfoBean.getApprovedcontriAmnt()+"','"+this.userID+"',SYSDATE,'"+this.userSignName+"','"+this.userStation+"','"+this.userRegion+"',"+pensionNo+","+cpfpfwTransID+",'"+designation+"')";
			}
			st=con.createStatement();
			log.info("createCPFPFWTrans================="+sqlQuery);
			inserted=st.executeUpdate(sqlQuery);
		}catch(EPISException se){
			throw se;
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(null, st, con);
		}
		return inserted;
	}
	private String getTranscd(String cpfpfwTransCD){
		int transcd=0;
		String description="";
		StringBuffer buffer=new StringBuffer();
		if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_CPF_CHECK_LIST)){
			transcd=1;
			description="CPF CHECK LIST APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_CPF_VERFICATION)){
			transcd=2;
			description="CPF VERFICATION APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_CPF_RECOMMENDATION)){
			transcd=3;
			description="CPF RECOMMENDATION APPROVED AT HEAD QUARTERS";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_CPF_APPROVAL)){
			transcd=4;
			description="CPF APPROVAL AT ED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_CPF_DELETE)){
			transcd=5;
			description="CPF DATA DELETED";	
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_PERSONAL_VERFICATION)){
			transcd=21;
			description="FINAL SETTLEMENT PERSONAL VERFICATION APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_PROCESS_FORM)){
			transcd=22;
			description="FINAL SETTLEMENT PROCESS FORM APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_SRMGR)){
			transcd=23;
			description="FINAL SETTLEMENT RECOMMENDATION FORM APPROVED BY SRMGR  AT REGION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_RECOMMENDATION_DGM)){
			transcd=24;
			description="FINAL SETTLEMENT RECOMMENDATION FORM APPROVED BY DGM AT HEAD QUARTERS";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_APPROVAL)){
			transcd=25;
			description="FINAL SETTLEMENT ED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FS_DELETE)){
			transcd=26;
			description="FINAL SETTLMENT DATA DELETED";	
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_CHECK_LIST)){
			transcd=31;
			description="PFW CHECK LIST APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_PART_II)){
			transcd=32;
			description="PFW PART-II VERFICATION APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_PART_III_SRMGR)){
			transcd=33;
			description="PFW PART-III VERFICATION APPROVED BY SRMGR AT REGION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_PART_IV_SECRETARY)){
			transcd=34;
			description="PFW PART-IV VERFICATION APPROVED BY SECRETARY AT HEAD QUARTERS";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_APPROVAL_ED)){
			transcd=35;
			description="PFW FINAL APPROVAL BY ED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_DELETE)){
			transcd=36;
			description="PFW DATA DELETED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_REVISED_VERIFICATION)){
			transcd=37;
			description="PFW REVISED VERFICATION APPROVED BY SRMGR AT REGION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_REVISED_RECOMMENDATION)){
			transcd=38;
			description="PFW REVISED VERFICATION APPROVED BY SECRETARY AT HEAD QUARTERS";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_PFW_REVISED_APPROVAL_ED)){
			transcd=39;
			description="PFW REVISED FINAL APPROVAL BY ED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_PROCESS_ARREAR_FORM)){
			transcd=41;
			description="FINAL SETTLEMENT ARREAR PROCESS FORM APPROVED AT STATION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_SRMGR)){
			transcd=42;
			description="FINAL SETTLEMENT ARREAR RECOMMENDATION FORM APPROVED BY SRMGR  AT REGION";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_ARREAR_RECOMMENDATION_DGM)){
			transcd=43;
			description="FINAL SETTLEMENT ARREAR RECOMMENDATION FORM APPROVED BY DGM AT HEAD QUARTERS";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FINAL_ARREAR_APPROVAL)){
			transcd=44;
			description="FINAL SETTLEMENT ARREAR ED";
		}else if(cpfpfwTransCD.equals(Constants.APPLICATION_PROCESSING_FSA_DELETE)){
			transcd=45;
			description="FINAL SETTLEMENT ARREAR DATA DELETED";
		}
		buffer.append(transcd);
		buffer.append(",");
		buffer.append(description);
		return buffer.toString();
	}
	private String checkTransInfo(Connection con,String pensionNo,String cpfpfwTransID,int transCD) throws EPISException{
		String checkInfo="";
		Statement st=null;
		ResultSet rs=null;
		String checkTransSelQuery="SELECT 'X' AS FLAG FROM EPIS_ADVANCES_TRANSACTIONS WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD="+transCD;
		log.info("checkTransInfo============"+checkTransSelQuery);
		try{
			st=con.createStatement();
			rs=st.executeQuery(checkTransSelQuery);
			if(rs.next()){
				checkInfo=rs.getString("FLAG");
			}
			
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(rs, st, null);
		}
		return checkInfo;
	}
	public ArrayList readTransInfo(String pensionNo,String cpfpfwTransID,String cpfpfwTransCD,String path) throws EPISException{
		int transCode=0;
		CPFPFWTransBean transBean=null;
		ArrayList detailTransList=new ArrayList();
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		String sqlQuery="";
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		sqlQuery="SELECT TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,APRVDSIGNNAME,PURPOSETYPE,SUBSCRIPTIONAMOUNT,CONTRIBUTIONAMOUNT,CPFFUND,APPROVEDAMOUNT,APPROVEDSUBSCRIPTIONAMT,APPROVEDCONTRIBUTIONAMT,APPROVEDBY,to_char(APPROVEDDATE,'dd/MM/yy') as APPROVEDDATE,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID,REMARKS,DESIGNATION  FROM EPIS_ADVANCES_TRANSACTIONS WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD<="+transCode+" ORDER BY TRANSCD";
		try{
			con=commonDB.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sqlQuery);
			while(rs.next()){
				transBean=new CPFPFWTransBean();
				if(rs.getString("TRANSNO")!=null){
					transBean.setTransNo(rs.getString("TRANSNO"));
				}else{
					transBean.setTransNo("---");
				}
				
				if(rs.getString("TRANSDATE")!=null){
					transBean.setTransDate(commonUtil.converDBToAppFormat(rs.getDate("TRANSDATE"),"dd-MMM-yyyy"));
				}else{
					transBean.setTransDate("---");
				}
		
				if(rs.getString("TRANSCD")!=null){
					transBean.setTransCode(rs.getString("TRANSCD"));
				}else{
					transBean.setTransCode("---");
				}
				if(rs.getString("APRVDSIGNNAME")!=null){
					transBean.setTransDispSignName(rs.getString("APRVDSIGNNAME"));
				}else{
					transBean.setTransDispSignName("---");
				}
				if(rs.getString("TRANSDESCRIPTION")!=null){
					transBean.setTransDesc(rs.getString("TRANSDESCRIPTION"));
				}else{
					transBean.setTransDesc("---");
				}
				
				if(rs.getString("PURPOSETYPE")!=null){
					transBean.setTransPurposeType(rs.getString("PURPOSETYPE"));
				}else{
					transBean.setTransPurposeType("---");
				}
				
				if(rs.getString("APPROVEDBY")!=null){
					transBean.setTransApprovedBy(rs.getString("APPROVEDBY"));
				}else{
					transBean.setTransApprovedBy("---");
				}
				
				if(rs.getString("APPROVEDDATE")!=null){
					transBean.setTransApprovedDate(rs.getString("APPROVEDDATE"));
				}else{
					transBean.setTransApprovedDate("---");
				}
				
				if(rs.getString("AIRPORTCODE")!=null){
					transBean.setTransAirportcode(rs.getString("AIRPORTCODE"));
				}else{
					transBean.setTransAirportcode("---");
				}
				
				if(rs.getString("REGION")!=null){
					transBean.setTransRegion(rs.getString("REGION"));
				}else{
					transBean.setTransRegion("---");
				}
				
				if(rs.getString("PENSIONNO")!=null){
					transBean.setTransPensionNo(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
				}else{
					transBean.setTransPensionNo("---");
				}
				log.info("ssssssssssssssssssssss"+transBean.getTransApprovedBy());
				if(!transBean.getTransApprovedBy().equals("---")){
					transBean.setTransDigitalSign(this.readUserSignatures(path,transBean.getTransApprovedBy()));	
				}else{
					transBean.setTransDigitalSign("");
				}
				
				if(rs.getString("CPFPFWTRANSID")!=null){	
					transBean.setTransCPFPFWID(rs.getString("CPFPFWTRANSID"));
				}else{
					transBean.setTransCPFPFWID("---");
				}
			
				if(rs.getString("SUBSCRIPTIONAMOUNT")!=null){	
					transBean.setTransSubscriptionAmt(rs.getString("SUBSCRIPTIONAMOUNT"));
				}else{
					transBean.setTransSubscriptionAmt("0");
				}
				
				if(rs.getString("CONTRIBUTIONAMOUNT")!=null){	
					transBean.setTransContributionAmt(rs.getString("CONTRIBUTIONAMOUNT"));
				}else{
					transBean.setTransContributionAmt("0");
				}
				
				if(rs.getString("CPFFUND")!=null){	
					transBean.setTransCPFFund(rs.getString("CPFFUND"));
				}else{
					transBean.setTransCPFFund("0");
				}
				
				if(rs.getString("APPROVEDAMOUNT")!=null){	
					transBean.setTransApprovedAmt(rs.getString("APPROVEDAMOUNT"));
				}else{
					transBean.setTransApprovedAmt("0");
				}
				if(rs.getString("APPROVEDSUBSCRIPTIONAMT")!=null){	
					transBean.setApprovedTransSubscriAmt(rs.getString("APPROVEDSUBSCRIPTIONAMT"));
				}else{
					transBean.setApprovedTransSubscriAmt("0");
				}
				if(rs.getString("APPROVEDCONTRIBUTIONAMT")!=null){	
					transBean.setApprovedTransContrAmt(rs.getString("APPROVEDCONTRIBUTIONAMT"));
				}else{
					transBean.setApprovedTransContrAmt("0");
				}
				if(rs.getString("DESIGNATION")!=null){	
					transBean.setDesignation(rs.getString("DESIGNATION"));
				}else{
					transBean.setDesignation("---");
				}
				
				if(transBean.getDesignation().equals("---") && !(transBean.getTransCode().equals("---"))){
					if(transBean.getTransCode().equals("22")){
						transBean.setDesignation(Constants.APPLICATION_FINAL_SETTLEMENT_PROCESS_FORM_APPROVAL_DESIGNATION);
					}else if(transBean.getTransCode().equals("23")){
						transBean.setDesignation(Constants.APPLICATION_FINAL_SETTLEMENT_RECOMMENDATION_FORM_APPROVAL_DESIGNATION);
						
					}else if(transBean.getTransCode().equals("24")){ 
						transBean.setDesignation(Constants.APPLICATION_FINAL_SETTLEMENT_VERIFIVATION_FORM_APPROVAL_DESIGNATION);
						
					}else if(transBean.getTransCode().equals("25")){
						transBean.setDesignation(Constants.APPLICATION_FINAL_SETTLEMENT_APPROVAL_FORM_APPROVAL_DESIGNATION_FOR_OLD_RECORDS);
						
					}else{
						transBean.setDesignation("---");
					}
				}
				if(rs.getString("REMARKS")!=null){	
					transBean.setRemarks(rs.getString("REMARKS"));
				}else{
					transBean.setRemarks("---");
				}
				detailTransList.add(transBean);
			}
			
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(rs, st, con);
		}
		
		return detailTransList;
	}
	public CPFPFWTransBean readTransInfo(String pensionNo,String cpfpfwTransID,String cpfpfwTransCD) throws EPISException{
		int transCode=0;
		CPFPFWTransBean transBean=new CPFPFWTransBean();
		ArrayList detailTransList=new ArrayList();
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		String sqlQuery="";
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		sqlQuery="SELECT TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,APRVDSIGNNAME,PURPOSETYPE,NVL(SUBSCRIPTIONAMOUNT,'0.00') as SUBSCRIPTIONAMOUNT,NVL(CONTRIBUTIONAMOUNT,'0.00') as CONTRIBUTIONAMOUNT,NVL(CPFFUND,'0.00') as CPFFUND,NVL(APPROVEDAMOUNT,'0.00') as APPROVEDAMOUNT,NVL(APPROVEDSUBSCRIPTIONAMT,'0.00') as APPROVEDSUBSCRIPTIONAMT,NVL(APPROVEDCONTRIBUTIONAMT,'0.00') as APPROVEDCONTRIBUTIONAMT,APPROVEDBY,to_char(APPROVEDDATE,'dd/MM/yy') as APPROVEDDATE,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID,REMARKS FROM EPIS_ADVANCES_TRANSACTIONS WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD="+transCode+" ORDER BY TRANSCD";
		log.info("readTransInfo:sqlQuery"+sqlQuery);
		try{
			con=commonDB.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(sqlQuery);
			if(rs.next()){
				
				if(rs.getString("TRANSNO")!=null){
					transBean.setTransNo(rs.getString("TRANSNO"));
				}else{
					transBean.setTransNo("---");
				}
				
				if(rs.getString("TRANSDATE")!=null){
					transBean.setTransDate(commonUtil.converDBToAppFormat(rs.getDate("TRANSDATE"),"dd-MMM-yyyy"));
				}else{
					transBean.setTransDate("---");
				}
		
				if(rs.getString("TRANSCD")!=null){
					transBean.setTransCode(rs.getString("TRANSCD"));
				}else{
					transBean.setTransCode("---");
				}
				if(rs.getString("APRVDSIGNNAME")!=null){
					transBean.setTransDispSignName(rs.getString("APRVDSIGNNAME"));
				}else{
					transBean.setTransDispSignName("---");
				}
				if(rs.getString("TRANSDESCRIPTION")!=null){
					transBean.setTransDesc(rs.getString("TRANSDESCRIPTION"));
				}else{
					transBean.setTransDesc("---");
				}
				
				if(rs.getString("PURPOSETYPE")!=null){
					transBean.setTransPurposeType(rs.getString("PURPOSETYPE"));
				}else{
					transBean.setTransPurposeType("---");
				}
				
				if(rs.getString("APPROVEDBY")!=null){
					transBean.setTransApprovedBy(rs.getString("APPROVEDBY"));
				}else{
					transBean.setTransApprovedBy("---");
				}
				
				if(rs.getString("APPROVEDDATE")!=null){
					transBean.setTransApprovedDate(rs.getString("APPROVEDDATE"));
				}else{
					transBean.setTransApprovedDate("---");
				}
				if(rs.getString("REMARKS")!=null){
					transBean.setRemarks(rs.getString("REMARKS"));
				}else{
					transBean.setRemarks("---");
				}
				if(rs.getString("AIRPORTCODE")!=null){
					transBean.setTransAirportcode(rs.getString("AIRPORTCODE"));
				}else{
					transBean.setTransAirportcode("---");
				}
				
				if(rs.getString("REGION")!=null){
					transBean.setTransRegion(rs.getString("REGION"));
				}else{
					transBean.setTransRegion("---");
				}
				
				if(rs.getString("PENSIONNO")!=null){
					transBean.setTransPensionNo(commonUtil.leadingZeros(5,rs.getString("PENSIONNO")));
				}else{
					transBean.setTransPensionNo("---");
				}
				
				if(rs.getString("CPFPFWTRANSID")!=null){	
					transBean.setTransCPFPFWID(rs.getString("CPFPFWTRANSID"));
				}else{
					transBean.setTransCPFPFWID("---");
				}
			
				if(rs.getString("SUBSCRIPTIONAMOUNT")!=null){	
					transBean.setTransSubscriptionAmt(rs.getString("SUBSCRIPTIONAMOUNT"));
				}else{
					transBean.setTransSubscriptionAmt("0");
				}
				
				if(rs.getString("CONTRIBUTIONAMOUNT")!=null){	
					transBean.setTransContributionAmt(rs.getString("CONTRIBUTIONAMOUNT"));
				}else{
					transBean.setTransContributionAmt("0");
				}
				
				if(rs.getString("CPFFUND")!=null){	
					transBean.setTransCPFFund(rs.getString("CPFFUND"));
				}else{
					transBean.setTransCPFFund("0");
				}
				
				if(rs.getString("APPROVEDAMOUNT")!=null){	
					transBean.setTransApprovedAmt(rs.getString("APPROVEDAMOUNT"));
				}else{
					transBean.setTransApprovedAmt("0");
				}
				if(rs.getString("APPROVEDSUBSCRIPTIONAMT")!=null){	
					transBean.setApprovedTransSubscriAmt(rs.getString("APPROVEDSUBSCRIPTIONAMT"));
				}else{
					transBean.setApprovedTransSubscriAmt("0");
				}
				if(rs.getString("APPROVEDCONTRIBUTIONAMT")!=null){	
					transBean.setApprovedTransContrAmt(rs.getString("APPROVEDCONTRIBUTIONAMT"));
				}else{
					transBean.setApprovedTransContrAmt("0");
				}
				detailTransList.add(transBean);
			}
			
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(rs, st, con);
		}
		
		return transBean;
	}
	private String readUserSignatures(String path,String approvedBy) throws EPISException{
		UserBean userBean=new UserBean();
		String finalPath="",signatureName="";
		try {
			userBean=this.readUserInfo(approvedBy);
			finalPath=path+userBean.getEsignatoryName();
			log.info("finalPath----"+finalPath);
			UserDAO.getInstance().getImage(finalPath,userBean.getUserName());
			signatureName=userBean.getEsignatoryName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return signatureName;
	}
	//	For Advances
	//Modified By Radha On 20-Jul-2011 for saving designation in transaction table
	public int createCPFPFWTrans(String pensionNo,String cpfpfwTransID,String designation,String transType,String cpfpfwTransCD,CPFPFWTransInfoBean transInfoBean) throws EPISException{
		int transCode=0,inserted=0;
		String sqlQuery="",transDescr="";
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		transDescr=transInfo[1];
		Connection con=null;
		Statement st=null;
		
				
		try {
			con = commonDB.getConnection();
			
			if(this.checkTransInfo(con,pensionNo,cpfpfwTransID,transCode).equals("X")){
				//Update
				sqlQuery="UPDATE EPIS_ADVANCES_TRANSACTIONS SET APPROVEDBY='"+this.userID+"',APRVDSIGNNAME='"+this.userSignName+"',APPROVEDDATE=SYSDATE,TOTALINATALLMENTS='"+transInfoBean.getTotalInst()+"',INTERESTINSTALLMENTS='"+transInfoBean.getInterestinstallments()+"',INTERESTINSTALLAMT='"+transInfoBean.getIntinstallmentamt()+"',MTHINSTALLMENTAMT='"+transInfoBean.getMthinstallmentamt()+"',RECOMMENDEDAMT='"+transInfoBean.getAmntRecommended()+"'  WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+cpfpfwTransID+" AND TRANSCD="+transCode;
			}else{
				//Insert
				sqlQuery="INSERT INTO EPIS_ADVANCES_TRANSACTIONS(TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,PURPOSETYPE,APPROVEDBY,APPROVEDDATE,APRVDSIGNNAME,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID,TOTALINATALLMENTS,INTERESTINSTALLMENTS,INTERESTINSTALLAMT,MTHINSTALLMENTAMT,RECOMMENDEDAMT,DESIGNATION) VALUES (EPISADVTRANSID.NEXTVAL,SYSDATE,'"+transCode+"','"+transDescr+"','"+transType+"','"+this.userID+"',SYSDATE,'"+this.userSignName+"','"+this.userStation+"','"+this.userRegion+"',"+pensionNo+","+cpfpfwTransID+",'"+transInfoBean.getTotalInst()+"','"+transInfoBean.getInterestinstallments()+"','"+transInfoBean.getIntinstallmentamt()+"','"+transInfoBean.getMthinstallmentamt()+"','"+transInfoBean.getAmntRecommended()+"','"+designation+"')";
			}
			
			log.info("CPF  createCPFPFWTrans() sqlQuery ---- "+sqlQuery); 
			st=con.createStatement();
			inserted=st.executeUpdate(sqlQuery);
		}catch(EPISException se){
			throw se;
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(null, st, con);
		}
		return inserted;
	}
	 public int deleteCPFPFWTrans(String pensionNo,String transId,String transType,String cpfpfwTransCD,String verfiedBy) throws EPISException{
			int transCode=0,inserted=0;
			String sqlQuery="",transDescr="";
			String getTransInfo=this.getTranscd(cpfpfwTransCD);
			String[] transInfo= getTransInfo.split(",");
			transCode=Integer.parseInt(transInfo[0]);
			transDescr=transInfo[1];
			Connection con=null;
			Statement st=null;
			
					
			try {
				con = commonDB.getConnection();
				sqlQuery="INSERT INTO EPIS_ADVANCES_TRANSACTIONS(TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,PURPOSETYPE,REMARKS,APPROVEDBY,APPROVEDDATE,APRVDSIGNNAME,AIRPORTCODE,REGION,PENSIONNO,CPFPFWTRANSID) VALUES (EPISADVTRANSID.NEXTVAL,SYSDATE,'"+transCode+"','"+transDescr+"','"+transType+"','"+verfiedBy+"','"+this.userID+"',SYSDATE,'"+this.userSignName+"','"+this.userStation+"','"+this.userRegion+"',"+pensionNo+","+transId+")";
				log.info("deleteCPFPFWTrans sqlQuery ---- "+sqlQuery); 
				st=con.createStatement();
				inserted=st.executeUpdate(sqlQuery);
			}catch(EPISException se){
				throw se;
			} catch (SQLException e) {
				throw new EPISException(e);
			}finally{
				commonDB.closeConnection(null, st, con);
			}
			return inserted;
		}
	private UserBean readUserInfo(String approvedBy) throws EPISException{
		UserBean userBean=new  UserBean();
		try {
			log.info("readUserInfo::approvedBy"+approvedBy);
			userBean=UserDAO.getInstance().getUser(approvedBy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		}
		return userBean;
	}
//	 New Method

//	Regarding  Final Settlement

	public int createFSFSATrans(String pensionNo,CPFPFWTransInfoBean transInfoBean,String nsSanctionNo,String transType,String cpfpfwTransCD,String designation) throws EPISException{
		int transCode=0,inserted=0;
		String sqlQuery="",transDescr="",remarks="";
		String getTransInfo=this.getTranscd(cpfpfwTransCD);
		String[] transInfo= getTransInfo.split(",");
		transCode=Integer.parseInt(transInfo[0]);
		transDescr=transInfo[1];
		Connection con=null;
		Statement st=null;
		//Regarding the Resignation cases we need not store the resignation remarks.
/*		if(transInfoBean.getSeperationreason().equals("Resignation")){
			remarks=transInfoBean.getSeperationremarks();
		}else{*/
		/*}*/
		if(transInfoBean.getRemarksFlag().equals("Y")){
			 remarks=transInfoBean.getTransremarks();
		 }else{
			 remarks="";
		 }
		
		
		try {
			con = commonDB.getConnection();
			
			if(this.checkTransInfo(con,pensionNo,nsSanctionNo,transCode).equals("X")){
				//Update
				sqlQuery="UPDATE EPIS_ADVANCES_TRANSACTIONS SET REMARKS='"+remarks+"',APPROVEDBY='"+this.userID+"',APRVDSIGNNAME='"+this.userSignName+"',APPROVEDDATE=SYSDATE,SUBSCRIPTIONAMOUNT="+transInfoBean.getEmplshare()+",CONTRIBUTIONAMOUNT="+transInfoBean.getEmplrshare()+",LESSPENSIONCONTRIBUTION="+transInfoBean.getPensioncontribution()+",NETCONTRIBUTION="+transInfoBean.getNetcontribution()+
					" WHERE PENSIONNO="+pensionNo+" AND CPFPFWTRANSID="+nsSanctionNo+" AND TRANSCD="+transCode;
			}else{
				//Insert
				sqlQuery="INSERT INTO EPIS_ADVANCES_TRANSACTIONS(TRANSNO,TRANSDATE,TRANSCD,TRANSDESCRIPTION,PURPOSETYPE,APPROVEDBY,APPROVEDDATE,APRVDSIGNNAME,AIRPORTCODE,REGION,SUBSCRIPTIONAMOUNT,CONTRIBUTIONAMOUNT,LESSPENSIONCONTRIBUTION,NETCONTRIBUTION,REMARKS,PENSIONNO,CPFPFWTRANSID,DESIGNATION) VALUES " +
				"(EPISADVTRANSID.NEXTVAL,SYSDATE,'"+transCode+"','"+transDescr+"','"+transType+"','"+this.userID+"',SYSDATE,'"+this.userSignName+"','"+this.userStation+"','"+this.userRegion+"','"+transInfoBean.getEmplshare()+"','"+transInfoBean.getEmplrshare() +"','"+transInfoBean.getPensioncontribution()+"','"+transInfoBean.getNetcontribution()+"','"+remarks+"',"+pensionNo+","+nsSanctionNo+",'"+designation+"')";
			}
			
			log.info("==========In createFSFSATrans Query===========" + sqlQuery);
			
			st=con.createStatement();
			inserted=st.executeUpdate(sqlQuery);
		}catch(EPISException se){
			throw se;
		} catch (SQLException e) {
			throw new EPISException(e);
		}finally{
			commonDB.closeConnection(null, st, con);
		}
		return inserted;
	}
	 
	//By Radha On 27-Jul-2011  for Displaying Designation and Signatures dynamically in Reports
	public UserBean readUserSignaturesforreports(String path,String approvedBy) throws EPISException{
		UserBean userBean=new UserBean();
		String finalPath="",signatureName="";
		try {
			userBean=this.readUserInfo(approvedBy);
			finalPath=path+userBean.getEsignatoryName();
			log.info("finalPath----"+finalPath);
			UserDAO.getInstance().getImage(finalPath,userBean.getUserName());
			signatureName=userBean.getEsignatoryName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EPISException(e);
		} catch (EPISException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return userBean;
	}
}
