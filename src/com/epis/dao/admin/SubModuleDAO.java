package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.admin.Bean;
import com.epis.bean.admin.SMBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class SubModuleDAO {
	
	Log log  = new Log(SubModuleDAO.class);
	private SubModuleDAO(){
		
	}
	private static final SubModuleDAO dao = new SubModuleDAO();
	public static SubModuleDAO getInstance(){
		return dao;
	}
	public List searchSM(String moduleCode,String subModuleName) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select SUBMODULECD,MODULECODE,SUBMODNAME,nvl(sortingorder,0) sortingorder from epis_submodules where SUBMODULECD is not null and upper(MODULECODE) like upper(nvl(?,''))||'%' and upper(nvl(SUBMODNAME,'')) like upper(nvl(?,''))||'%' ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(moduleCode));
					pstmt.setString(2,StringUtility.checknull(subModuleName));					
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						SMBean sm = new SMBean(StringUtility.checknull(reusltSet.getString("SUBMODULECD")),StringUtility.checknull(reusltSet.getString("MODULECODE")),StringUtility.checknull(reusltSet.getString("SUBMODNAME")),StringUtility.checknull(reusltSet.getString("sortingorder")));
						list.add(sm);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("SubModuleDAO:Search Sub Module(s) for Module: "+moduleCode+" ,SM Name: "+subModuleName+"  Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("SubModuleDAO:searchApproval:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("SubModuleDAO:searchApproval:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		
		return list;
		
	}

	public void saveSM(SMBean smbean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			if(connection!=null){
				String saveunitSql ="insert into epis_submodules (SUBMODULECD, MODULECODE,SUBMODNAME,created_by,sortingorder,CREATEDON) values (?,?,?,?,?,SYSDATE)";
				String smcd =AutoGeneration.getNextCodeGBy("epis_submodules","SUBMODULECD",4,smbean.getModuleCode(),connection);
				pstmt=connection.prepareStatement(saveunitSql);		
				
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(smcd));
					pstmt.setString(2,StringUtility.checknull(smbean.getModuleCode()));
					pstmt.setString(3,StringUtility.checknull(smbean.getSubModuleName()));
					pstmt.setString(4,StringUtility.checknull(smbean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(smbean.getSortingOrder()));
					
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("SubModuleDAO : SM created with info :"+smbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("SubModuleDAO:saveSM:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("SubModuleDAO:saveSM:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editSM(SMBean smbean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  epis_submodules set MODULECODE=?,SUBMODNAME=?,sortingorder=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where SUBMODULECD=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(smbean.getModuleCode()));
					pstmt.setString(2,StringUtility.checknull(smbean.getSubModuleName()));
					pstmt.setString(3,StringUtility.checknull(smbean.getSortingOrder()));
					pstmt.setString(4,StringUtility.checknull(smbean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(smbean.getSubModuleCode()));					
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("SubModuleDAO :editSM  with info :"+smbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("SubModuleDAO:editSM:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SubModuleDAO:editSM:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public SMBean findSM(String smcode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		SMBean smbean=null;
		
		try{	
			String searchQry="select SUBMODULECD,MODULECODE,SUBMODNAME,nvl(sortingorder,0) sortingorder from epis_submodules where SUBMODULECD =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(smcode));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						smbean = new SMBean(StringUtility.checknull(reusltSet.getString("SUBMODULECD")),StringUtility.checknull(reusltSet.getString("MODULECODE")),StringUtility.checknull(reusltSet.getString("SUBMODNAME")),StringUtility.checknull(reusltSet.getString("sortingorder")));
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("SubModuleDAO:findSM for Cd: "+smcode+" :result:"+smbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("SubModuleDAO:findSM:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SubModuleDAO:findSM:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return smbean;
	}

	public void deleteSM(String smcodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			String deleteapprovalSql ="delete  from epis_submodules where SUBMODULECD in ('"+smcodes+"')";
			log.info("SubModuleDAO: Delete SM Qry:"+deleteapprovalSql );
			
			if(connection!=null){
				stmt=connection.createStatement();
				if(stmt!=null){										
					stmt.executeUpdate(deleteapprovalSql);
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
			log.error("SubModuleDAO:deleteSM:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SubModuleDAO:deleteSM:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List getSMList() throws EPISException{
		SMBean smbean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List smList = new ArrayList();
		
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select MODULECODE,SUBMODULECD,SUBMODNAME ,nvl(sortingorder,0) sortingorder from epis_submodules ";
			rs = DBUtility.getRecordSet(query,st);
			
			while(rs.next()){
				smbean = new SMBean(rs.getString("SUBMODULECD"),rs.getString("MODULECODE"),
				rs.getString("SUBMODNAME"),rs.getString("sortingorder"));
				smList.add(smbean);
			}
		}catch (SQLException sqle) {
			log.error("SubModuleDAO:getSMList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("SubModuleDAO:getSMList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return smList;
	}
	public List getModuleList() throws EPISException{
		
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		Bean mbean=null;
		List mList = new ArrayList();
		
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select CODE,NAME from epis_modules ";
			rs = DBUtility.getRecordSet(query,st);
			
			while(rs.next()){
				mbean = new Bean(rs.getString("code"),rs.getString("name"));
				mList.add(mbean);
			}
		}catch (SQLException sqle) {
			log.error("SubModuleDAO:getModuleList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("SubModuleDAO:getModuleList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return mList;
	}	
                   
}
