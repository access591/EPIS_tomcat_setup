<!--
/*
  * File       : FinanceDataMapping.jsp
  * Date       : 29/01/2010
  * Author     : AIMS 
  * Description: 
  * Copyright (2008) by the Navayuga Infotech, all rights reserved.
  */
-->

<%@ page language="java"%>
<%@ page import="java.util.*,com.epis.utilities.CommonUtil,org.displaytag.tags.TableTag"%>
<%@ page import="com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ page import="com.epis.bean.rpfc.FinacialDataBean"%>
<%@ page import="com.epis.bean.rpfc.PensionBean,com.epis.bean.rpfc.EmpMasterBean"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.SearchInfo"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>



<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags-display" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			String region = "";
			CommonUtil common = new CommonUtil();
			HashMap hashmap = new HashMap();
			hashmap = common.getRegion();
			Set keys = hashmap.keySet();
			Iterator it = keys.iterator();
			String empNameChecked = "", unmappedFlag = "";
			boolean empNameChecked1 = false;
			if (request.getAttribute("empNameChecked") != null) {
				empNameChecked = request.getAttribute("empNameChecked")
						.toString();
				empNameChecked1 = Boolean.getBoolean(empNameChecked);

			}
			if (request.getAttribute("unmappedFlag") != null) {
				unmappedFlag = request.getAttribute("unmappedFlag").toString();

			}
			System.out.println("UnmappedFlag" + unmappedFlag);

			%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/dialog_box.css" />
		<script type="text/javascript" src="<%=basePath%>js/dialog_box.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript"> 
		
		 function editPensionMaster(cpfaccno,employeeName,employeeCode,region,airportCode){
				var flag="true";
				document.forms[0].action="<%=basePath%>personalForm.do?method=personalEdit&cpfacno="+cpfaccno+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode;
				document.forms[0].method="post";
				document.forms[0].submit();
		}
		function validateForm(empserialNO,cpfaccno,region) {
			
			var regionID="",airportcode="",reportType="",yearID="",monthID="",yearDesc="";
			
			var swidth=650;
			var sheight=500;
			reportType="Html";
			yearID="NO-SELECT";
          // code added on 09/03/2010
			var page="PensionContributionScreen";
			
			var mappingFlag="true";
			var pfidStrip='1 - 1'; 
			var params = "&frm_region="+region+"&frm_airportcode="+airportcode+"&frm_year="+yearID+"&frm_reportType="+reportType+"&cpfAccno="+cpfaccno+"&mappingFlag="+mappingFlag+"&frm_pfids="+pfidStrip+"&empserialNO="+empserialNO+"&page="+page;
			
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
			winHandle = window.open(newParams,"Utility","fullscreen=yes,menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
			winOpened = true;
			winHandle.window.focus();
		   }
	 function Search(){
	   var empNameCheak="",unmappedFlag="";
 		 if(window.document.forms[0].empNameChecked.checked==true){
 		  empNameCheak=window.document.forms[0].empNameChecked.checked;
 		 }
 		 else {
 		 empNameCheak=false;
 		 }
 		 if(window.document.forms[0].unmappedRecords.checked==true){
 			unmappedFlag=window.document.forms[0].unmappedRecords.checked;
 	 		 }
 	 		 else {
 	 		unmappedFlag=false;
 	 		 }
	 		 var allRecordsFlag="";
 		/*if(window.document.forms[0].allRecords.checked==true){
 			allRecordsFlag=window.document.forms[0].allRecords.checked;
 	 		 }
 	 		 else {
 	 			allRecordsFlag=false;
 	 		 }*/
 		
       	document.forms[0].action="<%=basePath%>personalForm.do?method=financeDataSearch&empNameCheak="+empNameCheak+"&unmappedFlag="+unmappedFlag+"&allRecordsFlag="+allRecordsFlag;
        document.forms[0].method="post";
		document.forms[0].submit();
   		 }

	 function updateStatus(){
	 	   	 var count=0,mappingStr='',pfid='',finalStrPfid='',finalMappingStr='',finalStrNotPfid='',showInfo="";
	 	   	
	 	   	  if(document.forms[0].mappingUpdate.length!=undefined){
					 for(var i=0;i<document.forms[0].mappingUpdate.length;i++){
						 count++;
						  alert(document.forms[0].mappingUpdate[i].checked);
					 	 if (document.forms[0].mappingUpdate[i].checked==true){
					 	 	mappingStr=document.forms[0].mappingUpdate[i].value.split(':');
					 	 	pfid=document.getElementById("empserialNO"+i).value;
					 	 	showInfo=pfid;
					 	 	if(pfid!=''){
					 	 		finalStrPfid=mappingStr+','+pfid;
					 	 	}else{
					 	 		if(finalStrNotPfid==''){
					 	 			finalStrNotPfid=mappingStr;
					 	 		}else{
					 	 			finalStrNotPfid=finalStrNotPfid+'@'+mappingStr;
					 	 		}
					 	 		
					 	 	}
					 	 	if(finalMappingStr!=''){
					 	 		finalMappingStr=finalMappingStr+'@'+finalStrPfid;
					 	 	}else{
					 	 		finalMappingStr=finalStrPfid;
					 	 	}
					 	 	
						}
					 }
			 }
	
	 		

		   	var answer =confirm('Are you sure, do you want lock PFID '+showInfo);
		   	if(answer){
		  		document.forms[0].action="<%=basePath%>personalForm.do?method=updateMappingFlag&frm_mappedRec="+finalMappingStr;
		   		document.forms[0].method="post";
				document.forms[0].submit();
		   }
	 }
	 function mappringRecords(){
	 var count=0,mappingStr='',pfid='',finalStrPfid='',finalMappingStr='',finalStrNotPfid='';
	
	   if(document.forms[0].mappingUpdate.length!=undefined){   
			 for(var i=0;i<document.forms[0].mappingUpdate.length;i++){
				 count++;
			 	 if (document.forms[0].mappingUpdate[i].checked==true){
			 	 	mappingStr=document.forms[0].mappingUpdate[i].value.split(':');
			 	 	pfid=document.getElementById("empserialNO"+count).value;
			 	 	if(pfid!=''){
			 	 		finalStrPfid=mappingStr+','+pfid;
			 	 	}else{
			 	 		if(finalStrNotPfid==''){
			 	 			finalStrNotPfid=mappingStr;
			 	 		}else{
			 	 			finalStrNotPfid=finalStrNotPfid+'@'+mappingStr;
			 	 		}
			 	 		
			 	 	}
			 	 	if(finalMappingStr!=''){
			 	 		finalMappingStr=finalMappingStr+'@'+finalStrPfid;
			 	 	}else{
			 	 		finalMappingStr=finalStrPfid;
			 	 	}
			 }else{
				 		   alert('User Should Select any PFID for mapping');		     
		       				return false;
				 	}
			}
	  }else{
	  		if(document.forms[0].mappingUpdate.checked){
				 		 	 	mappingStr=document.forms[0].mappingUpdate[i].value.split(':');
			 	 	pfid=document.getElementById("empserialNO"+count).value;
			 	 	if(pfid!=''){
			 	 		finalStrPfid=mappingStr+','+pfid;
			 	 	}else{
			 	 		if(finalStrNotPfid==''){
			 	 			finalStrNotPfid=mappingStr;
			 	 		}else{
			 	 			finalStrNotPfid=finalStrNotPfid+'@'+mappingStr;
			 	 		}
			 	 		
			 	 	}
			 	 	if(finalMappingStr!=''){
			 	 		finalMappingStr=finalMappingStr+'@'+finalStrPfid;
			 	 	}else{
			 	 		finalMappingStr=finalStrPfid;
			 	 	}
				 	}else{
				 		   alert('User Should select any PFID');		     
		       				return false;
				 	}
	  }
	 		

	 	
	 		var answer =confirm('Are you sure, do you want Map this '+finalMappingStr+' records');
		   if(answer){
		  	document.forms[0].action="<%=basePath%>personalForm.do?method=mappingUpdate&frm_mappedRec="+finalMappingStr;
		   	document.forms[0].method="post";
			document.forms[0].submit();
		   
	 	}
		 
	 }
	function pfidNotAvailable(cpflist){
	
		var  cpfdatalist=cpflist.split('@');
		
		
		var cpfaccno='',region='',finalcpfstring='',cpfdatastring='';
		for(k=0;k<cpfdatalist.length;k++){
			var indivdualcpfdatalist=cpfdatalist[k];
			var  fnalindivdualcpfdatalist=indivdualcpfdatalist.split(',');
			cpfdatastring=fnalindivdualcpfdatalist[1]+','+fnalindivdualcpfdatalist[4];
			if(finalcpfstring==''){
				finalcpfstring=cpfdatastring;
			}else{
				finalcpfstring=finalcpfstring+','+cpfdatastring;
			}
			
		}
		alert('These cpfaccno is not processing for pfids is not available'+finalcpfstring);

		
	}
		function pfidAvailable(cpflist){
		var  cpfdatalist=cpflist.split('@');
		var cpfaccno='',region='',finalcpfstring='',cpfdatastring='';
		for(k=0;k<cpfdatalist.length;k++){
			var indivdualcpfdatalist=cpfdatalist[k];
			var  fnalindivdualcpfdatalist=indivdualcpfdatalist.split(',');
			cpfdatastring=fnalindivdualcpfdatalist[6]+','+fnalindivdualcpfdatalist[4];
			if(finalcpfstring==''){
				finalcpfstring=cpfdatastring;
			}else{
				finalcpfstring=finalcpfstring+','+cpfdatastring;
			}
			
		}
		return finalcpfstring;

		
	}
	 function callReport(){
		 var empNameCheak="",unmappedFlag="";
 		 if(window.document.forms[0].empNameChecked.checked==true){
 		  empNameCheak=window.document.forms[0].empNameChecked.checked;
 		 } 		
 		 else {
 		 empNameCheak=false;
 		 }
 		 var employeeName=window.document.forms[0].employeeName.value;
 		 var region=window.document.forms[0].region.value;
 		 if(window.document.forms[0].unmappedRecords.checked==true){
 			unmappedFlag=window.document.forms[0].unmappedRecords.checked;
 	 		 }
 	 		 else {
 	 		unmappedFlag=false;
 	 		 }
 		    var swidth=screen.Width-10;
 			var sheight=screen.Height-150;
	   		var reportID="",sortColumn="EMPLOYEENAME";
	   	 	reportID=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
	   	 var url="<%=basePath%>personalForm.do?method=financeDataSearch&empNameCheak="+empNameCheak+"&unmappedFlag="+unmappedFlag+"&reportType="+reportID+"&employeeName="+employeeName+"&region="+region;
	  	   	 //	var url="<%=basePath%>psearch?method=personalEmpReport&reportType="+reportID+"&frm_sortcolumn="+sortColumn;
	   	 	wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 }
	 var xmlHttp;
	 function getNodeValue(obj,tag){
			return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
	   	}
		function createXMLHttpRequest(){
			if(window.ActiveXObject){
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		 	}else if (window.XMLHttpRequest){
				xmlHttp = new XMLHttpRequest();
			 }
		}
	function getAirports(){	
		var regionID;
		regionID=document.forms[0].region.options[document.forms[0].region.selectedIndex].text;
		createXMLHttpRequest();	
		var url ="<%=basePath%>psearch.do?method=getFinanceTblAirports&region="+regionID;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getAirportsList;
		xmlHttp.send(null);
 }
	function getAirportsList(){
		if(xmlHttp.readyState ==3 ||  xmlHttp.readyState ==2 ||  xmlHttp.readyState ==1){
		 	
		}
		if(xmlHttp.readyState ==4){
			if(xmlHttp.status == 200){ 
				var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
				
				if(stype.length==0){
				 	var obj1 = document.getElementById("select_airport");
				  	obj1.options.length=0; 
		  			obj1.options[obj1.options.length]=new Option('[Select One]','','true');
				}else{
		 		   	var obj1 = document.getElementById("select_airport");
		 		  	obj1.options.length = 0;
				 	for(i=0;i<stype.length;i++){
		  				if(i==0){
							obj1.options[obj1.options.length]=new Option('[Select One]','','true');
						}
						obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
					}
		  		}
			}
		}
	}
	function selectCheckboxes(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData,dateOfBirth;
		   if(document.forms[0].chk==undefined){
		  	alert('User Should select request');		     
		       				return false;
		   }     
		   if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){	
		           
			          pfid=str[0];
			          obj=str[1];
			          employeeName=str[2];
			          employeeCode=str[3];
			          region=str[4];
			          airportCode=str[5];
			          index=0;
			          totalData=0;
			    
			        }
			        
			        
			        if(frmType=='Lock'){
			        	updateStatus();
			        }else if(frmType=='Mapping'){
			        	mappringRecords();
			        }else if(frmType=='Print'){
			        
			        	validateForm(pfid,obj,region);
			        }
		   	
		}
  	</script>
	</head>
	<body class="BodyBackground">
		<%=ScreenUtilities.screenHeader("Finance Data Mapping")%>
		<form name="datamapping" method="post" action="<%=basePath%>personalForm.do?method=financeDataSearch">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<%boolean flag = false;%>

				<tr>
					<td height="15%">

						<table width="90%" border="0">
							<tr>
								<td class="tableTextBold">
									Employee No. :
								</td>
								<td>
									<input type="text" name="employeeCode" class="TextField">
								</td>
								<td class="tableTextBold">
									CPFAcno:
								</td>
								<td>
									<input type="text" name="cpfaccno" class="TextField">
								</td>
								<td>
									<input type="hidden" name="airPortCD">
								</td>
							</tr>


							<tr>
								<%EmpMasterBean empSerach = new EmpMasterBean();
			if (request.getAttribute("empSerach") != null) {
				empSerach = (EmpMasterBean) request.getAttribute("empSerach");
			}

			%>
								<td class="tableTextBold">
									EmployeeName:
								</td>
								<td>
									<input class="TextField" type="text" name="employeeName" value='<%=empSerach.getEmpName()%>'>
									&nbsp;
									
									<input name="empNameChecked" type="checkbox" <%=empNameChecked.equals("true")?"checked":"unchecked" %> />
								</td>

								<td class="tableTextBold">
									Region:
								</td>
								<td>
									<select name="region" style="width: 130px" onChange="javascript:getAirports()" class="TextField">
										<option value="">
											[Select One]
										</option>

										<%int j = 0;
			boolean exist = false;
			while (it.hasNext()) {
				region = hashmap.get(it.next()).toString();
				j++;
				if (region.equalsIgnoreCase(empSerach.getRegion()))
					exist = true;
				if (region.equalsIgnoreCase(empSerach.getRegion())) {

					%>
										<option value="<%=empSerach.getRegion()%>" <% out.println("selected");%>>
											<%=empSerach.getRegion()%>
										</option>


										<%} else {%>
										<option value="<%=region%>">
											<%=region%>
										</option>

										<%}

			%>

										<%}

			%>

									</select>
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									PFID:
								</td>
								<td>
									<input type="text" name="pfid" class="TextField"></input>
								</td>
								<td class="tableTextBold">
									Airport Code:
								</td>
								<td>
									<select name="select_airport" class="TextField">
										<option value="">
											[Select One]
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									UnMapped Records List
								</td>

								<td colspan="3">
									<input type="checkbox" name="unmappedRecords" <%=unmappedFlag.equals("true")?"checked":"unchecked" %> />
								</td>

							</tr>
							<tr>


								<td colspan="4" align="center">
									<input type="button" class="btn" value="Search" class="btn" onclick="Search();">
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<%=ScreenUtilities.searchSeperator()%>


			<%FinacialDataBean dbBeans = new FinacialDataBean();
			SearchInfo getSearchInfo = new SearchInfo();

			if (request.getAttribute("financeDatalist") != null) {
				int totalData = 0, index = 0, totalUnmappedRecords = 0;
				String empNameCheck = "";
				SearchInfo searchBean = new SearchInfo();
				//	EmpMasterBean empSerach = new EmpMasterBean();
				empSerach = (EmpMasterBean) request.getAttribute("empSerach");
				PensionBean pensionBean = new PensionBean();
				flag = true;
				ArrayList dataList = new ArrayList();
				BottomGridNavigationInfo bottomGrid = new BottomGridNavigationInfo();
				searchBean = (SearchInfo) request.getAttribute("searchBean");
				dataList = (ArrayList) request.getAttribute("financeDatalist");

				session.setAttribute("getSearchBean1", empSerach);
				empNameCheck = empSerach.getEmpNameCheak();

				System.out.println("empNameCheck " + empNameCheck);
				totalData = searchBean.getTotalRecords();
				totalUnmappedRecords = searchBean.getTotalUnmappedRecords();
				bottomGrid = searchBean.getBottomGrid();
				index = searchBean.getStartIndex();
				region = (String) request.getAttribute("region");

				System.out.println("Size===After=========" + dataList.size());

				%>
			<table width="90%" align="center" border="0" cellspacing="0" cellpadding="0">

				<tr>
					<td width="30%" height="29" align="left" valign="top">
						<%=ScreenUtilities.getAcessOptions(session, 5)%>
					</td>
					<td width="50%" align="left" valign="top">
						&nbsp;
					</td>
				</tr>

				<%ResourceBundle bundle = null;

				bundle = ResourceBundle
						.getBundle("com.epis.resource.ApplicationResources");
				int sizeofgridlenght = Integer.parseInt(bundle
						.getString("common.gridlength"));
				int autoIncrement = 0;

				%>
				<logic:present name="financeDatalist">

					<tr>
						<td align="center">

							<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="financeDatalist" style="width:100%;height:50px" pagesize="<%=sizeofgridlenght%>" name="requestScope.financeDatalist"
								requestURI="./personalForm.do?method=financeDataSearch">

								<display:setProperty name="export.amount" value="list" />
								<display:setProperty name="export.excel.filename" value="financedatamapping.xls" />
								<display:setProperty name="export.pdf.filename" value="financedatamapping.pdf" />
								<display:setProperty name="export.rtf.filename" value="financedatamapping.rtf" />
								<display:column media="html" headerClass="GridHBg" class="GridLCells">
									<input type="radio" name="chk"
										value="<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getCpfAcNo()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeName()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeCode()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getRegion()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getAirportCode()%>" />
								</display:column>
								<%if (sizeofgridlenght <= autoIncrement) {
					autoIncrement = 1;
				} else {
					autoIncrement = autoIncrement + 1;
				}

				%>
								<display:column sortable="true" media="html" headerClass="GridLTHCells" class="GridLTCells" title="PFID">
									<input type="text" id="empserialNO<%=autoIncrement%>" class="TextField" size="10" name="empserialNO" value="<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>" />
									<input type="hidden" id="empserialNO<%=autoIncrement%>" class="TextField" size="10" name="empserialNO" value="<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>" />
								</display:column>

								<display:column sortable="true" property="cpfAcNo" headerClass="GridLTHCells" class="GridLTCells" title="CPFAC.No" />
								<display:column sortable="true" style="white-space: nowrap" property="employeeName" headerClass="GridLTHCells" class="GridLTCells" title="EmployeeName" />
								<display:column sortable="true" style="white-space: nowrap" property="employeeCode" headerClass="GridLTHCells" class="GridLTCells" title="EmployeeNo" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" style="white-space: nowrap" property="desegnation" headerClass="GridLTHCells" class="GridLTCells" title="Designation" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" style="white-space: nowrap" property="dateofBirth" headerClass="GridLTHCells" class="GridLTCells" title="DateofBirth" />
								<display:column sortable="true" style="white-space: nowrap" property="dateofJoining" headerClass="GridLTHCells" class="GridLTCells" title="DateofJoining" />
								<display:column sortable="true" style="white-space: nowrap" property="airportCode" headerClass="GridLTHCells" class="GridLTCells" title="Airport Code" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" style="white-space: nowrap" property="region" headerClass="GridLTHCells" class="GridLTCells" title="Region" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" style="white-space: nowrap" property="totalRecrods" headerClass="GridLTHCells" class="GridLTCells" title="No of Months" decorator="com.epis.utilities.StringFormatDecorator" />
								<display:column sortable="true" media="html" headerClass="GridLTHCells" class="GridLTCells" title="&nbsp;&nbsp;&nbsp;">
									<input type="checkbox" name="mappingUpdate"
										value="<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getCpfAcNo()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeName()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeCode()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getRegion()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getAirportCode()%>" />
								</display:column>
							</display:table>
						</td>
					</tr>
				</logic:present>
			</table>

			<%}%>

			<%=ScreenUtilities.screenFooter()%>
		</form>


	</body>
</html>
