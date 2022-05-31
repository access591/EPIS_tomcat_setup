package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.epis.bean.admin.UnitBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.admin.ScreenBean;
import com.epis.bean.investment.InvestmentProposalBean;

public class ScreenDAO {
	
	Log log  = new Log(ScreenDAO.class);
	private ScreenDAO(){
		
	}
	private static final ScreenDAO dao = new ScreenDAO();
	public static ScreenDAO getInstance(){
		return dao;
	}
	public List searchScreen(String screenName,String submodule,String module) throws ServiceNotAvailableException, EPISException{
		List list =new ArrayList();
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;	
		ScreenBean sbean =null;
		try{	
			String searchQry="SELECT SCREENCODE,module,submodule,SCREENNAME,nvl(sortingorder,0) sortingorder FROM (select SCREENCODE,SCREENNAME,(select NAME from epis_modules where CODE=sc.MODULECODE) module,(select SUBMODNAME from epis_submodules where SUBMODULECD=sc.SUBMODULECD) submodule,sortingorder  from epis_accesscodes_mt sc) where SCREENCODE is not null and upper(SCREENNAME) like '%'||upper(nvl(?,''))||'%' and upper(nvl(submodule,'')) like upper(nvl(?,''))||'%' and upper(nvl(module,'')) like upper(nvl(?,''))||'%'";
			connection = DBUtility.getConnection();
			
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);
				log.info("search qry::"+searchQry+" param::"+screenName+""+submodule+""+module);
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(screenName));
					pstmt.setString(2,StringUtility.checknull(submodule));
					pstmt.setString(3,StringUtility.checknull(module));				
					rs = pstmt.executeQuery();
					log.info("search qry::");
					
					while(rs.next()){
						sbean=new ScreenBean();
						sbean.setScreenCD(StringUtility.checknull(rs.getString("SCREENCODE")));
						sbean.setModuleName(StringUtility.checknull(rs.getString("module")));
						sbean.setSubModuleName(StringUtility.checknull(rs.getString("submodule")));
						sbean.setScreenName(StringUtility.checknull(rs.getString("SCREENNAME")));
						sbean.setSortingOrder(StringUtility.checknull(rs.getString("sortingorder")));
						list.add(sbean);
					}
					log.info("search qry:eeeee:");
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ScreenDAO:searchScreen for screenName: "+screenName+" ,submodule: "+submodule+" ,module: "+module +" Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("ScreenDAO:searchScreen:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("ScreenDAO:searchScreen:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,connection);			
		}
		return list;
	}

public void saveScreen(ScreenBean sbean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;
		try{
			connection = DBUtility.getConnection();
			String screenCD =AutoGeneration.getNextCodeGBy("epis_accesscodes_mt","SCREENCODE",6,sbean.getSubModuleCd(),connection);
			if(connection!=null){
				String saveSql ="insert into epis_accesscodes_mt (SCREENCODE, SCREENNAME,SUBMODULECD, PATH,MODULECODE,sortingorder,CREATEDON,created_by) values (?,?,?,?,?,?,SYSDATE,?)";
				pstmt=connection.prepareStatement(saveSql);
				if(pstmt!=null){
					pstmt.setString(1,screenCD);
					pstmt.setString(2,StringUtility.checknull(sbean.getScreenName()));
					pstmt.setString(3,StringUtility.checknull(sbean.getSubModuleCd()));
					pstmt.setString(4,StringUtility.checknull(sbean.getScreenPath()));
					pstmt.setString(5,StringUtility.checknull(sbean.getModuleCd()));
					pstmt.setString(6,StringUtility.checknull(sbean.getSortingOrder()));
					pstmt.setString(7,StringUtility.checknull(sbean.getLoginUserId()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
				String details[]=sbean.getOptionDetails();
				if(details.length>0){
					String saveOptions ="insert into epis_screen_options (SCREENOPTIONCODE,SCREENCODE,OPTIONNAME,PATH,CREATEDON,CREATED_BY) values (?,?,?,?,SYSDATE,?)";
					pstmt=connection.prepareStatement(saveOptions);
					for(int i=0;i<details.length;i++)
					{
						StringTokenizer st=new StringTokenizer(details[i],"|");
						while(st.hasMoreTokens())
						{
						String optionCD =AutoGeneration.getNextCodeGBy("epis_screen_options","SCREENOPTIONCODE",8,screenCD,connection);
						String name=st.nextToken();
						String path=st.nextToken();
						
						pstmt.setString(1,optionCD);
						pstmt.setString(2,screenCD);
						pstmt.setString(3,StringUtility.checknull(name));
						pstmt.setString(4,StringUtility.checknull(path));
						pstmt.setString(5,StringUtility.checknull(sbean.getLoginUserId()));
						pstmt.executeUpdate();
						}
					}
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("screenDAO : saveScreen created with info :"+sbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("screenDAO:saveScreen:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			log.error("screenDAO:saveScreen:Exception:"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}


	public void editScreen(ScreenBean sbean) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		PreparedStatement pstmt=null;	
		
		try{		
			connection=DBUtility.getConnection();	
			String updateSql ="update  epis_accesscodes_mt set SCREENNAME=?,PATH=?,UPDATED_BY=?,sortingorder=? ,UPDATED_DT=SYSDATE where SCREENCODE like nvl(?,'')||'%' ";
			if(connection!=null){
				pstmt=connection.prepareStatement(updateSql);		
				if(pstmt!=null){					
					pstmt.setString(1,StringUtility.checknull(sbean.getScreenName()));
					pstmt.setString(2,StringUtility.checknull(sbean.getScreenPath()));
					pstmt.setString(3,StringUtility.checknull(sbean.getLoginUserId()));
					pstmt.setString(4,StringUtility.checknull(sbean.getSortingOrder()));
					pstmt.setString(5,StringUtility.checknull(sbean.getScreenCD()));
					pstmt.executeUpdate();
				}else{
					throw new ServiceNotAvailableException();
				}
				String details[]=sbean.getOptionDetails();
				if(details.length>0){
					
					String delQry="delete from epis_screen_options where upper(SCREENCODE) like '%'||upper(nvl(?,''))||'%'";
					pstmt=connection.prepareStatement(delQry);
					pstmt.setString(1,StringUtility.checknull(sbean.getScreenCD()));
					pstmt.executeUpdate();
					String saveOptions ="insert into epis_screen_options (SCREENOPTIONCODE,SCREENCODE,OPTIONNAME,PATH,CREATEDON,CREATED_BY) values (?,?,?,?,SYSDATE,?)";
					pstmt=connection.prepareStatement(saveOptions);
					for(int i=0;i<details.length;i++)
					{
						StringTokenizer st=new StringTokenizer(details[i],"|");
						while(st.hasMoreTokens())
						{
						String optionCD =AutoGeneration.getNextCodeGBy("epis_screen_options","SCREENOPTIONCODE",8,StringUtility.checknull(sbean.getScreenCD()),connection);
						String name=st.nextToken();
						String path=st.nextToken();
						
						pstmt.setString(1,optionCD);
						pstmt.setString(2,StringUtility.checknull(sbean.getScreenCD()));
						pstmt.setString(3,StringUtility.checknull(name));
						pstmt.setString(4,StringUtility.checknull(path));
						pstmt.setString(5,StringUtility.checknull(sbean.getLoginUserId()));
						pstmt.executeUpdate();
						}
					}
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			log.info("ScreenDAO :edit screen with info :"+sbean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("Screen DAO:editScreen:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("screen DAO:editscreen:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,pstmt,connection);	
		}
	}

	public ScreenBean findScreen(String screencode) throws ServiceNotAvailableException, EPISException {
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		ScreenBean bean=null;
		
		try{	
			String editQry="SELECT SCREENCODE,module,submodule,SCREENNAME,PATH,nvl(sortingorder,0) sortingorder FROM (select SCREENCODE,SCREENNAME,PATH,(select NAME from epis_modules where CODE=sc.MODULECODE) module,(select SUBMODNAME from epis_submodules where SUBMODULECD=sc.SUBMODULECD) submodule,sortingorder  from epis_accesscodes_mt sc) where SCREENCODE is not null and upper(SCREENCODE) like '%'||upper(nvl(?,''))||'%'";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(editQry);	
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(screencode));				
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						bean=new ScreenBean();
						bean.setScreenCD(screencode);
						bean.setScreenPath(rs.getString("PATH"));
						bean.setModuleName(StringUtility.checknull(rs.getString("module")));
						bean.setSubModuleName(StringUtility.checknull(rs.getString("submodule")));
						bean.setSortingOrder(StringUtility.checknull(rs.getString("sortingorder")));
						bean.setScreenName(rs.getString("SCREENNAME"));
						}
					int count=0;
					String cntQry="Select count(*) from epis_screen_options where upper(SCREENCODE) like '%'||upper(nvl(?,''))||'%'";
					pstmt = connection.prepareStatement(cntQry);	
					pstmt.setString(1,StringUtility.checknull(screencode));				
					rs = pstmt.executeQuery();
					if(rs.next())
						count=rs.getInt(1);
					
					String details[]=new String[count+count];
					count=0;
					String detQry="Select * from epis_screen_options where upper(SCREENCODE) like '%'||upper(nvl(?,''))||'%'";
					pstmt = connection.prepareStatement(detQry);	
					pstmt.setString(1,StringUtility.checknull(screencode));				
					rs = pstmt.executeQuery();
					while(rs.next()){
						details[count]=rs.getString("OPTIONNAME");
						count++;
						details[count]=rs.getString("path");
						count++;
					}
					//System.out.println("hi anil  "+count);
					bean.setOptionDetails(details);
				}else {
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		log.info("ScreenDAO:findscreen for Cd: "+screencode+" :result:"+bean);
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			log.error("ScreenDAO:findscreen:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ScreenDAO:findscreen:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,connection);			
		}
		return bean;
	}


public void deleteScreen(String screencodes) throws ServiceNotAvailableException, EPISException {
		
		Connection connection=null;		
		Statement stmt=null;
		
		try{
			connection = DBUtility.getConnection();
			String delQry="delete from epis_screen_options where SCREENCODE in ('"+screencodes+"')";
			String deleteSql ="delete  from epis_accesscodes_mt where SCREENCODE in ('"+screencodes+"')";
			//log.info("screen DAO: Delete screen Qry:"+deleteSql );
			if(connection!=null){
				stmt=connection.createStatement();
				if(stmt!=null){	
					stmt.executeUpdate(delQry);
					stmt.executeUpdate(deleteSql);
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
			log.error("Screen DAO:delete:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Screen DAO:delete:Exception"+e.getMessage());
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
