
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;
%>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=redumptionbondsecuritiresreport.xls");
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
							<tr>
							<th class="tblabel" align="center" valign="middle" colspan=7>
							      <%=request.getParameter("trust")%>
							</th>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr><td>&nbsp;</td></tr>
						<tr>
							<th class="tblabel"><bean:message key="report.redumptionsecurity" bundle="common" />
							<bean:message key="report.redumptionyear" bundle="common" />&nbsp;
							  <%=request.getParameter("year")%>
							</th>
							</tr>
							<tr><td>&nbsp;</td></tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						<tr>
							<td  align="left" valign="middle" class="tblabel" >
								<bean:message key="ytm.srno" bundle="common" />
							</td>
						
							<td align="left" class="tblabel">
								<bean:message key="report.particulars" bundle="common" />
							</td>
							<td align="left" class="tblabel">
							<bean:message key="report.facevalue" bundle="common" />
							</td>
							<td align="left" class="tblabel">
							<bean:message key="report.amountrecvd" bundle="common" />
							</td>
						   <td align="left" class="tblabel">
							<bean:message key="report.duedateredumption" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.recvedateredumption" bundle="common" />
							</td>
							</tr>
						<c:set var="facevalue" scope="page" value="0"/>  
						<c:set var="amount" scope="page" value="0"/>  
						<c:forEach var='record' items='${redumptionBondList}'> 
                                 <tr >
                                 <td class="tableText"  nowrap><%=cnt++%></td> 
                                   
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.securityName}'/>&nbsp;</td>
                               	   <td class="tableText"  align=left nowrap><c:set var='facevalue' value='${record.faceValue+facevalue}'/><fmt:formatNumber type="NUMBER" value='${record.faceValue}'/>&nbsp;</td> 
                               	   <td class="tableText"  align=left nowrap><c:set var='amount' value='${record.amountRceived+amount}'/><fmt:formatNumber type="NUMBER" value='${record.amountRceived}'/>&nbsp;</td> 
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.redemptionDate}'/>&nbsp;</td>  
                                   <td class="tableText"  align=left nowrap><c:out value='${record.redumptionDueDate}'/>&nbsp;</td>
                               </tr>
                               </c:forEach>
                               <tr>
                               <td >&nbsp;</td>
                               <td align="right" class="tblabel">Total:</td><TD align="left"> <fmt:formatNumber type="NUMBER" value='${facevalue}'/></td>
                               <TD align="left"> <fmt:formatNumber type="NUMBER" value='${amount}'/></td>
                               <td>&nbsp;</td><td>&nbsp;</td>
                               </tr>
                               <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=6><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
