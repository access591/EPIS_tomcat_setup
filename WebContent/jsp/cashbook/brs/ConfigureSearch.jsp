<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
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
	</head>
	<body>
		<html:form action="/Configure.do?method=mappingValues" method="post">
			<%=ScreenUtilities.screenHeader("brs.title")%>
			<table width="70%" border="0" align="center">
				<tr>
					<td align="center" nowrap colspan="4">
						<html:errors bundle="error" property="bankName" />
					</td>
				</tr>				
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankName" bundle="common" />
						:
					</td>				
					<td width="20%">					
						<html:select property="accountNo" styleClass="TextField" style='width:280px' >
							<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails"  />										
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="center" nowrap colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<html:submit styleClass="butt">
							Submit
						</html:submit>
						<html:button styleClass="butt" property="Clear" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>









