

<%@ page language="java" import="com.epis.bean.investment.InvestmentRegisterBean,com.epis.utilities.ScreenUtilities,com.epis.utilities.StringFormatDecorator" pageEncoding="UTF-8" %>
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
	    <link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<script type="text/javascript" src="js/DateTime1.js"></script>
		<script type="text/javascript" src="js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript">
		function selectCheckboxes(optiontype) { 
				
		if(optiontype=='Add'){		 
			
			 window.location.href='searchInvestmentRegister.do?method=showInvestmentRegister&mode=<%=mode%>';
		 }
		
	  }
	 function validate()
	 {
	 	
	 	
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
	<html:form action="/searchInvestmentRegister.do?method=searchInvestmentRegister" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("investmentRegister.title")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
			<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.letterno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="letterNo" styleClass="TextField" maxlength="50"  style="width:120 px" tabindex="1"/>
					
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.proposalrefno" bundle="common"/>:
				</td><td align=left width="20%" >
					<html:text size="12" property="refNo" styleClass="TextField"  maxlength="25" style="width:120 px" tabindex="2"/>
					
				</td>		
			
					
							
			</tr>
			<tr>
				<td  class="tableTextBold" align=right nowrap>
						
						<bean:message key="proposal.securitycategory" bundle="common" />
						:
					</td>			
				
				<td align=left >
						<html:select property="securityCategory"  styleClass="TextField" style="width:100px" tabindex="3">
							<html:option value="">[Select one]</html:option>
							<html:options collection="categoryRecords" property="categoryCd" labelProperty="categoryCd" />
						</html:select>
					</td>	
				<td class="tableTextBold" align=right nowrap>
				<bean:message key="investmentRegister.securityname" bundle="common"/></td>
				<td align="left">
				<html:text property="securityName" styleClass="TextField" maxlength="50"  style="width:120px" tabindex="4"/> 
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
			
			<logic:present name="investmentRegisterList">
				<tr>
                        <td  align="left" valign="top">
					   <display:table  cellpadding="0" cellspacing="0"  class="GridBorder"  keepStatus="false" style="width:710px;height:50px" export="true"  sort="list" id="investmentRegisterTable" name="requestScope.investmentRegisterList" requestURI="/searchInvestmentRegister.do?method=searchInvestmentRegister" pagesize="5" >		            		            	            
					   <logic:present name="investmentRegisterTable" >
					   
					   
					     
			            <display:column property="letterNo" headerClass="GridLTHCells" class="GridLTCells" sortable="true"  title="Letter NO."  />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="securityName" sortable="true" title="Security Name" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="settlementDate" sortable="true" title="Settlement Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="maturityDate" sortable="true" title="Maturity Date" />
			            <display:column headerClass="GridLTHCells" class="GridLTCells" property="trustType" sortable="true" title="Trust" />
			            <display:column headerClass="GridHBg" class="GridLTCells" media="html">
						 <img src='images/print.gif' border='0' alt='Report' onclick="openReport('/InvestmentRegisterReport.do?method=generateInvestmentRegisterReport&&registerCd=<%=((InvestmentRegisterBean)pageContext.getAttribute("investmentRegisterTable")).getRegisterCd()%>');" />
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
