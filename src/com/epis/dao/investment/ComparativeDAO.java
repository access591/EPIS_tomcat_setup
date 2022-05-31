package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epis.bean.investment.QuotationBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;


public class ComparativeDAO {
	Log log=new Log(ComparativeDAO.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private ComparativeDAO(){
			
		}
	   private static final ComparativeDAO comparativeDAO= new ComparativeDAO();
	   public static ComparativeDAO getInstance(){
		return comparativeDAO;   
	   }
	public List searchComparative(QuotationBean qbean) throws Exception {
		List list =new ArrayList();;
		try{
			QuotationBean comparativebean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select distinct(LETTER_NO)LETTER_NO,nvl(to_char(OPENDATE,'dd/Mon/yyyy'),'--') OPENDATE," +
					" decode(STATUS,'','All','S','Stmt generated','N','Stmt Not Generated')STATUS,SHORTLISTED  from invest_quotaion_data where QUOTATIONCD is not null and  LETTER_NO like ? ";
			boolean bool=false;
			if(!"".equals( qbean.getStatus())){
				query +=" and  STATUS = ?  ";
				bool = true;
			}
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, qbean.getLetterNo()+"%");
			if(bool)
				ps.setString(2, qbean.getStatus());
			rs=ps.executeQuery();
			while(rs.next()){
				comparativebean = new QuotationBean();
				comparativebean.setLetterNo(rs.getString("LETTER_NO"));
				comparativebean.setOpendate(rs.getString("OPENDATE"));
				comparativebean.setStatus(rs.getString("STATUS"));
				comparativebean.setShortlisted(rs.getString("SHORTLISTED"));
				list.add(comparativebean);
			}
			
		}catch(Exception e){
			throw new Exception(e.toString());
		}finally {
			try {
				DBUtility.closeConnection(rs,st,con);
			} catch (Exception e) {
				log.error("ComparativeDAO:searchComparative():Exception: "+ e.getMessage());
				throw new Exception(e);
			}
		}
		
		return list;
	}
	public HashMap saveComparativeStatus(QuotationBean bean) throws Exception {
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			String getcompstmtSql="";
			con.setAutoCommit(false);
			if(!bean.getMode().equals("report"))
			{
			String updateDate ="Update invest_quotaion_data set OPENDATE=?,STATUS=?,COMP_UPDATED_BY=?,COMP_UPDATED_DT=SYSDATE,COMP_REMARKS=? where LETTER_NO=? ";
			String SelectNIQ="select quotationcd  from invest_quotaion_data where letter_no=? and to_char(maturitydate,'yyyy') not  between (select fromperiod from invest_quotationrequest where letter_no=?) and (select toperiod from invest_quotationrequest where letter_no=?) ";
			PreparedStatement Niqps=con.prepareStatement(SelectNIQ);
			Niqps.setString(1,StringUtility.checknull(bean.getLetterNo()));
			Niqps.setString(2,StringUtility.checknull(bean.getLetterNo()));
			Niqps.setString(3,StringUtility.checknull(bean.getLetterNo()));
			rs=Niqps.executeQuery();
			while(rs.next())
			{
				String quotationcd=StringUtility.checknull(rs.getString("quotationcd"));
				
				String QuotationUpdate="update invest_quotaion_data set NIQFLAG='Y',remarks='NOT AS PER NIQ' where QUOTATIONCD='"+quotationcd+"'";
				st.executeUpdate(QuotationUpdate);
			}
			
			
			
			 PreparedStatement updateps =con.prepareStatement(updateDate);
			 updateps.setString(1,bean.getOpendate());
			 updateps.setString(2,bean.getStatus());
			 updateps.setString(3,StringUtility.checknull(bean.getLoginUserId()));	
			 updateps.setString(4,StringUtility.checknull(bean.getRemarks()));	
			 updateps.setString(5,bean.getLetterNo());
			 updateps.executeUpdate();
			 updateps.close();
			}
			 
				 getcompstmtSql ="select qu.Arrangercd Arrangercd,NIQFLAG,arrangername,Security_Name,Nvl(Purchaeprice,0)Purchaeprice,Nvl(YTMPERCENTAGE,0)YTMPERCENTAGE,qu.remarks remarks  from invest_quotaion_data qu, invest_arrangers arr where qu.Arrangercd = arr.arrangercd and qu.YTMVERIFIED='Y'  and qu.letter_no=? order by YTMPERCENTAGE desc ";
			 
			log.info("ComparativeDAO:saveComparativeStatus(): "+getcompstmtSql);
			int i=1;
			  PreparedStatement pst = con.prepareStatement( getcompstmtSql);
			  pst.setString(1,bean.getLetterNo());
			  rs=pst.executeQuery();
				while(rs.next()){
					bean = new QuotationBean();
					bean.setArrangerCd(rs.getString("Arrangercd"));
					bean.setArranger(rs.getString("arrangername"));
					bean.setSecurityName(rs.getString("Security_Name"));
					bean.setPurchasePrice(rs.getString("Purchaeprice"));
					if(rs.getString("NIQFLAG").equals("Y"))
						bean.setYtm(rs.getString("YTMPERCENTAGE"));
					else
					bean.setYtm(rs.getString("YTMPERCENTAGE")+"H"+i);
					
					bean.setRemarks(rs.getString("remarks"));
					if(map.containsKey(rs.getString("arrangername")))
					{
						map1=(HashMap)map.get(rs.getString("arrangername"));
					}else{
						map1=new HashMap();
					}
					map1.put(rs.getString("Security_Name"),bean);
					map.put(rs.getString("arrangername"),map1);
					i++;
					if(rs.getString("NIQFLAG").equals("Y"))
					i--;
				}
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("ComparativeDAO:saveComparativeStatus():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
		return map;
	}
	public List getComparativeLetterNos() throws ServiceNotAvailableException, EPISException {
		List list =new ArrayList();
		PreparedStatement pstmt=null;
		try{
			QuotationBean qbean =null;
			con = DBUtility.getConnection();
			String query = "Select distinct(LETTER_NO) from invest_quotaion_data WHERE LETTER_NO IS NOT NULL and status='N' and Nvl(CMPNOTNEEDED,'N')='N' and  (YTMVERIFIED='Y' or YTMNOTNEEDED='Y')";
			query +=" order by LETTER_NO";
			if(con!=null){
				pstmt = con.prepareStatement(query);	
				rs = pstmt.executeQuery();
			while(rs.next()){
				qbean = new QuotationBean();
				qbean.setLetterNo(rs.getString("LETTER_NO"));
				list.add(qbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
			}catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("YTMVerificationDao:getLetterNo:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("YTMVerificationDao:getLetterNo:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
		return list;
	}
	public void updateCompFlag(QuotationBean bean) throws EPISException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DBUtility.getConnection();
			String update = "update invest_quotaion_data set STATUS='N',COMP_REMARKS='"+bean.getRemarks()+"',OPENDATE='"+bean.getOpendate()+"',COMP_UPDATED_BY='"
				+ bean.getLoginUserId()+"',COMP_UPDATED_DT=sysdate,CMPNOTNEEDED='Y' where LETTER_NO='"+bean.getLetterNo()+"'";
			if (connection != null) {
				stmt = connection.createStatement();
				if (stmt != null) {
					System.out.println(update);
					stmt.executeUpdate(update);
				} else {
					throw new ServiceNotAvailableException();
				}
			} else {
				throw new ServiceNotAvailableException();
			}

		} catch (ServiceNotAvailableException snex) {
			throw new EPISException(snex);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:SQLException"
					+ sqlex.getMessage());
			throw new EPISException(sqlex);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("YTMVerificationDao:editQuotation:Exception"
					+ e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, stmt, connection);
		}
	}
	public QuotationBean getMarketType(String letterNo)throws ServiceNotAvailableException, EPISException
	{
		QuotationBean bean=new QuotationBean();
		PreparedStatement pst=null;
		try{
			con = DBUtility.getConnection();
			if(con!=null)
			{
			String selectQry="select CATEGORYCD,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','O','Open Bid') MARKET_TYPE from invest_quotationrequest quotation,invest_sec_category category where category.categoryid=quotation.categoryid and quotation.letter_no=? ";
			 pst = con.prepareStatement(selectQry);
			  pst.setString(1,letterNo);
			  rs=pst.executeQuery();
			  if(rs.next())
			  {
				 bean.setSecurityCd(StringUtility.checknull(rs.getString("CATEGORYCD")));
				 bean.setMarketType(StringUtility.checknull(rs.getString("MARKET_TYPE")));
			  }
			}
			else {
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("YTMVerificationDao:getLetterNo:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("YTMVerificationDao:getLetterNo:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pst,con);			
		}
		return bean;
		
	}
}
