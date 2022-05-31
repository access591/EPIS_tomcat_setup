

<%@ page import="com.epis.bean.cashbookDummy.BankBook"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.bean.cashbookDummy.AccountingCodeInfo" %>
<%@ page import="com.epis.utilities.CurrencyFormat" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="java.text.DecimalFormat" %>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=GeneralLedger.xls");			
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
String basePath = basePathBuf.toString() + "PensionView/";
boolean narration = "Y".equals(request.getParameter("reportContent"))?false:true;
String fromDate = request.getParameter("fromDate")==null?"":request.getParameter("fromDate").trim();
String toDate = request.getParameter("toDate")==null?"":request.getParameter("toDate").trim();
String displayDate = "";
DecimalFormat df = new DecimalFormat("#######0.00");
if(!"".equals(fromDate) && fromDate.equals(toDate)){
	displayDate = "For Date : "+fromDate;
}else {
	if(!"".equals(fromDate)){
	displayDate = "FROM : "+fromDate;
	}
	if(!"".equals(toDate)){
		displayDate += "  TO : "+toDate;
	}
}
Map ledger = (LinkedHashMap) request.getAttribute("ledger");
Map accounts = (LinkedHashMap) request.getAttribute("accounts");
String reportbal = request.getParameter("reportbal").trim();
CurrencyFormat cf = new CurrencyFormat();
Map trusts  = null;
SQLHelper sql = new SQLHelper();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<style TYPE='text/css'> .breakhere{ page-break-after: always; }</style>
	</head>

	<body background="body">
		
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
<%
	Set s = ledger.keySet();
	Iterator iter = s.iterator();
	boolean bool = true;
	while(iter.hasNext()){
		String trust = (String)iter.next();
		Map trustBased = (LinkedHashMap)ledger.get(trust);
		Set ledgerSet = trustBased.keySet();
		Iterator ledgerIter = ledgerSet.iterator();
		while(ledgerIter.hasNext()){
			String accHead = (String)ledgerIter.next();
			List ledgerAcc = (ArrayList)trustBased.get(accHead);
			if(accounts.containsKey(accHead)){
				trusts  = (LinkedHashMap)accounts.get(accHead);
				if(trusts.containsKey(trust)){
					trusts.remove(trust);
				}
				if(trusts.size()==0 ){
					accounts.remove(accHead);
				}
			}
%>			
				
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<table width=100%>
						<thead>
							<tr>
								<td align=center>
									<table>
										<tr>
											<td class="tblabel" align="center" valign="middle" colspan=8>
												AIRPORTS AUTHORITY OF INDIA 
											</td>
											<tr>
											<td align="center" class="tblabel" colspan=8>
											<%=trust+" TRUST"%>
											</td>
										</tr>
										<tr>
											<td align="center" class="tblabel" colspan=8>
												LEDGER ACCOUNT
											</td>
										</tr>
										<tr>
											<td align="center" class="tblabel" colspan=8>
											ACCOUNT CODE : <%=accHead%>
											</td>
										</tr>						
										<tr>
											<td class="tbb" align="center" valign="middle" colspan=8>
												<%=displayDate%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</thead>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
<% if(bool){%>						
						<tr>
							<td align="right" class="tblabel">
								Date of Printing : <%=sdf.format(new Date())%>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
<%bool=false;}%>						
						<tr>				
							<td width=90% >
								<table width=100% border="1" align="center" cellpadding="0" cellspacing="0" valign="middle"  style='border-color: black; border-width: 1px'>
									<thead>
									<tr >
										<td  class=label >
											Date
										</td>
										<td class=label >
											Voucher No.
										</td>
										<td class=label >
											Voucher Type
										</td>											
										<td class=label  >
											Particulars
										</td>
										<td class=label >
											CH. NO./B.E.
										</td>		
										<td class=label  >
											Party Name
										</td>							
										<td class=label >
											Debit
										</td>
										<td class=label >
											Credit
										</td>
									</tr>
									</thead>
<%
	int size = ledgerAcc.size(); 
	String date = "";
	String month = "";
	String year = "";
	String fyear="";
	double credit = 0.00;
	double debit = 0.00;
	double creditTot = 0.00;
	double debitTot = 0.00;
	for(int cnt=0;cnt<size;cnt++){
		BankBook ledgerAccount = (BankBook)ledgerAcc.get(cnt);			
			
if(cnt!=0){			
	if(("D".equals(reportbal) && !date.equals(ledgerAccount.getVoucherDate()))||("M".equals(reportbal) && !month.equals(ledgerAccount.getVoucherDate().substring(3,6)))||("Y".equals(reportbal) && !fyear.equals(ledgerAccount.getFinYear())) ){
%>			
									<tr>
										<td  colspan=6 align="right" class=Data>
											Sub Total
										</td>
										<td   align="right" class=Data>
											<%=df.format(debitTot)%>
										</td>
										<td   align="right" class=Data>
											<%=df.format(creditTot)%>
										</td>
									</tr>
									<tr>
										<td class=label colspan=6 align="right">
											<B>To  Closing Balance (<%=debit < credit ?" CR.":" DR."%>)
										</td>
										<td class=label  align="right" >
											<B><%=debit < credit ?df.format(credit):""%>
										</td>
										<td class=label  align="right" >
											<B><%=debit > credit ?df.format(debit):""%>
										</td>
									</tr>	
<%
	creditTot = 0.00;
	debitTot = 0.00;
	}
}
	debitTot += ledgerAccount.getPayments();
		creditTot += ledgerAccount.getReceipts();		
if(cnt!=0){
		if(("D".equals(reportbal) && !date.equals(ledgerAccount.getVoucherDate()))||("M".equals(reportbal) && !month.equals(ledgerAccount.getVoucherDate().substring(3,6)))||("Y".equals(reportbal) && !fyear.equals(ledgerAccount.getFinYear())) ){
%>			
									<tr>
										
									</tr>
									<tr>
										<td class=label colspan=6 align="right">
											<B>  By  Opening Balance
										</td>
										<td class=label  align="right">
											<B><%=df.format(debit)%>
										</td>
										<td class=label  align="right">
											<B><%=df.format(credit)%>
										</td>
									</tr>		
<%}
}else if(cnt == 0){
	debit = ledgerAccount.getOpeningBalAmt()<0?Math.abs(ledgerAccount.getOpeningBalAmt()):0.00;
	credit = ledgerAccount.getOpeningBalAmt()>0?Math.abs(ledgerAccount.getOpeningBalAmt()):0.00;
	creditTot += credit;
	debitTot += debit;
%>
									<tr>
										<td class=label colspan=6 align="right">
											<B>By  Opening Balance 
										</td>
										<td class=label  align="right">
											<B><%=df.format(debit)%>
										</td>
										<td class=label  align="right">
											<B><%=df.format(credit)%>
										</td>
									</tr>							
<%
}
%>	
									
									<tr >
										<td class=Data  align=left>
											<%=ledgerAccount.getVoucherDate()%>
										</td>
										<td class=Data   align=left>
											<%=ledgerAccount.getVoucherno().toUpperCase()%>
										</td>
										<td class=Data  >
											<%=ledgerAccount.getVoucherType()%>
										</td>
										<td  class=Data   align=left>
											<font size=2><B><%=ledgerAccount.getBankName().toUpperCase()%></B>
<%if(narration){%>						<BR><I><%=sql.wrapTextArea(ledgerAccount.getDescription()==null?"":ledgerAccount.getDescription(),20)%></font>
<%}%>										</td>	
										<td class=Data align=left>
											<%=sql.wrapTextArea(ledgerAccount.getChequeNo(),10)%>
										</td>
										<td class=Data align=left >
											<%=sql.wrapTextArea(ledgerAccount.getPartyName(),15)%>
										</td>																			
										<td class=Data  align="right">
											<%=df.format(ledgerAccount.getPayments())%>
										</td>
										<td class=Data  align="right">
											<%=df.format(ledgerAccount.getReceipts())%>
										</td>
									</tr>	
				
<%
debit += ledgerAccount.getPayments();
credit += ledgerAccount.getReceipts();
double creditAmt = credit-debit > 0 ? credit-debit:0.00;
debit = credit-debit > 0 ? 0.00:debit-credit;
credit = creditAmt;

	date = ledgerAccount.getVoucherDate();	
		month = ledgerAccount.getVoucherDate().substring(3,6);	
	year = ledgerAccount.getVoucherDate().substring(7);	
	fyear=ledgerAccount.getFinYear();	
} 
%>
									<tr>
										<td  colspan=6 align="right" class=Data>
											Sub Total
										</td>
										<td   align="right" class=Data>
											<%=df.format(debitTot)%>
										</td>
										<td   align="right" class=Data>
											<%=df.format(creditTot)%>
										</td>
									</tr>
									<tr>
										<td class=label colspan=6 align="right">
											<B>To  Closing Balance (<%=debit < credit ?" CR.":" DR."%>)
										</td>
										<td class=label  align="right" >
											<B><%=debit < credit ?df.format(credit):""%>
										</td>
										<td class=label  align="right" >
											<B><%=debit > credit ?df.format(debit):""%>
										</td>
									</tr>	
									<tr>
									<td class=label colspan=6 align="right">
											<B>Total
										</td>
										<td class=label  align="right" >
											<B><%=debit > credit ?df.format(debit):df.format(credit)%>
										</td>
										<td class=label  align="right" >
											<B><%=debit > credit ?df.format(debit):df.format(credit)%>
										</td>
									</tr>		
<%
if(size==0){
%>
									<tr>
										<td class="ScreenSubHeading" colspan=7>
											No Record Found!
										</td>
									</tr>
<%
}

%>	
								</table>					
							</td>
						</tr>
					</table>					
				</td>
			</tr>
			<tr><td><P CLASS='breakhere'></td></tr>
	<%
		}
	}
s = accounts.keySet();
	iter = s.iterator();
	while(iter.hasNext()){
		String accHead = (String)iter.next();
		Map trustBased = (LinkedHashMap)accounts.get(accHead);
		Set accSet = trustBased.keySet();
		Iterator accIter = accSet.iterator();
		while(accIter.hasNext()){
			String trust = (String)accIter.next();
			AccountingCodeInfo accInfo = (AccountingCodeInfo)trustBased.get(trust);
%>			
				
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<table width=100%>
						<thead>
							<tr>
								<td align=center>
									<table>
										<tr>
											<td class="tblabel" align="center" valign="middle" colspan=8>
												AIRPORTS AUTHORITY OF INDIA 
											</td>
											<tr>
											<td align="center" class="tblabel" colspan=8>
											<%=trust+" TRUST"%>
											</td>
										</tr>
										<tr>
											<td align="center" class="tblabel" colspan=8>
												LEDGER ACCOUNT
											</td>
										</tr>
										<tr>
											<td align="center" class="tblabel" colspan=8>
											ACCOUNT CODE : <%=accInfo.getAccountHead()%>
											</td>
										</tr>						
										<tr>
											<td class="tbb" align="center" valign="middle" colspan=8>
												<%=displayDate%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</thead>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
				
						<tr>
							<td align="right" class="tblabel">
								Date of Printing : <%=sdf.format(new Date())%>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
			
						<tr>				
							<td width=90% >
								<table width=100% border="1" align="center" cellpadding="0" cellspacing="0" valign="middle" class=border>
									<thead>
									<tr >
										<td  class=label width='7%'>
											Date
										</td>
										<td class=label width='10%'>
											Voucher No.
										</td>
										<td class=label width='8%'>
											Voucher Type
										</td>											
										<td class=label width='59%'>
											Particulars
										</td>
										<td class=label width='8%'>
											CH. NO./B.E.
										</td>		
										<td class=label width='8%'>
											Party Name
										</td>							
										<td class=label width='8%'>
											Debit
										</td>
										<td class=label width='8%'>
											Credit
										</td>
									</tr>
									</thead>	
									<tr>
										<td class=label colspan=6 align="right">
											<B>  By  Opening Balance
										</td>
										<td class=label  align="right">
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())<0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
										<td class=label  align="right">
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())>0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
									</tr>
									<tr>
										<td class="ScreenSubHeading" colspan=7>
											No Record Found!
										</td>
									</tr>		
									<tr>
										<td  colspan=6 align="right" class=Data>
											Sub Total
										</td>
										<td   align="right" class=Data>
											<%=df.format(Double.parseDouble(accInfo.getAmount())<0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
										<td   align="right" class=Data>
											<%=df.format(Double.parseDouble(accInfo.getAmount())>0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
									</tr>
									<tr>
										<td class=label colspan=6 align="right">
											<B>To  Closing Balance (<%=Double.parseDouble(accInfo.getAmount())<0?"Dr.":"Cr."%>)
										</td>
										<td class=label  align="right" >
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())>0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
										<td class=label  align="right" >
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())<0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
									</tr>
									<tr>
									<td class=label colspan=6 align="right">
											<B> Total
										</td>
										<td class=label  align="right" >
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())<0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
										<td class=label  align="right" >
											<B><%=df.format(Double.parseDouble(accInfo.getAmount())>0?Math.abs(Double.parseDouble(accInfo.getAmount())):0.00)%>
										</td>
									</tr>			
								</table>					
							</td>
						</tr>
					</table>					
				</td>
			</tr>
			<tr><td><P CLASS='breakhere'></td></tr>
	<%
		}
	}
%>										
		</table>
	</body>
</html>
