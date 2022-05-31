<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-fmt" prefix="fmt"%>
<jsp:useBean id="cf" class="com.epis.utilities.CurrencyFormat" scope="request"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;
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
		<title>Investment Register Report</title>
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
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="report.investmentregister.title" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						<tr>
							<td  align="left" valign="middle" class="tblabel" >
								<bean:message key="ytm.srno" bundle="common" />
							</td>
							<td  align="left" valign="middle" class="tblabel" >
								<bean:message key="partyamount.voucherno" bundle="common" />
							</td>
							<td  align="left" valign="middle" class="tblabel" >
							<bean:message key="jv.voucherdate" bundle="common"/>
							</td>
							<td align="right" class="tblabel" >
								<bean:message key="brs.creditamount" bundle="common" />
							</td>
							
							<!-- 
							<td align="center" class="tblabel" >
							<bean:message key="report.interestrecvdrs" bundle="common" /><bean:message key="report.firsthalf" bundle="common" />
							</td> -->
							
							<!-- 
							<td align="center" class="tblabel" >
								<bean:message key="report.interestrecvddate" bundle="common" /><bean:message key="report.firsthalf" bundle="common" />
							</td> -->
							<!-- 
						<td align="center" class="tblabel">
								<bean:message key="report.interestrecvdrs" bundle="common" /><bean:message key="report.secondhalf" bundle="common" />
							</td> -->
								<!-- 
						<td align="center" class="tblabel">
								<bean:message key="report.interestrecvddate" bundle="common" /><bean:message key="report.secondhalf" bundle="common" />
							</td> -->
							
						
						
						
						</tr>
						 <c:forEach var='arranger' items='${voucherDetails}'> 
                                 <tr >
                                 <td class="tableText" align="left" nowrap><%=cnt++%></td> 
                                 <td class="tableText" align="left" nowrap><c:out value='${arranger.voucherNo}'/></td> 
                                 <td class="tableText" align="left"  nowrap><c:out value='${arranger.voucherDt}'/></td> 
                                 <td class="tableText"  align="right" nowrap><bean:define id="facevalue" name="arranger" property="creditAmount" type="java.lang.String"/><%=cf.getDecimalCurrency(Double.parseDouble(facevalue))%></td> 
                                 
                               </tr>
                               </c:forEach>
                                <%if(cnt==1){
                               %>
                                <tr>
                               <td class="tableText"  align=center nowrap colspan=4><bean:message key="report.norecords" bundle="common" /></td>
                               </tr>
                               <%}%>
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
