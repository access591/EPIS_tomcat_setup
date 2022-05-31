<%@ page import="java.util.*,com.epis.utilities.*"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>


<%@ page import="com.epis.bean.rpfc.*"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String arrearInfoString1 = "";
            String regionFlag = "false";
            System.out.println("the path is:"+path);
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
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/calendar.js"></SCRIPT>

		<SCRIPT type="text/javascript">
		<%
			Integer nomineeSize=(Integer)request.getAttribute("size");
			int nomineeCount=nomineeSize.intValue();
		%>
		
	/*function deleteFamilyDetils(cpfaccno,fmemberName,rowcolumn,rowid)
{  
  createXMLHttpRequest();	
   var rowcolumn=rowcolumn;
   var deleterow = document.getElementById(rowcolumn);
    document.getElementById(rowcolumn).style.display = 'none'; 
    document.getElementById(rowcolumn).innerText = "";
	var fname=fmemberName;
	var cpfaccno=cpfaccno;
	var region=document.forms[0].region.value;
	var url ="<%=basePath%>search1?method=deleteFamilyDetails&fmemberName="+fname+"&cpfaccno="+cpfaccno+"&region="+region+"&rowid="+rowid;
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = deleteFamilyDetails1;
	xmlHttp.send(null);
 }
 function deleteNomineeDetils(cpfaccno,nomineeName,rowcolumn,rowid)
  {
  createXMLHttpRequest();	
  var rowcolumn=rowcolumn;
  var deleterow = document.getElementById(rowcolumn);
    document.getElementById(rowcolumn).style.display = 'none'; 
    document.getElementById(rowcolumn).innerText = "";
	var nomineeName=nomineeName;
	var cpfaccno=cpfaccno;
	var region=document.forms[0].region.value;
	var url ="<%=basePath%>search1?method=deleteNomineeDetils&nomineeName="+nomineeName+"&cpfaccno="+cpfaccno+"&region="+region+"&rowid="+rowid;
	xmlHttp.open("post", url, true);
	xmlHttp.onreadystatechange = deleteNomineeDetils1;
	xmlHttp.send(null);
}
 function deleteNomineeDetils1()
 {	if(xmlHttp.readyState ==4)
	{  	if(xmlHttp.status == 200)
		{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		  if(stype.length==0){
		 	var obj1 = document.getElementById("deletestatus");
		 	obj1.options.length=0; 
		   }else{
		   var deletestatus = getNodeValue(stype[0],'deletestatus');
		   var fmemberName=getNodeValue(stype[0],'nomineeName');
		   alert(fmemberName +" "+deletestatus);
		   }
		}
	}
}*/

/*function deleteFamilyDetails1()
{   
	if(xmlHttp.readyState ==4)
	{  
		if(xmlHttp.status == 200)
		{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		
		  if(stype.length==0){
		 	var obj1 = document.getElementById("deletestatus");
		 	obj1.options.length=0; 
		  
		  }else{
		   var deletestatus = getNodeValue(stype[0],'deletestatus');
		   var fmemberName=getNodeValue(stype[0],'fmemberName');
		   alert(fmemberName +" "+deletestatus);
		 	
		   
		  }
		}
	}
}*/
		
		 function numsDotOnly()
	         {

	            if(((event.keyCode>=48)&&(event.keyCode<=57))||(event.keyCode==46))
	            {
	               event.keyCode=event.keyCode;
	            }

	            else
	            {
					
	              event.keyCode=0;

                }

	       }
	function dispOff()
   { 
     var totalshare=document.getElementsByName("totalshare");
      var nomineeName=document.getElementsByName("Nname");
   		
   		  var k;
   		  var finalShare=0;
   	
	if(window.document.forms[0].equalShare.checked)
	  {   
	  if(totalshare.length==1){
	  var totalShare =100/((totalshare.length));
		// alert("totalShare "+totalShare);
		  totalshare[0].value=totalShare;
		  var finalShare=finalShare+parseInt(totalshare[0].value);
		 
		 // alert(finalShare);
		   totalshare[0].readOnly=true;
	  }
   		 for(k=0;k<(totalshare.length)-1;k++){
   		 var totalShare =100/((totalshare.length)-1);
		// alert("totalShare "+totalShare);
		  totalshare[k].value=totalShare;
		  var finalShare=finalShare+parseInt(totalshare[k].value);
		 
		 // alert(finalShare);
		   totalshare[k].readOnly=true;
		//document.getElementsByName("totalshare").value=totalShare;
	}
	}
	else
	{
	for(k=0;k<(totalshare.length)-1;k++){
	
	//alert("inside else ");
	//window.document.forms[0].totalshare.disabled=false;
	 totalshare[k].value=="";
	 var l=document.getElementsByName("totalshare");
	 for(i=0;i<l.length;i++){
  	 l[i].value="";
  	  totalshare[k].readOnly=false;
  	 }
	
	}
	}
  }


	var xmlHttp;

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
	
	function getNodeValue(obj,tag)
   {
	return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
   }
	

		 
	
	 
	
	
	  
	
	 function call_calender(dobValue){
	  	  show_calendar(dobValue);
  	  
  	 }
     var count1 =0;
  	 function callNominee(){
  	  count1++;
  	 divNominee2.innerHTML+=divNominee1.innerHTML;
  	 var i=document.getElementsByName("Nname");
  	 i[i.length-1].value="";
  	  var j=document.getElementsByName("Naddress");
  	 j[j.length-1].value="";
  	 
  	 
  	 var k=document.getElementsByName("Ndob");
  	  k[k.length-1].value="";
  	  k[k.length-1].id ="Ndob"+count1;
  	  k = document.getElementsByName("cal1");
  	  k[k.length-1].onclick=function(){call_calender("forms[0].Ndob"+count1)};
  	//  alert(k[k.length-1].onclick);
   	 var l=document.getElementsByName("Nrelation");
  	 l[l.length-1].value="";
  	  var m=document.getElementsByName("guardianname");
  	 m[m.length-1].value="";
  	  var n=document.getElementsByName("totalshare");
  	 n[n.length-1].value="";
  	 }
	function charsCapsNumOnly()
	{
	
	if(event.keyCode >=97 || event.keyCode<=122)    
      event.keyCode = event.keyCode - 32;
    if(((event.keyCode >=65 && event.keyCode<=90))||(event.keyCode==32)||
                                (event.keyCode==46)||(event.keyCode==44))  
      event.keyCode = event.keyCode;
    else
     event.keyCode=0;
	}
	
	 function testSS(){
   		
   		   
  	     
   		 var ndob1=document.getElementsByName("Ndob");
   		 var nname=document.getElementsByName("Nname");
   		 var gaddress =document.getElementsByName("gaddress");
   		 var guardianname= document.getElementsByName("guardianname");
   		 var totalshare1=document.getElementsByName("totalshare");
   		 var k;
   		 
   		  for(k=0;k<ndob1.length;k++){
   		   if(!ndob1[k].value==""){
   		  	 var val1=convert_date(ndob1[k]);
   		     if(val1==false)
   		     {
   		     return false;
   		     }
   		  			if(ndob1[k].value!=""){
   		  				var s=ndob1[k].value; 
		     			var month = getJsDate1(s.toLowerCase(),false);
			   		
   		   				var birthmotnth=month.substring(0,2);
					    var birthday=month.substring(3,5);
				 	    var birthyear=month.substring(month.lastIndexOf(",")+1,month.length);
   					    var birthDate = new Date(birthyear, birthmotnth, birthday); 
				   	
				      var currDate = new Date();
			   		  var monthnumber = currDate.getMonth();
			 		  var monthday    = currDate.getDate();
			          var year        = currDate.getYear();
			    	  var currentDate = new Date(year, monthnumber, monthday); 
				    
		         
        			 var daysDiff=(currentDate-birthDate)/86400000;
			  		 var years=Math.round(daysDiff/365);
			  		 if(nname[k].value=="") {
          				  nname[k].focus();
            			 alert("Please Enter Nominee Name");
             			 return false;
          	 		}
          	 	//	alert("guardianname length "+guardianname[k].length)
					if(years<18 && guardianname[k].value=="") {
          				  guardianname[k].focus();
            			  alert("Please Enter Guardian Name");
             			 return false;
          	 		}
          	 		
          	 		if(years<18 && gaddress[k].value=="") {
          				  gaddress[k].focus();
            			  alert("Please Enter Guardian Address");
             			 return false;
          	 		}
         }
          }
          }
      var nomineeName=document.getElementsByName("Nname");      
   	  var totalshare=document.getElementsByName("totalshare");
   	   for(l=0;l<totalshare.length;l++){
   	    if(totalshare[l].value!=""){
			if(nname[l].value==""){
   		     		 alert("Please Enter Nominee Name");
   		     		 nname[l].focus();
   		      		 return false;
   		           }
		  var finalShare=finalShare+parseInt(totalshare[l].value);
		   if(finalShare>100){
          	  totalshare[0].focus();
              alert("Total share shoud't Exceed 100");
              return false;
		   }
		   }
   	   
   	   }
   	  var l;
   	  var finalShare=0;
	if(!window.document.forms[0].equalShare.checked && totalshare[0].value!=="")
	  {    
   		 for(l=0;l<(totalshare.length)-1;l++){
   		 if(nomineeName[l].value==""){
   		 alert("Please Enter Nominee Name");
   		 nomineeName[l].focus();
   		 return false;
   		 }
		  if(totalshare[l].value!=""){
		// alert("share is"+totalshare[l].value);
		  var finalShare=finalShare+parseInt(totalshare[l].value);
		   if(finalShare>100){
		   {  totalshare[0].focus();
              alert("Total share shoud't Exceed 100");
              return false;
          	 }
          	 
		   }
		   }
	    }
		}  
		
		if(window.document.forms[0].equalShare.checked)
	  {    
   		 for(l=0;l<(totalshare.length)-1;l++){
   		 if(nomineeName[l].value==""){
   		 alert("Please Enter Nominee Name");
   		 nomineeName[l].focus();
   		 return false;
   		 }
		 if(totalshare[l].value!=""){
		 var finalShare=finalShare+parseInt(totalshare[l].value);
		   if(finalShare>100){
		   {
          	  totalshare[0].focus();
              alert("Total share shoud't Exceed 100");
              return false;
          	 }
          	 
		   }
		   }
		   }
	}       
	   	document.forms[0].action="<%=basePath%>nomineesearchservlet.do?method=pensionUpdate";
     
		document.forms[0].method="post";
		document.forms[0].submit();
   		 }
   		 
   	 function getPensionNumber() {
	var date1=document.forms[0].dateofbirth;
   	var val1=convert_date(date1);
   	var empName =document.forms[0].empName.value;
	var cpfacno =document.forms[0].cpfacnoNew.value;
	var uniquenumber = cpfacno.substring(0, cpfacno.length);
	var formEmpName=document.forms[0].empName.value;
	var newFormEmpName="";
	var finalKey="";
	var isNotSpace=false;
	var dateofbirth =document.forms[0].dateofbirth.value;
	var withoutSlash=replaceAll(dateofbirth,"/","***");
	var withoutDt_array=withoutSlash.split("***");
	var day=withoutDt_array[0];
	var month=convert_month(withoutDt_array[1]);
	var year=withoutDt_array[2].substring(2,4);
	
	var finalDay=day+month+year;
	//alert("finaday "+finalDay);
	
	if(formEmpName.indexOf(" ")!=-1){
		newFormEmpName=replaceAll(formEmpName," ","***");
	}else if(formEmpName.indexOf(".")!=-1){
		newFormEmpName=replaceAll(formEmpName,".","***");
	}else{
		isNotSpace=true;
	}


	if(isNotSpace==true){
		finalKey=formEmpName.substring(0,2);
	}else{
    //alert('new Format'+newFormEmpName);
	var mytool_array=newFormEmpName.split("***");
	//alert(mytool_array);
	
	
	finalKey=mytool_array[0].substring(0,1);
	//alert(mytool_array.length);
	finalKey=finalKey+mytool_array[mytool_array.length-1].substring(0,1);
	}
	var finalName=finalDay+finalKey+uniquenumber;
   	document.forms[0].pensionNumber.value=finalName;
	}
function replaceAll(OldString, FindString, ReplaceString) {
  var SearchIndex = 0;
  var NewString = ""; 
  while (OldString.indexOf(FindString,SearchIndex) != -1)    {
    NewString += OldString.substring(SearchIndex,OldString.indexOf(FindString,SearchIndex));
    NewString += ReplaceString;
    SearchIndex = (OldString.indexOf(FindString,SearchIndex) + FindString.length);         
  }
  NewString += OldString.substring(SearchIndex,OldString.length);
  return NewString;
}

 function getJsDate1(dateStr, objFlag)
			{   var dateArr = dateStr.split("/");
				var jsDate = "";
				if (!isNaN(dateArr[1].charAt(0))){
					jsDate = dateArr[1] + " " + dateArr[0] + ", " + dateArr[2];
				}
				else
				{  var monthArr = [];
					monthArr["jan"] = "01";
					monthArr["feb"] = "02";
					monthArr["mar"] = "03";
					monthArr["apr"] = "04";
					monthArr["may"] = "05";
					monthArr["jun"] = "06";
					monthArr["jul"] = "07";
					monthArr["aug"] = "08";
					monthArr["sep"] = "09";
					monthArr["oct"] = "10";
					monthArr["nov"] = "11";
					monthArr["dec"] = "12";
					monthNum = monthArr[dateArr[1]];	
					jsDate = monthNum + " " + dateArr[0] + ", " + dateArr[2];
			
				}
				if (objFlag == true)
					return new Date(Date.parse(jsDate));
				else
					return jsDate;
			}
   		   		
   		 
		 function deleteFamilyDetails(familyMemberName,rowid)
		{	createXMLHttpRequest();	
			var url ="<%=basePath%>search1?method=deleteFamilyDetails&familyMemberName="+familyMemberName+"&rowid="+rowid;
			alert("URL "+url);
			xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = getDesegnationName;
			xmlHttp.send(null);
		}

		function getDesegnationName()
		{
			if(xmlHttp.readyState ==4)
			{ //alert("in readystate"+xmlHttp.responseText);
				if(xmlHttp.status == 200)
				{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
				
				  if(stype.length==0){
				 	var obj1 = document.getElementById("designation");
				 	obj1.options.length=0; 
				  
				  }else{
				   var designation = getNodeValue(stype[0],'designation');
			       document.forms[0].desegnation.value=designation
				  }
				}
			}
		}
		
	 function getAirports(){
		var regionID;
		regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		createXMLHttpRequest();	
		var url ="<%=basePath%>reportservlet?method=getAirports&region="+regionID;
		xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getAirportsList;
		xmlHttp.send(null);
    }
    function getAirportsList(){
		if(xmlHttp.readyState ==4){
			if(xmlHttp.status == 200){ 
				var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
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
	function editingNominee(i)
	{
	    
	    document.getElementById('Nname'+i).readOnly=false;
		document.getElementById('Naddress'+i).readOnly=false;
		document.getElementById('Ndob'+i).readOnly=false;
		document.getElementById('relationselect'+i).style.display = 'inline'; 
		document.getElementById('relationtext'+i).style.display = 'none'; 
		document.getElementById('guardianname'+i).readOnly=false;
		document.getElementById('gaddress'+i).readOnly=false;
		document.getElementById('totalshare'+i).readOnly=false;
		document.getElementById('editStatus'+i).value="Y";
		document.getElementById('Nname'+i).focus();
		
	}
	function disableNominee(count)
	{
		
		if(count !=0)
		{
		  
		for(var i=0; i<count; i++)
		{
			document.getElementById('relationselect'+i).style.display = 'none'; 
			document.getElementById('relationtext'+i).style.display = 'block'; 
		}
		}
		
	}
  </script>
	</head>

	<body class="BodyBackground"  onload="disableNominee('<%=nomineeCount%>');">
	
		<form method="post" action="#">
		<%=ScreenUtilities.screenHeader("Request For Nominee")%>
			<table width="100%" border=0 align="center" >
				<tbody >
				<tr>
					<td colspan=4 align=center>&nbsp;
					</td>
				</tr>
			
				
				<%
				EmpMasterBean bean1=null;
				if (request.getAttribute("EditBean") != null) {
                 bean1 = (EmpMasterBean) request.getAttribute("EditBean");
                System.out.println("the pfid is:"+bean1.getPfid());
                }
                 %>
                <tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 PF ID:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<input type="hidden" name="empSerialNo" value='<%=bean1.getEmpSerialNo()%>'>
				<input type="hidden" name="cpfNo" value='<%=bean1.getNewCpfAcNo()%>'>
				<input type="hidden" name="region" value='<%=bean1.getRegion()%>'>
				<%=bean1.getPfid()%>--<%=bean1.getEmpSerialNo()%>      
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Old CPFACC.NO :
				</td>
				<td width="20%" class="tableText" nowrap align=left>
					<%=bean1.getCpfAcNo()%>        
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Employee Code:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getEmpNumber()%>      
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Employee Name :
				</td><td width="20%" class="tableText" nowrap align=left>
					<%=bean1.getEmpName()%>
                </td>			
			</tr>
			
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Father's / Husband's Name:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getFhName()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Sex :
				</td><td width="20%" class="tableText" nowrap align=left>
					<%
					String sex = bean1.getSex().trim();
					if(sex.equals("M"))
					out.println("Male");
					else
					out.println("Female");
					%>
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Date of Birth:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDateofBirth()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Date of Joining :
				</td><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDateofJoining()%>  
					
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Employee Level:
				</td>
				<td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getEmpLevel().trim()%>     
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					Designation :
				</td ><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDesegnation()%>  
					
                </td>			
			</tr>
			
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 Discipline:
				</td>
				<td width="20%" nowrap align=left align=left>
				<%=bean1.getDepartment().trim()%>     
				</td>	
				<td  width="20%" class="tableTextBold"  align=right nowrap>
					Division :
				</td align=left><td width="20%" class="tableText" nowrap align=left>
				<%=bean1.getDivision().trim()%>  
					
                </td>			
			</tr>
			<tr>
			<td colspan=4>&nbsp;</td>
			</tr>
			<tr>
			<td colspan=4 align=center>
										<div id="divNomineeHead">
										<table border=0 align="center" width="100%" >
											<tr>
												<td  class="tableTextBold" nowrap>
													Nomination for PF
												</td>

											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													&nbsp;&nbsp;Equal share check:&nbsp;
												</td>
												<%String equalSharecheck = bean1.getEmpNomineeSharable();%>
												<%if (bean1.getEmpNomineeSharable().equals("true")) {%>
												<td>
													<input type="checkbox" name="equalShare" tabindex="30" value='<%=("true".equals(equalSharecheck)?"true":"")%>' onClick='dispOff()' <%=(("true".equals(equalSharecheck))?"checked":"")%>>
												</td>
												<%} else {%>
												<td>
													<input type="checkbox" name="equalShare" value="true" onClick='dispOff()'>
												</td>
												<%}%>
											</tr>

											<tr>
												<td class="tableTextBold">
													Name&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Dateof Birth&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Relation with<br> Member&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Name of <br>Guardian&nbsp;&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Address of <br>Guardian&nbsp;&nbsp;&nbsp;
												</td>
												<td class="tableTextBold" nowrap>
													Total Share
													<BR>
													payable in %
												</td>
												<td>
													&nbsp;<b><img alt="Add New Record" src="<%=basePath%>images/action/addIcon.gif" onclick="callNominee();dispOff()" tabindex="38"></b>
												</td>


											</tr>
											</div>
											<%
				int count1 = 0;
                String nomineeRows = bean1.getNomineeRow();

                CommonUtil commonUtil = new CommonUtil();
                ArrayList nomineeList = commonUtil.getTheList(nomineeRows,
                        "***");
                System.out.println("the size is"+nomineeList.size());

                String tempInfo[] = null;
                String tempData = "";
                String nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", addressofGuardian = "",srno="",rowid="";
                for (int i = 0; i < nomineeList.size(); i++) {
                    count1++;
                    tempData = nomineeList.get(i).toString();
                    tempInfo = tempData.split("@");
                    String nomineeName = tempInfo[0];
                    nomineeAddress = tempInfo[1];
                    nomineeDob = tempInfo[2];
                    nomineeRelation = tempInfo[3];
                    nameofGuardian = tempInfo[4];
                    addressofGuardian = tempInfo[5];
                    totalShare = tempInfo[6];
                    srno=tempInfo[7];
                    System.out.println("the serial no is:"+srno);
                    if (nomineeAddress.equals("xxx")) {
                        nomineeAddress = "";
                    }

                    if (nomineeDob.equals("xxx")) {
                        nomineeDob = "";
                    }
                    if (nomineeRelation.equals("xxx")) {
                        nomineeRelation = "";
                    }

                    if (nameofGuardian.equals("xxx")) {
                        nameofGuardian = "";
                    }
                    if (addressofGuardian.equals("xxx")) {
                        addressofGuardian = "";
                    }
                    if (totalShare.equals("xxx")) {
                        totalShare = "";
                    }
                    if(srno.equals("xxx"))
                    {
                    	srno="";
                    }
                    
                    rowid = tempInfo[8];
                    if (rowid.equals("xxx")) {
                        rowid = "";
                    }

                    %>
											<tr id="name<%=i%>">
												<td width="15%">
													<input type="text" class="TextField" style="width: 120px;" styleClass="textbox" id="Nname<%=i%>" name="Nname" maxlength="50" value='<%=nomineeName%>' onblur="dispOff()" tabindex="31" readonly>
													<input type="hidden" name="empOldNname" value='<%=nomineeName%>'>
													<input type="hidden" name="srno" value='<%=srno%>'>
													<input type="hidden" name="empsrno" value='<%=bean1.getEmpSerialNo()%>'>
												</td>
												<td width="15%">
													<input type="text" class="TextField" style="width: 120px;" styleClass="textbox" id="Naddress<%=i%>" name="Naddress" maxlength="150" value='<%=nomineeAddress%>' tabindex="32" readonly>
												</td>
												<td width="15%" nowrap >
													<input type="text" class="TextField" style="width: 60px;" styleClass="textbox" id="Ndob<%=i%>" name="Ndob" value='<%=nomineeDob%>' readOnly >
													<a href="#" name="cal1" onclick="javascript:call_calender('forms[0].Ndob')"><img src="<%=basePath%>images/calendar.gif" border="0" align="center" /></a>&nbsp;
												</td>
												<td id="relationtext<%=i%>" width="15%" >
												<input type="text" class="TextField" id="Nrelation<%=i%>" style="width: 70px;" styleClass="textbox" name="Nrelation" value='<%=nomineeRelation%>' readOnly>
												</td>
												   
												<td id="relationselect<%=i%>" width="15%">

													<select name="Nrelation" id="Nrelation<%=i%>" styleClass="listbox" style="width:70px" class="TextField">
														<option value="">
															[Select One]
														</option>
														<option value='SPOUSE' <%if(nomineeRelation.equals("SPOUSE")){ out.println("selected");}%>>
															SPOUSE
														</option>
														<option value='SON' <%if(nomineeRelation.equals("SON")){ out.println("selected");}%>>
															SON
														</option>
														<option value='DAUGHTER' <%if(nomineeRelation.equals("DAUGHTER")){ out.println("selected");}%>>
															DAUGHTER
														</option>
														<option value='MOTHER' <%if(nomineeRelation.equals("MOTHER")){ out.println("selected");}%>>
															MOTHER
														</option>
														<option value='FATHER' <%if(nomineeRelation.equals("FATHER")){ out.println("selected");}%>>
															FATHER
														</option>
														<option value='SONS WIDOW' <%if(nomineeRelation.equals("SONS WIDOW")){ out.println("selected");}%>>
															SON'S WIDOW
														</option>
														<option value='WIDOWS DAUGHTER' <%if(nomineeRelation.equals("WIDOWS DAUGHTER")){ out.println("selected");}%>>
															WIDOW'S DAUGHTER
														</option>

														<option value='MOTHER-IN-LOW' <%if(nomineeRelation.equals("MOTHER-IN-LOW")){ out.println("selected");}%>>
															MOTHER-IN-LOW
														</option>
														<option value='FATHER-IN-LOW' <%if(nomineeRelation.equals("FATHER-IN-LOW")){ out.println("selected");}%>>
															FATHER-IN-LOW
														</option>
													</select>
												</td>
												<td width="15%">
													<input type="text" class="TextField" style="width: 80px;" styleClass="textbox" name="guardianname" id="guardianname<%=i%>" maxlength="50" value='<%=nameofGuardian%>'readonly >
												</td>
												<td width="15%">
													<input type="text" class="TextField" style="width: 80px;" styleClass="textbox" name="gaddress" id="gaddress<%=i%>" maxlength="150" value='<%=addressofGuardian%>' readonly>
												</td>
												<td width="15%">
													<input type="text" class="TextField" style="width: 60px;" styleClass="textbox" name="totalshare" id="totalshare<%=i%>" value='<%=totalShare%>' onkeypress="numsDotOnly()" readonly>
													<input type="hidden" name="editStatus" id="editStatus<%=i%>">
												</td>
												<td width="15%">
												
														<input type="button" value="E" class="butt" onclick="editingNominee('<%=i%>');"/>
													</td>


											</tr>
											<%}

            								%>
											

            

										</table>
										<div id="divNominee1">
											<table width="100%" border="0" align="center" >
												<tr>
													<td width="15%">
														
														<input type="text" class="TextField" style="width: 120px;"  name="Nname" maxlength="50"  onblur="dispOff()">
														
														
														<input type="hidden" name="empOldNname" value="">
														<input type="hidden" name="srno" value="">
													<input type="hidden" name="empsrno" value="">
													</td>

													<td width="15%">
														
														<input type="text" class="TextField"   name="Naddress" maxlength="150" >
														
													</td>
													<td width="15%" nowrap >
														
														<input type="text" style="width: 60px;" class="TextField" name="Ndob">
														<a href="#" name="cal1" onclick="javascript:call_calender('forms[0].Ndob')"><img src="<%=basePath%>images/calendar.gif" border="0" align="center" /></a>&nbsp;
														

													</td>
													<td  width="15%" >
														
														<select name="Nrelation" class="TextField" style="width:70px">
															<option value="">
																[Select One]
															</option>
															<option value='SPOUSE'>
																SPOUSE
															</option>
															<option value='SON'>
																SON
															</option>
															<option value='DAUGHTER'>
																DAUGHTER
															</option>
															<option value='MOTHER'>
																MOTHER
															</option>
															<option value='FATHER'>
																FATHER
															</option>
															<option value='SONS WIDOW'>
																SON'S WIDOW
															</option>
															<option value='WIDOWS DAUGHTER'>
																WIDOW'S DAUGHTER
															</option>
															<option value='MOTHER-IN-LOW'>
																MOTHER-IN-LOW
															</option>
															<option value='FATHER-IN-LOW'>
																FATHER-IN-LOW
															</option>

														</select>

													</td>
													<td  width="15%">
														<input type="text" style="width: 80px;" class="TextField" name="guardianname" maxlength="50" >
													</td>
													<td width="15%" >
														
														<input type="text" style="width: 80px;" class="TextField" name="gaddress" maxlength="150" >
														
													</td>
													<td  width="15%">
														
														<input type="text" style="width: 60px;" class="TextField" name="totalshare" onkeypress="numsDotOnly()" >
													</td>
													<td width="15%">
													<input type="hidden" name="editStatus" value="N">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b></b>
													</td>
													

												</tr>
											</table>
										</div>
										<div id="divNominee2">
										</div>
										
										
			
			</td>
			</tr>
			<tr>
			<td colspan=4>&nbsp;</td>	
			</tr>
			<tr>
			<tr>
			<td  colspan=4 align="center">
			
				<input type="button" class="butt" value="Update" class="btn" onclick="testSS()" >
				<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn" tabindex="47">
				
			</td>
			</tr>		

		</table>
<%=ScreenUtilities.screenFooter()%>
		</form>
	</body>
</html>



