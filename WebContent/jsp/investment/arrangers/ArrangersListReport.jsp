
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
		response.setHeader("Content-Disposition", "attachment; filename=ArrangersList.xls");
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
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="arranger.reporttitle" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp</td>
							</tr>
						</table>
						</td></tr>
						<tr>
						<td>
						<table width="100%" border="1"  class="border" align="center" cellpadding="0" cellspacing="0" >
						<tr>
							<td  align="center" valign="middle" class="label" rowspan=2>
								<bean:message key="ytm.srno" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
							<bean:message key="arranger.name" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
								<bean:message key="arranger.officeaddresswithphone" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
							<bean:message key="arranger.delhiofficewithphone" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
								<bean:message key="arranger.pramotornamewithmobile" bundle="common" />
							</td>
							
							<td  align="center" class="label" valign="middle" rowspan=2>
								<bean:message key="arranger.delhiofficenamewithphone" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
							<bean:message key="arranger.email" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
								<bean:message key="arranger.networth" bundle="common" />
							</td>
						
							<td align="center" class="label" colspan=4>
							<bean:message key="arranger.registeredmembership" bundle="common" />
							</td>
						
							<td align="center" class="label" rowspan=2>
								<bean:message key="arranger.remarks" bundle="common" />
							</td>
						</tr>
						<tr rowpan=8>
						<td align="center" class="label">
								<bean:message key="arranger.sbi" bundle="common" />
							</td>
						<td align="center" class="label">
								<bean:message key="arranger.bse" bundle="common" />
							</td>
						<td align="center" class="label">
								<bean:message key="arranger.nse" bundle="common" />
							</td>
						<td align="center" class="label">
								<bean:message key="arranger.rbi" bundle="common" />
							</td>
						
						</tr>
						 <c:forEach var='arranger' items='${arrangerList}'> 
                                 <tr >
                                 <td class="Data"  nowrap ><%=cnt++%></td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.arrangerName}'/></td> 
                               	   <td class="Data"  nowrap><pre><c:out value='${arranger.regOffAddr}'/></pre><br>Ph no: <c:out value='${arranger.regPhoneNo}'/><br>Fax no: <c:out value='${arranger.regFaxNo}'/></td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.delhiOffAddr}'/><br><c:out value='${arranger.delhiOffPhNo}'/><br><c:out value='${arranger.delhiOffFaxNo}'/></td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.pramotorname}'/><br><c:out value='${arranger.pramotorContactNo}'/></td> 
                                   <td class="Data" nowrap><c:out value='${arranger.nameOfHeadOfDelhiOff}'/><br><c:out value='${arranger.delhiHeadOffMobileNo}'/><br><c:out value='${arranger.delhiHeadOffPhNo}'/></td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.email}'/> &nbsp;</td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.networthAmount}'/> &nbsp;</td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.regwithsebi}'/> <br><c:out value='${arranger.sebivaliddate}'/></td> </td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.regwithbse}'/> <br><c:out value='${arranger.bsevaliddate}'/></td> </td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.regwithnse}'/> <br><c:out value='${arranger.nsevaliddate}'/></td> </td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.regwithrbi}'/> <br><c:out value='${arranger.rbivaliddate}'/></td> </td> 
                                   <td class="Data"  nowrap><c:out value='${arranger.remarks}'/> &nbsp;</td> 
                                    
                                   
                               </tr>
                               </c:forEach>
					</table>
				</td>
			</tr>		
			</thead>	
			
		</table>
	</body>
</html>
