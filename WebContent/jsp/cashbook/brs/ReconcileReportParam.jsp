<%@ page import="com.epis.bean.cashbook.PartyInfo,com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
//String accountType = request.getParameter("accountType") ==null?"":request.getParameter("accountType"); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Bank Info Search</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePathBuf%>js/calendar.js"></script>

		<script language="javascript" type="text/javascript">		  
		    
		   function  getReport(){
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/reconciledReport.do?method=showReport&&accountNo="+document.forms[0].accountNo.value+"&&reportType="+document.forms[0].reportType.value+"&&fromDate="+document.forms[0].fromDate.value+"&&toDate="+document.forms[0].toDate.value;
				window.open(url,"StatementInfo","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
			
		   function clearData(){
		   		  document.forms[0].accountNo.value="";
		   		
		   }
		  </script>
	</head>

	<body>
		<html:form action="/reconciledReport.do?method=showReport" method="post" onsubmit="return getReport()">
			<%=ScreenUtilities.screenHeader("reconciledreport.title")%>
			<table width="100%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						From Date : 
					</td>
					<td width="20%">
						<input class="TextField" name='fromDate' maxlength="11" size="15" />
						&nbsp; <a href="javascript:show_calendar('forms[0].fromDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePathBuf%>/images/calendar.gif" src="" alt="" /></a>
						
					</td>
				
					<td width="20%" class="tableTextBold" align="right" nowrap>
						To Date : 
					</td>
					<td width="20%">
						<input class="TextField" name='toDate' maxlength="11" size="15" />
						&nbsp; <a href="javascript:show_calendar('forms[0].toDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePathBuf%>/images/calendar.gif" src="" alt="" /></a>
					</td>
				</tr>
				<tr>
				
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankName" bundle="common" />
						:
					</td>				
					<td width="20%">					
						<html:select property="accountNo" styleClass="TextField" style='width:180px' >
							<html:option value="">Select One</html:option>
							<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails"  />										
						</html:select>
					</td>				
				
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" />  : 
					</td>
					<td align="left">
						<html:select property="reportType"  styleClass="TextField" style="width: 60px">
						<html:option value="html">
							HTML
						</html:option>
						<html:option value="excel">
							Excel
						</html:option>
							
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				
				
				<tr>
					<td colspan="4" align="center">
						<input type="submit" class="butt" value="Submit" tabindex="4" />
						<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" />
					</td>
				</tr>
						
			 </table>
			<%=ScreenUtilities.searchFooter()%>			
	   </html:form>
	</body>
</html>
				