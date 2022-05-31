
package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epis.bean.admin.Bean;
import com.epis.bean.admin.RoleBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class RoleDAO {
	
	Log log  = new Log(RoleDAO.class);
	private RoleDAO(){
		
	}
	private static final RoleDAO dao = new RoleDAO();
	public static RoleDAO getInstance(){
		return dao;
	}
	public List searchRole(String roleName) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select * from epis_role where ROLENAME is not null and upper(ROLENAME) like upper(nvl(?,''))||'%' ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(roleName));
								
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						RoleBean sm = new RoleBean(StringUtility.checknull(reusltSet.getString("ROLECD")),StringUtility.checknull(reusltSet.getString("ROLENAME")),StringUtility.checknull(reusltSet.getString("DESCRIPTION")),StringUtility.checknull(reusltSet.getString("MODULES")));
						list.add(sm);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("RoleDAO:Search  for Role name: "+roleName+"  Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("RoleDAO:searchApproval:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("RoleDAO:searchApproval:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		
		return list;
		
	}

	public void saveRole(RoleBean bean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			if(connection!=null){
				String saveunitSql ="insert into epis_role (ROLECD, ROLENAME,DESCRIPTION,MODULES,created_by,CREATEDON) values (?,?,?,?,?,SYSDATE)";
				String rolecd =AutoGeneration.getNextCode("epis_role","ROLECD",5,connection);
				pstmt=connection.prepareStatement(saveunitSql);		
				
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(rolecd));
					pstmt.setString(2,StringUtility.checknull(bean.getRoleName()));
					pstmt.setString(3,StringUtility.checknull(bean.getRoleDescription()));	
					pstmt.setString(4,StringUtility.checknull(bean.getModules()));	
					pstmt.setString(5,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("RoleDAO : Role created with info :"+bean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("RoleDAO:saveRole:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("RoleDAO:saveRole:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editRole(RoleBean bean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  epis_role set ROLENAME=?,DESCRIPTION=?,MODULES=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where ROLECD=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(bean.getRoleName()));
					pstmt.setString(2,StringUtility.checknull(bean.getRoleDescription()));
					pstmt.setString(3,StringUtility.checknull(bean.getModules()));
					pstmt.setString(4,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(bean.getRoleCd()));		
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("RoleDAO :editRole  with info :"+bean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("RoleDAO:editRole:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoleDAO:editRole:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public RoleBean findRole(String roleCd) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		RoleBean bean=null;
		
		try{	
			String searchQry="select * from epis_role where ROLECD =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(roleCd));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						bean = new RoleBean(StringUtility.checknull(reusltSet.getString("ROLECD")),StringUtility.checknull(reusltSet.getString("ROLENAME")),StringUtility.checknull(reusltSet.getString("DESCRIPTION")),StringUtility.checknull(reusltSet.getString("MODULES")));
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("RoleDAO:findRole for Cd: "+roleCd+" :result:"+bean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("RoleDAO:findRole:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoleDAO:findRole:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return bean;
	}

	public void deleteRole(String roleCd) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			String deleteapprovalSql ="delete  from epis_role where  instr ('"+roleCd+"',ROLECD)>0";
			System.out.println("RoleDAO: Delete Role Qry:"+deleteapprovalSql );
			
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
			log.error("RoleDAO:deleteRole:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoleDAO:deleteRole:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List getRoles(String userId)throws ServiceNotAvailableException, EPISException {
		Connection connection=null;		
		PreparedStatement pst=null;
		List roles=new ArrayList();
		ResultSet rs=null;
		try{
			
			connection = DBUtility.getConnection();
			StringBuffer query = new StringBuffer(" select rolecd,rolename from epis_role where rolecd is");
			query.append(" not null and rolecd != case when '1' != ? then '00001' else '%' end " );			
			pst = connection.prepareStatement(query.toString());
			pst.setString(1,userId);
			rs = pst.executeQuery();			
			while(rs.next()){
				roles.add(new Bean(rs.getString("rolecd"),rs.getString("rolename")));
			}			
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("RoleDAO:deleteRole:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("RoleDAO:deleteRole:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pst,connection);	
		}
		return roles;
	}
	    
}
