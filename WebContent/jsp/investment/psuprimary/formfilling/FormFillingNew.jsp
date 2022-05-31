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
			    if(document.forms[0].investAmt.value=="") {
				  	alert("Please Enter Amount In Rs (Mandatory)");
				  	document.forms[0].investAmt.focus();
				  	return false;
			    }
			  	if(document.forms[0].investAmt.value!="" && parseFloat(document.forms[0].investAmt.value)!=0) {
					if(!ValidateFloatPoint(document.forms[0].investAmt.value,15,4)) {
						alert("Please Enter Valid Amount  (Expecting  Numeric Value)");
						document.forms[0].investAmt.focus();
						document.forms[0].investAmt.select();
						return false;
					}
				}
				if(document.forms[0].marketType.value==""){
					alert("Please Enter The Market Type (Mandatory)");
					document.forms[0].marketType.focus();
					return false;
				}
				
				if(document.forms[0].noofBonds.value==""){
					alert("Please Enter The No.of Bonds (Mandatory)");
					document.forms[0].noofBonds.focus();
					return false;
				}
				if(!ValidateNum(document.forms[0].noofBonds.value))
				{
					alert("Please Enter Valid No.of Bonds (Expection Numeric Value)");
					document.forms[0].noofBonds.focus();
					return false;
				}
				if(document.forms[0].securityName.value=="") {
					alert("Please Enter The Security Name  (Mandatory)");
					document.forms[0].securityName.focus();
					return false;
				}
				
				if(!ValidateSpecialAlphaNumeric(document.forms[0].securityName.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].securityName.focus();
				return false;
				}	
				
				
				
				if(document.forms[0].statueoftaxOption.value=="")
				{
					alert("Please Enter StatueOfTaxOption (Mandatory)");
					document.forms[0].statueoftaxOption.focus();
					return false;
				}
				if(document.forms[0].nameofApplicant.value=="")
				{
					alert("Please Enter Name Of Applicant (Mandatory)");
					document.forms[0].nameofApplicant.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].nameofApplicant.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].nameofApplicant.focus();
				return false;
				}
				
				if(document.forms[0].panno.value=="")
				{
					alert("Please Enter  Applicant Pan No (Mandatory)");
					document.forms[0].panno.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].panno.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].panno.focus();
				return false;
				}
				
				
				if(document.forms[0].mailingAddress.value=="")
				{
					alert("Please Enter  Mailing Address (Mandatory)");
					document.forms[0].mailingAddress.focus();
					return false;
				}
				
				
				
				
				if(document.forms[0].contactPerson.value=="")
				{
					alert("Please Enter  Contact Person (Mandatory)");
					document.forms[0].contactPerson.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].contactPerson.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].contactPerson.focus();
				return false;
				}
				
				if(document.forms[0].contactNumber.value=="")
					{
						alert("Please Enter The Contact Number(Mandatory)");
						document.forms[0].contactNumber.focus();
						return(false);
					}
					if(!ValidateNum(document.forms[0].contactNumber.value))
					{
						alert("Please Enter Valid Telephone Number (Expecting  Numeric Value)");
						document.forms[0].contactNumber.focus();
						return(false);
					}
				
				
		if(document.forms[0].uploadDocument.value!=''){  
			

		photo=document.forms[0].uploadDocument.value;
		photo=photo.substr(photo.length-5,photo.length);
		photo1=photo.substr(photo.indexOf("."),photo.length);

		if( photo1!=".gif" && photo1!=".jpg" && photo1!=".jpeg" && photo1!=".pdf" && photo1!=".txt" && photo1!=".doc" && photo1!=".xls" && photo1!=".GIF" && photo1!=".JPG" && photo1!=".JPEG" && photo1!=".PDF" && photo1!=".TXT" && photo1!=".DOC" && photo1!=".XLS"  )
		{
			  alert("Please select a file of Type (*.jpg,*.jpeg,*.gif,*.pdf,*.txt,*.doc,*.xls)");
			  document.forms[0].uploadDocument.focus();
		      return false;
		}
		if(document.forms[0].partyContactNo.value!='')
		{
			if(!ValidateNum(document.forms[0].partyContactNo.value))
			{
						alert("Please Enter Valid Telephone Number (Expecting  Numeric Value)");
						document.forms[0].partyContactNo.focus();
						return(false);
			}
		}
		document.forms[0].extName.value=photo1;
        }
				
					  
			}
			
			var xmlhttpreq;
		
			function  LoadTrustType() {
				document.forms[0].trustType.value='';
				document.forms[0].investAmt.value='';
				document.forms[0].marketTypedef.value='';
				document.forms[0].marketType.value='';
				
				document.forms[0].securityCategory.value='';
				
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
					document.forms[0].securityCategory.value=security;
					document.forms[0].investAmt.value=investamount;
					document.forms[0].marketTypedef.value=marketdescription;
					document.forms[0].marketType.value=marketType;
										
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
		  		
  		
		</script>
	</head>
	<body onload="document.forms[0].proposalRefNo.focus();">
		<html:form action="/addformfilling.do?method=addformfilling&mode=PSUPrimary" method="post" enctype="multipart/form-data" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("formfilling.title")%>
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
						<html:select property="proposalRefNo" onchange="LoadTrustType();" styleClass="TextField" style="width:200 px" tabindex="1">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="proposalRefList" property="refNo" labelProperty="refNo" />
						</html:select>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="formfilling.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="trustType" maxlength="5" style="width: 120px;" styleClass="TextField" tabindex="2" readonly="true" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="formfilling.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="securityCategory" style="width: 120px;" readonly="true" tabindex="3" styleClass="TextField" />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.markettype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <html:text size="12" property="marketTypedef" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="4" readonly="true" />
						<html:hidden property="marketType" />

					</td>
					
									
				</tr>
				<tr>
				
		<td width="25%" class="tableTextBold" align=right nowrap>
<font color=red>*</font>
<bean:message key="formfilling.noofbonds" bundle="common" />
</td>
<td align=left width="25%">
<html:text size="12" property="noofBonds" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="5" />

</td>
										<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="formfilling.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%" >
						<html:text property="securityName" size="12" styleClass="TextField" style="width:160 px" tabindex="6" maxlength="100"/>

					</td>
				</tr>
			<tr>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.amountinrs" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="investAmt" styleClass="TextField" style="width:120 px" maxlength="18" tabindex="7"  />
						

					</td>
					
					<td  width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.statueoftaxoption" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:select property="statueoftaxOption"  styleClass="TextField" style="width:200 px" tabindex="8">
							<html:options collection="taxoption" property="code" labelProperty="name" />
						</html:select>
					</td>
					
					
				</tr>
				
				<tr>
					
					
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.nameofApplicant" bundle="common" />
					</td>
					
					<td align=left width="25%">
						<html:text size="12" property="nameofApplicant" value="AAI EPF TRUST" styleClass="TextField" style="width:160 px" maxlength="100" tabindex="9" readonly="true" />

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.applicantspanno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="panno" value="AACTA4918P" styleId="panno" readonly="true" maxlength="20" style="width: 120px;" tabindex="10" styleClass="TextField" />
						

					</td>

				</tr>

				<tr>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.mailingaddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="mailingAddress" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)" value="Room No. 246,CPF SECTION,RAJIV GANDHI BHAWAN,NEW DELHI." readonly="true" tabindex="11" />
					</td>
					
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<font color=red>*</font>
					<bean:message key="formfilling.contactperson" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactPerson" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="12"  />
				</td>
					

				</tr>
				<tr>
					
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<font color=red>*</font>
				<bean:message key="formfilling.contactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactNumber" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="13"  />
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<bean:message key="formfilling.uploadeddocument" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:file property="uploadDocument" tabindex="14"/>
				<html:hidden property="extName"/>
				</td>
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.partyname" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="partyName" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="15"  />
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.partycontactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="partyContactNo" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="16"  />
				</td>
					
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="formfilling.partyaddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="partyAddress" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)"  tabindex="17" />
					</td>
					<td colspan=2>&nbsp;</td>
				
				</tr>
				
				
				<tr>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.pname" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="pname" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="18"  />
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.pid" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="pid" styleClass="TextField" style="width: 120px;" maxlength="25" tabindex="19"  />
				</td>
					
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="formfilling.clientid" bundle="common" />
					</td>
					<td align=left width="25%">
						<html:text property="clientId" styleClass="TextField" style="width: 120px;" maxlength="25" tabindex="20"  />
						
					</td>
					<td colspan=2>&nbsp;</td>
				
				</tr>
			<tr>
				
				<td colspan=2 width="50%"></td>
				</tr>
				
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="15">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="16" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchFormFilling.do?method=searchFormFilling&mode=PSUPrimary'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="17" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
