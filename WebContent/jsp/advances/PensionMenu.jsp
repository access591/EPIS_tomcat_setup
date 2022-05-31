
<%@page errorPage="error.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="bean" class="com.epis.services.rpfc.PensionService" scope="request">
	<jsp:setProperty name="bean" property="*" />
</jsp:useBean>

<%String userId = (String) session.getAttribute("userid");
            if (userId == null) {
                RequestDispatcher rd = request
                        .getRequestDispatcher("./PensionIndex.jsp");
                rd.forward(request, response);
            }

            %>

<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<style type="text/css">
<!--

.text {
	font: normal 12px Arial, Helvetica, sans-serif;
	color: #E4E4E4;
}
A.link {
	font: bold 12px Arial, Helvetica, sans-serif;
	color:#FFFFFF;
	text-decoration:none;
}
A.link:hover {
	color: #FFCC00;
	font: bold 12px Arial, Helvetica, sans-serif;
	text-decoration:none;
}
.box {
	background-image: url(./view/images/table_top.gif);
	background-repeat: no-repeat;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	color: #FFFFFF;
	font-size: 12px;
}
.head2 {
	background-image: url(./view/images/head_img.gif);
	background-repeat: no-repeat;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	color: #FFFFFF;
	font-size: 12px;
}
.line {
	border-right: 1px solid #233F53;
	border-bottom: 1px solid #233F53;
	border-left: 1px solid #233F53;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
}
.textfld {
	background-color:#FFFFFF;
	border:1px solid #7F9DB9;
	width:150px;
	font-family: Arial, Verdana, Tahoma, Helvetica, Arial, sans-serif;
	font-size: 12px;
	color: #333333;
}
.textfld1 {
	width:154px;
	font-family: Arial, Verdana, Tahoma, Helvetica, Arial, sans-serif;
	font-size: 12px;
	color: #333333;
}
.button {
	height: 23px;
	width: 68px;
	border: 1px none #333333;
	background-image: url(images/button.gif);
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	color: #FFFFFF;
}
-->
</style>
	<head>
		<TITLE>AAI</TITLE>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>view/css/ddlevelsmenu-base.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>view/css/ddlevelsmenu-topbar.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>view/css/ddlevelsmenu-sidebar.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>view/css/aai.css">
		<script type="text/javascript" src="<%=basePath%>view/scripts/ddlevelsmenu.js">
		</script>
		<script type="text/javascript">
		<%//if (session.getAttribute("usertype").equals("Admin")) {%>
			ddlevelsmenu.setup("ddtopmenubar", "topbar",'<%=basePath%>') //ddlevelsmenu.setup("mainmenuid", "topbar|sidebar")
			<%//}%>
		</script>
	</head>
	<body class="BodyBackground">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
			<%//if (session.getAttribute("usertype").equals("Admin")) {

                %>
				<td align="left" valign="top">
	
					<table width="100%" cellpadding="0" cellspacing="0" >
						<tr>
							<td>
								<div id="ddtopmenubar" class="mattblackmenu"  style="height:42px;background-image: url(<%=basePath%>view/images/navbg.gif)" >
									<ul>
										<li style="background-image: url(<%=basePath%>view/images/img3.gif)">
											<a href="#" rel="ddsubmenu1" target="mainbody1">PFW / CPF Advance</a>
										</li>
										<li style="background-image: url(<%=basePath%>view/images/img3.gif)">
											<a href="#" rel="ddsubmenu2" target="mainbody1">Approvals</a>
										</li>
										<li style="background-image: url(<%=basePath%>view/images/img3.gif)">
											<a href="#" rel="ddsubmenu3" target="mainbody1">Final Settlement</a>
										</li>
										<li style="background-image: url(<%=basePath%>view/images/img3.gif)">
											<a href="#" rel="ddsubmenu4" target="mainbody1">Reports</a>
										</li>
									</ul>
								</div>
								<!--Top Drop Down Persional Menu -->
								<ul id="ddsubmenu1" class="ddsubmenustyle">
									<li>
										<a href="#">Masters</a>
										<ul>
							
											<li>
												<a href="#">Advances/PFW</a>
												<ul>
													<li>
														<a href="<%=basePath%>loadAdvance.do?method=loadAdvanceForm" >New</a>
													</li>
													<li>
														<a href="<%=basePath%>advanceSearch.do?method=loadAdvanceSearchForm">Search</a>
													</li>
												

												</ul>
											</li>
											<li>
												<a href="#">Verification</a>
												<ul>
													<li>
														<a href="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form2" >PFW Part-II Verification</a>
													</li>
													<li>
														<a href="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form3" >PFW Part-III Verification</a>
													</li>
													<li>
														<a href="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=form4" >PFW Part-IV Verification</a>
													</li>
													<li>
														<a href="<%=basePath%>loadApproval.do?method=loadPFWApprovalSearch&flag=CPFVerification" >Advance Verification</a>
													</li>

												</ul>
											</li>
										  </ul>
										  </li>
								</ul>
								<ul id="ddsubmenu2" class="ddsubmenustyle">	
											<li>
												<a href="#">Approvals</a>
												<ul>
													<li>
														<a href="<%=basePath%>loadAdvanceCPFForm.do?method=loadCPFFormApproval" >CPF Final Approval</a>
													</li>
													<li>
														<a href="<%=basePath%>loadAdvanceForm4.do?method=loadForm4Approval" >PFW Final Approval</a>
													</li>
												

												</ul>
											</li>
								</ul>
								<ul id="ddsubmenu3" class="ddsubmenustyle">	
										    			<li>
															<a href="#">Final Settlement</a>
															<ul>
																<li>
																<a href="<%=basePath%>loadNoteSheet.do?method=loadNoteSheet" >New</a>
																</li>											
																<li>
																	<a href="<%=basePath%>noteSheetSearch.do?method=loadNoteSheetSearchForm">Search</a>
																</li>												
															</ul>
												</li>
											
										</ul>
										
									</li>
								</ul>
								<ul id="ddsubmenu4" class="ddsubmenustyle">	
									<li>
										<a href="#">Reports</a>
											<ul>
												<li>
													<a href="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=FinalPaymentRegister" >Final Payment Register</a>
												</li>
												<li>
													<a href="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=SanctionOrder" >Sanction Order-Region Wise</a>
												</li>											
												<li>
													<a href="<%=basePath%>advanceparamreport.do?method=loadAdvanceInputForm&flag=AdvancesReport" >Advances Report</a>
												</li>												
											</ul>
									</li>
								
								</ul>



								
								</td>
								</tr>
								</table>
							</td>
						<%//}%>

				<td align="left" valign="top">
				<table width="100%" height="42" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>view/images/navbg.gif">
			  	<tr>
				 <td align="right" valign="middle">
				 <table width="350" height="26" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>view/images/img2.gif">
        	      <tr>
                  <td width="5" align="left" valign="top"><img src="<%=basePath%>view/images/img1.gif" width="5" height="26" /></td>
                  <td width="130" align="center" valign="middle" class="text">User: <%=session.getAttribute("userid")%></td>
                  <td width="2" align="left" valign="middle"><img src="<%=basePath%>view/images/divider.gif" width="2" height="16" /></td>
                  <td width="208" align="center" valign="middle" class="text">LoginTime : <%=session.getAttribute("currDateTime")%>&nbsp;</td>
                  <td width="5" align="right" valign="top"><img src="<%=basePath%>view/images/img3.gif" width="5" height="26" /></td>
                </tr>
            	</table>
            	</td>
           		 
            	<td width="84" align="right" valign="middle">
            	<table width="84" height="26" border="0" cellpadding="0" cellspacing="0" background="<%=basePath%>view/images/img2.gif">
                <tr>
                  <td width="5" align="left" valign="top"><img src="<%=basePath%>view/images/img1.gif" width="5" height="26" /></td>
                  <td width="24" align="center" valign="middle"><a href="<%=basePath%>PensionLogin?method=changepassword&flag=checkusername" ><img src="<%=basePath%>view/images/password.gif" alt="Change Password" width="18" height="18" border="0" /></a></td>
                 <%if(session.getAttribute("usertype").equals("Admin")){%>
                  <td width="24" align="center" valign="middle"><a href="<%=basePath%>PensionMenu.jsp"><img src="<%=basePath%>view/images/home.gif" alt="Home" width="18" height="18" border="0" /></a></td>
                 <%}else if(!session.getAttribute("usertype").equals("Admin")) {%>
                   <td width="24" align="center" valign="middle"><a href="<%=basePath%>PensionLogin?method=home"><img src="<%=basePath%>view/images/home.gif" alt="Home" width="18" height="18" border="0" /></a></td>
                   <%}%>
                  <td width="24" align="center" valign="middle"><a href="<%=basePath%>PensionLogin?method=logoff" target="_parent"><img src="<%=basePath%>view/images/logout.gif" alt="Logout" width="16" height="18" border="0" /></a></td>
                  <td width="5" align="right" valign="top"><img src="<%=basePath%>view/images/img3.gif" width="5" height="26" /></td>
                </tr>               
           		 </table>
           		</td>
           		<td width="10" align="left" valign="middle"><img src="<%=basePath%>view/images/spacer.gif" width="10" height="10" /></td>
           	
		  </tr>
		  </table>
		  </td>

						</tr>
					</table>

			

	</body>
</html>


