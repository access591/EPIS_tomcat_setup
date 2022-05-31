
<%@ page language="java" import="java.util.*,java.text.DecimalFormat,com.epis.utilities.CommonUtil" pageEncoding="UTF-8"%>
<%@ page import="com.epis.bean.rpfc.TrustPCBean,com.epis.bean.rpfc.TrustPCRegionBean"%>


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
        <td width="55%">EMPLOYEES PROVIDENT FUND</td>
      </tr>
	     <tr>
        <td colspan="3" align="center">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3" align="center" class="reportlabel">STATEMENT OF TRUSTWISE PENSION CONTRIBUTION FOR THE PERIOD 1995-96 TO 2007-08</td>
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


    <%

  	
  	if(request.getAttribute("cardList")!=null){
  			if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "Trust_Wise_PCReport";
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
    <td><table width="100%" border="1" cellspacing="0" cellpadding="0">
      <tr>
     
        <td width="3%" rowspan="2" class="reportsublabel">Sl.No</td>
		<td width="8%"  rowspan="2" class="reportsublabel">PFID</td>
        <td width="8%"  rowspan="2" class="reportsublabel">CPF No</td>
        <td width="12%" rowspan="2" class="reportsublabel">Employee No</td>
        <td width="8%"  rowspan="2" class="reportsublabel">Name </td>
		<td rowspan="2" align="center" class="reportsublabel">Design</td>
        <td rowspan="2" align="center" class="reportsublabel">DOB  </td>
        <td align="center" rowspan="2" class="reportsublabel">DOJ</td>
        <td align="center" rowspan="2" class="reportsublabel">Option</td>
		<td align="center" colspan="6" class="reportsublabel">IAAI ECPF TRUST</td>
		<td align="center" colspan="6" class="reportsublabel">NAA ECPF TRUST</td>
			<td align="center" rowspan="2" class="reportsublabel">Total</td>
		<td align="center" rowspan="2" class="reportsublabel">Station</td>
		<td align="center" rowspan="2" class="reportsublabel">Region</td>
		<td align="center" rowspan="2" class="reportsublabel">Mapping Status</td>
		<td align="center" rowspan="2" class="reportsublabel">Remarks</td>	
      </tr>
      <tr>

        <td width="6%"  class="reportsublabel">Pension Contribution(2008)</td>
        <td width="6%"  class="reportsublabel">Interest(2008)</td>
		<td width="6%"  class="reportsublabel">Total(2008)</td>
	    <td width="6%"  class="reportsublabel">Pension Contribution(2009)</td>
        <td width="6%"  class="reportsublabel">Interest(2009)</td>
		<td width="6%"  class="reportsublabel">Total(2009)</td>
        <td width="6%"  class="reportsublabel">Pension Contribution(2008)</td>
        <td width="6%"  class="reportsublabel">Interest(2008)</td>
		<td width="6%"  class="reportsublabel">Total(2008)</td>
		 <td width="6%"  class="reportsublabel">Pension Contribution(2009)</td>
        <td width="6%"  class="reportsublabel">Interest(2009)</td>
		<td width="6%"  class="reportsublabel">Total(2009)</td>
		
		
     </tr>
  
     <tr></tr>
     <%
     	TrustPCBean trustPCBean=new TrustPCBean();
     	TrustPCRegionBean trustRegionBean=new TrustPCRegionBean();
     	DecimalFormat df = new DecimalFormat("#########0");
     	String trustRegion="";
     	ArrayList cardRgnReportList=new ArrayList();
     	int srlno=0;
     	long grandTotalsForPFID=0,grandIAD2008TotalPCTotal=0,grandIAD2009TotalPCTotal=0,grandNAD2008TotalPCTotal=0,grandNAD2009TotalPCTotal=0,grandTotals=0;
     	   //double grandIADTotalPC = 0.0,grandIADTotalPCTotal = 0.0,grandIADTotalPCInterest = 0.0,grandNADTotalPC = 0.0,grandNADTotalPCTotal = 0.0,grandNADTotalPCInterest = 0.0;
   	   long grandTotalsIAD2008PCTotal=0,grandTotalsIAD2009PCTotal=0,grandTotalsNAD2008PCTotal=0,grandTotalsNAD2009PCTotal=0,grandAllRegionTotals=0;
   	   long grandTotalsIAD2008PCInterest=0,grandTotalsIAD2008PenisonTotal=0,grandTotalsIAD2009PCInterest=0,grandTotalsIAD2009PenisonTotal=0;
   	   long grandTotalsNAD2008PCInterest=0,grandTotalsNAD2008PenisonTotal=0,grandTotalsNAD2009PCInterest=0,grandTotalsNAD2009PenisonTotal=0;
        for(int crdRgList=0;crdRgList<cardReportList.size();crdRgList++){
        trustRegionBean=(TrustPCRegionBean)cardReportList.get(crdRgList);
        trustRegion=trustRegionBean.getRegionName();
        cardRgnReportList=trustRegionBean.getRegionList();
        %>
          <tr>
          	<td colspan="26">Region:<%=trustRegion%></td>
          </tr>
        <%
     	for(int cardList=0;cardList<cardRgnReportList.size();cardList++){
		trustPCBean=(TrustPCBean)cardRgnReportList.get(cardList);
		srlno++;
		System.out.println("Pensionno"+trustPCBean.getPensionno()+"IAD 2008"+trustPCBean.getIad2008PCTotal()+"IAD 2009"+trustPCBean.getIad2009PCTotal()+"NAD 2008"+trustPCBean.getNad2008PCTotal()+"NAD 2009"+trustPCBean.getNad2008PCTotal());
		grandTotalsIAD2008PCInterest=grandTotalsIAD2008PCInterest+Long.parseLong(trustPCBean.getIad2008PensionInterest());
		grandTotalsIAD2008PenisonTotal=grandTotalsIAD2008PenisonTotal+Long.parseLong(trustPCBean.getIad2008Pensiontotal());
		grandTotalsIAD2009PCInterest=grandTotalsIAD2009PCInterest+Long.parseLong(trustPCBean.getIad2009PensionInterest());
		grandTotalsIAD2009PenisonTotal=grandTotalsIAD2009PenisonTotal+Long.parseLong(trustPCBean.getIad2009Pensiontotal());
		
		grandTotalsNAD2008PCInterest=grandTotalsNAD2008PCInterest+Long.parseLong(trustPCBean.getNad2008PensionInterest());
		grandTotalsNAD2008PenisonTotal=grandTotalsNAD2008PenisonTotal+Long.parseLong(trustPCBean.getNad2008Pensiontotal());
		grandTotalsNAD2009PCInterest=grandTotalsNAD2009PCInterest+Long.parseLong(trustPCBean.getNad2009PensionInterest());
		grandTotalsNAD2009PenisonTotal=grandTotalsNAD2009PenisonTotal+Long.parseLong(trustPCBean.getNad2009Pensiontotal());
		
		
		grandIAD2008TotalPCTotal = grandIAD2008TotalPCTotal+Long.parseLong(trustPCBean.getIad2008PCTotal());
		grandIAD2009TotalPCTotal = grandIAD2009TotalPCTotal+Long.parseLong(trustPCBean.getIad2009PCTotal());
		grandNAD2008TotalPCTotal=grandNAD2008TotalPCTotal+Long.parseLong(trustPCBean.getNad2008PCTotal());
		grandNAD2009TotalPCTotal=grandNAD2009TotalPCTotal+Long.parseLong(trustPCBean.getNad2009PCTotal());
		grandTotalsForPFID=Long.parseLong(trustPCBean.getIad2008PCTotal())+Long.parseLong(trustPCBean.getNad2008PCTotal())+Long.parseLong(trustPCBean.getIad2009PCTotal())+Long.parseLong(trustPCBean.getNad2009PCTotal());
		grandTotals=grandTotals+grandTotalsForPFID;
		
     %>
      <tr>
        <td  class="NumData"><%=srlno%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getPfID()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getCpfaccno()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getEmployeeNo()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getEmployeeName()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getDesignation()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getDateOfBirth()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getDateofjoining()%></td>
        <td class="Data"><%=trustPCBean.getWetherOption().trim()%></td>
        <td class="NumData"><%=trustPCBean.getIad2008Pensiontotal()%></td>
        <td class="NumData"><%=trustPCBean.getIad2008PensionInterest()%></td>
        <td class="NumData"><%=trustPCBean.getIad2008PCTotal()%></td>
        <td class="NumData"><%=trustPCBean.getIad2009Pensiontotal()%></td>
        <td class="NumData"><%=trustPCBean.getIad2009PensionInterest()%></td>
        <td class="NumData"><%=trustPCBean.getIad2009PCTotal()%></td>
        <td class="NumData"><%=trustPCBean.getNad2008Pensiontotal()%></td>
        <td class="NumData"><%=trustPCBean.getNad2008PensionInterest()%></td>
        <td class="NumData"><%=trustPCBean.getNad2008PCTotal()%></td>
        <td class="NumData"><%=trustPCBean.getNad2009Pensiontotal()%></td>
        <td class="NumData"><%=trustPCBean.getNad2009PensionInterest()%></td>
        <td class="NumData"><%=trustPCBean.getNad2009PCTotal()%></td>
        <td class="NumData"><%=grandTotalsForPFID%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getStation()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getRegion()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getMappingFlag()%></td>
        <td class="Data" nowrap="nowrap"><%=trustPCBean.getRemarks()%></td>
        
      </tr>
	<%}%>
	<tr>
        <td  class="Data" colspan="9">Totals</td>
        <td class="NumData"><%=grandTotalsIAD2008PenisonTotal%></td>
        <td class="NumData"><%=grandTotalsIAD2008PCInterest%></td>
        <td class="NumData"><%=grandIAD2008TotalPCTotal%></td>
         <td class="NumData"><%=grandTotalsIAD2009PenisonTotal%></td>
        <td class="NumData"><%=grandTotalsIAD2009PCInterest%></td>
        <td class="NumData" ><%=grandIAD2009TotalPCTotal%></td>
        
        <td class="NumData" ><%=grandTotalsNAD2008PenisonTotal%></td>
        <td class="NumData" ><%=grandTotalsNAD2008PCInterest%></td>
        <td class="NumData" ><%=grandNAD2008TotalPCTotal%></td>
         <td class="NumData" ><%=grandTotalsNAD2009PenisonTotal%></td>
        <td class="NumData" ><%=grandTotalsNAD2009PCInterest%></td>
        <td class="NumData" ><%=grandNAD2009TotalPCTotal%></td>
        <td class="NumData" ><%=grandTotals%></td>
        <td class="Data" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;</td>
       
        
      </tr>

<%
 grandTotalsIAD2008PCTotal=grandTotalsIAD2008PCTotal+grandIAD2008TotalPCTotal;
 grandTotalsIAD2009PCTotal=grandTotalsIAD2009PCTotal+grandIAD2009TotalPCTotal;
 grandTotalsNAD2008PCTotal=grandTotalsNAD2008PCTotal+grandNAD2008TotalPCTotal;
 grandTotalsNAD2009PCTotal=grandTotalsNAD2009PCTotal+grandNAD2009TotalPCTotal;
 grandAllRegionTotals=grandAllRegionTotals+grandTotals;
 grandIAD2008TotalPCTotal=0;
 grandIAD2009TotalPCTotal=0;
 grandNAD2008TotalPCTotal=0;
 grandNAD2009TotalPCTotal=0;
 grandTotals=0;
%>
  
  <%}%>
  	<tr>
        <td  class="Data" colspan="11">Grand Totals</td>
        <td class="NumData"><%=grandTotalsIAD2008PCTotal%></td>
        <td class="NumData" colspan="3"><%=grandTotalsIAD2009PCTotal%></td>
        <td class="NumData" colspan="3"><%=grandTotalsNAD2008PCTotal%></td>
        <td class="NumData" colspan="3"><%=grandTotalsNAD2009PCTotal%></td>
        <td class="NumData" ><%=grandAllRegionTotals%></td>
        <td class="Data" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;</td>
       
        
      </tr>
  <%}%>
      </table></td>
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
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
