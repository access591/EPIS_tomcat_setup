<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<jsp:useBean id="sh" class="com.epis.utilities.SQLHelper" scope="request"/>

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
		document.forms[0].extName.value=photo1;
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
		
        }
					  
			
			winOpened = false;
			function openDocument()
			{
				var path=document.forms[0].extName.value;
				if(path!="")
				{
					var file="/uploads/documents/"+document.forms[0].formCd.value+path;
					
					
				wd = screen.availWidth - 10;
				ht = screen.availHeight - 35;
				winHandle = window.open(file,"docWin","left=0,top=0,width=" + wd + ",height=" + ht + ",toolbar=no,status=no,scrollbars=yes,");
				winOpened=true;
				}
			}
					
			function clearData(){
	       		window.document.forms[0].reset();
	  		}
	  		  		
  		
		</script>
	</head>
	<body onload="document.forms[0].noofBonds.focus();">
		<html:form action="/editformfilling.do?method=updateformfilling&mode=rbiauction" method="post" enctype="multipart/form-data" onsubmit="return validate();">
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
						<bean:write property="proposalRefNo" name="formfillBean"/>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="formfilling.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<bean:write property="trustType" name="formfillBean"/>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="formfilling.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<bean:write property="securityCategory" name="formfillBean" />
						


					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.markettype" bundle="common" />
						:
					</td>
					<td align=left width="25%">
					    <bean:write property="marketType" name="formfillBean"/>
					</td>
					
									
				</tr>
				<tr>
				
		<td width="25%" class="tableTextBold" align=right nowrap>
<font color=red>*</font>
<bean:message key="formfilling.noofbonds" bundle="common" />
</td>
<td align=left width="25%">
<html:text size="12" property="noofBonds" name="formfillBean" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="5" />

</td>
										<td width="25%" class="tableTextBold" align=right nowrap >
						<font color=red>*</font>
						<bean:message key="formfilling.securityname" bundle="common" />
						:
					</td>
					<td align=left width="25%" >
						<html:text property="securityName" name="formfillBean" size="12" styleClass="TextField" readonly="true" style="width:160 px" tabindex="6" maxlength="100"/>

					</td>
				</tr>
			<tr>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.amountinrs" bundle="common" />:
					</td>
					<td align=left width="25%" nowrap="nowrap">
					<bean:write name="formfillBean" property="investAmt"/>
					</td>
					
					<td colspan=2>&nbsp;</td>
					
					
					
				</tr>
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.faxno" bundle="common" />:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text size="12" property="faxNumber" name="formfillBean" styleClass="TextField" style="width:120 px" maxlength="15" tabindex="7"  />
						

					</td>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<bean:message key="formfilling.contactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactNumber" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="13"  />
				</td>
				</tr>
				
				<tr>
					
					
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.appdate" bundle="common" />
					</td>
					
					<td align=left width="25%"  nowrap>
						<html:text property="arrangerDate" name="formfillBean" styleClass="TextField" styleId="arrangerDate" style='width:150 px' tabindex="3" maxlength="12" />
						<html:link href="javascript:showCalendar('arrangerDate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.dateofauction" bundle="common" />
						:
					</td>
					<td align=left width="25%"  nowrap>
						<html:text property="auctionDate" name="formfillBean" styleClass="TextField" styleId="auctionDate" style='width:150 px' tabindex="3" maxlength="12" />
						<html:link href="javascript:showCalendar('auctionDate');" tabindex="4">
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
				<html:text property="contactPerson" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="12"  />
				</td>
					
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.arrangeraddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="mailingAddress" name="formfillBean" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)" tabindex="11" />
					</td>
					

					

				</tr>
				
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="lettertobank.bankname" bundle="common"/>:
					</td><td align=left width="25%" >
					<html:select property="accountNo" name="formfillBean"  styleClass="TextField" style='width:230px' tabindex="5">          
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
						<html:text property="panno" name="formfillBean" value="AACTA4918P" styleId="panno" readonly="true" maxlength="20" style="width: 120px;" tabindex="10" styleClass="TextField" />
						

					</td>
				</tr>
				
				
				<tr>
				
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<bean:message key="formfilling.uploadeddocument" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:file property="uploadDocument"/>
				<html:hidden property="extName" name="formfillBean"/>
							
				<a href="javascript:void(0)" onclick="openDocument();"><bean:message key="formfilling.uploadeddocument" bundle="common"/></a></td>
				<td colspan=2>&nbsp;</td>
				</tr>
				
					
				<tr>
				<td  colspan=4 align=center id="displayNone">&nbsp;
				<html:hidden property="formCd" name="formfillBean"/>
				</td>
				</tr>

				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" tabindex="15">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						&nbsp;
						<html:button styleClass="butt" property="Clear" tabindex="16" onclick="clearData();">Clear </html:button>
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchFormFilling.do?method=searchFormFilling&mode=rbiauction'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="17" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
