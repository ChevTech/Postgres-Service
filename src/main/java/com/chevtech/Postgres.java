package com.chevtech;

import org.postgresql.geometric.PGpoint;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import static com.chevtech.Utils.printBatchUpdateException;
import static com.chevtech.Utils.printSQLException;

public class Postgres {
    private Connection conn;

    public Postgres() {
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

    /* CRUD - CREATE, READ, UPDATE, DELETE */
    public City getCity(String cityName) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        City city = null;

        try {
            String query = "SELECT name, location FROM cities " +
                    "WHERE name = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, cityName);
            rs = stmt.executeQuery();

            while(rs.next()){
                String name = rs.getString("name");
                PGpoint location = (PGpoint) rs.getObject("location");
                city = new City(name, location);
            }

        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) { stmt.close(); }
        }

        return city;
    }

    public void insertCity(City city) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO cities (name, location) " +
                                                "VALUES (?, ?);");

            stmt.setString(1, city.getName());
            stmt.setObject(2, city.getLocation());
            stmt.executeUpdate();
        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) { stmt.close(); }
        }
    }

    public ArrayList<CityWeather> getCityWeather(String cityName) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CityWeather> result = new ArrayList<CityWeather>();

        try {
            String query = "SELECT city, temp_lo, temp_hi, prcp, date FROM weather " +
                                "WHERE city = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, cityName);
            rs = stmt.executeQuery();

            while(rs.next()){
                String city = rs.getString("city");
                int temp_lo = rs.getInt("temp_lo");
                int temp_hi = rs.getInt("temp_hi");
                double prcp = rs.getDouble("prcp");
                LocalDate date = rs.getObject("date", LocalDate.class);
                result.add(new CityWeather(city, temp_lo, temp_hi, prcp, date));
            }

        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) { stmt.close(); }
        }

        return result;
    }

    public ArrayList<CityWeather> getAllWeather() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<CityWeather> result = new ArrayList<CityWeather>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT city, temp_lo, temp_hi, prcp, date FROM weather");

            while(rs.next()){
                String city = rs.getString("city");
                int temp_lo = rs.getInt("temp_lo");
                int temp_hi = rs.getInt("temp_hi");
                double prcp = rs.getDouble("prcp");
                LocalDate date = rs.getObject("date", LocalDate.class);
                result.add(new CityWeather(city, temp_lo, temp_hi, prcp, date));
            }
        } catch (SQLException e){
            printSQLException(e);
        } finally {
            if(stmt != null) { stmt.close(); }
        }

        return result;
    }

    public void insertWeather(CityWeather data) throws SQLException{
        PreparedStatement stmt = null;
        try {
             stmt = conn.prepareStatement("INSERT INTO weather (city, temp_lo, temp_hi, prcp, date) " +
                                                "VALUES (?, ?, ?, ?, ?);");

            stmt.setString(1, data.getCity());
            stmt.setInt(2, data.getTemp_hi());
            stmt.setInt(3, data.getTemp_lo());
            stmt.setDouble(4, data.getPrcp());
            stmt.setObject(5, data.getDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            stmt.close();
        }
    }

    public void insertManyWeather(ArrayList<CityWeather> dataArray) throws SQLException{
        PreparedStatement stmt = null;
        String query = "INSERT INTO weather (city, temp_lo, temp_hi, prcp, date) " +
                            "VALUES (?, ?, ?, ?, ?);";

        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(query);

            for(CityWeather data : dataArray) {
                stmt.setString(1, data.getCity());
                stmt.setInt(2, data.getTemp_hi());
                stmt.setInt(3, data.getTemp_lo());
                stmt.setDouble(4, data.getPrcp());
                stmt.setObject(5, data.getDate());
                stmt.addBatch();
            }

            int[] updateCounts = stmt.executeBatch();

            //Commit only if whole batch is successful
            conn.commit();
        } catch (BatchUpdateException batchEx) {
            printBatchUpdateException(batchEx);
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if(stmt != null) stmt.close();
            conn.setAutoCommit(true);
        }
    }

    public void deleteCityWeather(String cityName) throws SQLException{
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM weather WHERE city = ?");
            stmt.setString(1, cityName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if(stmt != null) stmt.close();
        }
    }
}
