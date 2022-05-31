<%@ page language="java" import="java.util.*,com.epis.utilities.*,com.epis.bean.rpfc.*" pageEncoding="UTF-8"%>

<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.ArrayList" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />
		<LINK rel="stylesheet" href="<%=basePath%>css/displaytagstyle.css" type="text/css" />
<title>AAI</title>
</head>

<body>
<%
String reportType="",fileName="",filePrefix="",filePostfix="";
if(request.getAttribute("AAIEPF1List")!=null){
if (request.getAttribute("reportType") != null) {
				reportType = (String) request.getAttribute("reportType");
				if (reportType.equals("Excel Sheet")
						|| reportType.equals("ExcelSheet")) {
				
					filePrefix = "CPFDeviation_Report";
					fileName=filePrefix+".xls";
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + fileName);
				}
			}
%>
<table width="1178" border="0">
  <tr>
    <td width="5">&nbsp;</td>
    <td width="1">&nbsp;</td>
    <td width="20">&nbsp;</td>
    <td width="135">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="38">&nbsp;</td>
    <td width="240">&nbsp;</td>
    <td width="14">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td colspan="2"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="21" colspan="2" width="177">&nbsp;</td>
      </tr>
    </table></td>
    <td width="360">&nbsp;</td>
  </tr>
  
  <tr>
    <td colspan="7" rowspan="2"><img src="<%=basePath%>/images/logoani.gif"  width="75" height="55" align="right" /></td>
    <td nowrap="nowrap" rowspan="2">AIRPORTS AUTHORITY OF INDIA</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>

  </tr>
  <tr>
    <td nowrap="nowrap"></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
 
  </tr>
  
  <tr>
    <td colspan="13"><table cellspacing="0" cellpadding="0">
      <tr>
        <td height="24" colspan="8" width="620" align="right">CPF DEVIATION STATEMENT</td>
      </tr>
    </table></td>
    
    
    
    
  </tr>
 
 
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width="3">&nbsp;</td>
    <td width="271">&nbsp;</td>

  </tr>
 
  <tr>
    <td colspan="13"><table width="1166" border="1"  cellpadding="0" cellspacing="0">
      <tr>
        <td width="47"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="48" />
          <tr>
            <td width="48" valign="top" class="reportsublabel"><div align="center">Sl No</div></td>
          </tr>
          <tr> </tr>
        </table></td>
        <td   valign="top" class="reportsublabel"><div align="center">PF ID</div></td>
          <td  valign="top" class="reportsublabel"><div align="center">Month Year</div></td>
        <td   valign="top" class="reportsublabel"><div align="center">CPF A/c No</div></td>
        <td   valign="top" class="reportsublabel"><div align="center">Emp No</div></td>
        <td   valign="top" class="reportsublabel"><div align="center">Emp Name</div></td>
        <td   valign="top" class="reportsublabel"><div align="center">Designation</div></td>
        <td   valign="top" class="reportsublabel" align="center" >Emoluments</td>
        <td  valign="top" class="reportsublabel"><div align="center">PF Statuary</div></td>
        <td   valign="top" class="reportsublabel" nowrap="nowrap"><div align="center">Calc CPF</div></td>
        <td   valign="top" class="reportsublabel"><div align="center">Diff CPF</div></td>
       
        <td  align="center"  valign="top"><table cellspacing="0" cellpadding="0">
          <col width="68" />
          <tr>
            <td  align="center" valign="top"><div align="center" class="reportsublabel">Station&nbsp;</div></td>
          </tr>
          <tr> </tr>
        </table></td>
        <td width="62"   valign="top"><table cellspacing="0" cellpadding="0">
          <col width="69" />
          <tr>
            <td   width="69"  valign="top" class="reportsublabel"><div align="center">Region</div></td>
          </tr>
          <tr> </tr>
        </table></td>
      </tr>
    
      <%
          DecimalFormat df = new DecimalFormat("#########0");
        ArrayList AAIEPF1List=new ArrayList();
        FinacialDataBean AAIEPF1Bean=new FinacialDataBean();
        
        
        AAIEPF1List=(ArrayList)request.getAttribute("AAIEPF1List");
        System.out.println("--------AAIEPF1List Size in JSP----------"+AAIEPF1List.size());
        int slno=1;
        float subGrandTotal=0,contGrandTotal=0,outstandGrandTotal=0;
        
        for(int i=0;i<AAIEPF1List.size();i++){
        
        AAIEPF1Bean=(FinacialDataBean)AAIEPF1List.get(i);
        
        System.out.println("------Pension no--------"+AAIEPF1Bean.getPensionNumber());
        
        %>
         <tr>
        <td class="Data"><%=slno%></td>
        <td class="Data"><%=AAIEPF1Bean.getPensionNumber()%></td>        
        <td class="Data"><%=AAIEPF1Bean.getMonthYear()%></td>
        <td class="Data"><%=AAIEPF1Bean.getCpfAccNo()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmployeeNewNo()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmployeeName()%></td>
        <td class="Data"><%=AAIEPF1Bean.getDesignation()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmoluments()%></td>
        <td class="Data"><%=AAIEPF1Bean.getEmpPfStatuary()%></td>
        <td class="Data"><%=AAIEPF1Bean.getCpfArriers()%></td>
        <td class="Data"><%=AAIEPF1Bean.getCpfDifference()%></td>
        <td class="Data"><%=AAIEPF1Bean.getAirportCode()%></td>
        <td class="Data"><%=AAIEPF1Bean.getRegion()%></td>
       
      </tr>
        
        <%
      
        slno++;
        }
        
       %>
            
      
    </table>
      </td>
  </tr>
  <%}else{
        
        %>
        
          <table align="center" width="100%">
          <tr>
          <td align="center">
          <strong>No Records Found</strong>
          </td>
          </tr>
          </table>
        <%
        }
      
      %>
 
 
</table>
</body>
</html>
