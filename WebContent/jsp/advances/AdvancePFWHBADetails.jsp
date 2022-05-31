	<%@ page language="java" import="java.util.*,com.epis.bean.advances.AdvanceBasicBean,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");

			String basePath = basePathBuf.toString();
			
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>AAI</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css">	
		
	</head>
	<body>
	
 	

								<tr>
									<td>
										<table cellpadding="1" cellspacing="1" border="0" align="center" width="100%">

											<tr>
												<td class="tableTextBold">
													HBA Purposes:
												</td>
												<td>
													<html:select styleClass="TextField" property="hbapurposetype" style="width: 240px" onchange="chkhbapurposedtl();">
														<html:option value="NO-SELECT">Select One</html:option>
														<html:option value='purchasesite'>Purchasing of dwelling site</html:option>
														<html:option value='purchasehouse'>Purchasing of dwelling house</html:option>
														<html:option value='constructionhouse'>Construction of dwelling house</html:option>
														<html:option value='acquireflat'>Acquiring a Flat</html:option>
														<html:option value='renovationhouse'>Renovation/Alteration/Addition of dwelling house</html:option>
														<html:option value='repaymenthba'>Repayment of HBA Loan taken from</html:option>
														<html:option value='hbaothers'>Any Others</html:option>
													</html:select>
													<!-- <select name='select_hba_purpose_dtl' style='width:130px' onchange="chkhbapurposedtl();">
													<option value="NO-SELECT">
														Select One
													</option>
													<option value='purchasesite'>
														Purchasing of dwelling site
													</option>
													<option value='purchasehouse'>
														Purchasing of dwelling house
													</option>
													<option value='constructionhouse'>
														Construction of dwelling house
													</option>
													<option value='acquireflat'>
														Acquiring a Flat
													</option>
													<option value='renovationhouse'>
														Renovation/Alteration/Addition of dwelling house
													</option>
													<option value='repaymenthba'>
														Repayment of HBA Loan taken from
													</option>
													<option value='hbaothers'>
														Any Others
													</option>
												</select>-->
												</td>


											</tr>
											<tr id="repaymentHbaLoan">
												<td class="tableTextBold">
													Repayment of HBA loan :
												</td>
												<td>
													<html:select styleClass="TextField" property="hbarepaymenttype" onchange="chkhbapurposedtl();">
														<html:option value="NO-SELECT"> Select One </html:option>
														<html:option value='LOANFRMGVRMNT'> Loan from State government/Central Govt. </html:option>
														<html:option value='RGDCOPERATIVESOCIETY'> Registered Cooperative society </html:option>
														<html:option value='SHB'> State Housing Board </html:option>
														<html:option value='NB'> Nationalized Bank </html:option>
														<html:option value='PFI'> Public Financial Institution </html:option>
														<html:option value='MC'> Municipal corporation </html:option>
													</html:select>

													<!-- <select name='repayment' Style='width:130px'>
													<option value="NO-SELECT">
														Select One
													</option>
													<option value='loan1'>
														Loan from State government/Central Govt.
													</option>
													<option value='regi'>
														Registered Cooperative society
													</option>
													<option value='houseing'>
														State Housing Board
													</option>
													<option value='nation'>
														Nationalized Bank
													</option>
													<option value='Pfi'>
														Public Financial Institution
													</option>
													<option value='muncipal'>
														Municipal corporation
													</option>
												</select>-->
												</td>
											</tr>
											<tr id="hbaAnyOthers">

												<td class="tableTextBold">
													Reason:
												</td>
												<td>
													<html:text styleClass="TextField" property="advReasonText" maxlength="50"/>
												</td>
												<td colspan="2">
													&nbsp;
												</td>
											</tr>
											<tr id="repaymentHbaLoanInfo">
												<td class="tableTextBold">
													Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaloanname" maxlength="50"/>
												</td>

												<td class="tableTextBold">
													Address:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaloanaddress" maxlength="50"/>
												</td>
											</tr>
											<tr id="repaymentHbaLoanInfo2">
												<td class="tableTextBold">
													Outstanding amount of Name
													<br />
													loan with interest:
												</td>
												<td>
													<html:text styleClass="TextField" property="osamountwithinterest" maxlength="15"/>
												</td>
											</tr>

											<tr>
												<td class="tableTextBold">
													Property Address:
												</td>
												<td>
													<html:text styleClass="TextField" property="propertyaddress" maxlength="150" />

												</td>
												<td class="tableTextBold">
													Actual Cost :
												</td>
												<td>
													<html:text styleClass="TextField" property="actualcost" maxlength="15" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Owner Name:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownername" maxlength="50" />

												</td>
												<td class="tableTextBold">
													Owner address :
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaowneraddress" maxlength="100" />
												</td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Area:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownerarea" maxlength="45" />

												</td>
												<td class="tableTextBold">
													Plot/House/Flat No :
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownerplotno" maxlength="50" />

												</td>


											</tr>
											<tr>
												<td class="tableTextBold">
													Locality:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownerlocality" maxlength="150" />

												</td>
												<td class="tableTextBold">
													Name of Municipality :
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownermuncipality" maxlength="50" />

												</td>


											</tr>
											<tr>
												<td class="tableTextBold">
													City:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbaownercity" maxlength="50" />

												</td>
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="tableTextBold">
													Whether HBA is drawn from AAI:
												</td>
												<td>
													<html:select styleClass="TextField" property="hbadrwnfrmaai" style="width:119px" onchange="chkHBAWthDrwl();">
														<html:option value='Y'>Yes</html:option>
														<html:option value='N'>No</html:option>
													</html:select>

												</td>
												<td class="tableTextBold">
													Intimated to AAI for permission:
												</td>
												<td>
													<html:select styleClass="TextField" property="hbapermissionaai" style="width:119px">
														<html:option value='Y'>Yes</html:option>
														<html:option value='N'>No</html:option>
													</html:select>
													<!-- <select name='select_permission_aai' Style='width:130px'>
												
													<option value='Y' selected="selected">
														Yes
													</option>
													<option value='N'>
														No
													</option>
												</select>-->
												</td>
											</tr>

											<tr id="chk_HBA_wthdrwn_dtl">
												<td colspan="4">
													<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">

														<tr>
															<td class="tableTextBold">
																Purpose :
															</td>
															<td>
																<html:text styleClass="TextField" property="hbawthdrwlpurpose" maxlength="100" />
															</td>

															<td class="tableTextBold">
																Amount :
															</td>
															<td>
																<html:text styleClass="TextField" property="hbawthdrwlamount" maxlength="15" />
															</td>

															<td class="tableTextBold">
																Address:
															</td>
															<td>
																<html:text styleClass="TextField" property="hbawthdrwladdress" maxlength="150" />
															</td>
														</tr>

													</table>
												</td>
											</tr>

										</table>
									</td>
								</tr>

							 
							 
</body>
</html>