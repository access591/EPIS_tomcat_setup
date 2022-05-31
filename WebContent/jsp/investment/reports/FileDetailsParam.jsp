<%@ page language="java" import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Investment Made</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 var vWinCal;
		 function validate(){
		 
		 		if(document.forms[0].fromDate.value!=''){
						
					if(!convert_date(document.forms[0].fromDate)){
						document.forms[0].fromDate.select();
						return(false);
					   }
					}
				if(document.forms[0].toDate.value!=''){
					if(!convert_date(document.forms[0].toDate)){
						document.forms[0].toDate.select();
						return(false);
					   }
					}
				if(document.forms[0].fromDate.value!=''&&document.forms[0].toDate.value!='') {
					if(compareDates(document.forms[0].fromDate.value,document.forms[0].toDate.value)=="larger") {
						alert( "From date should not be greater than To date" );
						document.forms[0].fromDate.select();
						return false;
						}
					}
	
	var url = "investmentReports.do?method=reportFileDetails&frmDT="+document.forms[0].fromDate.value+"&toDT="+document.forms[0].toDate.value+"&trust="+document.forms[0].trustType.value+"&security="+document.forms[0].securityName.value+"&reportType="+document.forms[0].reportType.value;
     var sWidth=screen.width-10;
	 var sHeight=screen.height-120;
var windSize="width=222,height=222,top=222,left=222,status=no,scrollbars=yes,resizable=yes";
wind1=window.open(url,"popup1","menubar=yes,toolbar = yes,status=no,scrollbars=yes,resizable=yes,width="+sWidth+",height="+sHeight+",top=0,left=0");

wind1.window.focus();
			return false;
		 }
   
  function clearData(){
   document.forms[0].reset();
   document.forms[0].trustType.select();
   
  }
 function closeWin() {
		if( vWinCal != null ) vWinCal.close();
		if( wind1 != null ) wind1.close();
		//if( win_id != null ) win_id.close();
	}
	
		</script>
	</head>
	<body onunload="closeWin();" onload="document.forms[0].trustType.select();">
	<html:form action="/investmentReports.do?method=reportInvestStmt" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("report.filedetails")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			 <tr>                    
			 	 <td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="sq.trustname" bundle="common"/>:</td>
                 <td align=left><html:text size="25" styleClass="TextField" property="trustType" maxlength="25" /></td> 
                   <td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="sq.securityname" bundle="common"/>:</td>
                  <td style="width: 130px;" align=left><html:text size="25" styleClass="TextField" property="securityName" maxlength="25" />
                  </td>
                 <td>&nbsp;</td>
             </tr>
			<tr>
				<td  class="tableTextBold" width="50%" align=right><bean:message key="report.fromdate" bundle="common"/>:</td>
                  <td align=left width="20%" nowrap>
					<html:text size="12" property="fromDate" styleId="fromDate" styleClass="TextField" style="width:98 px" tabindex="1" maxlength="12" />	<html:link href="javascript:showCalendar('fromDate');">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>
				<td  class="tableTextBold" width="50%" align=right><bean:message key="report.todate" bundle="common"/>:</td>
                  <td align=left width="20%" nowrap>
					<html:text size="12" property="toDate" styleId="toDate" styleClass="TextField" style="width:98 px" tabindex="1" maxlength="12" />	<html:link href="javascript:showCalendar('toDate');">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>				
                  
                      </tr>   
                      
                      <tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" /> :
					</td>
					<td align="left">
						<html:select property="reportType"  styleClass="TextField" style="width: 125px">
						<html:option value="html">
							HTML
						</html:option>
						<html:option value="excel">
							Excel
						</html:option>
							
						</html:select>
					</td>
				</tr> 
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" value="Ok" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
			
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
