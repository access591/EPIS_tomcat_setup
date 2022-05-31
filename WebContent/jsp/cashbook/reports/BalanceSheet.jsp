<%@ page language="java" pageEncoding="UTF-8" buffer="32kb" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.epis.utilities.SQLHelper" %>
<%@ page import="com.epis.utilities.CommonUtil" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%-- <%@ taglib uri="/tags-c" prefix="c" %> --%>
<bean:define id="reportType" name="bean" property="reportType" type="java.lang.String"/>
<%	
	if("excel".equals(StringUtility.checknull(reportType))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=BalanceSheet.xls");	
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="css/aai.css" type="text/css" />
		<link rel="stylesheet" href="css/epis.css" type="text/css" />
		<script language="javascript" >
			function execute(trust,month,finyear,sch){
				if(finyear=='2010'){
					alert("The Data for this year does not Exists.");
					return false;
				}
				var swidth=screen.Width-100;
				var sheight=screen.Height-150;
				var url = "/reports.do?method=getScheduleReport&&trustType="+trust+"&&finYear="+finyear+"&&toMonth="+month+"&&scheduleType="+sch;
				window.open(url,"ScheduleBalanceReport","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
				return false;
			}
		</script>
		
	</head>
	<body background="body">
		<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" >
							<tr>
								<td rowspan="3">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="5">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="5">
									<bean:write name="bean" property="trustType" /> <bean:message key="ie.address" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="5">
									<bean:define id="tomonth" name="bean" property="toMonth" type="java.lang.String"/>
									<bean:message key="bs.title" bundle="common" /> AS ON <%=CommonUtil.getFullNameofMonth(tomonth)%> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="height: 1cm">
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="border">
							<tr>
								<td class="label" align="center" >
									LIABILITIES
								</td>
								<td class="label" align="center" >
									As Per Sch.
								</td>
								<logic:iterate id="data" name="bean" property="finYear">
									<td class="label" align="center" nowrap="nowrap">
										<bean:write name="data" /> 
									</td>
								</logic:iterate>
								<td class="label" align="center" >
									ASSETS
								</td>
								<td class="label" align="center" >
									As Per Sch.
								</td>
								<logic:iterate id="data" name="bean" property="finYear">
									<td class="label" align="center" nowrap="nowrap">
										<bean:write name="data" /> 
									</td>
								</logic:iterate>
							</tr>
							<logic:iterate id="iereport" name="report" >
								<tr>
									<logic:iterate id="strReport" name="iereport" >
									<td class="Data" align="right" nowrap="nowrap">
<logic:present name="strReport">
										<bean:define  id="dispString"  name="strReport" type="java.lang.String"/>
											<%=dispString%>		
</logic:present>																				
									</td>									
									</logic:iterate>	
								</tr>
							</logic:iterate>	
							<tr>
								<logic:iterate id="strReport" name="Totals" >
								<td class="label" align="right" nowrap="nowrap">
										<bean:write name="strReport" /> 
								</td>									
								</logic:iterate>	
							</tr>									
						</table>
					</td>
				</tr>
			</thead>
		</table>
	</body>
</html>
		
						
			