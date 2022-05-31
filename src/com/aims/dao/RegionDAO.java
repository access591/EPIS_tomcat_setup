/*
 * Created on Jun 22, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.aims.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.epis.utilities.ApplicationException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.Log;
import com.aims.info.configuration.RegionInfo;


/**
 * @author rajanin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RegionDAO {
	Properties prop=null;		
	DBAccess db = DBAccess.getInstance();
	Connection con = null;
	ResultSet rs = null;
	Log log = new Log(RegionDAO.class);
	
	private static RegionDAO regionDaoInstance = new RegionDAO();
	
	public RegionDAO() {
		try {
			//db.makeConnection();
			//con = db.getConnection();
			prop=CommonUtil.getPropsFile(Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,RegionDAO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static RegionDAO getInstance() {
		return regionDaoInstance;
	}
	public void saveRegion(RegionInfo regionInfo) throws ApplicationException{
		String regioncd = "";
		String usercd ="";
		int recordCnt =0;
		try{
			
			db.makeConnection();
			con = db.getConnection();
			recordCnt=db.getRecordCount("SELECT COUNT(*) FROM  regionmaster WHERE Upper(trim(REGIONNM))=Upper('"+regionInfo.getRegionname()+"')");
			if(recordCnt!=0)
			{   
			  throw new Exception("Record Already Exists with Region : " + regionInfo.getRegionname());
			}
			regioncd =AutoGeneration.getNextCodeGBy("regionmaster","REGIONCD",6,"RHQ",con);
			
			String saveRegionSql ="insert into regionmaster(REGIONCD, REGIONNM, REGIONDESCR, STATUS,USERCD) values ('"+regioncd+"','"+regionInfo.getRegionname()+"','"+regionInfo.getRegiondescr()+"','A','"+regionInfo.getUserCd()+"')";
			log.info("-------Insert Region Query---------"+saveRegionSql);	
			
			db.executeUpdate(saveRegionSql);		
			  db.commitTrans();
		}catch(Exception e){
			log.info("<<<<<<<<<< Insert Region Exception  >>>>>>>>>>>>"+ e.getMessage());
			throw new ApplicationException(e.toString());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public void editRegion(RegionInfo regionInfo)throws ApplicationException{
		try{
		    db.makeConnection();
		    con = db.getConnection();
		    db.setAutoCommit(false);
		    String editRegionSql = "update regionmaster set REGIONNM='"+regionInfo.getRegionname()+"',REGIONDESCR='"+regionInfo.getRegiondescr()+"',STATUS='"+regionInfo.getStatus()+"' where REGIONCD = '"+regionInfo.getRegioncd()+"'";
		    log.info(">>>>>>>>>>>>>>>> EditRegion sql>>>>>>>>>>>>> "+editRegionSql);
		    db.executeUpdate(editRegionSql);
			db.commitTrans();
		}catch(Exception e){
			log.info("<<<<<<<<<< editRegion Exception  >>>>>>>>>>>>"+ e.getMessage());
			throw new ApplicationException();
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public RegionInfo findByRegionCd(String regioncd){
		RegionInfo info = new RegionInfo();
		try {
			db.makeConnection();
			con = db.getConnection();
			String query = "Select * from regionmaster  where REGIONCD = '"+regioncd+"'";
			log.info(">>>>>>>>>>>>findByRegioncdCd >>> "+query);
			rs = db.getRecordSet(query);
			if(rs.next()){
				info.setRegioncd(rs.getString("REGIONCD"));				
				info.setRegionname(rs.getString("REGIONNM"));				
                info.setRegiondescr(rs.getString("REGIONDESCR"));
                info.setStatus(rs.getString("STATUS"));
			}
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in findByRegionCd "+e.getStackTrace());			
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return info;
	}
	public List searchRegion(RegionInfo searchRegionInfo){
		List regionList = new ArrayList();
		try{
			db.makeConnection();
			con = db.getConnection();
			String searhRegionquery = "select REGIONCD,REGIONNM,REGIONDESCR,DECODE(Status,'A','Active','N','InActive') Status from regionmaster where REGIONCD is not null ";
			System.out.println("searhRegionquery "+searhRegionquery);
			if(!searchRegionInfo.getRegionname().trim().equals("")){
				searhRegionquery = searhRegionquery+" and Upper(REGIONNM) like Upper('%"+ searchRegionInfo.getRegionname().trim() + "%')";
			}
			if(!searchRegionInfo.getRegioncd().equals("")){
				searhRegionquery = searhRegionquery+" and Upper(REGIONCD) = Upper('"+ searchRegionInfo.getRegioncd().trim()+"')"; 
			}
			searhRegionquery = searhRegionquery+" order by  REGIONCD ";
			log.info("<<<<<<<<<<<<<<<<< SearchRegion query>>>>>>>>>>>"+searhRegionquery);
			rs = db.getRecordSet(searhRegionquery);
			RegionInfo regionInfo;
			while(rs.next()){
				regionInfo =  new RegionInfo();
				regionInfo.setRegionname(rs.getString("REGIONNM"));				
				regionInfo.setRegioncd(rs.getString("REGIONCD"));
				regionInfo.setRegiondescr(rs.getString("REGIONDESCR"));
				regionInfo.setStatus(rs.getString("Status"));
				regionList.add(regionInfo);
			}
          System.out.println("regionList "+regionList);			
		}catch(Exception e){
			log.info("<<<<<<<<<< searchRegion Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return regionList;		
	}
	public List selectRegionCd() {
		log.info("inside dao select selectRegionCd");
		List list = new ArrayList();
        String Qry ="";
		try {
			
			db.makeConnection();con = db.getConnection();
			Qry = "select REGIONCD,REGIONNM from regionmaster";
			rs = db.getRecordSet(Qry);
			while(rs.next()){
				RegionInfo region = new RegionInfo();
				region.setRegioncd(rs.getString("REGIONCD"));
				region.setRegionname(rs.getString("REGIONNM"));
				list.add(region);
			}
			//log.info("list in dao selectRegionCd " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db.closeRs();
			    db.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}

	/*public void deleteRegion(String region){
		try {
			db.makeConnection();
			con = db.getConnection();
			db.setAutoCommit(false);
			StringTokenizer st = new StringTokenizer(region, ",");
			int i = 0;
			String sSQL = "";
			while (st.hasMoreTokens()) {
				sSQL = "update regionmaster  set deletedflag = 'Y' where regioncd = '"+st.nextToken()+"'";
				log.info("deleteRegion query "+sSQL);
				db.executeUpdate(sSQL);
			}
			log.info("deleted  record successfully in deleteRegion() " + i);
			db.commitTrans();
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}*/
	}

