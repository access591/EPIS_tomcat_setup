
<%@ page language="java" import="com.epis.info.login.LoginInfo" pageEncoding="UTF-8"%>
<%@ page   import="com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" /> 
     <script language="javascript">
		function validate(){
		    
		    if(document.forms[0].newPassword.value==""){
			     alert("New Password is Mandatory");
			     document.forms[0].newPassword.focus();
			     return false;
		    }
		    if(document.forms[0].oldPassword.value==""){
		   		 alert("Old Password Mandatory");
			     document.forms[0].oldPassword.focus();
			     return false;
		     }
		      if(document.forms[0].confirmPassword.value==""){
		   		 alert("Confirm Password Mandatory");
			     document.forms[0].confirmPassword.focus();
			     return false;
		     }
		     if(document.forms[0].newPassword.value!= document.forms[0].confirmPassword.value){
		   		 alert("New Password and Confirm Password should be same.");
			     document.forms[0].confirmPassword.focus();
			     return false;
		     }
		    return true; 
		 }
		function clearData(){
       		 document.forms[0].oldPassword.value="";
        	 document.forms[0].newPassword.value="";
        	 document.forms[0].confirmPassword.value="";
        	
  		}
		
		</script>
    
  </head>
  
  <body onload="document.forms[0].oldPassword.focus();">
  <html:form action="changePassword.do?method=changeAccountPassword" onsubmit="return validate();">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
    <table  height="450" width="400" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center" valign="top">
    <%=ScreenUtilities.screenHeader("changepassword.title")%>
    <table width="100%" height="40" border="0" cellpadding="3" cellspacing="3" >
		<tr>
					<td  align=center colspan=2><html:errors bundle="error" /></td>
				</tr>
		  <tr>           
            <td width="40%" align="right" class="tableTextBold"><bean:message bundle="common" key="login.userid"/></td>
            <td width="60%" align="left" ><%=((LoginInfo)session.getAttribute("user")).getUserName()%> <input type=hidden name="userId" value='<%=((LoginInfo)session.getAttribute("user")).getUserName()%>'>     </td>           
          </tr>
          <tr>           
            <td width="40%" align="right" class="tableTextBold"><bean:message bundle="common" key="login.oldpassword"/> </td>
            <td width="60%" align="left" ><input type=password name="oldPassword" class="TextField"></td>           
          </tr>
          <tr>           
            <td width="40%" align="right" class="tableTextBold" ><bean:message bundle="common" key="login.newpassword"/></td>
            <td width="60%" align="left" ><input type=password name="newPassword" class="TextField"></td>           
          </tr>
          <tr>           
            <td width="40%" align="right" class="tableTextBold" ><bean:message bundle="common" key="login.confirmpassword"/></td>
            <td width="60%" align="left" ><input type=password name="confirmPassword" class="TextField"></td>           
          </tr>
           <tr>                        
              <td width="50%" align="center"  colspan=2>
              <html:submit styleClass="butt"><bean:message bundle="common" key="button.save"/></html:submit></td>
               
          </tr>
    		   </table>
    <%=ScreenUtilities.searchFooter()%> 
    </td></tr></table>
    </td>
      </tr>
</table>
</html:form>
  </body>
</html>
