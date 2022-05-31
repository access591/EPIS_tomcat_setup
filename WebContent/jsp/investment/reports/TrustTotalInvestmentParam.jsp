<%@ page language="java" import="com.epis.bean.investment.InvestmentReportsBean,com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<% String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'ArrangersSearch.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		 var vWinCal;
		 function validate(){
		 var date="";
		 if(document.forms[0].trustType.value==""){
		    alert("Trust Type is Mandatory");
		    return false;
		  }	
		  if(document.forms[0].fromDate.value==""){
		    alert("Date is Mandatory");
		    return false;
		  }
		 if(document.forms[0].fromDate.value!="")
			date=FormatDate(document.forms[0].fromDate.value);
	var url = "investmentReports.do?method=TrustTotalInvestmentReport&trusttype="+document.forms[0].trustType.value+"&date="+date+"&reportType="+document.forms[0].reportType.value;
     var sWidth=screen.width-10;
	 var sHeight=screen.height-120;
    
var windSize="width=222,height=222,top=222,left=222,status=no,scrollbars=yes,resizable=yes";
wind1=window.open(url,"popup1","menubar=yes,toolbar = yes,status=no,scrollbars=yes,resizable=yes,width="+sWidth+",height="+sHeight+",top=0,left=0");

wind1.window.focus();
			return false;
		 }
   
  function clearData(){
   document.forms[0].reset();
   
  }
 function closeWin() {
		if( vWinCal != null ) vWinCal.close();
		if( wind1 != null ) wind1.close();
		//if( win_id != null ) win_id.close();
	}
 
		</script>
	</head>
	<body onunload="closeWin();" >
	<html:form action="/investmentReports" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("report.totalinvestment")%>
			<table width="550" border="0" cellspacing="3" cellpadding="3">
				<tr>                    
			 	 <td  class="tableTextBold" width="50%" align=right nowrap ><font color=red>*</font>
			 	 <bean:message key="proposal.trusttype" bundle="common"/>:</td>
                 <td align=left >
                 <html:select  property="trustType" styleClass="TextField" style="width:120 px" >
						<html:option value="">[Select One]</html:option>
						<html:options collection="trustList" property="trustType" labelProperty="trustType"/>
					</html:select>
					</td> 
                      </tr>    
                      <tr>
				<td  align=right class="tableTextBold" nowrap><font color=red>*</font>
						<bean:message key="report.fromdate" bundle="common"/> : 
						</td>
						<td align=left nowrap>
							 <html:text property="fromDate" styleClass="TextField" maxlength="11" styleId="fromDate"  size="12" />
							 <html:link href="javascript:showCalendar('fromDate');">
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
			<td  colspan=6 align=center><html:submit styleClass="butt" property="Search" value="Ok" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
			
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</bdy>
</html>
