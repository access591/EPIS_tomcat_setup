/**
 * File       : DateValidation.java
 * Date       : 08/07/2007
 * Author     : AIMS 
 * Description: 
 * Copyright (2007) by the Navayuga Infotech, all rights reserved.
 */

package com.epis.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DateValidation {
	Log log = new Log(DateValidation.class);

	public String convertDateFormat(String date) throws InvalidDataException {
		char[] delimiters = { '.', ' ', '-', ',' };
		char[] rmdelimiters = { '*', ',' };
		String newDateFormat = "";
		char[] delimit = { '/' };
		String finalDate = "";
		log.info("====checkDayFormat========Form=date=" + date);
		date = validateAlphaNumber(date.toCharArray());
		if (checkDelimeters(date.toCharArray()) == true) {
			if (date.indexOf("'") != -1) {
				String rmdelimiter = "\'";
				date = StringUtility.replace(date.trim().toCharArray(),
						rmdelimiter.toCharArray(), "").toString();
			} else {
				date = StringUtility.replaces(date.trim().toCharArray(),
						rmdelimiters, "").toString();
			}
		}

		newDateFormat = StringUtility.replaces(date.trim().toCharArray(),
				delimiters, "/").toString();
		if (newDateFormat.indexOf("/") != -1) {
			newDateFormat = removeDelimeter(newDateFormat.toCharArray());
			newDateFormat = checkDayFormat(newDateFormat);
			newDateFormat = checkMonthYearFormat(newDateFormat);
			CommonUtil commonUtil = new CommonUtil();
			if (!newDateFormat.equals("")) {
				finalDate = commonUtil.converDBToAppFormat(newDateFormat,
						"MM/dd/yyyy", "dd-MMM-yyyy");
			} else {
				throw new InvalidDataException("Invalid Date");
			}

		} else {
			throw new InvalidDataException("Invalid Date Format");
		}

		return finalDate;

	}

	private String checkDayFormat(String dateFormat) {
		String day = "", tempDay = "", replacedDay = "", tempDayVal = "";
		int index = 0, tempVal = 0;
		index = dateFormat.indexOf("/");
		String finalday = "";

		/*
		 * Day value should be a two digits or one digit
		 */
		log.info("====checkDayFormat=========index=" + index);
		if (index != 0) {
			tempDayVal = dateFormat.substring(0, index);
			if (index >= 1 && index <= 2) {
				day = dateFormat.substring(0, index);
				replacedDay = day + "/";
			} else {
				tempDay = dateFormat.substring(0, 2);
				char days[] = tempDay.toCharArray();
				boolean dayFlag = false;
				for (int i = 0; i < days.length; i++) {
					if (Character.isDigit(days[i]) == true) {
						dayFlag = true;
					}
				}
				tempVal = Integer.parseInt(tempDay);

				if (dayFlag == true) {
					if (dayFlag) {
						if (tempVal >= 1 && tempVal <= 31) {

							day = tempDay;
							replacedDay = day + "/";
						}
					}
				}
			}

			finalday = StringUtility.replace(tempDayVal.toCharArray(),
					day.toCharArray(), replacedDay).toString();
			finalday = finalday
					+ dateFormat.substring(index + 1, dateFormat.length());
			log.info("====checkDayFormat=========days=" + finalday);
		}

		return finalday;

	}

	private String checkMonthYearFormat(String dateFormat) {
		String month = "", tempMonth = "", tempYear = "", year = "", tempDateFrmt = "", fnalDtFrmt = "", replacedVal = "";
		String oldDateVal = "";
		int strtIndex = 0, endIndex = 0, monthVal = 0;
		char[] delimit = { '/' };
		log.info("======checkMonthYearFormat======" + dateFormat);

		strtIndex = dateFormat.indexOf("/");
		endIndex = dateFormat.lastIndexOf("/");
		if (strtIndex != -1 && endIndex != -1) {
			log.info("======checkMonthYearFormat======" + strtIndex
					+ "endIndex" + endIndex);
			if (strtIndex == endIndex) {
				tempDateFrmt = dateFormat.substring(strtIndex + 1, dateFormat
						.length());
				oldDateVal = tempDateFrmt;

				if (tempDateFrmt.length() >= 4) {
					tempDateFrmt = chkAlphaNumber(tempDateFrmt);

					tempMonth = tempDateFrmt.substring(0, 2);
					tempYear = tempDateFrmt.substring(2, tempDateFrmt.length());
				} else {
					tempMonth = tempDateFrmt.substring(0, 1);
					tempYear = tempDateFrmt.substring(1, tempDateFrmt.length());
				}
				log.info("======checkMonthYearFormat======(tempMonth)"
						+ tempMonth);
				log.info("======checkMonthYearFormat======(tempYear)"
						+ tempYear);
				monthVal = Integer.parseInt(tempMonth);
				if (monthVal >= 1 && monthVal <= 12) {

					char months[] = tempMonth.toCharArray();
					boolean mnthFlag = false;
					for (int i = 0; i < months.length; i++) {
						if (Character.isDigit(months[i]) == true) {
							mnthFlag = true;
						}
					}
					String chkYear = "";

					if (mnthFlag == true) {

						month = tempMonth;
						year = tempYear;
						int odd = 0, even = 2, strtInd = 0, endInd = 0;
						boolean chkflag = false;
						if (year.length() > 4) {
							chkYear = year.substring(0, 2);
							for (int i = 1; i < year.length(); i++) {

								if (chkflag != true) {
									if (Integer.parseInt(chkYear) == 19
											|| Integer.parseInt(chkYear) == 20) {
										chkflag = true;
										year = year.substring(strtInd, year
												.length());
									} else {
										strtInd = i + odd;
										endInd = i + even;
										chkYear = year.substring(strtInd,
												endInd);
									}
								}

							}
						}

						replacedVal = month + "/" + year;

						fnalDtFrmt = StringUtility.replace(
								dateFormat.toCharArray(),
								oldDateVal.toCharArray(), replacedVal)
								.toString();
						log.info("======checkMonthYearFormat======(fnalDtFrmt)"
								+ fnalDtFrmt);
					} else {
						/*
						 * Write a code for if month enters charcters
						 */
						month = dateFormat.substring(strtIndex + 1, endIndex);
						System.out.println("month=" + month);
					}
				} else {
					tempMonth = tempDateFrmt.substring(0, 1);
					monthVal = Integer.parseInt(tempMonth);
				}
			} else {
				tempMonth = dateFormat.substring(strtIndex + 1, endIndex);

				oldDateVal = tempMonth;
				tempMonth = chkAlphaNumber(tempMonth);
				if (!tempMonth.equals(oldDateVal)) {
					fnalDtFrmt = StringUtility.replace(
							dateFormat.toCharArray(), oldDateVal.toCharArray(),
							tempMonth).toString();
				} else {
					fnalDtFrmt = dateFormat;
				}

				log.info("month=" + fnalDtFrmt);
			}
		}

		return fnalDtFrmt;

	}

	private boolean checkDelimeters(char[] date) {
		boolean chkDelimeter = false;
		int count = 0;
		char[] delimiters = { '.', ' ', '-', ',', '/', '*', '\'' };
		for (int i = 0; i < date.length; i++) {
			for (int j = 0; j < delimiters.length; j++) {
				if (date[i] == delimiters[j]) {
					// System.out.println("Delimeters"+delimiters[j]);
					count++;
				}
			}
		}

		if (count > 2) {
			chkDelimeter = true;
		}

		return chkDelimeter;

	}

	public String replaceAllWords2(String original, String find,
			String replacement) {
		StringBuffer result = new StringBuffer(original.length());
		String delimiters = "+-*/(),. ";
		StringTokenizer st = new StringTokenizer(original, delimiters, true);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			if (w.equals(find)) {
				result.append(replacement);
			} else {
				result.append(w);
			}
		}
		return result.toString();
	}

	private String removeDelimeter(char[] date) {
		int count = 0;
		char delimiters = '/';
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < date.length; i++) {
			if (date[i] == delimiters) {
				count++;
				if (count > 2) {
					i++;
				}
			}
			if (i < date.length) {
				sb.append(date[i]);
			}

		}

		return sb.toString();

	}

	private String chkAlpha(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				buff.append(c);

			}

		}
		return buff.toString();
	}

	private String chkDigit(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if (c >= '0' && c <= '9') {
				buff.append(c);

			}

		}
		return buff.toString();
	}

	private String chkAlphaNumber(String value) {
		String month = "", letter = "", digit = "", alphaMonth = "", digitMonth = "", finalValue = "";
		String findValue = "";
		letter = chkAlpha(value.toCharArray());
		digit = chkDigit(value.toCharArray());

		if (!letter.equals("")) {
			alphaMonth = getMonthsList(letter.substring(0, 3).toLowerCase());
		}
		if (!digit.equals("")) {
			digitMonth = getMonthValue(digit);
		}
		if (!alphaMonth.equals("")) {
			month = alphaMonth;
			findValue = letter;
		} else if (!digitMonth.equals("0")) {
			month = digitMonth;
			findValue = digit;
		} else {
			month = value;
		}

		if (!month.equals(value)) {
			finalValue = StringUtility.replace(value.toCharArray(),
					findValue.toCharArray(), month).toString();
		} else {
			finalValue = value;
		}

		return finalValue;

	}

	public String getMonthsList(String month) {
		String value = "";
		Map map = new LinkedHashMap();
		map.put("jan", "01");
		map.put("feb", "02");
		map.put("mar", "03");
		map.put("apr", "04");
		map.put("may", "05");
		map.put("jun", "06");
		map.put("jul", "07");
		map.put("aug", "08");
		map.put("sep", "09");
		map.put("oct", "10");
		map.put("nov", "11");
		map.put("dec", "12");

		value = (String) map.get(month);

		return value;

	}

	public String getMonthName(String month) {
		String value = "";
		Map map = new LinkedHashMap();
		map.put("01", "Jan");
		map.put("02", "Feb");
		map.put("03", "Mar");
		map.put("04", "Apr");
		map.put("05", "May");
		map.put("06", "Jun");
		map.put("07", "Jul");
		map.put("08", "Aug");
		map.put("09", "Sep");
		map.put("10", "Oct");
		map.put("11", "Nov");
		map.put("12", "Dec");

		value = (String) map.get(month);

		return value;

	}

	public String getMonthValue(String month) {
		String value = "";
		int monthVal = 0;
		monthVal = Integer.parseInt(month);
		if (monthVal >= 1 && monthVal <= 12) {
			value = month;
		} else {
			value = "0";
		}
		return value;

	}

	private String validateAlphaNumber(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		StringBuffer digitBuff = new StringBuffer();
		StringBuffer finalBuff = new StringBuffer();
		String validDate = "";

		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				buff.append(c);

			} else if (c >= '0' && c <= '9') {
				digitBuff.append(c);
			} else if (c == '/' || c == '.' || c == '-' || c == ' ') {
				digitBuff.append(c);
			}
			finalBuff.append(c);
		}
		String validDt = buff.toString();
		System.out.println("validateAlphaNumber=Before=(validDt)=" + validDt);
		if (!validDt.equals("")) {
			if (validDt.length() > 2) {
				validDt = getMonthsList(validDt.substring(0, 3).toLowerCase());
			} else {
				validDt = "";
			}

			System.out.println("validateAlphaNumber==(validDt)=" + validDt);
			if (validDt != null && !validDt.equals("")) {
				validDate = finalBuff.toString().trim();
			} else {

				validDate = digitBuff.toString();
				if (validDate.indexOf(".") != -1
						&& (validDate.indexOf(" ") != -1)) {
					String rmdelimiter = ".";
					validDate = StringUtility.replace(
							validDate.trim().toCharArray(),
							rmdelimiter.toCharArray(), "").toString().trim();
				}
			}
		} else {
			validDate = finalBuff.toString().trim();

		}

		System.out.println("validateAlphaNumber===" + validDate);
		return validDate;
	}

	public String getLetterYear() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat yearformatter = new SimpleDateFormat("yy");
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String currentMonth = formatter.format(today);
		int month = Integer.parseInt(currentMonth);
		String currentYear = yearformatter.format(today);
		int year = Integer.parseInt(currentYear);
		String result = "";
		int cyear = 0;
		if (month >= 4) {
			cyear = year + 1;
			result = "" + year + "-" + cyear;

		} else {
			cyear = year - 1;
			result = "" + cyear + "-" + year;
		}

		return result;
	}
	public String getLetterYear(String dealYear) {
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy"); 
		Date today=null;
		try {
			today = df.parse(dealYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat yearformatter = new SimpleDateFormat("yy");
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String currentMonth = formatter.format(today);
		int month = Integer.parseInt(currentMonth);
		String currentYear = yearformatter.format(today);
		int year = Integer.parseInt(currentYear);
		String result = "";
		int cyear = 0;
		if (month >= 4) {
			cyear = year + 1;
			result = "" + year + "-" + cyear;

		} else {
			cyear = year - 1;
			result = "" + cyear + "-" + year;
		}

		return result;
	}

	public String getCurrentDate() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String formate = formatter.format(today);
		return formate;
	}

	public String getBankLetterYear() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat yearformatter = new SimpleDateFormat("yy");
		SimpleDateFormat yearformatter1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String currentMonth = formatter.format(today);
		int month = Integer.parseInt(currentMonth);
		String currentYear = yearformatter.format(today);
		String currentyear1 = yearformatter1.format(today);
		int year = Integer.parseInt(currentYear);
		int year1 = Integer.parseInt(currentyear1);
		String result = "";
		int cyear = 0;
		if (month >= 4) {
			cyear = year + 1;
			result = "" + year1 + "-" + cyear;

		} else {
			cyear = year - 1;
			result = "" + cyear + "-" + year1;
		}

		return result;
	}

	public int getBankLetterMonth() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String currentMonth = formatter.format(today);
		int month = Integer.parseInt(currentMonth);
		return month;
	}

	public String getTenure(int fromyear, int fromMonth, int fromDate,
			int toYear, int toMonth, int toDate) {
		log.info("this calling...");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.set(fromyear, fromMonth, fromDate);
		cal2.set(toYear, toMonth, toDate);

		long milis1 = cal1.getTimeInMillis();

		long milis2 = cal2.getTimeInMillis();
		long years = 0;
		long months = 0;
		try {
			long diff = milis2 - milis1;

			long diffDays = diff / (24 * 60 * 60 * 1000);

			years = diffDays / 365;

			long remaining = diffDays % 365;

			months = remaining / 30;
		} catch (Exception e) {
			System.out.println("the exception is" + e.toString());
		}

		return years + " Year" + months + " Months";
	}

	public String convertMonth(String year) {
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;

		String formateDate = "";
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			String vsql = "SELECT to_char(TO_DATE('" + year
					+ "','dd/mm/yyyy'),'dd/mm/yyyy') Maturitydate from dual ";
			rs = stmt.executeQuery(vsql);
			if (rs.next()) {
				formateDate = rs.getString("Maturitydate");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBUtility.closeConnection(rs, stmt, con);
		}
		return formateDate;
	}

	public static boolean isDateBetween(String from, String to, String date) {
		boolean bool = false;
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
			con = DBUtility.getConnection();
			stmt = con.createStatement();
			String vsql = "select case when to_date('"+date+"')>=to_date('"+from+"') and to_date('"+date+"') <= to_date('"+to+"') then 1 else 0 end from  dual";
			rs = stmt.executeQuery(vsql);
			if (rs.next()) {
				bool = rs.getBoolean(1);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			DBUtility.closeConnection(rs, stmt, con);
		}
		return bool;
	}
	
	public String getJVLetterYear(String dealYear) {
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy"); 
		Date today=null;
		try {
			today = df.parse(dealYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat yearformatter = new SimpleDateFormat("yy");
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat outputyearformatter=new SimpleDateFormat("yyyy");
		String currentMonth = formatter.format(today);
		int month = Integer.parseInt(currentMonth);
		String currentYear = yearformatter.format(today);
		String outyear=outputyearformatter.format(today);
		int year = Integer.parseInt(currentYear);
		int outputyear=Integer.parseInt(outyear);
		String result = "";
		int cyear = 0;
		if (month >= 4) {
			cyear = year + 1;
			result = "" + outputyear + "-" + cyear;

		} else {
			cyear = outputyear - 1;
			result = "" + cyear + "-" + year;
		}

		return result;
	}

	public static void main(String args[]) throws Exception {
		DateValidation datevalid = new DateValidation();
		System.out.println("the coming..formate is"
				+ datevalid.getLetterYear("23/mar/2005"));
	}
}
