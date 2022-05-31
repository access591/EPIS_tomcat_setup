
<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

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
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT SRC="/js/EMail and Password.js"></SCRIPT>
		<SCRIPT SRC="/js/GeneralFunctions.js"></SCRIPT>
		<SCRIPT SRC="/js/DateTime1.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		function validate(){
		   if(document.forms[0].arrangerName.value=="")
		   {
		     alert("Please  Enter Arranger Name (Mandatory)");
		     document.forms[0].arrangerName.focus();
		     return false;
		   }
		  if(!ValidateSpecialAlphaNumeric(document.forms[0].arrangerName.value)) 
		  {
		    alert("Please Enter Valid Arranger Name (Expecting Alpha Numeric Value)"); 
		     document.forms[0].arrangerName.focus();
		     return false;
		  }
		  if(document.forms[0].categoryId.value=="")
		   {
		     alert("Please Select Security Category (Mandatory)");
		     document.forms[0].categoryId.focus();
		     return false;
		   }
		if(!ValidateTextArea(document.forms[0].regOffAddr.value)){
			alert("Please Enter Valid Address(Expecting Alpha Numeric Value)");
			document.forms[0].regOffAddr.focus();
			return false;
	    }	
		if(!TxtAreaMaxLength(document.forms[0].regOffAddr.value,100)){
		   document.forms[0].regOffAddr.focus();
		   return false;
		}
		if (document.forms[0].regPhoneNo.value != ""){
			if(!ValidatePh(document.forms[0].regPhoneNo.value)){
			alert("Please Enter Valid Phone Number (Expecting  Numeric Value)");
			document.forms[0].regPhoneNo.focus();
			return false;
			}
		}
		if (document.forms[0].regFaxNo.value != ""){
			if(!ValidatePh(document.forms[0].regFaxNo.value)){
			alert("Please Enter Valid Fax Number(Expecting  Numeric Value");
			document.forms[0].regFaxNo.focus();
			return false;
			}
		}
		if(!ValidateTextArea(document.forms[0].delhiOffAddr.value)){
			alert("Please Enter Valid Address(Expecting Alpha Numeric Value)");
			document.forms[0].delhiOffAddr.focus();
			return false;
	    }	
		if(!TxtAreaMaxLength(document.forms[0].delhiOffAddr.value,100)){
		   document.forms[0].delhiOffAddr.focus();
		   return false;
		}
		if (document.forms[0].delhiOffPhNo.value != ""){
			if(!ValidatePh(document.forms[0].delhiOffPhNo.value)){
			alert("Please Enter Valid Phone Number (Expecting  Numeric Value)");
			document.forms[0].delhiOffPhNo.focus();
			return false;
			}
		}
		if (document.forms[0].delhiOffFaxNo.value != ""){
			if(!ValidatePh(document.forms[0].delhiOffFaxNo.value)){
			alert("Please Enter Valid Fax Number (Expecting  Numeric Value)");
			document.forms[0].delhiOffFaxNo.focus();
			return false;
			}
		}
		 if(!ValidateAlphaNumeric(document.forms[0].nameOfHeadOfDelhiOff.value)){
		alert("Please enter Valid Delhi Head Ofiice Name");
	    document.forms[0].nameOfHeadOfDelhiOff.focus();
	    return false;
		}
		
		if (document.forms[0].delhiHeadOffMobileNo.value != ""){
			if(!ValidatePh(document.forms[0].delhiHeadOffMobileNo.value)){
			alert("Please Enter Valid Phone Number (Expecting  Numeric Value)");
			document.forms[0].delhiHeadOffMobileNo.focus();
			return false;
			}
		}
		if (document.forms[0].delhiHeadOffPhNo.value != ""){
			if(!ValidatePh(document.forms[0].delhiHeadOffPhNo.value)){
			alert("Please Enter Valid Phone Number (Expecting  Numeric Value)");
			document.forms[0].delhiHeadOffPhNo.focus();
			return false;
			}
		}
		
		if(!ValidateName(document.forms[0].pramotorname.value)) 
		  {
		    alert("Please Enter Valid Promotor Name (Expecting Alpha  Numeric Value)");
		    document.forms[0].pramotorname.focus();
		    return false;
		     
		  }
		  
		  if (document.forms[0].pramotorContactNo.value != ""){
			if(!ValidatePh(document.forms[0].pramotorContactNo.value)){
			alert("Please Enter Valid Phone Number (Expecting  Numeric Value)");
			document.forms[0].pramotorContactNo.focus();
			return false;
			}
		}
		
		if (document.forms[0].pramotorEmail.value != ""){ 
			if(!ValidateEMail(document.forms[0].pramotorEmail.value) ){
				alert("Please Enter Valid Promotor Email");
				document.forms[0].pramotorEmail.focus();
				return false;
			}
		} 
		 if(document.forms[0].networthAmount.value!='')
	  {
		  if(!ValidateFloatPoint(document.forms[0].networthAmount.value,8,2))
		  {
		    alert("Please Enter Valid Networth Amount (Expecting Numeric Value)");
			document.forms[0].networthAmount.focus();
	    	return false;
		  }
	  }
	    
		if(document.forms[0].networthAsOn.value!=""){
		   
			if(!convert_date(document.forms[0].networthAsOn))
			{
			return false;
			}
			document.forms[0].networthAsOn.value=FormatDate(document.forms[0].networthAsOn.value);
		} 
		
		if (document.forms[0].email.value == ""){ 
		  alert("Please Enter Arranger Email (Mandatory)");
		     document.forms[0].email.focus();
		     return false;
		}
		
	   if (document.forms[0].email.value != ""){ 
			if(!ValidateEMail(document.forms[0].email.value) ){
				document.forms[0].email.focus();
				return false;
			}
		} 
		
		if(!ValidateAlphaNumeric(document.forms[0].regwithsebi.value)){
		alert("Please Enter Valid Registration No / Membership No with SEBI (Expecting Alpha Numeric Value)");
	    document.forms[0].regwithsebi.focus();
	    return false;
		} 
		if(document.forms[0].sebivaliddate.value!=""){
			if(!convert_date(document.forms[0].sebivaliddate))
			{
			document.forms[0].sebivaliddate.focus();
			return false;
			}
			document.forms[0].sebivaliddate.value=FormatDate(document.forms[0].sebivaliddate.value);
		} 
		if(!ValidateAlphaNumeric(document.forms[0].regwithbse.value)){
		alert("Please Enter Valid Registration No / Membership No with BSE (Expecting Alpha Numeric Value)");
	    document.forms[0].regwithbse.focus();
	    return false;
		} 
		if(document.forms[0].bsevaliddate.value!=""){
			if(!convert_date(document.forms[0].bsevaliddate))
			{
			document.forms[0].bsevaliddate.focus();
			return false;
			}
			document.forms[0].bsevaliddate.value=FormatDate(document.forms[0].bsevaliddate.value);
		}
		if(!ValidateAlphaNumeric(document.forms[0].regwithnse.value)){
		alert("Please enter Valid Registration No / Membership No with NSE-WDM (Expecting Alpha Numeric Value)");
	    document.forms[0].regwithnse.focus();
	    return false;
		}  
		if(document.forms[0].nsevaliddate.value!=""){
			if(!convert_date(document.forms[0].nsevaliddate))
			{
			document.forms[0].nsevaliddate.focus();
			return false;
			}
			document.forms[0].nsevaliddate.value=FormatDate(document.forms[0].nsevaliddate.value);
		} 
		if(!ValidateAlphaNumeric(document.forms[0].regwithrbi.value)){
		alert("Please Enter Valid Registration No / Membership No with RBI (Expecting Alpha Numeric Value)");
	    document.forms[0].regwithrbi.focus();
	    return false;
		}  
		if(document.forms[0].rbivaliddate.value!=""){
			if(!convert_date(document.forms[0].rbivaliddate))
			{
			document.forms[0].rbivaliddate.focus();
			return false;
			}
			document.forms[0].rbivaliddate.value=FormatDate(document.forms[0].rbivaliddate.value);
		} 
		if(!ValidateAlphaNumeric(document.forms[0].remarks.value)){
		alert("Please enter Valid Remarks (Expecting Alpha Numeric Value)");
	    document.forms[0].remarks.focus();
	    return false;
		}  
		
	      var category="";
			for(i=0;i<document.forms[0].categoryId.length;i++)
			{
				if(document.forms[0].categoryId[i].selected==true)
				category+= "|"+document.forms[0].categoryId[i].value+"|";
			}
			category=category.substring(0,category.length-1);
			document.forms[0].categoryIds.value=category; 
			return true;
	}
	function clearData(){
        window.document.forms[0].reset();
  }
</SCRIPT>
	</head>
	<body onload="document.forms[0].arrangerName.focus();">
			<html:form action="/addArrangers.do?method=addArrangers" onsubmit="return validate();">
		<%=ScreenUtilities.screenHeader("arranger.title")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold" nowrap>
							<font color=red>*</font><bean:message key="arranger.name" bundle="common" />
						</td>
						<td width="18%" align=left>
							<html:text property="arrangerName" styleClass="TextField" tabindex="1" maxlength="50" size="15" />
						</td>
						<td width="15%" align=right class="tableTextBold">
						<font color=red>*</font>	<bean:message key="arranger.seccategory" bundle="common" />: 
						</td>
						<td width="18%" align=left>
						     <html:hidden property="categoryIds" />
							 <html:select property="categoryId" style="width: 120px;height:50 px" tabindex="2" styleClass="TextField" multiple="true" size="3" >
                      <html:options name="categorylist" property="categoryId" 
						labelProperty="categoryCd"  labelName="categoryId" collection="categorylist" />
                   </html:select>
						</td>
						<td align="right" width="15%" align=right>
							&nbsp;
						</td>
						<td width="18%" align=left>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.officeaddress" bundle="common" />:
						</td>
						<td width="18%" align=left>
							<html:textarea property="regOffAddr" styleClass="TextField" tabindex="3"  cols="15" style="height:50px" />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.phoneno" bundle="common" /> : 
						</td>
						<td width="18%" align=left>
							<html:text property="regPhoneNo" styleClass="TextField" tabindex="4" maxlength="14" size="15" />
						</td>
						<td align="right" width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.faxno" bundle="common" />:
						</td>
						<td width="18%" align=left>
							<html:text property="regFaxNo" styleClass="TextField" tabindex="5" maxlength="15" size="15"  />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.delhioffice" bundle="common" />:
						</td>
						<td width="18%" align=left>
							<html:textarea property="delhiOffAddr" styleClass="TextField" tabindex="6"  cols="15"   style="height:50px"/>
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.phoneno" bundle="common" />: 
						</td>
						<td width="18%" align=left>
							<html:text property="delhiOffPhNo" styleClass="TextField" tabindex="7" maxlength="14" size="15" />
						</td>
						<td align="right" width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.faxno" bundle="common" />:
						</td>
						<td width="18%" align=left>
							<html:text property="delhiOffFaxNo" styleClass="TextField" tabindex="8" maxlength="15" size="15"  />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.delhiofficename" bundle="common" />:
						</td>
						<td width="18%" align=left>
							<html:text property="nameOfHeadOfDelhiOff" styleClass="TextField" tabindex="9" maxlength="50" size="15" />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.mobileno" bundle="common"/> : 
						</td>
						<td width="18%" align=left>
							<html:text property="delhiHeadOffMobileNo" styleClass="TextField" tabindex="10" maxlength="14" size="15" />
						</td>
						<td align="right" width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.telephoneno" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="delhiHeadOffPhNo" styleClass="TextField" tabindex="11" maxlength="14" size="15"  />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.pramotorname" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="pramotorname" styleClass="TextField" tabindex="12" maxlength="50" size="15" />
						</td>
						<td width="15%" align=right nowrap class="tableTextBold">
							<bean:message key="arranger.contactno" bundle="common"/> : 
						</td>
						<td width="18%" align=left>
							<html:text property="pramotorContactNo" styleClass="TextField" tabindex="13" maxlength="14" size="15"  />
						</td>
						<td align="right" width="15%" align=right class="tableTextBold">
						<bean:message key="arranger.email" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="pramotorEmail" styleClass="TextField" tabindex="14" maxlength="50" size="15"  />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.networth" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="networthAmount" styleClass="TextField" tabindex="15" maxlength="9" size="15"  />
						</td>
						<td width="15%" align=right class="tableTextBold">
						<bean:message key="arranger.networthason" bundle="common"/> : 
						</td>
						<td width="15%" nowrap>
							 <html:text property="networthAsOn" styleClass="TextField" maxlength="11" styleId="amountason" tabindex="16" size="15" />
							 <html:link href="javascript:showCalendar('amountason');" tabindex="17">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
						<td align="right" width="15%" align=right class="tableTextBold">
							<font color=red>*</font><bean:message key="arranger.email" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="email" styleClass="TextField" tabindex="18"  maxlength="50" size="15" />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.membershipwithsbi" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="regwithsebi" styleClass="TextField" tabindex="19" maxlength="20" size="15"  />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.validdate" bundle="common"/>:
						</td>
						<td width="15%" nowrap>
							 <html:text property="sebivaliddate" maxlength="11" styleClass="TextField" styleId="svaliddate" tabindex="20" size="15" /> 
							 <html:link href="javascript:showCalendar('svaliddate');" tabindex="21">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
					</tr>
					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.membershipwithbse"  bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="regwithbse" styleClass="TextField" maxlength="20" tabindex="22" size="15" />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.validdate" bundle="common"/>:
						</td>
						<td width="15%" nowrap>
							 <html:text property="bsevaliddate" styleClass="TextField" maxlength="11" tabindex="23" styleId="bvaliddate" size="15"  /> 
							  <html:link href="javascript:showCalendar('bvaliddate');" tabindex="24">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
					</tr>

					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.membershipwithnse"  bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="regwithnse" styleClass="TextField" maxlength="20" tabindex="25" size="15"  />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.validdate" bundle="common"/>:
						</td>
						<td width="15%" nowrap>
							<html:text property="nsevaliddate" styleClass="TextField" maxlength="11" styleId="nvaliddate" tabindex="26" size="15" maxlength="12"  /> 
							 <html:link href="javascript:showCalendar('nvaliddate');" tabindex="27">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
					</tr>

					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.membershipwithrbi" bundle="common"/>:
						</td>
						<td width="18%" align=left>
							<html:text property="regwithrbi" styleClass="TextField" maxlength="20" tabindex="28" size="15"  />
						</td>
						<td width="15%" align=right class="tableTextBold">
							<bean:message key="arranger.validdate" bundle="common"/>:
						</td>
						<td width="15%" nowrap>
							 <html:text property="rbivaliddate" styleClass="TextField" maxlength="11" tabindex="29" styleId="rvaliddate" size="15" maxlength="30"  /> 
							 <html:link href="javascript:showCalendar('rvaliddate');" tabindex="30">
										<html:img src="images/calendar.gif"  alt="Datepicker" border="0"  align="center" />
							</html:link>
						</td>
					</tr>
					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.remarks" bundle="common"/>:
						</td>
						<td width="18%" align=left colspan=3>
							<html:text property="remarks" styleClass="TextField" maxlength="200" tabindex="31" size="52"  />
						</td>
					</tr>
					<tr>
						<td width="15%" align=right colspan=3 class="tableTextBold">
							<bean:message key="arranger.status" bundle="common"/>:
						</td>
						<td width="18%" align=left colspan=3>
							<html:select property="status" styleClass="TextField" tabindex="32">
								<html:option value="A">
									Active
								</html:option>
								<html:option value="I">
									InActive
								</html:option>
							</html:select>
						</td>
					</tr>
					<tr>
						<td colspan=6>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan=6 align=center>
							<html:submit value="Save" styleClass="butt" tabindex="33" />
							<html:reset  styleClass="butt" property="Clear" value="Clear" tabindex="34" onclick="clearData();"  />
							<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchArrangers.do?method=searchArrangers'" onblur="document.forms[0].arrangerName.focus();"  tabindex="35"/>
						</td>
					</tr>
					</table>
			<%=ScreenUtilities.screenFooter()%>
	</html:form>	
	</body>
</html>
