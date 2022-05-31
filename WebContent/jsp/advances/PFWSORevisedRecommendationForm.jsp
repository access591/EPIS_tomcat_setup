 <!--
/*
  * File       : PFWSORevisedRecommendationForm.jsp
  * Date       : 24-May-2012
  * Author     : Radha P
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
     
		function saveDetails(frmName){				 
				var purposeType=document.forms[0].purposeType.value;				
			 
				var date2;
				var day1, day2;
				var month1, month2;
				var year1, year2;
			     
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
						
			 
				 
		         
		    var bankname,empname,accno,rtgscode,partyname,partyaddr;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	 
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	 }
			   
			url="<%=basePath%>loadApproval.do?method=updatePFWRevisedDetails&frm_name="+frmName+"&bankflag="+bankFlag;
			//alert(url);
			document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
				
		
		}
		function frmPrsnlReset(frmName){
			var pensionNo=document.forms[0].pensionNo.value;
			var advanceTransID=document.forms[0].advntrnsid.value;
			var purposeType='PFW';			 
			var url="<%=basePath%>loadApproval.do?method=loadPFWRevisedDetails&frm_name=PFWRevised&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransID+"&frmPurposeType="+purposeType+"&frmName="+frmName;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
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
document.getElementById("bankempname").readOnly=true;
document.getElementById("bankname").readOnly=true;
document.getElementById("banksavingaccno").readOnly=true;
document.getElementById("bankemprtgsneftcode").readOnly=true;
   
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
bankFlag='Y';
}
}

function loadDet(){
var purposeType = document.getElementById("purposeType").value; 
if(purposeType=='HBA' || purposeType=='SUPERANNUATION'){
document.getElementById("purposeDet").style.display="block";
}else{
document.getElementById("purposeDet").style.display="none";
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

	<body onload="loadBankDetails();loadDet();">

		<html:form method="post" action="loadApproval.do?method=updatePFWRevisedDetails">
			<%=ScreenUtilities.screenHeader("PFW Revised Sanction Order Recommendation")%>
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
									<html:text styleClass="TextField" property="advanceTransIDDec" name="Form2ReportBean" readonly="true" />
									 
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
									<html:hidden property="paymentinfo" name="Form2ReportBean" />	
									<html:hidden property="advanceTransID" name="Form2ReportBean" />
									<html:hidden property="revAdvanceTransID" name="Form2ReportBean" />										 
									<input type="hidden" name="advanceType" value="<bean:write property="advanceType" name="Form2ReportBean"/>" />  
									<input type="hidden" name="purposeType" value="<bean:write property="purposeType" name="Form2ReportBean"/>"/>
									<html:text styleClass="TextField" property="pensionNo" name="Form2ReportBean"  readonly="true" />
								</td>

							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Sanction Date
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="sanctiondate"  name="Form2ReportBean"  readonly="true" />

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									Sanction Order No
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="pfwSanctionOrderNo"  name="Form2ReportBean"  readonly="true" />

								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									purpose Type
								</td>
								<td width="2%">
									:
								</td>
								<td>
									<html:text styleClass="TextField" property="purposeType" name="Form2ReportBean"    readonly="true"   />
								</td>
							</tr> 
							<tr id="purposeDet">
								<td>
									&nbsp;
								</td>
								<td class="tableTextBold">
									 Property Address
								</td>
								<td width="2%" align="center">
									:
								</td>
								<td>
								<html:text styleClass="TextField"   property="propertyaddress"  name="basicReportBean" />
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
									<html:text styleClass="TextField" property="narration"  name="Form2ReportBean" />
								</td>
							</tr>
	 
							<!-- ----Bank Details----> 
			<tr>
			<td colspan="5">
			<table>
				 <tr>
							<td>
								<table>
								
								<tr> 
									
									<td class="tableTextBold">
									 BANK  DETAILS  FOR PAYMENT   <img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="javascript:editBankDetails();" />
									 
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
							
							 
							</table> 
						</td>		
					 	</tr>
							<!-- ----Bank Details---->

							
							 
							<tr>
								<td align="center" colspan="4">
									<table align="center">
										<tr>
											<td>
												&nbsp;
											</td>
											<td align="right" id="submit">
												<input type="button" class="btn" value="Submit" onclick="javascript:saveDetails('<bean:write name="advanceBean" property="frmName" />')" tabindex="9" />
											</td>
											<td align="right">
												<input type="button" class="btn" value="Reset" onclick="javascript:frmPrsnlReset('<bean:write name="advanceBean" property="frmName" />')" class="btn" tabindex="10" />
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

 