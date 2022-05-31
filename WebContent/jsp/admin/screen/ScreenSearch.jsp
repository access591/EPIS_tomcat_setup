
<%@ page language="java" import="com.epis.bean.admin.ScreenBean,com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
	    <meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">		
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />	
		
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript" src="js/DateTime1.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		function validate(){
		
			    if(document.forms[0].screenName.value!=""){
			    if(!ValidateAlphaNumeric(document.forms[0].screenName.value)){
		    		 alert("Enter Valid Screen Name ");
		    		 document.forms[0].screenName.focus();
		   			  return false;
		    		 }
		    	}
		        
					
		     return true; 
		 }
		 function clearData(){
        	document.forms[0].moduleName.value="";
         	document.forms[0].subModuleName.value="";
         	document.forms[0].screenName.value="";
         	document.forms[0].moduleName.focus();
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
			    var url = "actionScreen.do?method=deleteScreen&deleteall="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 }else{
			 	return;
			 }
		 }else if(optiontype=='E'){	 			 
			    var url = "actionScreen.do?method=showEditScreen&screenCD="+recordcd;
				document.forms[0].action=url;
				document.forms[0].submit();
			 
		 }
	  }
  
	</script>
	</head>
	<body onload="clearData();document.forms[0].moduleName.focus();">
	<html:form action="/actionScreen.do?method=searchScreen"  onsubmit="return validate();">
	<%=ScreenUtilities.saearchHeader("screen.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                  
					<tr>
						<td colspan=4 align=center>&nbsp;<html:errors  bundle="error"/>
						</td>
					</tr>
					<tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
							 <bean:message key="screen.module" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:select  property="moduleName" styleClass="TextField" style="width:120 px" tabindex="1" >
								<html:option value="">[Select one]</html:option>
								<html:options collection="modules" property="name" labelProperty="name"/>
							</html:select>
							
						</td>	
						<td  width="20%" class="tableTextBold" align=right nowrap>
							  <bean:message key="screen.submodulename" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:select  property="subModuleName" styleClass="TextField" style="width:120 px" tabindex="2" >
								<html:option value="">[Select one]</html:option>
								<html:options collection="submodules" property="subModuleName" labelProperty="subModuleName"/>
							</html:select>
						</td>			
					</tr>
					<tr>
						<td  width="20%" class="tableTextBold" align=right nowrap>
							 <bean:message key="screen.screenname" bundle="common"/>:
						</td><td width="20%" align="left">
							<html:text size="12" property="screenName" styleClass="TextField" style="width:120 px" tabindex="3" maxlength="20" />
						</td>	
								
					</tr>
					
					
					<tr>
						<td colspan=4 align=center>&nbsp;
						</td>
					</tr>
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

                            <td width="67"> <html:submit  tabindex="4" styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button tabindex="5" styleClass="butt" property="Clear"  onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                              <td width="40"><html:button tabindex="6" styleClass="btn" property="Add"   onclick="javascript:window.location.href='/actionScreen.do?method=showNewScreen'"><bean:message key="button.add" bundle="common"/></html:button></td>
                              <td width="40"><html:button property="Edit" tabindex="7" styleClass="btn" onclick="selectCheckboxes('E');"><bean:message key="button.edit" bundle="common"/></html:button></td>
                              <td width="60"><html:button property="Delete" tabindex="8" styleClass="btn" onclick="selectCheckboxes('D');"><bean:message key="button.delete" bundle="common"/></html:button></td>
                            </tr>
                          </table></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="screens">
						<tr>
                          <td  align="left" valign="top">
                          <display:table   cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true"  sort="list" id="screenTable" name="requestScope.screens" requestURI="/actionScreen.do" pagesize="5" >		            		            	            
						    <display:column  media="html" style="width:10Px" headerClass="GridHBg" class="GridLCells">
						    <input type="radio" style="width:15px;height:15px" name="selectall" value="<bean:write name="screenTable" property="screenCD"/>">
						     </display:column>
						    <display:column style="width:200px" property="moduleName" headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Module"  /></
				            <display:column style="width:200px" property="subModuleName"  headerClass="GridLTHCells"  class="GridLTCells" sortable="true"  title="Sub Module"  /></
				            <display:column style="width:300px" property="screenName" headerClass="GridLTHCells"  class="GridLTCells"  sortable="true" title="Screen" />
				     		
			        	</display:table>							  
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>

