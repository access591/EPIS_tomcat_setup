

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
            <td width="71%"><strong> AIRPORTS AUTHORITY OF INDIA </strong></td>
          </tr>                 
          
          <tr>
            <td width="29%"><strong> Application for Final Payment of CPF </strong></td>
            <td width="71%">&nbsp;</td>
          </tr>                
          
        </table></td>
      </tr>
      
        <tr>
        <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="23%" rowspan="2">&nbsp;</td>
            <td width="71%"><strong>(To be filled by the Member/legal heir/Nominee in Triplicate)</strong></td>
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
            <td width="37%" class="reportsublabel">&nbsp;</td>
            <td width="2%"></td>
            <td width="34%" class="reportsublabel" align="center">Date:&nbsp;<bean:write property="advtransdt"  name="Form2ReportBean"/></td>
            <td width="10">&nbsp;</td>
          </tr>
          
          <tr>
          <td>&nbsp;</td>
          </tr>
          
            <tr>
			<td width="15%">&nbsp;</td>
			<td width="30%" class="reportsublabel">	Application No </td>
			<td width="3%">:</td>
			<td width="37%" class="reportdata"><bean:write name="Form2ReportBean" property="nssanctionno" /></td>
			<td width="15%">&nbsp;</td>
		</tr>
		
		 <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">	PF ID </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="pfid" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Employee Code </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="employeeNo" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Name of Member</td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="employeeName" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Designation </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="designation" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">CPF A/c No </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="cpfaccno" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Date of Birth </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="dateOfBirth" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Father's Name </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="fhName" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Date of Joining in AAI </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="dateOfJoining" /></td>
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
			<td  class="reportsublabel">Permanent Address </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="permenentaddress" /></td>
			<td>&nbsp;</td>
		</tr>
		
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Present Address/Postal Address </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="presentaddress" /></td>
			<td>&nbsp;</td>
		</tr>
		
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Telephone NO </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="phoneno" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">E-Mail </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="mailID" /></td>
			<td>&nbsp;</td>
		</tr>
           
        <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Station </td>
			<td>:</td>
			<td class="reportdata">
			
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
			<td>&nbsp;</td>
		</tr>
           
        <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Region </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="region" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Separation Reason </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="seperationreason" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Date of separation reason </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="Form2ReportBean" property="seperationdate" /></td>
			<td>&nbsp;</td>
		</tr>
		
		
         
          <logic:equal property="quarterallotment"  name="empNoteSheetPersonalInfoBean" value="Y">
          
                      
          <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Whether Member has been allotted the Govt quarter by the department </td>
			<td>:</td>
			<td class="reportdata"><bean:write name="empNoteSheetPersonalInfoBean" property="quarterallotment" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Quarter Name</td>
			<td>:</td>
			<td class="reportdata"><bean:write name="empNoteSheetPersonalInfoBean" property="quarterno" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Name of colony</td>
			<td>:</td>
			<td class="reportdata"><bean:write name="empNoteSheetPersonalInfoBean" property="colonyname" /></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Station</td>
			<td>:</td>
			<td class="reportdata"><bean:write name="empNoteSheetPersonalInfoBean" property="empstation" /></td>
			<td>&nbsp;</td>
		</tr>
		              
          
          
          </logic:equal>
                           
          
          <logic:equal property="resignationreason"  name="Form2ReportBean" value="otherdepartment">
          
           
          
          <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(a) Whether resigned for joining some other organization</td>
			<td>:</td>
			<td class="reportdata">Yes</td>
			<td>&nbsp;</td>
		  </tr>
          
             <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(The copy of form 13 is to enclosed for transfer of CPF)            
            </td>        
            <td>&nbsp;</td>
          </tr>
          
            <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(b)  Whether gone through proper channel </td>
			<td>:</td>
			<td class="reportdata">&nbsp;</td>
			<td>&nbsp;</td>
		  </tr>
          
         <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(C)  Presently working in</td>
			<td>:</td>
			<td class="reportdata"><bean:write property="organizationname"  name="otherDetBean"/> and
            <bean:write property="organizationaddress"  name="otherDetBean"/></td>
			<td>&nbsp;</td>
		</tr>
               
          <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(D) Date of Appointment with present employer</td>
			<td>:</td>
			<td class="reportdata"><bean:write property="appointmentdate"  name="otherDetBean"/>
			<td>&nbsp;</td>
		</tr>
		
		  <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(E)Posted As</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="postedas"  name="otherDetBean"/>
			<td>&nbsp;</td>
		</tr>
               
             
		  <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">(F)Place of Posting/working </td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="workingplace"  name="otherDetBean"/>
			<td>&nbsp;</td>
		</tr>
                            
          </logic:equal>   
                  
                     
			
		 <logic:notEqual property="paymentinfo"  name="Form2ReportBean" value="N">	
		 
		   <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Payment Desired At</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="city"  name="bankMasterBean"/>
			<td>&nbsp;</td>
		</tr>	
		
		 <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Bank Name</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="bankname"  name="bankMasterBean"/>
			<td>&nbsp;</td>
		</tr>	
		
		
		 <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Branch Name</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="branchaddress"  name="bankMasterBean"/>
			<td>&nbsp;</td>
		</tr>	
		
		 <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Bank Account number</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="banksavingaccno"  name="bankMasterBean"/>
			<td>&nbsp;</td>
		</tr>
		
		 <tr>
			<td>&nbsp;</td>
			<td  class="reportsublabel">Mode of Payment</td>
			<td>:</td>
			<td class="reportdata"> <bean:write property="bankpaymentmode"  name="bankMasterBean"/>
			<td>&nbsp;</td>
		</tr>		
		            
          
          	<tr>
				<td>&nbsp;</td>
			</tr>
			
			</logic:notEqual>
			
			<logic:notEqual property="lodInfo" name="Form2ReportBean" value="---">
			
			<bean:define id="loddocument" property="lodInfo" name="Form2ReportBean"/>
			 <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				<strong>List of documents to be enclosed</strong>  &nbsp;
				
				
				</td>										
			</tr>
			
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td  colspan="3" nowrap="nowrap"><%=loddocument%></td>										
				</tr>
			
			
			</logic:notEqual>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			        
          <logic:equal  property="frmName"  name="advanceBean" value="FSAppForm">
          
           <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				Certified that the Particulars are true to the best of my knowledge. &nbsp;
							
				</td>										
			</tr>
          
          </logic:equal>
          
          <logic:equal  property="frmName"  name="advanceBean" value="FSFormIReport">
          
           <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				Certified that the above information filled up by the Applicant is correct as per the office records.&nbsp;
				</td>										
			</tr>
          
          </logic:equal>
          
			
			      
      <tr>
      <td>&nbsp;</td>
      </tr>
      
      <tr>
      <td>&nbsp;</td>
      </tr>
      
       <logic:equal  property="frmName"  name="advanceBean" value="FSAppForm">
      
            
      <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">Date:&nbsp;<bean:write property="advtransdt"  name="Form2ReportBean"/></td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">Signature or left/right thumb</td>           
          </tr>	        
        </table></td>
      </tr> 
      
      <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">impression of the member</td>           
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
        <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="29%" rowspan="2">&nbsp;</td>
            <td width="71%" align="center"><strong>Advance stamped receipt</strong></td>
          </tr>                 
          
        </table></td>
      </tr>
      
        <tr>
        <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="29%" rowspan="2">&nbsp;</td>
            <td width="71%" align="center">(To be furnished only in case of 8(b) above)</td>
          </tr>                 
          
        </table></td>
      </tr>
      
       <tr>
      <td>&nbsp;</td>
      </tr> 
      
       <tr>
        <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td width="19%" rowspan="2">&nbsp;</td>
            <td width="71%">Received a sum of Rs. * …………………………….. (Rupees* ………………………… only) from Regional Provident
             Fund Commissioner/Officer-in-charge of Sub-Accounts office …………… by deposit in my savings bank account 
             towards the settlement of my Provident Fund Account. </td>           
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
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">Signature or left/right thumb</td>           
          </tr>	        
        </table></td>
      </tr> 
      
      <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">impression of the member</td>           
          </tr>	        
        </table></td>
      </tr> 
      
      </logic:equal>
      
     <logic:present name="transBean">
      <logic:equal  property="frmName"  name="advanceBean" value="FSFormIReport">
        <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">Date:&nbsp;<bean:write property="transApprovedDate"  name="transBean"/></td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">Signature and Designation</td>           
          </tr>	        
        </table></td>
      </tr> 
      
       <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">of Station-In-charge/Manager(P&A)</td>           
          </tr>	        
        </table></td>
      </tr> 
      
      </logic:equal>     
      </logic:present> 
      
      <logic:notPresent name="transBean">
      <logic:equal  property="frmName"  name="advanceBean" value="FSFormIReport">
        <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel"></td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">Signature and Designation</td>           
          </tr>	        
        </table></td>
      </tr> 
      
       <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel">&nbsp;</td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">of Station-In-charge/Manager(P&A)</td>           
          </tr>	        
        </table></td>
      </tr> 
      
      </logic:equal>     
      </logic:notPresent>
      
    	          
          </td>   
          
          </tr>
          </table>
      </td>
      </tr>    
      </logic:present>
       
      
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