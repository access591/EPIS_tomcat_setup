

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
            <td width="71%"><strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Application for Final Payment of CPF </strong></td>
          </tr>                 
          
        </table></td>
      </tr>
      
        <tr>
        <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="29%" rowspan="2">&nbsp;</td>
            <td width="71%"><strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Verify by the Personal Department)</strong></td>
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
            <td width="20%">&nbsp;</td>
            <td width="37%" class="reportsublabel" >Application No </td>
            <td>:</td>
            <td class="reportdata"><bean:write property="nssanctionno"  name="Form2ReportBean"/></td>
            <td width="10">&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">PF ID </td>
            <td>:</td>
            <td class="reportdata"><bean:write property="pfid"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>           
          </tr> 
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Employee Code  </td>
             <td>:</td>
            <td class="reportdata"><bean:write property="employeeNo"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Name of Member</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="employeeName"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Designation</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="designation"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">CPF A/c No</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="cpfaccno"  name="Form2ReportBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Date of Birth</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="dateOfBirth"  name="Form2ReportBean"/></td>            
            <td>&nbsp;</td>
          </tr>          
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Father's Name</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="fhName"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>
           
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Date of Joining in AAI</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="dateOfJoining"  name="Form2ReportBean"/></td>           
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
            <td class="reportsublabel">Permanent Address</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="permenentaddress"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>          
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Present Address/Postal Address</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="presentaddress"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Telephone NO</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="phoneno"  name="Form2ReportBean"/></td>            
            <td>&nbsp;</td>
          </tr>          
          
          <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">E-Mail</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="mailID"  name="Form2ReportBean"/></td>          
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Station </td>
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
            <td class="reportsublabel">Region</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="region"  name="Form2ReportBean"/></td>
          
            <td>&nbsp;</td>
          </tr>          
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Separation Reason</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="seperationreason"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>
          
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Date of separation reason</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="seperationdate"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>
          
             <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Reason for Resignation</td>
            <td>:</td>
            <td class="reportdata"><bean:write property="resignationreason"  name="Form2ReportBean"/></td>           
            <td>&nbsp;</td>
          </tr>
          
          <tr>
				<td>&nbsp;</td>
			</tr>
          
          
          <logic:equal property="resignationreason"  name="Form2ReportBean" value="otherdepartment">
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(a) Whether resigned for joining some other organization:&nbsp;Yes
            
            </td>
           
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
            <td class="reportsublabel" colspan="3">(b)  Whether gone through proper channel :            
            </td>            
            <td>&nbsp;</td>
          </tr>
          
          
          
             <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(C)  Presently working in : <bean:write property="organizationname"  name="otherDetBean"/> and
            <bean:write property="organizationaddress"  name="otherDetBean"/>
            
            </td>
        
            <td>&nbsp;</td>
          </tr>
          
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(D) Date of Appointment with present employer : <bean:write property="appointmentdate"  name="otherDetBean"/>
            
            </td>
         
            <td>&nbsp;</td>
          </tr>
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(E)Posted As :   <bean:write property="postedas"  name="otherDetBean"/>
            
            </td>
          
            <td>&nbsp;</td>
          </tr>
          
          
            <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel" colspan="3">(F)Place of Posting/working  :   <bean:write property="workingplace"  name="otherDetBean"/>
            
            </td>
           
            <td>&nbsp;</td>
          </tr>
          
          
          
          
          
          </logic:equal>   
          
          <tr>
				<td>&nbsp;</td>
			</tr>       
                      
			
		 <logic:notEqual property="paymentinfo"  name="Form2ReportBean" value="N">
			
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
            <td class="reportsublabel">Payment Desired At </td>
            <td>:</td>
            <td  class="reportdata"><bean:write property="city"  name="bankMasterBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Bank Name</td>
            <td>:</td>
            <td  class="reportdata"><bean:write property="bankname"  name="bankMasterBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Branch Name </td>
            <td>:</td>
            <td  class="reportdata"><bean:write property="branchaddress"  name="bankMasterBean"/></td>
            <td>&nbsp;</td>
          </tr>
          
           <tr>
            <td>&nbsp;</td>
            <td class="reportsublabel">Bank Account number</td>
            <td>:</td>
            <td  class="reportdata"> <bean:write property="banksavingaccno"  name="bankMasterBean"/></td>
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
				List of documents to be enclosed  &nbsp;
				
				
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
			        
          
           <tr>					
		   		<td>&nbsp;</td>																	
				<td  colspan="4">
				Certified that the above information filled up by the Applicant is correct as per the office records.&nbsp;
				</td>										
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			
		<logic:present name="transBean">	
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
      </logic:present>
      <logic:notPresent name="transBean">
      <tr>      
        <td height="22" colspan="4" class="reportsublabel"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>            
            <td width="21%">&nbsp;</td>
            <td width="27%" class="reportsublabel"> </td>
            <td width="2%">&nbsp;</td>
            <td width="24%" class="reportsublabel">&nbsp;</td>
            <td width="30%" nowrap="nowrap" align="right">Signature and Designation</td>           
          </tr>	        
        </table></td>
      </tr> 
      
       </logic:notPresent>	
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