<%@ page import="com.epis.bean.cashbook.VoucherInfo,com.epis.utilities.ScreenUtilities" %>
<%@ page language="java" pageEncoding="UTF-8" buffer="16kb"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%
StringBuffer basePathBuf = new StringBuffer(request.getScheme());
			basePathBuf.append("://").append(request.getServerName()).append(
					":");
			basePathBuf.append(request.getServerPort());
			basePathBuf.append(request.getContextPath()).append("/");
//String accountType = request.getParameter("accountType") ==null?"":request.getParameter("accountType"); 
int i=0;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - Cashbook - Master - Unreconcile Voucher Param</title>

		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Voucher Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePathBuf%>js/calendar.js"></script>
		<script type="text/javascript" src="<%=basePathBuf%>js/prototype.js"></script>
		<script language="javascript" type="text/javascript">
		
		var k;
		var cwin;
		function showDetails(url,i){	
		url=url+"&&accountNo="+document.getElementById('accountNo'+i).value+"&&i="+i;		
			cwin = window.open(url,'VoucherInfo', 'width=450,height=300,left=450,top=110,resizable=yes,scrollbars=yes');
			k=i;
		}
	    function getStatementsFromchild(id,text,bankAmt){
	    		    	   	
			document.getElementById('keyno'+k).value = id;
			document.getElementById('bankAmt'+k).value=bankAmt;
			
			cwin.close();
		}
		function saveDetails(i){
			
				k=i;
				var checkflag=false;
				var voucherkeyNo="'";
				var voucherAmt=0;
				var accountnumber="'";
				var l;				
		   		if(document.getElementById('keyno'+k).value == ""){
			   		 alert("Please Select Key No (Manadatory)");
			   		 document.getElementById('keyno'+k).focus();
			   		 return false;
		   		 }
		   		 for(var j=0; j<document.forms[0].keynoCheck.length; j++)
		   		 {
		   		 	if(document.forms[0].keynoCheck[j].checked==true)
		   		 	{
		   		 		l=j+1;
		   		 		voucherkeyNo+=document.forms[0].keynoCheck[j].value+"','";
		   		 		voucherAmt+=parseFloat(document.getElementById('voucherAmt'+l).value);
		   		 		accountnumber+=document.getElementById('accountNo'+i).value+"','";
		   		 		checkflag=true;
		   		 	}
		   		 }
		   		 if(!checkflag)
		   		 {
		   			voucherkeyNo+=document.getElementById('voucherkeyNo'+i).value+"','";
		   			accountnumber+=document.getElementById('accountNo'+i).value+"','";
		   			voucherAmt+=parseFloat(document.getElementById('voucherAmt'+i).value);
		   		}
		   		 voucherkeyNo=voucherkeyNo.substr(0,voucherkeyNo.length-2);
		   		 accountnumber=accountnumber.substr(0,accountnumber.length-2);
		   		 
		   		 
		   		 
		   		 
		   		 if(parseFloat(document.getElementById('bankAmt'+i).value)!=parseFloat(voucherAmt))
		   		 {
		   		 	alert("The Voucher Amount and Bank Amount is not Matching");
		   		 	document.getElementById('keyno'+k).focus();
			   		 return false;
		   		}
		   		
		      	var url = "LoadStatements.do?method=saveDetails";	       
		      	//balinstallments = balinstallments+parseInt(document.getElementById('recinst'+empadvcd).value);	     	     
		     
		      //alert(url);
		      	var ajaxRequest = new Ajax.Request(url, {
					method:       'post', 
					parameters: {keyno: document.getElementById('keyno'+i).value,voucherNo:voucherkeyNo,accountno:accountnumber},
					asynchronous: true,
					onSuccess:   saveDetval
				});  
		 
		}
				 function saveDetval(response){
			alert(response.responseText);
			var checkflag=false;
			 var l;
			 
			for(var j=0; j<document.forms[0].keynoCheck.length; j++)
		   		 {
		   		  
		   		 	if(document.forms[0].keynoCheck[j].checked==true)
		   		 	{
		   		 		l=j+1;
		   		 		document.forms[0].keynoCheck[j].checked=false;
		   		 		document.forms[0].keynoCheck[j].disabled=true;
		   		 		document.getElementById('savebut'+l).disabled = true;
		    			document.getElementById('helpicon'+l).style.display = 'none'; 
		   		 		checkflag=true;
		   		 		
		   		 	}
		   		 	
		   		 }
		   	
			if(!checkflag)
			{
		    document.all('savebut'+k).disabled = true;
		    document.getElementById('helpicon'+k).style.display = 'none';
		    } 			  
		}
			
			
		
		  </script>
	</head>
	<body>
	<html:form action="/voucher.do?method=showReport" method="post" >
			<%=ScreenUtilities.screenHeader("brs.unreconcilevouchers")%>
		<table width="100%" border="0" align="center">
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" >
							<tr>
								<td class="label" align="center" nowrap>&nbsp;</td>
								<td class="label" align="center"  nowrap>
									<b> <bean:message key="voucher.type" bundle="common" /> </b>
									
								</td>
								
								<td class="label" align="center"  nowrap >
									<b> <bean:message key="trust.trustType" bundle="common" /> </b>
								</td>
								<td class="label" align="center"  nowrap>
									<b> <bean:message key="voucher.date" bundle="common" /> </b>
								</td>	
								
								<td class="label" align="center"  nowrap >
									 <b><bean:message key="voucher.no" bundle="common" /> </b>									
								</td>							
								
								
								<td class="label" align="center"  nowrap >
									 <b><bean:message key="voucher.credit" bundle="common" /> </b>									
								</td>	
								
								<td class="label" align="center"  nowrap >
									 <b><bean:message key="voucher.debit" bundle="common" /> </b>									
								</td>	
								
								
								<td class="label" align="center"  nowrap width="5%">
									 <b><bean:message key="voucher.keyno" bundle="common" /> </b>
								</td>
								
		
							</tr>
							<logic:notEmpty  name="voucherlist">
							<logic:iterate id="voucher" name="voucherlist">	
							<% i++; %>						
							<tr>
								<td class="label" align=center">
								<input type="checkbox" name='keynoCheck'  value="<bean:write name="voucher" property="keyNo"/>"/>
								<input type="hidden" name='voucherAmt' id='voucherAmt<%=i%>' value="<bean:write name="voucher" property="amount"/>"/>
								<input type="hidden" name='bankAmt' id='bankAmt<%=i%>'/>
								<td class="Data" align="center" >									
									<bean:write name="voucher"  property="voucherType"/>
									<input type='hidden' name='accountNo<%=i%>' id='accountNo<%=i%>' value="<bean:write name="voucher" property="accountNo"/>"/>
								</td>
								<td class="Data" align="center" >
									<bean:write name="voucher"  property="trustType"/>
								</td>
								<td class="Data" align="center"  >
									<bean:write name="voucher"  property="voucherDt"/>
								</td>
								<td class="Data" align="center" >
									<bean:write name="voucher"  property="voucherNo"/>
									<input type='hidden' name='voucherNo<%=i%>' id='voucherNo<%=i%>' value="<bean:write name="voucher" property="voucherNo"/>"/>
									<input type='hidden' name='voucherkeyNo<%=i%>' id='voucherkeyNo<%=i%>' value="<bean:write name="voucher" property="keyNo"/>"/>
									</td>
								
								<td class="Data" align="center"  >
									<bean:write name="voucher"  property="creditAmount"/>
								</td>
								
								<td class="Data" align="center"  >
									<bean:write name="voucher"  property="debitAmount"/>
								</td>
								
								<td class="label" align="center" width="5%" valign="bottom" nowrap="nowrap">
									<input type="text" name='keyno<%=i%>' id='keyno<%=i%>' maxlength='15' Class="tableText" style="width:80px " readonly />	&nbsp;									
								</td>	
								<td align="center">
								<div id='helpicon<%=i%>'  style="display:inline;" >
									<img id="img" src="<%=basePathBuf%>images/search1.gif" style="cursor:hand;" onclick="showDetails('LoadStatements.do?method=showDetails','<%=i%>');"/>
									</div>
								<td align="center">												
					  		     <input type="button" value="S" onclick="saveDetails('<%=i%>')" id="savebut<%=i%>" />
					  	
					  	 	 	</td>
							</tr>
							</logic:iterate>	
							</logic:notEmpty>
							<logic:empty name="voucherlist">
							<tr>
								<td class="tbb" colspan="6" align="center" >
									<font color="red" size="4" >
										<bean:message key="common.norecords" bundle="common" />
									</font>
								</td>
							</tr>
							</logic:empty>														
						</table>
					</td>
				</tr>	
						
		</table>
		<%=ScreenUtilities.searchFooter()%>		
</html:form>
</body>
</html>