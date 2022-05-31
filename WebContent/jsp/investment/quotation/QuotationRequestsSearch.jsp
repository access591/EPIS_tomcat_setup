

<%@ page language="java" import="com.epis.bean.investment.QuotationRequestBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
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
		
		var recordcd = getCheckedValue(document.forms[0].selectall);
		
	   if(optiontype!='Add'){
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		}
		if(optiontype=='Add'){		 
			
			 window.location.href='searchQuotationRequests.do?method=showQuotationRequestsAdd';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchQuotationRequests.do?method=deleteQuotationRequest&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchQuotationRequests.do?method=showEditQuotationRequest&letterno="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
  function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"QuotationRequest","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
  
		function clearData(){
   			document.forms[0].letterNo.value='';
   			document.forms[0].proposalRefNo.value='';
   			document.forms[0].trustType.value='';
   			document.forms[0].securityCategory.value='';
   			document.forms[0].letterNo.focus();
   
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/searchQuotationRequests.do?method=searchQuotationRequests">
	<%=ScreenUtilities.screenHeader("quotation.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="quotation.letterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="letterNo" styleClass="TextField" maxlength="30"  style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="quotation.proposalrefno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="proposalRefNo" styleClass="TextField"  maxlength="20" style="width:120 px" tabindex="2"/>
					
				</td>		
			
					
							
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.trusttype" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="trustType"  style="width: 120px;" styleClass="TextField" tabindex="3">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="trustList" property="trustType" labelProperty="trustType" />
                                             
                        </html:select>					
				</td>			
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="quotation.securitycategory" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="securityCategory"  style="width: 120px;" styleClass="TextField" tabindex="4">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="securityList" property="categoryId" labelProperty="categoryCd" />
                                            
                        </html:select>		
				</td>								
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
			
			<logic:present name="quotationrequestList">
				<tr>
                          <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="quotationrequestTable" name="requestScope.quotationrequestList" requestURI="/searchQuotationRequests.do?method=searchQuotationRequests" pagesize="5" >		            		            	            
					   <logic:present name="quotationrequestTable" >
					   
					   
					     <logic:equal value="Y" property="hasQuotations" name="quotationrequestTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
					     </display:column>
					     </logic:equal>
					     <logic:notEqual value="Y" property="hasQuotations" name="quotationrequestTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="quotationrequestTable" property="letterNo"/>">
					     </display:column>
					     </logic:notEqual>
			            <display:column property="proposalRefNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Proposal Ref NO."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="trustType" sortable="true" title="Trust Type" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Category" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="surplusFund" sortable="true" title="SurplusFund" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="marketType" sortable="true" title="Market Type" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="remarks" sortable="true" title="Remarks"/>
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/QuotationRequestReport.do?method=generateQuotationRequestReport&&letterNo=<%=((QuotationRequestBean)pageContext.getAttribute("quotationrequestTable")).getLetterNo()%>');" />
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
