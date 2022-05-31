<%@ page import="com.epis.bean.investment.AmountTypeBean,com.epis.utilities.ScreenUtilities" %>
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
		<title>AAI - Investment - investmentprocedure - Party Amount Details </title>

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
	    function getStatementsFromchild(id,text){	    	   	
			document.getElementById('keyno'+k).value = id;
			cwin.close();
		}
		function saveDetails(index,securityname,amountdate,amount,rowid){
				k=index;	
				
		   		
		   		if(document.getElementById("party["+index+"].amounttype").value=="")
		   		{
		   			alert("Please Select Amount Type");
		   			document.getElementById("party["+index+"].amounttype").focus();
		   			return false;
		   		}
		   		
		   		
		   		
		      	var url = "partyamount.do?method=updatePartyDetails";	       
		      	
		      	var ajaxRequest = new Ajax.Request(url, {
					method:       'post', 
					parameters: {securityName:securityname,amountdate:amountdate,amount:amount,rowvalue: rowid,amounttype:document.getElementById("party["+index+"].amounttype").value},
					asynchronous: true,
					onSuccess:   saveDetval
				}); 
		 
		}
		 function saveDetval(response){
			alert(response.responseText);
		    document.all('savebut'+k).disabled = true;
		    document.getElementById('helpicon'+k).style.display = 'none'; 			  
		}
			
		function selectCheckboxes(optiontype) { 
		if(optiontype=='Add'){		 
			
			 window.location.href='partyamount.do?method=showPartyAmountTypeAdd';
		 }
		 }
				
		
		  </script>
	</head>
	<body>
	<html:form action="/partyamount.do?method=partyDetails" method="post" >
			<%=ScreenUtilities.screenHeader("partyamount.title")%>
		<table width="100%" border="0" align="center">
		 <tr>
                         <td  align="left" valign="top"><%=ScreenUtilities.getAcessOptions(session,7)%></td>
                          <td  align="left" valign="top" colspan=3>&nbsp;</td>
                        </tr>
				<tr>
					<td   align="center" nowrap colspan="4">
						<html:errors bundle="error" />
					</td>
					
				</tr>	
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" >
							<tr>
								<td class="label" align="left"  nowrap>
									<b> <bean:message key="partyamount.employeepartycode" bundle="common" /> </b>
									
								</td>
								<td class="label" align="left" nowrap>
								<b><bean:message key="partyamount.amountdate" bundle="common"/>
								
								<td class="label" align="center"  nowrap >
									<b> <bean:message key="partyamount.credit" bundle="common" /> </b>
								</td>
								<td class="label" align="center"  nowrap>
									<b> <bean:message key="partyamount.accounthead" bundle="common" /> </b>
								</td>	
								
								<td class="label" align="center"  nowrap >
									 <b><bean:message key="partyamount.voucherno" bundle="common" /> </b>									
								</td>							
								
								
								<td class="label" align="center"  nowrap >
									 <b><bean:message key="partyamount.amountType" bundle="common" /> </b>									
								</td>	
								
								
								
		
							</tr>
							<logic:notEmpty  name="partydetails">
							<logic:iterate id="party" name="partydetails" >	
												
							<tr>
								<td class="DataLeft" align="left" >									
									<bean:write name="party"  property="emppartycode"/>
									
								</td>
								<td class="DataLeft" align="left" >									
									<bean:write name="party"  property="amountdate"/>
									
								</td>
								<td class="DataRight" align="right" >
									<bean:write name="party"  property="credit"/>
								</td>
								<td class="Data" align="center"  >
									<bean:write name="party"  property="accounthead"/>
								</td>
								<td class="DataLeft" align="left" >
									<bean:write name="party"  property="voucherno"/>
									
								</td>
								
								
								
								<td class="label" align="center" width="5%" valign="bottom" nowrap="nowrap">
								<html:select name="party" property="amounttype" indexed="true"  style='width:100px'  styleClass="TextField" tabindex="1">
							<html:option value="">[SelectOne]</html:option>
							<html:options collection="amounttypelist" property="code" labelProperty="name" />
						</html:select>
						</td>	
								<td align="center">
								
								<td align="center">												
					  		     <input type="button" value="S" onclick="saveDetails('<%=i%>','<bean:write name="party" property="emppartycode"/>','<bean:write name="party" property="amountdate"/>','<bean:write name="party" property="credit"/>','<bean:write name="party" property="rowid"/>')" id="savebut<%=i%>" />
					  	
					  	 	 	</td>
							</tr>
							<% i++; %>	
							</logic:iterate>	
							</logic:notEmpty>
							<logic:empty name="partydetails">
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