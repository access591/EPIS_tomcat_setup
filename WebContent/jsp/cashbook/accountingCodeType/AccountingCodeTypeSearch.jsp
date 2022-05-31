<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.bean.cashbook.AccountingCodeTypeBean,com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >
<html>
	<head>
		<title>AAI - Cashbook - Master - Account Code Type Search</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Accounting Code Type" />

		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script language="javascript">
			function selectCheckboxes(optiontype) { 		
				var recordcd = getCheckedValue(document.forms[0].deleteRecord);				
				if(optiontype!='Add'){
					if(recordcd==''){
						alert("Select atleast one Record");
						return;
					}
				}
				if(optiontype=='Add'){						
					 window.location.href='/accCodeTypeSearchResult.do?method=fwdtoNew';
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/accCodeTypeSearchResult.do?method=delete&deleteRecord="+recordcd;
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/accCodeTypeSearchResult.do?method=fwdtoEdit&&code="+recordcd;
					document.forms[0].submit();					 
				}
			}		   
			</script>
	</head>

	<body onload="document.forms[0].accountCodeType.focus()">
		<html:form action="/accCodeTypeSearchResult.do?method=search" method="post" >
			<%=ScreenUtilities.screenHeader("accCodeType.title")%>
			<table width="70%" border="0" align="center">

				<tr>
					<td colspan="4" align="center" style="height:10px">
						<html:errors bundle="error" property="accountCodeType" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="accCodeType.accCodeType" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="accountCodeType" maxlength="50" styleClass="tableText" tabindex="1"/>
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="accCodeType.description" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="description" maxlength="150" styleClass="tableText" tabindex="2"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center" style="height:10px">
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<html:submit styleClass="butt">
							<bean:message key="button.search" bundle="common" />
						</html:submit>
						<html:button styleClass="butt" property="Clear" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<%=ScreenUtilities.getAcessOptions(session, 1)%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
				</tr>
				<html:radio property="deleteRecord" value="" style="display:none;" />				
				<tr>
					<td align="center">
						<display:table cellpadding="0" cellspacing="0" pagesize="5" class="GridBorder" style="width:100%;height:50px" keepStatus="true" sort="list" name="records" name="requestScope.records" id='searchData' requestURI="/accCodeTypeSearchResult.do?method=search">
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((AccountingCodeTypeBean)pageContext.getAttribute("searchData")).getCode()%>' />
							</display:column>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="accountCodeType" sortable="true" title="Account Code Type" decorator="com.epis.utilities.StringFormatDecorator" maxLength="30" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="description" sortable="true" title="Description" decorator="com.epis.utilities.StringFormatDecorator" maxLength="30"/>							
						</display:table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>