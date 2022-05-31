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
	var aURL ="<%=basePath%>EPISHelp/index.html";
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

<body ><%=ScreenUtilities.screenHeader("NODAL OFFICERS MENU ")%>
<table width="90%" border=0 cellpadding=0 cellspacing=0 ><tr><td width="60%">
<table width="398" border="0" cellspacing="0" cellpadding="10">
                    <tr>
                      <td align="center" valign="top"><table width="162" border="0" cellspacing="6" cellpadding="0">
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>personalForm.do?method=loadPerMstr" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Click Search to search PF related data of employees all over India. By default, you can search PFID all over India. You can also search PFID based on region.');return false" class="link">Search for PFID</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle"  background="<%=basePath%>images/buttonbg.gif"><a href="javascript:alert('User Not allowed to Create New PFID')" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Click Create NEW PFID to create new employee PF identity all over India. Unique identity is allotted to new employee.');return false" class="link">Create NEW PFID</a></td>
                          </tr>
                    
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>importuploadaction.do?method=loadImportedProcess" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Click Import  to import CPF Data.');return false" class="link">Import CPF Data</a></td>
                          </tr>
                         <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>aaiepfreportservlet.do?method=loadnodalepfforms" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Click   to Verify Imported CPF Data.');return false" class="link">AAI - EPF - FORMS</a></td>
                          </tr>
							 <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a
							href="<%=basePath%>PensionView/UniquePensionNumberSearch.jsp" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Click   to Edit Monthly CPF Recoverie Data.');return false" class="link"> Edit Cpf Recoveries</a></td>
                          </tr>						
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a  href="<%=basePath%>reportservlet.do?method=loadFinContri" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Viewing Pension Contribution Report Based on search criteria.');return false" class="link">Pension Contribution</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>reportservlet.do?method=loadpfcardInput" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Viewing PFCard Report Based on search criteria.');return false" class="link">PFCard</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>rpfcforms.do?method=loadform7Input" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Viewing Form-7 Report Based on search criteria.');return false" class="link">Form-7</a></td>
                          </tr>
						   <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>pfinance.do?method=loadAdj" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Adj in CPFRecoveries.');return false" class="link">Adj in CPFRecoveries</a></td>
                          </tr>
                          <tr>
                            <td height="26" align="center" valign="middle" background="<%=basePath%>images/buttonbg.gif"><a href="<%=basePath%>reportservlet.do?method=loadpcsummary" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'PC Summary Report');return false" class="link">PC Summary Report</a></td>
                          </tr>
                   
                      </table></td>
                    </tr>
                </table></td>
                <td width="40%"><FIELDSET ><LEGEND class="tableSideTextBold">Download Standard Formats </LEGEND><table height="100%" border="0" cellspacing="0" cellpadding="10">
               <tr>
                <td align="right">&nbsp;</a> </td>
              </tr>
              
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_1.xls" target="_blank" title="Click here to Download OPENING BALANCE Format" class="link"><font color="BLACK">AAIEPF FORM-1 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font></a> </td>
              </tr>
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_2.xls" target="_blank" title="Click here to Download ADJUSTMENT IN OPENING BALANCE" class="link"><font color="BLACK">AAIEPF FORM-2 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></a> </td>
              </tr>
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_3.xls" target="_blank" title="Click here to Download MONTHLY CPF RECOVERY" class="link"><font color="BLACK">AAIEPF FORM-3 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></a> </td>
              </tr>
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_3A.xls" target="_blank" title="Click here to Download Adjustment of CPF Recovery of Previous Month" class="link"><font color="BLACK">AAIEPF FORM-3A &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></a> </td>
              </tr>
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_4.xls" target="_blank" title="Click here to Download CPF Received From Other Organisation" class="link"><font color="BLACK">AAIEPF FORM-4 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></a> </td>
              </tr>
              <tr>
                <td align="right"><a href="../../jsp/rpfc/AAIEPFFormats/AAIEPF_8.xls" target="_blank" title="Click here to Download REFUNDABLE ADVANCE/NRFW/PFW &amp; FINAL SETTLEMENT" class="link"><font color="BLACK">AAIEPF FORM-8 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></a> </td>
              </tr>
            </table></FIELDSET ></td></tr></table><%=ScreenUtilities.screenFooter()%>
</body>
</html>
