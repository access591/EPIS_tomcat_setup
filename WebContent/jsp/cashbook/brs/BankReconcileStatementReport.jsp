
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.cashbook.BankBook" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;
BankBook book=(BankBook)request.getAttribute("bankBook");
double receipts = ("Cr.".equals(book.getAmountType())?book.getOpeningBalAmt():0.00);
double payments = ("Dr.".equals(book.getAmountType())?book.getOpeningBalAmt():0.00);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'BankReconcileStatementReport.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body background="body">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="brs.bankreconcilestatement" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>&nbsp;</td>
						</tr>
						<tr>
						<td>
						
						<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						
						<logic:equal name="voucherbean" property="checkingRecordCount" value="notAvailable">
						<tr>
							<td colspan=2 align=center>No Records Found</td>
						</tr>
						
						</logic:equal>
						<logic:equal name="voucherbean" property="checkingRecordCount" value="available">
						<tr>
							<td class=tblabel width="70%" align=left>
							Closing balance as per bank statement: 
							</td>
							<td class=tableText width="30%" align="right">
							<bean:define id="bankclosing" name="voucherbean" property="bankClosingBal" type="java.lang.String" />
							<%=cf.getDecimalCurrency(Double.parseDouble(bankclosing))%>
							</td>
						</tr>
						
						
						<tr>
							<td colspan=2>&nbsp;</td>
						</tr>
						<tr>
						<td class=tblabel colspan=2 align=left>I.LESS:</td>
						</tr>
						
						<tr>
							<td class=tblabel align=left>
							Credit Entries In Bank Statement,Not In Bank Book: 
							</td>
							<td class=tableText align="right">
							<bean:define id="bankcredit" name="voucherbean" property="bankCredit" type="java.lang.String" />
							<%=cf.getDecimalCurrency(Double.parseDouble(bankcredit))%>
							</td>
						</tr>
						
						<tr>
							<td class=tblabel align=left>
							Debit Voucher In Bank Book ,Not In Bank Statement:
							</td>
							<td class=tableText align="right">
							<bean:define id="bankbookdebit" name="voucherbean" property="bankbookDebit" type="java.lang.String" />
							<%=cf.getDecimalCurrency(Double.parseDouble(bankbookdebit))%>
							</td>
						</tr>
						
						<tr>
							<td colspan=2>&nbsp;</td>
						</tr>
						
						<tr>
						<td class=tblabel colspan=2 align=left>II.ADD:</td>
						</tr>
						
						<tr>
							<td class=tblabel align=left>
							Debit Entries in Bank Statement, Not In Bank Book: 
							</td>
							<td class=tableText align="right">
							<bean:define id="bankdebit" name="voucherbean" property="bankDebit" type="java.lang.String" />
							<%=cf.getDecimalCurrency(Double.parseDouble(bankdebit))%>
							</td>
						</tr>
						
						<tr>
							<td class=tblabel align=left>
							Credit Voucher In Bank Book,Not in bank Statement:
							</td>
							<td class=tableText align="right">
							<bean:define id="bankbookcredit" name="voucherbean" property="bankbookCredit" type="java.lang.String" />
							<%=cf.getDecimalCurrency(Double.parseDouble(bankbookcredit))%>
							</td>
						</tr>
						
						<tr>
							<td colspan=2>&nbsp;</td>
						</tr>
						
						
						
						<tr>
							<td class=tblabel align=left>
							Closing balance as per bank book: 
							</td>
							<td class=tableText align=right>
							
							<%=cf.getDecimalCurrency((Double.parseDouble(bankclosing)+Double.parseDouble(bankdebit)+Double.parseDouble(bankbookcredit))-(Double.parseDouble(bankcredit)+Double.parseDouble(bankbookdebit)))%>
							</td>
						</tr>
						</logic:equal>
						</table>
					</td>
					</tr>
					</table>
						
					
						
  </body>
</html>
