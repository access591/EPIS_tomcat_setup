<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ConfirmationToCompany </title>

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
		<script type="text/javascript" src="js/ytm/bond.js"></script>

		<script type="text/javascript">
			var httpRequest; 
			function validate(){
				
			    if(document.forms[0].confirm.value=="")
			    {
			    	alert("Please Enter The Confirmation (Mandatory)");
			    	document.forms[0].confirm.focus();
			    	return false;
			    }
			    
			   
			    	if(document.forms[0].remarks.value=="")
			    	{
			    		alert("Please Enter The Remarks (Mandatory)");
			    		document.forms[0].remarks.focus();
			    		return false;
			    	}
			    
			    
			    
			    
			  	if(document.forms[0].confirm.value=="Y")
			  	{
				if(document.forms[0].dealDate.value=="")
				{
					alert("Please Enter Deal Date(Mandotory)");
					document.forms[0].dealDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].dealDate)){
					document.forms[0].dealDate.select();
					return(false);
				}
				
				if(document.forms[0].settlementDate.value=="")
				{
					alert("Please Enter Settlement Date(Mandotory)");
					document.forms[0].settlementDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].settlementDate)){
					document.forms[0].settlementDate.select();
					return(false);
				}
				
				if(compareDates(document.forms[0].dealDate.value,document.forms[0].settlementDate.value) == 'larger')
				{
					alert(" Deal Date Should be less than Settlement Date ");
					document.forms[0].dealDate.focus();
					return false;
				}
				
				if(document.forms[0].maturityDate.value=="")
				{
					alert("Please Enter Maturity Date(Mandotory)");
					document.forms[0].maturityDate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].maturityDate)){
					document.forms[0].maturityDate.select();
					return(false);
				}
				
				if(compareDates(document.forms[0].settlementDate.value,document.forms[0].maturityDate.value) == 'larger')
				{
					alert(" Settlement Date Should be less than Maturity Date ");
					document.forms[0].dealDate.focus();
					return false;
				}
				if(document.forms[0].cuponRate.value=="") {
					alert("Please Enter The Cupon Rate (Mandatory)");
					document.forms[0].cuponRate.focus();
					return false;
				}
				
				
				if(document.forms[0].cuponRate.value!="" && parseFloat(document.forms[0].cuponRate.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].cuponRate.value,7,2)) {
						alert("Please Enter Valid Cupon Rate (Expecting  Numeric Value)");
						document.forms[0].cuponRate.focus();
						document.forms[0].cuponRate.select();
						return false;
					}
				}
				
				
				if(document.forms[0].faceValue.value=="") {
					alert("Please Enter The Face Value (Mandatory)");
					document.forms[0].faceValue.focus();
					return false;
				}
				
				
				if(document.forms[0].faceValue.value!="" && parseFloat(document.forms[0].faceValue.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)) {
						alert("Please Enter Valid Face Value  (Expecting  Numeric Value)");
						document.forms[0].faceValue.focus();
						document.forms[0].faceValue.select();
						return false;
					}
				}
				
				
				if(document.forms[0].noofBonds.value=="") {
					alert("Please Enter The No.ofBonds (Mandatory)");
					document.forms[0].noofBonds.focus();
					return false;
				}
				
				
				if(document.forms[0].noofBonds.value!="" && parseFloat(document.forms[0].noofBonds.value)!=0) {
					if(!ValidateNum(document.forms[0].noofBonds.value)) {
						alert("Please Enter Valid No.of Bonds (Expecting  Numeric Value)");
						document.forms[0].noofBonds.focus();
						document.forms[0].noofBonds.select();
						return false;
					}
				}
				
				
				
				
				if(document.forms[0].interestDate.value==""){
				alert("Please Enter Interest Payment Date(Mandatory)");
				document.forms[0].interestDate.focus();
				return false;
			}
			if(document.forms[0].interestMonth.value==""){
				alert("Please Enter Interest Payment Month(Mandatory)");
				document.forms[0].interestMonth.focus();
				return false;
			}
				
				if(document.forms[0].modeOfInterest.value=="H")
				{
						if(document.forms[0].interestDate1.value==""){
					alert("Please Enter Interest Payment Date two(Mandatory)");
					document.forms[0].interestDate1.focus();
					return false;
						}
				if(document.forms[0].interestMonth1.value==""){
				alert("Please Enter Interest Month(Mandatory)");
				document.forms[0].interestMonth1.focus();
				return false;
				}
				}
				
				if(document.forms[0].callOption.value=="Y")
				{
					if(document.forms[0].callDate.value=="")
					{
					alert("Please Enter Call Date(Mandotory)");
					document.forms[0].callDate.focus();
					return false;
					}
					
					if(!convert_date(document.forms[0].callDate)){
					document.forms[0].callDate.select();
					return(false);
					}
				}
				}				  
			}
			var xmlhttpreq;
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
				
				document.forms[0].ytmValue.value=="";
				if(document.forms[0].cuponRate.value!="" && parseFloat(document.forms[0].cuponRate.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].cuponRate.value,7,2)) {
						alert("Please Enter Valid Cupon Rate Value  (Expecting  Numeric Value)");
						document.forms[0].cuponRate.focus();
						document.forms[0].cuponRate.select();
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
				
				if(document.forms[0].modeOfInterest.value=="")
				{
					alert("Please Select Mode of Interest.");
					document.forms[0].modeOfInterest.select();
					return false;
				}
				
				
				if(document.forms[0].cuponRate.value!=""&& document.forms[0].offeredPrice.value!=""&&document.forms[0].settlementDate.value!=""&& document.forms[0].maturityDate.value!="" && document.forms[0].modeOfInterest.value!="")
				{
				
					var ytmvalue=Calculate_bond_ytm(document.forms[0].offeredPrice.value,document.forms[0].cuponRate.value,document.forms[0].faceValue.value,document.forms[0].settlementDate.value,document.forms[0].maturityDate.value,document.forms[0].modeOfInterest.value);
					
					document.forms[0].ytmValue.value=ytmvalue;
					
					
				}
			}
			function parseMessages(responseXML)
			{
			
			var Data=responseXML.getElementsByTagName("Data")[0];		
				var childnodeslength=Data.childNodes.length;			
				if(childnodeslength!=0) {
				var ytmvalue=Data.childNodes[0].getElementsByTagName("YTM")[0];		
				var ytm=ytmvalue.childNodes[0].nodeValue;
				document.forms[0].ytmValue.value=ytm;
				}
			
				
			}
			
	
			
			
		
			
		
			function  LoadTrustType() {
				document.forms[0].trustType.value='';
				document.forms[0].marketTypedef.value='';
				document.forms[0].marketType.value='';
				document.forms[0].securityCategory.value='';
				document.forms[0].amountInv.value='';
				document.forms[0].noofBonds.value='';
				
			if(!document.forms[0].refNo.value=="")
			{
			
				var url;
			
			
				var url="jsp/investment/psuprimary/confirmationtocompany/LoadTrust.jsp?proposalRefNo="+document.forms[0].refNo.value+"&mode=confirm";
			
			
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
						var noofbonds=node.childNodes[0].getElementsByTagName("noofbonds")[0].firstChild.nodeValue;
											
						document.forms[0].trustType.value=trustType;
						document.forms[0].securityCategory.value=securityCategory;
						document.forms[0].marketTypedef.value=marketdef;
						document.forms[0].marketType.value=market;
						document.forms[0].amountInv.value=investamt;
						document.forms[0].noofBonds.value=noofbonds;

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
	function displayRemarks()
	{
		if(document.forms[0].confirm.value=="N")
	    {
	    	document.forms[0].dealDate.value="";
	    	document.forms[0].settlementDate.value="";
	    	document.forms[0].maturityDate.value="";
	    	document.forms[0].cuponRate.value="";
	    	document.forms[0].faceValue.value="";
	    	document.forms[0].premiumPaid.value=0;
	    	document.forms[0].callOption.value="";
	    	document.forms[0].modeOfInterest.value="";
	    	document.forms[0].purchasePrice.value="";
	    	document.forms[0].offeredPrice.value=0;
	    	document.forms[0].ytmValue.value="";
	    	displayCalldate();
	    	displayinterestDate();
	    	
	    	document.getElementById('confirmationremarks').style.display = 'none';
	    }
	    else
	    {
	    	document.getElementById('confirmationremarks').style.display = 'inline';
	    }
	}
	function displayCalldate()
	{
		if(document.forms[0].callOption.value=="Y")
		{
			document.getElementById('calloptiondate').style.display='inline';
		}
		else{
			document.getElementById('calloptiondate').style.display='none';
			
		}
	}
	function displayinterestDate()
	{
		if(document.forms[0].modeOfInterest.value=="H")
		{
			document.getElementById('interestdatelabel').style.display='inline';
			document.getElementById('interestdatetext').style.display='inline';
			document.getElementById('interestdatenone').style.display='none';
			
		}
		else
		{
			document.getElementById('interestdatelabel').style.display='none';
			document.getElementById('interestdatetext').style.display='none';
			document.getElementById('interestdatenone').style.display='inline';
		}
	}
	
	
	
	function calPurchaseValue()
{
	var purchaseamt=0.0;
	var priceoffered=0.0;	
	
	if(document.forms[0].premiumPaid.value!="" && parseFloat(document.forms[0].premiumPaid.value)!=0){
		if(!ValidateFloatPoint(document.forms[0].premiumPaid.value,5,2)){
			document.forms[0].premiumPaid.focus();
			document.forms[0].premiumPaid.select();
			return false;
		}
	}
	
	if(document.forms[0].premiumPaid.value ==""){
		document.forms[0].premiumPaid.value = 0;
	}
	
	if(document.forms[0].faceValue.value!=""&& parseFloat(document.forms[0].faceValue.value)!=0)
	{
		if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)){
			document.forms[0].faceValue.focus();
			document.forms[0].faceValue.select();
			return false;
		}
	}
	
	if(document.forms[0].noofBonds.value!=""&& parseFloat(document.forms[0].noofBonds.value)!=0)
	{
		if(!ValidateNum(document.forms[0].noofBonds.value)){
			document.forms[0].noofBonds.focus();
			document.forms[0].noofBonds.select();
			return false;
		}
	}
	if(document.forms[0].faceValue.value !="" && document.forms[0].noofBonds.value != "" ){
		
			var unitfacevalue = parseFloat(document.forms[0].faceValue.value);
			var units = parseFloat(document.forms[0].noofBonds.value);
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
			
			document.forms[0].offeredPrice.value=parseFloat(priceoffered);
			document.forms[0].purchasePrice.value=parseFloat(purchaseamt);
		
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
function caluculatePremiumPaid()
{
	if(document.forms[0].faceValue.value!=""&& document.forms[0].offeredPrice.value!="")
	{
		if(!ValidateFloatPoint(document.forms[0].faceValue.value,14,2)){
			document.forms[0].faceValue.focus();
			document.forms[0].faceValue.select();
			return false;
		}
		
		if(!ValidateFloatPoint(document.forms[0].offeredPrice.value,7,2)){
			document.forms[0].offeredPrice.focus();
			document.forms[0].offeredPrice.select();
			return false;
		}
		
		var facevalueamount=parseFloat(document.forms[0].faceValue.value);
		var offferamt=parseFloat(document.forms[0].offeredPrice.value);
		var pamt=0.00;
		if(facevalueamount>offferamt)
			{
				pamt=facevalueamount-offferamt;
				document.forms[0].purchaseOption.value="D";
				
			}
			if(facevalueamount<=offferamt)
			{
				pamt=offferamt-facevalueamount;
				document.forms[0].purchaseOption.value="P";
			}
	document.forms[0].premiumPaid.value=parseFloat(pamt).toFixed(2);
	displayPurchase();	
	}
}
	
		  		
  		
		</script>
	</head>
	<body onload="displayRemarks();displayCalldate();displayinterestDate();displayPurchase();">
		<html:form action="/editConfirmationCompany.do?method=editConfirmationCompany" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("confirmationcomapny.title")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.proposalrefno" bundle="common" />
						:
					</td>
					
					
					<td align=left width="25%">
						<bean:write property="refNo" name="conformationBean"/>
						
					</td>
					
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="confirmationcomapny.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					<bean:write property="trustType" name="conformationBean"/>
					
					</td>
					</tr>
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="confirmationcompany.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<bean:write property="securityCategory" name="conformationBean"/>
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.markettype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<bean:write property="marketTypedef" name="conformationBean"/>
					 	<html:hidden property="marketType" name="conformationBean"/>
						<html:hidden property="amountInv"  name="conformationBean"/>
						<html:hidden property="letterNo" name="conformationBean"/>
						<html:hidden property="refNo" name="conformationBean"/>
					</td>
					
					</tr>
					<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.confirmed" bundle="common"/>
						</td>
					<td align=left width="25%">
					<html:select property="confirm" name="conformationBean" onchange="displayRemarks();" styleClass="TextField" style="width:200 px" tabindex="5">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="confirmationList" property="key" labelProperty="value" />
					</html:select>
					</td>
					
				<td width="50%" colspan=2>&nbsp;</td>	
				</tr>
				<tr id="confirmationremarks">
				<td colspan=4 align=center>
				<table width="550" border=0 align="center" bordercolor=green>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font>
					<bean:message key="confirmationcomapny.dealdate" bundle="common" />
					</td>
					<td align=left width="25%">
					<html:text property="dealDate" name="conformationBean" styleId="dealDate" style="width: 95px;" tabindex="6" styleClass="TextField" />
							<html:link href="javascript:showCalendar('dealDate');" tabindex="7">
								<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
							</html:link>
					</td>
					
						<td width="50%" colspan=2>&nbsp;</td>	
				</tr>
					<tr>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
				<font color=red>*</font>
				<bean:message key="confirmationcomapny.settlementDate" bundle="common" />
				</td>
				<td align=left width="25%">
				<html:text property="settlementDate" name="conformationBean" styleId="settlementDate" style="width: 95px;" tabindex="8" styleClass="TextField" />
						<html:link href="javascript:showCalendar('settlementDate');" tabindex="9">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="confirmationcomapny.maturityDate" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:text property="maturityDate" name="conformationBean" onblur="getYTMValue();" styleId="maturityDate" style="width: 95px;" tabindex="10" styleClass="TextField" />
						<html:link href="javascript:showCalendar('maturityDate');" tabindex="11">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
						</td>
						
				</tr>
			<tr>
			
			<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.cuponrate" bundle="common" />
						:
					</td>
					<td align=left width="25%" >
						<html:text property="cuponRate" name="conformationBean" onblur="getYTMValue();" size="12" styleClass="TextField" style="width:160 px" tabindex="12" maxlength="8"/>

					</td>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcomapny.facevalue" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap>
						<html:text size="12" property="faceValue" name="conformationBean" onblur="caluculatePremiumPaid();calPurchaseValue();getYTMValue();" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="13"  />
						

					</td>
					
					
				</tr>
				
				<tr>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font>
					<bean:message key="confirmationcomapny.noofbonds" bundle="common"/>:</td>
					<td align="left" width="25%" nowrap>
					
					<html:text size="12" property="noofBonds" name="conformationBean" onblur="calPurchaseValue();getYTMValue();" styleClass="TextField" style="width:120 px"  tabindex="14" maxlength="12"  />
					</td>
					
					
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<bean:message key="confirmationcomapny.offeredprice" bundle="common"/>
					</td>
					<td align=left width="25%">
					<html:text size="12" property="offeredPrice" name="conformationBean" styleClass="TextField" style="width:120 px" onblur="caluculatePremiumPaid();getYTMValue();"  tabindex="15" maxlength="8"  />
					</td>
					
					
					
					</tr>
					<tr>
					
					
					
					
					<td width="25%" class="tableTextBold" id="premium" align=right nowrap>
						<bean:message key="sq.premiumpaid" bundle="common" />
						:
					</td>
					<td width="25%" class="tableTextBold" id="discount" align=right nowrap>
						<bean:message key="sq.discount" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					<html:text size="12" property="premiumPaid" name="conformationBean" onblur="calPurchaseValue();getYTMValue();" styleClass="TextField" style="width:120 px"  tabindex="17" maxlength="6"  readonly="true" />
					</td>
					
					<td colspan=2><html:hidden property="purchaseOption" name="conformationBean"/></td>
					
					
					
					</tr>
					
					<tr>
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<bean:message key="confirmationcomapny.purchaseprice" bundle="common"/>
					</td>
					<td align=left width="25%">
					<html:text size="12" property="purchasePrice" name="conformationBean" styleClass="TextField" style="width:120 px"  tabindex="18" maxlength="16" readonly="true" />
					</td>
						
					<td width="25%" class="tableTextBold" align="right" nowrap>
						<bean:message key="confirmationcomapny.modeofinterest" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:select property="modeOfInterest" name="conformationBean" onchange="displayinterestDate();getYTMValue();"  styleClass="TextField" style="width:200 px" tabindex="21">
							
							<html:options collection="modeinterestList" property="key" labelProperty="value" />
						</html:select>
						</td>
					
					</tr>
					
					<tr>
					
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<bean:message key="confirmationcomapny.putcalloption" bundle="common"/>
					</td>
					<td align=left width="25%">
					<html:select property="callOption" name="conformationBean" onchange="displayCalldate();"  styleClass="TextField" style="width:200 px" tabindex="20">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="confirmationList" property="key" labelProperty="value" />
						</html:select>
					</td>
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<bean:message key="confirmationcomapny.ytm" bundle="common"/>
					</td>
					<td align=left width="25%">
					<html:text size="12" property="ytmValue" name="conformationBean" styleClass="TextField" style="width:120 px"  tabindex="19" maxlength="6" readonly="true"  />
					</td>
						
						
						
						
					</tr>
					
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="confirmationcompany.interestdate" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:select property="interestDate" name="conformationBean" styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth" name="conformationBean"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
						</td>
						<td id="interestdatenone" colspan="2">&nbsp;</td>
						<td id="interestdatelabel" align="right" nowrap>
						<bean:message key="confirmationcompany.interestdate" bundle="common" />
						</td>
						<td id="interestdatetext" align="left">
						<html:select property="interestDate1" name="conformationBean"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth1" name="conformationBean"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
						</td>
										
					</tr>
					</table>
					</td>
					</tr>
					<tr id="confirmationremarks">
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="confirmationcompany.remarks" bundle="common" />
					</td>
					
					<td align=left width="25%">
						<html:textarea property="remarks" name="conformationBean" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].remarks.value,299)" tabindex="26" />

					</td>
					<td colspan=2>&nbsp;</td>
					
					
					</tr>
					
					<tr id="calloptiondate">
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<bean:message key="confirmationcompany.calldate" bundle="common"/>
					</td>
					<td width="25%" align="left">
					<html:text property="callDate" name="conformationBean" styleId="callDate" style="width: 95px;" tabindex="27" styleClass="TextField" />
						<html:link href="javascript:showCalendar('callDate');" tabindex="28">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					<td colspan=2>&nbsp;</td>
					</tr>

				
					
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;
				<html:hidden property="mode" name="conformationBean"/>
				</td>
				</tr>

				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="29">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="30" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchConfirmationCompany.do?method=searchConfirmationCompany&mode='+document.forms[0].mode.value" onblur="document.forms[0].letterNo.focus();" tabindex="31" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
