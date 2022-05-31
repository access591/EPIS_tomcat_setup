

<%@ page language="java" import="com.epis.bean.investment.LetterToBankBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String mode=request.getParameter("mode");
System.out.println("the mode is...."+mode);
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
			
			 window.location.href='searchLettertoBank.do?method=showLettertoBankAdd&mode=<%=mode%>';
		 }
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchLettertoBank.do?method=deleteLettertoBank&deleteall="+recordcd+"&mode=<%=mode%>";
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchLettertoBank.do?method=showEditLettertoBank&letterNo="+recordcd+"&mode=<%=mode%>";
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
    
  function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"LetterToBank","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
  
		function clearData(){
   			document.forms[0].reset();
   
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].letterNo.focus();">
	<html:form action="/searchLettertoBank.do?method=searchLettertoBank">
	<%=ScreenUtilities.screenHeader("lettertobank.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="lettertobank.letterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="letterNo" styleClass="TextField" maxlength="50"  style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="lettertobank.proposalrefno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="refNo" styleClass="TextField"  maxlength="25" style="width:120 px" tabindex="2"/>
					
				</td>		
			
					
							
			</tr>
			<logic:equal name="modeType" scope="request" value="psuprimary">
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="lettertobank.bankname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="accountNo"  styleClass="TextField" style='width:230px' tabindex="3">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="banks" property="accountNo" labelProperty="bankDetails" />
                                             
                        </html:select>					
				</td>			
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="lettertobank.beneficiaryname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text property="beneficiaryName" size="12" styleClass="TextField" maxlength="100" style="width:120 px" tabindex="4" />	
				</td>								
			</tr>
			</logic:equal>
			<logic:equal name="modeType" scope="request" value="rbiauction">
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="lettertobank.sellerrefno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text property="sellerRefNo" size="12" styleClass="TextField" maxlength="20" style="width:120 px" tabindex="3" />					
				</td>			
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="lettertobank.securityname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text property="securityName" size="12" styleClass="TextField" maxlength="100" style="width:120 px" tabindex="4" />	
				</td>								
			</tr>
			
			</logic:equal>
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
			
			<logic:present name="lettertobankList">
				<tr>
                        <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="lettertobankTable" name="requestScope.lettertobankList" requestURI="/searchLettertoBank.do?method=searchLettertoBank" pagesize="5" >		            		            	            
					   <logic:present name="lettertobankTable" >
					   
					   
					     <logic:equal value="Y" property="hasQuotations" name="lettertobankTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <img src='images/lockIcon.gif' border='0' alt='Lock' />									
					     </display:column>
					     </logic:equal>
					     <logic:notEqual value="Y" property="hasQuotations" name="lettertobankTable">
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="lettertobankTable" property="letterNo"/>">
					     </display:column>
					     </logic:notEqual>
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter NO."  />
			            <logic:equal name="modeType" scope="request" value="psuprimary">
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="bankName" sortable="true" title="Bank Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="beneficiaryName" sortable="true" title="Beneficiary Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="bankDate" sortable="true" title="Bank Date" />
			            </logic:equal>
			            
			            <logic:equal name="modeType" scope="request" value="rbiauction">
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="dealDate" sortable="true" title="Deal Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="settlementDate" sortable="true" title="Settlement Date" />
			            </logic:equal>
			            
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
			             <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/LettertoBankReport.do?method=generateLettertoBankReport&&letterNo=<%=((LetterToBankBean)pageContext.getAttribute("lettertobankTable")).getLetterNo()%>');" />
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
