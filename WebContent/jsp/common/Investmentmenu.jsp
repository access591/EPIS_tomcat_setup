
<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'menu.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" /> 
	<link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ddaccordion.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/ddaccordioninit.js"></script>
	<link href="<%=basePath%>css/leftmenu.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  
  <tr>
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="15" cellpadding="0">
      <tr>
        <td align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="198" align="left" valign="top"><table width="198" border="0" cellpadding="0" cellspacing="0" background="images/crvM.gif">
              <tr>
                <td height="9" valign="top"><img src="images/crvT.gif" width="198" height="5" /></td>
              </tr>
              <tr>
                    <td align="center" valign="top"><table width="180" height="49" border="0" cellpadding="0" cellspacing="0" background="images/userID.gif">
                      <tr>
                        <td align="center" valign="middle"><table width="164" border="0" cellspacing="3" cellpadding="0">
                          <tr>
                            <!--<td width="45" align="right" valign="middle" class="userID"></td>     -->
                            <td width="110" align="left" valign="middle" class="userID"><i>Srinivas</i></td>
                          </tr>
                          <tr>
                            <!--<td align="right" valign="middle" class="userID" ></td>   -->
                            <td align="left" valign="middle" class="userID">12th Jan 2010 10:05</td>
                          </tr>
                        </table></td>
                      </tr>
                    </table></td>
                  </tr>
                   <tr>
                    <td height="5"><img src="images/spacer.gif" width="1" height="1" /></td>
                  </tr>
              <tr>
                <td align="center" valign="top"><table width="180" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="images/menu.gif" width="180" height="26" /></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top"><div class="glossymenu">
					<a class="menuitem submenuheader" href="index.html">Masters</a>
                          <div class="submenu">
                            <ul>
                              <li><a href="/searchTrustType.do?method=searchTrustType" target="leftbody">Trust Type</a></li>
                              <li><a href="/searchSCategory.do?method=searchSCategory" target="leftbody">Security Category</a></li>
                              <li><a href="/searchArrangers.do?method=searchArrangers" target="leftbody">Arrangers</a></li>
                              <li><a href="/searchRatio.do?method=searchRatio" target="leftbody">Investment Ratio</a></li>
                            </ul>
                          </div>
					  <a class="menuitem submenuheader" href="index.html">Investment Procedure</a>
                          <div class="submenu">
                            <ul>
                              <li><a href="/searchProposal.do?method=searchInvestProposal" target="leftbody">Intial Proposal</a></li>
                              <li><a href="/searchQuotationRequests.do?method=searchQuotationRequests" target="leftbody">Request for Quotation</a></li>
                              <li><a href="/searchQuotation.do?method=searchQuotation" target="leftbody">Submit Quotation</a></li>
                              <li><a href="/actionYTM.do?method=searchYTM" target="leftbody">YTM Verification</a></li>
                              <li><a href="/searchcomparative.do?method=searchComparative" target="leftbody">Comparative Statement</a></li>
                              <li><a href="/searchshortlist.do?method=searchShortlist" target="leftbody">Shortlisting Arrangers</a></li>
							  <li><a href="/searchinvestapproval.do?method=searchInvestApproval" target="leftbody">Investment Approval</a></li>
                            </ul>
                          </div>
                      <a class="menuitem submenuheader" href="index.html">Reports</a>                       
					  </div></td>
                  </tr>
                  
                </table></td>
              </tr>
              <tr>
                <td height="9" valign="bottom"><img src="images/crvB.gif" width="198" height="5" /></td>
              </tr>
            </table></td>
            <td width="100%" align="left" valign="top">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      
    </table></td>
  </tr>
</table>
  </body>
</html>
