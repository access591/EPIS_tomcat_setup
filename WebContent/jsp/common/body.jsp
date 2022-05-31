
<%@ page language="java" import="com.epis.info.login.LoginInfo" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
String module=request.getParameter("mod");

String profile="M";
LoginInfo user=(LoginInfo)session.getAttribute("user");
if(user!=null){
	profile=user.getProfile();
}
String mpath="/menuheader.do?module="+module;
String path = "";
String bpath="/jsp/common/leftbody.jsp?";
if("NP".equals(request.getParameter("prf"))){
    profile=module;
    path = "/commonMenue.do?method=showMenue&mod="+profile;
    if("CB".equals(module)){
    	bpath = "/Notifications.do?method=getNotifications";
    }else if("LA".equals(module)){
    	bpath = "/lanotifications.do?method=getNotifications";
    }
}else{
	if("N".equals(user.getUserType())){
			bpath="/jsp/common/NodalOfficerMenu.jsp?";
	}else{
		if("M".equals(profile)){		
			bpath="/nomineesearchservlet.do?method=nomineeApprovalStatus";
		}else if("U".equals(profile)){		
			bpath="/jsp/common/uleftbody.jsp?";
		}else if("R".equals(profile)){	
			bpath="/jsp/common/rleftbody.jsp?";
		}else if("C".equals(profile)){		
			bpath="/jsp/common/cleftbody.jsp?";
		}
	}
	if("S".equals(profile)){		
		path="/super.do?";
	}else{
	path = "/Profile.do?method=getProfile&&profile="+profile;
	}
} 
 
//System.out.println("hi anil::"+profile);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'body.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css" />
    
  </head>
  <BODY leftMargin="0" topMargin="0" >
		<form name="complaintAlert" action="">
			<TABLE align="left" cellSpacing="0" cellPadding="0" width="100%"  height="100%" border="0" >
				<TBODY>
					<TR>
						<TD  valign="top" >
							<TABLE  height=100% cellpadding=0 cellspacing=0 border=0 width=100%>					 
								<tr><td colspan=2 height=30><jsp:include page="<%=mpath%>" flush="true"/></td></tr>
								<Tr>
									<TD  width=23% >
										<IFrame name='menu' id='menu' src="<%=path+"&&sessionId="+session.getId()%>" width="100%" height="100%" scrolling="auto" frameborder=0 ALLOWTRANSPARENCY=TRUE>
										</IFrame>
									</TD>
									<Td   height="100%" align=left >
										<IFrame name='leftbody' id='leftbody' src="<%=bpath+"&&sessionId="+session.getId()%>" width="100%" height="100%" scrolling="auto" frameborder=0 ALLOWTRANSPARENCY=TRUE>
										</IFrame>
									</Td>
								</Tr>	
								<tr>
							    <td align="left" valign="top" colspan=2><table width="100%" border="0" cellpadding="0" cellspacing="0" class="footer">
							      <tr>
							        <td width="50%" align="left" class="footer">© 2010 Navayuga Infotech, All Rights Reserved.</td>
							        <td width="50%" align="right" class="footer">Designed &amp; Developed By: Navayuga Infotech</td>
							      </tr>
							    </table></td>
							  </tr>			
							</Table>							
						</Td>
					</TR>
				</TBODY>
			</TABLE>
		</form>
  </BODY>
</html>
