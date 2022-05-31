<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.bean.cashbook.BankMasterInfo,com.epis.utilities.ScreenUtilities"%>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Bank Info Search</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="js/CommonFunctions.js"></script>
		<script language="javascript" type="text/javascript">
		   
		   function clearData(){
		   		  document.forms[0].bankName.value="";
		   		  document.forms[0].branchName.value="";
		   		  document.forms[0].bankCode.value="";
		   		  document.forms[0].accountNo.value="";
		   }
		   function selectCheckboxes(optiontype) { 		
				var recordcd = getCheckedValue(document.forms[0].deleteRecord);				
				if(optiontype!='Add'){
					if(recordcd==''){
						alert("Select atleast one Record");
						return;
					}
				}
				if(optiontype=='Add'){						
					 window.location.href="/bankInfoSearchResult.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/bankInfoSearchResult.do?method=delete&deleteRecord="+recordcd;
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/bankInfoSearchResult.do?method=fwdtoEdit&&code="+recordcd;
					document.forms[0].submit();					 
				}
			}
		  </script>
	</head>

	<body>
		<html:form action="/bankInfoSearchResult.do?method=search" method="post">
			<%=ScreenUtilities.screenHeader("bankInfo.title")%>
			<table width="70%" border="0" align="center">
				<tr>
					<td align="center" nowrap colspan="4">
						<html:errors bundle="error" property="bankName" />
					</td>

				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankName" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="bankName" maxlength="50" styleClass="tableText" />
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.branchName" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="branchName" maxlength="150" styleClass="tableText" />
					</td>
				</tr>

				<tr>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankCode" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="bankCode" maxlength="50" styleClass="tableText" />
					</td>
					<td width="20%" class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankAcno" bundle="common" />
						:
					</td>
					<td width="20%">
						<html:text property="accountNo" maxlength="150" styleClass="tableText" />
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
			<%=ScreenUtilities.getAcessOptions(session,1)%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
				</tr>
				<html:radio property="deleteRecord" value="" style="display:none;" />
				<tr>
					<td align="center" width="100%">
						<display:table cellpadding="0" cellspacing="0" pagesize="5" class="GridBorder" style="width:710px;height:50px" keepStatus="true" export="true" sort="list" name="BankList" name="requestScope.BankList" id='searchData'
							requestURI="/bankInfoSearchResult.do?method=search">							
							<display:column style="width:10px;" media="html" headerClass="GridHBg" class="GridLCells">
								<html:radio property="deleteRecord" value='<%=((BankMasterInfo)pageContext.getAttribute("searchData")).getAccountNo()%>' />
							</display:column>
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="bankName" sortable="true" title="Bank Name" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="accountNo" sortable="true" title="Account No" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="bankCode" sortable="true" title="Bank Code" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="IFSCCode" sortable="true" title="IFSC Code" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="accountCode" sortable="true" title="Account Code" />
							<display:column headerClass="GridLTHCells" class="GridLTCells" property="particular" sortable="true" title="Particular" />
						</display:table>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>









