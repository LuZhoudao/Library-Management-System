import java.io.Console;
import java.sql.*;
import oracle.jdbc.driver.*;

import controller.*;
import static exception_handler.ExceptionHandler.*;
import myOracle.*;
import static myOracle.OracleDB.*;



public class Main {

    public static void main(String[] args) {

        try {
            //create connection
            getConnection();
//            initialize the database
            initializeDB();

            //start the process
            Tools.printWelcomeMessage();

            WelcomePage.main(null);

            Tools.printLeaveMessage();
            //close the connection

            closeConnection();

        } catch (Exception e) {
                if (e instanceof  SQLException) {
                    printSQLException((SQLException) e);
                } else {
                    e.printStackTrace();
                }
        }

    }
}

