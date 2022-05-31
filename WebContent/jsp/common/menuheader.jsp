
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String module =request.getParameter("module");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>EPIS</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/tooltip.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/tooltip.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ddaccordion.js"> </script>     
	<link href="<%=basePath%>css/leftmenu.css" rel="stylesheet" type="text/css" />
	<SCRIPT type="text/javascript">
	
	function loadhelp(){
	
	    //alert("params"+params);
		var aURL ="<%=basePath%>EPISHelp/index.html";
		var aWinName="AAI";
		
	   var wOpen;
	   var sOptions;
	
	   sOptions = 'status=yes,menubar=yes,scrollbars=yes,resizable=yes,toolbar=yes';
	   sOptions = sOptions + ',width=' + (screen.availWidth - 10).toString();
	   sOptions = sOptions + ',height=' + (screen.availHeight - 122).toString();
	   sOptions = sOptions + ',screenX=0,screenY=0,left=0,top=0';
	
	   wOpen = window.open( '', aWinName, sOptions );
	   wOpen.location = aURL;
	   wOpen.focus();
	   wOpen.moveTo( 0, 0 );
	   wOpen.resizeTo( screen.availWidth, screen.availHeight );
	   

	}
	</script>
  </head>
  
  <body>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">    
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top"><table width="100%" height="32" border="0" cellpadding="0" cellspacing="0" background="images/menu_bg.gif">
          <tr>
            <td width="15" align="left" valign="middle"><img src="images/spacer.gif" / width="15px" /></td>
            <td width="80%" align="left" valign="middle"><div id="menu">
      <ul>
      
        <li class='<%="AM".equals(module)?"selected":""%>'>
<logic:match  value="AM" name="user"  property="modules">
       	<a href="<%=basePath%>body.do?prf=NP&mod=AM" target="body" >
</logic:match>
<logic:notMatch  value="AM" name="user"  property="modules">
		<a href="#" onmouseout="popUp(event,'t1')" onmouseover="popUp(event,'t1')" onclick="return false">
</logic:notMatch>
       	<bean:message bundle="common" key="m.admin"/></a></li>        
        <li class='<%="RC".equals(module)?"selected":""%>'>
<logic:match  value="RC" name="user"  property="modules">
       	<a href="<%=basePath%>body.do?prf=NP&mod=RC" target="body">
</logic:match>
<logic:notMatch  value="RC" name="user"  property="modules">
		<a href="#" onmouseout="popUp(event,'t1')" onmouseover="popUp(event,'t1')" onclick="return false">
</logic:notMatch>        
        <bean:message bundle="common" key="m.rpfc"/></a></li>
        <li class='<%="CB".equals(module)?"selected":""%>'>
<logic:match  value="CB" name="user"  property="modules">
       	<a href="<%=basePath%>body.do?prf=NP&mod=CB" target="body">
</logic:match>
<logic:notMatch  value="CB" name="user"  property="modules">
		<a href="#" onmouseout="popUp(event,'t1')" onmouseover="popUp(event,'t1')" onclick="return false">
</logic:notMatch>        
        <bean:message bundle="common" key="m.cashbook"/></a></li>
        <li class='<%="LA".equals(module)?"selected":""%>'>
<logic:match  value="LA" name="user"  property="modules">
       	<a href="<%=basePath%>body.do?prf=NP&mod=LA" target="body">
</logic:match>
<logic:notMatch  value="LA" name="user"  property="modules">
		<a href="#" onmouseout="popUp(event,'t1')" onmouseover="popUp(event,'t1')" onclick="return false">
</logic:notMatch>
        <bean:message bundle="common" key="m.advances"/></a></li>
        <li class='<%="IT".equals(module)?"selected":""%>'>
<logic:match  value="IT" name="user"  property="modules">
       	<a href="<%=basePath%>body.do?prf=NP&mod=IT" target="body">
</logic:match>
<logic:notMatch  value="IT" name="user"  property="modules">
		<a href="#" onmouseout="popUp(event,'t1')" onmouseover="popUp(event,'t1')" onclick="return false">
</logic:notMatch>        
        <bean:message bundle="common" key="m.investment"/></a></li>
		
      </ul>
    </div></td>
            <td width="20%" align="right" valign="middle"><table width="94" border="0" cellspacing="0" cellpadding="0">
            <div id="t1" class="tip"><bean:message bundle="common" key="login.accessdenied"/></div>
                <tr>
                  <td width="16" align="left" valign="top"><a href="<%=basePath%>home.do" target="_top"><img src="images/home.gif" alt="Home" width="16" height="16" border="0" /></a></td>
                  <td width="10" align="left" valign="top">&nbsp;</td>
                  <td width="16" align="left" valign="top"><a href="<%=basePath%>userSearchResult.do?method=myAccount" target="leftbody"><img src="images/myaccount.gif" alt="My Account" width="16" height="16" border="0" /></a></td>
                  <td width="10" align="left" valign="top">&nbsp;</td>
                  <td width="16" align="left" valign="top"><logic:match  value="N" name="user"  property="userType">
       <a href="javascript:loadhelp()"><img src="images/help.gif" alt="Help" width="16" height="16" border="0" /></a>
</logic:match>
<logic:notMatch  value="N" name="user"  property="userType">
		<a href="javascript:void(0);"><img src="images/help.gif" alt="Help" width="16" height="16" border="0" /></a>
</logic:notMatch></td>
                  <td width="10" align="left" valign="top">&nbsp;</td>
                  <td width="16" align="left" valign="top"><a href="<%=basePath%>logout.do?method=logout" target="_top"><img src="images/logout.gif" alt="Logout" width="16" height="16" border="0" /></a></td>
                </tr>
            </table></td>
            <td width="15" align="left" valign="middle"><img src="images/spacer.gif" / width="15px" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="3" align="left" valign="top" class="line"><img src="images/spacer.gif" width="1" height="1" /></td>
      </tr>
    </table></td>
  </tr>
  
</table>
  </body>
</html>
