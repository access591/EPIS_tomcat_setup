
<%@ page language="java" import="com.epis.utilities.ScreenUtilities,com.epis.bean.cashbook.PartyInfo,java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags-html" prefix="html"%>
<%@ taglib uri="/tags-bean" prefix="bean"%>


<%
StringBuffer basePath = new StringBuffer(request.getScheme());
	 basePath.append("://").append(request.getServerName()).append(":");
	 basePath.append(request.getServerPort()).append(request.getContextPath());
	 basePath.append("/");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>CashBook To Investment Mapping</title>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/overlib.js"></script>
		<script type="text/javascript">    			
 		var httpRequest; 
 		var detailArray=new Array();
		 function clearDetails()
		{
		document.forms[0].cbPartyName.value="";
		document.forms[0].invPartyName.value="";
		document.forms[0].cbPartyName.focus();
		refreshDetails();
		}
		
		function saveDetails()
		{
			
			save();
		}
	function save()
	{
		
			if(document.forms[0].cbPartyName.value=="")
			{
					alert("Please Enter Cash Book Party Names[Mandatory]");
					document.forms[0].cbPartyName.focus();
					return(false);
			}
	
			if(document.forms[0].invPartyName.value=="")
			{
					alert("Please Enter Investment  Party Names[Mandatory]");
					document.forms[0].invPartyName.focus();
					return(false);
			}
			
		
		detailArray[detailArray.length]=[document.forms[0].cbPartyName.value,document.forms[0].invPartyName.value];
		var url;
		var cbName=document.forms[0].cbPartyName.value;
		var invName=document.forms[0].invPartyName.value;
		cbName=cbName.replace(/%/g,'~');
		cbName=cbName.replace(/#/g,'ß');
		invName=invName.replace(/%/g,'~');
		invName=invName.replace(/#/g,'ß');
		url="/jsp/investment/quotation/ArrangersLoad.jsp?cbName="+cbName+"&invName="+invName+"&mode=cbtoinv";
		sendURL(url,"showValues");
}
function refreshDetails()
{
	
	document.forms[0].cbPartyName.length=1;
	document.forms[0].invPartyName.length=1;
	var url;
	url="/jsp/investment/quotation/ArrangersLoad.jsp?mode=cbtoinvrefresh";
	sendURL(url,"showRefreshValues");
		
	
}

function showRefreshValues()
{
	
	if (httpRequest.readyState == 4)
		{ 
			if(httpRequest.status == 200) 
				{ 			
					var node = httpRequest.responseXML.getElementsByTagName("PartyData")[0];
													
   					if(node) 
					{
						for (i = 0; i < parseInt(node.childNodes.length); i++) 
						{
							var bills = node.childNodes[i];
						     if(bills.getElementsByTagName("ModuleType")[0].firstChild.nodeValue=='C')
								document.forms[0].cbPartyName.options[document.forms[0].cbPartyName.options.length]	=	new Option(bills.getElementsByTagName("PartyName")[0].firstChild.nodeValue,bills.getElementsByTagName("PartyName")[0].firstChild.nodeValue);
							else
								document.forms[0].invPartyName.options[document.forms[0].invPartyName.options.length]	=	new Option(bills.getElementsByTagName("PartyNameDesc")[0].firstChild.nodeValue,bills.getElementsByTagName("PartyName")[0].firstChild.nodeValue);
							
							
							
						}

						
					} 
					

				}
		}

}
	function showValues()
	{
		var str='<TABLE border=1 width="80%" cellpadding=1 cellspacing=0 bordercolor=silver>';
		var j=0;
		for(i=0;i<detailArray.length;i++)
		{
			
			str+='<TR>';
			str+='<td class=tb  align=center>'+(j+1)+'</td>';
			if(detailArray[i][0].length<20)
				str+='<TD class=tb align=left>'+detailArray[i][0]+'</TD>';
			else
			{
				str+="<TD class=tb align=left>"+detailArray[i][0].substring(0,20);
				str+="<a class=tbl href=# onMouseOver=\"overlib('"+detailArray[i][0]+"\')\" ";
				str+="onMouseOut='nd()'>...</a></TD>";
			}
			if(detailArray[i][1].length<20)
				str+='<TD class=tb align=left>'+detailArray[i][1]+'</TD>';
			else
			{
				str+="<TD class=tb align=left>"+detailArray[i][1].substring(0,20);
				str+="<a class=tbl href=# onMouseOver=\"overlib('"+detailArray[i][1]+"\')\" ";
				str+="onMouseOut='nd()'>...</a></TD>";
			}
			
			str+='</tr>';
			j++;
		}
		str+='</TABLE>';
		document.all['detailsTable'].innerHTML = str;
		clearDetails();
	}
 	function sendURL(url,responseFunction)
	{           
		if (window.ActiveXObject) {			// for IE  
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP"); 
		} else if (window.XMLHttpRequest) { // for other browsers  
			httpRequest = new XMLHttpRequest(); 
		}

		if(httpRequest) {
			
			httpRequest.open("GET", url, true);  //2nd arg is url with name/value pairs, 3 specify asynchronus communication
			httpRequest.onreadystatechange = eval(responseFunction) ; //which will handle the callback from the server side element
			httpRequest.send(null); 
		}
	}
			
	function loadValues(){ 
	<%
		List  partylist=(List)request.getAttribute("cashinvestList");
		for(int i=0; i<partylist.size(); i++)
		{
			PartyInfo info=(PartyInfo)partylist.get(i);
			%>
			detailArray[detailArray.length]=['<%=info.getPartyName()%>','<%=info.getInvPartyname()%>'];
		<%	
		}
	%>
	showValues();
	}
	
			 
	 </script>
	</head>

	<body onload="loadValues();">
		<html:form action="/partySearchResult.do?method=search">
		<div id="overDiv" style="position:absolute; visibility:hide;z-index:1;"></div>
			<%=ScreenUtilities.screenHeader("party.cashbooktoinvesttitle")%>
			<table align="center" width=80%>
				<tr>
					<td align="center">
						<table align="center">
						<tr>
							<td colspan=5>&nbsp;</td>
						</tr>
						
							<tr>
								<td class="label" >
									<bean:message key="party.cashbookpartynames" bundle="common"/>
								</td>
							<td>
							<html:select property="cbPartyName"  styleClass="TextField" tabindex="11" style="width:350px">
							<html:option value="">[select One]</html:option>
							<html:options collection="cashList" property="partyName"  labelProperty="partyDetail" />
							</html:select>
							</td>
										
								<td class="label" >
									<bean:message key="party.investpartynames" bundle="common"/>
								</td>
								
							<td>
							<html:select property="invPartyName"  styleClass="TextField" tabindex="11" style="width:350px">
							<html:option value="">[select One]</html:option>
							<html:options collection="investList" property="partyName"  labelProperty="invPartyname" />
							</html:select>
							</td>
							<td nowrap>
							<a href="javascript:void(0)" onclick="return saveDetails();"><img border="0" style='cursor:hand' src="<%=basePath%>images/saveIcon.gif" alt="save" /></a>
							<a href=# onclick="clearDetails();">
							<img border="0" style='cursor:hand' src="<%=basePath%>images/cancelIcon.gif" alt="clear" /></a>
							</td>	
							</tr>
							<tr>
							<td colspan=5>
							<div id=detailsTable></div>
							</td>
						</tr>
							
						</table>
					</td>
				</tr>
				</table>
					
	<%=ScreenUtilities.screenFooter()%>
		</html:form>
	</body>
</html>
