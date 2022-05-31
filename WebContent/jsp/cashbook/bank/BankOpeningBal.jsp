<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

String basePath = basePathBuf.toString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master - Bank Opening Balance Master</title>
		<meta http-equiv="pragma" content="no-cache" content="" />
		<meta http-equiv="cache-control" content="no-cache" content="" />
		<meta http-equiv="expires" content="0" content="" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" content="" />
		<meta http-equiv="description" content="This is my page" content="" />
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/calendar.js" type=""></script>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js" type=""></script>
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" type="">
			function checkAccount(){
				if(document.forms[0].opendate.value == ""){
					alert("Please Enter Date (Mandatory)");
					document.forms[0].opendate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].opendate)){
					return false;
				}
   		  		if(document.forms[0].amount.value == ""){
					alert("Please Enter Amount (Mandatory)");
					document.forms[0].amount.focus();
					return false;
				}

			}
			function popupWindow(mylink, windowname){
				document.forms[0].bankName.value="";
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
			function bankDetails(bankName,accountNo,trustType){	
		   		document.forms[0].bankName.value=bankName;
		   		document.forms[0].accountNo.value=accountNo;
			}
			var bool = false;
			
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
				function getBankDetails(){
					document.forms[0].accountNo.length=1 ;
					createXMLHttpRequest();	
					var url ="/bankOpenBal.do?method=getAccno&&bankName="+document.forms[0].bankName.value;
					xmlHttp.open("post", url, true);
					xmlHttp.onreadystatechange = getAjaxDetails;
					xmlHttp.send(null);
				}
				function getAjaxDetails(){				
					if(xmlHttp.readyState ==4){		
						if(xmlHttp.status == 200){ 
							var stype = xmlHttp.responseXML.getElementsByTagName('ServiceType');							
							if(stype.length==0){
								var accNo = document.getElementById("accNo");			
							}else{
								for(i=0;i<stype.length;i++){
									var accNo = getNodeValue(stype[i],'accNo');																	
									document.forms[0].accountNo.options[i+1]=new Option(accNo,accNo);						
								}
							}
						}
					}
					if(bool){														
							document.forms[0].bankName.value=trim('<%=request.getAttribute("bankName")%>');
							document.forms[0].accountNo.value=trim('<%=request.getAttribute("accountNo")%>');
							bool=false;
							}
				}
				
				
				function whileloading(){
					if(document.forms[0].ScreenType.value=="EDIT"){
						bool=true;
						getBankDetails();					
					}
				}
		</script>
	</head>
	<body onload="whileloading()">
	<html:form action="/bankOpenBal.do?method=add" onsubmit="javascript : return checkAccount()" method="post">
			<%=ScreenUtilities.screenHeader("bankopenbal.search")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
					<tr>				
						<td><html:errors bundle="error"/>
							<input type=hidden name="ScreenType" value="<bean:write name="ScreenType" scope="request"/>"/>
						 </td>
					</tr>
					
		           	<tr>
<logic:equal value="NEW" name="ScreenType" scope="request">		           	
						<td class="tableTextBold" width="100px" align="right">
							<bean:message key="bankInfo.bankAcno" bundle="common" />
						</td>
						<td align="left" colspan=4>
							<html:select property="accountNo" styleClass="TextField" style="width: 290px">
								<html:option value="">
									Select One
								</html:option>		
								<html:options collection="bankDetails" property="accountNo" labelName="bankName" name="BankMasterInfo" labelProperty="bankName"  />										
							</html:select>
						</td>
</logic:equal>		

<logic:equal value="EDIT" name="ScreenType" scope="request">		           	
						<td class="tableTextBold" width="100px" align="right">
							<bean:message key="bankInfo.bankName" bundle="common" />
						</td>
						<td align="left">
							<html:text property="bankName" styleClass="TextField" style="width:120 px ;height:50 px" readonly="true"/>
								
						</td>
						
						<td class="tableTextBold" align="right">
							<bean:message key="bankInfo.bankAcno" bundle="common" /> &nbsp;
						</td>
						
						<td align="left">
							<html:text property="accountNo" styleClass="TextField" style="width:120 px ;height:50 px" readonly="true"/>
						</td>
</logic:equal>					
						
						
		
					</tr>
					<tr>
						<td class="tableTextBold" width="100px" align="right">
							<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankopenbal.openeddate" bundle="common" />
						</td>
						<td align="left">
							<html:text property="opendate" styleClass="tableText"/>	
							&nbsp; <a href="javascript:show_calendar('forms[0].opendate')"> <img name="calendar" alt='calendar' border="0" src="<%=basePath%>/images/calendar.gif" src="" alt="" /></a>
						</td>
					</tr>
					<tr>
						<td class="tableTextBold" align="right">
							<font color="red">&nbsp;*&nbsp;</font><bean:message key="bankopenbal.amount" bundle="common" />
						</td>
						<td align="left">
							<html:text property="amount" styleClass="tableText" onblur="if(!ValidateFloatPoint(this.value,15,4))this.focus();"/>
							&nbsp;
							<html:select property='amountType' style='width:40px'>
								<html:option value='DR'>Dr.</html:option> 
								<html:option value='CR'>Cr.</html:option>
							</html:select>
						</td>
						
					</tr>
					<tr>
						<td class="tableTextBold" align="right">
							<bean:message key="bankopenbal.details" bundle="common" /> 
						</td>
						<td align="left">
							<html:text property="details" styleClass="tableText" tabindex="4" size="18" />
						</td>
					</tr>
					<tr>
						<td height="10px">
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="submit" class="butt" value="Submit" />
							<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()"  />
							<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)"  />
						</td>
					</tr>
					<tr>
						<td height="10px">
						</td>
					</tr>
			</table><%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>