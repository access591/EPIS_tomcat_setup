package com.epis.utilities;


public class AutoGeneration 
{
//getNextCode
 public static String getNextCode(String tname,String fname,int flen, java.sql.Connection con) throws Exception
  {  
     String str="";
     String tableName="";
     String fieldName="";
     int fieldLength=0;
     String strsql="";
	java.sql.ResultSet rs=null;

	 tableName=tname;
	 fieldName=fname;
	 fieldLength=flen; 

	strsql="select lpad(nvl(max(to_number(" + fieldName + "))+1,1)," + fieldLength + ",0) code from " + tableName; 
   
	java.sql.Statement st = con.createStatement();
	rs=st.executeQuery(strsql);
	while(rs.next())
	{
		str=rs.getString(1);
	}
	rs.close();
	st.close();

	return(str);
  }

//getNextCodeGroupBy
public  static String getNextCodeGBy(String tname, String fname, int flen, String Gby, java.sql.Connection con) throws Exception
{  
	 Log log = new Log(AutoGeneration.class);

     String str="";
     String tableName="";
     String fieldName="";
     String gBy="";
     int fieldLength=0;
     String strsql;
	java.sql.ResultSet rs=null;
     int lenplusone=0;
     int lengt=0;
     int difflen=0; 	

	 tableName=tname;
	 fieldName=fname;
	 gBy=Gby;		
	 fieldLength=flen; 
	 lenplusone=(gBy.length())+1;
	 lengt=gBy.length();
	 difflen=flen-lengt;

	strsql = "select lpad(nvl(max(to_number(substr(" + fieldName + "," + lenplusone + "," + flen + "))+1),1)," + difflen + ",0) code from " + tableName + " where  substr(" + fieldName + ",1," + lengt + ") ='" + gBy + "'" ;
	log.info(strsql) ;
	java.sql.Statement st = con.createStatement();
	rs=st.executeQuery(strsql);
	while(rs.next())
	{
		str=rs.getString(1);
	}
	rs.close();
	st.close();

	str=gBy+str;
	return(str);
  }
  
}



