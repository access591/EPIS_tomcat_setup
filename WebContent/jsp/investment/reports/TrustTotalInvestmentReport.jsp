
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb" import="com.epis.utilities.CurrencyFormat"%>
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
		response.setHeader("Content-Disposition", "attachment; filename=trusttotalinvestmentreport.xls");
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
						<tr>
							<th class="tblabel"><bean:message key="report.trusttotal" bundle="common" />
							 &nbsp; <%=request.getParameter("trust")%>
							  &nbsp;    <bean:message key="report.ason" bundle="common" />
							   <%=request.getParameter("date")%>
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
							<td align="center" class="tblabel" colspan="5">
								<bean:message key="report.investment" bundle="common" />
							</td>
							</tr>
							<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td align="left" class="tblabel">
							<bean:message key="report.openingbalance" bundle="common" /> <%=request.getParameter("date")%>
							</td>
						   <td align="left" class="tblabel">
							<bean:message key="report.add" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.total" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.less" bundle="common" />
							</td>
						    <td align="left" class="tblabel" >
							<bean:message key="report.investtotal" bundle="common" /> <%=request.getParameter("date")%>
							</td>
							
						</tr>
						<c:set var="amountrceived" scope="page" value="0"/> 
						<c:set var="purchase" scope="page" value="0"/> 
						<c:set var="openingbalance" scope="page" value="0"/>  
						<c:set var="basebal" scope="page" value="0"/>  
						<c:set var="totalbal" scope="page" value="0"/>  
						<c:forEach var='record' items='${trustTotalInvestList}'> 
                                 <tr >
                                 <td class="tableText"  nowrap><%=cnt++%></td> 
                                   
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.securityCategory}'/>&nbsp;</td>
                               	   <td class="tableText"  align=left nowrap><c:set var='openingbalance' value='${record.openingbal+openingbalance}'/><fmt:formatNumber type="NUMBER" value='${record.openingbal}'/>&nbsp;</td> 
                               	   <td class="tableText"  align=left nowrap><c:set var='purchase' value='${record.purchasePrice+purchase}'/><fmt:formatNumber type="NUMBER" value='${record.purchasePrice}'/>&nbsp;</td>  
                                   <td class="tableText"  align=left nowrap><c:set var='amountrceived' value='${record.amountRceived+amountrceived}'/><fmt:formatNumber type="NUMBER" value='${record.amountRceived}' /> &nbsp;</td> 
                                   <td class="tableText"  align=left nowrap><c:set var='basebal' value='${record.baseamount+basebal}'/><fmt:formatNumber type="NUMBER" value='${record.baseamount}'/>&nbsp;</td>
                                    <td class="tableText"  align=left nowrap><c:set var='totalbal' value='${record.investmentFaceValue+totalbal}'/><fmt:formatNumber type="NUMBER" value='${record.investmentFaceValue}'/>&nbsp;</td>
                                   
                               </tr>
                               </c:forEach>
                               <tr>
                               <td >&nbsp;</td>
                               <td align="right" class="tblabel">Total:</td>
                               <TD align="left"> <fmt:formatNumber type="NUMBER" value='${openingbalance}'/></td>
                               <TD align="left"> <fmt:formatNumber type="NUMBER" value='${purchase}'/></td>
                               <TD align="left"> <fmt:formatNumber type="NUMBER" value='${amountrceived}'/></td>
                                <TD align="left"> <fmt:formatNumber type="NUMBER" value='${basebal}'/></td>
                               <TD align="left"> <fmt:formatNumber type="NUMBER" value='${totalbal}' /></td>
                               </tr>
                               <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=8><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
