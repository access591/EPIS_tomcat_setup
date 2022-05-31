<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="com.epis.services.admin.UserService" %>
<%@ page import="com.epis.bean.admin.Bean" %>
<%@ page import="com.epis.bean.admin.ScreenOptionsBean" %>
<%@ page import="com.epis.bean.admin.AccessRightsBean" %>
<%@ page import="com.epis.info.login.LoginInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.epis.services.admin.AccessRightsService" %>
<%@ page import="com.epis.utilities.Configurations" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
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
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CBG.js" ></SCRIPT>
		<style type="text/css"> body {background-color: transparent} </style>		
		<script type="text/javascript">
		
		var selectAll = new CheckBoxGroup();		
		
		selectAll.setControlBox("All");
		</script>
	</head>
	<%
	String moduleCds=StringUtility.checknull(request.getParameter("modules"));
	String userId=StringUtility.checknull(request.getParameter("UserId"));
	%>
	<body >
		<br>
			<table width="500"  align="center" border=0 cellpadding=3 cellspacing=0 bordercolor="#7F9DC5">
				<html:form action="/accessRights.do?method=edit">
					<tbody>
						
						<%  List userAccCodes=AccessRightsService.getInstance().getAccessRights(Configurations.getAccessRightsType(),userId);
						System.out.println("...userAccCodes."+userAccCodes.size());
							List moduleList=UserService.getInstance().getModuleList(moduleCds);
							if(moduleList!=null&&moduleList.size()>0){
							%>
							<tr >
							<td class="tableSideTextBold" align="left" colspan=4><input type="hidden" name="userName" value="<%=userId%>" /><input type="hidden" name="moduleCodes" value="<%=moduleCds%>" /><input type="checkbox" name="All" id="All" style="width:15px; height:15px" onClick="selectAll.check(this);">All								
							</td>
							
							
							</tr>
							<%
								for(int count=0;count<moduleList.size();count++){
								Bean mbean=(Bean)moduleList.get(count);
								if(mbean!=null){
								
								%>
								<tr>
									<td class="tableSideTextBold" align="left" colspan=4><script type="text/javascript">selectAll.addToGroup('<%=mbean.getCode()%>');</script><input type="checkbox" name="<%=mbean.getCode()%>" id="<%=mbean.getCode()%>" style="width:15px; height:15px" onClick="selectAll.check(this);"><%=mbean.getName()%>								
									</td>								
								</tr>
								<%
								List subModuleList=UserService.getInstance().getSubModuleList(mbean.getCode());
								if(subModuleList!=null && subModuleList.size()>0){
									for(int scount=0;scount<subModuleList.size();scount++){
										Bean msbean=(Bean)subModuleList.get(scount);
										if(msbean!=null){
									
										%>
										<tr>
											<td class="tableSideTextBold" align="left" >&nbsp;							
											</td>		
											<td class="tableSideTextBold" align="left" colspan=3><script type="text/javascript">selectAll.addToGroup('<%=msbean.getCode()%>');</script><input type="checkbox" name="<%=msbean.getCode()%>" id="<%=msbean.getCode()%>" style="width:15px; height:15px" onClick="selectAll.check(this);"><%=msbean.getName()%>								
											</td>								
										</tr>
										<%
										List screensList=UserService.getInstance().getScreensList(msbean.getCode(),userId);
										if(screensList!=null && screensList.size()>0){
										for(int sccount=0;sccount<screensList.size();sccount++){
											AccessRightsBean sbean=(AccessRightsBean)screensList.get(sccount);
											ArrayList screenOptionsList=null;
											if(sbean!=null){
												screenOptionsList=sbean.getScreenOptions();
											
									
										%>
										<tr>
											<td class="tableSideTextBold" align="left" width="30" >&nbsp;							
											</td>
											<td class="tableSideTextBold" align="left" width="30">&nbsp;							
											</td>	
											<%
											out.println("<script type='text/javascript'> var "+sbean.getScreenCode()+"Group = new CheckBoxGroup(); "+sbean.getScreenCode()+"Group.setControlBox('"+sbean.getScreenCode()+"'); "+sbean.getScreenCode()+"Group.setMasterBehavior('some');</script>");
											%>
											<td class="tableSideTextBold" align="left" colspan=2><script type="text/javascript">selectAll.addToGroup('<%=sbean.getScreenCode()%>');<%out.println(sbean.getScreenCode()+"Group.addToGroup('"+sbean.getScreenCode()+"'); ");%></script><input type="checkbox" name="<%=sbean.getScreenCode()%>" id="<%=sbean.getScreenCode()%>" style="width:15px; height:15px" onClick='selectAll.check(this);<%out.println(sbean.getScreenCode()+"Group.check(this);");%>' <%=userAccCodes.contains(sbean.getScreenCode())?"checked":""%>  ><%=sbean.getScreenName()%>								
											</td>	
																		
										</tr>
										<%
										
										if(screenOptionsList!=null){
										ScreenOptionsBean sobean=null;
										
											for(int socount=0;socount<screenOptionsList.size();socount++){
												sobean=(ScreenOptionsBean)screenOptionsList.get(socount);
											
											%>
										<tr>
											<td class="tableSideTextBold" align="left" width="30" >&nbsp;							
											</td>
											<td class="tableSideTextBold" align="left" width="30">&nbsp;							
											</td>	
											<td class="tableSideTextBold" align="left" width="30">&nbsp;							
											</td>
											
											<td class="tableSideTextBold" align="left" width="350">
											<script type="text/javascript">selectAll.addToGroup('<%=sobean.getOptionCode()%>');</script>
											<input type="checkbox" name="<%=sobean.getOptionCode()%>" id="<%=sobean.getOptionCode()%>" style="width:15px; height:15px" <%=userAccCodes.contains(sobean.getOptionCode())?"checked":""%> >	<%=sobean.getOptionName() %>						
											</td>	
																		
										</tr>
										<%
											
											
											
											
											}//end for
										
										}//end if
										
										
										}//end if 
									}
								}
										
										
										}
									}
								}
								}							
								
							}
							%>
							<tr>
							<td colspan="5" align="center"><html:submit styleClass="butt" >Save</html:submit>&nbsp;&nbsp;<html:reset styleClass="butt">Reset</html:reset>
								&nbsp;
							</td>
						    </tr>
							<%
							
							}
						
						%>
						
					</tbody>
				</html:form>
			</table>
	</body>
</html>
