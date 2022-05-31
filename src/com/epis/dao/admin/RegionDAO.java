package com.epis.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.epis.bean.admin.RegionBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class RegionDAO {	
	
	Log log  = new Log(RegionDAO.class);
	
	private RegionDAO(){
		
	}
	
	private static final RegionDAO regionDAO = new RegionDAO();
	public static RegionDAO getInstance(){
		return regionDAO;
	}
	
	public List searchRegion(String regionName,String aaiCategory) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select * from employee_region_master where regionid is not null and upper(regionname) like upper(nvl(?,''))||'%' and AAICATEGORY like upper(nvl(?,''))||'%' ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(regionName));
					pstmt.setString(2,StringUtility.checknull(aaiCategory));
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						RegionBean region = new RegionBean(StringUtility.checknull(reusltSet.getString("REGIONID")),StringUtility.checknull(reusltSet.getString("REGIONNAME")),StringUtility.checknull(reusltSet.getString("AAICATEGORY")),StringUtility.checknull(reusltSet.getString("REMARKS")));
						list.add(region);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("RegionDAO:Search Region for regionName: "+regionName+" ,aaiCategory: "+aaiCategory +"Results : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("RegionDAO:searchRegion:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("RegionDAO:searchRegion:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return list;
	}

	public void saveRegion(RegionBean rbean) throws ServiceNotAvailableException, EPISException {
		String regioncd = "";
		Connection connection=null;		
		PreparedStatement pstmt=null;
		int recordCnt =0;
		
		try{
			connection = DBUtility.getConnection();
			if(connection!=null){
				recordCnt=DBUtility.getRecordCount("SELECT COUNT(REGIONNAME) FROM  employee_region_master WHERE Upper(trim(REGIONNAME))=Upper('"+rbean.getRegionName()+"')");
				
				if(recordCnt!=0)
				{   
				  throw new Exception("Record Already Exists with Region Name : " + rbean.getRegionName());
				}
				
				regioncd =AutoGeneration.getNextCode("employee_region_master","REGIONID",2,connection);
				String saveregionSql ="insert into employee_region_master (REGIONID, REGIONNAME,AAICATEGORY, REMARKS,created_by,created_dt) values (?,?,?,?,?,SYSDATE)";
				pstmt=connection.prepareStatement(saveregionSql);		
				
				if(pstmt!=null){
					pstmt.setString(1,regioncd);
					pstmt.setString(2,StringUtility.checknull(rbean.getRegionName()));
					pstmt.setString(3,StringUtility.checknull(rbean.getAaiCategory()));
					pstmt.setString(4,StringUtility.checknull(rbean.getRemarks()));
					pstmt.setString(5,StringUtility.checknull(rbean.getLoginUserId()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("Region DAO : Region created with info :"+rbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("RegionDAO:saveRegion:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("RegionDAO:saveRegion:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editRegion(RegionBean rbean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;	
		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  employee_region_master set REGIONNAME=?,AAICATEGORY=?, REMARKS=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where REGIONID=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					pstmt.setString(1,StringUtility.checknull(rbean.getRegionName()));
					pstmt.setString(2,StringUtility.checknull(rbean.getAaiCategory()));
					pstmt.setString(3,StringUtility.checknull(rbean.getRemarks()));
					pstmt.setString(4,StringUtility.checknull(rbean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(rbean.getRegionCd()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("Region DAO :edit Region  with info :"+rbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Region DAO:editRegion:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Region DAO:editRegion:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public RegionBean findRegion(String regioncd) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		RegionBean region=null;
		
		try{	
			String searchQry="select * from employee_region_master where regionid =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(regioncd));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						 region = new RegionBean(StringUtility.checknull(reusltSet.getString("REGIONID")),StringUtility.checknull(reusltSet.getString("REGIONNAME")),StringUtility.checknull(reusltSet.getString("AAICATEGORY")),StringUtility.checknull(reusltSet.getString("REMARKS")));
						
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("RegionDAO:findRegion for Cd: "+regioncd+" :result:"+region);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Region DAO:findRegion:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Region DAO:findRegion:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return region;
	}

	public void deleteRegion(String regioncds) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			
			connection = DBUtility.getConnection();
			String deleteRegionSql ="delete  from employee_region_master where regionid in ("+regioncds+")";
			log.info("Region DAO: Delete Regions Qry:"+deleteRegionSql );
			
			if(connection!=null){
				stmt=connection.createStatement();
				if(stmt!=null){										
					stmt.executeUpdate(deleteRegionSql);
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
			log.error("Region DAO:deleteRegion:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Region DAO:deleteRegion:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}	
	public List getRegionList() throws Exception{		
		List regionList = new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		RegionBean region=null;
		
		try{	
			String searchQry="select * from employee_region_master where regionid is not null ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){									
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						 region = new RegionBean(StringUtility.checknull(reusltSet.getString("REGIONID")),StringUtility.checknull(reusltSet.getString("REGIONNAME")),StringUtility.checknull(reusltSet.getString("AAICATEGORY")),StringUtility.checknull(reusltSet.getString("REMARKS")),StringUtility.checknull(reusltSet.getString("REGIONCD")));
						 regionList.add(region);
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Region DAO:Region List:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Region DAO:Region List:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return regionList;
	}	
	
}

