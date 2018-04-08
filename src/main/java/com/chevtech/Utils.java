package com.chevtech;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.Arrays;

public class Utils {
    public static void printBatchUpdateException(BatchUpdateException ex){
        System.err.println("----BatchUpdateException----");
        for(Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());

                int[] updateCounts = ex.getUpdateCounts();
                System.out.println(Arrays.toString(updateCounts));
            }
        }
    }

    public static void printSQLException(SQLException ex){
        System.err.println("----SQLException----");
        for(Throwable e : ex){
            if(e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());

                Throwable t = ex.getCause();

                while(t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
