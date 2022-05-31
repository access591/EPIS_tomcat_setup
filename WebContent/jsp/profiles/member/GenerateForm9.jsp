
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body  ><br><FIELDSET ><LEGEND class="epis">Form-9</LEGEND>
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">

   <tr>
		<td align="center">
			<table  border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
				<tr>
					<td ><img src="<%=basePath%>images/aimslogo.gif" ></td>
					<td class="label" align=center valign="top" nowrap="nowrap"><font color='black' size='4' face='Helvetica'>
						AIRPORTS AUTHORITY OF INDIA</font></td>
				</tr>
			</table>
		</td>
	</tr>
	<TR>
		<td>
			<table  width="100%"  cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td align="center">FORM 9</td>
			</tr>
			<tr>
				<td align="center">(For Exempted Establishment only)</td>
			</tr>
			<tr>
				<td align="center">(Paragraph 24 of the Employees’ Pension Scheme, 1995)</td>
			</tr>
			<tr>
				<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			</table>
		</td>
    </TR>
    
    <tr>
    	<td>
    		<table cellpadding="1" cellspacing="2" width="100%" align="center">
    	     <TR>
	    		<td class="reportlabel" align="center">DECLARATION BY A PERSON TAKING UP EMPLOYMENT IN THE ESTABLISMENT</td>
    		</TR>
   			 <TR>
	   			 <td><span class="Data">I </span> <span class="label"> </span> <span class="Data">S/o W/o Daughter of</span> <span class="label"></span>  <span class="Data">do hereby  solemnly</span> </td>
  			 </TR>
    		  <TR>
	   			 <td class="Data">declare that :-</td>
   			 </TR>
   			 <TR>
	   			 <td>a)	I was employed in M/s……………………………(Name and full address of the establishment) with PF A/c No  <span class="Data"> </span> and left service on  <span class="Data"></span> prior to that, I was employed in……………….with PF A/c No  <span class="Data">__________________________</span>  from  <span class="Data">_______________</span> to  <span class="Data">_______________</span></td>
   			 </TR>
   			 <TR>
	   			 <td>b)	I am a member of the Pension Fund from  <span class="Data">_______________</span> to  <span class="Data">_______________</span> and copy of the Scheme Certificate is enclosed.</td>
   			 </TR>
   			 <TR>
	   			 <td class="label">c)	I have/ have not withdrawn the amount of my Provident Fund/ Pension Fund.</td>
   			 </TR>
   			  <TR>
	   			 <td class="label">d)	I have/ have not drawn any benefits under the Employees’ Pension Scheme, 1995 in respect of my past service, in any establishment.</td>
   			 </TR>
   			  <TR>
	   			 <td class="label">e)	I have/ have never been a member of any Provident Fund and/ or Pension Fund. </td>
   			 </TR>
   			 	<tr>
				<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
    		</table>
    	</td>
    </tr>
    <tr>
    	<td> &nbsp; &nbsp; &nbsp; &nbsp;</td>
    </tr>
	<tr>
		<td align="left">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td  class="label">Date…………………</td>
					<td  class="label" align=right> *Signature or left hand thumb impression of the employee</td>
				</tr>
				<tr>
					
					<td  class="label">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td  class="label">Encl. Copy of Scheme Certificate</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
				<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td align="center">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="center" class="reportlabel" >(To be filled by the Employer)</td>
				
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
						  <TR>
	   						 <td><span class="label">(1)	Shri</span> <span class="Data"> </span> is appointed as  <span class="Data"></span> in M/s …………………………..…………………………………………….(Name of the Factory/ Establishment ) with effect from <span class="Data"></span> .</td>
   						 </TR>
   						 <TR>
	   						 <td class="label">(2)	Copy of the Scheme Certificate is enclosed.</td>
   						 </TR>
   						  <TR>
	   						 <td class="label">(3)	Declaration and nomination in Form 2 is enclosed.</td>
   						 </TR>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
    <tr>
		<td align="left">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="label">Date…………………</td>
					<td class="label">Signature of the Employer or Manager or other Authorized Officer</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label">*Left hand thumb impression in the case of illiterate male member and right hand thumb impression by illiterate female member.</td>
				</tr>
			</table>
		</td>
	</tr>
	    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td align="center"><input type="button" value="Print" onclick="javascript:window.open('<%=basePath%>jsp/profiles/member/Form9Report.jsp')" ></td></tr>
   </table>
	</FIELDSET >
	</body>
</html>
