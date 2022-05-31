package com.epis.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.epis.bean.admin.AccessRightsBean;
import com.epis.bean.admin.ScreenOptionsBean;
import com.epis.common.exception.EPISException;
import com.epis.utilities.Configurations;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class  AccessRightsDAO {
	
	Log log  = new Log(AccessRightsDAO.class);
	
	private AccessRightsDAO(){
		
	}
	private static final AccessRightsDAO dao = new AccessRightsDAO();
	public static AccessRightsDAO getInstance(){
		
		return dao;
	}
	public Map getAccessCodes(String modules) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		Map accessCodes = new LinkedHashMap();
		AccessRightsBean bean = null;
		try{
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer("select Nvl(code,' ') code,Nvl(name,' ') name, ");
			query.append("  Nvl(sub.submodulecd,' ') submodulecd,Nvl(sub.submodname,' ') submodname, ");
			query.append(" Nvl(mt.screencode,' ') screencode,Nvl(mt.screenname,' ') screenname from epis_modules modules,epis_submodules sub,");
			query.append(" epis_accesscodes_mt mt where code = modulecode(+) and mt.submodulecd(+) = ");
			query.append(" sub.submodulecd and code = ? order by code, sub.modulecode, mt.screencode ");
			pst = con.prepareStatement(query.toString());
			pst.setString(1,modules);
			rs = pst.executeQuery();
			while(rs.next()){
				bean = new AccessRightsBean();
				bean.setModuleCode(StringUtility.checknull(rs.getString("code")));
				bean.setModuleName(StringUtility.checknull(rs.getString("name")));
				bean.setSubModuleCode(StringUtility.checknull(rs.getString("submodulecd")));
				bean.setSubModuleName(StringUtility.checknull(rs.getString("submodname")));
				bean.setScreenCode(StringUtility.checknull(rs.getString("screencode")));
				bean.setScreenName(StringUtility.checknull(rs.getString("screenname")));
				accessCodes.put(rs.getString("screencode"),bean);
			}
		}catch(Exception e){
			log.error("AccessRightsDAO:getAccessCodes:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}		 
		return accessCodes;
	}
	public List getAccessRights(String userType, String user) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
	
		ArrayList list=new ArrayList();	
		
		
		try{
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer("select trim(screencode) screencode from epis_accessrights where screencode is not null and" );			
			query.append("USER".equals(userType)?" userid ":"stagecd ");
			query.append(" = ? ");
			pst = con.prepareStatement(query.toString());
			pst.setString(1,user);			
			rs = pst.executeQuery();
			while(rs.next()){
				
				list.add(rs.getString("screencode"));
			}
		}catch(Exception e){
			log.error("AccessRightsDAO:getAccessRights:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}		 
		return list;
	}	  
	
	public void saveAccessRights(List screens,String userorroleId,String modulecodes) throws EPISException{
		Connection con = null;		
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		
		try{
			con = DBUtility.getConnection();
			String deleteQuery=" delete from epis_accessrights where userid=? and instr('"+modulecodes+"',substr(screencode,1,2))>0";
			if("ROLE".equals(Configurations.getAccessRightsType())){
				deleteQuery=" delete from epis_accessrights where STAGECD=? and  instr('"+modulecodes+"',substr(screencode,1,2))>0";
			}
			String insertQuery="insert into epis_accessrights (USERID,STAGECD,SCREENCODE,ACCESSRIGHT,CREATEDON,created_by)values(?,?,?,'Y',sysdate,?)";
			con.setAutoCommit(false);
			pst=con.prepareStatement(deleteQuery);
			pst.setString(1,userorroleId);
			pst.execute();
			pst1=con.prepareStatement(insertQuery);
			String []screeninfo=new String[2];
			//String flag="N";
			Iterator it=screens.iterator();
			//for(int count=0;count<screens.size();count++){
				while(it.hasNext()){
				screeninfo=(String[])it.next();
				
				//flag="N";
				if("USER".equals(Configurations.getAccessRightsType())){
					pst1.setString(1,userorroleId);
					pst1.setString(2,null);
				}else if ("ROLE".equals(Configurations.getAccessRightsType())){
					pst1.setString(1,null);
					pst1.setString(2,userorroleId);
					
				}
				pst1.setString(3,screeninfo[0]);
				//pst1.setString(4,screeninfo[1]);
				//pst1.setString(5,screeninfo[2]);
				//pst1.setString(6,screeninfo[3]);
				//pst1.setString(7,screeninfo[4]);
				//pst1.setString(8,screeninfo[5]);
				//if("Y".equals(screeninfo[1])||"Y".equals(screeninfo[2])||"Y".equals(screeninfo[3])||"Y".equals(screeninfo[4])||"Y".equals(screeninfo[5]))
				//{
				//	flag="Y";
				//}
				//pst1.setString(9,flag);
				pst1.setString(4,screeninfo[1]);
				int count1=pst1.executeUpdate();
				//System.out.println("..................."+count1);
			//}
				}
			
			con.commit();
			
		}catch(Exception e){
			try{
			con.rollback();
			}catch(Exception ex){
				throw new EPISException(ex);
			}
			log.error("AccessRightsDAO:getAccessRights:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(null,pst,con);
		}		 
		
	}
	
	public List getAccessOptions(String ScreenCode,String userName) throws EPISException{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
	
		ArrayList list=new ArrayList();	
		//System.out.println("......."+ScreenCode+"........."+userName);
		
		try{
			con = DBUtility.getConnection();
			StringBuffer query = new StringBuffer("select screenoptioncode,options.screencode,optionname,path,nvl(accessright,'N') accessright ");
			query.append(" from (select screenoptioncode, screencode, optionname, path  from epis_screen_options where screenCode = ? ");
			//query.append(ScreenCode);
			query.append(") options, (select trim(screencode) screencode, accessright from epis_accessrights where screencode is not null and ");				
			query.append("USER".equals(Configurations.getAccessRightsType())?" userid ":"stagecd ");
			query.append("= ?");
			//query.append(userName);			
			query.append(") acr where options.screenoptioncode=acr.screencode(+) order by screenoptioncode" );			
			pst = con.prepareStatement(query.toString());
			//System.out.println(".....getAccessOptions Qry........."+query.toString());
			pst.setString(1,ScreenCode);	
			pst.setString(2,userName);
			rs = pst.executeQuery();
			System.out.println(query.toString());
			while(rs.next()){
				
				list.add(new ScreenOptionsBean(rs.getString("screenoptioncode"),rs.getString("optionname"),rs.getString("path"),rs.getString("accessright")));
				
			}
		}catch(Exception e){
			log.error("AccessRightsDAO:getAccessOptions:Exception:"+e.getMessage());
			throw new EPISException(e) ;
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}		 
		return list;
	}
}
