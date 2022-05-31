
<%@ page language="java"  import="com.epis.bean.admin.SMBean,com.epis.bean.admin.Bean" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
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

		<title>Admin - Sub Module Search Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 function validate(){
		    if(document.forms[0].subModuleName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].subModuleName.value)){
		    		 alert("Enter Valid Sub Module Name ");
		    		 document.forms[0].subModuleName.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		}
		   
	  
	  function selectCheckboxes(optiontype) { 
		
		var recordcd = getCheckedValue(document.forms[0].selectall);
		
	   
		if(recordcd=='')
		{
			alert("select atleast one record");
			return;
		}
		if(optiontype=='D'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchSM.do?method=deleteSM&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='E'){	 			 
			    var url = "searchSM.do?method=showEditSM&smcode="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
	  
	  function clearData(){
	         document.forms[0].subModuleName.value="";
        	 document.forms[0].moduleCode.value="";   
	   
	  }
	</script>
	</head>
	<body onload="clearData();document.forms[0].moduleCode.focus();">
	<html:form action="/searchSM.do?method=searchSM" onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("submodule.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr>
						<td colspan=4 align=center>&nbsp;<html:errors  bundle="error"/>
						</td>
					</tr>
					<tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
							 <bean:message key="submodule.module" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:select  property="moduleCode" styleClass="TextField" style="width:120 px" tabindex="1" ><html:option value="">[Select One]</html:option>
							<html:options collection="modules" property="code" labelProperty="name"/>	</html:select>	
						</td>	
						<td  width="20%" class="tableTextBold" align=right nowrap>
							<bean:message key="submodule.submodulename" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:text size="12" property="subModuleName" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="30" />		
						</td>			
					</tr>	
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit  styleClass="butt" tabindex="3"><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button styleClass="butt" property="Clear"  tabindex="4" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                              <td width="40"><html:button property="Add" tabindex="5" styleClass="btn" onclick="javascript:window.location.href='searchSM.do?method=showSMAdd'"><bean:message key="button.add" bundle="common"/></html:button></td>
                              <td width="40"><html:button property="Edit" tabindex="6" styleClass="btn" onclick="selectCheckboxes('E');"><bean:message key="button.edit" bundle="common"/></html:button></td>
                              <td width="60"><html:button property="Delete" tabindex="7" styleClass="btn" onclick="selectCheckboxes('D');"><bean:message key="button.delete" bundle="common"/></html:button></td>
                            </tr>
                          </table></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="smList">
						<tr>
                          <td  align="left" valign="top">
							   <display:table  cellpadding="0" cellspacing="0" class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="smTable" name="requestScope.smList" requestURI="/searchSM.do?method=searchSM" pagesize="5" >		            		            	            
							    <display:column  media="html" style="width:10Px" headerClass="GridHBg" class="GridLCells">
							    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="smTable" property="subModuleCode"/>">
							     </display:column>
					            <display:column style="width:200px" property="moduleCode" headerClass="GridLTHCells"  class="GridLTCells"  sortable="true"  title="Module Code"  />
					             <display:column style="width:200px" property="subModuleCode" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Sub Module Code"  />
					              <display:column style="width:300px" property="subModuleName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Sub Module Name"  />
					                  <display:column style="width:300px" property="sortingOrder" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Sorting Order"  />
					        	</display:table>
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>
                   
	</html:form>
	</body>
</html>
