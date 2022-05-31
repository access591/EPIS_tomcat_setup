<!--
/*
  * File       : AdvancePFWForm2Report.jsp
  * Date       : 07/10/2009
  * Author     : Suneetha V
  * Description: 
  * Copyright (2009) by the Navayuga Infotech, all rights reserved.
  */
-->



<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
	<head>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
		<script type="text/javascript">
	</script>
	</HEAD>
	<body>
		<html:form method="post" action="loadAdvance.do?method=loadAdvanceForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="26%" rowspan="2">
									<img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" />
								</td>
								<td width="70%">
									<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; AIRPORTS AUTHORITY OF INDIA</strong>
								</td>
							</tr>
							<tr>
								<td width="62%">
									<p align="left">
										<strong>(DEPARTMENT OF FINANCE &amp; ACCOUNTS)</strong>
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<logic:present name="Form2ReportBean">
					<tr>


						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="26%" rowspan="2">
										&nbsp;
									</td>
									<td width="70%" class="reportsublabel">
										<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Verified by the Directorate of Personnel</strong>
									</td>
								</tr>
							</table>
						</td>

					</tr>
					<tr>
						<td colspan="2" align="center">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
								<tr>

									<td width="18%">
										&nbsp;
									</td>
									<td width="30%" class="reportContentsublabel">
										KEY NO
									</td>
									<td width="3%">
										:
									</td>
									<td width="28%" class="reportdata">
										<bean:write name="Form2ReportBean" property="advanceTransID" />
									</td>
									<td width="20%">
										&nbsp;
									</td>

								</tr>

								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Employee code
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="employeeNo" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Employee Name
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="employeeName" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Designation
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="designation" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Father&rsquo;s Name
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="fhName" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Department
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="department" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Purpose
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="purposeTypeDescr" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Date of joining in AAI
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="dateOfJoining" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td class="reportContentsublabel">
										Date of Birth
									</td>
									<td>
										:
									</td>
									<td class="reportContentdata">
										<bean:write name="Form2ReportBean" property="dateOfBirth" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>

								<logic:equal property="purposeType" value="MARRIAGE" name="Form2ReportBean">
									<tr>
										<td>
											&nbsp;
										</td>

										<logic:equal property="purposeOptionType" value="SELF" name="Form2ReportBean">
											<td colspan="3" class="reportContentsublabel" align="justify">
												Certified that the name of applicant is an employee and age is on date is
												<bean:write name="Form2ReportBean" property="fmlyAge" />
												as per official records.
											</td>
										</logic:equal>

										<logic:notEqual property="purposeOptionType" value="SELF" name="Form2ReportBean">
											<td colspan="3" class="reportContentsublabel" align="justify">
												Certified that

												<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
             Ms.
             </logic:equal>

												<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             Mr.
             </logic:equal>

												<bean:write name="Form2ReportBean" property="fmlyEmpName" />
												is

												<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
             daughter
             </logic:equal>

												<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             son
             </logic:equal>

												<logic:equal property="purposeOptionType" value="DEPENDENT BROTHER" name="Form2ReportBean">
             brother
             </logic:equal>

												<logic:equal property="purposeOptionType" value="DEPENDENT SISTER" name="Form2ReportBean">
             sister
             </logic:equal>



												of Shri
												<logic:notEqual property="purposeOptionType" value="SELF" name="Form2ReportBean">
													<bean:write name="Form2ReportBean" property="employeeName" />
												</logic:notEqual>
												<logic:equal property="purposeOptionType" value="SELF" name="Form2ReportBean">
													<bean:write name="Form2ReportBean" property="fhName" />
												</logic:equal>
												And is member of family and

												<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
              her
             </logic:equal>

												<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             his
             </logic:equal>


												<logic:equal property="purposeOptionType" value="DEPENDENT BROTHER" name="Form2ReportBean">
           his
             </logic:equal>

												<logic:equal property="purposeOptionType" value="DEPENDENT SISTER" name="Form2ReportBean">
             her
             </logic:equal>


												age is on date is
												<bean:write name="Form2ReportBean" property="fmlyAge" />
												as per official records.

											</td>
										</logic:notEqual>
									</tr>
								</logic:equal>

								<logic:equal property="purposeType" value="HE" name="Form2ReportBean">
									<tr>
										<td>
											&nbsp;
										</td>
										<td colspan="3" class="reportContentsublabel">
											Certified that

											<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
             Ms.
             </logic:equal>

											<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             Mr.
             </logic:equal>

											<bean:write name="Form2ReportBean" property="fmlyEmpName" />
											is

											<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
             daughter
             </logic:equal>

											<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             son
             </logic:equal>
											of Shri
											<bean:write name="Form2ReportBean" property="employeeName" />
											And is dependent and

											<logic:equal property="purposeOptionType" value="DAUGHTER" name="Form2ReportBean">
              her
             </logic:equal>

											<logic:equal property="purposeOptionType" value="SON" name="Form2ReportBean">
             his
             </logic:equal>

											age is on date is
											<bean:write name="Form2ReportBean" property="fmlyAge" />
											as per official records.
										</td>
									</tr>
								</logic:equal>

								<logic:equal property="purposeType" value="HBA" name="Form2ReportBean">
									<logic:equal property="takenloan" value="Y" name="Form2ReportBean">
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3" class="reportContentsublabel">
												Certified that

												<logic:equal property="gender" value="M" name="Form2ReportBean">
            Shri.
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
            Smt.
            </logic:equal>

												<bean:write name="Form2ReportBean" property="employeeName" />

												<logic:equal property="gender" value="M" name="Form2ReportBean">
               s/o 
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
             d/o
            </logic:equal>
												Shri
												<bean:write name="Form2ReportBean" property="fhName" />
												has taken HBA loan from AAI for the purpose
												<bean:write name="Form2ReportBean" property="hbawthdrwlpurpose" />
												Amount
												<bean:write name="Form2ReportBean" property="hbawthdrwlamount" />
												and address
												<bean:write name="Form2ReportBean" property="hbawthdrwladdress" />
												.


											</td>
										</tr>
									</logic:equal>
								</logic:equal>


								<logic:equal property="purposeType" value="HBA" name="Form2ReportBean">
									<logic:equal property="takenloan" value="N" name="Form2ReportBean">
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3" class="reportContentsublabel">
												Certified that
												<logic:equal property="gender" value="M" name="Form2ReportBean">
            Shri.
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
            Smt.
            </logic:equal>

												<bean:write name="Form2ReportBean" property="employeeName" />
												<logic:equal property="gender" value="M" name="Form2ReportBean">
               s/o 
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
             d/o
            </logic:equal>
												Shri
												<bean:write name="Form2ReportBean" property="fhName" />
												has not taken HBA loan from AAI for the purpose
												<bean:write name="Form2ReportBean" property="purposeOptionType" />
												and address
												<bean:write name="Form2ReportBean" property="propertyaddress" />
												.
											</td>
										</tr>
									</logic:equal>
								</logic:equal>
								<logic:equal property="purposeType" value="SUPERANNUATION" name="Form2ReportBean">
									 
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3" class="reportContentsublabel">
												Certified that

												<logic:equal property="gender" value="M" name="Form2ReportBean">
            Shri.
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
            Smt.
            </logic:equal>

												<bean:write name="Form2ReportBean" property="employeeName" />

												<logic:equal property="gender" value="M" name="Form2ReportBean">
               s/o 
            </logic:equal>

												<logic:equal property="gender" value="F" name="Form2ReportBean">
             d/o
            </logic:equal>
												Shri
												<bean:write name="Form2ReportBean" property="fhName" />
												has attend the age of 54 years or one year before the date of retirement .
												 


											</td>
										</tr>
									 
								</logic:equal>

								<tr>
									<td height="24">
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td class="reportdata">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										&nbsp;
									</td>
								</tr>


							</table>
						</td>
					</tr>
				</logic:present>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<logic:present name="transList">
				<tr>
					<td height="22" colspan="2" class="reportsublabel">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<logic:iterate id="trans" name="transList" indexId="slno">
						
						<logic:equal name="trans" property="transCode"  value="32">
							<tr>
								<td width="18%">
									&nbsp;
								</td>
								<td width="30%">
									&nbsp;Date : <bean:write name="trans" property="transApprovedDate" />
								</td>
								<td width="3%">
									&nbsp;
								</td>
								<td width="28%">
									&nbsp;
										<img src="<%=basePath%>/uploads/dbf/<bean:write name="trans" property="transDigitalSign" />" width="150" height="48" border="no" alt="" />
										
								</td>
								<td width="20%">
									&nbsp;
								</td>

							</tr>
							<!-- <tr>
								<td width="18%">
									&nbsp;
								</td>
								<td width="30%">
									&nbsp;
								</td>
								<td width="3%">
									&nbsp;
								</td>
								<td width="28%" class="reportContentlabel">
									(Authorized Signatory)
								</td>
								<td width="20%">
									&nbsp;
								</td>
							</tr>-->
							
							<tr>
								<td width="18%">
									&nbsp;
								</td>
								<td width="30%">
									&nbsp;
								</td>
								<td width="3%">
									&nbsp;
								</td>								 
								<td width="28%" class="reportContentlabel">
									(<bean:write name="trans" property="transDispSignName" />)
								</td>								
								<td width="20%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td width="18%">
									&nbsp;
								</td>
								<td width="30%">
									&nbsp;
								</td>
								<td width="3%">
									&nbsp;
								</td>
								<logic:notEqual name="trans" property="designation"  value="---">
								<td width="28%" class="reportContentlabel">
									<bean:write name="trans" property="designation" />
								</td>
								</logic:notEqual>
								<td width="20%">
									&nbsp;
								</td>
							</tr>
							 
							</logic:equal>
							</logic:iterate>

						</table>
					</td>
				</tr>
				</logic:present>
				<logic:notPresent name="transList">
				<tr>
						<td colspan="6" height="22" class="reportsublabel">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
						 		
			<tr>

			<td colspan="6" height="22" class="reportsublabel">
						 
			<tr>
            <td width="21%">&nbsp;</td>
            <td width="27%"  class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="44%" class="reportsublabel">&nbsp;___________________</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">(Authorized Signatory)</td>
          </tr>
           <tr>
            <td>&nbsp;</td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="reportsublabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
          </tr>
		</td>
		</tr>
		</table>
		</td>
		</tr>
				</logic:notPresent>
				
				
				<tr>
					<td class="screenlabel">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="50%" class="screenlabel">
						&nbsp;
					</td>
					<td width="50%">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
