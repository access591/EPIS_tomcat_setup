<%@ include file="/jsp/rpfc/common/inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
 <LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
</head>

<body>
  <%
    	ArrayList cardReportList=new ArrayList();
    	ArrayList missingPFIDsList=new ArrayList();
    	DecimalFormat df = new DecimalFormat("#########0.00");
  	String reportType="",fileName="",region="",stationName="",filePrefix="",filePostfix="";
  	CommonUtil commonUtil=new CommonUtil();
  	
  	if(request.getAttribute("regionDesc")!=null){
  		region=(String)request.getAttribute("regionDesc");
  	}
  	if(request.getAttribute("airportcode")!=null){
  		stationName=(String)request.getAttribute("airportcode");
  	}
  	if("NO-SELECT".equals(stationName)){
  		stationName=region;
  		filePostfix=stationName;
  	}else{
  		filePostfix=region+"_"+stationName;
  	}%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
        <td width="7%" rowspan="2"><img src="<%=basePath%>images/logoani.gif" width="100" height="50" /></td>
        <td>AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
      <tr>
        <td width="38%">&nbsp;</td>
        <td width="55%">EMPLOYEES PROVIDENT FUND</td>
      </tr>
	     <tr>
        <td colspan="3" align="center">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3" align="center" class="reportlabel">Annual Statement of Recoveries and Employer Contribution Region wise from Members during the Year  <font style="text-decoration: underline"><%=request.getAttribute("dspYear")%></font></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="73%">&nbsp;</td>
        <td width="27%" class="reportsublabel">Total No of subscribers<font style="text-decoration: underline"><%=request.getAttribute("totalSub")%> </font></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td class="reportsublabel">Statutory Rate PF Contribution 12.00%</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td class="reportsublabel">Statutory Rate pension Contribution 8.33%</td>
      </tr>
    </table></td>
  </tr>
    <tr>
    <td>&nbsp;</td>
  </tr>
   <%

  	
  	if(request.getAttribute("cardList")!=null){
  			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-12_Report";
					fileName=filePrefix+filePostfix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
  	cardReportList=(ArrayList)request.getAttribute("cardList");
  	
		
	
  %>
  <tr>
    <td><table width="100%" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="6">&nbsp;</td>
    
        <td colspan="9" align="center" class="reportsublabel">EMPLOYER CONTRIBUTION</td>
      <td colspan="2">&nbsp;</td>
        
      </tr>
      <tr>
        <td width="3%" rowspan="2" class="reportsublabel">Sl.No</td>
        <td width="8%" rowspan="2" class="reportsublabel">PF ID </td>
	    <td width="8%" rowspan="2" class="reportsublabel">CPF A/C No(Old)</td>
        <td width="4%" rowspan="2" class="reportsublabel">Emp No</td>
        <td width="6%" rowspan="2" class="reportsublabel">Name of Member</td>
        <td width="4%" rowspan="2" class="reportsublabel">Desig</td>
        <td width="2%" colspan="2" class="reportsublabel" nowrap="nowrap">Op. Bal. As On 01-Apr-<%=request.getAttribute("dspYear").toString().substring(0,4)%></td>
        <td width="4%" rowspan="2" class="reportsublabel">Transfer from other Orgn. during the year</td>
        <td width="6%" rowspan="2" class="reportsublabel">Employer contribution PF(net)</td>
        <td width="5%" rowspan="2" class="reportsublabel">PFW/ NRFW</td>
        <td width="4%" rowspan="2" class="reportsublabel">Interest Credited</td>
        <td width="4%" rowspan="2" class="reportsublabel">Pension Contribution</td>
        <td width="4%" colspan="2" class="reportsublabel">Final payment</td>
        <td width="5%" rowspan="2" class="reportsublabel">Closing Balance (6-7+8+9-10+11-13-14)</td>
        <td width="2%" rowspan="2" class="reportsublabel">Station</td>
        <td width="4%" rowspan="2" class="reportsublabel">Remarks</td>

      </tr>
      <tr>
	  	<td width="4%" class="reportsublabel">Employer Contribution</td>
		<td width="2%" class="reportsublabel" >Adj in Opening Bal.</td>
		<td width="4%" class="reportsublabel">PF</td>
		<td width="2%" class="reportsublabel" >Pension contri. Deducted</td>
       

       </tr>
        <tr>
        <td class="NumData">1</td>
        <td class="Data">2</td>
        <td>2A</td>
        <td>3</td>
        <td>4</td>
        <td>5</td>
        <td>6</td>
        <td>7</td>
        <td>8</td>
        <td>9</td>
        <td>10</td>
        <td>11</td>
        <td>12</td>
        <td>13</td>
        <td>14</td>
        <td>15</td>
        <td>16</td>
       <td>17</td>
  

     
      </tr>
		     <%
     	AaiEpfForm11Bean epfForm11Bean=new AaiEpfForm11Bean();
     	int srlno=0;
     	double totalOBAAISub=0,finalPaymentTotal=0,totalAdjAaiContr=0,totalOthrOrgEmpShare=0,totalPF=0,totalPFWContr=0,totalInterestCredited=0,totalClosingBal=0;
     	double totalPC=0.00;
     	for(int cardList=0;cardList<cardReportList.size();cardList++){
		epfForm11Bean=(AaiEpfForm11Bean)cardReportList.get(cardList);
		srlno++;
     %>
      <tr <%=epfForm11Bean.getHighlightedColor()%>>
        <td class="NumData"><%=srlno%></td>
        <td class="Data"><%=epfForm11Bean.getPfID()%></td>
        <td class="Data"><%=epfForm11Bean.getCpfAccno()%></td>
        <td class="Data"><%=epfForm11Bean.getEmployeeNo()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm11Bean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm11Bean.getDesignation()%></td>
        <td  class="NumData"><%=epfForm11Bean.getObAAISub()%></td>
        <td  class="NumData"><%=epfForm11Bean.getAdjAaiContr()%></td>
        <td  class="NumData"><%=epfForm11Bean.getTnsfrOthrOrgEmpShare()%></td>
        <td  class="NumData"><%=epfForm11Bean.getPf()%></td>
        <td  class="NumData"><%=epfForm11Bean.getPfwContr()%></td>
        <td  class="NumData"><%=epfForm11Bean.getAaiIntersetCredited()%></td>
        <td  class="NumData"><%=epfForm11Bean.getPensinonContr()%></td>
        <td  class="NumData"><%=0.00%></td>
        <td  class="NumData"><%=epfForm11Bean.getFinalAAIContr()%></td>
        <td  class="NumData"><%=epfForm11Bean.getAaiClosingBal()%></td>
        <td class="Data"><%=epfForm11Bean.getAirportCode()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm11Bean.getRemarks()%></td>

     
      </tr>
      <%
      totalOBAAISub+=Double.parseDouble(epfForm11Bean.getObAAISub());
      totalAdjAaiContr+=Double.parseDouble(epfForm11Bean.getAdjAaiContr());
      totalOthrOrgEmpShare+=Double.parseDouble(epfForm11Bean.getTnsfrOthrOrgEmpShare());
      totalPF+=Double.parseDouble(epfForm11Bean.getPf());
      totalPFWContr+=Double.parseDouble(epfForm11Bean.getPfwContr());
      totalInterestCredited+=Double.parseDouble(epfForm11Bean.getAaiIntersetCredited());
      totalPC+=Double.parseDouble(epfForm11Bean.getPensinonContr());
      finalPaymentTotal+=Double.parseDouble(epfForm11Bean.getFinalAAIContr());
      totalClosingBal+=Double.parseDouble(epfForm11Bean.getAaiClosingBal());
      }
      %>
     <tr>
     <%
     		double differgrandtotals=0.00,grandTotalDiff=0.00;
     		
     		differgrandtotals=totalOBAAISub-totalAdjAaiContr+totalOthrOrgEmpShare+totalPF-totalPFWContr+totalInterestCredited-finalPaymentTotal;
     		grandTotalDiff=differgrandtotals-totalClosingBal;
     		System.out.println(df.format(grandTotalDiff));
     	%>
     	 <td class="Data" colspan="6">Grand Totals</td>
        <td class="NumData" ><%=Math.round(totalOBAAISub)%></td>
        <td class="NumData"><%=Math.round(totalAdjAaiContr)%></td>
        <td class="NumData"><%=Math.round(totalOthrOrgEmpShare)%></td>
        <td class="NumData"><%=Math.round(totalPF)%></td>
        <td class="NumData"><%=Math.round(totalPFWContr)%></td>
        <td class="NumData"><%=Math.round(totalInterestCredited)%></td>
          <td class="NumData"><%=Math.round(totalPC)%></td>
        <td class="NumData"><%=0.00%></td>
         <td class="NumData"><%=Math.round(finalPaymentTotal)%></td>
        <td class="NumData"><%=Math.round(totalClosingBal)%></td>
         <%if (grandTotalDiff!=0.00){%>
        <td class="Data" colspan="2"><%=df.format(grandTotalDiff)%></td>
        <%}else{%>
     	<td class="Data" colspan="2">&nbsp;</td>
     	<%}%>
     
     </tr>
    </table></td>
  </tr>
  <%}%>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
