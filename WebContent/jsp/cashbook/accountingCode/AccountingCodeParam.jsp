<%@ page import="com.epis.bean.cashbook.AccountingCodeInfo"%>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java"  pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Account Code Search" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		<script language="javascript" >
			function  getReport(){
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/accCodeReport.do?method=getReport&&accountHead="+document.forms[0].accountHead.value+"&&particular="+document.forms[0].particular.value+"&&type="+document.forms[0].type.value+"&&screenType="+document.forms[0].screenType.value;
				window.open(url,"AccountingCode","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
		 </script>
	</head>

	<body class="BodyBackground1">
		<html:form action="/accCodeReport.do?method=getReport" method="post" onsubmit="return getReport()">
			
			<%=ScreenUtilities.screenHeader("accCode.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan="4" align="center">
						&nbsp; <html:errors bundle="error" property="accountHead"/>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.accCode" bundle="common" />:
					</td>
					<td align="left">
						<html:text property="accountHead" styleClass="tableText"/>
					</td>
				
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.name" bundle="common" />:
					</td>
					<td align="left">
						<html:text property="particular" styleClass="tableText"/>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCodeType.accCodeType" bundle="common" />: 
					</td>
					<td align="left">
						<html:select property="type"  styleClass="TextField">
							<html:option value="">
							Select One
						</html:option>
							<html:options name="AccountingCodeTypeBean" property="code" labelProperty="accountCodeType" labelName="code" collection="typesList" />
						</html:select>
					</td>
				
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" />:
					</td>
					<td align="left">
						<html:select property="screenType"  styleClass="TextField" style="width: 60px">
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
					<td align="center" colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<html:submit  value="Submit" tabindex="4" styleClass="butt"/>	
						<html:button value="Reset" property="Reset" onclick="javascript:document.forms[0].reset()" styleClass="butt"/>
						<html:button  value="Cancel" property="Cancel"onclick="javascript:history.back(-1)" styleClass="butt"/>				
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>
