package com.epis.dao.cashbookDummy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import com.epis.bean.cashbookDummy.BankMasterInfo;
import com.epis.bean.cashbookDummy.PartyInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.UserTracking;
 
public class PartyDAO {
	Log log = new Log(PartyDAO.class);

	public ArrayList getPartyList(PartyInfo info, String type) throws Exception {
		log.info("PartyDAO : getPartyList : Entering method");		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map parties = new HashMap();
		ArrayList partyInfo = new ArrayList();
		try {
			con = DBUtility.getConnection();
			log.info(selectQueryActive);
			if(info.getModuleType().equals("I"))
				pst = con.prepareStatement(selectQueryActive);
			
			if(info.getModuleType().equals("C"))	
			pst = con.prepareStatement(selectQuery);
			
			
			if(info.getModuleType().equals(""))
				pst = con.prepareStatement(combineQuery);
			if(!info.getModuleType().equals(""))
			{
			pst.setString(1, (info.getPartyName()==null?"":info.getPartyName())+"%");
			pst.setString(2,info.getModuleType()+"%");
			pst.setString(3, (info.getPartyCode()==null?"":info.getPartyCode())
					+ ("edit".equals(type) ? "" : "%"));
			}
			if(info.getModuleType().equals(""))
			{
				pst.setString(1, (info.getPartyName()==null?"":info.getPartyName())+"%");
				pst.setString(2, (info.getPartyCode()==null?"":info.getPartyCode())
						+ ("edit".equals(type) ? "" : "%"));
				
				pst.setString(3, (info.getPartyName()==null?"":info.getPartyName())+"%");
				pst.setString(4, (info.getPartyCode()==null?"":info.getPartyCode())
						+ ("edit".equals(type) ? "" : "%"));
			}
			rs = pst.executeQuery();
			List bankInfo = null;
			while (rs.next()) {
				if(parties.containsKey(rs.getString("partycode"))){
					
					info = (PartyInfo)parties.get(rs.getString("partycode"));
					bankInfo = info.getBankInfo();
				}else {
					
					info = new PartyInfo();
					info.setPartyCode(rs.getString("partycode"));
					info.setPartyName(rs.getString("PARTYNAME"));
					info.setPartyDetail(rs.getString("PARTYDETAIL"));
					info.setMobileNo(rs.getString("MOBILENO"));
					info.setContactNo(rs.getString("CONTACTNO"));
					info.setFaxNo(rs.getString("FAXNO"));
					info.setEmailId(rs.getString("EMAIL"));
					info.setModuleType(rs.getString("MODULE_TYPE"));
					info.setRefNo(rs.getString("refno"));
					info.setSecurityName(rs.getString("securityname"));
					info.setFacevalueinRs(rs.getString("facevalueinrs"));
					info.setDealDate(rs.getString("dealdate"));
					info.setSettlementDate(rs.getString("settlementdate"));
					info.setISIN(rs.getString("isin"));
					bankInfo = new ArrayList();
				}
			
				BankMasterInfo bInfo = new BankMasterInfo();
				bInfo.setBankName(rs.getString("BANKNAME"));
				bInfo.setAccountNo(rs.getString("ACCOUNTNO"));
				bInfo.setBankCode(rs.getString("BANKCODE"));
				bInfo.setIFSCCode(rs.getString("IFSCCODE"));
				bInfo.setBranchName(rs.getString("BRANCHNAME"));
				bInfo.setAddress(rs.getString("ADDRESS"));
				bInfo.setPhoneNo(rs.getString("PHONENO"));
				bInfo.setFaxNo(rs.getString("BFAXNO"));
				bInfo.setAccountType(rs.getString("ACCOUNTTYPE"));
				bInfo.setNEFTRTGSCode(rs.getString("NEFT_RTGSCODE"));
				bInfo.setMICRNo(rs.getString("MICRNO"));
				bInfo.setContactPerson(rs.getString("CONTACTPERSON"));
				bInfo.setMobileNo(rs.getString("BMOBILENO"));
				bankInfo.add(bInfo);
				info.setBankInfo(bankInfo);
				//parties.put(rs.getString("partycode"),info);
				partyInfo.add(info);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		
		/*Set s = parties.keySet();
		Iterator iter = s.iterator();
		while(iter.hasNext()){
			partyInfo.add(parties.get(iter.next()));
		}*/
		log.info("PartyDAO :getPartyList() leaving method");
		return partyInfo;
	}

	public boolean exists(PartyInfo info,String accNos) throws Exception {
		log.info("PartyDAO : exists : Entering method");
		boolean exists = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(countQuery);
			pst.setString(1, info.getPartyName());
			//pst.setString(2, accNos);
			rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("PartyDAO : exists : Leaving method");
		return exists;
	}

	public void addPartyRecord(PartyInfo info) throws Exception {
		log.info("PartyDAO : addPartyRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String seq = null;
		List bankInfo = null;		
		try{
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			try {			
				pst = con.prepareStatement(sequalQuery);
				rs = pst.executeQuery();
				while(rs.next()){
					seq = rs.getString(1);
				}
			} catch (Exception e) {
				log.printStackTrace(e);
				throw e;
			} finally {
				try {
					rs.close();
					pst.close();					
				} catch (SQLException e) {
					log.printStackTrace(e);
				}
			}
			try {
				pst = con.prepareStatement(insertQuery);
				pst.setString(1, seq);
				pst.setString(2, info.getPartyName());
				pst.setString(3, info.getPartyDetail());
				pst.setString(4, info.getFaxNo());
				pst.setString(5, info.getEmailId());
				pst.setString(6, info.getMobileNo());
				pst.setString(7, info.getEnteredBy());
				pst.setString(8, info.getContactNo());
				pst.executeUpdate();
			} catch (Exception e) {
				log.printStackTrace(e);
				throw e;
			} finally {
				try {
					pst.close();
				} catch (SQLException e) {
					log.printStackTrace(e);
				}
			}
			if(info.getBankInfo()!=null){
			bankInfo = info.getBankInfo();
			pst = con.prepareStatement(insertBankQuery);
			for(int i=0;i<bankInfo.size();i++){
				try {
					BankMasterInfo bInfo =(BankMasterInfo) bankInfo.get(i);
					pst.setString(1, seq);
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
				} catch (Exception e) {
					log.printStackTrace(e);
					throw e;
				}
			}			
			con.commit();
	}
		} catch (SQLException e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			con.rollback();
			log.printStackTrace(e);
			throw e;
		} finally {
			try {	
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		
		UserTracking.write(info.getEnteredBy(),seq+"-"+ info.getPartyName() + "-"
				+ info.getEmailId() + "-" + info.getMobileNo()
				+ info.getFaxNo(), "S", "CB", info.getPartyName(),"Party Master");
		log.info("PartyDAO : addPartyRecord : Leaving method");
	}

	public void updatePartyRecord(PartyInfo info) throws Exception {
		log.info("PartyDAO : updatePartyRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		con = DBUtility.getConnection();
		con.setAutoCommit(false);
		try {
			try {
				pst = con.prepareStatement(updateQuery);
				pst.setString(1, info.getPartyDetail());
				pst.setString(2, info.getFaxNo());
				pst.setString(3, info.getMobileNo());
				pst.setString(4, info.getEmailId());
				pst.setString(5, info.getContactNo());
				pst.setString(6, info.getPartyCode());
				pst.executeUpdate();
			} catch (SQLException e) {
				con.rollback();
				log.printStackTrace(e);
				throw e;
			} catch (Exception e) {
				con.rollback();
				log.printStackTrace(e);
				throw e;
			} finally {
				try {
					pst.close();
				} catch (SQLException e) {
					log.printStackTrace(e);
				}
			}
			try {
				pst = con.prepareStatement(deleteDtQuery);
				pst.setString(1, info.getPartyCode());
				pst.executeUpdate();
			} catch (SQLException e) {
				con.rollback();
				throw e;
			} catch (Exception e) {
				con.rollback();
				throw e;
			} finally {
				try {
					pst.close();
				} catch (SQLException e) {
					log.printStackTrace(e);
				}
			}
			List bankInfo = info.getBankInfo();
			if(info.getBankInfo()!=null){
			pst = con.prepareStatement(insertBankQuery);
			for(int i=0;i<bankInfo.size();i++){
				try {
					BankMasterInfo bInfo =(BankMasterInfo) bankInfo.get(i);
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
				} catch (Exception e) {
					con.rollback();
					log.printStackTrace(e);
					throw e;
				}
			}
		}
			con.commit();
		} catch (SQLException e) {
				con.rollback();
				throw e;
			} catch (Exception e) {
				con.rollback();
				throw e;
			} finally {
				try {					
					pst.close();
					con.close();
				} catch (SQLException e) {
					log.printStackTrace(e);
				}
		}
		
		UserTracking.write(info.getEnteredBy(),info.getPartyCode()+"-"+ info.getPartyName() + "-"
				+ info.getEmailId() + "-" + info.getMobileNo()
				+ info.getFaxNo(), "U", "CB", info.getPartyName(),"Party Master");
		log.info("PartyDAO : updatePartyRecord : leaving method");

	}

	public void deletePartyRecord(String codes) throws Exception {
		log.info("PartyDAO : deletePartyRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		con = DBUtility.getConnection();
		con.setAutoCommit(false);
		try {
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, codes);
			pst.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		try {
			pst = con.prepareStatement(deleteDtQuery);
			pst.setString(1, codes);
			pst.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("PartyDAO : deletePartyRecord : leaving method");

	}

	String selectQuery = "select Nvl(reg.isin,' ') isin,info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO,Nvl(BANKNAME,' ') BANKNAME,Nvl(BRANCHNAME,' ') BRANCHNAME,Nvl(BANKCODE,' ') BANKCODE,Nvl(ADDRESS,' ') ADDRESS,Nvl(PHONENO,' ') PHONENO,Nvl(BFAXNO,' ') BFAXNO,Nvl(ACCOUNTNO,' ') ACCOUNTNO,Nvl(ACCOUNTTYPE,' ') ACCOUNTTYPE,Nvl(IFSCCODE,' ') IFSCCODE,Nvl(NEFT_RTGSCODE,' ') NEFT_RTGSCODE,Nvl(MICRNO,' ') MICRNO,Nvl(CONTACTPERSON,' ') CONTACTPERSON,Nvl(BMOBILENO,' ') BMOBILENO,decode(MODULE_TYPE,'C','Cash Book','I','Investment') MODULE_TYPE,nvl(reg.proposal_ref_no,' ')refno,nvl(reg.security_name,' ')securityname,nvl(reg.facevalue_inrs,0) facevalueinrs,nvl(to_char(reg.dealdate,'dd/Mon/yyyy'),' ') dealdate,nvl(to_char(reg.settlement_date,'dd/Mon/yyyy'),' ')settlementdate from CB_Party_info info,CB_PARTY_BANK_DETAILS dt,investment_register reg where Upper(PARTYNAME) like Upper(?) and module_type like(?) and info.partycode = dt.partycode(+) and upper(info.partyname)=upper(reg.security_proposal(+)) and info.partycode like ?" ;
	String selectQueryActive = "select Nvl(reg.isin,' ') isin,info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO,Nvl(BANKNAME,' ') BANKNAME,Nvl(BRANCHNAME,' ') BRANCHNAME,Nvl(BANKCODE,' ') BANKCODE,Nvl(ADDRESS,' ') ADDRESS,Nvl(PHONENO,' ') PHONENO,Nvl(BFAXNO,' ') BFAXNO,Nvl(ACCOUNTNO,' ') ACCOUNTNO,Nvl(ACCOUNTTYPE,' ') ACCOUNTTYPE,Nvl(IFSCCODE,' ') IFSCCODE,Nvl(NEFT_RTGSCODE,' ') NEFT_RTGSCODE,Nvl(MICRNO,' ') MICRNO,Nvl(CONTACTPERSON,' ') CONTACTPERSON,Nvl(BMOBILENO,' ') BMOBILENO,decode(MODULE_TYPE,'C','Cash Book','I','Investment') MODULE_TYPE,nvl(reg.proposal_ref_no,' ')refno,nvl(reg.security_name,' ')securityname,nvl(reg.facevalue_inrs,0) facevalueinrs,nvl(to_char(reg.dealdate,'dd/Mon/yyyy'),' ') dealdate,nvl(to_char(reg.settlement_date,'dd/Mon/yyyy'),' ')settlementdate from CB_Party_info info,CB_PARTY_BANK_DETAILS dt,investment_register reg where (Upper(isin) like Upper(?) or isin is null)  and module_type like(?) and info.partycode = dt.partycode(+) and upper(info.partyname)=upper(reg.security_proposal(+)) and  info.partycode like ? and reg.status='A' order by reg.dealdate desc" ;
	
	String combineQuery="select info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO,Nvl(BANKNAME,' ') BANKNAME,Nvl(BRANCHNAME,' ') BRANCHNAME,Nvl(BANKCODE,' ') BANKCODE,Nvl(ADDRESS,' ') ADDRESS,Nvl(PHONENO,' ') PHONENO,Nvl(BFAXNO,' ') BFAXNO,Nvl(ACCOUNTNO,' ') ACCOUNTNO,Nvl(ACCOUNTTYPE,' ') ACCOUNTTYPE,Nvl(IFSCCODE,' ') IFSCCODE,Nvl(NEFT_RTGSCODE,' ') NEFT_RTGSCODE,Nvl(MICRNO,' ') MICRNO,Nvl(CONTACTPERSON,' ') CONTACTPERSON,Nvl(BMOBILENO,' ') BMOBILENO,decode(MODULE_TYPE,'C','Cash Book','I','Investment') MODULE_TYPE,nvl(reg.proposal_ref_no,' ')refno,nvl(reg.security_name,' ')securityname,nvl(reg.facevalue_inrs,0) facevalueinrs,nvl(to_char(reg.dealdate,'dd/Mon/yyyy'),' ') dealdate,nvl(to_char(reg.settlement_date,'dd/Mon/yyyy'),' ')settlementdate from CB_Party_info info,CB_PARTY_BANK_DETAILS dt,investment_register reg where Upper(PARTYNAME) like Upper(?) and module_type='C' and info.partycode = dt.partycode(+) and upper(info.partyname)=upper(reg.security_proposal(+)) and info.partycode like ? union  select info.partycode, PARTYNAME,Nvl(PARTYDETAIL,' ') PARTYDETAIL,Nvl(CONTACTNO,' ') CONTACTNO,Nvl(FAXNO,' ') FAXNO,Nvl(EMAIL,' ') EMAIL,Nvl(MOBILENO,' ') MOBILENO,Nvl(BANKNAME,' ') BANKNAME,Nvl(BRANCHNAME,' ') BRANCHNAME,Nvl(BANKCODE,' ') BANKCODE,Nvl(ADDRESS,' ') ADDRESS,Nvl(PHONENO,' ') PHONENO,Nvl(BFAXNO,' ') BFAXNO,Nvl(ACCOUNTNO,' ') ACCOUNTNO,Nvl(ACCOUNTTYPE,' ') ACCOUNTTYPE,Nvl(IFSCCODE,' ') IFSCCODE,Nvl(NEFT_RTGSCODE,' ') NEFT_RTGSCODE,Nvl(MICRNO,' ') MICRNO,Nvl(CONTACTPERSON,' ') CONTACTPERSON,Nvl(BMOBILENO,' ') BMOBILENO,decode(MODULE_TYPE,'C','Cash Book','I','Investment') MODULE_TYPE,nvl(reg.proposal_ref_no,' ')refno,nvl(reg.security_name,' ')securityname,nvl(reg.facevalue_inrs,0) facevalueinrs,nvl(to_char(reg.dealdate,'dd/Mon/yyyy'),' ') dealdate,nvl(to_char(reg.settlement_date,'dd/Mon/yyyy'),' ')settlementdate from CB_Party_info info,CB_PARTY_BANK_DETAILS dt,investment_register reg where Upper(PARTYNAME) like Upper(?) and module_type='I' and info.partycode = dt.partycode(+) and upper(info.partyname)=upper(reg.security_proposal(+)) and info.partycode like ? and reg.status='A' ";

	String countQuery = "select count(*) from CB_Party_info where Upper(PARTYNAME) = upper(?) ";
	
	String sequalQuery = "select lpad(PARTY_SEQ.nextval,10,'0') seq from dual";

	String insertQuery = "insert into CB_Party_info (PARTYCODE,PARTYNAME,PARTYDETAIL,FAXNO,EMAIL,MOBILENO,ENTEREDBY,CONTACTNO) values (?,upper(?),?,?,?,?,?,?)";

	String insertBankQuery = "insert into CB_PARTY_BANK_DETAILS (PARTYCODE,BANKNAME,BRANCHNAME,BANKCODE,ADDRESS,PHONENO,BFAXNO,ACCOUNTNO,ACCOUNTTYPE,IFSCCODE,NEFT_RTGSCODE,MICRNO,CONTACTPERSON,BMOBILENO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	String updateQuery = "update CB_Party_info set PARTYDETAIL=?,FAXNO=?,MOBILENO=?,EMAIL=?,CONTACTNO=? where PARTYCODE=? ";

	String deleteQuery = "Delete from CB_Party_info  Where INSTR(upper(?),upper(PARTYCODE)) > 0";

	String deleteDtQuery = "Delete from CB_PARTY_BANK_DETAILS  Where INSTR(upper(?),upper(PARTYCODE)) > 0";

}
