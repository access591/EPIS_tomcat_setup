<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
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
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script language="javascript" >
			function checkFundsTransfer(){
				if(document.forms[0].fromAccountNo.value == ''){
					alert("Please Select the From Bank(Mandatory)");
					document.forms[0].fromAccountNo.focus();
					return false;
				}
				if(document.forms[0].toAccountNo.value == ''){
					alert("Please Select the To Bank(Mandatory)");
					document.forms[0].toAccountNo.focus();
					return false;
				}
				if(document.forms[0].fromAccountNo.value == document.forms[0].toAccountNo.value){
					alert("From Bank and To Bank Should not be same (Mandatory)");
					document.forms[0].toAccountNo.focus();
					return false;
				}
				if(document.forms[0].preparationDate.value == ''){
					alert("Please Enter the Preparation Date(Mandatory)");
					document.forms[0].preparationDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].preparationDate)){
					document.forms[0].preparationDate.select();
					return(false);
				}
				if(document.forms[0].amount.value == ''){
					alert("Please Enter the Amount(Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}
				if(!ValidateFloatPoint(document.forms[0].amount.value,15,4)){
					document.forms[0].amount.focus();
					return false;
				}
			}
		</script>
	</head>
	<body onload="document.forms[0].fromAccountNo.focus()">
		<html:form action="/FundsTransfer.do?method=save" method="post" onsubmit="return checkFundsTransfer()">
			<%=ScreenUtilities.screenHeader("ft.title")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="15%">
									<table align="center" cellspacing="5" border="0">
										<tr>
											<td colspan="2" align="center">
												&nbsp; <html:errors bundle="error"/>
												<html:hidden property="keyno"/>												
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.frombank" bundle="common" />
												:
											</td>
											<td align="left">
												<html:select property="fromAccountNo" styleClass="TextField" style='width:200px'>
													<html:option value="">Select One</html:option>
													<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails" />
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.tobank" bundle="common" />
												:
											</td>
											<td align="left">
												<html:select property="toAccountNo" styleClass="TextField" style='width:200px'>
													<html:option value="">Select One</html:option>
													<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails" />
												</html:select>
											</td>
										</tr>										
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.amount" bundle="common" />
												:
											</td>
											<td align="left">
												<html:text maxlength="15" property="amount" styleClass='TextField' style="width:80 px" />												
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right" nowrap="nowrap">
												<bean:message key="ft.prepDate" bundle="common" />
												:
											</td>
											<td align="left">
												<html:text maxlength="11" property="preparationDate" styleId="preparationDate" styleClass='TextField' style="width:80 px" />
												<html:link href="javascript:showCalendar('preparationDate');">
													<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
												</html:link>
											</td>
										</tr>
										<tr>
											<td align="center" colspan="5">
												<input type="submit" class="butt" value="Submit"  />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn"  />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn"  />
											</td>
										</tr>
										<tr>
											<td height="10px">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
