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
	if(request.getAttribute("perForm10DList")!=null){
		formList=(ArrayList)request.getAttribute("perForm10DList");
	}
	personalInfo=(EmployeePersonalInfo)formList.get(0);
	familyDetails=(ArrayList)formList.get(1);
	nomieeDetails=(ArrayList)formList.get(2);
%>
<body>
<table  width="100%"align="center" cellpadding="0" cellspacing="0" border="0"> 
<tr>
	 <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
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
        <td colspan="3" align="center" class="reportlabel">TO BE SUBMITTED IN DUPLICATE IN RESPECT OF </td>
      </tr>
	  <tr>
        <td colspan="3" align="center" class="reportlabel">EACH PERSON ELIGIBLE FOR PENSION</td>
      </tr>
	
    </table></td>
  </tr>

  <tr>
  	<td >
		<table align="center" width="100%" height="50" cellpadding="0" cellspacing="0" border="0">
			<tr>	
				<td colspan="2" class="reportsublabel">Descriptive of Pensioner and his/her Specimen Signature/Thumb impression.</td>		
				
			</tr>
			
			<tr>	
				<td width="51%" class="reportsublabel">1.Member Name (In Block Letters) </td>		
				<td width="49%" class="Data"><%=personalInfo.getEmployeeName().toUpperCase()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">2.E.P.F.  Account Number</td>		 
				<td class="Data"><%=personalInfo.getCpfAccno()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">3.Name of the Pensioner</td>		 
				<td class="Data"><%=personalInfo.getEmployeeName()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">4.Father/Husband Name</td>		
				<td class="Data"><%=personalInfo.getFhName()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">5.Sex</td>		
				<td class="Data"><%=personalInfo.getGender()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">6.Nationality</td>		 
				<td class="Data"><%=personalInfo.getNationality()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">7.Height</td>		
				<td class="Data"><%=personalInfo.getHeightWithInches()%></td>		
			</tr>
			<tr>	
				<td class="reportsublabel">8.Personal marks Of Identification</td>		
				<td class="Data">1.</td>		
			</tr>
			<tr>	
				<td>&nbsp;</td>		
				<td class="Data">2.</td>		
			</tr>
				<tr>	
				<td class="reportsublabel">9.Specimen signature of Pensioner</td>		
				<td class="Data">1.</td>		
			</tr>
			<tr>	
				<td>&nbsp;</td>		
				<td class="Data">2.</td>		
			</tr>
			<tr>	
				<td>&nbsp;</td>		
				<td class="Data">3.</td>		
			</tr>
			<tr>	
				<td class="reportsublabel">11.(Only in the case of illiterate Claimant (Pensioner)</td>		
				<td>&nbsp;</td>		
			</tr>
			<tr>	
				<td class="reportsublabel">Left Hand Finger Impression);</td>		
				<td>&nbsp;</td>		
			</tr>
			<tr>	
				<td colspan="2">
					<table align="center" cellpadding="0" cellspacing="0" border="1" width="80%">
						<tr>
							<td class="reportsublabel">THUMB</td>
							<td class="reportsublabel">INDEX</td>
							<td class="reportsublabel">MIDDLE</td>
							<td class="reportsublabel">RING</td>
							<td class="reportsublabel">SMALL</td>
						</tr>
						<tr>
								<td>&nbsp;</td>		
								<td>&nbsp;</td>		
								<td>&nbsp;</td>		
								<td>&nbsp;</td>		
								<td>&nbsp;</td>	
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table align="center" cellpadding="2" cellspacing="2" border="0" width="100%">
						<tr>
							
							<td colspan="4" align="right" class="reportsublabel">Signature</td>
						</tr>
						<tr>
						<td colspan="4" align="right" class="reportsublabel">Name of attesting Authority</td>
						</tr>
						<tr>
						<td colspan="4" align="right" class="reportsublabel">/Official Seal</td>
						</tr>
					
					</table>
				</td>
			</tr>
				<tr>
						<td colspan="2" align="left" class="reportsublabel">Place</td>
						</tr>
						<tr>
						<td colspan="2" align="left" class="reportsublabel">Date</td>
						</tr>
			<tr>
				<td colspan="2" class="reportsublabel">Certified that:-</td>
			
			</tr>
			<tr>
				<td colspan="2" class="reportsublabel">(i).I am not drawing Pension under Employee's Pension Scheme,1995:</td>	
			</tr>
			<tr>
				<td colspan="2" class="reportsublabel">(ii).The particulars given in this application are true and correct.</td>	
			</tr>
			<tr>	
				<td colspan="2">
					<table align="center" cellpadding="1" cellspacing="1" border="0" width="100%">
						<tr>
						<td colspan="4" align="right" class="reportsublabel">Signature of the applicant</td>
						</tr>
						<tr>
						<td colspan="4" align="right" class="reportsublabel">Left hand Thumb Impression</td>
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
