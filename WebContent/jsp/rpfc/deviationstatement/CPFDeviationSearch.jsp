<!--
/*
  * File       : FinanceDataSearch.jsp
  * Date       : 05/02/2009
  * Author     : AIMS 
  * Description: 
  * Copyright (2008) by the Navayuga Infotech, all rights reserved.
  */
-->


<%@ page language="java" import="java.util.*,com.epis.utilities.*"%>
<%@ page import="com.epis.bean.rpfc.DatabaseBean,com.epis.utilities.ScreenUtilities,com.epis.bean.rpfc.SearchInfo,com.epis.bean.rpfc.BottomGridNavigationInfo"%>
<%@ page import="com.epis.bean.rpfc.FinacialDataBean"%>

<%@ taglib uri="/tags-display" prefix="display"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
					
					
    String region="";
  	CommonUtil common=new CommonUtil();    

   	HashMap hashmap=new HashMap();
	hashmap=common.getRegion();

	Set keys = hashmap.keySet();
	Iterator it = keys.iterator();
	

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
		<SCRIPT type="text/javascript" src="<%=basePath%>js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/CommonFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/GeneralFunctions.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=basePath%>js/DateTime.js"></SCRIPT>
		<script type="text/javascript"> 
		function redirectPageNav(navButton,index,totalValue){      
	   
   		document.forms[0].action="<%=basePath%>psearch?method=financenavigation&navButton="+navButton+"&strtindx="+index+"&total="+totalValue;
		document.forms[0].method="post";
		document.forms[0].submit();
	}
	function openURL(sURL) { 
	//alert(sURL);
		window.open(sURL,"Window1","menubar=no,width='100%',height='100%',toolbar=no,fullscreen=yes");

	} 
	function hiLite(imgDocID, imgObjName, comment) {
	if (browserVer == 1) {
	document.images[imgDocID].src = eval(imgObjName + ".src");
	window.status = comment; return true;
	}}
	
	
	 function testSS(){
			
			 if(document.forms[0].fromDt.value==""){
			   alert("Please Enter From Date");
			   document.forms[0].fromDt.focus();
			   return false;
			 }
			 
			  if(document.forms[0].toDt.value==""){
			   alert("Please Enter To Date");
			   document.forms[0].toDt.focus();
			   return false;
			 }
			 
	    if(!document.forms[0].fromDt.value==""){
   		    var date1=document.forms[0].fromDt;
   	        var val1=convert_date(date1);
   		    if(val1==false)
   		     {
   		      return false;
   		     }
   		    }
   		     if(!document.forms[0].toDt.value==""){
   		    var date1=document.forms[0].toDt;
   	        var val1=convert_date(date1);
   		    if(val1==false)
   		     {
   		      return false;
   		     }
   		    }   		

    	document.forms[0].action="<%=basePath%>pfinance.do?method=deviationsearch";
    	
		document.forms[0].method="post";
		document.forms[0].submit();
   		 }
   		 
   		
   		
    		function selectSubMenuOptions(frmType){
				    
		      var count=0;
		      var str=new Array();
		      var   effectiveDt,employeeName,cpfaccno,pensionno,employeeno,designation,unitCD,region;
		   if(document.forms[0].chk==undefined){
		  	alert('User Should select request');		     
		       				return false;
		   }     
		   if(document.forms[0].chk.length!=undefined){    
		  
		      for(var i=0;i<document.forms[0].chk.length;i++){
		      	    
			      if (document.forms[0].chk[i].checked){
			        count++;
			        str=document.forms[0].chk[i].value.split(':');
			      }
		      }
		      if(count==0){
		      	alert('User Should select request');		     
		       				return false;
		      }
		     }else{
		     	 	if(document.forms[0].chk.checked){
				 		str=document.forms[0].chk.value.split(':');
				 	}else{
				 		   alert('User Should select request');		     
		       				return false;
				 	}
		     }
		         for(var j=0;j<str.length;j++){	
			          effectiveDt=str[0];
			          employeeName=str[1];
			          cpfaccno=str[2];
			          pensionno=str[3];
			          employeeno=str[4];
			          designation=str[5];
			          unitCD=str[6];
			          region=str[7];
			        }
			        
			        
			        if(frmType=='editoption'){
			        	submit_Pension(effectiveDt,employeeName,cpfaccno,pensionno,employeeno,designation,unitCD,region)
			        }else if(frmType=='Print'){
			        	callReport();
			        }
		   	
		}
   		 function submit_Pension(effectiveDt,employeeName,cpfaccno,pensionno,employeeno,designation,unitCD,region){
		  	path="<%=basePath%>validatefinance.do?method=getFinanceDetailEdit&frmName=CPFDeviation&cpfaccno="+cpfaccno+"&pensionno="+pensionno+"&airportCD="+unitCD+"&effectiveDate="+effectiveDt+"&employeeNM="+employeeName+"&employeeno="+employeeno+"&designation="+designation+"&region="+region;
			document.forms[0].action=path;
			document.forms[0].method="post";
			document.forms[0].submit();	
		}


 
		function callReport(){
		 document.forms[0].action="<%=basePath%>validatefinance.do?method=loadcpfdeviationInput";
		 document.forms[0].method="post";
		 document.forms[0].submit();
		}

   	 
	</script>
	</head>
	<body class="BodyBackground">
		<form method="post" action="<%=basePath%>psearch?method=financesearch">
	<%=ScreenUtilities.screenHeader("CPF Deviation Statement")%>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

				<%boolean flag = false;%>
				<tr>
					<td>
						<table align="center">
							<tr>
								<td class="tableTextBold">
									PF ID :
								</td>
								<td>
									<input type="text" name="pfid" class="TextField">
								</td>
							</tr>
							
							
							<tr>
								<td class="tableTextBold">
									From Date:<font color="red">&nbsp;*</font>
								</td>
								<td>
									<input type="text" name="fromDt" class="TextField">
									<a href="javascript:show_calendar('forms[0].fromDt');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
								</td>
									<td class="tableTextBold">
									To Date:<font color="red">&nbsp;*</font>
								</td>
								<td>
									<input type="text" name="toDt" class="TextField">
									<a href="javascript:show_calendar('forms[0].toDt');"><img src="<%=basePath%>/images/calendar.gif" border="no" /></a>
								</td>
							</tr>
							
						
							<tr>
								<td colspan="4" align="center"> 
									<input type="button" class="btn" value="Search" class="btn" onclick="testSS();">
									<input type="button" class="btn" value="Reset" onclick="javascript:document.forms[0].reset()" class="btn">
									<input type="button" class="btn" value="Cancel" onclick="javascript:history.back(-1)" class="btn">
								</td>
							</tr>
				
						</table>
</td>
</tr>
</table>
						
			
<%=ScreenUtilities.searchSeperator()%>	
				
				
				<%
				       if (request.getAttribute("searchlist") != null) {
				       ArrayList searchDeviationList=new ArrayList();
				     
				       searchDeviationList=(ArrayList)request.getAttribute("searchlist");
				         System.out.println("----------"+searchDeviationList.size());
				         
				         session.setAttribute("searchDeviationList", searchDeviationList);
				       
				       
				%>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					 <tr>
                  		 <td  height="29" align="left" valign="top">
                  		 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                        		<tr>  
									<td><input type="button" value="Edit" class="btn" onclick="javascript:selectSubMenuOptions('editoption')">
									</td>
					
								
							<td>
								<input type="button" value="Print" class="btn" onclick="javascript:selectSubMenuOptions('Print')">
							</td>
                        </tr>
                        </table></td>
                         
                    </tr>
	
				  
				 
							<tr>							
								<td align="center">
								
									<display:table style="width:100%;height:50px"  class="GridBorder" cellspacing="0" cellpadding="0" export="true" sort="list" id="advanceList" sort="list"  pagesize="10" name="requestScope.searchlist" requestURI="./pfinance.do?method=deviationsearch" >											

									<display:setProperty name="export.amount" value="list" />
    								<display:setProperty name="export.excel.filename" value="AdvancesSearch.xls" />
    								<display:setProperty name="export.pdf.filename" value="AdvancesSearch.pdf" />
    								<display:setProperty name="export.rtf.filename" value="AdvancesSearch.rtf" />
    								    							
    								<display:column   headerClass="GridHBg" class="GridLCells">
    								<input type="radio" name="chk" value="<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getMonthYear()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getEmployeeName()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getCpfAccNo()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getPensionNumber()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getEmployeeNewNo()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getDesignation()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getAirportCode()%>:<%=((FinacialDataBean)pageContext.getAttribute("advanceList")).getRegion()%>"/>
    								</display:column>
    								<display:column property="pensionNumber"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="PF ID" />
									<display:column property="monthYear" 	  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Month Year" />
    								<display:column property="cpfAccNo"  	  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Cpf.AccNo" />
									<display:column property="employeeNewNo"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Employee New.No" />
									<display:column property="employeeName"	  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Employee Name" />								
									<display:column property="emoluments"     sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Emoluments"/>									
									<display:column property="empPfStatuary"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="EmpPfStatuary" />
									<display:column property="cpfArriers"     sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Calc CPF" />
									<display:column property="cpfDifference"  sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Diff CPF"/>
									<display:column property="airportCode"    sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Airport Code" />
									<display:column property="region"        sortable="true"  headerClass="GridLTHCells" class="GridLTCells"   title="Region" />
											
									</display:table>
								</td>
							</tr>
							
		</table>
		
				<%
				  }
				%>
					
		
<%=ScreenUtilities.screenFooter()%>
		</form>
	</body>
</html>
