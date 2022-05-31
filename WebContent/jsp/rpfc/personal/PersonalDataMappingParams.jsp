<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
	String[] year = {"1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009"};				
					%>
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.bean.rpfc.DatabaseBean" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList" %>

<html>
<HEAD>
   	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

<script type="text/javascript">
 function LoadWindow(params){
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>psearch?method=getPerData";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	
	function validateForm(user) {
			var reportType="";
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
        	if(user!='SEHGAL'&&user!='navayuga'){
    	      alert("' "+user+" ' User Not allowed to Access the Reports");
	          return false;
        	}
			var index=document.forms[0].select_region.selectedIndex;
		   	var airportcode="";
			if(document.forms[0].select_region.selectedIndex>0){
		    	regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
			}else{
				regionID=document.forms[0].select_region.value;
			}
		
			if(document.forms[0].select_airport.length>1){
				airportcode=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
			}else{
				airportcode=document.forms[0].select_airport.value;
			}
			monthID=document.forms[0].select_month.selectedIndex;
			yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].text;
			if(yearID=='Select One'){
			alert("Please Select Year");
			document.forms[0].select_year.focus();
			return false;
			}
			if(monthID==0){
			alert("Please Select Month");
			document.forms[0].select_month.focus();
			return false;
			}
			
			url = "<%=basePath%>psearch?method=loadPerData&frm_region="+regionID+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_airportcd="+airportcode;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
			
			return false;
			
		
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
		monthID=document.forms[0].select_month.selectedIndex;
		regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].text;
		if(monthID<10){
		monthID="0"+monthID;
		}
	createXMLHttpRequest();	
	var url ="<%=basePath%>reportservlet?method=getAirports&region="+regionID+"&frm_year="+yearID+"&frm_month="+monthID;;
	
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
</script>
</HEAD>
<body class="BodyBackground" onload="javascript:frmload()">
<%
	String monthID="",yearDescr="",region="",monthNM="";
	Iterator monthIterator=null;
  	Iterator yearIterator=null;
  	Iterator regionIterator=null;
  		HashMap hashmap=new HashMap();
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	
  	}
  	if(request.getAttribute("monthIterator")!=null){
  	monthIterator=(Iterator)request.getAttribute("monthIterator");
  	}
  	if(request.getAttribute("yearIterator")!=null){
  	yearIterator=(Iterator)request.getAttribute("yearIterator");
  	}
%>
<form action="post" >
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					<jsp:include page="/PensionView/PensionMenu.jsp"/>
					</td>
				</tr>
				<tr>
					<td >&nbsp;</td>
				</tr>
					<%if(request.getAttribute("message")!=null){%>
						<tr>
							<td class="label" align="center"><font color="red"><%=request.getAttribute("message")%></font></td>
						</tr>
						<%}%>
				<tr>
				<td>
					<table align="center" width="75%" cellpadding="0" cellspacing="0">
					
					<tr>
						<td>
							<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbborder">
								<tr>
									<td height="5%" colspan="2" align="center" class="ScreenMasterHeading">	Import Form-3-2007 Personal Info</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							
							<tr>
								  <td class="label" align="right"> Year:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
							      <td><Select name='select_year' Style='width:100px'>
									   <option value='' >Select One</option>
											<% for(int j=0;j<year.length;j++) {%>
										   <option value='<%=year[j]%>' ><%=year[j]%></option>
										<%}%>
									  </Select>
								</td>
  							</tr>
						<tr>
							<td class="label" align="right"> Month:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
							<td >
								<select name="select_month" style="width:100px">
  								 <Option Value='' Selected>Select One</Option>
			  				
  							    <%while(monthIterator.hasNext()) { 
								Map.Entry mapEntry = (Map.Entry)monthIterator.next();
								monthID=mapEntry.getKey().toString();
								monthNM=mapEntry.getValue().toString();
								 %>
								
								<option value="<%=monthID%>" ><%=monthNM%></option>
							 	<%}%>
								</select>
	  						</td>
						</tr>
						<tr>
							<td class="label" align="right">Region: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>									
								<SELECT NAME="select_region" style="width:120px" onChange="javascript:getAirports()" >
								<option value="NO-SELECT">[Select One]</option>
								<%
						     	  while(regionIterator.hasNext()){
						     	  
							 	  region=hashmap.get(regionIterator.next()).toString();
							 	%>
							  	<option value="<%=region%>" ><%=region%></option>
	                       		<% }%>
								</SELECT>
							</td>
						</tr>
	
						
						<tr>
							<td class="label" align="right">Aiport Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
							<td>									
								<SELECT NAME="select_airport" style="width:120px" >
								
							  	<option value='NO-SELECT' Selected>[Select One]</option>
	                       		
								</SELECT>
							</td>
						</tr>
					

		
		
						<tr>  
     						 <td align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>  
   						 </tr>  		
						<tr>
						 <td align="right" >
        					<input type="button" class="btn"  name="Submit" value="Submit" onclick="javascript:validateForm('<%=session.getAttribute("userid")%>')"> 
        					<input type="button" class="btn"  name="Reset" value="Reset" onclick="javascript:resetReportParams()" >
        					<input type="button" class="btn"  name="Submit" value="Cancel" onclick="javascript:history.back(-1)" >
       					 </td>
       					 </tr>
                    	<tr>  
     						 <td align="center"></td>  
   						 </tr>  	
   					
					</table>
					</td>
				</tr>
			
			</table>
		</form>
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="label" >Processing.......</SPAN>
		</div>
</body>
</html>