
<%@ page language="java" import="com.epis.bean.rpfc.NomineeStatusBean,com.epis.utilities.*,java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.struts.actions.IncludeAction" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'leftbody.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />  
    <link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
    <style type="text/css"> body {background-color: transparent} </style>
  </head>
  
  <body><br><FIELDSET ><LEGEND class="epis">CPF Advances Request Status </LEGEND>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td>&nbsp;</td></tr>
   <tr><td class="epis">Your CPF Advance reuest is verified and forwarded to RHQ.</td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
       
</table>
</FIELDSET >
<br><FIELDSET ><LEGEND class="epis">Part Final Withdrawl Request Status</LEGEND>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td>&nbsp;</td></tr>
   <tr><td class="epis">No Requests in Pending</td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
  
  

     
</table>
</FIELDSET >

<br><FIELDSET ><LEGEND class="epis">Nominee Request Status</LEGEND>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr><td>&nbsp;</td></tr>
  <tr><td><table width="700" border="0" class="GridBorder" cellspacing="0" cellpadding="0">
			<%
				ArrayList nomineeList=(ArrayList)request.getAttribute("requestStatus");
				int count=nomineeList.size();
				if(count==0)
				{
			%>
			<tr ><th align="center" width="100%" class="norecords" colspan=7>No Requests in Pending</th></tr>
			<%
			}
			else
			{
			%>
			
			<tr class="GridHBg">
				<td  class="GridLTCells" nowrap>
					 PF ID  
				</td>
				
				<td  class="GridLTCells" nowrap>
					 Nominee Name
				</td>
				<td  class="GridLTCells" nowrap>
					Nominee DOB.
				</td>
				<td  class="GridLTCells" nowrap>
					Nominee Relation
				</td>
				
				<td  class="GridLTCells" nowrap>
					Name of The guardian
				</td>
				<td  class="GridLTCells" nowrap>
					Request Date
				</td>
				<td  class="GridLTCells" nowrap>
					Status.
				</td>
				
				
				
					
							
			</tr>
			
			
			<%
				String claz="";
			    for(int i=0; i<nomineeList.size(); i++)
			    {
			    NomineeStatusBean empbean=(NomineeStatusBean)nomineeList.get(i);
			     claz = (i%2 != 0) ? "GridRowBg2" : "GridRowBg1"; 
			     %>
			     <tr class="<%=claz%>" >
			     <td class="GridLTCells"><%=empbean.getPensionNo()%></td>
			     <td class="GridLTCells"><%=empbean.getNomineeName()%></td>
			     <td class="GridLTCells"><%=empbean.getNomineeDOB()%></td>
			     <td class="GridLTCells"><%=empbean.getNomineeRelation()%></td>
			     <td class="GridLTCells"><%=empbean.getNameoftheGuardian()%></td>
			     <td class="GridLTCells"><%=empbean.getRequestDate()%></td>
			     <td class="GridLTCells"><%=empbean.getStatus()%></td>
			     
			     
			     
			     
			     
			     <%
			     
			    }
			}
			%>
			
			</table></td></tr>
   <tr><td class="epis">

   </td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
   <tr><td>&nbsp;</td></tr>
  
  

     
</table>
</FIELDSET >

  </body>
</html>
