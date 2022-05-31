
<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
			    if(document.forms[0].refNo.value==""){
			    alert("please Enter  Ref. No ");
			    		 document.forms[0].refNo.focus();
			   			  return false;
			    }
		   		if(!ValidateAlphaNumeric(document.forms[0].refNo.value)){
		    		 alert("Enter Valid Ref. No ");
		    		 document.forms[0].refNo.focus();
		   			  return false;
		    		 }
		    	if(document.forms[0].proposaldate.value==''){
						alert("please Enter Proposal Date ");
						document.forms[0].proposaldate.select();
						return(false);
					   }
					if(!convert_date(document.forms[0].proposaldate)){
						document.forms[0].proposaldate.select();
						return(false);
					   }
					var currentDate = new Date()
					var month = currentDate.getMonth() + 1
					var day = currentDate.getDate()
					var year = currentDate.getFullYear()
					var todaysdate = day + "/" + month + "/" + year ;
					var cmp =compareDates(document.forms[0].proposaldate.value,todaysdate);
					if (cmp == "larger"){
						alert("Proposal Date  Should Be Less Than  Or Equal To The Current Date");
						document.forms[0].proposaldate.focus();
						return false;
						}	
					
		        if(document.forms[0].trustType.value==""){
		    		 alert("please Select trust Type ");
		    		 document.forms[0].trustType.focus();
		   			  return false;
		    		 }
		    	if(document.forms[0].securityCat.value==""){
		    		 alert("please Select Security Category ");
		    		 document.forms[0].securityCat.focus();
		   			  return false;
		    		 }
		    	if(document.forms[0].marketType.value=="")
		    	{
		    		alert("Please Select MarketType [Mandatory]");
		    		document.forms[0].marketType.focus();
		    		return false;
		    	}
		    	if(document.forms[0].amountInv.value==""){
		    		 alert("please Enter Amount Invested ");
		    		 document.forms[0].amountInv.focus();
		   			  return false;
		    		 }
		    	if (!ValidateFloatPoint(document.forms[0].amountInv.value,15,4)){
					document.forms[0].amountInv.focus();
					return false;
				    }
				
				
				
		     
		    return true; 
		 }
		 function clearData(){
        	window.document.forms[0].reset();
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].refNo.focus();">
		<html:form action="/editProposal.do?method=editProposal" method="POST" onsubmit="return validate();" enctype="multipart/form-data">
			<%=ScreenUtilities.screenHeader("proposal.newtitle")%>
			<table width="550" border=0 align="center">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.refno" bundle="common" />
					</td>
					<td align=left width="20%">
						<html:text size="12" property="refNo" name="proposal" readonly="true" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="25" />
					</td>	
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color="red">*</font>
						<bean:message key="proposal.proposaldate" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap>
						<html:text property="proposaldate" name="proposal" styleClass="TextField" styleId="proposaldate" style='width:150 px' tabindex="3" maxlength="12" />
						<html:link href="javascript:showCalendar('proposaldate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
				</tr>
				<tr>			
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="trustType" name="proposal" styleClass="TextField" style="width:150 px" tabindex="5">
							<html:option value="">[Select one]</html:option>
							<html:options collection="trustRecords" property="trustCd" labelProperty="trustType" />
						</html:select>
					</td>				
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="securityCat" name="proposal" styleClass="TextField" style="width:120 px" tabindex="6">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.marketType" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="marketType" name="proposal" styleClass="TextField" style="width:150px" tabindex="5">
							<html:options collection="marketTypeList" property="code" labelProperty="name" />
						</html:select>

					</td>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.amtinvest" bundle="common" />
						:
					</td>
					<td align=left width="25%" nowrap="nowrap">
						<html:text property="amountInv" name="proposal" style="width:120px" styleClass="TextField" tabindex="4" maxlength="18" />
						<bean:message key="proposal.fundformate" bundle="common" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap>
						<bean:message key="proposal.subject" bundle="common" />
					</td>
					<td align=left colspan=3> 
						<html:textarea property="subject" name="proposal" cols="50" rows="2" onkeypress="return TxtAreaMaxLength(this.value,99)"></html:textarea>
					</td>
				</tr>
				<tr>
					<td width="25%" class="tableTextBold" align=right nowrap>
						<bean:message key="proposal.remarks" bundle="common" />
						:
					</td>
					<td align=left colspan=3>
						<html:textarea property="remarks" name="proposal" cols="50" rows="3" onkeypress="return TxtAreaMaxLength(this.value,1999)"></html:textarea>
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" value="Save" tabindex="9" />
						&nbsp;
						<html:button styleClass="butt" property="Clear" value="Clear" tabindex="10" onclick="clearData();" />
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchProposal.do?method=searchInvestProposal'" onblur="document.forms[0].refNo.focus();" tabindex="11" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
