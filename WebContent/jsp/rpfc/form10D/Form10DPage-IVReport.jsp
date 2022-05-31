<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>

<%@ page import="java.util.ArrayList"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>AAI</title>
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<%
	ArrayList formList=new ArrayList();
	ArrayList familyDetails=new ArrayList();
	ArrayList nomieeDetails=new ArrayList();
	ArrayList schemeList=new ArrayList();
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	EmployeeAdlPensionInfo adlPersonInfo=new EmployeeAdlPensionInfo();
	String documentAttach="";
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}
	personalInfo=(EmployeePersonalInfo)formList.get(0);
	familyDetails=(ArrayList)formList.get(1);
	nomieeDetails=(ArrayList)formList.get(2);
	schemeList=(ArrayList)formList.get(4);
	adlPersonInfo=(EmployeeAdlPensionInfo)formList.get(5);
	documentAttach=adlPersonInfo.getDocumentInfo();
	String[] documentloaded=documentAttach.split(":");
%>
	<body>
		<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">




			<tr>
				<td class="reportsublabel">
					16.If the claim is preferred by nominee,indicates his/her
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="reportsublabel">
					1.Name
				</td>
				<td class="Data"><%=adlPersonInfo.getClaimNomineeRefName()%></td>
			</tr>
			<tr>
				<td class="reportsublabel">
					2.Relationship with the deceased Member
				</td>
				<td class="Data"><%=adlPersonInfo.getClaimNomineeRefRelation()%>
				</td>
			</tr>
			<tr>

				<td colspan="2">
					&nbsp;
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<table cellpadding="1" cellspacing="1" border="0" width="100%" align="center">
						<tr>
							<td width="50%" class="reportsublabel">
								17.Details of Scheme Certificate
							</td>
							<td width="24%" class="reportsublabel">
								Scheme Certificate
								<br />
								received & enclosed
							</td>
							<td width="26%" class="Data">
								<%=adlPersonInfo.getSchemeCertificateRecEncl()%>
							</td>

						</tr>
						<tr>
							<td class="reportsublabel">
								Already in possession of the Member,if any
							</td>
							<td class="reportsublabel">
								Not Received
							</td>
							<td class="Data">
								<%=adlPersonInfo.getPossesionMember()%>
							</td>

						</tr>
						<tr>
							<td class="reportsublabel">
								Full post all address
							</td>
							<td class="reportsublabel">
								Not Applicable
							</td>
							<td>
								<%=adlPersonInfo.getNomineePostalAddrss()%><br/>
								<%=adlPersonInfo.getNomineePincode()%>
							</td>

						</tr>
						<tr>
							<td colspan="3" class="reportsublabel">
								Pensioners may at their discretion opt to draw pension
							</td>
						</tr>
						<tr>
							<td colspan="3" class="reportsublabel">
								either from the designated Post office or from designated Bank
							</td>
						</tr>
						<tr>
							<td colspan="3" class="reportsublabel">
								If received,indicate:
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table cellpadding="1" cellspacing="1" border="1" width="90%" align="center">
									<tr>
										<td class="reportsublabel">
											Sl.No
										</td >
										<td class="reportsublabel">
											Scheme Certificate Control No.
										</td>
										<td class="reportsublabel">
											Authority who issue the Scheme Certificate
										</td>
									</tr>
									<%if(schemeList.size()!=0){
									String schemeData="",srno="",schemeControlNo="",schemeAuthortiy="";
									for(int k=0;k<schemeList.size();k++){
									schemeData=(String)schemeList.get(k);
									String[] schemeInfo=schemeData.split("@");
									if(!schemeInfo[0].equals("xxx")){
										schemeControlNo=schemeInfo[0];
									}else{
										schemeControlNo="";
									}
									if(!schemeInfo[1].equals("xxx")){
										schemeAuthortiy=schemeInfo[1];
									}else{
										schemeAuthortiy="";
									}
									if(!schemeInfo[2].equals("xxx")){
										srno=schemeInfo[2];
									}else{
										srno="";
									}
									
									%>
									<tr>
										<td class="Data"><%=srno%></td>
										<td class="Data"><%=schemeControlNo%></td>
										<td class="Data"><%=schemeAuthortiy%></td>
									</tr>	
									
									<%}}else{%>
									<tr height="100">
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<%}%>
								</table>
							</td>

						</tr>
						<tr>
							<td colspan="3">
								<table cellpadding="1" cellspacing="1" border="0" width="100%" align="center">
									<tr>
										<td width="50%" class="reportsublabel">
											18.If Pension is being drawn Under E.P.S.,1995
										</td>
										<td width="25%" class="reportsublabel">
											PPO No.issued by
										</td>
										<td width="25%">
											<table cellpadding="1" cellspacing="1" border="1" width="90%" align="center">
												<tr>
													<td width="48%" class="reportsublabel">
														RO
													</td>
													<td width="52%" class="reportsublabel">
														SRO
													</td>

												</tr>
												<tr height="30">
													<%if(adlPersonInfo.getPponoIssuedBy().equals("RO")){%>
													<td>Y</td>
													<%}else{%>
														<td>N</td>
													<%}%>
													<%if(adlPersonInfo.getPponoIssuedBy().equals("SRO")){%>
													<td>Y</td>
													<%}else{%>
														<td>N</td>
													<%}%>

												</tr>
											</table>
										</td>

									</tr>




								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table cellpadding="1" cellspacing="1" border="0" width="100%" align="center">
						<tr>
							<td class="reportsublabel" colspan="2">Documents enclosed(Indicate as per the Instructions)	</td>
						</tr>
							<%
							int docSrlNo=0;
							if(documentloaded.length!=0){
							for(int doclist=0;doclist<documentloaded.length;doclist++){
							System.out.println("Documents Loaded"+documentloaded[doclist]);
								docSrlNo++;%>
						<tr>
							<td class="Data" width="3%"><%=docSrlNo%></td>
							<td class="Data" align="left"><%=documentloaded[doclist]%></td>
						</tr>
					<%}}%>
					</table>
				</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		</table>
	</body>
</html>
