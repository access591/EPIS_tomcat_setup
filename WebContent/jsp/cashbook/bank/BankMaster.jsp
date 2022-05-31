<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">		
				function checkRecord(){
					if(document.forms[0].bankName.value == ""){
						alert("Please Enter Bank Name (Mandatory)");
						document.forms[0].bankName.focus();
						return false;
					}
					if(document.forms[0].branchName.value == ""){
						alert("Please Enter Branch Name (Mandatory)");
						document.forms[0].branchName.focus();
						return false;
					}
					if(document.forms[0].bankCode.value == ""){
						alert("Please Enter Bank Code (Mandatory)");
						document.forms[0].bankCode.focus();
						return false;
					}
					if(document.forms[0].phoneNo.value == ""){
						alert("Please Enter Phone No. (Mandatory)");
						document.forms[0].phoneNo.focus();
						return false;
					}
					if(!ValidatePh(document.forms[0].phoneNo.value)){
						alert("Please Enter Valid Phone No. ");
						document.forms[0].phoneNo.focus();
						return false;
					}
					if(document.forms[0].faxNo.value == ""){
						alert("Please Enter Fax No. (Mandatory)");
						document.forms[0].faxNo.focus();
						return false;
					}
					if(!ValidatePh(document.forms[0].faxNo.value)){
						alert("Please Enter Valid Fax No. ");
						document.forms[0].faxNo.focus();
						return false;
					}
					if(document.forms[0].accountCode.value == ""){
						alert("Please Enter Account Code (Mandatory)");
						document.forms[0].accountCode.focus();
						return false;
					}
					if(document.forms[0].accountNo.value == ""){
						alert("Please Enter Account No. (Mandatory)");
						document.forms[0].accountNo.focus();
						return false;
					}
					if(document.forms[0].trustType.value == ""){
						alert("Please Enter Trust Type (Mandatory)");
						document.forms[0].trustType.focus();
						return false;
					}
					if(document.forms[0].IFSCCode.value == ""){
						alert("Please Enter IFSC Code (Mandatory)");
						document.forms[0].IFSCCode.focus();
						return false;
					}
					if(document.forms[0].NEFTRTGSCode.value == ""){
						alert("Please Enter NEFT/RTGS Code (Mandatory)");
						document.forms[0].NEFTRTGSCode.focus();
						return false;
					}
					if(document.forms[0].MICRNo.value == ""){
						alert("Please Enter MICR Code (Mandatory)");
						document.forms[0].MICRNo.focus();
						return false;
					}
					if(document.forms[0].contactPerson.value == ""){
						alert("Please Enter Contact Person Code (Mandatory)");
						document.forms[0].contactPerson.focus();
						return false;
					}
					if(document.forms[0].mobileNo.value == ""){
						alert("Please Enter Mobile No Code (Mandatory)");
						document.forms[0].mobileNo.focus();
						return false;
					}
					if(!ValidatePh(document.forms[0].MobileNo.value)){
						alert("Please Enter Valid Mobile No. ");
						document.forms[0].MobileNo.focus();
						return false;
					}					
				}
				function popupWindow(mylink, windowname){
					if (! window.focus){
						return true;
					}
					var href;
					if (typeof(mylink) == 'string'){
					   href=mylink;
					} else {
						href=mylink.href;
					}
					progress=window.open(href, windowname, 'width=750,height=500,statusbar=yes,scrollbars=yes,resizable=yes');
					return true;
				}
				function details(accountCode,particular){	
		   			document.forms[0].accountCode.value=accountCode;
				}
				var xmlHttp;
				function createXMLHttpRequest(){
					if(window.ActiveXObject) {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					} else if (window.XMLHttpRequest) {
						xmlHttp = new XMLHttpRequest();
					}
				}
				function getNodeValue(obj,tag) { 
					if(obj.getElementsByTagName(tag)[0].firstChild){
						return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
					}else return "";
				}
				function getUnits(){
					document.forms[0].unitName.length=1 ;
					createXMLHttpRequest();	
					var url ="/bankInfo.do?method=getUnits&&region="+document.forms[0].region.value;
					xmlHttp.open("post", url, true);
					xmlHttp.onreadystatechange = getAjaxUnits;
					xmlHttp.send(null);
				}
				function getAjaxUnits(){				
					if(xmlHttp.readyState ==4){		
						if(xmlHttp.status == 200){ 
							var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');
							if(stype.length==0){
								var unitCode = document.getElementById("unitCode");			
							}else{
								for(i=0;i<stype.length;i++){
									var unitCode = getNodeValue(stype[i],'unitCode');
									var unitName = getNodeValue(stype[i],'unitName');
									document.forms[0].unitName.options[i+1]=new Option(unitName,unitCode);						
								}
								if(bool){
									document.forms[0].unitName.value=trim('<%=request.getAttribute("unitName")%>');
									bool=false;
								}else{
									document.getElementById("unitName").options[0].selected=true;
								}
							}
						}
					}
				}
				var bool = false;
				function whileloading(){
					document.forms[0].bankName.focus();
					if(document.forms[0].ScreenType.value=="EDIT"){
						bool=true;
						getUnits();
						document.forms[0].accountNo.readOnly=true;
					}		
								
				}
		</script>
	</head>

	<body onload="whileloading()">
		<html:form action="/bankInfo.do?method=add" onsubmit="javascript : return checkRecord()" method="post">

			<%=ScreenUtilities.screenHeader("bankInfo.title")%>

			<table width="550" border="0" cellspacing="3" cellpadding="0">

				<tr>
					<td colspan=5>
						<html:errors bundle="error" />
						<input type=hidden name="ScreenType" value="<bean:write name="ScreenType" scope="request"/>" />
					</td>
				</tr>
				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.bankName" bundle="common" />
					</td>
					<td align="left">
						<html:text property="bankName" styleClass="tableText" maxlength="50" />
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.branchName" bundle="common" />
					</td>
					<td align="left">
						<html:text property="branchName" styleClass="tableText" maxlength="50" />
					</td>

				</tr>

				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.bankCode" bundle="common" />
					</td>
					<td align="left">
						<html:text property="bankCode" styleClass="tableText" maxlength="10" />
					</td>

					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.Address" bundle="common" />
					</td>
					<td align="left">
						<html:text property="address" onblur="if(this.value.length>150){alert('Address length should be less than 150 characters');this.focus();}" styleClass="tableText" />
					</td>

				</tr>

				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.phoneNo" bundle="common" />
					</td>
					<td align="left">
						<html:text property="phoneNo" styleClass="tableText" maxlength="15" />
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.faxNo" bundle="common" />
					</td>
					<td align="left">
						<html:text property="faxNo" styleClass="tableText" maxlength="15" />
					</td>

				</tr>

				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.accountCode" bundle="common" />
					</td>
					<td align="left">
						<html:select property="accountCode" styleClass="TextField" style="width:120 px ;height:50 px">
							<html:option value="">Select One</html:option>
							<html:options name="AccountingCodeInfo" property="accountHead" labelProperty="accountHead" labelName="accountHead" collection="accountHeads" />
						</html:select>
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.bankAcno" bundle="common" />
					</td>
					<td align="left">
						<html:text property="accountNo" styleClass="tableText" maxlength="20" />
					</td>

				</tr>
				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.accountType" bundle="common" />
					</td>
					<td align="left">
						<html:select property="accountType" styleClass="TextField" style="width: 90px">
							<html:option value="S">
								Savings
							</html:option>
							<html:option value="C">
								Current
							</html:option>

						</html:select>
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.accountName" bundle="common" />
					</td>
					<td align="left">
						<html:text property="accountName" styleClass="tableText" maxlength="100" />
					</td>



				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.IFSCCode" bundle="common" />
					</td>

					<td align="left">
						<html:text property="IFSCCode" styleClass="tableText" maxlength="11" />
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.NEFTRTGSCode" bundle="common" />
					</td>
					<td align="left">
						<html:text property="NEFTRTGSCode" styleClass="tableText" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.MICRNo" bundle="common" />
					</td>
					<td align="left">
						<html:text property="MICRNo" styleClass="tableText" maxlength="9" />

					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.trustType" bundle="common" />
					</td>
					<td align="left">
						<html:select property="trustType" styleClass="TextField" style="width:120 px ;height:50 px">
							<html:option value="">Select One</html:option>
							<html:options name="TrustTypeBean" property="trustType" labelProperty="trustType" labelName="trustType" collection="trustTypes" />
						</html:select>
					</td>


				</tr>
				<tr>
					<td class="tableTextBold" align="right" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.MobileNo" bundle="common" />
					</td>
					<td align="left">
						<html:text property="mobileNo" styleClass="tableText" maxlength="10" />
					</td>

					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.region" bundle="common" />
						&nbsp;
					</td>
					<td align="left">
						<html:select property="region" styleClass="TextField" style="width:120 px ;height:50 px" onchange="getUnits()">
							<html:option value="">Select One</html:option>
							<html:options name="RegionBean" property="regionName" labelProperty="regionName" labelName="regionName" collection="regions" />
						</html:select>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="bankInfo.unitName" bundle="common" />
						&nbsp;
					</td>
					<td align="left">
						<html:select property="unitName" styleClass="TextField" style="width: 90px">
							<html:option value="">
						Select One
					</html:option>
						</html:select>
					</td>
					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="bankInfo.ContactPerson" bundle="common" />
					</td>
					<td align="left">
						<html:text property="contactPerson" styleClass="tableText" maxlength="50" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<input type="submit" class="butt" value="Submit" tabindex="4" />
						<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>