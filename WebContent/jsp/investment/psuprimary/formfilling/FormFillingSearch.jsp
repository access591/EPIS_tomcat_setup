
<%@ page language="java" import="com.epis.bean.investment.FormFillingBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'QuotationRequestsSearch' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
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
			
			 window.location.href='searchFormFilling.do?method=showFormFillingAdd&mode=PSUPrimary';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			 if(val[1]=='A'){
		 			alert("This FormFilling cannot be deleted.As the Approval has been done.");
		 			return false;
		 		}
		 	else{
			    var url = "searchFormFilling.do?method=deleteFormFilling&deleteall="+recordcd+"&mode=PSUPrimary";
				document.forms[0].action=url;
				document.forms[0].submit();
				}
			 }else{
			 	return;
			 }
		 }
		 else if(optiontype=='Edit'){
		 
		 		if(val[1]=='A'){
		 			alert("This FormFilling cannot be edited As the Approval has been done.");
		 			return false;
		 		}
		 		else{	 			 
			    var url = "searchFormFilling.do?method=showEditFormFilling&formCd="+recordcd+"&mode=PSUPrimary";
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
		 		var url="searchFormFilling.do?method=approvalFormFilling&formCd="+recordcd+"&&edit=Y&mode=PSUPrimary&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchFormFilling.do?method=approvalFormFilling&formCd="+recordcd+"&mode=PSUPrimary&updatedFlag=1";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 else if(optiontype=='Approval-2')
		 {	
		 	if(val[1]!='A'){
		 			alert("As Level-1 approval is not Completed, Level-2 Approval cannot be made");
		 			return false;
		 	}else if(val[2]=='A'){
		 		var url="searchFormFilling.do?method=approvalFormFilling&formCd="+recordcd+"&&edit=Y&mode=PSUPrimary&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}else{	    
			 	var url="searchFormFilling.do?method=approvalFormFilling&formCd="+recordcd+"&mode=PSUPrimary&updatedFlag=2";
			 	document.forms[0].action=url;
			 	document.forms[0].submit();
		 	}
		 }
		 
		 
	  }
  
  function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"FormFilling","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
  
		function clearData(){
   			document.forms[0].reset();
   
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].securityName.focus();">
	<html:form action="/searchFormFilling.do?method=searchFormFilling&mode=PSUPrimary">
	<%=ScreenUtilities.screenHeader("formfilling.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="formfilling.securityname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="securityName" styleClass="TextField" maxlength="100"  style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="formfilling.proposalrefNo" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="proposalRefNo" styleClass="TextField"  maxlength="25" style="width:120 px" tabindex="2"/>
					
				</td>		
			
					
							
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="formfilling.trusttype" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="trustType"  style="width: 120px;" styleClass="TextField" tabindex="3">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="trustList" property="trustType" labelProperty="trustType" />
                                             
                        </html:select>					
				</td>			
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="formfilling.nameofApplicant" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text property="nameofApplicant" size="12" styleClass="TextField" maxlength="100" style="width:120 px" tabindex="4" />	
				</td>								
			</tr>
			<tr>
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
			<td colspan=2>&nbsp;</td>
			</tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
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
			
			<logic:present name="formfillingList">
				<tr>
                        <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="formfillingTable" name="requestScope.formfillingList" requestURI="/searchFormFilling.do?method=searchFormFilling&mode=PSUPrimary" pagesize="5" >		            		            	            
					   <logic:present name="formfillingTable" >
					   
					   
					     <logic:equal value="Y" property="hasQuotations" name="formfillingTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
					     </display:column>
					     </logic:equal>
					     <logic:notEqual value="Y" property="hasQuotations" name="formfillingTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="formfillingTable" property="formCd"/>|<bean:write name="formfillingTable" property="flags"/>">
					     </display:column>
					     </logic:notEqual>
			            <display:column property="proposalRefNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Proposal Ref NO."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="trustType" sortable="true" title="Trust Type" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="investAmt" sortable="true" title="Amount In Rs" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="nameofApplicant" sortable="true" title="Name of Applicant" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="noofBonds" sortable="true" title="No of Bonds"/>
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/FormFillingReport.do?method=generateFormFillingReport&&mode=PSUPrimary&formCd=<%=((FormFillingBean)pageContext.getAttribute("formfillingTable")).getFormCd()%>');" />
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
