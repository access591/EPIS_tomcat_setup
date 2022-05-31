<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Investment - Master - Fund Accured</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Fund Accured" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript">
			function check(){
				if(document.forms[0].amount.value == ""){
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}		
				if(!ValidateFloatPoint(document.forms[0].amount.value,15,4)){
					alert("Please Enter Valid Amount (Expecting  Numeric Value)");
					return false;
				}
				if(document.forms[0].trustType.value=="")
				{
					alert("Please Select Trust Type (Mandatory)");
					document.forms[0].trustType.focus();
					return false;
				}
				if(document.forms[0].asOnDate.value==''){
						alert("please Enter AsOn Date ");
						document.forms[0].asOnDate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].asOnDate)){
						document.forms[0].asOnDate.select();
						return(false);
					   }
			}			

		</script>
	</head>
	<body onload="document.forms[0].finYear.focus()">
		<html:form action="/fundAccuredNew.do?method=add" method="post" onsubmit="javascript : return check()">
			<%=ScreenUtilities.screenHeader("fa.title")%>
			<table width="70%" border="0" align="center">
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr style="height:1cm">
					<td class="tableTextBold" align="right">
						<bean:message key="fa.finyear" bundle="common" />
					</td>
					<td align="left">
						<html:select property="finYear" styleClass="TextField"  >
							<html:options name="Bean" property="code" labelProperty="code" labelName="code" collection="finYear" />
						</html:select>
					</td>
				</tr>
				<tr style="height:1cm">
					<td class="tableTextBold" align="right">
						<font color=red>*</font><bean:message key="fa.amount" bundle="common" />
					</td>
					<td align="left">
						<html:text property="amount" />
					</td>
				</tr>
				<tr>
					<td  class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.trusttype" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="trustType" style="width:150px" tabindex="5" styleClass="TextField">
							<html:option value="">[Select one]</html:option>
							<html:options collection="trustRecords" property="trustType" labelProperty="trustType" />
						</html:select>
					</td>	
				</tr>
				<tr>
				<td class="tableTextBold" align=right nowrap>
						<font color="red">*</font>
						<bean:message key="fa.asondate" bundle="common" />
						:
					</td>
					<td align=left  nowrap>
						<html:text property="asOnDate" styleClass="TextField" styleId="proposaldate" style='width:150 px' tabindex="3" maxlength="12" />
						<html:link href="javascript:showCalendar('asOnDate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
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