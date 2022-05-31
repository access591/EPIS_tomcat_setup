

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities,com.aims.info.payrollprocess.PayInputParamInfo" %>
<%@ page import="com.epis.bean.rpfc.FinancialYearBean,com.epis.info.login.LoginInfo"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
                      LoginInfo logInfo=new LoginInfo();
                    if(session.getAttribute("user")!=null)
					{
				      logInfo=(LoginInfo)session.getAttribute("user");
				     
                    
                    }
                    	
            %>

<html >
<head>
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
	<script type="text/javascript">

	function frmload(){
		 process.style.display="none";
	}
	 function LoadWindow(params){
    	var newParams =params;
		winHandle = window.open(newParams,"Utility","menubar=yes,toolbar= yes,statusbar=1,scrollbars=yes,resizable=yes");
		winOpened = true;
		winHandle.window.focus();
	}
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>paySlip.do?method=loadPaySlipParamsPage";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	function validateForm() {
		var regionID="",airportcode="",params="",monthDescr="",sortingOrder="",emplyName="",reportType="",url="",yearID="",monthID="",yearDesc="",formType="",toYearID="",formType="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var transferFlag="",pfidStrip="";	
		reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;

		if(document.forms[0].select_year.selectedIndex>0){
			yearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
		}else{
			yearID=document.forms[0].select_year.value;
		}
		if(document.forms[0].select_month.selectedIndex>0){
			monthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
			monthDescr=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].text;
		}else{
			monthID=document.forms[0].select_month.value;
		}
		if(yearID=='NO-SELECT'){
			alert('User Should be select Year');
			document.forms[0].select_year.focus();
			return false;
		}
		var splitYearID = yearID.split("-");
		yearID=splitYearID[0];
		if(monthID=='NO-SELECT'){
			alert('User Should be select Month');
			document.forms[0].select_month.focus();
			return false;
		}
		if(reportType=='[Select One]'){
			alert('User Should be select report Type');
			document.forms[0].select_reportType.focus();
			return false;
		}
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		
		if(document.forms[0].select_airport.length>1){
			airportcode=document.forms[0].select_airport.options[document.forms[0].select_airport.selectedIndex].value;
		}else{
			airportcode=document.forms[0].select_airport.value;
		}
		var empserialNO=document.forms[0].empserialNO.value;
		emplyName=document.forms[0].empName.value;
		var empno=document.forms[0].empno.value;
		if(document.forms[0].empName.value=''){
			  		alert('Should be checked Employee Name');
			  		document.forms[0].empName.focus();
			  		return false;
		}
		
		params = "&frm_region="+regionID+"&frm_year="+yearID+"&frm_mnthid="+monthID+"&frm_mnthdescr="+monthDescr+"&frm_reportType="+reportType+"&frm_empnm="+emplyName+"&frm_pensionno="+empserialNO+"&frm_empno="+empno+"&frmAirportCode="+airportcode;
		url="<%=basePath%>paySlip.do?method=viewPaySlip"+params;
		
		if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   		}
		
	}
     </script>
	</head>
	<body class="BodyBackground" onload="javascript:frmload()">
		<%String monthID = "", yearDescr = "", region = "", monthNM = "";
			String employeeno= "",employeeName= "",pensionNo= "",stncode= "";
            ArrayList yearList = new ArrayList();
            ArrayList pfidList = new ArrayList();
            Iterator regionIterator = null;
            Iterator monthIterator = null;
            HashMap hashmap = new HashMap();
            String[] year=null;
            PayInputParamInfo payInfo=new PayInputParamInfo();
			if(session.getAttribute("pinput")!=null){
				  payInfo=(PayInputParamInfo)session.getAttribute("pinput");
				  
			        	 employeeno = payInfo.getEmployeeno();
			        
			        	 employeeName = payInfo.getEmployeeName();
			        
			        	 pensionNo =payInfo.getPensionNo();
			        
			        	 stncode = payInfo.getStncode();
			        
				  
			}
          

      
         
		 

            
            %>
		<form action="post">
		<%=ScreenUtilities.screenHeader("Employee Pay Slip")%>
			<table width="75%" border="0" align="center" cellpadding="2" cellspacing="0" class="tbborder">
				
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				
				<tr>
					<td class="tableTextBold" align="right">
						Year:					</td>
					<td align=left>
						<select name='select_year' style='width:100px' class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>
							<option value='2012-2013'>
								2012-2013
							</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="tableTextBold" align="right">
						Month:
					</td>
					<td align=left>
						<select name="select_month" style="width:100px" class="TextField">
							<option value='NO-SELECT'>
								Select One
							</option>

<option value="169">Apr</option>
<option value="170">May</option>
<option value="171">Jun</option>
<option value="172">Jul</option>
<option value="173">Aug</option>
<option value="174">Sep</option>
<option value="175">Oct</option>
<option value="176">Nov</option>
<option value="177">Dec</option>
<option value="178">Jan</option>
<option value="179">Feb</option>
<option value="180">Mar</option>

</select>

							
						
					</td>
				</tr>
				<tr>
																	<td class="tableTextBold" align="right">
																		Region:<font color=red>*</font> 
																	</td>
																	<td align=left>
																		<select name="select_region" style="width:120px"  class="TextField">
																	
																			<option value="CHQNAD">
																				CHQNAD
																			</option>
																		</select>
																	</td>
																</tr>
																<tr>
																	<td class="tableTextBold" align="right">
																		Aiport Name:
																	</td>
																	<td align=left>
																		<select name="select_airport" style="width:120px" class="TextField">
																	
																			<option value="CHQD">
																				CHQD
																			</option>
																		</select>
																	</td>
																</tr>
	
				<tr>
					<td class="tableTextBold" align="right">Employee Name:</td>
					<td align=left>
						<input type="text" name="empName" readonly="readonly" class="TextField" tabindex="3" value="<%=employeeName%>">
					
					</td>

				</tr>
	
				<tr>
					<td class=tableTextBold align="right">Report Type:</td>
					<td align="left">
						<select name="select_reportType" style="width:88px"  class="TextField">
							
							<option value="html">
								Html
							</option>
					
						</select>
					</td>
				<input type="hidden" name="empserialNO"  value="<%=pensionNo%>"/>
				<input type="hidden" name="stncd"  value="<%=stncode%>" />
				<input type="hidden" name="empno"  value="<%=employeeno%>" />
				</tr>	<input type="hidden" name="empName"  value="<%=employeeName%>" />
		

				<tr>
					<td align="center" colspan="2">
						<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm()">
						<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
						
					  
                   </td>
				</tr>
				<tr>
					<td align="center"></td>
				</tr>
			</table>



		
		<div id="process" style="position: fixed;width: auto;height:35%;top: 200px;right: 0;bottom: 100px;left: 10em;" align="center" >
			<img src="<%=basePath%>/images/Indicator.gif" border="no" align="middle"/>
			<SPAN class="tableTextBold" >Processing.......</SPAN>
		</div>
<%=ScreenUtilities.screenFooter()%>
</form>
	</body>
</html>