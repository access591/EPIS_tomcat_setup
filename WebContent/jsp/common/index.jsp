
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <base href="<%=basePath%>">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" /> 
   
  </head>

  <body>

  <html:form action="loginValidation.do?method=loginValidation">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr>
    <td align="left" valign="top"><table width="100%" height="66" border="0" cellpadding="0" cellspacing="0" background="images/top_bg.gif">
      <tr>
        <td width="80%" align="left" valign="middle"><img src="images/title.gif" width="498" height="45" hspace="15" /></td>
        <td width="20%" align="right" valign="middle"><img src="images/epis_logo.gif" width="99" height="32" hspace="15" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top"><table width="100%" height="32" border="0" cellpadding="0" cellspacing="0" background="images/menu_bg.gif">
          <tr>
            <td width="15" align="left" valign="middle"><img src="images/spacer.gif" / width="15px" /></td>
            <td width="95%" align="left" ><font color=#eef2f6><marquee scrolldelay='250' direction='left' class="epis"><bean:message bundle="common" key="login.welcome"/></marquee></font></td>
           
            <td width="15" align="left" valign="middle"><img src="images/spacer.gif" / width="15px" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="3" align="left" valign="top" class="line"><img src="images/spacer.gif" width="1" height="1" /></td>
      </tr>
    </table></td>
  </tr>
   
   <tr>
    <td align="left" valign="top"><table width="100%" height="450" border="0" cellspacing="0" cellpadding="0">
   
      <tr>
        <td align="center" valign="middle"><table width="30%" height="60" border="0" cellpadding="3" cellspacing="3" >
           <tr>
					<td  align=center colspan=2>
					<html:errors bundle="error"/>
					</td>
				</tr>
				</Table>
				<table width="30%" height="60" border="1" cellpadding="3" cellspacing="3" >
          <tr>           
            <td  align="center" class="aims" colspan=2><bean:message bundle="common" key="login.userlogin"/></td>
          </tr>
          <tr>           
            <td width="40%" align="right" class="epis"><bean:message bundle="common" key="login.userid"/></td>
            <td width="60%" align="left" ><input type=text name="userId" class="textbox" ></td>           
          </tr>
          <tr>           
            <td width="40%" align="right" class="epis" ><bean:message bundle="common" key="login.password"/></td>
            <td width="60%" align="left" ><input type=password name="password" class="textbox"></td>           
          </tr>
           <tr>         
              <td width="50%" align="center" class="epis" colspan=2><html:submit><bean:message bundle="common" key="button.signin"/></html:submit></td>
          </tr>
        </table></td>
      </tr>
      
  
  
</table>
</html:form>
  </body>
</html:html>
