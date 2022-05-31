<!--
/*
  * File       : AdvancePFWForm3.jsp
  * Date       : 07/10/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();

%>
<script type="text/javascript"><!--
		function validate(){
				var ex=/^[-]?[0-9.]+$/;
				if(document.forms[0].emoluments.value==""){
		        alert("Please Enter Emoluments ");
		        document.forms[0].emoluments.focus();
		        return false;		        
		        }
		        
		        if (!ex.test(document.forms[0].emoluments.value) && document.forms[0].emoluments.value!="")
		    	{
				  alert("Emoluments shoud be Numeric");
				 document.forms[0].emoluments.select();
				 return false;
		    	}		
		        
		        if(document.forms[0].subscriptionAmt.value==""){
		        alert("Please Enter Employee Subscription ");
		        document.forms[0].subscriptionAmt.focus();
		        return false;		        
		        }	
		        
		        if (!ex.test(document.forms[0].subscriptionAmt.value) && document.forms[0].subscriptionAmt.value!="")
		    	{
				  alert("Employee Subscription shoud be Numeric");
				 document.forms[0].subscriptionAmt.select();
				 return false;
		    	}
		    	
		    	if(document.forms[0].contributionAmt.value==""){
		        alert("Please Enter Employer Contribution  ");
		        document.forms[0].contributionAmt.focus();
		        return false;		        
		        }	
		         if (!ex.test(document.forms[0].contributionAmt.value) && document.forms[0].contributionAmt.value!="")
		    	{
				  alert("Employer Contribution  shoud be Numeric");
				 document.forms[0].contributionAmt.select();
				 return false;
		    	}
		        if(document.forms[0].CPFFund.value==""){
		        alert("Please Enter CPFFund ");
		        document.forms[0].CPFFund.focus();
		        return false;		        
		        }
		       /* if(document.forms[0].amntrcdedrs.value==""){
		        alert("Please Enter Amount Recommended  ");
		        document.forms[0].amntRecommended.focus();
		        return false;		        
		        }*/
		  
		    	    if (!ex.test(document.forms[0].CPFFund.value) && document.forms[0].CPFFund.value!="")
		    	{
				  alert("CPF Fund  shoud be Numeric");
				 document.forms[0].CPFFund.select();
				 return false;
		    	}
		    	 if (!ex.test(document.forms[0].firstInsSubAmnt.value) && document.forms[0].firstInsSubAmnt.value!="")
		    	{
				  alert("First Installment to be released Employee subscription Amount shoud be Numeric");
				 document.forms[0].firstInsSubAmnt.select();
				 return false;
		    	}
		    	 if (!ex.test(document.forms[0].firstInsConrtiAmnt.value) && document.forms[0].firstInsConrtiAmnt.value!="")
		    	{
				  alert("First Installment to be released Employee contribution Amount shoud be Numeric");
				 document.forms[0].firstInsConrtiAmnt.select();
				 return false;
		    	}
		    	
		    	/* if (!ex.test(document.forms[0].amntrcdedrs.value) && document.forms[0].amntrcdedrs.value!="")
		    	{
				  alert("Amount Recommended  shoud be Numeric");
				 document.forms[0].amntRecommended.select();
				 return false;
		    	}*/		
		       
		        return true;
		}
		function saveForm3(){
		
		       	if(!validate()){
					return false;
		  		}       
		        var flag='Form3Approval';
				url="<%=basePath%>form3Advance.do?method=savePFWForm3&frmFlag="+flag;
				var dateofmembership=document.forms[0].dateOfMembership.value;
				var purposeType=document.forms[0].purposeType.value;				
				var purposeOptionType=document.forms[0].purposeOptionType.value;
				var date2;
				var day1, day2;
				var month1, month2;
				var year1, year2;
			    day1 = dateofmembership.substring (0, dateofmembership.indexOf ("-"));
			    month1 = dateofmembership.substring (dateofmembership.indexOf ("-")+1, dateofmembership.lastIndexOf ("-"));
				year1 = dateofmembership.substring (dateofmembership.lastIndexOf ("-")+1, dateofmembership.length);
				
				var currentDate = new Date();
	             
  		
		  		var myYear1 =currentDate.getYear();
		  		
		  		var myMonth1 = currentDate.getMonth()+1;  
				var myDay1 = currentDate.getDate();
				date2=myDay1 + "/" + myMonth1 + "/" + myYear1;
			
				day2 = date2.substring (0, date2.indexOf ("/"));
				month2 = date2.substring (date2.indexOf ("/")+1, date2.lastIndexOf ("/"));
				year2 = date2.substring (date2.lastIndexOf ("/")+1, date2.length); 
				
				date1 = year1+"/"+month1+"/"+day1;
				date2 = year2+"/"+month2+"/"+day2;
				
				var firstDate = Date.parse(date1);
				var secondDate= Date.parse(date2);
				
				var msPerDay = 1000 * 60 * 60 * 24 * 365;
				dbd = (secondDate-firstDate)/ msPerDay;
						
				
				if(purposeType=='MARRIAGE' || purposeType=='HE'){				   
				    if(dbd<5 && purposeType=='MARRIAGE' && purposeOptionType=='SELF'){
						alert('The Membership of CPF Fund should be min 5 years ');
						document.forms[0].dateOfMembership.focus();
						return false;
					}else{					
						if(dbd<7 && purposeOptionType!='SELF'){
						alert('The Membership of CPF Fund should be min 7 years ');
						document.forms[0].dateOfMembership.focus();
						return false;
						}
						
					}
								
				}
				
				if(purposeType=='HBA'){
				if((document.forms[0].approvedsubamt.value=='') && (document.forms[0].approvedconamt.value=='')){
					alert('You  are not entered values for Both Recommended Amounts');
					  	
					}else if(document.forms[0].approvedsubamt.value==''){
						alert('You are not entered Recommended Subscription Amount');
						  
					}else if(document.forms[0].approvedconamt.value==''){
						alert('You are not entered Recommended Contribution Amount');
						 	
					}				
				}
				var verified=document.forms[0].verifiedby.value;
		        
		        if(verified=='PERSONNEL'){
		        flag='Form3Approval';
		        }else{
		        flag='Form3Edit';
		        }
		         
		    calCPFFund();
		     var CPFFund,RecomendAmt,contriAmnt,pfwrule,pfwAmntApplied ;
		    pfwrule = document.forms[0].prpsecvrdclse.value;
	 		CPFFund = document.forms[0].CPFFund.value;
	 		var  nintyPercOfCPFFund =  parseFloat(CPFFund*90/100) ; 
	 		RecomendAmt = document.forms[0].approvedsubamt.value; 
	 		 pfwAmntApplied = parseFloat(document.forms[0].advnceapplid.value);
	 		if(purposeType!='LIC'){
		    if(purposeType=='SUPERANNUATION' || purposeType=='HBA'){ 
	 		contriAmnt = document.forms[0].approvedconamt.value;   			  		 	 
 			var totRecAmt =parseFloat(RecomendAmt) + parseFloat(contriAmnt); 		 
  		 	//alert("-----CPFFund---"+CPFFund+"----90PercOfCPFFund-----"+nintyPercOfCPFFund+"----RecomendAmt----"+RecomendAmt+"--contriAmnt----"+contriAmnt+"====totRecAmt===="+totRecAmt);
 		 
 		if(purposeType=='SUPERANNUATION'){
			if(totRecAmt>nintyPercOfCPFFund){
				alert("Total Of Recommended Subscription and Contribution  should not be exceed to 90% Of CPFFund " +nintyPercOfCPFFund);
				document.forms[0].approvedsubamt.focus();
				return false; 
			} 
			}
		//	alert("pfwAmntApplied  "+parseFloat(pfwAmntApplied)+"totRecAmt  "+parseFloat(totRecAmt));
			 if(parseFloat(totRecAmt)> parseFloat(pfwAmntApplied)){
				alert("Total Of Recommended Subscription and Contribution  should not be greater than  Amount of PFW applied "+pfwAmntApplied);
				document.forms[0].approvedsubamt.focus();
				return false; 
			} 
			
		     }else{
		     var totRecAmt =parseFloat(RecomendAmt); 
		        /*if(totRecAmt>nintyPercOfCPFFund){
				alert("Total Of Recommended Subscription   should not be exceed to 90% Of CPFFund");
				document.forms[0].approvedsubamt.focus();
				return false; 
			} */ 
			
			if(totRecAmt>pfwAmntApplied){
				alert("Total Of Recommended Subscription   should not be greater than  Amount of PFW applied "+pfwAmntApplied);
				document.forms[0].approvedsubamt.focus();
				return false; 
			} 
		     
		     } 
		     
		     }   
				var bankname,empname,accno,rtgscode,partyname,partyaddr;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	partyname=document.forms[0].partyName.value;;
		 	partyaddr = document.forms[0].partyAddress.value;
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''||partyname!=''||partyaddr!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	}
				    
			url="<%=basePath%>form3Advance.do?method=savePFWForm3&frmFlag="+flag+"&bankflag="+bankFlag;
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
				
		
		}
		function frmPrsnlReset(){
			var pensionNo=document.forms[0].pensionNo.value;
			var advanceTransID=document.forms[0].advntrnsid.value;
			var formType="Y";
			var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmName=PFWForm3&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransID;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		function calRecomdedAmntStatus(){		
		
			if(document.forms[0].chkCalFlag.value=='Y'){
				document.forms[0].chkCalFlag.value='N';
				document.forms[0].prpsecvrdclse.focus();
				return false;
			}
		}
		function calRecomdedAmnt(){
			 var advanceType=document.forms[0].advanceType.value;
			 var purposeType=document.forms[0].purposeType.value;
			 var purposeOptionType=document.forms[0].purposeOptionType.value;
			 var emoluments=Number(document.forms[0].emoluments.value);
			 var subscriptionAmnt=Number(document.forms[0].subscriptionAmt.value);
			 var chkCalFlag=document.forms[0].chkCalFlag.value;
			 var reqstAdvnceAmnt=Number(document.forms[0].reqstAdvnceAmnt.value);
			 var elgEmoluments=0.0,elgAmnt=0.0,finalElAmnt=0.0,outStandAmount=0.0;
			
			 if (purposeType=="HBA"
						&& (purposeOptionType=="PURCHASESITE" || purposeOptionType
								=="ACQUIREFLAT"|| purposeOptionType
								=="PURCHASEHOUSE"|| purposeOptionType
								=="CONSTRUCTIONHO")) {
					elgEmoluments = (36 * emoluments);
				
					
					if (elgEmoluments < subscriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = subscriptionAmnt;
					}
					
			}else if(purposeType=="HBA" && (purposeOptionType=="RENOVATIONHOUS")){
					elgEmoluments = (12 * emoluments);
				
					if (elgEmoluments < subscriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = subscriptionAmnt;
					}
					
				}else if(purposeType=="HBA"&& (purposeOptionType=="REPAYMENTHBA")){
					elgEmoluments = (36 * emoluments);
				
					if (elgEmoluments < subscriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = subscriptionAmnt;
					}
				}else if(purposeType=="MARRIAGE" || purposeType=="HE"){
					elgEmoluments = (12 * emoluments);
				
					if (elgEmoluments < subscriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = subscriptionAmnt;
					}
					
				}
				
			 	if (elgAmnt < reqstAdvnceAmnt) {
					finalElAmnt = elgAmnt;
				} else {
					finalElAmnt = reqstAdvnceAmnt;
				}
				recmndAmnt = finalElAmnt - outStandAmount;
				//document.forms[0].amntrcdedrs.value=recmndAmnt;
				//document.forms[0].amntrcdedrs.focus();
				 document.forms[0].chkCalFlag.value='Y';
			 
		}
		function days_between(date1, date2) {

  			  // The number of milliseconds in one day
		    var ONE_DAY = 1000 * 60 * 60 * 24
		
		    // Convert both dates to milliseconds
		    var date1_ms = date1.getTime()
		    var date2_ms = date2.getTime()
		
		    // Calculate the difference in milliseconds
		    var difference_ms = Math.abs(date1_ms - date2_ms)
		    
		    // Convert back to days and return
		    return Math.round(difference_ms/ONE_DAY)

		}
		function calCPFFund(){	
				  
		    document.forms[0].CPFFund.value=parseFloat(document.forms[0].subscriptionAmt.value)+parseFloat(document.forms[0].contributionAmt.value);
		
		}
		
			
		 function advancesForm2Report(){	 
		 
		 var transID,pensionNo,advanceType;
 	     var flag='ApprovalReport';			
	   	 var apprdstatus='A';
		
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		advanceType=document.forms[0].advanceType.value;
		    
	 
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			status='A';	
			if(apprdstatus=='N'){
			
				alert(transID+' Should be approved.');
			}else{
		    var url="<%=basePath%>loadApproval.do?method=advanceForm2Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
		
		}
		
		function calSubcriptionAmt(){
		  
		  if(document.forms[0].purposeType.value=='MARRIAGE' || document.forms[0].purposeType.value=='HE'){		  
		   document.forms[0].subscriptionAmt50percent.value=parseFloat(document.forms[0].subscriptionAmt.value)/2;
		  }
		
		}
		
		function calSubscriptionAmount(){
		document.forms[0].subscriptionAmt.value=parseFloat(document.forms[0].subscriptionAmt50percent.value)*2;
		 
		}
	
	
	function selectBankDetail(){		
				var bankDetailsInfo='';
				bankDetailsInfo=document.forms[0].bankdetail.options[document.forms[0].bankdetail.selectedIndex].value;
		  	//alert(bankDetailsInfo);
		  		
		  		if(bankDetailsInfo=='bank'){   		    
		   			document.getElementById("BankDetails").style.display="block";
		   		 	document.getElementById("partydet").style.display="none";
		   		}else if(bankDetailsInfo=='party'){  
		   		    		     
					    document.getElementById("BankDetails").style.display="block";
		   		    	document.getElementById("partydet").style.display="block";
		   		     
		   		}else{
		   			document.getElementById("BankDetails").style.display="none";
		   		 	document.getElementById("partydet").style.display="none";
		   		} 	
		      
		}
	
		
		
    var bankinfo,type;
function loadBankDetails(){

bankinfo=document.forms[0].paymentinfo.value;
type=document.forms[0].paymentinfo.value
//alert(bankinfo);
if(bankinfo=='Y'){
 
  document.getElementById("selectBankDet").style.display="none";	
   document.getElementById("editBankDet").style.display="block";
  
document.getElementById("bankempname").readOnly=true;
document.getElementById("bankname").readOnly=true;
document.getElementById("banksavingaccno").readOnly=true;
document.getElementById("bankemprtgsneftcode").readOnly=true;

if(document.forms[0].partyName.value!='---'){
document.getElementById("partyName").readOnly=true;
document.getElementById("partyAddress").readOnly=true;
}else{
document.getElementById("partydet").style.display = "none";
}  
}else{
document.getElementById("BankDetails").style.display = "none"; 
document.getElementById("partydet").style.display = "none"; 
document.getElementById("selectBankDet").style.display="block";
}
 

}
var bankFlag='N';
function editBankDetails(){
if(bankinfo=='N'){
alert('Nothing to Edit');
bankFlag='Y';
}else{
alert('You want to edit the Details');
document.getElementById("bankempname").readOnly=false;
document.getElementById("bankname").readOnly=false;
document.getElementById("banksavingaccno").readOnly=false;
document.getElementById("bankemprtgsneftcode").readOnly=false;
document.getElementById("partyName").readOnly=false;
document.getElementById("partyAddress").readOnly=false;
bankFlag='Y';
}
}

function loadDet(){
var purposeType = document.getElementById("purposeType").value;
if(purposeType=='HBA' || purposeType=='SUPERANNUATION'|| purposeType=='PANDEMIC'){
document.getElementById("reccontriamnt").style.display="block";
}else{
document.getElementById("reccontriamnt").style.display="none";
}
}	 
--></script>
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
		<script type="text/javascript" src="<%=basePath%>js/commonfunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/datetime.js"></script>
		<SCRIPT type="text/javascript">
		
		</SCRIPT>

	</head>

	<body onload="calSubcriptionAmt();loadBankDetails(); loadDet();">

		<html:form method="post" action="form3Advance.do?method=savePFWForm3">
			<%=ScreenUtilities.screenHeader("PFW (Part Final Withdraw) Verfication Form-III")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">
				<tr>
					<td align="center" colspan="6">

						<table align="center" border="0">


							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td class="tableTextBold" width="25%">
									Key No
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="advntrnsid" readonly="true" />
									&nbsp;
									<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:advancesForm2Report()" />

								</td>

							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									PF ID
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:hidden property="pensionNo" />
									<html:hidden property="advanceTransIDDec" />
									<html:hidden property="verifiedby" />
									<html:hidden property="paymentinfo" />
									<html:hidden property="advnceapplid" />
							
									<html:text styleClass="TextField" property="pfid" readonly="true" />
								</td>

							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Date of Joining Fund
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="dateOfMembership" readonly="true" />

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Emoluments
								</td>
								<td width="2%">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="emoluments" onfocus="javascript:calRecomdedAmntStatus()" readonly="true" onchange="javascript:calRecomdedAmntStatus()" />
								</td>
							</tr>
						<logic:notEqual name="advancePFWForm4" property="purposeType" value="SUPERANNUATION">	
							<logic:notEqual name="advancePFWForm4" property="purposeType" value="MARRIAGE">
								<logic:notEqual name="advancePFWForm4" property="purposeType" value="HE">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									<bean:write property="emolumentsLabel" name="advancePFWForm4" />
									&nbsp;
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="mnthsemoluments" readonly="true" />
								</td>
							</tr>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
							
							<logic:equal name="advancePFWForm4" property="purposeType" value="MARRIAGE">
							<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									50% Employee Subscription
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="subscriptionAmt50percent" onblur="javascript:calSubscriptionAmount()"></html:text>
								</td>
							</logic:equal>
							<logic:equal name="advancePFWForm4" property="purposeType" value="HE">
							<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									50% Employee Subscription
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="subscriptionAmt50percent" onblur="javascript:calSubscriptionAmount()"></html:text>
								</td>
							</logic:equal>
							

							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Rule for PFW
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
								<!--  Previous -->
									<!-- <html:select styleClass="TextField" property="prpsecvrdclse">
										<html:option value="26">26</html:option>
										<html:option value="27">27</html:option>
										<html:option value="28">28</html:option>
										<html:option value="26(7)">26(7)</html:option>
										
									</html:select>-->
									
									<!--  Changed After request thru Mail on 06-Apr-2011-->
									 <html:select styleClass="TextField" property="prpsecvrdclse">
										<html:option value="25">25</html:option>
										<html:option value="26">26</html:option>
										<html:option value="27">27</html:option>
										<html:option value="25(7)">25(7)</html:option>
										<html:option value="30">30</html:option>
									</html:select> 
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Employee Subscription
								</td>
								<td width="2%" align="center">
									:
								</td>							
								
								<td class="reportdata">
									<html:text styleClass="TextField" property="subscriptionAmt" onblur="javascript:calRecomdedAmntStatus()" onchange="javascript:calRecomdedAmntStatus()"></html:text>
								</td>
								
								
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Employer Contribution
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="contributionAmt" onblur="calCPFFund();"></html:text>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Fund Available in CPF A/c
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="CPFFund" onblur="javascript:calRecomdedAmnt()" onchange="javascript:calRecomdedAmnt()"></html:text>
								</td>
							</tr>

							<logic:notEqual name="advancePFWForm4" property="purposeType" value="LIC">
							 	<tr>
									<td>
										&nbsp;
									</td>
									<td class="tableTextBold">
										Recommended Employee subscription Amount
									</td>
									<td width="2%" align="center">
										:
									</td>
									<td class="reportdata">
										<html:text styleClass="TextField" property="approvedsubamt"></html:text>
									</td>
								</tr> 
							</logic:notEqual>
							 
								<tr id ="reccontriamnt" style="display:none">
									<td>
										&nbsp;
									</td>
									<td class="tableTextBold">
										Recommended Employer contribution Amount
									</td>
									<td width="2%" align="center">
										:
									</td>

									<td class="reportdata">
										<html:text styleClass="TextField" property="approvedconamt"></html:text>
									</td>
								</tr> 
								
								<!-- new fields-->
								<tr id ="fisamt">
									<td>
										&nbsp;
									</td>
									<td class="tableTextBold">
										First Installment to be released Employee subscription Amount
									</td>
									<td width="2%" align="center">
										:
									</td>

									<td class="reportdata">
										<html:text styleClass="TextField" property="firstInsSubAmnt" ></html:text>
									</td>
								</tr> 
								<tr id ="ficamt">
									<td>
										&nbsp;
									</td>
									<td class="tableTextBold">
										First Installment to be released Employer contribution Amount 
									</td>
									<td width="2%" align="center">
										:
									</td>

									<td class="reportdata">
										<html:text styleClass="TextField" property="firstInsConrtiAmnt" ></html:text>
									</td>
								</tr> 
								
								<!-- new fields end-->
								
							<!-- <tr>						            
						            <td class="label" align="right">Amount recommended for sanction of PFW of Rs.&nbsp;:</td>
						           
						            <td class="reportdata"><html:text property="amntrcdedrs" ></html:text> </td>
						          </tr>-->
 
									 
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Verified By
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:select styleClass="TextField" property="authorizedsts">
										<html:option value="A">Accept</html:option>
										<html:option value="R">Reject</html:option>
									</html:select>
								</td>
							</tr>

							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Narration
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="narration" />
								</td>
							</tr>
	
							<input type="hidden" name="advanceType" value="<bean:write property="advanceType" name="Form3ReportBean"/>" />  
							<input type="hidden" name="purposeType" value="<bean:write property="purposeType" name="Form3ReportBean"/>"/>
							<input type="hidden" name="purposeOptionType" value="<bean:write property="purposeOptionType" name="Form3ReportBean"/>"/>
							<input type="hidden" name="reqstAdvnceAmnt" value="<bean:write property="advnceRequest" name="Form3ReportBean"/>"/>
							<input type="hidden" name="chkCalFlag" value="N" />
							
							<!-- ----Bank Details----> 
			<tr>
			<td colspan="5">
			<table>
					<tr>
							<td>
								<table>
							<tr align="right" id="selectBankDet">
									 
									<td class="tableTextBold">
									Bank Detail For Payment Made To :											 
									 									 
									 
													<html:select styleClass="TextField" property="bankdetail"   onchange="selectBankDetail();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value="bank">Employee</html:option>
														<html:option value="party">Party</html:option>
													</html:select>
									</td>	
								</tr>
							  </table>
							 </td>
							</tr>

							<tr>
							<td>
								<table>
								
								<tr id="editBankDet" style="display:none"> 
									
									<td class="tableTextBold">
									 BANK / PARTY DETAILS  FOR PAYMENT <img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="javascript:editBankDetails();" >
									 
									</td>
								  
								</tr> 
								</table>
							 </td>
							</tr>
							
								<tr id="BankDetails">								 
								<td align="center" colspan="5">
								<table width="650" align="center" border="0">								
								<tr>
									 <td>
										&nbsp;
									</td>
									<td class="tableTextBold" width="" >
									Name of Employee as per<br> Saving Bank Account 											 
									</td>									 
									<td width="4%">
										<html:text styleClass="TextField"  property="bankempname" name="bankMasterBean" /> 
									</td>								 
									<td class="tableTextBold" >
									Name Of Bank										 
									</td>									 
									<td width="4%">
									<html:text styleClass="TextField"  property="bankname" name="bankMasterBean"/>
									</td>									
								</tr>										
									<tr>	
									<td>
										&nbsp;
									</td>								 
									<td class="tableTextBold" >
									Bank A/c No										 
									</td>									 
									<td width="4%">
									<html:text styleClass="TextField"  property="banksavingaccno" name="bankMasterBean"/>
									</td> 								 
									<td class="tableTextBold" >
									IFSC Code Of Bank									 
									</td>									 
									<td width="4%">
									<html:text styleClass="TextField"  property="bankemprtgsneftcode"  name="bankMasterBean"/>
									</td> 
								</tr>	 
								</table> 
							 </td>				  
							</tr>
							
							<tr id ="partydet">
									 <td align="center">
									 <table width="650" align="center" border="0">
								  <tr>	
								   	<td width="18%"> 
									</td>	
									 					 
									<td class="tableTextBold" >
									Party Name	 						 
									</td>									 
									<td width="17%">
									<html:text   styleClass="TextField"  property="partyName"  name="bankMasterBean" />
									</td>
									 							 
									<td class="tableTextBold"> 
									Party Address 	 		 
									</td>									 
									<td  width="18%">
									<html:text styleClass="TextField"   property="partyAddress" name="bankMasterBean" />
									</td> 
									 
								</tr>
								 
								</table>  	
								</td>
								</tr>		 
							</table> 
						</td>		
						<tr>		 	
										
							<!-- ----Bank Details---->
							 
							<tr>
								<td align="center" colspan="4">
									<table align="center">
										<tr>
											<td>
												&nbsp;
											</td>
											<td align="right" id="submit">
												<input type="button" class="btn" value="Submit" onclick="javascript:saveForm3()" tabindex="9" />
											</td>
											<td align="right">
												<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="10" />
												<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="11" />
											</td>
										</tr>


									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

