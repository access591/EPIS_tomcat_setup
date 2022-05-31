package  com.epis.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserTracking {
	

	public static void write(String userCD, String activityDesc, String operationMode,
			String moduleCd, String keyField,String screenName) {
		Log log = new Log(UserTracking.class);
		Connection conn = null;
		PreparedStatement pst = null;
		int userCdLength = 30;
		int activityDescLength = 200;
		int operationModeLength = 1;
		int keyFieldLength = 30;
		int moduleCdLength = 3;
		int ScreenNameLength = 30;
		try {
			conn = DBUtility.getConnection();
			pst = conn
					.prepareStatement("INSERT INTO TRACKING_LOG (userCd, description, opeartionmode,keyfield,moduleCode,screenName) VALUES(?, ?, ?, ?,?,?)");
			int fidx = 1;
			pst.setString(fidx++, truncate(userCD==null?"":userCD, userCdLength));
			pst.setString(fidx++, truncate(activityDesc, activityDescLength));
			pst.setString(fidx++, truncate(operationMode, operationModeLength));
			pst.setString(fidx++, truncate(keyField, keyFieldLength));
			pst.setString(fidx++, truncate(moduleCd, moduleCdLength));
			pst.setString(fidx++, truncate(screenName, ScreenNameLength));
			pst.executeUpdate();
		} catch (Exception e) {
			log.printStackTrace(e);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.printStackTrace(e);
			}
		}
	}

	private static String truncate(String value, int length) {
		return (value.length() > length) ? value.substring(0, length) : value;
	}
}
