

<%@ page language="java" import="java.util.*,com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
	String forms[] = {"FINAL PAYMENT REGISTER","FINAL PAYMENT REGISTER ARREAR"};

					%>
<%
	String monthID="",yearDescr="",region="",monthNM="";
	int regionSize=0;
	Iterator monthIterator=null;
  	Iterator yearIterator=null;
  	Iterator regionIterator=null;
  	HashMap hashmap=new HashMap();
  	ArrayList yearList=new ArrayList();
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	regionSize=hashmap.size();
  	}
  	if(request.getAttribute("monthIterator")!=null){
  	monthIterator=(Iterator)request.getAttribute("monthIterator");
  	}
  	if(request.getAttribute("yearList")!=null){
  	yearList=(ArrayList)request.getAttribute("yearList");
  	}
%>
<html>
	<HEAD>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript">



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
		document.forms[0].action="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=FinalPaymentRegister";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	
	function validateForm(user) {
		var reportType="",url="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		
      //  if(user!='SEHGAL' && user!='navayuga'&&user!='BALWANT'){
      //    alert("' "+user+" ' User Not allowed to Access the Reports");
      //    return false;
       //  }
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
		   
		 
   		  
   		  if(document.forms[0].select_month.value==''){
   		  alert("Please Enter To Date");
   		  document.forms[0].select_month.focus();
   		  return false;
   		  } 
   		  
   		    
		    if(document.forms[0].select_month.value!=''){
		    	if (!convert_date(document.forms[0].select_month))
				{
					document.forms[0].select_month.focus();
					return false;
				}
		    }
        
    
        
        /* var index=document.forms[0].select_region.selectedIndex;
      	 if(document.forms[0].select_region.selectedIndex<1) {
   		  alert("Please Select Region");
   		  document.forms[0].select_region.focus();
   		  return false;
   		  }*/ 
    
          
		if(document.forms[0].formType.value==''){		
			alert('Please Select Form Type (Mandatory)');
			document.forms[0].formType.focus();
			return false;
		 }else{

		    var airportcode="",seperationreason="",sanctiondt="",trust="";
			//wind=window.open("<%=basePath%>view/advances/Monthly_PAYMENTS_WestRegion.xls","Report","toolbar=yes,scrollbars=yes,resizable=yes,top=0,left=0,menuBar=yes");
			//wind.window.focus();
			if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
			}else{
			regionID=document.forms[0].select_region.value;			
			}
			
			monthID=document.forms[0].select_month.value;
			reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
			yearID=document.forms[0].select_year.value;
			seperationreason=document.forms[0].seperationreason.options[document.forms[0].seperationreason.selectedIndex].text;
		    trust=document.forms[0].trust.options[document.forms[0].trust.selectedIndex].text;
		    		  
		    var fromDate=document.forms[0].select_year.value;		    
		    var toDate=document.forms[0].select_month.value;	
		    	  
		    var station=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
			
			var arrearType="";
			var frmType=document.forms[0].formType.value;
			if(frmType=='FINAL PAYMENT REGISTER')
			{
			arrearType="NON-ARREAR";
			}else{
			arrearType="ARREAR";
			}
				
		    					    
			url="<%=basePath%>advanceparamreport.do?method=finalPaymentRegister&frm_region="+regionID+"&frm_fromDate="+fromDate+"&frm_toDate="+toDate+"&frm_sepreason="+seperationreason+"&frm_trust="+trust+"&frm_reportType="+reportType+"&frm_station="+station+"&arrear_type="+arrearType;
					
		
		   if((document.forms[0].formType.value=='FINAL PAYMENT REGISTER')||(document.forms[0].formType.value=='FINAL PAYMENT REGISTER ARREAR')){
				
				if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   	 			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 						//alert("url "+url);	
   	 				 			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
   	 			}			
	
			}else{
			LoadWindow(url);
			if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	 				alert('Developement Activities are in Progress');
   	 		}
			
			}
			
		
			
			
			return false;
			
		}
		//return false;
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
		monthID=document.forms[0].select_month.value;
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
function loadDetails(){
	var reionsize;
	reionsize='<%=regionSize%>';
	//alert(reionsize);

	if(reionsize==1){ 
	getAirports();
	} 
}
</script>
	</HEAD>
	<body class="BodyBackground" onload="javascript:frmload();loadDetails();">
		
		<form action="post">
			<%=ScreenUtilities.screenHeader("Final Payment Register Report Params")%>
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
						<input type="text" name="select_month" class="TextField" />
						<a href="javascript:show_calendar('forms[0].select_month');"><img src="<%=basePath%>images/calendar.gif" border="no" alt="" /></a>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right" >
						Region&nbsp;<font color=red>*</font>
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_region" class="TextField" onChange="javascript:getAirports()">
						<%if(regionSize>1){%>	
							<option value="NO-SELECT">
								[Select One]
							</option>
							<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();%>
							<option value="<%=region%>">
								<%=region%>
							</option>
							<%}%>
						<%}%>
						<%if(regionSize==1){%>							 
							<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();%>
							<option value="<%=region%>" selected="selected">
								<%=region%>
							</option>
							<%}%>
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

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Reason For Seperation
					</td>
					<td align="center">
						:
					</td>
					<td>
						<select name="seperationreason" class="TextField">
							<option value="NO-SELECT">
								Select One
							</option>
							<option value="Retirement">
								Retirement
							</option>
							<option value="VRS">
								VRS
							</option>
								<option value="CRS">
								CRS
							</option>
							<option value="Death">
								Death
							</option>
							<option value="Resignation">
								Resignation
							</option>
							<option value="Termination">
								Termination
							</option>
							<option value="EarlyPensions">
								Option for Early Pensions
							</option>
							<option value="Others">
								Others
							</option>
						</select>
					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Trust
					</td>
					<td align="center">
						:
					</td>
					<td>
						<select name="trust" class="TextField">
							<option value="NO-SELECT">
								Select One
							</option>
							<option value="NAA">
								NAA
							</option>
							<option value="AAI">
								AAI
							</option>
							<option value="IAAI">
								IAAI
							</option>

						</select>
					</td>

				</tr>

				
				  <tr>
					<td>
						&nbsp;
					</td>
					<td class=tableTextBold align="right" nowrap>
						Form Type&nbsp;<font color=red>*</font>
					</td>
					<td align="center">
						:
					</td>
					<td >
						<Select name='formType' style="width:200px" class="TextField">
							<option value='' >
								[Select One]
							</option>
							<%for (int j = 0; j < forms.length; j++) {%>
							<option value='<%=forms[j]%>' >
								<%=forms[j]%>
							</option>
							<%}%>
						</SELECT>
					</td>
				</tr> 
				
				 
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right" nowrap>
						Report Type&nbsp;<font color=red>*</font>
					</td>
					<td align="center">
						:
					</td>
					<td>
						<SELECT NAME="select_reportType" class="TextField">

							<option value="html">
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
