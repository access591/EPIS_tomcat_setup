<%@ include file="/jsp/rpfc/common/inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Untitled Document</title>
</head>

<body>
<%
String reportType="",fileName="",filePrefix="",filePostfix="";
if(request.getAttribute("AAIEPF2List")!=null){
if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-2_Report";
					fileName=filePrefix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<table width="1351" border="0">
  <tr>
    <td width="110">&nbsp;</td>
    <td width="66">&nbsp;</td>
    <td width="90">&nbsp;</td>
    <td width="52">&nbsp;</td>
    <td width="99">&nbsp;</td>
    <td width="17">&nbsp;</td>
    <td width="117">&nbsp;</td>
    <td width="25">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="35">&nbsp;</td>
    <td width="212">&nbsp;</td>
    <td colspan="3">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="8">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="241">&nbsp;</td>
  </tr>
  <tr>
    <td>
    <table width="1351" cellspacing="0" cellpadding="0" border="0">
      <tr>
       
        <td width="556" rowspan="2"><img src="<%=basePath%>images/logoani.gif"  width="75" height="55" align="right" /></td>
    <td nowrap="nowrap" rowspan="2">AIRPORTS AUTHORITY OF INDIA</td>
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
    <td colspan="19"><table width="1351"  cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="19" align="center">EMPLOYEES PROVIDENT FUND</td>
      </tr>
    </table></td>
    
    
    
    
  </tr>
    </table></td>
    
  </tr>
 
  
   <tr>
    <td colspan="19"><table width="1351"  cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td height="24" colspan="19"  align="center">SCHEDULE OF PRIOR PERIOD    ADJUSTMENT IN OPEING BALANCE AS ON 1st April&nbsp;  <%=request.getAttribute("finYear")%></td>
      </tr>
    </table></td>
    
    
    
    
  </tr>
 
  <tr>
    <td colspan="19"><table width="1351" cellspacing="0" cellpadding="0" border="0">
      <tr>
        <td  colspan="4">Unit Name:- <%=request.getAttribute("region")%></td>
        <td  colspan="14">&nbsp;</td>
         <td align="right">Form no: AAIEPF-2</td>
      </tr>
    </table></td>    
  </tr>
  <tr>
    <td colspan="19"><table width="1351" border="1" cellpadding="0" cellspacing="0">
      <tr>
        <td  rowspan="2"><div align="center">Sl No</div></td>
        <td  rowspan="2"><div align="center">PF ID</div></td>
        <td  rowspan="2"><div align="center">CPF A/c No (Old)</div></td>
        <td  rowspan="2"><div align="center">Emp No</div></td>
        <td  rowspan="2"><div align="center">Name of the Member </div></td>
        <td  rowspan="2"><div align="center">Desig</div></td>
        <td  rowspan="2"><div align="center">Father's Name/ Husband name in case of Married women</div></td>
        <td  rowspan="2"><div align="center">Date of birth</div></td>
        <td  rowspan="2"><div align="center">Period of adjustment</div></td>
        <td  rowspan="2"><div align="center">Emoluments Amount</div></td>
        <td colspan="3"><div align="center">Adjustment In opening balances Subscription </div></td>
        <td colspan="3"><div align="center">Adjustment in opening Balances Employer Contribution </div></td>
        <td  rowspan="2"><div align="center">Adjustment in CPF Adv outstanding Balance </div></td>
        <td rowspan="2"><div align="center">Station </div></td>
        <td  rowspan="2"><div align="center">Remarks</div></td>
      </tr>
      <tr>
        <td width="79"><div align="center">Subscription amount</div></td>
        <td width="65"><div align="center">Interest thereon</div></td>
        <td width="51"><div align="center">Total       (10+11)</div></td>
        <td width="79"><div align="center">Contribution amount</div></td>
        <td width="89"><div align="center">interest thereon</div></td>
        <td width="106"><div align="center">Total    (13+14)</div></td>
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
      </tr>
      
         <%
         DecimalFormat df = new DecimalFormat("#########0");
        ArrayList AAIEPF2List=new ArrayList();
        AAIEPFReportBean AAIEPF1Bean=new AAIEPFReportBean();
        float  subAmtTotal=0,subIntTotal=0,subGrandTotal=0,pensionAmtTotal=0,pensionIntTotal=0,pensionGrandTotal=0;
        
        
        AAIEPF2List=(ArrayList)request.getAttribute("AAIEPF2List");
        System.out.println("--------AAIEPF2List Size in JSP----------"+AAIEPF2List.size());
        int slno=1;
        
        for(int i=0;i<AAIEPF2List.size();i++){
        
        AAIEPF1Bean=(AAIEPFReportBean)AAIEPF2List.get(i);
        
        System.out.println("------Pension no--------"+AAIEPF1Bean.getPensionNo());
        
        %>
        
          <tr>
        <td class="Data"><%=slno%></td>
        <td class="Data"><%=AAIEPF1Bean.getPensionNo()%></td>        
        <td class="Data" nowrap="nowrap"><%=AAIEPF1Bean.getCpfAccno()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmployeeNumber()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmployeeName()%></td>
        <td class="Data"><%=AAIEPF1Bean.getDesignation()%></td>
        <td class="Data"><%=AAIEPF1Bean.getFhName()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF1Bean.getDateOfBirth()%></td>
        <td class="Data" nowrap="nowrap"><%=AAIEPF1Bean.getObYear()%></td>
        <td class="Data">&nbsp;</td>
        <td class="Data"><%=Float.parseFloat(AAIEPF1Bean.getSubscriptionAmt())%></td>
        <td class="Data"><%=Float.parseFloat(AAIEPF1Bean.getSubscriptionInterest())%></td>
          <%
          float subNetAmt=0,pensionNetAmt=0;
          subNetAmt=Float.parseFloat(AAIEPF1Bean.getSubscriptionAmt())+Float.parseFloat(AAIEPF1Bean.getSubscriptionInterest());
          pensionNetAmt=((-1*Float.parseFloat(AAIEPF1Bean.getContributionamt()))+(-1*Float.parseFloat(AAIEPF1Bean.getPensionInterest())));
          %>
        <td class="Data"><%=subNetAmt%></td>
        <td class="Data"><%=-1*Float.parseFloat(AAIEPF1Bean.getContributionamt())%></td>
        <td class="Data"><%=-1*Float.parseFloat(AAIEPF1Bean.getPensionInterest())%></td>
        <td class="Data"><%=pensionNetAmt%></td>
        <td class="Data"><%=AAIEPF1Bean.getOutstandingAmt() %></td>
        <td class="Data"><%=AAIEPF1Bean.getAirportCode()%></td>
        <td class="Data">&nbsp;</td>
      </tr>
        
      
        <%
        subAmtTotal+=Float.parseFloat(AAIEPF1Bean.getSubscriptionAmt());
        subIntTotal+=Float.parseFloat(AAIEPF1Bean.getSubscriptionInterest());
        subGrandTotal+=subNetAmt;
        
        pensionAmtTotal+=-1*Float.parseFloat(AAIEPF1Bean.getContributionamt());
        pensionIntTotal+=-1*Float.parseFloat(AAIEPF1Bean.getPensionInterest());
        pensionGrandTotal+=pensionNetAmt;
        slno++;
        }
        
       %>
       <tr>
       <td colspan="10" align="right">Grand Total</td>        
        <td><%=df.format(subAmtTotal)%></td>
        <td><%=df.format(subIntTotal)%></td>
        <td><%=df.format(subGrandTotal)%></td>
        <td><%=df.format(pensionAmtTotal)%></td>
        <td><%=df.format(pensionIntTotal)%></td>
        <td><%=df.format(pensionGrandTotal)%></td>
         <td colspan="3">&nbsp;</td>
       </tr>
        
    
    
    </table></td>
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
        }
      
      %>
  
  
  
 
</table>
</body>
</html>
