<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.bean.cashbook.FundsTransferInfo" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
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
		<link href="css/aai.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
	
		<script language="javascript" type="text/javascript">		  
		    
		   function  getReport(){		   		
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/FundsFinApproval.do?method=SaveFinalApproval&&keyno="+document.forms[0].keyno.value;
				window.open(url,"Final Approval","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");				
			}
		  
		  </script>
	</head>
	<body>
		<html:form action="/FundsFinApproval.do?method=SaveFinalApproval" method="post" >	
			
			<%=ScreenUtilities.searchSeperator()%>
			
			<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center" colspan="2">
						<table border="0" width="80%" cellpadding="3" cellspacing="0" align="center" valign="middle">
							<tr>
								<td align="center">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="7" nowrap="nowrap">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>							
						</table>				
					<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right" class="label" colspan="2">	Date : 				
						<bean:write name="info" property="preparationDate"/>
					</td>
				</tr>
				<tr>
					<td align="left" class="Data" colspan=2>												
						<pre>To<br>The Manager,<br><bean:write name="info" property="address"/></pre>
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
					<td align="left" class="label" style="height: 70px">	
						<img  src="<bean:write name="info" property="finAppSignPath"/>" alt=""/>
					</td>					
					
				</tr>
				<tr>
					<td align="left" class="label">	
						(<bean:write name="info" property="appDispalyName"/>)
					</td>
					<td align="left" class="label">	
						(<bean:write name="info" property="finAppDispalyName"/>)
					</td>
				</tr>
				<tr>
					<td align="left" class="label">	
					
						<input type='hidden' name='keyno' value="<bean:write name="info" property="keyno"/>" />
					</td>
					<td align="left" class="label">	
				</td>
				</tr>
				<tr>
					<td align="left" class="label">	
						(AUTHORISED SIGNATORIES)
					</td>
					<td align="left" class="label"  nowrap>	
						(AUTHORISED SIGNATORIES)
					</td>
				</tr>
				<tr>
					<td align="left" class="label">	
						&nbsp;
					</td>
				</tr>
				
					<tr>
						<td colspan="4" align="center">
							<input type="submit" class="butt" value="Approve" tabindex="4" onclick="getReport()"/>
						</td>
					</tr>
			</thead>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html> 