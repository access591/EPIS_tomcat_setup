<%
	String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
	String queryString = request.getQueryString();
	String title = request.getParameter("title")==null?"":request.getParameter("title");
%>

<head>
<title>Reports</title>
<LINK rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css">
<SCRIPT SRC="<%=basePath%>js/GeneralFunctions.js"></SCRIPT>

<SCRIPT>
		window.moveTo(0,0);
		window.resizeTo(screen.width, screen.height); 
		resetIt();
</SCRIPT>
<Script Language='Javascript'>
	LoadReport();
	function LoadReport(){
		var str = "<Table Width=100% Height=100% Border=0 CellSpacing=0 CellPadding=0>";
		str+="<Tr><Td Align='Center' vAlign='middle'><Img Src='<%=basePath%>images/loading1.gif'></Img></Td></Tr></Table>";
		document.all['Loading'].innerHTML = str;
		var url = "<%=queryString%>";
		if(window.XMLHttpRequest){
			req = new XMLHttpRequest();
			req.onreadystatechange = populateValues;
			req.open("GET",url,true);
			req.send(null);
		}else if(window.ActiveXObject){
			req = new ActiveXObject("Microsoft.XMLHTTP");
			req.onreadystatechange = populateValues;
			req.open("GET",url,true);
			req.send();
		}
	}
	function populateValues(){	
		if(req.readyState==4){//Complete
			if(req.status==200){
				var str = req.responseText;
				document.all['Loading'].innerHTML = str;
			}	
		}
	}
</Script>
</head>
<HTML>
<Body Onload='LoadReport();'>
<Div id='Loading' LeftMargin='0' TopMargin='0' Width='100%' Height='100%'></Div>
</Body>