
<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.epfforms.*"%>
<%@ page import="com.epis.services.rpfc.FinancialReportService"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AAI</title>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
        <td>&nbsp;</td>
        <td width="7%" rowspan="2"><img src="<%=basePath%>/images/logoani.gif" width="100" height="50" /></td>
        <td>AIRPORTS AUTHORITY OF INDIA</td>
      </tr>
       <tr>
        <td width="38%">&nbsp;</td>
        <td width="55%">&nbsp;</td>
      </tr>
    
      <tr>
        <td colspan="3" align="center" class="reportlabel">MONTHLY SCHEDULE OF EMPLOYEE-WISE PENSION CONTRIBUTION FOR THE MONTH <font style="text-decoration: underline"><%=request.getAttribute("dspMonth")%></font> (FY<%=request.getAttribute("dspYear")%>.)</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <%
    	ArrayList cardReportList=new ArrayList();
    	ArrayList missingPFIDsList=new ArrayList();
    	
  	String reportType="",fileName="",region="",stationName="",filePrefix="",filePostfix="";
  	CommonUtil commonUtil=new CommonUtil();
  	 DecimalFormat df = new DecimalFormat("#########0");
  	
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
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
      <tr>
       
        <td  class="reportsublabel">Total No of subscribers<font style="text-decoration: underline"> <%=request.getAttribute("totalSub")%></font></td>
      </tr>

      <tr>
       
        <td class="reportsublabel">Statutory Rate pension Contribution 8.33%</td>
      </tr>
       <tr>
       
        <td class="reportsublabel">Ceiling Pay:Rs.6500</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%" class="reportsublabel">Unit Name:</td>
        <td width="91%"><font style="text-decoration: underline"><%=stationName%></font></td>
      </tr>
     
    </table></td>
  </tr>
    <%

  	  double emolumentTotal=0,EPFTotal=0,VPFTotal=0,PFAdvTotal=0,PFAdvIntTotal=0,subGrandTotal=0,PFNetTotal=0,PFContTotal=0,contGrandTotal=0,outstandGrandTotal=0;
  	  
  	if(request.getAttribute("cardList")!=null){
  			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "AAI_RPFC_Forms-6A_Report";
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
     
        <td width="3%"  class="reportsublabel">Sl.No</td>
        <td width="8%"  class="reportsublabel">PF ID </td>
		<td width="8%"  class="reportsublabel" nowrap="nowrap">CPF A/c No<br/> (Old)</td>
        <td width="8%"  class="reportsublabel">Emp No</td>
        <td width="12%"  class="reportsublabel">Name of the Member </td>
        <td width="8%"  class="reportsublabel">Desig</td>
		<td width="12%"  class="reportsublabel">Father's Name/ Husband name <br/>(in case of Married women)</td>
		<td width="4%"  class="reportsublabel" nowrap="nowrap">Date of birth</td>
		<td width="4%"  class="reportsublabel" nowrap="nowrap">Date of Joining</td>
		<td width="4%"  class="reportsublabel" nowrap="nowrap">Pension Option</td>
		<td width="4%"  class="reportsublabel" nowrap="nowrap">Salary Month <br/>mm-yyyy</td>
        <td width="4%"  class="reportsublabel">Salary drawn/ paid for<br/> Pension Contribution Rs</td>
        <td width="4%"  class="reportsublabel">Pension Contribution</td>
        <td width="4%"  class="reportsublabel">No. of days for which salary drawn</td>
        <td align="center"  class="reportsublabel">Station</td>
        <td align="center"  class="reportsublabel">Region</td>
        <td align="center"  class="reportsublabel">Remarks</td>
      </tr>

  
     <tr></tr>
     <%
     	AaiEpfform3Bean epfForm3Bean=new AaiEpfform3Bean();
     	int srlno=0;
     	double diff=0,grandDiffe=0;
     	for(int cardList=0;cardList<cardReportList.size();cardList++){
		epfForm3Bean=(AaiEpfform3Bean)cardReportList.get(cardList);
		srlno++;
		System.out.println(epfForm3Bean.getPfID()+epfForm3Bean.getFormDifference());
		
		diff=Double.parseDouble(epfForm3Bean.getFormDifference());
		
		
     %>
      <tr <%=epfForm3Bean.getHighlightedColor()%>>
        <td  class="NumData"><%=srlno%></td>
        <td class="Data"><%=epfForm3Bean.getPfID()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getCpfaccno()%></td>
        <td class="Data"><%=epfForm3Bean.getEmployeeNo()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getFhName()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getDateOfBirth()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getDateofJoining()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getWetherOption()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getMonthyear()%></td>
        <td class="NumData"><%=epfForm3Bean.getEmoluments()%></td>
        <td class="NumData"><%=epfForm3Bean.getPensionContribution()%></td>
        <td class="Data" nowrap="nowrap">0</td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getStation()%></td>
        <td class="Data" nowrap="nowrap"><%=epfForm3Bean.getRegion()%></td>
        <%if(diff!=0){%>
         <td class="Data" nowrap="nowrap"><%=diff%></td>
        <%}else{%>
        <td class="Data">---</td>
        <%}%>
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
	 grandDiffe+=diff;   
	
	}%>
	
    <tr>
    <td class="Data" colspan="11" align="right">Grand Total</td>            
    <td class="Data"><%=Math.round(emolumentTotal)%></td>
    <td class="Data"><%=Math.round(PFContTotal)%></td>  
    <td class="Data" colspan="3">&nbsp;</td> 
    <td class="Data" ><%=Math.round(grandDiffe)%></td>     
   
    
    </tr>
      
	 <tr>
	  	<td colspan="20" class="Data">&nbsp;</td>
  	</tr>

    <%
    
     double emolumentTot=0,EPFTot=0,VPFTot=0,PFAdvTot=0,PFAdvIntTot=0,subGrandTot=0,PFNetTot=0,PFContTot=0,contGrandTot=0,outstandGrandTot=0;
     
    if(request.getAttribute("missingPFIDsList")!=null){
     	AaiEpfform3Bean missingPFIDsBean=new AaiEpfform3Bean();
     	int missSrlno=0;
     	missingPFIDsList=(ArrayList)request.getAttribute("missingPFIDsList");
     	if(missingPFIDsList.size()!=0){%>
     	  	 <tr>
  		<td colspan="20" class="Data">Missing PFID's</td>
	</tr>
    <%for(int missingPFIDs=0;missingPFIDs<missingPFIDsList.size();missingPFIDs++){
		missingPFIDsBean=(AaiEpfform3Bean)missingPFIDsList.get(missingPFIDs);
		missSrlno++;
     %>
      <tr>
        <td  class="NumData"><%=missSrlno%></td>
        <td class="Data"><%=missingPFIDsBean.getPfID()%></td>
        <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getCpfaccno()%></td>
        <td class="Data"><%=missingPFIDsBean.getEmployeeNo()%></td>
        <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap">---</td>
        <td class="Data" nowrap="nowrap">---</td>
       	<td class="Data" nowrap="nowrap">---</td>
        <td class="Data" nowrap="nowrap">---</td>
        <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getMonthyear()%></td>
        <td class="NumData"><%=missingPFIDsBean.getEmoluments()%></td>
        <td class="NumData"><%=missingPFIDsBean.getPensionContribution()%></td>
        <td class="NumData">0</td>
        <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getStation()%></td>
         <td class="Data" nowrap="nowrap"><%=missingPFIDsBean.getRegion()%></td>
        <td class="Data">---</td>
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
	    <td class="Data" colspan="11" align="right">Grand Total</td>            
	    <td class="Data"><%=Math.round(emolumentTot)%></td>
	    <td class="Data"><%=Math.round(PFContTot)%></td>  
	     <td class="Data" colspan="4">&nbsp;</td>  
    </tr>
    
	 <% }%>

    </table></td>
  </tr>
  <%}}%>
  
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
