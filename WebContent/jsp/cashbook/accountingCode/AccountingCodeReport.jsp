<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	if("excel".equals(request.getParameter("reportType"))){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=AccountingCode.xls");
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
								<td rowspan="5">
									<img src="/images/logoani.gif" width="87" height="50" align="right" alt="" />
								</td>
								<td class="tblabel" align="center" valign="middle" colspan="7">
									<bean:message key="com.aai" bundle="common" />
								</td>
							</tr>
							<tr>
								<td align="center" class="tblabel" colspan="7">
									<bean:message key="accCode.report.title" bundle="common" />
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
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="border">
						<thead>
							<tr>
								<td class="label" align="center">
									<bean:message key="accCode.accCode" bundle="common" />
								</td>
								<td class="label" align="center">
									<bean:message key="accCode.name" bundle="common" />
								</td>
								<td class="label" align="center">
									<bean:message key="accCodeType.accCodeType" bundle="common" />
								</td>
								<td class="label" align="center">
									<bean:message key="accCode.openedDate" bundle="common" />
								</td>
<logic:iterate id="trusts" name="trustTypes">
								<td class="label" align="center">
									<bean:write name="trusts"  property="trustType"/>
								</td>
</logic:iterate>								
							</tr>
						</thead>
<logic:iterate id="acc" name="accList">							
							<tr>
								<td class="Data" align="center">
									<bean:write name="acc"  property="accountHead"/>
								</td>
								<td class="Data" align="left">
									<bean:write name="acc"  property="particular"/>
								</td>
								<td class="Data" align="left" nowrap>
									<bean:write name="acc"  property="type"/>
								</td>
								<td class="Data" align="center">
									<bean:write name="acc"  property="date"/>
								</td>
	<logic:iterate id="trusts" name="trustTypes">		
		<logic:iterate id="openBal" name="acc" property="openBalances">
			<bean:define id="openbaltrust" name="openBal" property="key" type="java.lang.String"/>
			<bean:define id="openbalAccountingCode" name="openBal" property="value" type="com.epis.bean.cashbook.AccountingCodeInfo"/>
			<logic:equal value="<%=openbaltrust%>" name="trusts" property="trustType">
				
								<td class="Data" align="right" nowrap="nowrap">
									<bean:write name="openbalAccountingCode"  property="amount" />
								</td>
			</logic:equal>
		</logic:iterate>
	</logic:iterate>								
							</tr>
</logic:iterate>			
<logic:empty name="accList">
							<tr>
								<td class="tbb" colspan="7" align="center" >
									<font color="red" size="4" ><bean:message key="common.norecords" bundle="common" /></font>
								</td>
							</tr>
</logic:empty>																	
						</table>
					</td>
				</tr>			
		</table>
	</body>
</html>
