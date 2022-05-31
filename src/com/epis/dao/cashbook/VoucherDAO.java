package com.epis.dao.cashbook;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.epis.bean.admin.Bean;
import com.epis.bean.cashbook.AccountingCodeInfo;
import com.epis.bean.cashbook.BankBook;
import com.epis.bean.cashbook.VoucherDetails;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UserTracking;
import com.epis.services.cashbook.EmployeeService;

import com.epis.utilities.Authentication;

public class VoucherDAO {

	Log log = new Log(VoucherDAO.class);

	private VoucherDAO() {

	}

	private static final VoucherDAO dao = new VoucherDAO();

	public static VoucherDAO getInstance() {
		return dao;
	}
	

	public String addVoucherRecord(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : addVoucherRecord() entering method");
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBUtility.getConnection();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(getQueries("keyNoGenQuery"));
			pst.setString(1, info.getPreparedDt());
			pst.setString(2, info.getPreparedDt());
			rs = pst.executeQuery();
			while (rs.next()) {
				info.setKeyNo(rs.getString(1));
			}
		} catch (Exception e) {
			log.info("VoucherDAO : Exception : " + e.toString());
			throw e;

		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			pst = conn.prepareStatement(getQueries("insertQuery"));
			pst.setString(1, info.getKeyNo());
			pst.setString(2, info.getAccountNo());
			pst.setString(3, info.getFinYear());
			pst.setString(4, info.getTrustType());
			pst.setString(5, info.getVoucherType());
			pst.setString(6, info.getPartyType());
			pst.setString(7, info.getEmpPartyCode());
			pst.setString(8, info.getPreparedBy());
			pst.setString(9, info.getDetails());
			pst.setString(10, info.getPreparedDt());
			pst.setString(11, info.getEmpCpfacno());
			pst.setString(12, info.getEmpRegion());
			pst.setString(13, info.getPfidFlag());
			pst.setString(14, info.getEmployeeName());
			pst.setString(15, info.getTransactionType());
			pst.executeUpdate();

		} catch (Exception e) {
			conn.rollback();
			log.info("VoucherDAO : Exception : " + e.toString());
			throw e;

		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		UserTracking
				.write(info.getEnteredBy(), info.getAccountNo() + "-"
						+ info.getEmpPartyCode() + "-" + info.getPartyType()
						+ "-" + info.getVoucherType() + "-"
						+ info.getTrustType() + "-" + info.getFinYear(), "S",
						"CB", info.getKeyNo(), "Voucher Info");
		try {
			pst = conn.prepareStatement(getQueries("insertDtQuery"));
			List voucherDts = info.getVoucherDetails();
			int length = voucherDts.size();
			for (int i = 0; i < length; i++) {
				VoucherDetails voucherDt = (VoucherDetails) voucherDts.get(i);
				pst.setString(1, info.getKeyNo());
				pst.setString(2, voucherDt.getAccountHead());
				pst.setString(3, voucherDt.getMonthYear());
				pst.setDouble(4, voucherDt.getCredit());
				pst.setString(5, voucherDt.getDetails());
				pst.setDouble(6, voucherDt.getDebit());
				pst.setString(7, voucherDt.getChequeNo());
				pst.executeUpdate();
				UserTracking.write(info.getEnteredBy(), voucherDt
						.getAccountHead()
						+ "-"
						+ voucherDt.getMonthYear()
						+ "-"
						+ voucherDt.getCredit()
						+ "-"
						+ voucherDt.getDetails()
						+ "-"
						+ voucherDt.getDebit()
						+ "-"
						+ voucherDt.getChequeNo(), "S", "CB", info.getKeyNo(),
						"Voucher Details");
			}
		} catch (Exception e) {
			conn.rollback();
			log.info("VoucherDAO : Exception : " + e.toString());
			throw e;

		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (!"C".equals(info.getVoucherType())
					&& "E".equals(info.getPartyType())
					&& !"true".equals(info.getPfidFlag())) {
				EmployeeService empService = new EmployeeService();
				empService.updateEmpDesig(info.getDesignation(), info
						.getEmpPartyCode());
				UserTracking.write(info.getEnteredBy(), info.getDesignation(),
						"U", "CB", info.getEmpPartyCode(),
						"Update Employee Desig. Details");
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.info("VoucherDAO : Exception : " + e.toString());
			throw e;

		} finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		log.info("VoucherDAO : addVoucherRecord() leaving method");
		return info.getKeyNo();
	}

	public List searchRecords(VoucherInfo info, String type) throws Exception {
		log.info("VoucherDAO : searchRecords : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List voucherInfo = new ArrayList();
		String query = null;
		try {
			con = DBUtility.getConnection();

			if ("edit".equals(type)) {
				pst = con.prepareStatement(getQueries("editQuery"));
				pst.setString(1, (info.getKeyNo() != null ? info.getKeyNo()
						: ""));

			} else if ("A".equals(type)) {
				if (info.getPreparedDt() != null
						&& !"".equals(info.getPreparedDt().trim())) {
					query = getQueries("selectAppQuery") + " and preperation_dt = to_date('"
							+ info.getPreparedDt() + "','dd/mon/yyyy')";
				} else {
					query = getQueries("selectAppQuery");
				}
				pst = con.prepareStatement(query);
				pst.setString(1, info.getBankName() + "%");
				pst.setString(2, info.getFinYear() + "%");
				pst.setString(3, info.getVoucherType() + "%");
				pst.setString(4, info.getAccountNo() == null ? "%" : info
						.getAccountNo().trim()
						+ "%");
				pst.setString(5, info.getKeyNo() == null ? "%" : info
						.getKeyNo().trim()
						+ "%");
			} else {
				query = getQueries("selectQuery");
				if (info.getVoucherDt() != null
						&& !"".equals(info.getVoucherDt().trim())) {
					query = query + " and voucher_dt = to_date('"
							+ info.getVoucherDt() + "','dd/mon/yyyy')";
				}
				if (info.getPreparedDt() != null
						&& !"".equals(info.getPreparedDt().trim())) {
					query = query + " and PREPERATION_DT = to_date('"
							+ info.getPreparedDt() + "','dd/mon/yyyy')";
				}
				if (info.getVoucherNo() != null
						&& !"".equals(info.getVoucherNo().trim())) {
					query = query
							+ " and upper(voucher.voucherno) like upper('"
							+ info.getVoucherNo() + "%') ";
				}
				query=query+" order by voucher.ENTEREDDT desc";
				pst = con.prepareStatement(query);
				pst.setString(1, info.getBankName() + "%");
				pst.setString(2, info.getFinYear() + "%");
				pst.setString(3, info.getVoucherType() + "%");
				pst.setString(4, info.getAccountNo() == null ? "%" : info
						.getAccountNo().trim()
						+ "%");
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new VoucherInfo();
				info.setKeyNo(rs.getString("KEYNO"));
				info.setBankName(rs.getString("BANKNAME"));
				info.setFinYear(rs.getString("FYEAR"));
				info.setVoucherType(rs.getString("VOUCHERTYPE"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setVoucherNo(rs.getString("voucherNo"));
				info.setPreparedDt(rs.getString("preperation_dt"));
				info.setAmount(rs.getString("Amount"));
				info.setVoucherDt(rs.getString("voucher_dt"));
				voucherInfo.add(info);
			}
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
		log.info("VoucherDAO : searchRecords : leaving method");
		return voucherInfo;
	}

	public VoucherInfo getReport(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : getReport : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			log.info("VoucherDAO : getReport : Report Parent Table");
			pst = con.prepareStatement(getQueries("reportQuery"));
			pst.setString(1, info.getKeyNo());
			rs = pst.executeQuery();
			if (rs.next()) {
				info = new VoucherInfo();
				info.setKeyNo(rs.getString("KEYNO"));
				info.setBankName(rs.getString("BANKNAME"));
				info.setAccountNo(rs.getString("accountNo"));
				info.setFinYear(rs.getString("FYEAR"));
				info.setVoucherType(rs.getString("VOUCHERTYPE"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setTrustType(rs.getString("trusttype"));
				info.setTransactionType(rs.getString("TRANSACTIONTYPE"));
				if (rs.getString("emp_party_code") != null) {
					info.setEmpPartyCode(rs.getString("emp_party_code"));
				} else {
					info.setEmpPartyCode("");
				}
				info.setPartyDetails(rs.getString("partyDetails"));
				info.setVoucherNo(rs.getString("voucherno"));
				info.setCheckedBy(rs.getString("checkedby"));
				info.setApprovedBy(rs.getString("approvedby"));
				info.setDetails(rs.getString("details"));
				info.setVoucherDt(rs.getString("VOUCHER_DT"));
				info.setStatus(rs.getString("APPROVAL"));
				info.setPreparedDt(rs.getString("preperation_dt"));
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
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
		try {
			log.info("VoucherDAO : getReport : Report Child Table");
			pst = con.prepareStatement(getQueries("reportDtQuery"));
			log.info(info.getKeyNo());
			pst.setString(1, info.getKeyNo());
			rs = pst.executeQuery();
			List voucherDts = new ArrayList();
			VoucherDetails voucherDt = null;
			while (rs.next()) {
				voucherDt = new VoucherDetails();
				voucherDt.setAccountHead(rs.getString("ACCOUNTHEAD"));
				voucherDt.setParticular(rs.getString("PARTICULAR"));
				voucherDt.setMonthYear(rs.getString("MONTH_YEAR"));
				voucherDt.setDetails(rs.getString("details"));
				voucherDt.setChequeNo(rs.getString("chequeno"));
				voucherDt.setDebit(Double.parseDouble(rs.getString("debit")));
				voucherDt.setCredit(Double.parseDouble(rs.getString("credit")));
				voucherDts.add(voucherDt);
			}
			info.setVoucherDetails(voucherDts);
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
		log.info("VoucherDAO : getReport : leaving method");
		return info;
	}

	public void updateApprovalVoucher(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : searchRecords : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("updateQuery"));
			pst.setString(1, info.getVoucherDt());
			pst.setString(2, info.getCheckedBy());
			pst.setString(3, info.getStatus());
			pst.setString(4, info.getApprovedBy());
			pst
					.setString(5, getVoucherNo(info.getKeyNo(), info
							.getVoucherDt()));
			pst.setString(6, info.getKeyNo());
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
		UserTracking.write(info.getEnteredBy(), info.getVoucherDt() + "-"
				+ info.getCheckedBy() + "-" + info.getApprovedBy() + "-"
				+ getVoucherNo(info.getKeyNo(), info.getVoucherDt()) + "-"
				+ info.getVoucherType() + "-" + info.getTrustType(), "S", "CB",
				info.getKeyNo(), "Voucher Info");
	}

	public BankBook getOpenBalance(String accountNo, String fromdate)
			throws Exception {
		log.info("VoucherDAO : getOpenBalance : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		BankBook book = new BankBook();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("bankOpenBalQuery"));
			pst.setString(1, accountNo);
			pst.setString(2, fromdate);
			pst.setString(3, accountNo);
			pst.setString(4, fromdate);
			pst.setString(5, accountNo);
			pst.setString(6, fromdate);
			pst.setString(7, accountNo);
			pst.setString(8, accountNo);
			pst.setString(9, fromdate);
			pst.setString(10, accountNo);
			pst.setString(11, accountNo);
			pst.setString(12, accountNo);
			pst.setString(13, accountNo);
			pst.setString(14, fromdate);

			rs = pst.executeQuery();

			if (rs.next()) {
				book.setOpeningBalAmt(rs.getDouble(1));
				book.setBankName(rs.getString("bankname"));
				book.setAmountType(rs.getString("amountType"));
				book.setAccountNo(accountNo);
			}

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
		log.info("VoucherDAO : getOpenBalance : Leaving method");
		return book;
	}
	public BankBook getBankReconcileOpenBalance(String accountNo, String fromdate)
			throws Exception {
		log.info("VoucherDAO : getOpenBalance : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		BankBook book = new BankBook();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("bankReconcileOpenBalQuery"));
			pst.setString(1, accountNo);
			pst.setString(2, fromdate);
			pst.setString(3, accountNo);
			pst.setString(4, fromdate);
			pst.setString(5, accountNo);
			pst.setString(6, fromdate);
			pst.setString(7, accountNo);
			pst.setString(8, accountNo);
			pst.setString(9, fromdate);
			pst.setString(10, accountNo);
			pst.setString(11, accountNo);
			pst.setString(12, accountNo);
			pst.setString(13, accountNo);
			pst.setString(14, fromdate);

			rs = pst.executeQuery();

			if (rs.next()) {
				book.setOpeningBalAmt(rs.getDouble(1));
				book.setBankName(rs.getString("bankname"));
				book.setAmountType(rs.getString("amountType"));
				book.setAccountNo(accountNo);
			}

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
		log.info("VoucherDAO : getOpenBalance : Leaving method");
		return book;

				}

	public List getBankBook(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : getBankBook : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List bookList = new ArrayList();
		List accountNos = getAccountNos(info.getAccountHead());
		con = DBUtility.getConnection();
		int size = accountNos.size();
		for (int cnt = 0; cnt < size; cnt++) {
			BankBook book = getOpenBalance((String) accountNos.get(cnt), info
					.getFromDate());
			List bankBookList = new ArrayList();
			BankBook bankBook = null;
			try {
				con = DBUtility.getConnection();
				pst = con.prepareStatement(getQueries("bankBookQuery"));
				pst.setString(1, (String) accountNos.get(cnt));
				pst.setString(2, (String) accountNos.get(cnt));
				pst.setString(3, (String) accountNos.get(cnt));
				pst.setString(4, (String) accountNos.get(cnt));
				pst.setString(5, (String) accountNos.get(cnt));
				pst.setString(6, (String) accountNos.get(cnt));
				pst.setString(7, info.getFromDate());
				pst.setString(8, info.getToDate());

				rs = pst.executeQuery();

				while (rs.next()) {
					bankBook = new BankBook();
					bankBook.setAccountHead(rs.getString("accounthead"));
					bankBook.setVoucherno(rs.getString("voucherno"));
					bankBook.setParticular(rs.getString("particular"));
					bankBook.setPartyName(rs.getString("partyname"));
					bankBook.setDescription(rs.getString("details"));
					bankBook.setPayments(rs.getDouble("debit"));
					bankBook.setReceipts(rs.getDouble("credit"));
					bankBook.setChequeNo(rs.getString("chequeno"));
					bankBook.setVoucherDate(rs.getString("voucher_dt"));
					bankBookList.add(bankBook);
				}
				book.setBankBookList(bankBookList);
			} catch (SQLException e) {
				log.printStackTrace(e);
				throw e;
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
			bookList.add(book);
		}
		con.close();
		log.info("VoucherDAO : getBankBook : leaving method");
		return bookList;
	}

	private List getAccountNos(String accountHead) throws Exception {
		log.info("VoucherDAO : getAccountNos : Entering method");
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("accNosQuery"));
			pst.setString(1, accountHead);
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
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
		log.info("VoucherDAO : getAccountNos : leaving method");
		return list;
	}

	public void updateVoucherRecord(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : updateVoucherRecord : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("updateVoucherQry"));
			pst.setString(1, info.getAccountNo());
			pst.setString(2, info.getFinYear());
			pst.setString(3, info.getTrustType());
			pst.setString(4, info.getPartyType());
			pst.setString(5, info.getEmpPartyCode());
			pst.setString(6, info.getPreparedBy());
			pst.setString(7, info.getDetails());
			pst.setString(8, info.getTransactionType());
			pst.setString(9, info.getKeyNo());
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
		UserTracking
				.write(info.getEnteredBy(), info.getAccountNo() + "-"
						+ info.getEmpPartyCode() + "-" + info.getPartyType()
						+ "-" + info.getVoucherType() + "-"
						+ info.getTrustType() + "-" + info.getFinYear(), "U",
						"CB", info.getKeyNo(), "Voucher Info");
		try {
			pst = con.prepareStatement(getQueries("deleteDtQuery"));
			pst.setString(1, info.getKeyNo());
			pst.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			pst = con.prepareStatement(getQueries("insertDtQuery"));
			List voucherDts = info.getVoucherDetails();
			int length = voucherDts.size();

			for (int i = 0; i < length; i++) {
				VoucherDetails voucherDt = (VoucherDetails) voucherDts.get(i);
				pst.setString(1, info.getKeyNo());
				pst.setString(2, voucherDt.getAccountHead());
				pst.setString(3, voucherDt.getMonthYear());
				pst.setDouble(4, voucherDt.getCredit());
				pst.setString(5, voucherDt.getDetails());
				pst.setDouble(6, voucherDt.getDebit());
				pst.setString(7, voucherDt.getChequeNo());
				pst.executeUpdate();
				UserTracking.write(info.getEnteredBy(), voucherDt
						.getAccountHead()
						+ "-"
						+ voucherDt.getMonthYear()
						+ "-"
						+ voucherDt.getCredit()
						+ "-"
						+ voucherDt.getDetails()
						+ "-"
						+ voucherDt.getDebit()
						+ "-"
						+ voucherDt.getChequeNo(), "U", "CB", info.getKeyNo(),
						"Voucher Details");
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			log.info("VoucherDAO : Exception : " + e.toString());
			throw e;

		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		log.info("VoucherDAO : updateVoucherRecord : leaving method");

	}

	public String getVoucherNo(String keyno, String approveddt)
			throws Exception {
		log.info("VoucherDAO : getVoucherNo : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String voucherNo = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("voucherNoQuery"));
			pst.setString(1, approveddt);
			pst.setString(2, approveddt);
			pst.setString(3, keyno);
			pst.setString(4, keyno);
			pst.setString(5, keyno);
			rs = pst.executeQuery();
			if (rs.next()) {
				voucherNo = rs.getString(1);
			}
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
		log.info("VoucherDAO : getVoucherNo : leaving method");
		return voucherNo;
	}

	public void deleteVoucher(String codes) throws Exception {
		log.info("VoucherDAO : deleteVoucher : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(getQueries("deleteVDTQuery"));
			pst.setString(1, codes);
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
			pst = con.prepareStatement(getQueries("deleteVQuery"));
			pst.setString(1, codes);
			pst.executeUpdate();
			con.commit();
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
		log.info("VoucherDAO : deleteVoucher : leaving method");
	}

	public List getLedgerAccount(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : getLedgerAccount : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("ledgerQuery"));
			pst.setString(1, info.getFromDate());
			pst.setString(2, info.getFromDate());
			pst.setString(3, info.getToDate());
			pst.setString(4, info.getToDate());
			pst.setString(5, info.getVoucherType() + "%");
			pst.setString(6, info.getBankCode() + "%");
			pst.setString(7, info.getTrustType() + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				BankBook book = new BankBook();
				book.setVoucherDate(rs.getString("vdate"));
				book.setAccountNo(rs.getString("accno"));
				book.setBankName(rs.getString("bank"));
				book.setParticular(rs.getString("particulars"));
				book.setVoucherType(rs.getString("vtype"));
				book.setVoucherno(rs.getString("NO"));
				book.setPayments(rs.getDouble("dt"));
				book.setReceipts(rs.getDouble("cr"));
				dataList.add(book);
			}
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
		log.info("VoucherDAO : getLedgerAccount : leaving method");
		return dataList;
	}

	public Map getGenLedger(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : getGenLedger : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map ledger = new LinkedHashMap();
		List ledgers = null;
		Map trustTypeBased = null;
		try {
			con = DBUtility.getConnection();
			
			System.out.println("Gen Ledger Query : " + getQueries("genLedgerQuery"));
			pst = con.prepareStatement(getQueries("genLedgerQuery"));
			pst.setString(1, info.getTrustType() + "%");
			pst.setString(2, info.getFromDate());
			pst.setString(3, info.getFromDate());
			pst.setString(4, info.getTrustType() + "%");
			pst.setString(5, info.getFromDate());
			pst.setString(6, info.getFromDate());
			pst.setString(7, info.getTrustType() + "%");
			pst.setString(8, info.getAccountHead() + "%");
			pst.setString(9, info.getVoucherType() + "%");
			pst.setString(10, info.getFromDate());
			pst.setString(11, info.getFromDate());
			pst.setString(12, info.getToDate());
			pst.setString(13, info.getToDate());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (ledger.containsKey(rs.getString("trusttype"))) {
					trustTypeBased = (LinkedHashMap) ledger.get(rs
							.getString("trusttype"));
					if (trustTypeBased.containsKey(rs.getString("Accountcode"))) {
						ledgers = (List) trustTypeBased.get(rs
								.getString("Accountcode"));
					} else {
						ledgers = new ArrayList();
					}
				} else {
					trustTypeBased = new LinkedHashMap();
					ledgers = new ArrayList();
				}
				BankBook ledgerAcc = new BankBook();
				ledgerAcc.setOpeningBalAmt(rs.getDouble("openingBal"));
				ledgerAcc.setTrustType(rs.getString("trusttype"));
				ledgerAcc.setAccountHead(rs.getString("Accountcode"));
				ledgerAcc.setVoucherDate(rs.getString("vdate"));
				ledgerAcc.setBankName(rs.getString("bank"));
				ledgerAcc.setDescription(rs.getString("details"));
				ledgerAcc.setVoucherType(rs.getString("vtype"));
				ledgerAcc.setVoucherno(rs.getString("voucherno"));
				ledgerAcc.setPayments(rs.getDouble("debit"));
				ledgerAcc.setReceipts(rs.getDouble("credit"));
				ledgerAcc.setPartyName(rs.getString("partyname"));
				ledgerAcc.setChequeNo(StringUtility.checknull(rs.getString("chequeNo")));
				ledgers.add(ledgerAcc);
				trustTypeBased.put(rs.getString("Accountcode"), ledgers);
				ledger.put(rs.getString("trusttype"), trustTypeBased);
			}
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
		log.info("VoucherDAO : getGenLedger : leaving method");
		return ledger;
	}

	public Map getTrialBalance(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : getTrialBalance : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map trialBal = new HashMap();
		List accountHeads = null;
		Map trustTypeBased = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("trailBalQuery"));
			pst.setString(1, info.getTrustType() + "%");
			pst.setString(2, info.getFromDate());
			pst.setString(3, info.getFromDate());
			pst.setString(4, info.getToDate());
			pst.setString(5, info.getToDate());
			pst.setString(6, info.getTrustType() + "%");
			pst.setString(7, info.getFromDate());
			pst.setString(8, info.getFromDate());
			pst.setString(9, info.getTrustType() + "%");
			pst.setString(10, info.getFromDate());
			pst.setString(11, info.getFromDate());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (trialBal.containsKey(rs.getString("trusttype"))) {
					trustTypeBased = (HashMap) trialBal.get(rs
							.getString("trusttype"));
					if (trustTypeBased.containsKey(rs.getString("type"))) {
						accountHeads = (List) trustTypeBased.get(rs
								.getString("type"));
					} else {
						accountHeads = new ArrayList();
					}
				} else {
					trustTypeBased = new HashMap();
					accountHeads = new ArrayList();
				}
				BankBook trialBalance = new BankBook();
				trialBalance.setTrustType(rs.getString("trusttype"));
				trialBalance.setAccountHeadType(rs.getString("type"));
				trialBalance.setAccountHead(rs.getString("Accountcode"));
				trialBalance
						.setOpeningBalAmtDebit(rs.getDouble("openBaldebit"));
				trialBalance.setOpeningBalAmt(rs.getDouble("openBalcredit"));
				trialBalance.setPayments(rs.getDouble("debit"));
				trialBalance.setReceipts(rs.getDouble("credit"));
				accountHeads.add(trialBalance);
				trustTypeBased.put(rs.getString("type"), accountHeads);
				trialBal.put(rs.getString("trusttype"), trustTypeBased);
			}
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
		log.info("VoucherDAO : getTrialBalance : leaving method");
		return trialBal;
	}

	public List ePaymentSearch(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : ePaymentSearch : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List voucherList = new ArrayList();
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("ePaymentsSearchQuery"));
			pst.setString(1, info.getFromDate());
			pst.setString(2, info.getFromDate());
			pst.setString(3, info.getToDate());
			pst.setString(4, info.getToDate());
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new VoucherInfo();
				info.setBankName(rs.getString("bankname"));
				info.setFinYear(rs.getString("FYEAR"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setKeyNo(rs.getString("keyno"));
				info.setTrustType(rs.getString("TRUSTTYPE"));
				info.setVoucherDt(rs.getString("voucher_dt"));
				info.setVoucherNo(rs.getString("VOUCHERNO"));
				voucherList.add(info);
			}
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
		log.info("VoucherDAO : ePaymentSearch : leaving method");
		return voucherList;
	}

	public void ePaymentTransfers(String keynos) throws Exception {
		log.info("VoucherDAO : ePaymentTransfers : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("ePaymentsQuery"));
			pst.setString(1, keynos);
			rs = pst.executeQuery();
			while (rs.next()) {
				
			}
			String path = createTextFile(new ArrayList());
			sendMail(path);
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
		log.info("VoucherDAO : ePaymentTransfers : leaving method");
	}

	private void sendMail(String path) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("aims.resource.Mail");
		String host = bundle.getString("host");
		String authid = bundle.getString("authid");		
		String authpwd = bundle.getString("authpwd");
		
		//		 Create properties for the Session
		Properties prop=new Properties();

		// If using static Transport.send(),need to specify the mail server here
		prop.put("mail.smtp.host", host);

		prop.put("mail.smtp.auth", "true");

        //Setting Authentication Mail Server UserID and Password 
		Authenticator auth = new Authentication(authid,authpwd);

		// Get a session
		Session session = Session.getInstance(prop,auth);
		MimeMessage message = new MimeMessage(session);
		InternetAddress add=new InternetAddress("rajani.nannuri@navayuga.com","AAI ");
		message.setFrom(add);
		message.setRecipients(Message.RecipientType.TO, "jayasree.kalapala@navayuga.com");
		message.setSubject("Bill");
		message.setSentDate(new java.util.Date());
		
		StringBuffer htmlText = new StringBuffer("<table><tr><td><font face='arial'>Dear Sir/Madam,<br>");
		htmlText.append("<br>Please find the Attached E-Payments.<BR>");
		htmlText.append("<BR><BR>Best Regards<BR>AAI<BR></font></td></tr>");
		
		BodyPart bodyPart1 = new MimeBodyPart();
		bodyPart1.setContent(htmlText.toString(), "text/html");
		
		//Adds Attechment:
		BodyPart bodyPart2 = new MimeBodyPart();
		
		//first attachment
		DataSource source = new FileDataSource(path);
		bodyPart2.setDataHandler(new DataHandler(source));
		bodyPart2.setFileName("E-Payments.txt"); 

		Multipart multipart = new MimeMultipart();

		//Create a related multi-part to combine the parts
		multipart.addBodyPart(bodyPart1);
        multipart.addBodyPart(bodyPart2);

		message.setContent(multipart);
		Transport.send(message);
	}

	public String createTextFile(List ePayments) {
		Writer writer = null;
		File file = null;
		try {
			String text = "This is a text file";
			file = new File("write.txt");			
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}
	
	public Map getAccountHeadsOpeningBalance(VoucherInfo vinfo) throws Exception {
		log.info("VoucherDAO : getAccountHeadsOpeningBalance : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map accounts = new LinkedHashMap();
		Map trustTypes = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("accountOpenBalQuery"));
			pst.setString(1, vinfo.getFromDate());
			pst.setString(2, vinfo.getFromDate());
			pst.setString(3, vinfo.getTrustType()+"%");
			pst.setString(4, vinfo.getAccountHead()+"%");
			rs = pst.executeQuery();
			while (rs.next()) {
				if(accounts.containsKey(rs.getString("ACCOUNTHEAD"))){
					trustTypes = (LinkedHashMap)accounts.get(rs.getString("ACCOUNTHEAD"));
				}else{
					trustTypes = new LinkedHashMap();
				}
				if("AIN".equals(rs.getString("TRUSTTYPE"))){
					if("".equals(vinfo.getTrustType()) || "A".equals(vinfo.getTrustType())){
						AccountingCodeInfo info = new AccountingCodeInfo();					
						info.setAccountHead(rs.getString("ACCOUNTHEAD"));
						info.setTrustType("AAI EPF");
						info.setAmount(rs.getString("openbal"));
						trustTypes.put("AAI EPF",info);
					}
					if("".equals(vinfo.getTrustType()) || "I".equals(vinfo.getTrustType())){
						AccountingCodeInfo info = new AccountingCodeInfo();
						info.setAccountHead(rs.getString("ACCOUNTHEAD"));
						info.setTrustType("IAAI ECPF");
						info.setAmount(rs.getString("openbal"));
						trustTypes.put("IAAI ECPF",info);
					}
					if("".equals(vinfo.getTrustType()) || "N".equals(vinfo.getTrustType())){
						AccountingCodeInfo info = new AccountingCodeInfo();
						info.setAccountHead(rs.getString("ACCOUNTHEAD"));
						info.setTrustType("NAA ECPF");
						info.setAmount(rs.getString("openbal"));
						trustTypes.put("NAA ECPF",info);
					}
				}else {
					AccountingCodeInfo info = new AccountingCodeInfo();
					info.setAccountHead(rs.getString("ACCOUNTHEAD"));
					info.setTrustType(rs.getString("TRUSTTYPE"));
					info.setAmount(rs.getString("openbal"));
					trustTypes.put(rs.getString("TRUSTTYPE"),info);
				}
				accounts.put(rs.getString("ACCOUNTHEAD"),trustTypes);
			}
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
		log.info("VoucherDAO : getAccountHeadsOpeningBalance : leaving method");
		return accounts;
	}
	
	public List search(VoucherInfo info) throws Exception {
		log.info("VoucherDAO : search : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List voucherInfo = new ArrayList();
		try {
			con = DBUtility.getConnection();
			String query = getQueries("selectQuery");
			if (!"".equals(StringUtility.checknull(info.getVoucherDt()).trim())) {
				query += " and voucher_dt = to_date('"+ info.getVoucherDt() + "','dd/mon/yyyy')";
			}
			if (!"".equals(StringUtility.checknull(info.getPreparedDt()).trim())) {
				query += " and PREPERATION_DT = to_date('"+ info.getPreparedDt() + "','dd/mon/yyyy')";
			}
			query = query+" order by voucher.ENTEREDDT desc";
			pst = con.prepareStatement(query);
			pst.setString(1, info.getBankName() + "%");
			pst.setString(2, info.getFinYear() + "%");
			pst.setString(3, info.getVoucherType() + "%");
			pst.setString(4, StringUtility.checknull(info.getAccountNo()).trim()+"%");
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new VoucherInfo();
				info.setKeyNo(rs.getString("KEYNO"));
				info.setBankName(rs.getString("BANKNAME"));
				info.setFinYear(rs.getString("FYEAR"));
				info.setVoucherType(rs.getString("VOUCHERTYPE"));
				info.setPartyType(rs.getString("PARTYTYPE"));
				info.setVoucherNo(rs.getString("voucherNo"));
				info.setPreparedDt(rs.getString("preperation_dt"));
				info.setAmount(rs.getString("Amount"));
				info.setVoucherDt(rs.getString("voucher_dt"));
				voucherInfo.add(info);
			}
		} catch (SQLException e) {
			throw new EPISException(e);
		} catch (Exception e) {
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("VoucherDAO : search : leaving method");
		return voucherInfo;
	}	
	
	private String getQueries(String queryName){
		
		HashMap queries = new HashMap();
		
		StringBuffer insertQuery = new StringBuffer("insert into Cb_VOUCHER_INFO(KEYNO,ACCOUNTNO,FYEAR,TRUSTTYPE");
		insertQuery.append(" ,VOUCHERTYPE,PARTYTYPE,EMP_PARTY_CODE,PREPAREDBY,details,preperation_dt,ecpfacno,");
		insertQuery.append(" eregion,pfidFlag,EMPLOYEENAME,TRANSACTIONTYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?");
		insertQuery.append(" ,?)");
		queries.put("insertQuery",insertQuery);

		StringBuffer insertDtQuery = new StringBuffer("insert into Cb_VOUCHER_DETAILS(KEYNO,ACCOUNTHEAD,MONTH_YEAR,");
		insertDtQuery.append(" credit,Details,debit,chequeno) values (?,?,?,?,?,?,?)");
		queries.put("insertDtQuery",insertDtQuery);

		StringBuffer selectQuery = new StringBuffer("select VOUCHER_DT voucher_dt,to_char(preperation_dt,");
		selectQuery.append(" 'dd/Mon/YYYY') preperation_dt,voucherNo,KEYNO,BANKNAME,FYEAR,Decode(VOUCHERTYPE");
		selectQuery.append(" ,'P','Payment','R','Receipt','C','Contra','') VOUCHERTYPE,Decode(PARTYTYPE,'E',");
		selectQuery.append(" 'Employee','P','Party','B','Bank','') PARTYTYPE,( select case when ");
		selectQuery.append(" voucher.vouchertype='P' then sum(dt.debit-dt.credit)||' Dr.' else ");
		selectQuery.append(" sum(dt.credit-dt.debit)||' Cr.' end from Cb_voucher_details dt where dt.keyno = ");
		selectQuery.append(" voucher.keyno)Amount from  Cb_VOUCHER_INFO voucher,Cb_BANK_INFO bank where bank.ACCOUNTNO");
		selectQuery.append(" = voucher.ACCOUNTNO and upper(BANKNAME) like upper(?) and FYEAR like ? and");
		selectQuery.append(" VOUCHERTYPE like ? and upper(voucher.ACCOUNTNO) like upper(?)  ");
		queries.put("selectQuery",selectQuery);

		StringBuffer selectAppQuery = new StringBuffer("select to_char(VOUCHER_DT,'dd/Mon/YYYY') voucher_dt,");
		selectAppQuery.append(" to_char(preperation_dt,'dd/Mon/YYYY') preperation_dt,voucherNo,KEYNO,BANKNAME,");
		selectAppQuery.append(" FYEAR,Decode(VOUCHERTYPE,'P','Payment','R','Receipt','C','Contra','') VOUCHERTYPE");
		selectAppQuery.append(" ,Decode(PARTYTYPE,'E','Employee','P','Party','B','Bank','') PARTYTYPE ,( select ");
		selectAppQuery.append(" case when voucher.vouchertype='P' then sum(dt.debit-dt.credit)||' Dr.' else ");
		selectAppQuery.append(" sum(dt.credit-dt.debit)||' Cr.' end from Cb_voucher_details dt where dt.keyno=");
		selectAppQuery.append(" voucher.keyno)Amount  from  Cb_VOUCHER_INFO voucher,Cb_BANK_INFO bank where ");
		selectAppQuery.append(" bank.ACCOUNTNO = voucher.ACCOUNTNO and upper(BANKNAME) like upper(trim(?))and");
		selectAppQuery.append(" FYEAR like ? and upper(VOUCHERTYPE) like upper(?) and voucherno is null and ");
		selectAppQuery.append(" upper(voucher.ACCOUNTNO) like upper(?) and upper(voucher.keyno) like upper(?) ");
		selectAppQuery.append(" order by voucher.ENTEREDDT ");
		queries.put("selectAppQuery",selectAppQuery);

		StringBuffer reportQuery = new StringBuffer(" SELECT Nvl(TRANSACTIONTYPE,'C') TRANSACTIONTYPE,");
		reportQuery.append(" Nvl(voucher.voucherNo,' ') voucherNo,voucher.accountNo accountNo,keyno, bankname,");
		reportQuery.append(" fyear,DECODE (vouchertype,'P', 'Payment','R', 'Receipt','C', 'Contra',' ') ");
		reportQuery.append(" vouchertype,Nvl(partytype,' ') partytype,DECODE ( voucher.trusttype,'I', ");
		reportQuery.append(" 'IAAI ECPF','N', 'NAA ECPF','A', 'AAI EPF',' ') trusttype,(CASE WHEN partytype =");
		reportQuery.append(" 'E' and voucher.PFIDFLAG='true' THEN ecpfacno WHEN partytype = 'B' THEN  (SELECT ");
		reportQuery.append(" bankname FROM Cb_bank_info WHERE accountno = emp_party_code) else emp_party_code  END )");
		reportQuery.append(" emp_party_code,(CASE  WHEN partytype = 'E' and voucher.PFIDFLAG='true' THEN  ");
		reportQuery.append(" employeename WHEN partytype = 'E'   THEN (SELECT employeename FROM ");
		reportQuery.append(" employee_personal_info WHERE pensionno = emp_party_code) ELSE   emp_party_code ");
		reportQuery.append(" END ) partyDetails,   Nvl(preparedby,' ') preparedby, Nvl(voucherno,' ') voucherno,");
		reportQuery.append(" Nvl(checkedby,' ') checkedby, Nvl(approvedby,' ') approvedby,Nvl(details,' ') details");
		reportQuery.append(" ,Nvl(to_char(VOUCHER_DT,'dd/Mon/YYYY'),' ') VOUCHER_DT,APPROVAL,to_char(preperation_dt");
		reportQuery.append(" ,'dd/Mon/YYYY') preperation_dt FROM Cb_VOUCHER_INFO voucher, Cb_bank_info bank WHERE ");
		reportQuery.append(" bank.accountno = voucher.accountno AND keyno = ? ");
		queries.put("reportQuery",reportQuery);

		StringBuffer reportDtQuery = new StringBuffer("SELECT voucher.ACCOUNTHEAD ACCOUNTHEAD,acc.PARTICULAR");
		reportDtQuery.append(" PARTICULAR,MONTH_YEAR,Nvl(details,' ') details,Nvl(credit,0.0) credit,Nvl(debit,0.0)");
		reportDtQuery.append(" debit,Nvl(chequeno,'0') chequeno FROM Cb_voucher_details voucher, Cb_ACCOUNTCODE_INFO acc");
		reportDtQuery.append(" WHERE voucher.ACCOUNTHEAD = acc.ACCOUNTHEAD AND keyno = ? ");
		queries.put("reportDtQuery",reportDtQuery);

		StringBuffer updateQuery = new StringBuffer("update Cb_VOUCHER_INFO set VOUCHER_DT=?,CHECKEDBY=?,APPROVAL=?");
		updateQuery.append(" ,APPROVEDBY=?,voucherno=? where KEYNO=?");
		queries.put("updateQuery",updateQuery);

		StringBuffer bankOpenBalQuery = new StringBuffer("select (case when Nvl((select amountType from ");
		bankOpenBalQuery.append(" bankopeningbal_info  where accountno = ? and openeddate <= ?),'CR') = 'DR' then");
		bankOpenBalQuery.append(" (NVl((select amount from bankopeningbal_info where accountno = ? and openeddate ");
		bankOpenBalQuery.append(" <= ?),  0) + NVL(ncredit, 0) - Nvl(ndebit, 0)) else (NVl((select amount from ");
		bankOpenBalQuery.append(" bankopeningbal_info where accountno = ? and openeddate <= ?),  0) - NVL(ncredit,");
		bankOpenBalQuery.append(" 0) + Nvl(ndebit, 0))  end) OpeningBanace, (select bankname from bank_info where");
		bankOpenBalQuery.append(" accountno = ?) bankname,  decode((select amountType from bankopeningbal_info");
		bankOpenBalQuery.append(" where accountno = ? and openeddate <= ?), 'DR', 'Dr.', 'Cr.') amountType from");
		bankOpenBalQuery.append(" (select sum(decode(?||'C',info.accountno||info.vouchertype,nvl(credit, 0), ");
		bankOpenBalQuery.append(" nvl(debit, 0))) nDebit,sum(decode(?||'C',info.accountno||info.vouchertype,");
		bankOpenBalQuery.append(" nvl(debit, 0), nvl(credit, 0))) nCredit from Cb_voucher_details dt,(select keyno,");
		bankOpenBalQuery.append(" info.accountno,   vouchertype, voucher_dt,emp_party_code from Cb_VOUCHER_INFO");
		bankOpenBalQuery.append(" info,bankopeningbal_info openbal where (info.accountno = ? or ");
		bankOpenBalQuery.append(" (info.emp_party_code = ? and info.vouchertype = 'C')) and voucher_dt is not");
		bankOpenBalQuery.append(" null and  info.voucher_dt < ? and info.accountno = openbal.accountno(+) and ");
		bankOpenBalQuery.append("info.voucher_dt >= openbal.openeddate) info where dt.keyno = info.keyno)");
		queries.put("bankOpenBalQuery",bankOpenBalQuery);
		
		StringBuffer bankReconcileOpenBalQuery = new StringBuffer("select (case when Nvl((select amountType from ");
		bankReconcileOpenBalQuery.append(" cb_bankopeningbal_info  where accountno = ? and openeddate <= ?),'CR') = 'DR' then");
		bankReconcileOpenBalQuery.append(" (NVl((select amount from cb_bankopeningbal_info where accountno = ? and openeddate ");
		bankReconcileOpenBalQuery.append(" <= ?),  0) + NVL(ncredit, 0) - Nvl(ndebit, 0)) else (NVl((select amount from ");
		bankReconcileOpenBalQuery.append(" cb_bankopeningbal_info where accountno = ? and openeddate <= ?),  0) - NVL(ncredit,");
		bankReconcileOpenBalQuery.append(" 0) + Nvl(ndebit, 0))  end) OpeningBanace, (select bankname from CB_BANK_INFO where");
		bankReconcileOpenBalQuery.append(" accountno = ?) bankname,  decode((select amountType from cb_bankopeningbal_info");
		bankReconcileOpenBalQuery.append(" where accountno = ? and openeddate <= ?), 'DR', 'Dr.', 'Cr.') amountType from");
		bankReconcileOpenBalQuery.append(" (select sum(decode(?||'C',info.accountno||info.vouchertype,nvl(credit, 0), ");
		bankReconcileOpenBalQuery.append(" nvl(debit, 0))) nDebit,sum(decode(?||'C',info.accountno||info.vouchertype,");
		bankReconcileOpenBalQuery.append(" nvl(debit, 0), nvl(credit, 0))) nCredit from Cb_voucher_details dt,(select keyno,");
		bankReconcileOpenBalQuery.append(" info.accountno,   vouchertype, voucher_dt,emp_party_code from Cb_VOUCHER_INFO");
		bankReconcileOpenBalQuery.append(" info,cb_bankopeningbal_info openbal where (info.accountno = ? or ");
		bankReconcileOpenBalQuery.append(" (info.emp_party_code = ? and info.vouchertype = 'C')) and voucher_dt is not");
		bankReconcileOpenBalQuery.append(" null and  info.voucher_dt < ? and info.accountno = openbal.accountno(+) and ");
		bankReconcileOpenBalQuery.append("info.voucher_dt >= openbal.openeddate) info where dt.keyno = info.keyno)");
		queries.put("bankReconcileOpenBalQuery",bankReconcileOpenBalQuery);
		
		

		StringBuffer bankBookQuery = new StringBuffer("SELECT info.keyno, dt.accounthead, InitCap(acc.particular)");
		bankBookQuery.append(" particular, decode(?,emp_party_code,debit,credit) credit,decode(?,emp_party_code,");
		bankBookQuery.append(" credit,debit) debit, InitCap(Nvl(info.DETAILS,' ')) details, vouchertype,");
		bankBookQuery.append(" InitCap(partyname) partyname,info.voucherno,to_char(info.voucher_dt,'dd/Mon/YYYY')");
		bankBookQuery.append(" voucher_dt,Nvl(chequeno,'0') chequeno FROM Cb_voucher_details dt,(SELECT DETAILS,");
		bankBookQuery.append(" keyno, vouchertype,(CASE WHEN partytype = 'E' THEN (SELECT employeename FROM ");
		bankBookQuery.append(" employee_personal_info WHERE pensionno = emp_party_code) WHEN vouchertype = 'C'");
		bankBookQuery.append(" AND emp_party_code != ? THEN (SELECT bankname FROM bank_info WHERE accountno =");
		bankBookQuery.append(" emp_party_code) WHEN partytype = 'B' THEN (SELECT bankname FROM bank_info WHERE ");
		bankBookQuery.append(" accountno = info.accountno) ELSE  emp_party_code END ) partyname, ? accountno,");
		bankBookQuery.append(" voucherno,voucher_dt,emp_party_code,partytype FROM Cb_VOUCHER_INFO info WHERE ");
		bankBookQuery.append(" APPROVAL='Y' and (accountno = ? OR (vouchertype = 'C' AND emp_party_code = ?))");
		bankBookQuery.append(" and voucherno is not null and voucher_dt between ? and ?) info,accountcode_info");
		bankBookQuery.append(" acc WHERE info.keyno = dt.keyno and acc.ACCOUNTHEAD = dt.accounthead order by ");
		bankBookQuery.append(" info.voucher_dt,info.voucherno");
		queries.put("bankBookQuery",bankBookQuery);

		StringBuffer editQuery = new StringBuffer("select *,( select case when voucher.vouchertype='P' then ");
		editQuery.append(" sum(dt.debit-dt.credit)||' Dr.' else sum(dt.credit-dt.debit)||' Cr.' end from ");
		editQuery.append(" Cb_voucher_details dt where dt.keyno=voucher.keyno)Amount from  Cb_VOUCHER_INFO voucher,");
		editQuery.append(" BANK_INFO bank where bank.ACCOUNTNO = voucher.ACCOUNTNO and  KEYNO=?");
		queries.put("editQuery",editQuery);

		StringBuffer updateVoucherQry = new StringBuffer("update  Cb_VOUCHER_INFO set ACCOUNTNO=?,FYEAR=?,TRUSTTYPE=?");
		updateVoucherQry.append(" ,PARTYTYPE=?,EMP_PARTY_CODE=?,PREPAREDBY=?,details=?,TRANSACTIONTYPE=? where KEYNO=?");
		queries.put("updateVoucherQry",updateVoucherQry);

		StringBuffer deleteDtQuery = new StringBuffer(" delete from  Cb_voucher_details  where KEYNO=?");
		queries.put("deleteDtQuery",deleteDtQuery);

		StringBuffer deleteVQuery = new StringBuffer("delete from  Cb_VOUCHER_INFO where INSTR(upper(?),upper(KEYNO)) > 0");
		queries.put("deleteVQuery",deleteVQuery);

		StringBuffer deleteVDTQuery = new StringBuffer(" delete from  Cb_voucher_details  where INSTR(upper(?),upper(KEYNO)) > 0");
		queries.put("deleteVDTQuery",deleteVDTQuery);

		StringBuffer voucherNoQuery = new StringBuffer("SELECT  voucher.vouchertype|| 'V/'|| vinfo.fyear||");
		voucherNoQuery.append(" '/'|| SUBSTR (vinfo.accountno, LENGTH (vinfo.accountno) - 4, 5)|| '/'|| LPAD");
		voucherNoQuery.append(" (  (SELECT CASE WHEN (? >= TO_DATE ('01/nov/2009', 'dd-mm-yy') AND  hseries<500 )");
		voucherNoQuery.append(" THEN 500 WHEN ? >= TO_DATE ('01/nov/2009', 'dd-mm-yy') THEN to_number(hseries)");
		voucherNoQuery.append(" WHEN lseries < 500 THEN lseries END series  from ( SELECT (SELECT MAX");
		voucherNoQuery.append(" (TO_NUMBER (NVL (SUBSTR (voucherno,INSTR (voucherno, '/', -1) + 1 ),0))) no1");
		voucherNoQuery.append(" FROM Cb_VOUCHER_INFO  info ,(SELECT vouchertype FROM Cb_VOUCHER_INFO info where  keyno");
		voucherNoQuery.append(" = ?) vinfo WHERE voucherno IS NOT NULL and vinfo.vouchertype = info.vouchertype");
		voucherNoQuery.append(" AND NVL (SUBSTR (voucherno, INSTR (voucherno, '/', -1) + 1), 0) < 500) lseries,");
		voucherNoQuery.append(" (SELECT Nvl(MAX (NVL (SUBSTR (voucherno, INSTR (voucherno, '/', -1) + 1),0)),0)");
		voucherNoQuery.append(" no1 FROM Cb_VOUCHER_INFO   info ,(SELECT vouchertype FROM Cb_VOUCHER_INFO info where ");
		voucherNoQuery.append(" keyno = ?) vinfo WHERE voucherno IS NOT NULL and vinfo.vouchertype = ");
		voucherNoQuery.append(" info.vouchertype AND NVL (SUBSTR (voucherno, INSTR (voucherno, '/', -1) + 1), 0)");
		voucherNoQuery.append(" >= 500) hseries FROM DUAL)) + 1, 4, 0) FROM (SELECT SUBSTR (fyear, 3) fyear,");
		voucherNoQuery.append(" vouchertype, bankcode,info.accountno accountno  FROM Cb_VOUCHER_INFO info, Cb_bank_info");
		voucherNoQuery.append(" bank WHERE info.accountno = bank.accountno AND keyno = ?) vinfo, Cb_VOUCHER_INFO");
		voucherNoQuery.append(" voucher WHERE voucher.vouchertype = vinfo.vouchertype AND UPPER (NVL ");
		voucherNoQuery.append(" (voucher.voucherno, ' ')) != 'REJECTED' GROUP BY voucher.vouchertype, bankcode,");
		voucherNoQuery.append(" vinfo.fyear, vinfo.accountno");
		queries.put("voucherNoQuery",voucherNoQuery);

		StringBuffer keyNoGenQuery = new StringBuffer("select to_char(to_date(?,'dd/mon/yyyy'),'ddmmyy')||");
		keyNoGenQuery.append(" Lpad(Nvl(max(substr(KEYNO,7)),0)+1,6,'0') voucherNo from Cb_VOUCHER_INFO where ");
		keyNoGenQuery.append(" to_char(to_date(?,'dd/mon/yyyy'),'ddmmyy')=substr(KEYNO,0,6)");
		queries.put("keyNoGenQuery",keyNoGenQuery);

		StringBuffer ledgerQuery = new StringBuffer("SELECT   TO_CHAR (voucher_dt, 'dd/Mon/YYYY') vdate, ");
		ledgerQuery.append(" info.accountno accno,bank.bankcode || ' ' || bank.bankname bank, info.details ");
		ledgerQuery.append(" particulars,decode(vouchertype,'P','Payment','R','Receipt','Contra') vtype, ");
		ledgerQuery.append(" voucherno NO, case when vouchertype='P' then SUM (NVL (dt.debit, 0))-SUM (NVL");
		ledgerQuery.append(" (dt.credit, 0)) else 0 end dt,case when vouchertype='R' then SUM (NVL (dt.credit,");
		ledgerQuery.append(" 0))-SUM (NVL (dt.debit, 0)) when vouchertype='C' then SUM (NVL (dt.credit, 0)) else");
		ledgerQuery.append(" 0 end cr FROM Cb_VOUCHER_INFO info, Cb_voucher_details dt, Cb_bank_info bank WHERE voucherno");
		ledgerQuery.append(" IS NOT NULL AND voucherno != 'REJECTED' AND bank.accountno = info.accountno AND");
		ledgerQuery.append(" info.keyno = dt.keyno and voucher_dt between decode(?,'','01-JAN-1000',?) and ");
		ledgerQuery.append(" decode(?,'','31-JAN-9999',?) and voucherType like ? AND UPPER (bank.bankcode)");
		ledgerQuery.append(" LIKE UPPER (?) AND UPPER (bank.trusttype) LIKE UPPER (?) GROUP BY voucher_dt,");
		ledgerQuery.append(" info.accountno,bank.bankcode,bank.bankname,info.details,vouchertype,voucherno ");
		ledgerQuery.append(" ORDER BY voucher_dt, voucherno");
		queries.put("ledgerQuery",ledgerQuery);

		StringBuffer accNosQuery = new StringBuffer("SELECT accountno from Cb_bank_info where accountcode = ? ");
		queries.put("accNosQuery",accNosQuery);

		StringBuffer genLedgerQuery = new StringBuffer("Select decode(vinfo.TRUSTTYPE,'N','NAA ECPF','I',");
		genLedgerQuery.append(" 'IAAI ECPF','A','AAI EPF') trusttype,vdt.accounthead || ' ' || ainfo.particular");
		genLedgerQuery.append(" Accountcode,to_char(vinfo.voucher_dt, 'dd/Mon/YYYY') vdate,(binfo.accountcode ");
		genLedgerQuery.append(" || ' ' || binfo.bankname) bank, trim(vinfo.details) details, decode(VOUCHERTYPE,");
		genLedgerQuery.append(" 'R', 'Receipt', 'P', 'Payment', 'C', 'Contra') vtype, vinfo.voucherno,");
		genLedgerQuery.append(" decode(vinfo.vouchertype, 'P', sum(vdt.debit) - sum(vdt.credit)) debit,");
		genLedgerQuery.append(" decode(vinfo.vouchertype, 'R', sum(vdt.credit) - sum(vdt.debit)) credit,");
		genLedgerQuery.append(" (select sum(credit) - sum(debit) from ((select info.accounthead ACCOUNTHEAD,");
		genLedgerQuery.append(" TRUSTTYPE trusttype, Nvl(decode(amounttype, 'CR', amount, 'DR', 0), 0) credit, ");
		genLedgerQuery.append(" Nvl(decode(amounttype, 'CR', 0, 'DR', amount), 0) debit from Cb_accountcode_info ");
		genLedgerQuery.append(" info, Cb_accountcode_details dt where upper(trim(TRUSTTYPE)) like upper(trim(?))");
		genLedgerQuery.append(" and info.accounthead = dt.accounthead and info.opendate <= decode(?, '',");
		genLedgerQuery.append(" '01/Jan/1000', ?)) union all (select dt.accounthead ACCOUNTHEAD, info.trusttype");
		genLedgerQuery.append(" trusttype, sum(credit) credit, sum(debit) debit from Cb_VOUCHER_INFO info,");
		genLedgerQuery.append(" Cb_voucher_details  dt, accountcode_info ainfo where dt.keyno = info.keyno and");
		genLedgerQuery.append(" ainfo.accounthead = dt.accounthead and upper(trim(info.trusttype)) like ");
		genLedgerQuery.append(" upper(trim(?)) and info.voucher_dt < decode(?, '', '01/Jan/1000', ?) and ");
		genLedgerQuery.append(" info.voucher_dt >= decode(ainfo.opendate,'','01/jan/1000', ainfo.opendate) ");
		genLedgerQuery.append(" group by dt.accounthead, info.trusttype, ainfo.particular)) where ACCOUNTHEAD ");
		genLedgerQuery.append(" = vdt.accounthead and trusttype = vinfo.TRUSTTYPE ) openingBal,(CASE WHEN ");
		genLedgerQuery.append(" partytype = 'E' and vinfo.PFIDFLAG='true' THEN EMPLOYEENAME WHEN partytype = ");
		genLedgerQuery.append(" 'E' THEN (SELECT EMPLOYEENAME FROM employee_personal_info WHERE PENSIONNO = ");
		genLedgerQuery.append(" emp_party_code) WHEN partytype = 'B' THEN  (SELECT bankname FROM Cb_bank_info ");
		genLedgerQuery.append(" WHERE accountno = emp_party_code) else emp_party_code  END ) partyname,(select");
		genLedgerQuery.append(" nvl(nvl(chequeno,'--') from Cb_voucher_details where keyno=''||vinfo.keyno||'' and rownum<2),'--') chequeNo  from ");
		genLedgerQuery.append(" Cb_VOUCHER_INFO  vinfo, Cb_voucher_details  vdt, Cb_bank_info binfo, Cb_accountcode_info ");
		genLedgerQuery.append(" ainfo where ainfo.accounthead = vdt.accounthead and binfo.accountno = ");
		genLedgerQuery.append(" vinfo.ACCOUNTNO and vdt.keyno = vinfo.keyno and vinfo.keyno in (select keyno");
		genLedgerQuery.append(" from Cb_VOUCHER_INFO info where info.voucherno is not null) and upper(trim(");
		genLedgerQuery.append(" vinfo.trustType)) like upper(trim(?)) and upper(trim(vdt.accounthead)) like");
		genLedgerQuery.append(" upper(trim(?)) and upper(trim(vinfo.vouchertype)) like upper(trim(?)) and ");
		genLedgerQuery.append(" vinfo.voucher_dt between decode(?, '', '01/Jan/1000', ?) and decode(?, '', ");
		genLedgerQuery.append(" '01/Jan/3000', ?) group by vinfo.voucherno, vinfo.voucher_dt, binfo.accountcode,");
		genLedgerQuery.append(" binfo.bankname, vinfo.details, vinfo.TRUSTTYPE, VOUCHERTYPE, vdt.accounthead,");
		genLedgerQuery.append(" ainfo.particular,partytype,vinfo.PFIDFLAG,EMPLOYEENAME,emp_party_code,vinfo.keyno");
		genLedgerQuery.append(" order by  vdt.accounthead, vinfo.voucher_dt");
		queries.put("genLedgerQuery",genLedgerQuery);

		StringBuffer trailBalQuery = new StringBuffer("select decode(trusttype, 'N', 'NAA ECPF', 'I', 'IAAI ECPF'");
		trailBalQuery.append(" , 'A', 'AAI EPF') trusttype,Decode(type,'','--','A','Asset','L','Liability','I',");
		trailBalQuery.append(" 'Income','E','Expenditure','T','Inter Trust','U','Inter Unit','C','Current Asset'");
		trailBalQuery.append(" ,'B','Current Liability','R','Inter Unit Remittance A/C') type, upper(acc) Accountcode,");
		trailBalQuery.append(" sum(dr) openBaldebit, sum(cr) openBalcredit, sum(debit) debit, sum(credit) credit");
		trailBalQuery.append(" from ((select ainfo.type type, dt.accounthead || ' ' || ainfo.particular acc,");
		trailBalQuery.append(" info.trusttype trusttype, 0 cr, 0 dr, sum(credit) credit, sum(debit) debit from");
		trailBalQuery.append(" Cb_VOUCHER_INFO info, Cb_voucher_details  dt, Cb_accountcode_info ainfo where dt.keyno =");
		trailBalQuery.append(" info.keyno and ainfo.accounthead = dt.accounthead and  upper(trim(info.trusttype))");
		trailBalQuery.append(" like upper(trim(?)) and  info.voucher_dt between decode(?, '', '01/jan/1000',?)");
		trailBalQuery.append(" and decode(?, '', '01/jan/3000', ?) group by dt.accounthead, info.trusttype,");
		trailBalQuery.append(" ainfo.particular, ainfo.type) union all (select  type, ACCOUNTHEAD acc,");
		trailBalQuery.append(" trusttype trusttype, sum(credit),sum(debit), 0 credit, 0 debit from ((select");
		trailBalQuery.append(" info.type type, info.accounthead || ' ' || info.particular ACCOUNTHEAD,");
		trailBalQuery.append(" TRUSTTYPE trusttype, Nvl(decode(amounttype, 'CR', amount, 'DR', 0), 0) credit,");
		trailBalQuery.append(" Nvl(decode(amounttype, 'CR', 0, 'DR', amount), 0)  debit from Cb_accountcode_info");
		trailBalQuery.append(" info, Cb_accountcode_details dt where upper(trim(TRUSTTYPE)) like upper(trim(?)) and");
		trailBalQuery.append(" info.accounthead = dt.accounthead and info.opendate <= decode(?, '', '01/Jan/1000', ?))");
		trailBalQuery.append(" union all (select ainfo.type type, dt.accounthead || ' ' || ainfo.particular ");
		trailBalQuery.append(" ACCOUNTHEAD, info.trusttype trusttype, sum(credit) credit, sum(debit) debit  ");
		trailBalQuery.append(" from Cb_VOUCHER_INFO info,Cb_voucher_details dt,Cb_accountcode_info ainfo  where dt.keyno");
		trailBalQuery.append(" = info.keyno and ainfo.accounthead = dt.accounthead and upper(trim(info.trusttype))");
		trailBalQuery.append(" like upper(trim(?)) and info.voucher_dt < decode(?, '', '01/Jan/1000',?) and ");
		trailBalQuery.append(" info.voucher_dt >= decode(ainfo.opendate,'','01/jan/1000',ainfo.opendate) group");
		trailBalQuery.append(" by dt.accounthead, info.trusttype,ainfo.particular, ainfo.type)) group by type,");
		trailBalQuery.append(" ACCOUNTHEAD,trusttype )) group by trusttype, type, acc order by trusttype, type,");
		trailBalQuery.append(" acc");
		queries.put("trailBalQuery",trailBalQuery);

		StringBuffer ePaymentsSearchQuery = new StringBuffer("select keyno,bankname,FYEAR,decode(vinfo.TRUSTTYPE,");
		ePaymentsSearchQuery.append(" 'N','NAA ECPF','I','IAAI ECPF','A','AAI EPF') TRUSTTYPE,decode(PARTYTYPE,'P','Party'");
		ePaymentsSearchQuery.append(" ,'E','Employee','B','Bank') PARTYTYPE,DETAILS,VOUCHERTYPE,VOUCHERNO,");
		ePaymentsSearchQuery.append(" to_char(voucher_dt,'dd/Mon/YYYY') voucher_dt  from Cb_VOUCHER_INFO vinfo,bank_info");
		ePaymentsSearchQuery.append(" binfo where binfo.accountno = vinfo.accountno and  VOUCHER_DT between decode(?,''");
		ePaymentsSearchQuery.append(" ,'01/Jan/1000',?) and decode(?,'','01/Jan/3000',?)  and VOUCHERTYPE = 'P' and APPROVAL='Y'");
		queries.put("ePaymentsSearchQuery",ePaymentsSearchQuery);

		StringBuffer ePaymentsQuery = new StringBuffer(" select * from  Cb_VOUCHER_INFO  where INSTR(upper(?),");
		ePaymentsQuery.append(" upper(KEYNO)) > 0");
		queries.put("ePaymentsQuery",ePaymentsQuery);
		
		StringBuffer accountOpenBalQuery = new StringBuffer("select ainfo.ACCOUNTHEAD|| ' ' || ainfo.particular ACCOUNTHEAD ,");
		accountOpenBalQuery.append(" decode(TRUSTTYPE,'N','NAA ECPF','I','IAAI ECPF','A','AAI EPF','AIN') TRUSTTYPE,");
		accountOpenBalQuery.append(" Nvl((case when OPENDATE <= ? then decode(AMOUNTTYPE, 'CR', AMOUNT, -AMOUNT) ");
		accountOpenBalQuery.append(" else 0 end) + Nvl((select sum(det.credit) - sum(det.debit)  from Cb_voucher_details ");
		accountOpenBalQuery.append(" det,Cb_VOUCHER_INFO info where info.keyno =det.keyno and info.voucher_dt > ? ");
		accountOpenBalQuery.append(" and ainfo.accounthead = det.accounthead and details.trusttype = ");
		accountOpenBalQuery.append(" info.trusttype),0),0) openbal,particular from accountcode_details details, ");
		accountOpenBalQuery.append(" accountcode_info ainfo where ainfo.accounthead = details.accounthead(+) and  ");
		accountOpenBalQuery.append(" (trusttype ='' or trusttype like ?) and ainfo.ACCOUNTHEAD like ? order by ainfo.accounthead, trusttype ");
		queries.put("accountOpenBalQuery",accountOpenBalQuery);
		
		
		StringBuffer voucherQuery = new StringBuffer("select decode(VOUCHERTYPE,'R','Reciept','P','Payment','C', 'Contra') VOUCHERTYPE,");
			
		voucherQuery.append(" ACCOUNTNO,info.KEYNO KEYNO,TRUSTTYPE,to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,");		
		voucherQuery.append(" VOUCHERNO,decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit)) credit, ");
		voucherQuery.append(" decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit)) debit,(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))+ decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) voucheramt   from cb_voucher_info info, cb_voucher_details details where VOUCHER_DT<=? and VOUCHER_DT>='01 Apr 2011' ");
		voucherQuery.append(" and info.keyno = details.keyno and RECONCILE_STATUS='N' and ACCOUNTNO=? group by VOUCHER_DT,VOUCHERNO,VOUCHERTYPE, ACCOUNTNO,info.KEYNO,TRUSTTYPE order by to_date(VOUCHER_DT,'dd/mm/yyyy'),VOUCHERNO"); 
		
		queries.put("voucherQuery",voucherQuery);
		
		
		StringBuffer unReconcilevoucherQuery = new StringBuffer("select decode(VOUCHERTYPE,'R','Reciept','P','Payment','C', 'Contra') VOUCHERTYPE,");
		
		unReconcilevoucherQuery.append(" decode(?,ACCOUNTNO,ACCOUNTNO,EMP_PARTY_CODE)ACCOUNTNO,info.KEYNO KEYNO,TRUSTTYPE,to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,");		
		unReconcilevoucherQuery.append(" VOUCHERNO,(case when (vouchertype='C') then (decode(?, emp_party_code, sum(debit), sum(credit))) else(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))) end) credit, ");
		unReconcilevoucherQuery.append(" (case when (vouchertype='C') then decode(?, emp_party_code, sum(credit), sum(debit)) else  (decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) end) debit,(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))+ decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) voucheramt   from cb_voucher_info info, cb_voucher_details details where VOUCHER_DT<=? and VOUCHER_DT>='01 Apr 2012' ");
		unReconcilevoucherQuery.append(" and info.keyno = details.keyno and (RECONCILE_STATUS='N' or(vouchertype='C' and (ACCOUNTFLAG='N' or EMPACCOUNTFLAG='N')) )  and (ACCOUNTNO=? or (vouchertype = 'C' AND emp_party_code =?)) group by VOUCHER_DT,VOUCHERNO,VOUCHERTYPE, ACCOUNTNO,info.KEYNO,TRUSTTYPE,emp_party_code order by to_date(VOUCHER_DT,'dd/mm/yyyy'),VOUCHERNO"); 
		
		queries.put("unReconcilevoucherQuery",unReconcilevoucherQuery);
		
		
		
		StringBuffer ReconcilevoucherQuery = new StringBuffer("select sum(credit)bankbookcredit,sum(debit)bankbookdebit from(select decode(VOUCHERTYPE,'R','Reciept','P','Payment','C', 'Contra') VOUCHERTYPE,");
		
		ReconcilevoucherQuery.append(" ACCOUNTNO,info.KEYNO KEYNO,TRUSTTYPE,to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,");		
		ReconcilevoucherQuery.append(" VOUCHERNO,(case when (vouchertype='C') then (decode(?, emp_party_code, sum(debit), sum(credit))) else(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))) end) credit, ");
		ReconcilevoucherQuery.append(" (case when (vouchertype='C') then decode(?, emp_party_code, sum(credit), sum(debit)) else  (decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) end) debit,(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))+ decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) voucheramt   from cb_voucher_info info, cb_voucher_details details where VOUCHER_DT<=? and VOUCHER_DT>='01 Apr 2012' ");
		ReconcilevoucherQuery.append(" and info.keyno = details.keyno and (RECONCILE_STATUS='N' or(vouchertype='C' and (ACCOUNTFLAG='N' or EMPACCOUNTFLAG='N')) )  and (ACCOUNTNO=? or (vouchertype = 'C' AND emp_party_code =?)) group by VOUCHER_DT,VOUCHERNO,VOUCHERTYPE, ACCOUNTNO,info.KEYNO,TRUSTTYPE,emp_party_code order by to_date(VOUCHER_DT,'dd/mm/yyyy'),VOUCHERNO)"); 
		
		queries.put("ReconcilevoucherQuery",ReconcilevoucherQuery);
		
		return queries.get(queryName).toString();
	}

	public List getFinYears() {
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+((Calendar.MONTH>2)?1:0));
		String[] yearArray = StringUtility.getFinYears("2003",year);
		 Bean years = null;
		 int len = yearArray.length;
		 List finYears = new ArrayList();
		 for(int i=0;i<len;i++ ){
			 years = new Bean(yearArray[i],yearArray[i]);
			 finYears.add(years);
		 }		 		 
		 return finYears;
	}
	public List unreconcileVouchers(String  date,String accountNo) throws Exception {
		log.info("VoucherDAO : showReport : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			con = DBUtility.getConnection();
			String query = getQueries("unReconcilevoucherQuery");
			
			
			
			pst = con.prepareStatement(query);
			log.info("account no in dao is "+accountNo);
			pst.setString(1,accountNo);
			pst.setString(2,accountNo);
			pst.setString(3,accountNo);	
			pst.setString(4,date);
			pst.setString(5,accountNo);	
			pst.setString(6,accountNo);
			rs = pst.executeQuery();
			while (rs.next()) {
				VoucherInfo voucher = new VoucherInfo();
				voucher.setKeyNo(rs.getString("KEYNO"));
				voucher.setVoucherDt(rs.getString("VOUCHER_DT"));
				voucher.setAccountNo(rs.getString("ACCOUNTNO"));
				voucher.setTrustType(rs.getString("TRUSTTYPE"));
				voucher.setVoucherNo(rs.getString("VOUCHERNO"));
				voucher.setVoucherType(rs.getString("VOUCHERTYPE"));
				voucher.setDebitAmount(rs.getString("debit"));
				voucher.setCreditAmount(rs.getString("credit"));
				voucher.setAmount(rs.getString("voucheramt"));
				dataList.add(voucher);
			}
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
		log.info("VoucherDAO : getLedgerAccount : leaving method");
		return dataList;
	}
	
	public VoucherInfo bankreconcileReport(String  date,String accountNo)throws Exception {
		
		Connection con = null;
		PreparedStatement pst = null;
		PreparedStatement bankpst=null;
		PreparedStatement bankclosest=null;
		ResultSet rs = null;
		ResultSet bankrs=null;
		ResultSet rs2=null;
		int count=0;
		VoucherInfo info=new VoucherInfo();
		
		try{
			con=DBUtility.getConnection();
			String query = getQueries("ReconcilevoucherQuery");
			pst = con.prepareStatement(query);
			
			pst.setString(1,accountNo);
			pst.setString(2,accountNo);	
			pst.setString(3,date);
			pst.setString(4,accountNo);	
			pst.setString(5,accountNo);	
			rs = pst.executeQuery();
			int voucherCount=DBUtility.getRecordCount("select count(*)   from (select decode(VOUCHERTYPE,'R','Reciept','P','Payment','C','Contra') VOUCHERTYPE,ACCOUNTNO,info.KEYNO KEYNO,TRUSTTYPE,to_char(VOUCHER_DT, 'dd/Mon/YYYY') VOUCHER_DT,VOUCHERNO,decode(VOUCHERTYPE,'R',sum(credit) - sum(debit),'P',0,sum(credit)) credit,decode(VOUCHERTYPE,'P',sum(debit) - sum(credit),'R',0,sum(debit)) debit,(decode(VOUCHERTYPE,'R',sum(credit) - sum(debit),'P',0,sum(credit)) +decode(VOUCHERTYPE,'P',sum(debit) - sum(credit),'R',0,sum(debit))) voucheramt from cb_voucher_info info, cb_voucher_details details where VOUCHER_DT <= '"+date+"' and VOUCHER_DT >= '01 Mar 2011' and info.keyno = details.keyno and RECONCILE_STATUS = 'N' and  ACCOUNTNO = '"+accountNo+"'  group by VOUCHER_DT,VOUCHERNO,VOUCHERTYPE,ACCOUNTNO,info.KEYNO,TRUSTTYPE order by to_date(VOUCHER_DT, 'dd/mm/yyyy'), VOUCHERNO)");
			if(voucherCount>0)
			{
				count=count+1;
			}
			if(rs.next())
			{
				info.setBankbookCredit(StringUtility.checknull(rs.getString("bankbookcredit")));
				info.setBankbookDebit(StringUtility.checknull(rs.getString("bankbookdebit")));
				
			}
			String BankQuery="select sum(CREDITAMOUNT)bankstcredit,sum(DEBITAMOUNT)bankstdebit from( select  to_char(TRANSACTIONDATE,'dd/mon/yyyy') TRANSACTIONDATE,to_char(VALUEDATE,'dd/mon/yyyy')VALUEDATE,nvl(CREDITAMOUNT,decode(DEBIT_CREDIT,'C',AMOUNT,0))CREDITAMOUNT,nvl(DEBITAMOUNT,decode(DEBIT_CREDIT,'D',AMOUNT,0))DEBITAMOUNT,(nvl(AMOUNT, 0)+ nvl(CREDITAMOUNT, 0)+nvl(DEBITAMOUNT,0))bankamount,DESCRIPTION,ACCOUNTNO,CHEQUENO,KEYNO from  cb_brs where RECONCILE_STATUS='N' and  ACCOUNTNO=? and TRANSACTIONDATE<=?)";
			bankpst=con.prepareStatement(BankQuery);
			bankpst.setString(1,accountNo);
			bankpst.setString(2,date);
			bankrs=bankpst.executeQuery();
			if(bankrs.next())
			{
				info.setBankCredit(StringUtility.checknull(bankrs.getString("bankstcredit")));
				info.setBankDebit(StringUtility.checknull(bankrs.getString("bankstdebit")));
				count=count+1;
			}
			
			String bankcloseQuery="select nvl(closingbal,0)closingbal from cb_brs where keyno=( select max(keyno) from cb_brs where transactiondate in(select max(transactiondate) from cb_brs where accountno=? and TRANSACTIONDATE<=?)   and accountno=? and TRANSACTIONDATE<=?)";
			
			bankclosest=con.prepareStatement(bankcloseQuery);
			bankclosest.setString(1,accountNo);
			bankclosest.setString(2,date);
			bankclosest.setString(3,accountNo);
			bankclosest.setString(4,date);
			rs2=bankclosest.executeQuery();
			if(rs2.next())
			{
				info.setBankClosingBal(StringUtility.checknull(rs2.getString("closingbal")));
				count=count+1;
			}
			
			if(count==3)
				info.setCheckingRecordCount("available");
			else
				info.setCheckingRecordCount("notAvailable");
			
			
		}
			
		catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				bankpst.close();
				bankclosest.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		
		
		
	}
		return info;
	}
}
