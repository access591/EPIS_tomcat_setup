
/**
 * @author anilkumark
 *
 */

package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.Configurations;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.admin.ScreenBean;


public class CommonMenueDAO {
	
	Log log  = new Log(CommonMenueDAO.class);
	private CommonMenueDAO(){
		
	}
	private static final CommonMenueDAO dao = new CommonMenueDAO();
	public static CommonMenueDAO getInstance(){
		return dao;
	}
	public Map getMenueItems(String username,String modulecd,String roleCd) throws ServiceNotAvailableException, EPISException{
		LinkedHashMap map =new LinkedHashMap();
		List list =new ArrayList();
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;	
		ScreenBean sbean =null;
		String comSM="";
		String SM="";
		int count=0;
		try{	
			String Qry="";
			if("USER".equals(Configurations.getAccessRightsType())){
				Qry="SELECT AR.SCREENCODE,USERID,SCREENNAME,PATH,(select SUBMODNAME from epis_submodules where SUBMODULECD=AC.SUBMODULECD) submodule,(select SORTINGORDER from epis_submodules  where SUBMODULECD = AC.SUBMODULECD) SORTING FROM EPIS_ACCESSCODES_MT AC,epis_accessrights AR WHERE AR.SCREENCODE=AC.SCREENCODE AND AR.USERID=TRIM(?) AND AC.MODULECODE=TRIM(?)  order by SORTING,SUBMODULECD,AC.SORTINGORDER,SCREENCODE";
			}else if("ROLE".equals(Configurations.getAccessRightsType())){
				Qry="SELECT AR.SCREENCODE,USERID,SCREENNAME,PATH,(select SUBMODNAME from epis_submodules where SUBMODULECD=AC.SUBMODULECD) submodule,(select SORTINGORDER from epis_submodules  where SUBMODULECD = AC.SUBMODULECD) SORTING FROM EPIS_ACCESSCODES_MT AC,epis_accessrights AR WHERE AR.SCREENCODE=AC.SCREENCODE AND AR.STAGECD=TRIM(?) AND AC.MODULECODE=TRIM(?)  order by SORTING,SUBMODULECD,AC.SORTINGORDER,SCREENCODE";
				username=roleCd;
			}
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(Qry);
				log.info("menue qry::"+Qry+" param::"+username+""+modulecd);
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(username));
					pstmt.setString(2,StringUtility.checknull(modulecd));
					rs = pstmt.executeQuery();
					
					
					while(rs.next()){
						sbean=new ScreenBean();
						SM=rs.getString("SUBMODULE");
						if(!SM.equals(comSM)){
						if(count!=0)
							map.put(comSM,list);
						comSM=SM;
						list =new ArrayList();
						}
						sbean.setScreenPath(rs.getString("PATH")+"&screenCode="+rs.getString("SCREENCODE"));
						sbean.setScreenName(rs.getString("SCREENNAME"));
						list.add(sbean);
						count++;
					}
					map.put(comSM,list);
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
		//log.info("ScreenDAO:searchScreen for screenName: "+screenName+" ,submodule: "+submodule+" ,module: "+module +" Result : "+list.size()+"Records");
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("MenueDAO:getMenueItems:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("MenueDAO:getMenueItems:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,connection);			
		}
		return map;
	}

	 
}
