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
		var detailArray=new Array();
		function saveToDetails()
		{
			if(document.forms[0].securityName.value=="")
			{
				alert("Please Select Security Name [Mandatory]");
				document.forms[0].securityName.focus();
				return false;
			}
			if(document.forms[0].credit.value=="")
			{
				alert("Amount is Mandatory");
				document.forms[0].credit.focus();
				return false;
			}
			if(!ValidateFloatPoint(document.forms[0].credit.value,15,4)){
					document.forms[0].credit.focus();
					document.forms[0].credit.select();
					return false;
				}
			if(document.forms[0].amountdate.value=="") {
					alert("Amount Date is Mandatory");
					document.forms[0].amountdate.focus();
					return false;
				}
				if(!convert_date(document.forms[0].amountdate)){
					document.forms[0].amountdate.select();
					return(false);
				}
				if(document.forms[0].amounttype.value=="")
			{
				alert("Please Select Amount Type [Mandatory]");
				document.forms[0].amounttype.focus();
				return false;
			}
			
			detailArray[detailArray.length]=[document.forms[0].securityName.value,document.forms[0].credit.value,document.forms[0].amountdate.value,document.forms[0].amounttype.value,document.forms[0].amounttype[document.forms[0].amounttype.selectedIndex].text];
		showValues();
			
		}
		
		
		function clearDetails()
		{
			document.forms[0].securityName.value="";
			document.forms[0].credit.value="";
			document.forms[0].amountdate.value="";
			document.forms[0].amounttype.value="";
			document.forms[0].securityName.focus();
			
			
		}
		function del(index)
	{
		var temp=new Array();
		for(var i=0;i<detailArray.length;i++)
		{
			if(i!=index)
				temp[temp.length]=detailArray[i];
		}
		detailArray=temp;
		showValues();
		return false;
	}
	function showValues()
	{
		var tableStr='<table width="100%" border=1 bordercolor="white" cellspacing=0 cellpadding=0>';
		for(var i=0;i<detailArray.length;i++){
		tableStr += '<tr>';
		tableStr += '<td align="center" width= "30%" nowrap>'+detailArray[i][0]+'</td>';
		tableStr += '<td align="center" width= "20%">'+detailArray[i][1]+'</td>';
		tableStr += '<td align="center" width= "20%">'+detailArray[i][2]+'</td>';
		tableStr += '<td align="center" width= "20%">'+detailArray[i][4]+'</td>';
		tableStr+='<TD class=tb align=center ><a href=# onclick=del1('+i+')>';
			tableStr += '<a href=# onclick=del('+i+')><img src="/images/cancelIcon.gif" alt=delete border=0 width=20 height=20></a></td>';
			tableStr+='</TR>';
			
		}
		tableStr+='</TABLE>';
		document.all['detailsTable'].innerHTML = tableStr;
		clearDetails();
	}
		function validate()
		{
			
			setValues();
		}
		function setValues()
	{
	
		var temp;
		for(var i=0;i<detailArray.length;i++)
		{
			temp = detailArray[i][0]+'|'+detailArray[i][1]+'|'+detailArray[i][2]+'|'+detailArray[i][3];
			
			
			document.forms[0].achieveDetails.options[document.forms[0].achieveDetails.options.length]=new Option('x',temp);
			document.forms[0].achieveDetails.options[document.forms[0].achieveDetails.options.length-1].selected=true;
		}
	}
	
		</script>
	</head>
	<body onload="document.forms[0].securityName.focus();">
		<html:form action="/savepartyamount.do?method=savePartyAmount" onsubmit="validate();" >
		<%=ScreenUtilities.screenHeader("partyamount.title")%>
		<table width="550" border=0 align="center">
				<tr>
					<td colspan=5 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
									<TR style="display:none">
										<TD colspan=5><html:select property="achieveDetails" multiple="true"></html:select></TD>
										
									</TR>
									
				<tr>
				<td width="30%" class="label" align="center" nowrap>
				<font color=red>*</font><bean:message key="partyamount.securityname" bundle="common" />
				</td>
				<td width="20%" class="label" align="center" nowrap>
				<font color=red>*</font><bean:message key="partyamount.credit" bundle="common"/>
				</td>
				<td width="20%" class="label" align="center" nowrap>
				<font color=red>*</font><bean:message key="partyamount.amountdate" bundle="common"/>
				</td>
				<td width="20%" class="label" align="center" nowrap>
				<font color=red>*</font><bean:message key="partyamount.amountType" bundle="common"/>
				</td>
				<td width="20%" align="center" class="tableTextBold" >&nbsp;</td>
				</tr>
				<tr>
				<td colspan=5>
				<div id=detailsTable></div>
				</td>
				</tr>
				<tr>
				<td align="center" width="30%" nowrap>
				<html:select property="securityName"   style='width:250px'  styleClass="TextField" tabindex="1">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="securityList" property="emppartycode" labelProperty="emppartycode" />
						</html:select>
				</td>
				<td align="center" width="20%" nowrap>
				<html:text property="credit" style='width:100px'  styleClass="TextField" tabindex="2" maxlength="19" />
				
				</td>
				<td align="center" width="20%" nowrap>
				<html:text styleId="amountdate" property="amountdate" style="width: 95px;" tabindex="3" styleClass="TextField" />
						<html:link href="javascript:showCalendar('amountdate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>
				</td>
				<td align="center" width="20%" nowrap>
				<html:select property="amounttype"   style='width:100px'  styleClass="TextField" tabindex="5">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="amounttypelist" property="code" labelProperty="name" />
						</html:select>
				</td>
				<td class="label" align="right" nowrap="nowrap">
									<img border="0" src="/images/saveIcon.gif" alt="save" onclick='saveToDetails()' style='cursor:hand' />
									<img border="0" style='cursor:hand' src="/images/cancelIcon.gif" alt="clear" onclick='clearDetails()' />
								</td>
				</tr>
				<tr>
					<td class="tableTextBold" align="right" nowrap="nowrap" colspan="5">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<html:submit styleClass="butt">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						<html:button styleClass="butt" property="Clear" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>

			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
				
				