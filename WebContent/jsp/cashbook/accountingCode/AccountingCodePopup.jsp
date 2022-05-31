
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities,com.epis.services.cashbook.AccountingCodeTypeService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epis.bean.cashbook.AccountingCodeTypeBean" %>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

String basePath = basePathBuf.toString() ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>AAI - Cashbook - Master -Account Code Master</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function checkAccount(){
				if(document.forms[0].accountCode.value == ""){
					alert("Please Enter Account Code (Mandatory)");
					document.forms[0].accountCode.focus();
					return false;
				}
				if(document.forms[0].accountName.value == ""){
					alert("Please Enter Particular(Mandatory)");
					document.forms[0].accountName.focus();
					return false;
				}
				if(document.forms[0].openedDate.value != ""){
					if(!convert_date(document.forms[0].openedDate)){
						return false;
					}
					convert_date(document.forms[0].openedDate);
				}
				if(document.forms[0].amount.value == "")
					document.forms[0].amount.value = "0";
				if(document.forms[0].amountI.value == "")
					document.forms[0].amountI.value = "0";
				if(document.forms[0].amountN.value == "")
					document.forms[0].amountN.value = "0";
				if(document.forms[0].openedDate.value != "" && (document.forms[0].amount.value == "0" && document.forms[0].amountI.value == "0" && document.forms[0].amountN.value == "0")){
					alert("Please Enter Opening Balance(Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}
				if(document.forms[0].openedDate.value == "" && (document.forms[0].amount.value != "0" || document.forms[0].amountI.value != "0" || document.forms[0].amountN.value != "0")){
					alert("Please Enter Opened Date(Mandatory)");
					document.forms[0].openedDate.focus();
					return false;
				}
			}
			function redirect(){
<%
if("Y".equals(request.getParameter("redirect"))){
%>
				window.opener.details('<%=request.getParameter("accHead")%>','<%=request.getParameter("particular")%>');	
				window.close();
<%	
}
%>			
			}
		</script>
	</head>
	<body class="BodyBackground1" onload="redirect();document.forms[0].accountCode.focus();" >
		<FORM name="account" action="<%=basePathBuf%>AccountingCode?method=addAccountRecord" onsubmit="javascript : return checkAccount()" method=post>
		<%=ScreenUtilities.screenHeader("accCode.title")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align=center class=tableTextBold>
						<font color=red size="2"><%=(request.getParameter("error")==null || "null".equals(request.getParameter("error"))?"":"Error : "+request.getParameter("error"))%>
						<BR>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" >
							<tr>
								<td height="15%">
									<table align="center" border="0">
										<tr>
											<td class="tableTextBold" align="right">
												Account Code <font color="red">&nbsp;*</font>
												<input type=hidden name='popUp' value='Y' >
											</td>
											<td align="left">
												<input type="text" name="accountCode" maxlength="25" tabindex="1" Class="tableText" >
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												Particular <font color="red">&nbsp;*</font>
											</td>
											<td align="left">
												<input type="text" name="accountName" maxlength="50" tabindex="2" Class="tableText" >
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" width="100px" align="right">
												Opened Date
											</td>
											<td align="left">
												<input name='openedDate' maxlength="11" size="18" tabindex=2 Class="tableText" />
												&nbsp; <a href="javascript:show_calendar('forms[0].openedDate')"> 
												<img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align=right>
												Trust Type
											</td>
											<td class="tableTextBold" align="left">
												Opening Balance
											</td>
											
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												AAI EPF
											</td>
											<td align="left">
												<input type="text" Class="tableText"  name="amount" value=0 maxlength="14" tabindex="3" size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();"/>
												&nbsp;
												<select name='amountType' style='width:40px' Class="tableText" ><option value='DR'>Dr.</option> <option value='CR'>Cr.</option></select>
											</td>											
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												IAAI ECPF
											</td>
											<td align="left">
												<input type="text" Class="tableText"  name="amountI" value=0 maxlength="14" tabindex="3" size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();"/>
												&nbsp; 
												<select name='amountTypeI' style='width:40px' Class="tableText" ><option value='DR'>Dr.</option> <option value='CR'>Cr.</option></select>
											</td>											
										</tr>
										<tr>
											<td class="tableTextBold" align="right">
												NAA ECPF
											</td>
											<td align="left">
												<input type="text" Class="tableText"  name="amountN" value=0 maxlength="14" tabindex="3" size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();"/>
												&nbsp;
												<select name='amountTypeN' style='width:40px' Class="tableText" ><option value='DR'>Dr.</option> <option value='CR'>Cr.</option></select>
											</td>
											
										</tr>
										<tr>
											<td class="tableTextBold">
												Type of Account
											</td>
											<td align="left">
												<select name="accountType" Style='width:130px' tabindex="3" Class="tableText" >
													<option value="">
														[Select One]
													</option>
													<% 
													List accTypes = AccountingCodeTypeService.getInstance().getAccountingCodeTypes();
													for(int i=0;i<accTypes.size();i++ ){
														AccountingCodeTypeBean bean = (AccountingCodeTypeBean)accTypes.get(i);
														%>
														<option value="<%=bean.getCode()%>"><%=bean.getAccountCodeType()%></option>
														
														<%
													}
													%>
												</select>
											</td>
										</tr>
										<tr>
											<td height="10px">
											</td>
										</tr>
										<tr>
											<td align="center" colspan="2">
												<input type="submit" class="btn" value="Submit" tabindex="4">
												<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
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
		</form>
	</body>
</html>
