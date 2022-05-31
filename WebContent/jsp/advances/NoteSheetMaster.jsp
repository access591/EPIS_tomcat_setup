<!--
/*
  * File       : NoteSheetMaster.jsp
  * Date       : 15/10/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
%>
<%
    String reg="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	System.out.println(".............keys................"+keys);

	Iterator it = keys.iterator();
	

  %>
<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
	<head>
		<base href="<%=basePath%>" />

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="this is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />		
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
	
		<script type="text/javascript">		
		var detailArray = new Array();
		var srno,nomineename,nomineeaddress,nomineeDOB,nomineerelation,gardianname,gardianaddress,totalshare,nomineeflag;
		var i;
		var emplshare=0,emplrshare=0,pensioncontribution=0,adhocamt=0;
		
		//var ex=/^[0-9]+$/;
		function hide(focusFlag) {
			
		   	if(focusFlag!='true'){
		   		document.forms[0].pfid.focus();
		   	}else{
		   	    document.forms[0].seperationreason.focus();
		   	}		  
		}					
		function popupWindow(windowname){		
			var pensionNo;
			pensionNo=document.forms[0].pfid.value;
			var LeftPosition = (screen.width) ? (screen.width-850)/2 : 0;
			var TopPosition = (screen.height) ? (screen.height-500)/2 : 0;
			if (! window.focus)return true;
			var href;
	   		href="loadAdvance.do?method=loadLookupPFID&frm_pensionno="+pensionNo;
			progress=window.open(href, "NOTESHEETPFID", 'width=850,height=500,top='+TopPosition+',left='+LeftPosition+',statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
		}
		function retrivedPersonalInfo(){
			var pensionNo,url,flag,trust;
			pensionNo=document.forms[0].pfid.value;
			if(pensionNo==''){
				if(document.forms[0].employeeName.value=='' && document.forms[0].employeeNo.value==''){
					alert('PF ID should be enter value');
					document.forms[0].pfid.focus();
					return false;
				}
			}
			
			trust=document.forms[0].trust.value;
			//alert('-----trust-------'+trust);
			
				flag="notesheet";
				
	   			url="<%=basePathWoView%>loadAdvance.do?method=lookupPFID&frm_pensionno="+pensionNo+"&goFlag="+flag+"&frm_trust="+trust;
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
				
		}		
	function test(pfids,empcode,empname,designation,fname,department,dojaai,dojcpf,dob,emolument,pensionNo,region,emppfstatuary,cpfaccno,airportcode){	   
	   	    document.forms[0].pfid.value=pfids;
			//document.forms[0].employeeNo.value=empcode;
		//	document.forms[0].employeeName.value=empname;
		  //  document.forms[0].designation.value=designation;
		  //  document.forms[0].fhName.value=fname;
		    document.forms[0].pensionNo.value=pensionNo;
		      document.forms[0].go.focus();
	 	//	document.forms[0].cpfaccno.value=cpfaccno;	
		  //  document.forms[0].dateOfJoining.value=dojaai;	
		 //   document.forms[0].dateOfBirth.value=dob;		
		   //  document.forms[0].station.value=airportcode;		
		   // document.forms[0].region.value=region;		
		    
	}		
	function frmPrsnlReset(){
			 hide('false');
			url="<%=basePathWoView%>loadNoteSheet.do?method=loadNoteSheet";
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
	} 		
	function submitData(){
		 	var ex=/^[0-9-.]+$/;
		 		
			var bool=false,advanceDtl="",pfwPurpose="",advPurpose="",pensionNo="",totalInstall="";
			var ndob=document.getElementsByName("nomineeDOB");
			for(var i=0;i<(ndob.length);i++){
				if(ndob[i].value!='')
				{
					if (!convert_date(ndob[i]))
				    {
						ndob[i].focus();
						return false;
					}
				}			
			}		
			if(document.forms[0].pfid.value==''){
				alert('PF ID Is Required');
				document.forms[0].pfid.focus();
				return false;
			}
								
			//if (!convert_date(document.forms[0].dateOfBirth))
			///{
		//		document.forms[0].dateOfBirth.focus();
		//		return false;
		//	}			
			if(document.forms[0].seperationreason.value!=''){
			    if(document.forms[0].seperationdate.value==''){
			        alert('Seperation Date Is Required');
					document.forms[0].seperationdate.focus();
					return false;
			    }
			}
			if(document.forms[0].seperationdate.value!=''){
			    if(document.forms[0].seperationreason.value==''){
			        alert('Seperation Reason Is Required');
					document.forms[0].seperationreason.focus();
					return false;
			    }
			}			
			if (!convert_date(document.forms[0].seperationdate))
			{
				document.forms[0].seperationdate.focus();
				return false;
			}
			if(document.forms[0].amtadmtdate.value==''){
				alert('Amount Admitted for Payment by CPF(Hqrs)with interest up to Is Required ');
				document.forms[0].amtadmtdate.focus();
				return false;
			}
			if (!convert_date(document.forms[0].amtadmtdate))
			{
				document.forms[0].amtadmtdate.focus();
				return false;
			}
			if(document.forms[0].emplshare.value==''){
				alert('Employee Share (Subscription)(A) Is Required');
				document.forms[0].emplshare.focus();
				return false;
			}
			if (!ex.test(document.forms[0].emplshare.value) && document.forms[0].emplshare.value!="")
		    {
				alert("Employee Share (Subscription)(A) shoud be Numeric");
				document.forms[0].emplshare.select();
				return false;
		    }
		    
		    
			
		    
			if(document.forms[0].emplrshare.value==''){
				alert('Employer Share ( Contribution)(B) Is Required');
				document.forms[0].emplrshare.focus();
				return false;
			}
			if (!ex.test(document.forms[0].emplrshare.value) && document.forms[0].emplrshare.value!="")
		    {
				alert("Employer Share (Contribution)(B) shoud be Numeric");
				document.forms[0].emplrshare.select();
				return false;
		    }
			if(document.forms[0].pensioncontribution.value==''){
				alert('Less pension contribution(C) Is Required');
				document.forms[0].pensioncontribution.focus();
				return false;
			}			
			if (!ex.test(document.forms[0].pensioncontribution.value) && document.forms[0].pensioncontribution.value!="")
		    {
				alert("Less pension contribution(C) shoud be Numeric");
				document.forms[0].pensioncontribution.select();
				return false;
		    }
			
			if(document.forms[0].sanctiondt.value!=''){
				if (!convert_date(document.forms[0].sanctiondt))
				{
				document.forms[0].sanctiondt.focus();
				return false;
				}
			}
			if (!convert_date(document.forms[0].sanctiondt))
			{
				document.forms[0].sanctiondt.focus();
				return false;
			}		
			if(document.forms[0].paymentdt.value!=''){
				if (!convert_date(document.forms[0].paymentdt))
				{
				document.forms[0].paymentdt.focus();
				return false;
				}	
			}
						
			/*if((document.forms[0].sanctiondt.value!='') && (document.forms[0].paymentdt.value!='')){
				if(compareDates(document.getElementById('paymentdt').value,document.getElementById('sanctiondt').value)=='larger'){
					alert("Sanction Date should be Greater than or Equal to Payment Date");		
					document.forms[0].sanctiondt.focus();
					return false;
				}		
			}*/
			
				
			var station;
			
			if((document.forms[0].region.value=='RAUSAP') ||  (document.forms[0].trust.value=='IAAI')){			
			station=document.forms[0].station[document.forms[0].station.options.selectedIndex].text;	
			}else{
			station=document.forms[0].station.value;			
			}
			
			//alert('-----station------'+station);
				
			var url ="<%=basePathWoView%>loadNoteSheet.do?method=saveNoteSheet&frm_station="+station;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();		
   }
    var count1 =0,slno=0;
   
    
   function callNominee(){         
	  	 divNominee2.innerHTML+=divNominee1.innerHTML;
	  	 var i=document.getElementsByName("nomineename");
	  	 i[i.length-1].value="";	  	 
	  	 var j=document.getElementsByName("nomineeaddress");
	  	 j[j.length-1].value="";	  	 	 
	  		  	
	  	 var k=document.getElementsByName("nomineeDOB");	  	 
	  	 count1 = k.length-1;	    
	  	 k[k.length-1].value="";
	  	 k[k.length-1].id ="nomineeDOB"+count1;	  	  	  	 
	  	 k = document.getElementsByName("cal1");	  	  	
	  	 k[k.length-1].onclick=function(){call_calender("forms[0].nomineeDOB"+count1)};	 	 
	 	 
	  	var l=document.getElementsByName("nomineerelation");	  
	  	l[l.length-1].value="";	  	
	  	var m=document.getElementsByName("gardianname");
	    m[m.length-1].value="";
	    var g=document.getElementsByName("gardianaddress");
	    g[g.length-1].value="";
	  	var n=document.getElementsByName("totalshare");
	  	n[n.length-1].value="";	  	 	
	  	
	  	var s=document.getElementsByName("serialNo");	  
	  	s[s.length-1].value="";	 
	  	
  		var f=document.getElementsByName("nomineeflag");	  
	  	f[f.length-1].value="";	  
	          
  }  	 
  	 
  function call_calender(dobValue){       	
  	  show_calendar(dobValue);
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
 
 function calNetContribution(){ 
 
 //alert('----calNetContribution-----');
   if(document.forms[0].seperationreason.value!='VRS'){
   
    if(document.forms[0].emplshare.value!="" || document.forms[0].emplrshare.value!="" || document.forms[0].pensioncontribution.value!=""){
   
    if(document.forms[0].emplshare.value!=""){    
    emplshare=parseInt(document.forms[0].emplshare.value);
    }else{
    emplshare=0;
    }        
    if(document.forms[0].emplrshare.value!=""){    
    emplrshare=parseInt(document.forms[0].emplrshare.value);
    }else{
    emplrshare=0;
    }       
    if(document.forms[0].pensioncontribution.value!=""){    
    pensioncontribution=parseInt(document.forms[0].pensioncontribution.value);
    }else{
    pensioncontribution=0;
    }     
    
    document.forms[0].netcontribution.value=(emplshare+emplrshare)-pensioncontribution;   
     
    }
    
    }else{     
       document.forms[0].adhocamt.focus();       
    }
    
 }	
 
 function calNetContributionAmt(){ 
 
 
    if(document.forms[0].emplshare.value!="" || document.forms[0].emplrshare.value!="" || document.forms[0].pensioncontribution.value!="" || document.forms[0].adhocamt.value!=""){
   
    if(document.forms[0].emplshare.value!=""){    
    emplshare=parseInt(document.forms[0].emplshare.value);
    }else{
    emplshare=0;
    }        
    if(document.forms[0].emplrshare.value!=""){    
    emplrshare=parseInt(document.forms[0].emplrshare.value);
    }else{
    emplrshare=0;
    }       
    if(document.forms[0].pensioncontribution.value!=""){    
    pensioncontribution=parseInt(document.forms[0].pensioncontribution.value);
    }else{
    pensioncontribution=0;
    }     
    
    if(document.forms[0].adhocamt.value!=""){
    adhocamt=parseInt(document.forms[0].adhocamt.value);
    }else{
    adhocamt=0;
    }      
    
    if(adhocamt==0){  
    document.forms[0].netcontribution.value=(emplshare+emplrshare)-pensioncontribution;   
    }else{
      document.forms[0].netcontribution.value=((emplshare+emplrshare)-pensioncontribution)-adhocamt;    
    }
   
    }
    
   
      
 }			
 
 function displayNominee(){
     if(document.forms[0].seperationreason.value=='Death'){
     	document.getElementById("nomineelabel").style.display="block";
     	document.getElementById("dispnominee").style.display="block";
     	//document.getElementById("remarks").style.display="none";
     }else{
        document.getElementById("nomineelabel").style.display="none";
     	document.getElementById("dispnominee").style.display="none";
     }
     
     if(document.forms[0].seperationreason.value=='Resignation'){
     	document.getElementById("resignationdata").style.display="block";
     	document.getElementById("remarks").style.display="none";
     }else{
        document.getElementById("resignationdata").style.display="none";
     	document.getElementById("remarks").style.display="block";
     }
     
      if(document.forms[0].seperationreason.value=='VRS'){                      
           
           document.getElementById("adhocamt").style.display="block";
           document.getElementById("adhocamtlabel").style.display="block";
           document.getElementById("adhocnetlabel").style.display="block";
           document.getElementById("netcontlabel").style.display="none";       
           
      }else{         
       	 
       	  document.getElementById("adhocamt").style.display="none";
       	  document.getElementById("adhocamtlabel").style.display="none";
       	  document.getElementById("adhocnetlabel").style.display="none";
          document.getElementById("netcontlabel").style.display="block";      
       	
      }
      
      if(document.forms[0].seperationreason.value=='VRS'){   
       calNetContributionAmt();
      }else{
      calNetContribution();
      }
 }
 
 function deleteNominee(){
   alert('-----deleteNominee()------'+deleteNominee());
 }
 
 function getStations(){ 
   
      var url = "/editNoteSheet.do?method=loadStations";
	
	  var index=document.forms[0].region.selectedIndex.value;
       var selectedIndex=document.forms[0].region.options.selectedIndex;
	   var searchregion=document.forms[0].region[selectedIndex].value;  
	 
	  
    var ajaxRequest = new Ajax.Request(url, {
	    method:       'get', 
	    parameters: {region: searchregion},
	    asynchronous: true,
	    onSuccess:   showResponse
	});    

 
 }
 
 function getNodeValue(obj,tag){    
  if(obj.getElementsByTagName(tag)[0].firstChild) {
		return unescape(obj.getElementsByTagName(tag)[0].firstChild.nodeValue);
	}else{
	  return "";
	}
 }
  function showResponse(xmlHttpRequest) {  	 
	 var obj1 = document.getElementById("station");
	obj1.options.length = 0;
	 
	 
	  var xm = xmlHttpRequest.responseXML;    
     var stype = xmlHttpRequest.responseXML.getElementsByTagName('Detail');
     
   
       if(stype.length>=1){
	       for(var i=0;i<stype.length;i++){	      
	         var val = getNodeValue(stype[i],'Station');	      
	 		var nm = getNodeValue(stype[i],'Station'); 	 		
	 		obj1.options[obj1.options.length] = new Option(nm,val);
	       }
       }else{
       obj1.options[obj1.options.length] = new Option("---No Stations--","");
       }
	  
}

function deleteNomineeDetils(cpfaccno,nomineeName,rowcolumn,rowid)
{  
  var rowcolumn=rowcolumn;
  document.forms[0].deletionStatus[rowid].value="D";
 
  var deleterow = document.getElementById(rowcolumn);
  document.getElementById(rowcolumn).style.display = 'none'; 	    
} 

 </script>

	</head>

	
<body class="bodybackground" onload="hide('<%=request.getAttribute("focusFlag")%>');">
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
		<%=ScreenUtilities.screenHeader("Final Settlement Master Form")%>
		<table width="750" border="0" cellspacing="3" cellpadding="0">
					
									  
										<tr>
											<td class="tableTextBold">
												PF ID:&nbsp;
											</td>
											<td>
												<html:hidden property="pensionNo" />

												

												<html:text styleClass="TextField" property="pfid" maxlength="15" tabindex="1" />


												<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('AdvanceFormInfo');" alt="Click The Icon to Select Details" />
												<input type="button" class="butt" value="GO" name="go" onclick="retrivedPersonalInfo();" tabindex="3" />
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Code:&nbsp;
											</td>
											<td>
												<html:text property="employeeNo" styleClass="TextField" tabindex="4" readonly="true" />
											</td>
											<td class="tableTextBold">
												Old CPF A/C No:&nbsp;
											</td>
											<td>
												<html:text property="cpfaccno"  styleClass="TextField" tabindex="5" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Name:&nbsp;
											</td>
											<td>
												<html:text property="employeeName" styleClass="TextField" tabindex="6" readonly="true" />
											</td>
											<td class="tableTextBold">
												Father's Name:&nbsp;
											</td>
											<td>
												<html:text property="fhName" styleClass="TextField" tabindex="7" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Date of Birth:&nbsp;
											</td>
											<td>
												<html:text property="dateOfBirth" styleClass="TextField" tabindex="8" readonly="true" />
											</td>
											<td class="tableTextBold">
												Date of Joining in AAI:&nbsp;
											</td>
											<td>
												<html:text property="dateOfJoining" styleClass="TextField" tabindex="9" readonly="true" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Designation:&nbsp;
											</td>
											<td>
												<html:hidden property="empmailid" />
												<html:text property="designation" styleClass="TextField" tabindex="10"/>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Reason For Seperation:&nbsp;
											</td>
											<td>
												<html:select property="seperationreason" styleClass="TextField" style="width:119px" tabindex="11" onchange="displayNominee()">
													<html:option value="">Select One</html:option>
													<html:option value="Retirement">Retirement</html:option>
													<html:option value="VRS">VRS</html:option>
													<html:option value="Death">Death</html:option>
													<html:option value="Resignation">Resignation</html:option>
													<html:option value="Termination">Termination</html:option>
													<html:option value="EarlyPensions">Option for Early Pensions</html:option>
													<html:option value="Others">Others</html:option>
												</html:select>
											</td>
											<td class="tableTextBold">
												Date Of Seperation:&nbsp;
											</td>
											<td>
												<html:text property="seperationdate" styleClass="TextField" maxlength="12" tabindex="12" />
												<a href="javascript:show_calendar('forms[0].seperationdate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>

											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Region:&nbsp;
											</td>
											
											<td>		
								          
								             
								             <select name="region" class="TextField" style="width:119px" onchange="getStations();">
								             
								             <%
									while(it.hasNext()){			
									 boolean exist = false;
									  reg=hashmap.get(it.next()).toString();  										
									  %>		

									   <logic:equal property="region" name="advanceForm" value="<%=reg%>">
									   <%
										  exist = true; 
									   %>
									    </logic:equal>
									  <%
										 									  
									  	if (exist) {
									  
								   %>
								     <option value="<bean:write property="region" name="advanceForm"/>" <% out.println("selected");%>><bean:write property="region" name="advanceForm"/></option>								
								   <% }else{
								     
								   %>
								     <option value="<%=reg%>"><%=reg%></option>
								
								<%} }%>	
								             
								             </select>
								           
								          
								           </td>
								           
											<td class="tableTextBold">
												Station:&nbsp;
												<logic:present name="airportsList">
													<bean:write name="advanceForm" property="station"/> /	
													</logic:present>
											</td>										
										
										  <td  class="tableTextBold" nowrap="nowrap">									 
											
											
											
											<logic:present name="airportsList">
											
													 
										   
										   <html:select property="station" name="advanceForm" styleClass="TextField"  style="width:119px" tabindex="11">
										    <logic:iterate name="airportsList" id="st">								      
										      	
										      	<bean:define id="station" property="station" name="st"></bean:define>
												<html:option value="<%=String.valueOf(station)%>">
												<logic:equal name="st" property="station" value="KOLKATA PROJ">
												 KOLKATA PROJECT
												</logic:equal>
												
												<logic:notEqual name="st" property="station" value="KOLKATA PROJ">
												<bean:write name="st" property="station"/>
												</logic:notEqual>
												
												
												</html:option>																								
																								
										    </logic:iterate>
										   </html:select>
										    </logic:present>
										     										    										     
											</td>					
										</tr>

										<tr>
											<td class="tableTextBold">
												Amount Admitted for Payment by
												<br>
												CPF(Hqrs)with interest up to <font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="amtadmtdate" styleClass="TextField" maxlength="12" tabindex="15" />
												<a href="javascript:show_calendar('forms[0].amtadmtdate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Employee Share (Subscription)(A)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="emplshare"  styleClass="TextField" maxlength="13" tabindex="16" />
											</td>
											<td class="tableTextBold">
												Employer Share ( Contribution)(B)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											<td>
												<html:text property="emplrshare" styleClass="TextField" maxlength="13" tabindex="17" />
											</td>
										</tr>
										<tr>
											<td class="tableTextBold">
												Less pension contribution(C)<font color="red">&nbsp;*</font>:&nbsp;
											</td>
											
											<td>
												<html:text property="pensioncontribution" styleClass="TextField" maxlength="13" tabindex="18" onblur="calNetContribution()" />
											</td>
										
											<td class="tableTextBold" id="adhocamtlabel"  style="display:none;">
												Adhoc Amount already paid (D):&nbsp;
											</td>
											<td id="adhocamt" style="display:none;">
												<html:text property="adhocamt" styleClass="TextField" tabindex="19"  maxlength="13"  onblur="calNetContributionAmt()" />
											</td>
											
										</tr>
										<tr id="netcontr">
										    <td class="tableTextBold" id="netcontlabel">
												Net contribution(A+B-C):&nbsp;
											</td>
											<td class="tableTextBold" id="adhocnetlabel" style="display: none;">
												Net contribution(A+B-C-D):&nbsp;
											</td>
											<td>
												<html:text property="netcontribution" styleClass="TextField" maxlength="13" tabindex="20" />
											</td>
											<td colspan="2">&nbsp;</td>										
										</tr>
										<tr>											
											<td class="tableTextBold">
												Sanction Date:&nbsp;
											</td>
											<td>
												<html:text property="sanctiondt" styleClass="TextField" tabindex="21" maxlength="12" />
												<a href="javascript:show_calendar('forms[0].sanctiondt');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
											<td class="tableTextBold">
												Payment Date:&nbsp;
											</td>
											<td>
												<html:text property="paymentdt" styleClass="TextField" tabindex="22" maxlength="12" />
												<a href="javascript:show_calendar('forms[0].paymentdt');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
										</tr>
									
										<tr id="nomineelabel" style="display:none">
										<td colspan="4" >
											<table align="center" border="0">
												<tr>
													<td class="label" align="center">
														Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Address&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Dateof Birth&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Relation with Member&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Name of Guardian&nbsp;&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Address of Guardian&nbsp;&nbsp;&nbsp;
													</td>
													<td class="label" align="center">
														Total Share
														<BR>
														payable in %
													</td>
													<td>
													&nbsp;<b><img alt="" src="<%=basePath%>/images/addIcon.gif" onclick="callNominee();" ></b>
													</td>
												</tr>	
												<%int size = 0;%>
												<logic:present name="nomineeList">										
												<logic:iterate name="nomineeList" id="nominee" indexId="slno" >										
												<%size++;%>
											
												<tr id="name<%=slno.intValue()%>">
			 												<td>
																<html:text property="nomineename" name="nominee" maxlength="50" size="18" tabindex="24"></html:text>
																&nbsp;
																<html:hidden property="serialNo" name="nominee" />
																<html:hidden property="nomineeflag" name="nominee" />
																<input type="hidden" name="deletionStatus" id="deletionStatus<%=slno.intValue()%>" value="">
																<input type="hidden" name="empOldNname" value="">
															</td>

															<td align="center" valign="top">
																&nbsp;
																<html:text property="nomineeaddress" name="nominee" size="16" maxlength="150" tabindex="25"></html:text>
															</td>
															<td align="center" valign="top" nowrap="nowrap">
																&nbsp;&nbsp;

																<input type="text" name="nomineeDOB" tabindex="26" id="nomineeDOB<%=slno.intValue()%>" value='<bean:write property="nomineeDOB" name="nominee" />' size="12" maxlength="12" />
																<img src="<%=basePath%>/images/calendar.gif" border="no" name="cal1" onclick="javascript:call_calender('forms[0].nomineeDOB<%=slno.intValue()%>')" alt="" />

															</td>
															<td align="center" valign="top">
																&nbsp;
																<html:select property="nomineerelation" name="nominee" tabindex="27">
																	<html:option value="">
																[Select One]
															</html:option>
																	<html:option value='SPOUSE'>
																SPOUSE
															</html:option>
																	<html:option value='SON'>
																SON
															</html:option>
																	<html:option value='DAUGHTER'>
																DAUGHTER
															</html:option>
																	<html:option value='MOTHER'>
																MOTHER
															</html:option>
																	<html:option value='FATHER'>
																FATHER
															</html:option>
																	<html:option value='SONS WIDOW'>
																SON'S WIDOW
															</html:option>
																	<html:option value='WIDOWS DAUGHTER'>
																WIDOW'S DAUGHTER
															</html:option>
																	<html:option value='MOTHER-IN-LOW'>
																MOTHER-IN-LOW
															</html:option>
																	<html:option value='FATHER-IN-LOW'>
																FATHER-IN-LOW
															</html:option>

																</html:select>

															</td>
															<td align="center" valign="top">
																&nbsp;&nbsp;&nbsp;&nbsp;
																<html:text property="gardianname" name="nominee" size="16" maxlength="50"  tabindex="28"></html:text>

															</td>
															<td align="center" valign="top">
																&nbsp;&nbsp;
																<html:text property="gardianaddress" name="nominee" size="16" maxlength="150" tabindex="29"></html:text>
																&nbsp;
															</td>
															<td align="center" valign="top">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<html:text property="totalshare" name="nominee" size="5" tabindex="30" maxlength="20" onkeypress="numsDotOnly()"></html:text>
															</td>
															<td align="center" valign="top">
														&nbsp;<b><img alt="Delete" src="<%=basePath%>/images/cancelIcon.gif" onclick="deleteNomineeDetils('<bean:write property="cpfaccno" name="advanceForm"/>','<bean:write property="nomineename" name="nominee"/>','name<%=slno.intValue()%>','<%=slno.intValue()%>');" tabindex="27"></b>
														</td>

														</tr>
														
													</logic:iterate>
												</logic:present>
												
											</table>
											</td>
											</tr>
											<tr id="dispnominee" style="display:none">
											<td  colspan="4" align="center">
											<div id="divNominee1">
												<table border="0" align="center">
													<tr>
														<td align="center" valign="top">															
															<html:text property="nomineename" maxlength="50" size="18" tabindex="31"></html:text>
															&nbsp;
															<html:hidden property="serialNo" />
															<html:hidden property="nomineeflag" />
															<input type="hidden" name="deletionStatus" id="deletionStatus<%=size%>" value="">
															<input type="hidden" name="empOldNname" value="">
														</td>

														<td align="center" valign="top">
															&nbsp;
															<html:text property="nomineeaddress" size="16" maxlength="150" tabindex="32"></html:text>
														</td>
														<td align="center" valign="top" nowrap="nowrap">
															&nbsp;&nbsp;
															<input type="text" name="nomineeDOB" id="nomineeDOB<%=size%>" size="12" maxlength="12" tabindex="33" />
															<img src="<%=basePath%>/images/calendar.gif" border="no" name="cal1" onclick="javascript:call_calender('forms[0].nomineeDOB<%=size%>')" alt="" />

														</td>
														<td align="center" valign="top">
															&nbsp;
															<html:select property="nomineerelation" tabindex="34">
																<html:option value="">
																[Select One]
															</html:option>
																<html:option value='SPOUSE'>
																SPOUSE
															</html:option>
																<html:option value='SON'>
																SON
															</html:option>
																<html:option value='DAUGHTER'>
																DAUGHTER
															</html:option>
																<html:option value='MOTHER'>
																MOTHER
															</html:option>
																<html:option value='FATHER'>
																FATHER
															</html:option>
																<html:option value='SONS WIDOW'>
																SON'S WIDOW
															</html:option>
																<html:option value='WIDOWS DAUGHTER'>
																WIDOW'S DAUGHTER
															</html:option>
																<html:option value='MOTHER-IN-LOW'>
																MOTHER-IN-LOW
															</html:option>
																<html:option value='FATHER-IN-LOW'>
																FATHER-IN-LOW
															</html:option>

															</html:select>

														</td>
														<td align="center" valign="top">
															&nbsp;&nbsp;&nbsp;&nbsp;
															<html:text property="gardianname" size="16" maxlength="50" tabindex="35"></html:text>

														</td>
														<td align="center" valign="top">
															&nbsp;&nbsp;
															<html:text property="gardianaddress" size="16" maxlength="150" tabindex="36"></html:text>
															&nbsp;
														</td>
														<td align="center" valign="top">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<html:text property="totalshare" size="5" onkeypress="numsDotOnly()" maxlength="20" tabindex="37"></html:text>
														</td>
														<td align="center" valign="top">
															&nbsp;&nbsp;&nbsp;
														</td>

													</tr>
												</table>
											</div>
											
											<div id="divNominee2">
											</div>
										</td>
										</tr>
										<tr id="resignationdata" style="display:none">
										    <td class="tableTextBold">
												Payment to be made in favor of  : &nbsp;
											</td>
											<td>
												<html:text property="seperationfavour" styleClass="TextField" tabindex="38" maxlength="200" />
											</td>
											<td class="tableTextBold">
												Remarks  : &nbsp;
											</td>
											<td>
												<html:textarea property="seperationremarks" styleClass="TextField" tabindex="39" rows="5" cols="27" />
											</td>
										</tr>
										
										<tr id="remarks">
											<td class="tableTextBold">
												Remarks : &nbsp;
											</td>
											<td>
												<html:text property="remarks" styleClass="TextField" tabindex="40" maxlength="200" />
											</td>
										
										<td class="tableTextBold">
												Trust:&nbsp;
											</td>
											<td>												
												<html:select  property="trust"  styleClass="TextField" style="width:119px"  tabindex="23" >
												<html:option value="NAA">NAA</html:option>
												<html:option value="AAI">AAI</html:option>
												<html:option value="IAAI">IAAI</html:option>
												</html:select>
											</td>
										</tr>

									</table>
									<table align="center">

										<tr>
											<td align="left" id="submit">
												<input type="button" class="btn" value="Submit" onclick="submitData();" />
											</td>
											<td align="right">

												<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" />
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" />
											</td>
										</tr>


									</table>
							
<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

