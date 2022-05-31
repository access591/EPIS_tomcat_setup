package com.epis.dao.cashbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.cashbook.BankMasterInfo;
import com.epis.bean.cashbook.PartyInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;

public class PartyDAO {
	Log log = new Log(PartyDAO.class);

	private PartyDAO() {

	}

	private static final PartyDAO dao = new PartyDAO();

	public static PartyDAO getInstance() {
		return dao;
	}


	public List search(PartyInfo info) throws Exception {
		log.info("PartyDAO : search : Entering method");		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List partyInfo = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(selectQuery);
			pst.setString(1, StringUtility.checknull(info.getPartyName())+"%");
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new PartyInfo();
				info.setPartyCode(rs.getString("partycode"));
				info.setPartyName(rs.getString("PARTYNAME"));
				info.setPartyDetail(rs.getString("PARTYDETAIL"));
				info.setMobileNo(rs.getString("MOBILENO"));
				info.setContactNo(rs.getString("CONTACTNO"));
				info.setFaxNo(rs.getString("FAXNO"));
				info.setEmailId(rs.getString("EMAIL"));
				partyInfo.add(info);		
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("PartyDAO :search leaving method");
		return partyInfo;
	}
	public List getPartyList(String type)throws Exception
	{
		
		List PartyList=new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PartyInfo info=null;
		String PartyLoadQuery=null;
		try{
			con = DBUtility.getConnection();
			if(type.equals("B"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand'),replace(INVEST_CASH_PARTYNAME,'&','ampersand')  from cb_party_info where MAPPING_FLAG='Y' order by (case  when instr(partyname, '%') between 1 and 6 then to_number(substr(partyname, 0, instr(partyname, '%') - 1))  else  0 end)";
			if(type.equals("C"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand')partydes,replace(PARTYNAME,'&','ampersand') party  from cb_party_info where MAPPING_FLAG='N'and GROUPING_FLAG='N' and module_type=? and partyname   in(select emp_party_code from cb_voucher_info info where partytype='P')  order by (case  when instr(partyname, '%') between 1 and 6 then to_number(substr(partyname, 0, instr(partyname, '%') - 1))  else  0 end)";
			if(type.equals("I"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand')||'--'|| nvl(register.facevalue_inrs,0) partydes,replace(PARTYNAME,'&','ampersand') party   from cb_party_info info,investment_register register where trim(upper(info.partyname)) = trim(upper(register.SECURITY_PROPOSAL)) and  MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type=? and partyname  not in(select emp_party_code from cb_voucher_info info where partytype='P') order by (case  when instr(partyname, '%') between 1 and 6 then to_number(substr(partyname, 0, instr(partyname, '%') - 1))  else  0 end)";
			
			
			pst=con.prepareStatement(PartyLoadQuery);
			if(!type.equals("B"))
			pst.setString(1,type);
			rs=pst.executeQuery();
			while(rs.next())
			{
				info=new PartyInfo();
				if(!type.equals("B"))
				{
				info.setPartyName(StringUtility.checknull(rs.getString(2)));
				info.setPartyDetail(StringUtility.checknull(rs.getString(1)));
				}
				if(type.equals("B"))
				{
					info.setPartyName(StringUtility.checknull(rs.getString(1)));
					info.setInvPartyname(StringUtility.checknull(rs.getString(2)));
				}
				
				PartyList.add(info);
			}
			
		}
		catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		
		
		return PartyList;
		
	}
	public List getGroupPartyList(String type)throws Exception
	{
		List PartyList=new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PartyInfo info=null;
		String PartyLoadQuery=null;
		try{
			con = DBUtility.getConnection();
			if(type.equals("B"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand'),replace(INVEST_CASH_PARTYNAME,'&','ampersand'),Group_name  from cb_party_info where MAPPING_FLAG='Y'  order by  group_name";
			if(type.equals("C"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand')partydes,replace(PARTYNAME,'&','ampersand') party  from cb_party_info where MAPPING_FLAG='N'and GROUPING_FLAG='N' and module_type=? and partyname   in(select emp_party_code from cb_voucher_info info where partytype='P')  order by (case  when instr(partyname, '%') between 1 and 6 then to_number(substr(partyname, 0, instr(partyname, '%') - 1))  else  0 end)";
			if(type.equals("I"))
				PartyLoadQuery="select replace(PARTYNAME,'&','ampersand')||'--'|| nvl(register.facevalue_inrs,0) partydes,replace(PARTYNAME,'&','ampersand') party   from cb_party_info info,investment_register register where trim(upper(info.partyname)) = trim(upper(register.SECURITY_PROPOSAL)) and  MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type=? and partyname  not in(select emp_party_code from cb_voucher_info info where partytype='P') order by (case  when instr(partyname, '%') between 1 and 6 then to_number(substr(partyname, 0, instr(partyname, '%') - 1))  else  0 end)";
			
			
			
			pst=con.prepareStatement(PartyLoadQuery);
			if(!type.equals("B"))
			pst.setString(1,type);
			rs=pst.executeQuery();
			while(rs.next())
			{
				info=new PartyInfo();
				if(!type.equals("B"))
				{
				info.setPartyName(StringUtility.checknull(rs.getString(2)));
				info.setPartyDetail(StringUtility.checknull(rs.getString(1)));
				}
				if(type.equals("B"))
				{
					info.setPartyName(StringUtility.checknull(rs.getString(1)));
					info.setInvPartyname(StringUtility.checknull(rs.getString(2)));
					info.setGroupName(StringUtility.checknull(rs.getString(3)));
					
				}
				
				PartyList.add(info);
			}
			
		}
		catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		
		
		return PartyList;
	}
	
	public List getGroupList()throws Exception{
		
		List PartyList=new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PartyInfo info=null;
		String PartyLoadQuery="";
		try{
			con = DBUtility.getConnection();
			PartyLoadQuery="select replace(PARTYNAME,'&','ampersand'),module_type,Group_name  from cb_party_info where groupING_FLAG='Y'  order by  group_name";
			
			pst=con.prepareStatement(PartyLoadQuery);
			rs=pst.executeQuery();
			while(rs.next())
			{
				info=new PartyInfo();
				if(rs.getString("module_type").equals("I"))
					info.setInvPartyname(StringUtility.checknull(rs.getString(1)));
				if(rs.getString("module_type").equals("C"))
					info.setPartyName(StringUtility.checknull(rs.getString(1)));
				
				info.setGroupName(StringUtility.checknull(rs.getString("Group_name")));
				PartyList.add(info);
			}
			
			
		}
		catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		return PartyList;
		
	}
	
	public void delete(String[] recs) throws Exception {
		log.info("PartyDAO : delete : Entering method");
		Connection con = null;
		PreparedStatement pst = null;		
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			StringBuffer sb = new StringBuffer();
			int len = recs.length;
			for (int i = 0; i < len; i++) {
				sb.append(recs[i]).append("|");
			}			
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, sb.toString());
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, sb.toString());
			pst.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null,pst,con);
		}
		log.info("PartyDAO : delete : leaving method");		
	}
	
	public void add(PartyInfo info) throws Exception {
		log.info("PartyDAO : add : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String moduleType="";
		try{
			if(StringUtility.checknull(info.getModuleType()).equals(""))
				moduleType="C";
			else
				moduleType=info.getModuleType();
				
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(sequalQuery);
			rs = pst.executeQuery();
			while(rs.next()){
				info.setPartyCode(rs.getString(1));
			}
			DBUtility.closeConnection(rs,pst,null);
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, info.getPartyCode());
			pst.setString(2, info.getPartyName());
			pst.setString(3, info.getPartyDetail());
			pst.setString(4, info.getFaxNo());
			pst.setString(5, info.getEmailId());
			pst.setString(6, info.getMobileNo());
			pst.setString(7, info.getLoginUserId());
			pst.setString(8, info.getContactNo());
			pst.setString(9, info.getLoginUnitCode());
			pst.setString(10, moduleType);
			pst.executeUpdate();
			pst.close();
			if(info.getBankDetails()!=null){
				BankMasterInfo bInfo = null;
				List bankInfo = info.getBankDetails();
				pst = con.prepareStatement(insertBankQuery);
				for(int i=0;i<bankInfo.size();i++){			
					bInfo = (BankMasterInfo)bankInfo.get(i);
					pst.setString(1, info.getPartyCode());
					pst.setString(2, bInfo.getBankName());
					pst.setString(3, bInfo.getBranchName());
					pst.setString(4, bInfo.getBankCode());
					pst.setString(5, bInfo.getAddress());
					pst.setString(6, bInfo.getPhoneNo());
					pst.setString(7, bInfo.getFaxNo());
					pst.setString(8, bInfo.getAccountNo());
					pst.setString(9, bInfo.getAccountType());
					pst.setString(10, bInfo.getIFSCCode());
					pst.setString(11, bInfo.getNEFTRTGSCode());
					pst.setString(12, bInfo.getMICRNo());
					pst.setString(13, bInfo.getContactPerson());
					pst.setString(14, bInfo.getMobileNo());
					pst.executeUpdate();	
				}
			}	
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null,pst,con);
		}
		
		UserTracking.write(info.getLoginUserId(),info.getPartyCode()+"-"+ info.getPartyName() + "-"
				+ info.getEmailId() + "-" + info.getMobileNo()
				+ info.getFaxNo(), "S", "CB", info.getPartyName(),"Party Master");
		log.info("PartyDAO : add : Leaving method");
	}
	
	public boolean exists(PartyInfo info) throws Exception {
		log.info("PartyDAO : exists : Entering method");
		boolean exists = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(countQuery);
			pst.setString(1, info.getPartyName());
			rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("PartyDAO : exists : Leaving method");
		return exists;
	}
	
	public PartyInfo edit(PartyInfo info) throws Exception {
		log.info("PartyDAO : edit : Entering method");		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List bankDetails = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(editQuery);
			pst.setString(1, StringUtility.checknull(info.getPartyCode()));
			rs = pst.executeQuery();
			boolean bool = true;
			while (rs.next()) {
				if(bool){					
					info.setPartyCode(rs.getString("partycode"));
					info.setPartyName(rs.getString("PARTYNAME"));
					info.setPartyDetail(rs.getString("PARTYDETAIL"));
					info.setMobileNo(rs.getString("MOBILENO"));
					info.setContactNo(rs.getString("CONTACTNO"));
					info.setFaxNo(rs.getString("FAXNO"));
					info.setEmailId(rs.getString("EMAIL"));
					bool = false;
				}
				BankMasterInfo bInfo = new BankMasterInfo();
				bInfo.setBankName(rs.getString("BANKNAME"));
				bInfo.setBranchName(rs.getString("BRANCHNAME"));
				bInfo.setBankCode(rs.getString("BANKCODE"));
				bInfo.setAddress(rs.getString("ADDRESS"));
				bInfo.setPhoneNo(rs.getString("PHONENO"));
				bInfo.setFaxNo(rs.getString("BFAXNO"));
				bInfo.setAccountNo(rs.getString("ACCOUNTNO"));
				bInfo.setAccountType(rs.getString("ACCOUNTTYPE"));
				bInfo.setIFSCCode(rs.getString("IFSCCODE"));
				bInfo.setNEFTRTGSCode(rs.getString("NEFT_RTGSCODE"));
				bInfo.setMICRNo(rs.getString("MICRNO"));
				bInfo.setContactPerson(rs.getString("CONTACTPERSON"));
				bInfo.setMobileNo(rs.getString("BMOBILENO"));
				bankDetails.add(bInfo);
			}
			info.setBankDetails(bankDetails);
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs,pst,con);
		}
		log.info("PartyDAO :edit leaving method");
		return info;
	}
	public List partyReport(String party) throws Exception {
		
		log.info("PartyDAO : delete : Entering method");
		String reportQuery="";
		Connection con = null;
		Statement pst = null;	
		ResultSet rs=null;
		PartyInfo info=null;
		List partyInfo = new ArrayList();
		log.info("party in dao is :"+party);
		try {
			con = DBUtility.getConnection();
			pst = con.createStatement();
			reportQuery = "select * from Cb_Party_info where Upper(trim(PARTYNAME)) like upper('"+party+"%')";
			log.info("reportQuery :"+reportQuery);
			rs = pst.executeQuery(reportQuery);
			while (rs.next()) {
				info = new PartyInfo();
				info.setPartyCode(rs.getString("partycode"));
				info.setPartyName(rs.getString("PARTYNAME"));
				info.setPartyDetail(rs.getString("PARTYDETAIL"));
				info.setMobileNo(rs.getString("MOBILENO"));
				info.setContactNo(rs.getString("CONTACTNO"));
				info.setFaxNo(rs.getString("FAXNO"));
				info.setEmailId(rs.getString("EMAIL"));
				partyInfo.add(info);
			}
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null,pst,con);
		}
		log.info("PartyDAO : delete : leaving method");	
		return partyInfo;
	}
	
	
	public void update(PartyInfo info) throws Exception {
		log.info("PartyDAO : update : Entering method");
		Connection con = null;
		PreparedStatement pst = null;		
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, info.getPartyDetail());
			pst.setString(2, info.getFaxNo());
			pst.setString(3, info.getMobileNo());
			pst.setString(4, info.getEmailId());
			pst.setString(5, info.getContactNo());
			pst.setString(6, info.getLoginUserId());
			pst.setString(7, info.getPartyCode());
			pst.executeUpdate();
			pst.close();
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, info.getPartyCode());
			pst.executeUpdate();
			pst.close();
			if(info.getBankDetails()!=null){
				BankMasterInfo bInfo = null;
				List bankInfo = info.getBankDetails();
				pst = con.prepareStatement(insertBankQuery);
				for(int i=0;i<bankInfo.size();i++){			
					bInfo = (BankMasterInfo)bankInfo.get(i);
					pst.setString(1, info.getPartyCode());
					pst.setString(2, bInfo.getBankName());
					pst.setString(3, bInfo.getBranchName());
					pst.setString(4, bInfo.getBankCode());
					pst.setString(5, bInfo.getAddress());
					pst.setString(6, bInfo.getPhoneNo());
					pst.setString(7, bInfo.getFaxNo());
					pst.setString(8, bInfo.getAccountNo());
					pst.setString(9, bInfo.getAccountType());
					pst.setString(10, bInfo.getIFSCCode());
					pst.setString(11, bInfo.getNEFTRTGSCode());
					pst.setString(12, bInfo.getMICRNo());
					pst.setString(13, bInfo.getContactPerson());
					pst.setString(14, bInfo.getMobileNo());
					pst.executeUpdate();	
				}
			}	
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new EPISException(e);
		} catch (Exception e) {
			con.rollback();
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null,pst,con);
		}
		log.info("PartyDAO : update : leaving method");	
		
	}
	
	String editQuery = "select info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO,Nvl(BANKNAME,' ') BANKNAME,Nvl(BRANCHNAME,' ') BRANCHNAME,Nvl(BANKCODE,' ') BANKCODE,Nvl(ADDRESS,' ') ADDRESS,Nvl(PHONENO,' ') PHONENO,Nvl(BFAXNO,' ') BFAXNO,Nvl(ACCOUNTNO,' ') ACCOUNTNO,Nvl(ACCOUNTTYPE,' ') ACCOUNTTYPE,Nvl(IFSCCODE,' ') IFSCCODE,Nvl(NEFT_RTGSCODE,' ') NEFT_RTGSCODE,Nvl(MICRNO,' ') MICRNO,Nvl(CONTACTPERSON,' ') CONTACTPERSON,Nvl(BMOBILENO,' ') BMOBILENO from Cb_Party_info info,Cb_PARTY_BANK_DETAILS dt where info.partycode = dt.partycode(+) and upper(trim(info.partycode)) = upper(trim(?))";

	String selectQuery = "select info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO  from Cb_Party_info info where upper(trim(PARTYNAME)) like upper(trim(?)) " ;

	String countQuery = "select count(*) from Cb_Party_info where Upper(trim(PARTYNAME)) = upper(trim(?)) ";
	
	String sequalQuery = "select lpad(PARTY_SEQ.nextval,10,'0') seq from dual";

	String insertQuery = "insert into Cb_Party_info (PARTYCODE,PARTYNAME,PARTYDETAIL,FAXNO,EMAIL,MOBILENO,ENTEREDBY,CONTACTNO,UNITCODE,MODULE_TYPE) values (?,upper(?),?,?,?,?,?,?,?,?)";

	String insertBankQuery = "insert into Cb_PARTY_BANK_DETAILS (PARTYCODE,BANKNAME,BRANCHNAME,BANKCODE,ADDRESS,PHONENO,BFAXNO,ACCOUNTNO,ACCOUNTTYPE,IFSCCODE,NEFT_RTGSCODE,MICRNO,CONTACTPERSON,BMOBILENO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	String updateQuery = "update Cb_Party_info set PARTYDETAIL=?,FAXNO=?,MOBILENO=?,EMAIL=?,CONTACTNO=?,EDITEDBY=?,EDITEDDT=SYSDATE where PARTYCODE=? ";

	String deleteQuery = "Delete from Cb_Party_info  Where INSTR(upper(?),upper(PARTYCODE)) > 0";

	String deleteDtQuery = "Delete from Cb_PARTY_BANK_DETAILS  Where INSTR(upper(?),upper(PARTYCODE)) > 0";

	
}
