<%@ page language="java" pageEncoding="UTF-8" buffer="32kb"%>
<%@ page import="com.epis.utilities.ScreenUtilities"%>
<%@ page import="com.epis.utilities.DBUtility" %>
<jsp:useBean class="com.epis.utilities.SQLHelper" id="sql" />
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>AAI - Cashbook - Master - Bank Info Search</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="description" content="Bank Master Info" />
		<link href="css/epis.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/buttons.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="css/aai.css" type="text/css" />
		<SCRIPT type="text/javascript">
			function check(){
				var form =document.forms[0];
				if(document.getElementById('dataLine').value == ""){
					alert("Please enter Line No(Mandatory)");
					form.dataLine.focus();
					return false;
				}
				if(form.AMOUNT.value != '0' && form.DEBIT_CREDIT.value == '0'){
					alert("If Transaction Amount (Credit/Debit) is selected \n then Amount Type - Credit(C)/Debit(D) should be selected");
					form.DEBIT_CREDIT.focus();
					return false;
				}
				if(form.AMOUNT.value == '0' && form.DEBIT_CREDIT.value != '0'){
					alert("If Amount Type - Credit(C)/Debit(D) is selected \n then Transaction Amount (Credit/Debit) should be selected");
					form.AMOUNT.focus();
					return false;
				}
				if(form.AMOUNT.value != '0' && (form.CREDITAMOUNT.value != '0' || form.DEBITAMOUNT.value != '0')){
					alert("If Transaction Amount (Credit/Debit) is selected \n then Transaction Amount (debit) and  Transaction Amount (Credit) should not be selected");
					return false;
				}
				if(form.CREDITAMOUNT.value != '0' && form.DEBITAMOUNT.value == '0'){
					alert("If Transaction Amount (Credit) is selected \n then Transaction Amount (debit) should be selected");
					form.DEBITAMOUNT.focus();
					return false;
				}
				if(form.CREDITAMOUNT.value == '0' && form.DEBITAMOUNT.value != '0'){
					alert("If Transaction Amount (debit) is selected \n then Transaction Amount (Credit) should be selected");
					form.CREDITAMOUNT.focus();
					return false;
				}								
				if(form.CREDITAMOUNT.value != '0'  && (form.AMOUNT.value != '0' || form.DEBIT_CREDIT.value != '0')){
					alert("If Transaction Amount (Credit) is selected \n then Transaction Amount (Credit/Debit) and Amount Type - Credit(C)/Debit(D) should not be selected");
					return false;
				}
				
				var obj = form.elements;
				var val ='';
				var times = 0;
				for(i=0;i<obj.length;i++){
					val = obj[i].value;
					if(val==0 && obj[i].name != 'CLOSINGBAL'){
						times = parseInt(times)+1;
					}
					for(j=i+1;j<obj.length && val!=0;j++){						
	 					if(obj[i].name!='dataLine' && obj[j].value == val){
	 						document.forms[0].elements[j].focus();
	 						alert("Two Columns No. Should not be Same");
	 						return false;
	 					}
	 				}
	 			}
	 			if(times > 2){
	 				alert("Except Closing Balance atleast Six columns should be selected(Mandatory)");
	 				return false;
				}
			}
		</SCRIPT>
	</head>
	<body onload="document.forms[0].TRANSACTIONDATE.focus()">
		<html:form action="/Configure.do?method=save" method="post" onsubmit="return check()">
			<%=ScreenUtilities.screenHeader("brs.title")%>
			<table width="50%" border="0" align="center">
				<tr>
					<td align="center" nowrap colspan="2">
						<html:errors bundle="error" property="bankName" />
					</td>
				</tr>
				<tr>
					<td  class="tableTextBold">
						<bean:define id="accno"><bean:write name="accountNo"/></bean:define>
						Bank : 
					</td>
					<td  class="label" nowrap="nowrap">
						<%=sql.getDescription("cb_bank_info","BANKNAME","accountNo",accno,DBUtility.getConnection())+" ("+accno+")"%>
						<html:hidden property="accountNo" value="<%=accno%>"/>
					</td>
				</tr>
				
				<tr>
					<td  class="tableTextBold">
						<font color="red">*</font> Data Starts From Line : 
					</td>
					<td  class="label" nowrap="nowrap">
						<html:text property="dataLine" size="5" />
					</td>
				</tr>
				
				<tr>
					<td  class="tableTextBold">
						&nbsp;
					</td>					
				</tr>
				<tr>
					<td  class="epis" align="right">
						Column Type&nbsp;&nbsp;&nbsp;
					</td>
					<td  align="left" class="epis">
						&nbsp;&nbsp;&nbsp;Column No.
					</td>
				</tr>
				<tr>
					<td  class="tableTextBold" style="height: 5px">
					</td>					
				</tr>
			<logic:iterate  id="column" name="columns">				
				<bean:define id="colVal" name="column" property="columnValue" type="java.lang.String"/>
				<bean:define id="colNo" type="java.lang.String" >
					<logic:empty name="mappedColumns" property="colMapping">0</logic:empty>
					<logic:iterate  id="colMap" name="mappedColumns" property="colMapping">	
						<logic:equal name="colMap" property="columnValue" value="<%=colVal%>">
							<bean:write name="colMap" property="columnNo" />
						</logic:equal>
					</logic:iterate>
				</bean:define>				
				<tr>					
					<td class="tableTextBold" width="50%" nowrap="nowrap"><bean:write name="column"  property="description"/>&nbsp;&nbsp;&nbsp;</td>
					<td align="left" width="50%">&nbsp;&nbsp;&nbsp;					
						<select id="<bean:write name="column"  property="columnValue"/>" name="<bean:write name="column"  property="columnValue"/>" style="width:55px">
							<OPTION value="0">Select</OPTION>
						<c:forEach step="1" end="7" begin="1" var="i" >
							<c:if test='${i == colNo}'>						
								<OPTION value="<c:out value="${i}" />" selected="selected"><c:out value="${i}" /> </OPTION>
							</c:if>
							<c:if test='${i != colNo}'>						
								<OPTION value="<c:out value="${i}" />" ><c:out value="${i}" /> </OPTION>
							</c:if>
						</c:forEach>							
						</select>
					</TD>					
				</tr>
			</logic:iterate>
				<tr>
					<td align="center" nowrap colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<html:submit styleClass="butt">
							<bean:message key="button.save" bundle="common" />
						</html:submit>
						<html:button styleClass="butt" property="Clear" onclick="javascript:document.forms[0].reset()">
							<bean:message key="button.clear" bundle="common" />
						</html:button>
					</td>
				</tr>
			</table>
			<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>









