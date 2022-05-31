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
		document.forms[0].extName.value=photo1;
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
		<html:form action="/editformfilling.do?method=updateformfilling&mode=PSUPrimary" method="post" enctype="multipart/form-data" onsubmit="return validate();">
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
						<html:text property="securityName" name="formfillBean" size="12" styleClass="TextField" style="width:160 px" tabindex="6" maxlength="100"/>

					</td>
				</tr>
			<tr>

				<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.amountinrs" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<bean:write property="investAmt" name="formfillBean"/>
						<bean:define id="amount" property="investAmt" name="formfillBean"/>
						</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
					<bean:message key="formfilling.figureinwords" bundle="common"/>:</td>
					<td align="left" width="25%" nowrap="nowrap">
					
					<%=sh.ConvertInWords(Double.parseDouble((String)amount))%>
					</td>
				</tr>
				
				<tr>
					
					<td  width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.statueoftaxoption" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:select property="statueoftaxOption" name="formfillBean"  styleClass="TextField" style="width:200 px" tabindex="9">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="taxoption" property="code" labelProperty="name" />
						</html:select>
					</td>
					<td   width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.nameofApplicant" bundle="common" />
					</td>
					
					<td align=left width="25%">
						<html:text size="12" property="nameofApplicant" name="formfillBean" styleClass="TextField" style="width:160 px" maxlength="100" tabindex="10" />

					</td>

				</tr>

				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.applicantspanno" bundle="common" />
						:
					</td>
					<td align=left width="25%">
						<html:text property="panno" styleId="panno" name="formfillBean" readonly="true" maxlength="20" style="width: 120px;" tabindex="11" styleClass="TextField" />
						

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="formfilling.mailingaddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="mailingAddress" name="formfillBean" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)" tabindex="12" />
					</td>

				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align="right" nowrap>
					<font color=red>*</font>
					<bean:message key="formfilling.contactperson" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactPerson"  name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="13"  />
				</td>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<font color=red>*</font>
				<bean:message key="formfilling.contactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="contactNumber" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="14"  />
				</td>
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				<bean:message key="formfilling.uploadeddocument" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:file property="uploadDocument"/>
				<html:hidden property="extName" name="formfillBean"/>
				</td>
				<td colspan=2 width="50%"  class="tableTextBold">
				
				<a href="javascript:void(0)" onclick="openDocument();"><bean:message key="formfilling.uploadeddocument" bundle="common"/></a></td>
				</tr>
				<tr>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.partyname" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="partyName" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="15"  />
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.partycontactno" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="partyContactNo" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="14" tabindex="16"  />
				</td>
					
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="formfilling.partyaddress" bundle="common" />
					</td>
					<td align=left width="25%">
						
						<html:textarea property="partyAddress" name="formfillBean" rows="3" cols="15" style="WIDTH: 200 px" onkeypress="return TxtAreaMaxLength(document.forms[0].mailingAddress.value,300)"  tabindex="17" />
					</td>
					<td colspan=2>&nbsp;</td>
				
				</tr>
				
				
				<tr>
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.pname" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="pname" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="50" tabindex="18"  />
				</td>
				
				<td width="25%" class="tableTextBold" align="right" nowrap>
				
				<bean:message key="formfilling.pid" bundle="common"/>
				</td>
				<td align="left" width="25%">
				<html:text property="pid" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="25" tabindex="19"  />
				</td>
					
				</tr>
				
				<tr>
				<td width="25%" class="tableTextBold" align=right nowrap>
						
						<bean:message key="formfilling.clientid" bundle="common" />
					</td>
					<td align=left width="25%">
						<html:text property="clientId" name="formfillBean" styleClass="TextField" style="width: 120px;" maxlength="25" tabindex="20"  />
						
					</td>
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
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchFormFilling.do?method=searchFormFilling&mode=PSUPrimary'" onblur="document.forms[0].proposalRefNo.focus();" tabindex="17" />
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
