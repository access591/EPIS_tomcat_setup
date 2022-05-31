
<%@ page language="java"  import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.admin.ProfileOptionBean" %>
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
		<title>Admin - Profile Option search Page</title>
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
		    if(document.forms[0].optionName.value!=""){
		   		 if(!ValidateAlphaNumeric(document.forms[0].optionName.value)){
		    		 alert("Enter Valid Option Name ");
		    		 document.forms[0].optionName.focus();
		   			  return false;
		    		 }
		     }
		    return true; 
		 }
	  
	  function clearData(){
	  document.forms[0].optionCode.value="";
	   document.forms[0].optionName.value="";
	   
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
			    var url = "searchOption.do?method=deleteOption&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='E'){	 			 
			    var url = "searchOption.do?method=showEditOption&optioncode="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
	</script>
	</head>
	<body onload="clearData();document.forms[0].optionCode.focus();">
	<html:form action="/searchOption.do?method=searchOption" onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("profileoptions.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      <tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
								<bean:message key="profileoptions.optioncode" bundle="common"/>:
						</td>
						<td width="20%" align="left">
							<html:text size="12" property="optionCode" styleClass="TextField" style="width:120 px" tabindex="1" maxlength="5" />		
						</td>	
						<td  width="20%" class="tableTextBold" align=right nowrap>
						<bean:message key="profileoptions.optionname" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:text size="12" property="optionName" styleClass="TextField" style="width:120 px" tabindex="2" maxlength="200" />		
						</td>			
					</tr>		
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit  tabindex="3" styleClass="butt"><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button tabindex="4" property="Clear"  styleClass="butt" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                              <td width="40"><html:button tabindex="5" property="Add"  styleClass="btn" onclick="javascript:window.location.href='searchOption.do?method=showOptionAdd'"><bean:message key="button.add" bundle="common"/>
				</html:button></td>
                             <td width="40"><html:button property="Edit" tabindex="6" styleClass="btn" onclick="selectCheckboxes('E');"><bean:message key="button.edit" bundle="common"/></html:button></td>
                              <td width="60"><html:button property="Delete" tabindex="7" styleClass="btn" onclick="selectCheckboxes('D');"><bean:message key="button.delete" bundle="common"/></html:button></td>
                            </tr>
                          </table></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="optionList">
						<tr>
                          <td  align="left" valign="top">
                          <display:table    cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px"  export="true"  sort="list" id="optionTable" name="requestScope.optionList" requestURI="/searchOption.do?method=searchOption" pagesize="5" >		            		            	            
					    <display:column  media="html" style="width:10Px" headerClass="GridHBg" class="GridLCells">
					    <input type="radio" style="width:15px;height:px" name="selectall" value="<bean:write name="optionTable" property="optionCode"/>">
					     </display:column>
			            <display:column style="width:100px" property="optionCode" headerClass="GridLTHCells"  class="GridLTCells" sortable="true" title="Option Code"  />
			             <display:column style="width:300px" property="optionName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Option Name"  />
			              <display:column style="width:300px" property="description" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Description"  />
			           
			        	</display:table>							   
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%></html:form>
                   
	</body>
</html>
