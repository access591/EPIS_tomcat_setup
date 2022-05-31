<!--
/*
  * File       : FinanceDataSearch.jsp
  * Date       : 05/02/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2008) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page language="java" import="java.util.*,com.epis.utilities.*"%>
<%@ page import="com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ page import="com.epis.bean.rpfc.FinacialDataBean"%>

<%@ taglib uri="/tags-display" prefix="display"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
					
					 String region="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	Iterator it = keys.iterator();
	
	Iterator regionIterator = null;
            Iterator monthIterator = null;
          
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
               // Set keys = hashmap.keySet();
                regionIterator = keys.iterator();

            }
					
	 String pensionNo="",status="",airportcode="",toregion="",toairportcode="";
	 if (request.getAttribute("pensionno") != null) {
	 pensionNo=request.getAttribute("pensionno").toString();
	 }
	 
	 if (request.getAttribute("status") != null) {
	 status=request.getAttribute("status").toString();
	 }
	 
	 if (request.getAttribute("newairportcode") != null) {
	 toairportcode=request.getAttribute("newairportcode").toString();
	 }
	 
	 if (request.getAttribute("newregion") != null) {
	 toregion=request.getAttribute("newregion").toString();
	 }

     System.out.println("------toairportcode--------"+toairportcode);
      System.out.println("------toregion--------"+toregion);
  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/GeneralFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>
		<script type="text/javascript"> 
	
	 function testSS(){		 
	 
	 
			  if('<%=status%>'=='TRANSFER IN/OTHERS'){
			 if(document.forms[0].select_region.value=="NO-SELECT"){
			   alert("Please select From Region");
			   document.forms[0].select_region.focus();
			   return false;
			 }	
			 
			  if(document.forms[0].select_airport.value=="NO-SELECT"){
			   alert("Please select From Airport Code");
			   document.forms[0].select_airport.focus();
			   return false;
			 }	
			 }
			 
			   if('<%=status%>'=='TRANSFER OUT/OTHERS'){
			   if(document.forms[0].seperationreason.value=="NO-SELECT"){
			   alert("Please select Seperation Reason");
			   document.forms[0].seperationreason.focus();
			   return false;
			 }	
			 }
			 
			  if(document.forms[0].sepetaionDt.value==""){
			  
			   if('<%=status%>'=='TRANSFER IN/OTHERS'){			 
			    alert("Please enter Transfer Date");
			   }else{			  
			   alert("Please enter Seperation Date");
			   }
			   document.forms[0].sepetaionDt.focus();
			   return false;
			 }		 
			 
			
	 
	   if(!document.forms[0].sepetaionDt.value==""){
   		    var date1=document.forms[0].sepetaionDt;
   	        var val1=convert_date(date1);
   		    if(val1==false)
   		     {
   		      return false;
   		     }
   		    } 	

    	document.forms[0].action="<%=basePath%>pfinance.do?method=updatetransferdata";
		document.forms[0].method="post";
		document.forms[0].submit();
		
   		 }
   
    function checkData(){
    
    if('<%=status%>'=='TRANSFER IN/OTHERS'){
     document.getElementById("station").style.display="block";
     document.getElementById("reg").style.display="block";
     document.getElementById("fromstation").style.display="block";
     document.getElementById("fromreg").style.display="block";    
    }else{
    
     document.getElementById("sepreason").style.display="block";
    
    }
         
    }	
    
    
    function getAirports(param){	
		var transferFlag,airportcode,regionID,yearID,frm_ltstmonthflag,monthID;
					if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		createXMLHttpRequest();	
		if(param=='airport'){
			var url ="<%=basePath%>psearch.do?method=getPersonalTblAirports&region="+regionID;
			xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = getAirportsList;
		}else{
		}
		
		
		
		xmlHttp.send(null);
    }
   		
   	 function createXMLHttpRequest()
	{
	if(window.ActiveXObject)
	 {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	 }
	else if (window.XMLHttpRequest)
	 {
		xmlHttp = new XMLHttpRequest();
	 }
	 }
	 
	 function getAirportsList()
	{
		if(xmlHttp.readyState ==3 ||  xmlHttp.readyState ==2 ||  xmlHttp.readyState ==1){
			 process.style.display="block";
		}
		if(xmlHttp.readyState ==4)
		{
			if(xmlHttp.status == 200)
				{ 
			      	var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
					 process.style.display="none";
					  if(stype.length==0){
		 //	alert("in if");
		 	var obj1 = document.getElementById("select_airport");
		 
		 	
		  	obj1.options.length=0; 
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		 
		  
		  }else{
		   	var obj1 = document.getElementById("select_airport");
		    // alert(stype.length);	
		  	obj1.options.length = 0;
		  	
		  	for(i=0;i<stype.length;i++){
		  		if(i==0)
					{
				//	alert("inside if")
					obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
					}
		         // 	alert("in else");
			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
			}
		  }
		}
	}
	 
}

function getNodeValue(obj,tag)
   	{
		return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
   	}

function frmload(){
		 process.style.display="none";
	}
    	 
		
	</script>
	</head>
	<body class="BodyBackground" onload="checkData();javascript:frmload()">

		<form method="post" action="<%=basePath%>psearch.do?method=financesearch">
			<%=ScreenUtilities.screenHeader("Transfer Details")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">


				<%boolean flag = false;%>
				<tr>
					<td>
						<table align="center">


							<tr>

								<td class="tableTextBold">
									Status
								</td>
								<td class="tableTextBold">
									<input type="hidden" name="pensionno" value="<%=pensionNo%>" />
									<input type="hidden" name="status" value="<%=status%>" />
									<%=status%>
								</td>

							</tr>

							<tr id="sepreason" style="display: none;">
								<td class="tableTextBold">
									Sepeation Reason:<font color="red">&nbsp;*</font>
								</td>
								<td>
									<select name="seperationreason" class="TextField">
										<option value="NO-SELECT">
											select one
										</option>
										<option value="Retirement">
											Retirement
										</option>
										<option value="Death">
											Death
										</option>
										<option value="VRS">
											VRS
										</option>
										<option value="Resignation">
											Resignation
										</option>
										<option value="Termination">
											Termination
										</option>
										<option value="Other">
											Other
										</option>

									</select>
								</td>
							</tr>


							<tr id="fromreg" style="display: none;">
								<td class="tableTextBold">
									From Region :<font color="red">&nbsp;*</font>
								</td>
								<td>

									<select class="TextField" name="select_region" style="width:120px" onchange="javascript:getAirports('airport');">
										<option value="NO-SELECT">
											[Select One]
										</option>
										<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();
															
																			%>
										<option value="<%=region%>">
											<%=region%>
										</option>
										<%}%>
									</select>
								</td>
							</tr>

							<tr id="fromstation" style="display: none;">
								<td class="tableTextBold">
									From Airport Code:<font color="red">&nbsp;*</font>
								</td>
								<td>
									<select class="TextField" name="select_airport" style="width:120px">
										<option value='NO-SELECT' Selected="Selected">
											[Select One]
										</option>
									</select>
								</td>
							</tr>

							<tr id="station" style="display: none;">
								<td class="tableTextBold">
									To Airport Code:
								</td>
								<td class="TextField">
									<%=toairportcode%>
									<input type="hidden" name="airportcode" value="<%=toairportcode%>" />
								</td>
							</tr>

							<tr id="reg" style="display: none;">
								<td class="tableTextBold">
									To Region :
								</td>
								<td class="TextField">
									<%=toregion%>
									<input type="hidden" name="region" value="<%=toregion%>" />
								</td>
							</tr>


							<tr>
								<% if(status.equals("TRANSFER IN/OTHERS")){
								%>
								<td class="tableTextBold">
									Transfer Date:<font color="red">&nbsp;*</font>
								</td>
								<%
								}else{
								%>
								<td class="tableTextBold">
									Sepeation Date:<font color="red">&nbsp;*</font>
								</td>

								<%}	%>
								<td>
									<input type="text" name="sepetaionDt" class="TextField" />
									<a href="javascript:show_calendar('forms[0].sepetaionDt');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
								</td>
							</tr>

							<tr>
								<td colspan="1">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>

								<td>
									<input type="button" class="btn" value="Update" class="btn" onclick="testSS();" />
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" />

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>


					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</form>

		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center">
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle" alt="" />
			<SPAN class="tableTextBold">Processing.......</SPAN>
		</div>
	</body>
</html>
