<!--
/*
  * File       : AdvancePFWForm4.jsp
  * Date       : 07/11/2009
  * Author     : Suresh Kumar Repaka
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			 basePathBuf.append("://").append(request.getServerName()).append(":");
			 basePathBuf.append(request.getServerPort());
			 basePathBuf.append(request.getContextPath()).append("/");
			 
String basePath = basePathBuf.toString();
String basePathWoView = basePathBuf.toString();
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
		<script type="text/javascript" src="<%=basePath%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime.js"></script>
		<script type="text/javascript">
		function frmPrsnlReset(){
			var pensionNo=document.forms[0].pensionNo.value;
			var advanceTransID=document.forms[0].advntrnsid.value;
			var formType="Y";
			var url="<%=basePath%>form4Advance.do?method=advanceForm4Report&frmPensionNo="+pensionNo+"&frmTransID="+advanceTransID+"&frm_type="+formType;
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		function submitData(frmName){
		var bankFlag='N';			
		var url ="<%=basePathWoView%>form4Advance.do?method=savePFWForm4&frmName="+frmName+"&bankflag="+bankFlag;
		document.forms[0].action=url;
		document.forms[0].method="post";
		document.forms[0].submit();		
		
		}
		
	  function advancesForm4Report(){	
	       var flag='ApprovalReport';		       
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			
			var transID,pensionNo;
			var status='A';
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		advanceType=document.forms[0].advanceType.value;
			
			if(status=='N'){
			
				alert(transID+' Should be approved.');
			}else{	
		    var url="<%=basePath%>form4AdvanceVerification.do?method=advanceForm4VerificationReport&frmName=PFWForm3Report&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmAdvStatus="+status+"&frmFlag="+flag;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}
		}
		
		 var bankinfo,type;
	function loadBankDetails(){

	bankinfo=document.forms[0].paymentinfo.value;	 
	//alert(bankinfo);
	if(bankinfo=='Y'){ 
  	 
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
	 
	}
  
}
	
	 
		</script>
	</head>

	<body class="bodybackground" onload="loadBankDetails();">

		<html:form method="post" action="form4Advance.do?method=savePFWForm4">
			<%=ScreenUtilities.screenHeader("PFW (Part Final Withdraw) Final Approval")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0">


				<tr>
					<td width="20%">
						&nbsp;
					</td>
					<td class="tableTextBold" align="right" width="25%">
						PF ID
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td class="reportdata">
						<html:text styleClass="TextField" property="pfid" readonly="true" />
						&nbsp;
						<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:advancesForm4Report()" />
					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Key No
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">

						<html:hidden property="pensionNo" />
						<html:hidden property="advanceTransIDDec" />
						<html:hidden property="advanceType" />
						<html:hidden property="paymentinfo" />
						<html:text styleClass="TextField" property="advntrnsid" readonly="true" />
					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableSideTextBold" align="right">
						<u>Entitlement</u>
					</td>

					<td>
						&nbsp;
					</td>
					<td class="reportdata">
						&nbsp;
					</td>
				</tr>
				<logic:notEqual name="Form3ReportBean" property="purposeType" value="SUPERANNUATION">
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						<bean:write property="emolumentsLabel" name="advancePFWForm4" />
						&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="mnthsemoluments" />
					</td>
				</tr>
			</logic:notEqual>
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
						<html:text styleClass="TextField" property="empshare" readonly="true" />
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
						<html:text styleClass="TextField" property="contributionAmt" readonly="true" />
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
						<html:text styleClass="TextField" property="advnceapplid" readonly="true" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount sanctioned
					</td>
					<td align="center">
						:
					</td>

					<td class="reportdata">
						<html:text styleClass="TextField" property="amntrcdedrs" readonly="true"></html:text>
					</td>
				</tr>
				
			 
			 <tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						First Installment to be released Employee subscription Amount  
					</td>
					<td align="center">
						:
					</td>

					<td class="reportdata">
						<html:text styleClass="TextField" property="firstInsSubAmnt" name="Form3ReportBean" readonly="true"></html:text>
					</td>
				</tr>			 
			 
		
			  
			   <tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						First Installment to be released Employee Contribution Amount  
					</td>
					<td align="center">
						:
					</td>

					<td class="reportdata">
						<html:text styleClass="TextField" property="firstInsConrtiAmnt" name="Form3ReportBean" readonly="true"></html:text>
					</td>
				</tr>		
				
			
			 
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Approved
					</td>
					<td align="center">
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
					<td class="tableTextBold" align="right">
						Remarks
					</td>
					<td align="center">
						:
					</td>

					<td class="reportdata">
						<html:text styleClass="TextField" property="authorizedrmrks"></html:text>
					</td>
				</tr>
				<!-- <tr>
					  <td class="label" align="left" colspan="4">The amount of Rs.<bean:write property="amntRecommendedDscr" name="Form3ReportBean"/> (in word) is sanctioned provisionally under rule <bean:write property="prpsecvrdclse" name="Form3ReportBean"/> subject to rectification at the next board of trustee's meeting.
						            <td> 
					<td>
						&nbsp;
					</td>


				</tr> -->
				
				
				<!-- ----Bank Details----> 
			<tr>
			<td colspan="5">
			<table>
					 
							<tr>
							<td>
								<table>
								
								<tr id="editBankDet" style="display:none"> 
									
									<td class="tableTextBold">
									 BANK / PARTY DETAILS  FOR PAYMENT  
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
								<td align="left" id="submit">
									<input type="button" class="butt" value="Submit" tabindex="9" onclick="submitData('<bean:write name="advanceBean" property="frmName"/>');" />
								</td>
								<td align="right">
									<input type="button" class="butt" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="10" />
									<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="11" />
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

