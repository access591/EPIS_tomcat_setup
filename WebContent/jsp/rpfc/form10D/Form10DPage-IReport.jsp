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
	EmployeePersonalInfo personalInfo=new EmployeePersonalInfo();
	EmployeeAdlPensionInfo adlPersonInfo=new EmployeeAdlPensionInfo();
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}
	personalInfo=(EmployeePersonalInfo)formList.get(0);
	
%>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	 <tr>
    <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
     <tr>
        <td>&nbsp;</td>
        <td width="7%" rowspan="2"><img src="<%=basePath%>/images/logoani.gif" width="100" height="50" /></td>
        <td>AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
       <tr>
        <td width="38%">&nbsp;</td>
        <td width="55%">&nbsp;</td>
      </tr>
         <tr>
        <td colspan="3" align="center" class="reportlabel">&nbsp; </td>
      </tr>
      <tr>
        <td colspan="3" align="center" class="reportlabel">APPLICATION FOR MONTHLY PENSION </td>
      </tr>
	  <tr>
        <td colspan="3" align="center" class="reportlabel">FORM 10-D(EPS) </td>
      </tr>
	  <tr>
        <td colspan="3" align="center" class="reportlabel">Employee's Pension Scheme,1995 </td>
      </tr>
	    <tr>
        <td colspan="3" align="center" class="reportlabel">(Read Instructions before filling in this form) </td>
      </tr>
    </table></td>
  </tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
  <tr>
  	<td >
		<table align="center" width="100%" height="50" cellpadding="2" cellspacing="2" border="0">
			<tr>	
				<td width="50%" class="reportsublabel">1.By whom the Pension is Claimed?</td>		
				<td width="50%" class="reportsublabel">2.Type of Pension Clamied.</td>
			</tr>
			<tr>	
				<td>&nbsp;</td>		
				<td class="Data"><%=personalInfo.getWetherOption()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Member Name (In Block Letters)</td>		
				<td class="Data"><%=personalInfo.getEmployeeName().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Sex</td>		
				<td class="Data"><%=personalInfo.getGender().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Marital Status</td>		
				<td class="Data"><%=personalInfo.getMaritalStatus().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Date Of Birth/Age</td>		
				<td class="Data"><%=personalInfo.getDateOfBirth().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Parent/Spouse Name</td>		
				<td class="Data"><%=personalInfo.getFhName().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">E.P.F.  Account Number</td>		 
				<td class="Data"><%=personalInfo.getCpfAccno()%></td>		
			</tr>
			<tr>	
				<td colspan="2">
					<table align="center" cellpadding="0" cellspacing="0" border="1" width="80%">
						<tr>
							<td class="reportsublabel">RO</td>
							<td class="reportsublabel">SRO</td>
							<td class="reportsublabel">Establishment Code No</td>
							<td class="reportsublabel">Member A/c No.</td>
						</tr>
						<tr>
								<td>&nbsp;</td>		
								<td>&nbsp;</td>		
								<td class="Data">DL-36478</td>		
								<td>&nbsp;</td>		
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="reportsublabel">Name & Address the Establishment<br />In which the member was last employed.</td>
				<td class="Data">Airport Authority Of India</td>
			</tr>
			<tr>
				<td class="reportsublabel">Date of Leaving Service:</td>
				<td class="Data"><%=personalInfo.getSeperationDate()%></td>		
			</tr>
			<tr>
				<td class="reportsublabel">Reason of Leaving Service:</td>
				<td class="Data"><%=personalInfo.getSeperationReason()%></td>		
			</tr>
			<tr>
				<td class="reportsublabel">Address for Communication:</td>
				<td>&nbsp;</td>		
			</tr>
					<tr>	
				<td colspan="2">
					<table align="center" cellpadding="2" cellspacing="2" border="2" bordercolor="gray" width="70%">
						<tr height="50">
							<td colspan="4" class="Data"><%=personalInfo.getTempAddress()%></td>
						
						</tr>
						<tr height="50">
								<td colspan="4" class="Data"><%=personalInfo.getPerAddress()%></td>
						</tr>
					</table>
				</td>
			</tr>
	  </table>	
	</td>
  </tr>
</tr>
 
</table>
</body>
</html>
