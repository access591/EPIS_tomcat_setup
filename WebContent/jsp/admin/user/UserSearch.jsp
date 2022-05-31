<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.bean.admin.UserBean,com.epis.utilities.StringFormatDecorator" %>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>AAI - Admin - Masters - User Search Screen</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="User Search Screen">
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<script language="javascript" >
		    
		    function addUser(){
                  document.forms[0].action = "/userSearchResult.do?method=fwdtoNew";
                  document.forms[0].submit();
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
					    var url = "userSearchResult.do?method=delete&&userIds="+recordcd;
						document.forms[0].action=url;
						document.forms[0].submit();
					 }else{
					 	return;
					 }
				 }else if(optiontype=='E'){	 			 
					    var url = "userSearchResult.do?method=fwdtoEdit&&userId="+recordcd;
						document.forms[0].action=url;
						document.forms[0].submit();
					 
				 }
	 		 }
		    function clearData(){
		   document.forms[0].userName.value="";
		   document.forms[0].employeeId.value="";
		   document.forms[0].userType.value="";
		   document.forms[0].profileType.value="";
		   document.forms[0].unit.value="";
		   document.forms[0].status.value="";	   
	        }  
		  </script>
	</head>
	<body onload="document.forms[0].userName.focus();">
		<html:form action="/userSearchResult.do?method=searchResult">
		<%=ScreenUtilities.saearchHeader("user.title")%><table width="550" border="0" cellspacing="3" cellpadding="0">
                      <tr>
						<td colspan=4 align=center>
						<html:errors  bundle="error"/>
						</td>
			            </tr>
                      
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.username" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:text maxlength="12" property="userName" styleClass='TextField' size="12" tabindex="1"/>
							</td>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.employeeid" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:text maxlength="12" property="employeeId" styleClass='TextField' size="12" tabindex="2"/>
							</td>
						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.usertype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="userType" styleClass="TextField" style="width: 90px" tabindex="3">
									<html:option value="">
									Select One
								</html:option>
									<html:option value="U">
									User
								</html:option>
								<html:option value="N">
									Nodal Officer
								</html:option>
									<html:option value="A">
									Admin
								</html:option>
								</html:select>
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.profiletype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="profileType" styleClass="TextField" style="width: 90px" tabindex="4">
									<html:option value="">
									Select One
								</html:option>
									<html:option value="M">
									Member Level
								</html:option>
									<html:option value="U">
									Unit Level
								</html:option>
									<html:option value="R">
									RHQ Level
								</html:option>
									<html:option value="C">
									CHQ Level
								</html:option>
								</html:select>
							</td>

						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.unit" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
								<html:select property="unit" styleClass="TextField" style="width: 90px" tabindex="5">
									<html:option value="">
									Select One
								</html:option>
									<html:options name="UnitBean" property="unitCode" labelProperty="unitName" labelName="code" collection="unitList" />
								</html:select>
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.status" bundle="common" />
								:
							</td>
							<td width="20%" align="left" >
								<html:select property="status" styleClass="TextField" style="width: 90px" tabindex="6">
									<html:option value="" >
										Select One
									</html:option>
										<html:option value="Y">
										Active
									</html:option>
										<html:option value="N">
										In-Active
									</html:option>
								</html:select>
							</td>

						</tr>

						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>
                      <tr>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td align="left" valign="middle">&nbsp;</td>
                        <td height="30" align="left" valign="bottom"><table width="150" border="0" cellspacing="0" cellpadding="0">
                          <tr>

								
                            <td width="67"> <html:submit  tabindex="7" styleClass="butt" ><bean:message key="button.search" bundle="common"/></html:submit></td>
                            <td width="83">	<html:button tabindex="8" styleClass="butt" value="Clear" property="clear" onclick="clearData();"/></html></td>
                          </tr>
                        </table></td>
                      </tr>
                    </table>
                    <%=ScreenUtilities.searchSeperator()%>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="37%" height="29" align="left" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
                            <tr>       
                              <td width="40"><input tabindex="9" type="button" class="btn" value="Add" onclick="addUser();">
								</td>
                                <td width="40"><html:button property="Edit" tabindex="10" styleClass="btn" onclick="selectCheckboxes('E');"><bean:message key="button.edit" bundle="common"/></html:button></td>
                              <td width="60"><html:button property="Delete" tabindex="11" styleClass="btn" onclick="selectCheckboxes('D');"><bean:message key="button.delete" bundle="common"/></html:button></td>
                            </tr>
                          </table></td>
                          <td width="63%" align="left" valign="top">&nbsp;</td>
                        </tr>
                        <logic:present name="userList">
						<tr>
                          <td  align="left" valign="top">
                          		<input type="checkbox" name="deleteRecord" value="" style="display:none;" >
									<display:table cellpadding="0" cellspacing="0"  class="GridBorder" keepStatus="true" style="width:710px;height:50px" export="true" sort="list" name="userList" name="requestScope.userList" pagesize="5" id='searchData' requestURI="/userSearchResult.do?method=searchResult">
										<display:setProperty name="export.amount" value="list" />
										<display:setProperty name="export.excel.filename" value="User.xls" />
										<display:setProperty name="export.pdf.filename" value="User.pdf" />
										<display:setProperty name="export.rtf.filename" value="User.rtf" />
										<display:column  media="html" style="width:10px" headerClass="GridHBg" class="GridLCells">
											<input type="radio" name="selectall" value='<%=((UserBean)pageContext.getAttribute("searchData")).getUserId()%>' />
										</display:column>
										<display:column property="userName" sortable="true" headerClass="GridLTHCells"  class="GridLTCells"  title="User Name" />
										<display:column property="employeeId" sortable="true" title="Employee Id" headerClass="GridLTHCells"  class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator" />
										<display:column property="email" sortable="true" title="E-Mail " headerClass="GridLTHCells"  class="GridLTCells"  decorator="com.epis.utilities.StringFormatDecorator" />
										<display:column property="unitName" sortable="true" headerClass="GridLTHCells"  class="GridLTCells"  title="Unit" decorator="com.epis.utilities.StringFormatDecorator" />
										<display:column property="userType" sortable="true" title="User Type" headerClass="GridLTHCells"  class="GridLTCells" />
										<display:column property="profileType" sortable="true" title="Profile " headerClass="GridLTHCells"  class="GridLTCells"  />
										<display:column property="status" sortable="true" title="Status" headerClass="GridLTHCells"  class="GridLTCells"  />
										
									</display:table>                        
					       </td></tr>
		   	              </logic:present>                       
                    </table><%=ScreenUtilities.searchFooter()%>                  
		</html:form>
	</body>
</html>
