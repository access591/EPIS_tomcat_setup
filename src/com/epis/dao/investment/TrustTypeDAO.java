package com.epis.dao.investment;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.jsp.JspException;
import com.epis.bean.investment.TrustTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;

public class TrustTypeDAO {
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	
	Log log  = new Log(TrustTypeDAO.class);
	private TrustTypeDAO(){
		
	}
	private static final TrustTypeDAO trustTypeDAO = new TrustTypeDAO();
	public static TrustTypeDAO getInstance(){
		return trustTypeDAO;
	}
	
	public List searchTrust(String trusttype) throws Exception{
		List list =new ArrayList();;
		try{
			TrustTypeBean trust =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select TRUSTCD,TRUSTTYPE,nvl(DESCRIPTION,'--')DESCRIPTION from invest_trusttype where trustcd is not null";
			if(!trusttype.equals(" ")){
				query +=" and upper(trusttype) like upper('"+trusttype+"%')";
			}
			query +=" order by trustcd";
			log.info("TrustTypeDAO:searchTrustType(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				trust = new TrustTypeBean();
				trust.setTrustCd(rs.getString("TRUSTCD"));
				trust.setTrustType(rs.getString("TRUSTTYPE"));
				trust.setDescription(rs.getString("DESCRIPTION"));
				list.add(trust);
			}
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("TrustTypeDAO:searchTrustType():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		
		return list;
		
	}

	public void saveTrustType(TrustTypeBean tbean) throws Exception {
		String trustcd = "";
		int recordCnt =0;
		try{
			con = DBUtility.getConnection();
			recordCnt=DBUtility.getRecordCount("SELECT COUNT(*) FROM  invest_trusttype WHERE Upper(trim(TRUSTTYPE))=Upper('"+tbean.getTrustType()+"')");
			if(recordCnt!=0)
			{   
			  throw new Exception("Record Already Exists with TrustType : " + tbean.getTrustType());
			}
			trustcd =AutoGeneration.getNextCode("invest_trusttype","TRUSTCD",2,con);
			
			String saveTrustSql ="insert into invest_trusttype(TRUSTCD, TRUSTTYPE, DESCRIPTION,CREATED_BY,CREATED_DT) values ('"+trustcd+"','"+tbean.getTrustType()+"','"+tbean.getDescription()+"','"+tbean.getLoginUserId()+"',SYSDATE)";
			log.info("TrustTypeDAO:saveTrustType(): "+saveTrustSql);	
			DBUtility.executeUpdate(saveTrustSql);		
		}catch(Exception e){
			log.error("TrustTypeDAO:saveTrustType():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
		
	}

	public void editTrustType(TrustTypeBean tbean) throws Exception {
		String updateTrustSql ="update invest_trusttype set  TRUSTTYPE='"+tbean.getTrustType()+"',DESCRIPTION='"+tbean.getDescription()+"',UPDATED_BY='"+tbean.getLoginUserId()+"',UPDATED_DT=SYSDATE where TRUSTCD='"+tbean.getTrustCd()+"'";
		log.info("TrustTypeDAO:editTrustType(): "+updateTrustSql);	
		try{

		DBUtility.executeUpdate(updateTrustSql);		
		DBUtility.commitTrans(con);
		
		}catch(Exception e){
			log.error("TrustTypeDAO:editTrustType():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}

	public TrustTypeBean findTrustType(String trustcd) throws Exception {
		TrustTypeBean trust =null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select * from invest_trusttype where trustcd is not null";
			if(!trustcd.equals(" ")){
				query +=" and TRUSTCD = '"+trustcd+"'";
			}
			query +=" order by trustcd";
			log.info("TrustTypeDAO:findTrustType(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			if(rs.next()){
				trust=new TrustTypeBean();
				trust.setTrustCd(rs.getString("TRUSTCD"));
				trust.setTrustType(rs.getString("TRUSTTYPE"));
				trust.setDescription(rs.getString("DESCRIPTION"));
			}
		}catch(Exception e){
			log.error("TrustTypeDAO:editTrustType():Exception: "+ e.getMessage());
			throw new Exception(e);
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("TrustTypeDAO:editTrustType():Exception: "+ e.getMessage());
				throw new Exception(e);
				
			}
		}
		return trust;
	}

	public void deleteTrustType(String trustcds) throws ServiceNotAvailableException, EPISException {
		String deleteTrustSql ="delete  from invest_trusttype where TRUSTCD in ("+trustcds+")";
		log.info("TrustTypeDAO:deleteTrustType(): "+deleteTrustSql);		
		try{
			con = DBUtility.getConnection();
			
			if(con!=null){
				st=con.createStatement();
				if(st!=null){
					DBUtility.setAutoCommit(false,con);
					st.executeUpdate(deleteTrustSql);
					
				}else{
					throw new ServiceNotAvailableException();
				}
			}else{
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
			
		}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){
			try{
				DBUtility.rollbackTrans(con);
				
			}
			catch(Exception e)
			{
				log.error(e.getMessage());
			}
			sqlex.printStackTrace();
			log.error("TrustTypeDAO:deleteUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("TrustTypeDAO:deleteUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,st,con);	
		}
	}
	public List getTrustTypes() throws Exception{
		List list =new ArrayList();;
		try{
			TrustTypeBean trust =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			/* changed by of CASH and Investment Only one Trust "AAI EPF" should be there and should be default*/
			String query = "select * from invest_trusttype where trustcd is not null and TRUSTCD='03'";
			query +=" order by trustcd";
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				trust = new TrustTypeBean();
				trust.setTrustCd(rs.getString("TRUSTCD"));
				trust.setTrustType(rs.getString("TRUSTTYPE"));
				trust.setDescription(rs.getString("DESCRIPTION"));
				list.add(trust);
			}
		}catch(Exception e){
			e.getMessage();
			throw new JspException(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				throw new JspException(e.toString());
				
			}
		}
		
		return list;
		
	}
	
	public List getTrustTypes(String mode) throws Exception{
		List list =new ArrayList();;
		try{
			String query="";
			TrustTypeBean trust =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			if(mode.equals("rbiauctionManual"))
			{
			 query = "select * from invest_trusttype where trustcd is not null and TRUSTCD='03'";
			}
			query +=" order by trustcd";
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				trust = new TrustTypeBean();
				trust.setTrustCd(rs.getString("TRUSTCD"));
				trust.setTrustType(rs.getString("TRUSTTYPE"));
				trust.setDescription(rs.getString("DESCRIPTION"));
				list.add(trust);
			}
		}catch(Exception e){
			e.getMessage();
			throw new JspException(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				throw new JspException(e.toString());
				
			}
		}
		
		return list;
		
	}
}
