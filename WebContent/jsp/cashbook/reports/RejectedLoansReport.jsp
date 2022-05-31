
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.cashbook.PaymentReceiptDtBean,com.epis.utilities.SQLHelper,com.epis.utilities.StringUtility" %>
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
									
									<bean:message key="rejectedcases.title" bundle="common" /> AS ON <%=StringUtility.checknull(request.getParameter("approvedDate"))%> 
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
						
						 
    					
    					<tr>
    						<td class="label" align="left" width="30%">
    						<bean:message key="rejectedcases.pfid" bundle="common" />
    						</td>
    						<td class="label" align="center" width="20%">
    						<bean:message key="jv.employeename" bundle="common"/>
    						</td>
    						<td class="label" align="left" width="30%">
    						<bean:message key="sq.remarks" bundle="common"/>
    						</td>
    						
    						
    					</tr>
    					
    					<%
    						int i=0;
    					%>
    					
    				<tr>
    				<logic:iterate id="reject" name="rejectedCases">
    					
    					   					
    					<td class=tableText align="left" nowrap="nowrap"><bean:write name="reject" property="empPartyCode"/></td>
    					<td class=tableText align="left" nowrap="nowrap"><bean:write name="reject" property="employeeName"/></td>
    					<td class=tableText align="left" nowrap="nowrap"><bean:write name="reject" property="details"/></td>
    					<%
    						i++;
    					%>
    					
    					</tr>
    					
    					</logic:iterate>
    					<%
    						if(i==0)
    						{
    					%>
    					
    					<tr>
                               <td class="tableText"  align=center nowrap colspan=3><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
    					
    					
    
   
    
    
    
 	</table>
					</td>
				</tr>
			</thead>
		</table>
	</body>
</html>