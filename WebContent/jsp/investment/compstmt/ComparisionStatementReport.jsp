<%@ page language="java" import="java.util.*,com.epis.bean.investment.QuotationBean" %>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		
		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body >
	<table width="70%" border=0 align="center" >
		<tbody >
			
             <tr>
             <td valign=bottom align=center height=100>
                  <table border=0>
                      <tr>
							<td rowspan="5" >
								<img src="<%=basePath%>images/logoani.gif" width="87" height="55" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
                   </table>
              </td>
              </tr>
               <tr>
					<td align="center" >
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="com.comstmt" bundle="common" /> &nbsp; Of Quotations Received From Arrangers For Ivestment Of Funds in &nbsp;&nbsp;<bean:write name="quotationbean" property="securityCd"/>&nbsp;Securities in <bean:write name="quotationbean" property="marketType"/> Market</th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>						
						</td></tr>
              <tr>
             <td valign=middle align=center  cellpadding=0 cellspacing=0>
                  <table width=700 border=0>
                      <tr>
                      <td align=left class="epis">
                       <bean:message key="com.rate" bundle="common" /> <bean:write name="quotationbean" property="securityCd"/> Securities (Through Secondary <bean:write name="quotationbean" property="marketType"/> Market) 
                      </td>
                      <td  class="episdata">
                       &nbsp;
                      </td>
                      <td align=right class="epis">
                        <bean:message key="com.date" bundle="common" />:
                      </td>
                       <td  class="episdata">
                       <%=request.getParameter("opendate")%>
                      </td>
                      </tr>
                   </table>
              </td>
              </tr>    <Tr><td>&nbsp;</td></tr>
              <logic:present name="compArray">
                        <tr>
                        <td valign=top>
                         <table width=100% border=1 cellpadding=2 cellspacing=0 >
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
                         		int l=1;
                         		for(int k=2;k<arrSize-1;k++){
                         			%>
                         			<td class="tableSideTextBold" align="center">(<%=l++%>)<br> rates(Rs.)/YTM% </td>                         			
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
                       
                 </td>       
              </tr>
       	
			</tbody>
	</table>
	</body>
</html>
