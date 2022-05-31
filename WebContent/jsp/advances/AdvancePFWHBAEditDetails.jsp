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
										<html:select styleClass="TextField" property="hbapurposetype" name="advanceBean" onchange="chkhbapurposedtl();">
											<html:option value="NO-SELECT">Select One</html:option>
											<html:option value='PURCHASESITE'>Purchasing of dwelling site</html:option>
											<html:option value='PURCHASEHOUSE'>Purchasing of dwelling house</html:option>
											<html:option value='CONSTRUCTIONHOUSE'>Construction of dwelling house</html:option>
											<html:option value='ACQUIREFLAT'>Acquiring a Flat</html:option>
											<html:option value='RENOVATIONHOUSE'>Renovation/Alteration/Addition of dwelling house</html:option>
											<html:option value='REPAYMENTHBA'>Repayment of HBA Loan taken from</html:option>
											<html:option value='HBAOTHERS'>Any Others</html:option>
										</html:select>
									
									</td>


								</tr>
								<tr id="repaymentHbaLoan">
									<td class="tableTextBold">
										Repayment of HBA loan :
									</td>
									<td>
										<html:select styleClass="TextField" property="hbarepaymenttype"  name="advanceReportBean" onchange="chkhbapurposedtl();">
											<html:option value="NO-SELECT"> Select One </html:option>
											<html:option value='LOANFRMGVRMNT'> Loan from State government/Central Govt. </html:option>
											<html:option value='RGDCOPERATIVESOCIETY'> Registered Cooperative society </html:option>
											<html:option value='SHB'> State Housing Board </html:option>
											<html:option value='NB'> Nationalized Bank </html:option>
											<html:option value='PFI'> Public Financial Institution </html:option>
											<html:option value='MC'> Municipal corporation </html:option>
										</html:select>

										
									</td>
								</tr>
								<tr id="hbaAnyOthers">

									<td class="tableTextBold">
										Reason:
									</td>
									<td>
										<html:text styleClass="TextField" property="advReasonText" name="advanceEditInfo"  maxlength="50"/>
												
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
										<html:text styleClass="TextField" property="hbaloanname" name="advanceReportBean" maxlength="50"/>

									</td>

									<td class="tableTextBold">
										Address:
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaloanaddress"  name="advanceReportBean" maxlength="50"/>

									</td>
								</tr>
								<tr id="repaymentHbaLoanInfo2">
									<td class="tableTextBold">
										Outstanding amount of Name
										<br />
										loan with interest:
									</td>
									<td>
										<html:text styleClass="TextField" property="osamountwithinterest"  name="advanceReportBean" maxlength="15"/>

									</td>


								</tr>
								<tr>
									<td class="tableTextBold">
										Property Address:
									</td>
									<td>
										<html:text styleClass="TextField" property="propertyaddress" name="advanceReportBean" maxlength="150" />

									</td>
									<td class="tableTextBold">
										Actual Cost :
									</td>
									<td>
										<html:text styleClass="TextField" property="actualcost" name="advanceReportBean" maxlength="15" />

									</td>


								</tr>
								<tr>
									<td class="tableTextBold">
										Owner Name:
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownername" name="advanceReportBean" maxlength="50" />

									</td>
									<td class="tableTextBold">
										Owner address :
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaowneraddress" name="advanceReportBean" maxlength="100" />

									</td>


								</tr>
								<tr>
									<td class="tableTextBold">
										Area:
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownerarea" name="advanceReportBean" maxlength="45" />

									</td>
									<td class="tableTextBold">
										Plot/House/Flat No :
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownerplotno" name="advanceReportBean" maxlength="50" />

									</td>


								</tr>
								<tr>
									<td class="tableTextBold">
										Locality:
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownerlocality" name="advanceReportBean" maxlength="150" />

									</td>
									<td class="tableTextBold">
										Name of Municipality :
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownermuncipal" name="advanceReportBean" maxlength="50" />

									</td>


								</tr>
								<tr>
									<td class="tableTextBold">
										City:
									</td>
									<td>
										<html:text styleClass="TextField" property="hbaownercity" name="advanceReportBean" maxlength="50" />

									</td>
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="tableTextBold">
										Whether HBA is drawn from AAI:

									</td>
									<td>
										<html:select styleClass="TextField" property="hbadrwnfrmaai" name="advanceReportBean" onchange="chkHBAWthDrwl();">
											<html:option value='Y'>Yes</html:option>
											<html:option value='N'>No</html:option>
										</html:select>

									</td>
									<td class="tableTextBold">
										Intimated to AAI for permission:
									</td>
									<td>
										<html:select styleClass="TextField" property="hbapermissionaai" name="advanceReportBean">
											<html:option value='Y'>Yes</html:option>
											<html:option value='N'>No</html:option>
										</html:select>
										
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
													<html:text styleClass="TextField" property="hbawthdrwlpurpose" name="advanceReportBean" maxlength="100" />
												</td>

												<td class="tableTextBold">
													Amount :
												</td>
												<td>
													<html:text styleClass="TextField" property="hbawthdrwlamount" name="advanceReportBean" maxlength="15" />
												</td>

												<td class="tableTextBold">
													Address:
												</td>
												<td>
													<html:text styleClass="TextField" property="hbawthdrwladdress" name="advanceReportBean" maxlength="100" />
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