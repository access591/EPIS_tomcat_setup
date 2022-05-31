
<%@ page language="java" import="com.epis.utilities.ScreenUtilities"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-c" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		
      
	</head>
	<body background="body">
	<html:form action="/actionYTM.do?method=updateQuotation" onsubmit="return selectCheckboxes()">
	
		<table width="550" border=0 align="center" >
			<tr>
				<td align="center" >
					<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<td rowspan="5" >
								<img src="images/logoani.gif" width="87" height="50" align="right" alt="" />
							</td>
							<th class="tblabel" align="center" valign="middle" colspan=7>
								<bean:message key="com.aai" bundle="common" />
							</th>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="0" align="center" valign="middle">
						<tr>
							<th class="tblabel"><bean:message key="ytm.newtitle" bundle="common" /></th>
							<td class="tblabel" align="center" valign="middle" colspan=7>&nbsp</td>
							</tr>
						</table>
						</td></tr>
                 
                        <tr>
                        <td valign=top>
                         <table  border=1  cellpadding=0 cellspacing=0 >
                         <tr>       <td  class="tableTextBold" width="5%" nowrap><bean:message key="ytm.qualified" bundle="common"/></td>   
                                  <td  class="tableTextBold" nowrap> <bean:message key="ytm.srno" bundle="common"/></td> 
                                  <td  class="tableTextBold" nowrap ><bean:message key="ytm.securityname" bundle="common"/></td> 
                                  <td  class="tableTextBold" nowrap ><bean:message key="yttm.couponrate" bundle="common"/></td>  
                                  <td  class="tableTextBold" nowrap> <bean:message key="ytm.dealdates" bundle="common"/> </td>  
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.maturitydates" bundle="common"/> </td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.settlementdates" bundle="common"/></td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.facevalueperunit" bundle="common"/></td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.purchasePricePerunit" bundle="common"/></td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.noofunits" bundle="common"/></td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.facevalueinrs" bundle="common"/></td>
                                  <td class="tableTextBold" nowrap><bean:message key="ytm.purchasepriceinrs" bundle="common"/></td>
                                  <td class="tableTextBold" nowrap><bean:message key="ytm.displayytm" bundle="common"/></td>
                                  <td  class="tableTextBold" nowrap><bean:message key="ytm.arranger" bundle="common"/></td>
                                  
                                   
                               </tr>
                          
                                <c:forEach var='letter' items='${letterdetails}'> 
                                 <tr >
                                 <td  width="5%" nowrap><c:out value='${letter.status}'/></td>
                                    <td class="tableText"  nowrap><c:out value='${letter.SNo}'/></td> 
                               	   <td class="tableText"  nowrap><c:out value='${letter.securityName}'/></td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.interestRate}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.dealDate}'/> &nbsp;</td> 
                                   <td class="tableText" nowrap><c:out value='${letter.maturityDate}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.settlementDate}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.investmentFaceValue}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.purchasePrice}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.numberOfUnits}'/> &nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.totalFaceValue}'/>&nbsp;</td> 
                                   <td class="tableText"  nowrap><c:out value='${letter.totalPurchasePrice}'/>&nbsp;</td> 
                                   
                                   <td class="tableText"  nowrap><font color="<c:out value='${letter.bgcolor}'/>"><c:out value='${letter.ytm}' /> &nbsp;</font></td>  
                                   <td class="tableText"  nowrap><c:out value='${letter.arranger}' /> &nbsp;</td> 
                                   
                               </tr>
                               </c:forEach>
                              </table>
                          </td>   
                        </tr>
                        
                        
                 </td>       
              </tr>
       	</table>
				<%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>
