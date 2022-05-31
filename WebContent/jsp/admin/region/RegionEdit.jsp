<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>Admin Region Master</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].regionName.value==""){
		     alert("Region Name is Mandatory");
		     document.forms[0].regionName.focus();
		     return false;
		    }
		   	 if(!ValidateAlphaNumeric(document.forms[0].regionName.value)){
		     alert("Enter Valid Region Name ");
		     document.forms[0].regionName.focus();
		     return false;
		    } 
		    if(document.forms[0].aaiCategory.value==""){
			     alert("AAI Category is Mandatory");
			     document.forms[0].aaiCategory.focus();
			     return false;
		    }
		    if(document.forms[0].remarks.value!=""){
		   		 if(!ValidateTextArea(document.forms[0].remarks.value)){
		    		 alert("Enter Valid Remarks ");
		    		 document.forms[0].remarks.focus();
		   			  return false;
		    		 }
		     }
		    
		    return true; 
		 }
		
		</SCRIPT>
	</head>
	<body onload="document.forms[0].regionName.focus();" >
		<html:form action="/editRegion.do?method=updateRegion" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("region.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
					</tr>
					<tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
						 <bean:message key="common.stars" bundle="common"/><bean:message key="region.regionname" bundle="common"/>:
						</td>
						<td width="20%" align="left">					
						    <html:hidden property="regionCd" name="region" />
							<html:text size="12" property="regionName" styleClass="TextField" name="region" style="width:120 px" tabindex="1" maxlength="30" />
						</td>				
						<td class="tableTextBold" width="20%" align=right>
							<bean:message key="common.stars" bundle="common"/><bean:message key="region.aaicategory" bundle="common"/>:
						</td>
						<td width="20%"  align="left">
							<html:select property="aaiCategory" styleClass="TextField" style="width:150 px" name="region" tabindex="2">
								<html:option value="">[Select One]</html:option>
								<html:option value="METRO AIRPORT">METRO AIRPORT</html:option>
								<html:option value="NON-METRO AIRPORT">NON-METRO AIRPORT</html:option>
							</html:select>
						</td>	
					</tr>
					<tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
							 <bean:message key="region.remarks" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:textarea  property="remarks" styleClass="TextField" name="region" style="width:120Px" tabindex="3" rows="4"></html:textarea>
						</td>				
						<td  width="20%" align=right>
							&nbsp;
						</td>
						<td width="20%" >
							&nbsp;
						</td>	
				</tr>
				<tr>
					<td colspan=4 align=center>&nbsp;
					</td>
				</tr>
				<tr>
				<td  colspan=4 align=center>
				<html:submit    tabindex="4" styleClass="butt"> <bean:message key="button.save" bundle="common"/></html:submit>
				<html:reset  styleClass="butt" property="Reset" tabindex="5" onclick="Reset();"><bean:message key="button.reset" bundle="common"/> </html:reset>
				<html:button  property="Cancel" styleClass="butt" tabindex="6" onclick="javascript:window.location.href='searchRegion.do?method=searchRegion'" onblur="document.forms[0].regionName.focus();" ><bean:message key="button.cancel" bundle="common"/></html:button>
				</td>	
				</tr>
				<tr>
					<td colspan=4 align=center>&nbsp;
					</td>
				</tr>	
                    </table><%=ScreenUtilities.screenFooter()%>
	</html:form>
	
	</body>
</html>
