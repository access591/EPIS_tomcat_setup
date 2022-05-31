

<%@ page language="java" import="java.util.*,com.epis.utilities.CommonUtil,com.epis.utilities.Constants" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.ScreenUtilities" %>
<%@ page import="com.epis.bean.rpfc.FinancialYearBean"%>
<%@ page import="com.epis.services.rpfc.FinancialService"%>
<%@ page import="java.util.ArrayList"%>
<%String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
    String forms[] = {"FORM-6-PS-1995","FORM-6-PS"};

            %>
<html>
	<HEAD>
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />	

		<script type="text/javascript">

 function LoadWindow(params){
	var newParams ="<%=basePath%>/jsp/rpfc/common/Report.jsp?"+params
	winHandle = window.open(newParams,"Utility","menubar=yes,toolbar = yes,scrollbars=yes,resizable=yes");
	winOpened = true;
	winHandle.window.focus();
}

	
	function resetReportParams(){
		document.forms[0].action="<%=basePath%>reportservlet.do?method=loadForm6Cmp";
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	
	

	
	function validateForm() {
		var regionID="",airportcode="",reportType="",fromYearID="",toYearID="",fromMonthID="",toMonthID="",yearDesc="",formType="";
		var swidth=screen.Width-10;
		var sheight=screen.Height-150;
		var transferFlag="",pfidStrip="";	
		formType=document.forms[0].formType.options[document.forms[0].formType.selectedIndex].text;
		if(document.forms[0].select_region.selectedIndex>0){
			regionID=document.forms[0].select_region.options[document.forms[0].select_region.selectedIndex].text;
		}else{
			regionID=document.forms[0].select_region.value;
		}
		
     	reportType=document.forms[0].select_reportType.options[document.forms[0].select_reportType.selectedIndex].text;
		if(document.forms[0].select_year.selectedIndex>0){
		fromYearID=document.forms[0].select_year.options[document.forms[0].select_year.selectedIndex].value;
		}else{
		fromYearID=document.forms[0].select_year.value;
		}
		
		if(document.forms[0].select_to_year.selectedIndex>0){
		toYearID=document.forms[0].select_to_year.options[document.forms[0].select_to_year.selectedIndex].value;
		}else{
		toYearID=document.forms[0].select_to_year.value;
		}
		if(toYearID<fromYearID){
			alert('To year is less than from Year.Please select To Year');
			document.forms[0].select_to_year.focus();
			return false;
		}
		
		if(document.forms[0].select_month.selectedIndex>0){
		fromMonthID=document.forms[0].select_month.options[document.forms[0].select_month.selectedIndex].value;
		}else{
		fromMonthID=document.forms[0].select_month.value;
		}
		if(document.forms[0].select_to_month.selectedIndex>0){
		toMonthID=document.forms[0].select_to_month.options[document.forms[0].select_to_month.selectedIndex].value;
		}else{
		toMonthID=document.forms[0].select_to_month.value;
		}
		
		if(fromYearID=="1995"){
		
			if(fromMonthID=="01" || fromMonthID=="02" || fromMonthID=="03"){
				alert("Doesn't have Transaction Data for the Selected Month");
				document.forms[0].select_month.focus();
				return false;
			}
		}
	
		if(toYearID=='1995'){
			if(toMonthID=="01"|| toMonthID=="02" || toMonthID<="03"){
				alert("Doesn't have Transaction Data for the Selected Month");
				document.forms[0].select_to_month.focus();
				return false;
			}
		}
	
		var params = "&frm_region="+regionID+"&frm_year="+fromYearID+"&to_year="+toYearID+"&frm_month="+fromMonthID+"&to_month="+toMonthID+"&frm_reportType="+reportType+"&frm_formType="+formType;
		var url="<%=basePath%>reportservlet.do?method=loadForm6Comp"+params;
		
		if(reportType=='html' || reportType=='Html'){
	   	 			 LoadWindow(url);
   		}else if(reportType=='Excel Sheet' || reportType=='ExcelSheet' ){
   				 		wind1 = window.open(url,"winComps","toolbar=yes,statusbar=yes,scrollbars=yes,menubar=yes,resizable=yes,width="+swidth+",height="+sheight+",top=0,left=0");
						winOpened = true;
						wind1.window.focus();
      	}
		
	}
	
</script>
	</HEAD>
	<body class="BodyBackground" >
		<%String monthID = "", yearDescr = "", region = "", monthNM = "",monthToID="",monthToNM="";

           String[] year=null;
            Iterator regionIterator = null;
            Iterator monthIterator = null;
               Iterator monthToIterator = null;
            HashMap hashmap = new HashMap();
            if (request.getAttribute("regionHashmap") != null) {
                hashmap = (HashMap) request.getAttribute("regionHashmap");
                Set keys = hashmap.keySet();
                regionIterator = keys.iterator();

            }
            if (request.getAttribute("monthIterator") != null) {
                monthIterator = (Iterator) request
                        .getAttribute("monthIterator");
                 monthToIterator=(Iterator) request.getAttribute("monthToIterator");
            }
         	 if (request.getAttribute("year") != null) {
                year = (String[]) request.getAttribute("year");
            }

            %>
		<form action="post">
			<%=ScreenUtilities.screenHeader("Form-6 (PS) Comprehensive Report Params")%>

						<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
							<tr>
								<td class="tableTextBold" align="center">
									From Year:<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<Select name='select_year' Style='width:100px' class="TextField">
										<option value='NO-SELECT'>
											Select One
										</option>
										<%for (int j = 0; j < year.length; j++) {%>
										<option value='<%=year[j]%>'>
											<%=year[j]%>
										</option>
										<%}%>
									</Select>
								</td>

							</tr>
							<tr>
								<td class="tableTextBold" align="center">
									From Month:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<select name="select_month" style="width:100px" class="TextField">
										<Option Value='NO-SELECT' Selected>
											Select One
										</Option>
										<%while (monthIterator.hasNext()) {
											Map.Entry mapEntry = (Map.Entry) monthIterator.next();
											monthID = mapEntry.getKey().toString();
											monthNM = mapEntry.getValue().toString();%>
										<option value="<%=monthID%>">
											<%=monthNM%>
										</option>
										<%}%>
									</select>
								</td>
							</tr>
								<tr>
								<td class="tableTextBold" align="center">
									To Year:<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<Select name='select_to_year' Style='width:100px' class="TextField">
										<option value='NO-SELECT'>
											Select One
										</option>
										<%for (int j = 0; j < year.length; j++) {%>
										<option value='<%=year[j]%>'>
											<%=year[j]%>
										</option>
										<%}%>
									</Select>
								</td>

							</tr>
							<tr>
								<td class="tableTextBold" align="center">
									To Month:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<select name="select_to_month" style="width:100px" class="TextField">
										<Option Value='NO-SELECT' Selected>
											Select One
										</Option>
					
										<%while (monthToIterator.hasNext()) {
											Map.Entry mapEntry = (Map.Entry) monthToIterator.next();
											monthID = mapEntry.getKey().toString();
											monthNM = mapEntry.getValue().toString();%>
										<option value="<%=monthID%>">
											<%=monthNM%>
										</option>
										<%}%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tableTextBold" align="center">
									Region:<font color="red">*</font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<SELECT NAME="select_region" style="width:120px" class="TextField">
										<option value="NO-SELECT">
											[Select One]
										</option>
										<%while (regionIterator.hasNext()) {
											region = hashmap.get(regionIterator.next()).toString();
											%>
										<option value="<%=region%>">
											<%=region%>
										</option>
										<%}%>
									</SELECT>
								</td>
							</tr>
							<tr>
								<td class=tableTextBold align="center" nowrap>Form Type:<font color=red>*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><Select name='formType' Style='width:100px' class="TextField">
									<option value=''>[Select One]</option>
									<%for (int j = 0; j < forms.length; j++) {%>
									<option value='<%=forms[j]%>'><%=forms[j]%></option>
									<%}%>
									</SELECT></td>
							</tr>							


							<tr>
								<td class="tableTextBold" align="center" nowrap>
									Report Type: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td align="left">
									<SELECT NAME="select_reportType" style="width:88px" class="TextField">
										<option value="html">
											Html
										</option>
										<option value="ExcelSheet">
											Excel Sheet
										</option>
									</SELECT>
								</td>
							
							</tr>

			

							<tr>
								<td align="right">
									<input type="button" class="btn" name="Submit" value="Submit" onclick="javascript:validateForm()">
									<input type="button" class="btn" name="Reset" value="Reset" onclick="javascript:resetReportParams()">
									<input type="button" class="btn" name="Submit" value="Cancel" onclick="javascript:history.back(-1)">
								</td>
							</tr>
	
			</table>
	<%=ScreenUtilities.screenFooter()%>		


		</form>
		
	</body>
</html>
