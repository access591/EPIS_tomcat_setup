
<%@ page  import="java.util.ArrayList,java.util.List"%>
<%@ page language="java" isErrorPage="true"%>
<%@ page import="com.epis.bean.cashbookDummy.PartyInfo" %>
<%@ page import="com.epis.utilities.ScreenUtilities" %>


<%
StringBuffer basePath = new StringBuffer(request.getScheme());
	 basePath.append("://").append(request.getServerName()).append(":");
	 basePath.append(request.getServerPort()).append(request.getContextPath());
	 basePath.append("/");
	 String partyValue="";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>AAI - CashBook - Bank Info</title>
		<link rel="stylesheet" href="<%=basePath%>css/aai.css" type="text/css" />
		<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/displaytagstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">    			
 	
		 	function testSS(){
		   		var partyName=document.forms[0].partyNameText.value;
		   		var url ="<%=basePath%>Party?method=getPartyList&pName="+partyName+"&mType="+window.opener.document.forms[0].party.value;		
		   		document.getElementById("info").innerHTML="<img src='<%=basePath%>images/loading1.gif'>";
			    document.forms[0].action=url;
			    document.forms[0].method="post";
				document.forms[0].submit();
			}
	
	
			 function sendInfo(partyName,detail,isin){			 
				 window.opener.partyDetails(partyName,detail,isin);	 
				 window.close();			 
			}
			function loadValue(){
			//alert("pp"+window.opener.document.forms[0].party.value);
			if(window.opener.document.forms[0].party.value=='I'){
			 C.style.display="none";
			 I.style.display="block";
			
			}else{
			 C.style.display="block";
			I.style.display="none";
			}
			
			}
	 </script>
	</head>

	<body onload="loadValue();">
		<form>
			<%=ScreenUtilities.screenHeader("party.title")%>
			<table align="center" width=80%>
				<tr>
					<TD align="center">
						<TABLE align="center">
							<TR>
							
						
								<td class="label" id="C">
									Party Name:
								</td>
								<td class="label" id="I">
									ISIN:
								</td>
								<td>
									<input type="text" name="partyNameText" maxlength=25 />
									&nbsp;
								</td>&nbsp;					
								<td>
									&nbsp;
								</td>
							</TR>
							<tr>
							<!-- <td class="label">
							
							Module Tyep:
							</td>
							<td>
							<select name="moduleType">
							<option value="">[Select One]</option>
							<option value="I">Investment</option>
							<option value="C">Cash Book</option>
							</select>
							</td>-->
						
							<td colspan=3>
									<input type="button" class="butt" value="Search" class="btn" onclick="testSS();">
								</td>
							</tr>
						</TABLE>
					</TD>
				</tr>
				<tr>
					<td id="info"  >
					</td>
				</tr>
				<tr>
					<td >
						&nbsp;
					</td>
				</tr>
				<tr>
					<td >
						&nbsp;
					</td>
				</tr>
<%
if (request.getAttribute("PartyList") != null) {
	List dataList = (ArrayList) request.getAttribute("PartyList");
    int listSize = dataList.size();
	if (listSize == 0) {
%>
				<tr>
					<td  class="ScreenSubHeading">
						<H3> No Records Found </H3>
					</td>
				</tr>	
						
<%
	}else{
%>
				<tr>
					<td  class="ScreenSubHeading">
							Party Master Info
					</td>
				</TR>
				<tr>
					<td >
						&nbsp;
					</td>
				</tr>
				<TR>
					<TD >
						<TABLE width=100% border=1 class=border cellpadding="0" cellspacing="0" >
							<tr>
								<td CLASS=LABEL> ISIN</td>
								<td CLASS=LABEL> Party Name</td>
								<td CLASS=LABEL> Party Detail</td>
								<td CLASS=LABEL> Mobile No.</td>
								<td CLASS=LABEL> Mail ID</td>
								<td CLASS=LABEL> Module Type</td>
								<td class=LABEL> RefNo</td>
								<td class=LABEL> Security Name </td>
								<td class=LABEL> Face Value In Rs</td>
								<td class=LABEL>Deal Date</td>
								<td class=LABEL>Settlement Date</td>
								<td>
									
								</td>
							</tr>						
<%
	for(int cnt=0;cnt<listSize;cnt++){
		PartyInfo info = (PartyInfo)dataList.get(cnt);
%>
							<tr>
								<td CLASS=Data><%=info.getISIN()%>&nbsp;</td>
								<td CLASS=Data><%=info.getPartyName()%>&nbsp;</td>
								<td CLASS=Data><%=info.getPartyDetail()%>&nbsp;</td>
								<td CLASS=Data><%=info.getMobileNo()%>&nbsp;</td>
								<td CLASS=Data><%=info.getEmailId()%>&nbsp;</td>
								<td CLASS=Data><%=info.getModuleType()%>&nbsp;</td>
								<td CLASS=Data><%=info.getRefNo()%>&nbsp;</td>
								<td CLASS=Data><%=info.getSecurityName()%>&nbsp;</td>
								<td CLASS=Data><%=info.getFacevalueinRs()%>&nbsp;</td>
								<td CLASS=Data><%=info.getDealDate()%>&nbsp;</td>
								<td CLASS=Data><%=info.getSettlementDate()%>&nbsp;</td>
								<td>
								<input type="checkbox" name="isin" value="<%=info.getISIN()%>" onclick="javascript:sendInfo('<%=info.getPartyName()%>','<%=info.getPartyCode()%>','<%=info.getISIN()%>')" />
								</td>
							</tr>
<%		
	}

%>
							</TABLE>
					</TD>
				</tr>
<%	
}
}
%>
						
			</table>
			<%=ScreenUtilities.searchFooter()%>
		</form>
	</body>
</html>
