<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=PartyInfo.xls");
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
	</head>
	<body background="body">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">			
				<tr>
					<td align="center">
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
							<tr>
<logic:equal value="html" parameter="reportType"  >
								<td rowspan="5">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
</logic:equal>															
								<td class="tblabel" align="center" valign="middle" colspan="5">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="5">
									<bean:message key="party.report.title" bundle="common" />
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
						<table width="80%" border="1" cellpadding="0" cellspacing="0" align="center" class="border">
						<thead>
							<tr>
								<td class="label" align="center" width="30%">
									<bean:message key="party.name" bundle="common" />
								</td>
								<td class="label" align="center" width="10%">
									<bean:message key="party.address" bundle="common" />
								</td>
								<td class="label" align="center" width="10%">
									<bean:message key="party.mobileNo" bundle="common" />
								</td>
								<td class="label" align="center" width="5%">
									<bean:message key="party.faxNo" bundle="common" />
								</td>
								<td class="label" align="center" width="10%">
									<bean:message key="party.contactNo" bundle="common" />
								</td>
								
								<td class="label" align="center" width="10%">
									<bean:message key="party.email" bundle="common" />
								</td>
		
							</tr>
						</thead>
							<logic:notEmpty  name="PartyList">
							<logic:iterate id="party" name="PartyList">							
							<tr>
								<td class="Data" align="left" width="30%">
									<bean:write name="party"  property="partyName"/>
								</td>
								<td class="Data" align="left" width="10%">
									<bean:write name="party"  property="partyDetail"/>
								</td>
								<td class="Data" align="left" nowrap width="10%">
									<bean:write name="party"  property="mobileNo"/>
								</td>
								
								<td class="Data" align="left" width="5%">
									<bean:write name="party"  property="faxNo"/>
								</td>
								
								<td class="Data" align="left" width="10%">
									<bean:write name="party"  property="contactNo"/>
								</td>
								
								<td class="Data" align="left" width="10%">
									<bean:write name="party"  property="emailId"/>
								</td>
							
							</tr>
							</logic:iterate>	
							</logic:notEmpty>
							<logic:empty name="PartyList">
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							</logic:empty>														
						</table>
					</td>
				</tr>
			
		</table>
	</body>
</html>
