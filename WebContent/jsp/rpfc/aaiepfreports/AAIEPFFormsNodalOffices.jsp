<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=iso-8859-1" />
<title>AAI</title>
<style type="text/css">
<!--

.text {
	font: normal 12px Arial, Helvetica, sans-serif;
	color: #E4E4E4;
}
A.link {
	font: bold 12px Arial, Helvetica, sans-serif;
	color:#003366;
	text-decoration:none;
}
A.link:hover {
	color: #CC0000;
	font: bold 12px Arial, Helvetica, sans-serif;
	text-decoration:none;
}
.box {
	background-image: url(<%=basePath%>images/table_top.gif);
	background-repeat: no-repeat;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	color: #FFFFFF;
	font-size: 12px;
}
.headinfo {
	background-image: url(<%=basePath%>images/head_img.gif);
	background-repeat: no-repeat;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	color: #FFFFFF;
	font-size: 12px;
}
.line {
	border-right: 1px solid #233F53;
	border-bottom: 1px solid #233F53;
	border-left: 1px solid #233F53;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
}
.textfld {
	background-color:#FFFFFF;
	border:1px solid #7F9DB9;
	width:150px;
	font-family: Arial, Verdana, Tahoma, Helvetica, Arial, sans-serif;
	font-size: 12px;
	color: #333333;
}
.textfld1 {
	width:154px;
	font-family: Arial, Verdana, Tahoma, Helvetica, Arial, sans-serif;
	font-size: 12px;
	color: #333333;
}
.button {
	height: 23px;
	width: 68px;
	border: 1px none #333333;
	background-image: url(images/button.gif);
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #FFFFFF;
}
-->
</style>
<SCRIPT type="text/javascript">
	/************************************************************************************************************
	(C) www.dhtmlgoodies.com, October 2005
	
	This is a script from www.dhtmlgoodies.com. You will find this and a lot of other scripts at our website.	
	
	Updated:	
		March, 11th, 2006 - Fixed positioning of tooltip when displayed near the right edge of the browser.
		April, 6th 2006, Using iframe in IE in order to make the tooltip cover select boxes.
		
	Terms of use:
	You are free to use this script as long as the copyright message is kept intact. However, you may not
	redistribute, sell or repost it without our permission.
	
	Thank you!
	
	www.dhtmlgoodies.com
	Alf Magne Kalleland
	
	************************************************************************************************************/	
	var dhtmlgoodies_tooltip = false;
	var dhtmlgoodies_tooltipShadow = false;
	var dhtmlgoodies_shadowSize = 4;
	var dhtmlgoodies_tooltipMaxWidth = 200;
	var dhtmlgoodies_tooltipMinWidth = 100;
	var dhtmlgoodies_iframe = false;
	var tooltip_is_msie = (navigator.userAgent.indexOf('MSIE')>=0 && navigator.userAgent.indexOf('opera')==-1 && document.all)?true:false;
	function showTooltip(e,tooltipTxt)
	{
		
		var bodyWidth = Math.max(document.body.clientWidth,document.documentElement.clientWidth) - 20;
	
		if(!dhtmlgoodies_tooltip){
			dhtmlgoodies_tooltip = document.createElement('DIV');
			dhtmlgoodies_tooltip.id = 'dhtmlgoodies_tooltip';
			dhtmlgoodies_tooltipShadow = document.createElement('DIV');
			dhtmlgoodies_tooltipShadow.id = 'dhtmlgoodies_tooltipShadow';
			
			document.body.appendChild(dhtmlgoodies_tooltip);
			document.body.appendChild(dhtmlgoodies_tooltipShadow);	
			
			if(tooltip_is_msie){
				dhtmlgoodies_iframe = document.createElement('IFRAME');
				dhtmlgoodies_iframe.frameborder='5';
				dhtmlgoodies_iframe.style.backgroundColor='#FFFFFF';
				dhtmlgoodies_iframe.src = '#'; 	
				dhtmlgoodies_iframe.style.zIndex = 100;
				dhtmlgoodies_iframe.style.position = 'absolute';
				document.body.appendChild(dhtmlgoodies_iframe);
			}
			
		}
		
		dhtmlgoodies_tooltip.style.display='block';
		dhtmlgoodies_tooltipShadow.style.display='block';
		if(tooltip_is_msie)dhtmlgoodies_iframe.style.display='block';
		
		var st = Math.max(document.body.scrollTop,document.documentElement.scrollTop);
		if(navigator.userAgent.toLowerCase().indexOf('safari')>=0)st=0; 
		var leftPos = e.clientX + 10;
		
		dhtmlgoodies_tooltip.style.width = null;	// Reset style width if it's set 
		dhtmlgoodies_tooltip.innerHTML = tooltipTxt;
		dhtmlgoodies_tooltip.style.left = leftPos + 'px';
		dhtmlgoodies_tooltip.style.top = e.clientY + 10 + st + 'px';

		
		dhtmlgoodies_tooltipShadow.style.left =  leftPos + dhtmlgoodies_shadowSize + 'px';
		dhtmlgoodies_tooltipShadow.style.top = e.clientY + 10 + st + dhtmlgoodies_shadowSize + 'px';
		
		if(dhtmlgoodies_tooltip.offsetWidth>dhtmlgoodies_tooltipMaxWidth){	/* Exceeding max width of tooltip ? */
			dhtmlgoodies_tooltip.style.width = dhtmlgoodies_tooltipMaxWidth + 'px';
		}
		
		var tooltipWidth = dhtmlgoodies_tooltip.offsetWidth;		
		if(tooltipWidth<dhtmlgoodies_tooltipMinWidth)tooltipWidth = dhtmlgoodies_tooltipMinWidth;
		
		
		dhtmlgoodies_tooltip.style.width = tooltipWidth + 'px';
		dhtmlgoodies_tooltipShadow.style.width = dhtmlgoodies_tooltip.offsetWidth + 'px';
		dhtmlgoodies_tooltipShadow.style.height = dhtmlgoodies_tooltip.offsetHeight + 'px';		
		
		if((leftPos + tooltipWidth)>bodyWidth){
			dhtmlgoodies_tooltip.style.left = (dhtmlgoodies_tooltipShadow.style.left.replace('px','') - ((leftPos + tooltipWidth)-bodyWidth)) + 'px';
			dhtmlgoodies_tooltipShadow.style.left = (dhtmlgoodies_tooltipShadow.style.left.replace('px','') - ((leftPos + tooltipWidth)-bodyWidth) + dhtmlgoodies_shadowSize) + 'px';
		}
		
		if(tooltip_is_msie){
			dhtmlgoodies_iframe.style.left = dhtmlgoodies_tooltip.style.left;
			dhtmlgoodies_iframe.style.top = dhtmlgoodies_tooltip.style.top;
			dhtmlgoodies_iframe.style.width = dhtmlgoodies_tooltip.offsetWidth + 'px';
			dhtmlgoodies_iframe.style.height = dhtmlgoodies_tooltip.offsetHeight + 'px';
		
		}
				
	}
	
	function hideTooltip()
	{
		dhtmlgoodies_tooltip.style.display='none';
		dhtmlgoodies_tooltipShadow.style.display='none';		
		if(tooltip_is_msie)dhtmlgoodies_iframe.style.display='none';		
	}
	function Maximize() 
	{
	window.moveTo(0, 0);
  	window.resizeTo(screen.width, screen.height);
	}
	
	 function loadhelp(){
    //alert("params"+params);
	var aURL ="<%=basePath%>/PensionView/PensionHelp/index.html";
	var aWinName="AAI";
	
   var wOpen;
   var sOptions;

   sOptions = 'status=yes,menubar=yes,scrollbars=yes,resizable=yes,toolbar=yes';
   sOptions = sOptions + ',width=' + (screen.availWidth - 10).toString();
   sOptions = sOptions + ',height=' + (screen.availHeight - 122).toString();
   sOptions = sOptions + ',screenX=0,screenY=0,left=0,top=0';

   wOpen = window.open( '', aWinName, sOptions );
   wOpen.location = aURL;
   wOpen.focus();
   wOpen.moveTo( 0, 0 );
   wOpen.resizeTo( screen.availWidth, screen.availHeight );
   

	}
	
	 function loadsheet(){
   
	var aURL ="<%=basePath%>PensionView/MONTHLY_PAYMENTS_PRFORMA.xls";
	var aWinName="AAI";
	alert(aURL );
   var wOpen;
   var sOptions;

   sOptions = 'status=yes,menubar=yes,scrollbars=yes,resizable=yes,toolbar=yes';
   sOptions = sOptions + ',width=' + (screen.availWidth - 10).toString();
   sOptions = sOptions + ',height=' + (screen.availHeight - 122).toString();
   sOptions = sOptions + ',screenX=0,screenY=0,left=0,top=0';

   wOpen = window.open( aURL, aWinName, sOptions );
  // wOpen.location = aURL;
   wOpen.focus();
   wOpen.moveTo( 0, 0 );
   wOpen.resizeTo( screen.availWidth, screen.availHeight );
   

	}
	</SCRIPT>	
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ndtooltip.css" rel="stylesheet" type="text/css" />
</head>

<body ><%=ScreenUtilities.screenHeader("AAI-EPF Forms")%>
<table width="90%" border=0 cellpadding=0 cellspacing=0 ><tr><td width="60%">
<table width="398" border="0" cellspacing="0" cellpadding="10">
                    <tr>
                      <td align="center" valign="top"><table width="162" border="0" cellspacing="6" cellpadding="0">
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadob" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'A report with the same format given above but with the records which were imported into EPIS - unit wise with grand totals - Puporse is for comparision of totals with ref to the actual data given in the above format.');return false" class="link">AAI-EPF-I</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf2" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'A report with the same format given above but with the records which were imported into EPIS - unit wise with grand totals - Puporse is for comparision of totals with ref to the actual data given in the above format.');return false" class="link">AAI-EPF-2</a></td>
                          </tr>
                           <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf3" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Purpose of monthly CPF Recoveries  Data is for comparision of totals with ref to the actual data given in the above format.');return false" class="link">AAI-EPF-3</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf5" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'All India wide - each region information in single row based on the month and year selected by the user.Each region wise - unit wise data shall be displayed in Single row based on the month and year selected by the user');return false" class="link">AAI EPF-5</a></td>
                          </tr>
                           <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf7" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'This report Abstract Of Cpf  Recovery For The  Year based on Region/Airportwise');return false" class="link">AAI EPF-7</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf8" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'A report with the format of Monthly Schedule Of Refundable Advance/Nrfw/Pfw & Final Settlement.Compare the original sheet and this report');return false" class="link">AAI EPF-8</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf11" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'A report with the  format of Control Account Of Employees Subscription for the year.');return false" class="link">AAI EPF-11</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadepf12" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'A report with the format of Control Account Of Employer Contribution For The Year.');return false" class="link">AAI EPF-12</a></td>
                          </tr>
                        
                      </table></td>
                    </tr>
                </table></td>
               </tr></table><%=ScreenUtilities.screenFooter()%>
</body>
</html>
