<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>Investment - Submit Quotaion Data</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<script type="text/javascript" src="js/ytm/bond.js"></script>
		<SCRIPT type="text/javascript">
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
			}*/
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
			if(document.forms[0].investmentMode.value=="H")
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
				
				
				if(document.forms[0].faceValue.value!=""&& document.forms[0].priceoffered.value!=""&&document.forms[0].settlementDate.value!=""&& document.forms[0].maturityDate.value!="" && document.forms[0].investmentMode.value!="")
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
	
  		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].letterNo.focus();displayPurchase();showInterestDates();displaydemattext();" >
	<html:form action="/editQuotation.do?method=updateQuotation" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("sq.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="sq.letterno" bundle="common"/>: 
				</td>
				<td align=left width="20%"  >
					<html:text property="letterNo" name="quotation" style="width: 120px;" tabindex="1" styleClass="TextField" readonly="true"/>
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.trustname" bundle="common"/>: 
				</td><td align=left width="20%" >
					<html:text size="12" property="trustName" name="quotation" styleClass="TextField" style="width:120 px" tabindex="2" readonly="true" />
				</td>			
				<td  width="20%" class="tableTextBold" align=right nowrap>&nbsp;
				<html:hidden property="quotationCd" name="quotation"/>
					
				</td>
				<td width="20%" >
					&nbsp;
				</td>		
				
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font> <bean:message key="sq.arranger" bundle="common"/>: 
				</td>
				<td align=left width="20%" >
				<html:text property="arranger" name="quotation" style="width: 120px;" tabindex="3" styleClass="TextField" readonly="true"/>
					
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="sq.securityname" bundle="common"/>:
				</td>
				<td align=left width="20%" >
					<html:text property="securityName" name="quotation" style="width: 120px;" tabindex="4" styleClass="TextField" />  
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.securitycategory" bundle="common"/>: 
				</td><td align=left width="20%" >
					<html:text property="securityCd" name="quotation" style="width: 120px;" tabindex="5" styleClass="TextField" readonly="true" />      
					<html:hidden property="securityCategory" name="quotation"/>
				</td>
			</tr>
			<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.couponrate" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="faceValue" name="quotation" style="width: 120px;" tabindex="7" onblur="getYTMValue();" styleClass="TextField" />
					</td>
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="sq.facevalueinrs" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="investmentFaceValue" name="quotation" style="width:90px" styleClass="TextFieldFont" tabindex="8" onblur="calPurchaseValue();getYTMValue();" />
						<html:hidden property="priceoffered"/>
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.noofunits" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="numberOfUnits" name="quotation" style="width: 120px;" tabindex="9" styleClass="TextField"  />
					</td>
					
				</tr>
				<tr>
				
				<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="purchasePrice" name="quotation" style="width:90px" tabindex="10" styleClass="TextFieldFont"  onblur="calPurchaseValue();getYTMValue();" />
					</td>
				
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchasepriseoption" bundle="common" />
						:
					</td>
					<td align=left width="20%">
					<html:select property="purchaseOption" name="quotation" onchange="displayPurchase();" styleClass="TextField" tabindex="11" style="width:100 px">
							
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
						<html:text property="premiumPaid" name="quotation" style="width: 120px;" tabindex="12" styleClass="TextField" readonly="true" />
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
						<html:text property="eligpftrust" name="quotation" style="width:90px" tabindex="13" styleClass="TextFieldFont" />
					</td>
					
					<td id="yearlydisplaytext" width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td id="yearlydisplaydata" align=left width="20%">
						
						<html:select property="interestDate" name="quotation" styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth" name="quotation"  styleClass="TextField" tabindex="11" style="width:50px">
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
						
						<html:select property="interestDate1" name="quotation"  styleClass="TextField" tabindex="11" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth1" name="quotation"  styleClass="TextField" tabindex="11" style="width:50px">
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
						<html:text property="dealDate" name="quotation" styleId="dealDate" style="width: 100px;" tabindex="18" styleClass="TextField"  />
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
						<html:text property="maturityDate" name="quotation" styleId="maturityDate" style="width: 100px;" tabindex="20" styleClass="TextField" onblur="getTenureValue();getYTMValue();" />
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
						<html:text property="settlementDate" name="quotation" styleId="settlementDate" style="width: 100px;" tabindex="22" styleClass="TextField" onblur="getYTMValue();" />
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
					<html:select property="ytmcall" name="quotation" styleClass="TextField" tabindex="24" style="width:100 px">
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
						<html:text property="ytm" name="quotation" style="width:90px" tabindex="25"  />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.tenure" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="tenure" name="quotation" style="width: 120px;" tabindex="26" styleClass="TextField" readonly="true" />
					</td>
				</tr>

			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.directincentive" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:text property="directIncentive" name="quotation" style="width: 120px;" tabindex="27" styleClass="TextField" /> 				
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.brokerincentive" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:text property="brokerIncentive" name="quotation" style="width: 120px;" tabindex="28" styleClass="TextField" /> 					
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
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font> <bean:message key="sq.typeofcall" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:select property="callOption" name="quotation" styleClass="TextField" style="width:100 px" tabindex="31" >
					  <html:option value="">[Select One]</html:option>
					  <html:options collection="typeOfCallList" property="code" labelProperty="name"/>
                    </html:select>				
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.redemptiondate" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:text property="redemptionDate" name="quotation" styleId="redemptionDate" style="width: 95px;" tabindex="32" styleClass="TextField" />
					<html:link href="javascript:showCalendar('redemptionDate');" tabindex="33">
					<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>       
				</td>
				
				<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.minapp" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="minApp" name="quotation" style="width:90px" tabindex="34" styleClass="TextFieldFont" />
					</td>
			</tr>
			<tr>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.guarenteetype" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:select property="guarenteeType" name="quotation" styleClass="TextField" style="width:100 px"  tabindex="35">
					 <html:option value="">[Select One]</html:option>
					 <html:options collection="guarenteetypeList" property="code" labelProperty="name"/>               
                    </html:select>
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.investmentmode" bundle="common"/>:
				</td>
				<td width="20%" align=left>
					<html:select property="investmentMode" name="quotation" onchange="showInterestDates();getYTMValue();" styleClass="TextField" style="width:100 px"  tabindex="36">
					   <html:option value="">[Select One]</html:option>
					   <html:options collection="investmentModeList" property="code" labelProperty="name"/>
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
						<html:text property="dematno" name="quotation" style="width:90px" styleClass="TextFieldFont" tabindex="37" />
					</td>			
			</tr>
			<tr>
					
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.brokername" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="brokerName" name="quotation" style="width: 120px;" styleClass="TextField" tabindex="38" readonly="true" />
					</td>
					<td width="20%" class="tableTextBold" align=right nowrap >
						<bean:message key="sq.brokeraddress" bundle="common" />
						:
					</td>
					<td align=left width="20%" colspan=2>
						<html:textarea property="brokerAddress" name="quotation" style="width:250px" tabindex="39">
						</html:textarea>
					</td>
				</tr>
				<tr>
				<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.rating" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="rating" name="quotation" style="width:90px" styleClass="TextFieldFont" tabindex="40" />
					</td>	
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.remarks" bundle="common" />
						:
					</td>
					<td align=left width="20%" colspan=2>
						<html:textarea property="remarks" name="quotation" style="width:250px" tabindex="41">
						</html:textarea>
					</td>
				</tr>
			<tr>
				<td colspan=2 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center>
				<html:submit styleClass="butt"   tabindex="42" > <bean:message key="button.save" bundle="common"/></html:submit>&nbsp;
				<html:button  styleClass="butt" property="Clear" tabindex="43" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
				<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchQuotation.do?method=searchQuotation'" onblur="document.forms[0].letterNo.focus();"  tabindex="44"/>
			</td>	
			</tr>
			</table>
				<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
