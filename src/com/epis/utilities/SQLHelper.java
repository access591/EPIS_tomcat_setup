package  com.epis.utilities;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
public class SQLHelper
{
	
	Log log = new Log(SQLHelper.class);
	public String getDescription(String tableName, String returnFieldName, String keyFieldName, String keyFieldValue, java.sql.Connection con) throws Exception
	{
	// Function which accepts a key field name and its value, and returns the corresponding description.
	// Since the funcion is generalized, it also takes the table name 
	// and the name of the description field. If no row is found, it will return "" (empty string)

		String query = "";
		String description = "";
		query = "select " + returnFieldName + " from " + tableName + " where " + keyFieldName + " ='" + keyFieldValue + "'";
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			description = rs.getString(1);
		}
		rs.close();
		st.close();

		return(description);
	}
	public String getDescriptionMulti(String tableName, String returnFieldName, String cond, java.sql.Connection con) throws Exception
	{
	
		String query = "";
		String description = "";
		query = "select " + returnFieldName + " from " + tableName + " where "+cond;
		log.info("query"+query);
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next())
		{
			description = description+" "+rs.getString(1);
			log.info("description"+description+" ts "+rs.getString(1));
		}
		rs.close();
		st.close();

		return(description);
	}
	public String getMaxValue(String tableName, String keyFieldName, String keyFieldValue, String mcode, java.sql.Connection con)  throws Exception
	{
	//  Function which takes a table name, a field name and its field value
	//  and returns the max value of the fieldname supplied in the last parameter

		String query = "";
		String maxValue="";
		query="select " + "max(" + mcode + ") from " + tableName + " where " + keyFieldName + " like '%" + keyFieldValue + "%'";
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			maxValue=rs.getString(1);
		}
		rs.close();
		st.close();

		return maxValue;
	}

	public String escapeSingleQuotes(String strToEscape)
	{
		if(strToEscape.indexOf('\'') == -1)
			return strToEscape;
		StringBuffer strBuf = new StringBuffer(strToEscape);
		int j = 0;
		int i = 0;
		while(i <= strToEscape.length())
		{
			i = strToEscape.indexOf('\'', i);
			if (i == -1)
				break;
			strBuf = strBuf.insert(i+j, '\'');
			i++;
			j++;
		}
		strToEscape = strBuf.toString();
		return strToEscape;
	}
	
	public String replaceSingleQuotes(String strToEscape)
	{
		if(strToEscape.indexOf('\'') == -1)
			return strToEscape;
		StringBuffer strBuf = new StringBuffer(strToEscape);
		int j = 0;
		int i = 0;
		while(i <= strToEscape.length())
		{
			i = strToEscape.indexOf('\'', i);
			if (i == -1)
				break;
			strBuf = strBuf.insert(i+j, '\\');
			i++;
			j++;
		}
		strToEscape = strBuf.toString();
		return strToEscape;
	}

	public String left(String str,int len)
	{
		String rtnstr="";

		if (str==null || str.equals("")) return(rtnstr);

		if (str.length()<=len)
		{	
			rtnstr=str;
		}
		else
		{
			rtnstr=str.substring(0,len);
		}
		return(rtnstr);
	}

	public String right(String str,int len)
	{
		String rtnstr="";

		if (str==null || str.equals("")) return(rtnstr);

		if (str.length()<=len)
		{	
			rtnstr=str;
		}
		else
		{
			rtnstr=str.substring(str.length()-len);
		}
		return(rtnstr);
	}

	public String displayDesc(String str,int len)
	{
		String rtnstr="";

		if (str==null || str.equals("")) return(rtnstr);

		rtnstr=left(str,len);

		if (str.length()>len)			
			rtnstr=rtnstr+" "+"<a class=tbl href='javascript:void(0)' onMouseOver = \"overlib('"+escapeCarriageReturn(escapeNewline(replaceSingleQuotes(str)))+"')\" onMouseOut=\"nd()\">...</a>";
		else
			rtnstr = str;
		
		return(rtnstr);
	}

	public String updateUserStatus(String accessCd, String userId, java.sql.Connection con) throws Exception{
		java.sql.Statement stmt = con.createStatement();
		java.sql.CallableStatement cs = con.prepareCall("{call check_access(?,?,?)}");
		cs.setString(1,userId);
		cs.setString(2,accessCd);
		cs.registerOutParameter(3,java.sql.Types.VARCHAR);

		cs.execute();

		String status = cs.getString(3);
		if(cs!=null)
			cs.close();
		

		return status;



		/*
		stmt.executeUpdate("Insert into User_Status(Srno,Access_Cd,Usrid,Access_Date) values (get_nextcode('User_Status','Srno',10),'"+accessCd+"','"+userId+"',sysdate)");

		if(stmt!=null)
			stmt.close();			*/
	}
	public StringBuffer wrapTextArea2(String str2,int j)
	{
    StringBuffer textarea=new StringBuffer("");
    String str=str2;
	StringBuffer temp=new StringBuffer();
	int k=j;

	int i;
	if(str!=null)
	{
	if(str.length()>k)
	  {

		while(str.length()>k)
		  {

			for(i=0;i<=k-1;i++)
				 {
					 if(str.charAt(i)=='\n')
					  break;
				 }

				 if(i==k)
				 {
					temp=temp.append(str.substring(0,k));
					if(str.charAt(k)!=' ')
					{
					temp=temp.append("-");
					}
					temp.append("<BR>");
					str=str.substring(k,str.length());
				 }
				 else
				 {
					   temp=temp.append(str.substring(0,i+1));
			                   str=str.substring(i+1,str.length());
				 }
		  }
	
			temp=temp.append(str);
  			textarea=textarea.append(temp);
	   }else{
			textarea=textarea.append(str);
	       }
	}

		textarea=textarea.append("");
   
	    return textarea;
	}
	public String getAccess(String keyFieldName, String keyFieldValue,String keyFieldName1, String keyFieldValue1,  java.sql.Connection con) throws Exception
	{
		
		String status = updateUserStatus(keyFieldValue1,keyFieldValue,con);
		/*String query = "";
		String status = "";
		String airportcd="";
		query = "select AIRPORT_CD,USER_STATUS from am_user_mt where " + keyFieldName + " ='" +keyFieldValue+ "' and USER_STATUS='E'";
		
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next())
		{
			status = "Y";
			airportcd=rs.getString("AIRPORT_CD");
			rs.close();
		}
		else
		{
			status = "N";
			rs.close();
			return(status);
		}
		query="";
		query = "select * from am_user_access_mt where " + keyFieldName + " ='" +keyFieldValue+ "' and " + keyFieldName1 + " ='" +keyFieldValue1+ "'";

		rs = st.executeQuery(query);
		if(rs.next())
		{
/*			if (!strAirport.equals(airportcd) && !keyFieldValue1.substring(0,2).equals("TS"))
			{
				status = "N";
				rs.close();
				st.close();
				return(status);
			}
			else
			{*/
				//status= "Y";
//			}
			
		/*}
		else
		{
			status = "N";
		}
		rs.close();
		st.close();*/
		return(status);
	}

	public StringBuffer wrapTextArea(String str2,int j)
	{
    StringBuffer textarea=new StringBuffer("<pre class='DataLeft'>");
    String str=str2;
	StringBuffer temp=new StringBuffer();
	int k=j;

	int i;
	if(str!=null)
	{
	if(str.length()>k)
	  {

		while(str.length()>k)
		  {

			for(i=0;i<=k-1;i++)
				 {
					 if(str.charAt(i)=='\n')
					  break;
				 }

				 if(i==k)
				 {
					temp=temp.append(str.substring(0,k));
					if(str.charAt(k)!=' ')
					{
					temp=temp.append("-");
					}
					temp.append("<BR>");
					str=str.substring(k,str.length());
				 }
				 else
				 {
					   temp=temp.append(str.substring(0,i+1));
			                   str=str.substring(i+1,str.length());
				 }
		  }
	
			temp=temp.append(str);
  			textarea=textarea.append(temp);
	   }else{
			textarea=textarea.append(str);
	       }
	}

		textarea=textarea.append("</pre>");
   
	    return textarea;
	}



	public StringBuffer wrapTextArea1(String str2,int j)
	{
    StringBuffer textarea=new StringBuffer("<pre  class=fb>");
    String str=str2;
	StringBuffer temp=new StringBuffer();
	int k=j;

	int i;
	if(str!=null)
	{
	if(str.length()>k)
	  {

		while(str.length()>k)
		  {

			for(i=0;i<=k-1;i++)
				 {
					 if(str.charAt(i)=='\n')
					  break;
				 }

				 if(i==k)
				 {
					temp=temp.append(str.substring(0,k));
					if(str.charAt(k)!=' ')
					{
					temp=temp.append("-");
					}
					temp.append("<BR>");
					str=str.substring(k,str.length());
				 }
				 else
				 {
					   temp=temp.append(str.substring(0,i+1));
			                   str=str.substring(i+1,str.length());
				 }
		  }
	
			temp=temp.append(str);
  			textarea=textarea.append(temp);
	   }else{
			textarea=textarea.append(str);
	       }
	}

		textarea=textarea.append("</pre>");
   
	    return textarea;
	}

	public String checkNull(Object str)
	{
		if(str == null)
			return "";
		else
			return str.toString().trim();
	}

	public String replaceNewline(String str)
	{
		if(checkNull(str).equals(""))
			return "";
		return str.replace('\n', ' ').replace('\r', ' ');
	}

	public String escapeNewline(String strToEscape)
	{
		if(strToEscape.indexOf('\n') == -1)
			return strToEscape;
		StringBuffer strBuf = new StringBuffer(strToEscape);
		int j = 0;
		int i = 0;
		while(i <= strToEscape.length())
		{
			i = strToEscape.indexOf('\n', i);
			if (i == -1)
				break;
			strBuf = strBuf.insert(i+j, "\\");
			strBuf = strBuf.insert(i+j+2, "\\n");
			i++;
			j+=3;
		}
		strToEscape = strBuf.toString();
		return strToEscape;
	}

	public String escapeCarriageReturn(String strToEscape)
	{
		if(strToEscape.indexOf('\r') == -1)
			return strToEscape;
		StringBuffer strBuf = new StringBuffer(strToEscape);
		int j = 0;
		int i = 0;
		while(i <= strToEscape.length())
		{
			i = strToEscape.indexOf('\r', i);
			if (i == -1)
				break;
			strBuf = strBuf.insert(i+j, "\\");
			strBuf = strBuf.insert(i+j+2, "\\r");
			i++;
			j+=3;
		}
		strToEscape = strBuf.toString();
		return strToEscape;
	}

	String a1[]={"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
	String a2[]={"","Ten","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
	String a3[]={"","Eleven","Twelve","Thirteen","Forteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
	String a4[]={"","Hundred","Thousand","Lakh","Crore"};

	public String ConvertInWords(double d)
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
			
			t = (float)(d - (long)d) * 100;
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

	public String GetWords(int n)
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

	public double roundTo2Digits(double d)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		String sd = d+"";
		int i = sd.indexOf(".");
		if(sd.substring(i).length()>3)
		{
			if(Integer.parseInt(sd.substring(i+3,i+4))<=5)  
				return Double.parseDouble(sd.substring(0,i+3));
			else
				return Double.parseDouble(df.format(d));
		}
		else
			return d;
	}
	public String newLineAddress(String str)
	{
		String result="";
		StringTokenizer st=new StringTokenizer(str,",");
		while(st.hasMoreTokens())
		{
			
			result+=st.nextToken()+",<br>";
		}
		result=result.substring(0,(result.length())-5);
		
		return result;
		
	}
	

}
