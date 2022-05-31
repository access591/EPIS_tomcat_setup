
<%@ page language="java" import="com.epis.bean.investment.InvestmentProposalBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags-display" prefix="display"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		function validate(){
			    if(document.forms[0].refNo.value!=""){
			    if(!ValidateAlphaNumeric(document.forms[0].refNo.value)){
		    		 alert("Enter Valid Ref. No ");
		    		 document.forms[0].refNo.focus();
		   			  return false;
		    		 }
		    	}
		        if(document.forms[0].approveDate.value!=''){
					if(!convert_date(document.forms[0].approveDate)){
						document.forms[0].approveDate.select();
						return(false);
					   }
					}
					
		     return true; 
		 }
		 function clearData(){
        	document.forms[0].refNo.value="";
         	document.forms[0].trustType.value="";
         	document.forms[0].securityCat.value="";
         	//document.forms[0].amountInv.value="";
         	document.forms[0].approve.value="";
         	document.forms[0].approveDate.value="";
         	//document.forms[0].remarks.value="";
         	document.forms[0].refNo.focus();
  		}
		
  function selectCheckboxes(optiontype) { 
		
		var optionval = getCheckedValue(document.forms[0].selectall);
		var val = optionval.split("|");
		var recordcd = val[0];
	   if(optiontype!='Add'){
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		if(optiontype=='Add'){		 
			
			 window.location.href='searchProposal.do?method=showProposalAdd&mode=rbiauction';
		 }
		if(optiontype=='Delete'){		 
			var state=window.confirm("Are you sure you want to delete ?");
			if(state){
				if(val[1]=='A'){
		 			alert("This Proposal cannot be deleted.As the Approval has been done.");
		 			return false;
			 	}else{
				    var url = "searchProposal.do?method=deleteProposal&deleteall="+recordcd+"&mode=rbiauction";
					document.forms[0].action=url;
					document.forms[0].submit();
				}
			}else{
				return;
		}
		}else if(optiontype=='Edit'){	 	
		 		if(val[1]=='A'){
		 			alert("This Proposal cannot be edited As the Approval has been done.");
		 			return false;
		 		}else{
				    var url = "searchProposal.do?method=showEditProposal&refNo="+recordcd+"&mode=rbiauction";
					document.forms[0].action=url;
					document.forms[0].submit();
				}			 
		 }
		 else if(optiontype=='Approval-1')
		 {	
		 	if(val[2]=='A'){
		 			alert("The Level-2 Approval has already been Done.");
		 			return false;
		 	}else if(val[1]=='A'){
		 		var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-2')
		 {	
		 	if(val[1]!='A'){
		 			alert("As Level-1 approval is not Completed, Level-2 Approval cannot be made");
		 			return false;
		 	}if(val[3]=='A'){
		 			alert("The Level-3 Approval has already been Done.");
		 			return false;
		 	}else if(val[2]=='A'){
		 		var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-3')
		 {		    
		 	if(val[2]!='A'){
		 			alert("As Level-2 approval is not Completed, Level-3 Approval cannot be made");
		 			return false;
		 	}if(val[4]=='A'){
		 			alert("The Level-4 Approval has already been Done.");
		 			return false;
		 	}else if(val[3]=='A'){
		 		var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-4')
		 {		    
		 	if(val[3]!='A'){
		 			alert("As Level-3 approval is not Completed, Level-4 Approval cannot be made");
		 			return false;
		 	}else if(val[4]=='A'){
		 		var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-5')
		 {		    
		 	if(val[3]!='A'){
		 			alert("As Level-3 approval is not Completed, Level-5 Approval cannot be made");
		 			return false;
		 	}
		 	else if(val[5]=='A'){
		 			var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=5";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=5";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-6')
		 {		    
		 	if(val[3]!='A'){
		 			alert("As Level-3 approval is not Completed, Level-6 Approval cannot be made");
		 			return false;
		 	}else if(val[6]=='A'){
		 			var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&&edit=Y&mode=rbiauction&updatedFlag=6";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchProposal.do?method=approvalProposal&refNo="+recordcd+"&mode=rbiauction&updatedFlag=6";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
	  }
	 
	  
	  function openReport(url){
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"InvestmentProposal","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
	</script>
	</head>
	<body onload="document.forms[0].refNo.focus();">
		<html:form action="/searchProposal.do?method=searchInvestProposal&mode=rbiauction" onsubmit="return validate();">
			<%=ScreenUtilities.screenHeader("proposal.newtitle")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="proposal.refno" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:text size="12" property="refNo" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="25" />

					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="proposal.trusttype" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="trustType" styleClass="TextField" style="width:120 px" tabindex="2">
							<html:option value="">[Select one]</html:option>
							<html:options collection="trustRecords" property="trustCd" labelProperty="trustType" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>
					<td align=left width="20%">
						<html:select property="securityCat" styleClass="TextField" style="width:120 px" tabindex="4">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan=4 align=center>
						<html:submit styleClass="butt" value="Search" tabindex="7" />
						&nbsp;
						<html:button styleClass="butt" property="button" value="Clear" tabindex="8" onclick="clearData();" />
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="30%" height="29" align="left" valign="top">
						<%=ScreenUtilities.getAcessOptions(session, 9)%>
					</td>
					<td width="50%" align="left" valign="top">
						&nbsp;
					</td>
				</tr>
				<logic:present name="proposalList">
					<tr>
						<td align="left" valign="top">
						
							<display:table cellpadding="0" cellspacing="0" class="GridBorder" keepStatus="false" style="width:710px;height:50px" export="true" sort="list" id="proposalTable" name="requestScope.proposalList"
								requestURI="/searchProposal.do?method=searchInvestProposal&mode=rbiauction" pagesize="5">
								<logic:present name="proposalTable">
								<logic:equal value="Y" property="hasQrs" name="proposalTable">
							    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
							    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
							     </display:column>
							     </logic:equal>
							     <logic:notEqual value="Y" property="hasQrs" name="proposalTable">
							    <display:column media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
									<input type="radio" style="width:15px;height:px" name="selectall"
										value="<bean:write name="proposalTable" property="refNo"/>|<bean:write name="proposalTable" property="flags"/>" />
								</display:column>
							     </logic:notEqual>
								<display:column property="status" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Status" />
								<display:column property="refNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Proposal Ref. No." /></
				            <display:column property="amountInv" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Amount To Be Invested" /></
				            <display:column property="trustType" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Trust" />
								<display:column property="securityCat" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Category" />
								<display:column property="securityName" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Security Name" />
								<display:column property="settlementDate" headerClass="GridLTHCells" class="GridLTCells" sortable="true" title="Settlement Date" />
								<display:column headerClass="GridHBg" class="GridLTCells" media="html">
									<img src='images/print.gif' border='0' alt='Report' onclick="openReport('/initialproposal.do?method=getReport&&mode=rbiauction&refNo=<%=((InvestmentProposalBean)pageContext.getAttribute("proposalTable")).getRefNo()%>');" />
								</display:column>
								</logic:present>
							</display:table>
						
						</td>
					</tr>
				</logic:present>
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</html:form>
	</body>
</html>

