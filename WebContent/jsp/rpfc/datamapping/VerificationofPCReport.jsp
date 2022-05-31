<!--
/*
  * File       : FinanceDataMapping.jsp
  * Date       : 29/01/2010
  * Author     : AIMS 
  * Description: 
  * Copyright (2008) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil"%>
<%@ page
	import="com.epis.bean.rpfc.DatabaseBean,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ page import="com.epis.bean.rpfc.FinacialDataBean,com.epis.utilities.ScreenUtilities"%>
<%@ page import="com.epis.bean.rpfc.PensionBean,com.epis.bean.rpfc.EmpMasterBean"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
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
	String empNameChecked="",unmappedFlag="";
	boolean empNameChecked1=false;
	if(request.getAttribute("empNameChecked")!=null){
	  empNameChecked=request.getAttribute("empNameChecked").toString();
	  empNameChecked1 = Boolean.getBoolean(empNameChecked);
	 	
	}
	if(request.getAttribute("unmappedFlag")!=null){
		unmappedFlag=request.getAttribute("unmappedFlag").toString();
		
	}
	String pcreportverified="",form6_7_8status="",claimprocess="";
	
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
<SCRIPT type="text/javascript"
	src="<%=basePath%>js/calendar.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="<%=basePath%>js/DateTime.js"></SCRIPT>
<script type="text/javascript"> 

		function formReset(){
			document.forms[0].action="<%=basePath%>personalForm.do?method=loadVerificationReport";
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
			winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
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
 		 var pfidfrom=document.forms[0].pfidfrom.value;
 		 var pcverified=document.forms[0].pcverified.value;
 		 var allRecordsFlag="true";
 		document.forms[0].action="<%=basePath%>personalForm.do?method=financeDataSearch&empNameCheak="+empNameCheak+"&unmappedFlag="+unmappedFlag+"&allRecordsFlag="+allRecordsFlag+"&pfidfrom="+pfidfrom+"&pcverified="+pcverified;
        document.forms[0].method="post";
		document.forms[0].submit();
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
	   	 var url="<%=basePath%>psearch?method=financeDataSearch&empNameCheak="+empNameCheak+"&unmappedFlag="+unmappedFlag+"&reportType="+reportID+"&employeeName="+employeeName+"&region="+region;
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
	function updateStatus(status,pensionno){
		 var answer="";
		if(status=="N"){
		   answer =confirm('Are you sure, do you want Lock This Record from Form-6-7-8');
		}else{
		   answer =confirm('Are you sure, do you want Unlock This Record from Form-6-7-8');
		}
		   if(answer){
			   var formsstatus=status;
		var url="<%=basePath%>personalForm.do?method=formsDisable&formsstatus="+formsstatus+"&pensionno="+pensionno;
		 createXMLHttpRequest();	
	   	 	xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = updateStatus1;
			xmlHttp.send(null);
		   }
	 }
	 function updateStatus1()
		{
		if(xmlHttp.readyState ==4)
		{
		 alert(xmlHttp.responseText);
			}
		}
	 function pcreportverified(status,pensionno){
		 var answer="";
		if(status=="N"){
		   answer =confirm('Are you sure, do you want to Change The Status of PCReport Verified as No');
		}else{
		   answer =confirm('Are you sure, do you want to Change The Status of PCReport Verified as Yes');
		}
		
		   if(answer){
			   var pcreportstatus=status;
		var url="<%=basePath%>personalForm.do?method=formsDisable&pcreportstatus="+pcreportstatus+"&pensionno="+pensionno;
		 createXMLHttpRequest();	
	   	 	xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = pcreportverified1;
			xmlHttp.send(null);
		   }
	 }
	 function pcreportverified1()
		{
		if(xmlHttp.readyState ==4)
		{
		 alert(xmlHttp.responseText);
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
			        
			        
			        if(frmType=='Edit'){
			        
			       
			        	validateForm(pfid,obj,region);
			        }
		   	
		}
		// Migration --------------
		
		function pensionClaims(status,pensionno){
		 
		 var answer="";
		if(status=="Y"){
		   answer =confirm('Are you sure, do you want to Change The Status of PensionClaims Process as Yes');
		}else{
		   answer =confirm('Are you sure, do you want to Change The Status of PensionClaims Process as No');
		}
		   if(answer){
			   var pensionclaimstatus=status;
		var url="<%=basePath%>personalForm.do?method=formsDisable&pensionclaimstatus="+pensionclaimstatus+"&pensionno="+pensionno;
		 createXMLHttpRequest();	
	   	 	xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = pensionclaims;
			xmlHttp.send(null);
		   }
	 }
	 function pensionclaims()
		{
		if(xmlHttp.readyState ==4)
		{
		 alert(xmlHttp.responseText);
			}
		}
		
	// Migration --------------	
  	</script>
</head>
<body class="BodyBackground">
<form method="post"
	action="<%=basePath%>psearch?method=financeDataSearch">
<%=ScreenUtilities.screenHeader("Verification Of PC Report")%>
<table width="100%" border="0" align="center" cellpadding="1"
	cellspacing="1">


	<%boolean flag = false;%>
	

			<tr>
				<td class="tableTextBold">Employee No. :</td>
				<td><input type="text" name="employeeCode" class="TextField"/></td>
				<td class="tableTextBold">CPFAcno:</td>
				<td><input type="text" name="cpfaccno" class="TextField"/></td>

				<!-- 	<td class="label">
									AirportCode:
								</td> -->
				<td><input type="hidden" name="airPortCD"/></td>
			</tr>
            

			<tr>
				<%    EmpMasterBean empSerach = new EmpMasterBean();
                     if(request.getAttribute("empSerach")!=null){
				           empSerach = (EmpMasterBean) request.getAttribute("empSerach"); } %>
				<td class="tableTextBold">EmployeeName:</td>
				<td><input type="text" name="employeeName"
					value="<%=empSerach.getEmpName()%>" class="TextField">&nbsp; <input
					name="empNameChecked" type="checkbox"
					<%=empNameChecked.equals("true")?"checked":"unchecked" %> /></td>

				<td class="tableTextBold">Region:</td>
				<td><select name="region" style="width: 130px"
					onchange="javascript:getAirports()" class="TextField">
					<option value="">[Select One]</option>

					<%int j = 0;
			boolean exist = false;
			while (it.hasNext()) {
				region = hashmap.get(it.next()).toString();
				j++;
				if (region.equalsIgnoreCase(empSerach.getRegion()))
					exist = true;
				if (region.equalsIgnoreCase(empSerach.getRegion())) {

					%>
					<option value="<%=empSerach.getRegion()%>"
						<% out.println("selected");%>><%=empSerach.getRegion()%>
					</option>


					<%} else {%>
					<option value="<%=region%>"><%=region%></option>

					<%}

			%>

					<%}

			%>

				</select></td>
			</tr>
			<tr>
				<td class="tableTextBold">PFID:</td>
				<td><input type="text" name="pfid" class="TextField"/></td>
				<td class="tableTextBold">Airport Code:</td>
				<td><select name="select_airport" style="width: 130px" class="TextField">
					<option value="">[Select One]</option>
				</select></td>
			</tr>
		<tr>
             <td class="tableTextBold"> PFID From:</td><td><input type="text" name="pfidfrom"></input></td>
       		 <td class="tableTextBold">PCReportVerified</td><td><select name="pcverified" style="width: 130px" class="TextField">
					<option value="">[Select One]</option>
                    <option value="Y">Yes</option>
                   <option value="N"> No </option>
				</select></td>  
       </tr>

			<tr>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;</td>

				<td><input type="button" class="btn" value="Search" class="btn"
					onclick="Search();"/> <input type="button" class="btn"
					value="Reset" onclick="javascript:formReset()"
					class="btn"/> <input type="button" class="btn"
					value="Cancel" onclick="javascript:history.back(-1)" class="btn"/>
				</td>
				<td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			
		</table>

		

<%=ScreenUtilities.searchSeperator()%>	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbborder">
			<tr>
                   <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                   <td width="50%" align="left" valign="top">&nbsp;</td>
               </tr>
              <%
									ResourceBundle bundle=null;
									
									bundle=ResourceBundle.getBundle("com.epis.resource.ApplicationResources");
									int sizeofgridlenght=Integer.parseInt(bundle.getString("common.gridlength"));
									int autoIncrement=0;
								%>
               		  <logic:present name="financeDatalist" >
					     
							<tr>							
								<td align="center" width="100%">
							
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="financeDatalist" style="width:100%;height:50px" pagesize="<%=sizeofgridlenght%>" name="requestScope.financeDatalist" requestURI="./personalForm.do?method=financeDataSearch" >											
							
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="verficationofpcreport.xls" />
    								<display:setProperty name="export.pdf.filename" value="verficationofpcreport.pdf" />
    								<display:setProperty name="export.rtf.filename" value="verficationofpcreport.rtf" />
    								<display:column  media="html" headerClass="GridHBg" class="GridLCells">
    								<input type="radio" name="chk" value="<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getCpfAcNo()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeName()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getEmployeeCode()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getRegion()%>:<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getAirportCode()%>"/>
    								</display:column>
    								<display:column  sortable="true" property="pensionnumber" headerClass="GridLTHCells" class="GridLTCells" title="PFID" />
    										<display:column   sortable="true" property="cpfAcNo"        headerClass="GridLTHCells" class="GridLTCells"       title="CPFAC.No" />
    								<display:column   sortable="true" style="white-space: nowrap"  property="employeeName"   headerClass="GridLTHCells" class="GridLTCells"       title="EmployeeName" />
    								<display:column   sortable="true" style="white-space: nowrap"  property="employeeCode"   headerClass="GridLTHCells" class="GridLTCells"       title="EmployeeNo"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column   sortable="true" style="white-space: nowrap"  property="desegnation"    headerClass="GridLTHCells" class="GridLTCells"       title="Designation"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								<display:column   sortable="true" style="white-space: nowrap"  property="dateofBirth"    headerClass="GridLTHCells" class="GridLTCells"       title="DateofBirth"  />	    									
									<display:column   sortable="true" style="white-space: nowrap"  property="dateofJoining"  headerClass="GridLTHCells" class="GridLTCells"       title="DateofJoining"  />	
									<display:column   sortable="true" style="white-space: nowrap"  property="airportCode"    headerClass="GridLTHCells" class="GridLTCells"       title="Airport Code"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column   sortable="true" style="white-space: nowrap"  property="region"         headerClass="GridLTHCells" class="GridLTCells"       title="Region"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column   sortable="true" style="white-space: nowrap" property="totalRecrods"   headerClass="GridLTHCells" class="GridLTCells"       title="No.of Months "  decorator="com.epis.utilities.StringFormatDecorator"/> 
									<display:column  media="html" sortable="true" style="white-space: nowrap" headerClass="GridLTHCells" class="GridLTCells"       title="PCReport Verified"  decorator="com.epis.utilities.StringFormatDecorator">
									<%
    									
    									
    									if(sizeofgridlenght<=autoIncrement){
    										autoIncrement=1;
    									}else{
    										autoIncrement=autoIncrement+1;
    									}
    								
    								%>
									<select name="pcreportverified<%=autoIncrement%>" class="TextField" onchange="pcreportverified(this.value,'<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>');"  style="width:80px">
										<% pcreportverified =((PensionBean)pageContext.getAttribute("financeDatalist")).getPcreportverified();
										 %>
												<option value="N" <%if(pcreportverified.equals("N")){ out.println("selected");}%>>
														No
												</option>
												<option value="Y" <%if(pcreportverified.equals("Y")){ out.println("selected");}%>>
														Yes
												</option>
												</select>
    				
    								</display:column> 
    								<display:column  media="html" sortable="true" style="white-space: nowrap" headerClass="GridLTHCells" class="GridLTMFCells" title="Form 6-7-8 Status"  decorator="com.epis.utilities.StringFormatDecorator">
									<select name="formsstatus<%=autoIncrement%>" class="TextField" onchange="updateStatus(this.value,'<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>');" style="width:80px">
										<% form6_7_8status =((PensionBean)pageContext.getAttribute("financeDatalist")).getFormsdisable();
										 %>
												<option value="N" <%if(form6_7_8status.equals("N")){ out.println("selected");}%>>
														No
												</option>
												<option value="Y" <%if(form6_7_8status.equals("Y")){ out.println("selected");}%>>
														Yes
												</option>
												</select>
    								
    						
    								</display:column>     
								<display:column  media="html" sortable="true" style="white-space: nowrap" headerClass="GridLTHCells" class="GridLTMFCells" title="PensionClaims Processes"  decorator="com.epis.utilities.StringFormatDecorator">
									<select name="formsstatus<%=autoIncrement%>" class="TextField" onchange="pensionClaims(this.value,'<%=((PensionBean)pageContext.getAttribute("financeDatalist")).getPensionnumber()%>');" style="width:80px">
										<% claimprocess =((PensionBean)pageContext.getAttribute("financeDatalist")).getClaimsprocess();
										 %>
												<option value="N" <%if(claimprocess.equals("N")){ out.println("selected");}%>>
														No
												</option>
												<option value="Y" <%if(claimprocess.equals("Y")){ out.println("selected");}%>>
														Yes
												</option>
												</select>
    								
    						
    								</display:column>    
									</display:table>
								</td>
							</tr>
							</logic:present>
             
			

		</table>
	

</form>
<%=ScreenUtilities.screenFooter()%>
</body>
</html>
