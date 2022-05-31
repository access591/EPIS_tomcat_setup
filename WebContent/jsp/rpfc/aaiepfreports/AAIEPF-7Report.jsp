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
        <td colspan="3" align="center" class="reportlabel">REGION/AIRPORT WISE ABSTRACT OF CPF  RECOVERY FOR THE  YEAR  <font style="text-decoration: underline"><%=request.getAttribute("dspYear")%></font></td>
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
	String dispYear="",endFinYear="";
	if (request.getAttribute("dspYear")!=null){
		dispYear=(String)request.getAttribute("dspYear");
	}
	String[] finToYear=dispYear.split("-");
	endFinYear="31-Mar-"+finToYear[1];
  	if(request.getAttribute("cardList")!=null){
  			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_EPF-7_Report";
					fileName=filePrefix+filePostfix+"_"+dispYear+".xls";
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
        <td width="9%" rowspan="2" class="reportsublabel">Airport/Unit</td>
		<td width="4%" rowspan="2" class="reportsublabel">No of subscribers as on<%=endFinYear%> </td>
        <td width="5%" rowspan="2" class="reportsublabel">Total salary drawn/paid for CPF deduction</td>
   		<td colspan="5" class="reportsublabel" nowrap="nowrap" align="center">Employees subscription</td>
        <td colspan="3" class="reportsublabel" align="center">Employer contribution </td>
        <td colspan="3" class="reportsublabel" align="center">Recovery from other organisation</td>
        <td width="4%" rowspan="2" class="reportsublabel">Remarks</td>

      </tr>
      <tr>
	  	
		<td width="2%" class="reportsublabel">EPF</td>
		<td width="2%" class="reportsublabel" >VPF</td>
        <td width="5%" class="reportsublabel">PF Adv recovery (Principal)</td>
        <td width="7%" class="reportsublabel">PF Adv recovery (Interest)</td>
		<td width="7%" class="reportsublabel" >Total (5 to 8)</td>
		<td width="3%" class="reportsublabel">PF (Net)</td>
		<td width="4%" class="reportsublabel" >Pension contri.</td>
        <td width="6%" class="reportsublabel">Total Rs.(10+11)</td>
		<td width="2%" class="reportsublabel">EPF</td>
		<td width="6%" class="reportsublabel">Employer contribution</td>
		<td width="5%" class="reportsublabel">Pension  certificate amount</td>
        </tr>
		<tr>
				<td class="reportsublabel">1</td>
				<td class="reportsublabel">2</td>
				<td class="reportsublabel">3</td>
				<td class="reportsublabel">4</td>
				<td class="reportsublabel">5</td>
				<td class="reportsublabel">6</td>
				<td class="reportsublabel">7</td>
				<td class="reportsublabel">8</td>
				<td class="reportsublabel">9</td>
				<td class="reportsublabel">10</td>
				<td class="reportsublabel">11</td>
				<td class="reportsublabel">12</td>
				<td class="reportsublabel">13</td>
				<td class="reportsublabel">14</td>
				<td class="reportsublabel">15</td>
				<td class="reportsublabel">16</td>

		</tr>
  
     <tr></tr>
        <%
     	AaiEpfform3Bean epfForm3Bean=new AaiEpfform3Bean();
     	int srlno=0;
     	double emolumentTotal=0,EPFTotal=0,VPFTotal=0,PFAdvTotal=0,PFAdvIntTotal=0,subGrandTotal=0,PFContTotal=0,contGrandTotal=0,PFNetTotal=0;
     	for(int cardList=0;cardList<cardReportList.size();cardList++){
		epfForm3Bean=(AaiEpfform3Bean)cardReportList.get(cardList);
		srlno++;
     %>
      <tr>
        <td  class="NumData"><%=srlno%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getStation()%></td>
      	<td class="NumData" nowrap="nowrap"><%=epfForm3Bean.getTotalSubscribers()%></td>
      	<td  class="NumData"><%=epfForm3Bean.getEmoluments()%></td>
        <td class="NumData"><%=epfForm3Bean.getEmppfstatury()%></td>
        <td class="NumData"><%=epfForm3Bean.getEmpvpf()%></td>
        <td class="NumData"><%=epfForm3Bean.getPrincipal()%></td>
        <td class="NumData"><%=epfForm3Bean.getInterest()%></td>
        <td class="NumData"><%=epfForm3Bean.getSubscriptionTotal()%></td>
        <td class="NumData"><%=epfForm3Bean.getPf()%></td>
        <td class="NumData"><%=epfForm3Bean.getPensionContribution()%></td>
        <td class="NumData"><%=epfForm3Bean.getContributionTotal()%></td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data">---</td>
        <td class="Data"><%=epfForm3Bean.getRemarks()%></td>
     
      </tr>
            <%	
            
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
    <td class="Data" colspan="3" align="right">Grand Total</td>            
    <td class="Data"><%=Math.round(emolumentTotal)%></td>
    <td class="Data"><%=Math.round(EPFTotal)%></td>
    <td class="Data"><%=Math.round(VPFTotal)%></td>  
    
    <td class="Data"><%=Math.round(PFAdvTotal)%></td>  
    <td class="Data"><%=Math.round(PFAdvIntTotal)%></td>  
    <td class="Data"><%=Math.round(subGrandTotal)%></td>  
    <td class="Data"><%=Math.round(PFNetTotal)%></td>  
    <td class="Data"><%=Math.round(PFContTotal)%></td>  
    <td class="Data"><%=Math.round(contGrandTotal)%></td>     
    <td class="Data" colspan="4">&nbsp;</td>
    
    </tr>
    </table></td>
  </tr>
  <%}%>
  <tr>
    <td>&nbsp;</td>
  </tr>
    <%
    
     double emolumentTot=0,EPFTot=0,VPFTot=0,PFAdvTot=0,PFAdvIntTot=0,subGrandTot=0,PFNetTot=0,PFContTot=0,contGrandTot=0,outstandGrandTot=0;
     
    if(request.getAttribute("missingPFIDsList")!=null){
     	AaiEpfform3Bean missingPFIDsBean=new AaiEpfform3Bean();
     	ArrayList missingPFIDsList=new ArrayList();
     	int missSrlno=0;
     	missingPFIDsList=(ArrayList)request.getAttribute("missingPFIDsList");
     	if(missingPFIDsList.size()!=0){%>
   <tr>
  		<td colspan="20" class="Data">Missing PFID's</td>
	</tr>   	
     <%	for(int missingPFIDs=0;missingPFIDs<missingPFIDsList.size();missingPFIDs++){
		missingPFIDsBean=(AaiEpfform3Bean)missingPFIDsList.get(missingPFIDs);
		missSrlno++;
     %>
     <tr>
       <td colspan="20"><table width="100%" border="1" cellspacing="0" cellpadding="0">
     <tr>
        <td  class="NumData"><%=missSrlno%></td>
        <td class="Data"><%=missingPFIDsBean.getEmployeeNo()%></td>
        <td class="Data"><%=missingPFIDsBean.getEmployeeName()%></td>
        <td class="NumData"><%=missingPFIDsBean.getEmoluments()%></td>
        <td class="NumData"><%=missingPFIDsBean.getEmppfstatury()%></td>
        <td class="NumData"><%=missingPFIDsBean.getEmpvpf().trim()%></td>
        <td class="NumData"><%=missingPFIDsBean.getPrincipal().trim()%></td>
        <td class="NumData"><%=missingPFIDsBean.getInterest().trim()%></td>
        <td class="NumData"><%=missingPFIDsBean.getSubscriptionTotal().trim()%></td>
        <td class="NumData"><%=missingPFIDsBean.getPf()%></td>
        <td class="NumData"><%=missingPFIDsBean.getPensionContribution()%></td>
        <td class="NumData"><%=missingPFIDsBean.getContributionTotal()%></td>
         <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="NumData">---</td>
        <td class="Data" nowrap="nowrap">---</td>
      </tr>
      <%
	 emolumentTot+=Double.parseDouble(missingPFIDsBean.getEmoluments());
	 EPFTot+=Double.parseDouble(missingPFIDsBean.getEmppfstatury());
	 VPFTot+=Double.parseDouble(missingPFIDsBean.getEmpvpf());
	 PFAdvTot+=Double.parseDouble(missingPFIDsBean.getPrincipal());
	 PFAdvIntTot+=Double.parseDouble(missingPFIDsBean.getInterest());	 
	 subGrandTot+=Double.parseDouble(missingPFIDsBean.getSubscriptionTotal());
	 PFNetTot+=Double.parseDouble(missingPFIDsBean.getPf());
	 PFContTot+=Double.parseDouble(missingPFIDsBean.getPensionContribution());
	 contGrandTot+=Double.parseDouble(missingPFIDsBean.getContributionTotal());	    
	
	}%>
	
	 <tr>
    <td class="Data" colspan="3" align="right">Grand Total</td>            
    <td class="NumData"><%=Math.round(emolumentTot)%></td>
    <td class="NumData"><%=Math.round(EPFTot)%></td>
    <td class="NumData"><%=Math.round(VPFTot)%></td>  
    
    <td class="NumData"><%=Math.round(PFAdvTot)%></td>  
    <td class="NumData"><%=Math.round(PFAdvIntTot)%></td>  
    <td class="NumData"><%=Math.round(subGrandTot)%></td>  
    <td class="NumData"><%=Math.round(PFNetTot)%></td>  
    <td class="NumData"><%=Math.round(PFContTot)%></td>  
    <td class="NumData"><%=Math.round(contGrandTot)%></td>     
  	<td class="Data" colspan="8">---</td>
    
    </tr>
     <%}}%>
     </td>
     </table>
     </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
