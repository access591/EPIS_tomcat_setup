<%@ page language="java" import="java.util.*,com.epis.bean.investment.QuotationBean,java.sql.*" %>
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
int cnt=0;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>" />		
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
	</head>
	<body background="body">
	<%com.epis.utilities.SQLHelper sql = new com.epis.utilities.SQLHelper();%>	
	<table width="70%" border=0 align="center" cellpadding="4">
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
							<th class="tblabel"><bean:message key="app.title" bundle="common" /> &nbsp; For The Letter Number:<bean:write name="quotationbean" property="letterNo"/> </th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp;</td>
							</tr>
						</table>						
						</td></tr>
				<tr>
					<td colspan=4 align=center>
						<html:errors />
					</td>
				</tr>
               
                <Tr><td>&nbsp;</td></tr>
                
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
                       
                
       	
	<logic:iterate id="approval" name="quotationbean" property="approvals" > 
	<logic:equal value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.QuotationAppBean" />
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			
	</logic:equal>
	<logic:notEqual value="0" name="approval" property="key">
		<bean:define id="invBean" name="approval" property="value" type="com.epis.bean.investment.QuotationAppBean" />
			<logic:equal value="A" name="invBean" property="approved">
				<tr>					
					<td  align=right nowrap>
						<b><bean:message key="jv.date" bundle="common" /></b>
						:					
						<bean:write property="date" name="invBean" />
					</td>
				</tr>
				<tr>
					<td  align="left" wrap>
						<bean:define id="rem1" name="invBean" property='remarks' type="String"></bean:define>
						<pre><%=sql.wrapTextArea(rem1,90)%></pre>
					</td>					
				</tr>
				<bean:define id="appCount1" name="quotationbean" property="appCount" type="java.lang.String"></bean:define>
				<logic:notEqual value="<%=appCount1%>" name="approval" property="key">
				<tr>
					<td  align=left nowrap>
						<b>	<bean:message key="proposal.subplease" bundle="common" /> : </b>
					</td>					
				</tr>
				</logic:notEqual>
				<tr>
					<td align="right">
						<table border=0>
							<tr>
								<td align="right" class="label" >	
									<img  src="<bean:write name="invBean" property="signPath"/>" />
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									(<bean:write name="invBean" property="displayName"/>)
								</td>										
							</tr>
							<tr>
								<td align="center" class="label" >	
									<bean:write name="invBean" property="designation"/>
								</td>										
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<hr style="height:1px; color: black" />
					</td>
				</tr>
			</logic:equal>	
		</logic:notEqual>		
</logic:iterate>
			</table>
		
	</body>
</html>

