package com.epis.dao.cashbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;


import com.epis.bean.cashbook.BRStatementInfo;
import com.epis.bean.cashbook.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.AutoGeneration;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.StringUtility;

public class BRStatementDAO {
	Log log = new Log(BRStatementDAO.class);
	
	private BRStatementDAO() {
	}

	private static final BRStatementDAO dao = new BRStatementDAO();

	public static BRStatementDAO getInstance() {
		return dao;
	}

	public List getColumns() throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List columns = new ArrayList();
		BRStatementInfo brsInfo = null;
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getQueries("columnQuery"));
			while (rs.next()) {
				brsInfo = new BRStatementInfo();
				brsInfo.setColumnValue(rs.getString("COLUMNVALUE"));
				brsInfo.setDescription(rs.getString("DESCRIPTION"));
				columns.add(brsInfo);
			}
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, stmt, con);
		}
		return columns;
	}

	public BRStatementInfo getMappedColumns(BRStatementInfo info)
			throws Exception {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List columns = new ArrayList();
		BRStatementInfo brsInfo = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("mappingQuery"));
			pst.setString(1, info.getAccountNo());
			rs = pst.executeQuery();
			while (rs.next()) {
				brsInfo = new BRStatementInfo();
				brsInfo.setColumnValue(rs.getString("COLUMNVALUE"));
				brsInfo.setColumnNo(rs.getString("COLUMNNO"));
				columns.add(brsInfo);
				info.setDataLine(rs.getString("DATA_LINENO"));
			}
			info.setColMapping(columns);
			
		} catch (Exception e) {
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(rs, pst, con);
		}
		return info;
	}

	public void save(BRStatementInfo info) throws EPISException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(getQueries("deleteMapQuery"));
			pst.setString(1, info.getAccountNo());
			pst.executeUpdate();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				log.printStackTrace(e1);
				throw new EPISException(e1);
			}
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			BRStatementInfo colsInfo = null;
			pst = con.prepareStatement(getQueries("insertMapQuery"));
			List columns = info.getColMapping();
			log.info("-dataLine--"+info.getDataLine());
			int colsSize = columns.size();
			for (int i = 0; i < colsSize; i++) {
				colsInfo = (BRStatementInfo) columns.get(i);
				pst.setString(1, info.getAccountNo());
				pst.setString(2, colsInfo.getColumnNo());
				pst.setString(3, colsInfo.getColumnValue());
				pst.setString(4, info.getLoginUserId());
				pst.setString(5, info.getLoginUnitCode());
				pst.setString(6, info.getDataLine());
				pst.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				log.printStackTrace(e1);
				throw new EPISException(e1);
			}
			log.printStackTrace(e);
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pst, con);
		}
	}

	public void saveFile(BRStatementInfo info,String path)
			throws EPISException, Exception {
		Connection con = null;
		PreparedStatement pst = null;
		Statement st=null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			con = DBUtility.getConnection();			
			st = con.createStatement();
			rs1 = st.executeQuery("select count(*) from cb_brs_mapping where ACCOUNTNO='"+info.getAccountNo()+"'");
			if(rs1.next() &&  rs1.getInt(1)== 0){
				throw new EPISException("Mapping is not done for this Account No");
			} else {
				pst = con.prepareStatement(getQueries("fileCountQuery"));
				pst.setString(1,info.getFile().getFileName().trim());
				rs = pst.executeQuery();
				if(rs.next() &&  rs.getInt(1)> 0) {
					throw new EPISException("The File you are trying to import already exists");
				} else {
					log.info("path"+path);
					File file = saveFiletoPath(path,info);
					String keyno = saveFile(info);
					getData(file, keyno, info.getAccountNo());
				}
			}
		} catch (SQLException sqle) {
			log.error("BRStatementDAO:saveFile:Exception:" + sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception e) {
			log.error("BRStatementDAO:saveFile:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			st.close();
			con.close();
		}

	}

	private File saveFiletoPath(String path, BRStatementInfo info) throws EPISException {
		File file = null;
		try {
			file = new File(path +"uploads\\BRS\\"+ info.getFile().getFileName());
			InputStream inputStream = info.getFile().getInputStream();
			OutputStream out = new FileOutputStream(file);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
			inputStream.close();
		} catch (Exception e) {
			log.error("BRStatementDAO:saveFiletoPath:Exception:" + e.getMessage());
			throw new EPISException(e);
		}
		return file;
	}

	private String saveFile(BRStatementInfo info) throws EPISException {
		String keyno = null;
		Connection con = null;
		PreparedStatement pst = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			keyno = AutoGeneration.getNextCode("cb_brs_file","keyno", 6,con);
			pst = con.prepareStatement(getQueries("fileQuery"));
			pst.setString(1,keyno);
			pst.setString(2, info.getAccountNo());
			pst.setString(3,(info.getFile().getFileSize()> 0 ? StringUtility.checknull(info.getFile().getFileName()):""));
			pst.setString(4, info.getLoginUserId());
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			try {
				con.rollback();
				pst.close();
			} catch (SQLException e1) {			
				e1.printStackTrace();
			}			
			log.error("BRStatementDAO:saveFileInfo1:Exception:" + e.getMessage());
			throw new EPISException(e);
		}
		try{	
			if(info.getFile().getFileSize() > 0) {
				stmt = con.createStatement();
				rs = stmt.executeQuery("select STATEMENT_FILE from cb_brs_file where keyno = '"+keyno+"' FOR UPDATE");
				int bytesRead = 0;
				int bytesWritten = 0;
				int totbytesRead = 0;
				int totbytesWritten = 0;				
				if (rs.next()) {
					BLOB file = ((OracleResultSet) rs).getBLOB("STATEMENT_FILE");					
					int chunkSize = file.getChunkSize();					
					byte[] binaryBuffer = new byte[chunkSize];					
					int position = 1;					
					InputStream inputFileInputStream = info.getFile().getInputStream();					
					while ((bytesRead = inputFileInputStream.read(binaryBuffer)) != -1) {						
						bytesWritten = file.putBytes(position,binaryBuffer, bytesRead);						
						position += bytesRead;						
						totbytesRead += bytesRead;						
						totbytesWritten += bytesWritten;						
					}
					inputFileInputStream.close();
				}
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
			log.error("BRStatementDAO:saveFileInfo2:Exception:" + e.getMessage());
			throw new EPISException(e);
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}
		return keyno;
	}

	private void getData(File file, String keyno, String accountNo)
			throws EPISException {
		Connection con = null;
		PreparedStatement pst = null;
		List noValues = new ArrayList();
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);
			BRStatementInfo info = new BRStatementInfo();			
			info.setAccountNo(accountNo);
			info = getMappedColumns(info);
			int lineNo = Integer.parseInt(info.getDataLine());
			log.info("line no "+lineNo);
			List cols = info.getColMapping();
			Map columns = new HashMap();
			StringBuffer insertQuery = new StringBuffer("insert into cb_brs (FILE_KEY,KEYNO,ACCOUNTNO");
			StringBuffer valuesInsertQuery = new StringBuffer(") values ( '").append(keyno);
			valuesInsertQuery.append("',get_nextcode('cb_brs','keyno',11),'").append(accountNo).append("'");
			int colsSize = cols.size();
			for(int i=0;i<colsSize;i++){
				info = (BRStatementInfo)cols.get(i);
				if(!"0".equals(info.getColumnNo()) ){
					columns.put(info.getColumnNo(),info.getColumnValue());
				}
			}
			colsSize = columns.size();			
			for(int i=1;i<=colsSize;i++){
				if(columns.get(String.valueOf(i)) == null){
					noValues.add(String.valueOf(i));
				}else{
					insertQuery.append(",").append(columns.get(String.valueOf(i)));					
					if("TRANSACTIONDATE".equals(columns.get(String.valueOf(i))) || "VALUEDATE".equals(columns.get(String.valueOf(i)))){
						valuesInsertQuery.append(",to_date(?,'dd/MM/YY')");
					}else {
						valuesInsertQuery.append(",?");
					}
				}
			}
			insertQuery.append(valuesInsertQuery).append(")");
			pst = con.prepareStatement(insertQuery.toString());	
			BufferedReader br = new BufferedReader( new FileReader(file));		
			String strLine = "";		
			StringTokenizer st = null;		
			int lineNumber = 0;
			//read comma separated file line by line		
			while( (strLine = br.readLine()) != null){	
				lineNumber++;
				if(lineNumber>=lineNo){						
					//break comma separated line using ","		
					
					st = new StringTokenizer(strLine.replaceAll(",,",",0,"), ",");
					int pstCnt=1;
					for(int i=1;st.hasMoreTokens() && i<=colsSize;i++){	
						String token =  st.nextToken();
						if(i==1 || i==2)
						{
							StringTokenizer datevalue=new StringTokenizer(token," ");
							token=datevalue.nextToken();
						}
						if(!noValues.contains(String.valueOf(i))){
							pst.setString(pstCnt++,token);
						}	
					}
					for(int i=pstCnt;i<=colsSize;i++){	
						pst.setString(i,"");					
					}
					pst.executeUpdate();
				}
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
				pst.close();
				pst = con.prepareStatement("delete from cb_brs_file where keyno = '"+keyno+"'");
				pst.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}			
			log.error("BRStatementDAO:getData:Exception:" + e.getMessage());
			throw new EPISException(e);
		} finally {
			DBUtility.closeConnection(null, pst, con);
		}
	}
	
	public ArrayList showDetails(String description,String accountNo){
		log.info("inside dao");
		ArrayList list = new ArrayList();
		BRStatementInfo info =  null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			String strSql="select  to_char(TRANSACTIONDATE,'dd/mon/yyyy') TRANSACTIONDATE,TRANSACTIONDATE TRANSACTIONDATE1,to_char(VALUEDATE,'dd/mon/yyyy')VALUEDATE,nvl(CREDITAMOUNT,decode(DEBIT_CREDIT,'C',AMOUNT,0))CREDITAMOUNT,nvl(DEBITAMOUNT,decode(DEBIT_CREDIT,'D',AMOUNT,0))DEBITAMOUNT,(nvl(AMOUNT, 0)+ nvl(CREDITAMOUNT, 0)+nvl(DEBITAMOUNT,0))bankamount,DESCRIPTION,ACCOUNTNO,CHEQUENO,KEYNO from  cb_brs where ACCOUNTNO='"+ accountNo + "'";
			
			log.info("the strsqlis..."+strSql);
			if(!description.trim().equals(""))
				strSql +=" and  upper(DESCRIPTION) like upper('"+ description + "%')"; 
			strSql +="  and  RECONCILE_STATUS='N' order by TRANSACTIONDATE1";		
			log.info("query is "+strSql);
			st = con.createStatement();		
			rs=st.executeQuery(strSql);
			while(rs.next()){	
				info =  new BRStatementInfo();				
				info.setTransactionDt(rs.getString("TRANSACTIONDATE"));
				info.setValueDt(rs.getString("VALUEDATE"));		
				info.setChequeNo(rs.getString("CHEQUENO"));
				info.setDescription(rs.getString("DESCRIPTION"));						
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setKeyno(rs.getString("KEYNO"));
				info.setCreditAmt(rs.getString("CREDITAMOUNT"));
				info.setDebitAmt(rs.getString("DEBITAMOUNT"));
				info.setAmount(rs.getString("bankamount"));
				list.add(info);				
			}
		
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in showDetails "+e.getStackTrace());			
		}finally {
			try {
				rs.close();
				st.close();
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return list;
	}
	
	public String saveDetails(String keyno,String voucherNo,String accountno){
		String s = "";
		Connection con = null;
		Statement st = null;
		Statement st1 = null;
		ResultSet rs=null;
		String voucherNumber="";
		String vouchertype="";
		String infokeyno="";
		boolean accountflag=false;
		boolean empaccountflag=false;
		
		try {
			con = DBUtility.getConnection();
			//String inshisqry = "insert into EMPLOYEEADVANCESHISTORY select * from employeeadvances where EMPLOYEEADVCD = "+empadvcd;
			st=con.createStatement();
			
			rs=st.executeQuery("select VOUCHERNO,VOUCHERTYPE,KEYNO from cb_voucher_info where KEYNO in("+voucherNo+")");
			if(rs.next())
			{
				voucherNumber=StringUtility.checknull(rs.getString("VOUCHERNO"));
				vouchertype=StringUtility.checknull(rs.getString("VOUCHERTYPE"));
				infokeyno=StringUtility.checknull(rs.getString("KEYNO"));
			}
			String updateqry = "update cb_voucher_info set RECONCILE_STATUS= 'Y' where  KEYNO in("+voucherNo+")";
			log.info("updqry "+updateqry);
			s = "Updated Successfully";			
			con.setAutoCommit(false);			
			st.executeUpdate(updateqry);
			log.info("getAutoCommit "+con.getAutoCommit());
			if(keyno!=null){
				keyno = "'"+keyno.replaceAll(",","','")+"'";
				String updateqry1 = "update cb_brs set RECONCILE_STATUS= 'Y',VOUCHERNO='"+voucherNumber+"',CB_KEYNO='"+infokeyno+"' where keyno in ("+keyno+")";		
			log.info("updqry1 "+updateqry1);
			st1=con.createStatement();
			st1.executeUpdate(updateqry1);
			if(st1!=null)
			{
				st1.close();
				st1=null;
			}
			
			if(vouchertype.equals("C"))
			{
				String selqry="";
				 selqry="SELECT COUNT(*) FROM cb_voucher_info where keyno='"+infokeyno+"' and ACCOUNTNO in("+accountno+")";
				log.info("thesfsdfsdf"+selqry);
				st1=con.createStatement();
				rs=st1.executeQuery(selqry);
				if(rs.next())
				{
					accountflag=true;
				}
				if(rs!=null)
					rs.close();
				if(st1!=null)
				{
					st1.close();
					st1=null;
				}
				
				st1=con.createStatement();
				selqry="SELECT COUNT(*) FROM cb_voucher_info where keyno='"+infokeyno+"' and EMP_PARTY_CODE in("+accountno+")";
				rs=st1.executeQuery(selqry);
				if(rs.next())
				{
					empaccountflag=true;
				}
				if(rs!=null)
					rs.close();
				if(st1!=null)
				{
					st1.close();
					st1=null;
				}
				
				
			}
			if(vouchertype.equals("C"))
			{
				String updateqry2="";
				st1=con.createStatement();
				if(accountflag)
				updateqry2="update cb_voucher_info set accountflag='Y' where keyno='"+infokeyno+"'";
				if(empaccountflag)
					updateqry2="update cb_voucher_info set empaccountflag='Y' where keyno='"+infokeyno+"'";
				
				log.info("updatequery"+updateqry2);
				st1.executeUpdate(updateqry2);
			}
			}
			
			
			//log.info("getAutoCommit "+con.getAutoCommit());			
			con.commit();
			
		 } catch (Exception e) {
			log.info("Exception in catch, saveDetails() : " );
			s = "There is an error while saving the details";
			log.printStackTrace(e);
			try{
				con.rollback();
			}catch(Exception e1){
				log.printStackTrace(e1);
			}
		} finally {
			try {
				st.close();
				con.close();
			} catch (Exception e) {
				log.info("Exception in finally catch, saveDetails() : "+ e.getMessage());
			}
		}
		return s;
	}
	public String savePartyDetails(String rowvalue,String amounttype)
	{
		String message="";
		Connection con=null;
		Statement st=null;
		try{
			message="Update Successfully";
			con=DBUtility.getConnection();
			st=con.createStatement();
			String updateQuery="update cb_voucher_details set AMOUNT_TYPE='"+amounttype+"' where rowid='"+rowvalue+"' ";
			con.setAutoCommit(false);
			st.execute(updateQuery);
			con.commit();
			
			
		}
		catch(Exception e)
		{
			message=e.getMessage();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		finally{
			try {
				st.close();
				con.close();
			} catch (Exception e) {
				log.info("Exception in finally catch, savePartyDetails() : "+ e.getMessage());
			}
		}
		return message;
		
	}
	
	public ArrayList showReport(String fromDate,String toDate,String accountNo){
		log.info("inside dao");
		ArrayList list = new ArrayList();		
		VoucherInfo info =  null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();						
			
			String query = "select info.ACCOUNTNO,decode(VOUCHERTYPE,'R','Reciept','P','Payment','C', 'Contra') VOUCHERTYPE,to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT, VOUCHERNO,decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(debit)) credit,decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(credit)) debit ,info.DETAILS  from cb_voucher_info info, cb_voucher_details details where  info.keyno = details.keyno and RECONCILE_STATUS='Y' "; 
			if(!StringUtility.checknull(accountNo).trim().equals(""))
				query +=" and ACCOUNTNO ='"+accountNo+"' ";
			
			if(!StringUtility.checknull(fromDate).trim().equals(""))
				query +=" and VOUCHER_DT >='"+fromDate+"' ";
			if(!StringUtility.checknull(toDate).trim().equals(""))
				query +=" and VOUCHER_DT <='"+toDate+"' ";
			
			query +=" group by VOUCHERTYPE, VOUCHER_DT, VOUCHERNO,info.DETAILS,info.ACCOUNTNO ";
			pst = con.prepareStatement(query);			
			rs=pst.executeQuery();
			while(rs.next()){	
				info =  new VoucherInfo();				
				info.setVoucherDt(rs.getString("VOUCHER_DT"));
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setDetails(rs.getString("DETAILS"));				
				info.setVoucherNo(rs.getString("VOUCHERNO"));
				info.setVoucherType(rs.getString("VOUCHERTYPE"));
				info.setDebitAmount(rs.getString("debit"));
				info.setCreditAmount(rs.getString("credit"));
				list.add(info);			
			}
		
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in showDetails "+e.getMessage());			
		}finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return list;
	}
	
	public ArrayList unreconcileReport(String fromDate,String toDate,String accountNo){
		log.info("inside dao");
		ArrayList list = new ArrayList();
		BRStatementInfo info =  null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			String query = "select to_char(VALUEDATE,'dd/Mon/YYYY') VALUEDATE, ACCOUNTNO,nvl(CREDITAMOUNT,decode(DEBIT_CREDIT,'C',AMOUNT,0))CREDITAMOUNT,nvl(DEBITAMOUNT,decode(DEBIT_CREDIT,'D',AMOUNT,0))DEBITAMOUNT,DESCRIPTION  from cb_brs  where  RECONCILE_STATUS='N' "; 
			
			if(!StringUtility.checknull(accountNo).trim().equals(""))
				query +=" and ACCOUNTNO ='"+accountNo+"' ";
			
			if(!StringUtility.checknull(fromDate).trim().equals(""))
				query +=" and VALUEDATE >='"+fromDate+"' ";
			if(!StringUtility.checknull(toDate).trim().equals(""))
				query +=" and VALUEDATE <='"+toDate+"' ";	
			pst = con.prepareStatement(query);			
			rs=pst.executeQuery();
			while(rs.next()){	
				info =  new BRStatementInfo();				
				info.setValueDt(rs.getString("VALUEDATE"));
				info.setDescription(rs.getString("DESCRIPTION"));						
				info.setAccountNo(rs.getString("ACCOUNTNO"));
				info.setCreditAmt(rs.getString("CREDITAMOUNT"));
				info.setDebitAmt(rs.getString("DEBITAMOUNT"));
				list.add(info);			
			}
		
		}catch(Exception e){
			log.info(">>>>>>>>>>>>Exception in showDetails "+e.getMessage());			
		}finally {
			try {
				rs.close();
				pst.close();
				con.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return list;
	}
	
	 public ArrayList unreconcileVoucherReport(String fromDate, String toDate, String accountNo)
	    {
	        ArrayList list;
	        Connection con;
	        PreparedStatement pst=null;
	        ResultSet rs=null;
	        log.info("inside dao");
	        list = new ArrayList();
	        BRStatementInfo info = null;
	        con = null;
	        pst = null;
	        rs = null;
	        try
	        {
	            con = DBUtility.getConnection();
	            String query = "select decode(VOUCHERTYPE,'R','Reciept','P','Payment','C', 'Contra') VOUCHERTYPE,ACCOUNTNO,info.KEYNO KEYNO,TRUSTTYPE,to_char(VOUCHER_DT,'dd/Mon/YYYY') VOUCHER_DT,VOUCHERNO,(case when (vouchertype='C') then (decode(info.accountno, emp_party_code, sum(debit), sum(credit))) else(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))) end ) credit,(case when (vouchertype='C') then decode(info.ACCOUNTNO, emp_party_code, sum(credit), sum(debit)) else  (decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) end) debit,(decode(VOUCHERTYPE, 'R', sum(credit)-sum(debit), 'P', 0,sum(credit))+ decode(VOUCHERTYPE, 'P', sum(debit)-sum(credit), 'R', 0,sum(debit))) voucheramt   from cb_voucher_info info, cb_voucher_details details where  info.keyno = details.keyno and RECONCILE_STATUS='N' and (ACCOUNTNO=info.ACCOUNTNO or (vouchertype = 'C' AND emp_party_code =info.ACCOUNTNO))  ";
	            if(!StringUtility.checknull(accountNo).trim().equals(""))
	                query +=" and ACCOUNTNO ='" + accountNo + "' ";
	            if(!StringUtility.checknull(fromDate).trim().equals(""))
	                query +=" and voucher_dt >='" + fromDate + "' ";
	            if(!StringUtility.checknull(toDate).trim().equals(""))
	                query +=" and voucher_dt <='" + toDate + "' ";
	            query +=" group by VOUCHER_DT,VOUCHERNO,VOUCHERTYPE, ACCOUNTNO,info.KEYNO,TRUSTTYPE,emp_party_code order by to_date(VOUCHER_DT,'dd/mm/yyyy'),VOUCHERNO ";
	            log.info("the query is..." + query);
	            pst = con.prepareStatement(query);
	            
	            for(rs = pst.executeQuery(); rs.next(); list.add(info))
	            {
	                info = new BRStatementInfo();
	                info.setVoucherDate(rs.getString("VOUCHER_DT"));
	                info.setVoucherNo(rs.getString("VOUCHERNO"));
	                info.setAccountNo(rs.getString("ACCOUNTNO"));
	                info.setCreditAmt(rs.getString("credit"));
	                info.setDebitAmt(rs.getString("debit"));
	            }

	        }
	        catch(Exception e)
	        {
	            log.info(">>>>>>>>>>>>Exception in showDetails " + e.getMessage());
	        }
	       finally{
				try {
					rs.close();
					pst.close();
					con.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	       }
	        return list;
	    }

	private static String getQueries(String queryName) {
		Map queries = new HashMap();
		// Query to Retieve the columns
		queries.put("columnQuery",
			"select COLUMNVALUE,DESCRIPTION from cb_brs_columns");

		// Query to Retrieve the Mapped columns with the account
		queries.put("mappingQuery",
			"select COLUMNNO,COLUMNVALUE,DATA_LINENO from cb_brs_mapping  where ACCOUNTNO=? order by COLUMNNO");

		// Query to delete the Mapped columns with the account
		queries.put("deleteMapQuery",
			"delete from cb_brs_mapping  where ACCOUNTNO=?");

		// Query to insert the Mapped columns with the account
		queries.put("insertMapQuery",
			"insert into cb_brs_mapping (ACCOUNTNO,COLUMNNO,COLUMNVALUE,ENTEREDBY,UNITCODE,DATA_LINENO) values (?,?,?,?,?,?)");
		
		// Query to get the count of files which exists with the same name 
		queries.put("fileCountQuery",
			"select count(*) from cb_brs_file where STATEMENT_FILENAME = ? ");
		
		// Query to Insert File
		queries.put("fileQuery",
			"insert into cb_brs_file(keyno,ACCOUNTNO,STATEMENT_FILENAME,STATEMENT_FILE,CREATED_BY) values(?,?,?,EMPTY_BLOB(),?)");
		
		//	Query to Insert File
		queries.put("fileUploadQuery","select STATEMENT_FILE from cb_brs_file where keyno = ? FOR UPDATE");
		
		
		
		return (String) queries.get(queryName);
	}
	
	

}