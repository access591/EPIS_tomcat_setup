package com.epis.dao.cashbookDummy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.epis.bean.cashbookDummy.BankMasterInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.UserTracking;

public class BankMasterDAO {

	Log log = new Log(BankMasterDAO.class);

	public ArrayList getBankList(BankMasterInfo info, String type)
			throws Exception {
		log.info("BankMasterDAO : getBankList : Entering method");

		ArrayList bankInfo = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			if (type.equals("rem")) {
				pst = con.prepareStatement(remSelectQuery);
				pst.setString(1, info.getBankName() + "%");
			} else if (type.equals("other")) {
				pst = con.prepareStatement(otherSelectQuery);
				pst.setString(1, info.getBankName() + "%");
				pst.setString(2, info.getAccountNo());
			} else if ("edit".equals(type)) {
				pst = con.prepareStatement(editQuery);
				pst.setString(1, (info.getBankName() == null ? "" : info
						.getBankName())
						+ "%");
				pst.setString(2, info.getAccountNo());
			} else {
				pst = con.prepareStatement(selectQuery);
				pst.setString(1, (info.getBankName() == null ? "" : info
						.getBankName().trim())
						+ "%");
				pst.setString(2, (info.getBranchName() == null ? "" : info
						.getBranchName().trim())
						+ "%");
				pst.setString(3, (info.getBankCode() == null ? "" : info
						.getBankCode().trim())
						+ "%");
				pst.setString(4, (info.getAccountNo() == null
						|| "null".equals(info.getAccountNo()) ? "" : info
						.getAccountNo().trim())
						+ "%");
			}

			rs = pst.executeQuery();
			while (rs.next()) {
				info = new BankMasterInfo();
				info.setBankName(rs.getString("BANKNAME"));
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setBankCode(rs.getString("BANKCODE"));
				info.setIFSCCode(rs.getString("IFSCCODE"));
				info.setAccountCode(rs.getString("ACCOUNTCODE"));
				info.setParticular(rs.getString("PARTICULAR"));
				info.setBranchName(rs.getString("BRANCHNAME"));
				info.setAddress(rs.getString("ADDRESS"));
				info.setPhoneNo(rs.getString("PHONENO"));
				info.setFaxNo(rs.getString("FAXNO"));
				info.setAccountType(rs.getString("ACCOUNTTYPE"));
				info.setNEFTRTGSCode(rs.getString("NEFT_RTGSCODE"));
				info.setMICRNo(rs.getString("MICRNO"));
				info.setContactPerson(rs.getString("CONTACTPERSON"));
				info.setMobileNo(rs.getString("MOBILENO"));
				info.setTrustType(rs.getString("TRUSTTYPE"));
				info.setUnitName(rs.getString("unitname"));
				info.setRegion(rs.getString("region"));
				bankInfo.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankMasterDAO : getBankList : leaving method");
		return bankInfo;
	}

	public void addBankRecord(BankMasterInfo info) throws Exception {
		log.info("BankMasterDAO : addBankRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(insertQuery);
			pst.setString(1, info.getBankName());
			pst.setString(2, info.getBranchName());
			pst.setString(3, info.getBankCode());
			pst.setString(4, info.getAddress());
			pst.setString(5, info.getPhoneNo());
			pst.setString(6, info.getFaxNo());
			pst.setString(7, info.getAccountCode());
			pst.setString(8, info.getAccountNo());
			pst.setString(9, info.getAccountType());
			pst.setString(10, info.getIFSCCode());
			pst.setString(11, info.getNEFTRTGSCode());
			pst.setString(12, info.getMICRNo());
			pst.setString(13, info.getContactPerson());
			pst.setString(14, info.getMobileNo());
			pst.setString(15, info.getEnteredBy());
			pst.setString(16, info.getTrustType());
			pst.setString(17, info.getUnitName());
			pst.setString(18, info.getRegion());
			pst.executeUpdate();

		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
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
		UserTracking.write(info.getEnteredBy(), info.getBankCode() + "-"
				+ info.getIFSCCode() + "-" + info.getNEFTRTGSCode() + "-"
				+ info.getAccountCode(), "S", "CB", info.getAccountNo(),"Bank Master");
		log.info("BankMasterDAO : addBankRecord : leaving method");
	}

	public boolean exists(BankMasterInfo info) throws Exception {
		log.info("BankMasterDAO : exists : leaving method");
		boolean exists = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(countQuery);
			pst.setString(1, info.getAccountNo());
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
		log.info("BankMasterDAO : exists : leaving method");
		return exists;
	}

	public void updateBankRecord(BankMasterInfo info) throws Exception {
		log.info("BankMasterDAO : updateBankRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(updateQuery);
			pst.setString(1, info.getBankName());
			pst.setString(2, info.getBranchName());
			pst.setString(3, info.getBankCode());
			pst.setString(4, info.getAddress());
			pst.setString(5, info.getPhoneNo());
			pst.setString(6, info.getFaxNo());
			pst.setString(7, info.getAccountCode());
			pst.setString(8, info.getAccountType());
			pst.setString(9, info.getIFSCCode());
			pst.setString(10, info.getNEFTRTGSCode());
			pst.setString(11, info.getMICRNo());
			pst.setString(12, info.getContactPerson());
			pst.setString(13, info.getMobileNo());
			pst.setString(14, info.getUnitName());
			pst.setString(15, info.getTrustType());
			pst.setString(16, info.getRegion());
			pst.setString(17, info.getAccountNo());
			pst.executeUpdate();
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
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
		UserTracking.write(info.getEnteredBy(), info.getBankCode() + "-"
				+ info.getIFSCCode() + "-" + info.getNEFTRTGSCode() + "-"
				+ info.getAccountCode(), "U", "CB", info.getAccountNo(),"Bank Master");
		log.info("BankMasterDAO : updateBankRecord : leaving method");
	}

	public void deleteBankRecord(String codes) throws Exception {
		log.info("BankMasterDAO : deleteBankRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(deleteQuery);
			pst.setString(1, codes);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}

		log.info("AccountingCodeDAO : deleteBankRecord : leaving method");
	}

	public List getBankDetails(BankMasterInfo info) throws Exception {
		log.info("BankMasterDAO : getBankDetails : Entering method");

		ArrayList bankInfo = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(bankQuery);
			pst.setString(1, info.getTrustType());
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new BankMasterInfo();
				info.setBankCode(rs.getString("BANKCODE"));
				info.setAccountCode(rs.getString("ACCOUNTCODE"));
				bankInfo.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankMasterDAO : getBankDetails : leaving method");
		return bankInfo;
	}

	public List getRegions() throws Exception {
		log.info("BankMasterDAO : getRegions : Entering method");

		ArrayList region = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(regionQuery);
			rs = pst.executeQuery();
			while (rs.next()) {
				region.add(rs.getString("region"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankMasterDAO : getRegions : leaving method");
		return region;
	}

	public Map getUnits(String region) throws Exception {
		log.info("BankMasterDAO : getUnits : Entering method");

		Map unit = new HashMap();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(unitQuery);
			pst.setString(1, region);
			rs = pst.executeQuery();
			while (rs.next()) {
				unit.put(rs.getString("unitcode"),rs.getString("unitname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		log.info("BankMasterDAO : getUnits : leaving method");
		return unit;
	}

	String selectQuery = "select * from CB_BANK_INFO bank ,CB_ACCOUNTCODE_INFO acc where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and  bank.deleteflag='N' and Upper(BANKNAME) like Upper(?) and Upper(BRANCHNAME) like Upper(?) and Upper(BANKCODE) like Upper(?) and Upper(ACCOUNTNO) like Upper(?)";

	String bankQuery = "select distinct trim(BANKCODE) BANKCODE,ACCOUNTCODE from CB_BANK_INFO bank where Upper(TRUSTTYPE) like Upper(?)";

	String countQuery = "select count(*) from CB_BANK_INFO  bank ,CB_ACCOUNTCODE_INFO acc where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and ACCOUNTNO = ?";

	String remSelectQuery = "select * from CB_BANK_INFO bank ,CB_ACCOUNTCODE_INFO acc  where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and Upper(BANKNAME) like Upper(?) and ACCOUNTNO Not in (select ACCOUNTNO from  BANKOPENINGBAL_INFO)";

	String otherSelectQuery = "select * from CB_BANK_INFO bank ,CB_ACCOUNTCODE_INFO acc where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and Upper(BANKNAME) like Upper(?) and ACCOUNTNO Not in (?)";

	String insertQuery = "insert into CB_BANK_INFO(BANKNAME,BRANCHNAME,BANKCODE,ADDRESS,PHONENO,FAXNO,ACCOUNTCODE,ACCOUNTNO,ACCOUNTTYPE,IFSCCODE,NEFT_RTGSCODE,MICRNO,CONTACTPERSON,MOBILENO,ENTEREDBY,TRUSTTYPE,UNITNAME,REGION) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	String editQuery = "select * from CB_BANK_INFO bank ,CB_ACCOUNTCODE_INFO acc where acc.ACCOUNTHEAD=bank.ACCOUNTCODE and Upper(BANKNAME) like Upper(?) and upper(ACCOUNTNO)=upper(?)";

	String updateQuery = "Update CB_BANK_INFO set BANKNAME=?,BRANCHNAME=?,BANKCODE=?,ADDRESS=?,PHONENO=?,FAXNO=?,ACCOUNTCODE=?,ACCOUNTTYPE=?,IFSCCODE=?,NEFT_RTGSCODE=?,MICRNO=?,CONTACTPERSON=?,MOBILENO=?,UNITNAME=?,TRUSTTYPE=?,REGION=? where ACCOUNTNO=?";

	String deleteQuery = "Delete from CB_BANK_INFO  Where INSTR(upper(?),upper(ACCOUNTNO)) > 0";

	String regionQuery = "select distinct region from employee_unit_master";

	String unitQuery = "select unitcode,unitname from employee_unit_master where upper(trim(region)) = upper(trim(?))";
}
