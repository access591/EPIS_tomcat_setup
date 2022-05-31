<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.info.login.LoginInfo,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%@page import="com.epis.bean.advances.*"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			HttpSession httpsession = request.getSession();
			LoginInfo logInfo=new LoginInfo();
		//	 CommonUtil util = new CommonUtil();
         //int gridLength = util.gridLength();
            logInfo=(LoginInfo)httpsession.getAttribute("user");
         String userName=logInfo.getUserName();
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags-display" prefix="display"%>

<html>
	<head>


		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
		<script type="text/javascript">
		function closeWin(){
		 window.close();
		}
		function changeScreenSize(w,h){
		       window.resizeTo( w,h );
	    }
		</script>

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

	</head>
	<body onload="changeScreenSize(500,300)">
	 <TABLE width="400" border="0" align="center">
	 <tr>
	 <td align="center">
	  Dynamic Signature is not available for selected  PFID  AND Trans ID
	 </td>
	 </tr>
	 
	 <tr>
	 <td align="center">
	 <INPUT type="button" value="Close" onclick="closeWin()">
	 </td>
	 </tr>	 
	 
	 </TABLE>
		
	</body>
</html>
