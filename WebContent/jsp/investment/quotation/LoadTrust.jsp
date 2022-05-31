
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

	String proposalRefNo="";
	String mode="";
	String modeType="";
	proposalRefNo =checkNull(request.getParameter("proposalRefNo"));
	proposalRefNo=proposalRefNo.replaceAll("~","%");
	proposalRefNo=proposalRefNo.replaceAll("ß","#");
	mode=checkNull(request.getParameter("mode"));
	modeType=checkNull(request.getParameter("modetype"));
	ResultSet rs=null; 
	ResultSet rs1=null;
	Statement stmt=null;
	Connection con=null;
	
	String trusttype="";
	String security="";
	String securityId="";
	String securityName="";
	String amount="";
	String marketDescription="";
	String marketType="";
	String dealdate="";
	String settlementDate="";
	String faceValue="";
	String offeredPrice="";
	String principalamount="";
	String rbiRefNo="";
	String securityFullName="";
	
	StringBuffer sb=new StringBuffer();
    try{
		String strSql=null;
		if(!proposalRefNo.equals("")&& mode.equals(""))
		{
			
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			strSql ="SELECT proposal.TRUSTCD,TRUSTTYPE,CATEGORYCD,AMT_INV,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,MARKET_TYPE FROM INVEST_PROPOSAL proposal,INVEST_TRUSTTYPE trust WHERE REF_NO='"+proposalRefNo+"'and proposal.TRUSTCD=trust.TRUSTCD";
			System.out.println("the query is:"+strSql);
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			    trusttype =rs.getString("TRUSTTYPE");
			    security =rs.getString("CATEGORYCD");
			    amount=rs.getString("AMT_INV");
			    marketDescription=rs.getString("MARKET_TYPE1");
			    marketType=rs.getString("MARKET_TYPE");
			}
			String stSql="SELECT CATEGORYID FROM INVEST_SEC_CATEGORY WHERE CATEGORYCD='"+security+"'";
			System.out.println("the second query is:"+stSql);
			rs1=stmt.executeQuery(stSql);
			if(rs1.next())
			{
				securityId=rs1.getString("CATEGORYID");
			}
	
			
			sb.append("<child>");
			sb.append("<TrustType>"+trusttype+"</TrustType>");
			sb.append("<securityCategory>"+security+"</securityCategory>");
			sb.append("<investamount>"+amount+"</investamount>");
			sb.append("<securityId>"+securityId+"</securityId>");
			sb.append("<MarketType>"+marketType+"</MarketType>");
			sb.append("<MarketDesc>"+marketDescription+"</MarketDesc>");
			sb.append("</child>");
			
				
		    }
		    if(!proposalRefNo.equals("")&& mode.equals("lettertobank"))
		   {
		   	con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			if(modeType.equals("psuprimary"))
			strSql="select TRUSTTYPE,CATEGORYCD,AMT_INV,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,MARKET_TYPE from invest_formfilling where PROPOSAL_REF_NO='"+proposalRefNo+"'";
			else if(modeType.equals("rbiauction"))
			strSql ="SELECT PROPOSAL_REF_NO,SURITY_FULLNAME,company.TRUSTTYPE,company.CATEGORYCD,company.AMT_INV,decode(company.MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,company.MARKET_TYPE,replace(company.SECURITY_NAME,'&','and') securityname,to_char(DEALDATE,'dd/Mon/yyyy')DEALDATE,to_char(SETTLEMENT_DATE,'dd/Mon/yyyy')SETTLEMENT_DATE,(FACEVALUE*company.NO_OF_BONDS)FACEVALUE,OFFERED_PRICE,(OFFERED_PRICE*company.NO_OF_BONDS)principalamount FROM invest_confirmation_company company WHERE company.PROPOSAL_REF_NO||company.SECURITY_NAME||company.SURITY_FULLNAME='"+proposalRefNo+"'";
			
			System.out.println("the query is:"+strSql);
			rs = stmt.executeQuery(strSql);
				
			if(rs.next())
			{
 			    trusttype =rs.getString("TRUSTTYPE");
			    security =rs.getString("CATEGORYCD");
			    amount=rs.getString("AMT_INV");
			    marketDescription=rs.getString("MARKET_TYPE1");
			    marketType=rs.getString("MARKET_TYPE");
			    if(modeType.equals("rbiauction"))
			    {
			    securityName=rs.getString("securityname");
			    dealdate=rs.getString("DEALDATE");
			    settlementDate=rs.getString("SETTLEMENT_DATE");
			    faceValue=rs.getString("FACEVALUE");
			    offeredPrice=rs.getString("OFFERED_PRICE");
			    principalamount=rs.getString("PRINCIPALAMOUNT");
			    rbiRefNo=rs.getString("PROPOSAL_REF_NO");
			    securityFullName=rs.getString("SURITY_FULLNAME");
			    
			    }
			}
			
	
			
			sb.append("<child>");
			sb.append("<TrustType>"+trusttype+"</TrustType>");
			sb.append("<securityCategory>"+security+"</securityCategory>");
			sb.append("<investamount>"+amount+"</investamount>");
			sb.append("<securityName>"+securityName+"</securityName>");
			sb.append("<MarketType>"+marketType+"</MarketType>");
			sb.append("<MarketDesc>"+marketDescription+"</MarketDesc>");
			sb.append("<DealDate>"+dealdate+"</DealDate>");
			sb.append("<SettlementDate>"+settlementDate+"</SettlementDate>");
			sb.append("<FaceValue>"+faceValue+"</FaceValue>");
			sb.append("<offeredPrice>"+offeredPrice+"</offeredPrice>");
			sb.append("<PrincipalAmount>"+principalamount+"</PrincipalAmount>");
			sb.append("<RefNo>"+rbiRefNo+"</RefNo>");
			sb.append("<securityFullName>"+securityFullName+"</securityFullName>");
			sb.append("</child>");
			
			
			
	
		   }
		   
		   if(!proposalRefNo.equals("")&& mode.equals("RBItrust"))
			{
			
			con=com.epis.utilities.DBUtility.getConnection();
			stmt=con.createStatement();
			strSql ="SELECT proposal.TRUSTCD,TRUSTTYPE,category.categoryid categoryid,proposal.CATEGORYCD CATEGORYCD,AMT_INV-nvl((select sum(nvl(AMT_INV,0)) from invest_formfilling  where PROPOSAL_REF_NO='"+proposalRefNo+"'),0) AMT_INV,decode(MARKET_TYPE,'P','Primary','S','Secondary','R','RBI','RB','RBI OIL BONDS','O','Open Bid','PS','Primary And Secondary')MARKET_TYPE1,MARKET_TYPE,NVL(PROPOSALDT.SECURITY_NAME,'--')SECURITY_NAME FROM INVEST_PROPOSAL proposal,INVEST_PROPOSAL_DT PROPOSALDT, INVEST_TRUSTTYPE trust,INVEST_SEC_CATEGORY category WHERE PROPOSAL.REF_NO='"+proposalRefNo+"'and proposal.TRUSTCD=trust.TRUSTCD and proposal.categorycd = category.categorycd AND PROPOSAL.REF_NO=PROPOSALDT.REF_NO AND PROPOSALDT.SECURITY_NAME NOT IN(SELECT SECURITY_NAME FROM INVEST_FORMFILLING WHERE PROPOSAL_REF_NO='"+proposalRefNo+"')";
			
			rs = stmt.executeQuery(strSql);
				
			while(rs.next())
			{
				sb.append("<child>");
				sb.append("<TrustType>"+rs.getString("TRUSTTYPE")+"</TrustType>");
				sb.append("<securityCategory>"+rs.getString("CATEGORYCD")+"</securityCategory>");
				sb.append("<securityName>"+rs.getString("SECURITY_NAME")+"</securityName>");
				sb.append("<investamount>"+rs.getString("AMT_INV")+"</investamount>");
				sb.append("<securityId>"+rs.getString("categoryid")+"</securityId>");
				sb.append("<MarketType>"+rs.getString("MARKET_TYPE")+"</MarketType>");
				sb.append("<MarketDesc>"+rs.getString("MARKET_TYPE1")+"</MarketDesc>");
				sb.append("</child>");
			    
			}
			
	
			
			
			
				
		    }
	
		   
		   
		   	response.setContentType("text/xml");
			response.setHeader("Cache-Control","no-cache");				
			response.getWriter().write("<master>"+sb.toString()+"</master>");
		    
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
