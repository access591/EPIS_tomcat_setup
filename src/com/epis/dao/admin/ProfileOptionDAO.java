package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.epis.bean.admin.ProfileOptionBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ProfileOptionDAO {
	
	Log log  = new Log(ProfileOptionDAO.class);
	private ProfileOptionDAO(){
		
	}
	private static final ProfileOptionDAO dao = new ProfileOptionDAO();
	public static ProfileOptionDAO getInstance(){
		return dao;
	}
	public List searchOption(String optionCode,String optionName) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select * from EPIS_PROFILE_OPTIONS where optioncode is not null and upper(optioncode) like upper(nvl(?,''))||'%' and upper(nvl(optionname,'')) like upper(nvl(?,''))||'%' ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(optionCode));
					pstmt.setString(2,StringUtility.checknull(optionName));					
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						ProfileOptionBean option = new ProfileOptionBean(StringUtility.checknull(reusltSet.getString("optioncode")),StringUtility.checknull(reusltSet.getString("optionname")),StringUtility.checknull(reusltSet.getString("description")));
						list.add(option);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ProfileOptionDAO:Search Option(s) for OptionCode: "+optionCode+" ,OptionName: "+optionName+"  Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("ProfileOptionDAO:searchOption:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ProfileOptionDAO:searchOption:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		
		return list;
		
	}

	public void saveOption(ProfileOptionBean obean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			if(connection!=null){
				 
				String saveOptionSql ="insert into EPIS_PROFILE_OPTIONS (optioncode, optionname,description,path,optiontype,created_by,created_dt) values (?,?,?,?,?,?,SYSDATE)";
				String saveProfileOptionSql ="insert into epis_profile_options_mapping (optioncode,created_by,created_dt) values (?,?,SYSDATE)";
				String optioncd =AutoGeneration.getNextCodeGBy("EPIS_PROFILE_OPTIONS","optioncode",5,"OP",connection);
					DBUtility.setAutoCommit(false,connection);
					pstmt=connection.prepareStatement(saveOptionSql);		
				
					pstmt.setString(1,StringUtility.checknull(optioncd));
					pstmt.setString(2,StringUtility.checknull(obean.getOptionName()));
					pstmt.setString(3,StringUtility.checknull(obean.getDescription()));	
					pstmt.setString(4,StringUtility.checknull(obean.getPath()));
					pstmt.setString(5,StringUtility.checknull(obean.getOptionType()));	
					pstmt.setString(6,StringUtility.checknull(obean.getLoginUserId()));

					pstmt.executeUpdate();
				
					pstmt=connection.prepareStatement(saveProfileOptionSql);		
				
					pstmt.setString(1,StringUtility.checknull(optioncd));
					pstmt.setString(2,StringUtility.checknull(obean.getLoginUserId()));
					pstmt.executeUpdate();
					DBUtility.commitTrans(connection);
				
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("ProfileOptionDAO : Option created with info :"+obean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){	
			try{
			DBUtility.rollbackTrans(connection);
			}catch(Exception ex){
				log.error("ProfileOptionDAO:saveOption:SQLException:"+ex.getMessage());
				throw  new EPISException(ex);
			}
			log.error("ProfileOptionDAO:saveOption:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ProfileOptionDAO:saveOption:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editOption(ProfileOptionBean obean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  EPIS_PROFILE_OPTIONS set optionname=?,description=?,path=?,optiontype=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where optioncode=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(obean.getOptionName()));
					pstmt.setString(2,StringUtility.checknull(obean.getDescription()));
					pstmt.setString(3,StringUtility.checknull(obean.getPath()));
					pstmt.setString(4,StringUtility.checknull(obean.getOptionType()));	
					pstmt.setString(5,StringUtility.checknull(obean.getLoginUserId()));
					pstmt.setString(6,StringUtility.checknull(obean.getOptionCode()));
					
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("ProfileOptionDAO :edit Option  with info :"+obean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ProfileOptionDAO:editOption:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ProfileOptionDAO:editOption:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public ProfileOptionBean findOption(String optioncode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		ProfileOptionBean option=null;
		
		try{	
			String searchQry="select * from EPIS_PROFILE_OPTIONS where optioncode =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(optioncode));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						option = new ProfileOptionBean(StringUtility.checknull(reusltSet.getString("optioncode")),StringUtility.checknull(reusltSet.getString("optionname")),StringUtility.checknull(reusltSet.getString("description")),StringUtility.checknull(reusltSet.getString("path")),StringUtility.checknull(reusltSet.getString("optiontype")));
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ProfileOptionDAO:findOption for Cd: "+optioncode+" :result:"+option);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ProfileOptionDAO:findOption:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ProfileOptionDAO:findOption:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return option;
	}

	public void deleteOption(String optioncodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			String deleteunitSql ="delete  from EPIS_PROFILE_OPTIONS where optioncode in ('"+optioncodes+"')";
			log.info("ProfileOptionDAO: Delete Option Qry:"+deleteunitSql );
			if(connection!=null){
				stmt=connection.createStatement();
				if(stmt!=null){										
					stmt.executeUpdate(deleteunitSql);
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ProfileOptionDAO:deleteOption:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ProfileOptionDAO:deleteOption:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List getOptionList() throws EPISException{
		ProfileOptionBean option = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List optionList = new ArrayList();
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select optioncode,optionname,description  from EPIS_PROFILE_OPTIONS ";
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				option = new ProfileOptionBean(rs.getString("optioncode"),rs.getString("optionname"),
						rs.getString("description"));
				optionList.add(option);
			}
		}catch (SQLException sqle) {
			log.error("ProfileOptionDAO:getOptionList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("ProfileOptionDAO:getOptionList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return optionList;
	}	    
                   
}
