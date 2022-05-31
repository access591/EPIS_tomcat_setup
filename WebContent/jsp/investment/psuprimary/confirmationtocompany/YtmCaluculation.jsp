
<%@ page language="java" import="java.sql.*,com.epis.utilities.PensionCaluculation" pageEncoding="UTF-8"%>
<jsp:useBean id="dv" class="com.epis.utilities.DateValidation" scope="request" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%!
public String checkNull(String str)
{
	if(str==null)
	return "";
	else
	return str.trim();
}	
%>

<%

	String cuponRate="";
	String offeredPrice="";
	String settlementDate="";
	String maturityDate="";
	
	cuponRate =checkNull(request.getParameter("cuponRate"));
	offeredPrice=checkNull(request.getParameter("offeredPrice"));
	
	settlementDate = checkNull(request.getParameter("settlementDate"));
	maturityDate=checkNull(request.getParameter("maturityDate"));
	
	
	ResultSet rs=null; 
	Statement stmt=null;
	int years=0;
	String ytmValue="";
	
	StringBuffer sb=new StringBuffer();
    try{
		
		
			
			Connection con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			String strSql ="SELECT floor(months_between(to_char(to_date('"+maturityDate+"','dd/MM/YYYY'),'dd/Mon/YYYY'),to_char(to_date('"+settlementDate+"','dd/MM/YYYY'),'dd/Mon/YYYY'))/12)years FROM  dual";
			System.out.println("the query is:"+strSql);
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			   years=rs.getInt("years");
			}
			double cupon=Double.parseDouble(cuponRate);
			double offerprice=Double.parseDouble(offeredPrice);
			
				ytmValue=PensionCaluculation.getYTM(offerprice,cupon, 100, years);
			
			sb.append("<Data>")	;
			sb.append("<child>");
			sb.append("<YTM>"+ytmValue+"</YTM>");
			sb.append("</child>");
			sb.append("</Data>");
			response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");		
			System.out.println(sb.toString());		
			response.getWriter().write(sb.toString());	
			
		    }
	        
			catch(Exception e)
		    {
			sb.append("<master>");
            sb.append("<Exception>"+e.toString()+"</Exception>");
			sb.append("</master>");
			out.println(sb.toString());
		   }
		finally
		{
			if(rs != null )
			  rs.close();
			
		}
		
	
%>
