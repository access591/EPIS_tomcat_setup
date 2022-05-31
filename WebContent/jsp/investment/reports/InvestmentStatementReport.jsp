
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;
%>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=PartyInfo.xls");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />		
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
	</head>

	<body background="body">

		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="report.investstatement" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						<tr>
							<td  align="center" valign="middle" class="tblabel" >
								<bean:message key="ytm.srno" bundle="common" />
							</td>
						
							<td align="center" class="tblabel">
							<bean:message key="sq.rateofinterest" bundle="common" />
							(%)</td>
						
							<td align="center" class="tblabel">
								<bean:message key="sq.securityname" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
							<bean:message key="sq.disincno" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
								<bean:message key="sq.rating" bundle="common" />
							</td>
							
							<td  align="center" class="tblabel" valign="middle" >
								<bean:message key="sq.dematno" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
							<bean:message key="sq.investmentdate" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
								<bean:message key="report.investment" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
							<bean:message key="report.redemptionyear" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
								<bean:message key="report.investment" bundle="common" />
							</td>
							
							
							<td  align="center" class="tblabel" valign="middle" >
								<bean:message key="sq.maturitydate" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
							<bean:message key="report.fcbond" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
								<bean:message key="report.nobond" bundle="common" />
							</td>
						
							<td align="center" class="tblabel" >
							<bean:message key="report.maturitydetails" bundle="common" />
							</td>
						<td align="center" class="tblabel" >
								<bean:message key="sq.investmentmode" bundle="common" />
							</td>
						<td align="center" class="tblabel">
						<bean:message key="investmentRegister.interestdate" bundle="common" />
						</td>
							<td align="center" class="tblabel" >
								<bean:message key="report.annuminterest" bundle="common" />
							</td>
						</tr>
						<c:forEach var='record' items='${recordList}'> 
                                 <tr >
                                 <td class="tableText"  nowrap><%=cnt++%></td> 
                                   <td class="tableText"  nowrap><c:out value='${record.interestRate}'/>&nbsp;</td> 
                               	   <td class="tableText"  nowrap><c:out value='${record.securityName}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.disIncNo}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.rating}'/>&nbsp;</td> 
                                   <td class="tableText" nowrap><c:out value='${record.dematno}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.investmentdate}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.investmentFaceValue}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.redemptionDate}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.investmentFaceValue}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.maturityDate}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.faceValue}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.numberOfUnits}'/>&nbsp;</td>
                                   <td class="tableText"  nowrap><c:out value='${record.remarks}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${record.investmentMode}'/>&nbsp;</td>
                                   <td class="tableText"  nowrap><c:out value='${record.interestDate}'/>&nbsp;</td>
                                   <td class="tableText"  nowrap><c:out value='${record.interestRate}'/>&nbsp;</td>
                                    
                                   
                               </tr>
                               </c:forEach>
                               <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=16><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
