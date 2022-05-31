<%@ page language="java" import="com.epis.bean.investment.InvestmentReportsBean,com.epis.utilities.ScreenUtilities,com.epis.bean.investment.SecurityCategoryBean" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>

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
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 var vWinCal;
		 function validate(){
		 var securityName="";
		     if(document.forms[0].securityName.value !=""){
			for(i=0;i<document.forms[0].securityName.length;i++)
			{
				if(document.forms[0].securityName[i].selected==true)
				securityName+="'"+document.forms[0].securityName[i].value+"',";
				
			}
			securityName=securityName.substring(0,securityName.length-1);
			document.forms[0].securityNames.value=securityName.replace(/%/g,'^');  
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
				if(document.forms[0].investmentDate.value!=''){
					if(!convert_date(document.forms[0].investmentDate)){
						document.forms[0].investmentDate.select();
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
				if(document.forms[0].maturityMonth.value!=""||document.forms[0].maturityYear.value!="")
				{
					if(document.forms[0].maturityYear.value=="")
					{
						alert("Please Maturity Year");
						document.forms[0].maturityYear.focus();
						return false;
					}
					
					if(document.forms[0].maturityMonth.value=="")
					{
						alert("Please Maturity Month");
						document.forms[0].maturityMonth.focus();
						return false;
					}
				}
				
				
				
			
	var url = "investmentReports.do?method=InvestmentGroupRegisterReport&investDate="+document.forms[0].investmentDate.value+"&securityNames="+document.forms[0].securityNames.value+"&fromDate="+document.forms[0].fromDate.value+"&toDate="+document.forms[0].toDate.value+"&securityCd="+document.forms[0].securityCategoryId.value+"&interestMonth="+document.forms[0].interestMonthList.value+"&maturityMonth="+document.forms[0].maturityMonth.value+"&maturityYear="+document.forms[0].maturityYear.value+"&reportType="+document.forms[0].reportType.value;
	
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
	<%=ScreenUtilities.screenHeader("Investment Register")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
				<td width="15%" align=right class="tableTextBold" nowrap>
						<bean:message key="report.fromdate" bundle="common"/> : 
						</td>
						<td width="15%" nowrap>
							 <html:text property="fromDate" styleClass="TextField" maxlength="11" styleId="fromDate"  size="15" />
							 <html:link href="javascript:showCalendar('fromDate');">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
						<td>&nbsp;</td>
						<td width="15%" align=right class="tableTextBold" nowrap>
						<bean:message key="report.todate" bundle="common"/> : 
						</td>
						<td width="15%" nowrap>
							 <html:text property="toDate" styleClass="TextField" maxlength="11" styleId="toDate"  size="15" />
							 <html:link href="javascript:showCalendar('toDate');">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
				</tr>
			 <tr>                    
			 	 <td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="sq.investmentdate" bundle="common"/>:</td>
                 <td align=left width="20%" >
					<html:text property="investmentDate" styleClass="TextField" maxlength="11" styleId="investmentDate"  size="15" />
							 <html:link href="javascript:showCalendar('investmentDate');">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
				</td>		
                 
                 <td>&nbsp;</td>
            
				<td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="party.groupname" bundle="common"/>:</td>
                  <td style="width: 145px;" align=left>
                   <html:select property="securityName" style="width: 120px;height:50 px" styleClass="TextField" multiple="true" size="3">
                      <html:options name="secList" property="groupName"
						labelProperty="groupName"  labelName="groupName" collection="secList" />
                   </html:select>
                   <html:hidden property="securityNames" />
                 </td>
                      </tr>  
                <tr>
                <td  class="tableTextBold" align=right nowrap>
						
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="securityCategoryId"  styleClass="TextField" style="width:100px" tabindex="6">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>
					<td>&nbsp;</td>
					<td class="tableTextBold" align=right nowrap>
					<bean:message key="investmentRegister.interestpaymentmonth" bundle="common"/>
					</td>
					
					<td align=left>
					<html:select property="interestMonthList"   styleClass="TextField" tabindex="7" style="width:70px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthList" property="code"  labelProperty="name" />
						</html:select></td>
			</tr> 
			<tr id="maturity">
				<td class="tableTextBold" align=right nowrap>
					<bean:message key="investmentRegister.maturitymonth" bundle="common"/>
				</td>
				<td align=left>
				<html:select property="maturityMonth" styleClass="TextField" tabindex="8" style="width:70px">
				<html:option value="">[select One]</html:option>
							<html:options collection="maturityMonthList" property="code"  labelProperty="name" />
						</html:select>&nbsp;
						<html:select property="maturityYear" styleClass="TextField" tabindex="9" style="width:70px">
						<html:option value="">[select One]</html:option>
							<html:options collection="maturityYearList" property="code"  labelProperty="name" />
						</html:select>
						</td>
					<td colspan=3>&nbsp;</td>
				
				
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
