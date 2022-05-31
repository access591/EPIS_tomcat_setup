<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Type</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code Type" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript">
			function check(){
				if(document.forms[0].accountCodeType.value == ""){
					alert("Please Enter Account Code Type (Mandatory)");
					document.forms[0].accountCodeType.focus();
					return false;
				}		
				if(!ValidateName(document.forms[0].accountCodeType.value))		{
					alert("Please Enter Valid Account Code Type (Spl. Characters not allowed)");
					return false;
				}
			}
			
			function checkFields(){
				if(document.forms[0].code.value == ""){
					document.forms[0].accountCodeType.value="";
					document.forms[0].description.value="";
				}				
			}
		</script>
	</head>
	<body onload="checkFields();document.forms[0].accountCodeType.focus()">
		<html:form action="/accCodeType.do?method=add" method="post" onsubmit="javascript : return check()">
			<%=ScreenUtilities.screenHeader("accCodeType.title")%>
			<table width="70%" border="0" align="center">
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" />
						<html:hidden property="code" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="accCodeType.accCodeType" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="accountCodeType" maxlength="50" styleClass="tableText" />
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="accCodeType.description" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="description" maxlength="150" styleClass="tableText" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center" style="height:10px">
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<html:submit styleClass="butt">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						<html:button property="Clear" styleClass="butt" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						&nbsp;
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>