package utils;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.Assert;
import pages.BasePageClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils extends LoggerUtils {


    private static final String DATA_SOURCE_URL = PropertiesUtils.getDataSourceUrl();

    private static final String ROOT_USERNAME = PropertiesUtils.getRootUsername();

    private static final String ROOT_PASSWORD = PropertiesUtils.getRootPassword();


    public static String getUserId(String username) {
        log.trace("getUserId(" + username + ")");

        String sqlQuery = "SELECT user_id FROM users WHERE username = ?";

        //open a connection
        Connection connection = null;
        QueryRunner runQuery = new QueryRunner();
        //Returns one ResultSet column as an object via the ResultSet.getObject() method that performs type conversions.
        ScalarHandler<String> scalarHandler = new ScalarHandler<>();
        //the result is content of one cell
        String result = null;

        //establish connection
        try {
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
            result = runQuery.query(connection, sqlQuery, scalarHandler, username);
        } catch (SQLException e) {
            Assert.fail("Exception in method getUserId:" + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail("Exception in method getUserId while trying to close the connection" + e.getMessage());
            }
        }
        return result;
    }

}
