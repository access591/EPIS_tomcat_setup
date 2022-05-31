
<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8"%>
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

	
	
	ResultSet rs=null; 
	ResultSet rs1=null;
	Statement stmt=null,stmt1=null;
	
	String trusttype="";
	String security="";
	String securityId="";
	double investAmt=0.0;
	
	trusttype=checkNull(request.getParameter("trustType"));
	security=checkNull(request.getParameter("securityCat"));
	
	StringBuffer sb=new StringBuffer();
    try{
		
		if(!trusttype.equals("")&&!security.equals(""))
		{
			
			Connection con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			stmt1=con.createStatement();
			String stSql1="SELECT CATEGORYID FROM INVEST_SEC_CATEGORY WHERE CATEGORYCD='"+security+"'";
			System.out.println("the second query is:"+stSql1);
			rs1=stmt.executeQuery(stSql1);
			if(rs1.next())
			{
				securityId=rs1.getString("CATEGORYID");
			}
			String strSql ="SELECT INV_AMOUNT  FROM investment_made  WHERE TRUSTCD='"+trusttype+"'and CATEGORYID='"+securityId+"'";
			System.out.println("the query is:"+strSql);
			rs = stmt1.executeQuery(strSql);
			
			if(rs.next())
			{
				System.out.println("this is callingdoeoroere");
 			    investAmt =rs.getDouble("INV_AMOUNT");
 			    System.out.println("this is enddfererercallingdoeoroere");
 			   		  
			}
			 System.out.println("Amt:"+investAmt);
			sb.append("<child>");
			sb.append("<Amount>"+investAmt+"</Amount>");	
			sb.append("</child>");
			
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");				
			response.getWriter().write("<master>"+sb.toString()+"</master>");	
		    }
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
