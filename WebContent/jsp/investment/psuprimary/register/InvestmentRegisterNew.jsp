<%@ page language="java" import="com.epis.utilities.ScreenUtilities" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
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
		    
		 }
		function clearData(){
       		  document.forms[0].reset();    	 
  		}
  	</SCRIPT>
	</head>
	<body onload="getInvestSecurity();">
	<html:form action="/addInvestmentRegister.do?method=addInvestmentRegister" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("investmentRegister.title")%>
	

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
                       <bean:message key="investmentRegister.title" bundle="common" /> 
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
					 <bean:message key="investmentRegister.trusttype" bundle="common"/>: </font>
				</td><td align=left   styleClass="tableText">
				     <html:hidden property="trustType" name="registerBean"/>
					<bean:write property="trustType" name="registerBean"    />
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
			  <html:hidden  property="letterNo" name="registerBean"/>
			  <html:hidden property="securityName" name="registerBean" />
					<bean:message key="investmentRegister.securityname" bundle="common"/>:
				</td>
				<td  align=left width="20%" styleClass="tableText" colspan="3" nowrap>
				 <bean:write property="securityName" name="registerBean"/>
				</td>
				<tr>									
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.interestdate" bundle="common"/>:
				</td>
				<td width="20%" align=left  styleClass="tableText">
				    <html:hidden   property="interestDate" name="registerBean"/>
				    <html:hidden   property="interestDate1" name="registerBean"/>
					<bean:write property="interestDate" name="registerBean"   />
			  </tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.proposalrefno" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >				   
				<html:hidden   property="refNo" name="registerBean"/>
					<bean:write property="refNo" name="registerBean"   />
							
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="investmentRegister.securitycategory" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				   <html:hidden   property="securityCategory" name="registerBean"/>
					<bean:write property="securityCategory" name="registerBean"   />
				</td>
				
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.amountinv" bundle="common"/>:
				</td>
				<td width="20%" align=left nowrap>
					<html:hidden   property="amountInv" name="registerBean"/>
					<bean:write property="amountInv" name="registerBean"   />			
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.noofbonds" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden   property="noofBonds" name="registerBean"/>
					<bean:write property="noofBonds" name="registerBean"   /> 				
				</td>
				</tr>
				
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.confirmed" bundle="common"/>:
				</td>
				<td width="20%" align=left nowrap>
					<html:hidden   property="confirm" name="registerBean"/>
					<html:hidden  property="investdate" name="registerBean"/>
					<bean:write property="confirmationDef" name="registerBean"   />			
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.dealdate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden   property="dealDate" name="registerBean"/>
					<bean:write property="dealDate" name="registerBean"   /> 				
				</td>
				</tr>
				
				
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.settlementDate" bundle="common"/>:
				</td>
				<td width="20%" align=left nowrap>
					<html:hidden   property="settlementDate" name="registerBean"/>
					<bean:write property="settlementDate" name="registerBean"   />			
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.maturityDate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden   property="maturityDate" name="registerBean"/>
					<bean:write property="maturityDate" name="registerBean"   /> 				
				</td>
				</tr>
				
				
				<tr>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.cuponrate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="cuponRate" name="registerBean"  />
					<bean:write property="cuponRate" name="registerBean"  /> 					
				</td>
				 <td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.premiumpaidperunit" bundle="common"/>:
				</td><td width="20%" align=left styleClass="tableText">
				    <html:hidden property="premiumPaid" name="registerBean" />
					<bean:write property="premiumPaid" name="registerBean"  />     
				</td>
							
				</tr>
				
				
				<tr>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.facevalue" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="faceValue" name="registerBean"  />
					<bean:write property="faceValue" name="registerBean"  /> 					
				</td>
				 <td  width="20%" class="tableTextBold" align=right nowrap>
				 <logic:equal name="registerBean" property="purchaseOption" value="P">
				 
					 <bean:message key="confirmationcomapny.premiumpaidperunit" bundle="common"/>:
				</logic:equal>
				<logic:equal name="registerBean" property="purchaseOption" value="D">
				 
					 <bean:message key="confirmationcomapny.discountpaidperunit" bundle="common"/>:
				</logic:equal>
				</td><td width="20%" align=left styleClass="tableText">
				    <html:hidden property="purchaseOption" name="registerBean" />
					<bean:write property="purchaseOptionDef" name="registerBean"  />     
				</td>
							
				</tr>
				
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.offeredprice" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText" >
				    <html:hidden property="offeredPrice" name="registerBean"/>
					<bean:write property="offeredPrice" name="registerBean" />   					
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="investmentRegister.ytm" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="ytmValue" name="registerBean" />
					<bean:write property="ytmValue" name="registerBean"   />    
				</td>
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					  <bean:message key="investmentRegister.calloptiondate" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText">
				    <html:hidden property="callOption" name="registerBean" />
					<bean:write property="callOptinDef" name="registerBean"  /> 				
				</td>	
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.calldate" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText">
				    <html:hidden property="callDate" name="registerBean" />
					<bean:write property="callDate" name="registerBean"  /> 
				</td>	
				
				</tr>
				<tr>
				<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.modeofinterest" bundle="common"/>:
				</td>
				<td width="20%" align=left styleClass="tableText" >
				    <html:hidden property="modeOfInterest" name="registerBean" />
					<bean:write property="modeOfInterestDef" name="registerBean"  />  
				</td>
				
				<td  width="20%" class="tableTextBold" align=right nowrap>
					<bean:message key="investmentRegister.purchaseprice" bundle="common"/>:
				</td>
				<td align=left width="20%" >
					<html:hidden property="purchasePrice" name="registerBean" />
					<bean:write property="purchasePrice" name="registerBean"  /> 
				</td>
				</tr>
				
			<tr>
			<td  width="20%" class="tableTextBold" align=right nowrap>
					 <bean:message key="investmentRegister.agreed" bundle="common"/>:
				</td>
				<td align=left width="20%" styleClass="tableText">
				 <html:select property="acceptence" styleClass="TextField" style="width:120px">
								
								<html:options collection="agreeList" property="key" labelProperty="value" />
							</html:select>				
				</td>
						
			<td  width="20%" class="tableTextBold" align=right nowrap>
			<bean:message key="lettertobank.settlementamount" bundle="common"/>
			</td>
			<td align=left width="20%">
			<bean:write name="registerBean" property="settlementAmt"/>
			<input type="hidden" name="settlementAmount" value="<bean:write name="registerBean" property="settlementAmt"/>" />
			      <input type="hidden" name="mode" value="<bean:write name="registerBean" property="mode"/>"/> 
			      <html:hidden property="securityFullName" name="registerBean"/> 
					&nbsp;
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
