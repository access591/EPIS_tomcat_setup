
<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].categoryCd.value==""){
		     alert(" Please Enter The Security Category (Mandatory)");
		     document.forms[0].categoryCd.focus();
		     return false;
		    }
		    if(document.forms[0].categoryCd.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].categoryCd.value)){
		    		 alert(" Please Enter Valid Security Category Code(Expecting Alpha Numeric Value)");
		    		 document.forms[0].categoryCd.focus();
		   			  return false;
		    		 }
		     }
		     if(document.forms[0].openingbal.value=="")
		     {
		     	alert("Please Select The Opening Balance (Mandatory)");
		    	document.forms[0].openingbal.focus();
		   		return false;
		     }
		     if(document.forms[0].investinterest.value=="")
		     {
		     	alert("Please Select The InvestInterest (Mandatory)");
		    	document.forms[0].investinterest.focus();
		   		return false;
		     }
		     if(document.forms[0].investbaseamt.value=="")
		     {
		     	alert("Please Select The BaseAmount (Mandatory)");
		    	document.forms[0].investbaseamt.focus();
		   		return false;
		     }
		     if(document.forms[0].description.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].description.value)){
		    		 alert("Please Enter The Valid Description (Expecting Alpha Numeric Value) ");
		    		 document.forms[0].description.focus();
		   			  return false;
		    		 }
		     }
		     if(document.forms[0].openingbal.value==document.forms[0].investinterest.value ||document.forms[0].openingbal.value==document.forms[0].investbaseamt.value||document.forms[0].investbaseamt.value==document.forms[0].investinterest.value ){
		     alert(" Please Enter Account Heads Different ( Account Heads Should Not be Same)");
		     return false;
		     }
		    return true; 
		 }
		function clearData(){
        window.document.forms[0].reset();
  }
		</SCRIPT>
	</head>
	<body onload="document.forms[0].categoryCd.focus();" >
	<html:form action="/addSCategory.do?method=addSecurityCategory" onsubmit="return validate();" >
	<%=ScreenUtilities.screenHeader("category.newTitle")%>
	<table width="600" border="0" cellspacing="3" cellpadding="0">
                      <tr>
				<td colspan=4 align=center>
				<html:errors  bundle="error"/>
				</td>
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="category.categorycd" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="categoryCd" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="7"/>
					
				</td>	
				<td width="10%">&nbsp;</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="category.openingbal" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select property="openingbal" styleClass="TextField" tabindex="2" style="width:200 px"  >
						 <html:option value="">[Select One]</html:option>
						 <html:options collection="accHeadList" property="accHeads" labelProperty="accHeads"/>
                    </html:select>
				</td>							
			</tr>
			<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<font color=red>*</font><bean:message key="category.investinterest" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select property="investinterest" styleClass="TextField" tabindex="3" style="width:200 px"  >
						 <html:option value="">[Select One]</html:option>
						 <html:options collection="accHeadList" property="accHeads" labelProperty="accHeads"/>
                    </html:select>
					
				</td>	
				<td width="10%">&nbsp;</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <font color=red>*</font><bean:message key="category.investbaseamt" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:select property="investbaseamt" styleClass="TextField" tabindex="4" style="width:200 px"  >
						 <html:option value="">[Select One]</html:option>
						 <html:options collection="accHeadList" property="accHeads" labelProperty="accHeads"/>
                    </html:select>
				</td>	
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="category.desc" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="description" styleClass="TextField" style="width:180 px" tabindex="5" maxlength="100"/>	
				</td>							
			</tr>
			
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=6 align=center><html:submit styleClass="butt" property="Save" value="Save"  tabindex="6"/>&nbsp;<html:button styleClass="butt" property="Clear" value="Clear" tabindex="7" onclick="clearData();"/>
			<html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='searchSCategory.do?method=searchSCategory'" onblur="document.forms[0].categoryCd.focus();"  tabindex="8"/>
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
