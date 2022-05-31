/**
  * File       : UserDAO.java
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.epis.bean.rpfc.DatabaseBean;
import com.epis.bean.rpfc.UserBean;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.DBUtility;
import com.epis.utilities.InvalidDataException;
import com.epis.utilities.Log;
import com.epis.utilities.security.DESCryptographer;

public class UserDAO {

	Log log = new Log(UserDAO.class);

	DBUtility commonDB = new DBUtility();

	CommonUtil commonUtil = new CommonUtil();

	DESCryptographer deCryp = new DESCryptographer();

	protected int gridLength = commonUtil.gridLength();

	public ArrayList SearchNewUserAll(UserBean dbBean, int start, int gridLength)
			throws Exception {
		log.info("UserDAO :SearchNewUserAll() entering method");

		ArrayList hindiData = new ArrayList();
		UserBean data = null;
		Connection con = null;
		DatabaseBean searchData = new DatabaseBean();
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
					data = new UserBean();
					if (rs.getInt("USERID") != 0) {
						data.setUserId(rs.getInt("USERID"));
					} else {
						data.setUserId(0);
					}

					if (rs.getString("USERNAME") != null) {
						data.setUserName(rs.getString("USERNAME"));
					} else {
						data.setUserName("");
					}
					if (rs.getString("REGION") != null) {
						data.setRegion(rs.getString("REGION"));
					} else {
						data.setRegion("");
					}
					if (rs.getString("EXPIREDATE") != null) {
						data.setExpiryDate(rs.getString("EXPIREDATE"));
					} else {
						data.setExpiryDate("");
					}
					hindiData.add(data);
				}

				while (rs.next() && countGridLength < gridLength) {

					data = new UserBean();
					countGridLength++;
					if (rs.getInt("USERID") != 0) {
						data.setUserId(rs.getInt("USERID"));
					} else {
						data.setUserId(0);
					}

					if (rs.getString("USERNAME") != null) {
						data.setUserName(rs.getString("USERNAME"));
					} else {
						data.setUserName("");
					}
					if (rs.getString("REGION") != null) {
						data.setRegion(rs.getString("REGION"));
					} else {
						data.setRegion("");
					}
					if (rs.getString("EXPIREDATE") != null) {
						data.setExpiryDate(rs.getString("EXPIREDATE"));
					} else {
						data.setExpiryDate("");
					}

					log
							.info("CeilingUnitDAO :SearchPensionAll() Leaving method");
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
		log.info("UserDAO :SearchNewUserAll() leaving method");
		return hindiData;

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

	public String buildQuery(UserBean bean) throws Exception {
		log.info("UserDAO :buildQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "";

		String sqlQuery = "select USERID,USERNAME,to_char(EXPIREDATE,'DD-MON-YYYY HH24:MI:SS') EXPIREDATE,REGION  from employee_user where deleteflag='N'";
		if (!bean.getUserName().equals("")) {
			whereClause.append("USERNAME='" + bean.getUserName().trim() + "'");
			whereClause.append(" AND ");
		}

		if (!bean.getRegion().equals("")) {
			log.info("in side if" + bean.getRegion());
			whereClause.append("REGION like('%" + bean.getRegion().trim() + "%')");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if ((bean.getUserName().equals("")) && (bean.getRegion().equals(""))) {

		} else {
			query.append(" AND ");
			query.append(this.sTokenFormat(whereClause));
		}

		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();
		log.info("UserDAO :buildQuery() leaving method");
		return dynamicQuery;
	}

	public int totalUserData(UserBean dbBean) throws Exception {
		log.info("UserDAO :totalUserData() entering method");
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
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		log.info("UserDAO :totalUserData() leaving method");
		return totalRecords;
	}

	public boolean checkUser(String userName, String password) throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: checkUser() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String passWord1="";
		password = deCryp.doEncrypt(password);
		String testpassword=deCryp.doDecrypt("Wcv/IpeaB9af4fIOyltR5g==");
		String testpassword1=deCryp.doDecrypt("LdhEqqewa6s=");
			
			
		log.info("testpassword " +testpassword+"testpassword1"+testpassword1 );
		String query = "";
		con = commonDB.getConnection();
		try {
			
			String query2="select username,password from employee_user";
			
			query = "select count(*) as count from employee_user where username='"
					+ userName
					+ "' and password='"
					+ password.trim()
					+ "' and deleteflag='N'";
			log.info("query is " + query);

			log.info("connection is " + con);
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("count") != 0) {
					isCheckUser = true;
				}
			}
			
			rs2 = st.executeQuery(query2);
			while (rs2.next()) {
				if (rs2.getString("username") != null) {
					String username=rs2.getString("username");
					 passWord1=rs2.getString("password").trim();
					passWord1 = passWord1.trim();
					log.info("userName : " +username + " passWord : "+passWord1);
				}
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
			rs2.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: checkUser() leaving method" + isCheckUser);
		return isCheckUser;
	}

	
	public int checkExpiryDate(String userName, String password)
			throws Exception {
		log.info("UserDAO: checkExpiryDate() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int flag = 0;
		password = deCryp.doEncrypt(password);
		try {
			String query = "select count(*) as EXPIREDATE from employee_user where  expiredate<=sysdate and username='"
					+ userName
					+ "' and password='"
					+ password
					+ "' and  deleteflag='N'";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			if (rs.next()) {

				if (rs.getInt("EXPIREDATE") != 0) {
					flag = 1;

					String updatequery = "update employee_user set DELETEFLAG='Y' where  expiredate<=sysdate and username='"
							+ userName + "' and password='" + password + "'";
					log.info("updatequery is----------------" + updatequery);
					st.executeUpdate(updatequery);
				}
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
		log.info("UserDAO: checkExpiryDate() leaving method");
		return flag;
	}

	public ArrayList getAirportNames() throws Exception {
		boolean isCheckAirport = false;
		log.info("UserDAO: getAirportNames() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList airports = new ArrayList();
		String unit = "";
		try {
			String query = "select distinct(airportcode)  from employee_info";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			while (rs.next()) {

				UserBean bean = new UserBean();
				if (rs.getString("airportcode") != null) {

					unit = rs.getString("airportcode").trim();
					if (!unit.equals("")) {
						bean.setAirport(unit);
					} else {
						bean.setAirport("");
					}
				}

				airports.add(bean);
			}
			log.info("airport list " + airports.size());

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
		log.info("UserDAO: getAirportNames() leaving method");
		return airports;
	}

	public String validateUser(UserBean bean) throws InvalidDataException {
		log.info("UserDAO: validateUser() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String flag = "no";
		String query = "select  count(*) as  USERNAME from employee_user WHERE USERNAME='"
				+ bean.getUserName() + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("USERNAME") != 0) {
					flag = "yes";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new InvalidDataException(e.getMessage());
		}
		log.info("UserDAO: validateUser() leaving method");
		return flag;
	}
	public String checkOldPassword(UserBean bean) throws Exception {
		log.info("UserDAO: checkOldPassword() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String flag = "no";
		// String pwd=bean.getOldPwd();
		String pwd = deCryp.doEncrypt(bean.getOldPwd());
		String query = "select  PASSWORD  from employee_user WHERE USERNAME='"
				+ bean.getUserName() + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("PASSWORD").equals(pwd)) {
					flag = "yes";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
		log.info("UserDAO: checkOldPassword() leaving method");
		return flag;
	}

	public ArrayList getUserNames() throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: getUserNames() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList users = new ArrayList();

		String user = "";
		try {
			String query = "select username from employee_user";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			while (rs.next()) {
				UserBean bean = new UserBean();
				user = (String) rs.getString("username").toString().trim();
				if (!user.equals("")) {
					bean.setUserName(user);
				} else {
					bean.setUserName("");
				}
				users.add(bean);
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
		log.info("UserDAO: getUserNames() leaving method");
		return users;
	}

	/*public ArrayList getEmployee(UserBean dbBean) throws Exception {
		log.info("UserDAO: getEmployee() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String returnstr = "";

		StringBuffer sbf = new StringBuffer();
		ArrayList airportList = new ArrayList();
		String sqlQuery = "select distinct(EMPLOYEENAME) from employee_info where AIRPORTCODE='"
				+ dbBean.getAirport() + "' order by employeename";
		int countGridLength = 0;
		log.info("Query is(retriveByAll)" + sqlQuery);

		// String unitcode=dbBean.getUnitCode();
		String empName = "";
		String flag = "no";
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {

				UserBean bean = new UserBean();
				if (rs.getString("EMPLOYEENAME") != null)
					empName = rs.getString("EMPLOYEENAME").trim();
				if (!empName.equals("")) {
					bean.setEmployee(empName);
				} else {
					bean.setEmployee("");
				}
				airportList.add(bean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("UserDAO: getEmployee() leaving method");
		return airportList;
	}
*/
	/*public ArrayList getDetails(UserBean dbBean) throws Exception {
		log.info("UserDAO: getDetails() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sbf = new StringBuffer();
		ArrayList airportList = new ArrayList();
		log.info("airport code " + dbBean.getAirport());
		// to_char(DATEOFBIRTH,'DD-MON-YYYY')
		String sqlQuery = "select nvl(DESEGNATION,'--') DESEGNATION,nvl(DEPARTMENT,'--') DEPARTMENT,nvl(PENSIONNUMBER,'--') PENSIONNUMBER,nvl(DATEOFBIRTH,'') DATEOFBIRTH,nvl(EMAILID,'--') EMAILID,nvl(DIVISION,'--') DIVISION from employee_info where EMPLOYEENAME='"
				+ dbBean.getEmployee()
				+ "' and airportcode='"
				+ dbBean.getAirport() + "'";
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			UserBean bean = new UserBean();

			while (rs.next()) {
				if (rs.getString("DESEGNATION") != null) {
					bean.setDesignation(rs.getString("DESEGNATION").trim());
				} else {
					bean.setDesignation("");
				}
				if (rs.getString("DEPARTMENT") != null) {
					bean.setDepartment(rs.getString("DEPARTMENT"));
				} else {
					bean.setDepartment("");
				}
				if (rs.getString("PENSIONNUMBER") != null) {
					bean.setPensionNo(rs.getString("PENSIONNUMBER"));
				} else {
					bean.setPensionNo("");
				}
				if (rs.getString("DATEOFBIRTH") != null) {
					bean.setDOB(CommonUtil.getStringtoDate(rs.getString(
							"DATEOFBIRTH").toString()));
				} else {
					bean.setDOB("");
				}
				if (rs.getString("EMAILID") != null) {
					bean.setEmail(rs.getString("EMAILID"));
				} else {
					bean.setEmail("");
				}
				if (rs.getString("DIVISION") != null) {
					bean.setDivision(rs.getString("DIVISION"));
				} else {
					bean.setDivision("");
				}
				airportList.add(bean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("UserDAO: getDetails() leaving method");
		return airportList;

	}*/

	public void confirmPwd(UserBean bean) throws Exception {
		boolean isCheckPwd = false;
		log.info("UserDAO: confirmPwd() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList users = new ArrayList();
		try {
			String query = "select password from employee_user where username='"
					+ bean.getUserName() + "'";
			log.info("query is --" + query);
			DESCryptographer descrpt = new DESCryptographer();
			String password = descrpt.doEncrypt(bean.getNewPwd().trim());
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				String pwdupdate = "update  employee_user set password='"
						+ password + "',PASSWORDCHANGEFLAG='Y' where username='" + bean.getUserName()
						+ "'";
				log.info("pwdupdate query-" + pwdupdate);
				st.executeUpdate(pwdupdate);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: confirmPwd() leaving method");
		// return isCheckPwd;
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

	public UserBean editUserMaster(int userId, String username)
			throws Exception {log.info("UserDAO:editUserMaster entering method ");
			UserBean editBean = new UserBean();
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String query = "select USERID," + "USERNAME," + "USERTYPE,"
					+ "to_char(EXPIREDATE, 'DD/MON/YYYY@HH:MIam') EXPIREDATE,"
					+ "REGION," + "AAICATEGORY," +"PRIMARYMODULE,"+ "GRIDLENGTH," + "REMARKS,"
					+ "PHONENUMBER," + "EMAILID" + " from employee_user"
					+ " where deleteflag = 'N' and USERID=" + userId
					+ " and  USERNAME='" + username + "'";

			log.info("query is --" + query);

			try {
				con = DBUtility.getConnection();
				st = con.createStatement();
				rs = st.executeQuery(query);

				String dob = "";

				while (rs.next()) {
					if (rs.getInt("USERID") != 0) {
						editBean.setUserId(rs.getInt("USERID"));
					} else {
						editBean.setUserId(0);
					}
					if (rs.getString("USERNAME") != null) {
						editBean.setUserName(rs.getString("USERNAME"));
					} else {
						editBean.setUserName("");
					}
					if (rs.getString("REGION") != null) {
						editBean.setRegion(rs.getString("REGION"));
					} else {
						editBean.setRegion("");
					}
					if (rs.getString("USERTYPE") != null) {
						editBean.setUserType(rs.getString("USERTYPE"));
					} else {
						editBean.setUserType("");
					}
					if (rs.getString("EXPIREDATE") != null) {
						editBean.setExpiryDate(rs.getString("EXPIREDATE"));
					} else {
						editBean.setExpiryDate("");
					}
					if (rs.getString("GRIDLENGTH") != null) {
						editBean.setGridLength(rs.getString("GRIDLENGTH"));
					} else {
						editBean.setGridLength("");
					}
					if (rs.getString("AAICATEGORY") != null) {
						editBean.setAaiCategory(rs.getString("AAICATEGORY"));
					} else {
						editBean.setAaiCategory("");
					}
					if (rs.getString("PRIMARYMODULE") != null) {
						editBean.setPrimaryModule(rs.getString("PRIMARYMODULE"));
					} else {
						editBean.setPrimaryModule("");
					}
					if (rs.getString("REMARKS") != null) {
						editBean.setRemarks(rs.getString("REMARKS"));
					} else {
						editBean.setRemarks("");
					}
					if (rs.getString("PHONENUMBER") != null) {
						editBean.setPhoneNo(rs.getString("PHONENUMBER"));
					} else {
						editBean.setPhoneNo("");
					}

					if (rs.getString("EMAILID") != null) {
						editBean.setEmail(rs.getString("EMAILID"));
					} else {
						editBean.setEmail("");
					}

				}// end while

			} catch (Exception e) {
				log.printStackTrace(e);
			} finally {
				rs.close();
				st.close();
				con.close();
			}
			log.info("UserDAO:editUserMaster leaving method ");
			return editBean;
			}

	public int updateUserMaster(UserBean bean) throws Exception {
		log.info("UserDAO:updateUserMaster entering method ");
		Connection con = null;
		Statement st = null;
		int count = 0;
		log.info("bean.getEmail()----" + bean.getEmail());

		String query = "update employee_user set  REGION='" + bean.getRegion()
		+ "',REMARKS='" + bean.getRemarks() + "',GRIDLENGTH='"
		+ bean.getGridLength() + "',AAICATEGORY='"
		+ bean.getAaiCategory() +"',PRIMARYMODULE='"+bean.getPrimaryModule()+"',EMAILID='" + bean.getEmail()
		+ "',PHONENUMBER='" + bean.getPhoneNo()
		+ "',EXPIREDATE=to_date('" + bean.getExpiryDate()
		+ "','dd/MM/yyyy:hh:miam'),USERTYPE='" + bean.getUserType()
		+ "' where  USERID='" + bean.getUserId() + "'";
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
		log.info("UserDAO:updateUserMaster leaving method ");
		return count;
	}

	

	public int checkUserId(String userName, String password) throws Exception {

		log.info("UserDAO: checkExpiryDate() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int flag = 0;
		try {

			String query = "select USERID from employee_user where  username='"
					+ userName + "' and password='" + password
					+ "' and  deleteflag='N'";

			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("USERID") != 0)
					flag = rs.getInt("USERID");
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
		log.info("UserDAO: checkExpiryDate() leaving method");
		return flag;
	}

	public void addLoginHistory(String username, String userType,
			Timestamp logintime, String computername, String region)
			throws Exception {

		log.info("UserDAO: addLoginHistory() entering method");
		Connection con = null;
		PreparedStatement ps = null;

		int flag = 0;
		try {
			String query = "insert into employee_loginHistory(username,usertype,logintime,computername,region) values(?,?,?,?,?)";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, userType);
			ps.setTimestamp(3, logintime);
			ps.setString(4, computername);
			ps.setString(5, region);
			int i = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}

		finally {
			ps.close();
			con.close();
		}
		log.info("UserDAO: addLoginHistory() leaving method");

	}

	public String validateGroup(UserBean bean) throws InvalidDataException {

		log.info("UserDAO: validateGroup() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String flag = "no";
		String query = "select  count(*) as  GROUPNAME  from  employee_user_group  WHERE GROUPNAME='"
				+ bean.getGroupName() + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("GROUPNAME") != 0) {

					flag = "yes";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new InvalidDataException(e.getMessage());
		}
		log.info("UserDAO: validateGroup() leaving method");
		return flag;
	}

	/** Group Assignment */
	public void addNewGroupAssignmentRecord(UserBean bean, ArrayList formlist)
			throws Exception {
		log.info("UserDAO :addNewGroupAssignmentRecord() entering method");
		Connection conn = null;
		Statement st = null;
		ResultSet rs2 = null;
		ArrayList dblist = new ArrayList();
		int groupid = 0;
		int userid = 0;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			/** Retrieving Userids of a Group from Database */
			String st2 = "select  USERID  from employee_user_group_map where  GROUPID="
					+ bean.getGroupId() + "";
			rs2 = st.executeQuery(st2);
			while (rs2.next()) {
				UserBean data = new UserBean();
				if (rs2.getInt("USERID") != 0) {
					data.setUserId(rs2.getInt("USERID"));
				} else {
					data.setUserId(0);
				}
				dblist.add(data);
			}
			addNewGroupAssignmentRec(bean, formlist, dblist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs2.close();
			st.close();
			conn.close();
		}
		log.info("UserDAO :addNewGroupAssignmentRecord() leaving method");
	}

	public void addNewGroupAssignmentRec(UserBean bean, ArrayList formlist,
			ArrayList dblist) {
		log.info("UserDAO :addNewGroupAssignmentRec() entering method");

		try {
			ArrayList al = new ArrayList();
			ArrayList tempformlist = new ArrayList(formlist.size());
			ArrayList tempdblist = new ArrayList(dblist.size());

			tempformlist.addAll(formlist);
			tempdblist.addAll(dblist);

			if ((dblist.size() > 0) && (formlist.size() > 0)) {
				for (int i = 0; i < formlist.size(); i++) {
					UserBean ubean = new UserBean();
					int formval = 0, dbval = 0;

					/** Retrieving formlist values */
					ubean = (UserBean) formlist.get(i);
					formval = ubean.getUserId();
					for (int j = 0; j < dblist.size(); j++) {

						/** Retrieving databaselist values */
						UserBean dbean = new UserBean();
						dbean = (UserBean) dblist.get(j);
						dbval = dbean.getUserId();
						if (formval == dbval) {
							int fval = tempformlist.indexOf(formlist.get(i));
							tempformlist.remove(fval);
							int dval = tempdblist.indexOf(dblist.get(j));
							tempdblist.remove(dval);
						}
					}
				}
				if (tempformlist.size() > 0) {
					insertUsers(bean, tempformlist);
				}
				if (tempdblist.size() > 0) {
					deleteUsers(bean, tempdblist);
				}

			} else {
				insertUsers(bean, formlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("UserDAO :addNewGroupAssignmentRec() leaving method");
	}

	public void insertUsers(UserBean bean, ArrayList formlist) throws Exception {
		log.info("UserDAO :insertUsers() entering method");

		Connection conn = null;
		Statement st = null;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			int formval = 0, dbval = 0;
			for (int i = 0; i < formlist.size(); i++) {
				UserBean ubean = new UserBean();

				/** Retrieving formlist values */
				ubean = (UserBean) formlist.get(i);

				String str = "insert into employee_user_group_map(USERID,USERGROUPID,GROUPID) values("
						+ ubean.getUserId()
						+ ",usergroupid_seq.NEXTVAL,"
						+ bean.getGroupId() + ")";
				log.info("Insert Query------------" + str);
				st.executeUpdate(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			conn.close();
		}
		log.info("UserDAO :insertUsers() leaving method");
	}

	public void deleteUsers(UserBean bean, ArrayList dblist) throws Exception {
		log.info("UserDAO :deleteUsers() entering method");

		Connection conn = null;
		Statement st = null;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();

			for (int i = 0; i < dblist.size(); i++) {
				UserBean ubean = new UserBean();
				/** Retrieving formlist values */
				ubean = (UserBean) dblist.get(i);
				String str = "delete from  employee_user_group_map where USERID="
						+ ubean.getUserId()
						+ " and GROUPID="
						+ bean.getGroupId() + "";
				log.info("Delete Query------------" + str);
				st.executeUpdate(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			conn.close();
		}
		log.info("UserDAO :deleteUsers() leaving method");
	}

	public void deleteAllUsers(UserBean bean) throws Exception {
		log.info("UserDAO :deleteAllUsers() entering method");

		Connection conn = null;
		Statement st = null;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			String str = "delete from  employee_user_group_map where GROUPID="
					+ bean.getGroupId() + "";
			log.info("Delete Query------------" + str);
			st.executeUpdate(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			conn.close();
		}
		log.info("UserDAO :deleteAllUsers() leaving method");
	}

	public ArrayList getAssignedUserNames(UserBean bean) throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: getAssignedUserNames() entering method");
		Connection con = null;
		Statement st = null;

		ResultSet rs2 = null;

		ArrayList users = new ArrayList();

		String user = "";
		int groupid = 0;
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();

			String query2 = "select eu.userid,eu.username from  employee_user eu,employee_user_group_map eum where eu.userid=eum.userid  and  eum.groupid='"
					+ bean.getGroupId() + "'";

			log.info("query is " + query2);

			rs2 = st.executeQuery(query2);
			while (rs2.next()) {
				UserBean ubean = new UserBean();

				if (rs2.getInt("USERID") != 0) {
					ubean.setUserId(rs2.getInt("USERID"));
				} else {
					ubean.setUserId(0);
				}
				user = (String) rs2.getString("username").toString().trim();
				if (!user.equals("") && user != null) {
					ubean.setUserName(user);
				} else {
					ubean.setUserName("");
				}
				users.add(ubean);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			rs2.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: getAssignedUserNames() leaving method");
		return users;
	}

	/** Group */
	public void addNewGroupRecord(UserBean bean) throws InvalidDataException {

		log.info("UserDAO :addNewGroupRecord() entering method");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int groupid = 0;

		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			String sqlst = "select max(GROUPID) GROUPID from employee_user_group";
			log.info("Query is" + sqlst);
			rs = st.executeQuery(sqlst);
			String sql = "insert into employee_user_group(GROUPID,GROUPNAME,REMARKS)"
					+ "VALUES (usergroup_seq.NEXTVAL,'"
					+ bean.getGroupName()
					+ "','" + bean.getRemarks() + "')";

			log.info("Query is" + sql);
			st.execute(sql);
		} catch (SQLException e) {
			new InvalidDataException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("UserDAO :addNewGroupRecord() leaving method");
	}

	public ArrayList SearchNewGroupAll(UserBean dbBean, int start,
			int gridLength) throws Exception {
		log.info("UserDAO :SearchNewGroupAll() entering method");

		ArrayList hindiData = new ArrayList();
		UserBean data = null;
		Connection con = null;

		DatabaseBean searchData = new DatabaseBean();
		String sqlQuery = this.buildGroupQuery(dbBean);
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
					data = new UserBean();
					if (rs.getString("USERNAME") != null) {
						data.setUserName(rs.getString("USERNAME"));
					} else {
						data.setUserName("");
					}
					if (rs.getString("PENSIONNUMBER") != null) {
						data.setPensionNo(rs.getString("PENSIONNUMBER"));
					} else {
						data.setPensionNo("");
					}
					if (rs.getString("GROUPNAME") != null) {
						data.setGroupName(rs.getString("GROUPNAME"));
					} else {
						data.setGroupName("");
					}
					if (rs.getInt("GROUPID") != 0) { /* ...................2................... */
						data.setGroupId(rs.getInt("GROUPID"));
					} else {
						data.setGroupId(0);
					}
					hindiData.add(data);
				}
				while (rs.next() && countGridLength < gridLength) {
					data = new UserBean();
					countGridLength++;
					if (rs.getString("USERNAME") != null) {
						data.setUserName(rs.getString("USERNAME"));
					} else {
						data.setUserName("");
					}
					if (rs.getString("PENSIONNUMBER") != null) {
						data.setPensionNo(rs.getString("PENSIONNUMBER"));
					} else {
						data.setPensionNo("");
					}
					if (rs.getString("GROUPNAME") != null) {
						data.setGroupName(rs.getString("GROUPNAME"));
					} else {
						data.setGroupName("");
					}
					if (rs.getInt("GROUPID") != 0) { /* ...................2................... */
						data.setGroupId(rs.getInt("GROUPID"));
					} else {
						data.setGroupId(0);
					}
					log
							.info("CeilingUnitDAO :SearchPensionAll() Leaving method");
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
		log.info("UserDAO :SearchNewGroupAll() leaving method");
		return hindiData;

	}

	public int totalGroupData(UserBean dbBean) throws Exception {
		log.info("UserDAO :totalGroupData() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String selectSQL = this.buildGroupQuery(dbBean);
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
			e.printStackTrace();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		log.info("UserDAO :totalGroupData() leaving method");
		return totalRecords;
	}

	public UserBean editGroupMaster(int groupid, String groupname)
			throws Exception {
		log.info("UserDAO:editGroupMaster entering method ");
		UserBean editBean = new UserBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		UserBean data = null;

		String query = "select * from employee_user_group where groupid="
				+ groupid;
		log.info("query is ---------" + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			data = new UserBean();
			String dob = "";

			while (rs.next()) {

				if (rs.getInt("GROUPID") != 0) {

					log
							.info("..............rs.getInt(GROUPID)..................."
									+ rs.getInt("GROUPID"));
					data.setGroupId(rs.getInt("GROUPID"));
				} else {
					data.setGroupId(0);
				}
				if (rs.getString("GROUPNAME") != null) {
					log
							.info(".............rs.getString(GROUPNAME)..................."
									+ rs.getString("GROUPNAME"));
					data.setGroupName(rs.getString("GROUPNAME"));
				} else {
					data.setGroupName("");
				}
				if (rs.getString("REMARKS") != null) {
					log
							.info(".............rs.getString(REMARKS)..................."
									+ rs.getString("REMARKS"));
					data.setRemarks(rs.getString("REMARKS"));
				} else {
					data.setRemarks("");
				}
				if (rs.getString("GROUPFLAG") != null) {
					log
							.info(".............rs.getString(GROUPFLAG)..................."
									+ rs.getString("GROUPFLAG"));
					data.setSelectGroup(rs.getString("GROUPFLAG"));
				} else {
					data.setSelectGroup("");
				}

			}// end while

		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("UserDAO:editGroupMaster leaving method ");
		return data;
	}

	public int updateGroupMaster(UserBean bean) throws Exception {

		log.info("UserDAO:updateGroupMaster entering method ");
		Connection con = null;
		Statement st = null;
		int count = 0;
		String wedate = "";
		int rate = 0, ceilingcd = 0;
		String query = "update employee_user_group set  GROUPNAME='"
				+ bean.getGroupName() + "',REMARKS='" + bean.getRemarks()
				+ "',GROUPFLAG='" + bean.getSelectGroup()
				+ "'  where  GROUPID='" + bean.getGroupId() + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			count = st.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Exception is" + e.getMessage());
		} finally {
			st.close();
			con.close();
		}
		log.info("UserDAO:updateGroupMaster leaving method ");
		return count;
	}

	public ArrayList getGroupNames() throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: getGroupNames() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList groups = new ArrayList();

		String group = "";
		try {
			String query = "select GROUPID,GROUPNAME from employee_user_group where groupflag='Y'";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			while (rs.next()) {
				UserBean bean = new UserBean();
				if (rs.getInt("GROUPID") != 0) {
					bean.setGroupId(rs.getInt("GROUPID"));
				} else {
					bean.setGroupId(0);
				}
				group = (String) rs.getString("GROUPNAME").toString().trim();
				if (!group.equals("")) {
					bean.setGroupName(group);
				} else {
					bean.setGroupName("");
				}
				groups.add(bean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: getGroupNames() leaving method");
		return groups;
	}

	public ArrayList getExistingUserNames() throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: getExistingUserNames() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList users = new ArrayList();
		String user = "";
		try {
			String query = "select eu.userid,eu.username from employee_user eu  where eu.userid not in(select userid from employee_user_group_map) and deleteflag='N'";

			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				UserBean bean = new UserBean();
				if (rs.getInt("USERID") != 0) {
					bean.setUserId(rs.getInt("USERID"));
				} else {
					bean.setUserId(0);
				}
				user = (String) rs.getString("username").toString().trim();
				if (!user.equals("")) {
					bean.setUserName(user);
				} else {
					bean.setUserName("");
				}
				users.add(bean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: getExistingUserNames() leaving method");
		return users;
	}

	public String buildGroupQuery(UserBean bean) throws Exception {
		log.info("UserDAO :buildQuery() entering method");
		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = " select eu.USERNAME,eu.pensionnumber,eug.GROUPNAME,eug.GROUPID from employee_user eu,employee_user_group eug,employee_user_group_map eugm where eu.userid=eugm.userid and eug.GROUPID=eugm.GROUPID and eugm.GROUPID IN (select GROUPID from employee_user_group";

		if (!bean.getGroupName().equals("")) {
			whereClause.append("GROUPNAME='" + bean.getGroupName() + "'");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);
		if ((bean.getGroupName().equals(""))) {
			log.info("inside if");
			query.append(")");
		} else {
			log.info("inside else");
			if (!prefixWhereClause.equals("")) {
				query.append(" AND ");
			} else {
				query.append(" WHERE ");
			}
			query.append(this.sTokenFormat(whereClause));
			query.append(")");
		}

		/*
		 * String orderBy="ORDER BY ei.CPFACCNO"; query.append(orderBy);
		 */
		dynamicQuery = query.toString();
		log.info("UserDAO :buildQuery() leaving method");
		return dynamicQuery;
	}

	public ArrayList getRegions(UserBean bean) throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: getRegions() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs2 = null;
		ArrayList users = new ArrayList();

		String region = "";
		int groupid = 0;
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query2 = "select REGIONID,REGIONNAME from EMPLOYEE_REGION_MASTER where AAICATEGORY='"
					+ bean.getAaiCategory() + "'";
			log.info("query is " + query2);
			rs2 = st.executeQuery(query2);
			while (rs2.next()) {
				UserBean ubean = new UserBean();
				if (rs2.getInt("REGIONID") != 0) {
					ubean.setRegionId(rs2.getInt("REGIONID"));
				} else {
					ubean.setRegionId(0);
				}
				if (rs2.getString("REGIONNAME") != null) {
					ubean.setRegion(rs2.getString("REGIONNAME").toString()
							.trim());
				} else {
					ubean.setRegion("");
				}
				users.add(ubean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rs2.close();
			st.close();
			con.close();
		}
		log.info("UserDAO: getRegions() leaving method");
		return users;
	}

	public String checkRegion(UserBean dbBeans) throws Exception {
		boolean isCheckUser = false;
		log.info("UserDAO: checkRegion() entering method");
		Connection con = null;
		Statement pst = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String flag = "no";
		con = commonDB.getConnection();
		sqlQuery = "select count(*) as region from employee_region_master where AAICATEGORY=upper('"
				+ dbBeans.getAaiCategory()
				+ "') and REGIONNAME='"
				+ dbBeans.getRegion() + "'";
		log.info("query is " + sqlQuery);
		try {
			con = commonDB.getConnection();
			pst = con.createStatement();
			rs = pst.executeQuery(sqlQuery);
			if (rs.next()) {
				if (rs.getInt("region") != 0) {
					flag = "yes";
				}
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			rs.close();
			pst.close();
			con.close();
		}
		log.info("UserDAO: checkRegion() leaving method" + isCheckUser);
		return flag;
	}

	/*public String validateRegion(UserBean bean) throws InvalidDataException {
		log.info("UserDAO: validateRegion() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String flag = "no";
		String query = "select  count(*) as  REGIONNAME  from  employee_region_master  WHERE REGIONNAME='"
				+ bean.getGroupName() + "'";
		log.info("query is " + query);
		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				if (rs.getInt("REGIONNAME") != 0) {

					flag = "yes";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new InvalidDataException(e.getMessage());
		}
		log.info("UserDAO: validateRegion() leaving method");
		return flag;
	}*/

	public ArrayList SearchRegionAll(UserBean dbBean, int start, int gridLength)
			throws Exception {
		log.info("UserDAO :SearchRegionAll() entering method");
		ArrayList hindiData = new ArrayList();
		UserBean data = null;
		Connection con = null;
		DatabaseBean searchData = new DatabaseBean();
		String sqlQuery = this.buildRegionQuery(dbBean);
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

					data = new UserBean();
					if (rs.getInt("REGIONID") != 0) {
						data.setRegionId(rs.getInt("REGIONID"));
					} else {
						data.setRegionId(0);
					}
					if (rs.getString("REGIONNAME") != null) {
						data.setRegion(rs.getString("REGIONNAME"));
					} else {
						data.setRegion("");
					}
					if (rs.getString("AAICATEGORY") != null) {
						data.setAaiCategory(rs.getString("AAICATEGORY"));
					} else {
						data.setAaiCategory("");
					}
					if (rs.getString("REMARKS") != null) {
						data.setRemarks(rs.getString("REMARKS"));
					} else {
						data.setRemarks("");
					}
					hindiData.add(data);
				}
				while (rs.next() && countGridLength < gridLength) {
					data = new UserBean();
					countGridLength++;
					if (rs.getInt("REGIONID") != 0) {
						data.setRegionId(rs.getInt("REGIONID"));
					} else {
						data.setRegionId(0);
					}
					if (rs.getString("REGIONNAME") != null) {
						data.setRegion(rs.getString("REGIONNAME"));
					} else {
						data.setRegion("");
					}
					if (rs.getString("AAICATEGORY") != null) {
						data.setAaiCategory(rs.getString("AAICATEGORY"));
					} else {
						data.setAaiCategory("");
					}
					if (rs.getString("REMARKS") != null) {
						data.setRemarks(rs.getString("REMARKS"));
					} else {
						data.setRemarks("");
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
		log.info("UserDAO :SearchRegionAll() leaving method");
		return hindiData;

	}

	/* .........addNewUserRecord............ */

	public void addNewUserRecord(UserBean bean) throws InvalidDataException {
		log.info("UserDAO :addNewUserRecord() entering method");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int userid = 0;
		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();
			String password = deCryp.doEncrypt("pension");
			String sqlst = "select max(USERID) USERID from employee_user";
			log.info("Query is" + sqlst);
			rs = st.executeQuery(sqlst);
			if (rs.next()) {
				if (rs.getInt("USERID") != 0) {

					userid = rs.getInt("USERID") + 1;
				} else {
					userid = 1;
				}
			}
			String sql = "insert into employee_user(USERNAME,USERID,PASSWORD,EMAILID,PRIMARYMODULE,"
					+ "USERTYPE,EXPIREDATE,GRIDLENGTH,AAICATEGORY,REGION,PHONENUMBER,REMARKS)"
					+ "VALUES ('"
					+ bean.getUserName()
					+ "',"
					+ userid
					+ ",'"
					+ password
					+ "','"
					+ bean.getEmail()
					+ "','"
					+ bean.getPrimaryModule()
					+ "','"
					+ bean.getUserType()
					+ "',to_date('"
					+ bean.getExpiryDate()
					+ "','dd/MM/yyyy:hh:miam'),'"
					+ bean.getGridLength()
					+ "','"
					+ bean.getAaiCategory()
					+ "','"
					+ bean.getRegion()
					+ "','"
					+ bean.getPhoneNo()
					+ "','"
					+ bean.getRemarks()
					+ "')";

			log.info("Query is" + sql);
			st.execute(sql);
		} catch (SQLException e) {
			new InvalidDataException(e.getMessage());
		}

		catch (Exception e) {
			log.printStackTrace(e);
		}

		log.info("UserDAO :addNewUserRecord() leaving method");
	}

	public int totalRegionData(UserBean dbBean) throws Exception {

		log.info("UserDAO :totalRegionData() entering method");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String selectSQL = this.buildRegionQuery(dbBean);
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
			e.printStackTrace();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		log.info("UserDAO :totalRegionData() leaving method");
		log.info("totalRecords in DAO............................"
				+ totalRecords);
		return totalRecords;
	}

	public UserBean editRegionMaster(int regionid, String regionname)
			throws Exception {
		log.info("UserDAO:editRegionMaster entering method ");
		UserBean editBean = new UserBean();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		UserBean data = null;

		String query = "select * from employee_region_master where regionid="
				+ regionid;
		log.info("query is --------------------------------" + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			data = new UserBean();
			String dob = "";

			while (rs.next()) {
				if (rs.getInt("REGIONID") != 0) {
					data.setRegionId(rs.getInt("REGIONID"));
				} else {
					data.setRegionId(0);
				}
				if (rs.getString("REGIONNAME") != null) {
					data.setRegion(rs.getString("REGIONNAME"));
				} else {
					data.setRegion("");
				}
				if (rs.getString("AAICATEGORY") != null) {
					data.setAaiCategory(rs.getString("AAICATEGORY"));
				} else {
					data.setAaiCategory("");
				}
				if (rs.getString("REMARKS") != null) {
					data.setRemarks(rs.getString("REMARKS"));
				} else {
					data.setRemarks("");
				}

			}// end while

		} catch (Exception e) {
			System.out.println("Exception is" + e.getMessage());
		} finally {
			rs.close();
			st.close();
			con.close();
		}
		log.info("UserDAO:editRegionMaster leaving method ");
		return data;
	}

	public int updateRegionMaster(UserBean bean) throws Exception {

		log.info("UserDAO:updateRegionMaster entering method ");
		Connection con = null;
		Statement st = null;
		int count = 0;

		String wedate = "";
		int rate = 0, ceilingcd = 0;

		String query = "update employee_region_master set  REGIONNAME='"
				+ bean.getRegion() + "',REMARKS='" + bean.getRemarks()
				+ "',AAICATEGORY='" + bean.getAaiCategory()
				+ "'  where  REGIONID='" + bean.getRegionId() + "'";
		log.info("query is " + query);

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			count = st.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Exception is" + e.getMessage());
		} finally {
			st.close();
			con.close();
		}
		log.info("UserDAO:updateRegionMaster leaving method ");
		return count;
	}

	public void addNewRegionRecord(UserBean bean) throws InvalidDataException {

		log.info("UserDAO :addNewRegionRecord() entering method");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int regionid = 0;
		String regid = "";

		try {
			conn = commonDB.getConnection();
			st = conn.createStatement();

			String sqlst = "select max(REGIONID) REGIONID from employee_region_master";
			log.info("Query is" + sqlst);
			rs = st.executeQuery(sqlst);

			if (rs.next()) {
				if (rs.getString("REGIONID") != null) {
					regionid = Integer.parseInt(rs.getString("REGIONID"));
					regionid += 1;
					regid = regionid + "";
				} else {
					regionid = 1;
					regid = regionid + "";
				}
			}
			log.info(".....Region ID........." + regid);

			String sql = "insert into employee_region_master(REGIONID,REGIONNAME,AAICATEGORY,REMARKS)"
					+ "VALUES ('"
					+ regid
					+ "','"
					+ bean.getRegion()
					+ "','"
					+ bean.getAaiCategory() + "','" + bean.getRemarks() + "')";
			log.info("Query is" + sql);
			st.execute(sql);
		} catch (SQLException e) {
			new InvalidDataException(e.getMessage());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		log.info("UserDAO :addNewRegionRecord() leaving method");
	}

	public String buildRegionQuery(UserBean bean) throws Exception {

		log.info("UserDAO :buildRegionQuery() entering method");

		StringBuffer whereClause = new StringBuffer();
		StringBuffer query = new StringBuffer();
		String dynamicQuery = "", prefixWhereClause = "";
		String sqlQuery = "select REGIONID,REGIONNAME,AAICATEGORY,REMARKS from employee_region_master ";
		if (!bean.getRegion().equals("")) {
			whereClause.append("REGIONNAME='" + bean.getRegion() + "'");
			whereClause.append(" AND ");
		}
		query.append(sqlQuery);

		if ((bean.getRegion().equals("")) && (bean.getRegion().equals(""))) {
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
		log.info("UserDAO :buildRegionQuery() leaving method");
		return dynamicQuery;
	}

	public ArrayList getAllAaiCategories() throws Exception {
		boolean isCheckAirport = false;
		log.info("UserDAO: getAllAaiCategories() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList airports = new ArrayList();
		String unit = "";
		try {
			String query = "select distinct(AAICATEGORY) from EMPLOYEE_REGION_MASTER";
			log.info("query is " + query);
			con = DBUtility.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			while (rs.next()) {
				UserBean bean = new UserBean();
				if (rs.getString("AAICATEGORY") != null) {
					bean.setAaiCategory(rs.getString("AAICATEGORY").toString()
							.trim());
				} else {
					bean.setAaiCategory("");
				}
				airports.add(bean);
			}
			log.info("airport list " + airports.size());

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
		log.info("UserDAO: getAllAaiCategories() leaving method");
		return airports;
	}

	public ArrayList getRegionData(UserBean dbBean) throws Exception {
		log.info("UserDAO: getRegionData() entering method");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sbf = new StringBuffer();
		ArrayList airportList = new ArrayList();
		String sqlQuery = "";
		// to_char(DATEOFBIRTH,'DD-MON-YYYY')
		if (dbBean.getAaiCategory().equals("all")) {
			sqlQuery = "select distinct(REGIONNAME) from employee_region_master";
		} else {
			sqlQuery = "select nvl(REGIONNAME,'--') REGIONNAME from employee_region_master where AAICATEGORY='"
					+ dbBean.getAaiCategory() + "'";
		}
		System.out.println("Query is(retriveByAll)" + sqlQuery);
		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sqlQuery);
			while (rs.next()) {
				UserBean bean = new UserBean();
				if (rs.getString("REGIONNAME") != null) {
					bean.setRegion(rs.getString("REGIONNAME"));
				} else {
					bean.setRegion("");
				}
				airportList.add(bean);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
		}
		log.info("UserDAO: getRegionData() leaving method");
		return airportList;
	}

	
public ArrayList getUserData(String userName, String password)
	throws Exception {

log.info("UserDAO: getUserData() entering method");
Connection con = null;
Statement st = null;
ResultSet rs = null;		
password = deCryp.doEncrypt(password);
UserBean ubean=new UserBean();
ArrayList userdata=new ArrayList();
try {
	String query = "select USERTYPE,GRIDLENGTH,REGION,passwordchangeflag from employee_user where username='"
			+ userName + "' and password='" + password + "'";

	log.info("query is " + query);
	con = DBUtility.getConnection();
	st = con.createStatement();
	rs = st.executeQuery(query);
	if (rs.next()) {
		if(rs.getString("USERTYPE")!=""){
		ubean.setUserType(rs.getString("USERTYPE"));
		}else{
		ubean.setUserType("");
		}
		if(rs.getString("GRIDLENGTH")!=""){
		ubean.setGridLength(rs.getString("GRIDLENGTH"));
		}else{
		ubean.setGridLength("");
		}
		if(rs.getString("REGION")!=""){
		ubean.setRegion(rs.getString("REGION"));
		}else{
		ubean.setRegion("");
		}	
		if(rs.getString("passwordchangeflag")!=""){
			ubean.setPasswordChangeFlag(rs.getString("passwordchangeflag"));
			}else{
			ubean.setPasswordChangeFlag("");
			}
		userdata.add(ubean);
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
log.info("UserDAO: getUserData() leaving method");
return userdata;
}


public void updatePasswordFlag(String userName,String password)
throws Exception {

log.info("UserDAO: updatePasswordFlag entering method");
Connection con = null;
Statement st = null;
ResultSet rs = null;		
try {
String query = "update employee_user set passwordchangeflag='Y' where username='"
		+ userName + "' ";

log.info("query is " + query);
con = DBUtility.getConnection();
st = con.createStatement();
 st.executeUpdate(query);


} catch (SQLException e) {
// TODO Auto-generated catch block
log.printStackTrace(e);
} catch (Exception e) {
// TODO Auto-generated catch block
log.printStackTrace(e);
}

finally {

st.close();
con.close();
}
log.info("UserDAO: updatePasswordFlag leaving method");

}
}
