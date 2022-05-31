
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.BottomGridNavigationInfo" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.SearchInfo" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>AAI</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
   	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
   	<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
    <SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime1.js"></SCRIPT>
	<script type="text/javascript"> 
    var monthtext=['--','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec'];
     var swidth=screen.Width-10;
	var sheight=screen.Height-150;
    
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
		regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		createXMLHttpRequest();	
		var url ="<%=basePath%>psearch.do?method=getFinanceTblAirports&region="+regionID;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getAirportsList;
		xmlHttp.send(null);
    }
	function getAirportsList(){
		if(xmlHttp.readyState ==3 ||  xmlHttp.readyState ==2 ||  xmlHttp.readyState ==1){
		 	process.style.display="block";
		}
		if(xmlHttp.readyState ==4){
			if(xmlHttp.status == 200){ 
				var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
				process.style.display="none";
				if(stype.length==0){
				 	var obj1 = document.getElementById("select_airport");
				  	obj1.options.length=0; 
		  			obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
				}else{
		 		   	var obj1 = document.getElementById("select_airport");
		 		  	obj1.options.length = 0;
				 	for(i=0;i<stype.length;i++){
		  				if(i==0){
							obj1.options[obj1.options.length]=new Option('[Select One]','NO-SELECT','true');
						}
						obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
					}
		  		}
			}
		}
	}
	function form9Report(pensionNo,obj,employeeName,employeeCode,region,airportCode,dateOfBirth,index,totalData){
			
			var flag="true";
			
			var url="<%=basePath%>personalForm.do?method=frmForm9Report&cpfacno="+obj+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&startIndex="+index+"&totalData="+totalData+"&frm_PensionNo="+pensionNo+"&frm_dateOfBirth="+dateOfBirth;
		
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		}
		
	function frmload(){
 		process.style.display="none";
 		populatedropdown("daydropdown", "monthdropdown", "yeardropdown");
	}
	 function Search(){
		process.style.display="none";
   		 var empNameCheak="",airportID="",sortColumn="EMPLOYEENAME",day="",month="",year="";
	
		
   		var dob=document.forms[0].dob.value;
		var doj=document.forms[0].doj.value;
		 if(!document.forms[0].dob.value==""){
	   		    var date1=document.forms[0].dob;
	   		   var val1=convert_date(date1);
	   	   
	   		    if(val1==false)
	   		     {
	   		      return false;
	   		     }
	   		    }
		 if(!document.forms[0].doj.value==""){
	   		    var date1=document.forms[0].doj;
	   	        var val1=convert_date(date1);
	   		    if(val1==false)
	   		     {
	   		      return false;
	   		     }
	   		    }
		 var employeeNo=document.forms[0].employeeCode.value;
		 var cpfaccno=document.forms[0].cpfaccno.value;
		// alert("emp no "+employeeNo +"cpfaccno "+cpfaccno +"dob "+dob+"doj"+doj);
   		var regionID;
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
			
		}
		if(document.forms[0].select_airport.selectedIndex>0){
			airportID=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].text;
		}else{
			airportID=document.forms[0].select_airport.value;
			
		}
		document.forms[0].action="<%=basePath%>personalForm.do?method=searchPersonal&region="+regionID+"&airPortCode="+airportID+"&frm_sortingColumn="+sortColumn+"&frm_dateOfBirth="+dob+"&doj="+doj+"&cpfaccno="+cpfaccno+"&employeeNo="+employeeNo;
	  	document.forms[0].method="post";
		document.forms[0].submit();
	}

	
		function callReport(){
   		var reportID="",sortColumn="EMPLOYEENAME";
   	 	reportID=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
   	 	var url="<%=basePath%>psearch?method=personalEmpReport&reportType="+reportID+"&frm_sortcolumn="+sortColumn;
   	 	wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 }

     function editPensionMaster(pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData){

					var flag="true";
					document.forms[0].action="<%=basePath%>personalForm.do?method=personalEdit&cpfacno="+obj+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&startIndex="+index+"&totalData="+totalData+"&pfid="+pfid;
					document.forms[0].method="post";
					document.forms[0].submit();
			
		}
     function viewPersonalDetails(pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData){

			var flag="true";
			var view="true";
			var url="<%=basePath%>personalForm.do?method=personalEdit&cpfacno="+obj+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&startIndex="+index+"&totalData="+totalData+"&pfid="+pfid+"&view="+view;
			
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	    }
     
		function resetMaster(){
     				document.forms[0].action="<%=basePath%>personalForm.do?method=loadPerMstr";
					document.forms[0].method="post";
					document.forms[0].submit();
			
		}
		function form2Report(pensionNo,obj,employeeName,employeeCode,region,airportCode,dateOfBirth,index,totalData){
			var flag="true";
			
			var url="<%=basePath%>personalForm.do?method=frmForm2Report&cpfacno="+obj+"&name="+employeeName+"&flag="+flag+"&empCode="+employeeCode+"&region="+region+"&airportCode="+airportCode+"&startIndex="+index+"&totalData="+totalData+"&frm_PensionNo="+pensionNo+"&frm_dateOfBirth="+dateOfBirth;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
		}
		
		function selectCheckboxes(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData,dateOfBirth;
		   if(frmType=='New'){
		   		document.forms[0].action="<%=basePath%>personalForm.do?method=loadAdd";
				document.forms[0].method="post";
				document.forms[0].submit();
		   }else{
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
			        	editPensionMaster(pfid,obj,employeeName,employeeCode,region,airportCode,index,totalData)
			        }else if(frmType=='Form-2'){
			        	form2Report(pfid      	
			        	,obj,employeeName,employeeCode,region,airportCode,dateOfBirth,index,totalData)
			        }else if(frmType=='Form-9'){
			        	form9Report(pfid      	
			        	,obj,employeeName,employeeCode,region,airportCode,dateOfBirth,index,totalData)
			        }
		   }
		  
		   	
		}
		function populatedropdown(dayfield, monthfield, yearfield){
		var today=new Date();
		var dayfield=document.getElementById(dayfield);
		var monthfield=document.getElementById(monthfield);
		var yearfield=document.getElementById(yearfield);
		for (var i=0; i<=31; i++){
		if(i==0){
		dayfield.options[i]=new Option("--","--")
		}else{
			dayfield.options[i]=new Option(i, i+1)
		}
		
		}
	
		for (var m=0; m<=12; m++){
		monthfield.options[m]=new Option(monthtext[m], monthtext[m]);
		}
	
		//select today's month
		var thisyear=today.getFullYear();
		var beginyear=1939;
		var size=thisyear-beginyear;
		for (var y=0; y<=size; y++){
		yearfield.options[y]=new Option(beginyear, beginyear);
		beginyear+=1
		}
		yearfield.options[0]=new Option("--","--", true, true); //select today's year
		}
		window.onload=function(){
			//populatedropdown("daydropdown", "monthdropdown", "yeardropdown");
		}
		

    </script>
  </head>
  <%
  	HashMap hashmap=new HashMap();
  	String region="";
  	Iterator regionIterator=null;
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	}
  %>
  <body onload="javascript:frmload()">
   <form name="personalMaster" action="" method="post">
   	<%=ScreenUtilities.saearchHeader("PF ID[Search]")%>
	 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

		<tr>
			<td height="15%">
				<table align="center">
					<tr>
						<td class="tableTextBold">Region:</td>
						<td>
							<SELECT NAME="select_region" onChange="javascript:getAirports()"  style="width:130px" class="TextField">
							<option value="NO-SELECT">[Select One]</option>
								<%
						     	  while(regionIterator.hasNext()){
							 	  region=hashmap.get(regionIterator.next()).toString();
							 	%>
							  	<option value="<%=region%>" ><%=region%></option>
	                       		<%}%>
							</SELECT>
						</td>
						<td class="tableTextBold">Airport Code:</td>
						<td>
							<select name="select_airport" class="TextField">
								<option value="NO-SELECT">[Select One]</option>
						    </select>
						</td>
					</tr>
					<tr>
						<td class="tableTextBold">Employee Name:</td>
						<td><input type="text" name="empName" onkeyup="return limitlength(this, 20)" class="TextField"></td>
						<td class="tableTextBold">PF ID:</td>
						<td><input type="text" name="pensionNO" onkeyup="return limitlength(this, 20)" class="TextField"></td>
						
					</tr>
                    <tr>	<td class="tableTextBold">
									Employee Code:
								</td>
								<td>
									<input type="text" name="employeeCode" onkeyup="return limitlength(this, 20)" class="TextField">
								</td>
								<td class="tableTextBold">
									Old CpfAccno:
								</td>
								<td>
									<input type="text" name="cpfaccno" class="TextField">
								</td>
							</tr>
					<tr>
					 <td class="tableTextBold">Date Of Birth:</td>
						<!--<td><select id="daydropdown" name="dayField" style="width:50px"></select><select id="monthdropdown" name="monthField" style="width:50px"></select><select id="yeardropdown" name="yearField" style="width:50px"></select> </td> -->
                    
					<td><input type="text" name="dob" tabindex="9"  onkeyup="return limitlength(this, 20)">
										<a href="javascript:show_calendar('forms[0].dob');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
										</td>

                   <td class="tableTextBold">
									Date of Joining:</td>
				<td>
									
								    <input type="text" name="doj" tabindex="9"  onkeyup="return limitlength(this, 20)" class="TextField">
										<a href="javascript:show_calendar('forms[0].doj');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
				</td>

</tr>
					<tr>
						<td align="left">&nbsp;</td>
						<td>
								<input type="button" class="btn" value="Search" class="btn" onclick="Search();">
								<input type="button" class="btn" value="Reset" onclick="javascript:resetMaster()" class="btn">
								<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
						</td>

					</tr>
			</table>
		</td>
	</tr>
</table>

		<%=ScreenUtilities.searchSeperator()%>		
		
				
		<%	EmployeePersonalInfo dbBeans = new EmployeePersonalInfo();
					SearchInfo getSearchInfo = new SearchInfo();
					int totalData = 0,index = 0;
					if (request.getAttribute("searchBean") != null) {
					SearchInfo searchBean = new SearchInfo();
					ArrayList dataList = new ArrayList();
					BottomGridNavigationInfo bottomGrid = new BottomGridNavigationInfo();
					searchBean = (SearchInfo) request.getAttribute("searchBean");
					dbBeans = (EmployeePersonalInfo) request.getAttribute("searchInfo");
					index = searchBean.getStartIndex();
				//	out.println("index "+index);
					session.setAttribute("getSearchBean1", dbBeans);
					dataList = searchBean.getSearchList();
					totalData = searchBean.getTotalRecords();
					bottomGrid = searchBean.getBottomGrid();
				

				%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
            <tr>
                        <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,5)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>
                       
					     <logic:present name="SearchInfoList" >
							<tr>							
								<td align="center" width="100%">
								
									<display:table cellspacing="0" cellpadding="0" export="true" class="GridBorder" sort="list" id="SearchInfoList" style="width:100%;height:50px" pagesize="10" name="requestScope.SearchInfoList" requestURI="./personalForm.do?method=searchPersonal" >											
		    						 
									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="form10D.xls" />
    								<display:setProperty name="export.pdf.filename" value="form10D.pdf" />
    								<display:setProperty name="export.rtf.filename" value="form10D.rtf" />
    								<display:column  media="html"  headerClass="GridHBg" class="GridLCells">
    								<input type="radio" name="chk" value="<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getOldPensionNo()%>:<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getCpfAccno()%>:<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getEmployeeName()%>:<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getEmployeeNumber()%>:<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getRegion()%>:<%=((EmployeePersonalInfo)pageContext.getAttribute("SearchInfoList")).getAirportCode()%>"/>
    								</display:column>
    								<display:column    property="pfID"          sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="PF ID" />
									<display:column    property="cpfAccno"      sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Old CPFACC.No" />
    								<display:column    property="employeeNumber"sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Employee Code" />
    								<display:column    property="employeeName"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Employee Name"  decorator="com.epis.utilities.StringFormatDecorator"/>    
    								<display:column    property="designation"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Designation"  decorator="com.epis.utilities.StringFormatDecorator"/>			
    								<display:column    property="dateOfBirth"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="D.O.B"  decorator="com.epis.utilities.StringFormatDecorator"/>	    									
									<display:column    property="wetherOption"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Pension Option"  decorator="com.epis.utilities.StringFormatDecorator"/>	
									<display:column    property="airportCode"   sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Airport Code"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									<display:column    property="region"        sortable="true"  headerClass="GridLTHCells" class="GridLTCells"       title="Region"  decorator="com.epis.utilities.StringFormatDecorator"/>    
									</display:table>
								</td>
							</tr>
							</logic:present>
						
		</table>
				<%}%>

			
				<%=ScreenUtilities.screenFooter()%>
		</form>
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>
  </body>
</html>
