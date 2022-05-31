<%@ page import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities"%>


<%@ page import="com.epis.bean.rpfc.EmpMasterBean"%>

<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String arrearInfoString1 = "";
            String regionFlag = "false";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>AAI</title>

<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
   	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
    <SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime1.js"></SCRIPT>
	<script type="text/javascript">    	
		
	function deleteFamilyDetils(cpfaccno,fmemberName,rowcolumn,rowid)
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
}

function deleteFamilyDetails1()
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
}
		
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
 function ValidateEMail(emailStr) 
{
	var emailPat=/^(.+)@(.+)$/
	var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]"
	var validChars="\[^\\s" + specialChars + "\]"
	var quotedUser="(\"[^\"]*\")"
	var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/
	var atom=validChars + '+'
	var word="(" + atom + "|" + quotedUser + ")"
	var userPat=new RegExp("^" + word + "(\\." + word + ")*$")
	var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$")
	var matchArray=emailStr.match(emailPat)
	if (matchArray==null) 
	{
		alert("Email address seems incorrect (check @ and .'s)")
		return false
	}
	var user=matchArray[1]
	var domain=matchArray[2]

	if (user.match(userPat)==null) 
	{
		alert("The username doesn't seem to be valid.")
		return false
	}

	var IPArray=domain.match(ipDomainPat)
	if (IPArray!=null) 
	{
		for (var i=1;i<=4;i++) 
		{
			if (IPArray[i]>255) 
			{
				alert("Destination IP address is invalid!")
				return false
			}
		}
	    return true
	}

	var domainArray=domain.match(domainPat)
	if (domainArray==null) 
	{
		alert("The domain name doesn't seem to be valid.")
		return false
	}

	var atomPat=new RegExp(atom,"g")
	var domArr=domain.match(atomPat)
	var len=domArr.length
	if (domArr[domArr.length-1].length<2 || 
		domArr[domArr.length-1].length>4) 
		{
		   alert("The address must end in a three-letter domain, or two letter country.")
		   return false
		}

	if (len<2) 
	{
		var errStr="This address is missing a hostname!"
		alert(errStr)
		return false
	}
	return true;
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
	
function getDesegnation(obj)
{	
  //  alert(obj);
	createXMLHttpRequest();	
	 var selectedIndex=document.forms[0].emplevel.options.selectedIndex;
	 var empLevelseleted=document.forms[0].emplevel[selectedIndex].text;
	// alert("selected level "+empLevelseleted);
	 var temp = new Array();
	 temp = empLevelseleted.split(',');
	 
	 var desegnation = temp[1];
	 
	 document.forms[0].desegnation.value=desegnation;
	
}
		function swap() { 
			if(document.getElementById('mySelect').value == 'Other'){ 
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'block'; 
			document.getElementById('myText').focus(); 
			
			} else{
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'none'; 
			}
			} 
			function swapback() { 
			document.getElementById('mySelect').selectedIndex = 0; 
			document.getElementById('mySelect').style.display = 'block'; 
			document.getElementById('myText').style.display = 'none'; 
			} 
    
	 function retrimentDate(){
   	 
	     var formDate=document.forms[0].dateofbirth.value;
	      if(formDate.lastIndexOf("-")!=-1 || formDate.lastIndexOf("/")!=-1){
			var year=formDate.substring(formDate.lastIndexOf("/")+1,formDate.length);
			if(year.length==4){
				var dateOfBirth=Number(year)+Number(60);
				dateOfBirth=formDate.replace(year,dateOfBirth);
				document.forms[0].dateOfAnnuation.value=dateOfBirth;
			}
		
		}
		
	 }
	 
	 function hide(){
			 divRegion.style.display="none";
			 divNominee1.style.display="none";
			 divNomineeHead.style.display="none";
			 if(document.forms[0].form1.value=="Yes"){
			 divNominee1.style.display="block";
			 divNomineeHead.style.display="block";
			  }
			 
			}
			
			function show(){
			// alert("hit"+document.forms[0].form1.value);
			 if(document.forms[0].form1.value=="Yes")
   		     {
   		     divNominee1.style.display="block";
   		      divNomineeHead.style.display="block";
   		   //   document.forms[0].nomineeflag.value="true";
   		     }
   		      else 
   		     {
   		      divNominee1.style.display="none";
   		      divNomineeHead.style.display="none";
   		
   		     }
			}
	 
	 function showRegion(){
			
			 if(document.forms[0].transferStatus.checked)
   		     {
   		    
   		     divRegion.style.display="block";
   		     }else{
   		       divRegion.style.display="none";
   		     }
   		     
			}
	
	  
	
	function limitlength(obj, length){
	var maxlength=length
	if (obj.value.length>maxlength)
	obj.value=obj.value.substring(0, maxlength)
	}
	
	 var count =0;
  	 function callFamily()

  	{ 
  	 count++;
     divFamily2.innerHTML+=divFamily1.innerHTML;
  	 var i=document.getElementsByName("FName");
  	 i[i.length-1].value="";
  	 var j=document.getElementsByName("Fdob");
  	  j[j.length-1].value="";
  	  j[j.length-1].id ="Fdob"+count;
  	 
  	  j = document.getElementsByName("cal");
  	
 	
  	  j[j.length-1].onclick=function(){call_calender("forms[0].Fdob"+count)};
  	 // alert(j[j.length-1].onclick);
  	  
  	  var k=document.getElementsByName("Frelation");
  	  k[k.length-1].value="";
  	
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
	
	 function testSS(index,totalData){
   		
   		if(document.forms[0].cpfacnoNew.value=="")
   		 {
   		  alert("Please Enter CpfAccount Number");
   		  document.forms[0].cpfacnoNew.focus();
   		  return false;
   		  }
   		 if(document.forms[0].airPortCode.value=="")
   		 {
   		  alert("Please Enter Airportcode");
   		  document.forms[0].airPortCode.focus();
   		  return false;
   		  }
   		 if(document.forms[0].empName.value=="")
   		 {
   		  alert("Please Enter Employee Name");
   		   document.forms[0].empName.focus();
   		  return false;
   		  }
   		  if(!ValidateName(document.forms[0].empName.value))
   		 {
   		  alert("Numeric/Invalid characters are not allowed");
   		   document.forms[0].empName.focus();
   		  return false;
   		  }
   		  
   		  if(document.forms[0].desegnation.value=="")
   		 {
   		  alert("Please Enter Designation");
   		  document.forms[0].desegnation.focus();
   		  return false;
   		  }
   		  
   	 var selectedIndex=document.forms[0].emplevel.options.selectedIndex;
	 var empLevelseleted=document.forms[0].emplevel[selectedIndex].text;
	// alert("selected level "+empLevelseleted);
	 var temp = new Array();
	 temp = empLevelseleted.split(',');
	 var desegnation = temp[1];
	// alert("desegnation"+desegnation);
	if(document.forms[0].desegnation.value!=desegnation){
	alert("Emp Level with Designation are not equal");
	document.forms[0].emplevel.focus();
   	 return false;
	}
	 
	 
   		  if(document.forms[0].dateofbirth.value=="")
   		 {
   		  alert("Please Enter Date of Birth");
   		  document.forms[0].dateofbirth.focus();
   		  return false;
   		  }
   		  if(!document.forms[0].dateofbirth.value==""){
   		   var date1=document.forms[0].dateofbirth;
   	       var val1=convert_date(date1);
   		   
   		   var now = new Date();
   		   var birthday1= document.forms[0].dateofbirth.value;
   		   var dt1   = birthday1.substring(0,2);
   		   var mon1  = birthday1.substring(3,6);
  		   var year1=birthday1.substring(birthday1.lastIndexOf("/")+1,birthday1.length);
  		  
  		    if(mon1 == "JAN") month = 0;
           	else if(mon1 == "FEB") month = 1;
        	else if(mon1 == "MAR") month = 2;
        	else if(mon1 == "APR") month = 3;
        	else if(mon1 == "MAY") month = 4;
        	else if(mon1 == "JUN") month = 5;
        	else if(mon1 == "JUL") month = 6;
        	else if(mon1 == "AUG") month = 7;
        	else if(mon1 == "SEP") month = 8;
        	else if(mon1 == "OCT") month = 9;
        	else if(mon1 == "NOV") month = 10;
        	else if(mon1 == "DEC") month = 11;
        	var birthDate=new Date(year1,month,dt1);   
        		
   		  if(birthDate > now){
		       alert("DateofBirth cannot be greater than Currentdate");
			   	document.forms[0].dateofbirth.focus();
		       return false;
			}
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		    
   		       }
   		    if(!document.forms[0].dateofjoining.value==""){
   		    var date1=document.forms[0].dateofjoining;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		   }
   		      
   		  if(!document.forms[0].dateOfAnnuation.value==""){
   		    var date1=document.forms[0].dateOfAnnuation;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		   }
   		      
   		   if(!document.forms[0].seperationDate.value==""){
   		    var date1=document.forms[0].seperationDate;
   	        var val1=convert_date(date1);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		     }
   		  
   		 if (!ValidateName(document.forms[0].fhname.value))
	  	{
		alert("Numeric/Invalid characters are not allowed");
		document.forms[0].fhname.focus();
		return false;
  	   }
  	   if(document.forms[0].paddress.value.length>150){
  	    alert("Permanent Address Exceeds the Limit");
		document.forms[0].paddress.focus();
		return false;
  	   }
  	  
   	  	if(!ValidateTextArea(document.forms[0].paddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].paddress.focus();
		return false;
  	  }
  	 
  	  if(document.forms[0].taddress.value.length>150){
  	    alert("Temporary Address Exceeds the Limit");
		document.forms[0].taddress.focus();
		return false;
  	   }
  	   if(!ValidateTextArea(document.forms[0].taddress.value)){
  	    alert("Please Enter valid data");
		document.forms[0].taddress.focus();
		return false;
  	   }
  	   
  	   if(document.forms[0].division.value==""){
  	    alert("Please Select division");
		document.forms[0].division.focus();
		return false;
  	   }
  	  /*  if(document.forms[0].transferStatus.checked){
  	    if(document.forms[0].select_region.value==""){
  	     alert("Please Select Transfer Region");
  	     document.forms[0].select_region.focus();
  	     return false;
  	    }else if(document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].text=="[Select One]"){
  	  
  	     alert("Please Select Transfer Airport");
  	     document.forms[0].select_airport.focus();
  	     return false;
  	    }
  	    }*/
  	    
  	   
  	    if(!document.forms[0].emailId.value==""){
  	     if(ValidateEMail(document.forms[0].emailId.value)==false){
  	   
		document.forms[0].emailId.focus();
		return false;}
  	   }
  	var oldAirportcode=  document.forms[0].oldAirportCode.value;
  	//var newAirportcode= document.forms[0].airPortCode.value; 
  	 	 var newAirportcodeIndex=document.forms[0].airPortCode.options.selectedIndex;
	 var newAirportcodewithRegion=document.forms[0].airPortCode[newAirportcodeIndex].text;
	
	  temp = newAirportcodewithRegion.split('-');
	 
	 var newAirportcode = temp[0];

  	if(oldAirportcode!=newAirportcode){
  	   	var answer =confirm('Old / New Airport Code are not equal. Are you sure, do you want to contine');
	
	if(!answer){
	 document.forms[0].airPortCode.focus();
		return false;
	  }
  	    }
   		  
   	  if(!ValidateTextArea(document.forms[0].remarks.value)){
  	   // alert("Please Enter valid data");
		document.forms[0].remarks.focus();
		return false;
  	   }
  	   
  	     	  
  	   if(document.forms[0].remarks.value.length>150){
  	    alert("Remarks Exceeds the Limit");
		document.forms[0].remarks.focus();
		return false;
  	   }
  	     var fdob1=document.getElementsByName("Fdob");
  	      var fname=document.getElementsByName("Fname")
  	      var j;
   		  	for(j=0;j<fdob1.length;j++){
   		  			var s=fdob1[j].value; 
  	     if(!fdob1[j].value==""){
   		  var date1=fdob1[j].value;
   		 
   	        var val1=convert_date(fdob1[j]);
   		  
   		    if(val1==false)
   		    {
   		    return false;
   		    }
   		     if(fname[j].value==""){
   		      alert("Please Enter Family Member Name");
   		      fname[j].focus();
   		      return false;
   		      }
   		      }
   		      }
  	     
   		 var ndob1=document.getElementsByName("Ndob");
   		 var nname=document.getElementsByName("Nname");
   		 var gaddress =document.getElementsByName("gaddress");
   		 var guardianname= document.getElementsByName("guardianname");
   		 var totalshare1=document.getElementsByName("totalshare");
   		 var k;
   		 
   		  for(k=0;k<ndob1.length;k++){
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
	   	document.forms[0].action="<%=basePath%>psearch?method=personalUpdate&startIndex="+index+"&totalData="+totalData+"&airportWithRegion="+newAirportcodewithRegion;
     
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
  </script>
</head>

<body class="BodyBackground"
	onload="document.forms[0].setEmpSerialNo.focus();hide();swap();retrimentDate();">

<form method="post" action="<%=basePath%>psearch?method=personalUpdate">
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>

	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="tbborder">
			<tr>
				<td align="center" class="ScreenMasterHeading">
				Form3-2007-EmpPersonalInfo &nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="center" class="label">
				<%if (request.getAttribute("ArrearInfo") != null) {
                arrearInfoString1 = (String) request.getAttribute("ArrearInfo");
                request.setAttribute("ArrearInfo", arrearInfoString1);
                System.out.println("arrearInfoString1****  "
                        + arrearInfoString1);

               }%> <%if (request.getAttribute("recordExist") != null) {%>
				<font color="red"><%=request.getAttribute("recordExist")%></font> <%}%>
				
				<td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="15%">
				<%String pensionNumber = "", editFrom = "";
            if (request.getAttribute("pensionNumber") != null) {
                pensionNumber = request.getAttribute("pensionNumber")
                        .toString();
            }

            %> <%if (request.getAttribute("EditBean") != null) {
                EmpMasterBean bean1 = (EmpMasterBean) request
                        .getAttribute("EditBean");
                String form2 = bean1.getForm2Nomination().trim();
                if (request.getAttribute("AirportList") != null) {
                    ArrayList AirportList = (ArrayList) request
                            .getAttribute("AirportList");
                     }
                if (request.getAttribute("editFrom") != null) {
                    editFrom = (String) request.getAttribute("editFrom");
                 }

                %>

				<table align="center">
					<!--<%=bean1.getFamilyRow()%>
					        <%=bean1.getNomineeRow()%>
				
							-->
					<tr>
						<td class="label"><b>PF ID: <b></td>
						<td><input type="text" readonly="true" name="pfid"
							readonly="true" maxlength="35" tabindex="1" readonly="true"
							value='<%=bean1.getPfid()%>' /> <input type="hidden"
							name="setEmpSerialNo" readonly="true" maxlength="35" tabindex="1"
							value='<%=bean1.getEmpSerialNo()%>' /></td>
					</tr>
					<tr>
						<td class="label">Old CPFACC.NO:<font color="red">&nbsp;*</font>
						</td>
						<td><input type="text" readonly="true" name="cpfacnoNew"
							maxlength="25" tabindex="2"
							onBlur="charsCapsNumOnly();getPensionNumber();"
							value='<%=bean1.getCpfAcNo()%>' /></td>
						<!-- 	<input type="text" name="cpfacnoNew" maxlength="25" tabindex="1" onBlur="charsCapsNumOnly();getPensionNumber();" value='<%=bean1.getCpfAcNo()%>'>-->

						<td class="label">Old / New Airport Code:<font color="red">&nbsp;*</font>
						</td>
						<td><input type="text" readonly="true" name="oldAirportCode"
							size="5" readonly="true" value='<%= bean1.getStation()%>'%><select
							name="airPortCode" readonly="true" tabindex="3">
							<option value="">[Select One]</option>
							<%if (request.getAttribute("AirportList") != null) {
                    ArrayList airpors = (ArrayList) request
                            .getAttribute("AirportList");
                    for (int i = 0; i < airpors.size(); i++) {
                        boolean exist = false;
                        EmpMasterBean airportBean = (EmpMasterBean) airpors
                                .get(i);
                        if (airportBean.getStation().equalsIgnoreCase(
                                bean1.getStation()))
                            exist = true;

                        %>

							<%if (exist) {%>

							<option value="<%= bean1.getStation()%>" selected><%=airportBean.getStationWithRegion()%>
							</option>

							<%} else {

                            %>

							<option value="<%= airportBean.getStation()%>"><%=airportBean.getStationWithRegion()%>
							</option>

							<%}
                    }
                }%>


						</select></td>


						<input type="hidden" name="airportSerialNumber"
							value='<%=bean1.getAirportSerialNumber()%>'
							onkeyup="return limitlength(this, s0)">
						<input type="hidden" name="region" value='<%=bean1.getRegion()%>'
							onkeyup="return limitlength(this, s0)">
						<input type="hidden" name="editFrom" value='<%=editFrom%>' />
					</tr>
					<tr>
						<td class="label">Employee Code:</td>
						<td><input type="text" readonly="true" name="employeeCode"
							maxlength="20" tabindex="4" value='<%=bean1.getEmpNumber()%>'
							onkeyup="return limitlength(this, 20)"></td>

						<td class="label">Employee Name:<font color="red">&nbsp;*</font>
						</td>

						<td><input type="text" readonly="true" name="empName"
							maxlength="50" tabindex="5" onblur="getPensionNumber();"
							value='<%=bean1.getEmpName()%>'
							onkeyup="return limitlength(this,50)"></td>
					</tr>
					<tr>
						<td class="label">Father's / Husband's Name:</td>
						<td><select name="select_fhname" disabled="disabled"
							tabindex="6" style="width: 64px">
							<option value='F'
								<%if(bean1.getFhFlag().trim().equals("F")){ out.println("selected");}%>>
							Father</option>
							<option value='H'
								<%if(bean1.getFhFlag().trim().equals("H")){ out.println("selected");}%>>
							Husband</option>
						</select> <input type="text" readonly="true" name="fhname" readonly="true"
							maxlength="50" tabindex="7" value='<%=bean1.getFhName()%>'
							onkeyup="return limitlength(this, 50)"></td>

						<td class="label">Sex:</td>

						<td><select name="sex" disabled="disabled" readonly="true"
							tabindex="8">
							<%String sex = bean1.getSex().trim();%>
							<option value="">[Select One]</option>
							<option value="M"
								<%if(sex.equals("M")){ out.println("selected");}%>>
							Male</option>
							<option value="F"
								<%if(sex.equals("F")){ out.println("selected");}%>>
							Female</option>

						</select></td>
					</tr>
					<tr>
						<td class="label">Date of Birth:<font color="red">&nbsp;*</font>
						</td>
						<td><input type="text" readonly="true" name="dateofbirth"
							tabindex="9" onblur="retrimentDate();getPensionNumber();"
							value='<%=bean1.getDateofBirth()%>'
							onkeyup="return limitlength(this, 20)"><a
							href="javascript:show_calendar('forms[0].dateofbirth');"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
						<td class="label">Date of Joining:</td>
						<td><input type="text" readonly="true" name="dateofjoining"
							tabindex="10" value='<%=bean1.getDateofJoining()%>'
							onkeyup="return limitlength(this, 20)"><a
							href="javascript:show_calendar('forms[0].dateofjoining');"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
					</tr>
					<tr>
						<td class="label">Employee Level:<font color="red">&nbsp;*</font>
						</td>
						<td>
						<%String emplevel = bean1.getEmpLevel().trim();%> <select
							name="emplevel" disabled="disabled" tabindex="11"
							onchange="getDesegnation(document.forms[0].emplevel.value)">
							<option value="">[Select One]</option>
							<option value='E-9'
								<%if(emplevel.equals("E-9")){ out.println("selected");}%>>
							E-9,Executive Directior</option>
							<option value='E-8'
								<%if(emplevel.equals("E-8")){ out.println("selected");}%>>
							E-8,General Manager</option>
							<option value='E-7'
								<%if(emplevel.equals("E-7")){ out.println("selected");}%>>
							E-7,Jt. General Manager</option>
							<option value='E-6'
								<%if(emplevel.equals("E-6")){ out.println("selected");}%>>
							E-6,Deputy General Manager</option>
							<option value='E-5'
								<%if(emplevel.equals("E-5")){ out.println("selected");}%>>
							E-5,Asst. General Manager</option>
							<option value='E-4'
								<%if(emplevel.equals("E-4")){ out.println("selected");}%>>
							E-4,Senior Manager</option>
							<option value='E-3'
								<%if(emplevel.equals("E-3")){ out.println("selected");}%>>
							E-3,Manager</option>
							<option value='E-2'
								<%if(emplevel.equals("E-2")){ out.println("selected");}%>>
							E-2,Assistant Manager</option>
							<option value='E-1'
								<%if(emplevel.equals("E-1")){ out.println("selected");}%>>
							E-1,Junior Executive</option>
							<option value='NE-1'
								<%if(emplevel.equals("NE-1")){ out.println("selected");}%>>
							NE-1,Jr. Attendant</option>
							<option value='NE-2'
								<%if(emplevel.equals("NE-2")){ out.println("selected");}%>>
							NE-2,Attendant</option>
							<option value='NE-3'
								<%if(emplevel.equals("NE-3")){ out.println("selected");}%>>
							NE-3,Senior Attendant</option>
							<option value='NE-4'
								<%if(emplevel.equals("NE-4")){ out.println("selected");}%>>
							NE-4,Jr. Assistant</option>
							<option value='NE-5'
								<%if(emplevel.equals("NE-5")){ out.println("selected");}%>>
							NE-5,Assistant</option>
							<option value='NE-6'
								<%if(emplevel.equals("NE-6")){ out.println("selected");}%>>
							NE-6,Senior Assistant</option>
							<option value='NE-7'
								<%if(emplevel.equals("NE-7")){ out.println("selected");}%>>
							NE-7,Supervisor</option>
							<option value='NE-8'
								<%if(emplevel.equals("NE-8")){ out.println("selected");}%>>
							NE-8,Superintendent</option>
							<option value='NE-9'
								<%if(emplevel.equals("NE-9")){ out.println("selected");}%>>
							NE-9,Sr. Superintendent</option>
							<option value='NE-10'
								<%if(emplevel.equals("NE-10")){ out.println("selected");}%>>
							NE-10,Sr. Superintendent(SG)</option>
							<option value='B1'
								<%if(emplevel.equals("B1")){ out.println("selected");}%>>
							B1,Chairman</option>
							<option value='B2'
								<%if(emplevel.equals("B2")){ out.println("selected");}%>>
							B2,Member</option>
						</select></td>

						<td class="label">Designation:<font color="red">&nbsp;*</font>
						</td>
						<td><input type="text" name="desegnation" readonly="true"
							readonly="true" tabindex="12" value='<%=bean1.getDesegnation()%>'
							onkeyup="return limitlength(this, 20)"></td>
					</tr>
					<tr>
						<td class="label">Discipline:</td>
						<td>
						<%String department = bean1.getDepartment().trim();

                %> <select name="department" disabled="disabled"
							tabindex="13" style="display: inline;">
							<option value="">[Select One]</option>
							<%if (request.getAttribute("DepartmentList") != null) {
                    ArrayList deptList = (ArrayList) request
                            .getAttribute("DepartmentList");
                    for (int i = 0; i < deptList.size(); i++) {
                        boolean exist = false;
                        EmpMasterBean deptBean = (EmpMasterBean) deptList
                                .get(i);
                        if (deptBean.getDepartment().equalsIgnoreCase(
                                bean1.getDepartment().trim()))
                            exist = true;
                        if (exist) {

                            %>
							<option value="<%=bean1.getDepartment().trim()%>"
								<% out.println("selected");%>><%=bean1.getDepartment().trim()%>
							</option>
							<%} else {%>
							<option value="<%= deptBean.getDepartment()%>"><%=deptBean.getDepartment()%>
							</option>

							<%}
                    }
                }%>

						</select></td>
						<td class="label">Division:<font color="red">&nbsp;*</font></td>
						<td>
						<%String division = bean1.getDivision().trim();

                %> <select name="division" disabled="disabled"
							tabindex="14">
							<option value="">[Select One]</option>
							<option value='NAD'
								<%if(division.equals("NAD")){ out.println("selected");}%>>
							NAD</option>
							<option value='IAD'
								<%if(division.equals("IAD")){ out.println("selected");}%>>
							IAD</option>
						</select></td>
					</tr>
					<%--<tr>
											<td class="label">
												Transfer Status:
											</td>
											<td>
												<input type="checkbox" name="transferStatus" tabindex="30" onclick="showRegion();">
											</td>
										</tr>--%>
					<%ArrayList regionList = new ArrayList();
                String rgnName = "", region = "";
                HashMap map = new HashMap();
                CommonUtil commonUtil = new CommonUtil();
                String[] regionLst = null;
                Iterator regionIterator1 = null;
                // ArrayList airportList1=null;
                if (session.getAttribute("region") != null) {
                    regionLst = (String[]) session.getAttribute("region");
                }

                for (int i = 0; i < regionLst.length; i++) {
                    rgnName = regionLst[i];

                    if (rgnName.equals("ALL-REGIONS")
                            && session.getAttribute("usertype").toString()
                                    .equals("Admin")) {
                        map = new HashMap();
                        map = commonUtil.getRegion();
                        break;
                    } else {
                        map.put(new Integer(i), rgnName);

                    }

                }
                Set keys = map.keySet();
                regionIterator1 = keys.iterator();

                %><!--						<tr>
											<td colspan="6">
												<div id="divRegion">
													<table width="100%">
														<tr>
															<td colspan="2" class="label">
																&nbsp;&nbsp;&nbsp;Region:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</td>
															<td class="label">
																<SELECT NAME="select_region" onChange="javascript:getAirports()" style="width:130px">
																	<option value="">
																		[Select One]
																	</option>
																	<%while (regionIterator1.hasNext()) {
                    region = map.get(regionIterator1.next()).toString();

                    %>
																	<option value="<%=region%>">
																		<%=region%>
																	</option>
																	<%}%>
																</SELECT>
															</td>
															<td class="label">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Airport Code:<font color="red">&nbsp;*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</td>
															<td>
																<select name="select_airport">
																	<option value="">
																		[Select One]
																	</option>
																</select>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>

										-->
					<tr>
						<td class="label">Separation Reason:</td>
						<td><select name="reason" disabled="disabled" tabindex="15"
							id="mySelect" onChange="swap();" style="display: inline;">
							<%String reason = bean1.getSeperationReason().trim();

                                                    %>
							<option value="">[Select One]</option>
							<option value='Retirement'
								<%if(reason.equals("Retirement")){ out.println("selected");}%>>
							Retirement</option>
							<option value='Death'
								<%if(reason.equals("Death")){ out.println("selected");}%>>
							Death</option>
							<option value='Resignation'
								<%if(reason.equals("Resignation")){ out.println("selected");}%>>
							Resignation</option>
							<option value='Termination'
								<%if(reason.equals("Termination")){ out.println("selected");}%>>
							Termination</option>
							<option value='Option for Early Pension'
								<%if(reason.equals("Option for Early Pension")){ out.println("selected");}%>>
							Option for Early Pension</option>
							<option value='VRS'
								<%if(reason.equals("VRS")){ out.println("selected");}%>>
							VRS</option>

							<option value='Other'
								<%if(reason.equals("Other")){ out.println("selected");}%>>
							Other</option>
						</select></td>
						<td class="label">Date of Separation:</td>
						<td><input type="text" name="seperationDate" readonly="true"
							tabindex="16" value='<%=bean1.getDateofSeperationDate()%>'
							onkeyup="return limitlength(this, 20)"><a
							href="javascript:show_calendar('forms[0].seperationDate');"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="text" name="Other" readyonly="true"
							value='<%=bean1.getOtherReason()%>' id="myText"
							style="display: none;" tabindex="17" onclick="swapback()"></td>
					</tr>
					<tr>

						<td class="label">Permanent Address:</td>

						<td><TEXTAREA name="paddress" size="150" maxlength="150"
							tabindex="18">
													<%=bean1.getPermanentAddress()%>
												</TEXTAREA></td>
						<td class="label">Temporary Address:</td>
						<td><TEXTAREA name="taddress" size="150" maxlength="150"
							tabindex="19">
													<%=bean1.getTemporatyAddress()%>
												</TEXTAREA></td>
					</tr>
					<tr>
						<td class="label">Pension Option Received:</td>

						<td><input type="text" name="wetherOption" readonly="true"
							value='<%=bean1.getWetherOption().trim()%>' tabindex="20"
							readonly="true"> <!-- 	<select name='wetherOption' tabindex="20"  >
													<%String wetherOption = bean1.getWetherOption().trim();

                                                   %>
													<option value="">
														[Select One]
													</option>
													<option value="A" <%if(wetherOption.equals("A")){ out.println("selected");}%>>
														A
													</option>
													<option value="B" <%if(wetherOption.equals("B")) out.println("selected");%>>
														B
													</option>
													<option value="No" <%if(wetherOption.equals("No")){ out.println("selected");}%>>
														No
													</option> 
												</select> --></td>
						<td class="label">Whether Form2 Nomination Received :</td>
						<td><select name="form1" disabled="disabled" tabindex="21"
							onchange="show()">

							<%String form1 = bean1.getForm2Nomination().trim();

                %>
							<option value="">[Select One]</option>
							<option value="Yes"
								<%if(form1.equals("Yes")){ out.println("selected");}%>>
							Yes</option>
							<option value="No"
								<%if(form1.equals("No")){ out.println("selected");}%>>
							No</option>

						</select></td>

					</tr>
					<tr>
						<input type="hidden" name="cpfacno"
							value='<%=bean1.getCpfAcNo()%>'>
						<input type="hidden" name="empOldName"
							value='<%=bean1.getEmpName()%>'>
						<input type="hidden" name="empOldNumber"
							value='<%=bean1.getEmpNumber()%>'>
						<td class="label">Date of Super Annuation:</td>
						<td><input type="text" name="dateOfAnnuation" readonly="true"
							tabindex="23" value='<%=bean1.getDateOfAnnuation()%>'></td>
						<td class="label">Email Id:</td>
						<td><input type="text" name="emailId" readonly="true"
							maxlength="50" onkeyup="return limitlength(this,50)"
							value='<%=bean1.getEmailId()%>' tabindex="24"></td>
					</tr>

					<tr>





					</tr>
					<tr>
						<td class="label">Remarks:</td>

						<td><TEXTAREA NAME="remarks" readonly="true" tabindex="25">
													<%=bean1.getRemarks()%>
												</TEXTAREA> <input type="hidden" name="ArrearInfo"
							value="<%=arrearInfoString1%>"></td>



					</tr>
					<br>
					<tr>
						<td class="ScreenSubHeading">Family Details</td>
					</tr>
					<tr>

						<td class="label">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name
						</td>
						<td class="label">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date
						of Birth</td>
						<td class="label">Relation with member</td>

						<td>&nbsp;<b><img alt="Add New Record"
							src="<%=basePath%>PensionView/images/addIcon.gif" tabindex="27"></b></td>

					</tr>

					<%int count = 0;
                String familyrows = bean1.getFamilyRow();

                ArrayList familyList = commonUtil.getTheList(familyrows, "***");

                String tempInfo[] = null;
                String tempData = "";
                String fMemberName = "", fDateofBirth = "", frelation = "", rowid = "";
                for (int i = 0; i < familyList.size(); i++) {

                    tempData = familyList.get(i).toString();
                    tempInfo = tempData.split("@");

                    fMemberName = tempInfo[0];
                    fDateofBirth = tempInfo[1];
                    if (fDateofBirth.equals("xxx")) {
                        fDateofBirth = "";
                    }
                    frelation = tempInfo[2];
                    if (frelation.equals("xxx")) {
                        frelation = "";
                    }
                    rowid = tempInfo[3];
                    if (rowid.equals("xxx")) {
                        rowid = "";
                    }

                    %>
					<tr id="name1<%=i%>">
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

						<input type="text" readonly="true" name="FName" tabindex="24"
							maxlength="50" value='<%=fMemberName%>'><input
							type="hidden" name="empOldFRName" value='<%=fMemberName%>'></td>


						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text"
							readonly="true" name="Fdob" value='<%=fDateofBirth%>'
							tabindex="25"><a href="#"
							onclick="javascript:show_calendar('forms[0].Fdob'+<%=i%>);"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
						<td><select name="Frelation" disabled="disabled"
							tabindex="26">

							<option value="">[Select One]</option>
							<option value='SPOUSE'
								<%if(frelation.equals("SPOUSE")){ out.println("selected");}%>>
							SPOUSE</option>
							<option value='SON'
								<%if(frelation.equals("SON")){ out.println("selected");}%>>
							SON</option>
							<option value='DAUGHTER'
								<%if(frelation.equals("DAUGHTER")){ out.println("selected");}%>>
							DAUGHTER</option>
							<option value='MOTHER'
								<%if(frelation.equals("MOTHER")){ out.println("selected");}%>>
							MOTHER</option>
							<option value='FATHER'
								<%if(frelation.equals("FATHER")){ out.println("selected");}%>>
							FATHER</option>
							<option value='SONS WIDOW'
								<%if(frelation.equals("SONS WIDOW")){ out.println("selected");}%>>
							SON'S WIDOW</option>
							<option value='WIDOWS DAUGHTER'
								<%if(frelation.equals("WIDOWS DAUGHTER")){ out.println("selected");}%>>
							WIDOW'S DAUGHTER</option>

							<option value='MOTHER-IN-LAW'
								<%if(frelation.equals("MOTHER-IN-LAW")){ out.println("selected");}%>>
							MOTHER-IN-LAW</option>
							<option value='FATHER-IN-LAW'
								<%if(frelation.equals("FATHER-IN-LAW")){ out.println("selected");}%>>
							FATHER-IN-LaW</option>

						</select></td>
						<td>&nbsp;<b><img alt="Delete" 
							src="<%=basePath%>PensionView/images/cancelIcon.gif" tabindex="27"></b></td>
						<!-- 		<td>
												&nbsp;<b><img alt="" src="<%=basePath%>PensionView/images/cancelIcon.gif" onclick="deleteFamilyDetails('<%=fMemberName%>','<%=i%>')"> </b>
											</td> -->
					</tr>


					<tr>



					</tr>
					<%}

                %>
				</table>
				<div id="divFamily1">
				<table>
					<tr>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="FName" maxlength="50" tabindex="27"></td>
						<input type="hidden" name="empOldFRName" value="">
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text"
							name="Fdob" id="Fdob0" tabindex="28"><a href="#"
							name="cal" onclick="javascript:call_calender('forms[0].Fdob0')"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
						<td>&nbsp; <select name="Frelation" disabled="disabled"
							tabindex="29">
							<option value="">[Select One]</option>
							<option value='SPOUSE'>SPOUSE</option>
							<option value='SON'>SON</option>
							<option value='DAUGHTER'>DAUGHTER</option>
							<option value='MOTHER'>MOTHER</option>
							<option value='FATHER'>FATHER</option>
							<option value='SONS WIDOW'>SON'S WIDOW</option>
							<option value='WIDOWS DAUGHTER'>WIDOW'S DAUGHTER</option>
							<option value='MOTHER-IN-LAW'>MOTHER-IN-LAW</option>
							<option value='FATHER-IN-LAW'>FATHER-IN-LAW</option>

						</select></td>

					</tr>
				</table>
				</div>

				<div id="divFamily2"></div>

				<div id="divNomineeHead">
				<table align="center" width="95%">
					<tr>
						<td class="ScreenSubHeading">Nomination for PF</td>

					</tr>
					<tr>
						<td>&nbsp;</td>
						<td class="label">&nbsp;&nbsp;Equal share check:&nbsp;</td>
						<%String equalSharecheck = bean1.getEmpNomineeSharable();%>
						<%if (bean1.getEmpNomineeSharable().equals("true")) {%>
						<td><input type="checkbox" name="equalShare" readonly="true"
							tabindex="30"
							value='<%=("true".equals(equalSharecheck)?"true":"")%>'
							onClick='dispOff()'
							<%=(("true".equals(equalSharecheck))?"checked":"")%>></td>
						<%} else {%>
						<td><input type="checkbox" name="equalShare" readonly="true"
							value="true" onClick='dispOff()'></td>
						<%}%>
					</tr>

					<tr>
						<td class="label">
						Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="label">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Address&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="label">Dateof
						Birth&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td class="label">Relation with Member&nbsp;&nbsp;</td>
						<td class="label">Name of Guardian&nbsp;&nbsp;&nbsp;</td>
						<td class="label">Address of Guardian&nbsp;&nbsp;&nbsp;</td>
						<td class="label">Total Share <BR> payable in % 
						</td>
						<td>&nbsp;<b><img alt="Add New Record"
							src="<%=basePath%>PensionView/images/addIcon.gif"
							 tabindex="38"></b></td>


					</tr>
					</div>
					<%int count1 = 0;
                String nomineeRows = bean1.getNomineeRow();

                CommonUtil commonUtil1 = new CommonUtil();
                ArrayList nomineeList = commonUtil.getTheList(nomineeRows,
                        "***");

                String tempInfo1[] = null;
                String tempData1 = "";
                String nomineeAddress = "", nomineeDob = "", nomineeRelation = "", nameofGuardian = "", totalShare = "", addressofGuardian = "";
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
                    rowid = tempInfo[7];
                    if (rowid.equals("xxx")) {
                        rowid = "";
                    }

                    %>
					<tr id="name<%=i%>">
						<td><input type="text" readonly="true" size="18" name="Nname" maxlength="50"
							value='<%=nomineeName%>' onblur="dispOff()" tabindex="31"><input
							type="hidden" name="empOldNname" value='<%=nomineeName%>'></td>
						<td><input type="text" readonly="true" size="16" name="Naddress"
							maxlength="150" value='<%=nomineeAddress%>' tabindex="32"></td>
						<td><input type="text" readonly="true" size="16" name="Ndob"
							value='<%=nomineeDob%>' tabindex="33"><a
							href="javascript:show_calendar('forms[0].Ndob');"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>
						</td>
						<td><select name="Nrelation" tabindex="34" disabled="disabled">
							<option value="">[Select One]</option>
							<option value='SPOUSE'
								<%if(nomineeRelation.equals("SPOUSE")){ out.println("selected");}%>>
							SPOUSE</option>
							<option value='SON'
								<%if(nomineeRelation.equals("SON")){ out.println("selected");}%>>
							SON</option>
							<option value='DAUGHTER'
								<%if(nomineeRelation.equals("DAUGHTER")){ out.println("selected");}%>>
							DAUGHTER</option>
							<option value='MOTHER'
								<%if(nomineeRelation.equals("MOTHER")){ out.println("selected");}%>>
							MOTHER</option>
							<option value='FATHER'
								<%if(nomineeRelation.equals("FATHER")){ out.println("selected");}%>>
							FATHER</option>
							<option value='SONS WIDOW'
								<%if(nomineeRelation.equals("SONS WIDOW")){ out.println("selected");}%>>
							SON'S WIDOW</option>
							<option value='WIDOWS DAUGHTER'
								<%if(nomineeRelation.equals("WIDOWS DAUGHTER")){ out.println("selected");}%>>
							WIDOW'S DAUGHTER</option>

							<option value='MOTHER-IN-LAW'
								<%if(nomineeRelation.equals("MOTHER-IN-LAW")){ out.println("selected");}%>>
							MOTHER-IN-LAW</option>
							<option value='FATHER-IN-LAW'
								<%if(nomineeRelation.equals("FATHER-IN-LAW")){ out.println("selected");}%>>
							FATHER-IN-LAW</option>
						</select></td>
						<td><input type="text" size="16" readonly="true" name="guardianname"
							maxlength="50" value='<%=nameofGuardian%>' tabindex="35"></td>
						<td><input type="text" size="16" readonly="true" name="gaddress"
							maxlength="150" value='<%=addressofGuardian%>' tabindex="36"></td>
						<td><input type="text" size="5" readonly="true" name="totalshare"
							value='<%=totalShare%>' onkeypress="numsDotOnly()" tabindex="37"></td>
						<td>&nbsp;<b><img alt="Delete"
							src="<%=basePath%>PensionView/images/cancelIcon.gif"	tabindex="27"></b></td>


					</tr>
					<%}

            %>
					<%}

            %>

				</table>
				<div id="divNominee1">
				<table>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
							type="text" size="18" name="Nname" maxlength="50" tabindex="39"
							onblur="dispOff()"> &nbsp; <input type="hidden"
							name="empOldNname" value=""></td>

						<td>&nbsp; <input type="text" size="16" name="Naddress"
							maxlength="150" tabindex="40"></td>
						<td>&nbsp;&nbsp; <input type="text" size="16" name="Ndob"
							tabindex="41"><a href="#" name="cal1"
							onclick="javascript:call_calender('forms[0].Ndob')"><img
							src="<%=basePath%>PensionView/images/calendar.gif" border="no" /></a>&nbsp;

						</td>
						<td>&nbsp; <select name="Nrelation" tabindex="40" disabled="disabled">
							<option value="">[Select One]</option>
							<option value='SPOUSE'>SPOUSE</option>
							<option value='SON'>SON</option>
							<option value='DAUGHTER'>DAUGHTER</option>
							<option value='MOTHER'>MOTHER</option>
							<option value='FATHER'>FATHER</option>
							<option value='SONS WIDOW'>SON'S WIDOW</option>
							<option value='WIDOWS DAUGHTER'>WIDOW'S DAUGHTER</option>
							<option value='MOTHER-IN-LAW'>MOTHER-IN-LAW</option>
							<option value='FATHER-IN-LAW'>FATHER-IN-LAW</option>

						</select></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" size="16"
							name="guardianname" readonly="true" maxlength="50" tabindex="42"></td>
						<td>&nbsp;&nbsp; <input type="text" readonly="true" size="16" name="gaddress"
							maxlength="150" tabindex="43"> &nbsp; </td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" readonly="true"
							size="5" name="totalshare" onkeypress="numsDotOnly()"
							tabindex="44"></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b></b></td>

					</tr>
				</table>
				</div>

				<div id="divNominee2"></div>

				<input type="hidden" name="flagData"
					value="<%=request.getAttribute("flag")%>">
			<tr>
				<td>
			<tr>
				<td>
				<table align="center">
					<tr>
						<td align="center"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
</table>

</form>
</body>
</html>



