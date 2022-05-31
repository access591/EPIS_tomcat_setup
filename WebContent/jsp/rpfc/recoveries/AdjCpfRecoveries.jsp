

<%@ page language="java"
	import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.ScreenUtilities,com.epis.utilities.Constants,com.epis.bean.rpfc.EmployeePensionCardInfo"
	contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";


	if (request.getAttribute("cpfRecoveryList") != null) {
		int totalData = 0;

		ArrayList dataList = new ArrayList();
		dataList = (ArrayList) request.getAttribute("cpfRecoveryList");

	}
	
%>
<html>
<HEAD>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

<script type="text/javascript"><!--
function numsDotOnly()
	{
    if(((event.keyCode>=48)&&(event.keyCode<=57))||(event.keyCode==46) ||(event.keyCode>=44 && event.keyCode<=47))
          {
             event.keyCode=event.keyCode;
          }
          else
          {
			 event.keyCode=0;
          }
    }
		function test(cpfaccno,region,pensionNumber,employeeName,dateofbirth,empSerialNo){
			document.forms[0].empName.value=employeeName;
			document.forms[0].empserialNO.value=empSerialNo;
			document.forms[0].cpfacno.value=cpfaccno;
			document.forms[0].region.value=region;
					
		}
		function popupWindow(mylink, windowname)
		{
		//var transfer=document.forms[0].transferStatus.value;
		var transfer="";
		var regionID="";
	/*	if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}*/
		if (! window.focus)return true;
		var href;
		if (typeof(mylink) == 'string')
		   href=mylink+"&transferStatus="+transfer+"&region="+regionID;
		else
		href=mylink.href+"&transferStatus="+transfer+"&region="+regionID;
	    progress=window.open(href, windowname, 'width=700,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
		
		return true;
		}
	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>pfinance.do?method=loadAdj";
		document.forms[0].method="post";
		document.forms[0].submit();
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
			if(document.forms[0].select_month.selectedIndex>0){
				monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
			}else{
				monthID=document.forms[0].select_month.value;
			}
			  frm_ltstmonthflag="false";
			var url ="<%=basePath%>psearch?method=getPFIDListWthoutTrnFlag&frm_region="+regionID+"&frm_airportcode="+airportcode+"&frm_month="+monthID+"&frm_year="+yearID+"&frm_ltstmonthflag="+frm_ltstmonthflag;;
		
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
	
   var count =0;
 function callFamily()
	{ 
	 divFamily1.style.display="block";
	}


	function frmload(){
		 process.style.display="none";
		 divFamily1.style.display="none";
		 divlist.style.display="none";
		 document.forms[0].select_year.value="2007-08";
	}

	function validateForm(){
		var regionID="",airportcode="",year="",month="",empName="",yearID="",select_month="",select_region="",select_airport="";
		if(document.forms[0].select_year.value=="NO-SELECT"){
			alert("Please Select The Year");
			document.forms[0].select_year.focus();
			return false;
	     }if(document.forms[0].select_month.value=="NO-SELECT"){
			alert("Please Select The Month");
			document.forms[0].select_month.focus();
			return false;
		
		   }else{
			   select_month1=document.forms[0].select_month.value;
		   }
	/*	if(document.forms[0].select_region.value=="NO-SELECT"){
			alert("Please Select The Region");
			document.forms[0].select_region.focus();
			return false;
		}else{
			select_region1=document.forms[0].select_region.value;
			}
	  	if(document.forms[0].select_region.value=="CHQIAD" && document.forms[0].select_airport.value=="NO-SELECT"){
	  		alert("Please Select The AirportCode");
	  		document.forms[0].select_airport.focus();
	  		return false;
		}else{
			select_airport1=document.forms[0].select_airport.value;
		}*/
		select_region1="";
		select_airport1="";
	  	if(document.forms[0].empName.value==""){
	  		alert("Please Select The EmployeeName");
	  		document.forms[0].empName.focus();
	  		return false;
		}
		var empserialNO=document.forms[0].empserialNO.value;
		var cpfacno=document.forms[0].cpfacno.value;
		var region=document.forms[0].region.value;
		var toYearID="";
	   	if(document.forms[0].select_year.value!='NO-SELECT'){
	  	  	year=document.forms[0].select_year.value;
			var splitYearID = year.split("-");
			yearID=splitYearID[0];
			
			if(yearID.substring(0,2)=='19' && yearID.substring(2,4)<'99'){
				toYearID=yearID.substring(0,2)+splitYearID[1]
			}else if(yearID.substring(0,2)=='20'){
				toYearID=yearID.substring(0,2)+splitYearID[1]
			}else if(yearID.substring(0,2)=='19' && yearID.substring(2,4)=='99'){
				toYearID=2000;
			}
		}

		var	url="<%=basePath%>pfinance.do?method=loadMonthlyRecoveries&frm_year="+yearID+"&to_year="+toYearID+"&empserialNO="+empserialNO+"&select_region="+select_region1+"&select_month="+select_month1+"&select_airport="+select_airport1+"&cpfacno="+cpfacno+"&region="+region;          
        createXMLHttpRequest();	
   	 	xmlHttp.open("post", url, true);
		xmlHttp.onreadystatechange = getSearchList;
		xmlHttp.send(null);
     }

	function getSearchList()
	{
	if(xmlHttp.readyState ==4)
	{
		//alert(xmlHttp.responseText);
	// divFamily1.style.display="none";
	if(xmlHttp.status == 200)
		{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
		  if(stype.length==0){
		 //	var pensionno = document.getElementById("pensionno");
		 	divlist.style.display="block"; 
		   	divlist.innerHTML="<table width='100%'><tr><td height='5%' width='10%' colspan='7' align='center'>No Records Found</td></tr></table>";  
		    }else{ divlist.style.display="block"; 
		   divlist.innerHTML="";
		    divlist.innerHTML="<table ><tr><td height='5%' width='14%' colspan='8' align='center' class='ScreenHeading'>Monthly CPFRecoveries</td></tr></table>";
			divlist.innerHTML=divlist.innerHTML+"<table align='center' width='100%' cellpadding=2 class='GridBorder' cellspacing='0' border='0'><tr class='GridHBg' colspan='11'><b><td class='GridLTCells' width='9%' >PFID</td><td class='GridLTCells' width='10%'>MonthYear</td><td class='GridLTCells' width='10%'>Emolument</td><td class='GridLTCells' width='10%'> CPF</td><td class='GridLTCells' width='10%'> VPF</td><td class='GridLTCells' width='10%'> Principle</td></b><td class='GridLTCells' width='10%'>Interest</td><td class='GridLTCells' width='10%'>PCContrib</td><td class='GridLTCells' width='10%'>AdvAmount</td><td class='GridLTCells' width='10%'>PFW_sub_amt</td><td class='GridLTCells' width='10%'>cont_amt</td></tr></table>";
			for(i=0;i<=stype.length;i++){
			 var pensionno="";
			  if( getNodeValue(stype[i],'pensionno')!=null && getNodeValue(stype[i],'pensionno')!=""){
				  pensionno = getNodeValue(stype[i],'pensionno');
	 	 	 }else pensionno="";

			 	 var monthyear="";
			  if( getNodeValue(stype[i],'monthyear')!=null){
				  monthyear = getNodeValue(stype[i],'monthyear');
			  }else monthyear="";
			  document.forms[0].monthyear.value=monthyear;
	 	 	 var emoluments="";
	 	 	 if( getNodeValue(stype[i],'emoluments')!=null){
	 	 		emoluments= getNodeValue(stype[i],'emoluments');
		     }else emoluments="0.00";
		    	     
	 	 	 var cpf="";
	 	 	 if( getNodeValue(stype[i],'cpf')!=null){
	 	 		cpf= getNodeValue(stype[i],'cpf');
		     }else cpf="0.00";
		   
	 	 	 var vpf="";
	 	 	 if( getNodeValue(stype[i],'vpf')!=null){
	 	 		vpf= getNodeValue(stype[i],'vpf');
		     }else vpf="0.00";
	 	 	
	 	 	 var principle="";
	 	 	 if( getNodeValue(stype[i],'principle')!=null){
	 	 		principle= getNodeValue(stype[i],'principle');
		     }else principle="0.00";
		   
		     var interest="";
	 	 	 if( getNodeValue(stype[i],'interest')!=null){
	 	 		interest= getNodeValue(stype[i],'interest');
			     }else interest="0.00";
	 	 	    var region="";
		     if( getNodeValue(stype[i],'region')!=null){
		      region= getNodeValue(stype[i],'region');
		      }else region="";
	 	 	 document.forms[0].region.value=region;
		    var PCContrib="0.00";
		    if( getNodeValue(stype[i],'PCContrib')!=null){
	 	 		PCContrib= getNodeValue(stype[i],'PCContrib');
				 }else PCContrib="0.00";
		   var advAmount="0.00";
		   if( getNodeValue(stype[i],'advAmount')!=null){
			   advAmount= getNodeValue(stype[i],'advAmount');
			 }else advAmount="0.00";

			 var loan_sub_amt="0.00";
			 if( getNodeValue(stype[i],'loan_sub_amt')!=null){
				 loan_sub_amt= getNodeValue(stype[i],'loan_sub_amt');
				 }else loan_sub_amt="0.00";
			 var loan_cont_amt="0.00";
			 if( getNodeValue(stype[i],'loan_cont_amt')!=null){
				 loan_cont_amt= getNodeValue(stype[i],'loan_cont_amt');
				 }else loan_cont_amt="0.00";
			
		 divlist.innerHTML=divlist.innerHTML+"<table align='center'  width='100%'  cellpadding=1  cellspacing='0'  border='0' class='GridBorder'><tr class='GridHBg'><td  class='GridRowBg2' width='7%'>"+pensionno+"</td><td  class='GridRowBg2' width='11%'>"+monthyear+"</td><td  class='GridRowBg2' width='10%'>"+emoluments+" </td><td  class='GridRowBg2' width='10%'>"+cpf+"</td><td  class='GridRowBg2' width='10%'>"+vpf+"</td><td  class='GridRowBg2' width='10%'>"+principle+"</td><td  class='GridRowBg2' width='10%'>"+interest+"</td><td  class='GridRowBg2' width='10%'>"+PCContrib+"</td> <td  class='GridRowBg2' width='10%'>"+advAmount+"</td><td  class='GridRowBg2' width='10%'>"+loan_sub_amt+"</td><td  class='GridRowBg2' width='10%'>"+loan_cont_amt+"</td> <td class='GridRowBg2' width='5%' ><img alt='Add Adjustments to the Record' src='<%=basePath%>images/addIcon.gif' onclick='callFamily()'</td> </tr></table>";
		
	 	 	}
	 	
		   }
	 			 
		}
	 }
	 }
	 function saveAdjustments(){
		var emoluments1 = document.getElementById('emoluments1').value; 
		if(emoluments1==""){
			emoluments1="0.00";
		}
		var cpf1 = document.getElementById('cpf1').value;
		if(cpf1==""){
			cpf1="0.00";
		}
		var vpf1 = document.getElementById('vpf1').value;
		if(vpf1==""){
			vpf1="0.00";
		}
		var principle1 = document.getElementById('principle1').value;
		var interest1=	document.getElementById('interest1').value;
		if(principle1==""){
			principle1="0.00";
		}
		if(interest1==""){
			interest1="0.00";
		}
		var pccontrib1 = document.getElementById('pccontrib1').value;
		if(pccontrib1==""){
			pccontrib1="0.00";
		}
		var advAmount1 = document.getElementById('advAmount1').value;
		if(advAmount1==""){
			advAmount1="0.00";
		}
		var loan_sub_amt1= document.getElementById('loan_sub_amt1').value;
		if(loan_sub_amt1==""){
			loan_sub_amt1="0.00";
		}
		var loan_cont_amt1= document.getElementById('loan_cont_amt1').value;
		if(loan_cont_amt1==""){
			loan_cont_amt1="0.00";
		}
			var toYearID="";
		   	if(document.forms[0].select_year.value!='NO-SELECT'){
		  	  	year=document.forms[0].select_year.value;
				var splitYearID = year.split("-");
				yearID=splitYearID[0];
				
				if(yearID.substring(0,2)=='19' && yearID.substring(2,4)<'99'){
					toYearID=yearID.substring(0,2)+splitYearID[1]
				}else if(yearID.substring(0,2)=='20'){
					toYearID=yearID.substring(0,2)+splitYearID[1]
				}else if(yearID.substring(0,2)=='19' && yearID.substring(2,4)=='99'){
					toYearID=2000;
				}
			}
		    var	empserialNO=document.forms[0].empserialNO.value;
		    var cpfacno=document.forms[0].cpfacno.value;
		   	var select_month=document.forms[0].select_month.value;
		   	var transmonthyear=document.forms[0].monthyear.value;
		   	var region=document.forms[0].region.value;
            var url="<%=basePath%>pfinance.do?method=saveEmoluments&emoluments="+emoluments1+"&cpf="+cpf1+"&vpf="+vpf1+"&principle="+principle1+"&interest="+interest1+"&frm_year="+yearID+"&to_year="+toYearID+"&empserialNO="+empserialNO+"&select_month="+select_month+"&pccontrib="+pccontrib1+"&region="+region+"&transmonthyear="+transmonthyear+"&advAmount="+advAmount1+"&loan_sub_amt="+loan_sub_amt1+"&loan_cont_amt="+loan_cont_amt1+"&cpfacno="+cpfacno;
            
              createXMLHttpRequest();	
	   	 	xmlHttp.open("post", url, true);
			xmlHttp.onreadystatechange = updateCpfrecoveries;
			xmlHttp.send(null);
		//	document.forms[0].method="post";
	   //	document.forms[0].submit();
   }

	 function updateCpfrecoveries(){
		 if(xmlHttp.readyState ==4){
		  divFamily1.style.display="none";
		  document.getElementById('emoluments1').value="";
		  document.getElementById('cpf1').value="";
		  document.getElementById('vpf1').value="";
		  document.getElementById('principle1').value="";
		  document.getElementById('interest1').value="";
		  document.getElementById('pccontrib1').value="";
		  document.getElementById('advAmount1').value="";
		  document.getElementById('loan_sub_amt1').value="";
		  document.getElementById('loan_cont_amt').value="";
			if(xmlHttp.status == 200)
				{ var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
				  if(stype.length==0){
				 //	var pensionno = document.getElementById("pensionno");
				 	divlist.style.display="block"; 
				   	divlist.innerHTML="<table width='100%'><tr><td height='5%' width='10%' colspan='7' align='center'>No Records Found</td></tr></table>";  
				    }else{ divlist.style.display="block"; 
				   divlist.innerHTML="";
				    divlist.innerHTML="<table ><tr><td height='5%' width='14%' colspan='8' align='center' class='ScreenHeading'>Monthly CPFRecoveries</td></tr></table>";
				    divlist.innerHTML=divlist.innerHTML+"<table align='center' width='100%' cellpadding=2 class='GridBorder' cellspacing='0' border='0'><tr class='GridHBg' colspan='11'><b><td class='GridLTCells' width='9%' >PFID</td><td class='GridLTCells' width='10%'>MonthYear</td><td class='GridLTCells' width='10%'>Emolument</td><td class='GridLTCells' width='10%'> CPF</td><td class='GridLTCells' width='10%'> VPF</td><td class='GridLTCells' width='10%'> Principle</td></b><td class='GridLTCells' width='10%'>Interest</td><td class='GridLTCells' width='10%'>PCContrib</td><td class='GridLTCells' width='10%'>AdvAmount</td><td class='GridLTCells' width='10%'>PFW_sub_amt</td><td class='GridLTCells' width='10%'>cont_amt</td></tr></table>";
					for(i=0;i<=stype.length;i++){
					 var pensionno="";
					  if( getNodeValue(stype[i],'pensionno')!=null){
						  pensionno = getNodeValue(stype[i],'pensionno');
			 	 	 }else pensionno="";
				 	 	 var monthyear="";
					  if( getNodeValue(stype[i],'monthyear')!=null){
						  monthyear = getNodeValue(stype[i],'monthyear');
					 	 	 }else monthyear="";
			 	 	 var emoluments="";
			 	 	 if( getNodeValue(stype[i],'emoluments')!=null){
			 	 		emoluments= getNodeValue(stype[i],'emoluments');
				     }else emoluments="0.00";
				    var cpf="";
			 	 	 if( getNodeValue(stype[i],'cpf')!=null){
			 	 		cpf= getNodeValue(stype[i],'cpf');
				     }else cpf="0.00";
				   
			 	 	 var vpf="";
			 	 	 if( getNodeValue(stype[i],'vpf')!=null){
			 	 		vpf= getNodeValue(stype[i],'vpf');
				     }else vpf="0.00";
			 	 	
			 	 	 var principle="";
			 	 	 if( getNodeValue(stype[i],'principle')!=null){
			 	 		principle= getNodeValue(stype[i],'principle');
				     }else principle="0.00";
				   
				     var interest="";
			 	 	 if( getNodeValue(stype[i],'interest')!=null){
			 	 		interest= getNodeValue(stype[i],'interest');
					     }else interest="0.00";
			 	 	    var region="";
			 	 	  var PCContrib="";
				 	 	 if( getNodeValue(stype[i],'PCContrib')!=null){
				 	 		PCContrib= getNodeValue(stype[i],'PCContrib');
						     }else PCContrib="0.00";
				 	    var region="";
					     if( getNodeValue(stype[i],'region')!=null){
					      region= getNodeValue(stype[i],'region');
					      }else region="";
				 	 	 document.forms[0].region.value=region;
					    var PCContrib="0.00";
					    if( getNodeValue(stype[i],'PCContrib')!=null){
				 	 		PCContrib= getNodeValue(stype[i],'PCContrib');
							 }else PCContrib="0.00";
					   var advAmount="0.00";
					   if( getNodeValue(stype[i],'advAmount')!=null){
						   advAmount= getNodeValue(stype[i],'advAmount');
						 }else advAmount="0.00";
						 var loan_sub_amt="0.00";
						 if( getNodeValue(stype[i],'loan_sub_amt')!=null){
							 loan_sub_amt= getNodeValue(stype[i],'loan_sub_amt');
							 }else loan_sub_amt="0.00";
						 var loan_cont_amt="0.00";
						 if( getNodeValue(stype[i],'loan_cont_amt')!=null){
							 loan_cont_amt= getNodeValue(stype[i],'loan_cont_amt');
							 }else loan_cont_amt="0.00";
						 
						 divlist.innerHTML=divlist.innerHTML+"<table align='center'  width='100%'  cellpadding=1  cellspacing='0'  border='0'><tr ><td  class='Data' width='7%'>"+pensionno+"</td><td  class='Data' width='11%'>"+monthyear+"</td><td  class='Data' width='10%'>"+emoluments+" </td><td  class='Data' width='10%'>"+cpf+"</td><td  class='Data' width='10%'>"+vpf+"</td><td  class='Data' width='10%'>"+principle+"</td><td  class='Data' width='10%'>"+interest+"</td><td  class='Data' width='10%'>"+PCContrib+"</td> <td  class='Data' width='10%'>"+advAmount+"</td><td  class='Data' width='10%'>"+loan_sub_amt+"</td><td  class='Data' width='10%'>"+loan_cont_amt+"</td> <td class='Data' width='5%' ><a href='#' onClick=\"validateForm('"+pensionno+"','"+region+"');\" ><img border='0'alt='Add Adjustments to the Record' src='<%=basePath%>images/addIcon.gif' onclick='callFamily()'></td> </tr></table>";
				 if(i==(stype.length)-1){
			
				// showbuttons.innerHTML= showbuttons.innerHTML+"<table align='center'><td align='center'><input type='button' value='Add' class='btn' onclick='addtoProcess()'><input type='button' value='Reset' onclick='javascript:document.forms[0].reset()' class='btn'><input type='button' value='Cancel' onclick='javascript:history.back(-1)' class='btn'></td></tr></table>";
			 	 	}
			 	 	}
			 	
				   }
			 			 
				}
			 }
		}
--></script>
</HEAD>
<body class="BodyBackground" onload="javascript:frmload()">
<%
	String monthID = "", yearDescr = "", region = "", monthNM = "";

	 String[] year=null;
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
              if (request.getAttribute("year") != null) {
                year = (String[]) request.getAttribute("year");
            }
	if (request.getAttribute("pfidList") != null) {
		pfidList = (ArrayList) request.getAttribute("pfidList");
	}
%>
<form action="post">
		
		<%=ScreenUtilities.screenHeader("Adjustments in CPFRecoveries")%>
			

<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" class="tbborder">
	

	<tr>
		<td class="tableTextBold" align="right">Year:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td><select name='select_year' Style='width: 100px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
					<%for (int j = 0; j < year.length; j++) {%>
																			<option value='<%=year[j]%>'>
																				<%=year[j]%>
																			</option>
																			<%}%>
						
		</select></td>
	</tr>

	<tr>
		<td class="tableTextBold" align="right">Month:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td><select name="select_month" style="width: 100px" class="TextField">
			<Option Value='NO-SELECT'>Select One</Option>

			<%
				while (monthIterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) monthIterator.next();
					monthID = mapEntry.getKey().toString();
					monthNM = mapEntry.getValue().toString();
			%>

			<option value="<%=monthID%>"><%=monthNM%></option>
			<%
				}
			%>
		</select></td>
	</tr>
	<!--  <tr>
		<td class="tableTextBold" align="right">Region:<font color=red>*</font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><SELECT NAME="select_region" style="width: 120px"
			onChange="javascript:getAirports('airport');">
			<option value="NO-SELECT">[Select One]</option>
			<%
				while (regionIterator.hasNext()) {
					region = hashmap.get(regionIterator.next()).toString();
			%>
			<option value="<%=region%>"><%=region%></option>
			<%
				}
			%>
		</SELECT></td>
	</tr>
	<tr>
		<td class="tableTextBold" align="right">Aiport
		Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><SELECT NAME="select_airport" style="width: 120px">
			<option value='NO-SELECT' Selected>[Select One]</option>
		</SELECT></td>
	</tr>-->

	<tr>
		<td class="tableTextBold" align="right">Employee
		Name:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="text" name="empName" readonly="readonly"
			class="TextField"> <img
			src="<%=basePath%>images/search1.gif"
			onclick="popupWindow('<%=basePath%>reportservlet.do?method=loadPFIDInformation','AAI');"
			alt="Click The Icon to Select EmployeeName" /></td>

	</tr>

	<tr>
		<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>

	<input type="hidden" name="empserialNO" readonly="true" tabindex="3">
	<input type="hidden" name="cpfacno" readonly="true" tabindex="3">

	<tr>
		<td align="right"><input type="button" class="btn" name="Submit"
			value="Submit" onclick="javascript:validateForm()"> <input
			type="button" class="btn" name="Reset" value="Reset"
			onclick="javascript:resetReportParams()"> <input
			type="button" class="btn" name="Submit" value="Cancel"
			onclick="javascript:history.back(-1)"></td>
	</tr>
	<tr>
		<td align="center"></td>
	</tr>


<tr>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>

<tr>
	<td height="25%">
	<%
		int index = 0;
		if (request.getAttribute("cpfRecoveryList") != null) {
			int totalData = 0;

			ArrayList dataList = new ArrayList();
			dataList = (ArrayList) request.getAttribute("cpfRecoveryList");
			if (dataList.size() == 0) {
	%>
	
<tr>

	<td>
	<table align="center" id="norec">
		<tr>
			<br>
			<td><b> No Records Found </b></td>
		</tr>
	</table>
	</td>
</tr>

<%
	} else if (dataList.size() != 0) {
%>
<tr>

	<td height="25%">

	<table align="center" width="100%" cellpadding=2 class="GridBorder"
		cellspacing="0" border="0">

		<tr class="GridHBg">
			<td class="GridLTCells">PFID</td>
			<td class="GridLTCells">MonthYear</td>
			<td class="GridLTCells">Emoluments</td>
			<td class="GridLTCells">CPF</td>
			<td class="GridLTCells">EMPVPF</td>
			<td class="GridLTCells">CPRINCIPAL</td>
			<td class="GridLTCells">INTEREST</td>
			<td class="GridLTCells">PCContrib</td>
			<td class="GridLTCells">&nbsp;<b><img
				alt="Add Adjustments to the Record"
				src="<%=basePath%>images/addIcon.gif"
				onclick="callFamily()" ></b></td>
		</tr>
		<%
			for (int i = 0; i < dataList.size(); i++) {
						EmployeePensionCardInfo cardInfo = (EmployeePensionCardInfo) dataList
								.get(i);
						if (cardInfo.getEmoluments() != null) {
		%>
		<tr>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getPensionNo()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getMonthyear()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getEmoluments()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getEmppfstatury()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getEmpvpf()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getPrincipal()%></td>
			<td class="GridRowBg1" width="15%"><%=cardInfo.getInterest()%></td>
		</tr>
		<%
			}
		%>

		<%
			}
				}
			}
		%>
	</table>

	<div id="divlist" align="left" class="containdiv2"></div>

	<div id="divFamily1">
	<table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input type="text" size="6" name="emoluments1"
				id="emoluments1" maxlength="50" tabindex="27" onkeypress="numsDotOnly()" ></td>
			<td><input type="text" size="6" name="cpf1" id="cpf1"
				tabindex="28" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="vpf1" id="vpf1"
				tabindex="29" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="principle1"
				id="principle1" tabindex="30" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="interest" id="interest1"
				tabindex="31" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="pccontrib"
				id="pccontrib1" tabindex="31" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="advAmount"
				id="advAmount1" tabindex="31" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="loan_sub_amt"
				id="loan_sub_amt1" tabindex="31" onkeypress="numsDotOnly()"></td>
			<td><input type="text" size="6" name="loan_cont_amt"
				id="loan_cont_amt1" tabindex="31" onkeypress="numsDotOnly()"></td>
			<td><input type="button" class="btn" name="Submit" value="Save"
				onclick="saveAdjustments();"></td>
		</tr>
	</table>
	</div>

	<input type="hidden" name="region" /> <input type="hidden"
		name="monthyear" />

<%=ScreenUtilities.screenFooter()%>
</form>
<div id="process"
	style="position: fixed; width: auto; height: 35%; top: 200px; right: 0; bottom: 100px; left: 10em;"
	align="center"><img
	src="<%=basePath%>images/Indicator.gif" border="no"
	align="middle" /> <SPAN class="tableTextBold">Processing.......</SPAN></div>

</body>
</html>
