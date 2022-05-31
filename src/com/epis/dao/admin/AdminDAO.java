package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.epis.bean.admin.Bean;
import com.epis.common.exception.EPISException;
import com.epis.info.login.LoginInfo;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;


public class AdminDAO {
	
	Log log  = new Log(AdminDAO.class);
	private AdminDAO(){
		
	}
	private static final AdminDAO dao = new AdminDAO();
	public static AdminDAO getInstance(){
		return dao;
	}
	//On 21-Jan-2012 for getting existed Users List	
	public List getUserList() throws EPISException{
		Bean user = null;
		Connection con = null;
		ResultSet resultsSet = null;
		Statement st = null;
		List userList = new ArrayList();
		
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select userid, username from epis_user where deleteflag='N' and userid is not null";
			resultsSet = DBUtility.getRecordSet(query,st);
			while(resultsSet.next()){
				user = new Bean(resultsSet.getString("username"),resultsSet.getString("username"));
				userList.add(user);
			}
		}catch (SQLException sqle) {
			log.error("AdminDAO:getUserList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("AdminDAO:getUserList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(resultsSet,st,con);
		}
		return userList;
	}
	public void resetPassword(LoginInfo loginInfo) throws EPISException{
		try{		
			String newPassword=loginInfo.getNewPassword();			
			String userId=loginInfo.getUserId();		
			String getdates="update epis_user set PASSWORD=encrypt('"+newPassword+"') where  USERNAME='"+userId+"' ";
			log.info(getdates);
		    DBUtility.executeUpdate(getdates);
		}catch (Exception e) {
			log.error("AdminDAO:resetPassword:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}
		
	}
	
		

}
