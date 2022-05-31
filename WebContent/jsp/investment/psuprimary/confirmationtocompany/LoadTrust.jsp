
<%@ page language="java" import="java.sql.*,com.epis.utilities.PensionCaluculation" pageEncoding="UTF-8"%>
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

	String proposalRefNo="";
	String mode="";
	proposalRefNo =checkNull(request.getParameter("proposalRefNo"));
	proposalRefNo=proposalRefNo.replaceAll("~","%");
	proposalRefNo=proposalRefNo.replaceAll("ß","#");
	System.out.println("the proposalRefno is..."+proposalRefNo);
	mode=checkNull(request.getParameter("mode"));
	ResultSet rs=null; 
	Statement stmt=null;
	String trusttype="";
	String security="";
	String amount="";
	String marketDescription="";
	String marketType="";
	String noofbonds="";
	
	String cuponRate="";
	String offeredPrice="";
	String settlementDate="";
	String maturityDate="";
	String modeOfInterest="";
	String faceValue="";
	
	double years=0.0;
	String ytmValue="";
	
	cuponRate =checkNull(request.getParameter("cuponRate"));
	offeredPrice=checkNull(request.getParameter("offeredPrice"));
	faceValue=checkNull(request.getParameter("faceValue"));
	settlementDate = checkNull(request.getParameter("settlementDate"));
	maturityDate=checkNull(request.getParameter("maturityDate"));
	modeOfInterest=checkNull(request.getParameter("modeofinterest"));
	StringBuffer sb=new StringBuffer();
	Connection con=com.epis.utilities.DBUtility.getConnection();
	stmt=con.createStatement();
	if(mode.equals("confirm"))
	{
    try{
		
		if(!proposalRefNo.equals(""))
		{
			
			
			String strSql ="SELECT TRUSTTYPE,CATEGORYCD,AMT_INV,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,MARKET_TYPE,NO_OF_BONDS FROM  invest_formfilling WHERE PROPOSAL_REF_NO='"+proposalRefNo+"'";
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			    trusttype =rs.getString("TRUSTTYPE");
			    security =rs.getString("CATEGORYCD");
			    amount=rs.getString("AMT_INV");
			    marketDescription=rs.getString("MARKET_TYPE1");
			    marketType=rs.getString("MARKET_TYPE");
			    noofbonds=rs.getString("NO_OF_BONDS");
			}
			
	
			
			sb.append("<child>");
			sb.append("<TrustType>"+trusttype+"</TrustType>");
			sb.append("<securityCategory>"+security+"</securityCategory>");
			sb.append("<investamount>"+amount+"</investamount>");
			sb.append("<MarketType>"+marketType+"</MarketType>");
			sb.append("<MarketDesc>"+marketDescription+"</MarketDesc>");
			sb.append("<noofbonds>"+noofbonds+"</noofbonds>");
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
		}
		
		if(mode.equals("preperationconfirm"))
	{
		System.out.println("this is calling for preperationconfirmation");
    try{
		
		if(!proposalRefNo.equals(""))
		{
			
			
			String strSql ="SELECT PROPOSAL_REF_NO,TRUSTTYPE,CATEGORYCD,AMT_INV,security_name,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,MARKET_TYPE,NO_OF_BONDS,(amt_inv-nvl((select sum(NO_OF_BONDS*FACEVALUE) from invest_confirmation_company   where PROPOSAL_REF_NO || SECURITY_NAME ='"+proposalRefNo+"' ), 0))remaining,(SECURITY_NAME||'_'||(select nvl(max(to_number(substr(SURITY_FULLNAME,length(SECURITY_NAME)+2,length(SURITY_FULLNAME)-length(SECURITY_NAME))))+1,1) from invest_confirmation_company where proposal_ref_no||security_name='"+proposalRefNo+"'))securityfullname FROM  invest_formfilling WHERE proposal_ref_no||security_name='"+proposalRefNo+"'";
			rs = stmt.executeQuery(strSql);
				
			
			
	
			if(rs.next())
			{
			sb.append("<child>");
			sb.append("<ProposalRefNo>"+rs.getString("PROPOSAL_REF_NO")+"</ProposalRefNo>");
			sb.append("<TrustType>"+rs.getString("TRUSTTYPE")+"</TrustType>");
			sb.append("<securityCategory>"+rs.getString("CATEGORYCD")+"</securityCategory>");
			sb.append("<investamount>"+rs.getString("AMT_INV")+"</investamount>");
			sb.append("<MarketType>"+rs.getString("MARKET_TYPE")+"</MarketType>");
			sb.append("<MarketDesc>"+rs.getString("MARKET_TYPE1")+"</MarketDesc>");
			sb.append("<noofbonds>"+rs.getString("NO_OF_BONDS")+"</noofbonds>");
			sb.append("<securityName>"+rs.getString("security_name")+"</securityName>");
			sb.append("<remainingAmt>"+rs.getString("remaining")+"</remainingAmt>");
			sb.append("<securityFullName>"+rs.getString("securityfullname")+"</securityFullName>");
			sb.append("</child>");
			}
			
			
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
		}
	
		if(mode.equals("ytm"))
		{
		try{
			String strSql ="SELECT round(months_between(to_char(last_day(to_date('"+maturityDate+"','dd/MM/YYYY')),'dd/Mon/YYYY'),to_char(trunc(to_date('"+settlementDate+"','dd/MM/YYYY'),'MM'),'dd/Mon/YYYY'))/12,2)years FROM  dual";
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			   years=rs.getDouble("years");
			}
			double cupon=Double.parseDouble(cuponRate);
			double offerprice=Double.parseDouble(offeredPrice);
			double facevalue=Double.parseDouble(faceValue);
			if("Y".equals(modeOfInterest)){
				ytmValue=PensionCaluculation.getYTM(offerprice,cupon, facevalue, years);
			}else if("H".equals(modeOfInterest)){
				ytmValue=PensionCaluculation.getYTMAnnualized(Double.parseDouble(PensionCaluculation.getYTM(offerprice,cupon, facevalue, years)));
			}
			
			sb.append("<Data>")	;
			sb.append("<child>");
			sb.append("<YTM>"+ytmValue+"</YTM>");
			sb.append("</child>");
			sb.append("</Data>");
			response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");		
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
			
		}
		
		
	
%>
