<%@ page language="java" import="com.epis.info.login.LoginInfo,java.util.Date" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'Advancesmenu.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" /> 
	<link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ddaccordion.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/ddaccordioninit.js"></script>
	<link href="<%=basePath%>css/leftmenu.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="15" cellpadding="0">
      <tr>
        <td width="198" align="left" valign="top"><table width="198" border="0" cellspacing="0" cellpadding="0">
          
          <tr>
            <td><table width="198" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>images/crvM.gif">
                <tr>
                  <td height="9" valign="top"><img src="<%=basePath%>images/crvT.gif" width="198" height="5" /></td>
                </tr>
                <tr>
                  <td align="center" valign="top"><table width="180" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><table width="180" height="49" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>images/userID.gif">
                          <tr>
                            <td align="center" valign="middle"><table width="164" border="0" cellspacing="3" cellpadding="0">
                                <tr>
                                  <td width="45" align="right" valign="middle" class="userID">User ID:</td>
                                  <td width="110" align="left" valign="middle" class="userID"><%=((LoginInfo)session.getAttribute("user")).getUserName()%></td>
                                </tr>
                                <tr>
                                  
                                  <%
                                  Date date=new Date(session.getCreationTime());                                  
                                  String time=date.toLocaleString();
                                  %>
                                  <td align="left" valign="middle" class="userID" colspan=2><%=time%></td>
                                </tr>
                            </table></td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td><img src="<%=basePath%>images/spacer.gif" width="8" height="8" /></td>
                      </tr>
                      <tr>
                        <td class="menuhead"><span>Menu</span></td>
                      </tr>
                      <tr>
                        <td align="left" valign="top"><div class="glossymenu"> <a class="menuitem submenuheader" href="javascript:void(0);">Masters</a>
                          <div class="submenu">
                            <ul>                              
							   <li><a href="/searchSM.do?method=searchSM" target="leftbody">Sub Module </a></li> 
							    <li><a href="/actionScreen.do?method=searchScreen" target="leftbody">Screen Details </a></li> 
							    <li><a href="/searchOption.do?method=searchOption" target="leftbody">Profile Options </a></li> 
							    <li><a href="/showProfileOptions.do?method=showProfileOptions" target="leftbody">Profile </a></li> 
							    <li  ><a href="/searchApproval.do?method=searchApproval" target="leftbody">Approvals </a></li>	
							    <li style="border-bottom-color:#FFFFFF;" ><a href="/accessRights.do?method=fwdtoAccessRights" target="leftbody">Access Rights</a></li>						     
                            </ul>
                          </div> </div></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="8" valign="bottom"><img src="<%=basePath%>images/crvB.gif" width="198" height="5" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
 </table>
 </body>
</html>
