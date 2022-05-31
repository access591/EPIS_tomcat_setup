package com.epis.dao.investment;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.epis.bean.investment.FormFillingAppBean;
import com.epis.bean.investment.FormFillingBean;
import com.epis.bean.investment.InvestProposalAppBean;
import com.epis.bean.investment.InvestmentProposalBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;


public class FormFillingDAO {
	Connection con=null;
	ResultSet rs=null;
	ResultSet rs1=null;
	Statement st=null;
	PreparedStatement pstmt=null;
	PreparedStatement prepare=null;
	Log log=new Log(FormFillingDAO.class);
	
	private FormFillingDAO()
	{
		
	}
	private static final FormFillingDAO formfillingdao=new FormFillingDAO();
	public static FormFillingDAO getInstance()
	{
		return formfillingdao;
	}
	
	public List searchFormFilling(FormFillingBean bean)throws ServiceNotAvailableException, EPISException
	{
		List list =new ArrayList();;
		try{
			FormFillingBean fillingbean =null;
			String query="";
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			if(bean.getMode().equals("rbiauction"))
				query="select FORMCD,formfill.PROPOSAL_REF_NO,formfill.TRUSTTYPE,formfill.CATEGORYCD,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','RB','RBI OIL BONDS','O','OpenBid') MARKET_TYPE,formfill.SECURITY_NAME,formfill.NO_OF_BONDS,formfill.AMT_INV,decode(STATUE_OF_TAXOPTION,'E','Exempted','N','NON-Exempted')STATUE_OF_TAXOPTION,NAME_OF_APPLICATION,PANNO,MAILING_ADDRESS,CONTACT_PERSON,CONTACT_NO,Nvl(APPROVED_1,'N')||'|'||Nvl(APPROVED_2,'N') flags,(case when (select count(*) from invest_confirmation_company data where data.PROPOSAL_REF_NO||data.security_name = formfill.PROPOSAL_REF_NO||formfill.security_name )>0 then 'Y' else 'N' end) qdata,Decode('A',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status from invest_formfilling formfill where formfill.CATEGORYCD<>'PSU' AND MARKET_TYPE<>'P'  and  FORMCD is not null  ";
			else
			query = "select FORMCD,formfill.PROPOSAL_REF_NO,formfill.TRUSTTYPE,formfill.CATEGORYCD,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','RB','RBI OIL BONDS','O','OpenBid') MARKET_TYPE,formfill.SECURITY_NAME,nvl(to_char(SETTLEMENT_DATE,'dd/Mon/yyyy'),'--')SETTLEMENT_DATE,formfill.NO_OF_BONDS,formfill.AMT_INV,decode(STATUE_OF_TAXOPTION,'E','Exempted','N','NON-Exempted')STATUE_OF_TAXOPTION,NAME_OF_APPLICATION,PANNO,MAILING_ADDRESS,CONTACT_PERSON,CONTACT_NO,Nvl(APPROVED_1,'N')||'|'||Nvl(APPROVED_2,'N') flags,(case when (select count(*) from invest_bankletter data where data.PROPOSAL_REF_NO = formfill.PROPOSAL_REF_NO )>0 then 'Y' else 'N' end) qdata,Decode('A',APPROVED_2,'Approved_2',APPROVED_1,'Approved_1','Not Approved') status from invest_formfilling formfill,investment_register register where formfill.CATEGORYCD='PSU' AND MARKET_TYPE='P' and formfill.proposal_ref_no=register.proposal_ref_no(+) AND FORMCD is not null  ";
			
			
					
			
			if(!bean.getSecurityName().equals(" ")){
				query +=" and upper(formfill.SECURITY_NAME) like upper('"+bean.getSecurityName()+"%')";
			}
			if(!bean.getProposalRefNo().equals(" ")){
				query +=" and upper(formfill.PROPOSAL_REF_NO) like upper('"+bean.getProposalRefNo()+"%')";
			}
			if(!bean.getTrustType().equals(" ")){
				query +=" and upper(formfill.TRUSTTYPE) like upper('"+bean.getTrustType()+"%')";
			}
			if(!bean.getSecurityCategory().equals("")){
				query +=" and upper(formfill.CATEGORYCD) like upper('"+bean.getSecurityCategory()+"%')";
			}
			if(!bean.getNameofApplicant().equals(" ")&&bean.getMode().equals("PSUPrimary")){
				query +=" and upper(NAME_OF_APPLICATION) like upper('"+bean.getNameofApplicant()+"%')";
			}
			if(bean.getMode().equals("rbiauction"))
				query +="and formfill.CATEGORYCD IN('SDL','GOI' )AND MARKET_TYPE IN('R','RB')";
			
			query +=" order by formfill.CREATED_DT DESC";
			log.info("FormFillingSearchDAO:searchFormFilling(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				fillingbean = new FormFillingBean();
				fillingbean.setFormCd(StringUtility.checknull(rs.getString("FORMCD")));
				fillingbean.setProposalRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
				fillingbean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
				fillingbean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
				fillingbean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
				fillingbean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
				if(bean.getMode().equals("psuprimary"))
				fillingbean.setSettlementDate(StringUtility.checknull(rs.getString("SETTLEMENT_DATE")));
				
				fillingbean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
				fillingbean.setInvestAmt(StringUtility.checknull(rs.getString("AMT_INV")));
				fillingbean.setStatueoftaxOption(StringUtility.checknull(rs.getString("STATUE_OF_TAXOPTION")));
				fillingbean.setNameofApplicant(StringUtility.checknull(rs.getString("NAME_OF_APPLICATION")));
				fillingbean.setPanno(StringUtility.checknull(rs.getString("PANNO")));
				fillingbean.setMailingAddress(StringUtility.checknull(rs.getString("MAILING_ADDRESS")));
				fillingbean.setContactPerson(StringUtility.checknull(rs.getString("CONTACT_PERSON")));
				fillingbean.setContactNumber(StringUtility.checknull(rs.getString("CONTACT_NO")));
				fillingbean.setHasQuotations(StringUtility.checknull(rs.getString("QDATA")));
				fillingbean.setFlags(StringUtility.checknull(rs.getString("flags")));
				fillingbean.setStatus(StringUtility.checknull(rs.getString("status")));
				list.add(fillingbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("FormFillingDAO:searchFormFilling:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("FormFillingDAO:searchFormFilling:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	
	public String getFinYear(String refNo)
	{
		String finYear="";
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
		String query = " select amount,Financialyear from Invest_Fundaccured acc,INVEST_PROPOSAL prop,invest_trusttype trt WHERE prop.ref_no " +
		" ='"+refNo+"' and Financialyear = (case when PROPOSALDATE >= '01/Apr/' || to_char " +
		" (PROPOSALDATE, 'YYYY') then to_char(PROPOSALDATE, 'YYYY') ||'-'|| to_char(to_number( " +
		" to_char(PROPOSALDATE, 'YY')) + 1) else to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) " +
		" - 1)||'-'|| to_char(PROPOSALDATE, 'YY') end) and trt.trusttype = acc.trusttype and prop.trustcd = trt.trustcd"; 
		rs = DBUtility.getRecordSet(query,st);
		if(rs.next()){	
			
			finYear = rs.getString("Financialyear");
		}else {
	
			SQLHelper sql = new SQLHelper();
			finYear = sql.getDescription("INVEST_PROPOSAL","(case when PROPOSALDATE >= '01/Apr/' || to_char " +
		" (PROPOSALDATE, 'YYYY') then to_char(PROPOSALDATE, 'YYYY') ||'-'|| to_char(to_number( " +
		" to_char(PROPOSALDATE, 'YY')) + 1) else to_char(to_number(to_char(PROPOSALDATE, 'YYYY')) " +
		" - 1)||'-'|| to_char(PROPOSALDATE, 'YY') end) Financialyear","ref_no",refNo,DBUtility.getConnection());
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {			
			DBUtility.closeConnection(rs,st,con);			
			}
		return finYear;
	}
	
	
	public void saveFormFilling(FormFillingBean bean)
	throws ServiceNotAvailableException, EPISException {

PreparedStatement pstmt = null;
try {
	con = DBUtility.getConnection();
	if (con != null) {
		String saveQry = "insert into invest_formfilling(FORMCD, PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD," +
				"MARKET_TYPE,SECURITY_NAME,NO_OF_BONDS,AMT_INV,STATUE_OF_TAXOPTION,NAME_OF_APPLICATION,PANNO,MAILING_ADDRESS,CONTACT_PERSON,CONTACT_NO,PATH,FAX_NUMBER,ARRANGER_DATE,AUCTION_DATE,CREATED_BY,ACCOUNTNO,PARTY_NAME,PARTY_CONTACTNO,PARTY_ADDRESS,PNAME,PID,CLIENTID,CREATED_DT) values " +
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		String updateQry="update invest_proposal set AMT_INV='"+bean.getInvestAmt()+"' where REF_NO='"+bean.getProposalRefNo()+"'";
		if(!bean.getMode().equals("rbiauction"))
		DBUtility.executeUpdate(updateQry);
		
		String FormCd =AutoGeneration.getNextCode("invest_formfilling","FORMCD",5,con);
		if(!bean.getExtName().equals(""))
		{
		FormFile  myform=bean.getUploadDocument();
		String contentType=myform.getContentType();
		String filename=myform.getFileName();
		byte filedata[]=myform.getFileData();
		
		if(!filename.equals("")){  
	        
	        //Create file
	        File fileToCreate = new File(bean.getFilePath(), FormCd+bean.getExtName());
	        //If file does not exists create file                      
	        if(!fileToCreate.exists()){
	          FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
	          fileOutStream.write(myform.getFileData());
	          fileOutStream.flush();
	          fileOutStream.close();
	        }
		}
		}
		
		pstmt = con.prepareStatement(saveQry);
		
		if (pstmt != null) {
			pstmt.setString(1, StringUtility
					.checknull(FormCd));
			pstmt.setString(2, StringUtility.checknull(bean.getProposalRefNo()));
			pstmt.setString(3, StringUtility.checknull(bean.getTrustType()));
			pstmt.setString(4, StringUtility.checknull(bean.getSecurityCategory()));
			pstmt.setString(5, StringUtility.checknull(bean.getMarketType()));
			pstmt.setString(6, StringUtility.checknull(bean.getSecurityName()));
			pstmt.setString(7, StringUtility.checknull(bean.getNoofBonds()));
			pstmt.setString(8, StringUtility.checknull(bean.getInvestAmt()));
			pstmt.setString(9, StringUtility.checknull(bean.getStatueoftaxOption()));
			pstmt.setString(10, StringUtility.checknull(bean.getNameofApplicant()));
			pstmt.setString(11, StringUtility.checknull(bean.getPanno()));
			pstmt.setString(12, StringUtility.checknull(bean.getMailingAddress()));
			pstmt.setString(13, StringUtility.checknull(bean.getContactPerson()));
			pstmt.setString(14, StringUtility.checknull(bean.getContactNumber()));
			pstmt.setString(15,StringUtility.checknull(bean.getExtName()));
			pstmt.setString(16,StringUtility.checknull(bean.getFaxNumber()));
			pstmt.setString(17,StringUtility.checknull(bean.getArrangerDate()));
			pstmt.setString(18,StringUtility.checknull(bean.getAuctionDate()));
			pstmt.setString(19, StringUtility.checknull(bean.getLoginUserId()));
			pstmt.setString(20,StringUtility.checknull(bean.getAccountNo()));
			pstmt.setString(21,StringUtility.checknull(bean.getPartyName()));
			pstmt.setString(22,StringUtility.checknull(bean.getPartyContactNo()));
			pstmt.setString(23,StringUtility.checknull(bean.getPartyAddress()));
			pstmt.setString(24,StringUtility.checknull(bean.getPname()));
			pstmt.setString(25,StringUtility.checknull(bean.getPid()));
			pstmt.setString(26,StringUtility.checknull(bean.getClientId()));
			
			
		
			pstmt.executeUpdate();
		} else {
			throw new ServiceNotAvailableException();
		}
	} else {
		throw new ServiceNotAvailableException();
	}
}
	catch (ServiceNotAvailableException snex) {
	throw snex;
} catch (SQLException sqlex) {
	throw new EPISException(sqlex);
} catch (Exception e) {
	throw new EPISException(e);
} finally {
	DBUtility.closeConnection(null, pstmt, con);
}
}
	public FormFillingBean findFormFill(FormFillingBean bean)throws ServiceNotAvailableException, EPISException
	{
			FormFillingBean formfilling=new FormFillingBean();
		try{
			con = DBUtility.getConnection();
			if(con!=null)
			{
				String query="SELECT  FORMCD,PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,decode(MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','O','OpenBid') MARKET_TYPE,SECURITY_NAME,NO_OF_BONDS,AMT_INV,STATUE_OF_TAXOPTION,NAME_OF_APPLICATION,PANNO,MAILING_ADDRESS,CONTACT_PERSON,CONTACT_NO,PATH,nvl(FAX_NUMBER,'')FAX_NUMBER,nvl(to_char(ARRANGER_DATE,'dd/Mon/yyyy'),'')ARRANGER_DATE,nvl(to_char(AUCTION_DATE,'dd/Mon/yyyy'),'')AUCTION_DATE,nvl(ACCOUNTNO,'')ACCOUNTNO,nvl(PARTY_NAME,' ')PARTY_NAME,nvl(PARTY_CONTACTNO,' ')PARTY_CONTACTNO,nvl(PARTY_ADDRESS,'')PARTY_ADDRESS,nvl(PNAME,'')PNAME,nvl(PID,'')PID,nvl(CLIENTID,'')CLIENTID FROM invest_formfilling WHERE FORMCD='"+bean.getFormCd()+"'";
				st=con.createStatement();
				rs=DBUtility.getRecordSet(query,st);
				if(rs.next())
				{
					formfilling.setFormCd(StringUtility.checknull(rs.getString("FORMCD")));
					formfilling.setProposalRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
					formfilling.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
					formfilling.setSecurityCategory(StringUtility.checknull("CATEGORYCD"));
					formfilling.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
					formfilling.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					formfilling.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
					formfilling.setInvestAmt(StringUtility.checknull(rs.getString("AMT_INV")));
					formfilling.setStatueoftaxOption(StringUtility.checknull(rs.getString("STATUE_OF_TAXOPTION")));
					formfilling.setNameofApplicant(StringUtility.checknull(rs.getString("NAME_OF_APPLICATION")));
					formfilling.setPanno(StringUtility.checknull(rs.getString("PANNO")));
					formfilling.setMailingAddress(StringUtility.checknull(rs.getString("MAILING_ADDRESS")));
					formfilling.setContactPerson(StringUtility.checknull(rs.getString("CONTACT_PERSON")));
					formfilling.setContactNumber(StringUtility.checknull(rs.getString("CONTACT_NO")));
					formfilling.setExtName(StringUtility.checknull(rs.getString("PATH")));
					formfilling.setFaxNumber(StringUtility.checknull(rs.getString("FAX_NUMBER")));
					formfilling.setArrangerDate(StringUtility.checknull(rs.getString("ARRANGER_DATE")));
					formfilling.setAuctionDate(StringUtility.checknull(rs.getString("AUCTION_DATE")));
					formfilling.setAccountNo(StringUtility.checknull(rs.getString("ACCOUNTNO")));
					formfilling.setPartyName(StringUtility.checknull(rs.getString("PARTY_NAME")));
					formfilling.setPartyContactNo(StringUtility.checknull(rs.getString("PARTY_CONTACTNO")));
					formfilling.setPartyAddress(StringUtility.checknull(rs.getString("PARTY_ADDRESS")));
					formfilling.setPname(StringUtility.checknull(rs.getString("PNAME")));
					formfilling.setPid(StringUtility.checknull(rs.getString("PID")));
					formfilling.setClientId(StringUtility.checknull(rs.getString("CLIENTID")));
					log.info("the account no is..."+formfilling.getAccountNo());
					
					
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("FormFillingDAO:searchFormFilling:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("FormFillingDAO:searchFormFilling:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		return formfilling;
	}
	public void  updateFormFill(FormFillingBean bean)
	throws ServiceNotAvailableException, EPISException {

		PreparedStatement pstmt = null;
		try {
			con = DBUtility.getConnection();
			
			if (con != null) {
				if(!bean.getExtName().equals(""))
				{
				FormFile  myform=bean.getUploadDocument();
				String contentType=myform.getContentType();
				String filename=myform.getFileName();
				byte filedata[]=myform.getFileData();
				
				if(!filename.equals("")){  
			        
			        //Create file
			        File fileToCreate = new File(bean.getFilePath(), bean.getFormCd()+bean.getExtName());
			        //If file does not exists create file                      
			        if(!fileToCreate.exists()){
			          FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
			          fileOutStream.write(myform.getFileData());
			          fileOutStream.flush();
			          fileOutStream.close();
			        }
				}
				}
				String saveQry = "update invest_formfilling set SECURITY_NAME='"+bean.getSecurityName()+"',NO_OF_BONDS='"+bean.getNoofBonds()+"',STATUE_OF_TAXOPTION='"+bean.getStatueoftaxOption()+"',ACCOUNTNO='"+StringUtility.checknull(bean.getAccountNo())+"',NAME_OF_APPLICATION='"+bean.getNameofApplicant()+"',PANNO='"+bean.getPanno()+"',MAILING_ADDRESS='"+bean.getMailingAddress()+"',CONTACT_PERSON='"+bean.getContactPerson()+"',CONTACT_NO='"+bean.getContactNumber()+"',PARTY_NAME='"+bean.getPartyName()+"',PARTY_CONTACTNO='"+bean.getPartyContactNo()+"',PARTY_ADDRESS='"+bean.getPartyAddress()+"',PNAME='"+bean.getPname()+"',PID='"+bean.getPid()+"',CLIENTID='"+bean.getClientId()+"',PATH='"+bean.getExtName()+"',UPDATED_BY='"+bean.getLoginUserId()+"',UPDATED_DT=sysdate where FORMCD='"+bean.getFormCd()+"'"; 
							
				DBUtility.executeUpdate(saveQry);
				
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pstmt, con);
		}
		}
	public void deleteFormFilling(String formCd)throws ServiceNotAvailableException, EPISException
	{
		try{
			con=DBUtility.getConnection();
			if(con!=null){
				String deleteQry="delete from invest_formfilling where FORMCD='"+formCd+"'";
				DBUtility.executeUpdate(deleteQry);
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pstmt, con);
		}
	}
	public String getproposalRefno(String formCd)throws ServiceNotAvailableException,EPISException
	{
			String refno="";
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				String refnoQuery="Select PROPOSAL_REF_NO from invest_formfilling where FORMCD='"+formCd+"'";
				st=con.createStatement();
				rs=DBUtility.getRecordSet(refnoQuery,st);
				if(rs.next())
				{
					refno=StringUtility.checknull(rs.getString("PROPOSAL_REF_NO"));
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, st, con);
		}
		
		return refno;
	}
	public FormFillingBean findapprovalformFilling(String formCd)throws ServiceNotAvailableException,EPISException
	{
		FormFillingBean bean=null;
		String approvalQuery="";
		String accountNo="";
		String bankquery="";
		try{
				approvalQuery="Select FORMCD,PROPOSAL_REF_NO,form.TRUSTTYPE,nvl(PATH,'')PATH,form.CATEGORYCD,decode(form.MARKET_TYPE,'P','Primary','S','Secondary','B','Both','R','RBI','O','Open Bid','PS','Primary And Secondary') MARKET_TYPE,nvl(form.SECURITY_NAME,'')SECURITY_NAME,NO_OF_BONDS,form.AMT_INV,decode(STATUE_OF_TAXOPTION,'E','Exempted','N','NON-Exempted')STATUE_OF_TAXOPTION,NAME_OF_APPLICATION,PANNO,MAILING_ADDRESS,CONTACT_PERSON,CONTACT_NO,nvl(to_char(AUCTION_DATE,'dd/Mon/yyyy'),'')AUCTION_DATE,nvl(FAX_NUMBER,'')FAX_NUMBER,nvl(PANNO,'')PANNO,nvl(ACCOUNTNO,'')ACCOUNTNO,nvl(PARTY_NAME,'--')PARTY_NAME,nvl(PARTY_CONTACTNO,'--')PARTY_CONTACTNO,nvl(PARTY_ADDRESS,'--')PARTY_ADDRESS,nvl(PNAME,'--')PNAME,nvl(PID,'--')PID,nvl(CLIENTID,'--')CLIENTID,Nvl(form.APPROVED_1,'N') APPROVED_1, to_char(form.APPROVE_1_DT,'dd/Mon/yyyy')APPROVE_1_DT ,Nvl(form.APPROVAL_1_REMARKS,' ') APPROVAL_1_REMARKS,Nvl(form.APPROVED_2,'N') APPROVED_2, to_char(form.APPROVE_2_DT,'dd/Mon/yyyy') APPROVE_2_DT ,Nvl(form.APPROVAL_2_REMARKS,' ') APPROVAL_2_REMARKS,Nvl(form.APPROVED_3,'N') APPROVED_3, to_char( form.APPROVE_3_DT,'dd/Mon/yyyy') APPROVE_3_DT ,Nvl(form.APPROVAL_3_REMARKS,' ') APPROVAL_3_REMARKS,Nvl(form.APPROVED_4,'N') APPROVED_4, to_char(form.APPROVE_4_DT,'dd/Mon/yyyy') APPROVE_4_DT ,Nvl(form.APPROVAL_4_REMARKS,' ') APPROVAL_4_REMARKS,Nvl(form.APPROVED_5,'N') APPROVED_5, to_char(form.APPROVE_5_DT,'dd/Mon/yyyy') APPROVE_5_DT ,Nvl(form.APPROVAL_5_REMARKS,' ') APPROVAL_5_REMARKS,nvl(form.APPROVED_6,'N')APPROVED_6,to_char(form.APPROVE_6_DT,'dd/Mon/yyyy')APPROVE_6_DT,nvl(form.APPROVAL_6_REMARKS,' ')APPROVAL_6_REMARKS,form.CREATED_BY,form.APPROVAL_1_BY,form.APPROVAL_2_BY,form.APPROVAL_3_BY,form.APPROVAL_4_BY,form.APPROVAL_5_BY,form.APPROVAL_6_BY,Decode('A',form.APPROVED_6,'6',form.APPROVED_5,'5',form.APPROVED_4,'4',form.APPROVED_3,'3',form.APPROVED_2,'2',form.APPROVED_1,'1','0') flag,to_char(Decode('A',form.APPROVED_6,form.APPROVE_6_DT,form.APPROVED_5,form.APPROVE_5_DT,form.APPROVED_4,form.APPROVE_4_DT,form.APPROVED_3,form.APPROVE_3_DT,form.APPROVED_2,form.APPROVE_2_DT,form.APPROVED_1, form.APPROVE_1_DT,PROPOSALDATE),'dd/Mon/YYYY') appDate,to_char(prop.PROPOSALDATE,'dd/Mon/YYYY')PROPOSALDATE,nvl(SUBJECT,'')SUBJECT,nvl(REMARKS,'--')REMARKS from invest_formfilling form,invest_proposal prop  where form.PROPOSAL_REF_NO=prop.REF_NO and FORMCD=?";
				bankquery="select nvl(BANKNAME,'')BANKNAME,nvl(ADDRESS,'')ADDRESS,nvl(ACCOUNTNO,'')ACCOUNTNO,decode(ACCOUNTTYPE,'S','Saving Account','C','Current Account')ACCOUNTTYPE from cb_bank_info where ACCOUNTNO=?";
				 
				con=DBUtility.getConnection();
				if(con!=null)
				{
					pstmt=con.prepareStatement(approvalQuery);
					if(pstmt!=null)
					{
						pstmt.setString(1,StringUtility.checknull(formCd));
						rs=pstmt.executeQuery();
						if(rs.next())
						{
							bean=new FormFillingBean();
							bean.setFormCd(StringUtility.checknull(rs.getString("FORMCD")));
							bean.setProposalRefNo(StringUtility.checknull(rs.getString("PROPOSAL_REF_NO")));
							bean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
							bean.setExtName(StringUtility.checknull(rs.getString("PATH")));
							bean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
							bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
							bean.setSecurityName(StringUtility.checknull(rs.getString("SECURITY_NAME")));
							bean.setNoofBonds(StringUtility.checknull(rs.getString("NO_OF_BONDS")));
							bean.setInvestAmt(StringUtility.checknull(rs.getString("AMT_INV")));
							bean.setStatueoftaxOption(StringUtility.checknull(rs.getString("STATUE_OF_TAXOPTION")));
							bean.setNameofApplicant(StringUtility.checknull(rs.getString("NAME_OF_APPLICATION")));
							bean.setPanno(StringUtility.checknull(rs.getString("PANNO")));
							bean.setMailingAddress(StringUtility.checknull(rs.getString("MAILING_ADDRESS")));
							bean.setContactPerson(StringUtility.checknull(rs.getString("CONTACT_PERSON")));
							bean.setContactNumber(StringUtility.checknull(rs.getString("CONTACT_NO")));
							bean.setProposaldate(StringUtility.checknull(rs.getString("PROPOSALDATE")));
							bean.setSubject(StringUtility.checknull(rs.getString("SUBJECT")));
							bean.setRemarks(StringUtility.checknull(rs.getString("REMARKS")));
							bean.setAuctionDate(StringUtility.checknull(rs.getString("AUCTION_DATE")));
							bean.setFaxNumber(StringUtility.checknull(rs.getString("FAX_NUMBER")));
							bean.setPanno(StringUtility.checknull(rs.getString("PANNO")));
							bean.setPartyName(StringUtility.checknull(rs.getString("PARTY_NAME")));
							bean.setPartyContactNo(StringUtility.checknull(rs.getString("PARTY_CONTACTNO")));
							bean.setPartyAddress(StringUtility.checknull(rs.getString("PARTY_ADDRESS")));
							bean.setPname(StringUtility.checknull(rs.getString("PNAME")));
							//bean.setPName(StringUtility.checknull(rs.getString("PNAME")));
							bean.setPid(StringUtility.checknull(rs.getString("PID")));
							bean.setClientId(StringUtility.checknull(rs.getString("CLIENTID")));
							accountNo=StringUtility.checknull(rs.getString("ACCOUNTNO"));
							
							bean.setUserId(StringUtility.checknull(rs.getString("CREATED_BY")));
							Map approvals = new LinkedHashMap();
							FormFillingAppBean appbean=new FormFillingAppBean();
							appbean.setApprovedBy(bean.getUserId());
							
							appbean = new FormFillingAppBean();
							appbean.setDate(rs.getString("APPROVE_1_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_1_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_1"));
							appbean.setApprovedBy(rs.getString("APPROVAL_1_BY"));
							approvals.put("1",appbean);
							appbean = new FormFillingAppBean();
							appbean.setDate(rs.getString("APPROVE_2_DT"));
							appbean.setRemarks(rs.getString("APPROVAL_2_REMARKS"));
							appbean.setApproved(rs.getString("APPROVED_2"));
							appbean.setApprovedBy(rs.getString("APPROVAL_2_BY"));
							approvals.put("2",appbean);
							
							
							bean.setApprovals(approvals);							
							bean.setFlags(rs.getString("flag"));
							bean.setAppDate(rs.getString("appDate"));
						}
						if(!accountNo.equals(""))
						{
							prepare=con.prepareStatement(bankquery);
							if(prepare!=null)
							{
								prepare.setString(1,accountNo);
							   rs1=prepare.executeQuery();
							   if(rs1.next())
							   {
								   bean.setBankName(StringUtility.checknull(rs1.getString("BANKNAME")));
								   bean.setBankAddress(StringUtility.checknull(rs1.getString("ADDRESS")));
								   bean.setAccountNo(StringUtility.checknull(rs1.getString("ACCOUNTNO")));
								   bean.setAccountType(StringUtility.checknull(rs1.getString("ACCOUNTTYPE")));
								   
							   }
							}
							
						}
						log.info("the pname is..."+bean.getPname());
					}
					else
					{
						throw new ServiceNotAvailableException();
					}
					
				}
				else{
					throw new ServiceNotAvailableException();
				}
				
				 
			
		}
		catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, st, con);
		}	
		return bean;
		
	}
	public void approvalUpdate(FormFillingAppBean bean)throws ServiceNotAvailableException,EPISException
	{
		log.info("this is calling for dao approvalUpdate...");
		 
		  
		  try{ 
			 con=DBUtility.getConnection(); 
			  int i = Integer.parseInt(StringUtility.checknull(bean.getApprovalLevel()));
			  
			  
			  String updateSql ="update invest_formfilling set APPROVED_"+i+"='A',APPROVE_"+i+"_DT=?,APPROVAL_"+i+"_REMARKS=?," +
			  		" APPROVAL_"+i+"_BY=?,APPROVAL_"+i+"_DT=SYSDATE where FORMCD=?";
			  
			  if(con!=null){
				  pstmt=con.prepareStatement(updateSql); 
				  if(pstmt!=null){
					  pstmt.setString(1,StringUtility.checknull(bean.getDate()));
					  pstmt.setString(2,StringUtility.checknull(bean.getRemarks()));
					  pstmt.setString(3,StringUtility.checknull(bean.getLoginUserId()));
					  pstmt.setString(4,StringUtility.checknull(bean.getFormCd()));
					  
					  pstmt.executeUpdate(); 
				  }else{ 
					  throw new ServiceNotAvailableException(); 
				  }
			  }else{ 
				  throw new ServiceNotAvailableException(); 
			  }
		  }catch(ServiceNotAvailableException  snex){ 
			  throw snex; 
		  }catch(SQLException sqlex){ 
			  sqlex.printStackTrace();
			  throw new EPISException(sqlex); 
		  }catch(Exception e){ 
			  e.printStackTrace();
			  throw new EPISException(e); 
		  } finally {
			  DBUtility.closeConnection(null,pstmt,con); 
		  }
		
	}
	public  List getProposal(String mode)throws ServiceNotAvailableException,EPISException
	{
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		try {
			FormFillingBean pbean = null;
			con = DBUtility.getConnection();

			String query ="";
			if(mode.equals("psuprimary"))
				query="select * from invest_formfilling where APPROVED_2='A' and CATEGORYCD='PSU' AND MARKET_TYPE='P' and PROPOSAL_REF_NO NOT IN(select PROPOSAL_REF_NO from invest_bankletter)";
			else if(mode.equals("rbiauction"))
				query="select PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,AMT_INV,PROPOSAL_REF_NO||SECURITY_NAME||SURITY_FULLNAME proposalsecurity,PROPOSAL_REF_NO||'--'||SECURITY_NAME||'--'||SURITY_FULLNAME proposalsecuritydef from INVEST_CONFIRMATION_COMPANY WHERE APPROVED_3='A' and((approved_4='A' and APPROVED_5='A')or(approved_5='A' and APPROVED_6='A') or(approved_4='A' and APPROVED_6='A')) AND CATEGORYCD<>'PSU' AND MARKET_TYPE<>'P' and PROPOSAL_REF_NO||SECURITY_NAME||SURITY_FULLNAME  NOT IN(select PROPOSAL_REF_NO||SECURITY_NAME||SURITY_FULLNAME from invest_bankletter)";
			
			if (con != null) {
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					pbean = new FormFillingBean();
					pbean.setProposalRefNo(rs.getString("PROPOSAL_REF_NO"));
					pbean.setTrustType(rs.getString("TRUSTTYPE"));
					pbean.setSecurityCategory(rs.getString("CATEGORYCD"));
					pbean.setInvestAmt(rs.getString("AMT_INV"));
					if(mode.equals("rbiauction"))
					{
						pbean.setProposalSecurity(StringUtility.checknull(rs.getString("proposalsecurity")));
						pbean.setProposalSecuritydef(StringUtility.checknull(rs.getString("proposalsecuritydef")));
					}
					list.add(pbean);
				}
			} else {
				throw new ServiceNotAvailableException();
			}
		} catch (ServiceNotAvailableException snex) {
			throw snex;
		} catch (SQLException sqlex) {
			log.error("InvestmentProposalDAO:getProposal:SQLException:"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {

			log.error("InvestmentProposalDAO:getProposal:Exception:"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pstmt, con);
		}

		return list;
	}

}
