<%@ page language="java" import="com.epis.utilities.StringUtility,java.sql.*" pageEncoding="UTF-8"%>
<%@ page import="com.epis.utilities.Configurations" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%	
    ResultSet rs=null; 
	String strSql="";
	Connection con=null;
	Statement stmt=null;
	
	
	StringBuffer sb=new StringBuffer();
		if (request.getParameter("mode").equals("M")){
	    	try
			{
			String userid=request.getParameter("UserId");
			if(!"".equals(userid))
			{
				con=com.epis.utilities.DBUtility.getConnection();
				stmt=con.createStatement();
				if("USER".equals(Configurations.getAccessRightsType())){
					strSql ="select code,replace(name,'&','and') name from epis_modules m where instr( (select modules from epis_user where username='"+userid+"'),code,1,1)>0";
				}else if("ROLE".equals(Configurations.getAccessRightsType())){
					strSql ="select code,replace(name,'&','and') name from epis_modules m where instr( (select modules from epis_role where rolecd='"+userid+"'),code,1,1)>0";
				}
				rs = stmt.executeQuery(strSql);
				sb.append("<Modules>");	
				if(rs!=null)
				{		String almodules="";
						while(rs.next())
						{
							almodules+=StringUtility.checknull(rs.getString("CODE"))+",";
							sb.append("<Module>");	
							sb.append("<code>"+StringUtility.checknull(rs.getString("CODE"))+"</code>");
							sb.append("<name>"+StringUtility.checknull(rs.getString("NAME"))+"</name>");
							sb.append("</Module>");	
						}
						if(almodules.length()>3){
							sb.append("<Module>");	
							sb.append("<code>"+almodules.substring(0,(almodules.length()-1))+"</code>");
							sb.append("<name>All</name>");
							sb.append("</Module>");
						}
				}
				
				sb.append("</Modules>");
			}
			}
			catch(Exception e)
			{
				sb.append("<Data>");
				sb.append("<Exception>"+e.toString()+"</Exception>");
				sb.append("</Data>");
			}
			finally
			{	
				com.epis.utilities.DBUtility.closeConnection(rs,stmt,con);
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache"); 	
			out.println(sb.toString());	 
			
			}
		
				 
%>