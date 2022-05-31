package com.epis.dao.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.BLOB;

import com.epis.bean.admin.AccessRightsBean;
import com.epis.bean.admin.Bean;
import com.epis.bean.admin.ScreenOptionsBean;
import com.epis.bean.admin.UserBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.Configurations;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class UserDAO {

	Log log = new Log(UserDAO.class);

	private UserDAO() {

	}

	private static final UserDAO dao = new UserDAO();

	public static UserDAO getInstance() {
		return dao;
	}

	public List getUserList(UserBean user, String userId) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		List userList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer(
					"select Nvl(USERNAME,' ') USERNAME,USERID,");
			query
					.append(" Nvl(EMPLOYEENO,' ') EMPLOYEENO,Nvl(EMAILID,' ') EMAILID,Nvl(UNITCD,' ') UNITCD");
			query
					.append(" ,Nvl(MODULES,' ') MODULES,decode(PROFILE,'M','Member','U','Unit','R','RHQ','C','CHQ')");
			query
					.append(" ||'Level' PROFILE ,Nvl(to_char(EXPIREDATE,'DD/Mon/YYYY'),' ') EXPIREDATE,");
			query
					.append(" to_char(CREATEDON,'DD/Mon/YYYY HH24:MI') CREATEDON,Nvl(GRIDLENGTH,' ') GRIDLENGTH,");
			query
					.append(" Nvl(DELETEFLAG,' ') DELETEFLAG,Nvl(PASSWORDCHANGEFLAG,' ') PASSWORDCHANGEFLAG, ");
			query
					.append(" Decode(Nvl(STATUS,'N'),'Y','Active','N','Inactive') STATUS,Decode(USERTYPE,'U',");
			query
					.append(" 'User','A','Admin','N','Nodal Officer') USERTYPE,Nvl(UNITNAME,' ') UNITNAME , nvl(DISPLAYNAME,' ') DISPLAYNAME from epis_user,employee_unit_master  ");
			query
					.append(" where UNITCD=UNITCODE(+) and upper(trim(USERNAME)) like upper(trim(?)) and ");
			query
					.append(" upper(Nvl(EMPLOYEENO,' ')) like upper(trim(?)) and  Nvl(UNITCD,' ') like ? ");
			query
					.append(" and USERTYPE like ? and STATUS like ? and PROFILE like ? and DELETEFLAG = 'N' ");
			if ("1".equals(userId)) {
				query.append(" and PROFILE in ('U','C','R','S','A','M') ");
			} else {
				query.append(" and PROFILE in ('U','C','R','M') ");
			}
			pst = con.prepareStatement(query.toString());
			pst.setString(1, StringUtility.checknull(user.getUserName()) + "%");
			pst.setString(2, StringUtility.checknull(user.getEmployeeId())
					+ "%");
			pst.setString(3, StringUtility.checknull(user.getUnit()) + "%");
			pst.setString(4, StringUtility.checknull(user.getUserType()) + "%");
			pst.setString(5, StringUtility.checknull(user.getStatus()) + "%");
			pst.setString(6, StringUtility.checknull(user.getProfileType())
					+ "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				userList.add(new UserBean(rs.getString("USERID"), rs
						.getString("EMPLOYEENO"), rs.getString("USERTYPE"), rs
						.getString("PROFILE"), rs.getString("UNITCD"), rs
						.getString("STATUS"), rs.getString("USERNAME"), rs
						.getString("EMAILID"), StringUtility.checknull(
						rs.getString("MODULES")).split("//,"), rs
						.getString("EXPIREDATE"), rs.getString("GRIDLENGTH"),
						rs.getString("CREATEDON"), rs.getString("DELETEFLAG"),
						rs.getString("PASSWORDCHANGEFLAG"), rs
								.getString("UNITNAME"),rs
								.getString("DISPLAYNAME")));
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getUserList:SQLException:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getUserList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pst, con);
		}
		return userList;
	}

	public List getUserList(String userId) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		List userList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer(
					"select Nvl(USERNAME,' ') USERNAME,USERID,");
			query
					.append(" Nvl(EMPLOYEENO,' ') EMPLOYEENO,Nvl(EMAILID,' ') EMAILID,Nvl(UNITCD,' ') UNITCD");
			query
					.append(" ,Nvl(MODULES,' ') MODULES,decode(PROFILE,'M','Member','U','Unit','R','RHQ','C','CHQ')");
			query
					.append(" ||'Level' PROFILE ,Nvl(to_char(EXPIREDATE,'DD/Mon/YYYY'),' ') EXPIREDATE,");
			query
					.append(" to_char(CREATEDON,'DD/Mon/YYYY HH24:MI') CREATEDON,Nvl(GRIDLENGTH,' ') GRIDLENGTH,");
			query
					.append(" Nvl(DELETEFLAG,' ') DELETEFLAG,Nvl(PASSWORDCHANGEFLAG,' ') PASSWORDCHANGEFLAG, ");
			query
					.append(" Decode(Nvl(STATUS,'N'),'Y','Active','N','Inactive') STATUS,Decode(USERTYPE,'U',");
			query
					.append(" 'User','A','Admin') USERTYPE,Nvl(UNITNAME,' ') UNITNAME from epis_user,employee_unit_master where UNITCD=UNITCODE ");
			query.append("  and DELETEFLAG = 'N'");

			if ("1".equals(userId)) {
				query.append(" and PROFILE in ('U','C','R','S','A') ");
			} else {
				query.append(" and PROFILE in ('U','C','R') ");
			}
				log.info("====================="+query.toString());
			pst = con.prepareStatement(query.toString());
			rs = pst.executeQuery();
			while (rs.next()) {
				userList.add(new Bean(rs.getString("USERNAME"), rs
						.getString("USERNAME")));
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getUserList:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getUserList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pst, con);
		}
		return userList;
	}

	public void delete(String userIds) throws EPISException {
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = DBUtility.getConnection();
			String query = "update epis_user set DELETEFLAG = 'Y' where  USERID= ?";
			pst = con.prepareStatement(query);
			
			pst.setString(1, userIds);
			pst.executeUpdate();
		} catch (SQLException sqle) {
			log.error("UserDAO:delete:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:delete:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pst, con);
		}
	}

	public void add(UserBean user, String UserID) throws EPISException,
			Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rset = null;
		// log.info("user dao anil "+UserID);
		try {
		
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			String query = "insert into epis_user(USERNAME,EMPLOYEENO,EMAILID,UNITCD,MODULES,USERTYPE,STATUS,PROFILE,"
					+ "EXPIREDATE,ROLECD,USERID,CREATEDON,Password,created_by,esignatory,esignName,displayName,PENSIONNO,DESIGNATION) values(?,?,?,?,?,?,?,?,?,?,(select Nvl((select max(USERID)+1 "
					+ "from epis_user),1) from dual),sysdate,encrypt(?),?,EMPTY_BLOB(),?,?,?,?) ";
			log.info("==query=="+query);
			pst = con.prepareStatement(query);
			pst.setString(1, StringUtility.checknull(user.getUserName()));
			pst.setString(2, StringUtility.checknull(user.getEmployeeId()));
			pst.setString(3, StringUtility.checknull(user.getEmail()));
			pst.setString(4, StringUtility.checknull(user.getUnit()));
			pst.setString(5, StringUtility.checknull(StringUtility
					.arrayToString(user.getModules(), ",")));
			pst.setString(6, StringUtility.checknull(user.getUserType()));
			pst.setString(7, StringUtility.checknull(user.getStatus()));
			pst.setString(8, StringUtility.checknull(user.getProfileType()));
			pst.setString(9, StringUtility.checknull(user.getExpireDate())
					.trim());
			pst.setString(10, StringUtility.checknull(user.getRoleCd()).trim());
			pst.setString(11, StringUtility.checknull(user.getUserName())+user.getEmployeeId());
			pst.setString(12, StringUtility.checknull(UserID));
			
			
			log.info("==user.getEsignatory().getFileSize()=="+user.getEsignatory().getFileSize());
			if(user.getEsignatory().getFileSize()>0){
				pst.setString(13, StringUtility.checknull("".equals(user
						.getEsignatory()) ? ""
						: (user.getUserName() + user.getEsignatory().getFileName()
								.substring(
										user.getEsignatory().getFileName()
												.lastIndexOf(".")))));
			}else{
				pst.setString(13,"");
			}
			pst.setString(14,user.getDisplayName());
			pst.setString(15,user.getEmployeeId());
			pst.setString(16,user.getDesignation());
			pst.executeUpdate();
			pst.close();
			if (user.getEsignatory().getFileSize()>0) {
				pst = con
						.prepareStatement("select esignatory from epis_user where USERNAME ='"
								+ StringUtility.checknull(user.getUserName())
								+ "' FOR UPDATE");
				rset = pst.executeQuery();
				
				int bytesRead = 0;
				int bytesWritten = 0;
				int totbytesRead = 0;
				int totbytesWritten = 0;

				if (rset.next()) {
					BLOB image = ((OracleResultSet) rset).getBLOB("esignatory");					
					int chunkSize = image.getChunkSize();
					byte[] binaryBuffer = new byte[chunkSize];
					int position = 1;
					InputStream inputFileInputStream = user.getEsignatory()
							.getInputStream();
					while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1) {
						bytesWritten = image.putBytes(position, binaryBuffer,
								bytesRead);
						position += bytesRead;
						totbytesRead += bytesRead;
						totbytesWritten += bytesWritten;
					}
					inputFileInputStream.close();
				}
			}
			con.commit();
		} catch (SQLException sqle) {
			con.rollback();
			log.error("UserDAO:add:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			con.rollback();
			log.error("UserDAO:add:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rset, pst, con);
		}
	}

	public List getModuleList() throws Exception {
		Bean module = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List moduleList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select CODE,NAME  from epis_modules ";
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {
				module = new Bean(rs.getString("CODE"), rs.getString("NAME"));
				moduleList.add(module);
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getModuleList:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getModuleList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return moduleList;
	}

	public List getModuleList(String moduleCds) throws Exception {
		Bean module = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List moduleList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select CODE,NAME  from epis_modules where instr('"
					+ moduleCds + "',code,1,1)>0";
			rs = DBUtility.getRecordSet(query, st);
			while (rs.next()) {
				module = new Bean(rs.getString("CODE"), rs.getString("NAME"));
				moduleList.add(module);
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getModuleList:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getModuleList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return moduleList;
	}

	public List getSubModuleList(String moduleCd) throws Exception {
		Bean module = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List submoduleList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select MODULECODE ,SUBMODULECD, SUBMODNAME   from epis_submodules where MODULECODE='"
					+ moduleCd + "' order by SUBMODULECD";
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {
				module = new Bean(rs.getString("SUBMODULECD"), rs
						.getString("SUBMODNAME"));
				submoduleList.add(module);
			}
		} catch (SQLException sqle) {
			log
					.error("UserDAO:getSubModuleList:Exception:"
							+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getSubModuleList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return submoduleList;
	}

	public List getScreensList(String subModuleCd, String userID)
			throws Exception {
		AccessRightsBean bean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List screensList = new ArrayList();

		try {
			String query = "";
			con = DBUtility.getConnection();
			st = con.createStatement();
			if ("USER".equals(Configurations.getAccessRightsType())) {
				query = "select code.SCREENCODE ,SCREENNAME, SUBMODULECD,NEWSCREEN ,EDITSCREEN,DELETESCREEN, VIEWSCREEN, REPORTSCREEN, ACCESSRIGHT   from epis_accesscodes_mt code,(select * from epis_accessrights where userid='"
						+ userID
						+ "')  right where code.screencode=right.screencode(+) and  code.SUBMODULECD='"
						+ subModuleCd + "'  order by SCREENCODE";
			} else if ("ROLE".equals(Configurations.getAccessRightsType())) {
				query = "select code.SCREENCODE ,SCREENNAME, SUBMODULECD,NEWSCREEN ,EDITSCREEN,DELETESCREEN, VIEWSCREEN, REPORTSCREEN, ACCESSRIGHT   from epis_accesscodes_mt code,(select * from epis_accessrights where STAGECD='"
						+ userID
						+ "')  right where code.screencode=right.screencode(+) and  code.SUBMODULECD='"
						+ subModuleCd + "'  order by SCREENCODE";
			}
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {
				bean = new AccessRightsBean();
				bean.setScreenCode(StringUtility.checknull(rs
						.getString("SCREENCODE")));
				bean.setScreenName(StringUtility.checknull(rs
						.getString("SCREENNAME")));
				bean.setScreenOptions(getScreenOptions(StringUtility
						.checknull(rs.getString("SCREENCODE"))));
				// bean.setNewScreen(StringUtility.checkFlag(rs.getString("NEWSCREEN")));
				// bean.setEditScreen(StringUtility.checkFlag(rs.getString("EDITSCREEN")));
				// bean.setDeleteScreen(StringUtility.checkFlag(rs.getString("DELETESCREEN")));
				// bean.setViewScreen(StringUtility.checkFlag(rs.getString("VIEWSCREEN")));
				// bean.setReportScreen(StringUtility.checkFlag(rs.getString("REPORTSCREEN")));
				// bean.setAccessRight(StringUtility.checkFlag(rs.getString("ACCESSRIGHT")));
				screensList.add(bean);
			}
		} catch (SQLException sqle) {
			log
					.error("UserDAO:getSubModuleList:Exception:"
							+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getSubModuleList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return screensList;
	}

	public Map getAllScreenCodes() throws Exception {

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Map screensList = new HashMap();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select OPTIONCODE from ((select SCREENOPTIONCODE OPTIONCODE  from epis_screen_options where SCREENOPTIONCODE is not null)union(select SCREENCODE  OPTIONCODE from epis_accesscodes_mt where SCREENCODE is not null))";
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {

				screensList.put(rs.getString("OPTIONCODE"), rs
						.getString("OPTIONCODE"));
			}
		} catch (SQLException sqle) {
			log
					.error("UserDAO:getSubModuleList:Exception:"
							+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getSubModuleList:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return screensList;
	}

	public UserBean getUser(String userId) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		UserBean user = new UserBean();

		try {
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer(
					"select Nvl(USERNAME,' ') USERNAME,USERID,");
			query
					.append(" Nvl(EMPLOYEENO,' ') EMPLOYEENO,Nvl(EMAILID,' ') EMAILID,Nvl(UNITCD,' ') UNITCD");
			query
					.append(" ,Nvl(MODULES,' ') MODULES,PROFILE ,Nvl(to_char(EXPIREDATE,'DD/Mon/YYYY'),' ') EXPIREDATE,");
			query
					.append(" to_char(CREATEDON,'DD/Mon/YYYY HH24:MI') CREATEDON,Nvl(GRIDLENGTH,' ') GRIDLENGTH,");
			query
					.append(" Nvl(DELETEFLAG,' ') DELETEFLAG,Nvl(PASSWORDCHANGEFLAG,' ') PASSWORDCHANGEFLAG, ");
			query
					.append(" Nvl(STATUS,'N') STATUS, USERTYPE,Nvl(UNITNAME,' ') UNITNAME,Nvl(ROLECD,' ') ROLECD , Nvl(DISPLAYNAME,' ') DISPLAYNAME, Nvl(DESIGNATION,' ') DESIGNATION ");
			query
					.append(" ,esignatory,esignName from epis_user,employee_unit_master where UNITCD=UNITCODE(+) and USERID = ? ");
			pst = con.prepareStatement(query.toString());
			pst.setString(1, StringUtility.checknull(userId));
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new UserBean(rs.getString("USERID"), rs
						.getString("EMPLOYEENO"), rs.getString("USERTYPE"), rs
						.getString("PROFILE"), rs.getString("UNITCD"), rs
						.getString("STATUS"), rs.getString("USERNAME"), rs
						.getString("EMAILID"), rs.getString("MODULES").split(
						"\\,"), rs.getString("EXPIREDATE"), rs
						.getString("GRIDLENGTH"), rs.getString("CREATEDON"), rs
						.getString("DELETEFLAG"), rs
						.getString("PASSWORDCHANGEFLAG"), rs
						.getString("UNITNAME"), rs.getString("ROLECD"),rs.getString("DISPLAYNAME"));
				user.setEsignatoryName(rs.getString("esignName"));
				user.setDesignation(rs.getString("DESIGNATION"));
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getUser:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getUser:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pst, con);
		}
		return user;
	}

	public void getImage(String path, String userName) throws IOException,
			SQLException, EPISException {
		FileOutputStream outputFileOutputStream = null;
		String sqlText = null;
		Statement stmt = null;
		ResultSet rset = null;
		long blobLength;
		long position;
		BLOB image = null;
		int chunkSize;
		byte[] binaryBuffer;
		int bytesRead = 0;
		int totbytesRead = 0;
		int totbytesWritten = 0;
		Connection con = null;
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			con.setAutoCommit(false);
			File outputBinaryFile1 = new File(path);
			outputFileOutputStream = new FileOutputStream(outputBinaryFile1);
			sqlText = "SELECT esignatory " + "FROM   epis_user "
					+ "WHERE  USERNAME = '" + userName + "' FOR UPDATE";
			rset = stmt.executeQuery(sqlText);
			rset.next();
			image = ((OracleResultSet) rset).getBLOB("esignatory");
			
			blobLength = image.length();
			chunkSize = image.getChunkSize();
			binaryBuffer = new byte[chunkSize];
			for (position = 1; position <= blobLength; position += chunkSize) {
				bytesRead = image.getBytes(position, chunkSize, binaryBuffer);
				outputFileOutputStream.write(binaryBuffer, 0, bytesRead);
				totbytesRead += bytesRead;
				totbytesWritten += bytesRead;
			}
			outputFileOutputStream.close();
			con.commit();
			rset.close();
			stmt.close();

		} catch (IOException e) {
			con.rollback();
			log.error("Caught I/O Exception: (Write BLOB value to file - Get Method).");
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			log.error("Caught SQL Exception: (Write BLOB value to file - Get Method).");
			log.error("SQL:\n" + sqlText);
			e.printStackTrace();
			throw e;
		}
	}

	public void edit(UserBean user, String UserID) throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rset = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			StringBuffer query = new StringBuffer(
					" update epis_user set USERNAME=?,EMPLOYEENO=?,EMAILID=?, ");
			query.append(" UNITCD=?,MODULES=?,USERTYPE=?,STATUS=?,PROFILE=?,EXPIREDATE=trim(?),ROLECD=trim(?),UPDATED_BY=?,UPDATED_DT=SYSDATE ,esignName=?,displayName=?,designation=?,PENSIONNO=?");
			query.append(" where upper(trim(USERID))=upper(trim(?)) ");
			pst = con.prepareStatement(query.toString());
			pst.setString(1, StringUtility.checknull(user.getUserName()));
			pst.setString(2, StringUtility.checknull(user.getEmployeeId()));
			pst.setString(3, StringUtility.checknull(user.getEmail()));
			pst.setString(4, StringUtility.checknull(user.getUnit()));
			pst.setString(5, StringUtility.checknull(StringUtility
					.arrayToString(user.getModules(), ",")));
			pst.setString(6, StringUtility.checknull(user.getUserType()));
			pst.setString(7, StringUtility.checknull(user.getStatus()));
			pst.setString(8, StringUtility.checknull(user.getProfileType()));
			pst.setString(9, StringUtility.checknull(user.getExpireDate()));
			pst.setString(10, StringUtility.checknull(user.getRoleCd()));
			pst.setString(11, StringUtility.checknull(UserID));
			
			if(user.getEsignatory().getFileSize()>0){
				pst.setString(12, StringUtility.checknull("".equals(user
						.getEsignatory()) ? ""
						: (user.getUserName() + user.getEsignatory().getFileName()
								.substring(
										user.getEsignatory().getFileName()
												.lastIndexOf(".")))));
			}else{
				pst.setString(12,user.getEsignatoryName());
			}
			pst.setString(13, StringUtility.checknull(user.getDisplayName()));
			pst.setString(14, StringUtility.checknull(user.getDesignation()));
			pst.setString(15, StringUtility.checknull(user.getEmployeeId()));
			pst.setString(16, StringUtility.checknull(user.getUserId()));
			pst.executeUpdate();
			pst.close();
			if (user.getEsignatory().getFileSize()>0) {
				pst = con
						.prepareStatement("select esignatory from epis_user where USERNAME ='"
								+ StringUtility.checknull(user.getUserName())
								+ "' FOR UPDATE");
				rset = pst.executeQuery();
				
				int bytesRead = 0;
				int bytesWritten = 0;
				int totbytesRead = 0;
				int totbytesWritten = 0;

				if (rset.next()) {
					BLOB image = ((OracleResultSet) rset).getBLOB("esignatory");				
					int chunkSize = image.getChunkSize();
					byte[] binaryBuffer = new byte[chunkSize];
					int position = 1;
					InputStream inputFileInputStream = user.getEsignatory()
							.getInputStream();
					while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1) {
						bytesWritten = image.putBytes(position, binaryBuffer,
								bytesRead);
						position += bytesRead;
						totbytesRead += bytesRead;
						totbytesWritten += bytesWritten;
					}
					inputFileInputStream.close();
				}
			}
			con.commit();
		} catch (SQLException sqle) {
			con.rollback();
			log.error("UserDAO:edit:SQLException:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			con.rollback();
			log.error("UserDAO:edit:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pst, con);
		}
	}

	private ArrayList getScreenOptions(String screenCode) throws Exception {
		ScreenOptionsBean sobean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		ArrayList submoduleList = new ArrayList();

		try {
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select SCREENOPTIONCODE , OPTIONNAME  , PATH from epis_screen_options where SCREENCODE='"
					+ screenCode + "' order by SCREENOPTIONCODE";
			rs = DBUtility.getRecordSet(query, st);

			while (rs.next()) {
				sobean = new ScreenOptionsBean(
						rs.getString("SCREENOPTIONCODE"), rs
								.getString("OPTIONNAME"), rs.getString("PATH"));
				submoduleList.add(sobean);
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getSscreenOptions:Exception:"
					+ sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getSscreenOptions:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, st, con);
		}
		return submoduleList;
	}
	public UserBean getuserAccount(String userId) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		UserBean user = new UserBean();

		try {
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer("SELECT USERNAME, USERID, nvl(EMPLOYEENO,'')EMPLOYEENO, nvl(EMAILID,'')EMAILID,(SELECT UNITNAME FROM employee_unit_master UM WHERE U.UNITCD=UM.UNITCODE) UNITCD,nvl(MODULES,' ')MODULES,DECODE(USERTYPE,'U','User','Admin') USERTYPE,decode(PROFILE,'M','Member Level','U','Unit Level','R','RHQ Level','C','CHQ Level','S','Super User','A','Admin',PROFILE) PROFILE,decode(STATUS,'Y','Active','N','In-Active') STATUS,to_char(EXPIREDATE,'dd/Mon/YYYY') EXPIREDATE, (select ROLENAME from EPIS_ROLE R where u.ROLECD=r.ROLECD) ROLECD,nvl(DISPLAYNAME,' ') DISPLAYNAME FROM epis_user U WHERE USERID = ?  ");
			pst = con.prepareStatement(query.toString());
			pst.setString(1, StringUtility.checknull(userId));
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new UserBean();
				user.setEmail(rs.getString("EMAILID"));
				user.setEmployeeId(rs.getString("EMPLOYEENO"));
				user.setExpireDate(rs.getString("EXPIREDATE"));
				user.setModuleNames(rs.getString("MODULES"));
				user.setUserName(rs.getString("USERNAME"));
				user.setUnit(rs.getString("UNITCD"));
				user.setUserType(rs.getString("USERTYPE"));
				user.setProfileType(rs.getString("PROFILE"));
				user.setStatus(rs.getString("STATUS"));
				user.setRoleCd(rs.getString("ROLECD"));
				user.setDisplayName(rs.getString("DISPLAYNAME"));
				
			}
		} catch (SQLException sqle) {
			log.error("UserDAO:getUser:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("UserDAO:getUser:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pst, con);
		}
		return user;
	}

}
