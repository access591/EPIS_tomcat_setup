
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>
   							<tr>
								<td class="label" >Serial</br> Number</td>
								<td class="label">Account No</td>
								<td class="label">Employee Name</td>
								<td class="label" >Father's name or <br/>Husband name  <br/>( in case of married women)</td>
								<td class="label" >Basic wages, D.A.</td>
								<td class="label" >Age at Entry(Years)</td>
								<td class="label" nowrap="nowrap">Date of birth  </td>
								<td class="label" >Sex</td>
								<td class="label" >Date of entitlement<br> for membership</td>
								<td class="label" >Remarks</td>
								<td class="label" >Airport</br> Name</td>
								<td class="label" >Region</td>
								
							</tr>
  </body>
</html>
