
<%@ page language="java" import="com.epis.info.login.LoginInfo,java.util.Date" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MemberProfile.jsp' starting page</title>
    
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
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="5" cellpadding="0">
      <tr>
        <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="198" align="left" valign="top"><table width="198" border="0" cellpadding="0" cellspacing="0" background="images/crvM.gif">
              <tr>
                <td height="9" valign="top"><img src="images/crvT.gif" width="198" height="5" /></td>
              </tr>
				<tr>
                    <td align="center" valign="top"><table width="180" height="49" border="0" cellpadding="0" cellspacing="0" background="images/userID.gif">
                      <tr>
                        <td align="center" valign="middle"><table width="164" border="0" cellspacing="3" cellpadding="0">
                          <tr>
                            <!--<td width="45" align="right" valign="middle" class="userID"></td>     -->
                            <td width="110" align="left" valign="middle" class="userID"><i><%=((LoginInfo)session.getAttribute("user")).getUserName()%></i></td>
                          </tr>
                          <tr>
                            <!--<td align="right" valign="middle" class="userID" ></td>   -->
                            <td align="left" valign="middle" class="userID">Feb 02 2010 17:32:18</td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                   <tr>
                    <td height="5"><img src="images/spacer.gif" width="1" height="1" /></td>
                  </tr>
              <tr>
                <td align="center" valign="top"><table width="180" border="0" cellspacing="0" cellpadding="0">
			<!-- 	<tr><td align=left>User: John</td><tr>
				<tr><td align=left>Level: Member </td><tr> -->
                  <tr>
                    <td><img src="images/menu.gif" width="180" height="26" /></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top"><div class="glossymenu">
					<a class="menuitem submenuheader" href="index.html">Profile  </a>
                          <div class="submenu">
                            <ul>
                             <li><a href="<%=basePath%>jsp/profiles/member/GeneratePFCard.jsp" target="leftbody">My Profile</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/member/GenerateForm2.jsp" target="leftbody">Nominee Details</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/member/GeneratePFCard.jsp" target="leftbody">PF Card</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/member/GenerateForm2.jsp" target="leftbody">Form-2</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/member/GenerateForm9.jsp" target="leftbody">Form-9</a></li>
							    <li><a href="<%=basePath%>jsp/profiles/member/GenerateForm10C.jsp" target="leftbody">Form-10C</a></li>
								<li><a href="<%=basePath%>jsp/profiles/member/GenerateForm10D.jsp" target="leftbody">Form-10D</a></li>
								<li><a href="<%=basePath%>jsp/profiles/member/AdvanceRequestSearch.jsp" target="leftbody">Advances</a></li>
								<li><a href="<%=basePath%>jsp/profiles/member/PFWRequestSearch.jsp" target="leftbody">PFW</a></li>
								<li><a href="<%=basePath%>jsp/profiles/member/FinalSettlement.jsp" target="leftbody">Final Settlement</a></li>
                              <li style="border-bottom-color:#FFFFFF;" ><a href="<%=basePath%>jsp/profiles/member/TransferOfPF.jsp" target="leftbody">Transfer of PF</a></li>
                            </ul>
                          </div>					 
                                          
					  </div></td>
                  </tr>
                  
                </table></td>
              </tr>
              <tr>
                <td height="9" valign="bottom"><img src="images/crvB.gif" width="198" height="5" /></td>
              </tr>
            </table></td>
            <td width="100%" align="left" valign="top">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      
    </table></td>
  </tr>
</table>
  </body>
</html>
