

<%@ page language="java" import="com.epis.bean.investment.ConfirmationFromCompany,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String mode=request.getParameter("mode");

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'ConfirmationToCompanySearch' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<script type="text/javascript" src="<%=basePath%>js/DateTime1.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript">
		  function selectCheckboxes(optiontype) { 
		
		var optionval = getCheckedValue(document.forms[0].selectall);
		var val = optionval.split("|");
		var recordcd = val[0];
		var confirmation=val[1];
		var saveflag=val[2];
		
		
	   
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		
		if(optiontype=='Add'){		 
			if(saveflag=='C')
			{
			 window.location.href='searchConfirmationCompany.do?method=showConfirmationCompanyAdd&mode=<%=mode%>&letterNo='+recordcd;
			 }
			 else
			 {
			 	alert("Record is Already Added");
			 	return false;
			 }
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			 if(val[2]=='A'){
		 			alert("This ConfirmationFromCompany cannot be deleted.As the Approval has been done.");
		 			return false;
		 		}
		 	else{
			    var url = "searchConfirmationCompany.do?method=deleteConfirmationCompany&deleteall="+recordcd+"&mode=<%=mode%>";
				document.forms[0].action=url;
				document.forms[0].submit();
				}
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){
		 	if(val[3]=='A'){
		 			alert("This ConfirmationFromCompany cannot be deleted.As the Approval has been done.");
		 			return false;
		 		}
		 	if(val[2]=='C')
		 	{
		 		alert("The Confirmation Record is not Added");
		 		return false;
		 	}
		 	else if(val[2]!='C'){	 			 
			    var url = "searchConfirmationCompany.do?method=showEditConfirmationCompany&letterNo="+recordcd+"&mode=<%=mode%>";
				document.forms[0].action=url;
				document.forms[0].submit();
			}
			 
		 }
		 
		 
		 else if(optiontype=='Approval-1')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}	
		 	if(val[4]=='A'){
		 			alert("The Level-2 Approval has already been Done.");
		 			return false;
		 	}else if(val[3]=='A'){
		 		var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-2')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}		
		 	if(val[3]!='A'){
		 			alert("As Level-1 approval is not Completed, Level-2 Approval cannot be made");
		 			return false;
		 	}if(val[5]=='A'){
		 			alert("The Level-3 Approval has already been Done.");
		 			return false;
		 	}else if(val[4]=='A'){
		 		var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-3')
		 {	
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}		    
		 	if(val[4]!='A'){
		 			alert("As Level-2 approval is not Completed, Level-3 Approval cannot be made");
		 			return false;
		 	}if(val[6]=='A'){
		 			alert("The Level-4 Approval has already been Done.");
		 			return false;
		 	}else if(val[5]=='A'){
		 		var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=3";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-4')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}			    
		 	if(val[5]!='A'){
		 			alert("As Level-3 approval is not Completed, Level-4 Approval cannot be made");
		 			return false;
		 	}if(val[7]=='A'){
		 			alert("The Level-5 Approval has already been Done.");
		 			return false;
		 	}else if(val[6]=='A'){
		 		var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=4";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-5')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}			    
		 	if(val[6]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-5 Approval cannot be made");
		 			return false;
		 	}
		 	else if(val[7]=='A'){
		 			var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=5";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=5";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-6')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}			    
		 	if(val[6]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-6 Approval cannot be made");
		 			return false;
		 	}else if(val[8]=='A'){
		 			var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=6";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=6";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 
		 
		 else if(optiontype=='Approval-7')
		 {
		 	if(confirmation=='N')
		 	{
		 		alert("The Confirmation is Not Agree");
		 		return false;
		 	}			    
		 	if(val[6]!='A'){
		 			alert("As Level-4 approval is not Completed, Level-7 Approval cannot be made");
		 			return false;
		 	}else if(val[9]=='A'){
		 			var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&&mode=<%=mode%>&edit=Y&updatedFlag=7";
				 	document.forms[0].action=url;
				 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchConfirmationCompany.do?method=approvalConfirmation&letterNo="+recordcd+"&mode=<%=mode%>&updatedFlag=7";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		
	  }
	 function validate()
	 {
	 	
	 	if(document.forms[0].settlementDate.value!="")
	 	{
	 	 	if(!convert_date(document.forms[0].settlementDate)){
				document.forms[0].settlementDate.select();
				return(false);
			}
		}
	 }
    
  function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"ConfirmationCompany","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
  
		function clearData(){
   			document.forms[0].reset();
   
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/searchConfirmationCompany.do?method=searchConfirmationCompany" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("confirmationcomapny.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="confirmationcompany.letterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="letterNo" styleClass="TextField" maxlength="50"  style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="confirmationcomapny.proposalrefno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="refNo" styleClass="TextField"  maxlength="25" style="width:120 px" tabindex="2"/>
					
				</td>		
			
					
							
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="confirmationcomapny.settlementDate" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text property="settlementDate" styleId="settlementDate" style="width: 95px;" tabindex="3" styleClass="TextField" />
						<html:link href="javascript:showCalendar('settlementDate');" tabindex="4">
							<html:img src="images/calendar.gif" alt="Datepicker" border="0" align="center" />
						</html:link>					
				</td>			
				
				<td  class="tableTextBold" align=right nowrap>
						
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>			
				
				<td align=left >
						<html:select property="securityCategory"  styleClass="TextField" style="width:100px" tabindex="6">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>						
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				<input type="hidden" name="mode" value="<%=mode%>"/>
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit  styleClass="butt" tabindex="5" ><bean:message key="button.search" bundle="common"/></html:submit>&nbsp;<html:button styleClass="butt" property="Clear" tabindex="6"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
			</td>	
			</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                         <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,7)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>	
			
			<logic:present name="confirmationcompanyList">
				<tr>
                        <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="confirmationcompanyTable" name="requestScope.confirmationcompanyList" requestURI="/searchConfirmationCompany.do?method=searchConfirmationCompany" pagesize="5" >		            		            	            
					   <logic:present name="confirmationcompanyTable" >
					   
					   
					     <logic:equal value="Y" property="hasQuotations" name="confirmationcompanyTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
					     </display:column>
					     </logic:equal>
					     <logic:notEqual value="Y" property="hasQuotations" name="confirmationcompanyTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="confirmationcompanyTable" property="letterNo"/>|<bean:write name="confirmationcompanyTable" property="confirm"/>|<bean:write name="confirmationcompanyTable" property="saveflag"/>|<bean:write name="confirmationcompanyTable" property="flags"/>">
					     </display:column>
					     </logic:notEqual>
					     <display:column property="status" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Status"  />
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter NO."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="settlementDate" sortable="true" title="Settlement Date" decorator="com.epis.utilities.StringFormatDecorator" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Name" decorator="com.epis.utilities.StringFormatDecorator" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="maturityDate" sortable="true" title="Maturity Date" decorator="com.epis.utilities.StringFormatDecorator" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="trustType" sortable="true" title="Trust" decorator="com.epis.utilities.StringFormatDecorator" />
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/ConfirmationCompanyReport.do?method=generateConfirmationCompanyReport&&letterNo=<%=((ConfirmationFromCompany)pageContext.getAttribute("confirmationcompanyTable")).getLetterNo()%>');" />
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
