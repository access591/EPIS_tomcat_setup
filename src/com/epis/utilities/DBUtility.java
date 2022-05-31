package com.epis.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.epis.common.exception.EPISException;
import com.epis.common.exception.ServiceNotAvailableException;
import com.epis.utilities.Log;

public class DBUtility {

	private static ResourceBundle bundle = null;

	private static Log log = new Log(DBUtility.class);
	static {
		try {
			bundle = ResourceBundle.getBundle("com.epis.resource.dbresource");
		} catch (Exception exp) {
			log
					.error("DBUtility:Exception:Unable to Load com.epis.resource.dbresource propertied file in classpath.");
			log.error("DBUtility:Error:" + exp.getMessage());
		}
	}

	private static Connection getPooledConnection() throws EPISException {
		Connection connection = null;
		DataSource datasource = null;

		try {
			InitialContext initialContext = new InitialContext();
			datasource = (DataSource) initialContext.lookup(bundle
					.getString("jndi"));
			if (datasource != null) {
				connection = datasource.getConnection();
			}
		} catch (SQLException sqle) {
			log.error(sqle.getMessage());
			throw new EPISException(sqle);
		} catch (Exception exp) {
			log.error(exp.getMessage());
			throw new EPISException(exp);
		}
		return connection;
	}

	private static Connection getDirectConnection() throws EPISException {
		Connection connection = null;
		
		System.out.println("===========url=======" + bundle.getString("url") +"============ " +bundle.getString("username") );

		try {
			Class.forName(bundle.getString("driver"));
			connection = DriverManager.getConnection(bundle.getString("url"),
					bundle.getString("username"), bundle.getString("password"));
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.error("DBUtility:getDirectConnection:SQLException:"
					+ sqle.getErrorCode());
			throw new EPISException(sqle);
		} catch (Exception exp) {
			exp.printStackTrace();
			log.error("DBUtility:getDirectConnection:Exception:"
					+ exp.getMessage());
			throw new EPISException(exp);
		}
		return connection;

	}

	public static Connection getConnection() throws EPISException {
		Connection connection = null;
		String error = "";
		if (bundle == null) {
			log
					.error("DBUtility:getConnection:Exception: Unable to Locate the DB configuration file with name com.epis.resouce.dbresource.properties ");
			throw new EPISException(
					"Unable to Locate the DB configuration file ");
		}

		try {
			if ("DriverManager".equals(bundle.getString("connectiontype"))) {

				connection = getDirectConnection();
			} else if ("DataSource".equals(bundle.getString("connectiontype"))) {
				connection = getPooledConnection();
			} else {
				error = "DB Configuration Error. Check the data base configuration files.";
			}
			if (!"".equals(error)) {
				log.error(error);
				throw new ServiceNotAvailableException(error);
			}
			if (connection == null) {
				throw new ServiceNotAvailableException();
			}
			
			System.out.println("=====Resource Bundle : url ===== "+ bundle.getString("url"));
		} catch (ServiceNotAvailableException sne) {
			throw new EPISException(sne.getMessage());
		} catch (Exception e) {

			throw new EPISException(e);
		}

		return connection;
	}

	public static void closeConnection(ResultSet resultSet,
			Statement statement, Connection connection) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException exp) {
			log.error(exp.getMessage());
		}
	}

	public static void closeConnection(ResultSet resultSet,
			PreparedStatement pstatement, Connection connection) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (pstatement != null) {
				pstatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException exp) {
			log
					.error("DBUtility:closeConnection:Exception:"
							+ exp.getMessage());
		}
	}

	public static int executeUpdate(String updateQry) throws EPISException {
		int updateCount = 0;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DBUtility.getConnection();
			if (connection != null) {
				statement = connection.createStatement();
				if (statement != null) {
					updateCount = statement.executeUpdate(updateQry);
				}
			}
		} catch (SQLException sqlExp) {
			log.error(sqlExp.getMessage());
			throw new EPISException(sqlExp);
		} catch (Exception exp) {
			log.error(exp.getMessage());
			throw new EPISException(exp);
		} finally {
			closeConnection(null, statement, connection);
		}
		return updateCount;

	}

	public static ResultSet getRecordSet(String selectQry, Statement statement)
			throws EPISException {

		ResultSet resultSet = null;
		try {
			if (statement != null) {
				resultSet = statement.executeQuery(selectQry);
			}

		} catch (SQLException sqlExp) {
			log.error(sqlExp.getMessage());
			throw new EPISException(sqlExp);
		} catch (Exception exp) {
			log.error(exp.getMessage());
			throw new EPISException(exp);
		}

		return resultSet;
	}

	public static int getRecordCount(String Qry) throws Exception {
		ResultSet resultSet = null;
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			if (connection != null) {
				statement = connection.createStatement();
				if (statement != null) {
					resultSet = statement.executeQuery(Qry);
					if (resultSet.next()) {
						count = resultSet.getInt(1);
					}
				}
			}
		} catch (SQLException sqlExp) {
			log.error(sqlExp.getMessage());
			throw new EPISException(sqlExp);
		} catch (Exception exp) {
			log.error(exp.getMessage());
			throw new EPISException(exp);
		} finally {
			closeConnection(null, statement, connection);
		}

		return (count);
	}

	public static void setAutoCommit(boolean autoCommit, Connection connection)
			throws Exception, SQLException {
		if (connection != null) {
			connection.setAutoCommit(autoCommit);
		}

	}

	public static void commitTrans(Connection connection) throws Exception,
			SQLException {
		if (connection != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
		}

	}

	public static void rollbackTrans(Connection connection) throws Exception,
			SQLException {
		if (connection != null) {
			if (!connection.getAutoCommit()) {
				connection.rollback();
			}
		}
	}

	/* Added on 18-May-2010 By Suresh */
	public static void closeConnection(Connection connection,
			Statement statement, ResultSet resultSet) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException exp) {
			log.error(exp.getMessage());
		}
	}
	
	public static ResultSet prepExecuteQuery(String queyCode,String[] params,Connection con) throws EPISException{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			// Retrieve the Query from xml
			QueryDetailsRetriver query = new QueryDetailsRetriver(queyCode);

			// Creates a PreparedStatement object for sending parameterized SQL
			// statements to the database.
			pst = con.prepareStatement(query.getQuery());

			// Setting the designated parameter to the given Java String value.
			int paramLen = params.length;
			for(int i=0;i< paramLen ; i++){
				pst.setString(i+1, params[i]);
			}
			
			// Executes the SQL query
			rs = pst.executeQuery();
			
		}catch (SQLException e) {
			log.error(e.getMessage());
			throw new EPISException(e);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new EPISException(e);
		}
		return rs;
	}
	
	public static int prepExecuteUpdate(String queyCode,String[] params) throws EPISException{
		Connection con = null;
		PreparedStatement pst = null;
		int count = 0;
		try {
			
			// Getting the Database Connection.
			con = DBUtility.getConnection();

			// Retrieve the Query from xml
			QueryDetailsRetriver query = new QueryDetailsRetriver(queyCode);

			// Creates a PreparedStatement object for sending parameterized SQL
			// statements to the database.
			pst = con.prepareStatement(query.getQuery());

			// Setting the designated parameter to the given Java String value.
			int paramLen = params.length;
			for(int i=0;i< paramLen ; i++){
				pst.setString(i+1, params[i]);
			}
			// Executes the SQL query
			count = pst.executeUpdate();

		}catch (SQLException e) {
			log.error(e.getMessage());
			throw new EPISException(e);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new EPISException(e);
		}finally{
			closeConnection(con,pst,null);
		}
		return count;
	}
	public static void closeStatement(Statement statement){
		
		try {
		
			if(statement!=null){
				statement.close();
			}
		
		} catch (SQLException exp) {
			log.error(exp.getMessage());
		}
	}
}
