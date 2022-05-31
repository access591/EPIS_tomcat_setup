<!--
/*
  * File       : NoteSheetApplicationForm.jsp
  * Date       : 12/04/2010
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
		<script type="text/javascript" src="<%=basePath%>scripts/prototype.js"></script>
	
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
						
				flag="finalsettlement";
				
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
			url="<%=basePathWoView%>loadNoteSheet.do?method=loadFinalSettlement";
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
	} 		
	function submitData(){
		 	var ex=/^[0-9-.]+$/;
		 		
			var bool=false,advanceDtl="",pfwPurpose="",advPurpose="",pensionNo="",totalInstall="";
			var seperationreason=document.forms[0].seperationreason.value;
			var pensionNo=document.forms[0].pensionNo.value;
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
		
		if(document.forms[0].seperationreason.value=='Resignation'){
			    if(document.forms[0].resignationreason.value==''){
			        alert('Please select Reason for Resignation');
					document.forms[0].resignationreason.focus();
					return false;
			    }
			}
			
		 
			if(document.forms[0].seperationreason.value=='VRS'){
			    if(document.forms[0].resignationreason.value==''){
			        alert('Please select Reason for VRS');
					document.forms[0].resignationreason.focus();
					return false;
			    }
			}
				
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
							
			
			if(document.forms[0].appointmentdate.value!=''){
				if (!convert_date(document.forms[0].appointmentdate))
				{
					document.forms[0].appointmentdate.focus();
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
			
				
			var station,region;
			
			if((document.forms[0].region.value=='RAUSAP') ||  (document.forms[0].trust.value=='IAAI')){			
			station=document.forms[0].station[document.forms[0].station.options.selectedIndex].text;	
			}else{
			station=document.forms[0].station.value;			
			}
						
			region=document.forms[0].region.value;	
			if((document.forms[0].seperationreason.value=='Retirement') || (document.forms[0].seperationreason.value=='VRS') || (document.forms[0].seperationreason.value=='Death') || (document.forms[0].seperationreason.value=='Others')|| (document.forms[0].seperationreason.value=='Termination') || (document.forms[0].seperationreason.value=='Resignation') || (document.forms[0].seperationreason.value=='CRS')){
				bool=true;
			}
			//alert('-----station------'+station);
			
			var frmName='FSAppNew';
			if(bool){		
			
			var url ="<%=basePathWoView%>loadNoteSheet.do?method=saveFinalSettlement&frm_station="+station+"&frm_sepReason="+seperationreason+"&frm_pensionno="+pensionNo+"&frm_region="+region+"&frm_name="+frmName;
			//alert(url)
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();				   			
			  
			}
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
     if((document.forms[0].seperationreason.value=='Retirement')|| (document.forms[0].seperationreason.value=='Termination') || (document.forms[0].seperationreason.value=='VRS') || (document.forms[0].seperationreason.value=='Death') || (document.forms[0].seperationreason.value=='Others') || (document.forms[0].seperationreason.value=='Resignation') || (document.forms[0].seperationreason.value=='CRS')){
     		next.style.display="block";
			   	submit.style.display="none";
     }else{
       alert("Final Settlement Application Form Fecility is not available");
       return false;
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
function getResignationReason(){

	if((document.forms[0].seperationreason.value=='VRS') || (document.forms[0].seperationreason.value=='Resignation')){
		resigreason.style.display="block";
	}else{
		resigreason.style.display="none";
		 orgdetails.style.display="none";
	}

}

function displayOrgDetails(){
 
  
  if((document.forms[0].resignationreason.value=='otherdepartment'))
  orgdetails.style.display="block";
  else  
  orgdetails.style.display="none";

}


 </script>

	</head>

	
<body class="bodybackground" onload="hide('<%=request.getAttribute("focusFlag")%>');getResignationReason();">
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
		<%=ScreenUtilities.screenHeader("loansadvances.finalsettlementappnew.screentitle")%>
		<table width="750" border="0" cellspacing="3" cellpadding="0">
		
							<tr>
								<td class="screenlabelerrors" colspan="2" align="right" style="color:red">

									<logic:messagesPresent message="true">
										<html:messages id="message" message="true" bundle="common">
											<bean:write name="message" />
											<br />
										</html:messages>

									</logic:messagesPresent>

								</td>
							</tr>
					
									  
										<tr>
											<td class="tableTextBold">
												PF ID:&nbsp;
											</td>
											<td>
												<html:hidden property="pensionNo" />												

												<html:text styleClass="TextField" property="pfid" maxlength="15" tabindex="1" />


												<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('AdvanceFormInfo');" alt="Click The Icon to Select Details" />
												<input type="button" class="butt" value="GO" name="go" onclick="retrivedPersonalInfo();" tabindex="2" />
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
												CPF A/C No:&nbsp;
											</td>
											<td>
												<html:text property="cpfaccno"  styleClass="TextField" tabindex="3" maxlength="25" />
											</td>
											
											<td class="tableTextBold">
												Father's Name:&nbsp;
											</td>
											<td>
												<html:text property="fhName" styleClass="TextField" readonly="true" />
											</td>
										</tr>
										<tr>
										<td class="tableTextBold">
												Employee Code:&nbsp;
											</td>
											<td>
												<html:text property="employeeNo" styleClass="TextField"  readonly="true" />
												<html:hidden property="trust"/>
											</td>
											<td class="tableTextBold">
												Date of Joining in AAI:&nbsp;
											</td>
											<td>
												<html:text property="dateOfJoining" styleClass="TextField"  readonly="true" />
											</td>
											
										
										</tr>
										<tr>
											
											<td class="tableTextBold">
												Name:&nbsp;
											</td>
											<td>
												<html:text property="employeeName" styleClass="TextField" readonly="true" />
											</td>
											
											<td class="tableTextBold">
												Designation:&nbsp;
											</td>
											<td>												
												<html:text property="designation" styleClass="TextField" tabindex="4" maxlength="50"/>
											</td>
																						
										</tr>
										<tr>
																						
											<td class="tableTextBold">
												Date of Birth:&nbsp;
											</td>
											<td>
												<html:text property="dateOfBirth" styleClass="TextField"  readonly="true" />
											</td>
										</tr>
										
										<tr>
											
											<td class="tableTextBold">
												Permanent Address:&nbsp;
											</td>
											<td>
												<html:text property="permenentaddress" styleClass="TextField" tabindex="5" maxlength="150" />
											</td>
											
											<td class="tableTextBold">
											Present Address/Postal Address:&nbsp;
												
											</td>
											<td>												
												<html:text property="presentaddress" styleClass="TextField" tabindex="6" maxlength="150"/>
											</td>								
											
										</tr>
										
										<tr>
											
											<td class="tableTextBold">
												Telephone NO:&nbsp;
											</td>
											<td>
												<html:text property="phoneno" styleClass="TextField" tabindex="7"/>
											</td>
											
											<td class="tableTextBold">
											E-Mail:&nbsp;
												
											</td>
											<td>												
												<html:text property="empmailid" styleClass="TextField" tabindex="8" maxlength="100"/>
											</td>								
											
										</tr>
									
										<tr>
											<td class="tableTextBold">
												Region:&nbsp;
											</td>
											
											<td>		
								          
								             
								             <select name="region" class="TextField" style="width:119px" tabindex="9" onchange="getStations();">
								             
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
											
											</td>										
										
										  <td   nowrap="nowrap">									 
											
												<logic:present name="airportsList" >
													<label class="tableTextBold"><bean:write name="advanceForm" property="station" /> /</label>	
													</logic:present>
											
											<logic:present name="airportsList">
											
													 
										   
										   <html:select property="station" name="advanceForm" styleClass="TextField"  style="width:119px" tabindex="10">
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
										<td>&nbsp;</td>
										</tr>
										
											<tr>
											<td class="tableTextBold">
												Reason For Seperation:&nbsp;
											</td>
											<td>
												<html:select property="seperationreason" styleClass="TextField" style="width:119px" tabindex="11" onchange="displayNominee();getResignationReason()">
													<html:option value="">Select One</html:option>
													<html:option value="Retirement">Retirement</html:option>
													<html:option value="VRS">VRS</html:option>
													<html:option value="Death">Death</html:option>
													<html:option value="Resignation">Resignation</html:option>
													<html:option value="Termination">Termination</html:option>
													<html:option value="EarlyPensions">Option for Early Pensions</html:option>
													<html:option value="CRS">CRS</html:option>
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
										
										
										<tr id="resigreason" style="display:none">
											<td class="tableTextBold">
												Reason :&nbsp;
											</td>
											<td>
												<html:select property="resignationreason" styleClass="TextField" style="width:119px" tabindex="13" onchange="displayOrgDetails()">
													<html:option value="">Select One</html:option>
													<html:option value="personal">Personal Reason</html:option>
													<html:option value="otherdepartment">Joined in other Department</html:option>													
												</html:select>
											</td>			
											<td colspan="2">
											&nbsp;
											</td>																	
										</tr>
										
										<tr id="orgdetails" style="display:none">
											<td colspan="4">
											<table border="0" width="750">
											<tr>
											
											<td class="tableTextBold">
												Organization Name:&nbsp;
											</td>
											<td>
												<html:text property="organizationname" styleClass="TextField" tabindex="14" maxlength="150" />
											</td>
											
											<td class="tableTextBold">
											Organization Address:&nbsp;
												
											</td>
											<td>												
												<html:text property="organizationaddress" styleClass="TextField" tabindex="15" maxlength="150"/>
											</td>								
											
										</tr>
										
										<tr>
											
											<td class="tableTextBold">
												Date of Appointment:&nbsp;
											</td>
											<td>
												<html:text property="appointmentdate" styleClass="TextField" tabindex="16" maxlength="150" />
												<a href="javascript:show_calendar('forms[0].appointmentdate');"><img src="<%=basePath%>/images/calendar.gif" border="no" alt="" /></a>
											</td>
											
											<td class="tableTextBold">
											Posted As:&nbsp;												
											</td>
											<td>												
												<html:text property="postedas" styleClass="TextField" tabindex="17" maxlength="150"/>
											</td>								
											
										</tr>
										
										<tr>
											
											<td class="tableTextBold">
												Place of working :&nbsp;
											</td>
											<td>
												<html:text property="workingplace" styleClass="TextField" tabindex="18" maxlength="150" />
											</td>
											
											<td colspan="2">&nbsp;</td>								
											
										</tr>
											
											</table>
											</td>																	
										</tr>
										
										
										
										

									</table>
									<table align="center">

										<tr>
										
										<td align="left" style="display:none" id="next">
											<input type="button" class="btn" value="Next&gt;&gt;" onclick="submitData();" tabindex="19"/>
										</td>
										
											<td align="left" id="submit">
												<input type="button" class="btn" value="Submit" tabindex="19" onclick="submitData();" />
											</td>
											<td align="right">

												<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="20"/>
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="21"/>
											</td>
										</tr>


									</table>
							
<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

