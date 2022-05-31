
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.cashbook.PaymentReceiptDtBean,com.epis.utilities.SQLHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
double receiptAmttotal=0.0;
double paymentAmttotal=0.0;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PaymentAndReceiptReport.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>
  <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" >
							<tr>
								<td rowspan="3">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="5">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="5">
									 <bean:message key="ie.address" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="5">
									
									<bean:message key="paymentandreceipt.title" bundle="common" /> AS ON <%=request.getParameter("finyear")%> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="1" cellpadding="0" cellspacing="0" align="center" class="border">
						
						 <bean:define id="receipt" property="receiptList" name="beanlist" type="java.util.ArrayList"/>
    					<bean:define id="payment" property="paymentList" name="beanlist" type="java.util.ArrayList"/>
    					<bean:define id="bankinformation" property="bankDetails" name="beanlist" type="java.util.HashMap"/>
    					
    					<tr>
    						<td class="label" align="left" width="30%">
    						RECEIPT
    						</td>
    						<td class="label" align="center" width="20%">
    						CURRENT Year <br><%=request.getParameter("finyear")%>
    						</td>
    						<td class="label" align="left" width="30%">
    						PAYMENT
    						</td>
    						
    						<td class="label" align="center" width="20%" >
    						CURRENT Year <br> <%=request.getParameter("finyear")%>
    						</td>
    					</tr>
    					
    					<tr>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    					</tr>
    					<tr>
    						<td class="label" align="left">Opening Balance</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    				</tr>
    				<tr>
    				<logic:iterate id="bank" name="bankinformation">
    					
    					<bean:define id="dtbean" name="bank" property="value" type="com.epis.bean.cashbook.DailyStatement" />
    					<bean:define id="openingamt" name="dtbean" property="openingBalance" type="java.lang.String"/>
    					<%
    						StringTokenizer st=new StringTokenizer(openingamt,"-");
    						String openAmt="";
    						String openType="";
    						while(st.hasMoreTokens())
    						{
    						openAmt=st.nextToken();
    						openType=st.nextToken();
    						}
    						receiptAmttotal+=Double.parseDouble(openAmt);
    					%>
    					<td class=tableText align="left" nowrap="nowrap"><bean:write name="dtbean" property="bankName"/></td>
    					<td class=tableText align="right" nowrap="nowrap"><%=cf.getDecimalCurrency(Double.parseDouble(openAmt))%></td>
    					
    					<td>&nbsp;</td>
    					<td>&nbsp;</td>
    					</tr>
    					
    					</logic:iterate>
    					
    					<tr>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    					</tr>
    
    <%
    	
    	for(int i=0; i<payment.size(); i++)
    	{
    		PaymentReceiptDtBean receiptBean=(PaymentReceiptDtBean)receipt.get(i);
    		PaymentReceiptDtBean  paymentBean=(PaymentReceiptDtBean)payment.get(i);
    	%>
    <tr>
    	<td class=tableText align="left" nowrap="nowrap"><%=receiptBean.getAccheadDesc()%></td>
    	<td class=tableText align="right" nowrap="nowrap"><%=cf.getDecimalCurrency(Double.parseDouble(receiptBean.getReceiptAmt()))%></td>
    	<td class=tableText align="left" nowrap="nowrap"><%=paymentBean.getAccheadDesc()%></td>
    	<td class=tableText align="right" nowrap="nowrap"><%=cf.getDecimalCurrency(Double.parseDouble(paymentBean.getPaymentAmt()))%></td>
    </tr>
    <%
    	receiptAmttotal+=Double.parseDouble(receiptBean.getReceiptAmt());
    	paymentAmttotal+=Double.parseDouble(paymentBean.getPaymentAmt());
    }
    %>
    
    
    <tr>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    					</tr>
    					<tr>
    						<td>&nbsp;</td>
    						<td>&nbsp;</td>
    						<td class="label" align="left">Closing Balance</td>
    						<td>&nbsp;</td>
    				</tr>
    				<tr>
    				<logic:iterate id="bank" name="bankinformation">
    					
    					<bean:define id="dtbean" name="bank" property="value" type="com.epis.bean.cashbook.DailyStatement" />
    					<bean:define id="closingamt" name="dtbean" property="closingBalance" type="java.lang.String"/>
    					<%
    						StringTokenizer stclose=new StringTokenizer(closingamt,"-");
    						String closingAmt="";
    						String closingType="";
    						while(stclose.hasMoreTokens())
    						{
    						closingAmt=stclose.nextToken();
    						closingType=stclose.nextToken();
    						}
    						
    						paymentAmttotal+=Double.parseDouble(closingAmt);
    					%>
    					<td>&nbsp;</td>
    					<td>&nbsp;</td>
    					<td class=tableText align="left" nowrap="nowrap"><bean:write name="dtbean" property="bankName"/></td>
    					<td class=tableText align="right" nowrap="nowrap"><%=cf.getDecimalCurrency(Double.parseDouble(closingAmt))%></td>
    					
    					
    					</tr>
    					
    					</logic:iterate>
    					<tr>
    						<td>Total</td>
    						<td align=right><%=cf.getDecimalCurrency(receiptAmttotal)%></td>
    						<td>&nbsp;</td>
    						
    						<td align=right><%=cf.getDecimalCurrency(paymentAmttotal)%></td>
    						
    					</tr>
    
    
    
    
 	</table>
					</td>
				</tr>
			</thead>
		</table>
	</body>
</html>