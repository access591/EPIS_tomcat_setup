
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
		<title>My JSP 'RegionSearch.jsp' starting page</title>
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
		<SCRIPT type="text/javascript" src="js/GeneralFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		function validate(){
		if(document.forms[0].proposalRefNo.value==""){
		      alert("Please Enter The Proposal Reference No. (Mandatory)");
		     document.forms[0].proposalRefNo.focus();
		     return false;
		    }
		    if(document.forms[0].surplusFund.value=="")
		  {
		  	alert("Please Enter The Surplus Fund (Mandatory)");
		  	document.forms[0].surplusFund.focus();
		  	return false;
		  }
		  
			if(document.forms[0].surplusFund.value!="" && parseFloat(document.forms[0].surplusFund.value)!=0)
			if(!ValidateFloatPoint(document.forms[0].surplusFund.value,14,2))
			{
				alert("Please Enter Valid Surplus Fund (Expecting  Numeric Value)");
				document.forms[0].surplusFund.focus();
				document.forms[0].surplusFund.select();
				return false;
			}
		if(document.forms[0].marketType.value=="")
		{
			alert("Please Enter The Market Type (Mandatory)");
			document.forms[0].marketType.focus();
			return false;
		}
		if(document.forms[0].arrangers.value=="")
		{
		alert("Please Enter The Arrangers  (Mandatory)");
		document.forms[0].arrangers.focus();
		return false;
		}
		
		if(document.forms[0].minimumQuantum.value=="")
		  {
		  alert("Please Enter The Minimum Quantum Fund (Mandatory)");
		  	document.forms[0].minimumQuantum.focus();
		  	return false;
		  }
		  if(document.forms[0].minimumQuantum.value!="" && parseFloat(document.forms[0].minimumQuantum.value)!=0)
			if(!ValidateFloatPoint(document.forms[0].minimumQuantum.value,14,2))
			{
				alert("Please Enter Valid Minimum Quantum Fund (Expecting  Numeric Value)");
				document.forms[0].minimumQuantum.focus();
				document.forms[0].minimumQuantum.select();
				return false;
			}
	if(document.forms[0].quotationDate.value=="")
			{
				alert("Please Enter The Quotation Date (Mandatory)");
				document.forms[0].quotationDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].quotationDate)){
						document.forms[0].quotationDate.select();
						return(false);
					   }
	if(document.forms[0].quotationTime.value=="")
    {
       alert("Please Enter The quotationTime (Mandatory)");
        document.forms[0].quotationTime.focus();
        return false;
      }
			
			if(document.forms[0].quotationTime.value!="")
	{
		if(!validateTime24Hour(document.forms[0].quotationTime.value))
		{
			alert("Please Enter Valid Estimated Time (Expecting in HHMM format)");
			document.forms[0].quotationTime.focus();
			return(false);
		}
		
	}
	
	
	if(document.forms[0].validDate.value=="")
			{
				alert("Please Enter The Valid Date (Mandatory)");
				document.forms[0].validDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].validDate)){
						document.forms[0].validDate.select();
						return(false);
					   }
	if(document.forms[0].validTime.value=="")
    {
        alert("Please Enter The ValidTime (Mandatory)");
        document.forms[0].validTime.focus();
        return false;
      }
			
			if(document.forms[0].validTime.value!="")
	{
		if(!validateTime24Hour(document.forms[0].validTime.value))
		{
			alert("Please Enter Valid Estimated Time (Expecting in HHMM format)");
			document.forms[0].validTime.focus();
			return(false);
		}
	}
	
	if(document.forms[0].openDate.value=="")
			{
				alert("Please Enter The Open Date (Mandatory)");
				document.forms[0].openDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].openDate)){
						document.forms[0].openDate.select();
						return(false);
					   }
	if(document.forms[0].openTime.value=="")
    {
        alert("Please Enter The OpenTime (Mandatory)");
        document.forms[0].openTime.focus();
        return false;
      }
			
			if(document.forms[0].openTime.value!="")
	{
		if(!validateTime24Hour(document.forms[0].openTime.value))
		{
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
					if(document.forms[0].faceValue.value =="") {
					alert("Please Enter The Face Value (Mandatory)");
					document.forms[0].faceValue.focus();				
					return false;
					}
					if(document.forms[0].numberOfUnits.value =="") {
					alert("Please Enter The No. of Units (Mandatory)");
					document.forms[0].numberOfUnits.focus();				
					return false;
					}
					
					
					if(document.forms[0].maturityDate.value=="")
			{
				alert("Please Enter Maturity Date (Mandatory)");
				document.forms[0].maturityDate.focus();
				return false;
			}
			if(!convert_date(document.forms[0].maturityDate)){
						document.forms[0].maturityDate.select();
						return(false);
					   }
					
				
					
				}
		
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
	  		
function calFaceValue()
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
}
function calPurchaseValue()
{
	var purchaseamt=0.0;	
	if(!calFaceValue()){
		return false;
	}
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
	if(document.forms[0].faceValue.value !="" && document.forms[0].numberOfUnits.value != "" ){
		
			var unitfacevalue = parseFloat(document.forms[0].faceValue.value);
			var units = parseFloat(document.forms[0].numberOfUnits.value);
			var disc = 0.00;
			var paid = 0.00;
			
			if(document.forms[0].purchaseOption.value=='P')
			{
				purchaseamt= (unitfacevalue+parseFloat(document.forms[0].premiumPaid.value))*units;
				
			}
			if(document.forms[0].purchaseOption.value=='D')
			{
				purchaseamt= (unitfacevalue-parseFloat(document.forms[0].premiumPaid.value))*units;
			}
			
			
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
	calPurchaseValue();
	}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].proposalRefNo.focus();displayData();displayPurchase();" >
	<html:form action="/editQuotationRequest.do?method=updateQuotationRequest" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("quotation.title")%>
		<table width="450" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="quotation.proposalrefno" bundle="common"/>:
				</td><td align=left width="25%" >
				<html:text property="proposalRefNo" name="quotationRequest"  style="width: 120px;" readonly="true" tabindex="1" styleClass="TextField"/>      
					
					
				</td>	
				<td  width="25%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.trusttype" bundle="common"/>:
				</td><td align=left width="25%" >
					<html:text property="trustType" name="quotationRequest" maxlength="5" style="width: 120px;" styleClass="TextField" tabindex="2" readonly="true"/>        	
				</td>						
			</tr>
			<tr>
			<td  width="25%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.securitycategory" bundle="common"/>:
				</td><td align=left width="25%" >
					<html:text property="securityName" name="quotationRequest"  style="width: 120px;" readonly="true" tabindex="3" styleClass="TextField"/>      
					<html:hidden property="securityCategory" name="quotationRequest"/>
                        
					
				</td>
				<td  width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="quotation.surplusfund" bundle="common"/>:
				</td><td align=left width="25%" nowrap="nowrap">
					<html:text size="12" property="surplusFund" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="4"  readonly="true"/>	
				</td>				
			</tr>
			<tr>
				<td  width="25%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="quotation.marketType" bundle="common"/>:
				</td><td align=left width="25%" >
				<html:text size="12" property="marketTypedef" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="5"  readonly="true"/>	
					<html:hidden property="marketType" name="quotationRequest"/>
					
				</td>	
				<td  width="25%" class="tableTextBold" align=right nowrap rowspan=2>
					<font color=red>*</font><bean:message key="quotation.arrangers" bundle="common"/>:
				</td><td align=left width="25%" rowspan=2>
					<html:select property="arrangers" name="quotationRequest" styleClass="TextField" style="width:180 px;height:60 px;" multiple="true" size="3" tabindex="6">
					<html:options collection="arrangersList" property="arrangerCd" labelProperty="arrangerName"/>
					  </html:select>
				</td>						
			</tr>
			
			<tr>
									
				<td  width="30%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="quotation.minimumquantumoffered" bundle="common"/>
				</td><td align=left width="20%" >
					<html:text size="12" property="minimumQuantum" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="7"/>
					
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
			<td align=left width="25%" >
					<html:text property="quotationDate" name="quotationRequest" styleId="quotationDate" style="width: 95px;" tabindex="8" styleClass="TextField" />
					<html:link href="javascript:showCalendar('quotationDate');" tabindex="9">
					<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
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
				<td align=left width="25%" >
					<html:text size="12" property="quotationTime" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="10"/>
					
				</td>		
			
			</tr>
			
			<tr>
			<td  width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="quotation.validuptodate" bundle="common"/>:
				</td><td align=left width="25%" >
					<html:text property="validDate" name="quotationRequest" styleId="validDate" style="width: 95px;" tabindex="11" styleClass="TextField" />
					<html:link href="javascript:showCalendar('validDate');" tabindex="12">
					<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
					
				</td>	
				<td  width="30%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="quotation.validtime" bundle="common"/>
				</td><td align=left width="25%" >
					<html:text size="12" property="validTime" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="13"/>
					
				</td>		
			
			</tr>
			<tr>
			<td  width="25%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="quotation.openeddate" bundle="common"/>:
				</td><td align=left width="25%" >
					<html:text property="openDate" name="quotationRequest" styleId="openDate" style="width: 95px;" tabindex="14" styleClass="TextField" />
					<html:link href="javascript:showCalendar('openDate');" tabindex="15">
					<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
					</html:link>
					
				</td>	
				<td  width="25%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="quotation.openedtime" bundle="common"/>
				</td><td align=left width="25%" >
					<html:text size="12" property="openTime" name="quotationRequest" styleClass="TextField" style="width:120 px" maxlength="4" tabindex="16"/>
					
				</td>		
			
			</tr>
			<tr>
				<td  width="25%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.quantumsubmitaddress" bundle="common"/>:
				</td><td align=left width="25%" >
					<html:textarea  property="quotationAddress" name="quotationRequest" rows="3" cols="15"  style="WIDTH: 200 px"  onkeypress="return TxtAreaMaxLength(document.forms[0].quotationAddress.value,500)" tabindex="17"/>
					
				</td>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.remarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="remarks" name="quotationRequest" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>
						<html:hidden property="letterNo" name="quotationRequest"/>
					</td>
				
				
			</tr>
			<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.remarks1" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="formateRemarks" name="quotationRequest" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.modeofpaymentremarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="modeOfPaymentRemarks" name="quotationRequest" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.paymentthroughtremarks" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:textarea property="paymentThroughRemarks" name="quotationRequest" cols="15" rows="3" onkeypress="return TxtAreaMaxLength(this.value,999)" tabindex="18" ></html:textarea>

					</td>
					
					<td width="50%" colspan=2>
					</td>
				
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
					<font color="red">*</font><bean:message key="quotation.fromperiod" bundle="common"/>
				</td>
				<td align=left width="25%">
					<html:select property="fromPeriod" name="quotationRequest" styleClass="TextField" style="width:100 px" tabindex="19">
					<html:option value="">[SelectOne]</html:option>
					<html:options collection="periodList" property="code" labelProperty="name" />>
					</html:select>
				</td>
				
				<td width="25%" class="tableTextBold" align=right nowrap>
					<font color="red">*</font><bean:message key="quotation.toperiod" bundle="common"/>
				</td>
				<td align=left width="25%">
					<html:select property="toPeriod" name="quotationRequest" styleClass="TextField" style="width:100 px" tabindex="20">
					<html:option value="">[SelectOne]</html:option>
					<html:options collection="periodList" property="code" labelProperty="name" />
					</html:select>
				</td>
					
					
				</tr>
	
			<tr>
					<td colspan=4 align=center id="displayData">
						<table width="450" border=0 align="center">
						
						<tr>
						  <td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="quotation.nameofthetenderer" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="nameoftheTender" name="quotationRequest" style="width:90 px" tabindex="19" maxlength="30"/>
						</td>
						<td width="25%" class="tableTextBold" align=right nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.addressofthetenderer" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:textarea property="addressoftheTender" name="quotationRequest" tabindex="20" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].addressoftheTender.value,500)"  />
						</td>
						
						</tr>
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.telephoneno" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="telephoneNo" name="quotationRequest" style="width:90 px" tabindex="21" maxlength="10" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						
						<bean:message key="quotation.faxno" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:text property="faxNo" tabindex="22" name="quotationRequest" style="width:90 px" maxlength="15" />
						</td>
						</tr>
						
						
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.contactperson" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="contactPerson" name="quotationRequest" style="width:90 px" tabindex="23" maxlength="50" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.status" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:text property="status" name="quotationRequest" tabindex="24" style="width:90 px" maxlength="30" />
						</td>
						</tr>
						
						
						<tr>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.deliveryrequestedin" bundle="common" />:
						</td>
						<td width="25%" align="left">
						<html:text property="deliveryRequestedin" name="quotationRequest" tabindex="25" style="width:90 px" maxlength="50" />
						</td>
						<td width="25%" class="tableTextBold" align="right" nowrap>
						<font color="red">*</font>
						<bean:message key="quotation.accountno" bundle="common"/>
						</td>
						<td width="25%" align="left">
						<html:select property="accountNo" name="quotationRequest" tabindex="26" styleClass="TextField" style='width:280px' >
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
						<html:text name="quotationRequest" property="faceValue" style="width: 120px;" tabindex="27" styleClass="TextField" onblur="calPurchaseValue();"/>
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.noofunits" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text  name="quotationRequest" property="numberOfUnits" style="width: 120px;" tabindex="28" styleClass="TextField" onblur="calPurchaseValue();" />
					</td>
					</tr>
						<tr>
							<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="quotation.facevalueinrs" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text name="quotationRequest" property="investmentFaceValue" style="width:90px" styleClass="TextFieldFont" tabindex="29" readonly="true" />
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text name="quotationRequest" property="securityFullName" style="width: 120px;" tabindex="30" styleClass="TextField" />
					</td>
						</tr>
						<tr>
							<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchasepriseoption" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					<html:select name="quotationRequest" property="purchaseOption" onchange="displayPurchase();" styleClass="TextField" tabindex="31" style="width:100 px">
							
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
						<html:text name="quotationRequest" property="premiumPaid" style="width: 120px;" tabindex="32" styleClass="TextField" onblur="calPurchaseValue();" />
					</td>
					
					</tr>
					<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="sq.purchaseprice" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text name="quotationRequest" property="purchasePrice" style="width:90px" tabindex="33" styleClass="TextFieldFont"  readonly="true" />
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="sq.maturitydate" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text name="quotationRequest" property="maturityDate" styleId="maturityDate" style="width: 100px;" tabindex="34" styleClass="TextField"  />
						<html:link href="javascript:showCalendar('maturityDate');" tabindex="35">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					</tr>
					
						
						</table>
					</td>
				</tr>
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;</td>
				</tr>

			<tr>
			<td  colspan=4 align=center><html:submit   styleClass="butt" tabindex="36" > <bean:message key="button.save" bundle="common"/></html:submit>&nbsp;<html:button styleClass="butt" property="Clear" tabindex="37" onclick="clearData();">Clear </html:button>
			<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchQuotationRequests.do?method=searchQuotationRequests'" onblur="document.forms[0].proposalRefNo.focus();"  tabindex="38"/>
			</td>	
			</tr>
		</table>
				<%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>
