<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>

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
		
			function validate(){
				if(document.forms[0].proposalRefNo.value==""){
				    alert("Please Enter The Proposal Reference No. (Mandatory)");
				    document.forms[0].proposalRefNo.focus();
				    return false;
			    }
			    if(document.forms[0].surplusFund.value=="") {
				  	alert("Please Enter The Surplus Fund (Mandatory)");
				  	document.forms[0].surplusFund.focus();
				  	return false;
			    }
			  	if(document.forms[0].surplusFund.value!="" && parseFloat(document.forms[0].surplusFund.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].surplusFund.value,14,2)) {
						alert("Please Enter Valid Surplus Fund (Expecting  Numeric Value)");
						document.forms[0].surplusFund.focus();
						document.forms[0].surplusFund.select();
						return false;
					}
				}
				if(document.forms[0].marketType.value==""){
					alert("Please Enter The Market Type (Mandatory)");
					document.forms[0].marketType.focus();
					return false;
				}
				if(document.forms[0].arrangers.value=="") {
					alert("Please Enter The Arrangers  (Mandatory)");
					document.forms[0].arrangers.focus();
					return false;
				}
				
				if(document.forms[0].minimumQuantum.value==""){
					alert("Please Enter The Minimum Quantum Fund (Mandatory)");
					document.forms[0].minimumQuantum.focus();
					return false;
				}
				if(document.forms[0].minimumQuantum.value!="" && parseFloat(document.forms[0].minimumQuantum.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].minimumQuantum.value,14,2)) {
						alert("Please Enter Valid Minimum Quantum Fund (Expecting  Numeric Value)");
						document.forms[0].minimumQuantum.focus();
						document.forms[0].minimumQuantum.select();
						return false;
					}
				}
				if(document.forms[0].quotationDate.value==""){
					alert("Please Enter The Quotation Date (Mandatory)");
					document.forms[0].quotationDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].quotationDate)){
					document.forms[0].quotationDate.select();
					return(false);
				}
				if(document.forms[0].quotationTime.value==""){
					alert("Please Enter The quotationTime (Mandatory)");
					document.forms[0].quotationTime.focus();
					return false;
				}
				if(document.forms[0].quotationTime.value!=""){
					if(!validateTime24Hour(document.forms[0].quotationTime.value)){
						alert("Please Enter Valid Estimated Time (Expecting in HHMM format)");
						document.forms[0].quotationTime.focus();
						return(false);
					}	
				}
				if(document.forms[0].validDate.value=="") {
					alert("Please Enter The Valid Date (Mandatory)");
					document.forms[0].validDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].validDate)){
					document.forms[0].validDate.select();
					return(false);
				}
				if(document.forms[0].validTime.value=="") {
				    alert("Please Enter The ValidTime (Mandatory)");
				    document.forms[0].validTime.focus();
				    return false;
			    }		
				if(document.forms[0].validTime.value!="") {
					if(!validateTime24Hour(document.forms[0].validTime.value)) {
						alert("Please Enter Valid Estimated Time (Expecting in HHMM format)");
						document.forms[0].validTime.focus();
						return(false);
					}
				}
				if(document.forms[0].openDate.value=="") {
					alert("Please Enter The Open Date (Mandatory)");
					document.forms[0].openDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].openDate)){
					document.forms[0].openDate.select();
					return(false);
				}
				if(document.forms[0].openTime.value==""){
				    alert("Please Enter The OpenTime (Mandatory)");
				    document.forms[0].openTime.focus();
				    return false;
				}		
				if(document.forms[0].openTime.value!="") {
					if(!validateTime24Hour(document.forms[0].openTime.value)) {
						alert("Please Enter Valid Estimated Time (Expecting in HHMM format)");
						document.forms[0].openTime.focus();
						return(false);
					}
				}
				if(document.forms[0].fromPeriod.value=="")
				{
					alert("Please Enter The FromPeriod[Mandatory]");
					document.forms[0].fromPeriod.focus();
					return false;
				}
				if(document.forms[0].toPeriod.value=="")
				{
					alert("Please Enter The ToPeriod[Mandatory]");
					document.forms[0].toPeriod.focus();
					return false;
				}
				if(document.forms[0].marketType.value=="R" || document.forms[0].marketType.value=="O")
				{
					
					var count=0;
					for(var i=0; i<document.forms[0].arrangers.length; i++)
					{
						if(document.forms[0].arrangers[i].selected)
						count++;
					}
					if(count!=1)
					{
						alert("plese select one Arranger Only");
						document.forms[0].arrangers.focus();
						return(false);
					}
					if(document.forms[0].nameoftheTender.value=="")
					{
						alert("Please Enter The  NameoftheTenderer (Mandatory)");
						document.forms[0].nameoftheTender.focus();
						return(false);
					}
					if(!ValidateAlphaNumeric(document.forms[0].nameoftheTender.value))
					{
						alert("Please Enter The valid NameOfTheTenderer");
						document.forms[0].nameoftheTender.focus();
						return(false);
					}
					if(document.forms[0].addressoftheTender.value=="")
					{
						alert("Please Enter The AddressoftheTenderer(Mandatory)");
						document.forms[0].addressoftheTender.focus();
						return(false);
					}
					if(document.forms[0].telephoneNo.value=="")
					{
						alert("Please Enter The Telephone Number(Mandatory)");
						document.forms[0].telephoneNo.focus();
						return(false);
					}
					if(!ValidateNum(document.forms[0].telephoneNo.value))
					{
						alert("Please Enter Valid Telephone Number (Expecting  Numeric Value)");
						document.forms[0].telephoneNo.focus();
						return(false);
					}
					if(document.forms[0].faxNo.value!="")
					{
						if(!ValidateNum(document.forms[0].faxNo.value))
						{
							alert("please Enter Valid Fax Number (Expecting  Numeric Value)");
							document.forms[0].faxNo.focus();
							return(false);
						}
					}
					if(document.forms[0].contactPerson.value=="")
					{
						alert("Please Enter The Contact Person (Mandatory)");
						document.forms[0].contactPerson.focus();
						return(false);
					}
					if(!ValidateAlphaNumeric(document.forms[0].contactPerson.value))
					{
						alert("Please Enter Valid ContactPerson (Expecting Alpha Numeric Value)");
						document.forms[0].contactPerson.focus();
						return(false);
					} 
					if(document.forms[0].status.value=="")
					{
						alert("Please Enter The Status (Mandatory)");
						document.forms[0].status.focus();
						return(false);
					}
					if(!ValidateAlphaNumeric(document.forms[0].status.value))
					{
						alert("Please Enter Valid Status (Expecting Alpha Numeric Value)");
						document.forms[0].status.focus();
						return(false);
					}
					
					if(document.forms[0].deliveryRequestedin.value=="")
					{
						alert("Please Enter The DeliveryRequested (Mandatory)");
						document.forms[0].deliveryRequestedin.focus();
						return(false);
					}
					if(!ValidateAlphaNumeric(document.forms[0].deliveryRequestedin.value))
					{
						alert("Please Enter Valid DeliveryRequest (Expecting Alpha Numeric Value)");
						document.forms[0].deliveryRequestedin.focus();
						return(false);
					}
					
					if(document.forms[0].accountNo.value=="")
					{
						alert("Please Enter The AccountNO (Mandatory)");
						document.forms[0].accountNo.focus();
						return(false);
					}
					if(!ValidateAlphaNumeric(document.forms[0].accountNo.value))
					{
						alert("Please Enter Valid AccountNumber (Expecting Alpha Numeric Value)");
						document.forms[0].accountNo.focus();
						return(false);
					}					
					if(document.forms[0].faceValue.value =="" && !confirm("If The Coupon Rate is not Entered \n YTM Caluculation will not be done \n Would you like to continue... ")) {
						document.forms[0].faceValue.focus();
						return(false);				
					}
					if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0){
						if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)){
									document.forms[0].faceValue.focus();
									document.forms[0].faceValue.select();
									return false;
					}
					}
					
					if(document.forms[0].investmentFaceValue.value =="") {
					alert("Please Enter The Face Value (Mandatory)");
					document.forms[0].investmentFaceValue.focus();				
					return false;
					}
					if(document.forms[0].numberOfUnits.value =="") {
					alert("Please Enter The No. of Units (Mandatory)");
					document.forms[0].numberOfUnits.focus();				
					return false;
					}
					
					if(document.forms[0].securityFullName.value=="")
		    {
		    	alert("Please Enter The Security Name (Mandatory)");
		    	document.forms[0].securityFullName.focus();
		    	return false;
		    }
		    if(!ValidateSpecialAlphaNumeric(document.forms[0].securityFullName.value))
			{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].securityFullName.focus();
				return false;
			}
			/*if(document.forms[0].interestRate.value=="")
			{
				alert("Please Enter interestRate (Mandatory)");
				document.forms[0].interestRate.focus();
				return false;
			}*/
			if(document.forms[0].interestRate.value!="" && parseFloat(document.forms[0].interestRate.value)!=0){
				if(!ValidateFloatPoint(document.forms[0].interestRate.value,5,2)){
					document.forms[0].interestRate.focus();
					document.forms[0].interestRate.select();
					return false;
				}
			}
			/*if(document.forms[0].interestDate.value==""){
				alert("Please Enter The Interest Payment Date(Mandatory)");
				document.forms[0].interestDate.focus();
				return false;
			}*/
			if(document.forms[0].interestDate.value!="")
			{
			if(!convert_date(document.forms[0].interestDate)){
				document.forms[0].interestDate.select();
				return(false);
			}
			}
					
					
			/*if(document.forms[0].maturityDate.value=="")
			{
				alert("Please Enter Maturity Date (Mandatory)");
				document.forms[0].maturityDate.focus();
				return false;
			}*/
			
			if(document.forms[0].maturityDate.value!="")
			{
			if(!convert_date(document.forms[0].maturityDate)){
						document.forms[0].maturityDate.select();
						return(false);
					   }
			}
					
			}
					  
			}
			
			var xmlhttpreq;
		
			function  LoadTrustType() {
				document.forms[0].trustType.value='';
				document.forms[0].surplusFund.value='';
				document.forms[0].marketTypedef.value='';
				document.forms[0].marketType.value='';
				
				document.forms[0].securityCategory.value='';
				document.forms[0].securityName.value='';	
				if(!document.forms[0].proposalRefNo.value=="") 	{
					var url="jsp/investment/quotation/LoadTrust.jsp?proposalRefNo="+document.forms[0].proposalRefNo.value;
					xmlhttpreq=initialize();
					xmlhttpreq.open("GET",url,true); //The third parameter is a boolean value that controls the upcoming transaction aynchronously which means that the script processing carries on immediately after the send method is invoked with out waiting for response.  
					xmlhttpreq.send(null);
					xmlhttpreq.onreadystatechange=function() {
						if(xmlhttpreq.readyState==4) {
							if(xmlhttpreq.status==200) {
								parseMessages(xmlhttpreq.responseXML);
							}
							else {
								alert("There is a problem while retrieving the XML data");
							}
						}
					}
				}
			}
			function parseMessages(responseXML)
			{
				var Data=responseXML.getElementsByTagName("master")[0];		
				var childnodeslength=Data.childNodes.length;			
				if(childnodeslength!=0) {
					var trustType=Data.childNodes[0].getElementsByTagName("TrustType")[0];				
					var securityCategory=Data.childNodes[0].getElementsByTagName("securityCategory")[0];
					var securityId=Data.childNodes[0].getElementsByTagName("securityId")[0];
					var investamt=Data.childNodes[0].getElementsByTagName("investamount")[0];
					var market=Data.childNodes[0].getElementsByTagName("MarketType")[0];
					var marketdef=Data.childNodes[0].getElementsByTagName("MarketDesc")[0]; 
					var trust=trustType.childNodes[0].nodeValue;
					var security=securityCategory.childNodes[0].nodeValue;
					var securityCode=securityId.childNodes[0].nodeValue;
					var investamount=investamt.childNodes[0].nodeValue;
					var marketType=market.childNodes[0].nodeValue;
					var marketdescription=marketdef.childNodes[0].nodeValue;
					document.forms[0].trustType.value=trust;
					document.forms[0].securityCategory.value=securityCode;
					document.forms[0].securityName.value=security;
					document.forms[0].surplusFund.value=investamount;
					document.forms[0].minimumQuantum.value=investamount;
					document.forms[0].marketTypedef.value=marketdescription;
					document.forms[0].marketType.value=marketType;
										
				}
				
				displayData();
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
	  		function displayData()
	  		{
	  		    
	  		    if(document.forms[0].marketType.value=='R' || document.forms[0].marketType.value=='O')
	  		    {
	  		        document.getElementById('displaylabelmarketwise').style.display = 'inline';
	  		        document.getElementById('displaytimemarketwise').style.display = 'inline';
	  		        document.getElementById('displayData').style.display = 'inline';
	  		    	document.getElementById('displayNone').style.display = 'none';
	  		    	document.getElementById('displaylabel').style.display = 'none';
	  		    	document.getElementById('displaytime').style.display = 'none';
	  		    }
	  		    else
	  		    {
	  		    
	  		    document.getElementById('displayNone').style.display = 'inline';
	  		    document.getElementById('displayData').style.display ='none';
	  		    document.getElementById('displaylabelmarketwise').style.display = 'none';
	  		    document.getElementById('displaytimemarketwise').style.display = 'none';
	  		    document.getElementById('displaylabel').style.display = 'inline';
	  		    	document.getElementById('displaytime').style.display = 'inline';
	  		    }
	  		  
	  		}
	  		
/*function calFaceValue()
{
	var facevalueamt=0.0;
	if(document.forms[0].investmentFaceValue.value!="" && parseFloat(document.forms[0].investmentFaceValue.value)!=0){
		if(!ValidateFloatPoint(document.forms[0].investmentFaceValue.value,14,2)){
			document.forms[0].investmentFaceValue.focus();
			document.forms[0].investmentFaceValue.select();
			return false;
		}
	}
	if(document.forms[0].numberOfUnits.value!="" && parseFloat(document.forms[0].numberOfUnits.value)!=0){
		if(!ValidateFloatPoint(document.forms[0].numberOfUnits.value,14,2))	{
			document.forms[0].numberOfUnits.focus();
			document.forms[0].numberOfUnits.select();
			return false;
		}
	}
	if(document.forms[0].investmentFaceValue.value=="" || document.forms[0].investmentFaceValue.value==""){
		document.forms[0].investmentFaceValue.value="";
	}
	if(document.forms[0].investmentFaceValue.value!="" && document.forms[0].numberOfUnits.value!="")	{
		facevalueamt=parseFloat(document.forms[0].investmentFaceValue.value)*parseFloat(document.forms[0].numberOfUnits.value);
		document.forms[0].investmentFaceValue.value=parseFloat(facevalueamt);
	}
	return true;
}*/
function calPurchaseValue()
{
	var purchaseamt=0.0;
	var priceoffered=0.0;	
	
	if(document.forms[0].premiumPaid.value!="" && parseFloat(document.forms[0].premiumPaid.value)!=0){
		if(!ValidateFloatPoint(document.forms[0].premiumPaid.value,14,2)){
			document.forms[0].premiumPaid.focus();
			document.forms[0].premiumPaid.select();
			return false;
		}
	}
	if(document.forms[0].premiumPaid.value ==""){
		document.forms[0].premiumPaid.value = 0;
	}
	if(document.forms[0].investmentFaceValue.value !="" && document.forms[0].numberOfUnits.value != "" ){
		
			var unitfacevalue = parseFloat(document.forms[0].investmentFaceValue.value);
			var units = parseFloat(document.forms[0].numberOfUnits.value);
			var disc = 0.00;
			var paid = 0.00;
			
			if(document.forms[0].purchaseOption.value=='P')
			{
				priceoffered=unitfacevalue+parseFloat(document.forms[0].premiumPaid.value);
				purchaseamt= (unitfacevalue+parseFloat(document.forms[0].premiumPaid.value))*units;
				
			}
			if(document.forms[0].purchaseOption.value=='D')
			{
				priceoffered=unitfacevalue-parseFloat(document.forms[0].premiumPaid.value);
				purchaseamt= (unitfacevalue-parseFloat(document.forms[0].premiumPaid.value))*units;
			}
			
			document.forms[0].priceoffered.value=parseFloat(priceoffered);
			document.forms[0].purchasePrice.value=parseFloat(purchaseamt.toFixed(2));
		
	}
}
function displayPurchase()
{
	
	if(document.forms[0].purchaseOption.value=="P")
	{
		document.getElementById('premium').style.display = 'inline';
		document.getElementById('discount').style.display = 'none';
		
	}
	if(document.forms[0].purchaseOption.value=="D")
	{
		document.getElementById('premium').style.display = 'none';
		document.getElementById('discount').style.display = 'inline';
	}
	calPurchaseValue();
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
	function getTenureValue()
{
   
	var quotationdate=document.forms[0].quotationDate.value;
	var maturitydate=document.forms[0].maturityDate.value;
	if(quotationdate !=""&& maturitydate!="")
	{
		var url;
		url="jsp/investment/quotation/ArrangersLoad.jsp?mode=qTenure&quotationdate="+quotationdate+"&maturitydate="+maturitydate;
			
		
	sendURL(url,"loadTenure");
	}

}
function loadTenure()
{
	

	if (httpRequest.readyState == 4)
		{ 
			if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("master")[0];
											
   					if(node) 
					{
						var tenure = node.childNodes[0].getElementsByTagName("Tenure")[0].firstChild.nodeValue;
						document.forms[0].tenure.value=tenure;

					}
					
				}
		}

}
function loadPeriods()
{
	var fromPeriod=document.getElementById("fromPeriod");
	var toPeriod=document.getElementById("toPeriod");
	
	period=2011;
	for(i=1; i<=((2050-2011)+1); i++)
	{
		fromPeriod.options[i]=new Option(period,period);
		toPeriod.options[i]=new Option(period,period);
		period++;
	}
}
	  		
  		
		</script>
	</head>
	<body onload="document.forms[0].proposalRefNo.focus();displayData();displayPurchase();">
		<html:form action="/addQuotationRequest.do?method=addQuotationRequest" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("quotation.title")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.proposalrefno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:select property="proposalRefNo" onchange="LoadTrustType();displayData();" styleClass="TextField" style="width:200 px" tabindex="1">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="proposalRefList" property="refNo" labelProperty="refNo" />
						</html:select>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="trustType" maxlength="5" style="width: 120px;" styleClass="TextField" tabindex="2" readonly="true" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="securityName" style="width: 120px;" readonly="true" tabindex="3" styleClass="TextField" />
						<html:hidden property="securityCategory" />


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.surplusfund" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="surplusFund" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="4" readonly="true" />
						<bean:message key="quotation.fundformate" bundle="common" />

					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.marketType" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="marketTypedef" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="5" readonly="true" />
						<html:hidden property="marketType" />

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap rowspan=2>
						<font color=red>*</font>
						<bean:message key="quotation.arrangers" bundle="common" />
						:
					</td>
					<td align=left width="25%" rowspan=2>
						<html:select property="arrangers" styleClass="TextField" style="width:180 px;height:60 px;" multiple="true" size="3" tabindex="6">
							<html:options collection="arrangersList" property="arrangerCd" labelProperty="arrangerName" />
						</html:select>

					</td>
				</tr>
				<tr>
					<td width="30%" class="tableTextBold" align=right nowrap>
<font color=red>*</font>
<bean:message key="quotation.minimumquantumoffered" bundle="common" />
</td>
<td align=left width="20%">
<html:text size="12" property="minimumQuantum" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="7" />

</td>
				</tr>
				
				<tr>
					<td id="displaylabel" width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.quotationDate" bundle="common" />
						:
					</td>
					<td id="displaylabelmarketwise" width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.auctionDate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="quotationDate" styleId="quotationDate" style="width: 95px;" tabindex="8" styleClass="TextField" />
						<html:link href="javascript:showCalendar('quotationDate');" tabindex="9">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>

					</td>
					<td  id="displaytime" width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.quotationTime" bundle="common" />
					</td>
					<td id="displaytimemarketwise" width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.auctionTime" bundle="common" />
					</td>
					<td align=left width="25%">
						<html:text size="12" property="quotationTime" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="10" />

					</td>

				</tr>

				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.validuptodate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="validDate" styleId="validDate" style="width: 95px;" tabindex="11" styleClass="TextField" />
						<html:link href="javascript:showCalendar('validDate');" tabindex="12">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.validtime" bundle="common" />
					</td>
					<td align=left width="25%">
						<html:text size="12" property="validTime" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="13" />

					</td>

				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.openeddate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="openDate" styleId="openDate" style="width: 95px;" tabindex="14" styleClass="TextField" />
						<html:link href="javascript:showCalendar('openDate');" tabindex="15">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.openedtime" bundle="common" />
					</td>
					<td align=left width="25%">
						<html:text size="12" property="openTime" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="16" />

					</td>

				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.quantumsubmitaddress" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:textarea property="quotationAddress" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].quotationAddress.value,500)" tabindex="17" />

					</td>

					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.remarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="remarks" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.remarks1" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="formateRemarks" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.modeofpaymentremarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="modeOfPaymentRemarks" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
				
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.paymentthroughtremarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="paymentThroughRemarks" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
					
					<td width="50%" colspan=2>
					</td>
				
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
					<font color="red">*</font><bean:message key="quotation.fromperiod" bundle="common"/>
				</td>
				<td align=left width="25%">
					<html:select property="fromPeriod" styleClass="TextField" style="width:100 px" tabindex="19">
					<html:option value="">[SelectOne]</html:option>
					<html:options collection="periodList" property="code" labelProperty="name" />>
					</html:select>
				</td>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
					<font color="red">*</font><bean:message key="quotation.toperiod" bundle="common"/>
				</td>
				<td align=left width="25%">
					<html:select property="toPeriod" styleClass="TextField" style="width:100 px" tabindex="20">
					<html:option value="">[SelectOne]</html:option>
					<html:options collection="periodList" property="code" labelProperty="name" />
					</html:select>
				</td>
					
					
				</tr>

				<tr>
					<td colspan=4 align=center id="displayData">
						<table width="550" border=0  >
						
						<tr>
						  <td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.nameofthetenderer" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="nameoftheTender" style="width:90 px" tabindex="21" maxlength="30"/>
						</td>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.addressofthetenderer" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:textarea property="addressoftheTender" rows="3" cols="15" tabindex="22" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].addressoftheTender.value,500)"  />
						</td>
						
						</tr>
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.telephoneno" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="telephoneNo" style="width:90 px" tabindex="23" maxlength="10" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						
						<bean:message key="quotation.faxno" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:text property="faxNo" tabindex="24" style="width:90 px" maxlength="15" />
						</td>
						</tr>
						
						
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.contactperson" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="contactPerson" style="width:90 px" tabindex="25" maxlength="50" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.status" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:text property="status" tabindex="26" style="width:90 px" maxlength="30" />
						</td>
						</tr>
						
						
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.deliveryrequestedin" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="deliveryRequestedin" style="width:90 px" tabindex="27" maxlength="50" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.accountno" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:select property="accountNo" styleClass="TextField" tabindex="28" style='width:280px' >
							<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails"  />
							</html:select>
						</td>
						</tr>
						<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.couponrate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="faceValue" style="width: 120px;" tabindex="29" styleClass="TextField" />
					</td>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.facevalueinrs" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="investmentFaceValue" style="width:90px" styleClass="TextFieldFont" tabindex="30" onblur="calPurchaseValue();" />
						<html:hidden property="priceoffered"/>
					</td>
					</tr>
						<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.noofunits" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="numberOfUnits" style="width: 120px;" tabindex="31" styleClass="TextField" onblur="calPurchaseValue();" />
					</td>
							
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="securityFullName" style="width: 120px;" tabindex="32" styleClass="TextField" />
					</td>
						</tr>
						<tr>
							<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchasepriseoption" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					<html:select property="purchaseOption" onchange="displayPurchase();" styleClass="TextField" tabindex="33" style="width:100 px">
							
							<html:options collection="purchasePriseList" property="code"  labelProperty="name" />
						</html:select>
						
					</td>
					<td width="25%" class="tableTextBold" id="premium" align=right nowrap>
						<bean:message key="sq.premiumpaid" bundle="common" />
						:
					</td>
					<td width="25%" class="tableTextBold" id="discount" align=right nowrap>
						<bean:message key="sq.discount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="premiumPaid" style="width: 120px;" tabindex="34" styleClass="TextField" onblur="calPurchaseValue();" />
					</td>
					
					</tr>
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
					
						<bean:message key="sq.rateofinterest" bundle="common" />
						:
					</td>					
					<td width="25%" align=left>
						<html:text property="interestRate" style="width: 120px;" tabindex="35" styleClass="TextField" maxlength="6" />
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="interestDate" styleId="interestDate" style="width: 100px;" tabindex="36" styleClass="TextField" />
						<html:link href="javascript:showCalendar('interestDate');" tabindex="37">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					</tr>
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="purchasePrice" style="width:90px" tabindex="38" styleClass="TextFieldFont"  readonly="true" />
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="sq.maturitydate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="maturityDate" styleId="maturityDate" style="width: 100px;" tabindex="39" styleClass="TextField" />
						<html:link href="javascript:showCalendar('maturityDate');" tabindex="40">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					</tr>
					<!-- <tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.tenure" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="tenure" style="width: 120px;" tabindex="25" styleClass="TextField" readonly="true" />
					</td>
					<td width="25%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
					</tr>-->
						
						
						</table>
					</td>
				</tr>
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;</td>
				</tr>

				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="41">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="42" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchQuotationRequests.do?method=searchQuotationRequests'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="43" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
