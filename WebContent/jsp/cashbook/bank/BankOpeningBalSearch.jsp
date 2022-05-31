<%@ page import="com.epis.bean.cashbook.BankOpenBalInfo,com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"  %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
String basePath = basePathBuf.toString() ;

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>AAI - Cashbook - Master - Bank Opening Balance Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<script type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></script>
		
		
		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
		
		<script language="javascript" type="text/javascript">
		   
		   function clearData(){
		   		  document.forms[0].bankName.value="";		   		  
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
					 window.location.href="/bankOpenBalSearchResult.do?method=fwdtoNew";
				}
				if(optiontype=='Delete'){		 
					var state=window.confirm("Are you sure you want to delete ?");
					if(state){
						document.forms[0].action="/bankOpenBalSearchResult.do?method=delete&deleteRecord="+recordcd;
						document.forms[0].submit();
					}else{
						return;
					}
				}else if(optiontype=='Edit'){	 			 
					document.forms[0].action = "/bankOpenBalSearchResult.do?method=fwdtoEdit&&accountNo="+recordcd;
					document.forms[0].submit();					 
				}
			}		  
		  </script>
	</head>

	<body >
	
	<html:form action="/bankOpenBalSearchResult.do?method=search" method="post">
			<%=ScreenUtilities.screenHeader("bankopenbal.search")%>
			<table width="70%" border="0" align="center">
					
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="bankInfo.bankName" bundle="common" />
								:
							</td>
							
							<td width="20%">
								<html:text property="bankName" maxlength="50" styleClass="tableText"/>
							</td>
						<tr>
							<td height="5px">
								
							</td>
						</tr>														

						
						<tr>
							<td colspan="4" align="center">
								 <html:submit  styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit>
                            	<html:button styleClass="butt" property="Clear"  onclick="javascript:document.forms[0].reset()"><bean:message key="button.clear" bundle="common"/></html:button>
							</td>
						</tr>
						
						</table>   <%=ScreenUtilities.searchSeperator()%>
						<%=ScreenUtilities.getAcessOptions(session,1)%>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<html:radio property="deleteRecord" value="" style="display:none;" />
							<tr>
								<th align="center"><html:errors  bundle="error"  property="deleteRecord"/>&nbsp;</th>					
							</tr>			
					
                       		 
                       		<% if(request.getAttribute("dataList")!=null){%>
                       		
							<tr>
								<td align="center" width="100%">
									<input type="radio" name="deleteRecord"  style='display:none'/>																	
									<display:table  cellpadding="0" cellspacing="0" class="GridBorder" style="width:730px;height:50px" keepStatus="true" export="true" sort="list" name="dataList" name="requestScope.dataList" pagesize="10" id='searchData' requestURI="/bankOpenBalSearchResult.do?method=search">
										<display:setProperty name="export.amount" value="list" />
										<display:setProperty name="export.excel.filename" value="BankOpenBal.xls" />
										<display:setProperty name="export.pdf.filename" value="BankOpenBal.pdf" />
										<display:setProperty name="export.rtf.filename" value="BankOpenBal.rtf" />
										<display:column style="width:10px;"  media="html" headerClass="GridHBg" class="GridLCells">
											<html:radio property="deleteRecord" value='<%=((BankOpenBalInfo)pageContext.getAttribute("searchData")).getAccountNo()%>' />
										</display:column>
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="accountNo" sortable="true"  title="Account No">
										</display:column>
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="bankName" sortable="true" title="Bank Name" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="opendate" sortable="true" title="Opened Date" />
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="amount" sortable="true" title="Amount" decorator="com.epis.utilities.NumberFormatDecorator" format="$ {0,number,0,000.00}"/>
										<display:column headerClass="GridLTHCells"  class="GridLTCells" property="amountType" sortable="true" title="Type" />
										
									</display:table>
								</td>
							</tr>
							<%}%>
		</table><%=ScreenUtilities.searchFooter()%>
		
		</html:form>
	</body>
</html>
