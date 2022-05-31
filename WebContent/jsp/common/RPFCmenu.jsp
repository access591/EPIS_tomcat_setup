
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'RPFCmenu.jsp' starting page</title>
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
                            <td width="110" align="left" valign="middle" class="userID"><i>Srinivas</i></td>
                          </tr>
                          <tr>
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
                              <li><a href="TrustType.html" target="leftbody">Employee</a></li>
                              <li><a href="SecurityCategory.html" target="leftbody">Ceiling</a></li>
                              <li><a href="Arrangers.html" target="leftbody">Financial year</a></li>
                              <li style="border-bottom-color:#FFFFFF;" ><a href="InvestmentRatio.html" target="leftbody">Financial Details</a></li>
                            </ul>
                          </div>
					  <a class="menuitem submenuheader" href="index.html">Finance & Personal</a>
                          <div class="submenu">
                            <ul>
                              <li><a href="InitialProposal.html" target="leftbody">PF Id</a></li>
                              <li><a href="RequestForQuotation.html" target="leftbody">Import Finance Data</a></li>
                              <li><a href="SubmitQuotaion.html" target="leftbody">Pension Validation</a></li>                                     
							  <li style="border-bottom-color:#FFFFFF;"><a href="InvestmentApproval.html" target="leftbody">Arrear Finance Data Mapping</a></li>
                            </ul>
                          </div>
                      <a class="menuitem submenuheader" href="index.html">Reports</a>   
					  <div class="submenu">
                            <ul>
                              <li><a href="InitialProposal.html" target="leftbody">Form-3</a></li>
                              <li><a href="InitialProposal.html" target="leftbody">Form-3-PS</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-3-ALL</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-3-PFID</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-3-PS-PFID</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-4</a></li>							  
							  <li><a href="InitialProposal.html" target="leftbody">Form-5</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-6</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-6-ALL</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-6A</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-6B</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-7</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-7-PS</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-8-PS</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">PF Card</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Duplicate Form-3</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Employee Info-Death/Resigned/Retd.</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Comparative Statement</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">PF Contribution</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">Form-6 Comprehensive Report</a></li>
							  <li><a href="InitialProposal.html" target="leftbody">OB Process</a></li>
							  <li style="border-bottom-color:#FFFFFF;"><a href="InvestmentApproval.html" target="leftbody">Adj Bal Process</a></li>
                            </ul>
                          </div>
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
