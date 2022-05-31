
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String logintype=(String)request.getAttribute("LoginType");
%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <base href="<%=basePath%>">
    
    <title>AAI - Employees PF/ Pension Information System</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/login.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/leftmenu.css" rel="stylesheet" type="text/css" />
   <script language="javascript" >
		    function show(){
		    var logintype = "<%=logintype%>";
		   
		    	
				if(logintype=='M'){
				
				document.getElementById('light1').style.display='block';
				document.getElementById('fade').style.display='block'
				}else if(logintype=='U'){
				
				document.getElementById('light2').style.display='block';
				document.getElementById('fade').style.display='block'
				}else if(logintype=='R'){
				
				document.getElementById('light3').style.display='block';
				document.getElementById('fade').style.display='block'
				}else if(logintype=='C'){
				
				document.getElementById('light4').style.display='block';
				document.getElementById('fade').style.display='block'
				}
		    }
		  
	</script>
  </head>
  
  <body onload="show();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" valign="top"><table width="100%" height="66" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>images/top_bg.gif">
      <tr>
        <td width="80%" align="left" valign="middle"><img src="<%=basePath%>images/title.gif" width="498" height="45" hspace="15" /></td>
        <td width="20%" align="right" valign="middle"><img src="<%=basePath%>images/epis_logo.gif" width="99" height="32" hspace="15" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top" class="menu_bg"><img src="<%=basePath%>images/spacer.gif" width="1" height="5" /></td>
      </tr>
      <tr>
        <td height="3" align="left" valign="top" class="line"><img src="<%=basePath%>images/spacer.gif" width="1" height="1" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="15" cellpadding="0">
      <tr>
        <td width="253" align="left" valign="top"><img src="<%=basePath%>images/img_support.gif" width="253" height="436" /></td>
        <td width="100%" align="center" valign="top"><table width="464" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="232" align="left" valign="top"><table width="223" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="middle" background="<%=basePath%>images/login_img1.gif"><table width="118" border="0" cellspacing="0" cellpadding="0" style="margin-left:10px">
                    <tr>
                      <td align="center" class="loginTxt">Member</td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img4.gif" width="223" height="50" /></td>
              </tr>
              <tr>
                <td align="left" valign="top" class="loginbg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="56" align="left" valign="top">Member provides the facility to know the Personnel and Financial Information of any AAI employee who has access permissions.</td>
                    </tr>
                    <tr>
                      <td align="right"><table border="0" cellpadding="0" cellspacing="0">
                        <tr><td><a href="javascript:void(0);" onclick = "document.getElementById('light1').style.display='block';document.getElementById('fade').style.display='block';document.forms[0].userId.focus();" class="login_butt">Login</a></td></tr></table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img3.gif" width="223" height="4" /></td>
              </tr>
            </table></td>
            <td width="232" align="right" valign="top"><table width="223" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="middle" background="<%=basePath%>images/login_img1.gif"><table width="118" border="0" cellspacing="0" cellpadding="0" style="margin-left:10px">
                    <tr>
                      <td align="center" class="loginTxt">Unit</td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img4.gif" width="223" height="50" /></td>
              </tr>
              <tr>
                <td align="left" valign="top" class="loginbg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="56" align="left" valign="top">Authorised AAI employees can login and maintain the unit level information and get the reports.</td>
                    </tr>
                    <tr>
                      <td align="right"><table border="0" cellpadding="0" cellspacing="0">
                        <tr><td><a href="javascript:void(0)" onclick = "document.getElementById('light2').style.display='block';document.getElementById('fade').style.display='block';document.forms[1].userId.focus();" class="login_butt">Login</a></td></tr></table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img3.gif" width="223" height="4" /></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="left" valign="top"><table width="223" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="middle" background="<%=basePath%>images/login_img1.gif"><table width="118" border="0" cellspacing="0" cellpadding="0" style="margin-left:10px">
                    <tr>
                      <td align="center" class="loginTxt">RHQ</td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img4.gif" width="223" height="50" /></td>
              </tr>
              <tr>
                <td align="left" valign="top" class="loginbg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="56" align="left" valign="top">Authorised AAI employees can login and maintain the Regional level information and get the reports.</td>
                    </tr>
                    <tr>
                      <td align="right"><table border="0" cellpadding="0" cellspacing="0">
                        <tr><td><a href="javascript:void(0)" onclick = "document.getElementById('light3').style.display='block';document.getElementById('fade').style.display='block';document.forms[2].userId.focus();" class="login_butt">Login</a></td></tr></table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="images/login_img3.gif" width="223" height="4" /></td>
              </tr>
            </table></td>
            <td align="right" valign="top"><table width="223" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="middle" background="<%=basePath%>images/login_img1.gif"><table width="118" border="0" cellspacing="0" cellpadding="0" style="margin-left:10px">
                    <tr>
                      <td align="center" class="loginTxt">CHQ</td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td align="left" valign="top"><img src="<%=basePath%>images/login_img4.gif" width="223" height="50" /></td>
              </tr>
              <tr>
                <td align="left" valign="top" class="loginbg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="56" align="left" valign="top">Authorised AAI employees can login and maintain the CHQ level information and get the reports.</td>
                    </tr>
                    <tr>
                      <td align="right"><table border="0" cellpadding="0" cellspacing="0">
                        <tr><td><a href="javascript:void(0)" onclick = "document.getElementById('light4').style.display='block';document.getElementById('fade').style.display='block';document.forms[3].userId.focus();" class="login_butt">Login</a></td></tr></table></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td><img src="<%=basePath%>images/login_img3.gif" width="223" height="4" /></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="footer" >
      <tr>
        <td width="50%" align="left" class="footer">© 2010 Navayuga Infotech, All Rights Reserved.</td>
        <td width="50%" align="right" class="footer">Designed &amp; Developed By: Navayuga Infotech</td>
      </tr>
    </table></td>
  </tr>
</table>
<div id="light1" class="white_content">
<div style="float:left; position:absolute; display:block; left: 100px; top: 120px;">
		<html:form  action="loginValidation.do?method=loginValidation&ltype=M"><table cellpadding="0" cellspacing="15" border="0">
				   <tr>
					<td  align=center colspan=2>
					<html:errors bundle="error" />
					</td>
				  </tr>
				  <tr>
					<td align="right" class="loginT">User Name:</td>
					<td align="left"><input type="text" name="userId" class="loginpaginput" /></td>
				  </tr>
				  <tr>
					<td align="right"  class="loginT">Password:</td>
					<td align="left"><input type="password" name="password" class="loginpaginput"/></td>
				  </tr>
				  <tr>
					<td align="right"></td>
					<td align="left">
					<label><input type="submit" name="Submit" value="Submit" class="button" /></label>
					</td>
				  </tr>
				</table></html:form>
  </div>
				<a href="javascript:void(0)" 
		  onclick = "document.getElementById('light1').style.display='none';document.getElementById('fade')
		  .style.display='none'" style="padding-left:435px"><img src="<%=basePath%>images/close.gif" border="0" alt="Close" /></a>
</div>
		  
<div id="light2" class="white_content_unit">
<div style="float:left; position:absolute; display:block; left: 100px; top: 120px;">
		<html:form  action="loginValidation.do?method=loginValidation&ltype=U"><table cellpadding="0" cellspacing="15" border="0">
				  <tr>
					<td  align=center colspan=2>
					<html:errors bundle="error"/>
					</td>
				  </tr>
				 <tr>
					<td align="right" class="loginT">User Name:</td>
					<td align="left"><input type="text" name="userId" class="loginpaginput" /></td>
				  </tr>
				  <tr>
					<td align="right"  class="loginT">Password:</td>
					<td align="left"><input type="password" name="password" class="loginpaginput"/></td>
				  </tr>
				  <tr>
					<td align="right"></td>
					<td align="left">
					<label><input type="submit" name="Submit" value="Submit" class="button" /></label>
					</td>
				  </tr>
				</table></html:form>
  </div>
				<a href="javascript:void(0)" 
		  onclick = "document.getElementById('light2').style.display='none';document.getElementById('fade')
		  .style.display='none'" style="padding-left:435px"><img src="<%=basePath%>images/close.gif" border="0" alt="Close" /></a>
</div>
		  
<div id="light3" class="white_content_rhq">
<div style="float:left; position:absolute; display:block; left: 100px; top: 120px;">
		<html:form  action="loginValidation.do?method=loginValidation&ltype=R"><table cellpadding="0" cellspacing="15" border="0">
				  <tr>
					<td  align=center colspan=2>
					<html:errors bundle="error"/>
					</td>
				  </tr>
				 <tr>
					<td align="right" class="loginT">User Name:</td>
					<td align="left"><input type="text" name="userId" class="loginpaginput" /></td>
				  </tr>
				  <tr>
					<td align="right"  class="loginT">Password:</td>
					<td align="left"><input type="password" name="password" class="loginpaginput"/></td>
				  </tr>
				  <tr>
					<td align="right"></td>
					<td align="left">
					<label><input type="submit" name="Submit" value="Submit" class="button" /></label>
					</td>
				  </tr>
				</table></html:form>
  </div>
				<a href="javascript:void(0)" 
		  onclick = "document.getElementById('light3').style.display='none';document.getElementById('fade')
		  .style.display='none'" style="padding-left:435px"><img src="<%=basePath%>images/close.gif" border="0" alt="Close" /></a>
</div>

<div id="light4" class="white_content_chq">
<div style="float:left; position:absolute; display:block; left: 100px; top: 120px;">
		<html:form  action="loginValidation.do?method=loginValidation&ltype=C"><table cellpadding="0" cellspacing="15" border="0">
				  <tr>
					<td  align=center colspan=2>
					<html:errors bundle="error"/>
					</td>
				  </tr>
				  <tr>
					<td align="right" class="loginT">User Name:</td>
					<td align="left"><input type="text" name="userId" class="loginpaginput" /></td>
				  </tr>
				  <tr>
					<td align="right"  class="loginT">Password:</td>
					<td align="left"><input type="password" name="password" class="loginpaginput"/></td>
				  </tr>
				  <tr>
					<td align="right"></td>
					<td align="left">
					<label><input type="submit" name="Submit" value="Submit" class="button" /></label>
					</td>
				  </tr>
				</table></html:form>
</div>
				<a href="javascript:void(0)" 
		  onclick = "document.getElementById('light4').style.display='none';document.getElementById('fade')
		  .style.display='none'" style="padding-left:435px"><img src="<%=basePath%>images/close.gif" border="0" alt="Close" /></a>
</div>
<div id="fade" class="black_overlay"></div>
</body>
</html>
