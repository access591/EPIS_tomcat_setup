<!--
/*
  * File       : PFWCheckListApprovalForm.jsp
  * Date       : 24/03/2009
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
		     
		       /* if(document.forms[0].amntrcdedrs.value==""){
		        alert("Please Enter Amount Recommended  ");
		        document.forms[0].amntRecommended.focus();
		        return false;		        
		        }*/
		  
		    	 
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
		  		
		  		var lodcheckedlngth='',total='';
		       		      
		        var flag='Form3Approval';
				url="<%=basePath%>checklist.do?method=saveCPFCheckList&frmFlag="+flag;
				var dateofmembership=document.forms[0].dateOfMembership.value;
				var	advanceType=document.forms[0].advanceType.value;
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
				
				var verified=document.forms[0].verifiedby.value;
		        
		        if(verified=='PERSONNEL'){
		        flag='Form3Approval';
		        }else{
		        flag='Form3Edit';
		        }
		       
		        if(advanceType=='PFW' && purposeType=='HBA'){
		        lodcheckedlngth=document.forms[0].LODHBA.length;
		        
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODHBA[i].checked==true){
		 					lodhba=document.forms[0].LODHBA[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 		}
		        if(advanceType=='PFW' && purposeType=='MARRIAGE'){
		 			lodcheckedlngth=document.forms[0].LODPFWMARR.length;	
		 				 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWMARR[i].checked==true){
		 					lodhba=document.forms[0].LODPFWMARR[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		 		 	if(advanceType=='PFW' && purposeType=='HE'){
		 			lodcheckedlngth=document.forms[0].LODPFWHE.length;	
		 				 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWHE[i].checked==true){
		 					lodhba=document.forms[0].LODPFWHE[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	}
		      if(advanceType=='PFW' && purposeType=='SUPERANNUATION'){
		 			lodcheckedlngth=document.forms[0].LODPFWSA.length;	
		 				 			
		 			for(i=0;i<lodcheckedlngth;i++){
		 				if(document.forms[0].LODPFWSA[i].checked==true){
		 					lodhba=document.forms[0].LODPFWSA[i].value;
		 					if(total==''){
		 						total=lodhba;
		 					}else{
		 						total=total+','+lodhba;
		 					}

		 				}
		 			}
		 	} 
		       
		       var bankname,empname,accno,rtgscode,partyname,partyaddr;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	partyname=document.forms[0].partyName.value;;
		 	partyaddr= document.forms[0].partyAddress.value;
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''||partyname!=''||partyaddr!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	}
		       
					url="<%=basePath%>checklist.do?method=savePFWCheckList&frmFlag="+flag+"&lodinfo="+total+"&bankflag="+bankFlag;
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
			
		function reportAdvances()
		{
		
		var transID,pensionNo;
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		
					
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
	    var url="<%=basePath%>loadAdvance.do?method=advanceReport&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 	winOpened = true;
		wind1.window.focus();
		}
		
		function checkLOD(){
		if(document.forms[0].chkwthdrwlflag.value=='N'){
		document.getElementById("lodrow").style.display="none";	  
		}else{
		document.getElementById("lodrow").style.display="block";	 
		}
		}
		
		function listOfDocs(){
		if(document.forms[0].chkwthdrwlflag.value=='Y')
		    document.getElementById("lodrow").style.display="block";
		else
		 	document.getElementById("lodrow").style.display="none";	  
		  
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

	<body onload="checkLOD();loadBankDetails();">

		<html:form method="post" action="form3Advance.do?method=savePFWForm3">
			<%=ScreenUtilities.screenHeader("PFW Check List Form")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">
				<tr>
					<td align="center" colspan="6">

						<table align="center" border="0">
							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td class="tableTextBold" width="25%">
									PF ID &nbsp;
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:hidden property="paymentinfo" />	
									<html:text styleClass="TextField" property="pfid" readonly="true" />
									&nbsp;
									<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:reportAdvances()" />

								</td>
							</tr>


							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Employee Name &nbsp;
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="employeeName" readonly="true" />
								</td>
							</tr>


							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td class="tableTextBold" width="25%">
									Emoluments
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td class="reportdata">
									<html:text styleClass="TextField" property="emoluments" />
									<html:hidden property="advntrnsid" />
									<html:hidden property="pensionNo" />
									<html:hidden property="advanceTransIDDec" />
									<html:hidden property="verifiedby" />
									<html:hidden property="purposeType" />

									<html:hidden property="purposeOptionTypeDesr" />
									<html:hidden property="advanceType" />



								</td>
							</tr>



							<logic:equal property="purposeType" name="Form3ReportBean" value="HBA">

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
										<html:text styleClass="TextField" property="mnthsemoluments" />
									</td>
								</tr>

							</logic:equal>

							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Date of Joining
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="dateOfMembership" readonly="true" />

								</td>
							</tr>
							<logic:equal property="advanceType" name="Form3ReportBean" value="PFW">

								<logic:notEqual property="purposeType" name="Form3ReportBean" value="HBA">
									<logic:notEqual property="purposeType" name="Form3ReportBean" value="LIC">
									<logic:notEqual property="purposeType" name="Form3ReportBean" value="SUPERANNUATION">

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="tableTextBold">
												Dependent
											</td>
											<td width="2%" align="center">
												:
											</td>
											<td>
												<html:text styleClass="TextField" property="purposeOptionType" />

											</td>
										</tr>

										<tr>
											<td>
												&nbsp;
											</td>
											<td class="tableTextBold">
												Date of Birth
											</td>
											<td width="2%" align="center">
												:
											</td>
											<td>
												<html:text styleClass="TextField" property="dateOfBirth" readonly="true" />

											</td>
										</tr>

										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>

							</logic:equal>
							 
							<logic:equal property="purposeType" name="Form3ReportBean" value="HBA">

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="tableTextBold">
										Whether HBA loan has been taken from AAI
									</td>
									<td width="2%" align="center">
										:
									</td>
									<td>


										<html:select styleClass="TextField" property="hbadrwnfrmaai" name="basicReportBean" style="width:119px">
											<html:option value='Y'>Yes</html:option>
											<html:option value='N'>No</html:option>
										</html:select>

									</td>
								</tr>
							</logic:equal>
					<logic:notEqual property="purposeType" name="Form3ReportBean" value="SUPERANNUATION">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Whether any part final has been taken past
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>

									<html:select styleClass="TextField" property="chkwthdrwlinfo" style="width:119px">
										<html:option value='Y'>Yes</html:option>
										<html:option value='N'>No</html:option>
									</html:select>

								</td>
							</tr>
						</logic:notEqual>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Enclosure are in order
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:select styleClass="TextField" property="chkwthdrwlflag" style="width:119px" onchange="listOfDocs();">
										<html:option value='Y'>Yes</html:option>
										<html:option value='N'>No</html:option>
									</html:select>
							</tr>

							<%
										  String chk1="",chk2="",chk3="",chk4="",chk5="",chk6="";
										%>

							<logic:equal property="advanceType" value="PFW" name="Form3ReportBean">
								<logic:equal property="purposeType" value="HBA" name="Form3ReportBean">
									<tr id="lodrow">
										<td colspan="4">
											<logic:present name="LODList">
												<logic:iterate name="LODList" id="lod">

													<logic:equal name="lod" property="lodInfo" value="TDPS">
														<%chk1="checked";%>
													</logic:equal>



													<logic:equal name="lod" property="lodInfo" value="NEDSHP">
														<%chk2="checked";%>
													</logic:equal>


													<logic:equal name="lod" property="lodInfo" value="AVPSH">
														<%chk3="checked";%>
													</logic:equal>

													<logic:equal name="lod" property="lodInfo" value="ECACH">
														<%chk4="checked";%>
													</logic:equal>


													<logic:equal name="lod" property="lodInfo" value="SCP">
														<%chk5="checked";%>
													</logic:equal>


													<logic:equal name="lod" property="lodInfo" value="AO">
														<%chk6="checked";%>
													</logic:equal>


												</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1">
												<tr>
													<td class="tableSideText">
														1.Title Deed of proposed seller
													</td>
													<td>
														<input type="checkbox" name="LODHBA" value="TDPS" <%=chk1%>>

													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														2.Non-encumbrance certificate in respect of the dwelling
														<br />
														site/house to be purchased
													</td>
													<td>

														<input type="checkbox" name="LODHBA" value="NEDSHP" <%=chk2%>>

													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														3.Agreement with the vendor for the purchase of site/house
													</td>
													<td>
														<input type="checkbox" name="LODHBA" value="AVPSH" <%=chk3%>>

													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														4.Estimate of the cost of construction in the case of advance
														<br />
														for the construction of the house.
													</td>
													<td>
														<input type="checkbox" name="LODHBA" value="ECACH" <%=chk4%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														5.Sanctioned construction plan
													</td>
													<td>
														<input type="checkbox" name="LODHBA" value="SCP" <%=chk5%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														6.Any others
													</td>
													<td>
														<input type="checkbox" name="LODHBA" value="AO" <%=chk6%>>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<bean:define id="loddocument" property="lodInfo" name="Form3ReportBean" />

								</logic:equal>
							</logic:equal>
							<logic:equal property="advanceType" value="PFW" name="Form3ReportBean">
								<logic:equal property="purposeType" value="MARRIAGE" name="Form3ReportBean">
									<tr id="lodrow">
										<td colspan="4">
											<logic:present name="LODList">
												<logic:iterate name="LODList" id="lod">
													<logic:equal name="lod" property="lodInfo" value="PFWMIS">
														<%chk1="checked";%>
													</logic:equal>



													<logic:equal name="lod" property="lodInfo" value="PFWPID">
														<%chk2="checked";%>
													</logic:equal>


													<logic:equal name="lod" property="lodInfo" value="PFWARVT">
														<%chk3="checked";%>
													</logic:equal>


												</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1">

												<tr>
													<td class="tableSideText">
														1.Marriage invitation card.
													</td>
													<td>
														<input type="checkbox" name="LODPFWMARR" value="PFWMIS" <%=chk1%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														2.Proof of age and Dependency.
													</td>
													<td>
														<input type="checkbox" name="LODPFWMARR" value="PFWPID" <%=chk2%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														3.Attested copy of Ration card/Voter’s Identity card/Tenth class certificate.
													</td>
													<td>
														<input type="checkbox" name="LODPFWMARR" value="PFWARVT" <%=chk3%>>
													</td>
												</tr>


											</table>
										</td>
									</tr>
									<bean:define id="loddocument" property="lodInfo" name="Form3ReportBean" />
								</logic:equal>
							</logic:equal>
							<logic:equal property="advanceType" value="PFW" name="Form3ReportBean">
								<logic:equal property="purposeType" value="HE" name="Form3ReportBean">
									<tr id="lodrow">
										<td colspan="4">
											<logic:present name="LODList">
												<logic:iterate name="LODList" id="lod">
													<logic:equal name="lod" property="lodInfo" value="PFWHEPAD">
														<%chk1="checked";%>
													</logic:equal>



													<logic:equal name="lod" property="lodInfo" value="PFWPSAFI">
														<%chk2="checked";%>
													</logic:equal>


													<logic:equal name="lod" property="lodInfo" value="PFWEECPY">
														<%chk3="checked";%>
													</logic:equal>

													<logic:equal name="lod" property="lodInfo" value="PFWDLI">
														<%chk4="checked";%>
													</logic:equal>

													<logic:equal name="lod" property="lodInfo" value="PFWDTCSTY">
														<%chk5="checked";%>
													</logic:equal>
												</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1">
												<tr>
													<td class="tableSideText">
														1.Proof of Age & Dependency
													</td>
													<td>
														<input type="checkbox" name="LODPFWHE" value="PFWHEPAD" <%=chk1%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														2.Proof of selection/admission from the institute.
													</td>
													<td>
														<input type="checkbox" name="LODPFWHE" value="PFWPSAFI" <%=chk2%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														3.Estimated expenditure of the course per year
													</td>
													<td>
														<input type="checkbox" name="LODPFWHE" value="PFWEECPY" <%=chk3%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														4.Demand Letter from the institute.
													</td>
													<td>
														<input type="checkbox" name="LODPFWHE" value="PFWDLI" <%=chk4%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														5.Duration of the course should be 3 year or more.
													</td>
													<td>
														<input type="checkbox" name="LODPFWHE" value="PFWDTCSTY" <%=chk5%>>
													</td>
												</tr>

											</table>
										</td>
									</tr>
									<bean:define id="loddocument" property="lodInfo" name="Form3ReportBean" />
								</logic:equal>
							</logic:equal>
							
							
							<logic:equal property="advanceType" value="PFW" name="Form3ReportBean">
								<logic:equal property="purposeType" value="SUPERANNUATION" name="Form3ReportBean">
									<tr id="lodrow">
										<td colspan="4">
											<logic:present name="LODList">
												<logic:iterate name="LODList" id="lod">
													<logic:equal name="lod" property="lodInfo" value="PFWAAY">
														<%chk1="checked";%>
													</logic:equal> 
													<logic:equal name="lod" property="lodInfo" value="PFWOYBARDS">
														<%chk2="checked";%>
													</logic:equal> 
 
												</logic:iterate>
											</logic:present>
											<table align="center" cellpadding="1" cellspacing="1">
												<tr>
													<td class="tableSideText">
														1.Attaining of age of 54 Years
													</td>
													<td>
														<input type="checkbox" name="LODPFWSA" value="PFWAAY" <%=chk1%>>
													</td>
												</tr>
												<tr>
													<td class="tableSideText">
														2.One Year before actual retirement date of superannuation.
													</td>
													<td>
														<input type="checkbox" name="LODPFWSA" value="PFWOYBARDS" <%=chk2%>>
													</td>
												</tr>
											 
											</table>
										</td>
									</tr>
									<bean:define id="loddocument" property="lodInfo" name="Form3ReportBean" />
								</logic:equal>
							</logic:equal>
							
							
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
									<html:text styleClass="TextField" property="subscriptionAmt"></html:text>
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
									<html:text styleClass="TextField" property="contributionAmt"></html:text>
								</td>
							</tr>



							<input type="hidden" name="purposeOptionType" value="<bean:write property="purposeOptionType" name="Form3ReportBean"/>">
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

