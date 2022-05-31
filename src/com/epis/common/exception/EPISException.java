package com.epis.common.exception;

import java.sql.SQLException;

public class EPISException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String error="";
	public EPISException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EPISException(String errormsg) {
		super();
		this.error=errormsg;
		// TODO Auto-generated constructor stub
	}
	public EPISException(Exception exp ) {
		super();
		// TODO Auto-generated constructor stub
		error=exp.getMessage();
	}
	public EPISException(SQLException sqlexp ) {
		super();
		// TODO Auto-generated constructor stub
		error=getErrorMessage(sqlexp);
	}
	private String getErrorMessage(SQLException sqlexp){
		String sqlerror="";
		int errorcode=sqlexp.getErrorCode();
		switch (errorcode) {
        
        case 1:    sqlerror="Record Already Exists with this data.";
        		   break;
        case 900:  sqlerror="Invalid SQL Statement."; 
        			break; 		   
        case 904:  sqlerror="Invalid column name."; 
                   break;        
        case 917:  sqlerror="Missing comma or Single Quote existing in the data."; 
        		   break;
        case 920:  sqlerror="Invalid Relational Operator used in the query.";
        		   break;
        case 921:  sqlerror="SQL statement not ended properly."; 
                   break;
        case 923:  sqlerror="FROM keyword not found where expected."; 
        		   break;                        
        case 936:  sqlerror="Missing Expression in the query."; 
                   break;
        case 942:  sqlerror="Table or View Does Not Exist."; 
        		   break;
        case 1017: sqlerror="Invalid username/password. Login denied to DB server."; 
                   break;
        case 1400: sqlerror="Cannot insert null into field.";
                   break;
        case 1401: sqlerror="Inserted data too large for one or more columns."; 
                   break;
        case 1438: sqlerror="Value larger than specified precision."; 
                   break;
        case 2292: sqlerror="Can't Delete. Data being used by some other table(s)."; 
                   break;
        case 2291: sqlerror="Parent key not found ."; 
        			break;
        case 12154: sqlerror="Cannot connect to Server. Server not found."; 
        			break;
        case 12203: sqlerror="Unable to connect to Server. Network failure.";
        			break;    
        case 17002: sqlerror="Unable to Connect to DB Server.";
					break;  
        default:    sqlerror=sqlexp.getMessage();
        			break;
    }

		
		return sqlerror;
	}
	public String getMessage(){
		return error;
	}
}
