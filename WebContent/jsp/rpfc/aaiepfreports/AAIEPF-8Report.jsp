<%@ include file="/jsp/rpfc/common/inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
 <LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
<title>AAI</title>
</head>
<%
String reportType="",fileName="",filePrefix="",filePostfix="";
if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-8_Report";
					fileName=filePrefix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<body>
<table width="1343" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="46">&nbsp;</td>
    <td width="49">&nbsp;</td>
    <td width="49">&nbsp;</td>
    <td width="72">&nbsp;</td>
    <td colspan="3" nowrap="nowrap">Form no:  AAIEPF-8</td>
    <td width="20">&nbsp;</td>
  </tr>
    
  <tr>
    <td colspan="12" rowspan="2" align="right">
    <table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="900" align="center"> <img src="<%=basePath%>images/logoani.gif"  width="75" height="55" align="right" /></td>
      </tr>
    </table>
    
   </td>
      <td><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="800">AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
    </table></td>   
   
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
    <td colspan="12">&nbsp;</td>
    <td><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="800">EMPLOYEES PROVIDENT FUND</td>
      </tr>
    </table></td>    
  </tr>
  
   <tr>
    <td colspan="18"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="1300" align="right">MONTHLY SCHEDULE OF REFUNDABLE ADVANCE/NRFW/PFW &amp; FINAL SETTLEMENT  MONTH &nbsp;:<%=request.getAttribute("month")%> &nbsp;&nbsp;&nbsp; FY&nbsp;:&nbsp;<%=request.getAttribute("finYear")%></td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="20" width="51">&nbsp;</td>
        <td height="20" width="51">&nbsp;</td>
        <td height="20" width="102">&nbsp;</td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td height="20">&nbsp;</td>
    <td height="20">&nbsp;</td>
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3">Month :&nbsp; <%=request.getAttribute("month")%></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3" nowrap="nowrap">Name of Unit :&nbsp; <%=request.getAttribute("region")%></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="24"><table width="1333" border="1" cellpadding="0" cellspacing="0">
      <tr>
        <td  rowspan="2" valign="top"><div align="center">Sl No</div></td>
        <td  rowspan="2" valign="top"><div align="center">PF ID</div></td>
        <td  rowspan="2" valign="top"><div align="center">CPF A/c No (Old)</div></td>
        <td  rowspan="2" valign="top"><div align="center">Emp No</div></td>
        <td  rowspan="2" valign="top"><div align="center">Name of the employee</div></td>
        <td  rowspan="2" valign="top"><div align="center">Desig</div></td>
        <td  rowspan="2" valign="top"><div align="center">Father's Name/ Husband name in case of Married</div></td>
        <td  rowspan="2" valign="top" nowrap="nowrap"><div align="center">Date of birth</div></td>
        <td  rowspan="2" valign="top"><div align="center">Month</div></td>
        <td colspan="2" valign="top"><div align="center">Refundable Advance Paid</div></td>
        <td colspan="4" valign="top"><div align="center">PFW/NRFW Paid</div></td>
        <td colspan="5" valign="top"><div align="center">Final settlement </div></td>
        <td colspan="2" valign="top"><div align="center">Payment detail for Adv/PFW &amp; Final payment</div></td>
        <td rowspan="2" valign="top"><div align="center">Station</div></td>
        <td rowspan="2" valign="top"><div align="center">Remarks if any </div></td>
      </tr>
      <tr>
        <td width="42" valign="top"><div align="center">Purpose</div></td>
        <td width="42" valign="top"><div align="center">Amount </div></td>
        <td width="42" valign="top"><div align="center">Purpose</div></td>
        <td width="42" valign="top"><div align="center">Employee Share </div></td>
        <td width="42" valign="top"><div align="center">AAI Share</div></td>
        <td width="42" valign="top"><div align="center">Total (12+13)</div></td>
        <td width="42" valign="top"><div align="center">Reason of settlement</div></td>
        <td width="42" valign="top"><div align="center">Employee Share</div></td>
        <td width="42" valign="top"><div align="center">AAI Share </div></td>
        <td width="42" valign="top"><div align="center">Pension Contribution deducted</div></td>
        <td width="42" valign="top"><div align="center">Net Payment (16+17-18)</div></td>
        <td width="42" valign="top"><div align="center">Cheque No. </div></td>
        <td width="42" valign="top"><div align="center">Date of payment </div></td>
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
        <td><div align="center">13</div></td>
        <td><div align="center">14</div></td>
        <td><div align="center">15</div></td>
        <td><div align="center">16</div></td>
        <td><div align="center">17</div></td>
        <td><div align="center">18</div></td>
        <td><div align="center">19</div></td>
        <td><div align="center">20</div></td>
        <td><div align="center">21</div></td>
        <td><div align="center">22</div></td>
        <td><div align="center">23</div></td>
      </tr>
      
      <%
        DecimalFormat df = new DecimalFormat("#########0");
        ArrayList AAIEPF1List=new ArrayList();
        AAIEPFReportBean AAIEPF1Bean=new AAIEPFReportBean();
        AAIEPFReportBean AAIEPF8Bean=new AAIEPFReportBean();
                
        AAIEPF1Bean=(AAIEPFReportBean)request.getAttribute("aaiEPFBean");
        System.out.println("--------AAIEPF1Bean ----------"+AAIEPF1Bean);
        int slno=1;
        double advAmtTotal=0,empShareTotal=0,loanAAITotal=0,loanGrandTotal=0,finalSetempShareTotal=0,finalSetAAITotal=0,pensionContTotal=0,netPaymentTotal=0;
                
        System.out.println("------Advance List  Size--------"+AAIEPF1Bean.getAdvancesList().size());
        System.out.println("------Loans List  Size--------"+AAIEPF1Bean.getLonesList().size());
        System.out.println("------Final Settlement List  Size--------"+AAIEPF1Bean.getFinalSettlementList().size());
        
        ArrayList advanceList=(ArrayList)AAIEPF1Bean.getAdvancesList();
        ArrayList loanList=(ArrayList)AAIEPF1Bean.getLonesList();
        ArrayList finalSettlementList=(ArrayList)AAIEPF1Bean.getFinalSettlementList();
        
        System.out.println("------Advance List  Size--------"+advanceList.size());
        System.out.println("------Loans List  Size--------"+loanList.size());
        System.out.println("------Final Settlement List  Size--------"+finalSettlementList.size());
        
         for(int i=0;i<advanceList.size();i++){
        
        AAIEPF8Bean=(AAIEPFReportBean)advanceList.get(i);
        
        //System.out.println("------Pension no--------"+AAIEPF1Bean.getPensionNo());
                
        %>
     
      <tr>
              
        <td class="Data"><%=slno%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getPensionNo()%></td>        
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getCpfAccno()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeNumber()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getFhName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDateOfBirth()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getTransDate()%></td>
        <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getAdvPurpose()%></td>
        <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getAdvAmt()%></td>       
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
        <td>&nbsp;</td>
        <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getAirportCode()%></td>       
        <td>&nbsp;</td>
      </tr>
      <%
      advAmtTotal+=Double.parseDouble(AAIEPF8Bean.getAdvAmt());
     
      slno++;
      }
      %>

     <% for(int i=0;i<loanList.size();i++){
        
        AAIEPF8Bean=(AAIEPFReportBean)loanList.get(i);
        
        //System.out.println("------Pension no--------"+AAIEPF1Bean.getPensionNo());
         double totalAmt=Double.parseDouble(AAIEPF8Bean.getEmpShare())+Float.parseFloat(AAIEPF8Bean.getAAIShare());
        %>
     
      <tr>
              
        <td class="Data"><%=slno%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getPensionNo()%></td>        
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getCpfAccno()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeNumber()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getFhName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDateOfBirth()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getTransDate()%></td>
        <td  class="Data">&nbsp;</td>
         <td>&nbsp;</td>
       <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getLoanPurpose()%></td>
       <td  class="Data"><%=AAIEPF8Bean.getEmpShare()%></td>
        <td  class="Data"><%=AAIEPF8Bean.getAAIShare()%></td>      
        <td  class="Data"><%=totalAmt%></td>
       <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getAirportCode()%></td>       
        <td>&nbsp;</td>
      </tr>
      <%
       empShareTotal+=Double.parseDouble(AAIEPF8Bean.getEmpShare());
      loanAAITotal+=Double.parseDouble(AAIEPF8Bean.getAAIShare());
      loanGrandTotal+=totalAmt;
      slno++;
      }
      %>

      
         <% for(int i=0;i<finalSettlementList.size();i++){
        
        AAIEPF8Bean=(AAIEPFReportBean)finalSettlementList.get(i);
        
        //System.out.println("------Pension no--------"+AAIEPF1Bean.getPensionNo());
                
        %>
     
      <tr>
              
        <td class="Data"><%=slno%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getPensionNo()%></td>        
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getCpfAccno()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeNumber()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getFhName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getDateOfBirth()%></td>
       <td class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getTransDate()%></td>
        <td  class="Data">&nbsp;</td>
        <td  class="Data">&nbsp;</td>
   		<td  class="Data">&nbsp;</td>
        <td  class="Data">&nbsp;</td>
        <td  class="Data">&nbsp;</td>
        <td  class="Data">&nbsp;</td>
        <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getSettlementReason()%></td>
        <td class="Data"><%=AAIEPF8Bean.getEmpShare()%></td>
        <td class="Data"><%=AAIEPF8Bean.getAAIShare()%></td>
        <td class="Data"><%=AAIEPF8Bean.getPensionContribution()%></td>
   		<td  class="Data"><%=AAIEPF8Bean.getNetAmt() %></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>       
       <td  class="Data" nowrap="nowrap"><%=AAIEPF8Bean.getAirportCode()%></td>       
        <td>&nbsp;</td>
       
      </tr>
      <%
      finalSetempShareTotal+=Double.parseDouble(AAIEPF8Bean.getEmpShare());
      finalSetAAITotal+=Double.parseDouble(AAIEPF8Bean.getAAIShare());
      pensionContTotal+=Double.parseDouble(AAIEPF8Bean.getPensionContribution());
      netPaymentTotal+=Double.parseDouble(AAIEPF8Bean.getNetAmt());
      
      slno++;
      }
      %>
      
      <tr>
              
        <td class="Data" colspan="9" align="right">Grand Total</td>
        <td class="Data" nowrap="nowrap">&nbsp;</td>
         <td class="Data" nowrap="nowrap"><%=df.format(advAmtTotal)%></td>
       <td class="Data" nowrap="nowrap">&nbsp;</td>
       <td class="Data" nowrap="nowrap"><%=df.format(empShareTotal)%></td>
        <td class="Data" nowrap="nowrap"><%=df.format(loanAAITotal)%></td>
         <td class="Data" nowrap="nowrap"><%=df.format(loanGrandTotal)%></td>
       <td class="Data" nowrap="nowrap">&nbsp;</td>
        <td class="Data" nowrap="nowrap"><%=df.format(finalSetempShareTotal)%></td>
        <td class="Data" nowrap="nowrap"><%=df.format(finalSetAAITotal)%></td>
         <td class="Data" nowrap="nowrap"><%=df.format(pensionContTotal)%></td>
         <td class="Data" nowrap="nowrap"><%=df.format(netPaymentTotal)%></td>
             <td class="Data" nowrap="nowrap" colspan="4">&nbsp;</td>
       
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
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="16"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="17" colspan="18" width="1064">Note:-1. Schedule may be reconciled with the    monthly GL of AAI. No revised schedule will be accepted subsequently.    Changes/Adj of previous month may be corrected next month.</td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="13"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="17" colspan="9" width="543">3. In colmn no 8, March month payments, may    be shown in the same month and likewise.</td>
      </tr>
    </table></td>
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
  </tr> 

</table>
</body>
</html>
