<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body >
		<br>
		<fieldset>
			<legend class="epis">
				<bean:message key="accessrights.title" bundle="common" />
			</legend>
			<table width="70%" border="0" align="center">
				<html:form action="/accessRights.do?method=fwdtoAccessRights">
					<tbody>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="20%" class="epis" align="right" nowrap>
								<%=session.getAttribute("Type")%>
								:
							</td>
							<td width="20%">
								<html:select property="user" styleClass="listbox" style="width: 90px">
									<html:options name="UserBean" property="userId" labelProperty="userName" labelName="userId" collection="userID" />
								</html:select>
							</td>
							<td class="epis" width="20%" align="right">
								<bean:message key="accessrights.modules" bundle="common" />:
							</td>
							<td width="20%" class="epis">
								<html:select property="modules" styleClass="listbox" style="width:120 px ;">
									<html:options name="Bean" property="code" labelProperty="name" labelName="code" collection="moduleList" />
								</html:select>
							</td>
							<td width="20%" class="epis">
								<html:submit value="OK"></html:submit>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
					</tbody>
				</html:form>
			</table>
		</fieldset>
	</body>
</html>
