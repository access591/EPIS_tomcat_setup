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
		<title>AAI - Cashbook - Report - Rejected Loans Param</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePathBuf%>js/calendar.js"></script>
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
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
		   function checkRecord()
		   {
		   if(document.forms[0].approvedDate.value!="")
		   {
		   if(!convert_date(document.forms[0].approvedDate)){
						document.forms[0].approvedDate.select();
						return(false);
					   }
			}
				
		   		var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var url = "/reports.do?method=getRejectedLoans&&pfId="+document.forms[0].pfId.value+"&transactionNo="+document.forms[0].transactionNo.value+"&approvedDate="+document.forms[0].approvedDate.value;
				window.open(url,"PaymentAndReceiptDetails","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
		   }
		   
		   
		  </script>
	</head>

	<body>
		<html:form  method="post" action="/reports.do?method=getRejectedLoans" onsubmit="javascript:return checkRecord()">
			<%=ScreenUtilities.screenHeader("rejectedcases.title")%>
			<table width="80%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				
				
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="rejectedcases.pfid" bundle="common" />
						:
					</td>				
					<td align="left">	
						<html:text property="pfId" style="width: 120px;" maxlength="10" tabindex="1" styleClass="TextField"/>
					</td>
					
					<td class="tableTextBold" align="right">
					<bean:message key="rejectedcases.transactionno" bundle="common"/>:
					</td>
					<td align="left">
					<html:text property="transactionNo" style="width: 120px;" maxlength="10" tabindex="2" styleClass="TextField"/>
					</td>
					
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
					<bean:message key="rejectedcase.approveddate" bundle="common"/>
					</td>
					<td align=left width="20%">
						<html:text property="approvedDate" styleId="approvedDate" style="width: 90px;" tabindex="3" styleClass="TextFieldFont" />
						<html:link href="javascript:showCalendar('approvedDate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
				</tr>
				
				
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td colspan="5" align="center">
						<input type="submit" class="butt" value="Submit" tabindex="5" />
						<input type="button" tabindex="6" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" />
						<input type="button" tabindex="7" class="butt" value="Cancel" onclick="javascript:history.back(-1)" />
					</td>
				</tr>
						
			 </table>
			<%=ScreenUtilities.searchFooter()%>			
	   </html:form>
	</body>
</html>
				