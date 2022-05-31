<%@ page language="java" import="com.epis.bean.investment.InvestmentReportsBean,com.epis.utilities.ScreenUtilities" %>
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
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 var vWinCal;
		  var categoryCd="",securityName="";
		 function validate(){
		 if(document.forms[0].securityCategoryId.value==""){
		    alert("Security Category is Mandatory");
		    return false;
		  }	
		  var index = document.forms[0].securityCategoryId.selectedIndex;
		    categoryCd = document.forms[0].securityCategoryId.options[index].text;
		 if(document.forms[0].fromDate.value==''){
			alert("From Date is Mandatory");
		    return false;		
					
		}
		if(document.forms[0].toDate.value==''){
			alert("To Date is Mandatory");
		    return false;		
					
		}
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
			if(document.forms[0].securityName.value!=''){	
			securityName=document.forms[0].securityName.value.replace(/%/g,'^');  	
		    }
	var url = "investmentReports.do?method=InvestmentStatisticsReport&categoryCd="+categoryCd+"&securityCategory="+document.forms[0].securityCategoryId.value+"&securityName="+securityName+"&fromDate="+document.forms[0].fromDate.value+"&toDate="+document.forms[0].toDate.value+"&reportType="+document.forms[0].reportType.value;
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
	<body onunload="closeWin();">
	<html:form action="/investmentReports" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("report.investmentstatistics")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
				<td width="15%" align=right class="tableTextBold" nowrap><font color=red>*</font>
						<bean:message key="arranger.seccategory" bundle="common"/> : 
						</td>
						<td width="15%" nowrap align=left>
							 <html:select  property="securityCategoryId" styleClass="TextField" style="width:170 px" tabindex="4" >
						<html:option value="">[Select One]</html:option>
						<html:options collection="seccatList" property="securityCategoryId" labelProperty="securityCategory"/>
					</html:select>
						</td>
						<td>&nbsp;</td>
					<td width="15%" align=right class="tableTextBold" nowrap>
						<bean:message key="ytm.securityname" bundle="common"/> : 
						</td>
						<td width="15%" nowrap align=left>
							 <html:select property="securityName" style="width: 120px;height:50 px" styleClass="TextField" >
							 <html:option value="">[Select one]</html:option>
                      <html:options name="secnamesList" property="securityName"
						labelProperty="securityName"  labelName="securityName" collection="secnamesList" />
                   </html:select>
						</td>
				</tr>
				<tr>
				<td width="15%" align=right class="tableTextBold" nowrap><font color=red>*</font>
						<bean:message key="report.fromdate" bundle="common"/> : 
						</td>
						<td width="15%" nowrap>
							 <html:text property="fromDate" styleClass="TextField" maxlength="11" styleId="fromDate"  size="12" />
							 <html:link href="javascript:showCalendar('fromDate');">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
						<td>&nbsp;</td>
						<td width="15%" align=right class="tableTextBold" nowrap><font color=red>*</font>
						<bean:message key="report.todate" bundle="common"/> : 
						</td>
						<td width="15%" nowrap>
							 <html:text property="toDate" styleClass="TextField" maxlength="11" styleId="toDate"  size="12" />
							 <html:link href="javascript:showCalendar('toDate');">
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
				<td colspan=6 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=6 align=center><html:submit styleClass="butt" property="Search" value="Ok" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
