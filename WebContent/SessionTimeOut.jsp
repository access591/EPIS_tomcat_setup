
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String target = request.getParameter("target");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <base href="<%=basePath%>">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" /> 
   <script language="JavaScript" type="text/JavaScript">
	
	javascript:window.history.forward(-1);
	
	function loading(){
		var target="<%=target%>";
		if(target=="T"){
		document.forms[0].action="/SessionTimeOut.jsp";
		document.forms[0].submit();
		}
	}

</script> 
  </head>

  <body onload="loading();">
<form target="_top">
  
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
            <td width="95%" align="left" ><font color=#eef2f6><marquee scrolldelay='250' direction='left' class="epis">Welcome To Employees Pension Information System.</marquee></font></td>
           
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
    <td align="left" valign="top">
  		<table  height="510" width="700" cellpadding="0" cellspacing="0" border=0>
                <tr height="40%">
                  <td align="left" valign="top" width="30%" >&nbsp;</td>
                  <td align="left" valign="top" width="40%">&nbsp;</td>
                  <td align="left" valign="top" width="30%">&nbsp;</td>
                </tr>
                <tr height="10%">
                  <td align="center" valign="top" width="30%" colspan=3 ><h2><font color='blue'>Session Expired !</font></h2></td>                  
                </tr>
                <tr >
                  <td align="center" valign="top" width="30%" colspan=3 ><a href="<%=basePath%>" style="color:Black;font-family:Verdana;font-size:14px;" >Re-Login</a>&nbsp;&nbsp;<a href="#" onclick="javascript: self.close ()" style="color:Black;font-family:Verdana;font-size:14px;">Close Window</a></td>                  
                </tr>
                <tr height="40%">
                  <td align="left" valign="top" width="30%">&nbsp;</td>
                  <td align="left" valign="top" width="40%">&nbsp;</td>
                  <td align="left" valign="top" width="30%">&nbsp;</td>
                </tr>
                
         </table>
    </td>
   </tr>
      
  
  
</table>
</form>
  </body>
</html:html>
