

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
	String forms[] = {"SANSACTION ORDER-REGION WISE"};

					%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<html>
	<HEAD>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>

		<script type="text/javascript"><!--



function popupWindow(mylink, windowname)
		{
		
		//var transfer=document.forms[0].transferStatus.value;
		var transfer="";
	
		var regionID="";
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		if (! window.focus)return true;
		var href;
		if (typeof(mylink) == 'string')
		   href=mylink+"?transferStatus="+transfer+"&region="+regionID;
		   
		else
		href=mylink.href+"?transferStatus="+transfer+"&region="+regionID;
	    
		progress=window.open(href, windowname, 'width=700,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
		
		return true;
		}
 function LoadWindow(params){
    //alert("params"+params);
  
	var newParams ="<%=basePath%>jsp/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=pfwsmaryreport";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	
	function validateForm(user) {
		var reportType="",url="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		
    
   		  
   		  if(document.forms[0].select_year.value==''){
   		  alert("Please Enter From Date");
   		  document.forms[0].select_year.focus();
   		  return false;
   		  } 
   		  
   		   if(document.forms[0].select_year.value!=''){
		    	if (!convert_date(document.forms[0].select_year))
				{
					document.forms[0].select_year.focus();
					return false;
				}
		    }
		   
		 
   		  
   		  if(document.forms[0].select_to_year.value==''){
   		  alert("Please Enter To Date");
   		  document.forms[0].select_to_year.focus();
   		  return false;
   		  } 
   		  
   		    
		    if(document.forms[0].select_to_year.value!=''){
		    	if (!convert_date(document.forms[0].select_to_year))
				{
					document.forms[0].select_to_year.focus();
					return false;
				}
		    }
        
    
          
		

		   /* var airportcode="";
			//wind=window.open("<%=basePath%>view/advances/Monthly_PAYMENTS_WestRegion.xls","Report","toolbar=yes,scrollbars=yes,resizable=yes,top=0,left=0,menuBar=yes");
			//wind.window.focus();
			if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
			}else{
			regionID=document.forms[0].select_region.value;			
			}*/
				
			reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
			
			
		  
		    var fromDate=document.forms[0].select_year.value;		    
		    var toDate=document.forms[0].select_to_year.value;
		    
		    //var station=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
				var regionID="",station="";
		    
			url="<%=basePath%>advanceparamreport.do?method=pfwSummaryReport&frm_region="+regionID+"&frm_fromDate="+fromDate+"&frm_toDate="+toDate+"&frm_reportType="+reportType+"&frm_station="+station;
		
					
		 	
				if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 						//alert("url "+url);	
   	 				 			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
   	 			}				
			
			return false;		
		
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
	 
	function getAirports(){	
		monthID=document.forms[0].select_to_year.value;
		regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		yearID=document.forms[0].select_year.value;
	
	createXMLHttpRequest();	
	
	var url ="<%=basePath%>getdetails.do?method=getAirports&region="+regionID;
	
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = getAirportsList;
	
	xmlHttp.send(null);
    }
 function getNodeValue(obj,tag)
   {
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
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
		  //    alert(stype.length);	
		  	obj1.options.length = 0;
		  	
		  	for(i=0;i<stype.length;i++){
		  		if(i==0)
					{
				//	alert("inside if")
					obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
					}
		          //	alert("in else");
			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
			}
		  }
		}
	}
}

function frmload(){
 process.style.display="none";
}

--></script>
	</HEAD>
	<body class="BodyBackground" onload="javascript:frmload()">
		<%
	String region="";
	
  	Iterator regionIterator=null;
  	HashMap hashmap=new HashMap();
  	
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	
  	}

%>
		<form action="post">
			<%=ScreenUtilities.screenHeader("PFW Summary Report Params")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">

				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td class="tableTextBold" width="25%">
						From Date&nbsp;<font color=red>*</font>
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td>
					<input type="text" name="select_year" class="TextField" />
						<a href="javascript:show_calendar('forms[0].select_year');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
					</td>

				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						To Date&nbsp;<font color=red>*</font>
					</td>
					<td align="center">
						:
					</td>
					<td>
						<input type="text" name="select_to_year" class="TextField" />
						<a href="javascript:show_calendar('forms[0].select_to_year');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
					</td>
				</tr>
				<!--<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Region
					</td>
					<td align="center">
						:
					</td>
					<td>

						<SELECT NAME="select_region" class="TextField" onChange="javascript:getAirports()">
							<option value="NO-SELECT">
								[Select One]
							</option>
							<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();%>
							<option value="<%=region%>">
								<%=region%>
							</option>
							<%}%>
						</SELECT>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Station
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_airport" class="TextField">
							<option value='NO-SELECT' Selected>
								[Select One]
							</option>
						</SELECT>
					</td>
				</tr> 
				-->
				
				<tr>
					<td>
						&nbsp;
					</td>
					<td class=tableTextBold align="right" nowrap>
						Report Type
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_reportType" class="TextField">

							<option value="html" selected="selected">
								Html
							</option>
							<option value="ExcelSheet">
								Excel Sheet
							</option>

						</SELECT>

					</td>
				</tr>
				<tr>

					<td colspan="4" align="center">
						<input type="button" class="butt" name="Submit" value="Submit" onclick="javascript:validateForm('<%=session.getAttribute("userid")%>')">
						<input type="button" class="butt" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
					</td>

				</tr>

			</table>
			<%=ScreenUtilities.screenFooter()%>
		</form>
		
			<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center">
			<img src="<%=basePath%>images/Indicator.gif" border="no" align="middle" />
			<SPAN class="tableTextBold">Processing.......</SPAN>
		</div>

	</body>
</html>
