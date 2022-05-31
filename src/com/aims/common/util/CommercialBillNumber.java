package com.aims.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommercialBillNumber {

	//java.sql.Statement st = null;

	public CommercialBillNumber() {
	}

	private String checkNull(String str) {
      if (str==null) 
		 return "";
	   else
		 return str.trim();
	}

	private List getChargeType(String type) {

		List chargeType = new ArrayList();

		

        if(type.equals("suppearnings")) {
			chargeType.add("A");
			chargeType.add("S");
			//chargeType.add("SR");
	    }

		if(type.equals("supploans")) {
			chargeType.add("A");
			chargeType.add("L");
			//chargeType.add("SR");
	    }

		if(type.equals("common")) {
			chargeType.add("A");
	    }
		return chargeType;
	}
	
	String prefixCd = "";
	String prefix = "";
	String suffix = "";
	String billnumber = "";
	String billType = "";
	boolean billTypeFlag = false;
	
	public String getBillType(List chargeType,String agencytype,java.sql.Connection con) throws java.sql.SQLException {

        String queryStr = "";

		if(chargeType.size()==2) {
			queryStr = "Select PREFIXCD,PREFIX,BILL_NUMBER,TYPE,SUFFIX from (Select PREFIXCD,PREFIX,nvl(BILL_NUMBER,0)BILL_NUMBER,TYPE,SUFFIX from BILLFORMAT where TYPE ='"+(String)chargeType.get(0)+"' and PREFIXCD IS NOT null and prefixcd=(Select max(PREFIXCD) from BILLFORMAT where TYPE ='"+(String)chargeType.get(0)+"') union Select PREFIXCD,PREFIX,BILL_NUMBER BILL_NUMBER,TYPE,SUFFIX from BILLFORMAT where TYPE = '"+(String)chargeType.get(1)+"' and PREFIXCD IS NOT  null and PREFIXCD=(Select max(PREFIXCD) from BILLFORMAT where TYPE ='"+(String)chargeType.get(1)+"') and status='L') order by TYPE";
		}else{
        	queryStr = " Select PREFIXCD,PREFIX,nvl(BILL_NUMBER,0) BILL_NUMBER,TYPE,SUFFIX from BILLFORMAT where TYPE ='"+(String)chargeType.get(0)+"' and PREFIXCD IS NOT null and prefixcd=(Select max(PREFIXCD) from BILLFORMAT where TYPE ='"+(String)chargeType.get(0)+"')";
		}
         
		java.sql.Statement st = con.createStatement();
		System.out.println("queryStr..."+queryStr);
		System.out.println("agencytype..."+agencytype);
		//String 		suffixCd="";
		ResultSet rsbillcd = st.executeQuery(queryStr);
			
		while(rsbillcd.next()) {	
			String agencytype1 = "";
			prefixCd = rsbillcd.getString("PREFIXCD");
			suffix = rsbillcd.getString("SUFFIX");
			if(!agencytype.equals(""))
				agencytype1="/"+agencytype;
			prefix = rsbillcd.getString("PREFIX")+agencytype1;
			//System.out.println("prefix "+prefix);
			billType = rsbillcd.getString("TYPE");
									
			if(billType.equals("A")) {
				billnumber = rsbillcd.getString("BILL_NUMBER");
			}

			if(!billType.equals("A")) {
				String tempNo = checkNull(rsbillcd.getString("BILL_NUMBER"));
				if(!tempNo.equals("")) {
					billTypeFlag = true;
					billnumber = rsbillcd.getString("BILL_NUMBER");
				}
			}
			
			if(billType.equals("S")) {
				billType = "S";
				break;
			}else if(billType.equals("L")) {
				billType = "L";
				break;
			}
			else{
				billType = "A";
			}
		}		
		if(rsbillcd != null)
			rsbillcd.close();
		if(st != null)
			st.close();

		return billType;
	}

	private String BillCodeGen(String prefix,String startbillno, String billType,String suffix) throws java.sql.SQLException {

		String sql="";
		if(prefix!=null && !prefix.equals("")) {
			 sql="SELECT '"+prefix+"/'||LPAD(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(BILL_NUMBER,LENGTH(TRIM(BILL_Number))-INSTR(REVERSE(TRIM(BILL_Number)),'/')+2))),"+startbillno+")),"+startbillno.length()+",'0')||'/"+suffix+"' CODE FROM BILLFORMAT WHERE PREFIX LIKE '"+(prefix==null?"":prefix)+"%' AND TYPE='"+billType+"' and STATUS='L'";
			 System.out.println("MMMMMMMMMsuffixx :"+sql);
		}else{
             sql="SELECT LPAD(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(BILL_NUMBER,LENGTH(TRIM(BILL_NUMBER))-INSTR(REVERSE(TRIM(BILL_Number)),'/')+2))),"+startbillno+")),"+startbillno.length()+",'0') CODE FROM BILLFORMAT WHERE PREFIX LIKE '"+(prefix==null?"":prefix)+"%' AND TYPE='"+billType+"' and STATUS='L'";
		     //System.out.println("NNNNNNNNNNNNNNN :"+sql);
		}
		return sql;
	}

	public String getBillNumber(String type,String agencytype,java.sql.Connection con) throws Exception {

		//validating bill number for Common For All or not
		//System.out.println("........"+validateBillNumber(con));
		if(validateBillNumber(con)) {		
				
			String query = "";
			String billcd = "";
			List chargeType = getChargeType(type);
			//System.out.println("chargeType :"+chargeType);

			String billType = getBillType(chargeType,agencytype,con);
			System.out.println("billType11 :"+billType);
			System.out.println("prefix22 :"+prefix);
			System.out.println("billnumber :"+billnumber);

			query=	BillCodeGen(prefix,billnumber,billType,suffix);
			System.out.println("BillCodeGen qry :"+query);
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			if(rs.next())
				billcd=rs.getString(1);

			if(rs != null)
				rs.close();
			if(st != null)
				st.close();

			//updateBillNumber(st);
			//System.out.println("bill number :"+billcd);

			return billcd;
		}else{
			return "NOTFOUND";
		}
	}

	public void updateBillNumber(java.sql.Connection con) throws java.sql.SQLException {
		
		String query = "";
		int billNo = Integer.parseInt(billnumber)+1; 
		
		if(String.valueOf(billNo).length()==billnumber.length()||String.valueOf(billNo).length()>billnumber.length()){
			query = "Update BILLFORMAT set BIll_Number='"+billNo+"' where  TYPE=decode('"+billTypeFlag+"','true','"+billType+"','A') and PrefixCd = (Select  max(PrefixCd) from BILLFORMAT where Type=decode('"+billTypeFlag+"','true','"+billType+"','A') )";
		}else{
			query = "Update BILLFORMAT set BIll_Number=Lpad(to_number('"+billnumber+"')+1,"+billnumber.length()+",0) where  TYPE=decode('"+billTypeFlag+"','true','"+billType+"','A') and PrefixCd = (Select  max(PrefixCd) from BILLFORMAT where Type=decode('"+billTypeFlag+"','true','"+billType+"','A')) ";
		}
		System.out.println("update query hlll :"+query);
		java.sql.Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
		billTypeFlag = false;
	}
	
	public boolean validateBillNumber(Connection con)throws Exception {
		ResultSet rs   = null;
		boolean flag = false;
		Statement stmt = con.createStatement();
        rs = stmt.executeQuery("Select *  from BILLFORMAT where Type = 'A' and BILL_NUMBER is not null");    
        if(!rs.next()) { 		
			flag = false;
		}else{
			flag = true;
		}
		if(stmt != null)
			stmt.close();
		if(rs != null)
			rs.close();

		return flag;
	}	

	public synchronized String getCMBillNumber(String type,java.sql.Connection con) throws Exception {
		String billCd = getBillNumber(type,"",con);  
		//System.out.println("bill number :"+billCd);
		if(billCd.equals("NOTFOUND")) {
  			throw new Exception("Bill number is not defined for 'Common For All Bills' in Bill Number Format Screen.Please Define <A href='/view/cm/BillFormatNew.jsp'><Font color='blue' size='2'>Bill Number Format</Font></A>");
        }else{
			updateBillNumber(con);
            return billCd;
		}
	}

	public synchronized String getCMBillNumber(String type,String agencytype,java.sql.Connection con) throws Exception {
		String billCd = getBillNumber(type,checkNull(agencytype),con);
		
		//System.out.println("bill number :"+billCd);

		if(billCd.equals("NOTFOUND")) {
  			throw new Exception("Bill number is not defined for 'Common For All Bills' in Bill Number Format Screen.Please Define <A href='/view/cm/BillFormatNew.jsp'><Font color='blue' size='2'>Bill Number Format</Font></A>");
        }else{
			updateBillNumber(con);
            return billCd;
		}
	}

	/*public static void main(String[] args) {
		CommercialBillNumber bill = new CommercialBillNumber();
		DBAccess db = new DBAccess();
		try {
		db.makeConnection("VOMM");
		java.sql.Connection con = db.getConnection();
		String billNo = bill.getCMBillNumber("facility",con);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}*/
}
