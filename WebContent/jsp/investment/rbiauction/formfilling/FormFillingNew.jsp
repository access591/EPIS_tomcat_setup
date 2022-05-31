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
			    
			    if(document.forms[0].marketType.value==""){
					alert("Please Enter The Market Type (Mandatory)");
					document.forms[0].marketType.focus();
					return false;
				}
				if(document.forms[0].faxNumber.value=="")
					{
						alert("Please Enter The Fax Number(Mandatory)");
						document.forms[0].faxNumber.focus();
						return(false);
					}
					if(!ValidateNum(document.forms[0].faxNumber.value))
					{
						alert("Please Enter Valid Fax Number (Expecting  Numeric Value)");
						document.forms[0].faxNumber.focus();
						return(false);
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
					if(document.forms[0].accountNo.value=="") {
				  	alert("Please Select  Account No.(Mandatory)");
				  	document.forms[0].accountNo.focus();
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
				if(document.forms[0].arrangerDate.value==''){
						alert("please Enter App. Date ");
						document.forms[0].arrangerDate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].arrangerDate)){
						document.forms[0].arrangerDate.select();
						return(false);
					   }
					if(document.forms[0].auctionDate.value==''){
						alert("please Enter Auction Date ");
						document.forms[0].auctionDate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].auctionDate)){
						document.forms[0].auctionDate.select();
						return(false);
					   }
				if(document.forms[0].marketType.value=="R")
				{
				if(document.forms[0].contactPerson.value=="")
				{
					alert("Please Enter  Arranger Name (Mandatory)");
					document.forms[0].contactPerson.focus();
					return false;
				}
				
				if(!ValidateAlphaNumeric(document.forms[0].contactPerson.value))
				{
				alert("Please Enter Valid Name(Expecting Alpha Numeric Value)");
				document.forms[0].contactPerson.focus();
				return false;
				}
				if(document.forms[0].mailingAddress.value=="")
				{
					alert("Please Enter  Arranger Address (Mandatory)");
					document.forms[0].mailingAddress.focus();
					return false;
				}
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
		
		document.forms[0].extName.value=photo1;
        }
        
			if(parseFloat(document.forms[0].investAmt.value)>parseFloat(document.forms[0].totalAmount.value))
			{
				alert("Please Enter Amount Invested is Less Than Or equalt Total Amount");
				document.forms[0].investAmt.focus();
				return false;
			}
			if(document.forms[0].securityName.length==2)
			{
				if(parseFloat(document.forms[0].investAmt.value)!=parseFloat(document.forms[0].totalAmount.value))
			{
				alert("Please Enter Amount Invested And Total Amount is NOt Equal");
				document.forms[0].investAmt.focus();
				return false;
			}
			}
					  
			}
			
			var xmlhttpreq;
		
			function  LoadTrustType() {
				document.forms[0].securityName.length=1;
				document.forms[0].trustType.value='';
				document.forms[0].totalAmount.value='';
				document.forms[0].marketTypedef.value='';
				document.forms[0].marketType.value='';
				
				document.forms[0].securityCategory.value='';
				
				if(!document.forms[0].proposalRefNo.value=="") 	{
					var url="jsp/investment/quotation/LoadTrust.jsp?proposalRefNo="+document.forms[0].proposalRefNo.value+"&mode=RBItrust";
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
				if(Data) 
					{
					
						for (i = 0; i < parseInt(Data.childNodes.length); i++) 
						{
							var arrangers = Data.childNodes[i];				
							
							
							document.forms[0].securityName.options[document.forms[0].securityName.options.length]	=	new Option(arrangers.getElementsByTagName("securityName")[0].firstChild.nodeValue,arrangers.getElementsByTagName("securityName")[0].firstChild.nodeValue);
						
						
						trustType = arrangers.getElementsByTagName("TrustType")[0].firstChild.nodeValue;
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
						
							
						}
						
					document.forms[0].trustType.value=trust;
					document.forms[0].securityCategory.value=security;
					document.forms[0].totalAmount.value=investamount;
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
		<html:form action="/addformfilling.do?method=addformfilling&mode=rbiauction" method="post" enctype="multipart/form-data" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("applicationform.title")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error"/>
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
						<bean:message key="formfilling.faxno" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="faxNumber" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="5"  />
						

					</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<font color=red>*</font>
				<bean:message key="formfilling.contactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactNumber" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="6"  />
				</td>
				</tr>
				
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.bankname" bundle="common"/>:
					</td><td align=left width="25%" >
					<html:select property="accountNo"   styleClass="TextField" style='width:230px' tabindex="7">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="banks" property="accountNo" labelProperty="bankDetails" />
                                             
                        </html:select>
                        </td>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.applicantspanno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="panno" value="AACTA4918P" styleId="panno" readonly="true" maxlength="20" style="width: 120px;" tabindex="8" styleClass="TextField" />
						

					</td>
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
				<bean:message key="formfilling.totalamount" bundle="common"/>:
				</td>
				<td align=left width="25%">
				<html:text property="totalAmount" style="width: 120px;" tabindex="9" readonly="true"/>
				</td>
				<td colspan=2>&nbsp;</td>
				</tr>
				
				
				<tr>
				
				
				
		<td width="25%" class="tableTextBold" align=right nowrap>
<font color=red>*</font>
<bean:message key="formfilling.noofbonds" bundle="common" />
</td>
<td align=left width="25%">
<html:text size="12" property="noofBonds" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="10" />

</td>
										<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="formfilling.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						
						<html:select property="securityName" styleClass="TextField" style="width:200 px" tabindex="11" >
						<html:option value="">[Select One]</html:option>
						</html:select>

					</td>
				</tr>
			<tr>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.amountinrs" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="investAmt" styleClass="TextField" style="width:120 px" maxlength="18" tabindex="12"  />
						

					</td>
					
					<td colspan=2 width="50%">&nbsp;</td>
					
					
					
					
				</tr>
				
				
				<tr>
					
					
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.appdate" bundle="common" />
					</td>
					
					<td align=left width="25%"  nowrap>
						<html:text property="arrangerDate" styleClass="TextField" styleId="arrangerDate" style='width:150 px' tabindex="13" maxlength="10" />
						<html:link href="javascript:showCalendar('arrangerDate');" tabindex="14">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.dateofauction" bundle="common" />
						:
					</td>
					<td align=left width="25%"  nowrap>
						<html:text property="auctionDate" styleClass="TextField" styleId="auctionDate" style='width:150 px' tabindex="15" maxlength="12" />
						<html:link href="javascript:showCalendar('auctionDate');" tabindex="16">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>

				</tr>

				<tr>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
					<font color=red>*</font>
					<bean:message key="formfilling.arrangername" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactPerson" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="17"  />
				</td>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.arrangeraddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="mailingAddress" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)" tabindex="18" />
					</td>
					

					

				</tr>
				
								
				<tr>
					
				
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<bean:message key="formfilling.uploadeddocument" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:file property="uploadDocument" tabindex="19"/>
				<html:hidden property="extName"/>
				</td>
				<td colspan=2 width="50%">&nbsp;</td>
				</tr>
					
				
				<tr>
				
				<td colspan=2 width="50%"></td>
				</tr>
				
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="20">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="21" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchFormFilling.do?method=searchFormFilling&mode=rbiauction'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="22" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
