
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PFCardReport.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/aai.css">
    
  </head>
  
  <body>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   
    <td width="120" rowspan="3" align="center"><img src="<%=basePath%>images/aimslogo.gif" width="88" height="50" align="right" /></td>
    <td class="reportlabel" nowrap="nowrap">AIRPORTS AUTHORITY OF INDIA</td>
    	<td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
     	<td width="96">&nbsp;</td>
     	<td width="95">&nbsp;</td>
     	<td width="85">&nbsp;</td>
  	 	<td width="384"  class="reportlabel">Employee's Provident Fund Trust</td>
  	 	<td width="87">&nbsp;</td>
    	<td width="272">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="3" class="reportlabel" style="text-decoration: underline" align="center">EPF &amp; PENSION CONTRIBUTION CARD </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   
    
    <td colspan="3" align="center" nowrap="nowrap" class="reportsublabel">FOR THE YEAR <font style="text-decoration: underline">2008-09</td>
    
    
  </tr>
  <tr>
    <td colspan="7">&nbsp;</td>
  </tr>
  
  <tr>
    <td colspan="7"><table width="100%" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td width="48%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="36%">Pf Id:</td>
            <td width="64%" class="Data">010571OS03573</td>
          </tr>
		    <tr>
            <td class="reportsublabel">Name:</td>
            <td class="Data">Om Prakash Singh</td>
          </tr>
		     <tr>
            <td class="reportsublabel">DATE OF BIRTH:</td>
            <td class="Data">01-MAY-1971</td>
          </tr>
		  <tr>
            <td class="reportsublabel">DATE OF JOINING:</td>
            <td class="Data">08-DEC-1998</td>
          </tr>
		  <tr>
            <td class="reportsublabel">DATE OF RETIRE:</td>
            <td class="Data">30-APR-2031</td>
          </tr>
		  <tr>
            <td class="reportsublabel">Pension Option</td>
            <td class="Data">Full Pay</td>
          </tr>
        </table></td>
        <td width="52%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" class="reportsublabel">CPF Acc.No(Old):</td>
            <td width="51%" class="Data">26036</td>
          </tr>
          <tr>
            <td width="49%" class="reportsublabel">EMP No:</td>
            <td width="51%" class="Data">300220</td>
          </tr>
		  <tr>
            <td class="reportsublabel">Designation:</td>
            <td class="Data">F&ro</td>
          </tr>
		       <tr>
            <td nowrap="nowrap" class="reportsublabel">Father/ Husband'S Name:</td>
            <td class="Data">Daya Shankar Singh</td>
          </tr>
		   <tr>
            <td class="reportsublabel">Gender:</td>
            <td class="Data">M</td>
          </tr>
		    <tr>
            <td class="reportsublabel">Marital Status:</td>
            <td>---</td>
          </tr>
		    <tr>
            <td nowrap="nowrap" class="reportsublabel">Date Of Pension Membership:</td>
            <td class="Data">08-DEC-1998</td>
          </tr>
        </table></td>
      </tr>
	  
    </table></td>
  </tr>

  <tr>
    <td colspan="7"><table width="100%" border="1" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2">&nbsp;</td>
        <td colspan="7" align="center" class="label">EMPLOYEES SUBSCRIPTION</td>
        <td colspan="3" align="center" class="label">AAI CONTRIBUTION</td>
        <td width="8%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
      </tr>
      <tr>
        <td width="4%" rowspan="2" class="label">Month</td>
        <td width="8%" rowspan="2" class="label">Emolument</td>
        <td width="8%" rowspan="2" class="label"><div align="center">EPF</div></td>
        <td width="3%" rowspan="2" class="label"><div align="center">VPF</div></td>
        <td colspan="2"><div align="center" class="label">Refund Of ADV./PFW </div></td>
        <td width="6%" rowspan="2"  class="label">TOTAL </td>
        <td width="5%" rowspan="2" class="label">Advance<br/>PFW PAID</td>
        <td width="3%" class="label">NET </td>
      
        <td width="3%" align="center" rowspan="2" class="label">AAI<br/>PF</td>
        <td width="6%" class="label" rowspan="2">PFW<br/>DRAWN</td>
        <td width="3%" class="label">NET</td>
        <td width="12%" class="label" rowspan="2">PENSION<br/>CONTR. </td>
        <td rowspan="2" class="label">Station</td>
        <td rowspan="2" class="label">Remarks</td>
      </tr>
      <tr>
       
        <td width="5%" class="label"><div align="center">Principal</div></td>
        <td width="7%" class="label"><div align="center">Interest</div></td>
        <td>(7-8)</td>
      
        <td class="label" >(10-11)</td>
       
      </tr>
      <tr>
        <td>1</td>
        <td>2</td>
        <td class="Data">3</td>
        <td class="Data">4</td>
        <td class="Data">5</td>
        <td class="Data">6</td>
        <td class="Data">7</td>
        <td class="Data">8</td>
        <td class="Data">9</td>
        <td class="Data">10</td>
        <td class="Data">11</td>
        <td class="Data">12</td>
        <td class="label">13</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    
        
  	
  	  <tr>
        <td colspan="2" class="label">OPENING BALANCE (OB)</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="NumData">0</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="NumData">0</td>
         <td class="NumData">0</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
     
      </tr>

       <tr>
        <td colspan="2" class="label">ADJ  IN OB</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="NumData">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td class="NumData">0</td>
         <td class="NumData">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
       
      </tr>
      
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Apr-08</td>
	 <td width="8%" class="NumData">12930</td>
	 <td width="8%" class="NumData">1552</td>
	 <td width="3%" class="NumData">1000</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">2552</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">2552</td>
	
 	 <td width="8%" class="NumData">475</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">475</td>

	 <td width="12%" class="NumData">1077</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
   

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">May-08</td>
	 <td width="8%" class="NumData">12930</td>
	 <td width="8%" class="NumData">1552</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1552</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1552</td>
	
 	 <td width="8%" class="NumData">475</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">475</td>

	 <td width="12%" class="NumData">1077</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
  

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Jun-08</td>
	 <td width="8%" class="NumData">13102</td>
	 <td width="8%" class="NumData">1566</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1566</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1566</td>
	
 	 <td width="8%" class="NumData">475</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">475</td>

	 <td width="12%" class="NumData">1091</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
   

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Jul-08</td>
	 <td width="8%" class="NumData">14050</td>
	 <td width="8%" class="NumData">1686</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1686</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1686</td>
	
 	 <td width="8%" class="NumData">516</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">516</td>

	 <td width="12%" class="NumData">1170</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
   

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Aug-08</td>
	 <td width="8%" class="NumData">29383</td>
	 <td width="8%" class="NumData">3526</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">3526</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">3526</td>
	
 	 <td width="8%" class="NumData">1078</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">1078</td>

	 <td width="12%" class="NumData">2448</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
    

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Sep-08</td>
	 <td width="8%" class="NumData">14917</td>
	 <td width="8%" class="NumData">1790</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1790</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1790</td>
	
 	 <td width="8%" class="NumData">547</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">547</td>

	 <td width="12%" class="NumData">1243</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
    

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Oct-08</td>
	 <td width="8%" class="NumData">14917</td>
	 <td width="8%" class="NumData">1790</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1790</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1790</td>
	
 	 <td width="8%" class="NumData">547</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">547</td>

	 <td width="12%" class="NumData">1243</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
   

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Nov-08</td>
	 <td width="8%" class="NumData">16325</td>
	 <td width="8%" class="NumData">1959</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1959</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1959</td>
	
 	 <td width="8%" class="NumData">599</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">599</td>

	 <td width="12%" class="NumData">1360</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
    </tr>

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Dec-08</td>
	 <td width="8%" class="NumData">16325</td>
	 <td width="8%" class="NumData">1959</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1959</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1959</td>
	
 	 <td width="8%" class="NumData">599</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">599</td>

	 <td width="12%" class="NumData">1360</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
    </tr>

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Jan-09</td>
	 <td width="8%" class="NumData">16325</td>
	 <td width="8%" class="NumData">1959</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1959</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1959</td>
	
 	 <td width="8%" class="NumData">599</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">599</td>

	 <td width="12%" class="NumData">1360</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
 

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Feb-09</td>
	 <td width="8%" class="NumData">40613</td>
	 <td width="8%" class="NumData">4874</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">4874</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">4874</td>
	
 	 <td width="8%" class="NumData">1491</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">1491</td>

	 <td width="12%" class="NumData">3383</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
    </tr>

  
  	
	 <tr>
	 
	 <td width="4%" nowrap="nowrap" class="Data">Mar-09</td>
	 <td width="8%" class="NumData">16325</td>
	 <td width="8%" class="NumData">1959</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" class="NumData">1959</td>
	  <td width="2%" class="NumData">0</td>
	 <td width="5%" class="NumData">1959</td>
	
 	 <td width="8%" class="NumData">599</td>
	 <td width="3%" class="NumData">0</td>
	 <td width="9%" class="NumData">599</td>

	 <td width="12%" class="NumData">1360</td>
	  <td width="8%" nowrap="nowrap" class="Data">North Region</td>
	 <td width="2%" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	
	 
	 
	 <tr>
	 <td  nowrap="nowrap" class="Data">YEAR TOTAL </td>
	 <td  nowrap="nowrap" class="NumData">218142</td>
	 <td width="8%" class="NumData">26172</td>
  	 <td width="3%" class="NumData">1000</td>
     <td width="5%" class="NumData">0</td>
	 <td width="7%" class="NumData">0</td>
	 <td width="6%" nowrap="nowrap" class="NumData">27172</td>
	 <td width="5%" nowrap="nowrap" class="NumData">0</td>
	 <td width="3%"  nowrap="nowrap" class="NumData">27172</td>
	 <td width="3%" class="NumData">8000</td>
	 <td width="6%" class="NumData">0</td>
	 <td width="3%" class="NumData">8000</td>
	 <td width="12%" class="NumData">18172</td>
	  <td colspan="2" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 </tr>
	  <tr>
	 <td width="50" nowrap="nowrap" class="label">INTEREST</td>
	 <td width="116" class="NumData">8.5</td>

	  <td colspan="6" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	  <td width="3%"  class="NumData" nowrap="nowrap">1170</td>
	 <td colspan="2" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	  <td width="3%" class="NumData" nowrap="nowrap">332</td>
	   <td width="3%" class="NumData" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	  <td colspan="3" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 
 </tr>
 <tr>
	 <td colspan="2" nowrap="nowrap" class="label">CLOSING BALANCE</td>
 	 <td colspan="6" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 <td width="3%"  class="NumData" nowrap="nowrap">28342</td>
	 <td colspan="2" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 <td width="3%" class="NumData" nowrap="nowrap">8332</td>
	 
	 <td width="12%" class="NumData">18172</td>
	 <td colspan="4" class="Data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
 </tr>
 <tr>
    <td colspan="15">&nbsp;</td>
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
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td nowrap="nowrap" colspan="6" class="label">RATE OF SUBSCRIPTION   12.00%</td>
    
  </tr>
   <tr>
    <td nowrap="nowrap" colspan="6" class="label">RATE OF INTEREST&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8.50%</td>
    
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
 
  <tr>
    <td colspan="7"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="53%"><table width="100%" border="1" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="5" class="reportsublabel"><div align="center">DETAILS OF PART FINAL WITHDRAWAL (PFW)</div></td>
            </tr>
          <tr>
            <td class="label">Sl No</td>
            <td class="label">Purpose</td>
            <td class="label">Date</td>
            <td class="label">Amount</td>
         
          </tr>
          
        </table></td>
        <td width="47%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <td class="label">NOTE:-</td>
             </tr>
           <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;">1. CREDITS INCLUDES MARCH SALARY PAID IN APRIL TO FEBRUARY SALARY PAID IN MARCH.ADVANCES/PFW SHOWN IN THE MONTH IT IS PAID.</td>
            </tr>
          <tr>
            <td>&nbsp;</td>
            </tr>
          <tr>
            <td class="label" style="text-align:justify;word-spacing: 5px;">2. IN CASE OF ANY DISCREPANCY IN THE BALANCES SHOWN ABOVE THE MATTER MAY BE BROUGHT TO THE NOTICE OF THE CPF CELL WITHIN 15 DAYS OF ISSUE OF THE STATEMENT, OTHERWISE THE BALANCES WOULD BE PRESUMED TO HAVE BEEN CONFIRMED.</td>
            </tr>
          <tr>
            <td>&nbsp;</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
   
    <tr>
  
    <td nowrap="nowrap" colspan="6" class="label">Date</td>
    
  </tr>
    <tr>
    <td nowrap="nowrap" colspan="6" class="label">M=MARRIED, U=UNMARRIED, W=WIDOW/WIDOWER</td>
    
  </tr>
    <tr>
  
    <td nowrap="nowrap" colspan="6" class="label">RAJIV GANDHI BHAWAN, SAFDARJUNG AIRPORT, NEW DELHI-110003. PHONE 011-24632950, FAX 011-24610540.</td>
    
  </tr>
  
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>


</table>

  </body>
</html>
