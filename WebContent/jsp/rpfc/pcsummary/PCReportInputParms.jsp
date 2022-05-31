

<%@ page language="java" import="java.util.*,com.epis.utilities.ScreenUtilities,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.StringUtility" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.EmployeeValidateInfo"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo"%>

<%@ page import="java.util.ArrayList" %>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
	
			
					%>
<html>
<HEAD>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		

<script type="text/javascript">

function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
			document.forms[0].txt_empname.value=employeeName;
			document.forms[0].frm_pensionno.value=empSerialNo;
			document.forms[0].chk_empflag.checked=true
		  			
		}

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
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>reportservlet.do?method=loadpcsummary";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	
	function validateForm(user) {
		var reportType="",url="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		
 
         if(document.forms[0].select_year.selectedIndex<1){
   		  alert("User Should be select Finance Year");
   		  document.forms[0].select_year.focus();
   		  return false;
   		  } 
        
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
			if(monthID<10){
				monthID="0"+monthID;
			}
			reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
			yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].text;
			yearID=yearID.lastIndexOf('-');
			var sortingOrder=document.forms[0].sortingOrder.value;
			var empName=document.forms[0].txt_empname.value;
			if(empName!='' && document.forms[0].chk_empflag.checked==false){
			  		alert('Should be checked Employee Name');
			  		document.forms[0].txt_empname.focus();
			  		return false;
			}
			if (document.forms[0].chk_empflag.checked==true){
			  			empName=document.forms[0].txt_empname.value;
        				frm_empflag=true;
       		}else{
	       		  frm_empflag=false;
       		}
			var params = "?region="+regionID+"&month="+monthID+"&year="+yearID+"&frm_reportType="+reportType+"&airportCD="+airportcode;
	
			var pensionno=document.forms[0].frm_pensionno.value;
			url = "<%=basePath%>reportservlet.do?method=PCSummary&frm_region="+regionID+"&frm_airportcd="+airportcode+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_reportType="+reportType+"&sortingOrder="+sortingOrder+"&frm_empnm="+empName+"&frm_emp_flag="+frm_empflag+"&frm_pensionno="+pensionno;
			
			if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   			}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
						winOpened = true;
						wind1.window.focus();
      		}
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
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].text;
		if(monthID<10){
		monthID="0"+monthID;
		}
	createXMLHttpRequest();	
	var url ="<%=basePath%>psearch.do?method=getPFCardAirports&region="+regionID;
	
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
  
  	Iterator regionIterator=null;
  		HashMap hashmap=new HashMap();
  		 String[] year=null;
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	
  	}
  	if(request.getAttribute("monthIterator")!=null){
  	monthIterator=(Iterator)request.getAttribute("monthIterator");
  	}
   if (request.getAttribute("year") != null) {
                year = (String[]) request.getAttribute("year");
            }
%>
<form action="post">
			<%=ScreenUtilities.screenHeader("Pension Contribution Summary Report Params")%>
			<table width="75%" border="0" align="center" cellpadding="2" cellspacing="0" class="tbborder">

						
									
									
																<tr>
																	<td class="tableTextBold" align="right">
																		Year:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<Select name='select_year' Style='width:100px' class="TextField">
																			<option value='No-SELECT'>
																				Select One
																			</option>
																			<%for (int j = 0; j < year.length; j++) {%>
																			<option value='<%=year[j]%>'>
																				<%=year[j]%>
																			</option>
																			<%}%>
																		</SELECT>
																	</td>

																</tr>
																<tr>
																	<td class="tableTextBold" align="right">
																		Month:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<select name="select_month" style="width:100px" class="TextField">
																			<Option Value='' Selected>
																				Select One
																			</Option>
																			<%while (monthIterator.hasNext()) {
																			Map.Entry mapEntry = (Map.Entry) monthIterator.next();
																			monthID = mapEntry.getKey().toString();
																			monthNM = mapEntry.getValue().toString();

				%>

																			<option value="<%=monthID%>">
																				<%=monthNM%>
																			</option>
																			<%}%>
																		</select>
																	</td>
																</tr>
																<tr>
																	<td class="tableTextBold" align="right">
																		Region:<font color=red>*</font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_region" class="TextField" style="width:120px" onChange="javascript:getAirports()">
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
																		</SELECT>
																	</td>
																</tr>
																<tr>
																	<td class="tableTextBold" align="right">
																		Aiport Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_airport" style="width:120px" class="TextField">
																			<option value='NO-SELECT' Selected>
																				[Select One]
																			</option>
																		</SELECT>
																	</td>
																</tr>
															<tr>
																	<td class="tableTextBold" align="right">
																		Employee Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<input type="text" value="" name="txt_empname" class="TextField">
																		<input type="hidden" name="frm_pensionno">
																		<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');" alt="Click The Icon to Select EmployeeName" />
																		<input type="checkbox" name="chk_empflag">
																	</td>
																</tr>
									
																<tr>
																	<td class=tableTextBold align="right" nowrap>
																		Report Type: <font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																			<SELECT NAME="select_reportType" style="width:88px" class="TextField">
																			<option value="">
																				[Select One]
																			</option>
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
																	<td class=tableTextBold align="right" nowrap>
																		Sorting Order: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="sortingOrder" style="width:88px" class="TextField">
																		
																			<option value="PENSIONNO">
																				PENSIONNO
																			</option>
																			<option value="employeename">
																				EmployeeName
																			</option>
																				<option value="airportcode">
																				Airportcode
																			</option>
																		</SELECT>
																	</td>
																</tr>

																<tr>
																
																		<td colspan="3" align="center">
									 								<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm('<%=session.getAttribute("userid")%>')">
																		<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
																	</td>
																	
																</tr>
															</table>
															<%=ScreenUtilities.screenFooter()%>
		</form>
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>
</body>
</html>