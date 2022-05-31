<%@ page language="java" import="java.sql.*,java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="dv" class="com.epis.utilities.DateValidation" scope="page"/>
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
    
	
	String letterno="";
	String refNo="";
	String maturityDate="";
	String quotationDate="";
	String reqquotationDate="";
	String tenure="";
	String cbName="";
	String invName="";
	String groupName="";
	String arrangercd=checkNull(request.getParameter("arrangerCd"));
	letterno=checkNull(request.getParameter("letterNo"));
	maturityDate=checkNull(request.getParameter("maturitydate"));
	reqquotationDate=checkNull(request.getParameter("quotationdate"));
	cbName=checkNull(request.getParameter("cbName"));
	invName=checkNull(request.getParameter("invName"));
	groupName=checkNull(request.getParameter("groupName"));
	cbName=cbName.replaceAll("~","%");
	cbName=cbName.replaceAll("ß","#");
	cbName=cbName.replaceAll("ampersand","&");
	
	
	invName=invName.replaceAll("~","%");
	invName=invName.replaceAll("ß","#");
	invName=invName.replaceAll("ampersand","&");
	
	groupName=groupName.replaceAll("~","%");
	groupName=groupName.replaceAll("ß","#");
	
	
	

	
	refNo=checkNull(request.getParameter("refNo"));
	
    ResultSet rs=null; 
	String strSql="";
	Connection con=null;
	Statement stmt=null;
	String strSqlmt=null;
	
	StringBuffer sb=new StringBuffer();
	
	
		if (request.getParameter("mode").equals("Arrangers")){
    	try
		{
		if(!refNo.equals(""))
		{
			
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			
			

			
			//strSql="select ARRANGERS from INVEST_QUOTATIONREQUEST_DT WHERE LETTER_NO='"+letterno+"'";
			
		strSql ="select TRUSTTYPE, mt.CATEGORYID,CATEGORYCD, ARRANGERCD,(select replace(arrangername,'&','and')  from invest_arrangers where arrangercd=dt.arrangercd) ArrangerName, dt.letter_no letter_no  FROM INVEST_QUOTATIONREQUEST mt    , INVEST_QUOTATIONREQUEST_DT dt,invest_sec_category sc WHERE mt.CATEGORYID=sc.CATEGORYID and mt.LETTER_NO = dt.letter_no and dt.letter_no=(select letter_no from invest_quotationrequest where PROPOSAL_REF_NO='"+refNo+"')";
				

			rs = stmt.executeQuery(strSql);
			sb.append("<QuotationData>");	
		
			if(rs!=null)
			{
				
					while(rs.next())
					{
						sb.append("<Child>");	
						sb.append("<Arrangers>"+checkNull(rs.getString("ARRANGERCD"))+"</Arrangers>");
						sb.append("<ArrangersName>"+checkNull(rs.getString("ArrangerName"))+"</ArrangersName>");
						sb.append("<TrustType>"+checkNull(rs.getString("TRUSTTYPE"))+"</TrustType>");
						sb.append("<SecurityCategory>"+checkNull(rs.getString("CATEGORYID"))+"</SecurityCategory>");
						sb.append("<SecurityName>"+checkNull(rs.getString("CATEGORYCD"))+"</SecurityName>");
						sb.append("<LetterNo>"+checkNull(rs.getString("letter_no"))+"</LetterNo>");
						sb.append("</Child>");	
					}
			}
			
			
			
			
			
			sb.append("</QuotationData>");
			 
			
			
			
			                
	
				
		}
		}
		catch(Exception e)
		{
			System.out.println("Exception in Station Level"+e);

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
		
		if(request.getParameter("mode").equals("jvfinyear"))
		{
			String preperationdate=request.getParameter("preperationdate");
			sb.append("<child>");
			sb.append("<FinYear>"+dv.getJVLetterYear(preperationdate)+"</FinYear>");
			sb.append("</child>");
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");				
			response.getWriter().write("<master>"+sb.toString()+"</master>");
			
		}
		
		if (request.getParameter("mode").equals("ArrangerName")){
		  
		String arrangerName="";
		try{
			
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			strSqlmt="SELECT ARRANGERNAME FROM INVEST_ARRANGERS WHERE ARRANGERCD='"+arrangercd+"'";
			rs = stmt.executeQuery(strSqlmt);
			
			if(rs.next())
			{
				arrangerName=rs.getString("ARRANGERNAME");
			}
			
			sb.append("<child>");
			sb.append("<ArrangerName>"+arrangerName+"</ArrangerName>");
			sb.append("</child>");
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");				
			response.getWriter().write("<master>"+sb.toString()+"</master>");
			
		}
		catch(Exception e)
		{
		
			sb.append("<master>");
			sb.append("<Exception>"+e.toString()+"</Exception>");
			sb.append("</master>");
		}
		finally{
		com.epis.utilities.DBUtility.closeConnection(rs,stmt,con);
		}
		}
		if(request.getParameter("mode").equals("Tenure"))
		{
			int fromDate=0;
			int fromMonth=0;
			int fromYear=0;
			int toDate=0;
			int toMonth=0;
			int toYear=0;
			if(!letterno.equals("")&& !maturityDate.equals(""))
			{
				
				try{
					con=com.epis.utilities.DBUtility.getConnection();
					stmt=con.createStatement();
					strSqlmt="select to_char(quotation_date,'dd/mm/yyyy') quotationdate from invest_quotationrequest WHERE LETTER_NO='"+letterno+"'";
					rs = stmt.executeQuery(strSqlmt);
					
					if(rs.next())
					{
						quotationDate=rs.getString("quotationdate");
					}
					
					
					StringTokenizer st1=new StringTokenizer(quotationDate,"/");
					while(st1.hasMoreTokens())
					{
						fromDate=Integer.parseInt(st1.nextToken());
						fromMonth=Integer.parseInt(st1.nextToken());
						fromYear=Integer.parseInt(st1.nextToken());
						
					}
					
					maturityDate=dv.convertMonth(maturityDate);
					
					StringTokenizer st2=new StringTokenizer(maturityDate,"/");
					while(st2.hasMoreTokens())
					{
						toDate=Integer.parseInt(st2.nextToken());
						toMonth=Integer.parseInt(st2.nextToken());
						toYear=Integer.parseInt(st2.nextToken());
					}
					
					 
					tenure=dv.getTenure(fromYear,fromMonth,fromDate,toYear,toMonth,toDate);
					
					sb.append("<child>");
					sb.append("<Tenure>"+tenure+"</Tenure>");
					sb.append("</child>");
					
					response.setContentType("text/xml");
					response.setHeader("Cache-Control","no-cache");				
					response.getWriter().write("<master>"+sb.toString()+"</master>");
				}
				catch(Exception e)
				{
					sb.append("<master>");
					sb.append("<Exception>"+e.toString()+"</Exception>");
					sb.append("</master>");
				}
				finally{
				}
			}
		}
		
		if(request.getParameter("mode").equals("qTenure"))
		{
			int fromDate=0;
			int fromMonth=0;
			int fromYear=0;
			int toDate=0;
			int toMonth=0;
			int toYear=0;
			if(!reqquotationDate.equals("")&& !maturityDate.equals(""))
			{
				
				try{
					
					
					reqquotationDate=dv.convertMonth(reqquotationDate);
					
					StringTokenizer st1=new StringTokenizer(reqquotationDate,"/");
					while(st1.hasMoreTokens())
					{
						fromDate=Integer.parseInt(st1.nextToken());
						fromMonth=Integer.parseInt(st1.nextToken());
						fromYear=Integer.parseInt(st1.nextToken());
						
					}
					
					maturityDate=dv.convertMonth(maturityDate);
					
					StringTokenizer st2=new StringTokenizer(maturityDate,"/");
					while(st2.hasMoreTokens())
					{
						toDate=Integer.parseInt(st2.nextToken());
						toMonth=Integer.parseInt(st2.nextToken());
						toYear=Integer.parseInt(st2.nextToken());
					}
					
					 
					tenure=dv.getTenure(fromYear,fromMonth,fromDate,toYear,toMonth,toDate);
					
					sb.append("<child>");
					sb.append("<Tenure>"+tenure+"</Tenure>");
					sb.append("</child>");
					response.setContentType("text/xml");
					response.setHeader("Cache-Control","no-cache");				
					response.getWriter().write("<master>"+sb.toString()+"</master>");
				}
				catch(Exception e)
				{
					sb.append("<master>");
					sb.append("<Exception>"+e.toString()+"</Exception>");
					sb.append("</master>");
				}
				finally{
				}
			}
		}
		
		if(request.getParameter("mode").equals("cbtoinv"))
		{
			
			try{
				String cbtoinvUpdateQry="update cb_party_info set invest_cash_partyname='"+invName+"',module_type='I',MAPPING_FLAG='Y' where Trim(upper(partyname))='"+cbName.toUpperCase().trim()+"'";
				con=com.epis.utilities.DBUtility.getConnection();
				stmt=con.createStatement();
				stmt.executeUpdate(cbtoinvUpdateQry);
				
				
			}
			catch(Exception e)
			{
					sb.append("<master>");
					sb.append("<Exception>"+e.toString()+"</Exception>");
					sb.append("</master>");
			}
			finally{
			stmt.close();
			con.close();
			}
		
		}
		
		
		
		if(request.getParameter("mode").equals("cbtoinvgroup"))
		{
			
			try{
				String cbUpdateQry="update cb_party_info set GROUPING_FLAG='Y',GROUP_NAME='"+groupName+"' where Trim(upper(partyname))in("+cbName.toUpperCase().trim()+")";
				String invUpdateQry="update cb_party_info set GROUPING_FLAG='Y',GROUP_NAME='"+groupName+"' where Trim(upper(partyname))in("+invName.toUpperCase().trim()+")";
				con=com.epis.utilities.DBUtility.getConnection();
				stmt=con.createStatement();
				stmt.executeUpdate(cbUpdateQry);
				stmt.executeUpdate(invUpdateQry);
				
				
			}
			catch(Exception e)
			{
					sb.append("<master>");
					sb.append("<Exception>"+e.toString()+"</Exception>");
					sb.append("</master>");
			}
			finally{
			stmt.close();
			con.close();
			}
		
		}
		
		
		
		if (request.getParameter("mode").equals("cbtoinvrefresh")){
		
    	try
		{
		
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			
			

			
			//strSql="select ARRANGERS from INVEST_QUOTATIONREQUEST_DT WHERE LETTER_NO='"+letterno+"'";
			
			strSql ="select replace(PARTYNAME,'&','ampersand')partydes,replace(PARTYNAME,'&','ampersand')party,module_type  from cb_party_info where MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type='C' and partyname   in(select emp_party_code from cb_voucher_info info where partytype='P')  union select replace(PARTYNAME,'&','ampersand')||'--'|| nvl(register.facevalue_inrs,0) partydes,replace(PARTYNAME,'&','ampersand') party,module_type  from cb_party_info info,investment_register register where trim(upper(info.partyname)) = trim(upper(register.SECURITY_PROPOSAL)) and  MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type='I' and partyname  not in(select emp_party_code from cb_voucher_info info where partytype='P') ";
				
			
			rs = stmt.executeQuery(strSql);
			sb.append("<PartyData>");	
		
			if(rs!=null)
			{
				
					while(rs.next())
					{
						sb.append("<Child>");	
						sb.append("<PartyName>"+checkNull(rs.getString("party"))+"</PartyName>");
						sb.append("<PartyNameDesc>"+checkNull(rs.getString("partydes"))+"</PartyNameDesc>");
						sb.append("<ModuleType>"+checkNull(rs.getString(3))+"</ModuleType>");
						sb.append("</Child>");	
					}
			}
			
			
			
			
			
			sb.append("</PartyData>");
			 
			
			
			
			                
	
				
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in Station Level"+e);

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
		
		
		if (request.getParameter("mode").equals("cbtoinvgrouprefresh")){
		
    	try
		{
		
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			
			

			
			//strSql="select ARRANGERS from INVEST_QUOTATIONREQUEST_DT WHERE LETTER_NO='"+letterno+"'";
			
			strSql ="select replace(PARTYNAME,'&','ampersand')partydes,replace(PARTYNAME,'&','ampersand')party,module_type  from cb_party_info where MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type='C' and partyname   in(select emp_party_code from cb_voucher_info info where partytype='P')  union select replace(PARTYNAME,'&','ampersand')||'--'|| nvl(register.facevalue_inrs,0) partydes,replace(PARTYNAME,'&','ampersand') party,module_type  from cb_party_info info,investment_register register where trim(upper(info.partyname)) = trim(upper(register.SECURITY_PROPOSAL)) and  MAPPING_FLAG='N' and GROUPING_FLAG='N' and module_type='I' and partyname  not in(select emp_party_code from cb_voucher_info info where partytype='P') ";
				
			
			rs = stmt.executeQuery(strSql);
			sb.append("<PartyData>");	
		
			if(rs!=null)
			{
				
					while(rs.next())
					{
						sb.append("<Child>");	
						sb.append("<PartyName>"+checkNull(rs.getString("party"))+"</PartyName>");
						sb.append("<PartyNameDesc>"+checkNull(rs.getString("partydes"))+"</PartyNameDesc>");
						sb.append("<ModuleType>"+checkNull(rs.getString(3))+"</ModuleType>");
						sb.append("</Child>");	
					}
			}
			
			
			
			
			
			sb.append("</PartyData>");
			 
			
			
			
			                
	
				
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in Station Level"+e);

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
		//System.out.println("the values.."+sb.toString()); 	
		out.println(sb.toString());	 
		
		}
		
		
				 
%>