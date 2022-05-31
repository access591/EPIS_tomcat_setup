
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'RegionSearch.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<link href="<%=basePath%>css/epis.css" rel="stylesheet" type="text/css" />
		
		<link href="<%=basePath%>css/buttons.css" rel="stylesheet" type="text/css" />
	</head>
	<body  >
	<table width="70%" border=0 align="center" ><form action="#" >		<tbody >
			
             <tr>
             <td valign=bottom align=center height=100>
                  <table border=0>
                      <tr>
                      <td align=center class="aims"><b>
                        AIRPORTS AUTHORITY OF INDIA
                     </b> </td>
                      </tr>
                   </table>
              </td>
              </tr>
              <Tr><td>&nbsp;</td></tr>
              <tr>
             <td valign=middle align=center  cellpadding=0 cellspacing=0>
                  <table width=700 border=0>
                      <tr>
                      <td align=left class="epis">
                        Rate For__________________
                      </td>
                      <td align=right class="epis">
                        Date of Opening:______________
                      </td>
                      </tr>
                   </table>
              </td>
              </tr>    <Tr><td>&nbsp;</td></tr>
                        <tr>
                        <td valign=top>
                         <table width=100% border=1  cellpadding=2 cellspacing=0 >
                         <tr>          
                                    <td  class="epis" rowspan=2> Sr. No.</td> 
                                   <td  class="epis" rowspan=2>Security Name</td>  
                                   <td  class="epis"> Arranger 1 </td>  
                                   <td  class="epis">Arranger 2 </td>
                                   <td  class="epis"> Arranger 3</td>
                                  <td  class="epis">Arranger 4</td>
                                  <td  class="epis"> Arranger 5</td>
                                  <td  class="epis"> Arranger 6</td>
                                  <td  class="epis"> Arranger 7</td>
                                  <td class="epis"> Arranger 8</td>
                                  <td  class="epis" rowspan=2>Remarks</td>
                               </tr>
                          
                                      <tr>
                                   
                                   <td class="epis">rates(Rs.)/YTM%</td> 
                                   <td class="epis">rates(Rs.)/YTM%</td> 
                                 <td class="epis">rates(Rs.)/YTM%</td> 
                                   <td class="epis">rates(Rs.)/YTM%</td> 
                                  <td class="epis">rates(Rs.)/YTM%</td> 
                                  <td class="epis">rates(Rs.)/YTM%</td> 
                                  <td class="epis">rates(Rs.)/YTM%</td> 
                                 <td class="epis">rates(Rs.)/YTM%</td> 
                                   
                               
                               </tr>
                                <tr>
                                    <td class="epis">1</td> 
                                   <td class="epis" nowrap>Security 1</td>  
                                   <td class="epis">109.94/9.35</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td> 
                               </tr>
                                <tr>
                                     <td class="epis">2</td> 
                                   <td class="epis" nowrap>Security 2</td>  
                                   <td class="epis">96.75/9.10</td> 
                                   <td class="epis">96.88/9.10</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               
                               <tr>
                                     <td class="epis">3</td> 
                                   <td class="epis">Security 3</td>  
                                   <td class="epis">&nbsp;</td> 
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">96.75/9.10</td>  
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">96.75/9.10</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               <tr>
                                     <td class="epis">4</td> 
                                   <td class="epis">Security 4</td>  
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                 <td class="epis">96.75/9.10</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               <tr>
                                     <td class="epis">5</td> 
                                   <td class="epis">Security 5</td>  
                                    <td class="epis">&nbsp;</td> 
                                    <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                    <td class="epis">96.75/9.10</td> 
                                    <td class="epis">&nbsp;</td> 
                                   <td class="epis">96.75/9.10</td> 
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               <tr>
                                     <td class="epis">6</td> 
                                   <td class="epis">Security 6</td>  
                                   <td class="epis">&nbsp;</td> 
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">96.75/9.10</td> 
                                   <td class="epis">&nbsp;</td>  
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               <tr>
                                     <td class="epis">7</td> 
                                   <td class="epis">Security 7</td>  
                                   <td class="epis">&nbsp;</td> 
                                  <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">&nbsp;</td> 
                                   <td class="epis">96.75/9.10</td> 
                                   <td class="epis">96.75/9.10</td> 
                                   <td class="epis">&nbsp;</td>  
                               </tr>
                               
                              </table>
                          </td>   
                        </tr>
                        <tr><td colspan=3 align=center></td></tr>
                        
                 </td>       
              </tr>
       	
			</tbody></form>
	</table>
	
	</body>
</html>
