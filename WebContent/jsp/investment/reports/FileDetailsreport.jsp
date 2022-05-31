<%@ page language="java" import="com.epis.bean.investment.QuotationBean,com.epis.bean.admin.Bean"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int cnt=1;

%>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=filedetailreport.xls");
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
							<th class="tblabel"><bean:message key="report.filedetails" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="border">
						<tr>
							<td  align="left" valign="middle" class="tblabel" rowspan=2>
								<bean:message key="ytm.srno" bundle="common" />
							</td>
						
							<td align="left" class="tblabel" rowspan=2>
								<bean:message key="report.particulars" bundle="common" />
							</td>
							
							<td align="left" class="tblabel" rowspan=2>
							<bean:message key="sq.rateofinterest" bundle="common" />
							</td>
							
							<td align="left" class="tblabel" rowspan=2>
							<bean:message key="sq.investmentdate" bundle="common" />
							</td>
							
							<td align="center" class="tblabel" colspan="<bean:write name="ratioSize" property="SNo"/>">
							<bean:message key="sq.securitycategory" bundle="common" />
							</td>
							<td align="left" class="tblabel" rowspan=2>
							<bean:message key="sq.remarks" bundle="common" />
							</td>
							</tr>
							<tr>
							<c:forEach var='ratio' items='${ratioList}'> 
							<td align="left" class="tblabel" >
							<c:out value='${ratio.categoryCd}'/>&nbsp;
							</td>
							</c:forEach>
							<td align="left" class="tblabel" ><bean:message key="report.total" bundle="common" /></td>
							</tr>
						<logic:iterate name="records" id="Results">
						<bean:define id="masterrecords" name="Results" property="key"/>
						<bean:define id="subrecords" name="Results" property="value"/>
						<tr>
						 <td class="tableText"  nowrap><%=cnt++%></td> 
						<logic:iterate name="masterrecords" id="list" >
						<td><bean:write name="list" property="securityName"/>&nbsp </td>
						<td><bean:write name="list" property="interestRate"/>&nbsp </td>
						<td><bean:write name="list" property="investmentdate"/>&nbsp </td>
						
						<logic:iterate name="subrecords" id="sublist" >
						 <td><bean:write name="sublist" property="code"/>&nbsp </td>
						 </logic:iterate>
						 <td><bean:write name="list" property="remarks"/>&nbsp </td>
						 </logic:iterate>
						</tr>
						</logic:iterate>
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
