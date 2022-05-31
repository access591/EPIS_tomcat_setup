<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'header.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>/css/leftmenu.css" rel="stylesheet" type="text/css" />
  </head>  
  <body>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="left" valign="top">
	        <table width="100%" height="66" border="0" cellpadding="0" cellspacing="0" background="images/top_bg.gif">
	          <tr>
	            <td width="80%" align="left" valign="middle"><img src="images/title.gif" width="498" height="45" hspace="15" /></td>
	            <td width="20%" align="right" valign="middle"><img src="images/epis_logo.gif" width="99" height="32" hspace="15" /></td>
	          </tr>
	        </table>
	    </td>
	  </tr>  
	</table>
  </body>
</html>
