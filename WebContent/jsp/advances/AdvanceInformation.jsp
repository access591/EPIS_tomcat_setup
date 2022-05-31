<!--
/*
  * File       : AdvanceInformation.jsp
  * Date       : 09/26/2009advReqAmnt
  * Author     : Suresh Kumar Repaka 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.info.login.LoginInfo,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
		String basePath = basePathBuf.toString();
		
		String profile="",region="",userStation="";

 		HttpSession httpsession = request.getSession();
		LoginInfo logInfo=new LoginInfo();
	         
        if(session.getAttribute("user")!=null)
	    {
	    logInfo=(LoginInfo)httpsession.getAttribute("user");
        profile=logInfo.getProfile();
        region=logInfo.getRegion();
        userStation=logInfo.getUnitName();
        System.out.println("-----%%%%%profile%%%%%%----"+profile);	
        System.out.println("-----%%%%%region%%%%%%----"+region);
        System.out.println("-----%%%%%station%%%%%%----"+userStation);
        				
	    }
        
%>

<%
    String reg="",placeofposting="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
   	ArrayList pop=new ArrayList();
	hashmap=common.getRegion();
	pop=common.loadAllStations();

	Set keys = hashmap.keySet();
	System.out.println(".............keys................"+keys);

	Iterator it = keys.iterator();
	

  %>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
		<script type="text/javascript"><!--
		var profileName='<%=profile%>';
		function hide(focusFlag) {
			document.getElementById("pfwpurp").style.display="block";
		   	document.getElementById("divadvance").style.display="none";
		   	if(focusFlag!='true'){
		   		document.forms[0].pfid.focus();
		   	}else{
		   		document.forms[0].emoluments.focus();
		   	}
			
		    document.getElementById("advanceOthers").style.display="none";
		  	//document.getElementById("cpftrust").style.display="none"; 
	   		
		}
		
		function frmPrsnlCancel(){
		
		var frmName;
				if(document.forms[0].select_advance_dtl.value=='advance')
				frmName='advances';
				else  if(document.forms[0].select_advance_dtl.value=='pfw')
				frmName='pfw';
		
				url="<%=basePath%>advanceSearch.do?method=loadAdvanceSearchForm&frm_name="+frmName;
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		
		
				
		function continueCPFAdvance(){
		 	var ex=/^[0-9]+$/;
			var bool=false,advanceDtl="",pfwPurpose="",advPurpose="",pensionNo="",totalInstall="",trust="";
			var stationflag="N";
			var frmName="";
			
			//advanceDtl=document.forms[0].select_advance_dtl.options[document.forms[0].select_advance_dtl.selectedIndex].value;
			
			advanceDtl=document.forms[0].select_advance_dtl.value;
			
			pfwPurpose=document.forms[0].select_dtl_pfwpurpose.options[document.forms[0].select_dtl_pfwpurpose.selectedIndex].value;
			advPurpose=document.forms[0].select_adv_purpose_dtl.options[document.forms[0].select_adv_purpose_dtl.selectedIndex].value;
			trust=document.forms[0].trust.options[document.forms[0].trust.selectedIndex].value;
			pensionNo=document.forms[0].pensionNo.value;
			if(advanceDtl=="pfw" && pfwPurpose=="LIC"){
				bool=true;
			}
			if(document.forms[0].pfid.value==''){
					alert('PF ID Is Required');
					document.forms[0].pfid.focus();
					return false;
			}
			if(document.forms[0].emoluments.value==0){
					alert('Amount of Emoluments Shouldnt accepted Zero');
					document.forms[0].emoluments.readonly=false;
					document.forms[0].emoluments.focus();
					return false;
			}
			
			
			if(document.forms[0].placeofposting.value=="NO-SELECT"){
					alert("Please Select Place of Posting");					 
					document.forms[0].placeofposting.focus();
					return false;
			}
			
		   
		    
			if(advanceDtl=='pfw'){
			if(document.forms[0].pfwReqAmnt.value==''){
					alert('Amount of PFW is Required ');
					document.forms[0].pfwReqAmnt.focus();
					return false;
			}else if(document.forms[0].pfwReqAmnt.value==0){
					alert('Amount of PFW Shouldnt accepted Zero');
					document.forms[0].pfwReqAmnt.focus();
					return false;
			}
				if (!ex.test(document.forms[0].pfwReqAmnt.value) && document.forms[0].pfwReqAmnt.value!="")
		    {
				  alert("Required PFW Amount shoud be Numeric");
				 document.forms[0].pfwReqAmnt.select();
				 return false;
		    }
		    
		   // alert(document.forms[0].recEmpSubAmnt.value);
		    //alert(document.forms[0].recEmpConrtiAmnt.value);
		    //alert(document.forms[0].firstInsSubAmnt.value);
		    //alert(document.forms[0].firstInsConrtiAmnt.value);
		    
		    if(!(document.forms[0].select_dtl_pfwpurpose.value == 'Marriage' || document.forms[0].select_dtl_pfwpurpose.value == 'Pandemic')){
		    if(document.forms[0].recEmpSubAmnt.value==''){
					alert('Amount of Recommended Employee subscription is Required');
					document.forms[0].recEmpSubAmnt.focus();
					return false;
			}
			}
			if (!ex.test(document.forms[0].recEmpSubAmnt.value) && document.forms[0].recEmpSubAmnt.value!="")
		    {
				 alert("Recommended Employee subscription Amount shoud be Numeric");
				 document.forms[0].recEmpSubAmnt.select();
				 return false;
		    }
		if(!(document.forms[0].select_dtl_pfwpurpose.value == 'Marriage' || document.forms[0].select_dtl_pfwpurpose.value == 'Pandemic')){
		    if(document.forms[0].recEmpConrtiAmnt.value==''){
					alert('Amount of Recommended Employer contribution is Required');
					document.forms[0].recEmpConrtiAmnt.focus();
					return false;
			}
			}
			if (!ex.test(document.forms[0].recEmpConrtiAmnt.value) && document.forms[0].recEmpConrtiAmnt.value!="")
		    {
				 alert("Recommended Employer contribution Amount shoud be Numeric");
				 document.forms[0].recEmpConrtiAmnt.select();
				 return false;
		    }
		   if(!(document.forms[0].select_dtl_pfwpurpose.value == 'Marriage' || document.forms[0].select_dtl_pfwpurpose.value == 'Pandemic')){
			if(document.forms[0].firstInsSubAmnt.value==''){
					alert('Amount of First Installment to be released Employee subscription is Required ');
					document.forms[0].firstInsSubAmnt.focus();
					return false;
			}
			}
			if (!ex.test(document.forms[0].firstInsSubAmnt.value) && document.forms[0].firstInsSubAmnt.value!="")
		    {
				  alert("First Installment to be released Employee subscription Amount shoud be Numeric");
				 document.forms[0].firstInsSubAmnt.select();
				 return false;
		    }
		if(!(document.forms[0].select_dtl_pfwpurpose.value == 'Marriage' || document.forms[0].select_dtl_pfwpurpose.value == 'Pandemic')){
		    if(document.forms[0].firstInsConrtiAmnt.value==''){
					alert('Amount of First Installment to be released Employee contribution is Required ');
					document.forms[0].firstInsConrtiAmnt.focus();
					return false;
			}
			}
			if (!ex.test(document.forms[0].firstInsConrtiAmnt.value) && document.forms[0].firstInsConrtiAmnt.value!="")
		    {
				  alert("First Installment to be released Employee contribution Amount shoud be Numeric");
				 document.forms[0].firstInsConrtiAmnt.select();
				 return false;
		    }
				
					if(pfwPurpose=='NO-SELECT'){
							alert('PFW Purpose Should Be Select');
							document.forms[0].select_dtl_pfwpurpose.focus();
							return false;
					}
					totalInstall="0";
			}else{
			if(document.forms[0].advReqAmnt.value==''){
					alert('Amount of Advance is Required ');
					document.forms[0].advReqAmnt.focus();
					return false;
			}else if(document.forms[0].advReqAmnt.value==0){
					alert('Amount of Advance Shouldnt accepted Zero');
					document.forms[0].advReqAmnt.focus();
					return false;
			}
			if (!ex.test(document.forms[0].advReqAmnt.value) && document.forms[0].advReqAmnt.value!="")
		    {
				  alert("Required adv Amount shoud be Numeric");
				 document.forms[0].advReqAmnt.select();
				 return false;
		    }
					if(advPurpose=='NO-SELECT'){
							alert('CPF Purpose Should Be Select');
							document.forms[0].select_adv_purpose_dtl.focus();
							return false;
					}
					totalInstall=document.forms[0].totalinstall.value;
					if(totalInstall=='NO-SELECT'){
							alert('CPF Total Insallment Should Be Select');
							document.forms[0].totalinstall.focus();
							return false;
					}
			}
			
			if(document.forms[0].dateOfJoining.value!=''){			
			   if(document.forms[0].dateOfJoining.value!=document.forms[0].dateOfMembership.value){
			      alert('Date of Joining in AAI and Date of Joining CPF should be equal');
			      document.forms[0].dateOfJoining.value.focus();
			      return false;			    
			   }			
			}
			
			//alert('-----old region-----'+document.forms[0].oldregion.value);
			//alert('-----new region-----'+document.forms[0].region.value);
			//alert('-----old station-----'+document.forms[0].oldstation.value);
			//alert('-----old station-----'+document.forms[0].station.value);
			
			if((document.forms[0].oldregion.value!=document.forms[0].region.value) || (document.forms[0].oldstation.value!=document.forms[0].station.value))
			{
			    stationflag="Y";
			}
			
			//alert('-----stationflag----'+stationflag);
			
			//alert('------select_advance_dtl value----'+document.forms[0].select_advance_dtl.value);
			
			var region;
			region=document.forms[0].region.value;
			
			// for region wise record checking
			var region,userRegion,userProfile,userStation,station;
			region=document.forms[0].region.value;
			station=document.forms[0].station.value;
			userRegion='<%=region%>';
			userProfile='<%=profile%>';
			userStation='<%=userStation%>';			
			//alert('--userProfile--'+userProfile);
			// alert('--region--'+region+'---userRegion--'+userRegion);
			// alert('--station--'+station+'---userStation--'+userStation);
			 
		 	 if(userProfile=='R'){
				if(region!=userRegion) {
				alert('This Pfid Belongs to '+region+' You cant Process these Data');
				return false;
				}
			}else if(userProfile=='U'){
				 
				if(((region==userRegion) && (station!=userStation)) || ((region!=userRegion) &&  (station!=userStation))) {
				alert('This Pfid Belongs to '+region+'/'+station+'  You cant Process these Data');
				return false;
				}
			}
		 
			
			//..............
			if(bool){		
			
			
			   	
			if(document.forms[0].select_dtl_pfwpurpose.value=='LIC'){
			 alert('LIC  facility is not activated');
			 return false;
			}
			
			   if(advanceDtl=='pfw')
				frmName='pfw';
					  
				//alert("prasd11111111");
				var url ="<%=basePath%>loadAdvance.do?method=saveAdvacneInfo&frm_pfwAdv="+advanceDtl+"&frm_pfwPurpose="+pfwPurpose+"&frm_advPurpose="+advPurpose+"&frm_pensionno="+pensionNo+"&frm_trust="+trust+"&frm_stationflag="+stationflag+"&frm_name="+frmName+"&frm_region="+region;
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
			}else {		
			 
			    if(advanceDtl=='pfw')
			       frmName='pfwnew';
			    else
			      frmName='advancesnew';
			    
			   //alert("prasdad 22222222"+document.forms[0].firstInsSubAmnt.value);
		    	var url ="<%=basePath%>loadAdvance.do?method=continueWithPTWAdvanceOptions&frm_pfwAdv="+advanceDtl+"&frm_pfwPurpose="+pfwPurpose+"&frm_advPurpose="+advPurpose+"&frm_totalinstall="+totalInstall+"&frm_pensionno="+pensionNo+"&frm_trust="+trust+"&frm_stationflag="+stationflag+"&frm_name="+frmName+"&frm_region="+region;
		   	    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		  }
		}
		function advance_info(){			
			if(document.forms[0].select_advance_dtl.value=="pfw"){   		    
	   			 document.getElementById("pfwpurp").style.display="block";
	   		     document.getElementById("divadvance").style.display="none";
	   		     //document.getElementById("cpftrust").style.display="none"; 
	   		    
	   		     
	   		}
	   		if(document.forms[0].select_advance_dtl.value=="advance"){   		    
	   		      document.getElementById("divadvance").style.display="block";
	   		      document.getElementById("pfwpurp").style.display="none";  
	   		     //document.getElementById("cpftrust").style.display="block";
	   		     
		   	}
		}
		
		function frmPrsnlReset(frmName){
		
			url="<%=basePath%>loadAdvance.do?method=loadAdvanceForm&frm_name="+frmName;
			
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		function retrivedPersonalInfo(){
			var pensionNo,url,flag;
			pensionNo=document.forms[0].pfid.value;
			if(pensionNo==''){
				if(document.forms[0].employeeName.value=='' && document.forms[0].employeeNo.value==''){
					alert('PF ID should be enter value');
					document.forms[0].pfid.focus();
					return false;
				}
			}
				flag=true;
				var frmName;
				
				
				if(document.forms[0].select_advance_dtl.value=='advance')
				frmName='advancesnew';
				else  if(document.forms[0].select_advance_dtl.value=='pfw')
				frmName='pfwnew';
				
				
				
	   			url="<%=basePath%>loadAdvance.do?method=lookupPFID&frm_name="+frmName+"&frm_pensionno="+pensionNo+"&goFlag="+flag+"&action_flag=loadpfid";
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		function hbaOthers(){			
			if(document.forms[0].advpurpose.value=="others"){   
			   	document.getElementById("divothers").style.display="block";		    
   		    }
		}		
		
		function purposes_nav(){			
			var bool=false;
			var advPurpose='',advanceDtl='';
			advPurpose=document.forms[0].select_adv_purpose_dtl.options[document.forms[0].select_adv_purpose_dtl.selectedIndex].value;
			//advanceDtl=document.forms[0].select_advance_dtl.options[document.forms[0].select_advance_dtl.selectedIndex].value;
			//alert(advPurpose);
			//alert(document.forms[0].select_dtl_pfwpurpose.value);
			if(document.forms[0].select_dtl_pfwpurpose.value == 'Marriage'||document.forms[0].select_dtl_pfwpurpose.value == 'Pandemic'){
			document.getElementById("fis").style.display="none";
			document.getElementById("fic").style.display="none";
			document.getElementById("resa").style.display="none";
			document.getElementById("reca").style.display="none";
			}else{
			document.getElementById("fis").style.display="block";
			document.getElementById("fic").style.display="block";
			document.getElementById("resa").style.display="block";
			document.getElementById("reca").style.display="block";
			}
			advanceDtl=document.forms[0].select_advance_dtl.value;
			
			if(document.forms[0].select_dtl_pfwpurpose.value=='LIC'){
			 alert('LIC  facility is not activated');
			 return false;
			}
			
			if(advanceDtl=='pfw' && document.forms[0].select_dtl_pfwpurpose.value=='LIC'){
				bool=true;
			}else
			if(advanceDtl=='advance' && advPurpose=='defence' ){
			
			}else if(advanceDtl=='advance' && advPurpose=='others'){
			document.getElementById("advanceOthers").style.display="block";
			
			}else{
			document.getElementById("advanceOthers").style.display="none";
			}
			if(bool){
			  		next.style.display="none";
			   		submit.style.display="block";    
   		    }else{
   		       	next.style.display="block";
			   	submit.style.display="none";
   		    }
		}	
		function popupWindow(windowname){		
			var pensionNo;
			pensionNo=document.forms[0].pfid.value;
					
			if (! window.focus)return true;
			var href;
	   		href="loadAdvance.do?method=loadLookupPFID&frm_pensionno="+pensionNo;
			progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
			return true;	
		}
		
   	  	function test(pfids,empcode,empname,designation,fname,department,dojaai,dojcpf,dob,emolument,pensionNo,region,emppfstatuary,cpfaccno,airportcode){	   
	   	  document.forms[0].pfid.value=pfids;
			//document.forms[0].employeeNo.value=empcode;
			//document.forms[0].employeeName.value=empname;
		   // document.forms[0].designation.value=designation;
		  //  document.forms[0].fhName.value=fname;
		  //  document.forms[0].department.value=department;
		  //  document.forms[0].dateOfJoining.value=dojaai;
		  //  document.forms[0].dateOfMembership.value=dojcpf;
		  //  document.forms[0].dateOfBirth.value=dob;
		 //   document.forms[0].emoluments.value=emolument;
		    document.forms[0].pensionNo.value=pensionNo;
		    document.forms[0].go.focus();
		 //   document.forms[0].region.value=region;  
		   
		  //  document.forms[0].subscriptionAmt.value=emppfstatuary;
	  	}				
	  	
	  	function dispDOJFund(){	  		  	   
	  	   if(document.forms[0].dateOfJoining.value!=''){	  	  	     
	  	      var dateOfJoining=document.forms[0].dateOfJoining.value;
	  	      //var yearval=parseInt(dateOfJoining.substring(7,11));
	  	       	  	      
	  	      /*if(yearval>1995)	  	       
	  	        document.forms[0].dateOfMembership.value=dateOfJoining;
	  	      else	  	           
	  	        document.forms[0].dateOfMembership.value='01-Apr-1995';
	  	        */
	  	        
	  	      document.forms[0].dateOfMembership.value=dateOfJoining;
	  	   }  	     
	  	 
	  	}
	  	
	  function loadStationsForRHQ(){	   
	  	if(profileName=='R'){
	  	getStations();
	  	}
	  
	  }
	  	function getStations(){ 
	   	  var index,selectedIndex,searchregion;
	  	if(profileName=='R'){
	  	searchregion='<%=region%>';
	  	} else{
	  	    index=document.forms[0].region.selectedIndex.value;
	         selectedIndex=document.forms[0].region.options.selectedIndex;
		     searchregion=document.forms[0].region[selectedIndex].value;  
		 
	  	}
	      var url = "/loadAdvance.do?method=loadStations"; 
		   //alert(url + searchregion);
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
function getPurpose(advanceType){
//alert(advanceType);
    
  if(advanceType=='advancesnew' || advanceType=='CPF'){
     document.getElementById("divadvance").style.display="block";
     document.getElementById("pfwpurp").style.display="none";
     document.getElementById("pfwInstall").style.display="none";
     document.getElementById("advReqAmt").style.display="block";
     document.getElementById("pfwReqAmt").style.display="none";
    
  }else{
     document.getElementById("divadvance").style.display="none";
     document.getElementById("pfwpurp").style.display="block";
     document.getElementById("pfwInstall").style.display="block";
     document.getElementById("advReqAmt").style.display="none";
  	 document.getElementById("pfwReqAmt").style.display="block";
    
  
  }
    

}


 --></script>
	</head>

	<body class="bodybackground" onload="hide('<%=request.getAttribute("focusFlag")%>');getPurpose('<bean:write name="advanceBean" property="frmName"/>');">

		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<%=ScreenUtilities.screenHeader(request.getAttribute("screenTitle").toString())%>
			<table width="720" border="0" cellspacing="3" cellpadding="0">


				<tr>
					<td height="15%">
						<table align="center" border="0">
							<tr>
								<td class="screenlabelerrors" colspan="3" align="center" style="color:red">


									<logic:messagesPresent message="true">
										<html:messages id="message" message="true"  bundle="common">
											<bean:write name="message" />
											<br />
										</html:messages>

									</logic:messagesPresent>

								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									PF ID
									<bean:message key="common.stars" bundle="common" />
									:&nbsp;
								</td>
								<td>

									<html:hidden property="pensionNo" />
									<html:text styleClass="TextField" property="pfid" tabindex="1" maxlength="15" />


									<!-- <input type="text" name="pfid" />-->
									<img src="<%=basePath%>/images/search1.gif" onclick="popupWindow('AdvanceFormInfo');" alt="Click The Icon to Select Details" />
									<input type="button" class="butt" name="go" value="GO" onclick="retrivedPersonalInfo();" tabindex="2" />
								</td>
								<td class="tableTextBold">
									SAP Employee No:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="employeeNo" readonly="true" />
									<!-- 	<input type="text" name="empcode" />-->
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									Name:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="employeeName" readonly="true" />
									<!--  <input type="text" name="empname" /> -->
								</td>

								<td class="tableTextBold">
									Designation:&nbsp;
								</td>
								<td>
									<html:hidden property="empmailid" />
									<html:text styleClass="TextField" property="designation" tabindex="3" maxlength="50"/>
									<!-- <input type="text" name="designation" />-->
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									Father's Name:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="fhName" readonly="true" />
									<!-- <input type="text" name="fname" /> -->
								</td>


								<td class="tableTextBold">
									Department:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="department" tabindex="4" maxlength="50"/>
									<!-- <input type="text" name="department" />-->
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									Date of Joining in AAI:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="dateOfJoining" tabindex="5" onblur="dispDOJFund()" readonly="true"/>
									<!-- 	<input type="text" name="djoin" /> -->
								</td>

								<td class="tableTextBold">
									Date of Joining CPF:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="dateOfMembership" readonly="true" />
									<!-- 	<input type="text" name="dcpf" /> -->
								</td>
							</tr>
							<tr>
								<td class="tableTextBold">
									Date of Birth:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="dateOfBirth" readonly="true" />
									<!-- <input type="text" name="dbirth" />-->
								</td>


							</tr>


							<%
									 if(profile.equals("M") || profile.equals("U")){
									 
									 %>

							<tr>

								<td class="tableTextBold">
									Region:&nbsp;
								</td>

								<td>
									<input type="hidden" name="oldregion" value="<bean:write name="advanceForm" property="region"/>" />
									<html:text styleClass="TextField" property="region" readonly="true" />
								</td>

								<td class="tableTextBold">
									Station :&nbsp;
								</td>

								<td class="screenlabel" nowrap="nowrap">
									<input type="hidden" name="oldstation" value="<bean:write name="advanceForm" property="station"/>" />
									<html:text styleClass="TextField" property="station" readonly="true" />
								</td>

							</tr>
							<%}else if(profile.equals("R")){
						 			%>
							<tr>
								<td class="tableTextBold">
									Region:&nbsp;
								</td>
								<td>
									<input type="hidden" name="oldregion" value="<bean:write name="advanceForm" property="region"/>" />
								    <html:text styleClass="TextField" property="region" readonly="true" value="<%=region%>" />
 
								</td>



								<td class="tableTextBold">
									Station :&nbsp;
								
								</td>

								<td  nowrap="nowrap">

									<input type="hidden" name="oldstation" value="<bean:write name="advanceForm" property="station"/>" />
									<label class="tableTextBold"><bean:write name="advanceForm" property="station" />/</label>
									
									<logic:present name="airportsList"> 
										<html:select styleClass="TextField" property="station" name="advanceForm" tabindex="7">
											<logic:iterate name="airportsList" id="st">

												<bean:define id="station" property="station" name="st"></bean:define>
												<html:option value="<%=String.valueOf(station)%>">
													<bean:write name="st" property="station" />
												</html:option>

											</logic:iterate>
										</html:select>
									</logic:present>

								</td>


							</tr>
							<%
									}else{
									%>
							<tr>
								<td class="tableTextBold">
									Region:&nbsp;
								</td>
								<td>
									<input type="hidden" name="oldregion" value="<bean:write name="advanceForm" property="region"/>" />

									<select name="region" class="TextField" tabindex="6"  onchange="getStations();">

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
										<option value="<bean:write property="region" name="advanceForm"/>" <% out.println("selected");%>>
											<bean:write property="region" name="advanceForm" />
										</option>
										<% }else{
								     
								   %>
										<option value="<%=reg%>">
											<%=reg%>
										</option>

										<%} }%>

									</select>


								</td>



								<td class="tableTextBold">
									Station :&nbsp;
								
								</td>

								<td  nowrap="nowrap">

									<input type="hidden" name="oldstation" value="<bean:write name="advanceForm" property="station"/>" />
									<label class="tableTextBold"><bean:write name="advanceForm" property="station" />/</label>
									
									<logic:present name="airportsList">

 										<html:select styleClass="TextField" property="station" name="advanceForm" tabindex="7">
											<logic:iterate name="airportsList" id="st">

												<bean:define id="station" property="station" name="st"></bean:define>
												<html:option value="<%=String.valueOf(station)%>">
													<bean:write name="st" property="station" />
												</html:option>

											</logic:iterate>
										</html:select>
									</logic:present>

								</td>


							</tr>
							<%
									}
									%>

							<tr>

								<td class="tableTextBold">
									Place of Posting
									<bean:message key="common.stars" bundle="common" />
									:
								</td> 
								<td>
									 
									<html:select styleClass="TextField" property="placeofposting" name="advanceForm" tabindex="8">
									<html:option value="NO-SELECT">
											Select One
										</html:option>
										<%
									for(int i=0;i<pop.size();i++){			
									 boolean exist = false;
									  placeofposting=pop.get(i).toString(); 									   									
									  %> 
								 
										<html:option value="<%=placeofposting%>">
											<%=placeofposting%>
										</html:option>

										<%  }%>

									</html:select>
								</td>
								<td class="tableTextBold">
									Trust &nbsp;:
								</td>
								<td>
									<html:select styleClass="TextField" property="trust" tabindex="9">
										<html:option value="NAA">NAA</html:option>
										<html:option value="AAI">AAI</html:option>
										<html:option value="IAAI">IAAI</html:option>
									</html:select>
								</td>
							</tr>

						<tr>

								<td class="tableTextBold">
									Emoluments (Pay + DA):&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="emoluments" tabindex="10" maxlength="15" />
								</td>
								<td id="advReqAmt" colspan="4">
								<table cellpadding="1" cellspacing="1" border="0">
								<tr>
								<td  class="tableTextBold">
									Amount of&nbsp;
									<br />
									Advance Required
									<bean:message key="common.stars" bundle="common" />
									:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="advReqAmnt" tabindex="11" maxlength="15" />
								</td>
								</tr>
								</table>
								</td>
									<td id="pfwpurp" colspan="2">
									<table cellpadding="1" cellspacing="1" border="0">
										<tr>
											<td class="tableTextBold" width="53%">
												PFW Purposes
												<bean:message key="common.stars" bundle="common" />
												:
											</td>
											<td>
												<select name='select_dtl_pfwpurpose' class="TextField" onchange="purposes_nav();" style='width:119px' tabindex="12">
													<option value="NO-SELECT">
														Select one
													</option>
													<option value='HBA'>
														HBA (Housing Built Area)
													</option>
													<option value='Marriage'>
														Marriage
													</option>
													<option value='HE'>
														Higher Education
													</option>
													<option value='LIC'>
														LIC (Life Insurance Policy)
													</option>
													<option value='SUPERANNUATION'>
														 Superannuation
													</option>
													<option value='Pandemic'>
														 PFW-Pandemic
													</option>
												</select>
												<html:hidden property="subscriptionAmt" />
											</td>
										</tr>
									</table>
								</td>
								
							</tr>

							<tr>
								<bean:define id="loadfrmName" name="advanceBean" property="frmName"></bean:define>
								<logic:equal name="advanceBean" property="frmName" value="pfwnew">
									<input type="hidden" name="select_advance_dtl" value="pfw" />
								</logic:equal>
								<logic:equal name="advanceBean" property="frmName" value="advancesnew">
									<input type="hidden" name="select_advance_dtl" value="advance" />
								</logic:equal>
								<td id="pfwReqAmt" colspan="4" >
								<table cellpadding="1" cellspacing="1" border="0">
								<tr>
								<td class="tableTextBold" width="52%">
									Amount of&nbsp;
									<br />
									PFW Required
									<bean:message key="common.stars" bundle="common" />
									:&nbsp;
								</td>
								<td>
									<html:text styleClass="TextField" property="pfwReqAmnt" tabindex="11" maxlength="15" />
								</td>
								</tr>
								</table>
							</td>
							</tr>
							<!--perpose of new fields-->
							<tr>
								<td id="pfwInstall" colspan="4">
									<table align="center" cellpadding="1" cellspacing="1" border="0" align="center">
										<tr>
											<td class="tableTextBold" width="70%">
												Recommended Employee subscription Amount  </td>
												<td id="resa" ><bean:message key="common.stars" bundle="common" />
												
											</td><td>:</td>
											<td width="38%">
												<html:text styleClass="TextField" property="recEmpSubAmnt" tabindex="11" maxlength="15" />
											</td>
											</tr>
											<tr>
											<td class="tableTextBold" width="70%">
												Recommended Employer contribution Amount  </td>
												<td id="reca" ><bean:message key="common.stars" bundle="common" />
												
											</td><td>:</td>
											<td width="38%">
												<html:text styleClass="TextField" property="recEmpConrtiAmnt" tabindex="11" maxlength="15" />
											</td>
											</tr>
										<tr>
											<td class="tableTextBold" width="70%">
												First Installment to be released Employee subscription Amount </td>
												<td id="fis" ><bean:message key="common.stars" bundle="common" />
												
											</td><td>:</td>
											<td width="38%">
												<html:text styleClass="TextField" property="firstInsSubAmnt" tabindex="11" maxlength="15" />
											</td>
											</tr>
											<tr>
										<td class="tableTextBold" width="80%">
												First Installment to be released Employer contribution Amount </td>
												<td id="fic" ><bean:message key="common.stars" bundle="common" />
												
											</td><td>:</td>
											<td width="38%">
												<html:text styleClass="TextField" property="firstInsConrtiAmnt" tabindex="11" maxlength="15" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<!--perpose of new fields end-->
							<tr>
								<td id="divadvance" colspan="4">
									<table align="center" cellpadding="1" cellspacing="1" border="0" align="center">
										<tr>
											<td class="tableTextBold" width="30%">
												Advance purposes
												<bean:message key="common.stars" bundle="common" />
												:
											</td>
											<td width="38%">
												<select name='select_adv_purpose_dtl' class="TextField" onchange="purposes_nav();" style='width:119px' tabindex="12">
													<option value="NO-SELECT">
														Select one
													</option>
													<option value='cost'>
														Cost Of Passage
													</option>
													<option value='obligatory'>
														Obligatory Expenses
													</option>
													<option value='obMarriage'>
														Marriage Expenses
													</option>
													<option value='illness'>
														Illness Expenses
													</option>
													<option value='education'>
														Higher Education
													</option>
													<option value='defence'>
														Defense Of Court Case
													</option>
													<option value='others'>
														Others
													</option>
												</select>
											</td>
											<td class="tableTextBold" width="64%">
												&nbsp;Total No of Installment
												<bean:message key="common.stars" bundle="common" />
												:
											</td>
											<td>
												<select name='totalinstall' class="TextField" style='width:119px' tabindex="13">
													<option value="NO-SELECT">
														Select one
													</option>
													<%
												int mnths=36;
												if(request.getAttribute("Advancemnths")!=null){
												
												mnths=Integer.parseInt(request.getAttribute("Advancemnths").toString());
												}
												System.out.println("mnths===========" +  request.getAttribute("Advancemnths"));
												 for(int i=1;i<=mnths;i++){%>
													<option value="<%=i%>">
														<%=i%>
													</option>
													<%}%>
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr id="advanceOthers">
								<td colspan="2">
									<table align="center" cellpadding="1" cellspacing="1" border="0">
										<tr>
											<td class="tableTextBold" width="41%">
												Reason:
											</td>
											<td>
												<html:text styleClass="TextField" property="advReasonText" tabindex="14" maxlength="100" />
											</td>
										</tr>
									</table>
								</td>
							</tr>

						</table>


						<table align="center">


							<tr>
								<td align="left" style="display:none" id="next">
									<input type="button" class="btn" value="Next&gt;&gt;" onclick="continueCPFAdvance();" tabindex="15" />
								</td>
								<td align="left" id="submit">
									<input type="button" class="butt" value="Submit" onclick="continueCPFAdvance();" tabindex="15" />
								</td>
								<td align="right">

									<input type="button" class="butt" value="Reset" onclick="javascript:frmPrsnlReset('<%=loadfrmName%>')" class="btn" tabindex="16" />
									<input type="button" class="butt" value="Cancel" onclick="javascript:frmPrsnlCancel()" class="btn" tabindex="17" />
								</td>
							</tr>


						</table>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

