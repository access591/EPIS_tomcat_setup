
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
	<body  ><br><FIELDSET ><LEGEND class="epis">Form-2</LEGEND>
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
		<td align="right">Form 2 (Revised)</td>
    </TR>
     <TR>
	    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </TR>
    <TR>
	    <td class="tblabel">
    		 Nomination and Declaration Form for Unexampled/Exempted Establishments/Declaration and Nomination Form under the Employees’ Provident Fund & Employees Pension
		     Scheme’ {Paragraph 33 & 61(1) of the Employees Provident Fund Scheme, 1952 & Paragraph 18 of the Employees’ Pension Scheme, 1995}
    	</td>
    </TR>
    <TR>
	    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </TR>
    <tr>
    	<td>
    		<table cellpadding="0" cellspacing="0" width="100%">
    		
     		</table>
    	</td>
    </tr>
    <TR>
	    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </TR>
    <TR>
		<td align="center">PART-A (EPF)</td>
    </TR>
       <TR>
	    <td class="tblabel">
    		 I hereby nominate the person(s)/cancel the nomination made by me previously and nominate the persons(s), mentioned below to receive the amount standing to my credit in 
    		 the Employees’ Provident Fund, in the event of my death.
    	</td>
    </TR>
    <tr>
    	<td>
    		<table cellpadding="0" cellspacing="0" border="1"  width="100%">
    		
    			<tr>
    				<td class="tblabel">Name of the<br> Nominee/Nominees</td>
    				<td class="tblabel" align="center">Address</td>
    				<td class="tblabel">Nominee’s Relationship<br/> with the member</td>
    				<td class="tblabel" align="center">Date of Birth</td>
    				<td class="tblabel">Total amount or share of<br> accumulations in Provident Fund <br>to be paid to each nominee</td>
    				<td class="tblabel">If the Nominee is a minor, name & relationship<br> & address of the guardian who may receive<br> the amount during the minority of nominee</td>
    			</tr>
    	
    			
    		</table>
    	</td>
    </tr>
    <TR>
	    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </TR>
    <tr>
    	<td class="Data">1.	Certified that I have no family as defined in Para 2(g) of the Employees’ Provident Fund Scheme, 1952 and should I acquire a family hereafter the above nomination should be deemed as cancelled.</td>
    </tr>
    <tr>
    	<td class="Data">2.	Certified that my father/mother is/are dependent upon me.</td>
    </tr>
     <tr>
    	<td class="Data">*Strike out whichever is not applicable.</td>
    </tr>
    <tr>
    	<td align="right">______________________________________</td>
    </tr>
    <tr>
    	<td align="right" class="Data">Signature or thumb impression of the subscriber</td>
    </tr>
    <tr>
    	<td align="right" class="Data">Designation:</td>
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td align="center">(Para 18)</td>
    </tr>
    <tr>
    	<td align="center">PART-B (EPS)</td>
    </tr>
    <tr>
    	<td class="Data">I hereby furnish below particulars of the members of my family who should be eligible to receive widow/children Pension in the event of my death.</td>
    </tr>
     <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
     <tr>
    	<td>
    		<table cellpadding="0" cellspacing="0" border="1"  width="100%"	>
    		
    			<tr>
    				<td class="tblabel">S.No</td>
    				<td class="tblabel">Name of the family Member</td>
    				<td class="tblabel">Address</td>
    				<td class="tblabel">Date of Birth</td>
    				<td class="tblabel">Relationship with the member</td>
    				
    			</tr>
    	
    			
    		</table>
    	</td>
    </tr>
     <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td class="Data">*Certified that I have no family, as defined in Para 2(vii) of Employees Pension Scheme, 1995 and should I acquire a family hereafter I shall furnish particulars thereon in the above form.</td>
    </tr>
    <tr>
    	<td class="Data">I hereby nominate the following persons for receiving the monthly widow pension (admissible under Para 16 (2)(a)(i) & (ii) in the event of my death without  leaving any eligible family member receiving pension</td>
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td>
    		<table cellpadding="0" cellspacing="0" border="1" width="100%">
    		
    			<tr>
    				<td class="tblabel">S.No</td>
    				<td class="tblabel">Name of the family Member</td>
    				<td class="tblabel">Address</td>
    				<td class="tblabel">Date of Birth</td>
    				<td class="tblabel">Relationship with the member</td>
    				
    			</tr>
    
    			
    			<tr>
    				<td class="Data">---</td>
    				<td class="Data">---</td>
    				<td class="Data">---</td>
    				<td class="Data">---</td>
    				<td class="Data">---</td>
    				
    			</tr>
    			
    		</table>
    	</td>
    </tr>
    <tr>
    	<td>
    			<table cellpadding="0" cellspacing="0" >
    				<tr>
    					<td class="tblabel">Date</td>
    					<td class="Data" ></td>
    					<td class="tblabel" colspan="3">*****Strike out whichever is not applicable</td>
    				</tr>
    			</table>
    	</td>
    </tr>
        <tr>
    	<td align="right">______________________________________</td>
    </tr>
    <tr>
    	<td align="right" class="Data">Signature or thumb impression of the subscriber</td>
    </tr>
     <tr>
    	<td align="center" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
     <tr>
    	<td align="center" class="tblabel">CERTIFICATE BY EMPLOYER</td>
    </tr>
    <tr>
    	<td>Certified that the above declaration and nomination has been signed/thumb impressed before me by .......................................... employed in my establishment
    	 after he/she has read the entries/entries have been read over to him/her by me and got confirmed by him/her.</td>
    	
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
    	<td>
    		<table width="100%" cellpadding="0" cellspacing="0">
   				 <tr>
    				<td class="Data">Place:</td>
    				<td class="Data"></td>
    				<td class="Data" align="right"> Signature of the employer (i.e, APD/HOD) or other authorized Officers of the establishment </td>
    			</tr>
    		</table>
    	</td>
    </tr>
    <tr>
    	<td>
    		<table width="100%" cellpadding="0" cellspacing="0">
    			<tr>
    		    	<td class="Data">Date:</td>
    				<td class="Data"></td>
    				<td class="Data">Designation:</td>
    				<td class="Data"></td>
    				<td class="Data" align="right">Name & Address of the Factory/Establishment</td>
    		     </tr>
    		</table>
    	</td>

    	
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td align="center"><input type="button" value="Print" onclick="javascript:window.open('<%=basePath%>jsp/profiles/member/Form2Report.jsp')" ></td></tr>
   </table>
	</FIELDSET >
	</body>
</html>
