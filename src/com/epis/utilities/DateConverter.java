/**
 * 
 */
package  com.epis.utilities;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * @author ravikumark
 *
 */
public class DateConverter {

	/**
	 * 
	 */
	public DateConverter() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	Note : Overloaded functions are prefixed with "-" in the following list
	Functions in this class :
		-public String getServerDate() throws Exception
		-public String getServerDate(String datePicture) throws Exception
		public String getServerDateTime(boolean withSeconds) throws Exception
		-public static String addDateField(String inputDateStr, int field, int value) throws Exception  
		-public static String addDateField(String inputDateStr, int field, int value, boolean withTime) throws Exception  
		public static String utcIst(String inputDateStr) throws Exception
		public static String istUtc(String inputDateStr) throws Exception
		public String addTimeField(String inputTimeStr, int field, int value) throws Exception
		public String formatTime(String timeStr) throws Exception
		public int timeDiff(String time1, String time2) throws Exception
		public int dateTimeDiff(String earlierdate, String laterdate) throws Exception
		public String convertMinutes(int Min) throws Exception
		public String convertDispMinutes(int Min) throws Exception
		public String format4DigitTime(String timeStr) throws Exception
		public Date getDate(String dateStr, String token) throws Exception
		public String convertOracleDate(String oracleDt) throws Exception
		public String convertMonth(String dateStr, String token) throws Exception
	*/
		//getServerDate with 'DD/Mon/YYYY' format
		public String getServerDate(java.sql.Connection con) throws Exception
		{
			String dateStr = "";
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select to_char(sysdate,'DD/Mon/YYYY') from dual");
			if(rs.next())
			{
				 dateStr=rs.getString(1);
			}
			rs.close();
			st.close();

			return(dateStr);
		}

		//getServerDate in requested format. Example: YYYY-Mon-DD, DD-MM-YY etc.
		public String getServerDate(String datePicture, java.sql.Connection con) throws Exception
		{
			String dateStr = "";
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select to_char(sysdate, '" + datePicture +"') from dual");
			if(rs.next())
			{
				 dateStr=rs.getString(1);
			}
			rs.close();
			st.close();

			return(dateStr);
		}

		//getServerDateTime
		public String getServerDateTime(boolean withSeconds, java.sql.Connection con) throws Exception
		{
			String dateStr = "";
			ResultSet rs = null;
			java.sql.Statement st = con.createStatement();
			if(withSeconds)
				rs = st.executeQuery("Select to_char(sysdate,'DD/Mon/YYYY HH:MI:SS') from dual");
			else
				rs = st.executeQuery("Select to_char(sysdate,'DD/Mon/YYYY HH:MI') from dual");
			if(rs.next())
			{
				dateStr = rs.getString(1);
			}
			rs.close();
			st.close();

			return(dateStr);
		}

		public String getServerTime24Hour(java.sql.Connection con) throws Exception
		{
			String dateStr = "";
			ResultSet rs = null;
			java.sql.Statement st = con.createStatement();
			rs = st.executeQuery("Select to_char(sysdate,'HH24MI') from dual");
			if(rs.next())
			{
				dateStr = rs.getString(1);
			}
			rs.close();
			st.close();

			return(dateStr);
		}

		public static String addDateField(String inputDateStr, int field, int value) throws Exception
		{
			/* Function to accept a date as a string and add the required amount to the specified field.
			The input date string MUST be of the form  dd/MMM/yyyy HH:mm:ss
			The field required to be added must be supplied in the form Calendar.<field_name>
			This value can be found in the Calendar or the GregorianCalendar of the Java API docs.
			The "value" parameter is an int, and can be either positive or negative.
			The output date string will be of the form "DD/Mon/YYYY HH:MM:SS"
			EXAMPLES :	addDateField("28/Feb/2001 20:30:00",java.util.Calendar.DATE,1)
						addDateField("28/Feb/2001 20:30:00",java.util.Calendar.HOUR,1)	*/
			return addDateField(inputDateStr, field, value, true);
		}

		public static String addDateField(String inputDateStr, int field, int value, boolean withTime) throws Exception
		{
			/*	This is an overloaded method, similar to the above
				The input date string MUST be of the form  dd/MMM/yyyy HH:mm:ss
				The field required to be added must be supplied in the form Calendar.<field_name>
				The "value" parameter is an int, and can be either positive or negative.
				The return value will be with or without time,
				depending on the whether the withTime parameter is true or false
			*/
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
			java.util.Date dateObj = sdf.parse(inputDateStr);
			java.util.Calendar calendar = new java.util.GregorianCalendar();
			calendar.setTime(dateObj);
			calendar.add(field, value);
			String day = "";
			day = (calendar.get(Calendar.DATE) > 9 ? ("" + calendar.get(Calendar.DATE)) : ("0" + calendar.get(Calendar.DATE)));
			String monthArr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			String month = monthArr[calendar.get(Calendar.MONTH)];
			String year = "";
			year = (calendar.get(Calendar.YEAR) > 9 ? ("" + calendar.get(Calendar.YEAR)) : ("0" + calendar.get(Calendar.YEAR)));
			String hours = "";
			hours = (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? ("" + calendar.get(Calendar.HOUR_OF_DAY)) : ("0" + calendar.get(Calendar.HOUR_OF_DAY)));
			String minutes = "";
			minutes = (calendar.get(Calendar.MINUTE) > 9 ? ("" + calendar.get(Calendar.MINUTE)) : ("0" + calendar.get(Calendar.MINUTE)));
			String seconds = "";
			seconds = (calendar.get(Calendar.SECOND) > 9 ? ("" + calendar.get(Calendar.SECOND)) : ("0" + calendar.get(Calendar.SECOND)));
			if(withTime == true)
				return (day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds);
			else
				return (day + "/" + month + "/" + year);
		}

		public static String utcIst(String inputDateStr) throws Exception
		{
			// Converts input date & time into IST, assuming that input is UTC
			// Input Format : dd/MMM/yyyy HH:mm:ss		Output Format : dd/MMM/yyyy HH:mm:ss
			return addDateField(inputDateStr, Calendar.MINUTE, 330, true);
		}

		public static String istUtc(String inputDateStr) throws Exception
		{
			// Converts input date & time into UTC, assuming that input is IST
			// Input Format : dd/MMM/yyyy HH:mm:ss		Output Format : dd/MMM/yyyy HH:mm:ss
			return addDateField(inputDateStr, Calendar.MINUTE, -330, true);
		}

		public String addTimeField(String inputTimeStr, int field, int value) throws Exception
		{
			/*
			You can call this function as in any of the following examples:
			Ex: addTimeField("1120", Calendar.MINUTE, 120) OR addTimeField("1120", Calendar.HOUR, 2)
			Ex2: addTimeField("11:20", Calendar.MINUTE, 120) OR addTimeField("11:20", Calendar.HOUR, 2)
			Ex3: addTimeField("11:20:20", Calendar.MINUTE, 120) OR addTimeField("11:20:20", Calendar.HOUR, 2)
			*/
			String retTime = "";
			retTime = addDateField("01/Jan/2001 " + formatTime(inputTimeStr), field, value, true);
			retTime = retTime.substring(retTime.indexOf(" ")+1, retTime.length());
			// The above statement will retrieve the time portion of the final date string
			return retTime;
		}

		public String formatTime(String timeStr) throws Exception
		{
		// Function that Always returns a time string of the format HH:MM:SS.
		// The input time string can be of the following formats:
		//						HHMM or HHMMSS or HH:MM or HH:MM:SS
			String retStr = "";
			//If the input is sent as HHMM or HHMMSS (without :) then the following:
			if(timeStr.indexOf(":") == -1)
			{
				//If the input is sent as HHMM (Ex: 1120) then the following IF will work:
				if(timeStr.length() == 4)
					retStr = timeStr.substring(0,2) + ":" + timeStr.substring(2,4) + ":00";
				//If the input is sent as HHMMSS (Ex: 112030) then the following IF will work:
				if(timeStr.length() == 6)
					retStr = timeStr.substring(0,2) + ":" + timeStr.substring(2,4) + ":" + timeStr.substring(4,6);
			}
			else
			{
				if(timeStr.length() == 5)
					retStr = timeStr + ":00";	// Assuming that timeStr is like HH:MM (Ex: 10:22)
				else
					retStr = timeStr;	// Assuming that timeStr is like HH:MM:SS (Ex: 10:22:33)
			}
			return retStr;
		}

		public int timeDiff(String time1, String time2) throws Exception
		{
			// Input times Must be of the 4 digit time format. Ex: 0102, 1122 etc.
			// Assumes that the second time is always greater than the first.
			// In case it is numerically lesser, then it must be in the next day.
			java.util.Date d1 = null;
			java.util.Date d2 = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy kk:mm:ss");
			d1 = sdf.parse("01/Jan/1970 " + format4DigitTime(time1));
			if(Integer.parseInt(time2) >= Integer.parseInt(time1))
				d2 = sdf.parse("01/Jan/1970 " + format4DigitTime(time2));
			else
				d2 = sdf.parse("02/Jan/1970 " + format4DigitTime(time2));
			return (int) ( (d2.getTime() - d1.getTime()) / ( 1000 * 60 ) );
		}

		public int dateTimeDiff(String earlierdate, String laterdate) throws Exception
		{
				// Input dates should be of format dd/MMM/yyyy kk:mm:ss
				// returns difference in minutes .
				java.util.Date d1 = null;
				java.util.Date d2 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy kk:mm:ss");
				d1 = sdf.parse(earlierdate);
				d2 = sdf.parse(laterdate);
				return (int) ( (d2.getTime() - d1.getTime()) / ( 1000 * 60 ) );
		}

		public String convertMinutes(int Min) throws Exception
		{
			String Hrs=(Min/60)+"";
			String Mn=(Min%60)+"";
			if (Hrs.length()==1) Hrs="0"+Hrs;
			if (Mn.length()==1) Mn="0"+Mn;
			return Hrs+Mn;
		}

		public String convertDispMinutes(int Min) throws Exception
		{
			String Hrs=(Min/60)+"";
			String Mn=(Min%60)+"";
			if (Hrs.length()==1) Hrs="0"+Hrs;
			if (Mn.length()==1) Mn="0"+Mn;
			return Hrs + ":" + Mn;
		}

		public String format4DigitTime(String timeStr) throws Exception
		{
			return timeStr.substring(0,2) + ":" + timeStr.substring(2,4) + ":00";
		}

		public java.util.Date getDate(String dateStr, String token) throws Exception
		{
		/*
			This function will take a date in the form of a string.
			The date passed is assummed to be of the form DD<token>Mon<token>YYYY.
			It will return a Date object, that can be used for date calculations and manipulation.
			The function assumes that the token is "/", if no token is passed.
		*/
			if (dateStr == null || dateStr.equals(""))
				return null;

			java.util.Date dateObject = null;
			if (token == null || token.equals(""))
				token = "/";
			StringTokenizer st = new StringTokenizer(dateStr, token);
			String dateArr[]=new String[3];
			String monthArr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			int tokenCount=0;
			while (st.hasMoreTokens()) {
				dateArr[tokenCount]=st.nextToken();
				tokenCount++;
			}
			int month=0;
			for(int i=0;i<monthArr.length;i++){
				if(dateArr[1].toUpperCase().equals(monthArr[i].toUpperCase())){
					month=i;
					break;
				}
			}
			dateObject = new java.util.Date(new Integer(dateArr[2]).intValue()-1900, month, new Integer(dateArr[0]).intValue());
			return dateObject;
		}

		public String convertOracleDate(String oracleDt) throws Exception
		{
			/*
				This function takes a date string and returns it in a different format.
				This is useful where we want to convert a date that is retrieved from oracle using 
				resultset.getDate("dateField") function.  That will be of the form YYYY-MON-DD.
				This function will convert the oracle date to the format "DD/Mon/YYYY", since that
				is the format being used to display dates on AIMS screens.
			*/
			String returnDt= new String("");

			if (oracleDt == null || oracleDt.equals(""))
				return "";

			StringTokenizer st = new StringTokenizer(oracleDt, "-");
			String dateArr[]=new String[3];
			String monthArr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			int tokenCount=0;
			while (st.hasMoreTokens()) {
				dateArr[tokenCount]=st.nextToken();
				tokenCount++;
			}
			returnDt=dateArr[2]+"/"+monthArr[Integer.parseInt(dateArr[1])-1]+"/"+dateArr[0];

			return returnDt;
		}

		public String convertMonth(String dateStr, String token) throws Exception
		{
			/*
				This function will take a date string and return it in a different format.
				The date passed to it is assumed to be of the format "DD<token>MM<token>YYYY",
				or "DD<token>MON<token>YYYY".
				If the date passed is of the form "DD<token>MM<token>YYYY", it will be converted 
				to "DD/MON/YYYY" and returned.  If not, it will be returned with the token as "/".
			*/

			if (dateStr == null || dateStr.equals(""))
				return "";

			String monDateStr = "";
			StringTokenizer st = new StringTokenizer(dateStr, token);
			String dateArr[]=new String[3];
			String monthArr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
			int tokenCount=0;
			while (st.hasMoreTokens()) {
				dateArr[tokenCount]=st.nextToken();
				tokenCount++;
			}
			try {
				monDateStr = dateArr[0] + "/" + monthArr[Integer.parseInt(dateArr[1])-1] + "/" + dateArr[2];
			}
			catch(NumberFormatException nfe) {
				monDateStr = dateArr[0] + "/" + dateArr[1] + "/" + dateArr[2];
			}
			return monDateStr;
		}		// End of convertMonth function

		public static String getNextFinYear(String finYear){
			int year = Integer.parseInt(finYear.substring(0,finYear.indexOf("-")))+1;
			int nexYear = Integer.parseInt(finYear.substring(finYear.indexOf("-")+1))+1;
			return year+"-"+nexYear;
		}
}
