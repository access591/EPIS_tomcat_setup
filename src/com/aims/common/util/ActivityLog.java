package com.aims.common.util;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ActivityLog 
{	
	private static ActivityLog activityLog=new ActivityLog();
	public static ActivityLog getInstance(){
		return activityLog;
	}
public void write (String userCD,String activityDesc, String operationMode, String keyField, java.sql.Connection conn) throws SQLException
  {
    String tableName="PAYROLL_ACTIVITY_LOG";
		
		 
    PreparedStatement pstmt = null;
	int userCdLength = 30;
    //int activityDateLength =activityDate.length();
    int activityDescLength = 200;
    int operationModeLength = 1;
    //int moduleCodeLength = 3;
	int keyFieldLength=30;
    try {
      try {
		
	pstmt = conn.prepareStatement("INSERT INTO " + tableName + 
                                            " (USER_CD, ACTIVITY_DESC, OPERATION_MODE,key_field) VALUES(?, ?, ?, ?)");
	
    
	 int fidx = 1;
	pstmt.setString(fidx++, truncate(userCD, userCdLength));
	//pstmt.setString(fidx++, truncate(activityDate, activityDateLength));
	pstmt.setString(fidx++, truncate(activityDesc, activityDescLength));
	pstmt.setString(fidx++, truncate(operationMode, operationModeLength));
	//pstmt.setString(fidx++, truncate(moduleCd, moduleCodeLength));
	pstmt.setString(fidx++, truncate(keyField, keyFieldLength));
	pstmt.executeUpdate();
	 } finally {
	if (pstmt != null)
	  pstmt.close();
      }
    } catch (SQLException e) {
      String text = "got error at insert into " + tableName + "  " + e;
      show(text);
      throw new SQLException(text);
    }
	}

public String truncate (String value, int length) {
    return (value.length() > length)
      ? value.substring(0, length)
      : value
      ;
  }
 public  void show (String s) {
    System.out.println(s);
  }
}
