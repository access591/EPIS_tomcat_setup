<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                    
                    String[] year = {"2007-08","2008-09","2009-10","2010-11"};
                    String[] userYears = {"2007-08","2008-09","2009-10","2010-11"};	
            %>

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%@ page import="com.epis.bean.rpfc.FinancialYearBean"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>

<html>
	<HEAD>
<style>
		.black_overlay{
			display: none;
			position: fixed;
			top: 0%;
			left: 0%;
			width: 100%;
			height: 3000px;
			background-color: black;
			z-index:1001;
			-moz-opacity: 0.8;
			opacity:.80;
			filter: alpha(opacity=80);
		}
		.white_content {
			display: none;
			position: fixed;
			top: 25%;
			left: 25%;
			width: 50%;
			height: 50%;
			padding: 16px;
			border: 16px solid orange;
			background-color: white;
			z-index:1002;
			overflow: auto;
		}
	</style>
		<LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>

		<script type="text/javascript">

		

 function LoadWindow(params){
	var newParams ="<%=basePath%>jsp/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
 
 	function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
			document.forms[0].empName.value=employeeName;
			document.forms[0].empserialNO.value=empSerialNo;
		  	document.forms[0].chk_empflag.checked=true;		
		}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>reportservlet.do?method=loadTransferInOutparams";
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
	     
		progress=window.open(href, windowname,'width=700,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
		
		return true;
		}
 function PfProcess(){
	   if(document.forms[0].empserialNO.value==""){
		  alert("Please Select Employee Name");
		  return false;
	   }
	  document.forms[0].select_reportType.disabled="true";
	  document.forms[0].select_pfidlst.disabled="true";
	  document.forms[0].select_formType.disabled="true";
	   var empserialNO=document.forms[0].empserialNO.value;
	   process.style.display="block";
	   createXMLHttpRequest();
        var url="<%=basePath%>reportservlet.do?method=ProcessforAdjOb&pensionno="+empserialNO;
     	xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = runPfProcess;
		xmlHttp.send(null);
	   }

    function runPfProcess()
   {
 	if(xmlHttp.readyState ==4)
   	{
  	process.style.display="none";
	alert(xmlHttp.responseText);
	  document.forms[0].select_reportType.disabled=false;
	  document.forms[0].select_pfidlst.disabled=false;
	  document.forms[0].select_formType.disabled=false;
    }
   }
   var bulkPrintFlag='false';
	function bulkreport(userID){
		var regionID1='',airportcode1='';
		document.forms[0].chk_ltstmonthflag.value=true;
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
		if(regionID1=='CHQNAD' || regionID1=='West Region'  || regionID1=='North-East Region' || regionID1=='North Region'|| regionID1=='RAUSAP'|| regionID1=='South Region' || (regionID1=='CHQIAD'&&airportcode1=='OFFICE COMPLEX')|| (regionID1=='CHQIAD'&&airportcode1=='IGI IAD')|| (regionID1=='CHQIAD'&&airportcode1=='IGICargo IAD')){
			if(!(userID=='SHIKHA' || userID=='NRFIN' || userID=='SRFIN' || userID=='NERFIN' || userID=='WRFIN' || userID=='SAPFIN'|| userID=='IGIFIN'|| userID=='CARGOFIN')){
				alert('Dont have Privileges for taking bulk print outs Other than Assistant Manager(F)/Manager(F) ');
				return false;
			}
		}
		bulkPrintFlag='true';
		validateForm();
	}
	function validateForm() {
		var regionID="",airportcode="",params="",sortingOrder="",empName="",reportType="",url="",yearID="",monthID="",yearDesc="",formType="",toYearID="",formType="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var transferFlag="",pfidStrip="";	
		reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;

		if(document.forms[0].select_year.selectedIndex>0){
		yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
		}else{
		yearID=document.forms[0].select_year.value;
		}
		if(yearID=='NO-SELECT'){
			alert('User Should be select Year');
			document.forms[0].select_year.focus();
			return false;
		}
		var splitYearID = yearID.split("-");
		yearID=splitYearID[0];
	
		
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
		if(document.forms[0].select_month.selectedIndex>0){
		monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
		}else{
		monthID=document.forms[0].select_month.value;
		}
		if(monthID=='NO-SELECT'){
			alert('User Should be select month');
			document.forms[0].select_month.focus();
			return false;
		}
		if(regionID=='AllRegions'){
			alert('User Should be select Region');
			document.forms[0].select_region.focus();
			return false;
		}
		if(reportType=='[Select One]'){
			alert('User Should be select report Type');
			document.forms[0].select_reportType.focus();
			return false;
		}
		
		
		var transMonthYear=document.forms[0].transMonthYear.value;
		var empserialNO=document.forms[0].empserialNO.value;
		frm_ltstmonthflag=document.forms[0].chk_ltstmonthflag.value;
     
		formType=document.forms[0].select_formType.options[document.forms[0].select_formType.selectedIndex].text;
		if(document.forms[0].empName.value!='' && document.forms[0].chk_empflag.checked==false){
			  		alert('Should be checked Employee Name');
			  		document.forms[0].empName.focus();
			  		return false;
		}
		if (document.forms[0].chk_empflag.checked==true){
			  			empName=document.forms[0].empName.value;
        				frm_empflag=true;
       	}else{
	       		  frm_empflag=false;
       	}
		if(airportcode=='NO-SELECT'){
				airportcode='';
		}
		if(regionID=='CHQIAD' &&  airportcode=='NO-SELECT'){
			alert('User Should be select airportcode');
			document.forms[0].select_airport.focus();
			return false;
		}
		params = "&frm_region="+regionID+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_reportType="+reportType+"&frm_empnm="+empName+"&frm_emp_flag="+frm_empflag+"&frm_pensionno="+empserialNO+"&sortingOrder="+sortingOrder+"&frm_ltstmonthflag="+frm_ltstmonthflag+"&transMonthYear="+transMonthYear+"&frmAirportCode="+airportcode;
		url="<%=basePath%>reportservlet.do?method=TransferInOutReport"+params;
		
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
		var transferFlag,airportcode,regionID,yearID,frm_ltstmonthflag,monthID;
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
			
			if(document.forms[0].select_airport.length>1){
				airportcode=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
			}else{
				airportcode=document.forms[0].select_airport.value;
			}
			if(document.forms[0].select_year.selectedIndex>0){
				yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
			}else{
				yearID=document.forms[0].select_year.value;
			}
			var formPageSize=document.forms[0].frmpagesize.value;
			if(yearID=='NO-SELECT'){
				alert('User should be select Year');
				document.forms[0].select_year.focus();
				return false;
		    }
		    
			if(document.forms[0].select_month.selectedIndex>0){
				monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
			}else{
				monthID=document.forms[0].select_month.value;
			}
		
		
	       		  frm_ltstmonthflag="true";
       		
			bulkPrintFlag="false";
			var url ="<%=basePath%>psearch.do?method=getPFIDBulkPrintingList&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_month="+monthID+"&frm_year="+yearID+"&frm_ltstmonthflag="+frm_ltstmonthflag+"&frm_pagesize="+formPageSize;
		
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
		    //alert(stype.length);	
		  	obj1.options.length = 0;
		  	var chkLength=false;
		  	if(stype.length==1){
		  		chkLength=true;
		  	}else{
		  	obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
		  	}
		  	
		  	for(i=0;i<stype.length;i++){
		  	
		       		if(chkLength==true){
		       			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
		       		}else{
		       		

		       			obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
		       		}
					
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
			      	var stype1 = xmlHttp.responseXML.getElementsByTagName('PARAMSTR');
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
			for(k=0;k<stype1.length;k++){
		 		document.forms[0].transMonthYear.value=getNodeValue(stype1[k],'PARAM1');
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
		<%String monthID = "", yearDescr = "", region = "", monthNM = "";

            ArrayList yearList = new ArrayList();
            ArrayList pfidList = new ArrayList();
            Iterator regionIterator = null;
            Iterator monthIterator = null;
            HashMap hashmap = new HashMap();
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
                Set keys = hashmap.keySet();
                regionIterator = keys.iterator();

            }
            if (request.getAttribute("monthIterator") != null) {
                monthIterator = (Iterator) request
                        .getAttribute("monthIterator");
            }
            if (request.getAttribute("yearList") != null) {
                yearList = (ArrayList) request.getAttribute("yearList");
            }
            if (request.getAttribute("pfidList") != null) {
            	pfidList = (ArrayList) request.getAttribute("pfidList");
            }
            
            %>
		<form action="post">
			 
	<%=ScreenUtilities.screenHeader("Region-Wise Transfer In/Out Params")%>
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
				 
				
					<%if (session.getAttribute("usertype").equals("Admin")) {

                %>
				<tr>
					<td  class="tableTextBold"  align="right">
						Year:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
				<%}else{%>
							<tr>
					<td class="tableTextBold"  align="right">
						Year:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<Select name='select_year' Style='width:100px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
					<%for (int j = 0; j < userYears.length; j++) {%>
																			<option value='<%=userYears[j]%>'>
																				<%=userYears[j]%>
																			</option>
																			<%}%>
						</SELECT>
					</td>
				</tr>
				<%}%>
				<tr>
					<td class="tableTextBold"  align="right" class="TextField">
						Month:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
				<input type="hidden" name="chk_ltstmonthflag">
				<tr>
																	<td class="tableTextBold" align="right">
																		Region:<font color="red">&nbsp;*</font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_region" style="width:120px" class="TextField"  onChange="javascript:getAirports('airport');">
																			<option value="AllRegions">
																				[Select One]
																			</option>
																			<%while (regionIterator.hasNext()) {
																			region = hashmap.get(regionIterator.next()).toString();

																			%>
																			<option value="<%=region%>" >
																				<%=region%>
																			</option>
																			<%}%>
																		</SELECT>
																	</td>
																</tr>
																<tr>
																	<td class="tableTextBold"  align="right">
																		Aiport Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_airport" style="width:120px"  class="TextField">
																			<option value='NO-SELECT' Selected>
																				[Select One]
																			</option>
																		</SELECT>
																	</td>
																</tr>
				
				<tr>
					<td class="tableTextBold"  align="right">
						Employee Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<input type="text" name="empName" readonly="true" tabindex="3" class="TextField">
						<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');" alt="Click The Icon to Select EmployeeName" />
						<input type="checkbox" name="chk_empflag">
                <!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#"  onclick="return PfProcess()"  title="Click here to ReProcess PF" class="link"><font color="BLACK"> ReProcess PF&nbsp;&nbsp; </font></a> </td>
					<!-- 	<input type="button" class="btn" name="Submit" value="ReProcessPF"  onclick="javascript:PfProcess()"> -->
					
 
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap>Form Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td><Select name='select_formType' Style='width:100px'  class="TextField" align="left">
						<option value='Transfer InOut'>Transefer In/Out</option>
						
						</SELECT>
					</td>
				</tr>
				<TR>
					<td class="tableTextBold" align="right" nowrap>
						Report Type: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<SELECT NAME="select_reportType" style="width:88px" class="TextField" onChange="javascript:getAirports('data')">
														<option value="html">
								Html
							</option>
							<option value="ExcelSheet">
								Excel Sheet
							</option>
						</SELECT>
					</td>
					&nbsp;&nbsp; 
				</TR>					
				<tr>
					<td align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>

				<input type="hidden" name="empserialNO">
				<input type="hidden" name="transMonthYear">



				<tr>
					<td align="center" colspan="2">
						<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm()">
						<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
						<input type="button" class="btn" name="Submit" value="Cancel" onclick="javascript:history.back(-1)">
					    </td>
				</tr>
				<tr>
					<td align="center"></td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</form>
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>PensionView/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="label" >Processing.......</SPAN>
		</div>

	</body>
</html>
