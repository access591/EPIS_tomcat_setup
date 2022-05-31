
<%@ page language="java" import="java.util.*,com.epis.bean.rpfc.EmployeeValidateInfo,com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   
    
    <title>AAI</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />		
	<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css"/>
 	<SCRIPT type="text/javascript">
 		 function numsDotOnly()
	         {

	            if(((event.keyCode>=48)&&(event.keyCode<=57))||(event.keyCode==46))
	            {
	               event.keyCode=event.keyCode;
	            }

	            else
	            {
					
	              event.keyCode=0;

                }

	       }
		function updateFinanceData(pfid,cpfacno,employeeName,effectiveDate,unitCD,region){
		var path="";
 		  
 		  if(isNaN(document.pensionvaledit.txtbasic.value)==true || document.pensionvaledit.txtbasic.value==''){
 		  	alert('Invalid Basic Data');
 		  	document.pensionvaledit.txtbasic.focus();
 		  	return false;
 		  
 		  }else if(isNaN(document.pensionvaledit.txtEmoluments.value)==true || document.pensionvaledit.txtEmoluments.value==''){
 		  	alert('Invalid Emoluments Data');
 		  	document.pensionvaledit.txtEmoluments.focus();
 		  	return false;
 		 //  }else if(isNaN(document.pensionvaledit.txtspecialbasic.value)==true || document.pensionvaledit.txtspecialbasic.value==''){
 		 // 	alert('Invalid Spceial Basic Data');
 		 // 	document.pensionvaledit.txtspecialbasic.focus();
 		//  	return false;
 		  }else if(isNaN(document.pensionvaledit.txtpfStatury.value)==true || document.pensionvaledit.txtpfStatury.value==''){
 		  	alert('Invalid PF Statury Data');
 		  	document.pensionvaledit.txtpfStatury.focus();
 		  	return false;
 		  }else if(isNaN(document.pensionvaledit.txtVPF.value)==true || document.pensionvaledit.txtVPF.value==''){
 		  	alert('Invalid VPF Data');
 		  	document.pensionvaledit.txtVPF.focus();
 		  	return false;
 		  }else if(isNaN(document.pensionvaledit.txtPFAdv.value)==true || document.pensionvaledit.txtPFAdv.value==''){
 		  	alert('Invalid PF Advance Data');
 		  	document.pensionvaledit.txtPFAdv.focus();
 		  	return false;
 		   }else if(isNaN(document.pensionvaledit.txtCPFInterest.value)==true || document.pensionvaledit.txtCPFInterest.value==''){
 		  	alert('Invalid CPF Interest Data');
 		  	document.pensionvaledit.txtCPFInterest.focus();
 		  	return false;
 		  }else if(isNaN(document.pensionvaledit.txtAdvanceDrwn.value)==true || document.pensionvaledit.txtAdvanceDrwn.value==''){
 		  	alert('Invalid Advance Drawn Data');
 		  	document.pensionvaledit.txtAdvanceDrwn.focus();
 		  	return false;
 		   }else if(isNaN(document.pensionvaledit.txtPartFinal.value)==true || document.pensionvaledit.txtPartFinal.value==''){
 		  	alert('Invalid Part Final Data');
 		  	document.pensionvaledit.txtPartFinal.focus();
 		  	return false;
 		  }else{
 		  	 var pfStatury=document.pensionvaledit.txtpfStatury.value;
 		  	 var basic=document.pensionvaledit.txtbasic.value;
 		  	 var txtEmoluments=document.pensionvaledit.txtEmoluments.value;
 		  //	 var specialBasic=document.pensionvaledit.txtspecialbasic.value;
 		  	 var vpf=document.pensionvaledit.txtVPF.value;
 		  	 var pfadvance=document.pensionvaledit.txtPFAdv.value;
 		  	 var cpfinterest=document.pensionvaledit.txtCPFInterest.value;
 		  	 var advanceDrawn=document.pensionvaledit.txtAdvanceDrwn.value;
 		  	 var partFinal=document.pensionvaledit.txtPartFinal.value;
			 var flag=false;
 		  	 if(document.pensionvaledit.oldbasic.value!=basic){
 		  	 	flag=true;
 		  	 }
 		  	 if(document.pensionvaledit.oldemoluments.value!=txtEmoluments){
 		  	 	flag=true;
 		  	 }
 		  	 if(document.pensionvaledit.oldpfStatury.value!=pfStatury){
 		  	 	flag=true;
 		  	 }
 		  	 if(flag==true){
 		  		 if(document.pensionvaledit.txtnewremarks.value==''){
 		  	 		alert('Please add comments');
 		  	 		document.pensionvaledit.txtnewremarks.focus();
 		  	 		return false;
 		  	 	}
 		  	 }
 		  	 path="";
 		  	 if(document.forms[0].frmName.value==''){
 	             path="<%=basePath%>validatefinance.do?method=updateFinanceDetail&frmName=FinanceData&pfid="+pfid+"&cpfaccno="+cpfacno+"&employee_name="+employeeName+"&effective_date="+effectiveDate+"&airportCD="+unitCD+"&region="+region;
 	             }else{
 				 path="<%=basePath%>validatefinance.do?method=updateFinanceDetail&frmName=CPFDeviation&pfid="+pfid+"&cpfaccno="+cpfacno+"&employee_name="+employeeName+"&effective_date="+effectiveDate+"&airportCD="+unitCD+"&region="+region;             
 	             }
 		  	
		  	document.forms[0].action=path;
			document.forms[0].method="post";
			document.forms[0].submit();
 		  	 	 
 		  }
		
		}
		function resetValidate(){
			
		
		}
		function closeValidate(){
			history.back(-1);
		}
 	</SCRIPT>
  </head>
  <%
  	
  	String effectiveDate="",unitCode="", frmName="";
  	boolean remarksFlag=false;
  	EmployeeValidateInfo employeeValidate=null;
	if(request.getAttribute("loadFinValidationList")!=null){
	employeeValidate=(EmployeeValidateInfo)request.getAttribute("loadFinValidationList");
		
	}
	if(request.getAttribute("effectiveDate")!=null){
	effectiveDate=(String)request.getAttribute("effectiveDate");
		
	}
	
    if(request.getAttribute("frmName")!=null){	
	frmName=(String)request.getAttribute("frmName");	
	}
	if(request.getAttribute("unitCode")!=null){
	unitCode=(String)request.getAttribute("unitCode");
	}
	
	
  %>
  <body  onload="document.forms[0].txtbasic.focus();">
   <form name="pensionvaledit" method="post">
<%=ScreenUtilities.screenHeader("Monthly CPF Recovery Data[Edit]")%>
				<table width="100%" border="0" align="center" cellpadding="1" cellspacing="3">

<tr>
		  				<td class="tableTextBold">PF ID:</td>
		  				<td class="tableSideText" align="left"><%=employeeValidate.getPensionNumber()%></td>
</tr>
		  			<tr>
		  				<td class="tableTextBold">CPF ACC.NO:</td>
		  				<td class="tableSideText" align="left"><%=employeeValidate.getCpfaccno()%></td>	
		  				<td class="tableTextBold">Employee No:</td>
		  				<td class="tableSideText" align="left"><%=employeeValidate.getEmployeeNo()%></td>
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">Employee Name:</td>
		  				<td class="tableSideText" align="left"><%=employeeValidate.getEmployeeName()%></td>
		  				<td class="tableTextBold" >Designation:</td>
		  				<td class="tableSideText" align="left"><%=employeeValidate.getDesegnation()%></td>
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">From Date:</td>
		  				<td class="tableSideText" align="left"><%=effectiveDate%></td>
		  				<td class="tableTextBold">Airport Code:</td>
		  				<td class="tableSideText" align="left"><%=unitCode%></td>
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">Basic:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtbasic" maxlength="15" value="<%=employeeValidate.getBasic()%>" onkeypress="numsDotOnly()"  tabindex="1"></td>
		  				<td class="tableTextBold">Emoluments:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtEmoluments" maxlength="15" value="<%=employeeValidate.getEmoluments()%>" onkeypress="numsDotOnly()" tabindex="2"></td>
		  				
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">PF Statuary:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtpfStatury" maxlength="15" value="<%=employeeValidate.getEmpPFStatuary()%>" onkeypress="numsDotOnly()" tabindex="4"></td>
		  				
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">VPF:</td>
		  				<td align="left"> <input class="TextField" type="text" name="txtVPF" maxlength="15" value="<%=employeeValidate.getEmpVPF()%>" onkeypress="numsDotOnly()" tabindex="5"></td>
		  				<td class="tableTextBold">PFADV:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtPFAdv" maxlength="15" value="<%=employeeValidate.getPfAdvance()%>" onkeypress="numsDotOnly()" tabindex="6"></td>
		  			</tr>
		  			<tr>
		  				<td class="tableTextBold">CPF Interest:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtCPFInterest" maxlength="9" value="<%=employeeValidate.getCpfInterest()%>" onkeypress="numsDotOnly()" tabindex="7"></td>
		  				
		  			</tr>
		  				<tr>
		  				<td class="tableTextBold">Advance Drawn:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtAdvanceDrwn" maxlength="15" value="<%=employeeValidate.getAdvanceDrawn()%>" onkeypress="numsDotOnly()" tabindex="8"></td>
		  				<td class="tableTextBold">Part Final:</td>
		  				<td align="left"><input class="TextField" type="text" name="txtPartFinal" maxlength="15" value="<%=employeeValidate.getPartFinal()%>" onkeypress="numsDotOnly()" tabindex="9"></td>
		  			</tr>
		  			<tr>
		  			
		  				
		  				<td class="tableTextBold">Remarks:<font color="red">*</font></td>
		  				<%if(employeeValidate.getRemarks().equals("---")){%>
		  				<td align="left"><input class="TextField" type="text" name="txtnewremarks" maxlength="35"   tabindex="10"></td>
		  				<%} else {remarksFlag=true;%>
			  				<td align="left"><input class="TextField" type="text" name="txtremarks" maxlength="25" value="<%=employeeValidate.getRemarks()%>"  readonly="readonly" onchange="document.forms[0].txtnewremarks.focus();" tabindex="10"></td>
		  				<%}%>
		  				
		  			</tr>
		  
		  			<%if(remarksFlag==true){%>
		  			<tr>
		  				<td class="tableTextBold"></td>
		  				<td align="left"><input class="TextField" type="text" name="txtnewremarks" maxlength="35"   tabindex="10"></td>
		  			</tr>
		  			<%}%>
		  			<tr>
		  				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		  			</tr>
		  
		  			
					
					 <input type="hidden" name="frmName" value="<%=frmName%>"/>
				
		  			<input type="hidden" name="oldbasic" value="<%=employeeValidate.getBasic()%>">
		  			<input type="hidden" name="oldemoluments" value="<%=employeeValidate.getEmoluments()%>">
		  			<input type="hidden" name="oldpfStatury" value="<%=employeeValidate.getEmpPFStatuary()%>">
		  			
		  	
				  	<tr>
				  	<td colspan="5" align="center">
				  	<table width="100%">
				  	<tr>
				  
				  	<td align="center"><input type="button" class="btn" name="btnUpdate" value="Update" onclick="javascript:updateFinanceData('<%=employeeValidate.getPensionNumber()%>','<%=employeeValidate.getCpfaccno()%>','<%=employeeValidate.getEmployeeName()%>','<%=effectiveDate%>','<%=unitCode%>','<%=employeeValidate.getRegion()%>')" tabindex="11">
				  	<input type="button" class="btn" name="btnReset" value="Reset" onclick="javascript:resetValidate()" tabindex="12">
				  	<input type="button" class="btn" name="btnCancel" value="Cancel" onclick="javascript:closeValidate()" tabindex="13"></td>
	          		 <input type="hidden" name="frmName" value="<%=frmName%>"/>		
   </tr>		  				
		  			</table>
		  			</td>	
		  				
					</tr>

	 
   </table>
   <%=ScreenUtilities.screenFooter()%>
   </form>
  </body>
</html>
