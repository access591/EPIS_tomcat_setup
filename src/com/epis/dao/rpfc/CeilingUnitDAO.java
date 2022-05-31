/**
  * File       : CeilingUnitDAO.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.dao.rpfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.epis.bean.rpfc.CeilingUnitBean;
import com.epis.bean.rpfc.FinancialYearBean;
import com.epis.bean.rpfc.PensionBean;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.bean.rpfc.DatabaseBean;
import com.epis.utilities.Log;


public class CeilingUnitDAO {

	Log log = new Log(CeilingUnitDAO.class);

	DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();

	protected int gridLength = commonUtil.gridLength();

	public boolean addCeilingRecord(CeilingUnitBean bean) throws SQLException {
		log.info("CeilingUnitDAO :addCeilingRecord() entering method");
		boolean isaddPensionRecord = false;

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int ceilingcode = 0;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			// maxceiling=this.getMaxCeiling();
			String sqlst = "select max(ceilingcode) ceilingcode from employee_ceiling_master";
			log.info("sql " + sqlst);

			rs = st.executeQuery(sqlst);

			if (rs.next()) {
				if (rs.getInt("ceilingcode") != 0) {
					ceilingcode = rs.getInt("ceilingcode") + 1;
				} else {
					ceilingcode = 1;
				}

			}
			log
					.info("CeilingUnitDAO:Maxceiling code is-------- "
							+ ceilingcode);

			String sql = "insert into employee_ceiling_master(CEILINGCODE,CEILINGRATE,WITHEFFCTDATE,WITHEFFCTTODATE)  VALUES ("
					+ ceilingcode
					+ ","
					+ bean.getRate()
					+ ",to_date('"
					+ bean.getFromWeDate()
					+ "','dd/Mon/yyyy'),to_date('"
					+ bean.getToWeDate() + "','dd/Mon/yyyy'))";
			log.info("sql in insert..................................." + sql);
			st.execute(sql);

			isaddPensionRecord = true;
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("CeilingUnitDAO :addCeilingRecord() leaving method");
		return isaddPensionRecord;
	}

	public boolean addInterestRecord(CeilingUnitBean bean) throws SQLException {
		log.info("CeilingUnitDAO :addInterestRecord() entering method");
		boolean isaddInterestRecord = false;

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int interestcode = 0;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			// maxceiling=this.getMaxCeiling();
			String sqlst = "select max(INTERESTCODE) INTERESTCODE from employee_pcinterestrate_master";
			log.info("------------sqlst---------------" + sqlst);
			rs = st.executeQuery(sqlst);
			if (rs.next()) {
				if (rs.getInt("INTERESTCODE") != 0) {
					interestcode = rs.getInt("INTERESTCODE") + 1;
				} else {
					interestcode = 1;
				}

			}
			log.info("CeilingUnitDAO:maxinterest is------------- "
					+ interestcode);

			String sql = "insert into employee_pcinterestrate_master(INTERESTCODE,INTERESTRATE,WITHEFFCTFROMDATE,WITHEFFCTTODATE)  VALUES ("
					+ interestcode
					+ ","
					+ bean.getInterestRate()
					+ ",to_date('"
					+ bean.getFromWeDate()
					+ "','dd/Mon/yyyy'),to_date('"
					+ bean.getToWeDate()
					+ "','dd/Mon/yyyy'))";
			log.info("sql in insert..................................." + sql);
			st.execute(sql);

			isaddInterestRecord = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("CeilingUnitDAO :addInterestRecord() leaving method");
		return isaddInterestRecord;
	}

	public boolean addUnitRecord(CeilingUnitBean bean) throws SQLException {
		log.info("CeilingUnitBean :addUnitRecord() entering method");
		boolean isaddUnitRecord = false;
		Connection conn = null;
		Statement st = null;
		String unitcode = "", unitname = "", unitoption = "", region = "";
		// int unitcode=0;

		try {

			conn = commonDB.getConnection();
			st = conn.createStatement();
			unitcode = bean.getUnitCode();
			unitname = bean.getUnitName();
			unitoption = bean.getUnitOption();
			region = bean.getRegion();

			String sql = "insert into employee_unit_master  VALUES ('"
					+ unitcode + "','" + unitname + "','" + unitoption + "','"
					+ region + "')";
			st.execute(sql);

			isaddUnitRecord = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("CeilingUnitDAO :addUnitRecord() leaving method");
		return isaddUnitRecord;
	}

	public ArrayList SearchCeilingAll(CeilingUnitBean dbBean, int start)
			throws Exception {
		log.info("CeilingUnitDAO :SearchCeilingAll() entering method");

		ArrayList hindiData = new ArrayList();
		CeilingUnitBean data = null;
		Connection con = null;
		System.out.println("start" + start + "end" + gridLength);
		String sqlQuery = this.buildQuery(dbBean);
		int countGridLength = 0;
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			if (rs.next()) {
				hindiData = new ArrayList();
				if (rs.absolute(start)) {
					countGridLength++;

					data = new CeilingUnitBean();

					if (rs.getInt("CEILINGCODE") != 0) {
						data.setCeilingCode(rs.getInt("CEILINGCODE"));
					} else {
						data.setCeilingCode(0);
					}
					if (rs.getDouble("CEILINGRATE") != 0.0) {
						data.setRate(rs.getDouble("CEILINGRATE"));
					} else {
						data.setRate(0.0);
					}
					if (rs.getString("WITHEFFCTDATE") != null) {
						data.setFromWeDate(rs.getString("WITHEFFCTDATE"));
					} else {
						data.setFromWeDate("");
					}
					if (rs.getString("WITHEFFCTTODATE") != null) {
						data.setToWeDate(rs.getString("WITHEFFCTTODATE"));
					} else {
						data.setToWeDate("");
					}

					hindiData.add(data);
				}
				while (rs.next() && countGridLength < gridLength) {

					data = new CeilingUnitBean();

					countGridLength++;

					if (rs.getInt("CEILINGCODE") != 0) {
						data.setCeilingCode(rs.getInt("CEILINGCODE"));
					} else {
						data.setCeilingCode(0);
					}
					if (rs.getDouble("CEILINGRATE") != 0.0) {
						data.setRate(rs.getDouble("CEILINGRATE"));
					} else {
						data.setRate(0.0);
					}
					if (rs.getString("WITHEFFCTDATE") != null) {
						data.setFromWeDate(rs.getString("WITHEFFCTDATE"));
					} else {
						data.setFromWeDate("");
					}
					if (rs.getString("WITHEFFCTTODATE") != null) {
						data.setToWeDate(rs.getString("WITHEFFCTTODATE"));
					} else {
						data.setToWeDate("");
					}

					
					hindiData.add(data);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			pst.close();
			rs.close();
			con.close();
		}
		log
		.info("CeilingUnitDAO :SearchPensionAll() Leaving method");
		return hindiData;

	}

	public ArrayList SearchUnitAll(CeilingUnitBean dbBean, int start,int gridLength)
			throws Exception {
		log.info("CeilingUnitDAO :SearchUnitAll() entering method");
		ArrayList hindiData = new ArrayList();
		CeilingUnitBean data = null;
		Connection con = null;
		log.info("start" + start + "end" + gridLength);

		String sqlQuery = this.buildQuery1(dbBean);
		int countGridLength = 0;
		System.out.println("Query is(retriveByAll)------" + sqlQuery);
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			if (rs.next()) {
				hindiData = new ArrayList();
				if (rs.absolute(start)) {
					countGridLength++;
					data = new CeilingUnitBean();

					if (rs.getString("unitcode") != null) {
						data.setUnitCode(rs.getString("unitcode"));
					} else {
						data.setUnitCode("");
					}
					if (rs.getString("unitname") != null) {
						data.setUnitName(rs.getString("unitname"));
					} else {
						data.setUnitName("");
					}
					if (rs.getString("unitoption") != null) {
						data.setUnitOption(rs.getString("unitoption"));
					} else {
						data.setUnitOption("");
					}
					if (rs.getString("region") != null) {
						data.setRegion(rs.getString("region"));
					} else {
						data.setRegion("");
					}

					hindiData.add(data);
				}
				while (rs.next() && countGridLength < gridLength) {

					data = new CeilingUnitBean();

					countGridLength++;

					if (rs.getString("unitcode") != null) {
						data.setUnitCode(rs.getString("unitcode"));
					} else {
						data.setUnitCode("");
					}
					if (rs.getString("unitname") != null) {
						data.setUnitName(rs.getString("unitname"));
					} else {
						data.setUnitName("");
					}

					if (rs.getString("unitoption") != null) {
						data.setUnitOption(rs.getString("unitoption"));
					} else {
						data.setUnitOption("");
					}
					if (rs.getString("region") != null) {
						data.setRegion(rs.getString("region"));
					} else {
						data.setRegion("");
					}
				
					hindiData.add(data);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pst.close();
			rs.close();
			con.close();
		}
		log.info("CeilingUnitDAO :SearchUnitAll() Leaving method");
		return hindiData;
	}

	public String checkWeDate(CeilingUnitBean dbBean) throws Exception {

		Connection con = null;
		Statement pst = null;
		ResultSet rs = null;
		// String wedate=dbBean.getFromWeDate();
		// double rate=dbBean.getRate();
		String flag = "no";
		String sqlQuery = "select  count(*) as  WITHEFFCTDATE from employee_ceiling_master WHERE WITHEFFCTDATE='"
				+ dbBean.getFromWeDate()
				+ "' AND WITHEFFCTTODATE='"
				+ dbBean.getToWeDate()
				+ "' AND CEILINGRATE="
				+ dbBean.getRate();
		int countGridLength = 0;
		log.info("Query is(retriveByAll)" + sqlQuery);

		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			rs = pst.executeQuery(sqlQuery);

			if (rs.next()) {

				if (rs.getInt("WITHEFFCTDATE") != 0) {
					flag = "yes";
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

	public String checkInterstWeDate(CeilingUnitBean dbBean) throws Exception {

		Connection con = null;
		Statement pst = null;
		ResultSet rs = null;
		StringBuffer sbf = new StringBuffer();
		String flag = "no";
		String sqlQuery = "select  count(*) as  WITHEFFCTDATE from employee_pcinterestrate_master  WHERE WITHEFFCTFROMDATE='"
				+ dbBean.getFromWeDate()
				+ "' AND WITHEFFCTTODATE='"
				+ dbBean.getToWeDate()
				+ "' AND INTERESTRATE="
				+ dbBean.getInterestRate();
		log.info("Query is(retriveByAll)" + sqlQuery);

		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			rs = pst.executeQuery(sqlQuery);
			if (rs.next()) {

				if (rs.getInt("WITHEFFCTDATE") != 0) {
					flag = "yes";
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		}
		return flag;
	}

	public String checkUnitCode(CeilingUnitBean dbBean) throws Exception {

		Connection con = null;
		PreparedStatement pst = null;
		Statement st = null;
		ResultSet rs = null;		
		String sqlQuery = "select count(*) as unitcode from employee_unit_master where (unitcode='"+dbBean.getUnitCode()+"' and region='"+dbBean.getRegion()+"') OR (unitname='"+dbBean.getUnitName()+"' and region='"+dbBean.getRegion()+"')";
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		String flag = "no";		
		try{
			con = commonDB.getConnection();
			st = con.createStatement();
			
			rs = st.executeQuery(sqlQuery);
			if (rs.next()) {
				if(rs.getInt("unitcode")!= 0){
					flag="yes";					
				}				
			}		
		}catch(Exception e){
			log.printStackTrace(e);
		}			
		return flag;}

	public ArrayList SearchPensionInfoReport(CeilingUnitBean dbBean)
			throws Exception {
		log.info("PensionData : SearchPensionInfoReport Entering method");
		ArrayList hindiData = new ArrayList();
		DatabaseBean data = null;
		Connection con = null;
		String sqlQuery = this.buildQuery(dbBean);
		int countGridLength = 0;
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = commonDB.getConnection();
			pst = con
					.prepareStatement(sqlQuery,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = pst.executeQuery();
			while (rs.next()) {
				data = new DatabaseBean();
				countGridLength++;
				if (rs.getString("NAME") != null) {
					data.setEmpName(rs.getString("NAME"));
				} else {
					data.setEmpName("");
				}
				if (rs.getString("CPFACCNO") != null) {
					data
							.setCpfACNumber(Integer.toString(rs
									.getInt("CPFACCNO")));
				} else {
					data.setCpfACNumber("");
				}
				if (rs.getString("DESEG") != null) {
					data.setDesegination(rs.getString("DESEG"));
				} else {
					data.setDesegination("");
				}
				if (rs.getString("SALPF") != null) {
					data.setSalary(Integer.toString(rs.getInt("SALPF")));
				} else {
					data.setSalary("");
				}
				if (rs.getString("PENSIONFUND") != null) {
					data.setPensionFund(Integer.toString(rs
							.getInt("PENSIONFUND")));
				} else {
					data.setPensionFund("");
				}
				if (rs.getString("PF") != null) {
					data.setPf(Integer.toString(rs.getInt("PF")));
				} else {
					data.setPf("");
				}
				log
						.info("CeilingUnitDAO :SearchPensionInfoReport() leaving method");
				hindiData.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			pst.close();
			rs.close();
			con.close();
		}
		return hindiData;
	}

	public int totalData(CeilingUnitBean dbBean) throws Exception {
		log.info("CeilingUnitDAO :totalData() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String selectSQL = this.buildQuery(dbBean);
		int totalRecords = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selectSQL);
			while (rs.next()) {
				totalRecords++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		log.info("CeilingUnitDAO :totalData() leaving method");
		return totalRecords;
	}

	public int totalRecords(CeilingUnitBean dbBean) throws Exception {

		log.info("CeilingUnitDAO :totalRecords() entering method");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String selectSQL = this.buildQuery1(dbBean);
		int totalRecords = 0;
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(selectSQL);
			while (rs.next()) {
				totalRecords++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		log.info("CeilingUnitDAO :totalRecords() leaving method");
		return totalRecords;
	}

	public String buildQuery(CeilingUnitBean bean) throws Exception {
		log.info("CeilingUnitDAO :buildQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";

		String sqlQuery = "select CEILINGCODE,CEILINGRATE,to_char(WITHEFFCTDATE,'dd/Mon/yyyy') WITHEFFCTDATE,to_char(WITHEFFCTTODATE,'dd/Mon/yyyy') WITHEFFCTTODATE from employee_ceiling_master ";

		if (!bean.getFromWeDate().equals("")) {
			whereClause.append(" WITHEFFCTDATE>=to_date('"
					+ bean.getFromWeDate() + "','dd/Mon/yyyy')");
			whereClause.append(" AND ");
		}
		if (!bean.getToWeDate().equals("")) {
			whereClause.append(" WITHEFFCTTODATE<= to_date('"
					+ bean.getToWeDate() + "','dd/Mon/yyyy')");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);

		if ((bean.getFromWeDate().equals(""))
				&& (bean.getToWeDate().equals(""))) {
			log.info("inside if");
		} else {
			log.info("inside else");

			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append("WHERE ");
			}
			query.append(this.sTokenFormat(whereClause));
		}
		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();
		log.info("PensionDAO :buildQuery() leaving method");

		return dynamicQuery;
	}

	public String buildQuery1(CeilingUnitBean bean) throws Exception {

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "select * from employee_unit_master  ";

		if (!bean.getUnitCode().equals("")) {
			whereClause.append("UNITCODE='" + bean.getUnitCode() + "'");
			whereClause.append(" AND ");
		}

		if (!bean.getUnitName().equals("")) {
			whereClause.append("UNITNAME='" + bean.getUnitName() + "'");
			whereClause.append(" AND ");
		}
		if (!bean.getUnitOption().equals("")) {
			whereClause.append("UNITOPTION='" + bean.getUnitOption() + "'");
			whereClause.append(" AND ");
		}

		if (!bean.getRegion().equals("")) {
			whereClause.append("REGION='" + bean.getRegion() + "'");
			whereClause.append(" AND ");
		}

		query.append(sqlQuery);

		if ((bean.getUnitCode().equals("")) && (bean.getUnitName().equals(""))
				&& (bean.getUnitOption().equals(""))
				&& (bean.getRegion().equals(""))) {

		} else {
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append("WHERE ");
			}
			query.append(this.sTokenFormat(whereClause));
		}
		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();
		return dynamicQuery;
	}

	private String sTokenFormat(StringBuffer stringBuffer) {

		StringBuffer whereStr = new StringBuffer();
		StringTokenizer st = new StringTokenizer(stringBuffer.toString());
		int count = 0;
		int stCount = st.countTokens();
		// && && count<=st.countTokens()-1st.countTokens()-1
		while (st.hasMoreElements()) {
			count++;
			if (count == stCount)
				break;
			whereStr.append(st.nextElement());
			whereStr.append(" ");
		}
		return whereStr.toString();
	}

	public void closeConnection(Connection con, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException se) {
				log.info("Problem in closing Statement." + se);
			}
		}

		try {
			if (con != null && !con.isClosed()) {
				con.close();
				con = null;
			}
		} catch (SQLException se) {
			log.info("Problem in closing Connection." + se);
		}
	}

	public boolean checkUser(String userName, String password) throws Exception {
		boolean isCheckUser = false;
		log.info("CeilingUnitDAO: checkUser() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			String query = "select username from emp_login where username='"
					+ userName + "' and password='" + password + "'";

			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			if (rs.next()) {
				isCheckUser = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}

		finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("CeilingUnitDAO: checkUser() leaving method");
		return isCheckUser;
	}

	public CeilingUnitBean editCeilingMaster(int ceilingcd) throws Exception {
		log.info("CeilingUnitDAO:editCeilingMaster entering method ");
		CeilingUnitBean editBean = new CeilingUnitBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String query = "select CEILINGCODE,CEILINGRATE,to_char(WITHEFFCTDATE,'dd/Mon/yyyy') WITHEFFCTDATE,to_char(WITHEFFCTTODATE,'dd/Mon/yyyy') WITHEFFCTTODATE from employee_ceiling_master where CEILINGCODE='"
				+ ceilingcd + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				if (rs.getInt("CEILINGCODE") != 0) {
					editBean.setCeilingCode(rs.getInt("CEILINGCODE"));
				} else {
					editBean.setCeilingCode(0);
				}
				if (rs.getDouble("CEILINGRATE") != 0.0) {

					editBean.setRate(rs.getDouble("CEILINGRATE"));
				} else {
					editBean.setRate(0.0);
				}
				if (rs.getString("WITHEFFCTDATE") != null) {
					editBean.setFromWeDate(rs.getString("WITHEFFCTDATE"));
				} else {
					editBean.setFromWeDate("");
				}
				if (rs.getString("WITHEFFCTTODATE") != null) {
					editBean.setToWeDate(rs.getString("WITHEFFCTTODATE"));
				} else {
					editBean.setToWeDate("");
				}

			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("CeilingUnitDAO:editCeilingMaster leaving method ");
		return editBean;
	}

	public CeilingUnitBean editUnitMaster(String unitcd, String region)
			throws Exception {
		log.info("CeilingUnitDAO:editUnitMaster entering method ");
		CeilingUnitBean editBean = new CeilingUnitBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String query = "select *  from employee_unit_master where unitcode='"
				+ unitcd + "' and  region='" + region + "'";
		log.info("query is ----" + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {

				if (rs.getString("unitcode") != null) {
					editBean.setUnitCode(rs.getString("unitcode"));
				} else {
					editBean.setUnitCode("");
				}
				if (rs.getString("UNITNAME") != null) {
					editBean.setUnitName(rs.getString("UNITNAME"));
				} else {
					editBean.setUnitName("");
				}
				if (rs.getString("UNITOPTION") != null) {
					editBean.setUnitOption(rs.getString("UNITOPTION"));
				} else {
					editBean.setUnitOption("");
				}
				if (rs.getString("region") != null) {
					editBean.setRegion(rs.getString("region"));
				} else {
					editBean.setRegion("");
				}
			}

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("CeilingUnitDAO:editUnitMaster leaving method ");
		return editBean;
	}

	public int updateCeilingMaster(CeilingUnitBean bean) throws Exception {

		log.info("CeilingUnitDAO:updateCeilingMaster entering method ");
		// PensionBean editBean =new PensionBean();
		Connection con = null;
		Statement st = null;
		int count = 0;
		String query = "update employee_ceiling_master set CEILINGRATE="
				+ bean.getRate() + ",WITHEFFCTDATE='" + bean.getFromWeDate()
				+ "',WITHEFFCTTODATE='" + bean.getToWeDate()
				+ "' where CEILINGCODE=" + bean.getCeilingCode() + "";

		log.info("query is " + query);
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			count = st.executeUpdate(query);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			st.close();
			con.close();
		}
		log.info("CeilingUnitDAO:updateCeilingMaster leaving method ");
		return count;
	}

	public int updateUnitMaster(CeilingUnitBean bean) throws Exception {

		log.info("CeilingUnitDAO:updateUnitMaster entering method ");
		// PensionBean editBean =new PensionBean();
		Connection con = null;
		Statement st = null;
		int count = 0;
		String unitname = "", hiddenunitcode = "", unitoption = "", unitcode = "", region = "", hiddenregion = "";
		unitcode = bean.getUnitCode();
		unitname = bean.getUnitName();
		unitoption = bean.getUnitOption();
		hiddenunitcode = bean.getHiddenUnitCode();
		region = bean.getRegion();
		hiddenregion = bean.getHiddenRegion();

		String query = "update employee_unit_master set UNITCODE='" + unitcode
				+ "',UNITNAME='" + unitname + "',UNITOPTION='" + unitoption
				+ "',REGION='" + region + "' where UNITCODE='" + hiddenunitcode
				+ "' and  REGION='" + hiddenregion + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			count = st.executeUpdate(query);
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			st.close();
			con.close();
		}
		log.info("CeilingUnitDAO:updateUnitMaster leaving method ");
		return count;
	}

	/** Creates a Connection to a Access Database */
	public static Connection getAccessDBConnection(String filename)
			throws SQLException {
		// filename = filename.replace('', '/').trim();
		final String accessDBURLPrefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
		final String accessDBURLSuffix = ";DriverID=22;READONLY=false}";
		String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;
		return DriverManager.getConnection(databaseURL, "", "");
	}

	public ArrayList getEmpAccno(String accessData) throws Exception {
		ArrayList a = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String query = "select * from emp order by acno";
		String employeeName = "", desegnation = "", accno = "";
		PensionBean ps = null;
		try {
			con = getAccessDBConnection(accessData);
			System.out.println("connectin is" + con);
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				accno = rs.getString("acno");
				employeeName = rs.getString("acname");
				desegnation = rs.getString("desig");
				ps = new PensionBean();
				ps.setEmployeeName(employeeName);
				ps.setAcno(accno);
				ps.setDesegnation(desegnation);
				a.add(ps);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {

			rs.close();
			st.close();
			con.close();
		}
		return a;
	}
	/*
	 *  Financial Year
	 */
	
	public boolean addFinancialYear(FinancialYearBean bean) throws SQLException {
		log.info("CeilingUnitDAO :addFinancialYear() entering method");
		boolean isaddPensionRecord = false;

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int financialcode = 0;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();		
			String sqlst = "select max(FINANCIALID) FINANCIALID from PENSION_FINANCIALYEAR";
			log.info("sql " + sqlst);

			rs = st.executeQuery(sqlst);

			if (rs.next()) {
				if (rs.getInt("FINANCIALID") != 0) {
					financialcode = rs.getInt("FINANCIALID") + 1;
				} else {
					financialcode = 1;
				}

			}					
			String range=bean.getFromDate()+"-"+bean.getToDate().substring(2);		

			String sql = "insert into PENSION_FINANCIALYEAR(FINANCIALID,FIANCIALYEAR,FINANCIALMONTH,DESCRIPTION,REMARKS)  VALUES ("
					+ financialcode
					+ ",'"
					+range
					+ "','"
					+ bean.getMonth()
					+ "','"
					+ bean.getDescription()
					+ "','"
					+bean.getRemarks()+"')";			
			st.execute(sql);

			isaddPensionRecord = true;
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("CeilingUnitDAO :addFinancialYear() leaving method");
		return isaddPensionRecord;
	}
	public ArrayList getFinancialYear() throws Exception {
		ArrayList a = new ArrayList();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String query = "select FINANCIALMONTH,TO_CHAR(TO_DATE(FINANCIALMONTH,'MM'),'Month') FINANCIALMONTHNAME,FIANCIALYEAR from PENSION_FINANCIALYEAR where FIANCIALYEAR=(select max(FIANCIALYEAR) from PENSION_FINANCIALYEAR)";
		
		FinancialYearBean fbean = null;
		try {
			con = commonDB.getConnection();			
			st = con.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {			
				fbean = new FinancialYearBean();
				if(rs.getString("FINANCIALMONTH")!=null){
					fbean.setMonthnumber(rs.getString("FINANCIALMONTH"));
					}else{
						fbean.setMonthnumber("");
					}
				if(rs.getString("FINANCIALMONTHNAME")!=null){
				fbean.setMonth(rs.getString("FINANCIALMONTHNAME"));
				}else{
					fbean.setMonth("");
				}
				if(rs.getString("FIANCIALYEAR")!=null){
					fbean.setFromDate(rs.getString("FIANCIALYEAR"));
				}else{
					fbean.setFromDate("");
				}
				a.add(fbean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {

			rs.close();
			st.close();
			con.close();
		}
		return a;
	}	
public ArrayList searchFinancialYearData(int start,int gridLength)
	throws Exception {
	log.info("CeilingUnitDAO :searchFinancialYearData() entering method");
	
	ArrayList hindiData = new ArrayList();
	FinancialYearBean data = null;
	Connection con = null;
	System.out.println("start" + start + "end" + gridLength);
	String sqlQuery ="select FINANCIALID,FIANCIALYEAR,TO_CHAR(TO_DATE(FINANCIALMONTH,'MM'),'Month') FINANCIALMONTH,DESCRIPTION from PENSION_FINANCIALYEAR";
	int countGridLength = 0;
	System.out.println("Query is(retriveByAll)" + sqlQuery);
	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
		con = commonDB.getConnection();
		pst = con
				.prepareStatement(sqlQuery,
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
		rs = pst.executeQuery();
		if (rs.next()) {
			hindiData = new ArrayList();
			if (rs.absolute(start)) {
				countGridLength++;
	
				data = new FinancialYearBean();
				
				if (rs.getInt("FINANCIALID") != 0) {
					data.setFinancialId(rs.getInt("FINANCIALID"));
				} else {
					data.setFinancialId(0);
				}	
				if (rs.getString("FIANCIALYEAR") != null) {
					data.setFromDate(rs.getString("FIANCIALYEAR"));
				} else {
					data.setFromDate("");
				}			
				if (rs.getString("FINANCIALMONTH") != null) {
					data.setMonth(rs.getString("FINANCIALMONTH"));
				} else {
					data.setMonth("");
				}
				if (rs.getString("DESCRIPTION") != null) {
					data.setDescription(rs.getString("DESCRIPTION"));
				} else {
					data.setDescription("");
				}	
				hindiData.add(data);
			}
			while (rs.next() && countGridLength < gridLength) {
	
				data = new FinancialYearBean();
	
				countGridLength++;
	
				if (rs.getInt("FINANCIALID") != 0) {
					data.setFinancialId(rs.getInt("FINANCIALID"));
				} else {
					data.setFinancialId(0);
				}
				if (rs.getString("FIANCIALYEAR") != null) {
					data.setFromDate(rs.getString("FIANCIALYEAR"));
				} else {
					data.setFromDate("");
				}
				if (rs.getString("FINANCIALMONTH") != null) {
					data.setMonth(rs.getString("FINANCIALMONTH"));
				} else {
					data.setMonth("");
				}
				if (rs.getString("DESCRIPTION") != null) {
					data.setDescription(rs.getString("DESCRIPTION"));
				} else {
					data.setDescription("");
				}	
				
				hindiData.add(data);
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		log.printStackTrace(e);
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		log.printStackTrace(e);
	} finally {
		pst.close();
		rs.close();
		con.close();
	}
	log
	.info("CeilingUnitDAO :searchFinancialYearData() Leaving method");
	return hindiData;	
}
public int totalFinancialYearData() throws Exception {
	log.info("CeilingUnitDAO :totalFinancialYearData() entering method");
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String selectSQL ="select FINANCIALID,FIANCIALYEAR,TO_CHAR(TO_DATE(FINANCIALMONTH,'MM'),'Month') FINANCIALMONTH,DESCRIPTION from PENSION_FINANCIALYEAR";
	int totalRecords = 0;
	try {
		con = commonDB.getConnection();
		st = con.createStatement();
		rs = st.executeQuery(selectSQL);
		while (rs.next()) {
			totalRecords++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		log.printStackTrace(e);
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		log.printStackTrace(e);
	} finally {
		st.close();
		rs.close();
		con.close();
	}
	log.info("CeilingUnitDAO :totalFinancialYearData() leaving method");
	return totalRecords;
}
public FinancialYearBean editFinancialYear(int financialId) throws Exception {
	log.info("CeilingUnitDAO:editFinancialYear entering method ");
	FinancialYearBean editBean = new FinancialYearBean();
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String query = "select FINANCIALID,FIANCIALYEAR,FINANCIALMONTH,TO_CHAR(TO_DATE(FINANCIALMONTH,'MM'),'Month') FINANCIALMONTHNAME,DESCRIPTION,REMARKS from PENSION_FINANCIALYEAR where FINANCIALID='"
			+ financialId + "'";
	log.info("query is " + query);

	try {
		con = DBUtility.getConnection();
		st = con.createStatement();
		rs = st.executeQuery(query);
		while (rs.next()) {

			if (rs.getInt("FINANCIALID") != 0) {
				editBean.setFinancialId(rs.getInt("FINANCIALID"));
			} else {
				editBean.setFinancialId(0);
			}		
			if (rs.getString("FIANCIALYEAR") != null) {
				editBean.setFromDate(rs.getString("FIANCIALYEAR"));
			} else {
				editBean.setFromDate("");
			}
			if (rs.getString("FINANCIALMONTH") != null) {
				editBean.setMonthnumber(rs.getString("FINANCIALMONTH"));
			} else {
				editBean.setMonthnumber("");
			}
			if (rs.getString("FINANCIALMONTHNAME") != null) {
				editBean.setMonth(rs.getString("FINANCIALMONTHNAME"));
			} else {
				editBean.setMonth("");
			}
			if (rs.getString("DESCRIPTION") != null) {
				editBean.setDescription(rs.getString("DESCRIPTION"));
			} else {
				editBean.setDescription("");
			}
			if (rs.getString("REMARKS") != null) {
				editBean.setRemarks(rs.getString("REMARKS"));
			} else {
				editBean.setRemarks("");
			}
		}

	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		rs.close();
		st.close();
		con.close();
	}
	log.info("CeilingUnitDAO:editFinancialYear leaving method ");
	return editBean;
}
public int updateFinancialYear(FinancialYearBean bean) throws Exception {

	log.info("CeilingUnitDAO:updateFinancialYear entering method ");
	Connection con = null;
	Statement st = null;
	int count = 0;
	String query = "update PENSION_FINANCIALYEAR set FINANCIALMONTH='"
			+ bean.getMonth()
			+ "',DESCRIPTION='"
			+ bean.getDescription()
			+ "',REMARKS='" 
			+ bean.getRemarks()
			+ "' where FINANCIALID="+ bean.getFinancialId()+"";

	log.info("query is " + query);
	try {
		con = DBUtility.getConnection();
		st = con.createStatement();
		count = st.executeUpdate(query);
	} catch (Exception e) {
		log.printStackTrace(e);
	} finally {
		st.close();
		con.close();
	}
	log.info("CeilingUnitDAO:updateFinancialYear leaving method ");
	return count;
}

}
