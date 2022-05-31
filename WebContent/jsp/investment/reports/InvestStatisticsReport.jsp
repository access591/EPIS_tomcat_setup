
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
		response.setHeader("Content-Disposition", "attachment; filename=SCInvestmentStatistics.xls");
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

		<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
			
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
							<th class="tblabel"><bean:message key="report.investstatistics" bundle="common" />&nbsp;<%=request.getParameter("category")%></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr><td>&nbsp;</td></tr>
						<tr>
						<td align="right" valign="middle" class="tblabel" width="15%"><bean:message key="sq.securityname" bundle="common"/>:</td>
						<td>&nbsp;<%=request.getParameter("sname").replaceAll("\\^","%")%></td>
						<td>&nbsp;</td></tr>
						<tr><td>&nbsp;</td></tr>
						</table>
						</td>
						</tr>
						<tr>
						<td>
						<table width="700" border="1" align="center" cellpadding="0" cellspacing="0">
						<tr>
						
							<td  align="center" valign="middle" class="tblabel" >
								<bean:message key="sq.facevalueinrs" bundle="common" /><BR>(<%=request.getParameter("fyear")%>)
							</td>
						
							<td align="center" class="tblabel">
								<bean:message key="report.totalinvestmentin" bundle="common" />&nbsp;<%=request.getParameter("category")%>
							</td>
							
							<td align="center" class="tblabel">
							<bean:message key="report.percentinvest" bundle="common" />
							</td>
						</tr>
						<c:forEach var='record' items='${investstatistics}'> 
                                 <tr >
                               	   <td class="tableText"  align=right nowrap><c:out value='${record.faceValue}'/>&nbsp;</td>
                
                               	     <td class="tableText"  align=right nowrap><c:out value='${record.purchasePrice}'/> &nbsp;</td> 
                               	   <td class="tableText"  align=right nowrap><c:out value='${record.investmentFaceValue}'/>&nbsp;</td>  
                               </tr>
                               </c:forEach>
                               <%if(cnt==0){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=5><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
