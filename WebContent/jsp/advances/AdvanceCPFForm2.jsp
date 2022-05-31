<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE> New Document </TITLE>
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
</HEAD>

<BODY>
<!--
/*
  * File       : AdvanceCPFForm2.jsp
  * Date       : 09/26/2009
  * Author     : Suresh Kumar Repaka 
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->
<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			String basePathWoView = basePathBuf.toString();
			String srcFrmName="";
		if (request.getParameter("srcFrmName") != null) {
			srcFrmName = request.getParameter("srcFrmName");
		}
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
		<script type="text/javascript" src="<%=basePath%>js/commonfunctions.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/datetime.js"></script>
		<script type="text/javascript"><!--
		var flag='N';
		var ex=/^[0-9.]+$/;
		function validate(){ 
				 		 
				if(document.forms[0].mnthsemoluments.value==""){
		        alert("Please Enter Six/three months of Emoluments ");
		        document.forms[0].mnthsemoluments.focus();
		        return false;		        
		        }
		        
		        if (!ex.test(document.forms[0].mnthsemoluments.value) && document.forms[0].mnthsemoluments.value!="")
		    	{
				  alert("Six/three months of Emoluments shoud be Numeric");
				 document.forms[0].mnthsemoluments.select();
				 return false;
		    	}		
		        
		        if(document.forms[0].empshare.value==""){
		        alert("Please Enter 50% of Employee share  ");
		        document.forms[0].empshare.focus();
		        return false;		        
		        }	
		         
		        if (!ex.test(document.forms[0].empshare.value) && document.forms[0].empshare.value!="")
		    	{
				  alert("50% of Employee share shoud be Numeric");
				 document.forms[0].empshare.select();
				 return false;
		    	}
		       
		         if (document.forms[0].mthinstallmentamt.value=="")
		    	{
				  alert("Please Enter Amount of Installment per month");
				 document.forms[0].mthinstallmentamt.focus();
				 return false;
		    	}
		        
		         if (!ex.test(document.forms[0].mthinstallmentamt.value) && document.forms[0].mthinstallmentamt.value!="")
		    	{
				  alert("Amount of Installment per month shoud be Numeric");
				 document.forms[0].mthinstallmentamt.select();
				 return false;
		    	}
		    	
		    	return true;
		      
	}
		function frmPrsnlReset(frmName){
			var pensionNo=document.forms[0].pensionNo.value;
			var transID=document.forms[0].advanceTransIDDec.value;
			var formType="Y";
			var url="<%=basePath%>advanceForm2.do?method=loadAdvanceForm2&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frm_formType="+formType+"&frm_name="+frmName;
		 	 
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		function saveForm2(frmName){	
		      
		       
		       var verifiedby="";
		       var recAmt=document.forms[0].amntrcdedrs.value;			
			  
			   
		  		if(!validate()){
					return false;
		  		}		       	
		  		
		  		verifiedby=document.forms[0].verifiedby.value;
		  		
		  		if(frmName=='CPFApproval'){
		  		         flag="NewForm";
		  		}else{			
			if(verifiedby=='PERSONNEL'){
			   flag="NewForm";
			}else{
				flag="EditForm";		
			}	  		
			}	
			 
		 	
		 	var bankname,empname,accno,rtgscode;
		 	bankname=document.forms[0].bankempname.value;
		 	empname=document.forms[0].bankname.value;
		 	accno=document.forms[0].banksavingaccno.value;
		 	rtgscode=document.forms[0].bankemprtgsneftcode.value;
		 	
		 	//alert(bankname+'--'+empname+'--'+accno+'--'+rtgscode);
		 	
		 	if(bankname!=''||empname!=''||accno!=''||rtgscode!=''){
		 	document.forms[0].paymentinfo.value='Y';
		 	} 
			
			
			
			//Amount of Installment per month   
		var advnceapplid,recommendedAmt,monInstAmtonApprovedAmnt,monInstAmt,noofinstallments ;
			 
		noofinstallments = parseFloat(document.forms[0].totalInst.value);
		advnceapplid=parseFloat(document.forms[0].advnceapplid.value);
		recommendedAmt=parseFloat(document.forms[0].amntrcdedrs.value);
	  	monInstAmtonApprovedAmnt=parseInt(recommendedAmt/noofinstallments);	
	  	monInstAmt = document.forms[0].mthinstallmentamt.value;
	  	
	  	 
	  	if(!calLastMntIntrst(monInstAmt)){
					return false;
		  		}
	  		 //alert(advnceapplid+'-advnceapplid-'+recommendedAmt+'--recommendedAmt');
	  	 // alert(monInstAmtonApprovedAmnt+'-monInstAmtonApprovedAmnt-'+monInstAmt+'--monInstAmt');
	  		if( monInstAmtonApprovedAmnt!=monInstAmt){
		        alert("Installment per month Sholud be Calculate on Amount Recommended ");
		        document.forms[0].mthinstallmentamt.focus();
		        return false;
			}
		 
			
		      //  if(flag=="N"){
		        
		      //   calculateMinAmt();
		     //   }else{
				url="<%=basePathWoView%>advanceForm2.do?method=saveCPFForm2&frm_name="+frmName+"&rec_amt="+recAmt+"&frmFlag="+flag+"&bankflag="+bankFlag+"&srcFrmName="+'<%=srcFrmName%>';
				//alert(url);
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
				//}
		}
		
		function setFlagStatus(){
		     if(flag=="Y"){
		       flag='N';
		     }
		}
		
		function calculateMinAmt(){
			if(!validate()){
					return false;
		  		}
		  
			var emoluments,subscription,advance,result;
			var outstndamount=0;
			emoluments=parseFloat(document.forms[0].mnthsemoluments.value);
			subscription=parseFloat(document.forms[0].empshare.value);
			advance=parseFloat(document.forms[0].advnceapplid.value);
			
		    if(document.forms[0].outstndamount.value!=''){
			outstndamount=parseFloat(document.forms[0].outstndamount.value);
			}
				
			if((emoluments<subscription) && (emoluments<advance)){
			    result=emoluments;			     
			}else  if(subscription<advance){
			  result=subscription;
			}else{
			    result=advance;
			    
			  }		  
		  document.forms[0].amntrcdedrs.value=result-outstndamount;
		  document.forms[0].amntapprvdrs.value=result-outstndamount;
		  
		//  alert('recomm--'+ document.forms[0].amntrcdedrs.value+'appr----'+ document.forms[0].amntapprvdrs.value);
		  
		  flag='Y';
		  
		}
		
		
		
	function calInstallAmt(){
	    
	  var advapplied=0,noofinstallments=0,monInstAmt=0,interestinstallments=0,interestRate=0;
	  advapplied=document.forms[0].advnceapplid.value;
	  noofinstallments=document.forms[0].totalInst.value;
	  interestRate=document.forms[0].interestRate.value;
	
	 //Amount of Installment per month   
	
	  monInstAmt=parseInt(advapplied/noofinstallments);	
	  document.forms[0].mthinstallmentamt.value=monInstAmt;
	  
	  //calculating No of Interest Installments  
	  
	  if(noofinstallments<=12)
	    interestinstallments=1;
	   else if(noofinstallments>12 && noofinstallments<=24)
	   interestinstallments=2;
	   else if(noofinstallments>24 && noofinstallments<=36)
	   interestinstallments=3; 
	   
	   document.forms[0].interestinstallments.value=interestinstallments;
	   
	  //calculating  Amount of Interest Installments  
	  
	  var interestInstall=parseInt((advapplied*interestRate/100)*interestinstallments);
	    document.forms[0].intinstallmentamt.value=interestInstall;
	  
	}
	
	function calLastMntIntrst(mthinstallmentamt){
	 var noofinstallments=0,mthinstallmentamt=0,totInstalmentAmnt=0,recommendedAmt=0,diffinIntrstAmnt=0;
	 var advnceapplid,recommendedAmt,monInstAmtonApprovedAmnt,monInstAmt,noofinstallments,lastmnthIntrstAmnt=0,noofintrestmnths=0 ;
		
		 if (document.forms[0].mthinstallmentamt.value=="")
		    	{
				  alert("Please Enter Amount of Installment per month");
				 document.forms[0].mthinstallmentamt.focus();
				 return false;
		    	}
		        
		         if (!ex.test(document.forms[0].mthinstallmentamt.value) && document.forms[0].mthinstallmentamt.value!="")
		    	{
				  alert("Amount of Installment per month shoud be Numeric");
				 document.forms[0].mthinstallmentamt.select();
				 return false;
		    	}
		    	
			 
		noofinstallments = parseFloat(document.forms[0].totalInst.value);
		advnceapplid=parseFloat(document.forms[0].advnceapplid.value);
		recommendedAmt=parseFloat(document.forms[0].amntrcdedrs.value);
	  	monInstAmtonApprovedAmnt=parseInt(recommendedAmt/noofinstallments);	
	  	monInstAmt = document.forms[0].mthinstallmentamt.value;
	  	 //alert(advnceapplid+'-advnceapplid-'+recommendedAmt+'--recommendedAmt');
	  	 // alert(monInstAmtonApprovedAmnt+'-monInstAmtonApprovedAmnt-'+monInstAmt+'--monInstAmt');
	  		 if( monInstAmtonApprovedAmnt!=monInstAmt){
		        alert("Installment per month Sholud be Calculate on Amount Recommended ");
		        document.forms[0].mthinstallmentamt.focus();
		        return false;
			} 
	   //alert(document.forms[0].intinstallmentamt.value+'-intinstallmentamt-');
	   if (document.forms[0].intinstallmentamt.value=="" || document.forms[0].intinstallmentamt.value==0)
		    	{
				  alert("Please Enter Amount of Interest Installments");
				 document.forms[0].intinstallmentamt.focus();
				 return false;
		    	}
		        
		         if (!ex.test(document.forms[0].intinstallmentamt.value) && document.forms[0].intinstallmentamt.value!="")
		    	{
				  alert("Amount of Interest Installments shoud be Numeric");
				 document.forms[0].intinstallmentamt.select();
				 return false;
		    	}
	  noofintrestmnths= parseInt(document.forms[0].interestinstallments.value);
	  ///lastmnthIntrstAmnt = parseInt(document.forms[0].intinstallmentamt.value)  ;
	  mthinstallmentamt=parseFloat(document.forms[0].mthinstallmentamt.value);
	  totInstalmentAmnt = mthinstallmentamt*noofinstallments;	  
	   
		diffinIntrstAmnt = recommendedAmt -totInstalmentAmnt; 
		
		
		//alert("totInstalmentAmnt="+totInstalmentAmnt+"=recommendedAmt="+recommendedAmt+"=diffinIntrstAmnt=="+diffinIntrstAmnt);
		if(diffinIntrstAmnt!=0){
		document.forms[0].lastmthinstallmentamt.value = (mthinstallmentamt+diffinIntrstAmnt);
		}else{
		document.forms[0].lastmthinstallmentamt.value = mthinstallmentamt;
		}
		return true;
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
		
	function advancesForm2Report(transID,pensionNo,advanceType,transDate,apprdstatus){	     
			var formType="N";
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			
			if(apprdstatus=='N'){
				alert(transID+' Should be approved.');
			}else{
			var url="<%=basePath%>advanceForm2.do?method=loadCPFVerificationForm&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmTransDate="+transDate+"&frm_formType="+formType;
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}		
	}
		
	  function advancesForm2Report(frmName){	     
			var formType="N";
			var swidth=screen.Width-10;
			var sheight=screen.Height-150;
			var transID,pensionNo,transDate;
			var apprdstatus='A',url='';
		transID=document.forms[0].advanceTransIDDec.value;
		pensionNo=document.forms[0].pensionNo.value;
		advanceType=document.forms[0].advanceType.value;
		transDate=document.forms[0].advntrnsdt.value;
			
			if(frmName=='CPFRecommendation'){
			url="<%=basePath%>advanceForm2.do?method=loadCPFVerificationForm&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmTransDate="+transDate+"&frm_formType="+formType;
			
			}
			if(frmName=='CPFApproval'){
			frmName='CPFRecommendation';
			url="<%=basePath%>advanceForm2.do?method=loadAdvanceForm2&frmPensionNo="+pensionNo+"&frmTransID="+transID+"&frmTransDate="+transDate+"&frm_formType="+formType+"&frm_name="+frmName;		
			}				
			
			if(apprdstatus=='N'){
				alert(transID+' Should be approved.');
			}else{
			wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
	   	 	winOpened = true;
			wind1.window.focus();
			}		
		}
		
var bankinfo;
function loadBankDetails(frmName){
//alert(frmName);
if(frmName=="CPFApproval"){
document.getElementById("BankDetailsHeadwoedit").style.display = 'block'; 
document.getElementById("BankDetailsHead").style.display = 'none'; 
bankinfo=='N';
}else{
document.getElementById("BankDetailsHeadwoedit").style.display = 'none'; 
document.getElementById("BankDetailsHead").style.display = 'block'; 
}
bankinfo=document.forms[0].paymentinfo.value; 
if(bankinfo=='Y'){
  
document.getElementById("bankempname").readOnly=true;
document.getElementById("bankname").readOnly=true;
document.getElementById("banksavingaccno").readOnly=true;
document.getElementById("bankemprtgsneftcode").readOnly=true;
} else{  
document.getElementById("BankDetails").style.display = 'block'; 
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
 		--></script>
	</head>

	<body class="bodybackground" onload="loadBankDetails('<bean:write name="advanceBean" property="frmName"/>');">

		<html:form method="post" action="advanceForm2.do?method=loadAdvanceForm2">




			<logic:equal name="advanceBean" property="frmName" value="CPFApproval">
				<%=ScreenUtilities.screenHeader("CPF Form Approval")%>
			</logic:equal>
			<logic:equal name="advanceBean" property="frmName" value="CPFRecommendation">
				<%=ScreenUtilities.screenHeader("CPF Recommendation Form")%>
			</logic:equal>

			<table width="720" border="0" cellspacing="3" cellpadding="0">

				<tr>
					<td width="15%">
						&nbsp;
					</td>
					<td class="tableTextBold" align="right" width="35%">
						Key ID&nbsp;
					</td>
					<td width="2%">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="advntrnsid" readonly="true" />
						&nbsp;
						<img src="<%=basePath%>images/viewDetails.gif" border="no" onclick="javascript:advancesForm2Report('<bean:write name="advanceBean" property="frmName"/>')" />
						<!-- 	<input type="text" name="empcode" />-->
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						PF ID&nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:hidden property="advanceType" />
						<html:hidden property="advanceTransIDDec" />
						<html:hidden property="purposeType" />
						<html:hidden property="pensionNo" />
						<html:hidden property="outstndamount" />
						<html:hidden property="prpsecvrdclse" />
						<html:hidden property="advntrnsdt" />
						<html:hidden property="verifiedby" />
						<html:hidden property="paymentinfo"/>	
						<html:text styleClass="TextField" property="pfid" tabindex="1" readonly="true" />
					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Name&nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="employeeName" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Designation&nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="designation" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						No of Installment Recommended &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="totalInst" readonly="true" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount of Installment per month &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="mthinstallmentamt" />
						<html:hidden property="interestRate" />
					</td>
				</tr>


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Six/three months of Emoluments &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="mnthsemoluments" maxlength="15"  readonly="true" onchange="setFlagStatus()" />
					</td>
				</tr>


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						50% of Employee share &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="empshare" maxlength="15" onblur="calculateMinAmt()" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Advance Applied &nbsp;
					</td>
					<td>
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
						No of Interest Installments &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="interestinstallments" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount of Interest Installments &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="intinstallmentamt" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount Recommended Rs. &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="amntrcdedrs" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount Approved Rs. &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="amntapprvdrs" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Last Month  Installment Amount Rs. &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="lastmthinstallmentamt"  readonly="true"/>
					</td>
				</tr>
				<!--    commented  0n 19-Jan-2012 and default Accept <tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Authorized Status &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:select styleClass="TextField" property="authorizedsts">
							<html:option value='A'>Accept</html:option>
							<html:option value='R'>Reject</html:option>

						</html:select>
					</td>
				</tr> -->
				<html:hidden property="authorizedsts" value="A"/>
				<tr>         
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Authorized Remarks &nbsp;
					</td>
					<td>
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="authorizedrmrks" maxlength="150" />
					</td>
				</tr> 
				
				
									<tr id="BankDetailsHead">
						 	 				<td colspan="1" class="tableTextBold" nowrap="nowrap">
											BANK DETAILS FOR PAYMENT:   <img alt="" src="<%=basePath%>images/icon-edit.gif" onclick="javascript:editBankDetails();" />
											</td>	
											 						
										</tr>
										 <tr id="BankDetailsHeadwoedit">
						 	 				<td colspan="1" class="tableTextBold" nowrap="nowrap">
											BANK DETAILS FOR PAYMENT:    
											</td>	
											 						
										</tr>
										
											<tr id="BankDetails">																						
													
												<td colspan="5">
												<table align="center" border="0">
																								
										<tr>										     
											<td class="tableTextBold">Name of Employee as per Saving Bank Account:</td>
											<td><html:text styleClass="TextField"  property="bankempname" name="bankMasterBean"/></td>
											 
											 <td class="tableTextBold">Name Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankname" name="bankMasterBean"/></td>
										</tr>
										
										
										<tr>					   
											<td class="tableTextBold">Bank A/c No:</td>
											<td><html:text styleClass="TextField"  property="banksavingaccno" name="bankMasterBean"/></td>
																						
											<td class="tableTextBold" width="20%">IFSC Code Of Bank:</td>
											<td><html:text styleClass="TextField"  property="bankemprtgsneftcode" name="bankMasterBean"/></td>
										</tr>
										 
										 
										<tr>
										<td>&nbsp;</td>
										</tr>
												</table>
											 </td>
											 </tr>
				
				 
			</table>


			<table align="center">


				<tr>

					<td align="left" id="submit">
						<input type="button" class="butt" value="Submit" onclick="saveForm2('<bean:write name="advanceBean" property="frmName"/>');" tabindex="10" />
					</td>
					<td align="right">

						<input type="button" class="butt" value="Reset" onclick="javascript:frmPrsnlReset('<bean:write name="advanceBean" property="frmName"/>')" class="btn" tabindex="11" />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="12" />
					</td>
				</tr>



			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>


</BODY>
</HTML>