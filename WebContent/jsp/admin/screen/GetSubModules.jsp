<%-- 

jsp:include page="/view/common/SessionCheck.jsp" flush="true"
This line creates the problem if you are going for ajax

--%> 
<%@ page language="java" import="java.sql.ResultSet,com.epis.utilities.*"%>

<%	
 	
	java.sql.Connection con=DBUtility.getConnection();
	java.sql.Statement stmt = con.createStatement();
    String strSql="";
	ResultSet rs=null;
		int i=1;
	
		
		String MCode=request.getParameter("MCode");
		strSql=" select SUBMODULECD,SUBMODNAME from epis_submodules where SUBMODULECD is not null and MODULECODE='"+MCode+"' ";
		StringBuffer  sb = new StringBuffer(); 
		//System.out.println("hi anil"+strSql);
			try
			{	
				rs = stmt.executeQuery(strSql);
				sb.append("<SUBMODULES>");
				
				if (rs!=null)
				{ 	
					sb.append("<MODULE>"+MCode+"</MODULE>");
					while(rs.next()){
					sb.append("<SMCD"+i+">"+rs.getString("SUBMODULECD")+"</SMCD"+i+">");  
					sb.append("<SMN"+i+">"+rs.getString("SUBMODNAME")+"</SMN"+i+">");  
					i++;
					}
					sb.append("<count>"+i+"</count>");
					sb.append("</SUBMODULES>");
					
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache"); 
					out.println(sb.toString()); 
					
				}	
			}catch(Exception e){
				System.out.println("ERROR IN GETTING SUBMODULE::"+e.getMessage());
				//log.error("GetSubModule:Exception:"+e.getMessage());
			}finally{
				DBUtility.closeConnection(rs,stmt,con);	
			}
	
	
%>
