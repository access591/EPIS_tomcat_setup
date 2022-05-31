package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.epis.bean.admin.RegionBean;
import com.epis.bean.investment.SecurityCategoryBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class SecurityCategoryDAO {
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	
	Log log  = new Log(SecurityCategoryDAO.class);
	private SecurityCategoryDAO(){
		
	}
	private static final SecurityCategoryDAO categoryDAO = new SecurityCategoryDAO();
	public static SecurityCategoryDAO getInstance(){
		return categoryDAO;
	}
	
	public List searchCategory(String categorycd) throws Exception{
		List list =new ArrayList();;
		try{
			SecurityCategoryBean security =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select CATEGORYID,CATEGORYCD,nvl(DESCRIPTION,'--')DESCRIPTION from invest_sec_category where categoryid is not null";
			if(!categorycd.equals(" ")){
				query +=" and upper(categorycd) like upper('"+categorycd+"%')";
			}
			query +=" order by categoryid";
			log.info("SecurityCategoryDAO:searchCategory(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				security = new SecurityCategoryBean();
				security.setCategoryId(rs.getString("categoryid"));
				security.setCategoryCd(rs.getString("categorycd"));
				security.setDescription(rs.getString("DESCRIPTION"));
				list.add(security);
			}
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("SecurityCategoryDAO:searchCategory():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		return list;
	}

	public void saveCategory(SecurityCategoryBean scbean) throws Exception {
		String categoryid = "";
		int recordCnt =0;
		try{

			con = DBUtility.getConnection();
			recordCnt=DBUtility.getRecordCount("SELECT COUNT(*) FROM  invest_sec_category WHERE Upper(trim(categorycd))=Upper('"+scbean.getCategoryCd()+"')");
			if(recordCnt!=0)
			{   
			  throw new Exception("Record Already Exists with SecurityCategory Code : " + scbean.getCategoryCd());
			}
			categoryid =AutoGeneration.getNextCode("invest_sec_category","categoryid",2,con);
			
			String saveCategorySql ="insert into invest_sec_category(categoryid, categorycd, OPENINGBAL,INVESTINTEREST,INVESTBASEAMT,DESCRIPTION,CREATED_BY,CREATED_DT) values ('"+categoryid+"','"+scbean.getCategoryCd()+"','"+scbean.getOpeningbal()+"','"+scbean.getInvestinterest()+"','"+scbean.getInvestbaseamt()+"','"+scbean.getDescription()+"','"+scbean.getLoginUserId()+"',SYSDATE)";
			log.info("SecurityCategoryDAO:saveCategory(): "+saveCategorySql);	
			DBUtility.executeUpdate(saveCategorySql);		
		}catch(Exception e){
			log.error("SecurityCategoryDAO:saveCategory():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
		
	}

	public void editCategory(SecurityCategoryBean scbean) throws Exception {
		String updateCategorySql ="update invest_sec_category set  categorycd='"+scbean.getCategoryCd()+"',OPENINGBAL='"+scbean.getOpeningbal()+"',INVESTBASEAMT='"+scbean.getInvestbaseamt()+"',INVESTINTEREST='"+scbean.getInvestinterest()+"',DESCRIPTION='"+scbean.getDescription()+"',UPDATED_BY='"+scbean.getLoginUserId()+"',UPDATED_DT=SYSDATE where categoryid='"+scbean.getCategoryId()+"'";
		log.info("SecurityCategoryDAO:editCategory(): "+updateCategorySql);	
		try{

		DBUtility.executeUpdate(updateCategorySql);		
		}catch(Exception e){
			log.error("SecurityCategoryDAO:editCategory():Exception: "+ e.getMessage());
			throw new Exception(e);
		}
	}

	public SecurityCategoryBean findSecurityCategory(String categoryid) throws Exception {
		SecurityCategoryBean category =null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select * from invest_sec_category where categoryid is not null";
			if(!categoryid.equals(" ")){
				query +=" and categoryid = '"+categoryid+"'";
			}
			query +=" order by categoryid";
			log.info("SecurityCategoryDAO:findSecurityCategory(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			if(rs.next()){
				category =new SecurityCategoryBean();
				category.setCategoryId(rs.getString("categoryid"));
				category.setCategoryCd(rs.getString("categorycd"));
				category.setDescription(rs.getString("DESCRIPTION"));
				category.setOpeningbal(rs.getString("OPENINGBAL"));
				category.setInvestbaseamt(rs.getString("INVESTBASEAMT"));
				category.setInvestinterest(rs.getString("INVESTINTEREST"));
			}
			
		}catch(Exception e){
			log.error("SecurityCategoryDAO:findSecurityCategory():Exception: "+ e.getMessage());
			throw new Exception(e);
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("SecurityCategoryDAO:findSecurityCategory():Exception: "+ e.getMessage());
				throw new Exception(e);
				
			}
		}
		return category;
	}

	public void deleteCategory(String categoryids) throws ServiceNotAvailableException, EPISException {
		
		String deleteCategorySql ="delete  from invest_sec_category where categoryid in ("+categoryids+")";
		log.info("SecurityCategoryDAO:deleteCategory(): "+deleteCategorySql);	
		try{
			con = DBUtility.getConnection();
			
			if(con!=null){
				st=con.createStatement();
				if(st!=null){
					DBUtility.setAutoCommit(false,con);
					st.executeUpdate(deleteCategorySql);
					
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
			log.error("QuotationRequestDAO:deleteUnit:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("QuotationRequestDAO:deleteUnit:Exception"+e.getMessage());
			throw new EPISException(e);
		}
		finally {			
			DBUtility.closeConnection(null,st,con);	
		}
	}
	public List getSecurityCategories() throws Exception{		
		List securityList = new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		SecurityCategoryBean security=null;
		
		try{	
			String searchQry="select * from invest_sec_category where CATEGORYID is not null ";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){									
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						 security = new SecurityCategoryBean();
						 security.setCategoryId(StringUtility.checknull(reusltSet.getString("CATEGORYID")));
						 security.setCategoryCd(StringUtility.checknull(reusltSet.getString("CATEGORYCD")));
						 security.setDescription(StringUtility.checknull(reusltSet.getString("DESCRIPTION")));
						 securityList.add(security);
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
			log.error("SecurityCategoryDAO:getSecurityCategories:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SecurityCategoryDAO:getSecurityCategories:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return securityList;
	}

	public List getAccountHeads() throws ServiceNotAvailableException, EPISException {
		List accHeadList = new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		SecurityCategoryBean security=null;
		
		try{	
			String searchQry="select accounthead from accountcode_details";
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){									
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						 security = new SecurityCategoryBean();
						 security.setAccHeads(StringUtility.checknull(reusltSet.getString("accounthead")));
						 accHeadList.add(security);
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
			log.error("SecurityCategoryDAO:getAccountHeads():SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SecurityCategoryDAO:getAccountHeads():Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return accHeadList;
	}
	
	public List getSecurityCategories(String mode) throws Exception{		
		List securityList = new ArrayList();
		Connection connection=null;
		ResultSet reusltSet=null;
		PreparedStatement pstmt=null;
		SecurityCategoryBean security=null;
		String searchQry="";
		
		try{
			if(mode.equals("psuprimary"))
			{
				searchQry="select * from invest_sec_category where CATEGORYID is not null and CATEGORYCD='PSU' ";
			}
			else if(mode.equals("rbiauction"))
			{
				searchQry="select * from invest_sec_category where categorycd not in('PSU','Any')";
			}
			else if(mode.equals("rbiauctionManual"))
			{
				searchQry="select * from invest_sec_category where categorycd is not null ";
			}
			else
			{
			searchQry="select * from invest_sec_category where CATEGORYID is not null ";
			}
			connection = DBUtility.getConnection();
			if(connection!=null){
				pstmt = connection.prepareStatement(searchQry);	
				if(pstmt!=null){									
					reusltSet = pstmt.executeQuery();
					while(reusltSet.next()){
						 security = new SecurityCategoryBean();
						 security.setCategoryId(StringUtility.checknull(reusltSet.getString("CATEGORYID")));
						 security.setCategoryCd(StringUtility.checknull(reusltSet.getString("CATEGORYCD")));
						 security.setDescription(StringUtility.checknull(reusltSet.getString("DESCRIPTION")));
						 securityList.add(security);
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
			log.error("SecurityCategoryDAO:getSecurityCategories:SQLException"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			e.printStackTrace();
			log.error("SecurityCategoryDAO:getSecurityCategories:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(reusltSet,pstmt,connection);			
		}
		return securityList;
	}

}
