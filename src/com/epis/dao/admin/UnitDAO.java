package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.epis.bean.admin.UnitBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class UnitDAO {
	
	Log log  = new Log(UnitDAO.class);
	private UnitDAO(){
		
	}
	private static final UnitDAO dao = new UnitDAO();
	public static UnitDAO getInstance(){
		return dao;
	}
	public List searchUnit(String unitCode,String unitName,String option,String region) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;	
		
		try{	
			String searchQry="select * from employee_unit_master where unitcode is not null and upper(unitcode) like upper(nvl(?,''))||'%' and upper(nvl(unitname,' ')) like upper(nvl(?,''))||'%' and upper(nvl(unitoption,' ')) like upper(nvl(?,''))||'%' and upper(nvl(region,' ')) like upper(nvl(?,''))||'%'";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(unitCode));
					pstmt.setString(2,StringUtility.checknull(unitName));
					pstmt.setString(3,StringUtility.checknull(option));
					pstmt.setString(4,StringUtility.checknull(region));
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						UnitBean unit = new UnitBean(StringUtility.checknull(reusltSet.getString("unitcode")),StringUtility.checknull(reusltSet.getString("unitNAME")),StringUtility.checknull(reusltSet.getString("unitoption")),StringUtility.checknull(reusltSet.getString("Region")));
						list.add(unit);
					}
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("UnitDAO:Search Unit(s) for UnitCode: "+unitCode+" ,UnitName: "+unitName+" ,UnitOption: "+option +"Region : "+region+" Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("UnitDAO:searchUnit:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("UnitDAO:searchUnit:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return list;
	}

	public void saveUnit(UnitBean ubean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		int recordCnt =0;
		
		try{
			connection = DBUtility.getConnection();
			
			if(connection!=null){
				recordCnt=DBUtility.getRecordCount("SELECT COUNT(unitcode) FROM  employee_unit_master WHERE Upper(trim(unitcode))=Upper('"+ubean.getUnitCode()+"')");
				
				if(recordCnt!=0)
				{   
				  throw new Exception("Record Already Exists with Unit Code : " + ubean.getUnitCode());
				}
				String saveunitSql ="insert into employee_unit_master (UNITCODE, UNITNAME,UNITOPTION, REGION,created_by,created_dt) values (?,?,?,?,?,SYSDATE)";
				pstmt=connection.prepareStatement(saveunitSql);
				
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(ubean.getUnitCode()));
					pstmt.setString(2,StringUtility.checknull(ubean.getUnitName()));
					pstmt.setString(3,StringUtility.checknull(ubean.getOption()));
					pstmt.setString(4,StringUtility.checknull(ubean.getRegion()));
					pstmt.setString(5,StringUtility.checknull(ubean.getLoginUserId()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("UnitDAO : Unit created with info :"+ubean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("UnitDAO:saveUnit:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			log.error("UnitDAO:saveUnit:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public void editUnit(UnitBean ubean) throws ServiceNotAvailableException, EPISException {
			
		Connection connection=null;		
		PreparedStatement pstmt=null;	
		
		try{		
			connection=DBUtility.getConnection();	
			String updateregionSql ="update  employee_unit_master set UNITNAME=?,unitoption=?, region=?,UPDATED_BY=?,UPDATED_DT=SYSDATE where unitcode=?";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateregionSql);		
				if(pstmt!=null){					
					pstmt.setString(1,StringUtility.checknull(ubean.getUnitName()));
					pstmt.setString(2,StringUtility.checknull(ubean.getOption()));
					pstmt.setString(3,StringUtility.checknull(ubean.getRegion()));
					pstmt.setString(4,StringUtility.checknull(ubean.getLoginUserId()));
					pstmt.setString(5,StringUtility.checknull(ubean.getUnitCode()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("UnitDAO :edit Unit with info :"+ubean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Unit DAO:editUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Unit DAO:editUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public UnitBean findUnit(String unitcode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		UnitBean unit=null;
		
		try{	
			String searchQry="select * from employee_unit_master where unitcode =? ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(unitcode));				
					reusltSet = pstmt.executeQuery();
					if(reusltSet.next()){
						unit = new UnitBean(StringUtility.checknull(reusltSet.getString("UNITCODE")),StringUtility.checknull(reusltSet.getString("UNITNAME")),StringUtility.checknull(reusltSet.getString("UNITOPTION")),StringUtility.checknull(reusltSet.getString("REGION")));
					}
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("RegionDAO:findRegion for Cd: "+unitcode+" :result:"+unit);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Unit DAO:findUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Unit DAO:findUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return unit;
	}

	public void deleteUnit(String unitcodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			connection = DBUtility.getConnection();			
			String deleteunitSql ="delete  from employee_unit_master where unitcode in ('"+unitcodes+"')";
			log.info("Unit DAO: Delete Units Qry:"+deleteunitSql );
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
			log.error("Unit DAO:deleteUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Unit DAO:deleteUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,stmt,connection);	
		}
	}
	public List getUnitList() throws EPISException{
		UnitBean unit = null;
		Connection con = null;
		ResultSet rs = null;
		Statement st = null;
		List unitList = new ArrayList();
		
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select UNITCODE,UNITNAME,UNITOPTION,REGION  from employee_unit_master ";
			rs = DBUtility.getRecordSet(query,st);
			
			while(rs.next()){
				unit = new UnitBean(rs.getString("UNITCODE"),rs.getString("UNITNAME"),
						rs.getString("UNITOPTION"),rs.getString("REGION"));
				unitList.add(unit);
			}
		}catch (SQLException sqle) {
			log.error("UnitDAO:getUnitList:Exception:"+sqle.getMessage());
			throw new EPISException(sqle) ;
		}catch (Exception e) {
			log.error("UnitDAO:getUnitList:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,st,con);
		}
		return unitList;
	}	    
                   
}
