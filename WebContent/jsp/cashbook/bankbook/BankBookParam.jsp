<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epis.bean.cashbookDummy.AccountingCodeInfo" %>
<%@ page import="com.epis.service.cashbookDummy.AccountingCodeService" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString() ;
	AccountingCodeService as = new AccountingCodeService();		
	List accList = as.getBankAccountingCodeList();
	System.out.println(accList.size());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master - Bank Master</title>

		<meta http-equiv="pragma" content="no-cache" content="" />
		<meta http-equiv="cache-control" content="no-cache" content="" />
		<meta http-equiv="expires" content="0" content="" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" content="" />
		<meta http-equiv="description" content="This is my page" content="" />
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></script>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/calendar.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" ></script>
		<script type="text/javascript" >
			function checkRecord(){
				if(document.forms[0].fromDate.value == ""){
					alert("Please Enter From Date (Mandatory)");
					document.forms[0].fromDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].fromDate)){
					return false;
				}
				if(document.forms[0].toDate.value == ""){
					alert("Please Enter To Date (Mandatory)");
					document.forms[0].toDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].toDate)){
					return false;
				}
				convert_date(document.forms[0].fromDate);
				convert_date(document.forms[0].toDate);
				if(document.forms[0].accountCode.value == ""){
					alert("Please Select Account Code (Mandatory)");
					document.forms[0].accountCode.focus();
					return false;
				}
				
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "<%=basePathBuf%>Voucher?method=getBankBook&&accountCode="+
				document.forms[0].accountCode.value+"&&fromDate="+document.forms[0].fromDate.value+
				"&&toDate="+document.forms[0].toDate.value+"&&reportType="+document.forms[0].reportType.value
				+"&&reportbal="+document.forms[0].reportbal.value;
				var wind1 = window.open(url,"BankBook","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
			function popupWindow(mylink, windowname){
				//document.forms[0].bankName.value="";
				if (! window.focus){
					return true;
				}
				var href;
				if (typeof(mylink) == 'string'){
				   href=mylink;
				} else {
					href=mylink.href;
				}
				progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
				return true;
			}
			function bankDetails(bankName,accountNo){	
		   		document.forms[0].bankName.value=bankName;
		   		document.forms[0].accountNo.value=accountNo;
			}
			function details(accountCode,particular){	
		   		document.forms[0].accountCode.value=accountCode;
			}
		</script>
	</head>

	<body  onload="document.forms[0].fromDate.focus();">
		<form name="bank" action="#" method="post"  onsubmit="javascript:return checkRecord()">
			<%=ScreenUtilities.screenHeader("Bank Book [Param]")%><table width="75%" border="0" align="center" cellpadding="1" cellspacing="1">
				
				

				
										<tr>
											<td >&nbsp;</td>
											<td class="tableTextBold" align=right width="100px">
												<font color="red">&nbsp;*&nbsp;</font>From : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='fromDate' maxlength="11" size="18" />
												&nbsp; <a href="javascript:show_calendar('forms[0].fromDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
												
											</td>
										<td >&nbsp;</td>
											<td class="tableTextBold" align=right width="100px"  nowrap>
												<font color="red">&nbsp;*&nbsp;</font>To : 
											</td>
											<td align="left" nowrap>
												<input class="TextField" name='toDate' maxlength="11" size="18" />
												&nbsp; <a href="javascript:show_calendar('forms[0].toDate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
											</td>
											<td >&nbsp;</td>
										</tr>
										<tr>
										<td >&nbsp;</td>
											<td class="tableTextBold" align=right>
												<font color="red">&nbsp;*&nbsp;</font>Bank A/c Code : 
											</td>
											<td align="left">
												<select class="TextField" name=accountCode>
													<option value="">Select One</option>
<%
	for(int i=0;i<accList.size();i++){
		AccountingCodeInfo acinfo = (AccountingCodeInfo)accList.get(i);
%>												
													<option value="<%=acinfo.getAccountHead()%>"><%=acinfo.getParticular()+" -- "+acinfo.getAccountHead()%></option>
<%
	}
%>														
												</select>
											<!-- 	<input type="text" name="accountCode" maxlength="25" size="15" readonly="readonly"  />
												&nbsp;
												<img style='cursor:hand' src="<%=basePath%>images/search1.gif"  onclick="popupWindow('<%=basePath%>cashbook/AccountInfo.jsp?type=BankBook','AAI');" alt="Click The Icon to Select Bank Account Code Master Records"  />
											-->
											<td >&nbsp;</td>
											
										
											<td class="tableTextBold" align=right nowrap>
												Report Type : 
											</td>
											<td align="left" nowrap >
												<select class="TextField" name='reportType' style="WIDTH:70px">
												<Option value="html">HTML</Option>
												<Option value="excel">MS-Excel</Option>
												</select>
											</td>
											<td >&nbsp;</td>
										</tr>
										<tr>
										<td >&nbsp;</td>
											<td class="tableTextBold" align=right nowrap>
												Closing Balances  :
											</td>
											<td align="left" nowrap >
												<select class="TextField" name='reportbal' style="WIDTH:70px">
												<Option value="D">Daily</Option>
												<Option value="M">Monthly</Option>
												<Option value="Y">Yearly</Option>
												</select>
											</td>											
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<tr>
											<td align="center" nowrap colspan=7>
												<input type="submit" class="butt" value="Submit"  />
												<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />
												<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>
									
			</table><%=ScreenUtilities.screenFooter()%>
		</form>
	</body>
</html>
