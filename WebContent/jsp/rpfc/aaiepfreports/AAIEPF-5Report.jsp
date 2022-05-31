<%@ include file="/jsp/rpfc/common/inc.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
 <LINK rel="stylesheet" href="<%=basePath%>css/style.css" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
       <td width="7%" rowspan="2"><img src="<%=basePath%>images/logoani.gif" width="88" height="50" align="right" /></td>
        <td class="reportlabel" nowrap="nowrap">AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
      <tr>
        <td width="38%">&nbsp;</td>
        <td width="55%" class="reportlabel">EMPLOYEES PROVIDENT FUND</td>
    
      <tr>
       <%if(request.getAttribute("dspMonth")!=null){%>
        <td colspan="3" align="center" class="reportlabel">REGION/AIRPORT-WISE ABSTRACT OF CPF  RECOVERY FOR THE MONTH <font style="text-decoration: underline"><%=request.getAttribute("dspMonth")%></font>(FY<%=request.getAttribute("dspYear")%>.)</td>
        <%}else{%>
         <td colspan="3" align="center" class="reportlabel">REGION/AIRPORT-WISE ABSTRACT OF CPF  RECOVERY FOR THE YEAR <font style="text-decoration: underline"><%=request.getAttribute("dspYear")%></font>(FY<%=request.getAttribute("dspYear")%>.)</td>
         <%}%>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <%
    	ArrayList cardReportList=new ArrayList();
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
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%" class="reportsublabel">Unit Name:</td>
        <td width="91%"><font style="text-decoration: underline"><%=stationName%></font></td>
      </tr>
     
    </table></td>
  </tr>
      <%
DecimalFormat df = new DecimalFormat("#########0");
  	 double emolumentTotal=0,EPFTotal=0,VPFTotal=0,PFAdvTotal=0,PFAdvIntTotal=0,subGrandTotal=0,PFNetTotal=0,PFContTotal=0,contGrandTotal=0,outstandGrandTotal=0;
  	if(request.getAttribute("cardList")!=null){
  			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-5_Report";
					fileName=filePrefix+filePostfix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
  	cardReportList=(ArrayList)request.getAttribute("cardList");
  	
		
	
  %>
  <tr>
  	<td>&nbsp;</td>
  </tr>
    <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
    <td><table width="100%" border="1" cellspacing="0" cellpadding="0">
      <tr>
     
        <td width="3%" rowspan="2" class="reportsublabel">Sl.No</td>
       
		<td width="8%" rowspan="2" class="reportsublabel">Airport/Unit</td>
        <td width="8%" rowspan="2" class="reportsublabel">Month</td>
        <td width="12%" rowspan="2" class="reportsublabel">No of subscribers</td>
        <td width="8%" rowspan="2" class="reportsublabel">Total salary drawn/paid for CPF deduction</td>
		<td colspan="5" align="center" class="reportsublabel">Employees subscription recovered by AAI</td>
        <td colspan="3" align="center" class="reportsublabel">AAI Contribution  </td>
        <td align="center" colspan="3" class="reportsublabel">Received from other organisation</td>
        <td align="center" rowspan="2" class="reportsublabel">Remarks</td>
      </tr>
      <tr>

        <td width="6%"  class="reportsublabel">EPF</td>
        <td width="6%"  class="reportsublabel">VPF</td>
        <td width="8%"  class="reportsublabel">PF Adv recovery<br/> (Principal)</td>
        <td width="8%"  class="reportsublabel">PF Adv recovery<br/> (Interest)</td>
        <td width="4%"  class="reportsublabel">Total <br/> (6 to 9)</td>
        <td width="5%"  class="reportsublabel">PF (Net)</td>
        <td width="4%"  class="reportsublabel">Pension contri. </td>
        <td width="4%"  class="reportsublabel">Total Rs. (11+12)</td>
        <td width="5%"  class="reportsublabel">Subscription</td>
        <td width="4%"  class="reportsublabel">Employer Contribution</td>
        <td width="4%"  class="reportsublabel">Pension  certificate amount</td>
		
     </tr>
  
     <tr></tr>
        <%
     	AaiEpfform3Bean epfForm3Bean=new AaiEpfform3Bean();
     	int srlno=0,totalSubscribers=0;
     	for(int cardList=0;cardList<cardReportList.size();cardList++){
		epfForm3Bean=(AaiEpfform3Bean)cardReportList.get(cardList);
		srlno++;
     %>
      <tr>
        <td  class="NumData"><%=srlno%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getStation()%></td>
      	<td class="Data" nowrap="nowrap"><%=epfForm3Bean.getMonthyear()%></td>
      	<td  class="NumData"><%=epfForm3Bean.getTotalSubscribers()%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getEmoluments()))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getEmppfstatury()))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getEmpvpf()))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getPrincipal()))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getInterest()))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getSubscriptionTotal()))%></td>
        <td class="NumData"><%=df.format(Math.round(Double.parseDouble(epfForm3Bean.getPf())))%></td>
        <td class="NumData"><%=df.format(Math.round(Double.parseDouble(epfForm3Bean.getPensionContribution())))%></td>
        <td class="NumData"><%=df.format(Double.parseDouble(epfForm3Bean.getContributionTotal()))%></td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data">---</td>
     
      </tr>
       	<%	
       	
       		totalSubscribers=totalSubscribers+Integer.parseInt(epfForm3Bean.getTotalSubscribers());
       		emolumentTotal+=Double.parseDouble(epfForm3Bean.getEmoluments());
	 		EPFTotal+=Double.parseDouble(epfForm3Bean.getEmppfstatury());
	 		VPFTotal+=Double.parseDouble(epfForm3Bean.getEmpvpf());
	 		PFAdvTotal+=Double.parseDouble(epfForm3Bean.getPrincipal());
	 		PFAdvIntTotal+=Double.parseDouble(epfForm3Bean.getInterest());	 
	 		subGrandTotal+=Double.parseDouble(epfForm3Bean.getSubscriptionTotal());
	 		PFNetTotal+=Double.parseDouble(epfForm3Bean.getPf());
			PFContTotal+=Double.parseDouble(epfForm3Bean.getPensionContribution());
	 		contGrandTotal+=Double.parseDouble(epfForm3Bean.getContributionTotal());
	}%>
      <tr>
      <td class="NumData" colspan="3">Grand Total</td>
      
       	<td class="NumData" ><%=totalSubscribers%></td>
        <td class="NumData" ><%=df.format(Math.round(emolumentTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(EPFTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(VPFTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(PFAdvTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(PFAdvIntTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(subGrandTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(PFNetTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(PFContTotal))%></td>
        <td class="NumData"><%=df.format(Math.round(contGrandTotal))%></td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data">---</td>
     
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
