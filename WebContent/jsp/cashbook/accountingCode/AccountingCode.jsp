<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.epis.bean.investment.TrustTypeBean"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.epis.bean.cashbook.AccountingCodeInfo"%>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Accounting Code Master</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript">
			function checkAccount(){
				if(document.forms[0].accountHead.value == ""){
					alert("Please Enter Account Code (Mandatory)");
					document.forms[0].accountHead.focus();
					return false;
				}
				if(!ValidateAccountingCode(document.forms[0].accountHead.value))		{
					alert("Please Enter Valid Account Code (Spl. Characters not allowed)");
					document.forms[0].accountHead.focus();
					return false;
				}
				if(document.forms[0].particular.value == ""){
					alert("Please Enter Accounting Name(Mandatory)");
					document.forms[0].particular.focus();
					return false;
				}				
				if(document.forms[0].date.value != ""){
					if(!convert_date(document.forms[0].date)){
						return false;
					}
					convert_date(document.forms[0].date);
				}
<%
List trustTypes = (ArrayList)request.getAttribute("trustTypes");
int length = trustTypes.size();
StringBuffer strbuf = new StringBuffer();
for(int i=0;i<length;i++){
	TrustTypeBean bean = (TrustTypeBean)trustTypes.get(i);
	out.println("if(document.forms[0].Amount"+bean.getTrustCd()+".value==''){ document.forms[0].Amount"+bean.getTrustCd()+".value = '0';} " );
	strbuf.append(" document.forms[0].Amount"+bean.getTrustCd()+".value == '0' &&");
}
	String str = strbuf.toString();
	str = str.substring(0,str.length()-3);
%>	
				if(document.forms[0].date.value != "" ) {
					if(<%=str%>){
						//alert("Please Enter Opening Balance(Mandatory)");
						//return false;
					}
				}
				if(document.forms[0].date.value == "" ){
					if(<%=str.replaceAll("&&","||").replaceAll("==","!=")%>){
						alert("Please Enter Opened Date(Mandatory)");
						document.forms[0].date.focus();
						return false;
					}
				}				
			}
			function check(){
				if(document.forms[0].screenType.value=='NEW'){
					document.forms[0].accountHead.value='';
					document.forms[0].particular.value='';
					document.forms[0].date.value='';
					document.forms[0].type.value='';
					document.forms[0].accountHead.focus();
				}
			}
		</script>
	</head>
	<body onload="check()">
		<html:form action="/accCode.do?method=add" onsubmit="javascript : return checkAccount()"  method="post">
			<%=ScreenUtilities.screenHeader("accCode.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td align="center"  colspan="4">
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td align="right" class="tableTextBold">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="accCode.accCode" bundle="common" />
					</td>
					<td align="left">
						<logic:empty scope="request" name="openBals">
							<html:text property="accountHead" styleClass="tableText" maxlength="10"/>
							<html:hidden property="screenType" value="NEW" />
						</logic:empty>
						<logic:notEmpty scope="request" name="openBals">
							<html:text property="accountHead" styleClass="tableText" readonly="true" />
							<html:hidden property="screenType" value="EDIT" />
						</logic:notEmpty>
					</td>

					<td class="tableTextBold" align="right">
						<font color="red">&nbsp;*&nbsp;</font>
						<bean:message key="accCode.name" bundle="common" />
					</td>
					<td align="left">
						<html:text property="particular" styleClass="tableText" maxlength="100"/>
					</td>

				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.openedDate" bundle="common" />
					</td>
					<td align="left">
						<html:text property="date" styleClass="tableText" maxlength="11" />
						&nbsp; <a href="javascript:show_calendar('forms[0].date')"> <img name="calendar" alt='calendar' border="0" src="/images/calendar.gif" src="" alt="" /></a>
					</td>
					<td class="tableTextBold" align="right" nowrap="nowrap">
						<bean:message key="accCodeType.accCodeType" bundle="common" />
					</td>
					<td align="left">
						<html:select property="type" styleClass="TextField" style="width: 90px">
							<html:option value="">
									Select One
								</html:option>
							
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.trustType" bundle="common" />
					</td>
					<td class="tableTextBold" align="center">
						<bean:message key="accCode.openingBalance" bundle="common" />
					</td>

				</tr>
				<logic:empty scope="request" name="openBals">
					<logic:iterate id="trust" scope="request" name="trustTypes">
						<tr>
							<td class="tableTextBold" align="right">
								<bean:write name="trust" property="trustType" />
							</td>
							<td align="left" nowrap="nowrap" colspan="2">
								<input type="text" name='Amount<bean:write name="trust" property="trustCd" />' value="0" maxlength="14"  size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();" />
								&nbsp;
								<select name='AmountType<bean:write name="trust" property="trustCd" />' style='width:40px'>
									<option value='DR'>
										Dr.
									</option>
									<option value='CR'>
										Cr.
									</option>
								</select>
							</td>
						</tr>
					</logic:iterate>
				</logic:empty>
				<logic:notEmpty scope="request" name="openBals">
					<%
Map openbals = (HashMap)request.getAttribute("openBals");
AccountingCodeInfo accInfo = null;
for(int i=0;i<length;i++){
	TrustTypeBean bean = (TrustTypeBean)trustTypes.get(i);
	if(openbals.containsKey(bean.getTrustType())){
		accInfo =(AccountingCodeInfo)openbals.get(bean.getTrustType());
%>
					<tr>
						<td class="tableTextBold" align="right">
							<%=bean.getTrustType()%>
						</td>
						<td align="left" nowrap="nowrap" colspan="2">
							<input type="text" name='Amount<%=bean.getTrustCd()%>' value='<%=accInfo.getAmount()%>' maxlength="14"  size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();" />
							&nbsp;
							<select name='AmountType<%=bean.getTrustCd()%>' style='width:40px'>
								<option value='DR' <%="DR".equals(accInfo.getAmountType())?"selected":""%>>
									Dr.
								</option>
								<option value='CR' <%="CR".equals(accInfo.getAmountType())?"selected":""%>>
									Cr.
								</option>
							</select>
						</td>
					</tr>
					<%
	}else{
%>
					<tr>
						<td class="tableTextBold" align="right">
							<%=bean.getTrustType()%>
						</td>
						<td align="left" nowrap="nowrap" colspan="2">
							<input type="text" name='Amount<%=bean.getTrustCd()%>' value='0' maxlength="14" tabindex="3" size="18" onblur="if(!ValidateFloatPoint(this.value,13,2))this.focus();" />
							&nbsp;
							<select name='AmountType<%=bean.getTrustCd()%>' style='width:40px'>
								<option value='DR'>
									Dr.
								</option>
								<option value='CR'>
									Cr.
								</option>
							</select>
						</td>
					</tr>

					<%
	}
}
%>
				</logic:notEmpty>
				<tr>
					<td height="10px">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<html:submit styleClass="butt" value="Save" />
						<html:button styleClass="butt" value="Clear" property="Reset" onclick="javascript:document.forms[0].reset()" />
					</td>
				</tr>
				<tr>
					<td height="10px">
					</td>
				</tr>


			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>