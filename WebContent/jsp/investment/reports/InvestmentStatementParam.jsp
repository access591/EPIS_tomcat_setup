<%@ page language="java" import="com.epis.bean.investment.InvestmentReportsBean,com.epis.utilities.ScreenUtilities" %>
<%@ taglib uri="/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags-html" prefix="html" %>
<%@ taglib uri="/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags-display" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/displaytagstyle.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="js/CommonFunctions.js" ></SCRIPT>
		<SCRIPT type="text/javascript">
		 var vWinCal;
		 function validate(){
	//alert(document.forms[0].reportType.value);
		    
	var url = "investmentReports.do?method=reportInvestStmt&year="+document.forms[0].year.value+"&security="+document.forms[0].securityName.value+"&reportType="+document.forms[0].reportType.value;
     var sWidth=screen.width-10;
	 var sHeight=screen.height-120;
    
var windSize="width=222,height=222,top=222,left=222,status=no,scrollbars=yes,resizable=yes";
wind1=window.open(url,"popup1","menubar=yes,toolbar = yes,status=no,scrollbars=yes,resizable=yes,width="+sWidth+",height="+sHeight+",top=0,left=0");

wind1.window.focus();
			return false;
		 }
   
  function clearData(){
   document.forms[0].reset();
   document.forms[0].year.focus();
   
  }
 function closeWin() {
		if( vWinCal != null ) vWinCal.close();
		if( wind1 != null ) wind1.close();
		//if( win_id != null ) win_id.close();
	}
	function loadYears()
		{
		var curryear=new Date().getYear();
		  document.forms[0].year.options[0]=new Option("[Select One]",''); 
		   for(var j=2000;j<=2017;j++)
			{
			    var i=document.forms[0].year.options.length;
			    var yer=j+"-"+(j+1);
			    if(j==curryear)
				{ 
				   document.forms[0].year.options[i]=new Option(yer,j);
				   document.forms[0].year.options[i].selected=true;
				}
				else
				{
					document.forms[0].year.options[i]=new Option(yer,j);
				}
			}
		}
  
		</script>
	</head>
	<body onunload="closeWin();" onload="loadYears()">
	<html:form action="/investmentReports.do?method=reportArrangers" onsubmit="return validate();">
	<%=ScreenUtilities.screenHeader("report.investstatement")%>
			<table width="550" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan=4 align=center>
						<html:errors bundle="error" />
					</td>
				</tr>
			 <tr>                    
			 	 <td  class="tableTextBold" width="50%" align=right nowrap><bean:message key="report.year" bundle="common"/>:</td>
                 <td align=left> <html:select property="year" style="width: 125px">
                     <options value="">Select one</options>
                   </html:select></td> 
                 <td>&nbsp;</td>
             </tr>
			<tr>
				<td  class="tableTextBold" width="50%" align=right><bean:message key="sq.securityname" bundle="common"/>:</td>
                  <td style="width: 130px;" align=left><html:text size="25" styleClass="TextField" property="securityName" maxlength="25" />
                  </td>
                      </tr>    
			
			<tr>
					<td class="tableTextBold" align="right">
						<bean:message key="accCode.reportType" bundle="common" /> :
					</td>
					<td align="left">
						<html:select property="reportType"  styleClass="TextField" style="width: 125px">
						<html:option value="html">
							HTML
						</html:option>
						<html:option value="excel">
							Excel
						</html:option>
							
						</html:select>
					</td>
				</tr>
				<tr>
				<td colspan=4 align=center>&nbsp;
				</td>
			</tr>
			<tr>
			<td  colspan=4 align=center><html:submit styleClass="butt" property="Search" value="Ok" />&nbsp;<html:button styleClass="butt" property="button" value="Clear"  onclick="clearData();"/>
			
			</td>	
			</tr>
			</table>
			
		<%=ScreenUtilities.searchFooter()%>
	</html:form>
	</body>
</html>
