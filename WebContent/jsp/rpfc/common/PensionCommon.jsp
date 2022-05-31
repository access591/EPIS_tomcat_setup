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
  </head>
  
  		<frameset rows="7%,*,3%">
		<FRAME src="<%=basePath%>PensionView/common/Pensionheader.jsp" scrolling="no" noresize="noresize" frameborder="0" marginheight="0" marginwidth="0">
		<%if(session.getAttribute("usertype")!=null){
			String userType=(String)session.getAttribute("usertype");


			System.out.println("userType"+userType);
			if(userType.equals("User")){%>
			<frame src="<%=basePath%>PensionView/PensionMenu2.jsp" name="mainbody" noresize="noresize">
			<%}else{%>
			<frame src="<%=basePath%>PensionView/PensionMenu.jsp" name="mainbody" noresize="noresize">
			<%}}%>
		<FRAME src="<%=basePath%>PensionView/common/PensionFooter.jsp" noresize="noresize" frameborder="0" marginheight="0" marginwidth="0" scrolling="no">
	</frameset>  
</html>
