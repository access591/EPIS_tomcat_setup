package com.epis.dao.cashbook;

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

import com.epis.bean.cashbook.DailyStatement;
import com.epis.bean.cashbook.IncomeAndExpenditureBean;
import com.epis.bean.cashbook.PaymentReceiptBean;
import com.epis.bean.cashbook.PaymentReceiptDtBean;
import com.epis.bean.cashbook.ReportBean;
import com.epis.bean.cashbookDummy.BankBook;
import com.epis.bean.cashbookDummy.VoucherInfo;
import com.epis.common.exception.EPISException;
import com.epis.utilities.DBUtility;
import com.epis.utilities.Log;
import com.epis.utilities.SQLHelper;
import com.epis.utilities.StringUtility;

public class ReportDAO {
	
	Log log = new Log(ReportDAO.class);

	private ReportDAO() {
	}

	private static final ReportDAO dao = new ReportDAO();

	public static ReportDAO getInstance() {
		return dao;
	}
	
	public List getBanks() throws EPISException{
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		List banks = new ArrayList();
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getQueries("bankQuery"));
			while(rs.next()){
				banks.add(rs.getString("ACCOUNTNO"));
			}			
		}catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,stmt,con);
		}		
		return banks;	
	}
	public PaymentReceiptBean getPaymentandReceiptReport(PaymentReceiptDtBean dtbean)throws EPISException
	{
		ArrayList paymentList=new ArrayList();
		ArrayList ReceiptList=new ArrayList();
		PaymentReceiptDtBean bean=null;
		PaymentReceiptBean listbean=null;
		PreparedStatement pst=null;
		PreparedStatement pstopening=null;
		PreparedStatement pstClose=null;
		Connection con=null;
		ResultSet rs=null;
		HashMap stmts = new HashMap(); 
		String accountNo = null;
		SQLHelper sql=new SQLHelper();
		List banks = null;
		DailyStatement dStmt=null;
		StringTokenizer st=new StringTokenizer(dtbean.getFinYear(),"-");
		String fromDate="01 Apr";
		String endDate="31 Mar";
		
		while(st.hasMoreTokens())
		{
			fromDate+=st.nextToken();
			endDate+="20"+st.nextToken();
		}
		
		try{
			con=DBUtility.getConnection();
			
			banks=getBanks();
			pstopening = con.prepareStatement(getQueries("bankOpenBalQuery"));
			pstClose = con.prepareStatement(getQueries("bankCloseBalQuery"));
			
			for(int i=0;i<banks.size();i++){
				accountNo = (String)banks.get(i);
				dStmt = new DailyStatement();
				dStmt.setAccountNo(accountNo);								
				pstopening.setString(1, accountNo);
				pstopening.setString(2, accountNo);
				pstopening.setString(3, fromDate);
				pstopening.setString(4, accountNo);
				pstopening.setString(5, fromDate);
				pstopening.setString(6, accountNo);
				pstopening.setString(7, fromDate);
				pstopening.setString(8, accountNo);				
				pstopening.setString(9, fromDate);
				pstopening.setString(10, accountNo);
				pstopening.setString(11, fromDate);
				pstopening.setString(12, accountNo);
				pstopening.setString(13, accountNo);
				pstopening.setString(14, accountNo);
				pstopening.setString(15, accountNo);
				pstopening.setString(16, fromDate);
				rs = pstopening.executeQuery();
				if (rs.next()) {
					dStmt.setOpeningBalance(rs.getString(1) +"-"+rs.getString("amountType"));
					dStmt.setBankName(rs.getString("bankname"));
				}
				rs.close();
				
				pstClose.setString(1, accountNo);
				pstClose.setString(2, accountNo);
				pstClose.setString(3,endDate);
				pstClose.setString(4, accountNo);
				pstClose.setString(5, endDate);
				pstClose.setString(6, accountNo);
				pstClose.setString(7, endDate);
				pstClose.setString(8, accountNo);				
				pstClose.setString(9, endDate);
				pstClose.setString(10, accountNo);
				pstClose.setString(11, endDate);
				pstClose.setString(12, accountNo);
				pstClose.setString(13, accountNo);
				pstClose.setString(14, accountNo);
				pstClose.setString(15, accountNo);
				pstClose.setString(16, endDate);
				rs = pstClose.executeQuery();
				if (rs.next()) {
					dStmt.setClosingBalance(rs.getString(1) +"-"+rs.getString("amountType"));					
				}
				
				stmts.put(dStmt.getAccountNo(),dStmt);
				rs.close();				
			}
			pstopening.close();
			pstClose.close();
			String paymentandReceiptQuery=" select ACCOUNTHEAD,sum(nvl(amount,0)) amount, paymenttype  from (select info.voucherno,details.accounthead, (case when (sum(nvl(details.debit, 0)) > sum(nvl(details.credit, 0)) and  info.vouchertype = 'P') then sum(nvl(details.debit, 0)) - sum(nvl(details.credit, 0)) when (sum(nvl(details.debit, 0)) < sum(nvl(details.credit, 0)) and   info.vouchertype = 'P') then  sum(nvl(details.credit, 0)) - sum(nvl(details.debit, 0))  end)amount, (case   when (sum(nvl(details.debit, 0)) > sum(nvl(details.credit, 0)) and info.vouchertype = 'P') then 'P'  when (sum(nvl(details.debit, 0)) < sum(nvl(details.credit, 0)) and info.vouchertype = 'P') then  'R'  end) paymenttype  from cb_voucher_info info, cb_voucher_details details  where info.keyno = details.keyno and info.fyear = ? and info.vouchertype='P'  group by info.voucherno, info.vouchertype,details.accounthead  union  select info.voucherno,details.accounthead ACCOUNTHEAD,(case when (sum(nvl(details.credit, 0)) > sum(nvl(details.debit, 0)) and  info.vouchertype = 'R') then  sum(nvl(details.credit, 0)) - sum(nvl(details.debit, 0)) when (sum(nvl(details.credit, 0)) < sum(nvl(details.debit, 0)) and  info.vouchertype = 'R') then  sum(nvl(details.debit, 0)) - sum(nvl(details.credit, 0))  end) amount,(case when (sum(nvl(details.credit, 0)) >sum(nvl(details.debit, 0)) and  info.vouchertype in ('R')) then  'R'  when (sum(nvl(details.credit, 0)) < sum(nvl(details.debit, 0)) and  info.vouchertype IN ('R')) then  'P' end) paymenttype from cb_voucher_info info, cb_voucher_details details  where info.keyno = details.keyno and info.fyear =? and info.vouchertype='R'  group by details.accounthead, info.vouchertype,info.voucherno) where amount > 0 group by ACCOUNTHEAD,paymenttype order by ACCOUNTHEAD ";
			pst=con.prepareStatement(paymentandReceiptQuery);
			pst.setString(1,dtbean.getFinYear());
			pst.setString(2,dtbean.getFinYear());
			
			rs=pst.executeQuery();
			while(rs.next())
			{
				
				if(rs.getString("paymenttype").equals("R"))
				{
					bean=new PaymentReceiptDtBean();
					bean.setReceiptAmt(rs.getString("amount"));
					bean.setAccheadDesc(rs.getString("ACCOUNTHEAD")+" "+sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD",rs.getString("ACCOUNTHEAD"),con));
					ReceiptList.add(bean);
				}
				if(rs.getString("paymenttype").equals("P"))
				{
					bean=new PaymentReceiptDtBean();
					bean.setPaymentAmt(rs.getString("amount"));
					bean.setAccheadDesc(rs.getString("ACCOUNTHEAD")+" "+sql.getDescription("cb_accountcode_info","PARTICULAR","ACCOUNTHEAD",rs.getString("ACCOUNTHEAD"),con));
					paymentList.add(bean);
				}
				
				
				
			}
			if(ReceiptList.size()>paymentList.size())
			{
				for(int i=paymentList.size(); i<ReceiptList.size(); i++)
				{
					bean=new PaymentReceiptDtBean();
					bean.setPaymentAmt("0");
					bean.setAccheadDesc("&nbsp;");
					paymentList.add(bean);
				}
			}
			if(paymentList.size()>ReceiptList.size())
			{
				for(int j=ReceiptList.size(); j<paymentList.size();j++)
				{
					bean=new PaymentReceiptDtBean();
					bean.setReceiptAmt("0");
					bean.setAccheadDesc("&nbsp;");
					ReceiptList.add(bean);
				}
				
			}
			
			listbean=new PaymentReceiptBean();
			listbean.setReceiptList(ReceiptList);
			listbean.setPaymentList(paymentList);
			listbean.setBankDetails(stmts);
			
		}
		catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}		
		return listbean;
	}
	
	public Map getDailyStatement(ReportBean bean) throws EPISException {
		DailyStatement dStmt = null;
		Connection con = null;
		PreparedStatement pst = null;
		PreparedStatement pstClose = null;
		ResultSet rs = null;
		Map stmts = new HashMap(); 
		String accountNo = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("bankOpenBalQuery"));
			pstClose = con.prepareStatement(getQueries("bankCloseBalQuery"));
			List banks = getBanks();
			
			for(int i=0;i<banks.size();i++){
				accountNo = (String)banks.get(i);
				dStmt = new DailyStatement();
				dStmt.setAccountNo(accountNo);								
				pst.setString(1, accountNo);
				pst.setString(2, accountNo);
				pst.setString(3, bean.getFromDate());
				pst.setString(4, accountNo);
				pst.setString(5, bean.getFromDate());
				pst.setString(6, accountNo);
				pst.setString(7, bean.getFromDate());
				pst.setString(8, accountNo);				
				pst.setString(9, bean.getFromDate());
				pst.setString(10, accountNo);
				pst.setString(11, bean.getFromDate());
				pst.setString(12, accountNo);
				pst.setString(13, accountNo);
				pst.setString(14, accountNo);
				pst.setString(15, accountNo);
				pst.setString(16, bean.getFromDate());
				rs = pst.executeQuery();
				if (rs.next()) {
					dStmt.setOpeningBalance(rs.getString(1) +" "+rs.getString("amountType"));
					dStmt.setBankName(rs.getString("bankname"));
				}
				rs.close();
				
				pstClose.setString(1, accountNo);
				pstClose.setString(2, accountNo);
				pstClose.setString(3, bean.getFromDate());
				pstClose.setString(4, accountNo);
				pstClose.setString(5, bean.getFromDate());
				pstClose.setString(6, accountNo);
				pstClose.setString(7, bean.getFromDate());
				pstClose.setString(8, accountNo);				
				pstClose.setString(9, bean.getFromDate());
				pstClose.setString(10, accountNo);
				pstClose.setString(11, bean.getFromDate());
				pstClose.setString(12, accountNo);
				pstClose.setString(13, accountNo);
				pstClose.setString(14, accountNo);
				pstClose.setString(15, accountNo);
				pstClose.setString(16, bean.getFromDate());
				rs = pstClose.executeQuery();
				if (rs.next()) {
					dStmt.setClosingBalance(rs.getString(1) +" "+rs.getString("amountType"));					
				}
				
				stmts.put(dStmt.getAccountNo(),dStmt);
				rs.close();				
			}
			pst.close();
			pstClose.close();
			
			pst = con.prepareStatement(getQueries("dailyStatement"));
			pst.setString(1, bean.getFromDate());
			rs = pst.executeQuery();
			while(rs.next()){
				dStmt = (DailyStatement) stmts.get(rs.getString("ACCOUNTNO"));
				if("AR".equals(rs.getString("code"))){
					dStmt.setAccretion(rs.getString("AMOUNT"));
					dStmt.setTotalReceipt(String.valueOf(Double.parseDouble(dStmt.getTotalReceipt())+rs.getDouble("AMOUNT")));
				}				
				else if("FP".equals(rs.getString("code"))){
					dStmt.setFinalPayment(rs.getString("AMOUNT"));
					dStmt.setTotalPayment(String.valueOf(Double.parseDouble(dStmt.getTotalPayment())+rs.getDouble("AMOUNT")));
				}
				else if("IOBR".equals(rs.getString("code"))){
					dStmt.setInterstOnBonds(rs.getString("AMOUNT"));
					dStmt.setTotalReceipt(String.valueOf(Double.parseDouble(dStmt.getTotalReceipt())+rs.getDouble("AMOUNT")));
				}
				else if("IP".equals(rs.getString("code"))){
					dStmt.setInvestment(rs.getString("AMOUNT"));
					dStmt.setTotalPayment(String.valueOf(Double.parseDouble(dStmt.getTotalPayment())+rs.getDouble("AMOUNT")));
				}
				else if("LAP".equals(rs.getString("code"))){
					dStmt.setLoansAndAdvance(rs.getString("AMOUNT"));
					dStmt.setTotalPayment(String.valueOf(Double.parseDouble(dStmt.getTotalPayment())+rs.getDouble("AMOUNT")));
				}
				else if("MOBR".equals(rs.getString("code"))){
					dStmt.setMaturityOfBonds(rs.getString("AMOUNT"));
					dStmt.setTotalReceipt(String.valueOf(Double.parseDouble(dStmt.getTotalReceipt())+rs.getDouble("AMOUNT")));
				}
				else if("OP".equals(rs.getString("code"))){
					dStmt.setOtherPayment(rs.getString("AMOUNT"));
					dStmt.setTotalPayment(String.valueOf(Double.parseDouble(dStmt.getTotalPayment())+rs.getDouble("AMOUNT")));
				}
				else if("OR".equals(rs.getString("code"))){
					dStmt.setOtherReceipts(rs.getString("AMOUNT"));
					dStmt.setTotalReceipt(String.valueOf(Double.parseDouble(dStmt.getTotalReceipt())+rs.getDouble("AMOUNT")));
				}
				else if("PFP".equals(rs.getString("code"))){
					dStmt.setPartFinal(rs.getString("AMOUNT"));
					dStmt.setTotalPayment(String.valueOf(Double.parseDouble(dStmt.getTotalPayment())+rs.getDouble("AMOUNT")));
				}
				else if("ROBR".equals(rs.getString("code"))){
					dStmt.setRedemptionOfBonds(rs.getString("AMOUNT"));
					dStmt.setTotalReceipt(String.valueOf(Double.parseDouble(dStmt.getTotalReceipt())+rs.getDouble("AMOUNT")));
				}
				
				stmts.put(dStmt.getAccountNo(),dStmt);
			}			
		}catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,pst,con);
		}		
		return stmts;
	}
	
	public Map getBalanceSheet(ReportBean bean) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		List asset = null;
		List liability = null;
		IncomeAndExpenditureBean bsBean = null;
		Map slmap = new HashMap();
		try {
			String[] finyears = bean.getFinYear();
			int finlen = finyears.length;
			StringBuffer query = new StringBuffer("select type.name ,type.description,type.type ");
			for(int i=0;i < finlen ;i++){
				if("2010".equals(finyears[i])){
					query.append(" ,((case when type.type = '00001' OR type.type='00009' then -1 else 1 end) * (Sum(");
					query.append(" Nvl(getaccheadopenbal(info.accounthead, '").append(bean.getTrustType());
					query.append("','01/APR/2010' ,''),0)))/12)*(to_number(to_char(to_date('");
					query.append(bean.getToMonth()).append("','MON'),'MM'))+ (case when to_number(");
					query.append(" to_char(to_date('").append(bean.getToMonth()).append("','MON'), 'MM'))>3 ");
					query.append(" then -3 else 9 end )) ");
				}else {
					query.append(" ,(case when type.type = '00001' OR type.type='00009' then -1 else 1 end) * (Sum( ");
					query.append(" Nvl(getaccheadopenbal(info.accounthead, '").append(bean.getTrustType());
					query.append("','01/'||to_char(add_months(to_date('").append(bean.getToMonth());
					query.append("','MON'),1),'MON')||(").append(finyears[i]).append(" + (case when ");
					query.append(" to_number(to_char(to_date('").append(bean.getToMonth());
					query.append("','MON'), 'MM'))>3 then -1 else 0 end))").append(",''),0))) ");					
				}
			}
			query.append(" from cb_scheduletype type, cb_accountcode_info info where (instr(");
			query.append(" type.accheadsadd, info.accounthead) > 0 or instr(type.accheadsless, ");
			query.append(" info.accounthead) > 0) group by type.name, type.description, type.type order by  type.name ");
			System.out.println(query);
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			
			asset = new ArrayList();
			liability = new ArrayList();
			StringBuffer appenderLink = new StringBuffer("<A href='#' onclick=\"execute('");
			appenderLink.append(bean.getTrustType()).append("','").append(bean.getToMonth());
			appenderLink.append("','");
			appenderLink.append(finyears[finlen-1]);
			while(rs.next()){
				bsBean = new IncomeAndExpenditureBean();
				bsBean.setParticular(rs.getString("description"));
				bsBean.setSchType(appenderLink+"','"+rs.getString("name")+"')\">"+rs.getString("name")+"</A>");
				String[] str = new String[finlen];
				for(int i=0;i < finlen ;i++){
					str[i] = rs.getString(4+i);
				}
				bsBean.setAmount(str);
				if("00001".equals(rs.getString("type")) || "00009".equals(rs.getString("type"))){
					bsBean.setType("Asset");
					asset.add(bsBean);
				}else if("00002".equals(rs.getString("type"))|| "00006".equals(rs.getString("type"))){
					bsBean.setType("Liability");
					liability.add(bsBean);
				}
			}		
			slmap.put("A",asset);
			slmap.put("L",liability);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return slmap;
	}
	
	public Map getIncomeAndExpenditure(ReportBean bean) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		List ieexp = null;
		List ieinc = null;
		Map iemap = new HashMap();
		IncomeAndExpenditureBean ieBean = null;
		try {
			String[] finyears = bean.getFinYear();
			int finlen = finyears.length;
			StringBuffer query = new StringBuffer("select type.name ,type.description,type.type ");
			for(int i=0;i < finlen ;i++){
				if("2010".equals(finyears[i])){
					query.append(" ,((Sum(");
					query.append(" Nvl(getaccheadopenbal(info.accounthead, '").append(bean.getTrustType());
					query.append("','01/APR/2010' ,''),0)))/12)*(to_number(to_char(to_date('");
					query.append(bean.getToMonth()).append("','MON'),'MM'))+ (case when to_number(");
					query.append(" to_char(to_date('").append(bean.getToMonth()).append("','MON'), 'MM'))>3 ");
					query.append(" then -3 else 9 end )) ");
				}else {
					query.append(" ,(Sum( ");
					query.append(" Nvl(getaccheadopenbal(info.accounthead, '").append(bean.getTrustType());
					query.append("','01/'||to_char(add_months(to_date('").append(bean.getToMonth());
					query.append("','MON'),1),'MON')||(").append(finyears[i]).append(" + (case when ");
					query.append(" to_number(to_char(to_date('").append(bean.getToMonth());
					query.append("','MON'), 'MM'))>3 then -1 else 0 end))").append(",''),0))) ");					
				}
			}
			query.append(" from cb_scheduletype type, cb_accountcode_info info where (instr(");
			query.append(" type.accheadsadd, info.accounthead) > 0 or instr(type.accheadsless, ");
			query.append(" info.accounthead) > 0) and type.type in ('00003','00004') group by type.name, type.description, type.type order by  type.name ");
			
			System.out.println(query);
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			
		    ieexp = new ArrayList();
			ieinc = new ArrayList();

			StringBuffer appenderLink = new StringBuffer("<A href='#' onclick=\"execute('");
			appenderLink.append(bean.getTrustType()).append("','").append(bean.getToMonth());
			appenderLink.append("','");
			appenderLink.append(finyears[finlen-1]);

			while(rs.next()){
				ieBean = new IncomeAndExpenditureBean();
				ieBean.setParticular(rs.getString("description"));
				ieBean.setSchType(appenderLink+"','"+rs.getString("name")+"')\">"+rs.getString("name")+"</A>");
				String[] str = new String[finlen];
				for(int i=0;i < finlen ;i++){
					str[i] = rs.getString(4+i);
				}
				ieBean.setAmount(str);
				if("00004".equals(rs.getString("type").trim())){
					ieBean.setType("Expenditure");
					ieexp.add(ieBean);
				}else if("00003".equals(rs.getString("type").trim())){
					ieBean.setType("Income");
					ieinc.add(ieBean);
				}
			}		
			iemap.put("E",ieexp);
			iemap.put("I",ieinc);	
		}catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,stmt,con);
		}	   
		return iemap;
	}
	
	
	public String getReserveAmount(String year,String trustType,String month) throws EPISException {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		String bal = "0";
		try {
			if(!"2010".equals(year)) {			
				StringBuffer query = new StringBuffer(" select Sum(Nvl(getaccheadopenbal(info.accounthead,'");
				query.append(trustType).append("','01/' || to_char(add_months(to_date('");
				query.append(month).append("', 'MON'),1),'MON') || (").append(year).append(" + (case when to_number(to_char");
				query.append(" (to_date('").append(month).append("','MON'),'MM')) > 3 then -1 else 0 end)),''),");
				query.append(" 0)) from cb_scheduletype type, cb_accountcode_info info where (instr(");
				query.append(" type.accheadsadd, info.accounthead) > 0 or instr(type.accheadsless, ");
				query.append(" info.accounthead) > 0) and type.type in ('00003', '00004')");
				
			    System.out.println("-----ss------"+query);
				con = DBUtility.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(query.toString());			
				while(rs.next()){
					bal = rs.getString(1);		
				}
			}
		}catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,stmt,con);
		}		
		if(!"2010".equals(year)) {
			bal = String.valueOf(Double.parseDouble(bal)+Double.parseDouble(getReserveAmount(String.valueOf(Integer.parseInt(year)-1),trustType,month)));
		}
		return bal;
	}	
	
	public Map getScheduleReport(ReportBean info) throws Exception {
		log.info("VoucherDAO : getTrialBalance : Entering method");
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map trialBal = new HashMap();
		List accountHeads = null;
		Map trustTypeBased = null;
		try {
			con = DBUtility.getConnection();
			pst = con.prepareStatement(getQueries("SchedQuery"));
			
			pst.setString(1, info.getTrustType());
			pst.setString(2, info.getFromDate());
			pst.setString(3, info.getFromDate());
			pst.setString(4, info.getToDate());
			pst.setString(5, info.getToDate());
			pst.setString(6, info.getTrustType());
			pst.setString(7, info.getFromDate());
			pst.setString(8, info.getFromDate());
			pst.setString(9, info.getToDate());
			pst.setString(10, info.getToDate());			
			pst.setString(11, info.getTrustType());
			pst.setString(12, info.getFromDate());
			pst.setString(13, info.getFromDate());
			pst.setString(14, info.getToDate());
			pst.setString(15, info.getToDate());
			pst.setString(16, info.getTrustType());
			pst.setString(17, info.getFromDate());
			pst.setString(18, info.getFromDate());
			pst.setString(19, info.getToDate());
			pst.setString(20, info.getToDate());
			pst.setString(21, info.getFromDate());
			pst.setString(22, info.getFromDate());
			pst.setString(23, info.getTrustType());
			pst.setString(24, info.getFromDate());
			pst.setString(25, info.getFromDate());
			pst.setString(26, info.getToDate());
			pst.setString(27, info.getToDate());
			pst.setString(28, info.getFromDate());
			pst.setString(29, info.getFromDate());
			pst.setString(30, info.getTrustType());
			pst.setString(31, info.getFromDate());
			pst.setString(32, info.getFromDate());
			pst.setString(33, info.getToDate());
			pst.setString(34, info.getToDate());
			pst.setString(35, info.getFromDate());
			pst.setString(36, info.getFromDate());
			pst.setString(37, info.getTrustType());
			pst.setString(38, info.getTrustType());
			pst.setString(39, info.getFromDate());
			pst.setString(40, info.getFromDate());
			pst.setString(41, info.getToDate());
			pst.setString(42, info.getToDate());
			pst.setString(43, info.getTrustType());
			pst.setString(44, info.getFromDate());
			pst.setString(45, info.getFromDate());
			pst.setString(46, info.getToDate());
			pst.setString(47, info.getToDate());
			pst.setString(48, info.getScheduleType());
			rs = pst.executeQuery();
			while (rs.next()) {
				if (trialBal.containsKey(rs.getString("trusttype"))) {
					trustTypeBased = (HashMap) trialBal.get(rs
							.getString("trusttype"));
					if (trustTypeBased.containsKey(rs.getString("type"))) {
						accountHeads = (List) trustTypeBased.get(rs
								.getString("type"));
					} else {
						accountHeads = new ArrayList();
					}
				} else {
					trustTypeBased = new HashMap();
					accountHeads = new ArrayList();
				}
				BankBook trialBalance = new BankBook();
				trialBalance.setTrustType(rs.getString("trusttype"));
				trialBalance.setAccountHeadType(rs.getString("type"));
				trialBalance.setAccountHead(rs.getString("Accountcode"));
				trialBalance.setOpeningBalAmtDebit(rs.getDouble("openBaldebit"));
				trialBalance.setOpeningBalAmt(rs.getDouble("openBalcredit"));
				
				if(rs.getDouble("debit") >= 0 && rs.getDouble("credit") >= 0){
					trialBalance.setPayments(rs.getDouble("debit"));
					trialBalance.setReceipts(rs.getDouble("credit"));
				} else if(rs.getDouble("debit") < 0 && rs.getDouble("credit") >= 0){
					trialBalance.setPayments(0.00);
					trialBalance.setReceipts(rs.getDouble("credit")+(-1.00 * rs.getDouble("debit")));
				} else if(rs.getDouble("debit") >= 0 && rs.getDouble("credit") < 0){
					trialBalance.setPayments(rs.getDouble("debit")+(-1.00 * rs.getDouble("credit")));
					trialBalance.setReceipts(0.00);
				} else if(rs.getDouble("debit") < 0 && rs.getDouble("credit") < 0){
					trialBalance.setPayments((-1.00 * rs.getDouble("credit")));
					trialBalance.setReceipts((-1.00 * rs.getDouble("debit")));
				}
					
				
				accountHeads.add(trialBalance);
				trustTypeBased.put(rs.getString("type"), accountHeads);
				trialBal.put(rs.getString("trusttype"), trustTypeBased);
			}
		} catch (SQLException e) {
			log.printStackTrace(e);
			throw e;
		} catch (Exception e) {
			log.printStackTrace(e);
			throw e;
		} finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
		log.info("VoucherDAO : getTrialBalance : leaving method");	
		
		return trialBal;
	}
	
	private String getQueries(String queryName){
		HashMap queries = new HashMap();
		
		StringBuffer bankOpenBalQuery = new StringBuffer("select abs(OpeningBanace) OpeningBanace, (select ");
		bankOpenBalQuery.append(" bankname from CB_BANK_INFO where accountno = ?) bankname, case when ");
		bankOpenBalQuery.append(" OpeningBanace >0 then decode((select amountType from cb_bankopeningbal_info");
		bankOpenBalQuery.append(" where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')), 'DR', 'Dr.',");
		bankOpenBalQuery.append(" 'Cr.') else decode((select amountType from cb_bankopeningbal_info where ");
		bankOpenBalQuery.append(" accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')), 'CR', 'Dr.', ");
		bankOpenBalQuery.append(" 'Dr.') end amountType from ( select (case when Nvl((select amountType from ");
		bankOpenBalQuery.append(" cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, ");
		bankOpenBalQuery.append(" 'dd/MM/YYYY')), 'CR') = 'DR' then (NVl((select amount from cb_bankopeningbal_info");
		bankOpenBalQuery.append(" where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')), 0) + ");
		bankOpenBalQuery.append(" NVL(ncredit, 0) - Nvl(ndebit, 0)) else (NVl((select amount from ");
		bankOpenBalQuery.append(" cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, ");
		bankOpenBalQuery.append(" 'dd/MM/YYYY')), 0) - NVL(ncredit, 0) + Nvl(ndebit, 0)) end) OpeningBanace ");
		bankOpenBalQuery.append(" from (select sum(decode(? || 'C', info.accountno || info.vouchertype, nvl(");
		bankOpenBalQuery.append(" credit, 0), nvl(debit, 0))) nDebit, sum(decode(?|| 'C', info.accountno ||");
		bankOpenBalQuery.append(" info.vouchertype, nvl(debit, 0), nvl(credit, 0))) nCredit from ");
		bankOpenBalQuery.append(" Cb_voucher_details dt, (select keyno, info.accountno, vouchertype, ");
		bankOpenBalQuery.append(" voucher_dt, emp_party_code from Cb_VOUCHER_INFO info, cb_bankopeningbal_info");
		bankOpenBalQuery.append(" openbal where (info.accountno = ? or (info.emp_party_code = ? and ");
		bankOpenBalQuery.append(" info.vouchertype = 'C')) and voucher_dt is not null and info.voucher_dt <");
		bankOpenBalQuery.append(" to_date(?, 'dd/MM/YYYY') and info.accountno = openbal.accountno(+) and ");
		bankOpenBalQuery.append(" info.voucher_dt >= openbal.openeddate) info where dt.keyno = info.keyno))");
		queries.put("bankOpenBalQuery",bankOpenBalQuery);
		
		StringBuffer bankCloseBalQuery = new StringBuffer("select abs(OpeningBanace) OpeningBanace, (select ");
		bankCloseBalQuery.append(" bankname from CB_BANK_INFO where accountno = ?) bankname, case when OpeningBanace >0 then decode((select amountType from cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')+1), 'DR', 'Dr.', 'Cr.') else decode((select amountType from cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')+1), 'CR', 'Dr.', 'Dr.') end amountType from ( select (case when Nvl((select amountType from cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')+1), 'CR') = 'DR' then (NVl((select amount from cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')+1), 0) + NVL(ncredit, 0) - Nvl(ndebit, 0)) else (NVl((select amount from cb_bankopeningbal_info where accountno = ? and openeddate <= to_date(?, 'dd/MM/YYYY')+1), 0) - NVL(ncredit, 0) + Nvl(ndebit, 0)) end) OpeningBanace from (select sum(decode(? || 'C', info.accountno || info.vouchertype, nvl(credit, 0), nvl(debit, 0))) nDebit, sum(decode(?|| 'C', info.accountno || info.vouchertype, nvl(debit, 0), nvl(credit, 0))) nCredit from Cb_voucher_details dt, (select keyno, info.accountno, vouchertype, voucher_dt, emp_party_code from Cb_VOUCHER_INFO info, cb_bankopeningbal_info openbal where (info.accountno = ? or (info.emp_party_code = ? and info.vouchertype = 'C')) and voucher_dt is not null and info.voucher_dt < to_date(?, 'dd/MM/YYYY')+1 and info.accountno = openbal.accountno(+) and info.voucher_dt >= openbal.openeddate) info where dt.keyno = info.keyno))");
		queries.put("bankCloseBalQuery",bankCloseBalQuery);
		
		StringBuffer bankQuery = new StringBuffer("select BANKNAME,ACCOUNTNO from Cb_BANK_INFO ");
		queries.put("bankQuery", bankQuery);
		
		StringBuffer dailyStatement = new StringBuffer("select info.ACCOUNTNO,mapp.type,mapp.description, ");
		dailyStatement.append(" decode(mapp.type,'P',sum(DEBIT),'R', sum(CREDIT)) AMOUNT,mapp.code from ");
		dailyStatement.append(" cb_voucher_info info,cb_voucher_details details, cb_daily_statement_mapping ");
		dailyStatement.append(" mapp where details.keyno = info.keyno and APPROVAL = 'Y' and instr(ACCHEADS, ");
		dailyStatement.append(" accounthead) > 0 AND INFO.VOUCHER_DT = to_date(?, 'dd/MM/YYYY') group by ");
		dailyStatement.append(" mapp.description, info.ACCOUNTNO, mapp.code,mapp.type order by ACCOUNTNO, mapp.type, mapp.description");
		queries.put("dailyStatement", dailyStatement);
		
		StringBuffer SchedQuery = new StringBuffer("select type,Accountcode,trusttype,openBalcredit, ");
		SchedQuery.append(" openBaldebit,sum(credit)+Nvl((select case when sum(credit) - sum(debit)<0 then  sum(debit)-sum(credit) else 0 end  from cb_voucher_info info,cb_voucher_details details,cb_bank_info binfo where details.keyno = info.keyno and binfo.accountno = info.accountno and upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?, '', '01/jan/1000', ?) and to_date(decode(?, '', '01/jan/3000', ?),'dd/Mon/YYYY')-1 and upper(trim(binfo.trusttype)) = upper(trim(vouinfo.trusttype)) and   binfo.accountcode = vouinfo.accounthead group by info.accountno, binfo.accountcode, binfo.trusttype),0)-Nvl((select sum(credit) from cb_voucher_info info, cb_voucher_details  det,");
		SchedQuery.append(" cb_bank_info bank where vouchertype = 'C' and det.keyno = info.keyno and bank.accountno = info.emp_party_code and ");
		SchedQuery.append(" upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?, '', '01/jan/1000', ?) and ");
		SchedQuery.append(" to_date(decode(?, '', '01/jan/3000',?),'dd/Mon/YYYY')-1 and  bank.accountcode = vouinfo.accounthead),0) credit,sum(debit)+Nvl((select case when sum(credit) - sum(debit)>0 then  sum(credit) - sum(debit)  else 0 end from cb_voucher_info info,cb_voucher_details details,cb_bank_info binfo where details.keyno = info.keyno and binfo.accountno = info.accountno and upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?, '', '01/jan/1000', ?) and to_date(decode(?, '', '01/jan/3000', ?),'dd/Mon/YYYY')-1 and upper(trim(binfo.trusttype)) = upper(trim(vouinfo.trusttype)) and   binfo.accountcode = vouinfo.accounthead group by info.accountno, binfo.accountcode, binfo.trusttype),0)-Nvl((select sum(credit) from cb_voucher_info info, cb_voucher_details  det,");
		SchedQuery.append(" cb_bank_info bank where vouchertype = 'C' and det.keyno = info.keyno and bank.accountno = info.accountno and ");
		SchedQuery.append(" upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?, '', '01/jan/1000', ?) and ");
		SchedQuery.append(" to_date(decode(?, '', '01/jan/3000',?),'dd/Mon/YYYY')-1 and det.accounthead = vouinfo.accounthead),0) debit from (select ACCOUNTCODETYPE ");
		SchedQuery.append(" type,upper(dt.accounthead || ' ' || ainfo.particular) Accountcode,dt.accounthead accounthead,info.trusttype ");
		SchedQuery.append(" trusttype,getAccHeadOpenBal(dt.accounthead, info.trusttype, ?, 'C') openBalcredit,");
		SchedQuery.append(" getAccHeadOpenBal(dt.accounthead, info.trusttype, ?, 'D') openBaldebit,sum(credit)");
		SchedQuery.append("   credit, sum(debit) debit from Cb_VOUCHER_INFO info,Cb_voucher_details dt,");
		SchedQuery.append(" cb_accountcode_info ainfo,cb_accountcodetype_info acctype where acctype.code = ");
		SchedQuery.append(" ainfo.type and dt.keyno = info.keyno and ainfo.accounthead = dt.accounthead and ");
		SchedQuery.append(" upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between ");
		SchedQuery.append(" decode(?, '', '01/jan/1000', ?) and to_date(decode(?, '', '01/jan/3000', ?),'dd/Mon/YYYY')-1 group by ");
		SchedQuery.append(" dt.accounthead, info.trusttype,  ainfo.particular,ACCOUNTCODETYPE union all select");
		SchedQuery.append(" ACCOUNTCODETYPE type, upper(dt.accountcode || ' ' || ainfo.particular) ");
		SchedQuery.append(" Accountcode,dt.accountcode accounthead, info.trusttype trusttype,getAccHeadOpenBal(dt.accountcode,");
		SchedQuery.append(" info.trusttype, ?, 'C') openBalcredit,getAccHeadOpenBal(dt.accountcode,");
		SchedQuery.append(" info.trusttype, ?, 'D') openBaldebit,sum(credit) credit,sum(debit) debit from ");
		SchedQuery.append(" Cb_Journalvoucher info,Cb_Journalvoucher_Details dt, cb_accountcode_info ainfo, ");
		SchedQuery.append(" cb_accountcodetype_info acctype where acctype.code = ainfo.type  and dt.keyno = ");
		SchedQuery.append(" info.keyno and ainfo.accounthead = dt.accountcode and upper(trim(info.trusttype)) ");
		SchedQuery.append(" like upper(trim(?)) and info.voucher_dt between decode(?, '', '01/jan/1000', ?) ");
		SchedQuery.append(" and to_date(decode(?, '', '01/jan/3000', ?),'dd/Mon/YYYY')-1 group by dt.accountcode, info.trusttype, ");
		SchedQuery.append(" ainfo.particular,ACCOUNTCODETYPE union all select ACCOUNTCODETYPE type,upper(det.accounthead||' '||info.particular) Accountcode,det.accounthead accounthead,det.trusttype trusttype,getAccHeadOpenBal(det.accounthead,det.trusttype,?,'C') openBalcredit,    getAccHeadOpenBal(det.accounthead,det.trusttype,?,'D') openBaldebit,0 credit,0 debit from cb_accountcode_details det,cb_accountcode_info info,cb_accountcodetype_info acctype where info.accounthead = det.accounthead and upper(trim(det.trusttype)) like upper(trim(?)) and acctype.code = info.type and det.accounthead || ' ' || det.trusttype not in ( select dt.accountcode || ' ' || info.trusttype from Cb_Journalvoucher info,Cb_Journalvoucher_Details dt,cb_accountcode_info ainfo,cb_accountcodetype_info acctype where acctype.code = ainfo.type and dt.keyno = info.keyno and ainfo.accounthead = dt.accountcode and upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?,'','01/jan/1000',?) and to_date(decode(?,'','01/jan/3000',?),'dd/Mon/YYYY')-1 group by dt.accountcode,info.trusttype union select dt.accounthead || ' ' || info.trusttype from Cb_VOUCHER_INFO info,Cb_voucher_details dt,cb_accountcode_info ainfo,cb_accountcodetype_info acctype where acctype.code = ainfo.type and dt.keyno = info.keyno and ainfo.accounthead = dt.accounthead and upper(trim(info.trusttype)) like upper(trim(?)) and info.voucher_dt between decode(?,'','01/jan/1000',?) and to_date(decode(?,'','01/jan/3000',?),'dd/Mon/YYYY')-1");
		SchedQuery.append(" group by dt.accounthead,info.trusttype)) vouinfo where vouinfo.Accountcode in (select upper(accounthead||' '||particular) from cb_accountcode_info where instr((select ACCHEADSADD||','||ACCHEADSLESS from cb_scheduletype where name= ? ),accounthead)>0) group by type, Accountcode, trusttype, ");
		SchedQuery.append(" openBalcredit, openBaldebit,vouinfo.accounthead order by trusttype, type, Accountcode");
		queries.put("SchedQuery",SchedQuery);
		
		return queries.get(queryName).toString();
	}
	public List getRejectedLoans(VoucherInfo info)throws EPISException
	{
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		List rejectCaseArray = new ArrayList();
		VoucherInfo bean=null;
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			String rejectedCases="select info.emp_party_code pensionno, info.employeename employeename, info.details remarks from cb_voucher_info info where info.approval = 'R'";
			if(!info.getEmpPartyCode().equals(""))
				rejectedCases+=" and emp_party_code like '%"+info.getEmpPartyCode() + "%'";
			if(!info.getEmpPartyCode().equals(""))
				rejectedCases+=" and transid like '%"+info.getTransactionId() + "%'";
			if(!info.getPreparedDt().equals(""))
				rejectedCases+=" and preperation_dt<='"+info.getPreparedDt()+"";
			rs = stmt.executeQuery(rejectedCases);
			while(rs.next()){
				bean=new VoucherInfo();
				bean.setEmpPartyCode(StringUtility.checknull(rs.getString("pensionno")));
				bean.setEmployeeName(StringUtility.checknull(rs.getString("employeename")));
				bean.setDetails(StringUtility.checknull(rs.getString("remarks")));
				
				rejectCaseArray.add(bean);
			}			
		}catch(Exception e){
			log.printStackTrace(e);
			throw new EPISException(e.toString());
		}finally{
			DBUtility.closeConnection(rs,stmt,con);
		}		
		return rejectCaseArray;	
	}

	

	

	

}
