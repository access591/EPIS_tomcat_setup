<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic"%>
<%@ taglib uri="/tags-c" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>Investment - Submit Quotaion Data</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/ezcalendar.js"></script>
		<SCRIPT SRC="/js/DateTime.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		var str="";
		 function validate(){
		   if(document.forms[0].investmentdate.value!=""){
		   
			if(!convert_date(document.forms[0].investmentdate))
			{
			return false;
			}
			document.forms[0].investmentdate.value=FormatDate(document.forms[0].investmentdate.value);
		} 
		    return true; 
		 }
		function clearData(){
       		      	 
  		}
  		function getCashbookSecurity(){
  		 str ="<select name='securityName' style='width:140px' ><option value=''>--select one--- <option></select>";
  		CashSecurityNames();
  		document.all['cashDiv'].innerHTML = str;	
  		}
  		
  		function getInvestSecurity(){
  		
  		 str="<input type='hidden' name='securityName' value='<bean:write property='securityName' name='regBean'  />' /><bean:write property='securityName' name='regBean'  />";
  		 document.all['cashDiv'].innerHTML = str;
  		}
  		 var xmlHttp;
				function createXMLHttpRequest(){
					if(window.ActiveXObject) {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					} else if (window.XMLHttpRequest) {
						xmlHttp = new XMLHttpRequest();
					}
				}
				function getNodeValue(obj,tag) { 
					if(obj.getElementsByTagName(tag)[0].firstChild){
						return obj.getElementsByTagName(tag)[0].firstChild.nodeValue;
					}else return "";
				}

  		function CashSecurityNames(){
					createXMLHttpRequest();	
					var url ="/investregister.do?method=getCashBookSecurities";
					xmlHttp.open("post", url, true);
					xmlHttp.onreadystatechange = showResponse;
					xmlHttp.send(null);
		}
			function showResponse(){
			 if(xmlHttp.readyState ==4){		
						if(xmlHttp.status == 200){ 
						
		 	var obj1 = document.getElementById("securityName");
		 	obj1.options.length = 0;
	    	var xm = xmlHttp.responseXML;  
	     	var stype = xmlHttp.responseXML.getElementsByTagName('SecurityDetail');
	     	//alert("stype -- "+stype.length);
	     	
	       	if(stype.length>=1){
	        	obj1.options[obj1.options.length] = new Option("Select One","");
		       	for(i=0;i<stype.length;i++){
		         	var val = getNodeValue(stype[i],'getSecurityName');	
		         		        
		 			var nm = getNodeValue(stype[i],'getSecurityName'); 		 		
		 			obj1.options[obj1.options.length] = new Option(nm.replace('^','&'),val.replace('^','&'));
		       	}
	       	}else{
	       		obj1.options[obj1.options.length] = new Option("--No Securities--","");
	       	}		 
		}
		}
		}
	 		</SCRIPT>
	</head>
	<body onload="getInvestSecurity();">
	<html:form action="/addInvestRegister.do?method=addInvestRegister" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("invest.reg")%>
	<html:hidden property="arrangerCd" name="regBean"/>

		<table width="600" border=0  align="center" >
			<tr>
             <td valign=middle align=center height=70 colspan=5>
                  <table border=0>
                      <tr>
                      <td rowspan="5" >
                        <table>
                        <tr><td >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td></tr>	
								</table>
					 </td>
                      <td align=center class="tableTextBold">
                      <table border=0 align=center>
                       <tr>
                       <td colspan=2  nowrap align=center>
                      <font size=4>
                       <bean:message key="invest.reg" bundle="common" /> 
                     </font></td></tr>
                     <tr>
                       <td colspan=2 nowrap>
                      <font size=4>
                       <bean:message key="invest.contributory" bundle="common" /> 
                     </font></td></tr>	
                  	
                     <tr><td>&nbsp;</td></tr>
                     <tr>
                     <td  width="50%" class="tableTextBold" align=right nowrap>
                     <font size=2>
					 <bean:message key="sq.trustname" bundle="common"/>: </font>
				</td><td align=left   styleClass="tableText">
				     <html:hidden property="trustName" name="regBean"/>
					<bean:write property="trustName" name="regBean"    />
				</td>	</tr>	
				</table> </td>
                      </tr>
                   </table>
              </td>
              </tr>
              </table>
              <table width="600" border=1 class="TableBorder" align="center" >
			  <tr>
			  <td  width="20%" class="tableTextBold" align=right nowrap>
			  <html:hidden  property="letterNo" name="regBean"/>
					<bean:message key="sq.securityname" bundle="common"/>:
				</td>
				<td  align=left width="20%" styleClass="tableText" colspan="3" nowrap>
				 <bean:message key="invest.exist" bundle="common"/>:
				 <input type=radio name="exist" value="O" onclick="getCashbookSecurity();" />
				 <bean:message key="invest.new" bundle="common"/>:
				 <input type=radio name="exist" value="N" onclick="getInvestSecurity();" checked="checked" />
				  <div id="cashDiv"></div>	
				</td>
				<tr>									
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.dateofinterest" bundle="common"/>:
				</td>
				<td width="20%" align=left  styleClass="tableText">
				    <html:hidden   property="interestDate" name="regBean"/>
					<bean:write property="interestdispmonth" name="regBean"   />
				</td>
				<logic:equal name="regBean" property="investmentMode" value="H">
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.dateofinterest" bundle="common"/>:
				</td>
				<td width="20%" align=left  styleClass="tableText">
				    <html:hidden   property="interestDate1" name="regBean"/>
					<bean:write property="interestdispmonth1" name="regBean"   />
				</td>
				
				</logic:equal>
					
			  </tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.disincno" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >				   
				
					<html:text property="disIncNo"  styleClass="TextField" />			
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.directincentive" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="directIncentive" name="regBean"/>
				    
					<bean:write property="directIncentive" name="regBean"   /> 				
				</td>
				
				
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.investdate" bundle="common"/>:
				</td>
				<td width="20%" align=left nowrap>
					<bean:write property="investdate" name="regBean"/>
					<html:hidden property="investdate" name="regBean"/> 					
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.amountinv" bundle="common"/>:
				</td>
				<td width="20%" align=left nowrap>
					<bean:write property="amountInv" name="regBean"/>
					<html:hidden property="amountInv" name="regBean"/> 
					<html:hidden property="proposalRefNo" name="regBean"/>					
				</td>	
				
				
				</tr>
				<tr>
				 <td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="confirmationcomapny.cuponrate" bundle="common"/>:
				</td><td width="20%" align=left styleClass="tableText">
				    <html:hidden property="faceValue" name="regBean" />
					<bean:write property="faceValue" name="regBean"  />     
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.noofunits" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >
				    <html:hidden property="numberOfUnits" name="regBean"/>
					<bean:write property="numberOfUnits" name="regBean" />   					
				</td>	
				
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="ytm.facevalueperunit" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText">
				    <html:hidden property="investmentFaceValue" name="regBean" />
					<bean:write property="investmentFaceValue" name="regBean"  /> 				
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="ytm.purchasePricePerunit" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText">
				    <html:hidden property="priceoffered" name="regBean" />
				    <bean:write property="priceoffered" name="regBean"  /> 				
				</td>
				
					
				</tr>
				
				<tr>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.brokerincentive" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="brokerIncentive" name="regBean"  />
					<bean:write property="brokerIncentive" name="regBean"  /> 					
				</td>
				
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.redemptiondate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="redemptionDate" name="regBean" />
					<bean:write property="redemptionDate" name="regBean"   />    
				</td>
				</tr>
				<tr>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="invest.registrar" bundle="common"/>:
				</td>
				<td align=left width="20%" >
					<html:text property="registrardetails" styleClass="TextField"  maxlength="1000"/>  
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="invest.brokernameaddr" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="brokerName" name="regBean" />
				    <html:hidden property="brokerAddress" name="regBean" />
					<bean:write property="brokerName" name="regBean"   /> <br> 
					<bean:write property="brokerAddress" name="regBean"   /> 
				</td>
					
				
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="confirmationcomapny.purchaseprice" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="purchasePrice" name="regBean" />
					<bean:write property="purchasePrice" name="regBean" />   
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="lettertobank.settlementamount" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="settlementAmount" name="regBean" />
				    
					<bean:write property="settlementAmount" name="regBean"   />
				</td>	
				</tr>
				
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="bankletter.settelementdate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="settlementDate" name="regBean" />
					<bean:write property="settlementDate" name="regBean" />   
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="bankletter.facevalueamount" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    
				    
					<bean:write property="totalFaceValue" name="regBean"   />
				</td>	
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="sq.securitycategory" bundle="common"/>: 
				</td><td align=left width="20%" styleClass="tableText">
				    <html:hidden property="securityCd" name="regBean"/>
				    <html:hidden property="securityCategory" name="regBean"/>
					<bean:write property="securityCategory" name="regBean"   />      
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.guarenteetype" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="guarenteeType" name="regBean"/>
					<bean:write property="guarantee" name="regBean"  />
					
				</td>	
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.investmentmode" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="investmentMode" name="regBean"  />
					<bean:write property="investmode" name="regBean"   />
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.maturitydate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="maturityDate" name="regBean" />
					<bean:write property="maturityDate" name="regBean"  /> 					
				</td>		
				</tr>
			
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="sq.rating" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >
				    <html:hidden property="rating" name="regBean" />
					<bean:write property="rating" name="regBean"   /> 				
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="sq.ytminpercent" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >
				    <html:hidden property="ytm" name="regBean" />
					<bean:write property="ytm" name="regBean"   /> 				
				</td>
			</tr>
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.typeofcall" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="callOption" name="regBean" />	
					<bean:write property="callopt" name="regBean"  />			
				</td>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="sq.investmenttype" bundle="common"/>:
				</td>
				<td width="20%" align=left>
				 <html:hidden property="investmentType" name="regBean"   />
				<bean:write property="investtype" name="regBean"  /> 				
				</td>
			</tr>
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
				<logic:equal name="regBean" property="purchaseOption" value="P">
					 <bean:message key="sq.premiumpaid" bundle="common"/>:
				</logic:equal>
				<logic:equal name="regBean" property="purchaseOption" value="D">
					 <bean:message key="sq.discount" bundle="common"/>:
				</logic:equal>
				</td>
				<td align=left width="20%" styleClass="tableText">
				 <html:hidden property="premiumPaid" name="regBean"   />
				 <html:hidden property="purchaseOption" name="regBean" />
				 <html:hidden property="dealDate" name="regBean" />
					<bean:write property="premiumPaid" name="regBean" /> 				
				</td>			
			<td  width="20%" class="tableTextBold" align=right nowrap>
			        
					<bean:message key="sq.remarks" bundle="common"/>:
				</td>

				<td width="20%" align=left styleClass="tableText" >
				  <html:hidden property="remarks" name="regBean"   />
					<bean:write property="remarks" name="regBean"  /> 
				</td>	
				</tr>
				</table>
				
					<table width="600" border=0  align="center" >
				<tr>
			<td  colspan=5 align=center>
				<html:submit styleClass="butt"   tabindex="3" > <bean:message key="button.save" bundle="common"/></html:submit>&nbsp;
				<html:button  styleClass="butt" property="Clear" tabindex="4" onclick="clearData();"><bean:message key="button.clear" bundle="common"/></html:button>
			</td>	
			</tr>
			</table>
			
				<%=ScreenUtilities.screenFooter()%>
	</html:form>
	</body>
</html>
