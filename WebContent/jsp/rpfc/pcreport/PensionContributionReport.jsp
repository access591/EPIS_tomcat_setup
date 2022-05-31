<%@ page language="java" import="java.util.ArrayList" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ArrayList a=new ArrayList();
String dispDesignation="";
%>

<%@ page import="com.epis.bean.rpfc.EmployeePersonalInfo,com.epis.bean.rpfc.EmployeePensionCardInfo,com.epis.bean.rpfc.PensionContBean"%>
<%@ page import="java.text.DecimalFormat"%>

<%@ page import="com.epis.utilities.CommonUtil,com.epis.utilities.Constants,com.epis.utilities.StringUtility,com.epis.bean.rpfc.TempPensionTransBean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	
    <script type="text/javascript">
    function deleteTransaction(monthyear,cpfaccno,region,airportcode){
  //	alert("month year"+monthyear +"cpfaccno " +cpfaccno +"region" +region +"airportcode"+airportcode);
    var answer =confirm('Are you sure, do you want  delete this record');
    var pfid=document.forms[0].pfid.value;
  
  
   var page="PensionContribution";
   
    var mappingFlag="true";
	if(answer){
	//alert("inside true");
    document.forms[0].action="<%=basePath%>reportservlet.do?method=deleteTransactionData&cpfaccno="+cpfaccno+"&monthyear="+monthyear+"&region="+region+"&pfid="+pfid+"&page="+page+"&airportcode="+airportcode+"&mappingFlag="+mappingFlag;;
		
		document.forms[0].method="post";
		document.forms[0].submit();
    }
     
    }  
    function test(){
   // 	alert('This pension contribution report');
    }
  
    </script>
  </head>
  
    <body onload="javascript:test()">
<%!
	ArrayList blockList=new ArrayList();
	String breakYear="";
%>

  <form action="method">
			<table width="100%" border="0" 	 align="center" cellpadding="0" cellspacing="0">

				  <%
  	 ArrayList PensionContributionList=new ArrayList();
  	 ArrayList pensionList=new ArrayList();
  	 CommonUtil commonUtil=new CommonUtil();
  	 boolean countFlag=true;
	 String fullWthrOptionDesc="",genderDescr="",mStatusDec="";
  	 String employeeNm="",pensionNo="",doj="",dob="",cpfacno="",employeeNO="",designation="",fhName="",gender="",fileName="",mStatus="",discipline="";
  	 String reportType ="",chkRegionString="",dispSignPath="",dispSignStation="",chkStationString="",chkBulkPrint="",whetherOption="",dateOfEntitle="",empSerialNo="";
  	 String mangerName="";
	  	 if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					fileName = "Pension_Contribution_report.xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
	 int size=0;
 	 PensionContributionList=(ArrayList)request.getAttribute("penContrList");
 	 String cntFlag="",buildFinyear="",tempBuildFinYear="";
 	  if(request.getAttribute("blkprintflag")!=null){
      chkBulkPrint=(String)request.getAttribute("blkprintflag");
    }
     if(request.getAttribute("regionStr")!=null){
    	chkRegionString=(String)request.getAttribute("regionStr");
    }
    if(request.getAttribute("stationStr")!=null){
      chkStationString=(String)request.getAttribute("stationStr");
    }
 	 for(int i=0;i<PensionContributionList.size();i++){
			PensionContBean contr=(PensionContBean)PensionContributionList.get(i);
			employeeNm=contr.getEmployeeNM();
			pensionNo=contr.getPensionNo();
			empSerialNo=contr.getEmpSerialNo();
			doj=contr.getEmpDOJ();
			
			dob=contr.getEmpDOB();
			cpfacno=StringUtility.replaces(contr.getCpfacno().toCharArray(),",=".toCharArray(),",").toString();
			
			if(cpfacno.indexOf(",=")!=-1){
						cpfacno=cpfacno.substring(1,cpfacno.indexOf(",="));
			}else if(cpfacno.indexOf(",")!=-1){
				cpfacno=cpfacno.substring(cpfacno.indexOf(",")+1,cpfacno.length());
			}
			whetherOption=contr.getWhetherOption();
			if(whetherOption.toUpperCase().trim().equals("A")){
			fullWthrOptionDesc="Full Pay";
			}else if(whetherOption.toUpperCase().trim().equals("B") || whetherOption.toUpperCase().trim().equals("NO")){
			fullWthrOptionDesc="Ceiling Pay";
			}else{
			fullWthrOptionDesc=whetherOption;
			}
			employeeNO=contr.getEmployeeNO();
			designation=contr.getDesignation();
			fhName=contr.getFhName();
			gender=contr.getGender();
			
			if(gender.trim().toLowerCase().equals("m")){
				genderDescr="Male";
			}else if(gender.trim().toLowerCase().equals("f")){
				genderDescr="Female";
			}else{
				genderDescr=gender;
			}
			
			mStatus	=contr.getMaritalStatus().trim();
			
			if(mStatus.toLowerCase().equals("m")||(mStatus.toLowerCase().trim().equals("yes"))){
				mStatusDec="Married";
			}else if(mStatus.toLowerCase().equals("u")||(mStatus.toLowerCase().trim().equals("no"))){
				mStatusDec="Un-married";
			}else if(mStatus.toLowerCase().equals("w")){
				mStatusDec="Widow";
			}else{
				mStatusDec=mStatus;
			}
			dateOfEntitle=contr.getDateOfEntitle();
			tempBuildFinYear=commonUtil.converDBToAppFormat(dateOfEntitle,"dd-MMM-yyyy","yyyy");
			buildFinyear=tempBuildFinYear+"-"+commonUtil.converDBToAppFormat(Integer.toString(Integer.parseInt(tempBuildFinYear)+1),"yyyy","yy");
			discipline=contr.getDepartment();
			cntFlag=contr.getCountFlag();
			pensionList=contr.getEmpPensionList();
			blockList=contr.getBlockList();
			 size=pensionList.size();
  			if(size!=0){
  			boolean signatureFlag=false;
  			   if(chkBulkPrint.equals("true")){
   		   			 if(chkRegionString.equals("North Region")){
   		   			signatureFlag=true;
   	    				dispSignStation="";
   	    				mangerName="(Anil Kumar Jain)";
   	     				dispDesignation="Asstt.General Manager(Fin), AAI, NR";
		    			dispSignPath=basePath+"PensionView/images/signatures/AKJain.gif";
		    		 }else if(chkRegionString.equals("South Region")){
		    			signatureFlag=true;
		    			dispSignStation="South Region";
		    			dispDesignation="Deputy General Manager(F&A)";
		    			dispSignPath=basePath+"PensionView/images/signatures/Parimala.gif";	
   		 			}else if(chkRegionString.equals("West Region")){
		    			signatureFlag=true;
		    			dispSignStation="";
		    			mangerName="(Shri S H Kaswankar)";
     					dispDesignation="Sr. Manager(Fin), AAI, WR, Mumbai";
		    			dispSignPath=basePath+"PensionView/images/signatures/Kaswankar.gif";	
   		 			}else if(chkRegionString.equals("CHQIAD")){
    					if(chkStationString.toLowerCase().equals("IGICargo IAD".toLowerCase())){
     						signatureFlag=true;
     						dispSignStation="";
     						mangerName="(Arun Kumar)";
     						dispDesignation="Sr. Manager(F&A), AAI,IGICargo IAD";
     						dispSignPath=basePath+"PensionView/images/signatures/IAD_Arun Kumar.gif";	
    		 			}else if(chkStationString.toLowerCase().equals("IGI IAD".toLowerCase())){
		     				signatureFlag=true;
		     				dispSignStation="";
		     				mangerName="(Arun Kumar)";
		     				dispDesignation="Sr. Manager(F&A), AAI,IGI IAD";
		     				dispSignPath=basePath+"PensionView/images/signatures/IAD_Arun Kumar.gif";	
    		 			}
    			  }
   		 		}
  %>
    <%!
		String getBlockYear(String year)
		{
		String bYear="";
		
		for(int by=0;by<blockList.size();by++){
			bYear=(String)blockList.get(by);
			String[] bDate=bYear.split(",");
			
			if(year.equals(bDate[1])){
				breakYear=bDate[1];
				breakYear=bYear;
				break;
			}else{
				breakYear="03-96";
			}
		}
	//	System.out.println("breakYear"+breakYear);
		//blockList.remove(breakYear);
		return breakYear;
		}
%>
								<tr>
					<td align="center" colspan="5" >
						<table border=0 cellpadding=3 cellspacing=0 width="40%" align="center" valign="middle">
							<tr>
								<td>
									<img src="<%=basePath%>images/logoani.gif" >
								</td>
								<td class="label" align="center" valign="top" nowrap="nowrap">
									<font color='black' size='4' face='Helvetica'> AIRPORTS AUTHORITY OF INDIA </font>
								</td>

							</tr>
						
						</table>
					</td>
				</tr>
					<tr>
						<td>
								<table border="0"    cellpadding="2" cellspacing="0" width="100%" align="center">
								<tr>
								<td align="center" class="reportsublabel" colspan="2">PENSION CONTRIBUTION REPORT FOR THE PERIOD <%=buildFinyear%> TO 2007-08</td>	
								</tr>
								</table>
								</td>
							</tr>
				<tr >
					<td>
						<table border="1" style="border-color:gray"    cellpadding="2" cellspacing="0" width="95%" align="center" 	>
							<tr >
								
								<td class="reportsublabel">PF ID</td>
								<td class="reportdata"><%=pensionNo%></td>	
									<input type="hidden" name="pfid" value="<%=empSerialNo%>">			
									<td class="reportsublabel">NAME</td>
									<td class="reportdata"><%=employeeNm%></td>		
												
							</tr>
							<tr>
								<td class="reportsublabel">EMP NO</td>
								<td class="reportdata"><%=employeeNO%></td>
							<td class="reportsublabel">DESIGNATION</td>
								<td class="reportdata"><%=designation%></td>
														
								
							</tr>
							<tr>
								<td class="reportsublabel">CPF NO</td>
								<td class="reportdata"><%=cpfacno%></td>
									<td class="reportsublabel">FATHER'S/HUSBAND'S NAME</td>
								<td class="reportdata"><%=fhName%></td>
								
								
							</tr>
							<tr>
								<td class="reportsublabel">DATE OF BIRTH</td>
								<td class="reportdata"><%=dob%></td>
								<td class="reportsublabel">GENDER</td>
								<td class="reportdata"><%=genderDescr%></td>
								
							</tr>
							<tr>
								<td class="reportsublabel">DATE OF JOINING</td>
								<td class="reportdata"><%=doj%></td>
								<td class="reportsublabel">DISCIPLINE</td>
								<td class="reportdata"><%=discipline%></td>
									
							</tr>
							<tr>
								<td class="reportsublabel">DATE OF MEMBERSHIP</td>
								<td class="reportdata"><%=dateOfEntitle%></td>
								<td class="reportsublabel">PENSION OPTION</td>
								<td class="reportdata"><%=fullWthrOptionDesc%></td>
							
									
							</tr>
						</table>
					</td>
				</tr>
				
				
				<tr>
					<td  colspan="5" >
					
						<table border="1" style="border-color:gray;"    cellpadding="2" cellspacing="0" width="95%" align="center" >
							
							<tr>
								<td class="label" width="10%" align="center">Month</td>
								<td class="label" width="10%" align="center">Emolument</td>
								<td class="label" width="10%" align="center">EPF</td>
								<td class="label" width="10%" align="center">Pension Contribution </td>
								<td class="label" width="10%" align="center">NET EPF</td>
								<td class="label" width="10%" align="center">Station</td>
								<td class="label" width="10%" align="center">Remarks</td>
							
							</tr>
							<%
							double totalEmoluments=0.0,pfStaturary=0.0,totalPension=0.0,empVpf=0.0,principle=0.0,interest=0.0,pfContribution=0.0;
							double grandEmoluments=0.0,grandCPF=0.0,grandPension=0.0,grandPFContribution=0.0;
							double cpfInterest=0.0,pensionInterest=0.0,pfContributionInterest=0.0;
							double grandCPFInterest=0.0,grandPensionInterest=0.0,grandPFContributionInterest=0.0;
							double cumPFStatury=0.0,cumPension=0.0,cumPfContribution=0.0;
							double cpfOpeningBalance=0.0,penOpeningBalance=0.0,pfOpeningBalance=0.0;
							double percentage=0.0;
							boolean openFlag=false;
							int count=0;
							int chkMnths=0;
							boolean flag=false;
							String findMnt="";
							int countMnts=0;
							DecimalFormat df = new DecimalFormat("#########0");
							String dispFromYear="",dispToYear="",totalYear="";
							boolean dispYearFlag=false;
							double rateOfInterest=0;
							String monthInfo="",getMnthYear="";
							int noofmonths=0;
							for(int j=0;j<pensionList.size();j++){
								TempPensionTransBean bean=(TempPensionTransBean)pensionList.get(j);
								if(bean!=null){
								String dateMontyYear=bean.getMonthyear();
								
								if(dispYearFlag==false){
									if(dispFromYear.equals("")){
										dispFromYear=commonUtil.converDBToAppFormat(dateMontyYear,"dd-MMM-yyyy","yy");
									}
								
									getMnthYear=commonUtil.converDBToAppFormat(dateMontyYear,"dd-MMM-yyyy","MM-yy");
								
									String monthInterestInfo=getBlockYear(getMnthYear);
									String [] monthInterestList=monthInterestInfo.split(",");
									if(monthInterestList.length==2){
											monthInfo=monthInterestList[1];
									
									rateOfInterest=new Double(monthInterestList[0]).doubleValue();
									}
							
									dispYearFlag=true;
								
									breakYear="";
								}
								
								noofmonths++;
						        String monthYear=bean.getMonthyear().substring(dateMontyYear.indexOf("-")+1,dateMontyYear.length());
						       // if(bean.getInterestRate()!=null || !bean.getInterestRate().equals(" ")){
						   //     	rateOfInterest=new Double(bean.getInterestRate()).doubleValue();
						     //   }
						        findMnt=commonUtil.converDBToAppFormat(bean.getMonthyear(),"dd-MMM-yyyy","MM-yy");
						     
						 //       System.out.println("findMnt==="+findMnt+"monthInfo==="+monthInfo+"dispYearFlag***********************"+dispYearFlag);
						       
								if(findMnt.equals(monthInfo)){
									flag=true;
								
									breakYear="";
								}
						
								count++;
							
								totalEmoluments= new Double(df.format(totalEmoluments+Math.round(Double.parseDouble(bean.getEmoluments())))).doubleValue();
								pfStaturary= new Double(df.format(pfStaturary+Math.round(Double.parseDouble(bean.getCpf())))).doubleValue();
								cumPFStatury=cumPFStatury+pfStaturary;
								empVpf = new Double(df.format(empVpf+Math.round(Double.parseDouble(bean.getEmpVPF())))).doubleValue();
								principle =new Double(df.format(principle+Math.round(Double.parseDouble(bean.getEmpAdvRec())))).doubleValue();
								interest =new Double(df.format(interest+Math.round(Double.parseDouble(bean.getEmpInrstRec())))).doubleValue();
								totalPension=new Double(df.format(totalPension+Math.round(Double.parseDouble(bean.getPensionContr())))).doubleValue();
								cumPension=cumPension+totalPension;
						        pfContribution= new Double(df.format(pfContribution+Math.round( Double.parseDouble(bean.getAaiPFCont())))).doubleValue();
						        cumPfContribution=cumPfContribution+pfContribution;
						        
						       
							
							%>
						
					         <% 
					   //      System.out.println(bean.getRecordCount());
					       if(bean.getRecordCount().equals("Single")){
								%>
							<tr>
							
								<td class="Data" align="center"><%=monthYear%></td>
								<td class="Data" align="right"><%=Math.round(Double.parseDouble(bean.getEmoluments()))%></td>
								<td class="Data" align="right"><%=Math.round(Double.parseDouble(bean.getCpf()))%></td>
								<td class="Data" align="right"><%=Math.round(Double.parseDouble(bean.getDbPensionCtr()))%></td>
								<td class="Data" align="right"><%=Math.round(Double.parseDouble(bean.getAaiPFCont()))%></td>
								<td class="Data" nowrap="nowrap"><%=bean.getStation()%></td>
								<td class="Data"><%=bean.getForm7Narration()%></td>
							</tr><%}else if(bean.getRecordCount().equals("Duplicate")){%>
								<tr bgcolor="yellow" >
								<td class="Data"  width="12%" align="center"><font color="red"><%=monthYear%></font></td>
								<td class="Data" width="12%" align="right"><font color="red"><%=Math.round(Double.parseDouble(bean.getEmoluments()))%></font></td>
								<td class="Data" width="12%" align="right"><font color="red"><%=Math.round(Double.parseDouble(bean.getCpf()))%></font></td>
								<td class="Data" width="12%" align="right"><font color="red"><%=Math.round(Double.parseDouble(bean.getDbPensionCtr()))%></font></td>
								<td class="Data" width="12%" align="right"><font color="red"><%=Math.round(Double.parseDouble(bean.getAaiPFCont()))%></font></td>
								<td class="Data" width="12%"><font color="red"><%=bean.getStation()%></font></td>
								<td class="Data" width="12%"><font color="red"><%=bean.getForm7Narration()%></font></td>
							    
							</tr>
								<%}%>
							 	  <%
							 
							  	if(flag==true){
							     if(noofmonths>12){
							    	 noofmonths=12;
							     }
							  	dispToYear=commonUtil.converDBToAppFormat(dateMontyYear,"dd-MMM-yyyy","yy");
							  	//System.out.println("dispFromYear=="+dispFromYear+"flag==="+flag+"dispToYear"+dispToYear+"rateOfInterest"+rateOfInterest);
							  	if(dispFromYear.equals(dispToYear)){
							  	if(dispFromYear.equals("00")){
							  		 	dispFromYear="99";
							  	}
							 
							  	if(dispFromYear.trim().length()<2){
							  	dispFromYear="0"+dispFromYear;
							  	}
							  	dispToYear=Integer.toString(Integer.parseInt(dispToYear)+1);
							  	if(dispToYear.trim().length()<2){
							  		dispToYear="0"+dispToYear;
							  	}
							  	}
							  	totalYear=dispFromYear+"-"+dispToYear;
						
							  	
							  	dispFromYear="";
							  %>
							 <tr>
								<td class="HighlightData" align="center">Total <%=totalYear%></td>
								<td class="HighlightData" align="right"><%=df.format(totalEmoluments)%></td>
								<td class="HighlightData" align="right"><%=df.format(pfStaturary)%></td>
								<td class="HighlightData" align="right"><%=df.format(totalPension)%></td>
								<td class="HighlightData" align="right"><%=df.format(pfContribution)%></td>
								<td class="HighlightData">---</td>
								<td class="HighlightData">---</td>
							
							
							</tr>
							<tr>
								<%
									//System.out.println("rateOfInterest"+rateOfInterest+"No of months"+noofmonths);
								    if(noofmonths<12){
								    	cpfInterest=Math.round((cumPFStatury*rateOfInterest/100)/12)+Math.round((cpfOpeningBalance*rateOfInterest/100)*noofmonths/12);
										pensionInterest=Math.round((cumPension*rateOfInterest/100)/12)+Math.round((penOpeningBalance*rateOfInterest/100)*noofmonths/12);
										pfContributionInterest=Math.round((cumPfContribution*rateOfInterest/100)/12)+Math.round((pfOpeningBalance*rateOfInterest/100)*noofmonths/12);
								    }else{
								    	cpfInterest=Math.round((cumPFStatury*rateOfInterest/100)/12)+Math.round(cpfOpeningBalance*rateOfInterest/100);
										pensionInterest=Math.round((cumPension*rateOfInterest/100)/12)+Math.round(penOpeningBalance*rateOfInterest/100);
										pfContributionInterest=Math.round((cumPfContribution*rateOfInterest/100)/12)+Math.round(pfOpeningBalance*rateOfInterest/100);
							  		}
								  //  System.out.println("cumPFStatury"+cumPFStatury+"cumPension"+cumPension);
								
								%>
								
								<td class="HighlightData" align="center">Interest(<%=rateOfInterest%>%)</td>
								<td class="HighlightData">---</td>
								<td class="HighlightData" align="right"><%=df.format(cpfInterest)%></td>
								<td class="HighlightData" align="right"><%=df.format(pensionInterest)%></td>
								<td class="HighlightData" align="right"><%=df.format(pfContributionInterest)%></td>
								<td class="HighlightData">---</td>
								<td class="HighlightData">---</td>
							
							
							</tr>
							<tr>
								<%
									flag=false;
									openFlag=true;
									noofmonths=0;
									cpfOpeningBalance=Math.round(pfStaturary+cpfInterest+Math.round(cpfOpeningBalance));
									penOpeningBalance=Math.round(totalPension+pensionInterest+Math.round(penOpeningBalance));
									pfOpeningBalance=Math.round(pfContribution+pfContributionInterest+Math.round(pfOpeningBalance));
								
								%>
								
								<td class="HighlightData" align="center">CL BAL</td>
								<td class="HighlightData">---</td>
								<td class="HighlightData" align="right"><%=df.format(cpfOpeningBalance)%></td>
								<td class="HighlightData" align="right"><%=df.format(penOpeningBalance)%></td>
								<td class="HighlightData" align="right"><%=df.format(pfOpeningBalance)%></td>
								<td class="HighlightData">---</td>
								<td class="HighlightData">---</td>
							
							
							</tr>
							<%
							grandEmoluments=grandEmoluments+totalEmoluments;
							grandCPF=grandCPF+pfStaturary;
							grandPension=grandPension+totalPension;
							grandPFContribution=grandPFContribution+pfContribution;
							
							grandCPFInterest=grandCPFInterest+cpfInterest;
							grandPensionInterest=grandPensionInterest+pensionInterest;
							grandPFContributionInterest=grandPFContributionInterest+pfContributionInterest;
							cumPFStatury=0.0;cumPension=0.0;cumPfContribution=0.0;
							totalEmoluments=0;pfStaturary=0;totalPension=0;pfContribution=0;
							cpfInterest=0;pensionInterest=0;pfContributionInterest=0;
							}%>
							<%	  	dispYearFlag=false;}}%>
							<tr>
								<td colspan="8">
									<table align="center" width="100%" cellpadding="0" cellspacing="0" border="1" bordercolor="gray">
									
							<tr>
								<td class="HighlightData"></td>
								<td class="HighlightData">Emolument</td>
								<td class="HighlightData">CPF</td>
								<td class="HighlightData">Interest</td>
								<td class="HighlightData">Pension Contribution</td>
								<td class="HighlightData">Interest</td>
								<td class="HighlightData">PF Contribution</td>
								<td class="HighlightData">Interest</td>
								
							</tr>
							<tr>
								<td class="HighlightData" align="center">Grand  Total of <%=count%> months </td>
								<td class="HighlightData"></td>
								<td class="HighlightData" align="right"><%=df.format(grandCPF)%></td>
								<td class="HighlightData" align="right"><%=df.format(grandCPFInterest)%></td>
								<td class="HighlightData" align="right"><%=df.format(grandPension)%></td>
								<td class="HighlightData" align="right"><%=df.format(grandPensionInterest)%></td>
								<td class="HighlightData" align="right"><%=df.format(grandPFContribution)%></td>
								<td class="HighlightData" align="right"><%=df.format(grandPFContributionInterest)%></td>
								
							</tr>
							<tr>
								<td class="HighlightData" align="center">Grand Total</td>
								<td class="HighlightData" align="right"><%=df.format(grandEmoluments)%></td>
								<td class="HighlightData"  colspan="2" align="right"><%=df.format(grandCPF+grandCPFInterest)%></td>
							
								<td class="HighlightData" colspan="2" align="right"><%=df.format(grandPension+grandPensionInterest)%></td>
							
								<td class="HighlightData"  colspan="2" align="right"><%=df.format(grandPFContribution+grandPFContributionInterest)%></td>
							
							
							
							</tr>
							
    </table></td>
							</tr>
<tr>
								     <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td class="label">NOTE:-</td>
             </tr>
  
          <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;" colspan="2">1. Family Pension Fund (FPF) for the period 01.04.1995 to 15.11.1995 calculated @1.16% of PAY from Employee's & AAI's EPF.</td>
            </tr>
             <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;" colspan="2">2. Pension Contribution(PC) for the period 16.11.1995 to 31.03.2008 calculated @8.33% of PAY from AAI Contribution to EPF.</td>
            </tr>
             <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;" colspan="2">3. Interest on Pension Contribution has been calculated at the EPF Interest rate.</td>
            </tr>
             <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;" colspan="2">4. The amount of Pension Contribution as calculated above has been adjusted against the opening balance of Employer share as on 01.04.2008.</td>
            </tr>
             <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;" colspan="2">5. In case of any discrepancy the matter may be brought to the notice of CPF/Pension Cell within 15 days of issue of Statement otherwise the balance would be presumed to have been confirmed. 
</td>
            </tr>
               <%if(signatureFlag==true){%>
  <tr>
    <td colspan="2">
    	<table width="100%" cellpadding="2" cellspacing="2" align="right">
    		<tr>
    			<td colspan="2"  align="right"><img src="<%=dispSignPath%>" /></td>
    		 </tr>
    		 <tr>
    			<td colspan="2"  class="label" align="right"><%=mangerName%></td>
    		 </tr>
    		 <tr>
    		 	<td class="label" align="left">Date:</td>
    			<td  class="label" align="right"><%=dispDesignation%></td>
    		</tr>
    		<tr>
    			<td  colspan="2"  align="right" class="label"><%=dispSignStation%></td>
    		</tr>
    	</table>
    
    </td>
   
  </tr>
<%}else{%>
   <tr>
            <td class="label" align="left">Date:</td>
            <td class="label" align="right">Assistant Manager/ Manager (Finance)
            </td>
  </tr>
<%}%>
         
            
          <tr>
            <td>&nbsp;</td>
            </tr>
        </table></td>
      </tr>
							
									</table>
								</td>
							</tr>
							
						</table>
						<%if(size-1!=i){%>
						<br style='page-break-after:always;'>
						<%}%>
					</td>
				</tr>
					
				<%}%>
				
			
				<%}%>
				
			</table>
		</form>	
  </body>
</html>
