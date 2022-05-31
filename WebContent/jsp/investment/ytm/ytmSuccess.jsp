
<%@ page language="java" import="com.epis.utilities.ScreenUtilities,com.epis.utilities.StringUtility"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-c" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body><br><FIELDSET ><LEGEND class="epis">YTM Verification</LEGEND>
	<table width="70%" border=0 align="center">
		<tbody>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			
			<tr>
				<td colspan=4 align="center"><font face="arial" size=5>YTM Verification Successs</td></tr>
		
			
			</tbody>
	</table>
	<%=ScreenUtilities.screenFooter()%>
	</FIELDSET >
	</body>
</html>
