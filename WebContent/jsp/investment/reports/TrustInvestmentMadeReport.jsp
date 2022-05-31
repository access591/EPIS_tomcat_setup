
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" buffer="16kb"%>
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
		response.setHeader("Content-Disposition", "attachment; filename=TrustInvestmentmadereport.xls");
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
							<td>
							<table>
							<tr>
								<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
							<tr>
							<th class="tblabel"><%=request.getParameter("trusttype")%></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
							
						</table>
						</td></tr>
						<tr>
						<tr>
							<th class="tblabel"><bean:message key="report.investmentmade" bundle="common" /></th>
							<td class="tableText" align="center" valign="middle" colspan=7><%=request.getParameter("fyear")%></td>
							</tr></tr>
						</table>
						</td></tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
						<td>
						<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0"   class="border">
						<tr>
							<td  align="left" valign="middle" class="tblabel" >
								<bean:message key="report.slno" bundle="common" />
							</td>
						
							<td align="left" class="tblabel">
								<bean:message key="report.particulars" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.rateofinterest" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.purchaseprice" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.facevalueinrs" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.premium" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.aauredintt" bundle="common" />
							</td>
						<td align="left" class="tblabel" >
							<bean:message key="sq.investdate" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="sq.ytminpercent" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="report.type" bundle="common" />
							</td>
							<td align="left" class="tblabel" >
							<bean:message key="ytm.arranger" bundle="common" />
							</td>
						</tr>
						<c:forEach var='record' items='${investmadeList}'> 
                                 <tr >
                                 <td class="tableText"  nowrap><%=cnt++%></td> 
                                   
                               	   <td class="tableText"  align=left nowrap><c:out value='${record.securityName}'/>&nbsp;</td>
                               	   <td class="tableText"  align=right nowrap><c:out value='${record.interestRate}'/>&nbsp;</td>  
                                   <td class="tableText"  align=right nowrap><c:out value='${record.purchasePrice}'/> &nbsp;</td> 
                                   <td class="tableText"  align=left nowrap><c:out value='${record.faceValue}'/>&nbsp;</td>
                                   <td class="tableText"  align=left nowrap><c:out value='${record.premiumPaid}'/>&nbsp;</td>
                                   <td class="tableText"  align=left nowrap><c:out value='${record.amountRceived}'/>&nbsp;</td>
                                   <td class="tableText"  align=left nowrap><c:out value='${record.investmentdate}'/>&nbsp;</td>
                                   <td class="tableText"  align=left nowrap><c:out value='${record.ytm}'/>&nbsp;</td>
                                    <td class="tableText"  align=left nowrap><c:out value='${record.investmentType}'/>&nbsp;</td>
                                    <td class="tableText"  align=left nowrap><c:out value='${record.brokerName}'/>&nbsp;</td>
                               </tr>
                               </c:forEach> 
                               <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=12><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
						
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
