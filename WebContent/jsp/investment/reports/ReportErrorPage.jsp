<%@ page language="java" import="com.epis.utilities.ScreenUtilities" %>

<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<SCRIPT Language="JavaScript">
		var winHandle="";
		function closeWin()
		{
	  		if (winHandle && winHandle.open && !winHandle.closed) winHandle.close();
		}
		</SCRIPT>
	</head>
	<body onUnload="closeWin()" >
	<html:form action="/investmentReports.do" >	
	<TABLE borderColor=#000066 cellSpacing=0 cellPadding=0 align=left width=760 bgColor=#ffffff border=1>
<TBODY>
<TR> 
    <TD bgColor=#003399 colSpan=4> 
      <TABLE height=20 cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY> 
        <TR> 
          <TD vAlign=center align=middle width=25 height="2">&nbsp;</TD>
          <TD width=710 class="twb" height="2">&nbsp; </TD>
          <TD vAlign=center align=middle width=25 height="2">&nbsp;</TD>
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>

	<TR>
		<TD vAlign=top width=0 bgColor=#eef2f6 colSpan=4 height=0>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
		<TBODY>
			<TR>
			<TD width=25 height=25>&nbsp;</TD>
			<TD width=710 height=25>&nbsp;</TD>
			<TD width=25 height=25>&nbsp;</TD>
			</TR>
	
			<TR>
			<TD width=25 height=25>&nbsp;</TD>
			<TD width=710 bgColor=#ffffff valign="middle" height="300" align=center>
			<table border=0 cellpadding=0 cellspacing=0 bgcolor="eef2f6" bordercolor="#000066">
              <tbody> 
              <tr> 
                <td valign=middle width=100 align="center"><img src="<%=basePath%>images/logoani.gif" width="75" height="42"></td>
                <td valign=top width=300 align="left"> 
                  <p>&nbsp;</p>
                  <p><strong><font color=#ffffff 
				 face=Verdana,Arial,Helvetica size=4><span class="tbb">ERROR</span></font></strong><span class="tbb"><br>
                    
                    </span><strong><font color=#ffff00 
      			face=Verdana,Arial,Helvetica size=2><span class="tbb"><font color="#FF0000">
                    <html:errors bundle="error" />
                    </p>
                  
                </td>
              </tr>
              </tbody>
            </table>
            <p>&nbsp;</p>
            </TD>
          <TD width=25>&nbsp;</TD>
        </TR>
	
	
	
	<TR>
		<TD width=25 height=25>&nbsp;</TD>
		<TD width=710 height=25>&nbsp;</TD>
		<TD width=25 height=25>&nbsp;</TD>
	</TR>
	</TBODY>
	</TABLE>
	</TD>
	</TR>
	
	

</TBODY>
</TABLE>
	
	</html:form>
	</body>
</html>
