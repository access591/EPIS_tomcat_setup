package com.epis.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.PropertyConfigurator;

//import sun.security.action.GetLongAction;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import com.epis.bean.advances.AdvanceBasicBean;
import com.epis.bean.rpfc.EmpMasterBean;
import com.epis.bean.rpfc.RegionBean;

public class CommonUtil implements Constants {
	static Log log = new Log(CommonUtil.class);
	DBUtility commonDB = new DBUtility();
	/**
	 * @param args
	 */
	static Properties props = new Properties();

	/* from suresh */
	public static Properties getPropsFile(String filepath){
		System.out.println(""+filepath);
		ClassLoader loader = ClassLoader.getSystemClassLoader();
	    InputStream inStream = null;
	    try{
	    	inStream = loader.getResourceAsStream(filepath);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		System.out.println("input stream is--- "+inStream);
		try{
			props.load(inStream);
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	}
	public static Properties getPropsFile(String filepath,Class className){
		System.out.println(""+filepath+"ClassName"+className);
		ClassLoader loader = className.getClassLoader();
	    InputStream inStream = null;
	    try{
	    	inStream = loader.getResourceAsStream(filepath);
	    	props.load(inStream);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return props;
	}
	public static Properties loadLogFile(String filepath,Class className){
		String filePath="";
	
		try{
			props=CommonUtil.getPropsFile(Constants.APPLICATION_CONFIG_PROPERTIES_FILE_NAME,className);
			filePath=props.getProperty("log.filepath");
			props.clone();
		    props=CommonUtil.getPropsFile(filepath,className);
			props.setProperty("info_file_path",filePath);
		 	PropertyConfigurator.configure(props);
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	} 
	public int gridLength() {
		int gridLength = 0;
		LoadProperties getProperties = new LoadProperties();
		Properties prop = new Properties();
		log.info(Constants.APPLICATION_PROPERTIES_FILE_NAME);
		ResourceBundle bundle = ResourceBundle
		.getBundle(Constants.APPLICATION_PROPERTIES_FILE_NAME);
		if (prop.getProperty("common.gridlength") != null) {
			gridLength = Integer
					.parseInt(bundle.getString("common.gridlength"));
		}

		return gridLength;
	}

	public String readExcelSheet(String fileName) throws BiffException,
			IOException {
		log.info("CommonUtil:readExcelSheet Entering method");
		String readExcelData = "";
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));
		try {
			Workbook workbook = Workbook.getWorkbook(new File(fileName), ws);
			Sheet s = workbook.getSheet(0);
			// Sheet s = workbook.getSheet(3);
			readExcelData = readDataSheet(s);
		} catch (BiffException e) {
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		log.info("CommonUtil:readExcelSheet leaving method");
		return readExcelData;
	}

	private String readDataSheet(Sheet s) {
		log.info("CommonUtil:readDataSheet Entering method");
		Cell cell = null;
		StringBuffer eachRow = new StringBuffer();
		log.info("Columns" + s.getColumns() + "Rows" + s.getRows());
		String delimiter = "*", cellContent = "";

		for (int j = 1; j< s.getRows(); j++) {
			for (int i = 0; i < s.getColumns(); i++) {
				cell = s.getCell(i, j);
				if (!cell.getContents().equals("")) {
					cellContent = StringUtility.replace(
							cell.getContents().toCharArray(),
							delimiter.toCharArray(), "").toString();
					// System.out.println("Format"+cell.getType()+"Contents"+cell.getContents());
					if (!cellContent.equals("")) {
						eachRow.append(cell.getContents() + "@");
					} else {
						eachRow.append("XXX" + "@");
					}

				} else {
					eachRow.append("XXX" + "@");
				}

			}

			eachRow.append("***");
		}
		log.info("CommonUtil:readDataSheet Leaving method");
		return eachRow.toString();
	}

	public ArrayList getTheList(String tableList, String delimeter) {
		// log.info("CommonUtil:getTheList Entering method");
		ArrayList tblList = new ArrayList();
		StringTokenizer strTokens = new StringTokenizer(tableList, delimeter);

		while (strTokens.hasMoreTokens()) {

			tblList.add(strTokens.nextToken());
		}
		// log.info("CommonUtil:getTheList Leaving method");
		return tblList;
	}

	public String escapeSingleQuotes(String strToEscape) {
		if (strToEscape.indexOf('\'') == -1)
			return strToEscape;
		StringBuffer strBuf = new StringBuffer(strToEscape);
		int j = 0;
		int i = 0;
		while (i <= strToEscape.length()) {
			i = strToEscape.indexOf('\'', i);
			if (i == -1)
				break;
			strBuf = strBuf.insert(i + j, '\'');
			i++;
			j++;
		}
		strToEscape = strBuf.toString();
		return strToEscape;
	}

	public void writeFile(String message, String className) {
		String fileName = "";
		fileName = "C://admin//" + generateFileName("AdminLog") + ".txt";
		log.info("Write File in Common Util" + fileName);
		log.info("Write File in Common Util" + message);
		File file = new File(fileName);
		boolean exists = (file).exists();
		try {
			if (!exists) {
				System.out.println("File Not Existed");
				file.createNewFile();
			}
			System.out.println("File Existed");
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName,
					true));
			String format = "\r\n"
					+ this
							.getDateTime(Constants.APPLICATION_DATE_TIME_FORMAT_HYPEN)
					+ ">> " + className + ">> " + message;
			out.write(format);
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String generateFileName(String prefix) {
		String fileName = "";
		fileName = prefix + "_"
				+ this.getDateTime(Constants.APPLICATION_DATE_FORMAT);
		return fileName;
	}

	public String getDateTime(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	public static String getFullNameofMonth(String mon) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM");
		System.out.println(mon);
		String month = "";
		try {
			Date date = sdf.parse(mon);
			sdf = new SimpleDateFormat("MMMMM");
			month = sdf.format(date).toUpperCase();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}
		
	public static Properties loadFile(String fileName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream inStream = null;
		try {
			inStream = loader.getResourceAsStream(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("input stream is--- " + inStream);
		try {
			props.load(inStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	public static String converDBToAppFormat(Date dbDate) {
		Calendar cal = Calendar.getInstance();
		String convertedDt = "";

		SimpleDateFormat fromDate = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat toDate = new SimpleDateFormat("dd-MMM-yyyy");
		convertedDt = toDate.format(dbDate);

		return convertedDt;
	}
	
	public static String converDBToAppFormat(Date dbDate,String fromDtFormat) {
		Calendar cal = Calendar.getInstance();
		String convertedDt = "";

		SimpleDateFormat fromDate = new SimpleDateFormat(fromDtFormat);
		SimpleDateFormat toDate = new SimpleDateFormat("dd-MMM-yyyy");
		convertedDt = toDate.format(dbDate);

		return convertedDt;
	}

	public static String converDBToAppFormat(Date dbDate,String fromDtFormat,String toDateFormat) {
		Calendar cal = Calendar.getInstance();
		String convertedDt = "";

		SimpleDateFormat fromDate = new SimpleDateFormat(fromDtFormat);
		SimpleDateFormat toDate = new SimpleDateFormat(toDateFormat);
		convertedDt = toDate.format(dbDate);

		return convertedDt;
	}

	public String getDate(String st) {
		//SimpleDateFormat fullMonthFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat fullMonthFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat toMonthFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		String date2 = "";
		Date dateR;
		try {
			
			dateR = fullMonthFormat.parse(st);

			String date1 = fullMonthFormat.format(dateR);
			date2 = toMonthFormat.format(fullMonthFormat.parse(date1));
			System.out.println("date1=====" + date1);
			System.out.println("date2=====" + date2);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}
	
	public static String getCurrentMonth() {
		SimpleDateFormat toMonthFormat = new SimpleDateFormat("MMM");
		return toMonthFormat.format(new Date());
	}
	
	public static String getNextMonth(String mon)  {
		SimpleDateFormat toMonthFormat = null;
		Calendar c1 = null;
		try {
			toMonthFormat = new SimpleDateFormat("MMM");
			c1 = Calendar.getInstance(); 
			c1.setTime(toMonthFormat.parse(mon));
			c1.add(Calendar.MONTH, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toMonthFormat.format(c1.getTime());
	}

	
	/**
	 * It'll save the file at the given path for given input stream object.
	 * 
	 * @param is
	 * @param path
	 */
	public static void saveFile(InputStream is, String path) throws Exception {

		try {
			File saveFilePath = new File(path);
			if (!saveFilePath.exists()) {
				File saveDir = new File(saveFilePath.getParent());
				if (!saveDir.exists())
					saveDir.mkdirs();
				saveFilePath.createNewFile();
			}

			FileOutputStream fout = new FileOutputStream(saveFilePath);
			int c;
			while ((c = is.read()) != -1) {
				fout.write(c);
			}
			fout.close();
			is.close();
		} catch (IOException _IOEx) {

		}
	}

	public Map getMonthsList() {
		Map map = new LinkedHashMap();
		map.put("01", "January");
		map.put("02", "February");
		map.put("03", "March");
		map.put("04", "April");
		map.put("05", "May");
		map.put("06", "June");
		map.put("07", "July");
		map.put("08", "August");
		map.put("09", "September");
		map.put("10", "October");
		map.put("11", "November");
		map.put("12", "December");
		return map;

	}

	public long getDateDifference(String date1, String date2) {
		long noOfDays = 0;
		int days = 0;
		Date validatDt1 = new Date();
		Date validatDt2 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			validatDt1 = sdf.parse(date1);
			validatDt2 = sdf.parse(date2);

			noOfDays = (validatDt2.getTime() - validatDt1.getTime());
			noOfDays = (noOfDays / (1000L * 60L * 60L * 24L * 365));
        
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return noOfDays;

	}


	public String converDBToAppFormat(String dbDate, String fromFormat,
			String toFormat) throws InvalidDataException {
		String convertedDt = "";
		SimpleDateFormat fromDate = new SimpleDateFormat(fromFormat);
		SimpleDateFormat toDate = new SimpleDateFormat(toFormat);
		try {
			if (!dbDate.equals("")) {
				convertedDt = toDate.format(fromDate.parse(dbDate));
			/*	log.info("Converted Date is(converDBToAppFormat) "
						+ convertedDt);*/
			} else {
				convertedDt = "";
			}

		} catch (ParseException e) {
			throw new InvalidDataException(e.getMessage());
		}

		return convertedDt;

	}
	public static long getDifferenceTwoDatesInDays(String retirmentDt,
			String currentDate) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		long days = 0;
		try {
			Date oldDate = df.parse(retirmentDt);
			Date newDate = df.parse(currentDate);
			days = ((oldDate.getTime() - newDate.getTime()) / (24 * 60 * 60 * 1000));
			System.out.println(days);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return days;
	}
	public boolean checkFormat(String dbDate) throws InvalidDataException {
		boolean chkFormat = false;
		String year = "", month = "";
		int yearLength = 0, monthLength = 0;
		char delimeter = '-';
		if (dbDate.length() > 8) {
			if (checkDateFormat(dbDate.toCharArray(), delimeter) == 2) {
				if (dbDate.indexOf("-") != -1) {
					int index = dbDate.indexOf("-");
					month = dbDate
							.substring(index + 1, dbDate.lastIndexOf("-"));
					monthLength = month.length();
				}
				if (dbDate.lastIndexOf("-") != -1) {
					int index = dbDate.lastIndexOf("-");
					year = dbDate.substring(index + 1, dbDate.length());
					yearLength = year.length();
				}
				if (monthLength == 3 && yearLength == 4) {
					chkFormat = true;
				}
			} else {
				throw new InvalidDataException("Invalid Date Format");
			}

		} else {
			throw new InvalidDataException("Invalid Date Format");
		}

		return chkFormat;

	}

	private int checkDateFormat(char[] dbDate, char delimeter) {
		int countDelimeters = 0;
		for (int i = 0; i < dbDate.length; i++) {
			if (dbDate[i] == delimeter) {
				countDelimeters++;
			}

		}

		return countDelimeters;

	}

	public int getDayLength(String dbDate) throws InvalidDataException {

		String day = "";
		int dayLength = 0;
		if (dbDate.indexOf("-") != -1) {
			int index = dbDate.indexOf("-");
			day = dbDate.substring(0, dbDate.indexOf("-"));
			dayLength = day.length();
		}

		return dayLength;

	}

	public String convertDateFormat(String date) {
		char[] delimiters = { '.', ' ', '-', ',' };
		String newDateFormat = "";
		String delimiter = "";
		if (checkDelimeters(date.toCharArray()) == true) {
			if (date.indexOf(',') != -1) {
				delimiter = ",";
				date = StringUtility.replace(date.trim().toCharArray(),
						delimiter.toCharArray(), "").toString();
			} else if (date.indexOf('*') != -1) {
				delimiter = "*";
				date = StringUtility.replace(date.trim().toCharArray(),
						delimiter.toCharArray(), "").toString();
			}
		}

		newDateFormat = StringUtility.replaces(date.trim().toCharArray(),
				delimiters, "/").toString();
		return newDateFormat;

	}

	private boolean checkDelimeters(char[] date) {
		boolean chkDelimeter = false;
		int count = 0;
		char[] delimiters = { '.', ' ', '-', ',', '/', '*' };
		for (int i = 0; i < date.length; i++) {
			for (int j = 0; j < delimiters.length; j++) {
				if (date[i] == delimiters[j]) {
					System.out.println("Delimeters" + delimiters[j]);
					count++;
				}
			}
		}

		if (count > 2) {
			chkDelimeter = true;
		}

		return chkDelimeter;

	}

	public String readTextfile(String fileName) {
		StringBuffer eachRow = new StringBuffer();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(fileName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// String strLine="avad $ sdasf $asdf";

			// Read File Line By Line
			String strLine;
			String tempInfo[] = null;
			while ((strLine = br.readLine()) != null) {

				tempInfo = strLine.split("$");
				// System.out.println("tempinfo length" + tempInfo.length);
				for (int i = 0; i <= tempInfo.length; i++) {
					eachRow.append(tempInfo[0]);
					// System.out.println("eachrow "+eachRow.toString());
					eachRow.append("***");
				}

			}
			// Print the content on the console
			// System.out.println(strLine);

			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return eachRow.toString();
	}

	public HashMap getRegion() {
		HashMap hashmap = new HashMap();
		hashmap.put("1", "East Region");
		hashmap.put("2", "West Region");
		hashmap.put("3", "South Region");
		hashmap.put("4", "North Region");
		hashmap.put("5", "North-East Region");
		hashmap.put("6", "CHQNAD");
		hashmap.put("7", "CHQIAD");
        // code commented to disable CHQ MASTERDATA ON Aug-06
		//hashmap.put("8", "CHQ");
		hashmap.put("8", "RAUSAP");
		
		return hashmap;
	}
   public TreeMap getRegion1() {
        
        TreeMap hashmap = new TreeMap();
        hashmap.put("1", "CHQNAD");
        hashmap.put("2", "North Region");
        hashmap.put("3", "East Region");
        hashmap.put("4", "West Region");
        hashmap.put("5", "South Region");
        hashmap.put("6", "North-East Region");
        hashmap.put("7", "RAUSAP");
        // code commented to disable CHQ MASTERDATA ON Aug-06
        // hashmap.put("8", "CHQ");
        hashmap.put("8", "CHQIAD");
        
        return hashmap;
    }
   public HashMap getRegionsForComparativeReport() {
		HashMap hashmap = new LinkedHashMap();
		hashmap.put("1", "East Region");
		hashmap.put("2", "West Region");
		hashmap.put("3", "South Region");
		hashmap.put("4", "North Region");
		hashmap.put("5", "North-East Region");
		hashmap.put("6", "CHQNAD");		
		hashmap.put("7", "IAD-AmritsarIAD");
		hashmap.put("8", "IAD-CAP IAD");
		hashmap.put("9", "IAD-CHENNAI IAD");
		hashmap.put("10", "IAD-CSIA IAD");	
		hashmap.put("11", "IAD-DPO IAD");
		hashmap.put("12", "IAD-IGI IAD");
		hashmap.put("13", "IAD-IGICargo IAD");
		hashmap.put("14", "IAD-KOLKATA");
		hashmap.put("15", "IAD-KOLKATA PROJ");
		hashmap.put("16", "IAD-OFFICE COMPLEX");
		hashmap.put("17", "IAD-TRIVANDRUM IAD");
		hashmap.put("18", "RAUSAP");
	
		return hashmap;
	}
	private String validateAlphabetic(char[] frmtDt) {
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
			}

		}
		String validDt = buff.toString();
		// System.out.println("validateAlphaNumber=Before=(validDt)="+validDt);
		// System.out.println("validateAlphaNumber==="+validDate);
		return validDate;
	}

	public String validateNumber(char[] frmtDt) {
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
			}

		}
		String validDt = digitBuff.toString() + "," + buff.toString();
		System.out.println("validateAlphaNumber=Before=(validDt)=" + validDt);
		// System.out.println("validateAlphaNumber==="+validDate);
		return validDt;
	}
	public String validateEmployeeName(char[] frmtDt) {
		StringBuffer buff = new StringBuffer();
		StringBuffer digitBuff = new StringBuffer();
		StringBuffer finalBuff = new StringBuffer();
		String validDate = "";

		for (int i = 0; i < frmtDt.length; i++) {
			char c = frmtDt[i];
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				buff.append(c);
			} else if (c >= '0' && c <= '9') {
				buff.append(c);
			}else if (c >= ' ') {
				digitBuff.append(c);
			}

		}
		String employeeName = buff.toString() ;
		System.out.println("validateAlphaNumber=Before=(validDt)=" + employeeName);
		// System.out.println("validateAlphaNumber==="+validDate);
		return employeeName;
	}
	public static String getDatetoString(Date dt,String format) {
		
		// SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat to = new SimpleDateFormat(format);
		String convertDate = "";
		convertDate = to.format(dt);
		//log.info("Date is" + convertDate);

	
		return convertDate;
	}
	public String getCurrentDate(String format) {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.format(new Date());
		return date;
	}

	public  String replaceAllWords2(String original, String find, String replacement) {
	    StringBuffer result = new StringBuffer(original.length());
	    
	    StringTokenizer st = new StringTokenizer(original,find);
	    while (st.hasMoreTokens()) {
	        String w = st.nextToken();
	        log.info(w);
	        if (w.equals(find)) {
	            result.append(replacement);
	        } else {
	            result.append(w);
	        }
	    }
	    return result.toString();
	}
	public int getYeareDifference(String date1, String date2) {
		long noOfDays = 0;
		int days = 0;
		String validatDt1 = "";
		String validatDt2 = "";
		SimpleDateFormat from = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
			try {
				validatDt1 = sdf.format(from.parse(date1));
				validatDt2 = sdf.format(from.parse(date2));
				days=Integer.parseInt(validatDt1)-Integer.parseInt(validatDt2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        log.info("no of days(getYeareDifference)" +noOfDays);
	
	return days;

	}
	public ArrayList getAirportsByFinanceTbl(String region) {
		Connection con = null;
		Statement st=null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String unitName = "", unitCode = "";
		
		try {
			con = commonDB.getConnection();
			 st = con.createStatement();
			String query = "SELECT distinct AIRPORTCODE  FROM mv_employee_pension_airports where region='"
				+ region+"'";
			log.info("getAirportsByFinanceTbl==query==========="+query);
			rs = st.executeQuery(query);
			while (rs.next()) {
				EmpMasterBean bean = new EmpMasterBean();
				if (rs.getString("AIRPORTCODE") != null) {
					unitName = (String) rs.getString("airportcode").toString().trim();
					bean.setStation(unitName);
				}else {
					bean.setStation("");
				}
				
				airportList.add(bean);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return airportList;

	}
	public ArrayList getAirports(String region) {
		Connection con = null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String unitName = "", unitCode = "";

		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			String sql = "select * from employee_unit_master where region in ('"+region+"','CHQIAD') order by region";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpMasterBean bean = new EmpMasterBean();
				unitName = (String) rs.getString("unitname").toString().trim();
			
				if (!unitName.equals("")) {
					bean.setStation(unitName.toUpperCase());
				} else {
					bean.setStation("");
				}
				if (!unitName.equals("")) {
					bean.setStationWithRegion(unitName.toUpperCase()+" - "+rs.getString("region"));
				} else {
					bean.setStationWithRegion("");
				}
				
				unitCode = (String) rs.getString("unitcode").toString().trim();
				if (!unitCode.equals("")) {
					
					bean.setUnitCode(unitCode.toUpperCase());
				} else {
					bean.setUnitCode("");
				}

				airportList.add(bean);
				// bean=null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return airportList;

	}
	public String duplicateWords(String words) {
		 String tempWords="";
		 Set s = new HashSet();
		 String[] wordList=words.split("=");
		    for(int i=0; i<wordList.length;i++){
		      if(!s.add(wordList[i]))
		        log.info("Duplicate detected : " + wordList[i]);
		    }
		    Iterator It = s.iterator();
	        int n=0;
	        while (It.hasNext()) {
	        	tempWords=tempWords+"="+It.next();
	          }
	        return tempWords;
	}
	public int getRowCount(ResultSet set) throws SQLException   
	{   
	   int rowCount;   
	   int currentRow = set.getRow();            // Get current row   
	   rowCount = set.last() ? set.getRow() : 0; // Determine number of rows   
	   if (currentRow == 0)                      // If there was no current row   
	      set.beforeFirst();                     // We want next() to go to first row   
	   else                                      // If there WAS a current row   
	      set.absolute(currentRow);              // Restore it   
	   return rowCount;   
	} 
	/*
	 * public static Date getStringtoDate(String dt) {
	 * log.info("CommonUtil:getStringtoDate-- Entering Method");
	 * SimpleDateFormat from = new SimpleDateFormat("dd/MMM/yyyy");
	 * SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd"); String str =
	 * ""; Date date = null; try { str = to.format(from.parse("01/07/06")); date =
	 * Date.parse(str); log.info("----" + date); } catch (ParseException e) {
	 * e.printStackTrace(); } log.info("CommonUtil:getStringtoDate-- Leaving
	 * Method"); return date;
	 *  }
	 */

/*	public static String getDatetoString(Date dt,String format) {
		//log.info("CommonUtil:getDatetoString-- Entering Method");
		// SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat to = new SimpleDateFormat(format);
		String convertDate = "";
		convertDate = to.format(dt);
		// log.info("Date is" + convertDate);

		//log.info("CommonUtil:getDatetoString-- Leaving Method");
		return convertDate;
	}
		public static String getDatetoString1(Date dt) {
		//log.info("CommonUtil:getDatetoString-- Entering Method");
		// SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat to = new SimpleDateFormat("dd/MMM/yy");
		String convertDate = "";
		convertDate = to.format(dt);
		log.info("Date is" + convertDate);

		//log.info("CommonUtil:getDatetoString-- Leaving Method");
		return convertDate;
	}
	public String getDate1(String st) {
		SimpleDateFormat fullMonthFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat toMonthFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date2 = "";
		Date dateR;
		try {
			// Date data = df.parse("02/20/2003");
			// String date1 = fullMonthFormat.format(data);
			dateR = fullMonthFormat.parse(st);

			String date1 = fullMonthFormat.format(dateR);
			date2 = toMonthFormat.format(fullMonthFormat.parse(date1));
			System.out.println("date1=====" + date1);
			System.out.println("date2=====" + date2);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date2;
	}
		public String converFormat(String date) {
		Calendar cal = Calendar.getInstance();
		String convertedDt = "";
		try {
			SimpleDateFormat fromDate = new SimpleDateFormat("yyyyMM");
			// SimpleDateFormat fromDate = new SimpleDateFormat("mmmmm");
			log.info("date" + date);
			SimpleDateFormat toDate = new SimpleDateFormat("dd-MMM-yy");
			convertedDt = toDate.format(fromDate.parse(date));
			log.info("convertedDt" + convertedDt);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDt;
	}
	*
	*/
	/*	public static String getStringtoDate(String dt) {
	SimpleDateFormat to = new SimpleDateFormat("dd/MMM/yyyy");
	SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
	String str = "";
	java.sql.Date date = null;
	try {
		str = to.format(from.parse(dt));

		System.out.println("----" + str);
	} catch (ParseException e) {
		str = dt;
		e.printStackTrace();
	}
	return str;
}

	public String converteFormToDateFormat(String date, String format) {
		String convertedDTFormat = "";
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat to = new SimpleDateFormat(format);
		try {
			convertedDTFormat = to.format(from.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDTFormat;
	}
*/
    
    public static String getExceptionMessage(String code){
        String msg = "";
        try{
            if(code != null)
                msg = ResourceBundle.getBundle("aims.resources.exceptionCodes").getString(code.trim());
        }catch(Exception ex){
            log.printStackTrace(ex);
        }
        
        return msg;
    }
    public ArrayList loadRegions() {
        Connection con = null;
        Statement st=null;
        ArrayList airportList = new ArrayList();
        ResultSet rs = null;
        RegionBean bean=null;
        String region="",aaiCategory="";
        try {
            con = commonDB.getConnection();
             st = con.createStatement();
            String query = "SELECT REGIONNAME,AAICATEGORY FROM EMPLOYEE_REGION_MASTER WHERE SORTEDCOL IS NOT NULL ORDER BY SORTEDCOL";
            log.info("loadRegions==query==========="+query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                bean = new RegionBean();
                if (rs.getString("REGIONNAME") != null && rs.getString("AAICATEGORY") != null) {
                    region = rs.getString("REGIONNAME").trim();
                    aaiCategory=rs.getString("AAICATEGORY").trim();
                    if(aaiCategory.equals("METRO AIRPORT")){
                
                        bean.setAirportcode(region);
                        bean.setRegion("CHQIAD");
                    }else{
                        bean.setAirportcode("-NA-");
                        bean.setRegion(region);
                    }
                    bean.setAaiCategory(aaiCategory);
                    log.info("Region"+bean.getRegion()+"Airportcode"+bean.getAirportcode());
                    
                }
                airportList.add(bean);
            }
        } catch (Exception e) {
            log.printStackTrace(e);
        }
        return airportList;

    }
    public String leadingZeros(int numOfLeadingZeros,String number) {
        int i=0;
        i=number.length();
        if ( i == numOfLeadingZeros){
            return number;
        }else{
          int j= numOfLeadingZeros - i;
          for (int k=0; k<j; k++){
              number="0"+number;
          }
         /* log.info("converted number is "+number);*/ 
        } 
        return number;
    }
    public  String trailingZeros(char[] number) {
        StringBuffer buff = new StringBuffer();
        boolean flag=false;
        System.out.println("trailingZeros=Before=(validDt)=" + number.length);
        for (int i = 0; i < number.length; i++) {
            char c = number[i];
            if (c != '0' || flag==true) {
                flag=true;
                buff.append(c);
            }
        }
        String trailingNumber = buff.toString() ;
        log.info("trailingNumber  " +trailingNumber);
        return trailingNumber;
    }
    public  String convertToLetterCase(String letter) {
        String tmpStr="",tmpChar="",preString="",postString="";
        int strLen=0,index=0;
        tmpStr = letter.toLowerCase();
        strLen = tmpStr.length();
        if (strLen > 0) {
            for (index = 0; index < strLen; index++) {
                if (index == 0) {
                    tmpChar = tmpStr.substring(0,1).toUpperCase();
                    postString = tmpStr.substring(1,strLen);
                    tmpStr = tmpChar + postString;
                }else {
                    tmpChar = tmpStr.substring(index, index+1);
                    if (tmpChar.equals(" ") && index < (strLen-1)) {
                        tmpChar = tmpStr.substring(index+1, index+2).toUpperCase();
                        preString = tmpStr.substring(0, index+1);
                        postString = tmpStr.substring(index+2,strLen);
                        tmpStr = preString + tmpChar + postString;
                }
             }
           }
        }
        return tmpStr;
    }
    public String getSearchPFID(String pfid){
    	String finalPFId="";
    	int startIndex=0;
		startIndex=getStringIndex(pfid);
		if(startIndex==0){
			if(pfid.length()==11){
				startIndex=5;
			}else {
				startIndex=-1;
			}
		}
	
		finalPFId=pfid.substring(startIndex+1,pfid.length());
		finalPFId=this.trailingZeros(finalPFId.toCharArray());
    	return finalPFId;
    }
    public int getStringIndex(String str) {
		int startIndex=0;
        if (str == null)
            return 0;
       
         for (int i = 0; i < str.length(); i++) {
        	 if (Character.isLetter(str.charAt(i)))
        		 startIndex=i;
        }
        
      return startIndex;
    }

    public String AddMonth(String date)throws Exception{
    	String datestring[]=date.split("-");
    	String day=datestring[0];
    	String month=datestring[1];
    	month=String.valueOf(Integer.parseInt(month)+1);
    	if(Integer.parseInt(month)+1<10){
    		month="0"+month;
    	}else{
    		month=month;
    	}
    	if(Integer.parseInt(day)>27){
    		day="01";
    	}else{
    		day=day;
    	}
    	String year=datestring[2];
    	
    	return this.converDBToAppFormat(day+"-"+month+"-"+year,"dd-MM-yyyy", "dd-MMM-yyyy");
    	
    }

    

    static String a1[]={"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
    static String a2[]={"","Ten","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
    static String a3[]={"","Eleven","Twelve","Thirteen","Forteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
    static String a4[]={"","Hundred","Thousand","Lakh","Crore"};

	public static String ConvertInWords(double d)
	{
		double t=0,n=0;
		String m="",S="";
		String InWords="";

		if (d == 0)
		{
			S = "Zero";
			InWords = S;
		}
		else
		{
			if (d < 0)
			{
				d = -d;
				m = "Minus";
			}
			
			t = (float)(d - (int)d) * 100;
			if (t != 0) 
				S = " Paise " + GetWords((int)t);

			n = (int)d;
			
			t = n%100;
			S = GetWords((int)t) + S;
			
			n = (int)(n/100);
			t = n%10;
			if (t != 0)
				S = GetWords((int)t) + " Hundred " + (S.trim().equals("") ? "" : "and " + S);
			
			n = (int)(n/10);
			t = n%100;
			if (t != 0)
				S = GetWords((int)t) + " Thousand " + S;
			
			n = (int)(n/100);
			t = n%100;
			if (t != 0)
				S = GetWords((int)t) + " Lakh " + S;
			
			n = (int)(n/100);
			t = n%100;
			if (t != 0)
				S = GetWords((int)t) + " Crores " + S;
			
			n = (int)(n/100);
			t = n%10;
			if (t != 0)
				S = GetWords((int)t) + " Hundred " + (S.indexOf("Crores") > 0 ? S : "Crores" + S);
			
			n = (int)(n/10);
			t = n%100;
			if (t != 0)
				S = GetWords((int)t) + " Thousand " + (S.indexOf("Crores") > 0? S : "Crores" + S);
			
			InWords = m.equals("")? m + " " + S : S ;
		}
		return(InWords);
	}

	public static String GetWords(int n)
	{
		
		String GetWords="";
		if (n%10 == 0)
		{
			if (n >= 10)
				GetWords = a2[(int)(n/10)].trim();
		}
		else
		{
			if (n < 10)
				GetWords = a1[n%10].trim();
			else
			{
				if (n < 20)
					GetWords = a3[n%10].trim();
				else
					GetWords = a2[(int)(n/10)].trim() + " " + a1[n%10].trim();
			}
		}
		return(GetWords);
	}

	
	public void createSheet(ArrayList finalDataList,String airport,WritableWorkbook workbook,ArrayList hdrsList) throws WriteException{
		  String data="";
		  log.info("list size is " +finalDataList.size());
		  ArrayList finalDtList=new ArrayList();
	       WritableSheet s = workbook.createSheet(airport, 0);
	       WritableFont wf = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
	       WritableCellFormat cf = new WritableCellFormat(wf);
		   cf.setWrap(true);
		  
		   EmpMasterBean formBean=new EmpMasterBean();
		   for(int i=0;i<hdrsList.size();i++){
			 
			   s.addCell(new Label( i,0,hdrsList.get(i).toString()));
		   }
		   WritableCellFormat  cellFormat;
		 
		   for(int k=0;k<finalDataList.size();k++){
			   int row=k;
			   log.info("inside for loop");
			   formBean=(EmpMasterBean)finalDataList.get(k);
			   		WritableCellFormat cf2 = new WritableCellFormat(NumberFormats.TEXT);
			   		//NumberCell fc1 = (NumberCell) Integer.parseInt(formBean.getCpfaccno());
			   	//	System.out.println(isInteger(formBean.getCpfaccno()));
			   		log.info("formBean.getCpfAcNo() "+formBean.getCpfAcNo());
			   		s.addCell(new Label (0,row,formBean.getCpfAcNo()));
			   		s.addCell(new Label (1,row,formBean.getEmpNumber()));
			   		s.addCell(new Label(2,row,formBean.getEmpName()));
					/*if(isInteger(formBean.getEmployeeNo())==true){
			   			s.addCell(new Number(0,row,Double.parseDouble(formBean.getEmployeeNo())));
			   		}else{
			   			s.addCell(new Label(2,row,formBean.getEmployeeNo()));
			   		}*/
					
					s.addCell(new Label(3,row,formBean.getDesegnation()));
					s.addCell(new Label(4,row,formBean.getEmoluments()));
					s.addCell(new Label(5,row,formBean.getEmployeePF()));
					s.addCell(new Label(6,row,formBean.getEmpSerialNo()));
					s.addCell(new Label(7,row,formBean.getPensionNumber()));
					s.addCell(new Label(8,row,formBean.getDateofBirth()));
					
				//	row++;
		   }
		
	}
	
	public ArrayList getForm6Region() {
		ArrayList hashmap = new ArrayList();
		hashmap.add( "CHQNAD");
		hashmap.add( "North Region");
		hashmap.add("East Region");
		hashmap.add( "West Region");
		hashmap.add( "South Region");
		hashmap.add( "North-East Region");
		hashmap.add( "RAUSAP");
		hashmap.add( "CHQIAD");
		return hashmap;
	}
	 public String getSearchPFID1(String pfid){
		 log.info("inetial pfid " +pfid);
	    	String finalPFId="";
	    	if(pfid.length()>=5){
	    	finalPFId=pfid.substring(pfid.length()-5, pfid.length());
			finalPFId=this.trailingZeros(finalPFId.toCharArray());
	    	}else{
	    		finalPFId=pfid;
	    	}
			log.info("finai pfId" +finalPFId);
	    	return finalPFId;
	    }
		public String getRegionLbls(String region) {
			String regionLabel="";
			  if(region.toLowerCase().equals("eastern region")){
		        	regionLabel="ER";
		        }else if(region.toLowerCase().equals("western region")){
		        	regionLabel="WR";
		        }else if(region.toLowerCase().equals("southern region")){
		        	regionLabel="SR";
		        }else if(region.toLowerCase().equals("north-east region")){
		        	regionLabel="NER";
		        }else if(region.toLowerCase().equals("northern region")){
		        	regionLabel="NR";
		        }else if(region.toLowerCase().equals("rausap")){
		        	regionLabel="RAU";
		        }else if(region.toLowerCase().equals("chqnad")){
		        	regionLabel="NAD";
		        }else if(region.toLowerCase().equals("chqiad")){
		        	regionLabel="IAD";
		        }
			return regionLabel;
		}
		public StringBuffer wrapTextArea(String str2, int j) {
		StringBuffer textarea = new StringBuffer("");
		String str = str2;
		StringBuffer temp = new StringBuffer();
		int k = j;

		int i;
		if (str != null) {
			if (str.length() > k) {

				while (str.length() > k) {

					for (i = 0; i <= k - 1; i++) {
						if (str.charAt(i) == '\n')
							break;
					}

					if (i == k) {
						temp = temp.append(str.substring(0, k));
						if (str.charAt(k) != ' ') {
							temp = temp.append("-");
						}
						temp.append("<BR>");
						str = str.substring(k, str.length());
					} else {
						temp = temp.append(str.substring(0, i + 1));
						str = str.substring(i + 1, str.length());
					}
				}

				temp = temp.append(str);
				textarea = textarea.append(temp);
			} else {
				textarea = textarea.append(str);
			}
		}

		

		return textarea;
	}
	public ArrayList getYearList(){
		ArrayList yearList=new ArrayList();
		String currentYear="",fromYear="";
		int noOfYear=0,nextYear=0;
		currentYear=this.getCurrentDate("yyyy");
		fromYear=Constants.APPLICATION_PENSION_FROM_YEAR;
		noOfYear=Integer.parseInt(currentYear)-Integer.parseInt(fromYear);
		log.info("currentYear"+currentYear+"fromYear"+fromYear+"noOfYear"+noOfYear);
		for(int i=0;i<=noOfYear;i++){
			nextYear=Integer.parseInt(fromYear)+i;
			log.info("nextYear===="+nextYear);
			yearList.add(new Integer(nextYear));
		}
		
		return yearList;
	}
	public String capitalizeFirstLettersTokenizer ( String s ) {  
		final StringTokenizer st = new StringTokenizer( s, " ", true ); 
		final StringBuffer sb = new StringBuffer();  
		while ( st.hasMoreTokens() ) 
		{        String token = st.nextToken();   			 
			token = token.substring(0,1).toUpperCase() + token.substring(1,token.length()).toLowerCase(); 
			log.info("=======token======="+token);
			sb.append( token );  
		}          
		return sb.toString(); 
}
	
	public String getCurrency(double num)
	{
		DecimalFormat df=new DecimalFormat("00");
		DecimalFormat df3=new DecimalFormat("000");
		String str="0";
		String sign="";
		if(num<0)
		{
			sign="-";
			num=Math.abs(num);
		}
		
		if(num>=0 && num<=999){
			str=String.valueOf((int)num);
		}else
		{
			str = String.valueOf(num);  
			long b = (long) (num%1000); 
			String bb=df3.format(b);
			//System.out.println(str);
			if(str.indexOf('E')!=-1){		
				if(str.substring(str.indexOf('.')+1,str.indexOf('E')).length()>=Integer.parseInt(str.substring(str.indexOf('E')+1)))
				{
					str = bb+"."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)));
					str=str.substring(0,str.length()-1);
				}
				else	
					str = bb;				
			}
			else 
			{
				str=bb+"."+str.substring(str.indexOf('.')+1) ;   
				str=str.substring(0,str.indexOf('.'));
			}
			long a = (long)num/1000;
			int i=1;
			while(a!=0) {	
				if(i<=3){
					b = (long)a%100;
					a = (long)a/100;
					
					if(b<=9&&a==0)
						str = b+","+str;
					else
						str = df.format(b)+","+str;         
				}else{				
					str=a+str;
					a=0;
				}
				i++;            
			}
		}
		return (sign+str);
		
	}
	public String getDecimalCurrency(double num)
	{
		DecimalFormat df=new DecimalFormat("00");
		DecimalFormat df1=new DecimalFormat("0.00");
		DecimalFormat df3=new DecimalFormat("000");
		String str="0";
		String sign="";
		if(num<0)
		{
			sign="-";
			num=Math.abs(num);
		}
		
		if(num>=0 && num<=999)
		{
			if((String.valueOf(num).indexOf("."))==-1)
			{
				str=String.valueOf((int)num);
				str=str+".00";
			}
			else
			{
				str=df1.format(num);
			}
		}
		else
		{
			str = String.valueOf(num);  
			long b = (long) (num%1000); 
			String bb=df3.format(b);
			//System.out.println(str);
			if(str.indexOf('E')!=-1)
			{		
				if(str.substring(str.indexOf('.')+1,str.indexOf('E')).length()>=Integer.parseInt(str.substring(str.indexOf('E')+1)))
				{
					//str = bb+"."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)));
					String decimals="";
					if(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1))))*1000 <995)
						decimals=df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1,str.indexOf('E')).substring(Integer.parseInt(str.substring(str.indexOf('E')+1)))));
					else
					{
						decimals="0.00";
						bb=String.valueOf(df3.format(Integer.parseInt(bb)+1));
					}				
					str = bb+"."+decimals.substring(2,decimals.length());
					//str=str.substring(0,str.length()-1);
				}
				else	
					str = bb+".00";				
			}
			else
			{
				//System.out.println("..asasas...."+df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))));
				if(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))*1000>=995)
				{
					bb=df3.format(Integer.parseInt(bb)+1);
					//System.out.println("......."+bb);
					str=bb+".00";
				}
				else
					str=bb+"."+df1.format(Double.parseDouble("0."+str.substring(str.indexOf('.')+1))).substring(2,4) ;   
				//System.out.println("..............."+str);
				//str=str.substring(0,str.indexOf('.'));
			}
			long a = (long)num/1000;
			int i=1;
			while(a!=0) 
			{	
				if(i<=3)
				{
					b = (long)a%100;
					a = (long)a/100;
					if(b<=9&&a==0)
						str = b+","+str;
					else
						str = df.format(b)+","+str;         
				}
				else
				{				
					//System.out.println(".....a..."+a);
					str=a+str;
					a=0;
				}
				i++;            
			}
		}
		return (sign+str);
	}
	public String getRegion ( String reg ) {  
		String region="";
		if(reg.equals("CHQNAD")){
			region="CHQNAD";
		}else if(reg.equals("East Region")){
			region="Eastern Region";
		}else if(reg.equals("South Region")){
			region="Southern Region";
		}else if(reg.equals("RAUSAP")){
			region="RAUSAP";
		}else if(reg.equals("North-East Region")){
			region="North-East Region";
		}else if(reg.equals("CHQIAD")){
			region="CHQIAD";
		}else if(reg.equals("North Region")){
			region="Northern Region";
		}else if(reg.equals("West Region")){
			region="Western Region";
		}					
		return region;
	}
	public String getRegionHeadQuarters ( String reg ) {  
		String regionHQ="";
		if(reg.equals("CHQNAD")){
			regionHQ="CHQNAD";
		}else if(reg.equals("Eastern Region")){
			regionHQ="Kolkata";
		}else if(reg.equals("Southern Region")){
			regionHQ="Chennai";
		}else if(reg.equals("RAUSAP")){
			regionHQ="RAUSAP";
		}else if(reg.equals("North-East Region")){
				regionHQ="Guwahati";
		}else if(reg.equals("CHQIAD")){
			regionHQ="CHQIAD";
		}else if(reg.equals("Northern Region")){
			regionHQ="New Delhi";
		}else if(reg.equals("Western Region")){
			regionHQ="Mumbai";
		}					
		return regionHQ;
	}
	public String getYN ( String status ) {  
		String statusDesc="";
		if(status.equals("Y")){
			statusDesc="Yes";
		}else if(status.equals("N")){
			statusDesc="No";
		}		
		return statusDesc;
	}
	public ArrayList getAirportList(String region) {
		Connection con = null;
		ArrayList airportList = new ArrayList();

		ResultSet rs = null;
		String unitName = "", unitCode = "";

		try {
			con = commonDB.getConnection();
			Statement st = con.createStatement();
			String sql = "select * from employee_unit_master where region='"+region+"' order by region";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				AdvanceBasicBean bean = new AdvanceBasicBean();
				unitName = (String) rs.getString("unitname").toString().trim();
			
				if (!unitName.equals("")) {
					bean.setStation(unitName);
				} else {
					bean.setStation("");
				}
			

				airportList.add(bean);
				// bean=null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}
		return airportList;

	}
	//By Radha on 06-Feb-2012 for seperating region & unit wise
public String getAirportsByProfile(String loginProfile,String loginUnitCode,String loginRegion) {
		
		StringBuffer sb=new StringBuffer();
		if(!(loginProfile.equals("U") || loginProfile.equals("M"))){
			
			if(loginProfile.equals("R")){
					if(!loginRegion.equals("CHQIAD")){
				loginUnitCode="-";
			}
			}else{
				loginUnitCode="-";
				loginRegion="-";
			}
		}
	
		sb.append(loginUnitCode);
		sb.append(",");
		sb.append(loginRegion);
		
		return sb.toString();
	}
public boolean compareTwoDates(String date1,String date2){
	Date compareWthDt=new Date();
	Date comparedDt=new Date();
	boolean finalDateFlag=false;
	SimpleDateFormat dateFormat=new SimpleDateFormat("MMM-yyyy");
	try {
		compareWthDt=dateFormat.parse(date1);
		comparedDt=dateFormat.parse(date2);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finalDateFlag=comparedDt.after(compareWthDt);
		
	
	return finalDateFlag;
}
public Map getFormsList() {
	Map map = new LinkedHashMap();
	map.put("AAIEPF-3", "AAIEPF-3 (Monthly CPF Recovery)");
	map.put("AAIEPF-1", "AAIEPF-1 (Opening Balance)");
	map.put("AAIEPF-2", "AAIEPF-2 (Adjustment in OpeningBalance)");
	map.put("AAIEPF-4", "AAIEPF-4 (CPF Received From Other Org..)");
	map.put("AAIEPF-8", "AAIEPF-8 (Advances/PFW/Final	Settlement)");
	map.put("OTHER", "OTHER");
	
	return map;

}
public String getMonthYear(String month,String year){
	 String fullMonthName = "",frmMonthYear="",disMonthYear="",displayDate="";
		if (!month.equals("00")) {
			frmMonthYear = "%" + "-" + month + "-" + year;
			disMonthYear = month + "-" + year;
			try {
				displayDate = this.converDBToAppFormat(disMonthYear,
						"MM-yyyy", "MMM-yyyy");
				
				} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				log.printStackTrace(e);
			}
		}
		return displayDate;
	 
}
public int GetDaysInMonth(int month, int year){
	 if (month < 1 || month > 12) {
		try {
			throw new InvalidDataException("month" + month
					+ "month must be between 1 and 12");
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			log.printStackTrace(e);
		}
	}
	if (1 == month || 3 == month || 5 == month || 7 == month || 8 == month
			|| 10 == month || 12 == month) {
		return 31;
	} else if (2 == month) {
		// Check for leap year
		if (0 == (year % 4)) {
			// If date is divisible by 400, it's a leap year.
			// Otherwise, if it's divisible by 100 it's not.
			if (0 == (year % 400)) {
				return 29;
			} else if (0 == (year % 100)) {
				return 28;
			}

			// Divisible by 4 but not by 100 or 400
			// so it leaps
			return 29;
		}
		// Not a leap year
		return 28;
	}
	return 30;
}

public Map getPFCardNoofMonthsList() {	
	Map map = new LinkedHashMap();
	map.put("1", "one Month");
	map.put("0.5", "half Month");	
	map.put("3", "3 Days");
	map.put("4", "4 Days");
	map.put("5", "5 Days");
	map.put("6", "6 Days");
	map.put("7", "7 Days");
	map.put("8", "8 Days");
	map.put("9", "9 Days");
	map.put("10", "10 Days");
	map.put("11", "11 Days");
	map.put("12", "12 Days");
	map.put("13", "13 Days");
	map.put("14", "14 Days");
	map.put("15", "15 Days");
	map.put("16", "16 Days");
	map.put("17", "17 Days");
	map.put("18", "18 Days");
	map.put("19", "19 Days");
	map.put("20", "20 Days");
	map.put("21", "21 Days");
	map.put("22", "22 Days");
	map.put("23", "23 Days");
	map.put("24", "24 Days");
	map.put("25", "25 Days");
	map.put("26", "26 Days");
	map.put("27", "27 Days");
	map.put("28", "28 Days");
	map.put("2", "two Months");
	map.put("90", "three Months");
	map.put("120", "four Months");
	map.put("150", "five Months");
	map.put("180", "six Months");
	map.put("210", "Seven Months");
	map.put("240", "Eight Months");
	map.put("270", "Nine Months");
	map.put("300", "Ten Months");
	
	return map;

}


	public Map getUsersList() {
		Connection con = null;
		Map userMap = new LinkedHashMap();
		Statement st = null;
		ResultSet rs = null;
		String userName = "", userId = "";

		try {
			con = commonDB.getConnection();
			st = con.createStatement();
			String sql = "SELECT userid,username,profile,usertype FROM epis_user WHERE PROFILE IN ('U','C','R') OR  USERTYPE='N'";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				 			 
				if (rs.getString("userid") != null) {
					userId =(String) rs.getString("userid").toString().trim();				 
				}else {
					userId="";
				}
				if (rs.getString("username") != null) {
					userName =(String) rs.getString("username").toString()
					.trim();
				}else{
					userName="";
				}
				 
				userMap.put(userId,userName);
				 
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error" + e.getMessage());

		}finally {
	        commonDB.closeConnection(rs, st, con);
	    }
		return userMap;

	}
	 //For Loading stations for Placeofposting
	 public ArrayList loadAllStations() {
			ArrayList stationList = new ArrayList();
			String station="";
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			try {
				con = commonDB.getConnection();
				st = con.createStatement();

				String query = "select distinct(UNITNAME) from employee_unit_master where unitname is not null";
				log.info("CommonUtil::loadAllStations()==" + query);
				rs = st.executeQuery(query);
				while (rs.next()) {
					 
					station = rs.getString("UNITNAME");					 
					stationList.add(station);
				}

			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>" + e.getMessage());
			} finally {
				DBUtility.closeConnection(rs, st, con);
			}
			return stationList;
		}
//	 By Radha On 13-MAr-2012 AccountType changed for Loans & Advances 	 
//	 By Radha On 25-Jan-2012 for Loading staions based on Profile(SAU/RAU) basis 
		public String getAccountType(String region,String station) {
			ArrayList stationList = new ArrayList();
			AdvanceBasicBean advanceBasicBean = null;
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			String  accountype="";
			try {
				con = DBUtility.getConnection();
				st = con.createStatement();
				  
				String query = "select advacctype from employee_unit_master where region='"
						+ region + "'   and  UPPER(unitname) ='"+station.toUpperCase().trim()+"'";
				
				log.info("CommonUtil::getAccountType()==query====" + query);
				rs = st.executeQuery(query);
				if (rs.next()) {
					accountype = rs.getString("accounttype");
					 
				}

			} catch (Exception e) {
				log.info("<<<<<<<<<< Exception  >>>>>>>>>>>>" + e.getMessage());
			} finally {
				DBUtility.closeConnection(rs, st, con);
			}
			return accountype;
		}
		public static double getLeastValue(double v1,double v2){
			double hval = 0.0;
			if(v1>v2)
				hval = v2;
			else
				hval = v1;
			return hval;
		}
		public static int returnIndexVal(String str){
			int indx = 0;
			if(str.lastIndexOf("Exception:")!=-1)
				indx = str.lastIndexOf("Exception:")+10;
			return indx;
		}

}
