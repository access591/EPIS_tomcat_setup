
<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'CHQProfile.jsp' starting page</title>
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
    <td align="left" valign="top"><table width="100%" border="0" cellspacing="5" cellpadding="0">
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
			<!-- 	<tr><td align=left>User: John</td><tr>
				<tr><td align=left>Level: Member </td><tr> -->
                  <tr>
                    <td><img src="images/menu.gif" width="180" height="26" /></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top"><div class="glossymenu">
					 <a class="menuitem submenuheader" href="index.html">Profile </a>
                          <div class="submenu">
                            <ul>
							  <li><a href="<%=basePath%>jsp/profiles/chq/CPFRecoveriesUpload.jsp" target="leftbody">Monthly CPF Recoveries</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/CPFPaymentsUpload.jsp" target="leftbody">Monthly CPF Payments</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/SupplementaryDataUpload.jsp" target="leftbody">Suplementary Data</a></li>  
							  <li><a href="<%=basePath%>jsp/profiles/chq/OpeningBalanceEntry.jsp" target="leftbody">OB Entry</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/OpeningBalanceAdjustments.jsp" target="leftbody">OB Adjustments</a></li> 
							  <li><a href="<%=basePath%>jsp/profiles/chq/NewJoinessInfo.jsp" target="leftbody">Transfer In/Out cases, New Joined, Retired, Resigned & Death Cases</a></li>
							  <li><a href="<%=basePath%>jsp/profiles/chq/AdvanceRequestSearch.jsp" target="leftbody">Advances</a></li>
							  <li><a href="<%=basePath%>jsp/profiles/chq/PFWRequestSearch.jsp" target="leftbody">PFW</a></li>
							  <li><a href="<%=basePath%>jsp/profiles/chq/FinalSettlement.jsp" target="leftbody">Final Settlement</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/TransferOfPF.jsp" target="leftbody">Transfer of PF</a></li>
                              <li><a href="Arrangers.html" target="leftbody">Sanction of Advance / PFW</a></li>
							  <li><a href="Arrangers.html" target="leftbody">Disbursement of Advance/PFW/Final Settlement</a></li>
							  <li style="border-bottom-color:#FFFFFF;" ><a href="InvestmentRatio.html" target="leftbody">Processing / recommendation of Final settlement cases</a></li>
                            </ul>
                          </div>	
					<a class="menuitem submenuheader" href="index.html">Reports </a>
                          <div class="submenu">
                            <ul>
                              <li><a href="<%=basePath%>jsp/profiles/chq/GeneratePFCard.jsp" target="leftbody">PF Card</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/GenerateForm2.jsp" target="leftbody">Form-2</a></li>
                              <li><a href="<%=basePath%>jsp/profiles/chq/GenerateForm9.jsp" target="leftbody">Form-9</a></li>
							  <li><a href="<%=basePath%>jsp/profiles/chq/GenerateForm10C.jsp" target="leftbody">Form-10C</a></li>
							  <li><a href="<%=basePath%>jsp/profiles/chq/GenerateForm10D.jsp" target="leftbody">Form-10D</a></li>					  
                              <li><a href="Arrangers.html" target="leftbody">OB Entry Report</a></li>
							  <li><a href="Arrangers.html" target="leftbody">OB Adjustments Report</a></li>
							  <li><a href="Arrangers.html" target="leftbody">CPF Recovery Schedule - Journal</a></li>
							  <li><a href="Arrangers.html" target="leftbody">Advances / PFW -Journal</a></li>
							  <li><a href="Arrangers.html" target="leftbody">Missing / New employees deviation statement </a></li>
							  <li><a href="Arrangers.html" target="leftbody">Error Reports During Data Uploading</a></li>
                              <li style="border-bottom-color:#FFFFFF;" ><a href="InvestmentRatio.html" target="leftbody">Upload statistics</a></li>
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
