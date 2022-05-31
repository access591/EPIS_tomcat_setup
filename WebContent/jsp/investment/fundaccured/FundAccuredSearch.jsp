<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ page import="com.epis.bean.investment.FundAccuredBean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Investment - Master - Fund Accured Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Fund Accured  Search" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/displaytagstyle.css" type="text/css" />
		<script language="javascript" >
			function selectCheckboxes(optiontype) {
				
				var recordcd = getCheckedValue(document.forms[0].deleteRecord);	
				
				if(optiontype!='Add'){
					if(recordcd==''){
						alert("Select atleast one Record");
						return;
					}
				}
				if(optiontype=='Add'){						
					 window.location.href="/fundAccured.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
					
						document.forms[0].action="/fundAccured.do?method=delete";
						document.forms[0].submit();
					}else{
						return;
					}
				}
			}
		  </script>
	</head>
	<body class="BodyBackground1">
		<html:form action="/fundAccured.do?method=search" method="post">

			<%=ScreenUtilities.screenHeader("fa.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan="4" align="center">
						&nbsp;
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr style="height:1cm">
					<td class="tableTextBold" align="right">
						<bean:message key="fa.finyear" bundle="common" />
					</td>
					<td align="left">
						<html:select property="finYear" styleClass="TextField"  >
							<html:option value="">Select One</html:option>
							<html:options name="Bean" property="code" labelProperty="code" labelName="code" collection="finYear" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<html:submit value="Search" styleClass="butt" />
						<html:button value="Clear" property="clear" onclick="javascript:document.forms[0].reset()" styleClass="butt" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<%=ScreenUtilities.getAcessOptions(session,1)%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
				</tr>
				<html:radio property="deleteRecord" value="" style="display:none;" />
				<tr>
					<td align="center" width="100%">
						<display:table cellpadding="0" cellspacing="0" class="GridBorder" keepStatus="true" style="width:100%" sort="list" name="list" name="requestScope.list" pagesize="5" id='searchData' requestURI="/fundAccured.do?method=search">
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((FundAccuredBean)pageContext.getAttribute("searchData")).getFinYear()+"#"+((FundAccuredBean)pageContext.getAttribute("searchData")).getTrustType()%>' />
								
							</display:column>
							<display:column property="finYear" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Financial Year" />							
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="amount" sortable="true" title="Fund Accured" decorator="com.epis.utilities.StringFormatDecorator"/>							
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="trustType" sortable="true" title="Trust Type" decorator="com.epis.utilities.StringFormatDecorator"/>							
						</display:table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>