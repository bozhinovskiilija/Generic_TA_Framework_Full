package utils;

import objects.DatabaseHero;
import objects.DatabaseUser;
import objects.Hero;
import objects.User;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.Assert;
import pages.BasePageClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseUtils extends LoggerUtils {


    private static final String DATA_SOURCE_URL = PropertiesUtils.getDataSourceUrl();

    private static final String ROOT_USERNAME = PropertiesUtils.getRootUsername();

    private static final String ROOT_PASSWORD = PropertiesUtils.getRootPassword();


    public static String getUserID(String username) {
        log.trace("getUserID(" + username + ")");

        String sqlQuery = "SELECT user_id FROM users WHERE user_id = ?";

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
            Assert.fail("Exception in method getUsername:" + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail("Exception in method getUsername while trying to close the connection" + e.getMessage());
            }
        }
        return result;
    }

    public static String getUsername(String sUserID) {
        log.trace("getUsername(" + sUserID + ")");
        String sqlQuery = "SELECT username FROM users WHERE user_id = ?";

        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ScalarHandler<String> scalarHandler = new ScalarHandler<>();
        String result = null;

        try {
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
            result = run.query(connection, sqlQuery, scalarHandler, sUserID);
        } catch (Exception e) {
            Assert.fail("Exception in method getUsername(" + sUserID + ") while trying to connect to database. Message: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail("Exception in method getUsername(" + sUserID + ") while trying to close database. Message: " + e.getMessage());
            }
        }
        return result;
    }


    public static List<String> getAllUsernames() {
        log.trace("getAllUsernames");

        String sqlQuery = "SELECT username FROM users";

        Connection connection = null;
        QueryRunner runQuery = new QueryRunner();
        ColumnListHandler<String> stringColumnListHandler = new ColumnListHandler<>();
        List<String> result = null;

        try {
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
            result = runQuery.query(connection, sqlQuery, stringColumnListHandler);
        } catch (SQLException e) {
            Assert.fail("Exception in method getAllUsernames:" + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail(
                    "Exception in method getAllUsernames while trying to close the connection" + e.getMessage());
            }
        }
        return result;
    }


    //read data from 1 table row(contains different data type - BeanHandler)
    public static DatabaseUser getDatabaseUser(String username) {

        log.trace("getDatabaseUser");

        String sqlQuery = "SELECT * FROM users WHERE username = ?";

        Connection connection = null;

        QueryRunner runQuery = new QueryRunner();

        ResultSetHandler<DatabaseUser> resultSetHandler = new BeanHandler<>(DatabaseUser.class);

        DatabaseUser result = null;

        try {
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
            result = runQuery.query(connection, sqlQuery, resultSetHandler, username);
        } catch (SQLException e) {
            Assert.fail("Exception in method getAllUsernames:" + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail(
                    "Exception in method getAllUsernames while trying to close the connection" + e.getMessage());
            }
        }

        return result;
    }


    public static List<DatabaseHero> getDatabaseHeroesForUser(String userId) {

        log.trace("getDatabaseHeroesForUser");

        String sqlQuery = "SELECT * FROM heroes WHERE fk_user_id = ?";

        Connection connection = null;

        QueryRunner runQuery = new QueryRunner();

        ResultSetHandler<List<DatabaseHero>> resultSetHandler = new BeanListHandler<>(DatabaseHero.class);

        List<DatabaseHero> result = null;

        try {
            connection = DriverManager.getConnection(DATA_SOURCE_URL, ROOT_USERNAME, ROOT_PASSWORD);
            result = runQuery.query(connection, sqlQuery, resultSetHandler, userId);
        } catch (SQLException e) {
            Assert.fail("Exception in method getDatabaseHeroesForUser:" + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    if (!connection.isClosed()) {
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e) {
                Assert.fail(
                    "Exception in method getDatabaseHeroesForUser while trying to close the connection" + e.getMessage());
            }
        }

        return result;
    }

    private static User assembleUser (DatabaseUser databaseUser) {
        String sUsername = databaseUser.getUsername();
        String sPassword = databaseUser.getPassword();
        String sEmail = databaseUser.getEmail();
        String sFirstName = databaseUser.getFirst_name();
        String sLastName = databaseUser.getLast_name();
        String sAbout = databaseUser.getAbout();
        String sSecretQuestion = databaseUser.getSecret_question();
        String sSecretAnswer = databaseUser.getSecret_answer();
        Date dCreatedAt = databaseUser.getCreated();
        List<Hero> heroes = new ArrayList<>();
        List<DatabaseHero> databaseHeroes = DatabaseUtils.getDatabaseHeroesForUser(databaseUser.getUser_id());
        for(DatabaseHero h : databaseHeroes) {
            Hero hero = assembleHero(h);
            heroes.add(hero);
        }
        return new User(sUsername, sPassword, sEmail, sFirstName, sLastName, sAbout, sSecretQuestion, sSecretAnswer, dCreatedAt, heroes);
    }

    public static Hero assembleHero(DatabaseHero databaseHero){

        String heroName = databaseHero.getName();
        String heroClass = databaseHero.getType();
        int heroLevel = databaseHero.getLevel();
        Date createdAt = databaseHero.getCreated();
        String username = DatabaseUtils.getUsername(databaseHero.getFk_user_id());

        return new Hero(heroName,heroClass,heroLevel,username,createdAt);

    }

    public static User getUser(String sUsername) {
        DatabaseUser databaseUser = DatabaseUtils.getDatabaseUser(sUsername);
        return assembleUser(databaseUser);
    }

}
