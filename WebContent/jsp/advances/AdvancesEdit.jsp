<!--
/*
  * File       : AdvancesEdit.jsp
  * Date       : 18/12/2009
  * Author     : Suneetha V 
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
String basePathWoView = basePathBuf.toString();

		String profile="",region="";
		
 		HttpSession httpsession = request.getSession();
		LoginInfo logInfo=new LoginInfo();
	         
        if(session.getAttribute("user")!=null)
	    {
	    logInfo=(LoginInfo)httpsession.getAttribute("user");
        profile=logInfo.getProfile();
        region=logInfo.getRegion();
        System.out.println("-----%%%%%profile%%%%%%----"+profile);	
        System.out.println("-----%%%%%region%%%%%%----"+region);
        				
	    }


%>


<%
    String reg="",placeofposting="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();
	Set keys = hashmap.keySet();
	System.out.println(".............keys................"+keys);
	Iterator it = keys.iterator();
	
	ArrayList pop=new ArrayList();
	pop=common.loadAllStations();
	 

  %>

<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en" "http://www.w3.org/tr/xhtml1/dtd/xhtml1-transitional.dtd">
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
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
		<script type="text/javascript">
		
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
		
		
				
		function continueCPFAdvance(){
		 	var ex=/^[0-9]+$/;
			var bool=false,advanceDtl="",pfwPurpose="",advPurpose="",pensionNo="",totalInstall="",trust="";
			
			advanceDtl=document.forms[0].advanceType.options[document.forms[0].advanceType.selectedIndex].value;
			pfwPurpose=document.forms[0].pfwPurpose.options[document.forms[0].pfwPurpose.selectedIndex].value;
			advPurpose=document.forms[0].advPurpose.options[document.forms[0].advPurpose.selectedIndex].value;
			trust=document.forms[0].trust.options[document.forms[0].trust.selectedIndex].value;
			pensionNo=document.forms[0].pensionNo.value;
			if(advanceDtl=="PFW" && pfwPurpose=="LIC"){
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
			if(document.forms[0].advReqAmnt.value==''){
					alert('Amount of PFW/Advance is Required ');
					document.forms[0].advReqAmnt.focus();
					return false;
			}else if(document.forms[0].advReqAmnt.value==0){
					alert('Amount of PFW/Advance Shouldnt accepted Zero');
					document.forms[0].advReqAmnt.focus();
					return false;
			}
	
			if (!ex.test(document.forms[0].advReqAmnt.value) && document.forms[0].advReqAmnt.value!="")
		    {
				  alert("Required Amount shoud be Numeric");
				 document.forms[0].advReqAmnt.select();
				 return false;
		    }
			if(advanceDtl=='PFW'){
				
					if(pfwPurpose=='NO-SELECT'){
							alert('PFW Purpose Should Be Select');
							document.forms[0].pfwPurpose.focus();
							return false;
					}
					totalInstall="0";
			}else{
					if(advPurpose=='NO-SELECT'){
							alert('CPF Purpose Should Be Select');
							document.forms[0].advPurpose.focus();
							return false;
					}					
					totalInstall=document.forms[0].cpftotalinstall.value;
					if(totalInstall=='NO-SELECT'){
							alert('CPF Total Insallment Should Be Select');
							document.forms[0].cpftotalinstall.focus();
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
		    var frmName,region;
		    region = document.forms[0].region.value;
		    
		    
		    // for region wise record checking
			var region,userRegion,userProfile;
			region=document.forms[0].region.value;
			userRegion='<%=region%>';
			userProfile='<%=profile%>';
			//alert('--region--'+region+'---userRegion--'+userRegion);
			if((region!=userRegion) &&(userProfile=='R')){
			alert('This Pfid Belongs to '+region+' You cant Process these Data');
			return false;
			}
			
			if(bool){
			
				frmName="pfwedit";  
				alert(document.forms[0].advanceTransID.value);
				var url ="<%=basePathWoView%>loadAdvanceEdit.do?method=updatePFWAdvacneInfo&frm_transId="+document.forms[0].advanceTransID.value+"&frm_pfwAdv="+advanceDtl+"&frm_pfwPurpose="+pfwPurpose+"&frm_advPurpose="+advPurpose+"&frm_pensionno="+pensionNo+"&frm_trust="+trust+"&frm_name="+frmName+"&frm_region="+region;
				 
				document.forms[0].action=url;
				document.forms[0].method="post";
				document.forms[0].submit();
			}else {		
		
			    			  
			    if(advanceDtl=='PFW')
			    frmName="pfwedit";
			    else
			    frmName="advancesedit";			    
   				
		    	var url ="<%=basePathWoView%>loadAdvanceEdit.do?method=updateAdvacneInfo&frm_transId="+document.forms[0].advanceTransID.value+"&frm_pfwAdv="+advanceDtl+"&frm_pfwPurpose="+pfwPurpose+"&frm_advPurpose="+advPurpose+"&frm_totalinstall="+totalInstall+"&frm_pensionno="+pensionNo+"&frm_trust="+trust+"&frm_name="+frmName+"&frm_region="+region;
		   	     
		   	    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		  }
		}
		function advance_info(){			
			if(document.forms[0].advanceType.value=="PFW"){   		    
	   			 document.getElementById("pfwpurp").style.display="block";
	   		     document.getElementById("divadvance").style.display="none";
	   		     //document.getElementById("cpftrust").style.display="none"; 
	   		    
	   		     
	   		}
	   		if(document.forms[0].advanceType.value=="CPF"){   		    
	   		      document.getElementById("divadvance").style.display="block";
	   		      document.getElementById("pfwpurp").style.display="none";  
	   		     //document.getElementById("cpftrust").style.display="block";
	   		     
		   	}
		}
		
		function frmPrsnlReset(){
			 hide('false');
			 var frmName='';
			 if(document.forms[0].advanceType.value=="CPF"){  
			 frmName="advancesedit";
			 }else{
			 frmName="pfwedit";
			 }
			url="<%=basePathWoView%>loadAdvanceEdit.do?method=editAdvances&frmTransID="+document.forms[0].advanceTransID.value+"&frmPensionNo="+document.forms[0].pensionNo.value+"&frm_name="+frmName;
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
				
	   			url="<%=basePathWoView%>loadAdvance.do?method=lookupPFID&frm_pensionno="+pensionNo+"&goFlag="+flag;
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
			advPurpose=document.forms[0].advPurpose.options[document.forms[0].advPurpose.selectedIndex].value;
			advanceDtl=document.forms[0].advanceType.options[document.forms[0].advanceType.selectedIndex].value;
			
			
			if(advanceDtl=='PFW' && document.forms[0].pfwPurpose.value=='LIC'){
				bool=true;
			}else
			if(advanceDtl=='CPF' && advPurpose=='DEFENCE' ){
			
			}else if(advanceDtl=='CPF' && advPurpose=='OTHERS'){
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
			document.forms[0].employeeNo.value=empcode;
			document.forms[0].employeeName.value=empname;
		    document.forms[0].designation.value=designation;
		    document.forms[0].fhName.value=fname;
		    document.forms[0].department.value=department;
		    document.forms[0].dateOfJoining.value=dojaai;
		    document.forms[0].dateOfMembership.value=dojcpf;
		    document.forms[0].dateOfBirth.value=dob;
		    document.forms[0].emoluments.value=emolument;
		    document.forms[0].pensionNo.value=pensionNo;
		    document.forms[0].region.value=region;  
		   
		    document.forms[0].subscriptionAmt.value=emppfstatuary;
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
	  	function chkadvance(){
	  	
	  	 
	  	 	if(document.forms[0].advanceType.value=="PFW"){   		    
	   			 document.getElementById("pfwpurp").style.display="block";
	   		     document.getElementById("divadvance").style.display="none";
	   		     //document.getElementById("cpftrust").style.display="none"; 
	   		    	   		     
	   		}
	   		if(document.forms[0].advanceType.value=="CPF"){   		    
	   		      document.getElementById("divadvance").style.display="block";
	   		      document.getElementById("pfwpurp").style.display="none";  
	   		     //document.getElementById("cpftrust").style.display="block";
	   		     
		   	}
		   	
		   	if(document.forms[0].pfwPurpose.value=="LIC"){   		 
		   	   document.getElementById("submit").style.display="block"; 
		   	
		   	}else{
		   	   document.getElementById("next").style.display="block"; 
		   	}
		   	
		   	if(document.forms[0].advPurpose.value=="OTHERS"){   		 
		   	   document.getElementById("advanceOthers").style.display="block"; 
		   	
		   	}else{
		   	   document.getElementById("advanceOthers").style.display="none"; 
		   	}
			   		
	  	 
	  	}
	  	
	  	function getStations(){ 
   
	      var url = "/loadAdvance.do?method=loadStations";
		  
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
	   	
 </script>
	</head>

	<body class="bodybackground" onload="hide('<%=request.getAttribute("focusFlag")%>');chkadvance();">

		<html:form method="post" action="loadAdvanceEdit.do?method=updateAdvacneInfo">
			<%=ScreenUtilities.screenHeader(request.getAttribute("screenTitle").toString())%>
			<table width="720" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td class="screenlabelerrors" colspan="2" align="right" style="color:red">

						<logic:messagesPresent message="true">
							<html:messages id="message" message="true">
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

						<html:hidden property="pensionNo" name="advanceEditInfo" />
						<html:hidden property="advanceTransID" name="advanceEditInfo" />
						<html:hidden property="paymentinfo" name="advanceEditInfo" />
						<html:hidden property="lodInfo" name="advanceEditInfo" />
						<html:hidden property="purposeOptionType" name="advanceEditInfo" />

						<html:hidden property="modeofpartyname" name="advanceEditInfo" />
						<html:hidden property="modeofpartyaddrs" name="advanceEditInfo" />
						<html:hidden property="chkwthdrwlinfo" name="advanceEditInfo" />

						<html:hidden property="advncerqddepend" name="advanceEditInfo" />
						<html:hidden property="utlisiedamntdrwn" name="advanceEditInfo" />


						<html:text styleClass="TextField" property="pfid" tabindex="1" maxlength="15" />

						<!-- <input type="text" name="pfid" />-->

					</td>
					<td class="tableTextBold">
						Old Employee No:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="employeeNo" readonly="true"  tabindex="2"/>
						<!-- 	<input type="text" name="empcode" />-->
					</td>
				</tr>
				<tr>
					<td class="tableTextBold">
						Name:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="employeeName"  readonly="true" tabindex="3"/>
						<!--  <input type="text" name="empname" /> -->
					</td>

					<td class="tableTextBold">
						Designation:&nbsp;
					</td>
					<td>
						<html:hidden property="empmailid" />
						<html:text styleClass="TextField" property="designation" name="advanceEditInfo" tabindex="4" maxlength="50"/>
						<!-- <input type="text" name="designation" />-->
					</td>
				</tr>
				<tr>
					<td class="tableTextBold">
						Father's Name:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="fhName" readonly="true" tabindex="5"/>
						<!-- <input type="text" name="fname" /> -->
					</td>


					<td class="tableTextBold">
						Department:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="department" tabindex="6" maxlength="50"/>
						<!-- <input type="text" name="department" />-->
					</td>
				</tr>
				<tr>
					<td class="tableTextBold">
						Date of Joining in AAI:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="dateOfJoining" tabindex="7" onblur="dispDOJFund()" readonly="true" />
						<!-- 	<input type="text" name="djoin" /> -->
					</td>

					<td class="tableTextBold">
						Date of Joining CPF:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="dateOfMembership" readonly="true" tabindex="8"/>
						<!-- 	<input type="text" name="dcpf" /> -->
					</td>
				</tr>
				<tr>
					<td class="tableTextBold">
						Date of Birth:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="dateOfBirth" readonly="true" tabindex="9"/>
						<!-- <input type="text" name="dbirth" />-->
					</td>
					<td class="tableTextBold">
						Created Date:&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="createdDate" name="advanceEditInfo" tabindex="10"/>
						<!-- <input type="text" name="department" />-->
					</td>

				</tr>
				<tr>
					<td class="tableTextBold">
						Region:&nbsp;
					</td>
					<td>
						<select class="TextField" name="region" style="width:119px" tabindex="11" onchange="getStations();">

							<%
									while(it.hasNext()){			
									 boolean exist = false;
									  reg=hashmap.get(it.next()).toString();  										
									  %>

							<logic:equal property="region" name="advanceEditInfo" value="<%=reg%>">
								<%
										  exist = true; 
									   %>
							</logic:equal>
							<%
										 									  
									  	if (exist) {
									  
								   %>
							<option value="<bean:write property="region" name="advanceEditInfo"/>" <% out.println("selected");%>>
								<bean:write property="region" name="advanceEditInfo" />
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

					<td class="tableSideTextBold" nowrap="nowrap">
						<bean:write name="advanceEditInfo" property="station" />
						/


						<logic:present name="airportsList">


							<html:select styleClass="TextField" property="station" name="advanceEditInfo" tabindex="12">
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
				<tr>

					<td class="tableTextBold">
						Place of Posting
						<bean:message key="common.stars" bundle="common" />:
					</td>
					  <td>
									 
						 <html:select styleClass="TextField" property="placeofposting" name="advanceEditInfo" tabindex="13" >
										<%
									for(int i=0;i<pop.size();i++){			
									 boolean exist = false;
									  placeofposting=pop.get(i).toString(); 
									 
									  %> 
									 <logic:equal property="placeofposting" name="advanceEditInfo" value="<%=placeofposting%>">
											<%
										  exist = true; 
										   System.out.println("-----"+exist) ;
									   %>
										</logic:equal>
								    
									<%
										 									  
									  	if (exist) { 
								   %>
										<html:option  value="<%=placeofposting%>" >
											<%=placeofposting%>
										</html:option>
										<% }else{
								     
								   %>
										<html:option value="<%=placeofposting%>">
											<%=placeofposting%>
										</html:option>

										<%} }%>


									</html:select>
								</td>        
					<td colspan="2">
						&nbsp;
					</td>

				</tr>
				<tr>

					<td class="tableTextBold" nowrap="nowrap">
						Emoluments (Pay + DA):&nbsp;
					</td>
					<td>
						<html:text styleClass="TextField" property="emoluments" name="advanceEditInfo" maxlength="15" tabindex="14"/>
					</td>
					<logic:equal property="advanceType" value="PFW" name="advanceEditInfo">
						<td class="tableTextBold" nowrap="nowrap">
							Amount of PFW Required
							<bean:message key="common.stars" bundle="common" />
							:&nbsp;
						</td>
					</logic:equal>
					<logic:notEqual property="advanceType" value="PFW" name="advanceEditInfo">
						<td class="tableTextBold" nowrap="nowrap">
							Amount of Advance Required
							<bean:message key="common.stars" bundle="common" />
							:&nbsp;
						</td>
					</logic:notEqual>
					<td>
						<html:text styleClass="TextField" property="advReqAmnt" name="advanceEditInfo" tabindex="15" maxlength="15" />
					</td>

				</tr>
				<tr>
					<td class="tableTextBold">
						PFW/Advances:&nbsp;
					</td>

					<td>
						<html:select styleClass="TextField" property="advanceType" name="advanceEditInfo" onchange="advance_info();" style='width:119px' tabindex="16">

							<html:option value="PFW">
													PFW
												</html:option>
							<html:option value="CPF">
													CPF Advance
												</html:option>
						</html:select>
					</td>

					<td class="tableTextBold">
						Trust &nbsp;:
					</td>
					<td>
						<html:select styleClass="TextField" property="trust" name="advanceEditInfo" style='width:119px' tabindex="17">
							<html:option value="NAA">NAA</html:option>
							<html:option value="AAI">AAI</html:option>
							<html:option value="IAAI">IAAI</html:option>
						</html:select>
					</td>

				</tr>
				<tr>
					<td id="pfwpurp" colspan="4">
						<table width="720" align="center" align="center" cellpadding="1" cellspacing="1" border="0">
							<tr>
								<td class="tableTextBold" width="23%">
									PFW Purposes
									<bean:message key="common.stars" bundle="common" />
									:
								</td>
								<td width="26%">
									<html:select styleClass="TextField" property="pfwPurpose" name="advanceEditInfo" onchange="purposes_nav();" tabindex="18">
										<html:option value="NO-SELECT">
														Select one
													</html:option>
										<html:option value="HBA">
														HBA (Housing Built Area)
													</html:option>
										<html:option value="MARRIAGE">
														Marriage
													</html:option>
										<html:option value="HE">
														Higher Education
													</html:option>
										<html:option value="LIC">
														LIC (Life Insurance Policy)
													</html:option>
										<html:option value='SUPERANNUATION'>
														Superannuation
										</html:option>
									</html:select>
									<html:hidden property="subscriptionAmt" />
								</td>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="divadvance" colspan="4" align="left">
						<table width="720" align="center" cellpadding="1" cellspacing="1" border="0" align="center">
							<tr>
								<td class="tableTextBold">
									Advance purposes
									<bean:message key="common.stars" bundle="common" />
									:
								</td>
								<td>
									<html:select styleClass="TextField" style="width: 118px" property="advPurpose" name="advanceEditInfo" onchange="purposes_nav();" tabindex="19">
										<html:option value="NO-SELECT">
														Select one
													</html:option>
										<html:option value="COST">
														Cost Of Passage
													</html:option>
										<html:option value="OBLIGATORY">
														Obligatory Expenses
													</html:option>
										<html:option value="OBMARRIAGE">
														Marriage Expenses
													</html:option>
										<html:option value="ILLNESS">
														Illness Expenses
													</html:option>
										<html:option value="EDUCATION">
														Higher Education
													</html:option>
										<html:option value="DEFENCE">
														Defense Of Court Case
													</html:option>
										<html:option value="OTHERS">
														 Others
													</html:option>
									</html:select>
								</td>
								<td class="tableTextBold">
									Total No of Installment
									<bean:message key="common.stars" bundle="common" />
									:
								</td>
								<td>
									<html:select styleClass="TextField" property="cpftotalinstall" name="advanceEditInfo" tabindex="20">
										<html:option value="NO-SELECT">
														Select one
													</html:option>
										<%for(int i=1;i<=36;i++){%>
										<html:option value="<%=String.valueOf(i)%>">
											<%=i%>
										</html:option>
										<%}%>
									</html:select>

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
									<html:text styleClass="TextField" property="advReasonText" name="advanceEditInfo" tabindex="21" maxlength="100" />
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>


			<table align="center">


				<tr>
					<td align="left" style="display:none" id="next">
						<input type="button" class="btn" value="Next&gt;&gt;" onclick="continueCPFAdvance();" tabindex="22" />
					</td>
					<td align="left" style="display:none" id="submit">
						<input type="button" class="btn" value="Submit" onclick="continueCPFAdvance();" tabindex="22" />
					</td>
					<td align="right">

						<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="23" />
						<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="24" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

