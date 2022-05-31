
<%@ page language="java"  import="com.epis.bean.admin.UnitBean" pageEncoding="UTF-8"%>
<%@ page  import="com.epis.utilities.ScreenUtilities" %>
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
		<title>Admin - Unit Master</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		 	if(document.forms[0].unitCode.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].unitCode.value)){
		    		 alert("Enter Valid Unit Code ");
		    		 document.forms[0].unitCode.focus();
		   			  return false;
		    		 }
		     }
		    if(document.forms[0].unitName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].unitName.value)){
		    		 alert("Enter Valid Unit Name ");
		    		 document.forms[0].unitName.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		 }
   
  function clearData(){
   			 document.forms[0].unitCode.value="";
        	 document.forms[0].unitName.value="";
        	 document.forms[0].option.value="";
        	 document.forms[0].region.value="";
   
  }
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
			
			 window.location.href='searchUnit.do?method=showUnitAdd';
		 }else
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchUnit.do?method=deleteUnit&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchUnit.do?method=showEditUnit&unitcode="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
	</script>
	</head>
	<body onload="clearData();document.forms[0].unitCode.focus();">
		<html:form action="/searchUnit.do?method=searchUnit" onsubmit="return validate();">
		
		<%=ScreenUtilities.saearchHeader("unit.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="unit.unitcode" bundle="common"/>:
				</td><td width="20%" align="left">
					<html:text size="12" property="unitCode" styleClass="TextField"  tabindex="1" maxlength="10" />				
				</td>	
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="unit.unitname" bundle="common"/>:
				</td>
				<td width="20%" align="left">
					<html:text size="12" property="unitName" styleClass="TextField"  tabindex="2" maxlength="50" />				
				</td>	
				</tr>
				<tr>
					<td  width="20%" align=right class="tableTextBold">
						<bean:message key="unit.option" bundle="common"/>:
					</td>
					<td width="20%" align="left">
						<html:select property="option" styleClass="TextField" style="width:100 px" tabindex="3">
							<html:option value="">[Select One]</html:option>
							<html:option value="Airport">Airport</html:option>
							<html:option value="RHQ">RHQ</html:option>
						</html:select>	
					</td>				
					<td class="tableTextBold" width="20%" align=right>
						<bean:message key="unit.region" bundle="common"/>:
					</td>
					<td width="20%" align="left">
						<html:select  property="region" styleClass="TextField" style="width:150 px" tabindex="4" >
							<html:option value="">[Select one]</html:option>
							<html:options collection="regions" property="regionName" labelProperty="regionName"/>
						</html:select>
					</td>	
				</tr>
			
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit tabindex="5" styleClass="butt"  ><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button tabindex="6" property="Clear" styleClass="butt" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,7)%></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="unitList">
						<tr>
                          <td  align="left" valign="top">
                          <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="unitTable" name="requestScope.unitList" requestURI="/searchUnit.do?method=searchUnit" pagesize="5" >		            		            	            
						    <display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
						    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="unitTable" property="unitCode"/>">
						     </display:column>
						    <display:column style="width:150px" property="unitCode" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Unit Code"  /></
				            <display:column style="width:150px" property="unitName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Unit Name"  /></
				            <display:column style="width:200px" headerClass="GridLTHCells"  class="GridLTCells" property="option" sortable="true" title="Unit Option" decorator="com.epis.utilities.StringFormatDecorator" />
				        	<display:column style="width:200px" headerClass="GridLTHCells"  class="GridLTCells" property="region" sortable="true" title="Region" decorator="com.epis.utilities.StringFormatDecorator" />
				            
			        	</display:table>							   
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>
                    </html:form>
	</body>
</html>
