package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;
import com.epis.bean.investment.InvestmentMadeBean;
import com.epis.bean.investment.LetterToBankBean;

public class InvestmentMadeDao {
	Connection con=null;
	ResultSet rs=null;
	Statement st=null;
	PreparedStatement pstmt=null;
	PreparedStatement prepare=null;
	Log log=new Log(InvestmentMadeDao.class);
	int recordCnt=0;
	private InvestmentMadeDao()
	{
		
	}
	private static final InvestmentMadeDao investmentmadedao=new InvestmentMadeDao();
	public static InvestmentMadeDao getInstance()
	{
		return investmentmadedao;
	}
	
	public List searchinvestMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		List list =new ArrayList();;
		try{
			InvestmentMadeBean investbean =null;
			con = DBUtility.getConnection();
			if(con !=null)
			{
			st = con.createStatement();
			String query = "select INVESTMENT_MADECD,made.TRUSTCD,made.CATEGORYID,INV_AMOUNT,TRUSTTYPE,CATEGORYCD from investment_made made,invest_trusttype type,invest_sec_category category where type.TRUSTCD=made.TRUSTCD and category.CATEGORYID=made.CATEGORYID and INVESTMENT_MADECD is not null  ";
			
			
					
			
			if(!bean.getTrustType().equals("")){
				query +=" and made.TRUSTCD='"+bean.getTrustType()+"'";
			}
			if(!bean.getSecurityCategory().equals("")){
				query +=" and made.CATEGORYID='"+bean.getSecurityCategory()+"'";
			}
			
			query +=" order by made.CREATED_DT DESC";
			log.info("InvestmentMadeDao:searchinvestMade(): "+query);	
			rs = DBUtility.getRecordSet(query,st);
			while(rs.next()){
				investbean=new InvestmentMadeBean();
				investbean.setInvestmentMadeCd(StringUtility.checknull(rs.getString("INVESTMENT_MADECD")));
				investbean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
				investbean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
				investbean.setInvAmount(StringUtility.checknull(rs.getString("INV_AMOUNT")));
							
				list.add(investbean);
			}
			}else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentMadeDao:searchinvestMade:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentMadeDao:searchinvestMade:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		log.info("the size"+list.size());
		return list;
	}
	public void addinvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		String investmadeCd=null;
		try{
			con = DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				String query="SELECT COUNT(*) FROM investment_made WHERE TRUSTCD='"+StringUtility.checknull(bean.getTrustType())+"' AND CATEGORYID='"+StringUtility.checknull(bean.getSecurityCategory())+"'";
				recordCnt=DBUtility.getRecordCount(query);
				if(recordCnt!=0)
					throw new Exception("Record Already exist with TrustCd and SecurityCategory");
				else
				{
					investmadeCd =AutoGeneration.getNextCode("INVESTMENT_MADE","INVESTMENT_MADECD",5,con);
					 
					String insertQuery="INSERT INTO INVESTMENT_MADE(INVESTMENT_MADECD,TRUSTCD,CATEGORYID,INV_AMOUNT,AS_ONDATE,CREATED_BY,CREATED_DT) VALUES(?,?,?,?,?,?,sysdate)";
					pstmt=con.prepareStatement(insertQuery);
					if(pstmt!=null)
					{
						pstmt.setString(1,StringUtility.checknull(investmadeCd));
						pstmt.setString(2,StringUtility.checknull(bean.getTrustType()));
						pstmt.setString(3,StringUtility.checknull(bean.getSecurityCategory()));
						pstmt.setString(4,StringUtility.checknull(bean.getInvAmount()));
						pstmt.setString(5,StringUtility.checknull(bean.getAsOnDate()));
						pstmt.setString(6,StringUtility.checknull(bean.getLoginUserId()));
						pstmt.executeUpdate();
						
						
					}
					else{
						throw new ServiceNotAvailableException();
					}
					
				}
					
					
					
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentMadeDao:addinvestmentMade:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentMadeDao:addinvestmentMade:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	public InvestmentMadeBean findInvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		InvestmentMadeBean investbean=null;
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				String query="SELECT INVESTMENT_MADECD,made.TRUSTCD,TRUSTTYPE,made.CATEGORYID,CATEGORYCD,INV_AMOUNT,to_char(AS_ONDATE,'dd/Mon/yyyy')AS_ONDATE from investment_made made,invest_trusttype trust,invest_sec_category category  where made.TRUSTCD=trust.TRUSTCD and made.CATEGORYID=category.CATEGORYID and INVESTMENT_MADECD=?";
				pstmt=con.prepareStatement(query);
				if(pstmt!=null){
					pstmt.setString(1,StringUtility.checknull(bean.getInvestmentMadeCd()));
					rs=pstmt.executeQuery();
					if(rs.next())
					{
						investbean=new InvestmentMadeBean();
						investbean.setTrustType(StringUtility.checknull(rs.getString("TRUSTTYPE")));
						investbean.setSecurityCategory(StringUtility.checknull(rs.getString("CATEGORYCD")));
						investbean.setInvAmount(StringUtility.checknull(rs.getString("INV_AMOUNT")));
						investbean.setAsOnDate(StringUtility.checknull(rs.getString("AS_ONDATE")));
						investbean.setInvestmentMadeCd(StringUtility.checknull(rs.getString("INVESTMENT_MADECD")));
					}
				}
				else{
					throw new ServiceNotAvailableException();
				}
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentMadeDao:addinvestmentMade:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentMadeDao:addinvestmentMade:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return investbean;
	}
	public void updateinvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		try{
			con=DBUtility.getConnection();
			if(con!=null){
				String updatequery="UPDATE investment_made SET INV_AMOUNT=?,AS_ONDATE=?,UPDATED_BY=?,UPDATED_DT=sysdate WHERE INVESTMENT_MADECD=?";
				pstmt=con.prepareStatement(updatequery);
				if(pstmt!=null)
				{
					pstmt.setString(1,StringUtility.checknull(bean.getInvAmount()));
					pstmt.setString(2,StringUtility.checknull(bean.getAsOnDate()));
					pstmt.setString(3,StringUtility.checknull(bean.getLoginUserId()));
					pstmt.setString(4,StringUtility.checknull(bean.getInvestmentMadeCd()));
					pstmt.executeUpdate();
					
				}
				else{
					throw new ServiceNotAvailableException();
				}
			}
			
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentMadeDao:addinvestmentMade:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentMadeDao:addinvestmentMade:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
	}
	public void deleteInvestmentMade(InvestmentMadeBean bean)throws EPISException,ServiceNotAvailableException
	{
		try{
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				String deleteQuery="delete from investment_made where INVESTMENT_MADECD='"+StringUtility.checknull(bean.getInvestmentMadeCd())+"'";
				DBUtility.executeUpdate(deleteQuery);
			}
			else{
				throw new ServiceNotAvailableException();
			}
		}
		catch(ServiceNotAvailableException snex){
			throw snex;
		}catch(SQLException sqlex){			
			log.error("InvestmentMadeDao:addinvestmentMade:SQLException:"+sqlex.getMessage());
			throw  new EPISException(sqlex);
		}catch(Exception e){
			
			log.error("InvestmentMadeDao:addinvestmentMade:Exception:"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,st,con);			
		}
		
	}

}
