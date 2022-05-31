<%@ page import="com.epis.bean.cashbookDummy.BankBook"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.utilities.CurrencyFormat" %>
<%@ page import="com.epis.utilities.DBUtility" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":"); 
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String reportType = request.getParameter("reportType");
String reportbal = request.getParameter("reportbal").trim();
if(reportType.equals("html")) {
	response.setContentType("text/html");
}else{
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=BankBook.xls");
}
String basePath = basePathBuf.toString() + "PensionView/";
List bankbookList = (List) request.getAttribute("book");
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
DecimalFormat df = new DecimalFormat("#######.##");
String fromDate = request.getParameter("fromDate")==null?"":request.getParameter("fromDate").trim();
String toDate = request.getParameter("toDate")==null?"":request.getParameter("toDate").trim();
CurrencyFormat cf = new CurrencyFormat();
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
		<link rel="stylesheet" href="<%=basePathBuf%>css/aai.css" type="text/css" />
	</head>

	<body background="body">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<td class="tblabel" align="center" valign="middle" colspan=7>
								AIRPORTS AUTHORITY OF INDIA 
							</td>
						</tr>
						<tr>
							<td align="center" class="tblabel" colspan=7>
							<%=sql.getDescription("cb_bank_info","TRUSTTYPE","ACCOUNTCODE",request.getParameter("accountHead"),DBUtility.getConnection())+" TRUST"%></font>
							</td>
						</tr>
						<tr>
							<td align="center" class="tblabel" colspan=7>
								BANK BOOK 
							</td>
						</tr>
						<tr>
							<td align="center" class="tblabel" colspan=7>
							<%="("+sql.getDescription("cb_bank_info","BANKNAME||' - '||ACCOUNTNO","ACCOUNTCODE",request.getParameter("accountHead"),DBUtility.getConnection())+")"%></font>
							</td>
						</tr>
						<tr>
							<td align="center" class="tblabel" colspan=7>
								<%=fromDate.equals(toDate)?"For Date : "+fromDate:"From : "+fromDate+"   To : "+toDate   %>
							</td>
						</tr>
					</table>
				</td>
			</tr>		
			</thead>	
			<tr>
				<td width="100%">
					<table width="100%">
						<tr>
<%
int len = bankbookList.size();
for(int cnt=0;cnt<len;cnt++){
BankBook book = (BankBook)bankbookList.get(cnt);
double receipts = ("Cr.".equals(book.getAmountType())?book.getOpeningBalAmt():0.00);
double payments = ("Dr.".equals(book.getAmountType())?book.getOpeningBalAmt():0.00);

%>
						</tr>							
						<tr>
							<td>
								<br />
							</td>
						</tr>	
						<tr>

							<td>
								<table cellpadding="0" cellspacing="0" border="0" width="100%" class="border">
									<thead>
									<tr>
										<td class="label" align="center" >
											Voucher Date
										</td>
										<td class="label" align="center">
											Voucher NO
										</td>
										<td class="label" align="center" >
											CH.NO./B.E.
										</td>
										<td class="label" align="center">
											A/C CODE								
										</td>
										<td class="label" align="center">
											Party Name
										</td>							
										<td class="label" align="center">
											Amount
										</td>
										<td class="label" align="center">
											DR.
										</td>
										<td class="label" align="center">
											CR.
										</td>
									</tr>
									</thead>
									<tr>

										<td class="Data" align="center">

										</td>
										<td class="Data" align="center"></td>
										<td class="Data" align="center"></td>										
										<td class="Data" align="center">
											OPENING BALANCE
										</td>
										<td class="Data" align="center"></td>		
										<td class="label" align="center">
										</td>
										<td class="label" align="right" nowrap>
											<%=cf.getDecimalCurrency(payments)%>
										</td>
										<td class="label" align="right" nowrap>
											<%=cf.getDecimalCurrency(receipts)%>
										</td>
									</tr>
									<%
List bookList = book.getBankBookList();
int size = bookList.size();
double payments1 =  0.00;
double receipts1 =  0.00;
double creditOB = receipts;
double debitOB = payments;
String date = "";
String month = "";
	String year = "";
HashMap hm = new HashMap();
String[] det = null;
for(int i=0;i<size;i++){
	book = (BankBook)bookList.get(i);
	if(hm.containsKey(book.getVoucherno())){	
		det = (String[])hm.get(book.getVoucherno());	
		int recs = Integer.parseInt(det[0]);
		double amount = Double.parseDouble(det[1]);
		amount += book.getVoucherno().startsWith("C")?(book.getPayments() - book.getReceipts()):(book.getReceipts() - book.getPayments());
		det[0] = Integer.toString(++recs);
		det[1] = Double.toString(amount);		
	}else {		
		det = new String[2];
		det[0] = "1";
		det[1] = Double.toString(book.getVoucherno().startsWith("C")?(book.getPayments() - book.getReceipts()):(book.getReceipts() - book.getPayments()));		
	}
	hm.put(book.getVoucherno(),det);
}
int recCount = 1;
String payRecep="";
String details = "";
String vDetails = "";
for(int i=0;i<size;i++){	
	String credit = "";
	String debit = "";
	book = (BankBook)bookList.get(i);	
	det = (String[])hm.get(book.getVoucherno());	
	int recs = Integer.parseInt(det[0]);
	double amount = Double.parseDouble(det[1]);
	payRecep = "";	
	details = "";
	vDetails = "";
	if(recCount == 1){
		if(amount > 0){
			credit = cf.getDecimalCurrency(amount);
			debit = "-";
		}else {
			debit = cf.getDecimalCurrency(Math.abs(amount));
			credit = "-";
		}
		vDetails ="<td class='Data'  rowspan='"+recs+"' width='70px'><font size=1>"+book.getVoucherDate()+"</td><td class='Data'  rowspan='"+recs+"' nowrap ><font size=1>"+book.getVoucherno()+"</td><td class='Data'  rowspan='"+recs+"' nowrap ><font size=1>"+book.getChequeNo()+"</td>";
		details = "<td class='Data'  rowspan='"+recs+"' width='1500px'><font size=1>"+book.getPartyName()+"<BR><font size=1><I>("+book.getDescription()+")</td>";
		payRecep = "<td class='Data' align='right' nowrap rowspan='"+recs+"'><font size=1>"+credit+"</td><td class='Data' align='right' rowspan='"+recs+"' nowrap><font size=1>"+debit+"</td>";
	}
	if(recs==recCount){
		recCount=1;
	}else {
		recCount++;	
	}
	if(i==0){
		date = book.getVoucherDate();
		month = book.getVoucherDate().substring(3,6);	
	year = book.getVoucherDate().substring(7);	
	}

if(i!=0){			
	if(("D".equals(reportbal) && !date.equals(book.getVoucherDate()))||("M".equals(reportbal) && !month.equals(book.getVoucherDate().substring(3,6)))||("Y".equals(reportbal) && !year.equals(book.getVoucherDate().substring(7))) ){
		date = book.getVoucherDate();		
		month = book.getVoucherDate().substring(3,6);	
	year = book.getVoucherDate().substring(7);	
%>
									<tr>
										<td class="label" align="right" colspan=5>
											SUB TOTAL
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right">
											<%=cf.getDecimalCurrency(receipts1)%>
										</td>
										<td class="label" align="right">
											<%=cf.getDecimalCurrency(payments1)%>
										</td>
									</tr>
									<tr>
										<td class="label" align="right" colspan="5">
											Closing balance(<%=(payments<receipts?" Cr. ":" Dr. ")%>)
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right" >
											<%=payments<receipts?cf.getDecimalCurrency(Math.abs(payments-receipts)):""%>
										</td>
										<td class="label" align="right" >
											<%=payments>receipts?cf.getDecimalCurrency(Math.abs(payments-receipts)):""%>
										</td>
									</tr>	
									<tr>
										<td class="label" align="right" colspan="5">
											Opening balance
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right" >
											<%=payments>receipts?cf.getDecimalCurrency(Math.abs(payments-receipts)):""%>
										</td>
										<td class="label" align="right" >
											<%=payments<receipts?cf.getDecimalCurrency(Math.abs(payments-receipts)):""%>
										</td>
									</tr>															
									
<%
		creditOB =payments<receipts?(Math.abs(payments-receipts)):0.00;
		debitOB = payments>receipts?(Math.abs(payments-receipts)):0.00;
		payments1 = 0.00;
		receipts1 = 0.00;
		
	}
	}
	payments += book.getVoucherno().startsWith("C")?book.getPayments():book.getReceipts(); 
	receipts += book.getVoucherno().startsWith("C")?book.getReceipts():book.getPayments();
	if(book.getVoucherno().startsWith("R")){
		receipts1 += book.getReceipts() - book.getPayments();
	}else if(book.getVoucherno().startsWith("P")){
		payments1 += book.getPayments() - book.getReceipts();
	}else if(book.getVoucherno().startsWith("C") && book.getPayments()!=0){
		
		receipts1 += book.getPayments() - book.getReceipts();
	}
	else if(book.getVoucherno().startsWith("C") && book.getReceipts()!=0){
		
		payments1 +=   book.getReceipts()-book.getPayments();
	}
	String amountDt = cf.getDecimalCurrency(book.getVoucherno().startsWith("C")?book.getReceipts():book.getPayments())+" CR";
	if("0.00 CR".equals(amountDt)){
		amountDt = cf.getDecimalCurrency(book.getVoucherno().startsWith("C")?book.getPayments():book.getReceipts())+" DR";
	}
%>
									<tr>
										<%=vDetails%>
										<td class="Data" nowrap>
											<font size=1><%=(book.getAccountHead()+" "+book.getParticular()).trim()%><%=details%>										
										<td class="Data" align="right" nowrap >
											<font size=1><%=recs>1?amountDt:""%>											
										</td>
										<%=payRecep%>
									</tr>	
										
<%
}
if(size==0){
	%>
									<tr>
										<td colspan="9" class="label" align="center">
											No Transactions!
										</td>
									</tr>
									<%
}
%>
									<tr>
										<td class="label" align="right" colspan=5>
											SUB TOTAL
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right">
											<%=cf.getDecimalCurrency(receipts1)%>
										</td>
										<td class="label" align="right">
											<%=cf.getDecimalCurrency(payments1)%>
										</td>
									</tr>
<%
//Grand Total Calculation

double grandTotDebit  = debitOB+receipts1;
double grandTotCredit = creditOB+payments1;
double debitCB = 0.00;
double creditCB = 0.00; 
if(grandTotDebit>grandTotCredit){
	grandTotCredit = grandTotDebit;
	creditCB = grandTotDebit-(payments1+creditOB);
}else {
	grandTotDebit = grandTotCredit;
	debitCB = grandTotCredit -(receipts1+debitOB);
}
%>											
									<tr>
										<td class="label" align="right" colspan="5">
											Closing balance(<%=(debitCB>creditCB?" Cr. ":" Dr. ")%>)
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right" >
											<%=debitCB==0.00?"":cf.getDecimalCurrency(debitCB)%>
										</td>
										<td class="label" align="right" >
											<%=creditCB==0.00?"":cf.getDecimalCurrency(creditCB)%>
										</td>
									</tr>								
									<tr>
										<td class="label" align="right" colspan="5">
											Grand Total
										</td>
										<td class="label" align="right" >
											
										</td>
										<td class="label" align="right" >
											<%=cf.getDecimalCurrency(grandTotDebit)%>
										</td>
										<td class="label" align="right" >
											<%=cf.getDecimalCurrency(grandTotCredit)%>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="Data" align="center">
								<br />
								<br />
							</td>
						</tr>
<%
}
%>
						<tr>
							<td align="center">
								<table width="75%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<br />
											<br />
											<br />
										</td>
									</tr>
									<tr>
										<td class="Data" align="center" width='33%'>
											Certified
										</td>
										<td class="Data" align="center" width='33%'>
											Cashier
										</td>
										<td class="Data" align="center" width='33%'>
											Manager(Finance)
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr></tr>

					</table>

				</td>
			</tr>
		</table>
	</body>
</html>
