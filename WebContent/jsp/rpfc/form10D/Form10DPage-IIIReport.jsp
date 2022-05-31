<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.*"%>

<%@ page import="java.util.ArrayList" %>
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
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	EmployeeAdlPensionInfo adlPersonInfo=new EmployeeAdlPensionInfo();
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}
	personalInfo=(EmployeePersonalInfo)formList.get(0);
	familyDetails=(ArrayList)formList.get(1);
	nomieeDetails=(ArrayList)formList.get(2);
	adlPersonInfo=(EmployeeAdlPensionInfo)formList.get(5);
	String accountOpenedLabel="",accountOpenedAddr="";
	if(adlPersonInfo.getPaymentInfoType().equals("B")){
		accountOpenedLabel="Details of Saving Bank Account Opened";
		accountOpenedAddr="Name of the Bank Address";
	}else{
		accountOpenedLabel="Details of Post Office Account Opened";
		accountOpenedAddr="Name of the Post Office Address";
	}
	
%>
<body>
<table  width="100%"align="center" cellpadding="0" cellspacing="0" border="0"> 
<tr>
	

  <tr>
  	<td>
		<table align="center" width="100%" height="50" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td class="reportsublabel">Particulars of Family:</td>
				<td class="reportsublabel">(Furnish details of all childeren including married childern)</td>
			</tr>
			<tr>
				<td colspan="2">
					<table cellpadding="0" cellspacing="0" border="1" width="80%" align="center">
						<tr>
						<td rowspan="2"class="reportsublabel" >Sl.No		</td>
						<td rowspan="2" class="reportsublabel">Name		</td>
						<td rowspan="2" class="reportsublabel">Date Of Birth/Age</td>
						<td rowspan="2" class="reportsublabel">Relation With Member</td>
						<td colspan="2" class="reportsublabel" align="center">Indicate against minor</td>
						</tr>
						<tr>
						<td class="reportsublabel">Guardian Name</td>
						<td class="reportsublabel">Relationship with member</td>
						</tr>
						
						<tr>
						<td class="reportsublabel">(1)</td>
						<td class="reportsublabel">(2)</td>
						<td class="reportsublabel">(3)</td>
						<td class="reportsublabel">(4)</td>
						<td class="reportsublabel">(5)</td>
						<td class="reportsublabel">(6)</td>
						</tr>
						<%
						 if(nomieeDetails.size()!=0){
						 NomineeBean nomineeInfo = new NomineeBean();
						 for(int nominee=0;nominee<nomieeDetails.size();nominee++){
						 nomineeInfo=(NomineeBean)nomieeDetails.get(nominee);
						%>
						
						<tr>
						<td class="Data"><%=nomineeInfo.getSrno()%></td>
						<td class="Data"><%=nomineeInfo.getNomineeName()%></td>
						<td class="Data"><%=nomineeInfo.getNomineeDob()%></td>
						<td class="Data"><%=nomineeInfo.getNomineeRelation()%></td>
						<td class="Data"><%=nomineeInfo.getNameOfGuardian()%></td>
						<td class="Data">&nbsp;</td>
						</tr>
						<%}}else{%>
						<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						</tr>
						<%}%>
					</table>
				</td>
				
			</tr>
			<tr>
				<td colspan="2" class="reportsublabel">Note:If any child is Physically handicapped,please indicate "DISABLED" below the name</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
						<td class="reportsublabel">Date of Death of Member (if applicable)</td>
						<td><%=adlPersonInfo.getMemberDeathDate()%></td>
			</tr>
			<tr>
						<td class="reportsublabel"><%=accountOpenedLabel%></td>
						<td>&nbsp;</td>
			</tr>
			<tr>
						<td class="reportsublabel"><%=accountOpenedAddr%></td>
						<td><%=adlPersonInfo.getAddressofPayementBranch()%></td>
			</tr>
			<tr>
						<td class="reportsublabel">Name of the Branch</td>
						<td><%=adlPersonInfo.getNameofPaymentBranch()%></td>
			</tr>
			<tr>
						<td class="reportsublabel">Full post all address</td>
						<td><%=adlPersonInfo.getAddressofPayementBranch()%></td>
			</tr>
			<tr>
						<td class="reportsublabel">Pincode</td>
						<td><%=adlPersonInfo.getPaymentBranchPincode()%></td>
			</tr>
			<tr>
						<td class="reportsublabel">Pensioncrs may at their discretionopt to drawn pension</td>
						<td>&nbsp;</td>
						
			</tr>
			<tr>
						<td class="reportsublabel">either from the designated post office or from designated Bank</td>
						<td>&nbsp;</td>
						
			</tr>
			<tr>
				<td colspan="2">
					<table cellpadding="0" cellspacing="0" border="1" width="80%" align="center">
						<tr>
						<td class="reportsublabel">Sl.No</td>
						<td class="reportsublabel">Name of the Claimant(s)</td>
						<td class="reportsublabel">Saving Bank/Post Office/Accounts No</td>
						
						</tr>
						<tr>
						<td class="Data">1</td>
						<td class="Data"><%=personalInfo.getEmployeeName()%>,Self</td>
						<td>&nbsp;</td>
						</tr>
					<%
						 if(nomieeDetails.size()!=0){
						 NomineeBean nomineeInfo = new NomineeBean();
						 for(int nominee1=0;nominee1<nomieeDetails.size();nominee1++){
						 nomineeInfo=(NomineeBean)nomieeDetails.get(nominee1);
						%>
						
						<tr>
						<td class="Data"><%=nomineeInfo.getSrno()%></td>
						<td class="Data"><%=nomineeInfo.getNomineeName()%>,<%=nomineeInfo.getNomineeRelation()%></td>
						<td class="Data"><%=nomineeInfo.getNomineeAccNo()%></td>
						</tr>
						<%}}else{%>
						<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						</tr>
						<%}%>
					</table>
				</td>
				
			</tr>
		</table>
		</td>
		</tr>

</table>
</body>
</html>
