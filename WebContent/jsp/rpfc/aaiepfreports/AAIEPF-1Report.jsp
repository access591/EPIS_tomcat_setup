<%@ include file="/jsp/rpfc/common/inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
<title>AAI</title>
</head>

<body>
<%
String reportType="",fileName="",filePrefix="",filePostfix="";
 		ArrayList AAIEPF1List=new ArrayList();
        ArrayList AAIEPF1List2=new ArrayList();
         ArrayList AAIEPF1PendingList1=new ArrayList();
        AAIEPFReportBean AAIEPF1Bean=new AAIEPFReportBean();
        AAIEPFReportBean AAIEPF1Bean2=new AAIEPFReportBean();
        AAIEPFReportBean AAIEPF1Bean3=new AAIEPFReportBean();
        DecimalFormat df = new DecimalFormat("#########0");
        
if(request.getAttribute("AAIEPF1List")!=null){
if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-1_Report";
					fileName=filePrefix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<table width="1178" border="0">
  <tr>
    <td width="5">&nbsp;</td>
    <td width="1">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="135">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="38">&nbsp;</td>
    <td width="240">&nbsp;</td>
    <td width="14">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td colspan="2"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="21" colspan="2" width="177">&nbsp;</td>
      </tr>
    </table></td>
    <td width="360">&nbsp;</td>
  </tr>
  
  <tr>
    <td colspan="7" rowspan="2"><img src="<%=basePath%>images/logoani.gif"  width="75" height="55" align="right" /></td>
    <td nowrap="nowrap" rowspan="2">AIRPORTS AUTHORITY OF INDIA</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>

  </tr>
  <tr>
    <td nowrap="nowrap"></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
 
  </tr>
  
  <tr>
    <td colspan="13"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="620" align="right">EMPLOYEES PROVIDENT FUND</td>
      </tr>
    </table></td>
    
    
    
    
  </tr>
 
  <tr>
    <td colspan="13"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="700" align="right">SCHEDULE OF CPF&nbsp; OPENING BALANCES AS ON&nbsp;&nbsp; <%=request.getAttribute("finYear")%></td>
      </tr>
    </table></td>
    
    
    
    
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="271">&nbsp;</td>

  </tr>
  <tr>
    <td height="27" colspan="9"><table cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td height="21" colspan="9" width="255">Unit Name:- <%=request.getAttribute("region")%></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>    
    <td>&nbsp;</td>    
    <td align="right">Form no:&nbsp;    AAIEPF-1</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="13"><table width="1166" border="1"  cellpadding="0" cellspacing="0">
      <tr>
        <td width="47" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="48" />
          <tr height="17">
            <td rowspan="2" height="71" width="48" valign="top" class="reportsublabel"><div align="center">Sl No</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" width="47"  valign="top" class="reportsublabel"><div align="center">PF ID</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel"><div align="center">CPF A/c No (Old)</div></td>
        <td  rowspan="2" width="62"  valign="top" class="reportsublabel"><div align="center">Emp No</div></td>
        <td  rowspan="2" width="122" valign="top" class="reportsublabel"><div align="center">Name of the Member</div></td>
        <td  rowspan="2" width="56" valign="top" class="reportsublabel" align="center" >Desig</td>
        <td  rowspan="2" width="118" valign="top" class="reportsublabel"><div align="center">Father's Name/ Husband name in case of Married women</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel" nowrap="nowrap"><div align="center">Date of birth</div></td>
        <td colspan="2" height="26"  valign="top" class="reportsublabel"><div align="center">Opening balances as on <%=request.getAttribute("dispOBYear")%></div></td>
        <td width="128" rowspan="2"  valign="top" class="reportsublabel"><table cellspacing="0" cellpadding="0">
          <col width="109" />
          <tr height="17">
            <td rowspan="2" height="71" width="109"  valign="top" class="reportsublabel"><div align="center">CPF Advance outstanding balance as on  <%=request.getAttribute("dispOBYear") %></div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" align="center"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="68" />
          <tr height="17">
            <td rowspan="2" align="center" width="68" valign="top"><div align="center" class="reportsublabel">Station&nbsp;</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td width="62" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="69" />
          <tr height="17">
            <td rowspan="2" height="71" width="69"  valign="top" class="reportsublabel"><div align="center">Remarks</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
      </tr>
      <tr>
        <td width="91"  valign="top"><div align="center">Subscription</div></td>
        <td width="136"  valign="top"><div align="center">Employer Contribution</div></td>
        </tr>
      <tr>
        <td><div align="center">1</div></td>
        <td><div align="center">2</div></td>
        <td><div align="center">2A</div></td>
        <td><div align="center">3</div></td>
        <td><div align="center">4</div></td>
        <td><div align="center">5</div></td>
        <td><div align="center">6</div></td>
        <td><div align="center">7</div></td>
        <td><div align="center">8</div></td>
        <td><div align="center">9</div></td>
        <td><div align="center">10</div></td>
        <td><div align="center">11</div></td>
        <td><div align="center">12</div></td>
      </tr>
      <%
                  
        AAIEPF1List=(ArrayList)request.getAttribute("AAIEPF1List");
        System.out.println("--------AAIEPF1List Size in JSP----------"+AAIEPF1List.size());
        int slno=1;
       
        double subGrandTotal=0.0,contGrandTotal=0.0,outstandGrandTotal=0.0;
        
        for(int i=0;i<AAIEPF1List.size();i++){
        
        AAIEPF1Bean=(AAIEPFReportBean)AAIEPF1List.get(i);
    
        
        %>
         <tr>
        <td width="48" class="Data"><%=slno%></td>
        <td width="47" class="Data"><%=AAIEPF1Bean.getPensionNo()%></td>        
        <td width="77" class="Data"><%=AAIEPF1Bean.getCpfAccno()%></td>
        <td width="62" class="Data"><%=AAIEPF1Bean.getEmployeeNumber()%></td>
        <td width="122" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean.getEmployeeName()%></td>
        <td width="56" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean.getDesignation()%></td>
        <td width="118" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean.getFhName()%></td>
        <td width="77" class="Data"><%=AAIEPF1Bean.getDateOfBirth()%></td>
        <td width="91" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean.getSubscriptionAmt()))%></td>
        <td width="136" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean.getContributionamt()))%></td>
        <td width="109" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean.getOutstandingAmt()))%></td>
        <td width="68" class="Data" nowrap="nowrap"><%=AAIEPF1Bean.getAirportCode()%></td>
        <td width="69" class="Data">&nbsp;</td>
      </tr>
         
        
        <%
        subGrandTotal+=Math.round(Double.parseDouble(AAIEPF1Bean.getSubscriptionAmt()));                           
        contGrandTotal+=Math.round(Double.parseDouble(AAIEPF1Bean.getContributionamt()));
               
        outstandGrandTotal+=Math.round(Double.parseDouble(AAIEPF1Bean.getOutstandingAmt()));                
        slno++;
        }        
       %>
              <tr>
        <td class="Data" colspan="8" align="right">Grand Total</td>            
        <td class="Data"><%=df.format(subGrandTotal)%></td>
        <td class="Data"><%=df.format(contGrandTotal)%></td>
        <td class="Data"><%=df.format(outstandGrandTotal)%></td>     
        <td class="Data" colspan="2">&nbsp;</td>
      </tr>
      
    </table>
      </td>
  </tr>
 
  <%}else{        
        %>        
          <table align="center" width="100%">
          <tr>
          <td align="center">
          <strong>No Records Found</strong>
          </td>
          </tr>
          </table>
        <%
        } %>
        
           <tr>
      <td  colspan="13">&nbsp; </td>
      </tr>
      <%
      	if(request.getAttribute("AAIEPF1PendingList1")!=null){
       %>
      
      <tr>
      <td  colspan="13">PF IDs are not added into control account </td>
      </tr>
  
        <tr>
    <td colspan="13"><table width="1166" border="1"  cellpadding="0" cellspacing="0">
      <tr>
        <td width="47" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="48" />
          <tr height="17">
            <td rowspan="2" height="71" width="48" valign="top" class="reportsublabel"><div align="center">Sl No</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" width="47"  valign="top" class="reportsublabel"><div align="center">PF ID</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel"><div align="center">CPF A/c No (Old)</div></td>
        <td  rowspan="2" width="62"  valign="top" class="reportsublabel"><div align="center">Emp No</div></td>
        <td  rowspan="2" width="122" valign="top" class="reportsublabel"><div align="center">Name of the Member</div></td>
        <td  rowspan="2" width="56" valign="top" class="reportsublabel" align="center" >Desig</td>
        <td  rowspan="2" width="118" valign="top" class="reportsublabel"><div align="center">Father's Name/ Husband name in case of Married women</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel" nowrap="nowrap"><div align="center">Date of birth</div></td>
        <td colspan="2" height="26"  valign="top" class="reportsublabel"><div align="center">Opening balances as on <%=request.getAttribute("dispOBYear") %></div></td>
        <td width="128" rowspan="2"  valign="top" class="reportsublabel"><table cellspacing="0" cellpadding="0">
          <col width="109" />
          <tr height="17">
            <td rowspan="2" height="71" width="109"  valign="top" class="reportsublabel"><div align="center">CPF Advance outstanding balance as on  <%=request.getAttribute("dispOBYear") %></div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" align="center"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="68" />
          <tr height="17">
            <td rowspan="2" align="center" width="68" valign="top"><div align="center" class="reportsublabel">Station&nbsp;</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td width="62" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="69" />
          <tr height="17">
            <td rowspan="2" height="71" width="69"  valign="top" class="reportsublabel"><div align="center">Remarks</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
      </tr>
      <tr>
        <td width="91"  valign="top"><div align="center">Subscription</div></td>
        <td width="136"  valign="top"><div align="center">Employer Contribution</div></td>
        </tr>
      <tr>
        <td><div align="center">1</div></td>
        <td><div align="center">2</div></td>
        <td><div align="center">2A</div></td>
        <td><div align="center">3</div></td>
        <td><div align="center">4</div></td>
        <td><div align="center">5</div></td>
        <td><div align="center">6</div></td>
        <td><div align="center">7</div></td>
        <td><div align="center">8</div></td>
        <td><div align="center">9</div></td>
        <td><div align="center">10</div></td>
        <td><div align="center">11</div></td>
        <td><div align="center">12</div></td>
      </tr>
      <%
                  
        AAIEPF1PendingList1=(ArrayList)request.getAttribute("AAIEPF1PendingList1");
    
        int slno1=1;
       
        double subGrandTotal1=0.0,contGrandTotal1=0.0,outstandGrandTotal1=0.0;
        
        for(int i=0;i<AAIEPF1PendingList1.size();i++){
        
        AAIEPF1Bean3=(AAIEPFReportBean)AAIEPF1PendingList1.get(i);
    
        
        %>
         <tr>
        <td width="48" class="Data"><%=slno1%></td>
        <td width="47" class="Data"><%=AAIEPF1Bean3.getPensionNo()%></td>        
        <td width="77" class="Data"><%=AAIEPF1Bean3.getCpfAccno()%></td>
        <td width="62" class="Data"><%=AAIEPF1Bean3.getEmployeeNumber()%></td>
        <td width="122" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean3.getEmployeeName()%></td>
        <td width="56" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean3.getDesignation()%></td>
        <td width="118" class="Data"  nowrap="nowrap"><%=AAIEPF1Bean3.getFhName()%></td>
        <td width="77" class="Data"><%=AAIEPF1Bean3.getDateOfBirth()%></td>
        <td width="91" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean3.getSubscriptionAmt()))%></td>
        <td width="136" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean3.getContributionamt()))%></td>
        <td width="109" class="Data"><%=Math.round(Double.parseDouble(AAIEPF1Bean3.getOutstandingAmt()))%></td>
        <td width="68" class="Data" nowrap="nowrap"><%=AAIEPF1Bean3.getAirportCode()%></td>
        <td width="69" class="Data">&nbsp;</td>
      </tr>
         
        
        <%
        subGrandTotal1+=Math.round(Double.parseDouble(AAIEPF1Bean3.getSubscriptionAmt()));                           
        contGrandTotal1+=Math.round(Double.parseDouble(AAIEPF1Bean3.getContributionamt()));
               
        outstandGrandTotal1+=Math.round(Double.parseDouble(AAIEPF1Bean3.getOutstandingAmt()));                
        slno1++;
        }        
       %>
              <tr>
        <td class="Data" colspan="8" align="right">Grand Total</td>            
        <td class="Data"><%=df.format(subGrandTotal1)%></td>
        <td class="Data"><%=df.format(contGrandTotal1)%></td>
        <td class="Data"><%=df.format(outstandGrandTotal1)%></td>     
        <td class="Data" colspan="2">&nbsp;</td>
      </tr>
        </table>
      </td>
  </tr>

  <%}%>
  
        
        <% double subGrandTot=0,contGrandTot=0,outstandGrandTot=0;
         
        if(request.getAttribute("AAIEPF1List2") != null){     
        %>
        
             <tr>
      <td  colspan="13">&nbsp; </td>
      </tr>
      
      
      <tr>
      <td  colspan="13">PF IDs are not available for the below CPF Numbers </td>
      </tr>
      
 <tr>
    <td colspan="13"><table width="1166" border="1"  cellpadding="0" cellspacing="0">
      <tr>
        <td width="47" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="48" />
          <tr height="17">
            <td rowspan="2" height="71" width="48" valign="top" class="reportsublabel"><div align="center">Sl No</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" width="47"  valign="top" class="reportsublabel"><div align="center">PF ID</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel"><div align="center">CPF A/c No (Old)</div></td>
        <td  rowspan="2" width="62"  valign="top" class="reportsublabel"><div align="center">Emp No</div></td>
        <td  rowspan="2" width="122" valign="top" class="reportsublabel"><div align="center">Name of the Member</div></td>
        <td  rowspan="2" width="56" valign="top" class="reportsublabel" align="center" >Desig</td>
        <td  rowspan="2" width="118" valign="top" class="reportsublabel"><div align="center">Father's Name/ Husband name in case of Married women</div></td>
        <td  rowspan="2" width="77"  valign="top" class="reportsublabel" nowrap="nowrap"><div align="center">Date of birth</div></td>
        <td colspan="2" height="26"  valign="top" class="reportsublabel"><div align="center">Opening balances as on <%=request.getAttribute("dispOBYear")%></div></td>
        <td width="128" rowspan="2"  valign="top" class="reportsublabel"><table cellspacing="0" cellpadding="0">
          <col width="109" />
          <tr height="17">
            <td rowspan="2" height="71" width="109"  valign="top" class="reportsublabel"><div align="center">CPF Advance outstanding balance as on <%=request.getAttribute("dispOBYear")%></div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td  rowspan="2" align="center"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="68" />
          <tr height="17">
            <td rowspan="2" align="center" width="68" valign="top"><div align="center" class="reportsublabel">Station&nbsp;</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
        <td width="62" rowspan="2"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="69" />
          <tr height="17">
            <td rowspan="2" height="71" width="69"  valign="top" class="reportsublabel"><div align="center">Remarks</div></td>
          </tr>
          <tr height="54"> </tr>
        </table></td>
      </tr>
      <tr>
        <td width="91"  valign="top"><div align="center">Subscription</div></td>
        <td width="136"  valign="top"><div align="center">Employer Contribution</div></td>
        </tr>
      
      <%
     
        
        
        AAIEPF1List2=(ArrayList)request.getAttribute("AAIEPF1List2");
        System.out.println("--------AAIEPF1List2 Size in JSP----------"+AAIEPF1List2.size());
        int slno=1;
        
        %>
      
    
         
          <%
           
           
           for(int i=0;i<AAIEPF1List2.size();i++){
        
       		 AAIEPF1Bean2=(AAIEPFReportBean)AAIEPF1List2.get(i);        
       		     
        %>
            
            
             <tr>
        <td width="40" class="Data"><%=slno%></td>
        <td width="47" class="Data">---</td>        
        <td width="77" class="Data"><%=AAIEPF1Bean2.getCpfAccno()%></td>
        <td width="62" class="Data">---</td>
        <td width="122" class="Data"><%=AAIEPF1Bean2.getEmployeeName()%></td>
        <td width="56" class="Data"><%=AAIEPF1Bean2.getDesignation()%></td>
        <td width="118" class="Data">---</td>
        <td width="77" class="Data">---</td>
        <td width="91" class="NumData"><%=Math.round(Double.parseDouble(AAIEPF1Bean2.getSubscriptionAmt()))%></td>
        <td width="136" class="NumData"><%=Math.round(Double.parseDouble(AAIEPF1Bean2.getContributionamt()))%></td>
        <td width="109" class="NumData"><%=Math.round(Double.parseDouble(AAIEPF1Bean2.getOutstandingAmt()))%></td>
        <td width="68" class="Data">---</td>
        <td width="69" class="Data">&nbsp;</td>
      </tr>
        <%
        subGrandTot+=Math.round(Double.parseDouble(AAIEPF1Bean2.getSubscriptionAmt()));          
        contGrandTot+=Math.round(Double.parseDouble(AAIEPF1Bean2.getContributionamt()));
        outstandGrandTot+=Math.round(Double.parseDouble(AAIEPF1Bean2.getOutstandingAmt()));
        
       
         
                
        slno++;
        }
        
        
        %>
       
       <tr>
        <td class="Data" colspan="8" align="right">Grand Total</td>            
        <td class="Data"><%=df.format(subGrandTot)%></td>
        <td class="Data"><%=df.format(contGrandTot)%></td>
        <td class="Data"><%=df.format(outstandGrandTot)%></td>     
        <td class="Data" colspan="2">&nbsp;</td>
      </tr>
           
        
        <%
          }
        
        %>
 
</table>
</body>
</html>
