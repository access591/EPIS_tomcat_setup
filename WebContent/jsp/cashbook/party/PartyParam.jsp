<%@ page import="com.epis.utilities.ScreenUtilities" %>
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

		<script language="javascript" type="text/javascript">		  
		    
		   function  getReport(){
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/party.do?method=partyReport&&partyName="+document.forms[0].partyName.value+"&&reportType="+document.forms[0].reportType.value;
				window.open(url,"PartyInfo","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				clearData();
				return false;
			}
			
		   function clearData(){
		   		  document.forms[0].partyName.value="";
		   		
		   }
		  </script>
	</head>

	<body>
		<html:form action="/party.do?method=partyReport" method="post" onsubmit="return getReport()">
			<%=ScreenUtilities.screenHeader("party.title")%>
			<table width="100%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="party.name" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="partyName" maxlength="50" styleClass="tableText"/>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" /> :
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
				