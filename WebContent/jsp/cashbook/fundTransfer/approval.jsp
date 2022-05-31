<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.bean.cashbook.FundsTransferInfo" %>
<%
SQLHelper sh = new SQLHelper();
FundsTransferInfo info = (FundsTransferInfo)request.getAttribute("info");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Funds Transfer</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />		
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/aai.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<html:form action="/FundsApproval.do?method=SaveApproval" method="post">		
			<%=ScreenUtilities.searchSeperator()%>			
			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
							<tr>
								<td >
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="7" nowrap="nowrap">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>							
						</table>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right" class="label" colspan=2>	Date : 				
						<bean:write name="info" property="preparationDate"/>
					</td>
				</tr>
				<tr>
					<td align="left" class="Data" colspan=2>												
						<pre style="width: 1cm">To<br>The Manager,<br></br><bean:write name="info" property="address"/></pre>
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						Subject :-	Transfer of funds from <bean:write name="info" property="accountType"/> Account No <bean:write name="info" property="fromAccountNo"/> - through RTGS
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						Sir,						
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left" class="Data" colspan=2>	
						&nbsp;&nbsp;&nbsp;&nbsp;
						You are requested to please transfer a sum of 
						Rs. <bean:write name="info" property="amount"/> -( Rs.<%=sh.ConvertInWords(Double.parseDouble(info.getAmount()))%> only) 
						from above mentioned <bean:write name="info" property="accountType"/> bank to 
						<bean:write name="info" property="toBank"/> Account No.<bean:write name="info" property="toAccountNo"/>
						of  &nbsp;"<bean:write name="info" property="accountName"/>" through RTGS. The IFSC Code of 
						<bean:write name="info" property="toBank"/> is "<bean:write name="info" property="ifscCode"/>".
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						Thanking You
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
						Yours faithfully
					</td>
				</tr>
				<tr>
					<td align="left" class="label" colspan=2>	
					
					</td>
				</tr>
				<tr>
					<td align="left" class="label" style="height: 70px" >	
						<img  src="<bean:write name="info" property="appSignPath"/>" alt=""/>
					</td>					
					
				</tr>
				<tr>
					<td align="left" class="label">	
						(<bean:write name="info" property="appDispalyName"/>)
					</td>
				</tr>
				<tr>
					<td align="left" class="label">	
						<input type='hidden' name='approvedBy' value="<bean:write name="info" property="approvedBy"/>" />
						<input type='hidden' name='keyno' value="<bean:write name="info" property="keyno"/>" />
					</td>
				</tr>
				<tr>
					<td align="left" class="label">	
						(AUTHORISED SIGNATORIES)
					</td>
				</tr>
				
				<tr>
					<td align="left" class="label">	
						&nbsp;
					</td>
				</tr>
				
				<tr>
					<td colspan="3" align="left">
						<html:submit styleClass="butt">
							<bean:message key="button.approve" bundle="common" />
						</html:submit>
					</td>
				</tr>
				
			</thead>
			</table>			
			<%=ScreenUtilities.searchFooter()%>			
		</html:form>
	</body>
</html> 