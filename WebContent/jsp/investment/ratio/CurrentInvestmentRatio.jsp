<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>

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
	</head>
	<body >
	<html:form action="/searchRatio.do?method=searchRatio" >
	<%=ScreenUtilities.screenHeader("ratio.title")%>
		<table width="550" border=0 align="center" >
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					&nbsp;
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="ratio.valid" bundle="common"/>:
				</td><td width="20%"  align=left class="tableText">
					<bean:write property="validFrom" name="ratiobean" />
				</td>	
				<td width="20%" >
					&nbsp;
					
				</td>					
			</tr>
			<tr><td colspan=2>&nbsp;</td></tr>
			<tr>
				<td colspan=4 align=center>
                    <table border="1" width=70% cellpadding=0 cellspacing=0 bordercolor="#85BDEC" >
                          
                           <tr bgcolor="#85BDEC" >
                        		<td style="width: 300px;" class="tableSideTextBold" align=center> <bean:message key="ratio.category"  bundle="common"/></td>
                        		<td style="width: 150px;" class="tableSideTextBold" align=center><bean:message key="ratio.percent"  bundle="common"/></td>     
                          </tr>
                          <bean:define  id="current"  property="catList" name="ratiobean" />
                              	<logic:present name="current" >   
                              	 	<logic:iterate id="sec" name="current" >       
                                        <tr> 
                                        <td style="width: 145px;" class="tableTextBold" align=right>
                                        <bean:write name="sec" property="categoryCd"/></td>
                                        <td style="width: 150px;" align=center class="tableText">
                                        <bean:write name="sec" property="percentage"/>
                                        %</td>
                                        </tr>
                                     </logic:iterate>   
                                    </logic:present> 
                                    <tr> 
                                      <td  class="tableSideTextBold" align=left colspan=2><bean:message key="ratio.note" bundle="common"/>  </td>
                                   </tr>  
                      </table>
                   </td>
           </tr>
			<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center>
				<html:submit styleClass="butt" property="Back" value="Back" />
			</td>	
			</tr>
			</table>
	<%=ScreenUtilities.searchFooter()%></html:form>
	</body>
</html>
