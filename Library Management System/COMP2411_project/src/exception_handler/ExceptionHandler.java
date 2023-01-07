package exception_handler;

import java.io.*;
import java.io.Console;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;


//public class ExceptionHandler {
//    public static void printBatchUpdateException(BatchUpdateException b) {
//        System.err.println("----BatchUpdateException----");
//        System.err.println("SQLState:  " + b.getSQLState());
//        System.err.println("Message:  " + b.getMessage());
//        System.err.println("Vendor:  " + b.getErrorCode());
//        System.err.print("Update counts:  ");
//        int[] updateCounts = b.getUpdateCounts();
//        for (int i = 0; i < updateCounts.length; i++) {
//            System.err.print(updateCounts[i] + "   ");
//        }
//    }
//
//    public static void printSQLException(SQLException ex) {
//        for (Throwable e : ex) {
//            if (e instanceof SQLException) {
//                if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
//                    e.printStackTrace(System.err);
//                    System.err.println("SQLState: " + ((SQLException)e).getSQLState());
//                    System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
//                    System.err.println("Message: " + e.getMessage());
//                    Throwable t = ex.getCause();
//                    while (t != null) {
//                        System.out.println("Cause: " + t);
//                        t = t.getCause();
//                    }
//                }
//            }
//        }
//    }
//
//
//    public static void getWarningsFromResultSet(ResultSet rs) throws SQLException {
//        printWarnings(rs.getWarnings());
//    }
//
//    public static void getWarningsFromStatement(Statement stmt) throws SQLException {
//        printWarnings(stmt.getWarnings());
//    }
//
//    public static void printWarnings(SQLWarning warning) throws SQLException {
//        if (warning != null) {
//            System.out.println("\n---Warning---\n");
//            while (warning != null) {
//                System.out.println("Message: " + warning.getMessage());
//                System.out.println("SQLState: " + warning.getSQLState());
//                System.out.print("Vendor error code: ");
//                System.out.println(warning.getErrorCode());
//                System.out.println("");
//                warning = warning.getNextWarning();
//            }
//        }
//    }
//
//    public static boolean ignoreSQLException(String sqlState) {
//        if (sqlState == null) {
//            System.out.println("The SQL state is not defined!");
//            return false;
//        }
//        // X0Y32: Jar file already exists in schema
//        if (sqlState.equalsIgnoreCase("X0Y32"))
//            return true;
//        // 42Y55: Table already exists in schema
//        if (sqlState.equalsIgnoreCase("42Y55"))
//            return true;
//        return false;
//    }
//}


//异常处理类 -- 不用管
public class ExceptionHandler {
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(((SQLException) e).getSQLState()) == false) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {
        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;
        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;
        return false;
    }
}
