package com.epis.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.PooledConnection;



import oracle.jdbc.pool.OracleConnectionCacheImpl;

public class DBAccess {
	Connection con;
    Statement st;
    ResultSet rs;
	String propertiesRoot;
	PooledConnection pc;
	
	OracleConnectionCacheImpl ods;
    int i=0;
    Log log  = new Log(DBAccess.class);
   // private static final DBAccess instance = new DBAccess();
	// Allow subclasses to override the constructor, if necessary.
	
	// Accessor only.
	public static DBAccess getInstance()
	{
		return new DBAccess();
	}

    public DBAccess()
	{
		con = null;
		st = null;
		rs = null;
		
		
	}
	
	//Connecting to the database
    public void makeConnection() 
    {
    	try
		{	
    		
    		String station="CHQD";
    		log.info("DBAccess    makeConnection    "+station);
			ResourceBundle bundle = ResourceBundle.getBundle("com.epis.resource."+station);
			log.info("YYYYYYYYYYYYYpayroll");
			String hostname=bundle.getString("hostname");
			String portno=bundle.getString("portno");
			String cstring=bundle.getString("connectionstring");
   			String username=bundle.getString("username");
		  	String password=bundle.getString("password");
			String url="jdbc:oracle:thin:@"+hostname+":"+portno+":"+cstring;
			log.info(url);
			
			/*ods = new OracleConnectionCacheImpl();
			ods.setURL(url);
			ods.setUser(username);
			ods.setPassword(password);		
			ods.setMaxLimit (10);
			ods.setMaxLimit (100);
			ods.setStmtCacheSize(20);*/
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			log.info("---------");
			con = DriverManager.getConnection(url,username,password);
			
			log.info("+Connected..........");
			//con  = ods.getConnection();			
			//log.info("------------New Connection Opened---------");
		}
		catch(Exception e)
		 {
			System.out.println("vvvvvvvvvvvvvvvvvvvvv "+e);
			e.printStackTrace();
		 }

    }

	public String getActiveSize(){
		return ("Active Size: "+ods.getActiveSize()+"  cacheSize: "+ods.getCacheSize());
	}
	
	public Connection getConnection()
	{
		return con;
	}

	//Getting Data from the Dtabase
	public ResultSet getRecordSet(String Qry) throws Exception
	{
		st=con.createStatement();
		rs=st.executeQuery(Qry);
		return(rs);
	}

	//getRecordCount
	public int getRecordCount(String Qry) throws Exception
	{
		st=con.createStatement();
		rs=st.executeQuery(Qry);
		while(rs.next())
		{
			this.i=rs.getInt(1);
		}
		
		return(i);
	}

	//Updating the database
	public int executeUpdate(String sqlStmt) throws Exception
	{
		st=con.createStatement();
		i=st.executeUpdate(sqlStmt);
		return(i);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		// Sets autocommit to 'true' or 'false' depending on the value passed
		// If this is set to true, commit and rollback are not required, since
		// each DML statement will be committed automatically.
		// If setAutoCommit(false) is called, then a transaction will only be committed when 
		// commitTrans() is called.
		con.setAutoCommit(autoCommit);
	}

	public void commitTrans() throws SQLException
	{
		// Will commit all statements after the previous commit.
		// Releases any locks that may have been placed via this connection.
		if (!con.getAutoCommit())
			con.commit();
	}

	public void rollbackTrans() throws SQLException
	{
		// Will rollback all statements after the previous commit or rollback.
		// Releases any locks that may have been placed via this connection.
		if (!con.getAutoCommit())
			con.rollback();
	}

	//Closing the ResultSet and Statement
	public void closeRs() throws Exception
	{
		if(rs!=null){
			rs.close();
		}
		if(st !=null){
			st.close();
		}
	}

	//Closing the Connection
	public void closeCon() throws Exception
	{
		
		if(!con.isClosed()){
			con.close();
			//log.info("***************Connection Closed****************");
		}
	}

}
