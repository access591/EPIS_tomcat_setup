
<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<script type="text/javascript" src="<%=basePath%>js/prototype.js"></script>
		<script type="text/javascript" src="js/ytm/bond.js"></script>
		<script type="text/javascript" src="js/GeneralFunctions.js"></script>
		<SCRIPT type="text/javascript">
		 function validate(){
			    
			    if(document.forms[0].bankLetterNo.value==""){
		    		 alert("please Enter Bank Letter No ");
		    		 document.forms[0].bankLetterNo.focus();
		   			  return false;
		    		 }
		   		
		        if(document.forms[0].accountNo.value=='')
				{
					alert("Please Select BankName Mandatory");
					document.forms[0].accountNo.focus();
					return(false);
				}
				
				if(document.forms[0].benificiaryBankName.value=="")
			    {
			    	alert("Please Enter The BenificiaryBankName (Mandatory)");
			    	document.forms[0].benificiaryBankName.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].benificiaryBankName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].benificiaryBankName.focus();
				return false;
				}
				
				if(document.forms[0].benificiaryBranch.value=="")
			    {
			    	alert("Please Enter The BeneficiaryBranchName (Mandatory)");
			    	document.forms[0].benificiaryBranch.focus();
			    	return false;
			    }
			    if(!ValidateAlphaNumeric(document.forms[0].benificiaryBranch.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].benificiaryBranch.focus();
				return false;
				}
				
				if(document.forms[0].accountType.value=="")
			    {
			    	alert("Please Enter The Account Type (Mandatory)");
			    	document.forms[0].accountType.focus();
			    	return false;
			    }
			    
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
				
				if(document.forms[0].remarks.value=="")
				{
					alert("Please Enter  BeneficiaryRemarks (Mandatory)");
					document.forms[0].remarks.focus();
					return false;
				}			
		    	
				if(document.forms[0].dealDate.value==''){
						alert("please Enter Deal Date ");
						document.forms[0].dealDate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].dealDate)){
						document.forms[0].dealDate.select();
						return(false);
					   }
					   
				if(document.forms[0].settlementDate.value==''){
						alert("please Enter Settlement Date ");
						document.forms[0].settlementDate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].settlementDate)){
						document.forms[0].settlementDate.select();
						return(false);
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
				
				if(document.forms[0].noofDays.value=='')
				{
					alert("please Enter noofDays Mandatory");
					document.forms[0].noofDays.focus();
					return false;
				}
				
				if(!ValidateNum(document.forms[0].noofDays.value))
				{
					alert("Please Enter Valid Number (Expecting Numeric Value) ");
					document.forms[0].noofDays.focus();
					return false;
				}
				
				if(document.forms[0].investmentFaceValue.value =="") {
					alert("Please Enter Face Value (Mandatory)");
					document.forms[0].investmentFaceValue.focus();				
					return false;
					}
			
			if(document.forms[0].numberOfUnits.value =="") {
				alert("Please Enter No. of Units (Mandatory)");
				document.forms[0].numberOfUnits.focus();				
				return false;
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
			
			if(document.forms[0].interestRate.value=="")
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
				alert("Please Enter Interest Payment Day(Mandatory)");
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
				alert("Please Enter Interest Payment Month(Mandatory)");
				document.forms[0].interestMonth1.focus();
				return false;
			}
			
			}
				
				if(document.forms[0].accuredAmount.value=='')
				{
					alert("please Enter Accured Amount Mandatory");
					document.forms[0].accuredAmount.focus();
					return false;
				}
				if(document.forms[0].accuredAmount.value!="" && parseFloat(document.forms[0].accuredAmount.value)!=0)
				if(!ValidateFloatPoint(document.forms[0].accuredAmount.value,14,2))
				{
				document.forms[0].accuredAmount.focus();
				return false;
				}
					
					
				
		     
		    return true; 
		 }
		
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
			document.forms[0].principleAmt.value=(parseFloat(document.forms[0].numberOfUnits.value)*parseFloat(document.forms[0].purchasePrice.value)).toFixed(2);
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
function ConsiderationAmount()
{
	var considerationamt=0;
	if(document.forms[0].principleAmt.value!="" && document.forms[0].accuredAmount.value!="")
	{
		
		considerationamt=parseFloat(document.forms[0].principleAmt.value)+parseFloat(document.forms[0].accuredAmount.value);
	}
	
	document.forms[0].totalConsideration.value=parseFloat(considerationamt);
	
}
function openReport(){

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
			
			if(document.forms[0].numberOfUnits.value =="") {
				alert("Please Enter No. of Units (Mandatory)");
				document.forms[0].numberOfUnits.focus();				
				return false;
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
			
			if(document.forms[0].interestRate.value=="")
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
				alert("Please Enter Interest Payment Day(Mandatory)");
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
				alert("Please Enter Interest Payment Day two(Mandatory)");
				document.forms[0].interestDate1.focus();
				return false;
			}
			
			if(document.forms[0].interestMonth1.value==""){
				alert("Please Enter Interest Payment Month two(Mandatory)");
				document.forms[0].interestMonth1.focus();
				return false;
			}
			}
			calPurchaseValue();
				
				var url = "addGenerateBankLetter.do?method=updateQuotationData";	       
		      	
		      	var ajaxRequest = new Ajax.Request(url, {
					method:       'post', 
					parameters: {faceValue:document.forms[0].faceValue.value,maturityDate:document.forms[0].maturityDate.value,interestRate:document.forms[0].interestRate.value,interestDate: document.forms[0].interestDate.value,interestDate1:document.forms[0].interestDate1.value,interestMonth:document.forms[0].interestMonth.value,interestMonth1:document.forms[0].interestMonth1.value,dealDate:document.forms[0].dealDate.value,settlementDate:document.forms[0].settlementDate.value,priceoffered:document.forms[0].priceoffered.value,letterNo:document.forms[0].letterNo.value},
					asynchronous: true,
					onSuccess:   openytmReport
				}); 
				
			}

		 function openytmReport()
		 {
		 		var url="/actionYTM.do?method=showYTMAddDetails&letterNo="+document.forms[0].letterNumber.value+"&ytmshow=yes";
				var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"GenerateBankLetter","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
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
	function selectedBank()
	{
		var bank=document.getElementById("accountNo");
		for(i=0;i<bank.options.length; i++)
		{
			if(bank.options[i].text.indexOf("SYNDICATE BANK")>=0)
			bank.options[i].selected=true;
		}
	}
		 
		 function clearData(){
        	window.document.forms[0].reset();
  		}
  		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].bankLetterNo.focus();displayPurchase();showInterestDates();selectedBank()" >
		<html:form action="/addGenerateBankLetter.do?method=addGenerateBankLetter" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("bankletter.title")%>
		<table width="100%" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  class="tableTextBold" align=right nowrap>
					 <bean:message key="bankletter.approvalrefno" bundle="common"/>
					 <input type="hidden" name="letterNumber" value="<bean:write name="bankBean" property="letterNo"/>"/>
				</td><td align=left nowrap >
					<bean:write name="bankBean" property="letterNo"/>
					<input type="hidden" name="letterNo" value="<bean:write name="bankBean" property="quotationCd"/>">
					
				</td>
				<td class="tableTextBold" align="right" nowrap>	
				<bean:message key="sq.trustname" bundle="common"/>
				</td>
				<td align=left nowrap>
				<bean:write name="bankBean" property="trustName"/>
				</td>
				
			</tr>
			
			<tr>
				<td  class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.securitycategory" bundle="common"/>
				</td><td align=left nowrap >
					<bean:write name="bankBean" property="securityCategory"/>
				</td>
				<td class="tableTextBold" align="right" nowrap>	
				<bean:message key="sq.arranger" bundle="common"/>
				</td>
				<td align=left nowrap>
				<bean:write name="bankBean" property="arranger"/>
				</td>
				
			</tr>
			
			<tr>
			
			<td   class="tableTextBold" align=right nowrap><font color=red>*</font>
					 <bean:message key="bankletter.lettterno" bundle="common"/>
				</td>
				<td align=left width="35%" nowrap>
					<html:text size="12" name="bankBean" property="bankLetterNo" styleId="bankLetterNo" styleClass="TextField" style='width:180px'  tabindex="1" maxlength="50" />	
					
				</td>	
			
				<td  width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
					  <bean:message key="bankletter.bankname" bundle="common"/>:
				</td><td align=left width="35%" >
					<html:select  property="accountNo" styleClass="TextField" style='width:230px'  tabindex="2" >
					<html:option value="">[Select one]</html:option>
					<html:options collection="banks" property="accountNo" labelProperty="bankDetails"/>
					</html:select>
				</td>			
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
						<html:text property="benificiaryBankName" maxlength="50"  style="width: 120px;"  tabindex="3" styleClass="TextField" />
						


					</td>
					
				
				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.branchname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="benificiaryBranch" styleClass="TextField" style="width:120 px" maxlength="50" tabindex="4"  />
					</td>
									
				</tr>
				<tr>
						<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="lettertobank.accounttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    
						
						<html:select property="accountType"   styleClass="TextField" style='width:130px' tabindex="5">          
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
					    <html:text size="12" property="ifscCode" styleClass="TextField" style="width:120 px" maxlength="11" tabindex="6"  />
					</td>
                       
				</tr>
				
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.creditaccountno" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="creditAccountNo" styleClass="TextField" style="width:120 px" maxlength="20" tabindex="7"  />
						

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="lettertobank.beneficiaryname" bundle="common" />
						:
					</td>
					<td align=left width="25%" >
						<html:text property="beneficiaryName" size="12" styleClass="TextField" style="width:160 px" tabindex="8" maxlength="100"/>

					</td>
					
				</tr>
				
				<tr>
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="lettertobank.remarks" bundle="common" />
					</td>
					
					<td align=left width="25%">
						<html:textarea property="remarks" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].remarks.value,999)" tabindex="9" />

					</td>
				</tr>
			
			<tr>
			<td  width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
					  <bean:message key="bankletter.dealdate" bundle="common"/>:
				</td><td align=left width="35%" >
					<html:text size="12" property="dealDate" name="bankBean" styleId="dealDate" styleClass="TextField" style='width:98 px' tabindex="10" maxlength="12" />	<html:link href="javascript:showCalendar('dealDate');" tabindex="11">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>	
			<td  width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
					  <bean:message key="bankletter.settelementdate" bundle="common"/>:
				</td><td align=left width="35%" >
					<html:text size="12" property="settlementDate" name="bankBean" styleId="settlementDate" styleClass="TextField" style='width:98 px' tabindex="12" maxlength="12" onblur="getYTMValue();" />	<html:link href="javascript:showCalendar('settlementDate');" tabindex="13">
						<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
				</td>
			
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
			<bean:message key="sq.couponrate" bundle="common"/>
			</td>
			<td align=left width=35%>
			<html:text  property="faceValue" styleId="faceValue" name="bankBean" onblur="getYTMValue();" styleClass="TextField" style='width:80px'  tabindex="14" maxlength="6"/>
			</td>
			
			<td width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
			<bean:message key="bankletter.noofdaysfromlastdate" bundle="common"/>
			</td>
			<td align=left width=35%>
			<html:text  property="noofDays" styleId="noofDays" styleClass="TextField" style='width:30px' tabindex="15" maxlength="2"/>
			</td>
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font><bean:message key="sq.facevalueinrs" bundle="common" />
						:
					</td>
			<td align=left width="35%">
						<html:text property="investmentFaceValue" name="bankBean" style="width:90px" styleClass="TextFieldFont" tabindex="16" onblur="calPurchaseValue();getYTMValue();" />
						<html:hidden property="priceoffered" name="bankBean"/>
					</td>
					<td width="35%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.noofunits" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="numberOfUnits" name="bankBean" style="width: 120px;" tabindex="17" styleClass="TextField" readonly="true" onblur="calPurchaseValue();" />
					</td>
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
			</td>
			<td align=left width="35%">
						<html:text property="purchasePrice" name="bankBean" style="width:90px" tabindex="18" styleClass="TextFieldFont"   onblur="calPurchaseValue();getYTMValue();" />
			</td>
			
			
			<td width="35%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.ytminpercent" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text property="ytm" name="bankBean" readonly="true" style="width:90px" tabindex="19"  />
					</td>
				
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" id="premium" align=right nowrap>
						<bean:message key="sq.premiumpaid" bundle="common" />
						:
					</td>
					<td width="35%" class="tableTextBold" id="discount" align=right nowrap>
						<bean:message key="sq.discount" bundle="common" />
						:
					</td>
					<td align=left width="35%">
						<html:text property="premiumPaid" name="bankBean" style="width: 120px;" tabindex="20" styleClass="TextField" readonly="true" onblur="calPurchaseValue();" />
					</td>
			<td width="35%" class="tableTextBold" align=right nowrap>
			<bean:message key="sq.purchasepriseoption" bundle="common" />
			</td>
			<td align=left width="35%">
					<html:select property="purchaseOption" onchange="displayPurchase();" name="bankBean" styleClass="TextField" tabindex="21" style="width:100 px">
							
							<html:options collection="purchasePriseList" property="code"  labelProperty="name" />
						</html:select>
						
					</td>
				
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
					</td>
					<td align=left width="35%">
						<html:text property="principleAmt" name="bankBean" style="width:90px" tabindex="22" styleClass="TextFieldFont"  readonly="true" />
					</td>
					<td width="35%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.maturitydate" bundle="common" />
						:
					</td>
					<td align=left width="35%">
						<html:text property="maturityDate" styleId="maturityDate" name="bankBean" style="width: 100px;" tabindex="23" styleClass="TextField" onblur="getYTMValue();" />
						<html:link href="javascript:showCalendar('maturityDate');" tabindex="24">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
			</tr>
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.investmentmode" bundle="common" />
						:
					</td>
					<td align=left width="35%">
						<html:select property="investmentMode" name="bankBean" styleClass="TextField" style="width:100 px" onchange="showInterestDates();"  tabindex="25">
							<html:option value="">[Select One]</html:option>
							<html:options collection="investmentModeList" property="code" labelProperty="name" />
						</html:select>
					</td>
			<td width="35%" class="tableTextBold" align=right nowrap>
				<font color=red>*</font><bean:message key="sq.rateofinterest" bundle="common" />
						:
					</td>					
					<td width="35%" align=left>
						<html:text property="interestRate" name="bankBean" style="width: 120px;" tabindex="26" styleClass="TextField" maxlength="26" />
					</td>
			</tr>
			<tr>
			<td id="yearlydisplaytext" width="35%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td id="yearlydisplaydata" align=left width="35%">
						<html:select property="interestDate"  name="bankBean" styleClass="TextField" tabindex="27" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth"  name="bankBean" styleClass="TextField" tabindex="28" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
					</td>
					<td id="yearlydisplaynone" colspan=2>&nbsp;</td>
					
					
					<td id="halfyearlydisplaytext" width="35%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.dateofinterest" bundle="common" />
						:
					</td>
					<td id="halfyearlydisplaydata" align=left width="35%">
						<html:select property="interestDate1" name="bankBean"  styleClass="TextField" tabindex="29" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestDatesList" property="code"  labelProperty="name" />
						</html:select>
						
						<html:select property="interestMonth1" name="bankBean"  styleClass="TextField" tabindex="30" style="width:50px">
							<html:option value="">[select One]</html:option>
							<html:options collection="interestMonthsList" property="code"  labelProperty="name" />
						</html:select>
					</td>
			</tr>
			
			
			<tr>
			<td width="35%" class="tableTextBold" align=right nowrap><font color=red>*</font>
			<bean:message key="bankletter.accuredamount" bundle="common"/>
			</td>
			<td align=left width=35%>
			<html:text  property="accuredAmount"  styleId="accuredAmount" onchange="ConsiderationAmount();" styleClass="TextField" style='width:120px'  tabindex="31" maxlength="15"/>
			</td>
			<td width="35%" class="tableTextBold" align="right" nowrap>
			<bean:message key="bankletter.totalconsideration" bundle="common"/>
			</td>
			<td align="left" width="35%">
			<html:text property="totalConsideration" styleId="totalConsideration" styleClass="TextField" style='width:120px'  readonly="true"  />
			
			
			</tr>
			
			<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="lettertobank.leftsign" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="leftSign" styleClass="TextField" style="width:120 px" maxlength="25" tabindex="32"  />
						

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="lettertobank.rightsign" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="rightSign" styleClass="TextField" style="width:120 px" maxlength="25" tabindex="33"  />
						

					</td>
				
				</tr>
					
			 
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			  <td  colspan=4 align=center ><html:submit styleClass="butt" value="Save"  tabindex="34" />&nbsp;<html:button styleClass="butt" property="Clear" value="Clear" tabindex="35" onclick="clearData();"/>&nbsp;<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchGenerateBankLetter.do?method=searchBankLetter'" onblur="document.forms[0].letterNo.focus();"  tabindex="36"/>
			</td>	
			</tr>
			</table>
				<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
