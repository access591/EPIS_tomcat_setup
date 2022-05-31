

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.NomineeForm2Info,com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.NomineeBean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
   	 	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
  </head>
  
  <body background="body">
   <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
   <%
   		ArrayList personalList=new ArrayList();
	   ArrayList nomineeList=new ArrayList();
	   ArrayList familyList=new ArrayList();
   	if(request.getAttribute("form2Report")!=null){
   		NomineeForm2Info nomineeInfo=(NomineeForm2Info)request.getAttribute("form2Report");
   		personalList=nomineeInfo.getPersonalList();
   		nomineeList=nomineeInfo.getNomineeList();
   		familyList=nomineeInfo.getFamilyList();
   	}
   %>
   <tr>
		<td align="center">
			<table  border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
				<tr>
					<td ><img src="<%=basePath%>/images/logoani.gif" ></td>
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
    		<%
    			String empDesignation="";
    			if(personalList.size()!=0){
    			EmployeePersonalInfo personalInfo=null;
    			for(int k=0;k<personalList.size();k++){
    			personalInfo=(EmployeePersonalInfo)personalList.get(k);
    			empDesignation=personalInfo.getDesignation();
    		%>
    			<tr>
    				<td class="tblabel">1.Name (Full In Block letters):</td>
    				<td class="Data"><%=personalInfo.getEmployeeName().toUpperCase()%></td>	
    				<td  class="tblabel">9.Address (Permanent)	:</td>
    				<td class="Data"><%=personalInfo.getPerAddress()%></td>	
    			</tr>
    			<tr>
    				<td  class="tblabel">2.Father’s/Husband’s Name:</td>
    				<td class="Data"><%=personalInfo.getFhName()%></td>	
    		
    			</tr>
    			<tr>
    				<td  class="tblabel">3.Date of Birth:</td>
    				<td class="Data"><%=personalInfo.getDateOfBirth()%></td>	
    			</tr>
    			<tr>
    				<td  class="tblabel">4.Sex:</td>
    				<td class="Data"><%=personalInfo.getGender()%></td>	
    				<td  class="tblabel"> Address(Temporary):</td>
    				<td class="Data"><%=personalInfo.getTempAddress()%></td>
    		    </tr>
    		    <tr>
    				<td class="tblabel">5.Marital Status:</td>
    				<td class="Data"><%=personalInfo.getMaritalStatus()%></td>	
    		    </tr>
    		    <tr>
    				<td  class="tblabel">6.PF ID:</td>
    				<td class="Data"><%=personalInfo.getPfID()%></td>	
    				
    		    </tr>
    		    <tr>
    				<td  class="tblabel"> 7.CPF ACC.NO:</td>
    				<td class="Data"><%=personalInfo.getCpfAccno()%></td>	
    				<td  class="tblabel">10.Date of Joining in AAI:</td>
    				<td class="Data"><%=personalInfo.getDateOfJoining()%></td>	
    		    </tr>
    		       <tr>
    		       <td  class="tblabel"> 8.Employee No.:</td>
    				<td class="Data"><%=personalInfo.getEmployeeNumber()%></td>	
    				<td  class="tblabel">11.Designation:</td>
    				<td class="Data"><%=personalInfo.getDesignation()%></td>	
    				
    		    </tr>
    		   <%}}%>
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
    	
    			<%
    			if(nomineeList.size()!=0){
    			NomineeBean nomineeInfo=null;
    			for(int nb=0;nb<nomineeList.size();nb++){
    			nomineeInfo=(NomineeBean)nomineeList.get(nb);
    		%>
    			<tr>
    				<td class="Data"><%=nomineeInfo.getNomineeName()%></td>
    				<td class="Data"><%=nomineeInfo.getNomineeAddress()%></td>
    				<td class="Data"><%=nomineeInfo.getNomineeRelation()%></td>
    				<td class="Data"><%=nomineeInfo.getNomineeDob()%></td>
    				<td class="Data"><%=nomineeInfo.getTotalShare()%></td>
    				<td class="Data"><%=nomineeInfo.getNameOfGuardian()%><br><%=nomineeInfo.getGaurdianAddress()%></td>
    			</tr>
    			<%}}%>
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
    	<td align="right" class="Data">Designation:<%=empDesignation%></td>
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
    	
    			<%
    			if(familyList.size()!=0){
    			NomineeBean familyInfo=null;
    			int srlNO=0;
    			String familyAddress="---";
    			for(int fb=0;fb<familyList.size();fb++){
    			familyInfo=(NomineeBean)familyList.get(fb);
    			srlNO++;
    		%>
    			<tr>
    				<td class="Data"><%=srlNO%></td>
    				<td class="Data"><%=familyInfo.getFamilyMemberName()%></td>
    				<td class="Data"><%=familyAddress%></td>
    				<td class="Data"><%=familyInfo.getFamilyDateOfBirth()%></td>
    				<td class="Data"><%=familyInfo.getFamilyRelation()%></td>
    				
    			</tr>
    			<%}}%>
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
    
    			<%
    			
    			String familyAddress="---";
    			
    		%>
    			<tr>
    				<td class="Data"><%=familyAddress%></td>
    				<td class="Data"><%=familyAddress%></td>
    				<td class="Data"><%=familyAddress%></td>
    				<td class="Data"><%=familyAddress%></td>
    				<td class="Data"><%=familyAddress%></td>
    				
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
   </table>
  </body>
</html>
