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
        <td colspan="3" align="center" class="reportlabel">Annual Statement of Recoveries and Subscription Region wise from Members during the Year<font style="text-decoration: underline"><%=request.getAttribute("dspYear")%></font></td>
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
				
					filePrefix = "AAI_EPF-11_Report";
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
    
        <td colspan="13" align="center" class="reportsublabel">EMPLOYEES SUBSCRIPTION</td>
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
        <td width="4%" rowspan="2" class="reportsublabel">Transfer from other Orgn. during the year (Employee share)</td>
        <td width="6%" rowspan="2" class="reportsublabel">Salary drawn/ paid for CPF deduction Rs.</td>
		<td width="2%" colspan="5" class="reportsublabel" nowrap="nowrap">Employees subscription</td>
        <td width="5%" rowspan="2" class="reportsublabel">Refundable advance/ PFW /NRFW</td>
        <td width="4%" rowspan="2" class="reportsublabel">Interest Credited</td>
        <td width="4%" rowspan="2" class="reportsublabel">Final payment</td>
        <td width="5%" rowspan="2" class="reportsublabel">Closing Balance (6+7+8+14-15+16-17)</td>
        <td width="2%" rowspan="2" class="reportsublabel">Station</td>
        <td width="4%" rowspan="2" class="reportsublabel">Remarks</td>

      </tr>
      <tr>
	  	<td width="4%" class="reportsublabel">Subscription</td>
		<td width="2%" class="reportsublabel" >Adj in Opening Bal.</td>
		<td width="4%" class="reportsublabel">EPF</td>
		<td width="2%" class="reportsublabel" >VPF</td>
        <td width="4%" class="reportsublabel">PF Adv recovery (Principal)</td>
        <td width="3%" class="reportsublabel">PF Adv recovery (Interest)</td>
		<td width="3%" class="reportsublabel" nowrap="nowrap">Total<br/>(10 to 13)</td>

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
        <td>18</td>
        <td>19</td>
        <td>20</td>

     
      </tr>
	<%
		double emolumentTotal=0,EPFTotal=0,VPFTotal=0,PFAdvTotal=0,PFAdvIntTotal=0,subGrandTotal=0,tnsfrOthrOrgEmpShare=0,PFContTotal=0,contGrandTotal=0,totalAAIContri=0;
		double refAdvTotal=0,intCreditedTotal=0,finalPaymentTotal=0,closingBalTotal=0,obTotal=0,adjOBTotal=0;
     	AaiEpfForm11Bean epfForm11Bean=new AaiEpfForm11Bean();
     	int srlno=0;
     	
     	for(int cardList=0;cardList<cardReportList.size();cardList++){
		epfForm11Bean=(AaiEpfForm11Bean)cardReportList.get(cardList);
		srlno++;
     %>
      <tr <%=epfForm11Bean.getHighlightedColor()%>>
        <td class="NumData"><%=srlno%></td>
        <td class="Data"><%=epfForm11Bean.getPfID()%></td>
        <td class="Data"><%=epfForm11Bean.getCpfAccno()%></td>
        <td class="Data"><%=epfForm11Bean.getEmployeeNo()%></td>
        <td class="Data"><%=epfForm11Bean.getEmployeeName()%></td>
        <td class="Data"><%=epfForm11Bean.getDesignation()%></td>
        <td class="NumData"><%=epfForm11Bean.getObEmpSub()%></td>
        <td class="NumData"><%=epfForm11Bean.getAdjEmpSubTotal()%></td>
        <td class="NumData"><%=epfForm11Bean.getTnsfrOthrOrgEmpShare()%></td>
        <td class="NumData"><%=epfForm11Bean.getEmoluments()%></td>
        <td class="NumData"><%=epfForm11Bean.getEmppfstatury()%></td>
        <td class="NumData"><%=epfForm11Bean.getEmpvpf()%></td>
        <td class="NumData"><%=epfForm11Bean.getPrincipal()%></td>
        <td class="NumData"><%=epfForm11Bean.getInterest()%></td>
        <td class="NumData"><%=epfForm11Bean.getEmptotal()%></td>
        <td class="NumData"><%=epfForm11Bean.getRefAdvPFW()%></td>
        <td class="NumData"><%=epfForm11Bean.getInterestCredited()%></td>
        <td class="NumData"><%=epfForm11Bean.getFinalEmpSubscr()%></td>
        <td class="NumData"><%=epfForm11Bean.getClosingBal()%></td>
        <td class="Data"><%=epfForm11Bean.getAirportCode()%></td>
        <td class="Data"><%=epfForm11Bean.getRemarks()%></td>

     
      </tr>
      <%	emolumentTotal+=Double.parseDouble(epfForm11Bean.getEmoluments());
	 		EPFTotal+=Double.parseDouble(epfForm11Bean.getEmppfstatury());
	 		VPFTotal+=Double.parseDouble(epfForm11Bean.getEmpvpf());
	 		PFAdvTotal+=Double.parseDouble(epfForm11Bean.getPrincipal());
	 		PFAdvIntTotal+=Double.parseDouble(epfForm11Bean.getInterest());	 
	 		subGrandTotal+=Double.parseDouble(epfForm11Bean.getEmptotal());
	 		obTotal+=Double.parseDouble(epfForm11Bean.getObEmpSub());
	 		adjOBTotal+=Double.parseDouble(epfForm11Bean.getAdjEmpSubTotal());
	 		refAdvTotal+=Double.parseDouble(epfForm11Bean.getRefAdvPFW());
	 		intCreditedTotal+=Double.parseDouble(epfForm11Bean.getInterestCredited());
	 		finalPaymentTotal+=Double.parseDouble(epfForm11Bean.getFinalEmpSubscr());
	 		closingBalTotal+=Double.parseDouble(epfForm11Bean.getClosingBal());
	 		tnsfrOthrOrgEmpShare+=Double.parseDouble(epfForm11Bean.getTnsfrOthrOrgEmpShare());
	}%>
     <tr>
     	<%
     		double differgrandtotals=0.00,grandTotalDiff=0.00;
     		differgrandtotals=obTotal+adjOBTotal+tnsfrOthrOrgEmpShare+subGrandTotal-refAdvTotal+intCreditedTotal-finalPaymentTotal;
     		grandTotalDiff=differgrandtotals-closingBalTotal;
     		System.out.println(df.format(grandTotalDiff));
     	%>
        <td class="NumData" colspan="7"><%=Math.round(obTotal)%></td>
        <td class="NumData"><%=Math.round(adjOBTotal)%></td>
        <td class="NumData"><%=Math.round(tnsfrOthrOrgEmpShare)%></td>
        <td class="NumData"><%=Math.round(emolumentTotal)%></td>
        <td class="NumData"><%=Math.round(EPFTotal)%></td>
        <td class="NumData"><%=Math.round(VPFTotal)%></td>
        <td class="NumData"><%=Math.round(PFAdvTotal)%></td>
        <td class="NumData"><%=Math.round(PFAdvIntTotal)%></td>
        <td class="NumData"><%=Math.round(subGrandTotal)%></td>
        <td class="NumData"><%=Math.round(refAdvTotal)%></td>
        <td class="NumData"><%=Math.round(intCreditedTotal)%></td>
        <td class="NumData"><%=Math.round(finalPaymentTotal)%></td>
        <td class="NumData"><%=Math.round(closingBalTotal)%></td>
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
