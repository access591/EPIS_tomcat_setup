package com.epis.dao.investment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.epis.bean.investment.AmountTypeBean;
import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;




public class AmountTypeDao {


	Connection con=null;
	ResultSet rs=null;
	Statement st=null;
	PreparedStatement pstmt=null;
	PreparedStatement prepare=null;

	Log log=new Log(AmountTypeDao.class);
	private AmountTypeDao()
	{
	}
	private static final AmountTypeDao amounttypedao=new AmountTypeDao();


	public static AmountTypeDao getInstance()
	{
		return amounttypedao;
	}
	public List PartyDetails()throws ServiceNotAvailableException,EPISException
	{
		List list=new ArrayList();
		AmountTypeBean bean=null;
		try{
			String amountquery="select emp_party_code, credit, ACCOUNTHEAD, voucherno,to_char(VOUCHER_DT,'dd/Mon/yyyy')VOUCHER_DT, det.rowid from cb_voucher_info info, cb_voucher_details det where info.keyno = det.keyno and partytype = 'P' and vouchertype = 'R' and voucherno is not null and AMOUNTTYPE_CD is null  order by emp_party_code";
			con=DBUtility.getConnection();
			if(con!=null)
			{
			st=con.createStatement();
			rs=st.executeQuery(amountquery);
			while(rs.next())
			{
				bean=new AmountTypeBean();
				bean.setEmppartycode(StringUtility.checknull(rs.getString("emp_party_code")));
				bean.setCredit(StringUtility.checknull(rs.getString("credit")));
				bean.setAccounthead(StringUtility.checknull(rs.getString("ACCOUNTHEAD")));
				bean.setVoucherno(StringUtility.checknull(rs.getString("voucherno")));
				bean.setRowid(StringUtility.checknull(rs.getString("rowid")));
				bean.setAmountdate(StringUtility.checknull(rs.getString("VOUCHER_DT")));
				
				
				list.add(bean);
				
			}
			}
			else {
				throw new ServiceNotAvailableException();
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
			log.error("AmountTypeDAO:PartyDetails:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return list;
	}
	public String updatePartyDetails(AmountTypeBean bean)throws ServiceNotAvailableException,EPISException
	{
		String message="";
		String amounttypecd="";
		try{
			con=DBUtility.getConnection();
			amounttypecd=AutoGeneration.getNextCodeGBy("invest_amounttype_details","AMOUNTTYPE_CD",12,"invest",con);
			String insertQuery="INSERT INTO INVEST_AMOUNTTYPE_DETAILS (AMOUNTTYPE_CD,AMOUNT,AMOUNT_TYPE,AMOUNT_DATE,SECURITY_NAME,ENTEREDBY,ENTEREDDT)values(?,?,?,?,?,?,sysdate)";
			DBUtility.setAutoCommit(false,con);
			message="Update Successfully";
			if(con!=null)
			{
				pstmt=con.prepareStatement(insertQuery);
				if(pstmt!=null){
					pstmt.setString(1,amounttypecd);
					pstmt.setString(2,bean.getCredit());
					pstmt.setString(3,bean.getAmounttype());
					pstmt.setString(4,bean.getAmountdate());
					pstmt.setString(5,bean.getEmppartycode());
					pstmt.setString(6,bean.getLoginUserId());
					pstmt.executeUpdate();
					
					
				}
				else{
					throw new ServiceNotAvailableException();
				}
				String updateQuery="update cb_voucher_details set AMOUNTTYPE_CD=? where rowid=?";
				prepare=con.prepareStatement(updateQuery);
				if(prepare!=null)
				{
					prepare.setString(1,bean.getAmounttype());
					prepare.setString(2,bean.getRowid());
					prepare.executeUpdate();
				}
				else{
					throw new ServiceNotAvailableException();
				}	
				
				
			}
			else {
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
		}
		catch(Exception e){
			message=e.getMessage();
			try {
				DBUtility.rollbackTrans(con);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.error("AmountTypeDAO:PartyDetails:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return message;
	}
	public List getSecurityList()throws ServiceNotAvailableException,EPISException
	{
		List list=new ArrayList();
		AmountTypeBean bean=null;
		try{
			String query="SELECT SECURITY_NAME FROM INVEST_REGISTER WHERE REGISTERCD IS NOT NULL";
			con=DBUtility.getConnection();
			if(con!=null)
			{
				st=con.createStatement();
				rs=st.executeQuery(query);
				while(rs.next())
				{
					bean=new AmountTypeBean();
					bean.setEmppartycode(StringUtility.checknull(rs.getString("SECURITY_NAME")));
					list.add(bean);
				}
				
			}
			else {
				throw new ServiceNotAvailableException();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("AmountTypeDAO:PartyDetails:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		return list;
		
	}
	public void savePartyDetails(AmountTypeBean bean)throws ServiceNotAvailableException,EPISException
	{
		String amounttypecd="";
		try{
			con=DBUtility.getConnection();
			
			String insertQuery="INSERT INTO INVEST_AMOUNTTYPE_DETAILS (AMOUNTTYPE_CD,AMOUNT,AMOUNT_TYPE,AMOUNT_DATE,SECURITY_NAME,ENTEREDBY,ENTEREDDT)values(?,?,?,?,?,?,sysdate)";
			DBUtility.setAutoCommit(false,con);
			if(con!=null)
			{
				
				log.info("this is calling. saveparty in dao");
					String details[]=bean.getAchieveDetails();
					for(int i=0; i<details.length; i++)
					{
						amounttypecd=AutoGeneration.getNextCodeGBy("invest_amounttype_details","AMOUNTTYPE_CD",12,"invest",con);
						String securityName="";
						String amount="";
						String amountdate="";
						String amounttype="";
						StringTokenizer st1=new StringTokenizer(details[i],"|");
						while(st1.hasMoreTokens())
						{
							securityName=StringUtility.checknull(st1.nextToken());
							amount=StringUtility.checknull(st1.nextToken());
							amountdate=StringUtility.checknull(st1.nextToken());
							amounttype=StringUtility.checknull(st1.nextToken());
							log.info("the security name is"+securityName);
							log.info("the amount is"+amount);
						}
						pstmt=con.prepareStatement(insertQuery);
						if(pstmt!=null)
						{
							pstmt.setString(1,amounttypecd);
							pstmt.setString(2,amount);
							pstmt.setString(3,amounttype);
							pstmt.setString(4,amountdate);
							pstmt.setString(5,securityName);
							pstmt.setString(6,bean.getLoginUserId());
							pstmt.executeUpdate();
							
						}
						else{
							throw new ServiceNotAvailableException();
						}
					}
					
				
				
			}
			else {
				throw new ServiceNotAvailableException();
			}
			DBUtility.commitTrans(con);
		}
		catch(Exception e){
			try {
				DBUtility.rollbackTrans(con);
			} 
			 catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new EPISException(e1);
			}
			e.printStackTrace();
			log.error("AmountTypeDAO:PartyDetails:Exception"+e.getMessage());
			throw new EPISException(e);
		}finally {			
				DBUtility.closeConnection(rs,pstmt,con);			
		}
		
	}

}
