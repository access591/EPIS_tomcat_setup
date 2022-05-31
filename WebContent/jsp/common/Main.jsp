
<%@ page language="java"  import="com.epis.info.login.LoginInfo" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basepath"+basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>EPIS</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">       
   <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />   
  </head>  
  <frameset rows="100%" border=0>
     <frameset rows="11%,87%" border=0>
            <frame name=header src="/header.do" marginwidth=0 marginheight=0 scrolling="no" noresize>          
            <frame name=body src="/body.do" marginwidth=0 marginheight=0 scrolling="no" noresize>      
     </frameset>
  </frameset> 
</html>
