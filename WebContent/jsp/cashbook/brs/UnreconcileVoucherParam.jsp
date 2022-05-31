<%@ page import="com.epis.bean.cashbook.VoucherInfo,com.epis.utilities.ScreenUtilities" %>
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
		<title>AAI - Cashbook - Master - Unreconcile Voucher Param</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePathBuf%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePathBuf%>js/DateTime1.js"></script>
		
		<script language="javascript" type="text/javascript">		  
		    
		   <%--function  getReport(){
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/voucher.do?method=showReport&&voucherDt="+document.forms[0].voucherDt.value+"&&reportType="+document.forms[0].reportType.value;
				window.open(url,"UnreconcileVocherReport","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				clearData();
				return false;
			}
			
		   function clearData(){
		   		  document.forms[0].voucherDt.value="";
		   		
		   }--%>
		   
		   function checkvalues()
		   {
		   	if(document.forms[0].voucherDt.value=="")
		   	{
		   		alert("Please Enter Voucher Date[Mandatory]");
		   		document.forms[0].voucherDt.focus();
		   		return false; 
		   	}
		   	if(!convert_date(document.forms[0].voucherDt)){
						document.forms[0].voucherDt.select();
						return(false);
					   }
		   }
		  </script>
	</head>

	<body>
		<html:form action="/voucher.do?method=unreconcileVouchers" method="post" onsubmit="return checkvalues();" >
			<%=ScreenUtilities.screenHeader("brs.unreconcilevouchers")%>
			<table width="80%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="voucher.date" bundle="common" />
						:
					</td>
					<td align="left">
						<html:text property="voucherDt" maxlength="50" styleClass="tableText"/>
						&nbsp; <a href="javascript:show_calendar('forms[0].voucherDt')">
						 <img name="calendar" alt='calendar' border="0" src="<%=basePathBuf%>/images/calendar.gif" /></a>
						
					</td>
				</tr>
				
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.bankName" bundle="common" />
						:
					</td>				
					<td align="left">					
						<html:select property="accountNo" styleClass="TextField" style='width:220px' >
							<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails"  />										
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td colspan="5" align="center">
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
				