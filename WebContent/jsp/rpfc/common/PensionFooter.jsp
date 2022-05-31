<!--
/*
  * File       : PensionCommon.jsp
  * Date       : 13/07/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
   <script type="text/javascript">
    function popupWindow(mylink, windowname){
				if (! window.focus){
					return true;
				}
				var href;
				if (typeof(mylink) == 'string'){
				   href=mylink;
				} else {
					href=mylink.href;
				}
				progress=window.open(href, windowname, ' left=0,top=490,width=100,height=200,statusbar=yes,scrollbars=yes ');
				return true;
			}
		</SCRIPT>
  </head>
  <body>
  <form>
  <table width="100%" border="0"  style="background-image: url(<%=basePath%>PensionView/images/navbg.gif)">
   <!-- 	<tr><td align="left"> <a  style="cursor:hand" onclick="popupWindow('<%=basePath%>PensionView/ModuleList.jsp','AAI');"><font color="blue"><h5><b>Secondary Module</b><h5></font></a></td> -->
   	<td align="right" valign="top"><font color="white" size="1" face="Arial">@ Copyright 2009, All rights reserved</font></td><tr>
   </table>
   </form>
    </body>
</html>
