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
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();

%>
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
		function saveForm2(){
				
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
				
				
				url="<%=basePath%>loadApproval.do?method=savePFWForm2&bankflag="+bankFlag;
				document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
		}
		
		function reportAdvances()
		{
		
		var transID,pensionNo;
		transID=document.forms[0].advntrnsid.value;
		pensionNo=document.forms[0].pensionNo.value;
		
			
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
	    var url="<%=basePath%>loadAdvance.do?method=advanceReport&frmPensionNo="+pensionNo+"&frmTransID="+transID;
		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
   	 	winOpened = true;
		wind1.window.focus();
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
</SCRIPT>

	</head>

	<body class="bodybackground" onload="loadBankDetails();">

		<html:form method="post" action="loadApproval.do?method=savePFWForm2">
			<%=ScreenUtilities.screenHeader("PFW (Part Final Withdraw) Verfication Form-II")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0">


				<tr>
					<td width="22%">
						&nbsp;
					</td>
					<td width="24%" class="tableTextBold">
						KEY NO
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="advanceTransID" readonly="true"></html:text>
						&nbsp;
						<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:reportAdvances()" />
						<html:hidden property="advntrnsid" name="Form2ReportBean" />
						<html:hidden property="pensionNo" name="Form2ReportBean" />
						<html:hidden property="advanceType" name="Form2ReportBean" />
						<html:hidden property="paymentinfo" name="Form2ReportBean" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Employee code
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="employeeNo" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Employee Name
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="employeeName" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Designation
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="designation" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Father&rsquo;s Name
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="fhName" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Department
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="department" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Date of joining in AAI
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="dateOfJoining" readonly="true"></html:text>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Date of Birth
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:text name="Form2ReportBean" styleClass="TextField" property="dateOfBirth" readonly="true"></html:text>
					</td>
				</tr> 
				 
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Taken Loan
					</td>
					<td align="center">
						:
					</td>
					<td class="reportdata">
						<html:select styleClass="TextField" property="takenloan">
							<html:option value='Y'>Yes</html:option>
							<html:option value='N'>No</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Verified By &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td class="tableSideTextBold">
						Personnel
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Authorized Status &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:select styleClass="TextField" property="authorizedsts">
							<html:option value='A'>Accept</html:option>
							<html:option value='R'>Reject</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold">
						Remarks &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="authorizedrmrks" maxlength="150" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>


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
								 
								<td align="left" id="submit">
									<input type="button" class="btn" value="Submit" onclick="saveForm2();" tabindex="9" />
								</td>
								<td align="right">
									<input type="reset" class="btn" value="Reset" class="btn" tabindex="10" />
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="11" />
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

