
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>Investment - Submit Quotaion Data</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Submit Quotaion Data" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/ytm/bond.js"></script>
		<script type="text/javascript" language="javascript">
		var httpRequest; 
		function validate(){
			if(document.forms[0].proposalRefNo.value==""){
			    alert("Please Select Ref No.(Mandatory)");
			    document.forms[0].proposalRefNo.focus();
			    return false;
		    }
		    if(document.forms[0].arranger.value=="") {
			    alert("Please Select Arranger(Mandatory)");
			    document.forms[0].arranger.focus();
			    return false;
		    }
		    if(document.forms[0].securityName.value=="")
		    {
		    	alert("Please Enter Security Name (Mandatory)");
		    	document.forms[0].securityName.focus();
		    	return false;
		    }
		    if(!ValidateSpecialAlphaNumeric(document.forms[0].securityName.value))
			{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].securityName.focus();
				return false;
			}
			if(document.forms[0].faceValue.value =="") {
				alert("Please Enter Coupon Rate (Mandatory)");
				document.forms[0].faceValue.focus();				
				return false;
			}
			if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0){
						if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)){
									document.forms[0].faceValue.focus();
									document.forms[0].faceValue.select();
									return false;
					}
					}
			if(document.forms[0].investmentFaceValue.value =="") {
					alert("Please Enter Face Value (Mandatory)");
					document.forms[0].investmentFaceValue.focus();				
					return false;
					}
			
			if(document.forms[0].numberOfUnits.value !="") {
				if(!ValidateFloatPoint(document.forms[0].numberOfUnits.value,14,2)){
					document.forms[0].numberOfUnits.focus();
					document.forms[0].numberOfUnits.select();
					return false;
				}
				
			}
			
			/*if(document.forms[0].interestRate.value=="")
			{
				alert("Please Enter interestRate (Mandatory)");
				document.forms[0].interestRate.focus();
				return false;
			}
			if(document.forms[0].interestRate.value!="" && parseFloat(document.forms[0].interestRate.value)!=0){
				if(!ValidateFloatPoint(document.forms[0].interestRate.value,5,2)){
					document.forms[0].interestRate.focus();
					document.forms[0].interestRate.select();
					return false;
				}
			}
			if(document.forms[0].interestDate.value==""){
				alert("Please Enter Interest Payment Date(Mandatory)");
				document.forms[0].interestDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].interestDate)){
				document.forms[0].interestDate.select();
				return(false);
			}*/
			if(document.forms[0].interestDate.value==""){
				alert("Please Select Interest Payment Day(Mandatory)");
				document.forms[0].interestDate.focus();
				return false;
			}
			if(document.forms[0].interestMonth.value==""){
				alert("Please Select Interest Payment Month(Mandatory)");
				document.forms[0].interestMonth.focus();
				return false;
			}
			
			if(document.forms[0].investmentMode.value=="H")
			{
				if(document.forms[0].interestDate1.value==""){
				alert("Please Select Interest Payment Day(Mandatory)");
				document.forms[0].interestDate1.focus();
				return false;
			}
			if(document.forms[0].interestMonth1.value==""){
				alert("Please Select Interest Payment Month(Mandatory)");
				document.forms[0].interestMonth1.focus();
				return false;
			}
			}
			if(document.forms[0].rating.value !=""){
				if(!ValidateAlphaNumeric(document.forms[0].rating.value)){
					alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
					document.forms[0].rating.focus();
					return false;
				}
			}
			
			if(document.forms[0].dealDate.value=="")
			{
				alert("Deal Date is Mandatory");
				document.forms[0].dealDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].dealDate)){
						document.forms[0].dealDate.select();
						return(false);
					   }
			if(document.forms[0].maturityDate.value=="")
			{
				alert("Maturity Date is Mandatory");
				document.forms[0].maturityDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].maturityDate)){
						document.forms[0].maturityDate.select();
						return(false);
					   }
			if(document.forms[0].settlementDate.value=="")
			{
				alert("Settlement Date is Mandatory");
				document.forms[0].settlementDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].settlementDate)){
						document.forms[0].settlementDate.select();
						return(false);
					   }
			
			
			if(document.forms[0].ytm.value!="" && parseFloat(document.forms[0].ytm.value)!=0) {
				if(!ValidateFloatPoint(document.forms[0].ytm.value,5,2)){
					document.forms[0].ytm.focus();
					document.forms[0].ytm.select();
					return false;
				}
			}
			
			if(document.forms[0].directIncentive.value!="" && parseFloat(document.forms[0].directIncentive.value)!=0){
				if(!ValidateFloatPoint(document.forms[0].directIncentive.value,14,2)){
					document.forms[0].directIncentive.focus();
					document.forms[0].directIncentive.select();
					return false;
				}
			}
			
			if(document.forms[0].brokerIncentive.value!="" && parseFloat(document.forms[0].brokerIncentive.value)!=0){
			
				if(!ValidateFloatPoint(document.forms[0].brokerIncentive.value,14,2)){
					document.forms[0].brokerIncentive.focus();
					document.forms[0].brokerIncentive.select();
					return false;
				}
			}
			if(document.forms[0].closingDate.value!="")
			{
				if(!convert_date(document.forms[0].closingDate)){
						document.forms[0].closingDate.select();
						return(false);
					   }
			}
					
			
		     /*if(document.forms[0].callOption.value=="")
		     {
		     	alert("Type of Call is Mandatory");
		     	document.forms[0].callOption.focus();
		     	return false;
		     }*/
		     
		     if(document.forms[0].ytmcall.value!="")
		    {
		     if(document.forms[0].redemptionDate.value=="")
			{
				alert("Redemption Date is Mandatory");
				document.forms[0].redemptionDate.focus();
				return false;
			}
			}
			if(document.forms[0].redemptionDate.value!="")
			{
			
			if(!convert_date(document.forms[0].redemptionDate)){
						document.forms[0].redemptionDate.select();
						return(false);
					   }
			}
			
			
			if(document.forms[0].brokerName.value !="")
			{
				if(!ValidateAlphaNumeric(document.forms[0].brokerName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].brokerName.focus();
				return false;
				}
			}
			if(document.forms[0].purchasePrice.value!="" && parseFloat(document.forms[0].purchasePrice.value)!=0)
			if(!ValidateFloatPoint(document.forms[0].purchasePrice.value,14,2))
			{
				document.forms[0].purchasePrice.focus();
				document.forms[0].purchasePrice.select();
				return false;
			}
			if(!TxtAreaMaxLength(document.forms[0].brokerAddress.value,200))
			{
			alert("Data should not exceed max.limit (200)");
			document.forms[0].brokerAddress.focus();
			return false;
			}
				
		    return true; 
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
		function clearData(){
       		 window.document.forms[0].reset();    	 
  		}
  	function getArrangerName()
  	{
  	     var arrangerCd=document.forms[0].arranger.value;
  	     
  	     if(arrangerCd !="")
  	     {
  	        var url;
  	        url="jsp/investment/quotation/ArrangersLoad.jsp?mode=ArrangerName&arrangerCd="+document.forms[0].arranger.value;
			
			
			sendURL(url,"loadArrangerName");
		}
  	        
  	 }
  		function getArrangers()
	{
	
		var refNo=document.forms[0].proposalRefNo.value;
		

		if(refNo!="")
		{
			
			var url;
			
			
				url="jsp/investment/quotation/ArrangersLoad.jsp?mode=Arrangers&refNo="+document.forms[0].proposalRefNo.value;
			
			
			sendURL(url,"loadArrangers");
		}

}

function loadArrangers()
{
	document.forms[0].arranger.length=1;
	if (httpRequest.readyState == 4)
		{ 
			if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("QuotationData")[0];
					var trustType ="";
					var securityCategory ="";
					var securityName="";
					var letterNo ="";
					//alert("theletn"+httpRequest.responseXML.getElementsByTagName("QuotationData")[0].childNodes.length);
													
   					if(node) 
					{
					
						for (i = 0; i < parseInt(node.childNodes.length); i++) 
						{
							var arrangers = node.childNodes[i];				
							
							
							document.forms[0].arranger.options[document.forms[0].arranger.options.length]	=	new Option(arrangers.getElementsByTagName("ArrangersName")[0].firstChild.nodeValue,arrangers.getElementsByTagName("Arrangers")[0].firstChild.nodeValue);
						trustType = arrangers.getElementsByTagName("TrustType")[0].firstChild.nodeValue;
						securityCategory = arrangers.getElementsByTagName("SecurityCategory")[0].firstChild.nodeValue;
						securityName=arrangers.getElementsByTagName("SecurityName")[0].firstChild.nodeValue;
						letterNo = node.childNodes[0].getElementsByTagName("LetterNo")[0].firstChild.nodeValue;
						
							
						}
						document.forms[0].trustName.value=trustType;
						document.forms[0].securityCategory.value=securityCategory;
						document.forms[0].securityCd.value=securityName;
						document.forms[0].letterNo.value=letterNo;
					}
					
					  
					
				}
		}
}
function getTenureValue()
{
   
	var letterno=document.forms[0].letterNo.value;
	var maturitydate=document.forms[0].maturityDate.value;
	if(letterno !=""&& maturitydate!="")
	{
		var url;
		url="jsp/investment/quotation/ArrangersLoad.jsp?mode=Tenure&letterNo="+letterno+"&maturitydate="+maturitydate;
			
		
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
function loadArrangerName()
{
	if (httpRequest.readyState == 4)
		{ 
			if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("master")[0];
											
   					if(node) 
					{
						var arrangername = node.childNodes[0].getElementsByTagName("ArrangerName")[0].firstChild.nodeValue;
						document.forms[0].brokerName.value=arrangername;

					}
					
				}
		}
}
/*function calFaceValue()
{
	var facevalueamt=0.0;
	if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0){
		if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)){
			document.forms[0].faceValue.focus();
			document.forms[0].faceValue.select();
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
	if(document.forms[0].faceValue.value=="" || document.forms[0].numberOfUnits.value==""){
		document.forms[0].investmentFaceValue.value="";
	}
	if(document.forms[0].faceValue.value!="" && document.forms[0].numberOfUnits.value!="")	{
		facevalueamt=parseFloat(document.forms[0].faceValue.value)*parseFloat(document.forms[0].numberOfUnits.value);
		document.forms[0].investmentFaceValue.value=parseFloat(facevalueamt);
	}
	return true;
}*/

function calPurchaseValue()
{
	var purchaseamt=0.0;
	var priceoffered=0.0;	
	
	
	
	if(document.forms[0].investmentFaceValue.value !="" && document.forms[0].purchasePrice.value != "" ){
		
			var unitfacevalue = parseFloat(document.forms[0].investmentFaceValue.value);
			var purchasevalue=parseFloat(document.forms[0].purchasePrice.value);
			var pamt=0.00;
			if(unitfacevalue>purchasevalue)
			{
				pamt=unitfacevalue-	purchasevalue;
				document.forms[0].purchaseOption.value="D";
				
			}
			if(unitfacevalue<=purchasevalue)
			{
				pamt=purchasevalue-unitfacevalue;
				document.forms[0].purchaseOption.value="P";
			}	
			
			
			document.forms[0].priceoffered.value=parseFloat(purchasevalue);
			document.forms[0].premiumPaid.value=parseFloat(pamt).toFixed(2);
		displayPurchase();
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
	
	}
function showInterestDates()
{
	if(document.forms[0].investmentMode.value=="" || document.forms[0].investmentMode.value=="Y")
	{
		document.getElementById('yearlydisplaytext').style.display='inline';
		document.getElementById('yearlydisplaydata').style.display='inline';
		document.getElementById('yearlydisplaynone').style.display='inline';
		document.getElementById('halfyearlydisplaytext').style.display='none';
		document.getElementById('halfyearlydisplaydata').style.display='none';
	}
	if(document.forms[0].investmentMode.value=="H")
	{
	
		document.getElementById('yearlydisplaytext').style.display='inline';
		document.getElementById('yearlydisplaydata').style.display='inline';
		document.getElementById('yearlydisplaynone').style.display='none';
		document.getElementById('halfyearlydisplaytext').style.display='inline';
		document.getElementById('halfyearlydisplaydata').style.display='inline';
	}

}

function Calculate_bond_ytm(offerPrice,couponrate,facevalue,settlementDate,maturityDate,investmentmode)
{
	
    var price = parseFloat(offerPrice);
    //var pricetype = document.bondytm.pricetype.value;
    var face = parseFloat(facevalue);
    var interest = parseFloat(couponrate)/100.0;
    var m = parseFloat(2);
    var n = Calculate_periods(m,settlementDate,maturityDate);
    
    var accrued = (Math.ceil(n)-n)*interest*face/2.0;
    var ytm;
    var aer;
    
    /*if( document.bondytm.pricetype[0].checked )
      ytm = 100*bond_ytm(price/face,interest,m,n);
    else*/
    if(investmentmode=='Y')
    {
       	aer = 100*bond_ytm((price+accrued)/face,interest,m,n);
     }
	else if(investmentmode=='H')
	{
	   ytm = 100*bond_ytm((price+accrued)/face,interest,m,n);
       aer = 100.0*(Math.pow(1.0+ytm/100./m,m) - 1.0);
     }

   return  aer.toFixed(2)
    
    //document.getElementById("aintout").innerHTML = "<b>"+accrued.toFixed(2)+"</b>";
}

function Calculate_periods(m,settlementDate,maturityDate)
{
    
    
   
    
    
    
    var settlementsplit=settlementDate.split("/");
    var maturitysplit=maturityDate.split("/");
    
    var sday   = parseInt(settlementsplit[0]);
    var smonth = parseInt(getMonth(settlementsplit[1]));
     var syear  = parseInt(settlementsplit[2]);
     
     var mday   = parseInt(maturitysplit[0]);
     var mmonth = parseInt(getMonth(maturitysplit[1]));
     var myear  = parseInt(maturitysplit[2]);
     
     
    
    
   	
    
    return pay_periods(m,sday,smonth,syear,mday,mmonth,myear);
}
var monthstring = ['JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC'];
function getMonth(month)
{
	for(i=0; i<monthstring.length; i++)
	{
		if(monthstring[i]==month)
		return (i+1);
	}
	
}

function getYTMValue()
{
				
				document.forms[0].ytm.value=="";
				if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)) {
						alert("Please Enter Valid Cupon Rate Value  (Expecting  Numeric Value)");
						document.forms[0].faceValue.focus();
						document.forms[0].faceValue.select();
						return false;
					}
				}
				
				if(document.forms[0].investmentFaceValue.value!="" && parseFloat(document.forms[0].investmentFaceValue.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].investmentFaceValue.value,14,2)) {
						alert("Please Enter Valid Face Value  (Expecting  Numeric Value)");
						document.forms[0].investmentFaceValue.focus();
						document.forms[0].investmentFaceValue.select();
						return false;
					}
				}
				
				
				if(document.forms[0].settlementDate.value!="")
				{
				if(!convert_date(document.forms[0].settlementDate)){
					document.forms[0].settlementDate.select();
					return(false);
				}
				}
				
				if(document.forms[0].maturityDate.value!="")
				{
				if(!convert_date(document.forms[0].maturityDate)){
					document.forms[0].maturityDate.select();
					return(false);
				}
				}
				
				if(document.forms[0].investmentMode.value=="")
				{
					alert("Please SelectInvest Mode.");
					document.forms[0].investmentMode.select();
					return false;
				}
				
				
				if(document.forms[0].faceValue.value!=""&& document.forms[0].priceoffered.value!=""&&document.forms[0].settlementDate.value!=""&& document.forms[0].maturityDate.value!="" && document.forms[0].investmentMode.value!="" && document.forms[0].investmentFaceValue.value!="")
				{
				
					
					var ytmvalue=Calculate_bond_ytm(document.forms[0].priceoffered.value,document.forms[0].faceValue.value,document.forms[0].investmentFaceValue.value,document.forms[0].settlementDate.value,document.forms[0].maturityDate.value,document.forms[0].investmentMode.value);
					
					document.forms[0].ytm.value=ytmvalue;
				}
			}
			function parseMessages(responseXML)
			{
			
			var Data=responseXML.getElementsByTagName("Data")[0];		
				var childnodeslength=Data.childNodes.length;			
				if(childnodeslength!=0) {
				var ytmvalue=Data.childNodes[0].getElementsByTagName("YTM")[0];		
				var ytm=ytmvalue.childNodes[0].nodeValue;
				document.forms[0].ytm.value=ytm;
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
			
			function displaydemattext()
			{
				
				if(document.forms[0].securityCd.value=="SDL" || document.forms[0].securityCd.value=="GOI")
				{
					document.getElementById("categorypurposedisplay").style.display="inline";
					document.getElementById("normaldisplay").style.display="none";
					
				}
				else
				{
					document.getElementById("categorypurposedisplay").style.display="none";
					document.getElementById("normaldisplay").style.display="inline";
				}
			}
	
		</script>
	</head>
	<body onload="document.forms[0].proposalRefNo.focus();displayPurchase();showInterestDates();displaydemattext();">
		<html:form action="/addQuotation.do?method=addQuotation" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("sq.title")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=6 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.refno" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="proposalRefNo" style="width:210 px" styleClass="TextField"  tabindex="1" onchange="getArrangers();displaydemattext();">
							<html:option value="">[Select One]</html:option>
							<html:options collection="quotationRequestList" property="proposalRefNo" labelProperty="proposalRefNo" />
						</html:select>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.letterno" bundle="common" />
						:
					</td>
					<td align=left width="20%" >

						<html:text property="letterNo" styleClass="TextField" style="width:120px" tabindex="2"  readonly="true" />

					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.trustname" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="trustName" style="width:90px" styleClass="TextFieldFont" tabindex="3"  readonly="true" />
					</td>
			

				</tr>


				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.arranger" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="arranger" styleClass="TextField" tabindex="4" style="width:120 px" onchange="getArrangerName();">
							<html:option value="">[Select One]</html:option>
						</html:select>
					</td>
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.securityname" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="securityName" onblur="displaydemattext();" style="width: 120px;" tabindex="5" styleClass="TextField" />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="securityCd" style="width:90px" styleClass="TextFieldFont" tabindex="6"  readonly="true" />
						<html:hidden property="securityCategory" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.couponrate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="faceValue" style="width: 120px;" tabindex="7" onblur="getYTMValue();" styleClass="TextField" />
					</td>
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="sq.facevalueinrs" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="investmentFaceValue" style="width:90px" styleClass="TextFieldFont" tabindex="8" onblur="calPurchaseValue();getYTMValue();" />
						<html:hidden property="priceoffered"/>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.noofunits" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="numberOfUnits" style="width: 120px;" tabindex="9" styleClass="TextField"  />
					</td>
					
				</tr>
				<tr>
				
				<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="purchasePrice" style="width:90px" tabindex="10" styleClass="TextFieldFont"  onblur="calPurchaseValue();getYTMValue();" />
					</td>
				
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchasepriseoption" bundle="common" />
						:
					</td>
					<td align=left width="20%">
					<html:select property="purchaseOption" onchange="displayPurchase();" styleClass="TextField" tabindex="11" style="width:100 px">
							
							<html:options collection="purchasePriseList" property="code"  labelProperty="name" />
						</html:select>
						
					</td>
					
					<td width="20%" class="tableTextBold" id="premium" align=right nowrap>
						<bean:message key="sq.premiumpaid" bundle="common" />
						:
					</td>
					<td width="20%" class="tableTextBold" id="discount" align=right nowrap>
						<bean:message key="sq.discount" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="premiumPaid" style="width: 120px;" tabindex="12" styleClass="TextField" readonly="true" />
					</td>
					
					
				</tr>
				<tr>
					<!-- <td width="20%" class="tableTextBold" align=right nowrap>
				<font color=red>*</font><bean:message key="sq.rateofinterest" bundle="common" />
						:
					</td>					
					<td width="20%" align=left>
					<html:text property="interestRate" style="width: 120px;" tabindex="13" styleClass="TextField" maxlength="6" />
					</td>-->
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.eligibilityofpf" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="eligpftrust" style="width:90px" tabindex="13" styleClass="TextFieldFont" />
					</td>
					
					<td id="yearlydisplaytext" width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td id="yearlydisplaydata" align=left width="20%">
						
						
						<html:select property="interestDate"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
					</td>
					<td id="yearlydisplaynone" colspan=2>&nbsp;</td>
					
					
					<td id="halfyearlydisplaytext" width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td id="halfyearlydisplaydata" align=left width="20%">
						
						
						<html:select property="interestDate1"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth1"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
					</td>
				</tr>
				<tr>
				
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.dealdate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="dealDate" styleId="dealDate" style="width: 100px;" tabindex="18" styleClass="TextField"  />
						<html:link href="javascript:showCalendar('dealDate');" tabindex="19">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.maturitydate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="maturityDate" styleId="maturityDate" style="width: 100px;" tabindex="20" styleClass="TextField" onblur="getTenureValue();getYTMValue();" />
						<html:link href="javascript:showCalendar('maturityDate');" tabindex="21">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.settlementDate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="settlementDate" styleId="settlementDate" style="width: 100px;" tabindex="22" styleClass="TextField" onblur="getYTMValue();" />
						<html:link href="javascript:showCalendar('settlementDate');" tabindex="23">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					
					
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.ytmputcalloption" bundle="common" />
						:
					</td>
					<td align=left width="20%">
					<html:select property="ytmcall" styleClass="TextField" tabindex="24" style="width:100 px">
							<html:option value="">[Select One]</html:option>
							<html:options collection="putCallList" property="code" labelProperty="name" />
						</html:select>
						
					</td>
					<!-- <td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.ytmmaturity" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="ytmmaturity" style="width: 120px;" tabindex="19" styleClass="TextField" />
					</td>-->
					
					
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.ytminpercent" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="ytm"  style="width:90px" tabindex="25"  />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.tenure" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="tenure" style="width: 120px;" tabindex="26" styleClass="TextField" readonly="true" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.directincentive" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="directIncentive" style="width: 120px;" tabindex="27" styleClass="TextField" />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.brokerincentive" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="brokerIncentive" style="width: 120px;" tabindex="28" styleClass="TextField" />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.closingdate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="closingDate" styleId="closingDate" style="width: 60px;" tabindex="29" styleClass="TextFieldFont" />
						<html:link href="javascript:showCalendar('closingDate');" tabindex="30">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
				</tr>
				
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.typeofcall" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="callOption" styleClass="TextField" tabindex="31" style="width:100 px">
							<html:option value="">[Select One]</html:option>
							<html:options collection="typeOfCallList" property="code" labelProperty="name" />
						</html:select>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="sq.redemptiondate" bundle="common" />
						:
					</td>
					<td width="20%" align=left>
						<html:text property="redemptionDate" styleId="redemptionDate" style="width: 60px;" tabindex="32" styleClass="TextFieldFont" />
						<html:link href="javascript:showCalendar('redemptionDate');" tabindex="33">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.minapp" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="minApp" style="width:90px" tabindex="34" styleClass="TextFieldFont" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.guarenteetype" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="guarenteeType" styleClass="TextField" style="width:100 px" tabindex="35">
							<html:option value="">[Select One]</html:option>
							<html:options collection="guarenteetypeList" property="code" labelProperty="name" />
						</html:select>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.investmentmode" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="investmentMode" styleClass="TextField" style="width:100 px" onchange="showInterestDates();getYTMValue();"  tabindex="36">
							
							<html:options collection="investmentModeList" property="code" labelProperty="name" />
						</html:select>
					</td>
					<td id="normaldisplay" width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.dematno" bundle="common" />
						:
					</td>
					<td id="categorypurposedisplay" width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.sglno" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="dematno" style="width:90px" styleClass="TextFieldFont" tabindex="37" />
					</td>
				</tr>
				<tr>
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.brokername" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="brokerName" style="width: 120px;" styleClass="TextField" tabindex="38" readonly="true" />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap >
						<bean:message key="sq.brokeraddress" bundle="common" />
						:
					</td>
					<td align=left width="20%" colspan=2>
						<html:textarea property="brokerAddress" style="width:250px" tabindex="39">
						</html:textarea>
					</td>
				</tr>
				<tr>
				<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.rating" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="rating" style="width:90px" styleClass="TextFieldFont" tabindex="40" />
					</td>

				
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.remarks" bundle="common" />
						:
					</td>
					<td align=left width="20%" colspan=2>
						<html:textarea property="remarks" style="width:250px" tabindex="41">
						</html:textarea>
					</td>
				</tr>
				<tr>
					<td colspan=2 align=center>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=6 align=center>
						<html:submit styleClass="butt" tabindex="42">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="43" onclick="clearData();">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
						&nbsp;
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchQuotation.do?method=searchQuotation'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="44" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>