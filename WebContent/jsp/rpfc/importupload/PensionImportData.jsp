
<%@ page language="java" import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ page buffer="16kb"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] year = {"2008","2009","2010"};
String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
String region="",region1="",year1="",airportcode1="";


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
<head>

<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
<title>AAI</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<script type="text/javascript"> 
		function createXMLHttpRequest(){
			if(window.ActiveXObject){
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		 	}else if (window.XMLHttpRequest){
				xmlHttp = new XMLHttpRequest();
			 }
		}
		function getNodeValue(obj,tag)
		   {
			return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
		   }
		
		function getAirports()
	   { 
		var region=document.forms[0].select_region.value;
		createXMLHttpRequest();	
	    var url ="<%=basePath%>psearch.do?method=getFinanceTblAirports&region="+region;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getAirportsList;
		xmlHttp.send(null);
	  }

		 function getMonth(){
			 formType=document.forms[0].select_proforma_type.value;
			 if(formType=='AAIEPF-1'){
              document.forms[0].select_month.value='04';
			 }else{
				  document.forms[0].select_month.value='';
			 }
		 }

	function getAirportsList()
	 {
		if(xmlHttp.readyState ==4)
		{
		 if(xmlHttp.status == 200)
			{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
			  if(stype.length==0){
			 	var obj1 = document.getElementById("airPortCode");
			   	obj1.options.length=0; 
			   	obj1.options[obj1.options.length]=new Option('Select One','','true');
			  }else{
			   	var obj1 = document.getElementById("airPortCode");
			   	obj1.options.length = 0;
			  	for(i=0;i<stype.length;i++){
			  if(i==0){	obj1.options[obj1.options.length]=new Option('Select One','','true');
			}
		obj1.options[obj1.options.length] = new Option(getNodeValue(stype[i],'airPortName'),getNodeValue(stype[i],'airPortName'));
		  	}
			  
				  }
			}
		}
	}
	function fnReset(formName){
		    document.forms[0].action="<%=basePath%>importuploadaction.do?method=loadImportedProcess&frmName="+formName;
           	document.forms[0].method="post";
			document.forms[0].submit();
	}
	 function frmLoad()
	  {
	 
	  
	     <%			
				if (request.getAttribute("region") != null) {
					region1 =request.getAttribute("region").toString();
					region =request.getAttribute("region").toString();
					}
	            if (request.getAttribute("year") != null) {
				year1 =request.getAttribute("year").toString();;
				}
	           if (request.getAttribute("airportcode") != null) {
	        	   airportcode1 =request.getAttribute("airportcode").toString();;
					}
				%>
				var region1='<%=region%>';
				
				if(region1==""){
				region1="Select One" ;
				}
				var year1='<%=year1%>';
				if(year1==""){
				year1="Select One" ;
				}
			// document.forms[0].airPortCode[document.forms[0].airPortCode.options.selectedIndex].text="Select One";
			 document.forms[0].select_region[document.forms[0].select_region.options.selectedIndex].text=region1;
		   //  document.forms[0].select_year[document.forms[0].select_year.options.selectedIndex].text=year1;
					
	  } 
    function fnUpload(){
           
		 	var fileUploadVal="",region="",formType="",airPortCode="",year="";
		 	fileUploadVal=document.forms[0].uploadfile.value;
		 	formType=document.forms[0].select_proforma_type.value;
		 	region=document.forms[0].select_region[document.forms[0].select_region.options.selectedIndex].text;
		 	airPortCode=document.forms[0].airPortCode.value;
		 	
         	year=document.forms[0].select_year[document.forms[0].select_year.options.selectedIndex].text;
		 
		 	 if(formType!='OTHER'){
		 	if(document.forms[0].select_region[document.forms[0].select_region.options.selectedIndex].text=="Select One"){
		 		alert('Please Select The Region');
		 		document.forms[0].select_region.focus();
		 		return false;
		 	}
		 	if(airPortCode=="" && region=='CHQIAD'){
		 		alert('Please Select The Airport');
		 		document.forms[0].airPortCode.focus();
		 		return false;
		 	}
		 
			if(document.forms[0].select_year[document.forms[0].select_year.options.selectedIndex].text=="Select One"){
				alert("Please Select Year");
				document.forms[0].select_year.focus();
				return false;
			}
			var month=document.forms[0].select_month.value;
			if(month==""){
				alert("Please Select Month");
				document.forms[0].select_month.focus();
				return false;
			}
		 	if(fileUploadVal==''){
		 		alert('Select a Upload File');
		 		document.forms[0].uploadfile.focus();
		 		return false;
		 	}else{
		 		var iChars = "!=*,";
		    var fileNameWithDrvieNm=document.forms[0].uploadfile.value;
		   
	        fileNameWithDrvieNm=fileNameWithDrvieNm.substring(fileNameWithDrvieNm.lastIndexOf("\\")+1,fileNameWithDrvieNm.lastIndexOf("."));
	      
          	for (var i = 0; i < fileNameWithDrvieNm.length; i++) {
		  	if (iChars.indexOf(fileNameWithDrvieNm.charAt(i)) != -1){
			   	alert("File Name like "+formType+"_REGIONNAME.xls");
		  		return false;
		  		}
			 }	
       
         
			var checkfileinfo=fileNameWithDrvieNm.split("_");
			var chckproformatype="",chckregioname="",selectedRegion="",chckAirportCode="",selectedAirportCode="";
			chckproformatype=checkfileinfo[0];
			chckregioname=checkfileinfo[1];
			if(checkfileinfo.length==5){
			chckAirportCode=checkfileinfo[2];
			
			}else if(checkfileinfo.length==3){
			 chckAirportCode=checkfileinfo[2];
			}else{
				chckAirportCode="";
			}
			var chckYear="",chckMonth="";
			if(checkfileinfo.length==5){
				chckYear=checkfileinfo[4];
			
				}else if(checkfileinfo.length==4){
					chckYear=checkfileinfo[3];
				}else{
					chckYear="";
				}
		
			if(checkfileinfo.length==5){
				chckMonth=checkfileinfo[3];
				}else if(checkfileinfo.length==4){
					chckMonth=checkfileinfo[2];
				}else{
					chckMonth="";
				}
			selectedRegion=region.replace(" ","");
			   if(chckproformatype!=formType){
					alert("File Name  like  '"+formType+"_"+selectedRegion+"_"+month+"_"+year+"'");
					document.forms[0].select_proforma_type.focus();
		  		return false;
			   }
				
				if(chckregioname.toLowerCase()!=""){
					if(chckregioname.toLowerCase()!=selectedRegion.toLowerCase()){
						alert("File Name  like  '"+formType+"_"+selectedRegion+"_"+month+"_"+year+"'");
						document.forms[0].select_region.focus();
				  		return false;
					}
				}
				
				selectedAirportCode=airPortCode.replace(" ","");
				//alert("chckAirportCode "+chckAirportCode +"selectedAirportCode " +selectedAirportCode);
				if(chckAirportCode.toLowerCase()!=selectedAirportCode.toLowerCase()&& selectedAirportCode!="SelectOne"){
					alert("File Name Like '"+formType+"_"+selectedRegion+"_"+selectedAirportCode+"_"+month+"_"+year+"'");
					document.forms[0].airPortCode.focus();
			  		return false;
				}
				if(chckproformatype!=formType&&chckAirportCode.toLowerCase()!=selectedAirportCode.toLowerCase()&& selectedAirportCode!="SelectOne"){
					alert("File Name  like  '"+formType+"_"+selectedRegion+"_"+selectedAirportCode+"_"+month+"_"+year+"'");
					document.forms[0].select_proforma_type.focus();
		  		return false;
			   }
				
				if(chckYear.toLowerCase()!=year.toLowerCase()&& selectedAirportCode!="SelectOne"){
				    alert("File Name Like  '"+formType+"_"+selectedRegion+"_"+selectedAirportCode+"_"+month+"_"+year+"'");
					document.forms[0].select_year.focus();
			  		return false;
				}else if(chckYear.toLowerCase()!=year.toLowerCase()&& selectedAirportCode=="SelectOne"){
				    alert("File Name Like  '"+formType+"_"+selectedRegion+"_"+month+"_"+year+"'");
					document.forms[0].select_year.focus();
			  		return false;
				}
		 	}
		 	 }
		 	if(fileUploadVal==''){
		 		alert('Select a Upload File');
		 		document.forms[0].uploadfile.focus();
		 		return false;
		 	}

			
		    document.forms[0].action="<%=basePath%>importuploadaction.do?method=uploadProcess&frm_file="+fileUploadVal+"&frm_region="+region+"&frm_formtype="+formType+"&year="+year+"&month="+month+"&airPortCode="+airPortCode;
           	document.forms[0].method="post";
			document.forms[0].submit();
			
		 	}
   	
		 
   		 </script>
</head>
<%
    String monthID="",monthNM="";
    String formID="",formNM="";
  	Iterator regionIterator=null;
  	Iterator monthIterator=null;
  	HashMap hashmap=new HashMap();
  	Iterator formIterator=null;
  	String fileName="";
  	if(request.getAttribute("regionHashmap")!=null){
  	hashmap=(HashMap)request.getAttribute("regionHashmap");
  	Set keys = hashmap.keySet();
	regionIterator = keys.iterator();
  	}
  	if(request.getAttribute("monthIterator")!=null){
  	  	monthIterator=(Iterator)request.getAttribute("monthIterator");
  	  	}
	if(request.getAttribute("formsListMap")!=null){
  		formIterator=(Iterator)request.getAttribute("formsListMap");
  	  	}
  	
  	if(request.getAttribute("fileName")!=null){
  		fileName=request.getAttribute("fileName").toString();





  	  	}

  
  	%>
<body class="BodyBackground" onload="frmLoad()">
<html:form action="importuploadaction.do?method=loadImportedProcess&frmName=nonsupplementary" enctype="multipart/form-data" >

<%=ScreenUtilities.screenHeader(request.getAttribute("screenTitles").toString())%>



		<table align="center" width="100%" align="center" cellpadding="0"
			cellspacing="0" class="tbborder" >


					<tr>

						<td class="tableTextBold" align="right" width="35%">AAIEPF FORMAT:<font
							color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="65%" align="left"><select name="select_proforma_type" class="TextField" style="width: 210px"
							onchange="getMonth()">
							<%
				 while (formIterator.hasNext()) {
				 Map.Entry mapEntry = (Map.Entry) formIterator.next();
				 formID = mapEntry.getKey().toString();
				 formNM = mapEntry.getValue().toString();
				 if(formID.equalsIgnoreCase(fileName)) {
				 %>  
							<option value="<%=fileName%>" <% out.println("selected");%>>
							<%=formNM%></option>


							<%} else {%>
							<option value="<%=formID%>"><%=formNM%></option>
							<%}
             	%>
				
							<%}%>
						</select></td>
					</tr>
					<tr>
						<td class="tableTextBold" align="right">Region:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td align="left" ><select name="select_region" style="width: 130px" class="TextField" onchange="getAirports()">
							<option value="">Select One</option>
							<%int k = 0;
			boolean exist = false;
            while (regionIterator.hasNext()) {
			region = hashmap.get(regionIterator.next()).toString();
			k++;
				
				if (region.equalsIgnoreCase(region1)) {

					%>
							<option value="<%=region1%>" <% out.println("selected");%>>
							<%=region1%></option>


							<%} else {%>
							<option value="<%=region%>"><%=region%></option>
							<%}

			%>

							<%}	%>
						</select></td>
					</tr>
					<tr>
						<td class="tableTextBold" align="right">Airport
						Code:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td align="left" ><select name="airPortCode" class="TextField">
							<option value="">Select One</option>


						</select></td>
					</tr>
					<tr>
						<td class="tableTextBold" align="right">Year:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td align="left"><select name="select_year" class="TextField" style="width: 100px">
							<option value="N0-SELECT">Select One</option>
                <%for (int j = 0; j < year.length; j++)  {
			     String year2 = year[j].toString();
			    	if (year2.equalsIgnoreCase(year1)) {
				%>   
							<option value="<%=year1%>" <% out.println("selected");%>>
							<%=year1%></option>


							<%} else {%>
							<option value="<%=year2%>"><%=year2%></option>
							<%}

			%>

							<%}	%>
							
						</select></td>
					</tr>
					<tr>
						<td class="tableTextBold" align="right">Salary Month:<font
							color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td align="left"><select name="select_month" class="TextField" style="width: 100px">
							<option value="N0-SELECT">Select One</option>
							<%while (monthIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) monthIterator.next();
				monthID = mapEntry.getKey().toString();
				monthNM = mapEntry.getValue().toString();

				%>
				<option value="<%=monthID%>"><%=monthNM%></option>
							<%}%>
						</select></td>
					</tr>


					<tr>
						<td class="tableTextBold" align="right">File to Upload:<font
							color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td align="left"><html:file property="uploadfile"/></td>
					</tr>
					<tr>
						
						<td colspan="2">
						<input type="button" class="butt" name="Submit"	value="Upload"	onclick="javascript:fnUpload()"/>
						<input type="button" class="butt" name="Submit"	value="Reset"	onclick="javascript:fnReset('<%=request.getAttribute("frmName")%>')"/>
						<input type="button" class="butt" name="Submit" value="Cancel" onclick="javascript:history.back(-1)"/></td>
					</tr>
				
			<tr>
				<td>&nbsp;</td>
			</tr>
			<input type="hidden" name="frmName" value="<%=request.getAttribute("frmName")%>">
			<logic:messagesPresent message="true">
			
			<tr>
				<td colspan="2">
				<fieldset><legend class="epis" >Information</legend>
					<table  width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left" colspan="2" ><font color="red">
										<html:messages id="message" message="true"  bundle="rpfccommon">
											<bean:write name="message" />
											<br />
										</html:messages>&nbsp;</td>
						</tr>
					</table>
						</fieldset >
				 </td>
				
				
			</tr>
		
		  </logic:messagesPresent>
		



	
</table>

<%=ScreenUtilities.screenFooter()%>
</html:form>

</body>
</html>
