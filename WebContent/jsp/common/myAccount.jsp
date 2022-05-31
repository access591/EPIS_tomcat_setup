<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>AAI - Admin - Masters - User New Screen</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="description" content="User New Screen"/>		
		<link href="css/style.css" rel="stylesheet" type="text/css" />	
		<link href="css/ezcalendar.css" rel="stylesheet" type="text/css" />
		
		
	</head>
	<body>
		<html:form action="/userSearchResult.do" method="POST"  enctype="multipart/form-data">
		<%=ScreenUtilities.screenHeader("login.myaccount")%>
					
			
				<table width="70%" border="0" align="center">
					<tbody>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
								<html:errors bundle="error" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.username" bundle="common" />:
							</td>
							<td width="20%" align="left">
							<bean:write property="userName" name="myaccount"/>

							</td>
							<td width="20%" class="tableTextBold" align="right" nowrap>
								<bean:message key="user.employeeid" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="employeeId" name="myaccount"/>
								
							</td>
						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.email" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="email" name="myaccount"/>
								
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.unit" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="unit" name="myaccount"/>
								
							</td>

						</tr>

						<tr>
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.usertype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="userType" name="myaccount"/>
								
							</td>

							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.profiletype" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="profileType" name="myaccount"/>
								
								
							</td>

						</tr>
						<tr>
			
						
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.modules" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="moduleNames" name="myaccount"/>					
																
							</td>
									
	
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="role.rolename" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="roleCd" name="myaccount"/>							
																
							</td>						
					
							<td class="tableTextBold" width="20%" align="right">
								<bean:message key="user.status" bundle="common" />
								:
							</td>
							<td width="20%" align="left">
							<bean:write property="status" name="myaccount"/>
								
							</td>

						</tr>
						<tr>
							<td class="tableTextBold" width="20%" align="right" nowrap>
								<bean:message key="user.expiredate" bundle="common" />
								:
							</td>
							<td width="20%" nowrap align="left">
							<bean:write property="expireDate" name="myaccount"/>
								
							</td>
							<td class="tableTextBold" width="20%" align="right">&nbsp;
								
							</td>
							<td width="20%" nowrap>&nbsp;
							
							</td>
							
						</tr>
						<tr>
							<td  align="center">
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								&nbsp;
							</td>
						</tr>

						<tr>
							<td colspan="4" align="center">
								<li><a href="/jsp/common/AccountResetPassword.jsp" target="leftbody">Change Password </a></li>  
							</td>
						</tr>
						
						
					</tbody>
				</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
