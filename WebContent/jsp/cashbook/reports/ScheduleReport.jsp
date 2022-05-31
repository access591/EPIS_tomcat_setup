<%@ page import="com.epis.bean.cashbookDummy.BankBook"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.epis.utilities.CurrencyFormat" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.utilities.DBUtility" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
String fromDate = request.getParameter("fromDate")==null?"":request.getParameter("fromDate").trim();
String toDate = request.getParameter("toDate")==null?"":request.getParameter("toDate").trim();
String displayDate = "";
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
if(!"".equals(toDate)){
	displayDate = "As on "+toDate;
}
if(!"".equals(fromDate) && "".equals(toDate)){
	displayDate = "FROM : "+fromDate;	
}
Map trialBal = (HashMap) session.getAttribute("trailBalance");

CurrencyFormat cf = new CurrencyFormat();
String trust = request.getParameter("trustType");
SQLHelper sql = new SQLHelper();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="/css/aai.css" type="text/css" />
		<link rel="stylesheet" href="/css/epis.css" type="text/css" />	
	</head>

	<body background="body">
		
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		
			<tr>
				<td>
					<table width=100% >	
						<thead>
						<tr>
							<td align=center>
								<table cellspacing="4">
									<tr>
										<td>
											<table border="0" cellpadding="3" cellspacing="0" align="center" >
												<tr>
													<td rowspan="4">
														<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
													</td>
													<td class="tblabel" align="center" valign="middle" colspan="7">
														<bean:message key="com.aai" bundle="common" />
													</td>
												</tr>
												<tr>
													<td align="center" class="tblabel" colspan="7">
														<%=trust+" "%>TRUST <bean:message key="ie.address" bundle="common" />
													</td>
												</tr>
												<tr>
													<td align="center" class="tblabel" colspan=7>
													SCHEDULE - <%=request.getParameter("scheduleType")+"  "+sql.getDescription("cb_scheduletype","upper(description)","name",request.getParameter("scheduleType"),DBUtility.getConnection())%> 
			
													</td>
												</tr>
												<tr>
													<td align="center" class="tblabel" colspan="7">														
														 AS ON <%=CommonUtil.getFullNameofMonth(request.getParameter("tomonth"))%> 
													</td>
												</tr>
											</table>
										</td>
									</tr>															
								</table>
							</td>
						</tr>
						</thead>
					
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
							<td width=90% valign="middle">
								<table width=100% border="0" align="center" cellpadding="0" cellspacing="0"  class="border">
									<thead>
									<tr >
										<td class=label  rowspan=2 width=40%>
											Particulars
										</td>									
										<td class=label  rowspan=2 align="right" width=15%>
											Opening Balance
										</td>
										<td class=label colspan=2 align="center" width=30%>
											Transactions
										</td>
										<td class=label  rowspan=2 align="right" width=15%>
											Closing Balance
										</td>					
									</tr>
									<tr >
										<td class=label  width=15% align="right">
											Debit
										</td>
										<td class=label width=15% align="right">
											Credit
										</td>													
									</tr>
								</thead>

<%	
	double openBalCredit = 0.00;
	double openBalDebit = 0.00;
	double closeBalCredit = 0.00;
	double closeBalDebit = 0.00;
	double totOpenCredit = 0.00;
	double totOpenDebit = 0.00;
	double totCredit = 0.00;
	double totDebit = 0.00;
	double totCloseCredit = 0.00;
	double totCloseDebit = 0.00;
	String openingBal = "";
	String closingBal = "";	
	
	Map trustBased = (HashMap)trialBal.get(trust);
	Set trailBalSet = trustBased.keySet();
	Iterator trailBalIter = trailBalSet.iterator();
	while(trailBalIter.hasNext()){
		String accHeadtype = (String)trailBalIter.next();
		List trialBalAcc = (ArrayList)trustBased.get(accHeadtype);
	int size = trialBalAcc.size(); 
	for(int cnt=0;cnt<size;cnt++){
		BankBook trialBalAccount = (BankBook)trialBalAcc.get(cnt);	
		openBalCredit = trialBalAccount.getOpeningBalAmt() > trialBalAccount.getOpeningBalAmtDebit() ? trialBalAccount.getOpeningBalAmt() - trialBalAccount.getOpeningBalAmtDebit():0.00;
		openBalDebit = trialBalAccount.getOpeningBalAmt() < trialBalAccount.getOpeningBalAmtDebit() ? trialBalAccount.getOpeningBalAmtDebit() - trialBalAccount.getOpeningBalAmt():0.00;
		openingBal = cf.getDecimalCurrency(Math.abs(openBalCredit-openBalDebit))+(openBalCredit > openBalDebit?" Cr.":openBalCredit < openBalDebit?" Dr.":" ");
		closeBalDebit = openBalDebit+trialBalAccount.getPayments();
		closeBalCredit = openBalCredit+trialBalAccount.getReceipts();
		double debit = closeBalDebit > closeBalCredit ? closeBalDebit - closeBalCredit:0.00;
		closeBalCredit = closeBalDebit < closeBalCredit ?  closeBalCredit - closeBalDebit:0.00;
		closeBalDebit = debit;
		closingBal = cf.getDecimalCurrency(Math.abs(closeBalCredit-closeBalDebit))+(closeBalCredit > closeBalDebit?" Cr.":closeBalCredit < closeBalDebit?" Dr.":" ");
		
		totOpenCredit += openBalCredit;
		totOpenDebit += openBalDebit;
		totCredit += trialBalAccount.getReceipts();
		totDebit += trialBalAccount.getPayments();
		totCloseCredit += closeBalCredit;
		totCloseDebit += closeBalDebit;			
%>
				<tr >
					<td class=data  width=40%>
						<font size=2 style="font-family: Times, serif " ><%=trialBalAccount.getAccountHead()%>
					</td>						
					<td class=data nowrap  align="right" width=15%>
						<font size=2 ><%=openingBal%>
					</td>
					<td class=data nowrap  align="right" width=15%>
						<font size=2 ><%=cf.getDecimalCurrency(trialBalAccount.getPayments())%>
					</td>
					<td class=data nowrap align="right" width=15%>
						<font size=2 ><%=cf.getDecimalCurrency(trialBalAccount.getReceipts())%>
					</td>
					<td class=data nowrap  align="right" width=15%>
						<font size=2 ><%=closingBal%>
					</td>																		
				</tr>						
<%		
	} 
	openingBal = cf.getDecimalCurrency(Math.abs(totOpenCredit-totOpenDebit))+(totOpenCredit > totOpenDebit?" Cr.":totOpenCredit < totOpenDebit?" Dr.":" ");
	closingBal = cf.getDecimalCurrency(Math.abs(totCloseCredit-totCloseDebit))+(totCloseCredit > totCloseDebit?" Cr.":totCloseCredit < totCloseDebit?" Dr.":" ");
		}
%>				
				<tr >
					<td class=data align="right"  width=40% colspan="4">
						Total
					</td>						
					</td>
					<td class=data nowrap  align="right" width=15%>
						<font size=2 ><%=closingBal%>
					</td>																		
				</tr>								
		</table>
	</body>
</html>