package com.aims.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.sql.PooledConnection;

import oracle.jdbc.pool.OracleConnectionCacheImpl;

public class LocalDBAccess {
	Connection con;
    Statement st;
    ResultSet rs;
	String propertiesRoot;
	PooledConnection pc;
	
	OracleConnectionCacheImpl ods;
    int i=0;
    Log log  = new Log(LocalDBAccess.class);
    private static final LocalDBAccess instance = new LocalDBAccess();
	// Allow subclasses to override the constructor, if necessary.
	
	// Accessor only.
	public static LocalDBAccess getInstance()
	{
		return instance;
	}

    protected LocalDBAccess()
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
			ResourceBundle bundle = ResourceBundle.getBundle("com.aims.resource.dbresource.AIMSFINANCEDB");
			String hostname=bundle.getString("hostname");
			String portno=bundle.getString("portno");
			String cstring=bundle.getString("connectionstring");
   			String username=bundle.getString("username");
		  	String password=bundle.getString("password");
			String url="jdbc:oracle:thin:@"+hostname+":"+portno+":"+cstring;
			
			ods = new OracleConnectionCacheImpl();
			ods.setURL(url);
			ods.setUser(username);
			ods.setPassword(password);		
			ods.setMaxLimit (10);
			ods.setMaxLimit (100);
			ods.setStmtCacheSize(20);
			
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//con = DriverManager.getConnection(url,username,password);
			con  = ods.getConnection();			
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
		rs.close();
		st.close();
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
