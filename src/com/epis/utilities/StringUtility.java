package com.epis.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.epis.bean.admin.Bean;

public class StringUtility {

	public static String checknull(String string) {
		if (string == null)
			string = "";
		return string.trim();
	}
	
	public static String checknull(String string,String append) {
		if (string == null)
			string = "" + append.trim();
		return string.trim() + append.trim();
	}

	public static String checkFlag(String string) {
		if (!"Y".equals(string)) {
			string = "N";
		}
		return string;
	}

	public static String arrayToString(String[] stringArray, String appender) {
		StringBuffer buffer = new StringBuffer();
		int size = stringArray.length;
		for (int arrCnt = 0; arrCnt < size; arrCnt++) {
			buffer.append(stringArray[arrCnt]).append(appender);
		}
		return size > 0 ? buffer.toString().substring(0, buffer.length() - 1)
				: "";
	}

	public static StringBuffer replace(char[] string, char[] replace,
			String charletter) {
		StringBuffer sb = new StringBuffer();
		boolean found;

		for (int i = 0; i < string.length; i++) {

			if (string[i] == replace[0]) {
				found = true;

				for (int j = 0; j < replace.length; j++) {
					if (!(string[i + j] == replace[j])) {
						found = false;
						break;
					}
				}

				if (found) {
					sb.append(charletter);
					i = i + (replace.length - 1);
					continue;
				}
			}
			sb.append(string[i]);
		}

		return sb;
	}

	public static StringBuffer replaces(char[] string, char[] replace,
			String charletter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length; i++) {

			for (int k = 0; k < replace.length; k++) {
				if (string[i] == replace[k]) {
					sb.append(charletter);
					if (i != (string.length - 1)) {
						i++;
					}
				}
			}
			sb.append(string[i]);
		}
		return sb;
	}
	
	public static String[] getFinYears(String fromYear,String toYear){
		int from = Integer.parseInt(fromYear);
		int to = Integer.parseInt(toYear);
		String[] years = new String[to-from];
		for(int year=from,i=0 ;year<to;year++,i++){
			years[i] = String.valueOf(year)+"-"+String.valueOf(year+1).substring(2,4);
		}		 
		return years;
	}
	
	public static String[] getFinYearsTill(String fromYear){
		int toYear = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));
		if(Integer.parseInt((new SimpleDateFormat("MM")).format(new Date()))>3){
			toYear ++;
		}
		return getFinYears(fromYear,String.valueOf(toYear));
	}
	
	public static String getDay(String date){
		String day = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			 con = DBUtility.getConnection();
			 stmt = con.createStatement();		
			 rs = stmt.executeQuery("select to_char(to_date('"+date+"','dd/mm/yyyy'),'Day') from dual");
			if(rs.next()){
				day = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtility.closeConnection(con,stmt,rs);
		}
		return day;
	}
	
	public static List getMonths(){
		List months = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			 con = DBUtility.getConnection();
			 stmt = con.createStatement();			
			 rs = stmt.executeQuery("select  to_char(add_months(to_date('01/01/1000', 'DD/MM/RRRR'), rownum-1), 'MON') from tab where rownum<13");
			while(rs.next()){
				months.add(new Bean(rs.getString(1),rs.getString(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtility.closeConnection(con,stmt,rs);
		}
		return months;
	}

	public static String[] getYearsTill(String fromYear) {
		int toYear = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date()));		
		int from = Integer.parseInt(fromYear);
		int to = toYear;
		String[] years = new String[to-from+1];
		for(int year=from,i=0 ;year<=to;year++,i++){
			years[i] = String.valueOf(year);
		}	
		return years;
	}
	
	public static List getFYearMonths(){
		List months = new ArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			 con = DBUtility.getConnection();
			 stmt = con.createStatement();			
			 rs = stmt.executeQuery("select to_char(add_months(to_date('01/01/1000', 'DD/MM/RRRR'), rownum+2), 'MON') from tab where rownum<13");
			while(rs.next()){
				months.add(new Bean(rs.getString(1),rs.getString(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtility.closeConnection(con,stmt,rs);
		}
		return months;
	}	
	
}
