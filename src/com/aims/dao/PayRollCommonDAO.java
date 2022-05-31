package com.aims.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.List;

import com.epis.utilities.CommonUtil;
import com.epis.utilities.Constants;
import com.epis.utilities.DBAccess;
import com.epis.utilities.Log;
import com.aims.info.configuration.RegionInfo;
import com.aims.info.configuration.StationInfo;


public class PayRollCommonDAO {
	
	Properties prop=null;	
	//DBAccess db = DBAccess.getInstance();
	
	
	private static PayRollCommonDAO accountsetupInstance = new PayRollCommonDAO();

	private PayRollCommonDAO() {		
		prop=CommonUtil.getPropsFile(Constants.PAYROLL_APPLICATION_PROPERTIES_FILE_NAME,PayRollCommonDAO.class);
	}
	
	public static PayRollCommonDAO getInstance() {
		return accountsetupInstance;
	}

	static Log log = new Log(PayRollCommonDAO.class);
	
	
	/*public static boolean checkName(Session session,String tablename,String namecolumn,String namecolumnval,String cdcolumn,int cdcolumnval){
		boolean flag = false;
		Integer userCount=null;
		String sql = "select count(*) from "+tablename+" where "+namecolumn+" = '"+namecolumnval+"' and "+cdcolumn+" <>"+cdcolumnval;
		List list = session.createQuery(sql).list();
		log.info("list size is "+list.size());
		userCount=(Integer)list.get(0);
		if(userCount.intValue()>0)
			flag = true;
		return flag;
	}
*/
	public static String getEmpno(String pensionno){
		String empno="";
	    log.info("CommonDAO:getEmpno() Entering Method");
	    DBAccess db = DBAccess.getInstance();
		ResultSet rs = null;	
		Connection con = null;
		
		
		try{
			db.makeConnection();
			con = db.getConnection();
			String sql = "SELECT EMPNO FROM employeeinfo WHERE PFID="+pensionno;
			rs=db.getRecordSet(sql);
			log.info("<<<<<<<<<< Exception --------->>>>>>>>>>>>"+ sql);
			if(rs.next()){
				if(rs.getString("EMPNO")!=null){
					empno=rs.getString("EMPNO");
				}
			
			}
		
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception --------->>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		log.info("CommonDAO:checkName() Leaving Method");
	    return empno;
	}
	public static void getFinYears(){
		String finyear="",tempfinYear="",paymonthid="",paymonth="";
	    log.info("CommonDAO:getEmpno() Entering Method");
	    DBAccess db = DBAccess.getInstance();
		ResultSet rs = null;	
		Connection con = null;
		Map monthsMap=new LinkedHashMap();
		Map findata=new LinkedHashMap();
		try{
			db.makeConnection();
			con = db.getConnection();
			String sql = "select (CASE WHEN f.fyeardescr = lag(f.fyeardescr) over(order by f.fyeardescr) THEN NULL   ELSE f.fyeardescr   END ) as fyeardescr,p.payrollmonthid as paymnthid, p.payrollmonthnm as paymnthnm from financialyear f, monthlypayroll p where f.status = 'A' and p.fyearcd = f.fyearcd";
			rs=db.getRecordSet(sql);
			while(rs.next()){
				if(rs.getString("fyeardescr")!=null){
					finyear=rs.getString("fyeardescr");
					
				}
				if(rs.getString("payrollmonthid")!=null){
					paymonthid=rs.getString("payrollmonthid");
				}
				if(rs.getString("payrollmonthnm")!=null){
					paymonth=rs.getString("payrollmonthnm");
				}
				monthsMap.put(paymonthid,paymonth);
				
			}
		
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception --------->>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		log.info("CommonDAO:checkName() Leaving Method");
	    
	}
	public static String checkName(String tablename,String namecolumn,String namecolumnval,String cdcolumn,String cdcolumnval,String type){
    log.info("CommonDAO:checkName() Entering Method");
		
		DBAccess db = DBAccess.getInstance();
		ResultSet rs = null;	
		Connection con = null;
		
		String errors = new String();
		try{
			db.makeConnection();con = db.getConnection();
			String sql = " select "+namecolumn+","+cdcolumn+" from "+tablename+" where ("+namecolumn+" = '"+namecolumnval+"' or "+cdcolumn+" = '"+cdcolumnval+"'"+")";
			
			if(type.equals("edit")){
				//sql = " select "+namecolumn+","+cdcolumn+" from "+tablename+" where "+namecolumn+" = '"+namecolumnval+"'";
				sql = sql+" and  "+cdcolumn+" != '"+cdcolumnval+"'";
			}
			//String sql = "select count(*) from "+tablename+" where "+namecolumn+" = '"+namecolumnval+"' and "+cdcolumn+" <>"+cdcolumnval;
			log.info("in common dao checkname()"+sql);
			
			rs=db.getRecordSet(sql);
			while(rs.next()){
				String nm=rs.getString(1);
				log.info(namecolumn+" : "+nm+" namecolumnval "+namecolumnval+" "+nm.equals(namecolumnval));
				if(nm.equals(namecolumnval))
					errors = errors+namecolumn+",";
				String cd = rs.getString(2);
				log.info(cdcolumn+" : "+cd+" cdcolumnval "+cdcolumnval+(cd .equals( cdcolumnval)));
				if(cd .equals( cdcolumnval)){
					errors= errors+cdcolumn+",";
				}				
			}
			log.info("errors in common dao errors "+errors.toString());
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception --------->>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}		
		log.info("CommonDAO:checkName() Leaving Method");
		return errors;
	}
	public static String getNameFromCd(String tablename,String namecolumn,String cdcolumn,Integer cd){
		
		log.info("CommonDAO:getNameFromCd() Entering Method");
		DBAccess db = DBAccess.getInstance();
		ResultSet rs = null;	
		Connection con = null;
		String name = "",sSQL = "";		
		try{
			db.makeConnection();con = db.getConnection();
			sSQL = "select "+namecolumn+" from "+tablename+" where "+cdcolumn+" = "+cd;
			rs=db.getRecordSet(sSQL);
			if(rs.next()){
				name=rs.getString(1);
			}			
			log.info("name ---------in getNameFromCd() is-----------------------------"+name);			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		log.info("CommonDAO:getNameFromCd() Leaving Method");
		return name;
	}
	public static String getNameFromCd(String tablename,String namecolumn,String cdcolumn,String cd){
	
		log.info("CommonDAO:getNameFromCd() Entering Method");
		DBAccess db = DBAccess.getInstance();
		ResultSet rs = null;	
		Connection con = null;
		String name = "",sSQL = "";
	
		try{
			db.makeConnection();con = db.getConnection();
			sSQL = "select "+namecolumn+" from "+tablename+" where "+cdcolumn+" = '"+cd+"'";
			log.info("=====sSQL====="+sSQL);
			rs=db.getRecordSet(sSQL);
			if(rs.next()){
				name=rs.getString(1);
			}			
			log.info("name ---------in getNameFromCd() is-----------------------------"+name);			
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
		} finally {
			try {
				db.closeRs();
				db.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		log.info("CommonDAO:getNameFromCd() Leaving Method");
		return name;
	}
	DBAccess db1 = DBAccess.getInstance();
	ResultSet rs1 = null;	
	Connection con1 = null;
	public List getRegions(String usercd) {
		List list = new ArrayList();
        String Qry ="";
		try {
			
			db1.makeConnection();con1 = db1.getConnection();
			Qry = "select REGIONCD,REGIONNM from regionmaster where REGIONCD=(select REGIONCD from userregistration where usercd='"+usercd+"') and STATUS='A' ";
			rs1 = db1.getRecordSet(Qry);
			while(rs1.next()){
				RegionInfo region = new RegionInfo();
				region.setRegioncd(rs1.getString("REGIONCD"));
				region.setRegionname(rs1.getString("REGIONNM"));
				list.add(region);
			}
			log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db1.closeRs();
			     db1.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}
	public List getStation2User(String usercd) {
		List list = new ArrayList();
        String Qry ="";
		try {
			
			db1.makeConnection();con1 = db1.getConnection();
			Qry = "select STATIONCD,STATIONNAME from stationmaster where STATIONCD=(select STATIONCD from userregistration where usercd='"+usercd+"') and STATUS='A' ";
			rs1 = db1.getRecordSet(Qry);
			while(rs1.next()){
				StationInfo station = new StationInfo();
				station.setStationcd(rs1.getString("STATIONCD"));
				station.setStationname(rs1.getString("STATIONNAME"));
				list.add(station);
			}
			log.info("list in dao " + list);
		} catch (Exception e) {
			log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>"+ e.getMessage());
		}finally {
			try {
				db1.closeRs();
			     db1.closeCon();
			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception >>>>>>>>>>>>"+ e.getMessage());
			}
		}
		return list;
	}
	public List getStations2Region(String regioncd) {
		StationInfo info = null;
		ArrayList list = new ArrayList();
		try {
			db1.makeConnection();
			con1 = db1.getConnection();
			String strSql="select  stationcd,Initcap(stationname)stationname from  stationmaster  where upper(trim(REGIONCD)) = UPPER('"+regioncd+"') and STATUS='A' "; 
			log.info(">>>>>>>>>>>>stations >>> "+strSql);
			rs1 = db1.getRecordSet(strSql);
			while(rs1.next()){
				info = new StationInfo();
				info.setStationcd(rs1.getString("STATIONCD"));
				info.setStationname(rs1.getString("STATIONNAME"));	
				list.add(info);
				
			}
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in Getting Stations "+e.getStackTrace());			
		}finally {
			try {
				db1.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return list;
	}
	public List getSubStations2Station(String stationcd) {
		StationInfo info = null;
		ArrayList list = new ArrayList();
		try {
			db1.makeConnection();
			con1 = db1.getConnection();
			String strSql="select  stationcd,Initcap(stationname)stationname from  stationmaster  where upper(trim(SAUSTATION)) = UPPER('"+stationcd+"') and STATUS='A' "; 
			log.info(">>>>>>>>>>>>stations >>> "+strSql);
			rs1 = db1.getRecordSet(strSql);
			while(rs1.next()){
				info = new StationInfo();
				info.setStationcd(rs1.getString("STATIONCD"));
				info.setStationname(rs1.getString("STATIONNAME"));	
				list.add(info);
				
			}
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in Getting SubStations "+e.getStackTrace());			
		}finally {
			try {
				db1.closeCon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return list;
	}
}
