

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                    
                   
            %>
<html>
	<HEAD>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

		<script type="text/javascript">

 function LoadWindow(params){
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
	winHandle = window.open(newParams,"AAIEPFFORMIII","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}
 
 	function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
			document.forms[0].empName.value=employeeName;
			document.forms[0].empserialNO.value=empSerialNo;
		  	document.forms[0].chk_empflag.checked=true;		
		}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>aaiepfreportservlet.do?method=loadepf3";
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

	
	function validateForm() {
		var regionID="",airportcode="",params="",sortingOrder="",empName="",reportType="",url="",yearID="",monthID="",yearDesc="",formType="",toYearID="",formType="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var transferFlag="",pfidStrip="";	
		reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
		sortingOrder=document.forms[0].select_sorting.options[document.forms[0].select_sorting.selectedIndex].value;
		if(document.forms[0].select_year.selectedIndex>0){
		yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
		}else{
		yearID=document.forms[0].select_year.value;
		}
		if(document.forms[0].select_toyear.selectedIndex>0){
			toyearID=document.forms[0].select_toyear.options[document.forms[0].select_toyear.selectedIndex].value;
			}else{
			toyearID=document.forms[0].select_toyear.value;
			}
		 
		if(yearID=='NO-SELECT'){
			alert('User should  select FromYear');
			document.forms[0].select_year.focus();
			return false;
		}
		if(toyearID=='NO-SELECT'){
			alert('User should  select ToYear');
			document.forms[0].select_toyear.focus();
			return false;
		}
		 
		if(reportType=='[Select One]'){
			alert('User should be select report Type');
			document.forms[0].select_reportType.focus();
			return false;
		}
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
			if(document.forms[0].select_from_month.selectedIndex>0){
				monthID=document.forms[0].select_from_month.options[document.forms[0].select_from_month.selectedIndex].value;
			}else{
				monthID=document.forms[0].select_from_month.value;
			}
			if(monthID=='NO-SELECT'){
				alert('User Should be select From Month');
				document.forms[0].select_from_month.focus();
				return false;
			}
			if(document.forms[0].select_to_month.selectedIndex>0){
				toMonthID=document.forms[0].select_to_month.options[document.forms[0].select_to_month.selectedIndex].value;
			}else{
				toMonthID=document.forms[0].select_to_month.value;
			}
			if(toMonthID=='NO-SELECT'){
				alert('User Should be select To Month');
				document.forms[0].select_to_month.focus();
				return false;
			}
		
	//	if(monthID!='NO-SELECT'){
		//	monthID=monthNames[Number(monthID)];
		//}
		
	
		var empserialNO=document.forms[0].empserialNO.value;
		if(empserialNO=='' ){
		pfidStrip=document.forms[0].select_pfidlst.options[document.forms[0].select_pfidlst.selectedIndex].value;
		}else{
		if(empserialNO!=''){
			pfidStrip='NO-SELECT'; 
		}else{
			pfidStrip='1 - 1'; 
		}
		}


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
		
		params = "&frm_region="+regionID+"&frm_year="+yearID+"&frm_month="+monthID+"&frm_reportType="+reportType+"&frm_empnm="+empName+"&frm_emp_flag="+frm_empflag+"&frm_pensionno="+empserialNO+"&frm_pfids="+pfidStrip+"&sortingOrder="+sortingOrder+"&frm_ToYear="+toyearID+"&frm_to_month="+toMonthID+"&frm_airportcode="+airportcode;
		url="<%=basePath%>aaiepfreportservlet.do?method=epf3report"+params;
		 //alert(url);
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
	
		var transferFlag,airportcode,regionID,yearID,toYearID,frm_ltstmonthflag,monthID,toMonthID;
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
			
			if(yearID=='NO-SELECT'){
				alert('User Should be select Year');
				document.forms[0].select_year.focus();
				return false;
			}
		    
			if(document.forms[0].select_from_month.selectedIndex>0){
				monthID=document.forms[0].select_from_month.options[document.forms[0].select_from_month.selectedIndex].value;
			}else{
				monthID=document.forms[0].select_from_month.value;
			}
			 
			if(monthID=='NO-SELECT'){
				alert('User Should be select From Month');
				document.forms[0].select_from_month.focus();
				return false;
			}
			
			if(document.forms[0].select_toyear.selectedIndex>0){
				toYearID=document.forms[0].select_toyear.options[document.forms[0].select_toyear.selectedIndex].value;
			}else{
				toYearID=document.forms[0].select_toyear.value;
			}
			 
			 
			if(document.forms[0].select_to_month.selectedIndex>0){
				toMonthID=document.forms[0].select_to_month.options[document.forms[0].select_to_month.selectedIndex].value;
			}else{
				toMonthID=document.forms[0].select_to_month.value;
			}
			if(toMonthID=='NO-SELECT'){
				alert('User Should be select To Month');
				document.forms[0].select_to_month.focus();
				return false;
			}
			  frm_ltstmonthflag="false";
			  
			 var resultedYearId;
			 //resultedYearId=yearID+"-"+toYearID;
			// alert(resultedYearId);
			var url ="<%=basePath%>psearch.do?method=getPFIDListWthoutTrnFlag&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_month="+monthID+"&frm_year="+yearID+"&frm_to_month="+toMonthID+"&frm_to_year="+toYearID+"&frm_ltstmonthflag="+frm_ltstmonthflag;
	//	alert(url);
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
		  document.forms[0].select_from_month.value="04";
		 document.forms[0].select_to_month.value="03";
	}

</script>
	</HEAD>
	<body class="BodyBackground" onload="javascript:frmload()">
		<%String monthID = "", yearDescr = "", region = "", monthNM = "",monthNM1="",monthID1="";;
			String[] year=null;
            ArrayList yearList = new ArrayList();
            ArrayList pfidList = new ArrayList();
            Iterator regionIterator = null;
            Iterator monthIterator = null;
            Iterator monthToIterator = null;
            HashMap hashmap = new HashMap();
             
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
                Set keys = hashmap.keySet();
                regionIterator = keys.iterator();

            }
             if (request.getAttribute("year") != null) {
                year = (String[]) request.getAttribute("year");
            }
            if (request.getAttribute("monthIterator") != null) {
                monthIterator = (Iterator) request
                        .getAttribute("monthIterator");
            }
        	
        	if (request.getAttribute("monthToIterator") != null) {
                monthToIterator = (Iterator) request.getAttribute("monthToIterator");
            }
        	
            if (request.getAttribute("pfidList") != null) {
            	pfidList = (ArrayList) request.getAttribute("pfidList");
            }
              
            
            %>
		<form action="post">
		<%=ScreenUtilities.screenHeader("AAI EPF-3 Input Params")%>


			<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbborder">
			 
				<tr>
					<td class="tableTextBold" align="right">
						From Year / Month:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<Select name='select_year' Style='width:50px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
					<%for (int j = 0; j < year.length; j++) {%>
																			<option value='<%=year[j]%>'>
																				<%=year[j]%>
																			</option>
																			<%}%>
						</SELECT>
					
						<select name="select_from_month" style="width:50px" class="TextField">
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
						To Year / Month:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<Select name='select_toyear' Style='width:50px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
					<%for (int j = 0; j < year.length; j++) {%>
																			<option value='<%=year[j]%>'>
																				<%=year[j]%>
																			</option>
																			<%}%>
						</SELECT>
						<select name="select_to_month" style="width:50px" class="TextField" >
							<Option Value='NO-SELECT'>
								Select One
							</Option>

				<%while (monthToIterator.hasNext()) {
                Map.Entry mapEntry1 = (Map.Entry) monthToIterator.next();
                monthID1 = mapEntry1.getKey().toString();
                monthNM1 = mapEntry1.getValue().toString();

                %>

							<option value="<%=monthID1%>">
								<%=monthNM1%>
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
																		<SELECT NAME="select_region" style="width:120px" class="TextField" onChange="javascript:getAirports('airport');">
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
						<input type="text" name="empName" readonly="readonly" tabindex="3" class="TextField">
						<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');" alt="Click The Icon to Select EmployeeName" />
						<input type="checkbox" name="chk_empflag">
					</td>

				</tr>
				<tr>
					<td class=tableTextBold align="right" nowrap>Form Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td><Select name='select_formType' Style='width:100px' class="TextField">
						<option value='AAIEPF-III'>AAIEPF-III</option>
						
						</SELECT>
					</td>
				</tr>
				<tr>
					<td class=tableTextBold align="right" nowrap>
						Report Type: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<SELECT NAME="select_reportType" style="width:88px" class="TextField" onChange="javascript:getAirports('data')">
								<option value='NO-SELECT' Selected>[Select One]</option>
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
							<td class="tableTextBold" align="right">PF ID Printing List:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
							<td>									
								<SELECT NAME="select_pfidlst" style="width:120px" class="TextField">
								
							  	<option value='NO-SELECT' Selected>[Select One]</option>
	                       		<%for (int pfid = 0; pfid < pfidList.size(); pfid++) {%>
									<option value='<%=pfidList.get(pfid)%>'><%=pfidList.get(pfid)%></option>
								<%}%>
								</SELECT>
							</td>
						</tr>
				<tr>
				<tr>
																	<td class=tableTextBold align="right" nowrap>
																		Sorting Order: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	</td>
																	<td>
																		<SELECT NAME="select_sorting" style="width:88px" class="TextField">
																		
																			<option value="PENSIONNO">
																				PFID
																			</option>
																			<option value="EMPLOYEENAME">
																				EmployeeName
																			</option>
																		
																		</SELECT>
																	</td>
																</tr>
				<tr>
					<td align="center">
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>

				<input type="hidden" name="empserialNO" readonly="true" tabindex="3" />




				<tr>
				
					<td align="center" colspan="3">
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
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>

	</body>
</html>
