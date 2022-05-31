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

<body>
<table  width="100%"align="center" cellpadding="0" cellspacing="0" border="0"> 


  <tr>
  	<td >
		<table align="center" width="80%" height="50" cellpadding="2" cellspacing="2" border="0">
			<tr>	
				<td colspan="2" align="center" class="reportlabel">(FOR OFFICE USE ONLY)</td>		
				
			</tr>
		
			
			<tr>	
				<td colspan="2" align="center" class="reportlabel">(PENSION SECTION/ACCOUNTS SECTION)</td>		
				
			</tr>

			<tr>	
				<td colspan="2">Certified that the particulars in the application have been verified with the relevant concerned documents.The claimant is eligible for Pension.The Input Data Sheet is placed below for approval:</td>		
				
			</tr>
			<tr>	
				<td colspan="2">Entered in Form 9/Form 3(PS),Master Ledger Card/Claim Inward Register</td>		
				
			</tr>
			<tr>	
				<td colspan="2">Form 2(R)enclosed alongwith the documents furnished by the claimant</td>		
				
			</tr>
			
	  </table>	
	</td>
  </tr>
  <tr>
	<td>&nbsp;</td>
	</tr>
   <tr>
	<td>
		<table align="center" width="100%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td width="34%" align="center">CLERK</td>
				<td width="19%" align="center">SS</td>
				<td width="23%" align="center">A.A.O</td>
				<td width="24%" align="center">A.P.F.C.</td>
			</tr>
				<tr>
				<td width="34%" align="center">date</td>
				<td width="19%" align="center">date</td>
				<td width="23%" align="center">date</td>
				<td width="24%" align="center">date</td>
			</tr>
	  </table>	
	</td>
	</tr>
	 <tr>
	  	<td>
		<table align="center" width="80%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td colspan="2"><hr /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
  	<td >
		<table align="center" width="80%" height="50" cellpadding="2" cellspacing="2" border="0">
			<tr>	
				<td colspan="2" align="center" class="reportlabel">(FOR OFFICE USE IN PENSION PRE-AUDIT CELL)</td>		
				
			</tr>
		 
			
		
			<tr>	
				<td colspan="2">The Input data sheet verified with reference to the application and the documents enclosed and found correct P.P.O may be generated throught Computer.</td>		
				
			</tr>
	
			
	  </table>	
	</td>
  </tr>
  <tr>
	<td>&nbsp;</td>
	</tr>
   <tr>
	<td>
		<table align="center" width="100%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td width="34%" align="center">CLERK</td>
				<td width="19%" align="center">SS</td>
				<td width="23%" align="center">A.A.O</td>
				<td width="24%" align="center">A.P.F.C.(PENSION)</td>
			</tr>
				<tr>
				<td width="34%" align="center">date</td>
				<td width="19%" align="center">date</td>
				<td width="23%" align="center">date</td>
				<td width="24%" align="center">date</td>
			</tr>
	  </table>	
	</td>
	</tr>
	<tr>
	  	<td>
		<table align="center" width="80%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td colspan="2"><hr /></td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
  	<td >
		<table align="center" width="80%" height="50" cellpadding="3" cellspacing="3" border="0">
			<tr>	
				<td colspan="2" align="center" class="reportlabel">(FOR OFFICE USE IN PENSION DISBURSEMENT SECTION)</td>		
				
			</tr>
		 	<tr>
				<td align="left" colspan="2" class="reportsublabel">P.P.O.No.</td>
			</tr>
			<tr>
				<td align="left" colspan="2" class="reportsublabel">Date of issue to the Bank</td>
			</tr>
			<tr>
				<td align="left" colspan="2" class="reportsublabel">Intimation sent to the claimant And also to Accounts Branch on</td>
			</tr>
	
	
			
	  </table>	
	</td>
  </tr>
  <tr>
	<td>&nbsp;</td>
	</tr>
   <tr>
	<td>
		<table align="center" width="100%" height="50" cellpadding="1" cellspacing="1" border="0">
			<tr>
				<td width="34%" align="center" class="reportsublabel">CLERK</td>
				<td width="19%" align="center" class="reportsublabel">SS</td>
				<td width="23%" align="center" class="reportsublabel">A.A.O</td>
				<td width="24%" align="center" class="reportsublabel">A.P.F.C.</td>
			</tr>
				<tr>
				<td width="34%" align="center" class="reportsublabel">date</td>
				<td width="19%" align="center" class="reportsublabel">date</td>
				<td width="23%" align="center" class="reportsublabel">date</td>
				<td width="24%" align="center" class="reportsublabel">date</td>
			</tr>
	  </table>	
	</td>
	</tr>

</table>
</body>
</html>
