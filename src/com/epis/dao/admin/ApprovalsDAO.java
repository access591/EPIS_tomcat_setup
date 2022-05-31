package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.epis.bean.admin.ApprovalsBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class ApprovalsDAO {
	
	Log log  = new Log(ApprovalsDAO.class);
	private ApprovalsDAO(){
		
	}
	private static final ApprovalsDAO dao = new ApprovalsDAO();
	public static ApprovalsDAO getInstance(){
		return dao;
	}
	public List searchApproval(String approvalCode,String approvalName) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select * from EPIS_APPROVALS where approvalcode is not null and upper(approvalcode) like upper(nvl(?,''))||'%' and upper(nvl(approvalname,'')) like upper(nvl(?,''))||'%' ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(approvalCode));
					pstmt.setString(2,StringUtility.checknull(approvalName));					
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						ApprovalsBean approval = new ApprovalsBean(StringUtility.checknull(reusltSet.getString("approvalcode")),StringUtility.checknull(reusltSet.getString("approvalname")),StringUtility.checknull(reusltSet.getString("description")));
						list.add(approval);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ApprovalsDAO:Search Approval(s) for approvalcode: "+approvalCode+" ,approvalname: "+approvalName+"  Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("ApprovalsDAO:searchApproval:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ApprovalsDAO:searchApproval:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		
		return list;
		
	}

	public void saveApproval(ApprovalsBean apbean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			if(connection!=null){
				String saveunitSql ="insert into EPIS_APPROVALS (approvalcode, approvalname,description,created_by,created_dt) values (?,?,?,?,SYSDATE)";
				String approvalcd =AutoGeneration.getNextCodeGBy("EPIS_APPROVALS","approvalcode",5,"AP",connection);
				pstmt=connection.prepareStatement(saveunitSql);		
				
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(approvalcd));
					pstmt.setString(2,StringUtility.checknull(apbean.getApprovalName()));
					pstmt.setString(3,StringUtility.checknull(apbean.getDescription()));
					pstmt.setString(4,StringUtility.checknull(apbean.getLoginUserId()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("ApprovalsDAO : Approval created with info :"+apbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("ApprovalsDAO:saveApproval:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ApprovalsDAO:saveApproval:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editApproval(ApprovalsBean apbean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  EPIS_APPROVALS set approvalname=?,description=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where approvalcode=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					
					pstmt.setString(1,StringUtility.checknull(apbean.getApprovalName()));
					pstmt.setString(2,StringUtility.checknull(apbean.getDescription()));
					pstmt.setString(3,StringUtility.checknull(apbean.getLoginUserId()));
					pstmt.setString(4,StringUtility.checknull(apbean.getApprovalCode()));					
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("ApprovalsDAO :editApproval  with info :"+apbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ApprovalsDAO:editApproval:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ApprovalsDAO:editApproval:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public ApprovalsBean findApproval(String approvalcode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		ApprovalsBean approval=null;
		
		try{	
			String searchQry="select * from EPIS_APPROVALS where approvalcode =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(approvalcode));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						approval = new ApprovalsBean(StringUtility.checknull(reusltSet.getString("approvalcode")),StringUtility.checknull(reusltSet.getString("approvalname")),StringUtility.checknull(reusltSet.getString("description")));
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ApprovalsDAO:findApproval for Cd: "+approvalcode+" :result:"+approval);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ApprovalsDAO:findApproval:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ApprovalsDAO:findApproval:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return approval;
	}

	public void deleteApproval(String approvalcodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			
			connection = DBUtility.getConnection();			
			String deleteapprovalSql ="delete  from EPIS_APPROVALS where approvalcode in ('"+approvalcodes+"')";
			log.info("ApprovalsDAO: Delete Approval Qry:"+deleteapprovalSql );
			
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
			log.error("ApprovalsDAO:deleteApproval:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ApprovalsDAO:deleteOption:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List getApprovalList() throws EPISException{
		ApprovalsBean apbean = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List approvalsList = new ArrayList();
		
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select approvalcode,approvalname,description  from EPIS_APPROVALS ";
			rs = DBUtility.getRecordSet(query,st);
			
			while(rs.next()){
				apbean = new ApprovalsBean(rs.getString("approvalcode"),rs.getString("approvalname"),
				rs.getString("description"));
				approvalsList.add(apbean);
			}
		}catch (SQLException sqle) {
			log.error("ApprovalsDAO:getApprovalsList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("ApprovalsDAO:getApprovalsList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return approvalsList;
	}	    
                   
}
