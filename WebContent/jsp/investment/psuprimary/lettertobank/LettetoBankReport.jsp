
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.investment.LetterToBankBean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cdate" class="com.epis.utilities.DateValidation" scope="request"/>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<jsp:useBean id="sh" class="com.epis.utilities.SQLHelper" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
LetterToBankBean bean=(LetterToBankBean)session.getAttribute("letterToBankBean");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<base href="<%=basePath%>">

		<title>Letter To Bank</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
	</head>
  
  <body >
  <html:form action="/addlettertobank.do?method=letttertoBank">
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
    <input type="hidden" name="mode" value="<bean:write name="letterToBankBean" property="mode"/>"/>
    <%
    	if((bean.getSecurityCategory().equals("PSU")&&bean.getMarketType().equals("P")))
    	{
    %>
    <table width="100%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=2>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left" nowrap><bean:message key="lettertobank.letterno" bundle="common"/>:
    <bean:write name="letterToBankBean" property="letterNo"/></td>
    <td class="reportsublabel" align="right">Date:<%=cdate.getCurrentDate()%></td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">To</td>
    </tr>
    
    <tr>
    <td colspan=2 align="left" class="reportsublabel">
    
    <bean:write name="letterToBankBean" property="beneficiaryName"/>
    
    
    </td>
    </tr>
    <tr>
    <td colspan=2 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=2 align="left" class="reportsublabel">Subject :- INVESTMENT IN <bean:write name="letterToBankBean" property="securityName"/></td>
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
    <td colspan="2" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp; We hereby authorise you to debit our account no.<bean:write name="letterToBankBean" property="accountNo"/> for an amount of Rs.<bean:define id="invamount" name="letterToBankBean" property="amountInv" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(invamount))%>(Rs <%=sh.ConvertInWords(Double.parseDouble(invamount))%> only) and remit the same through RTGS as for the followind details for investment in <bean:write name="letterToBankBean" property="beneficiaryName"/>. 
        
    </td>
    </tr>
    <tr>
    <td colspan=2>&nbsp;
    </td>
    </tr>
    
    <tr>
    <td class="reporsublabel" align="left">A)Beneficiary Details - <bean:write name="letterToBankBean" property="bankName"/></td>
    <td class="reportsublable" align="left">
    &nbsp;
    </td>
    </tr>
   <tr>
    <td colspan=2 align=center>
    <TABLE width="70%" border="1" cellpadding="3" cellspacing="0" >
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.beneficiaryname" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="beneficiaryName"/>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.creditaccountno" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="creditAccountNo"/>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.centerlocation" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="centerLocation"/>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.bankname" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="bankName"/>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.branchname" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="branchName"/>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.accounttype" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="accountTypedef"/>
    </tr>
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.ifsccode" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="ifscCode"/>
    </tr>
    
    
    </TABLE>
    
    
    	
    
    </td>
    </tr>
    <tr>
    <td colspan=2>&nbsp;</td>
    </tr>
    
    
    
    
     <tr>
    <td class="reporsublabel" align="left">Remittance Details :- </td>
    <td class="reportsublable" align="left">&nbsp;
    
    </td>
    </tr>
   <tr>
    <td colspan=2 align=center>
    <TABLE width="70%" border="1" cellpadding="3" cellspacing="0" >
    <tr>
    	<td class="reportsublable" align="left">Amount(In figures)
    	</td>
    	<td class="reportsublable" align="left"><%=cf.getDecimalCurrency(Double.parseDouble(invamount))%>
    	</td>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left">Amount(In words)
    	</td>
    	<td class="reportsublable" align="left"><%=sh.ConvertInWords(Double.parseDouble(invamount))%>
    	</td>
    </tr>
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.remarks" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="remarks"/>
    </tr>
    
    
    
    <tr>
    	<td class="reportsublable" align="left"><bean:message key="lettertobank.accounttype" bundle="common"/>
    	</td>
    	<td class="reportsublable" align="left"><bean:write name="letterToBankBean" property="accountTypaai"/>
    </tr>
    
    
    </TABLE>
    
    
    	
    
    </td>
    </tr>
 
    
    <tr>
    <td colspan=2 align="right" class="reportsublabel">
    Yours Faithfully,
    </td>
    </tr>
    <tr>
    	<td colspan=2>&nbsp;</td>
    </tr>
    
    <tr>
	<td align="left" class="reportsublabel" >	
	<bean:write name="letterToBankBean" property="leftSign"/>
	</td>
	<td align="right" class="reportsublabel" >	
	<bean:write name="letterToBankBean" property="rightSign"/>
	</td>	
	</tr>
			
	<tr>
	<td ALIGN="LEFT" class="reportsublabel" >Place   :NEW DELHI</TD>
	<TD>&nbsp;</td>
	</tr>
	<tr>
	<td ALIGN="LEFT" class="reportsublabel">Date   :<bean:write name="letterToBankBean" property="bankDate"/></TD>
	<TD>&nbsp;</td>
	</tr>
	
    
    </table>
    
    <%
    }
    else if((bean.getSecurityCategory().equals("SDL")&& (bean.getMarketType().equals("R")|| bean.getMarketType().equals("RB") ))||(bean.getSecurityCategory().equals("GOI")&&(bean.getMarketType().equals("R")||bean.getMarketType().equals("RB"))))
    {
    %>
    	<table width="100%" border="0" cellpadding="3" cellspacing="0" >
    <tr>
    <td colspan=3>&nbsp;</td>
    </tr>
    <tr>
    <td class="reportsublabel" align="left" nowrap><bean:message key="lettertobank.letterno" bundle="common"/>:
    <bean:write name="letterToBankBean" property="letterNo"/></td>
    <td class="reportsublabel" align="right">Date:<%=cdate.getCurrentDate()%></td>
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">To</td>
    </tr>
    
    <tr>
    <td colspan=3 align="left" class="reportsublabel">
    
    <bean:write name="letterToBankBean" property="bankAddress"/>
    
    </td>
    </tr>
    <tr>
    <td colspan=3 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=3 align="left" class="reportsublabel">Subject :-  <bean:write name="letterToBankBean" property="securityName"/>SGL Purchase Form</td>
    </tr>
    <tr>
    <td colspan=3 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan=3 align="left">Sir</td>
    </tr>
    <tr>
    <td colspan=3 align="left">&nbsp;</td>
    </tr>
    <tr>
    <td colspan="3" align="justify" class="reportsublabel">&nbsp;&nbsp;&nbsp; Kindly arrange to deposit the SGL Form,which will receive from the following counterpart with the Reserve Bank Of India and credit our <bean:write name="letterToBankBean" property="securityName"/> accountno.<bean:write name="letterToBankBean" property="aaiaccountNo"/> with you.
        
    </td>
    </tr>
    <tr>
    <td colspan=3>&nbsp;
    </td>
    </tr>
    
    <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.nameofseller" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="letterToBankBean" property="securityName"/>
     
     </td>
     </tr>
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.sellerrefno" bundle="common"/>				
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="letterToBankBean" property="sellerRefNo"/>
     
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
     <bean:write name="letterToBankBean" property="dealDate"/>
     
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="lettertobank.settlementdate" bundle="common"/> 	
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="letterToBankBean" property="settlementDate"/>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.security" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:write name="letterToBankBean" property="securityName"/>
     
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
      <bean:define id="facevalamt" name="letterToBankBean" property="faceValue" type="java.lang.String"/> 
     Rs.<%=cf.getDecimalCurrency(Double.parseDouble(facevalamt))%>    
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.rate" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:define id="offerprice" name="letterToBankBean" property="offeredPrice" type="java.lang.String" />
     Rs.<%=cf.getDecimalCurrency(Double.parseDouble(offerprice))%>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     (Principal Amount-Discount Amount)=(FaceValue*Rate)
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     <bean:define id="principalamt" name="letterToBankBean" property="principalAmount" type="java.lang.String" />
    Rs.<%=cf.getDecimalCurrency(Double.parseDouble(principalamt))%>
     </td>
     </tr>
     
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="bankletter.noofdays" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      <bean:write name="letterToBankBean" property="noofdays"/> Days
     
     </td>
     </tr>
     
          
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="lettertobank.accuredinterestamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
      <bean:define id="accuredamt" name="letterToBankBean" property="accuredInterestAmount" type="java.lang.String" />
    Rs.<%=cf.getDecimalCurrency(Double.parseDouble(accuredamt))%>
     
     </td>
     </tr>
     
     <tr>
     <TD align="left" class="reportsublabel" width="40%">
     <bean:message key="lettertobank.settlementamount" bundle="common"/>
     </TD>
     <td align="center" class="reportsublabel" width="20%">
     :</td>
     <td align="left" class="reportsublabel" width="40%" >
     
     <bean:define id="settlementamt" name="letterToBankBean" property="settlementAmount" type="java.lang.String" />
    Rs.<%=cf.getDecimalCurrency(Double.parseDouble(settlementamt))%>
       (Rs.<%=sh.ConvertInWords(Double.parseDouble(settlementamt))%> Only )
     
     </td>
     </tr>
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     <tr>
     <td colspan="3" align="left" class="reportsublabel">
     Please credit out SGL A/C SG020092 account with you and debit our savings account <bean:write name="letterToBankBean" property="aaiaccountNo"/> at bank for the settlement amount.
     
     </td>
     </tr>
     
     
     <tr>
     <td colspan="3">&nbsp;</td>
     </tr>
     
     <tr>
     <td colspan="3" align="left" class="reportsublabel">Yours Sincerely</td>
     </tr>
     
     <tr>
	<td align="left" class="reportsublabel" colspan=2 >	
	<bean:write name="letterToBankBean" property="leftSign"/>
	</td>
	<td align="right" class="reportsublabel" >	
	<bean:write name="letterToBankBean" property="rightSign"/>
	</td>	
	</tr>
			
	
     
     
     
     </table>
    
    <%
    
    }
    %>
    
    </td>
    </tr>
    <tr><td align="center" ><html:submit value="OK"/>
    </table>
    
    </html:form>
  </body>
</html>
