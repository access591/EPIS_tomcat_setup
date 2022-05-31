
<%@ page language="java"  import="com.epis.bean.admin.RoleBean" pageEncoding="UTF-8"%>
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

		<title>Admin - Sub Module Search Page</title>

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
		    if(document.forms[0].roleName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].roleName.value)){
		    		 alert("Enter Valid Role Name ");
		    		 document.forms[0].roleName.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		}
	   
	  
	  function clearData(){
	         document.forms[0].roleName.value="";
        	 
	   
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
			
			 window.location.href='searchRole.do?method=showRoleAdd';
		 }else
		if(optiontype=='Delete'){		 
			 var state=window.confirm("Are you sure you want to delete ?");
			 if(state){
			    var url = "searchRole.do?method=deleteRole&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='Edit'){	 			 
			    var url = "searchRole.do?method=showEditRole&roleCd="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
	</script>
	</head>
	<body onload="clearData();document.forms[0].roleName.focus();">
	<html:form action="/searchRole.do?method=searchRole" onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("role.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr>
				
					<td  width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="role.rolename" bundle="common"/>:
					</td><td width="20%" align="left">
						<html:text size="12" property="roleName" styleClass="TextField"  tabindex="1" maxlength="30" />		
					</td>			
					</tr>	
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit  tabindex="2" styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button tabindex="3" styleClass="butt" property="Clear"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,4)%></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="roleList">
						<tr>
                          <td  align="left" valign="top">
							   <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="roleTable" name="requestScope.roleList" requestURI="/searchRole.do?method=searchRole" pagesize="5" >		            		            	            
							    <display:column  media="html" style="width:10Px" headerClass="GridHBg" class="GridLCells">
							    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="roleTable" property="roleCd"/>">
							    </display:column>
					            <display:column property="roleCd" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Role Code"  />
					            <display:column property="roleName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Role Name"  />
					            <display:column property="roleDescription" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Role Description" decorator="com.epis.utilities.StringFormatDecorator" />
					            
					        	</display:table>	        	
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>     
                    </html:form>              
	</body>
</html>
