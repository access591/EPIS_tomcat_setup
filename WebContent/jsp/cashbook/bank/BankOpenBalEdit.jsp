<%@ page import="com.epis.bean.cashbook.BankOpenBalInfo,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

String basePath = basePathBuf.toString() ;
BankOpenBalInfo info = (BankOpenBalInfo) request.getAttribute("info");
DecimalFormat df = new DecimalFormat("#####.##");
System.out.println(info.getAmountType());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master - Bank Opening Balance Edit</title>
		<meta http-equiv="pragma" content="no-cache" content="" />
		<meta http-equiv="cache-control" content="no-cache" content="" />
		<meta http-equiv="expires" content="0" content="" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" content="" />
		<meta http-equiv="description" content="This is my page" content="" />
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/calendar.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" type=""></script>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<script type="text/javascript" type="">
			function checkAccount(){
				if(document.forms[0].openedDate.value == ""){
					alert("Please Enter Date (Mandatory)");
					document.forms[0].openedDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].openedDate)){
					return false;
				}
   		  		if(document.forms[0].amount.value == ""){
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}
				if(!ValidateFloatPoint(document.forms[0].amount.value,15,4)){
					alert("Please Enter A Valid Amount (Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}
			}
		</script>
	</head>
	<body  onload="document.forms[0].openedDate.focus();">
		<form name="account" action="<%=basePathBuf%>BankOpeningBal?method=updateOpenBalRecord" onsubmit="javascript : return checkAccount()" method="post" action="">
			<%=ScreenUtilities.screenHeader("Bank Opening Balance [Edit]")%><table width="50%" border="0" align="center" cellpadding="0" cellspacing="0">
				
							
										<tr>
											<td class="tableTextBold"  width="100px" align="right">
												Bank Name
											</td>
											<td  align="left">
												<%=info.getBankName()%>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold"  width="100px" align="right">
												Account No
											</td>
											<td  align="left">
												<%=info.getAccountNo()%>
												<input type="hidden" name='accountNo' value='<%=info.getAccountNo()%>' />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold"  width="100px" align="right">
												<font color="red">&nbsp;*&nbsp;</font>Date
											</td>
											<td align="left">
												<input class="TextField" name='openedDate' maxlength="11" size="18" value='<%=info.getOpendate()%>' tabindex="1"/>
												&nbsp; <a href="javascript:show_calendar('forms[0].openedDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" ></a>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold"  align="right">
												<font color="red">&nbsp;*&nbsp;</font>Amount 
											</td>
											<td align="left">
												<input class="TextField" type="text" name="amount" maxlength="14" tabindex="2" size="18"  onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();"/>
												&nbsp;
												<select class="TextField" name='amountType' style='width:40px'><option value='DR'  <%="DR".equals(info.getAmountType())?"Selected":""%>>Dr.</option> <option value='CR' <%="CR".equals(info.getAmountType())?"Selected":""%>>Cr.</option></select>
											</td>
										</tr>
										<tr>
											<td class="tableTextBold" align="right"> 
												Details 
											</td>
											<td align="left">
												<input class="TextField" type="text" name="details" maxlength="150" tabindex="3" size="18" value='<%=info.getDetails()%>'/>
											</td>
										</tr>
										<tr>
											<td height="10px">
											</td>
										</tr>
										<tr>
											<td align="center" colspan="2">
												<input type="submit" class="butt" value="Submit" tabindex="4"/>
												<input type="button" class="butt" value="Reset" tabindex="5" onclick="javascript:document.forms[0].reset()"  />
												<input type="button" class="butt" value="Cancel" tabindex="6" onclick="javascript:history.back(-1)"  />
											</td>
										</tr>
										
									</table><%=ScreenUtilities.searchFooter()%>
								
		</form>
	</body>
</html>
