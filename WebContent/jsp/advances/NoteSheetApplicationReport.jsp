

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>


<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
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
        <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="29%" rowspan="2"><img src="<%=basePath%>images/logoani.gif" width="75" height="48" align="right" /></td>
            <td width="71%"><strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  AIRPORTS  AUTHORITY OF INDIA</strong></td>
          </tr>
          
        </table></td>
      </tr>
     
      <logic:present name="Form2ReportBean" >
    
      <tr>
      <td>&nbsp;</td>
      </tr>
      
      <tr>
      <td colspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>         
            
          <td>
          
           <tr>
            <td width="20%">&nbsp;</td>
            <td width="37%" class="reportsublabel">Application No :&nbsp;<bean:write property="nssanctionno"  name="Form2ReportBean"/></td>
            <td width="2%"></td>
            <td width="34%" class="reportsublabel">PF ID :<bean:write property="pfid"  name="Form2ReportBean"/></td>
            <td width="10">&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">CPF A/c No :&nbsp;<bean:write property="cpfaccno"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">Father's Name :<bean:write property="fhName"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Employee Code :&nbsp;<bean:write property="employeeNo"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">Date of Joining in AAI :<bean:write property="dateOfJoining"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Name :&nbsp;<bean:write property="employeeName"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">Date of Birth :<bean:write property="dateOfBirth"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Designation :&nbsp;<bean:write property="designation"  name="Form2ReportBean"/></td>
            <td></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Permanent Address :&nbsp;<bean:write property="permenentaddress"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">Present Address/Postal Address :<bean:write property="presentaddress"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Telephone NO :&nbsp;<bean:write property="phoneno"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">E-Mail :<bean:write property="mailID"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Station :&nbsp;
            
            
           
            
            
             <logic:equal name="Form2ReportBean" property="airportcd" value="Office Complex">
             CHQ
            </logic:equal>
            <logic:equal name="Form2ReportBean" property="airportcd" value="Csia Iad">
            Mumbai Airport
            </logic:equal>
              <logic:equal name="Form2ReportBean" property="airportcd" value="Delhi">
            New Delhi
            </logic:equal>
               <logic:equal name="Form2ReportBean" property="airportcd" value="Igi Iad">
            IGI Airport New Delhi
            </logic:equal>
            
             <logic:equal name="Form2ReportBean" property="airportcd" value="Igicargo Iad">
            IGI Airport Cargo, New Delhi
            </logic:equal>
            
            <logic:equal name="Form2ReportBean" property="airportcd" value="Chennai Iad">
            Chennai
            </logic:equal>
            
            <logic:notEqual name="Form2ReportBean" property="airportcd" value="Office Complex">
            <logic:notEqual name="Form2ReportBean" property="airportcd" value="Csia Iad">
             <logic:notEqual name="Form2ReportBean" property="airportcd" value="Delhi">
              <logic:notEqual name="Form2ReportBean" property="airportcd" value="Igi Iad">
               <logic:notEqual name="Form2ReportBean" property="airportcd" value="Igicargo Iad">
                 <logic:notEqual name="Form2ReportBean" property="airportcd" value="Chennai Iad">
               <bean:write name="Form2ReportBean" property="airportcd"/>
            </logic:notEqual>
            </logic:notEqual>
             </logic:notEqual>
            </logic:notEqual>
            </logic:notEqual>
            </logic:notEqual>
            
            
            </td>
            <td></td>
            <td  class="reportsublabel">Region :<bean:write property="region"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Separation Reason :&nbsp;<bean:write property="seperationreason"  name="Form2ReportBean"/></td>
            <td></td>
            <td  class="reportsublabel">Date of separation reason :<bean:write property="seperationdate"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
                   
          <tr>
				<td>&nbsp;</td>
			</tr>
          
		   
			
		 <logic:present name="postingDetList">
		 
		 <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				Details of Posting  His member since his  date of appointment &nbsp;
				</td>										
			</tr>
			
			<tr>
			<td>&nbsp;</td>
			</tr>
			
		  <tr>     
          
            <td colspan="5">
            <table width="60%" align="center"  border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td>      
         
            <td class="reportContentdata" width="5%" rowspan="2"  align="center">SNo</td>
            <td class="reportContentdata" width="25%" rowspan="2" align="center">OLD CPF A/C NO</td>
            <td class="reportContentdata" width="10%" colspan="2" align="center">PERIOD</td>
            <td class="reportContentdata" width="10%" rowspan="2" align="center">REGION</td>
            <td class="reportContentdata" width="10%" rowspan="2" align="center">STATION  </td>            
            <td class="reportContentdata" width="20%" rowspan="2" align="center">POST HELD</td>
            <td class="reportContentdata" width="20%" rowspan="2" align="center">REMARKS</td>                       
            </td>
            </tr>
            
            <tr>
                <td>&nbsp;</td>
				<td width="17%">From Year</td>
				<td width="15%">To Year</td>
				</tr>
            
            </table>
            </td>                                        
          </tr>      
          
          <tr>
			<td>&nbsp;</td>
			</tr>  
			
		 <logic:iterate id="sorder" name="postingDetList" indexId="slno">
          <tr>     
          
            <td colspan="5">
            <table width="60%" align="center"  border="0" cellpadding="0" cellspacing="0">
            <tr>
            <td>        
            <td class="reportContentdata" width="5%"><%=slno.intValue()+1%></td>
            <td class="reportContentdata" width="20%" align="center"><bean:write name="sorder" property="cpfaccno"/></td>
            <td class="reportContentdata" width="15%" align="center"><bean:write name="sorder" property="fromYear"/></td>            
            <td class="reportContentdata" width="15%" align="center"><bean:write name="sorder" property="toYear"/></td>            
            <td class="reportContentdata" width="10%" align="center"><bean:write name="sorder" property="region"/></td>
            <td class="reportContentdata" width="10%" align="center"><bean:write name="sorder" property="airportcd"/></td>
            <td class="reportContentdata" width="10%" align="center"><bean:write name="sorder" property="postheld"/></td>
            <td align="right" class="reportContentdata" width="15%" align="center"><bean:write name="sorder" property="remarks"/></td>            
            </td>
            </tr>
            </table>
            </td>
                                        
          </tr>        
         </logic:iterate>
         
         </logic:present>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			
			<logic:notEqual property="banksavingaccno"  name="bankMasterBean" value="">
			
			<tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				Bank Detail For Payment &nbsp;
				</td>										
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			
			  <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Name as per saving :&nbsp;<bean:write property="bankempname"  name="bankMasterBean"/></td>
            <td></td>
            <td  class="reportsublabel">A/c No :<bean:write property="banksavingaccno"  name="bankMasterBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Name Of Bank :&nbsp;<bean:write property="bankname"  name="bankMasterBean"/></td>
            <td></td>
            <td  class="reportsublabel">Branch (Full Address) :<bean:write property="branchaddress"  name="bankMasterBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">IFSC Code Of Bank :&nbsp;<bean:write property="bankemprtgsneftcode"  name="bankMasterBean"/></td>
            <td></td>
            <td  class="reportsublabel">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          
          	<tr>
				<td>&nbsp;</td>
			</tr>
			
			</logic:notEqual>
			
			
			<logic:equal property="seperationreason"  name="Form2ReportBean" value="Death">
			
			  <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				PARTICULARS OF CLAIMANT (AS PER NOMINATION) &nbsp;
				</td>										
			</tr>
			
			<tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				(TO BE FILLED IN BY MAJOR NOMINEE/LEGAL HEIR OF DECEASED MEMBER)&nbsp;
				</td>										
			</tr>
			
			<tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				(COPY OF DEATH CARTIFICATE TO BE ATTACHED)&nbsp;
				</td>										
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
          
          
            <tr>
            <td width="10%">&nbsp;</td>
            <td class="reportsublabel" width="30%">Name Of The  Claimant(IN BLOCK LETTERS) &nbsp;</td>
            <td width="5%">: &nbsp;</td>
            <td colspan="2"><bean:write property="employeeName"  name="Form2ReportBean"/></td>          
          	</tr>
          	
          	
          	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Father's/Husband's Name &nbsp;</td> 
            <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="fhName"  name="Form2ReportBean"/></td>                                 
          	</tr>
          	
          	
          	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Age(As On The Date Of Death Of The Member) Of Bank &nbsp;</td>
            <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="empage"  name="empNoteSheetPersonalInfoBean"/></td>
            </tr>
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Marital Status ( On The Date Of Death Of Member)&nbsp;</td>
             <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="maritalstatus"  name="empNoteSheetPersonalInfoBean"/></td>
             </tr> 
            
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Relationship With Deceased &nbsp;</td>
            <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="emprelation"  name="empNoteSheetPersonalInfoBean"/></td>          
           	</tr>
           	
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Address:&nbsp;</td>
            <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="empage"  name="empNoteSheetPersonalInfoBean"/></td>          
           	</tr>
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Whether member has been alloted the govt. quarter by the department &nbsp;</td>
             <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="quarterallotment"  name="empNoteSheetPersonalInfoBean"/></td>           
           	</tr>
           	
           	<logic:equal property="quarterallotment"  name="empNoteSheetPersonalInfoBean" value="Y">
           	
           		<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Quarter No &nbsp;</td>
            <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="quarterno"  name="empNoteSheetPersonalInfoBean"/></td>                       
           	</tr>
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Name of colony &nbsp;</td>
             <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="colonyname"  name="empNoteSheetPersonalInfoBean"/></td>                        
           	</tr>
           	
           	
           	<tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Station &nbsp;</td>
             <td>: &nbsp;</td>
            <td colspan="2"><bean:write property="empstation"  name="empNoteSheetPersonalInfoBean"/></td>            
           	</tr>
           	
           	</logic:equal>
           
           	
           	<tr>
				<td>&nbsp;</td>
			</tr>
           	
			
			</logic:equal>
          
           <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				CERTIFIED THAT THE PARTICULARS ARE TRUE TO THE BEST OF MY KNOWLEDGE. &nbsp;
				</td>										
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			<bean:define id="loddocument" property="lodInfo" name="Form2ReportBean"/>
			 <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				List of Documents for <bean:write property="seperationreason"  name="Form2ReportBean"/>  &nbsp;
				</td>										
			</tr>
			
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td  colspan="3" nowrap="nowrap"><%=loddocument%></td>										
				</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
          
          </td>   
          
          </tr>
          </table>
      </td>
      </tr>
     
     
      </logic:present>
       
      <tr>
      
        <td height="22" colspan="2" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          
                  
          <logic:equal name="advanceBean" property="frmName" value="FSAppForm">
								<tr>            
            <td width="20%">&nbsp;</td>
            <td width="37%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="34%" class="reportsublabel">Signature of applicant</td>
            <td width="10">&nbsp;</td>           
          </tr>
		  </logic:equal>
										
		<logic:notEqual name="advanceBean" property="frmName" value="FSAppForm">
	
		
		<tr>
		 <td width="20%">&nbsp;</td>
            <td width="37%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="34%" class="reportsublabel">Authorized Signature</td>
            <td width="10">&nbsp;</td>  
		</tr>
		</logic:notEqual>
         
        </table></td>
      </tr>
      <tr>
        <td class="screenlabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="50%" class="screenlabel">&nbsp;</td>
        <td width="50%">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
		</html:form>
	</body>
</html>