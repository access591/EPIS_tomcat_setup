
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
<%@ page import="java.text.DecimalFormat" %>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=TrialBalance.xls");			
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
DecimalFormat df = new DecimalFormat("#######0.00");
String basePath = basePathBuf.toString() + "PensionView/";
String fromDate = request.getParameter("fromDate")==null?"":request.getParameter("fromDate").trim();
String toDate = request.getParameter("toDate")==null?"":request.getParameter("toDate").trim();
String displayDate = "";
if(!"".equals(toDate)){
	displayDate = "As on "+toDate;
}
if(!"".equals(fromDate) && "".equals(toDate)){
	displayDate = "FROM : "+fromDate;	
}

int reportType = Integer.parseInt(request.getParameter("reportType"));
Map trialBal = (HashMap) request.getAttribute("trailBalance");
CurrencyFormat cf = new CurrencyFormat();
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
	String grouping = request.getParameter("grouping");
	boolean bool =true;
	Set s = trialBal.keySet();
	Iterator iter = s.iterator();
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
	while(iter.hasNext()){
		String trust = (String)iter.next();
		Map trustBased = (HashMap)trialBal.get(trust);
		Set trailBalSet = trustBased.keySet();
		Iterator trailBalIter = trailBalSet.iterator();
		boolean trustchange = true;
		while(trailBalIter.hasNext()){
			String accHeadtype = (String)trailBalIter.next();
			List trialBalAcc = (ArrayList)trustBased.get(accHeadtype);
if(trustchange){			
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
										<td class="tblabel" align="center" valign="middle" colspan=7>
											AIRPORTS AUTHORITY OF INDIA 
										</td>
									</tr>
									<tr>
										<td align="center" class="tblabel" colspan=7>
										<%=trust+" "%>TRUST
										</td>
									</tr>
									<tr>
										<td align="center" class="tblabel" colspan=7>
											Trial Balance
										</td>
									</tr>
<%
if(!"Y".equals(grouping)){%>									
									<tr>
										<td align="center" class="tblabel" colspan=7>
										<%="--".equals(accHeadtype)?"":("Accounting Code Type :"+accHeadtype)%>
										</td>
									</tr>
<%
}%>									
									<tr>
										<td align="center" class="tblabel" colspan=7>
											<%=displayDate%>
										</td>
									</tr>									
								</table>
							</td>
						</tr>
						</thead>
<%if(bool){%>						
						<tr>
							<td align="right" class="tblabel">
								Date of Printing : <%=sdf.format(new Date())%>
							</td>
						</tr>
<%bool=false;}%>	
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>				
							<td width=90% >
								<table width=100% border="1" align="center" cellpadding="0" cellspacing="0" valign="middle" style='border-color: black; border-width: 1px'>
									<thead>
									<tr >
										<td class=label  rowspan=2 width=40% colspan=3>
											Particulars
										</td>
									<%
									if(reportType == 1){%>
										<td class=label colspan=2 align="center" width=30%>
											Opening Balance
										</td>
									<%
									}
									if(reportType == 3){ %>
										<td class=label  rowspan=2 align="right" width=15%>
											Opening Balance
										</td>
										<td class=label colspan=2 align="center" width=30%>
											Transactions
										</td>
										<td class=label  rowspan=2 align="right" width=15%>
											Closing Balance
										</td>
									<%
									}
									if(reportType == 1 || reportType == 2){%>
										<td class=label colspan=2 align="center" width=30% >
											Closing Balance
										</td>	
									<%
									}
									%>									
									</tr>
									<tr >
										<td class=label  width=15% align="right">
											Debit
										</td>
										<td class=label width=15% align="right">
											Credit
										</td>
								<%if(reportType == 1){%>
										<td class=label  width=15% align="right">
											Debit
										</td>									
										<td class=label width=15% align="right">
											Credit
										</td>	
									<%}%>					
									</tr>
									</thead>
<%
	}
	int size = trialBalAcc.size(); 
if(trustchange){	
	openBalCredit = 0.00;
	openBalDebit = 0.00;
	closeBalCredit = 0.00;
	closeBalDebit = 0.00;
	totOpenCredit = 0.00;
	totOpenDebit = 0.00;
	totCredit = 0.00;
	totDebit = 0.00;
	totCloseCredit = 0.00;
	totCloseDebit = 0.00;
	closingBal = "";
	openingBal = "";
	
}
if("Y".equals(grouping))
	trustchange = false;
	for(int cnt=0;cnt<size;cnt++){
		BankBook trialBalAccount = (BankBook)trialBalAcc.get(cnt);	
		openBalCredit = trialBalAccount.getOpeningBalAmt() > trialBalAccount.getOpeningBalAmtDebit() ? trialBalAccount.getOpeningBalAmt() - trialBalAccount.getOpeningBalAmtDebit():0.00;
		openBalDebit = trialBalAccount.getOpeningBalAmt() < trialBalAccount.getOpeningBalAmtDebit() ? trialBalAccount.getOpeningBalAmtDebit() - trialBalAccount.getOpeningBalAmt():0.00;
		openingBal = df.format(Math.abs(openBalCredit-openBalDebit))+(openBalCredit > openBalDebit?" Cr.":openBalCredit < openBalDebit?" Dr.":" ");
		closeBalDebit = openBalDebit+trialBalAccount.getPayments();
		closeBalCredit = openBalCredit+trialBalAccount.getReceipts();
		double debit = closeBalDebit > closeBalCredit ? closeBalDebit - closeBalCredit:0.00;
		closeBalCredit = closeBalDebit < closeBalCredit ?  closeBalCredit - closeBalDebit:0.00;
		closeBalDebit = debit;
		closingBal = df.format(Math.abs(closeBalCredit-closeBalDebit))+(closeBalCredit > closeBalDebit?" Cr.":closeBalCredit < closeBalDebit?" Dr.":" ");
		
		totOpenCredit += openBalCredit;
		totOpenDebit += openBalDebit;
		totCredit += trialBalAccount.getReceipts();
		totDebit += trialBalAccount.getPayments();
		totCloseCredit += closeBalCredit;
		totCloseDebit += closeBalDebit;		
		if(openBalDebit != 0.00 || openBalCredit != 0.00 || trialBalAccount.getPayments() != 0.00 ||  trialBalAccount.getReceipts() != 0.00){
%>
									<tr >
										<td class=data  width=40% colspan=3 align=left>
											<font size=2 style="font-family: Times, serif " ><%=trialBalAccount.getAccountHead()%>
										</td>										
									<%if(reportType == 1){%>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(openBalDebit)%>
										</td>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(openBalCredit)%>
										</td>
									<%} if(reportType == 3){%>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=openingBal%>
										</td>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(trialBalAccount.getPayments())%>
										</td>
										<td class=data nowrap align="right" width=15%>
											<font size=2 ><%=df.format(trialBalAccount.getReceipts())%>
										</td>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=closingBal%>
										</td>
									<%}if(reportType == 1 || reportType == 2){%>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(closeBalDebit)%>
										</td>
										<td class=data nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(closeBalCredit)%>
										</td>
									<%}%>										
									</tr>						
<%
		} 
	}
	openingBal = df.format(Math.abs(totOpenCredit-totOpenDebit))+(totOpenCredit > totOpenDebit?" Cr.":totOpenCredit < totOpenDebit?" Dr.":" ");
	closingBal = df.format(Math.abs(totCloseCredit-totCloseDebit))+(totCloseCredit > totCloseDebit?" Cr.":totCloseCredit < totCloseDebit?" Dr.":" ");
	if(!"Y".equals(grouping)){
%>
								<% if(reportType == 3){%>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=openingBal%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?0:(totCredit-totDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totDebit<totCredit?0:(totDebit-totCredit))%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=closingBal%>
										</td>																		
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 >0
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?totDebit:totCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?totDebit:totCredit)%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 >0
										</td>																		
									</tr>
								<%}%>
									<%if(reportType == 1){%>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?0:(totOpenCredit-totOpenDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit<totOpenCredit?0:(totOpenDebit-totOpenCredit))%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?0:(totCloseCredit-totCloseDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit<totCloseCredit?0:(totCloseDebit-totCloseCredit))%>
										</td>																												
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?totOpenDebit:totOpenCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?totOpenDebit:totOpenCredit)%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>																	
									</tr>
									<%} if(reportType == 2){%>
										<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?0:(totCloseCredit-totCloseDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit<totCloseCredit?0:(totCloseDebit-totCloseCredit))%>
										</td>																												
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>																	
									</tr>
									<%}%>					
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
if("Y".equals(grouping)){
%>
								<% if(reportType == 3){%>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=openingBal%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?0:(totCredit-totDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totDebit<totCredit?0:(totDebit-totCredit))%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=closingBal%>
										</td>																		
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 >0
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?totDebit:totCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totDebit>totCredit?totDebit:totCredit)%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 >0
										</td>																		
									</tr>
								<%}%>
									<%if(reportType == 1){%>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?0:(totOpenCredit-totOpenDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit<totOpenCredit?0:(totOpenDebit-totOpenCredit))%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?0:(totCloseCredit-totCloseDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit<totCloseCredit?0:(totCloseDebit-totCloseCredit))%>
										</td>																												
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?totOpenDebit:totOpenCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totOpenDebit>totOpenCredit?totOpenDebit:totOpenCredit)%>
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>																	
									</tr>
									<%} if(reportType == 2){%>
										<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Balancing Figure
										</td>
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?0:(totCloseCredit-totCloseDebit))%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit<totCloseCredit?0:(totCloseDebit-totCloseCredit))%>
										</td>																												
									</tr>
									<tr >
										<td class=label nowrap width=40% align="right" colspan=3>
											<font size=2 style="font-family: Times, serif " > Total
										</td>										
										<td class=label nowrap  align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>
										<td class=label nowrap align="right" width=15%>
											<font size=2 ><%=df.format(totCloseDebit>totCloseCredit?totCloseDebit:totCloseCredit)%>
										</td>																	
									</tr>
									<%}%>					
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