
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
		response.setHeader("Content-Disposition", "attachment; filename=ytminvestmentmadereport.xls");
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
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle" >
						<tr>
							<th class="tblabel"><bean:message key="report.ytmtrust" bundle="common" />
							 &nbsp; <%=request.getParameter("frmDT")%>
							  <bean:message key="report.to" bundle="common" />
							 &nbsp;    <%=request.getParameter("toDT")%>
							  &nbsp;    <bean:message key="report.ytm" bundle="common" />
							</th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
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
							<bean:message key="sq.rateofinterest" bundle="common" />
							</td>
						   <td align="left" class="tblabel">
							<bean:message key="report.category" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.facevalueinrs" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.investmentdate" bundle="common" />
							</td>
						    <td align="left" class="tblabel" >
							<bean:message key="sq.ytminpercent" bundle="common" />
							</td>
							
						</tr>
						<c:set var="facevalue" scope="page" value="0"/>  
						<c:forEach var='record' items='${ytminvestList}'> 
                                 <tr >
                                 <td class="tableText"  nowrap><%=cnt++%></td> 
                                   
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.securityName}'/>&nbsp;</td>
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.interestRate}'/>&nbsp;</td> 
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.securityCategory}'/>&nbsp;</td>  
                                   <td class="tableText"  align=left nowrap><c:set var='facevalue' value='${record.faceValue+facevalue}'/><c:out value='${record.faceValue}' /> &nbsp;</td> 
                                   <td class="tableText"  align=left nowrap><c:out value='${record.investmentdate}'/>&nbsp;</td>
                                    <td class="tableText"  align=left nowrap><c:out value='${record.ytm}'/>&nbsp;</td>
                                   
                               </tr>
                               </c:forEach>
                               <tr>
                               <td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                               <td align="right" class="tblabel">Total:</td><TD align="left"> <c:out value='${facevalue}'/></td>
                               <td>&nbsp;</td><td>&nbsp;</td>
                               </tr>
                               <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=15><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
