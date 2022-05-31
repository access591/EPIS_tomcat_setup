<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
<style>
p.page{ page-break-after: always; }
</style>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
</head>
<%
	ArrayList form10DList=new ArrayList();
	if(request.getAttribute("form10DList")!=null){
		form10DList=(ArrayList)request.getAttribute("form10DList");
		request.setAttribute("perForm10DList",form10DList);
	}
%>
<body>
<table width="100%" height="480" border="0" cellspacing="1" cellpadding="1" style="page-break-after:always;">

  <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-IReport.jsp"/>
		
    	</td>
  </tr>
</table>  

 <table width="100%"  cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-IIReport.jsp"/></td>
  </tr>
</table>

 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-IIIReport.jsp"/></td>
  </tr>
</table>

 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-IVReport.jsp"/></td>
  </tr>
</table>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-VReport.jsp"/></td>
  </tr>
</table>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-VIReport.jsp"/></td>
  </tr>
</table>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="page-break-after:always;">

  
   <tr>
    <td><jsp:include page="/jsp/rpfc/form10D/Form10DPage-VIIReport.jsp"/></td>
  </tr>
</table>
</body>
</html>
