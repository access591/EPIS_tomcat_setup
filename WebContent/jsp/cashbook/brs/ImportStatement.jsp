<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="32kb" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
//String accountType = request.getParameter("accountType") ==null?"":request.getParameter("accountType"); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Import Satement</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/CommonFunctions.js"></script>		
		<script language="javascript" type="text/javascript">		  
		    
		  
			
		   function clearData(){
		   		  document.forms[0].file.value="";
		   		
		   }
		   
		   function validate(){		   		   
		   var ext = document.forms[0].file.value;
			  ext = ext.substring(ext.length-3,ext.length);			  
			  ext = ext.toLowerCase();
			  if(ext != 'csv') {
			    alert('You selected a .'+ext+
			          ' file; please select a .csv file instead!');
			    return false;
			   }
			  else
			    return true; 					   
		   }
		  </script>
	</head>

	<body>
		<html:form action="/Import.do?method=saveFile" method="post" onsubmit="return validate();" enctype="multipart/form-data">
			<%=ScreenUtilities.screenHeader("brs.title")%>
			<table width="80%" border="0" align="center">
				<tr>
					<td   align="center" nowrap >
						<html:errors bundle="error" />
					</td>
					
				</tr>
				<tr>
					<td  class="tableTextBold" align="right" nowrap>
						<bean:message key="bankInfo.bankName" bundle="common" />
						:
					</td>				
					<td align="left">					
						<html:select property="accountNo" styleClass="TextField" style='width:280px' >
							<html:options collection="banks" property="accountNo" name="BankMasterInfo" labelProperty="bankDetails"  />										
						</html:select>
					</td>
				</tr>
				<tr>
					<td  class="tableTextBold" align="right" nowrap>
						File : 
					</td>
					<td align="left"> 
								
							<html:file property="file" tabindex="11" ></html:file>														
							<html:hidden property="fileName" />
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>

				<tr>
					<td colspan="5" align="center">
						<html:submit value="Save" styleClass="butt" tabindex="12" />
						<input type="button" class="butt" value="Reset" onclick="javascript:document.forms[0].reset()" />
						<input type="button" class="butt" value="Cancel" onclick="javascript:history.back(-1)" />
					</td>
				</tr>
						
			 </table>
			<%=ScreenUtilities.searchFooter()%>			
	   </html:form>
	</body>
</html>
				