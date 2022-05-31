
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.investment.GenerateBankLetterBean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cdate" class="com.epis.utilities.DateValidation" scope="request"/>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<jsp:useBean id="sh" class="com.epis.utilities.SQLHelper" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
GenerateBankLetterBean bean=(GenerateBankLetterBean)request.getAttribute("bankLetter");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<base href="<%=basePath%>">

		<title>Quotation Request Letter Formate</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
	</head>
  
  <body >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
    <td align="center">
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
		</td>
	</tr>
	
		
    
    <tr>
    <td align="center">
    <%
    	if((bean.getSecurity().equals("SDL")&&bean.getMarketType().equals("S"))||(bean.getSecurity().equals("GOI")&&bean.getMarketType().equals("S")))
    	{
    %>
    <table width="70%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=2>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left" nowrap><bean:message key="bankletter.lettterno" bundle="common"/>:<bean:write name="bankLetter" property="bankLetterNo"/></td>
    <td class="reportsublabel" align="right">Date:<%=cdate.getCurrentDate()%></td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">To</td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">The Manager</td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">
    <pre>
    <bean:write name="bankLetter" property="address"/>
    </pre>
    
    </td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">Subject :- INVESTMENT IN <bean:write name="bankLetter" property="securityName"/></td>
    </tr>
    <tr>
    <td colspan=2 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=2 align="left">Sir</td>
    </tr>
    <tr>
    <td colspan=2 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp;<bean:write name="bankLetter" property="trustName"/> have purchased <bean:write name="bankLetter" property="securityName"/> 
    from <bean:write name="bankLetter" property="arrangerName"/>. for Rs.<bean:define id="settleamount" name="bankLetter" property="settlementAmount" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(settleamount))%>(Rs <%=sh.ConvertInWords(Double.parseDouble(settleamount))%> only).with Value date on <bean:write name="bankLetter" property="settlementDate"/>. 
        
    </td>
    </tr>
    
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp;Since the transaction is rounded through <bean:write name="bankLetter" property="beneficiaryName"/> it is requested to debit our saving Bank a/cno.<bean:write name="bankLetter" property="accountNo"/>
    	for an amount of Rs.<%=cf.getDecimalCurrency(Double.parseDouble(settleamount))%>(Rs <%=sh.ConvertInWords(Double.parseDouble(settleamount))%> only) through RTGS transfer as per detail given below. 
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="center">
    	<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.beneficiaryname" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="beneficiaryName"/></td>
    		</tr>
    		
    		
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.ifsccode" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="ifscCode"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left">Beneficiary<bean:message key="lettertobank.accno" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="creditAccountNo"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.bankname" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="bankName"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="jv.narration" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="remarks"/></td>
    		</tr>
    			
    	</table>
    	</td>
    	</tr>
    	
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel" >&nbsp;&nbsp;&nbsp; Please ensure before debiting our saving a/c no. <bean:write name="bankLetter" property="accountNo" />they said securities has been credit in D-mat a/c maintained by your bank through stock holding corporation of India Ltd. DP-ID No. IN 301127 Client ID No. 16399167. 
    </td>
    </tr>
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp; It may Please be ensure the above said Payment may be transferred before 11.00 am on <bean:write name="bankLetter" property="settlementDate"/> Only
    </td>
    </tr>
    
    
    
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
   <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    <tr>
    <td colspan=2 align="right" class="reportsublabel">
    Yours Faithfully,
    </td>
    </tr>
    
    <tr>
	<td align="left" class="reportsublabel" >	
	<bean:write name="bankLetter" property="leftSign"/>
	</td>
	<td align="right" class="reportsublabel" >	
	<bean:write name="bankLetter" property="rightSign"/>
	</td>	
	</tr>
    
    </table>
    <%
    }
    else if((bean.getSecurity().equals("SDL")&&bean.getMarketType().equals("R"))||(bean.getSecurity().equals("SDL")&&bean.getMarketType().equals("O")))
    
    {
    %>
    <table width="70%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=3>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left" width="40%" nowrap="nowrap"><bean:message key="bankletter.lettterno" bundle="common"/>:<bean:write name="bankLetter" property="bankLetterNo"/></td>
    <td width="20%">&nbsp;</td>
    <td class="reportsublabel" align="right" width="40%">Date:<%=cdate.getCurrentDate()%></td>
    
    
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">To</td>
    </tr>
    <tr>
    <td colspan=3 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">
    <pre>
    <bean:write name="bankLetter" property="address"/>
    </pre>
    
    </td>
    </tr>
    <tr>
     <td colspan="3" align="left" class="reportsublabel">
     &nbsp;&nbsp;&nbsp; Kind Attn.:  Treasury Settlement Officer.
     </td>
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Dear Sir,
     </TD>
     </tr>
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Sub:<bean:write name="bankLetter" property="securityName"/> Purchase Form.
     </TD>
     </tr>
     
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Kindly arrange to deposit the <bean:write name="bankLetter" property="securityName"/> Form,which will receive from the following counterpart with the Reserve Bank Of India and credit our <bean:write name="bankLetter" property="securityName"/> accountno.<bean:write name="bankLetter" property="accountNo"/> with you.
     
     </TD>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.nameofseller" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="arrangerName"/>
     
     </td>
     </tr>
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.sellerrefno" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="referenceNo"/>
     
     </td>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.dealdate" bundle="common"/>		
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="dealDate"/>
     
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.valuedate" bundle="common"/> 	
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="settlementDate"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.security" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="securityName"/>
     
     </td>
     </tr>
     
     
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
      <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.facevalueamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     Rs. <bean:write name="bankLetter" property="investmentFaceValue"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.rate" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="rate"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.principalamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     Rs. <bean:write name="bankLetter" property="principalAmount"/>
     
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.noofdays" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      <bean:write name="bankLetter" property="noofDays"/> Days
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.brokenperiodinterest" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      Rs. <bean:write name="bankLetter" property="accuredAmount"/> 
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.totalconsideration" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      Rs. <bean:write name="bankLetter" property="settlementAmount"/>  (<bean:write name="bankLetter" property="settlementAmountWords"/> )
     
     </td>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     <tr>
     <td colspan="3" align="left" class="reportsublabel">
     Please credit out <bean:write name="bankLetter" property="securityName"/> account with you and debit our savings account <bean:write name="bankLetter" property="accountNo"/> at bank for the settlement amount.
     
     </td>
     </tr>
     
     
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
     <tr>
     <td colspan="3" align="left" class="reportsublabel">Yours Sincerely</td>
     </tr>
     
     
     
     </table>
    
    <%
    }
    else if((bean.getSecurity().equals("GOI")&&bean.getMarketType().equals("R"))||(bean.getSecurity().equals("GOI")&&bean.getMarketType().equals("O")))
    
    {
    %>
    <table width="70%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=3>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left" width="40%" nowrap="nowrap"><bean:message key="bankletter.lettterno" bundle="common"/>:<bean:write name="bankLetter" property="bankLetterNo"/></td>
    <td width="20%">&nbsp;</td>
    <td class="reportsublabel" align="right" width="40%">Date:<%=cdate.getCurrentDate()%></td>
    
    
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">To</td>
    </tr>
    <tr>
    <td colspan=3 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">
    <pre>
    <bean:write name="bankLetter" property="address"/>
    </pre>
    
    </td>
    </tr>
    <tr>
     <td colspan="3" align="left" class="reportsublabel">
     &nbsp;&nbsp;&nbsp; Kind Attn.:  Treasury Settlement Officer.
     </td>
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Dear Sir,
     </TD>
     </tr>
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Sub:<bean:write name="bankLetter" property="securityName"/> Purchase Form.
     </TD>
     </tr>
     
     <tr>
     <TD colspan="3" align="left" class="reportsublabel">
     Kindly arrange to deposit the <bean:write name="bankLetter" property="securityName"/> Form,which will receive from the following counterpart with the Reserve Bank Of India and credit our <bean:write name="bankLetter" property="securityName"/> accountno.<bean:write name="bankLetter" property="accountNo"/> with you.
     
     </TD>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.nameofseller" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="arrangerName"/>
     
     </td>
     </tr>
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.sellerrefno" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="referenceNo"/>
     
     </td>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.dealdate" bundle="common"/>		
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="dealDate"/>
     
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.valuedate" bundle="common"/> 	
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="settlementDate"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.security" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="securityName"/>
     
     </td>
     </tr>
     
     
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
      <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.facevalueamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     Rs. <bean:write name="bankLetter" property="investmentFaceValue"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.rate" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="bankLetter" property="rate"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.principalamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     Rs. <bean:write name="bankLetter" property="principalAmount"/>
     
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.noofdays" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      <bean:write name="bankLetter" property="noofDays"/> Days
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.brokenperiodinterest" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      Rs. <bean:write name="bankLetter" property="accuredAmount"/> 
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.totalconsideration" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      Rs. <bean:write name="bankLetter" property="settlementAmount"/>  (<bean:write name="bankLetter" property="settlementAmountWords"/> )
     
     </td>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     <tr>
     <td colspan="3" align="left" class="reportsublabel">
     Please credit out <bean:write name="bankLetter" property="securityName"/> account with you and debit our savings account <bean:write name="bankLetter" property="accountNo"/> at bank for the settlement amount.
     
     </td>
     </tr>
     
     
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
     <tr>
     <td colspan="3" align="left" class="reportsublabel">Yours Sincerely</td>
     </tr>
     
     
     
     </table>
    
    <%
    }
    else
    {
    %>
    <table width="70%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=2>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left"  nowrap="nowrap"><bean:message key="bankletter.lettterno" bundle="common"/>:<bean:write name="bankLetter" property="bankLetterNo"/></td>
    <td class="reportsublabel" align="right">Date:<%=cdate.getCurrentDate()%></td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">To</td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">The Manager</td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">
    <pre>
    <bean:write name="bankLetter" property="address"/>
    </pre>
    
    </td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">Subject :- INVESTMENT IN <bean:write name="bankLetter" property="securityName"/></td>
    </tr>
    <tr>
    <td colspan=2 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=2 align="left">Sir</td>
    </tr>
    <tr>
    <td colspan=2 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp;<bean:write name="bankLetter" property="trustName"/> have purchased <bean:write name="bankLetter" property="securityName"/> 
    from <bean:write name="bankLetter" property="arrangerName"/>. for Rs.<bean:define id="settleamount" name="bankLetter" property="settlementAmount" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(settleamount))%>(Rs <%=sh.ConvertInWords(Double.parseDouble(settleamount))%> only).with Value date on <bean:write name="bankLetter" property="settlementDate"/>. 
        
    </td>
    </tr>
    
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp;Since the transaction is rounded through <bean:write name="bankLetter" property="beneficiaryName"/> it is requested to debit our saving Bank a/cno.<bean:write name="bankLetter" property="accountNo"/>
    	for an amount of Rs.<%=cf.getDecimalCurrency(Double.parseDouble(settleamount))%>(Rs <%=sh.ConvertInWords(Double.parseDouble(settleamount))%> only) through RTGS transfer as per detail given below. 
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="center">
    	<table cellpadding="4" cellspacing="0" width="85%" align="center" border="1" bordercolor="black">
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.beneficiaryname" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="beneficiaryName"/></td>
    		</tr>
    		
    		
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.ifsccode" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="ifscCode"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left">Beneficiary<bean:message key="lettertobank.accno" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="creditAccountNo"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="lettertobank.bankname" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="bankName"/></td>
    		</tr>
    		
    		<tr>
    			<td class="reportsublabel" align="left"><bean:message key="jv.narration" bundle="common"/></td>
    			<td class="reportsublabel" align="left"><bean:write name="bankLetter" property="remarks"/></td>
    		</tr>
    			
    	</table>
    	</td>
    	</tr>
    	
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel" >&nbsp;&nbsp;&nbsp; Please ensure before debiting our saving a/c no. <bean:write name="bankLetter" property="accountNo" />they said securities has been credit in D-mat a/c maintained by your bank through stock holding corporation of India Ltd. DP-ID No. IN 301127 Client ID No. 16399167. 
    </td>
    </tr>
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    <tr>
    	<td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp; It may Please be ensure the above said Payment may be transferred before 11.00 am on <bean:write name="bankLetter" property="settlementDate"/> only.
    </td>
    </tr>
    
    
    
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
   <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    <tr>
    <td colspan=2 align="right" class="reportsublabel">
    Yours Faithfully,
    </td>
    </tr>
    
    <tr>
	<td align="left" class="reportsublabel" >	
	<bean:write name="bankLetter" property="leftSign"/>
	</td>
	<td align="right" class="reportsublabel" >	
	<bean:write name="bankLetter" property="rightSign"/>
	</td>	
	</tr>
    
    </table>
    <%
    }
    %>
    
    </td>
    </tr>
    </table>
    
    
  </body>
</html>
