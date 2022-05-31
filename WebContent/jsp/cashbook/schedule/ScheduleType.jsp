<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Schedule Type Master</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript">
			function checkAccount(){
				if(document.forms[0].schType.value==""){
					alert("Please Enter Schedule Type(Mandatory)");
					document.forms[0].schType.focus();
					return false;
				}
				if(document.forms[0].description.value==""){
					alert("Please Enter Description Type(Mandatory)");
					document.forms[0].description.focus();
					return false;
				}
				if(document.forms[0].type.value==""){
					alert("Please Enter Accounting code Type (Mandatory)");
					document.forms[0].type.focus();
					return false;
				}
				var bool = true;
				for(var i=0;i<document.forms[0].accountHead.options.length;i++){
					if(document.forms[0].accountHead.options[i].selected){
						bool = false;
					}
				}
				if(bool){
					alert("Please Select atleast one Accounting Code(Mandatory)");
					return false;
				}
			}
			function check(){
				if(document.forms[0].screenType.value=='NEW'){
					document.forms[0].schType.value = "";
					document.forms[0].description.value = "";
				}else{
					document.forms[0].schType.readOnly = true;
				}
			}
		</script>
	</head>
	<body onload="check()">
		<html:form action="/ScheduleType.do?method=add" onsubmit="javascript : return checkAccount()"  method="post">
			<%=ScreenUtilities.screenHeader("sch.title")%>
			<html:hidden property="screenType"/>
			<table width="550" border="0" cellspacing="3" cellpadding="3">
				<tr>
					<td align="center"  colspan="4">
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="sch.type" bundle="common" />
					</td>
					<td align="left">
						<html:text property="schType" styleClass="tableText" />																
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="sch.description" bundle="common" />
					</td>
					<td align="left">
						<html:text property="description" styleClass="tableText" maxlength="100"/>
					</td>
				</tr>		
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="sch.schtype" bundle="common" />
					</td>
					<td align="left">
						<html:select property="type" styleClass="TextField" style="width:150px">
							<html:option value="">
									Select One
								</html:option>
							<html:options name="AccountingCodeTypeBean" property="code" labelProperty="accountCodeType" labelName="code" collection="typesList" />
						</html:select>
					</td>
				</tr>	
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="sch.accHead.add" bundle="common" />
					</td>
					<td align="left">
						<html:select property="accHeadAdd" styleClass="tableText" multiple="multiple" size="4">
						<html:options collection="accHeads" property="accountHead" labelProperty="displayName"/>
						</html:select>
					</td>
				</tr>	
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="sch.accHead.less" bundle="common" />
					</td>
					<td align="left">
						<html:select property="accHeadLess" styleClass="tableText" multiple="multiple" size="4">
						<html:options collection="accHeads" property="accountHead" labelProperty="displayName"/>
						</html:select>
					</td>

				</tr>			
				<tr>
					<td height="10px">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<html:submit styleClass="butt" value="Save" />
						<html:button styleClass="butt" value="Clear" property="Reset" onclick="javascript:document.forms[0].reset()" />
					</td>
				</tr>
				<tr>
					<td height="10px">
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>