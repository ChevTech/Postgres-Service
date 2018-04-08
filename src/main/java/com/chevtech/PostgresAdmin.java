package com.chevtech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static com.chevtech.Utils.printSQLException;

public class PostgresAdmin {
    private Connection conn;

    public PostgresAdmin() {
        String url = "jdbc:postgresql://localhost/mydb";
        Properties props = new Properties();
        props.setProperty("user", "btgahbtgahbtgahbtgah");
        props.setProperty("password", "12345");

        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    public void createWeatherTable() throws SQLException {
        String createTableQuery =
                "CREATE TABLE weather (" +
                        "city varchar(80)," +
                        "temp_lo int," +
                        "temp_hi int," +
                        "prcp real," +
                        "date date" +
                        ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) stmt.close();
        }
    }

    public void createCityTable() throws SQLException {
        String createTableQuery =
                "CREATE TABLE cities (" +
                        "name varchar(80) UNIQUE," +
                        "location point" +
                        ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) stmt.close();
        }
    }

    public void dropTable(String dbName) throws SQLException {
        String dropTableQuery = "DROP TABLE " + dbName;

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(dropTableQuery);
        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) stmt.close();
        }
    }
}
