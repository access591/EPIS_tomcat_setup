<%@ page language="java"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/dialog_box.css" />
<script type="text/javascript" src="<%=basePath%>js/dialog_box.js"></script>
<script type="text/javascript">
  function init(){
  	var windowFlag='N';
  	var frmWindowFlag='<%=request.getAttribute("windowflag")%>';

  	if(window.opener!=null || !window.closed){
  	
  		if(window.opener!=undefined){
  			windowFlag='Y';
  		}
  		
  	}

	if(frmWindowFlag!=null){
	 windowFlag=frmWindowFlag;
  	}
	showDialog('<%=request.getAttribute("successinfo")%>','<%=request.getAttribute("successMessage")%>','<%=request.getAttribute("type")%>','','<%=basePath%><%=request.getAttribute("sucessPath")%>',windowFlag);
		
	
  } 
  window.onload=init;
</script>
</head>
<body>
<form name="rpfcmessage">
<div id="content">
<table>
</table>


</div>
</form>
</body>
</html>