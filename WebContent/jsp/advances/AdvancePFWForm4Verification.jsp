<!--
/*
  * File       : AdvancePFWForm4Verification.jsp
  * Date       : 04/02/2010
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
<script type="text/javascript">
		function validate(){
		
				var ex=/^[-]?[0-9.]+$/;
				var subscriptionAmnt=Number(document.forms[0].subscriptionAmt.value);
				var contributionAmt=Number(document.forms[0].contributionAmt.value);
				
				if(document.forms[0].purposeType.value=="HBA")
				{
				var recsubamt=Number(document.forms[0].approvedsubamt.value);
				var reccontamt=Number(document.forms[0].approvedconamt.value);
				
				  //alert('-----subscriptionAmnt-----'+subscriptionAmnt);
		     // alert('-----contributionAmt-----'+contributionAmt);
						       
		        if(document.forms[0].approvedsubamt.value!=""){	        
		    	if (!ex.test(document.forms[0].approvedsubamt.value))
		    	{
				  alert("Recommended Employee subscription Amount shoud be Numeric");
				 document.forms[0].approvedsubamt.focus();
				 return false;
		    	}	
		    		        
		       }
		       
		       if(document.forms[0].approvedconamt.value!=""){	        
		    	if (!ex.test(document.forms[0].approvedconamt.value))
		    	{
				  alert("Recommended Employer contribution Amount shoud be Numeric");
				 document.forms[0].approvedconamt.focus();
				 return false;
		    	}	
		    		        
		       }		   
		       
		      /* if(recsubamt>subscriptionAmnt){
		       alert("Recommended Employee subscription Amount should not exceed Employee Subscription "+subscriptionAmnt);
		        document.forms[0].approvedsubamt.focus();
				 return false;		       
		       }
		       
		        if(reccontamt>contributionAmt){
		        alert("Recommended Employer contribution Amount should not exceed Employer Contribution "+contributionAmt);
		        document.forms[0].approvedconamt.focus();
				 return false;		       
		       }*/
		       }
		       
		        if(document.forms[0].amntrcdedrs.value!=""){	        
		    	if (!ex.test(document.forms[0].amntrcdedrs.value))
		    	{
				  alert("Amount recommended for sanction of PFW of Rs. shoud be Numeric");
				 document.forms[0].amntrcdedrs.select();
				 return false;
		    	}	
		    		        
		       }		   
		       
		       	       
		     return true;
		}
		function saveForm3(frmName){
		
		       	if(!validate()){
					return false;
		  		}
		       		      
		        var flag;								
				var purposeType=document.forms[0].purposeType.value;				
				var purposeOptionType=document.forms[0].purposeOptionType.value;						
				var verified=document.forms[0].verifiedby.value;				
		        
		        if(verified=='PERSONNEL,FINANCE'){
		        	flag='Form4VerificationApproval';
		        }else{
		        	flag='Form4VerificationEdit';
		        }
		     
		     calRecomdedAmnt();
		     calCPFFund();
		     
		     var CPFFund,RecomendAmt,contriAmnt,pfwrule ,pfwAmntApplied;
		    pfwrule = document.forms[0].prpsecvrdclse.value;
	 		CPFFund = document.forms[0].CPFFund.value;
	 		var  nintyPercOfCPFFund =  parseFloat(CPFFund*90/100) ; 
	 		RecomendAmt = document.forms[0].approvedsubamt.value; 
	 		pfwAmntApplied = parseFloat(document.forms[0].advnceapplid.value); 
	 		if(purposeType!='LIC'){
		    if(purposeType=='SUPERANNUATION' || purposeType=='HBA'|| purposeType=='PANDEMIC'){ 
	 		contriAmnt = document.forms[0].approvedconamt.value;   			  		 	 
 			var totRecAmt =parseFloat(RecomendAmt) + parseFloat(contriAmnt); 		 
  		 	//alert("-----CPFFund---"+CPFFund+"----90PercOfCPFFund-----"+nintyPercOfCPFFund+"----RecomendAmt----"+RecomendAmt+"--contriAmnt----"+contriAmnt+"====totRecAmt===="+totRecAmt);
 			
 			 if(purposeType=='SUPERANNUATION'){  
			if(totRecAmt>nintyPercOfCPFFund){
				alert("Total Of Recommended Subscription and Contribution  should not be exceed to 90% Of CPFFund "+nintyPercOfCPFFund);
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
		      /*  if(totRecAmt>nintyPercOfCPFFund){
				alert("Total Of Recommended Subscription   should not be exceed to 90% Of CPFFund");
				document.forms[0].approvedsubamt.focus();
				return false; 
			}  */
			
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
		 			        				   
				url="<%=basePath%>form4AdvanceVerification.do?method=savePFWForm4Verification&frmFlag="+flag+"&bankflag="+bankFlag+"&frm_name="+frmName;
				//alert(url);
				document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();				
		
		}
		function frmPrsnlReset(){
			var pensionNo=document.forms[0].pensionNo.value;
			var advanceTransID=document.forms[0].advntrnsid.value;
			var formType="Y";
			var url="<%=basePath%>form4AdvanceVerification.do?method=advanceForm4VerificationReport&frmName=PFWForm4Verification&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransID;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		function calRecomdedAmntStatus(){
			if(document.forms[0].chkCalFlag.value=='Y'){
				document.forms[0].chkCalFlag.value='N';
				//document.forms[0].prpsecvrdclse.focus();
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
			 
			  if(purposeType=='HBA' || purposeType=='SUPERANNUATION' || purposeType=='PANDEMIC'){
			  
			  var sanctionAmt=parseFloat(document.forms[0].approvedsubamt.value)+parseFloat(document.forms[0].approvedconamt.value);
			  document.forms[0].amntrcdedrs.value=sanctionAmt;
			  
			  }
			
			 //alert('-----subscriptionAmnt-----'+subscriptionAmnt);
			 
			 /*if(document.forms[0].amntRecommended.value==0){
			
			 if (purposeType=="HBA"
						&& (purposeOptionType=="PURCHASESITE" || purposeOptionType
								=="ACQUIREFLAT"|| purposeOptionType
								=="PURCHASEHOUSE"|| purposeOptionType
								=="CONSTRUCTIONHOUSE")) {
					elgEmoluments = (36 * emoluments);
				
					
					if (elgEmoluments < subscriptionAmnt) {
						elgAmnt = elgEmoluments;
					} else {
						elgAmnt = subscriptionAmnt;
					}
					
			}else if(purposeType=="HBA" && (purposeOptionType=="RENOVATIONHOUSE")){
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
				
				//alert('-----elgAmnt-----'+elgAmnt);
				//alert('-----reqstAdvnceAmnt-----'+reqstAdvnceAmnt);
				
			 	if (elgAmnt < reqstAdvnceAmnt) {
					finalElAmnt = elgAmnt;
				} else {
					finalElAmnt = reqstAdvnceAmnt;
				}
				
				//alert('-----finalElAmnt------'+finalElAmnt);
				recmndAmnt = finalElAmnt - outStandAmount;
				document.forms[0].amntrcdedrs.value=recmndAmnt;
				
				}else{
				document.forms[0].amntrcdedrs.value=document.forms[0].amntRecommended.value;
				}
				
				//document.forms[0].amntrcdedrs.focus();
				 document.forms[0].chkCalFlag.value='Y';
				 */
			 
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
		
				
		function advancesForm3Report(){	
	        var flag='ApprovalReport';		       
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			
				var transID,pensionNo,advanceType,status;
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		advanceType=document.forms[0].advanceType.value;
		status='A';
						
			if(status=='N'){
			
				alert(transID+' Should be approved.');
			}else{			
		    var url="<%=basePath%>form3Advance.do?method=advanceForm3Report&frmName=PFWForm3Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
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
	
 	 
 		</script>
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
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/commonfunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/datetime.js"></script>
		<SCRIPT type="text/javascript">
		
		</SCRIPT>
		
	</head>

	<body class="bodybackground" onload="calRecomdedAmnt();loadBankDetails();">
	
		<html:form method="post" action="form4AdvanceVerification.do?method=savePFWForm4Verification">
		<%=ScreenUtilities.screenHeader("PFW (Part Final Withdraw) Verification Form-IV")%>
		<table width="720" border="0" cellspacing="3" cellpadding="0">
				
									
									 <tr>
									  <td width="22%">
												&nbsp;
											</td>
							            <td class="tableTextBold" width="25%">
											Key No
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">
										
											
											<bean:write property="advntrnsid" name="advancePFWForm4"/>
											
												&nbsp;<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:advancesForm3Report()"/>
										</td>
										
							           
							         </tr> 
							         <tr>
							           <td>&nbsp;</td>
							            <td class="tableTextBold">
											PF ID
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">
										<html:hidden property="advntrnsid"/>
										<html:hidden property="advanceTransIDDec"/>
											<html:hidden property="pensionNo"/>
											<html:hidden property="verifiedby"/>											
											<html:hidden property="emoluments"/>
											<html:hidden property="paymentinfo" />	
											
											<html:hidden property="subscriptionAmt"/>	
												
											<html:hidden property="CPFFund"/>	
											<html:hidden property="purposeType"/>
											<html:hidden property="purposeOptionType"/>	
											<html:hidden property="amntRecommended"/>
												<html:hidden property="prpsecvrdclse"/>	
										
											
																						
											<bean:write property="pfid" name="advancePFWForm4"/>										
										</td>
							           
							         </tr> 
							            <tr>
							            <td>&nbsp;</td>
									  <td class="tableTextBold">
											Name
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">
										
											<bean:write property="employeeName" name="advancePFWForm4"/>	
																					
										</td>
									</tr>	
							         <tr>
							         <td>&nbsp;</td>
									  <td class="tableTextBold">
											Designation
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">										
											<bean:write property="designation" name="advancePFWForm4"/>																						
										</td>
									</tr>	
									<tr>
									<td>&nbsp;</td>
									  <td class="tableTextBold" >
											Place of posting
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">											
											<bean:write property="placeofposting" name="advancePFWForm4"/>																						
										</td>
									</tr>	
									<tr>
									<td>&nbsp;</td>
									  <td class="tableTextBold" >
											Purpose 
										</td>
										<td width="2%" align="center">:</td>
										<td class="tableSideText">
										<bean:write property="purposeType" name="advancePFWForm4"/>
										
										 <logic:equal name="advancePFWForm4" property="purposeType" value="HBA">
										   ,<bean:write property="purposeOptionTypeDesr" name="advancePFWForm4"/>
										 </logic:equal>
											
																					
										</td>
									</tr>	  																														
															
						          									
									<tr>
					<td>
						&nbsp;
					</td>
					<logic:equal name="Form3ReportBean" property="purposeType" value="MARRIAGE">
						<td class="tableTextBold" align="right">
							100 % of own Subscription + Interest
						</td>
					</logic:equal>
					<logic:equal name="Form3ReportBean" property="purposeType" value="HE">
						<td class="tableTextBold" align="right">
							100 % of own Subscription + Interest
						</td>
					</logic:equal>
				 
					<logic:equal name="Form3ReportBean" property="purposeType" value="HBA">
						<td class="tableTextBold" align="right">
							Employee subscription
						</td>
					</logic:equal>
						 
					<logic:equal name="Form3ReportBean" property="purposeType" value="LIC">
						<td class="tableTextBold" align="right">
							Employee subscription
						</td>
					</logic:equal>
					<logic:equal name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
						<td class="tableTextBold" align="right">
							Employee subscription
						</td>
					</logic:equal>
					<logic:equal name="Form3ReportBean" property="purposeType" value="PANDEMIC">
						<td class="tableTextBold" align="right">
							Employee subscription
						</td>
					</logic:equal>
					<td align="center">
						:
					</td>


					<td>
						<html:text styleClass="TextField" property="empshare" />
					</td>
				</tr>
				
					<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						AAI Contribution
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="contributionAmt" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount of PFW applied
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="advnceapplid" />
					</td>
				</tr>
				 
				 				<logic:equal name="advancePFWForm4" property="purposeFlag" value="Y"> 
										<tr >
										<td>&nbsp;</td>
											<td class="tableTextBold">
												Recommended Employee subscription Amount  
											</td>
											<td width="2%" align="center">:</td>

											<td class="reportdata">
												<html:text styleClass="TextField"  property="approvedsubamt"></html:text>
											</td>
										</tr> 
										
										<tr>
										<td>&nbsp;</td>
											<td class="tableTextBold">
												Recommended Employer contribution Amount
											</td>
											<td width="2%" align="center">:</td>

											<td class="reportdata">
												<html:text styleClass="TextField"  property="approvedconamt"></html:text>											
											</td>
										</tr>
										
									<tr >
						              <td>&nbsp;</td>
									  <td class="tableTextBold">
											Amount recommended for sanction of PFW of Rs.
										</td>
										<td width="2%" align="center">:</td>
										<td>
											<html:text styleClass="TextField"  property="amntrcdedrs" />
																					
										</td>
									</tr>
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
									 
								   </logic:equal>  
						            <logic:notEqual name="advancePFWForm4" property="purposeType" value="HBA"> 
						              <logic:notEqual name="advancePFWForm4" property="purposeType" value="SUPERANNUATION"> 
						              <logic:notEqual name="advancePFWForm4" property="purposeType" value="PANDEMIC">
						              <logic:notEqual name="advancePFWForm4" property="purposeType" value="HE">
						            	<html:hidden property="amntrcdedrs"/>	
						             <tr>
						              <td>&nbsp;</td>
									  <td class="tableTextBold">
											Amount recommended for sanction of PFW of Rs.
										</td>
										<td width="2%" align="center">:</td>
										<td>
											<html:text styleClass="TextField"  property="approvedsubamt" />																					
										</td>
									</tr>	
									</logic:notEqual> 						       
						       </logic:notEqual>
						            </logic:notEqual>
						          	  </logic:notEqual> 
						            <tr>						
						            <td>&nbsp;</td>            
						            <td class="tableTextBold">Verified By</td>
						          <td width="2%" align="center">:</td>
						            <td class="reportdata">
						            
						            <html:select property="authorizedsts" styleClass="TextField" >
						            <html:option value="A">Accept</html:option>
						            <html:option value="R">Reject</html:option>
						            </html:select>
						            </td>
						          </tr>	
						          
						          <input type="hidden" name="advanceType" value="<bean:write property="advanceType" name="Form3ReportBean"/>">
						          <input type="hidden" name="reqstAdvnceAmnt" value="<bean:write property="advnceRequest" name="Form3ReportBean"/>">
						          <input type="hidden" name="chkCalFlag" value="N">
						          
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
								
								<tr id="editBankDet"  style="display:none"> 
									
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
									   <td>&nbsp;</td>										
									   <td align="left" id="submit">
											<input type="button" class="btn" value="Submit" onclick="javascript:saveForm3('<bean:write name="advanceBean" property="frmName"/>')" tabindex="9"/>
									   </td>
									   <td align="right">
											<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="10"/>
											<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="11"/>
										</td>
									</tr>


								</table>
							
		</table><%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>

