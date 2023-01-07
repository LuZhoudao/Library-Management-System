package myOracle;


import oracle.jdbc.driver.OracleConnection;

import java.io.Console;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


//数据库基础类和模版 -- 不用管
public class OracleDB {
    private static OracleConnection conn;

    public static void initializeDB ()  {
        //所有Table 调用初始化函数。
        BOOK_INFORMATIONTable.initializeTable(conn);
        BORROW_RECORDTable.initializeTable(conn);
        COLLECTIONTable.initializeTable(conn);
        PATRONTable.initializeTable(conn);
        STAFFTable.initializeTable(conn);
        RESERVETable.initializeTable(conn);
    }

    public static void getConnection() throws SQLException {
//        Console console = System.console();
//        System.out.print("Enter your username: ");    // Your Oracle ID with double quote
//        String userName = console.readLine();         // e.g. "98765432d"
//        System.out.print("Enter your password: ");    // Password of your Oracle Account
//        char[] password = console.readPassword();
//        String pwd = String.valueOf(password);

        Scanner input = new Scanner(System.in);
////        jump over input username and password of oracle account when testing. Every one must set its own account and password before doing the test.
        System.out.print("Enter your username: ");
        String userName = input.nextLine();
        System.out.print("Enter your password: ");
        String pwd = input.nextLine();

//        String userName = "\"21100038d\"";
//        String pwd = "updoiduc";


        // Connection
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        OracleConnection conn =
                (OracleConnection) DriverManager.getConnection(
                        "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms", userName, pwd);
        OracleDB.conn = conn;
    }

    public static void closeConnection() {
//        System.out.println("Releasing all open resources ...");
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }  catch (SQLException e){
            System.out.println("Error in executing the closeConnection.");
            while(e != null){
                System.out.println("message: " + e.getMessage());
                e = e.getNextException();
            }
        }
    }
}