

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.rpfc.FinancialYearBean,com.epis.info.login.LoginInfo"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                      LoginInfo logInfo=new LoginInfo();
                    if(session.getAttribute("user")!=null)
					{
				      logInfo=(LoginInfo)session.getAttribute("user");
				      System.out.println("..........Profile.........."+logInfo.getProfile());
                    
                    }
                    	
            %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<script type="text/javascript">

 function LoadWindow(params){
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
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
		document.forms[0].action="<%=basePath%>reportservlet.do?method=loadpfcardInput";
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
        var url="<%=basePath%>reportservlet?method=ProcessforAdjOb&pensionno="+empserialNO;
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
		if(regionID1=='CHQNAD' || regionID1=='North Region'|| regionID1=='South Region' || (regionID1=='CHQIAD'&&airportcode1=='OFFICE COMPLEX')){
			if(!(userID=='SHIKHA' || userID=='NRFIN' || userID=='SRFIN')){
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
	
		if(reportType=='[Select One]'){
			alert('User Should be select report Type');
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
		if(document.forms[0].select_month.selectedIndex>0){
		monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
		}else{
		monthID=document.forms[0].select_month.value;
		}

		
		var transMonthYear=document.forms[0].transMonthYear.value;
		var empserialNO=document.forms[0].empserialNO.value;
		if(empserialNO=='' ){
			
		pfidStrip=document.forms[0].select_pfidlst.options[document.forms[0].select_pfidlst.selectedIndex].value;
	//	alert('User Should  Select Bulk Print Option');
	//	document.forms[0].select_pfidlst.focus();
	//	return false;
		}else{
		if(empserialNO!=''){
			pfidStrip='NO-SELECT'; 
		}else{
			pfidStrip='1 - 1'; 
		}
		}

	
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
		if (document.forms[0].signature.checked==true){
  			frm_signature=true;
		}else{
			frm_signature=false;
		}
		
		params = "&frm_region="+regionID+"&frm_year="+yearID+"&frm_reportType="+reportType+"&frm_empnm="+empName+"&frm_emp_flag="+frm_empflag+"&frm_pensionno="+empserialNO+"&frm_pfids="+pfidStrip+"&sortingOrder="+sortingOrder+"&frm_ltstmonthflag="+frm_ltstmonthflag+"&transMonthYear="+transMonthYear+"&frmAirportCode="+airportcode+"&frm_blkprintflag="+bulkPrintFlag+"&frm_signature="+frm_signature;
		url="<%=basePath%>reportservlet.do?method=cardReport"+params;
		if(yearID=='2010'){
		alert("Under Contruiction........");
		}else{
		if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   		}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   	   		
   				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
						winOpened = true;
						wind1.window.focus();
      	}
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
            if (request.getAttribute("pfidList") != null) {
            	pfidList = (ArrayList) request.getAttribute("pfidList");
            }
            
            %>
		<form action="post">
		<%=ScreenUtilities.screenHeader("PF Card Report Params")%>
			<table width="75%" border="0" align="center" cellpadding="2" cellspacing="0" class="tbborder">
				
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				
				<tr>
					<td class="tableTextBold" align="right">
						Year:					</td>
					<td align=left>
						<select name='select_year' style='width:100px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
						<%for (int j = 0; j < year.length; j++) {%>
																			<option value='<%=year[j]%>'>
																				<%=year[j]%>
																			</option>
																			<%}%>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="tableTextBold" align="right">
						Month:
					</td>
					<td align=left>
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
							<input type="checkbox" name="chk_ltstmonthflag">
					</td>
				</tr>
				<tr>
																	<td class="tableTextBold" align="right">
																		Region:<font color=red>*</font> 
																	</td>
																	<td align=left>
																		<SELECT NAME="select_region" style="width:120px" onChange="javascript:getAirports('airport');" class="TextField">
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
																		Aiport Name:
																	</td>
																	<td align=left>
																		<select name="select_airport" style="width:120px" class="TextField">
																			<option value='NO-SELECT' selected>
																				[Select One]
																			</option>
																		</select>
																	</td>
																</tr>
				<tr>
					<td class="tableTextBold" align="right">Page Size:</td>
					<td align=left><input type="text" name="frmpagesize" value="100" class="TextField"></td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">Employee Name:</td>
					<td align=left>
						<input type="text" name="empName" class="TextField" readonly="true" tabindex="3" value="<%=request.getAttribute("empName")%>">
						<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');" alt="Click The Icon to Select EmployeeName" />
						<input type="checkbox" name="chk_empflag">
					</td>

				</tr>
				<tr>
					<td class=tableTextBold align="right">Form Type:</td>
					<td align="left"><select name='select_formType' style='width:100px' class="TextField">
						<option value='PF Card'>PF Card</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class=tableTextBold align="right">Report Type:</td>
					<td align="left">
						<select name="select_reportType" style="width:88px" onchange="javascript:getAirports('data')" class="TextField">
								<option value='NO-SELECT' selected>[Select One]</option>
							<option value="html">
								Html
							</option>
							<option value="ExcelSheet">
								Excel Sheet
							</option>
						</select>
					</td>
					
				</tr>
					<tr>
							<td class="tableTextBold" align="right">PF ID Printing List: </td>
							<td align="left">									
								<select name="select_pfidlst" style="width:120px" class="TextField" >
								
							  	<option value='NO-SELECT' selected>[Select One]</option>
	                       		<%for (int pfid = 0; pfid < pfidList.size(); pfid++) {%>
									<option value='<%=pfidList.get(pfid)%>'><%=pfidList.get(pfid)%></option>
								<%}%>
								</select>
							</td>
						</tr>
				<tr>
					<td class="tableTextBold" align="right">Signature:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td align="left"><input type="checkbox" name="signature"></td>
                </tr>
				<tr>
					<td align="center">
						&nbsp;
					</td>
				</tr>


				<input type="hidden" name="empserialNO"  value="<%=request.getAttribute("empPFID")%>" >

				<input type="hidden" name="transMonthYear">



				<tr>
					<td align="center" colspan="2">
						<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm()">
						<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
						<input type="button" class="btn" name="Submit" value="Cancel" onclick="javascript:history.back(-1)">
					    <input type="button" class="btn" name="button" value="Bulk Print" onclick="javascript:bulkreport('<%=session.getAttribute("userid")%>')">
                   </td>
				</tr>
				<tr>
					<td align="center"></td>
				</tr>
			</table>



		
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>
<%=ScreenUtilities.screenFooter()%>
</form>
	</body>
</html>