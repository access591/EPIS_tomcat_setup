
<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Initial Proposal</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="Initial Proposal" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript" src="js/GeneralFunctions.js"></script>

		<script type="text/javascript">
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
  		
		</script>
	</head>
	<body onload="document.forms[0].refNo.focus();">
		<html:form action="/addProposal.do?method=addProposal" method="POST" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("proposal.newtitle")%>
			<table border=0 align="center" cellspacing="4">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.refno" bundle="common" />
					</td>
					<td align=left>
						<html:text property="refNo"  styleClass="TextField" style="width:150px" tabindex="1" maxlength="25" />

					</td>
					<td class="tableTextBold" align=right nowrap>
						<font color="red">*</font>
						<bean:message key="proposal.proposaldate" bundle="common" />
						:
					</td>
					<td align=left  nowrap>
						<html:text property="proposaldate" styleClass="TextField" styleId="proposaldate" style='width:150 px' tabindex="3" maxlength="12" />
						<html:link href="javascript:showCalendar('proposaldate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
					</td>
					
				</tr>
				
				<tr>
					<td  class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.trusttype" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="trustType" style="width:150px" tabindex="5" styleClass="TextField">
							<!-- <html:option value="">[Select one]</html:option>-->
							<html:options collection="trustRecords" property="trustCd" labelProperty="trustType" />
						</html:select>
					</td>				

					<td  class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="securityCat" styleClass="TextField" style="width:100px" tabindex="6">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>
					</tr>
				<tr>
					<td  class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.marketType" bundle="common" />
						:
					</td>
					<td align=left >
						<html:select property="marketType" styleClass="TextField" style="width:150px" tabindex="7">
							<html:options collection="marketTypeList" property="code" labelProperty="name" />
						</html:select>

					</td>

				
					<td  class="tableTextBold" align=right nowrap>
						<font color=red>*</font>
						<bean:message key="proposal.amtinvest" bundle="common" />
						:
					</td>
					<td align=left  nowrap="nowrap">
						<html:text property="amountInv" styleClass="TextField" style="width:120px" tabindex="8" maxlength="18" />
						<bean:message key="proposal.fundformate" bundle="common" />
					</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap>
						<bean:message key="proposal.subject" bundle="common" />
					</td>
					<td align=left colspan="3">
						<html:textarea property="subject" cols="50" rows="2" tabindex="9" onkeypress="return TxtAreaMaxLength(this.value,99)"></html:textarea>
					</td>
				</tr>
				<tr>
					<td  class="tableTextBold" align=right nowrap>
						<bean:message key="proposal.remarks" bundle="common" />
						:
					</td>
					<td align=left  colspan=3>
						<html:textarea property="remarks" cols="50" rows="3" tabindex="9" onkeypress="return TxtAreaMaxLength(this.value,1999)"></html:textarea>
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" value="Save" tabindex="11" />
						&nbsp;
						<html:button styleClass="butt" property="Clear" value="Clear" tabindex="12" onclick="clearData();" />
						&nbsp;
						<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchProposal.do?method=searchInvestProposal'" onblur="document.forms[0].refNo.focus();" tabindex="13" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
