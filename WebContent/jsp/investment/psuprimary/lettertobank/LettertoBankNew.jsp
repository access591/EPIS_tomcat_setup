<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Investment Procedure - Request For Quotation</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Request For Quotation" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/GeneralFunctions.js"></script>

		<script type="text/javascript">
			var httpRequest; 
			function validate(){
				if(document.forms[0].refNo.value==""){
				    alert("Please Enter The Proposal Reference No. (Mandatory)");
				    document.forms[0].refNo.focus();
				    return false;
			    }
			    if(document.forms[0].securityCategory.value=="")
			    {
			    	alert("Please Enter the Security Category (Mandatory)");
			    	document.forms[0].securityCategory.focus();
			    	return false;
			    }
			    if(document.forms[0].mode.value=="psuprimary")
			    {
			    if(document.forms[0].accountNo.value=="") {
				  	alert("Please Select  Account No.(Mandatory)");
				  	document.forms[0].accountNo.focus();
				  	return false;
			    }
			    if(document.forms[0].bankName.value=="")
			    {
			    	alert("Please Enter The BankName (Mandatory)");
			    	document.forms[0].bankName.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].bankName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].bankName.focus();
				return false;
				}
				
				if(document.forms[0].branchName.value=="")
			    {
			    	alert("Please Enter The BranchName (Mandatory)");
			    	document.forms[0].branchName.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].branchName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].branchName.focus();
				return false;
				}
				
				/*if(document.forms[0].accountType.value=="")
			    {
			    	alert("Please Enter The Account Type (Mandatory)");
			    	document.forms[0].accountType.focus();
			    	return false;
			    }*/
			    
			    if(document.forms[0].ifscCode.value=="")
			    {
			    	alert("Please Enter The IFCCode (Mandatory)");
			    	document.forms[0].ifscCode.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].ifscCode.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].ifscCode.focus();
				return false;
				}		
			  	
				if(document.forms[0].bankDate.value=="")
				{
					alert("Please Enter Bank Date(Mandotory)");
					document.forms[0].bankDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].bankDate)){
					document.forms[0].bankDate.select();
					return(false);
				}
				
				
				if(document.forms[0].beneficiaryName.value=="") {
					alert("Please Enter The Benificiary Name  (Mandatory)");
					document.forms[0].beneficiaryName.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].beneficiaryName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].beneficiaryName.focus();
				return false;
				}	
								
				if(document.forms[0].creditAccountNo.value=="")
				{
					alert("Please Enter  Credit Account No (Mandatory)");
					document.forms[0].creditAccountNo.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].creditAccountNo.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].creditAccountNo.focus();
				return false;
				}
				
				
				
				
				
				if(document.forms[0].centerLocation.value=="")
				{
					alert("Please Enter  Center Location (Mandatory)");
					document.forms[0].centerLocation.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].centerLocation.value))
				{
				alert("Please Enter Center Location(Expecting Alpha Numeric Value)");
				document.forms[0].centerLocation.focus();
				return false;
				}
				
				if(document.forms[0].remitanceAmt.value=="")
					{
						alert("Please Enter Remitance Amount(Mandatory)");
						document.forms[0].remitanceAmt.focus();
						return(false);
					}
					if(document.forms[0].remitanceAmt.value!="" && parseFloat(document.forms[0].remitanceAmt.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].remitanceAmt.value,15,2)) {
						alert("Please Enter Valid Remitance Amount (Expecting  Numeric Value)");
						document.forms[0].remitanceAmt.focus();
						document.forms[0].remitanceAmt.select();
						return false;
					}
				}
									
				if(document.forms[0].remarks.value=="")
				{
					alert("Please Enter  Remarks (Mandatory)");
					document.forms[0].remarks.focus();
					return false;
				}
			}
			if(document.forms[0].mode.value=="rbiauction")
			{
				if(document.forms[0].sglAccountNo.value=="")
			    {
			    	alert("Please Enter SGL AccountNo (Mandatory)");
			    	document.forms[0].sglAccountNo.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].sglAccountNo.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].sglAccountNo.focus();
				return false;
				}
				
				if(document.forms[0].sellerRefNo.value=="")
			    {
			    	alert("Please Enter Seller RefNo (Mandatory)");
			    	document.forms[0].sellerRefNo.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].sellerRefNo.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].sellerRefNo.focus();
				return false;
				}
				
				if(document.forms[0].faceValue.value=="") {
					alert("Please Enter The Face Value (Mandatory)");
					document.forms[0].faceValue.focus();
					return false;
				}
				
				
				if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].faceValue.value,15,2)) {
						alert("Please Enter Valid Face Value  (Expecting  Numeric Value)");
						document.forms[0].faceValue.focus();
						document.forms[0].faceValue.select();
						return false;
					}
				}
				
				if(document.forms[0].noofdays.value=="")
			    {
			    	alert("Please Enter NoofDays From Last Date (Mandatory)");
			    	document.forms[0].noofdays.focus();
			    	return false;
			    }
			    if(document.forms[0].noofdays.value!="" && parseFloat(document.forms[0].noofdays.value)!=0) {
					if(!ValidateNum(document.forms[0].noofdays.value)) {
						alert("Please Enter Valid No.of days(Expecting  Numeric Value)");
						document.forms[0].noofdays.focus();
						document.forms[0].noofdays.select();
						return false;
					}
				}
				
				
				
				if(document.forms[0].accuredInterestAmount.value=="")
			    {
			    	alert("Please Enter Accured Interest Amount (Mandatory)");
			    	document.forms[0].accuredInterestAmount.focus();
			    	return false;
			    }
			    if(document.forms[0].accuredInterestAmount.value!="" && parseFloat(document.forms[0].accuredInterestAmount.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].accuredInterestAmount.value,15,2)) {
						alert("Please Enter Valid Accured Interest Amount  (Expecting  Numeric Value)");
						document.forms[0].accuredInterestAmount.focus();
						document.forms[0].accuredInterestAmount.select();
						return false;
					}
				}
				
				
				if(document.forms[0].settlementAmount.value=="")
			    {
			    	alert("Please Enter Settlement Amount (Mandatory)");
			    	document.forms[0].settlementAmount.focus();
			    	return false;
			    }
			    if(document.forms[0].settlementAmount.value!="" && parseFloat(document.forms[0].settlementAmount.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].settlementAmount.value,15,2)) {
						alert("Please Enter Valid Settlement Amount  (Expecting  Numeric Value)");
						document.forms[0].settlementAmount.focus();
						document.forms[0].settlementAmount.select();
						return false;
					}
				}
			
					
			}
				
				if(document.forms[0].leftSign.value!="")
				{
					if(!ValidateAlphaNumeric(document.forms[0].leftSign.value))
					{
					alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
					document.forms[0].leftSign.focus();
					return false;
					}
				}
				
				if(document.forms[0].rightSign.value!="")
				{
					if(!ValidateAlphaNumeric(document.forms[0].rightSign.value))
					{
					alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
					document.forms[0].rightSign.focus();
					return false;
					}
				}
				
				
					  
			}
			
			
		
			function  getbankDetails(){
				
				document.forms[0].bankName.value='';
				document.forms[0].branchName.value='';
				document.forms[0].accountType.value='';
				document.forms[0].accountTypedef.value='';
				
				
				
				if(!document.forms[0].accountNo.value=="")
			{
			
				var url;
			
			
				var url="/jsp/investment/psuprimary/lettertobank/LoadBankDetails.jsp?accountNo="+document.forms[0].accountNo.value;
			
			
				sendURL(url,"loadBankDetails");
				
			}
			}
			function loadBankDetails()
			{
				if (httpRequest.readyState == 4)
				{ 
				if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("master")[0];
				
				if(node) 
					{
						
					var bankName=node.childNodes[0].getElementsByTagName("BankName")[0].firstChild.nodeValue;				
					var branchName=node.childNodes[0].getElementsByTagName("BranchName")[0].firstChild.nodeValue;
					var accountType=node.childNodes[0].getElementsByTagName("AccountType")[0].firstChild.nodeValue;
					var accountTypedeff=node.childNodes[0].getElementsByTagName("AccountTypDef")[0].firstChild.nodeValue;
					document.forms[0].bankName.value=bankName;
					document.forms[0].branchName.value=branchName;
					document.forms[0].accountType.value=accountType;
					document.forms[0].accountTypedef.value=accountTypedeff;
					}
					}	
				}			
			}
			
		
			function  LoadTrustType() {
				
				document.forms[0].trustType.value='';
				document.forms[0].marketTypedef.value='';
				document.forms[0].marketType.value='';
				document.forms[0].securityCategory.value='';
				document.forms[0].amountInv.value='';
				if(document.forms[0].mode.value=='rbiauction')
				{
				document.forms[0].securityName.value='';
				document.forms[0].dealDate.value='';
				document.forms[0].settlementDate.value='';
				document.forms[0].faceValue.value='';
				document.forms[0].offeredPrice.value='';
				document.forms[0].principalAmount.value='';
				}
				
				
			if(!document.forms[0].refNo.value=="")
			{
			
				var url;
			
			
				var url="jsp/investment/quotation/LoadTrust.jsp?proposalRefNo="+document.forms[0].refNo.value+"&mode=lettertobank&modetype="+document.forms[0].mode.value;
			
			
				sendURL(url,"getTrustType");
			}
			}
			function getTrustType()
			{
			
			if (httpRequest.readyState == 4)
			{ 
			if(httpRequest.status == 200) 
				{ 			
				
				var node = httpRequest.responseXML.getElementsByTagName("master")[0];
				
				
				if(node) 
					{
						
						var trustType=node.childNodes[0].getElementsByTagName("TrustType")[0].firstChild.nodeValue;				
						var securityCategory=node.childNodes[0].getElementsByTagName("securityCategory")[0].firstChild.nodeValue;
						var market=node.childNodes[0].getElementsByTagName("MarketType")[0].firstChild.nodeValue;
						var marketdef=node.childNodes[0].getElementsByTagName("MarketDesc")[0].firstChild.nodeValue; 
						var investamt=node.childNodes[0].getElementsByTagName("investamount")[0].firstChild.nodeValue;
						if(document.forms[0].mode.value=='rbiauction')
						{
						var securityname=node.childNodes[0].getElementsByTagName("securityName")[0].firstChild.nodeValue;
						var dealdate=node.childNodes[0].getElementsByTagName("DealDate")[0].firstChild.nodeValue;
						var settlementdate=node.childNodes[0].getElementsByTagName("SettlementDate")[0].firstChild.nodeValue;
						var faceValue=node.childNodes[0].getElementsByTagName("FaceValue")[0].firstChild.nodeValue;
						var offeredprice=node.childNodes[0].getElementsByTagName("offeredPrice")[0].firstChild.nodeValue;
						var principalamount=node.childNodes[0].getElementsByTagName("PrincipalAmount")[0].firstChild.nodeValue;
						}
						
										
						document.forms[0].trustType.value=trustType;
						document.forms[0].securityCategory.value=securityCategory;
						document.forms[0].marketTypedef.value=marketdef;
						document.forms[0].marketType.value=market;
						document.forms[0].amountInv.value=investamt;
						
						if(document.forms[0].mode.value=='rbiauction')
						{
						
						document.forms[0].securityName.value=securityname;
						
						document.forms[0].dealDate.value=dealdate;
						document.forms[0].settlementDate.value=settlementdate;
						document.forms[0].faceValue.value=faceValue;
						document.forms[0].offeredPrice.value=offeredprice;
						document.forms[0].principalAmount.value=principalamount;
						}

					}	
				
					
										
				}
				}
				
				
			}
	
	
			function initialize() {
				var xmlhttpreq1;
				try {
					xmlhttpreq1=new XMLHttpRequest(); //For Mozilla, Opera
				} catch(e) {
					try {
						xmlhttpreq1=new ActiveXObject("Msxml2.XMLHTTP"); //For IE6.0
					} catch(e) {
						try {
							xmlhttpreq1=new ActiveXObject("Microsoft.XMLHTTP"); //For IE5.5+
						}
						catch(e) {
							alert("Browser doesn't support Ajax");
						}
					}
				}
				return xmlhttpreq1;
			}
	
			function clearData(){
	       		window.document.forms[0].reset();
	  		}
	  		
	function sendURL(url,responseFunction)
	{        
	
		if (window.ActiveXObject) {			// for IE  
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
		} else if (window.XMLHttpRequest) { // for other browsers  
			httpRequest = new XMLHttpRequest(); 
		}

		if(httpRequest) {
			
			httpRequest.open("GET", url, true);  //2nd arg is url with name/value pairs, 3 specify asynchronus communication
			httpRequest.onreadystatechange = eval(responseFunction) ; //which will handle the callback from the server side element
			httpRequest.send(null); 
		}
	}
		function caluculateSettlementAmount()
		{
			
			var settlementamount=0;
			if(document.forms[0].principalAmount.value!=""&& parseFloat(document.forms[0].principalAmount.value)!=0)
			{
				if(!ValidateFloatPoint(document.forms[0].faceValue.value,15,2)){
				document.forms[0].principalAmount.focus();
				document.forms[0].principalAmount.select();
				return false;
			}
			}
			
			
			
			
			
			if(document.forms[0].accuredInterestAmount.value!=""&& parseFloat(document.forms[0].accuredInterestAmount.value)!=0)
			{
				if(!ValidateFloatPoint(document.forms[0].accuredInterestAmount.value,15,2)){
				document.forms[0].accuredInterestAmount.focus();
				document.forms[0].accuredInterestAmount.select();
				return false;
			}
			}
			
			if(document.forms[0].principalAmount.value!=""&&document.forms[0].accuredInterestAmount.value!="")
			{
				
					settlementamount=parseFloat(document.forms[0].principalAmount.value)+parseFloat(document.forms[0].accuredInterestAmount.value);
					
					document.forms[0].settlementAmount.value=parseFloat(settlementamount);
					
			}
		}
		
		
		  		
  		
		</script>
	</head>
	<body onload="document.forms[0].refNo.focus();">
		<html:form action="/addlettertobank.do?method=addletterbank" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("lettertobank.title")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.proposalrefNo" bundle="common" />
						:
					</td>
					
					
					<td align=left width="25%">
						<html:select property="refNo" onchange="LoadTrustType();" styleClass="TextField" style="width:200 px" tabindex="1">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="proposalList" property="proposalRefNo" labelProperty="proposalRefNo" />
						</html:select>

					</td>
					
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="trustType" maxlength="5" style="width: 120px;" styleClass="TextField" tabindex="2" readonly="true" />
					</td>
					</tr>
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="securityCategory" style="width: 120px;" readonly="true" tabindex="3" styleClass="TextField" />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.markettype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="marketTypedef" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="4" readonly="true" />
						<html:hidden property="marketType" />
						<html:hidden property="amountInv"/>
						

					</td>
					
					</tr>
					<logic:equal name="modeType" scope="request" value="psuprimary">
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.bankname" bundle="common"/>:
					</td><td align=left width="25%" >
					<html:select property="accountNo"   styleClass="TextField" style='width:230px' tabindex="5">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="banks" property="accountNo" labelProperty="bankDetails" />
                                             
                        </html:select>
                        </td>
				
					<td colspan=2>&nbsp;</td>
					
									
				</tr>
				<tr>
				<td colspan=4 align="center" class="tableText" ><bean:message key="lettertobank.beneficiarydetails" bundle="common"/>
				</tr>
				<tr>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.bankname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="bankName" maxlength="50"  style="width: 120px;"  tabindex="6" styleClass="TextField" />
						


					</td>
					
				
				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.branchname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="branchName" styleClass="TextField" style="width:120 px" maxlength="50" tabindex="7"  />
					</td>
									
				</tr>
				<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="lettertobank.accounttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    
						
						<html:select property="accountType"   styleClass="TextField" style='width:130px' tabindex="8">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="accountypelist" property="key" labelProperty="value" />
                                             
                        </html:select>
                       </td>
                       
                       
                       <td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.ifsccode" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="ifscCode" styleClass="TextField" style="width:120 px" maxlength="11" tabindex="9"  />
					</td>
                       
				</tr>
				<tr>
				
		<td width="25%" class="tableTextBold" align=right nowrap>
<font color=red>*</font>
<bean:message key="lettertobank.bankdate" bundle="common" />
</td>
<td align=left width="25%">
<html:text property="bankDate" styleId="bankDate" style="width: 95px;" tabindex="10" styleClass="TextField" />
						<html:link href="javascript:showCalendar('bankDate');" tabindex="11">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
</td>
										<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="lettertobank.beneficiaryname" bundle="common" />
						:
					</td>
					<td align=left width="25%" >
						<html:text property="beneficiaryName" size="12" styleClass="TextField" style="width:160 px" tabindex="12" maxlength="100"/>

					</td>
				</tr>
			<tr>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.creditaccountno" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="creditAccountNo" styleClass="TextField" style="width:120 px" maxlength="20" tabindex="13"  />
						

					</td>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font>
					<bean:message key="lettertobank.centerlocation" bundle="common"/>:</td>
					<td align="left" width="25%" nowrap="nowrap">
					
					<html:text size="12" property="centerLocation" styleClass="TextField" style="width:120 px"  tabindex="14" maxlength="50"  />
					</td>
				</tr>
				
				<tr>
					
					<td  width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.remitanceamount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text size="12" property="remitanceAmt" styleClass="TextField" style="width:160 px" maxlength="16" tabindex="15" />
					</td>
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.remarks" bundle="common" />
					</td>
					
					<td align=left width="25%">
						<html:textarea property="remarks" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].remarks.value,999)" tabindex="16" />

					</td>

				</tr>
				</logic:equal>
				<logic:equal name="modeType" scope="request" value="rbiauction">
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.sglaccountno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="sglAccountNo" style="width: 120px;" readonly="true" tabindex="5" styleClass="TextField" value="SG020092" />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.sellerrefno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="sellerRefNo" styleClass="TextField" style="width:120 px" maxlength="20" tabindex="6" />
						
					</td>
					
					</tr>
					
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.dealdate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="dealDate" style="width: 120px;" readonly="true" tabindex="7" styleClass="TextField" />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.settlementdate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="settlementDate" styleClass="TextField" style="width:120 px"  tabindex="8" readonly="true" />
						
					</td>
					
					</tr>
					
					
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="securityName" style="width: 120px;" readonly="true" tabindex="9" styleClass="TextField"  />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.facevalueamount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="faceValue" styleClass="TextField" style="width:120 px"  tabindex="10" readonly="true" />
						
					</td>
					
					</tr>
					
					
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.offeredprice" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="offeredPrice" style="width: 120px;" readonly="true" tabindex="11" styleClass="TextField"  />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.principalamount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="principalAmount" styleClass="TextField" style="width:120 px"  tabindex="12" readonly="true" />
						
					</td>
					
					</tr>
					
					
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.noofdaysfromlastdate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="noofdays" style="width: 120px;" maxlength="3" tabindex="13" styleClass="TextField"  />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="lettertobank.accuredinterestamount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="accuredInterestAmount" onblur="caluculateSettlementAmount();" style="width: 120px;" maxlength="16" tabindex="14" styleClass="TextField"  />
						


					</td>
					
					
					
					</tr>
					
					<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.settlementamount" bundle="common" />
						:
						</td>
						<td align=left width="25%">
					    <html:text size="12" property="settlementAmount" styleClass="TextField" style="width:120 px" maxlength="16" readonly="true" tabindex="15"  />
						
						</td>
						<td width="50%" class="tableTextBold" colspan=2>&nbsp;</td>
						
					
					</tr>
				
				</logic:equal>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.leftsign" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="leftSign" styleClass="TextField" style="width:120 px" maxlength="25" tabindex="17"  />
						

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.rightsign" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="rightSign" styleClass="TextField" style="width:120 px" maxlength="25" tabindex="18"  />
						

					</td>
				
				</tr>

				
					
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;<input type="hidden" name="mode" value="<bean:write name="modeType" scope="request"/>"/></td>
				</tr>

				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="19">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="20" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchLettertoBank.do?method=searchLettertoBank&mode='+document.forms[0].mode.value" onblur="document.forms[0].refNo.focus();" tabindex="21" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
