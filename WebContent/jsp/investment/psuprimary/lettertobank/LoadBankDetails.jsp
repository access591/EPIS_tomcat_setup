
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

	String accountNo="";
	
	accountNo =checkNull(request.getParameter("accountNo"));
	
	ResultSet rs=null; 
	ResultSet rs1=null;
	Statement stmt=null;
	
	String bankName="";
	String branchName="";
	String accountType="";
	String accountTypeDef="";
	
	
	
	StringBuffer sb=new StringBuffer();
    try{
		
		if(!accountNo.equals(""))
		{
			
			Connection con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			String strSql ="select nvl(bankname,'--')brankname,nvl(BRANCHNAME,'--')BRANCHNAME,decode(ACCOUNTTYPE,'S','SavingsAccount','C','Current Account')ACCOUNTTYPEdef,ACCOUNTTYPE from cb_bank_info where ACCOUNTNO='"+accountNo+"'";
			
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			    bankName =rs.getString("brankname");
			    branchName =rs.getString("BRANCHNAME");
			    accountType=rs.getString("ACCOUNTTYPE");
			    accountTypeDef=rs.getString("ACCOUNTTYPEdef");
			    
			}
			
	
			
			sb.append("<child>");
			sb.append("<BankName>"+bankName+"</BankName>");
			sb.append("<BranchName>"+branchName+"</BranchName>");
			sb.append("<AccountType>"+accountType+"</AccountType>");
			sb.append("<AccountTypDef>"+accountTypeDef+"</AccountTypDef>");
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
