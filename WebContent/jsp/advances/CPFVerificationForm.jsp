<!--
/*
  * File       : CPFVerificationForm.jsp
  * Date       : 17/12/2009
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
String basePathWoView = basePathBuf.toString();
%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
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
		<script type="text/javascript">
		var flag='N';	
		function saveForm2(){
		var recommendAmt=0,mnthlyEmoluments=0,advnceapplid=0,purposeType="";	 	
		  		  recommendAmt=parseInt(document.forms[0].amntrcdedrs.value);
		  		  mnthlyEmoluments=parseInt(document.forms[0].userTransMnthEmolunts.value);
		  		  purposeType = document.forms[0].purposeType.value;
		  		  advnceapplid = parseInt(document.forms[0].advnceapplid.value);
		  		 // alert("advnceapplid=="+advnceapplid+" =mnthlyEmoluments=="+mnthlyEmoluments+"===recommendAmt=="+recommendAmt);
		  		  
		  		  if(purposeType=="OTHERS"){
		  		  if(recommendAmt<=0){
		  		  alert("Amount to be Recommended should not be Zero");
		  		  document.forms[0].amntrcdedrs.focus();
		  		  		return false;
		  		  
		  		  }
		  		  	
		  		  	if(recommendAmt>advnceapplid){
		  		  	alert("Amount to be Recommended should not be greater than Amount Applied");
		  		  		document.forms[0].amntrcdedrs.focus();
		  		  		return false;
		  		  	}
		  		  	
		  		  	if(recommendAmt<mnthlyEmoluments){
		  		  	if(recommendAmt!=advnceapplid){	
		  		  	if(recommendAmt>advnceapplid){
		  		  	alert("Amount to be Recommended should not be greater than Amount Applied");
		  		  		document.forms[0].amntrcdedrs.focus();
		  		  		return false;
		  		  	}else if(recommendAmt!=mnthlyEmoluments){
		  		  		alert("Amount to be Recommended should not less than Monthly Emolument");
		  		  		document.forms[0].amntrcdedrs.focus();
		  		  		return false;
		  		  	}
		  		  }
		  		  }
		  		  }
				url="<%=basePathWoView%>advanceForm2.do?method=saveCPFVerification";
				
	   		    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
				
		}
		
		function frmPrsnlReset(){
			var pensionNo=document.forms[0].pensionNo.value;
			var TransID=document.forms[0].advanceTransIDDec.value;
			var formType="Y";
			var url="<%=basePath%>advanceForm2.do?method=loadCPFVerificationForm&frmPensionNo="+pensionNo+"&frmTransID="+TransID+"&frm_formType="+formType;
			 
		 	document.forms[0].action=url;
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		
		
	function calInstallAmt(){
	    
	  var advapplied=0,noofinstallments=0,monInstAmt=0,interestinstallments=0,interestRate=0,recommendAmt=0,totInstalmentAmnt=0,diffinIntrstAmnt=0;
	  advapplied=document.forms[0].advnceapplid.value;
	  noofinstallments=document.forms[0].totalInst.value;
	  //interestRate=document.forms[0].interestRate.value;
	  recommendAmt=document.forms[0].amntrcdedrs.value;
	 //Amount of Installment per month   
	
	  monInstAmt=parseInt(recommendAmt/noofinstallments);	
	  document.forms[0].mthinstallmentamt.value=monInstAmt;
	  
	  //calculating No of Interest Installments  
	  
	  if(noofinstallments<=12){
	    interestinstallments=1;
	    interestRate=4;
	   }else if(noofinstallments>12 && noofinstallments<=24){
	   interestinstallments=2;
	    interestRate=8;
	   }else if(noofinstallments>24 && noofinstallments<=36){
	   interestinstallments=3; 
	    interestRate=12;
	   }
	   document.forms[0].interestinstallments.value=interestinstallments;
	   
	  //calculating  Amount of Interest Installments  
	  
	  //var interestInstall=parseInt((advapplied*interestRate/100)*interestinstallments);
	   var interestInstall=parseInt((recommendAmt*interestRate/100)/interestinstallments);
	// alert("==recommendAmt==="+recommendAmt+"==interestRate"+interestRate+"interestinstallments="+interestinstallments);
	    document.forms[0].intinstallmentamt.value=interestInstall;
	  
	  
	  totInstalmentAmnt = monInstAmt*noofinstallments;	  
	  diffinIntrstAmnt = recommendAmt -totInstalmentAmnt; 
		
		
		//alert("totInstalmentAmnt="+totInstalmentAmnt+"=recommendedAmt="+recommendedAmt+"=diffinIntrstAmnt=="+diffinIntrstAmnt);
		if(diffinIntrstAmnt!=0){
		document.forms[0].lastmthinstallmentamt.value = (monInstAmt+diffinIntrstAmnt);
		}else{
		document.forms[0].lastmthinstallmentamt.value = monInstAmt;
		}
	  
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
	
 		</script>
	</head>

	<body class="bodybackground" onload="calInstallAmt();">

		<html:form method="post" action="advanceForm2.do?method=loadAdvanceForm2">
			<%=ScreenUtilities.screenHeader("CPF Form Verification")%>
			<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">


				<tr>
					<td width="15%">
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Key ID&nbsp;
					</td>
					<td width="2%" align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="advntrnsid" readonly="true" />
						&nbsp; &nbsp;
						<img src="<%=basePath%>/images/viewDetails.gif" border="no" onclick="javascript:reportAdvances()" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						PF ID&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:hidden property="advntrnsid"/>
						<html:hidden property="advanceType" />						 
						<html:hidden property="pensionNo" />
						<html:hidden property="advanceTransIDDec" /> 
						<html:hidden property="lastmthinstallmentamt" />  
						<html:hidden property="userTransMnthEmolunts" />
						 
						<html:text styleClass="TextField" property="pfid" tabindex="1" readonly="true" />
					</td>

				</tr>


				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Name(IN BLOCK LETTERS) &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="empnamewthblk" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Designation&nbsp;
					</td>
					<td align="center">
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
						Date of Joining in Fund&nbsp;
					</td>
					<td align="center">
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
					<td class="tableTextBold" align="right">
						Previous advance outstanding&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="outstndamount" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Purpose of Advance &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="purposeType" readonly="true" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Purpose covered clause &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:select styleClass="TextField" property="prpsecvrdclse">
							<html:option value='29.1.1'>29.1.1</html:option>
							<html:option value='29.1.2a'>29.1.2a</html:option>
							<html:option value='29.1.2b'>29.1.2b</html:option>
							<html:option value='29.1.3'>29.1.3</html:option>
							<html:option value='29.1.4'>29.1.4</html:option>
							<html:option value='29.1.5'>29.1.5</html:option>
						</html:select>
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Six/Three months Emoluments &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="mnthsemoluments"  readonly="true"/>
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						50% of Employee share&nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="empshare" />
						<html:hidden property="advnceapplid" />
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Total No of Installments &nbsp;
					</td>
					<td align="center">
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
						Amount to be Recommended &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="amntrcdedrs"  onblur="calInstallAmt();"/>
					</td>
				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
						Amount of Per month Installment&nbsp;
					</td>
					<td align="center">
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
						No of Interest Installments &nbsp;
					</td>
					<td align="center">
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
						Amount of interest installments(Per month) &nbsp;
					</td>
					<td align="center">
						:
					</td>
					<td>
						<html:text styleClass="TextField" property="intinstallmentamt" />
					</td>
				</tr>



				<!--  commented  0n 19-Jan-2012 and default Accept <tr>
					<td>
						&nbsp;
					</td>
					<td class="tableTextBold" align="right">
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
				</tr> -->
		<html:hidden property="authorizedsts" value="A"/>

				<tr>
					<td colspan="4">
						<table width="720" border="0" cellspacing="3" cellpadding="0" align="center">

							<tr>
								<td width="50%" align="right" id="submit">
									<input type="button" class="butt" value="Submit" onclick="saveForm2();" tabindex="10" />
								</td>
								<td align="left" width="50%">

									<input type="button" class="butt" value="Reset" onclick="javascript:frmPrsnlReset()" class="btn" tabindex="11" />
									<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" class="btn" tabindex="12" />
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

