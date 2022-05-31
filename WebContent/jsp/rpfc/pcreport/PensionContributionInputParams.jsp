

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.rpfc.FinancialYearBean"%>

<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                  
            %>
<html>
	<HEAD>
		
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<script type="text/javascript"><!--

 function LoadWindow(params){
	var newParams ="<%=basePath%>common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
 
 function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
			document.forms[0].empName.value=employeeName;
			document.forms[0].empserialNO.value=empSerialNo;
		  			
		}
		 var bulkPrintFlag='false';
	function bulkreport(userID){
		
		var regionID1='',airportcode1='';
		var chkEmpName=document.forms[0].empName.value;
		if(chkEmpName!=''){
				alert('Bulk Print Cannot Process a Individual Records.Please click on Submit Button');
				document.forms[0].empName.focus();
				return false;
		}
		var bulkpfidlist=document.forms[0].select_pfidlst.options[document.forms[0].select_pfidlst.selectedIndex].text;
		if(bulkpfidlist=='[Select One]'){
				alert('User should be select PFID Printing List');
				document.forms[0].select_pfidlst.focus();
				return false;
		}
		if(document.forms[0].select_region.selectedIndex>0){
			regionID1=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID1=document.forms[0].select_region.value;
		}
		if(document.forms[0].select_airport.selectedIndex>0){
			airportcode1=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].text;
		}else{
			airportcode1=document.forms[0].select_airport.value;
		}
		if(regionID1=='North Region' || regionID1=='South Region' || regionID1=='West Region'|| (regionID1=='CHQIAD'&&airportcode1=='IGI IAD')|| (regionID1=='CHQIAD'&&airportcode1=='IGICargo IAD')){
			if(!(userID=='NRFIN' || userID=='SRFIN' || userID=='WRFIN' || userID=='IGIFIN'|| userID=='CARGOFIN')){
				alert('Dont have Privileges for taking bulk print outs Other than Assistant Manager(F)/Manager(F) ');
				return false;
			}
		}
		bulkPrintFlag='true';
		validateForm();
	}
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>reportservlet.do?method=loadFinContri";
		document.forms[0].method="post";
		document.forms[0].submit();
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
		   href=mylink+"&transferStatus="+transfer+"&region="+regionID;
		   
		else
		href=mylink.href+"&transferStatus="+transfer+"&region="+regionID;
	    progress=window.open(href, windowname, 'width=700,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
		
		return true;
		}

	
	function validateForm(){
		var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="",formType="",toYearID="",finalairportcode="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var transferFlag="",pfidStrip="";	
		
		reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		
      	if(document.forms[0].select_airport.length>1){
			finalairportcode=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
		}else{
			finalairportcode=document.forms[0].select_airport.value;
		}
	
			airportcode=finalairportcode;
		
			if(reportType=='[Select One]'){
				alert('User should be select report Type');
				document.forms[0].select_reportType.focus();
				return false;
			}
		
		transferFlag=document.forms[0].transferStatus.options[document.forms[0].transferStatus.selectedIndex].value;
	
		if(transferFlag=='NO-SELECT'){
				transferFlag='';
		}
		
		if(document.forms[0].select_year.selectedIndex>0){
		yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
		}else{
		yearID=document.forms[0].select_year.value;
		}
		if(document.forms[0].select_year.selectedIndex>0){
			toYearID=document.forms[0].select_to_year.options[document.forms[0].select_to_year.selectedIndex].value;
		}else{
			toYearID=document.forms[0].select_to_year.value;
		}
		
		if(document.forms[0].select_month.selectedIndex>0){
		monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
		}else{
		monthID=document.forms[0].select_month.value;
		}
	
		//monthID=document.forms[0].select_month.selectedIndex;
	//	if(monthID<10){
	//		monthID="0"+monthID;
	//	}
		
		var empserialNO=document.forms[0].empserialNO.value;
		if(empserialNO=='' ){
			pfidStrip=document.forms[0].select_pfidlst.options[document.forms[0].select_pfidlst.selectedIndex].value;
			
		}else{
			pfidStrip='1 - 1'; 
		}
		//alert(empserialNO+','+pfidStrip);
	//	var	page="PensionContribution";
		var mappingFlag="false";
		var params = "&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_reportType="+reportType+"&frm_formType"+formType+"&empserialNO="+empserialNO+"&transferStatus="+transferFlag+"&frm_pfids="+pfidStrip+"&frm_toyear="+toYearID+"&mappingFlag="+mappingFlag+"&frm_bulkprint="+bulkPrintFlag;
		var url="<%=basePath%>reportservlet.do?method=getReportPenContr"+params;
		
		if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   		}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
						winOpened = true;
						wind1.window.focus();
      	}
		
	}
	 function LoadWindow(params){
    var newParams =params;
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
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
	function getAirports(param){	
		var transferFlag,airportcode,regionID,finalairportcode;
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		
		createXMLHttpRequest();	
		if(param=='airport'){
			var url ="<%=basePath%>psearch.do?method=getPFCardAirports&region="+regionID;
			xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = getAirportsList;
		}else{
			transferFlag=document.forms[0].transferStatus.options[document.forms[0].transferStatus.selectedIndex].value;
			if(transferFlag=='NO-SELECT'){
				transferFlag='';
			}
			if(document.forms[0].select_airport.length>1){
				finalairportcode=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
			}else{
				finalairportcode=document.forms[0].select_airport.value;
			}
		   if(finalairportcode=='NO-SELECT'){
				airportcode='';
			}else{
				airportcode=finalairportcode;
			}
			var formPageSize=document.forms[0].frmpagesize.value;
			bulkPrintFlag="false";
			var url ="<%=basePath%>psearch.do?method=getPFIDBulkPrintingList&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_transflag="+transferFlag+"&frm_pagesize="+formPageSize;
			xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = getPFIDNavigationList;
		}
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

function getPFIDNavigationList()
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
		 	var obj1 = document.getElementById("select_pfidlst");
		 
		 	
		  	obj1.options.length=0; 
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		 
		  
		  }else{
		   	var obj1 = document.getElementById("select_pfidlst");
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
	function generateForm(){
		var formType;
		formType=document.forms[0].select_formType.options[document.forms[0].select_formType.selectedIndex].text;
		if(formType=='Employee Wise'){
		}
	}
--></script>
	</HEAD>
	<body class="BodyBackground" onload="javascript:frmload()">
		<%String monthID = "", yearDescr = "", region = "", monthNM = "";

            
            Iterator regionIterator = null;
            Iterator monthIterator = null;
            HashMap hashmap = new HashMap();
            String[] year=null;
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
                Set keys = hashmap.keySet();
                regionIterator = keys.iterator();

            }
            if (request.getAttribute("monthIterator") != null) {
                monthIterator = (Iterator) request
                        .getAttribute("monthIterator");
            }
                       if (request.getAttribute("year") != null) {
                year = (String[]) request.getAttribute("year");
            }

            %>
		<form action="post">
			<%=ScreenUtilities.screenHeader("Pension Contribution Report Params")%>
			<table width="75%" border="0" align="center" cellpadding="1" cellspacing="3" class="tbborder">
				<tr>
					<td class="tableTextBold" align="right">
						Finance From Year:
					</td>
					<td>
						<Select name='select_year' Style='width:100px' class="TextField">
							<option value='NO-SELECT'>
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
						Finance To Year:
					</td>
					<td>
						<Select name='select_to_year' Style='width:100px' class="TextField">
							<option value='NO-SELECT'>
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
						Finance To Month:
					</td>
					<td>
						<select name="select_month" style="width:100px" class="TextField">
							<Option Value='NO-SELECT'>
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
						Region:
					</td>
					
					<td>
						<SELECT NAME="select_region" style="width:130px" class="TextField" onChange="javascript:getAirports('airport')">
							<option value="NO-SELECT">
								[Select One]
							</option>
							<option value="AllRegions">
								AllRegions
							</option>
							<%int j = 0;
                boolean exist = false;
                while (regionIterator.hasNext()) {
                    region = hashmap.get(regionIterator.next()).toString();
                    j++;

                    {%>
							<option value="<%=region%>">
								<%=region%>
							</option>

							<%}
                }

            %>

							
						</SELECT>
					</td>
				</tr>
				<tr>
							<td class="tableTextBold" align="right">Aiport Name: </td>
							<td>									
								<SELECT NAME="select_airport" style="width:120px" class="TextField">
								
							  	<option value='NO-SELECT' Selected>[Select One]</option>
	                       		
								</SELECT>
							</td>
						</tr>
					<tr>
					<td class="tableTextBold" align="right">Page Size:</td>
					<td><input type="text" name="frmpagesize" tabindex="3" value="100" class="TextField"></td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						Employee Transfer Status:
					</td>
					<td>
						<SELECT NAME="transferStatus" style="width:88px" class="TextField">
							<option value="NO-SELECT">
								[Select One]
							</option>
							<option value="Y">
								Yes
							</option>
							<option value="N">
								No
							</option>
						</SELECT>
					</td>

				</tr>

				<tr>
					<td class="tableTextBold" align="right">
						Employee Name:
					</td>
					<td>
						<input type="text" name="empName" readonly="readonly" class="TextField" value="<%=request.getAttribute("empName")%>">
						<img src="<%=basePath%>images/search1.gif" onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');" alt="Click The Icon to Select EmployeeName" />
					</td>

				</tr>
				<TR>
					<td class=tableTextBold align="right" nowrap>
						Report Type:
					</td>
					<td>
						<SELECT NAME="select_reportType" style="width:88px" class="TextField" onChange="javascript:getAirports('pfid')">
							<option value="NO-SELECT">
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
							<td class="tableTextBold" align="right">PF ID Printing List:</td>
							<td>									
								<SELECT NAME="select_pfidlst" style="width:120px" class="TextField">
								
							  	<option value='NO-SELECT' Selected>[Select One]</option>
	                       		
								</SELECT>
							</td>
						</tr>
				<tr>
				<tr>
					<td  colspan="2">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>

				<input type="hidden" name="empserialNO" value="<%=request.getAttribute("empPFID")%>">
				<input type="hidden" name="chk_frmblkprint" value="false">
             	<tr>
					<td colspan="2">
					      <table cellpadding="0" cellspacing="0" align="center">
					      		<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm()">
								<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
								<input type="button" class="btn" name="Submit" value="Cancel" onclick="javascript:history.back(-1)">
								<input type="button" class="btn" name="bulk" value="Bulk Print" onclick="javascript:bulkreport('<%=request.getAttribute("frmUserName")%>')">
					      </table>
					
					</td>
				</tr>
				<tr>
					<td align="center"></td>
				</tr>
			</table>


<%=ScreenUtilities.screenFooter()%>
		</form>
<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>

	</body>