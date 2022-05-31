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
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.utilities.UtilityBean;

public class ShortListDAO {
	Log log=new Log(ShortListDAO.class);
	Connection con = null;
	ResultSet rs = null;
	Statement st = null;
	private ShortListDAO(){
			
		}
	   private static final ShortListDAO shortDAO= new ShortListDAO();
	   public static ShortListDAO getInstance(){
		return shortDAO;   
	   }
	public List searchShortList(QuotationBean qbean) throws Exception {
		List list =new ArrayList();;
		try{
			QuotationBean listbean =null;
			con = DBUtility.getConnection();
			st = con.createStatement();
			String query = "select distinct(LETTER_NO)LETTER_NO,to_char(OPENDATE,'dd/Mon/yyyy')OPENDATE,decode(SHORTLISTEDSTATUS,'','All','Y','Stmt Generated','N','Stmt Not Generated')STATUS,nvl(SHORTLISTEDSTATUS,' ')SHORTLISTEDSTATUS  from invest_quotaion_data where QUOTATIONCD is not null and  LETTER_NO like ? ";
			boolean bool=false;
			if(!"".equals( qbean.getStatus())){
				query +=" and  SHORTLISTEDSTATUS = ? ";
				bool = true;
			}
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%"+qbean.getLetterNo()+"%");
			if(bool)
				ps.setString(2, qbean.getStatus());
			rs=ps.executeQuery();
			while(rs.next()){
				listbean = new QuotationBean();
				listbean.setLetterNo(rs.getString("LETTER_NO"));
				listbean.setOpendate(rs.getString("OPENDATE"));
				listbean.setStatus(rs.getString("STATUS"));
				listbean.setShortlisted(rs.getString("SHORTLISTEDSTATUS"));
				list.add(listbean);
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
	public HashMap showShortList(String letterno,String remarks) throws Exception {
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		QuotationBean bean=null;
		try{
			
			con = DBUtility.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);
			String updateDate ="Update invest_quotaion_data set SLA_REMARKS=? where LETTER_NO=? ";
			 PreparedStatement updateps =con.prepareStatement(updateDate);
			 updateps.setString(1,remarks);
			 updateps.setString(2,letterno);
			 updateps.executeUpdate();
			
			String getcompstmtSql ="select qu.Arrangercd Arrangercd,arrangername,Security_Name,Nvl(Purchaeprice,0)Purchaeprice,Nvl(YTMPERCENTAGE,0)YTMPERCENTAGE,qu.remarks remarks  from invest_quotaion_data qu, invest_arrangers arr where qu.Arrangercd = arr.arrangercd and qu.letter_no=? and NIQFLAG='N' order by YTMPERCENTAGE desc";
			log.info("ComparativeDAO:saveComparativeStatus(): "+getcompstmtSql);
			int i=1;
			  PreparedStatement pst = con.prepareStatement( getcompstmtSql);
			  pst.setString(1,letterno);
			  rs=pst.executeQuery();
				while(rs.next()){
					bean = new QuotationBean();
					bean.setArrangerCd(rs.getString("Arrangercd"));
					bean.setArranger(rs.getString("arrangername"));
					bean.setSecurityName(rs.getString("Security_Name"));
					bean.setPurchasePrice(rs.getString("Purchaeprice"));
					if(i<=3)
					bean.setYtm(rs.getString("YTMPERCENTAGE")+"H"+i);
					else
						bean.setYtm(rs.getString("YTMPERCENTAGE"));
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
	public HashMap showShortList(String letterno)throws Exception{
		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		QuotationBean bean=null;
		try{
			con = DBUtility.getConnection();
			
			String getcompstmtSql ="select qu.Arrangercd Arrangercd,arrangername,Security_Name,Nvl(Purchaeprice,0)Purchaeprice,Nvl(YTMPERCENTAGE,0)YTMPERCENTAGE,nvl(decode(SHORTLISTED,'Y','Short Listed'),'Not Short Listed')SHORTLISTED,SHORTLISTEDSTATUS,qu.remarks remarks  from invest_quotaion_data qu, invest_arrangers arr where qu.Arrangercd = arr.arrangercd and qu.letter_no=? order by YTMPERCENTAGE desc";
				
			  PreparedStatement pst = con.prepareStatement( getcompstmtSql);
			  
			  pst.setString(1,letterno);
			  int i=1;
			  rs=pst.executeQuery();
				while(rs.next()){
					bean = new QuotationBean();
					bean.setArrangerCd(rs.getString("Arrangercd"));
					bean.setArranger(rs.getString("arrangername"));
					bean.setSecurityName(rs.getString("Security_Name"));
					bean.setPurchasePrice(rs.getString("Purchaeprice"));
					if(i<=3)
					bean.setYtm(rs.getString("YTMPERCENTAGE")+"H"+i);
					else
						bean.setYtm(rs.getString("YTMPERCENTAGE"));
					bean.setRemarks(rs.getString("remarks"));
					bean.setShortlisted(rs.getString("SHORTLISTED"));
					log.info("the arranger name is..."+rs.getString("arrangername"));
					if(map.containsKey(rs.getString("arrangername")))
					{
						map1=(HashMap)map.get(rs.getString("arrangername"));
					}else{
						map1=new HashMap();
					}
					map1.put(rs.getString("Security_Name"),bean);
					map.put(rs.getString("arrangername"),map1);
					i++;
				}
				
				con.commit();
				con.close();
		}
		catch(Exception e){
			con.rollback();
			log.error("ComparativeDAO:showShortList():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
		return map;
	}
	public void saveShortList(QuotationBean bean) throws Exception {
		UtilityBean util = null;
		try{
			con = DBUtility.getConnection();
			st = con.createStatement();
			con.setAutoCommit(false);	
			  
			  List list = bean.getShortListArrangres();
				for(int i=0;i<list.size();i++){
					String saveShortListSql ="update invest_quotaion_data qu set SHORTLISTED = 'Y',SLA_UPDATED_BY=?,SLA_UPDATED_DT=SYSDATE where  qu.letter_no=? and upper(qu.SECURITY_NAME) = upper(?) and qu.arrangercd =(select arrangercd from invest_arrangers arr  where upper(arr.arrangername) = upper(?)) ";
					log.info("ShortListDAO:saveShortList(): "+saveShortListSql);
					  PreparedStatement ps =con.prepareStatement(saveShortListSql);
					  ps.setString(1,StringUtility.checknull(bean.getLoginUserId()));
					  ps.setString(2,bean.getLetterNo());
					  util = (UtilityBean)list.get(i);
					  ps.setString(3,util.getValue());
					  ps.setString(4,util.getKey());
					  ps.executeUpdate();
					  ps.close();
					  String saveShortListStatusSql ="update invest_quotaion_data qu set SHORTLISTEDSTATUS = 'Y' where  qu.letter_no=?  ";
						PreparedStatement psstatus =con.prepareStatement(saveShortListStatusSql);
						psstatus.setString(1,bean.getLetterNo());
						psstatus.executeUpdate();
						 psstatus.close();
				}
				
				con.commit();
				con.close();
		}catch(Exception e){
			con.rollback();
			log.error("ShortListDAO:saveShortList():Exception: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	public List getShortListLetterNos() throws ServiceNotAvailableException, EPISException {
		List list =new ArrayList();
		PreparedStatement pstmt=null;
		try{
			QuotationBean qbean =null;
			con = DBUtility.getConnection();
			String query = "Select distinct(LETTER_NO) from invest_quotaion_data WHERE LETTER_NO IS NOT NULL and (Nvl(CMPNOTNEEDED,'N')='Y' or status='S') and SHORTLISTEDSTATUS='N' and Nvl(SLANOTNEEDED,'N')='N' ";
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
	public void updateSLFlag(QuotationBean bean) throws EPISException {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = DBUtility.getConnection();
			String update = "update invest_quotaion_data set SHORTLISTEDSTATUS='N',SLA_REMARKS='"+bean.getRemarks()+"',SLA_UPDATED_BY='"
				+ bean.getLoginUserId()+"',SLA_UPDATED_DT=sysdate,SLANOTNEEDED='Y' where LETTER_NO='"+bean.getLetterNo()+"'";
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

}
