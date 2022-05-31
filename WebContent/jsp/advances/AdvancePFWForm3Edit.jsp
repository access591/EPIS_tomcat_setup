<!--
/*
  * File       : AdvancePFWForm3Edit.jsp
  * Date       : 21/01/2010
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append( request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();

%>
<script type="text/javascript">
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
		        if(document.forms[0].amntrcdedrs.value==""){
		        alert("Please Enter Amount Recommended  ");
		        document.forms[0].amntRecommended.focus();
		        return false;		        
		        }
		  
		    	    if (!ex.test(document.forms[0].CPFFund.value) && document.forms[0].CPFFund.value!="")
		    	{
				  alert("CPF Fund  shoud be Numeric");
				 document.forms[0].CPFFund.select();
				 return false;
		    	}
		    	       if (!ex.test(document.forms[0].amntrcdedrs.value) && document.forms[0].amntrcdedrs.value!="")
		    	{
				  alert("Amount Recommended  shoud be Numeric");
				 document.forms[0].amntRecommended.select();
				 return false;
		    	}		
		       
		        return true;
		}
		function saveForm3(){
		
		       	if(!validate()){
					return false;
		  		}
		       
		        var flag='Form3Edit';
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
						
				
				if(purposeType!='HBA'){
				   
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
				
				calCPFFund();
		     var CPFFund,RecomendAmt,contriAmnt,pfwrule ,pfwAmntApplied;
		   
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
			 
			 if(parseFloat(totRecAmt)> parseFloat(pfwAmntApplied)){
				alert("Total Of Recommended Subscription and Contribution  should not be greater than  Amount of PFW applied "+pfwAmntApplied);
				document.forms[0].approvedsubamt.focus();
				return false; 
			} 
		     }else{
		   var totRecAmt =parseFloat(RecomendAmt); 
		      /* if(totRecAmt>nintyPercOfCPFFund){
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
				
				   
						url="<%=basePath%>form3Advance.do?method=savePFWForm3&frmFlag="+flag;
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
				document.forms[0].amntrcdedrs.value=recmndAmnt;
				document.forms[0].amntrcdedrs.focus();
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
		
	function loadDet(){
var purposeType = document.getElementById("purposeType").value;
if(purposeType=='HBA' || purposeType=='SUPERANNUATION'){
document.getElementById("reccontriamnt").style.display="block";
}else{
document.getElementById("reccontriamnt").style.display="none";
}
}
 		</script>
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

	<body class="bodybackground">

		<html:form method="post">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbborder">
							<tr>
								<td height="5%" colspan="2" align="center" class="ScreenMasterHeading">

									PFW (Part Final Withdraw) Verfication Form-III [Edit]&nbsp;&nbsp;<font color="red"> </font>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>

							<tr>
								<td height="15%">
									<table align="center" border="0" width="80%">

										<tr>
											<td class="screenlabel" width="40%">
												Key No:&nbsp;
											</td>
											<td class="reportdata" width="30%">

												<html:text property="advntrnsid" readonly="true" />

											</td>
											<td width="10%">
												&nbsp;
											</td>

										</tr>
										<tr>
											<td class="screenlabel">
												PF ID:&nbsp;
											</td>
											<td class="reportdata">
												<html:hidden property="pensionNo" />
												<html:text property="pfid" readonly="true" />
												<html:hidden property="advnceapplid" />
											</td>

										</tr>
										<tr>
											<td class="screenlabel">
												Date of Joining Fund:&nbsp;
											</td>
											<td>
												<html:text property="dateOfMembership" readonly="true" />

											</td>
										</tr>
										<tr>
											<td class="screenlabel">
												Emoluments:&nbsp;
											</td>
											<td>
												<html:text property="emoluments" onfocus="javascript:calRecomdedAmntStatus()" readonly="true" onchange="javascript:calRecomdedAmntStatus()" />
											</td>
										</tr>

										<tr>
											<td class="screenlabel">
												<bean:write property="emolumentsLabel" name="advancePFWForm4" />
												&nbsp;
											</td>
											<td>
												<html:text property="mnthsemoluments" readonly="true" />
											</td>
										</tr>

										<tr>
											<td class="screenlabel">
												Rule for PFW:&nbsp;
											</td>
											<td>
												<html:select property="prpsecvrdclse" style="width: 131px">
													<html:option value="25(A)">25(A)</html:option>
													<html:option value="24(1)(a)">24(1)(a)</html:option>
													<html:option value="24(1)(b)">24(1)(b)</html:option>
													<html:option value="24(1)(c)">24(1)(c)</html:option>
													<html:option value="24(1)">24(1)</html:option>
													<html:option value="24(6)">24(6)</html:option>
													<html:option value="25(B)">25(B)</html:option>
												</html:select>
											</td>
										</tr>
										<tr>
											<td class="label" align="right">
												Employee Subscription&nbsp;:
											</td>

											<td class="reportdata">
												<html:text property="subscriptionAmt" onblur="javascript:calRecomdedAmntStatus()" onchange="javascript:calRecomdedAmntStatus()"></html:text>
											</td>
										</tr>
										<tr>
											<td class="label" align="right">
												Employer Contribution&nbsp;:
											</td>

											<td class="reportdata">
												<html:text property="contributionAmt" onblur="calCPFFund();"></html:text>
											</td>
										</tr>
										<tr>
											<td class="label" align="right">
												Fund Available in CPF A/c&nbsp;:
											</td>

											<td class="reportdata">
												<html:text property="CPFFund" onblur="javascript:calRecomdedAmnt()" onchange="javascript:calRecomdedAmnt()"></html:text>
											</td>
										</tr>
										<logic:equal name="advancePFWForm4" property="purposeType" value="HBA">

											<tr>
												<td class="label" align="right">
													Approved employee subscription amount &nbsp;:
												</td>

												<td class="reportdata">
													<html:text property="approvedsubamt"></html:text>
												</td>
											</tr>


											<tr>
												<td class="label" align="right">
													Approved employer contribution amount&nbsp;:
												</td>

												<td class="reportdata">
													<html:text property="approvedconamt"></html:text>
												</td>
											</tr>

										</logic:equal>
										<tr>
											<td class="label" align="right">
												Amount recommended for sanction of PFW of Rs.&nbsp;:
											</td>

											<td class="reportdata">
												<html:text property="amntrcdedrs"></html:text>
											</td>
										</tr>


										<logic:equal property="paymentinfo" name="advancePFWForm4" value="Y">
											<tr>

												<td class="reportsublabel" align="right">
													<u>Bank Details for payment</u>
												</td>
												<td>
													&nbsp;
												</td>
												<td class="reportdata">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td class="label" align="right">
													Name (Appearing in the saving bank A/c ) &nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="bankempname" />
												</td>
											</tr>
											<tr>
												<td class="label" align="right">
													Address of Employee (as per bank Record)&nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="bankempaddrs" />
												</td>
											</tr>
											<tr>
												<td class="label" align="right">
													Bank Name&nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="bankname" />
												</td>
											</tr>
											<tr>

												<td class="label" align="right">
													Branch Address&nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="branchaddress" />
												</td>
											</tr>
											<tr>

												<td class="label" align="right">
													Saving bank A/c No.&nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="banksavingaccno" />
												</td>
											</tr>
											<tr>

												<td class="label" align="right">
													RTGS Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:
												</td>
												<td class="reportdata">
													<bean:write name="bankMasterBean" property="bankemprtgsneftcode" />
												</td>
											</tr>
											<tr>
												<td class="label" align="right">
													Employee mail ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;:
												</td>
												<td class="reportdata">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td class="label" align="right">
													MICR No.(Printed on cheque book)&nbsp;:
												</td>

												<td class="reportdata">
													<bean:write name="bankMasterBean" property="bankempmicrono" />
												</td>
											</tr>

										</logic:equal>



										<tr>
											<td class="label" align="right">
												Verified By&nbsp;:
											</td>

											<td class="reportdata">
												<html:select property="authorizedsts" style="width: 131px">
													<html:option value="A">Accept</html:option>
													<html:option value="R">Reject</html:option>
												</html:select>
											</td>
										</tr>

										<input type="hidden" name="advanceType" value="<bean:write property="advanceType" name="Form3ReportBean"/>">
										<input type="hidden" name="purposeType" value="<bean:write property="purposeType" name="Form3ReportBean"/>">
										<input type="hidden" name="purposeOptionType" value="<bean:write property="purposeOptionType" name="Form3ReportBean"/>">
										<input type="hidden" name="reqstAdvnceAmnt" value="<bean:write property="advnceRequest" name="Form3ReportBean"/>">
										<input type="hidden" name="chkCalFlag" value="N"/>   

										<tr>
											<td align="center" colspan="4">
												<table align="center">
													<tr>
														<td>
															&nbsp;
														</td>
														<td align="left" id="submit">
															<input type="button" class="btn" value="Update" onclick="javascript:saveForm3()" tabindex="9" />
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
						</html:form>
	</body>
</html>

