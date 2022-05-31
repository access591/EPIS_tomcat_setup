

<%@ page language="java" import="com.epis.bean.investment.GenerateBankLetterBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
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

		<title>My JSP 'GenerateBankLetterSearch' starting page</title>

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
			
			 window.location.href='searchGenerateBankLetter.do?method=showGenerateBankLetter';
		 }
		 if(optiontype=='Report')
		 {
		 	var swidth=screen.Width-10;
			var sheight=screen.Height-150;
		 	var url='/reportGenerateBankLetter.do?method=generateBankLetterReport&quotationCd='+recordcd;
		 	window.open(url,"GenerateBankLetter","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			return false;
		 	
		 	
		 }
		
	  }
  
  function openReport(url){
  				
		    	var swidth=screen.Width-10;
				var sheight=screen.Height-150;
				var wind1 = window.open(url,"GenerateBankLetter","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
			}
  
  
		function clearData(){
   			window.document.forms[0].reset();
   
  		}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].bankLetterNo.focus();">
	<html:form action="/searchGenerateBankLetter.do?method=searchBankLetter">
	<%=ScreenUtilities.screenHeader("bankletter.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="bankletter.lettterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="bankLetterNo" styleClass="TextField"   style='width:200px' tabindex="1" maxlength="50"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="bankletter.bankname" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:select property="accountNo"  styleClass="TextField" style='width:230px' tabindex="2">          
                        <html:option value="">[Select One]</html:option>
                        <html:options collection="banks" property="accountNo" labelProperty="bankDetails" />
                                             
                        </html:select>				
					
				</td>		
			
					
							
			</tr>
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit  styleClass="butt" tabindex="3" ><bean:message key="button.search" bundle="common"/></html:submit>&nbsp;<html:button styleClass="butt" property="Clear" tabindex="4"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
			</td>	
			</tr>
			</table>
			<%=ScreenUtilities.searchSeperator()%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                         <td width="30%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,7)%></td>
                          <td width="50%" align="left" valign="top">&nbsp;</td>
                        </tr>	
			
			<logic:present name="bankLetterList">
				<tr>
                          <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="bankletterTable" name="requestScope.bankLetterList" requestURI="/searchGenerateBankLetter.do?method=searchBankLetter" pagesize="5" >		            		            	            
					    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="bankletterTable" property="quotationCd"/>">
					     </display:column>
			            <display:column property="bankLetterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="LetterNo."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="dealDate" sortable="true" title="Deal Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="settlementDate" sortable="true" title="Settlement Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="letterNo" sortable="true" title="Approval RefNo." />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="bankName" sortable="true" title="Bank Name" />
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <a href="javascript:void(0)"  onclick="openReport('/actionYTM.do?method=showYTMAddDetails&letterNo=<%=((GenerateBankLetterBean)pageContext.getAttribute("bankletterTable")).getLetterNo()%>&ytmshow=yes');">YTM</a>
						</display:column>
			        	</display:table>
			        </td>
			        </tr>
		   	</logic:present>
			</table>
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
