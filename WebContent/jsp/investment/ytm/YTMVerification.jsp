
<%@ page language="java" import="com.epis.utilities.ScreenUtilities,com.epis.utilities.StringUtility"%>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-c" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ytmshow=(String)(request.getAttribute("ytmshow"));
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
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ezcalendar.css" rel="stylesheet" type="text/css" />
		<SCRIPT type="text/javascript">
		function selectCheckboxes() { 
		   	var count = 0;
			var str="";
			var single = false;
			var arr = document.forms[0].qualified;
			if(arr.length==undefined){//case for one record only
	    	var che = document.forms[0].qualified.checked;
	    	if(che == true){
	        count = 1;
			str = arr.value;
			single = true;//single record in search			
	      	}
		  	}else{	
			for(var i=0;i<arr.length;i=i+1)
			{
			if(arr[i].checked){
				count=count+1;
				}					
		  	  }
   			}
   if(count==0){
		 alert("Please Check atleast one record");
		 arr[0].focus();
		 return false;
		 }
		 if(!single){
		 for(var i=0;i<arr.length;i=i+1){
			if(arr[i].checked)
				str = str+arr[i].value+",";
		  }	
		  str = str.substring(0,str.length-1);	
	      }
	     document.forms[0].quotationCd.value=str;
	     return true;
	}
	function clearData() { 
		document.forms[0].reset();
		}
      </script>
	</head>
	<body>
	<html:form action="/actionYTM.do?method=updateQuotation" onsubmit="return selectCheckboxes()">
	<%=ScreenUtilities.screenHeader("ytm.newtitle")%>
		<table width="550" border=0 align="center" >
			<tr>
				<td colspan=4 align=center >
				<html:errors  bundle="error"/>
				</td>
			</tr>
                 <Tr><td class="tableTextBold"><bean:message key="ytm.letterno" bundle="common"/>:&nbsp<bean:write name="letterno" property="letterNo"/></td></tr>
                 <tr><td>&nbsp;<html:hidden property="quotationCd" /><html:hidden property="letterNo" name="letterno"/></td></tr>
                        <tr>
                        <td valign=top>
                         <table  border=1 class="TableBorder" cellpadding=0 cellspacing=0 >
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
                                 <td  width="5%" nowrap><input type="checkbox" name="qualified" value="<c:out value='${letter.quotationCd}'/>" <c:out value='${letter.ytmVerified}'/>/></td>
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
                        <%
                        if(ytmshow.equals("yes"))
                        {
                        %>
                        <input type="hidden" name="ytmshow"  value="ytmshow"/>
                        <%
                        }
                        else{
                         %>
                          <input type="hidden" name="ytmshow"  value="ytmsearch"/>
                         <%
                         }
                         %>
                        <tr><td colspan=3 align=center><html:submit  styleClass="butt" value="Save" />&nbsp;<html:button  styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
                        <html:button styleClass="butt" property="Cancel" value="Cancel" onclick="javascript:window.location.href='/actionYTM.do?method=searchYTM'"  onblur="document.forms[0].letterNo.focus();" />
                        </td></tr>
                        
                        
                 </td>       
              </tr>
       	</table>
				<%=ScreenUtilities.screenFooter()%></html:form>
	</body>
</html>
