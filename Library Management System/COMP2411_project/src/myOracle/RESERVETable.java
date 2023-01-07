package myOracle;


import java.io.*;
import java.io.Console;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;
import java.util.List;

import exception_handler.*;

public class RESERVETable extends Table{
    public static String  TABLE_NAME = "RESERVE";
    
    private RESERVETable () {};

    //select method:
    private static Vector<RESERVE> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<RESERVE> ivec = new Vector<>();
        ResultSet rset = select(selectQuery, paramList);
        try  {
            RESERVE r ;
            while (rset.next()) {
                r = new RESERVE();
                r.BOOK_ID = rset.getString(1);
                r.ISBN = rset.getString(2);
                r.PATRON_ID = rset.getString(3);
                r.DEADLINE = rset.getString(4);
                r.BOOK_STATE = rset.getInt(5) == 1;
                ivec.add(r);
            }
    
        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table RESERVE.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }

    //selectByKey
    public static RESERVE selectPI(String PATRON_ID, String ISBN) throws SQLException { //一人只能预定一本书
        RESERVE result = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE PATRON_ID = ? AND ISBN = ?";
        Object[] paramList = new Object[]{PATRON_ID, ISBN};
        Vector<RESERVE> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            result = ivec.elementAt(0);
        }
        return result;
    }

    

    //selectByAttribute
    public static Vector<RESERVE> selectByISBN(String ISBN) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[]{ISBN};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> selectByPATRON_ID(String PATRON_ID) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{PATRON_ID};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> selectByBOOK_ID(String BOOK_ID) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{BOOK_ID};
        return selectByQuery(selectQuery, paramList);
    }


    //selectSingle



    //selectByCondition
    public static Vector<RESERVE> select_overdue_available_reserve() throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE WHERE " + "DEADLINE > ? AND BOOK_STATE = 1";
        Object[] paramList = new Object[]{LocalDate.now().toString()};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> select_all() throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE";
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> select_overdue_unavailable_reserve() throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE WHERE " + "DEADLINE > ? AND BOOK_STATE = 0";
        Object[] paramList = new Object[]{LocalDate.now().toString()};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> select_available_reserve_ofPatron(String patron_id) throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE WHERE " + "PATRON_ID = ?";
        Object[] paramList = new Object[]{patron_id};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> select_unavailable_reserve_ofPatron(String patron_id) throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE WHERE " + "PATRON_ID = ?";
        Object[] paramList = new Object[]{patron_id};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<RESERVE> select_indue_unavailale_reserve() throws SQLException {
        String selectQuery = "SELECT * FROM RESERVE WHERE " + "DEADLINE < ? AND BOOK_STATE = 0 ";
        Object[] paramList = new Object[]{LocalDate.now().toString()};
        return selectByQuery(selectQuery, paramList);
    }


    ////Update method:



    
    //deleteBycondition
    public static void delete_out_of_date_RESERVE() {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE " + "DEADLINE < ?";
        Object[] paramList = new Object[]{LocalDate.now().toString()};
        update(updateQuery, paramList);
    }
    public static void deleteByPATRON_ID(String PATRON_ID) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{PATRON_ID};
        update(updateQuery, paramList);
    }
    public static void deleteByPI(String PATRON_ID, String ISBN) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE PATRON_ID = ? AND ISBN = ?";
        Object[] paramList = new Object[]{PATRON_ID, ISBN};
        update(updateQuery, paramList);
    }
    

    //insert

    public static void insertTuple (RESERVE o) throws SQLException {
        String insertQuery ;
        Object[] paramList = new Object[4];
        if (o.BOOK_STATE) {
            insertQuery = "INSERT INTO RESERVE(BOOK_ID, ISBN, PATRON_ID, DEADLINE, BOOK_STATE) VALUES (?,?,?,?,1)";
        } else {
            insertQuery = "INSERT INTO RESERVE(BOOK_ID, ISBN, PATRON_ID, DEADLINE, BOOK_STATE) VALUES (?,?,?,?,0)";
        }

        paramList[0] = o.BOOK_ID;
        paramList[1] = o.ISBN;
        paramList[2] = o.PATRON_ID;
        paramList[3] = o.DEADLINE;

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<RESERVE> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (RESERVE o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table RESERVE");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}


