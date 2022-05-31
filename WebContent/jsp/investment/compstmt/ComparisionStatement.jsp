<%@ page language="java" import="java.util.*,com.epis.bean.investment.QuotationBean,com.epis.utilities.ScreenUtilities" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.epis.action.investment.ArrangersAction" %>
<%@ page import="com.epis.utilities.StringUtility" %>
<%@ page import="java.util.SortedSet" %>
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
	<html:form action="/addcomparative.do" >	
	<%=ScreenUtilities.screenHeader("com.comstmt")%>
		<table width="550" border=0 align="center" >
			<tr>
             <td valign=middle align=center height=70>
                  <table border=0>
                      <tr>
                      <td align=center class="tableTextBold"><font size=4>
                       <bean:message key="com.aai" bundle="common" /> 
                     </b> </td>
                      </tr>
                   </table>
              </td>
              </tr>
              <tr>
             <td valign=top height="100%" align=center  >
                  <table width=700  border=0>
                      <tr>
                      <td align=right class="tableTextBold">
                       <bean:message key="com.rate" bundle="common" /> :
                      </td>
                      <td align=left class="tableText">
                       <%=request.getParameter("letterNo")%>
                      </td>
                      <td align=right class="tableTextBold">
                        <bean:message key="com.date" bundle="common" />:
                      </td>
                       <td  align=left class="tableText">
                       <%=request.getParameter("opendate")%>
                      </td>
                      </tr>
                   </table>
              </td>
              </tr>    <Tr><td>&nbsp;</td></tr>
              <logic:present name="compArray">
                        <tr>
                        <td valign=top>
                         <table width=100%  border=1  class="TableBorder" style="border-width:groove" cellpadding=2 cellspacing=0 >
                        <%  int arrSize = ((Integer)session.getAttribute("arrSize")).intValue();
                            int secSize = ((Integer)session.getAttribute("secSize")).intValue();
                            String[][] comp =(String[][])session.getAttribute("compArray");
                        for(int i=0;i<secSize;i++){
                        %>
                        <tr>
                        <%
                         	for(int j=0;j<arrSize;j++){
                         	%>
                         	<td  class='<%=(i==0)?"tableSideTextBold":"tableText"%>' rowspan='<%=(i==0&&(j==0 || j==1 || j==arrSize-1))?2:1%>'>
                         		<%=StringUtility.checknull(comp[i][j]).equals("")?"--":StringUtility.checknull(comp[i][j])%>                         		
                         	</td>                         	
                         	<%                         	
                         	}
                         	%></tr>                         	
                         	<%
                         	if(i==0){
                         	%>
                         	<TR>
                         	<%
                         		for(int k=2;k<arrSize-1;k++){
                         			%>
                         			<td class="tableSideTextBold"> rates(Rs.)/YTM% </td>                         			
                         			<%
                         		}
                         		%>
                         	</TR>
                         	<%
                         	}
                         }
						%>
               </table>    	            
                        </td>   
                        </tr>
                        	</logic:present> 
                        	<tr><td>&nbsp;</td></tr> 
                        <tr><td colspan=3 align=center>
                        <input type="button" class="butt" value="Report" onclick="javascript:window.open('<%=basePath%>/addcomparative.do?method=showCompatativeStmt&mode=report&letterNo=<%=request.getParameter("letterNo")%>&opendate=<%=request.getParameter("opendate")%>')" >
                        <input type="button" class="butt" name="Back" value="Back"  onclick="javascript:window.location.href='<%=basePath%>/searchcomparative.do?method=showComparativeAdd'" >
                        </td></tr> 
                 </td>       
              </tr>
       		</table>
				<%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>
