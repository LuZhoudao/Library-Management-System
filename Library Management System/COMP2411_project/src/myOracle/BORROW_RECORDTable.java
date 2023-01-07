package myOracle;


import java.io.*;
import java.io.Console;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Vector;
import java.util.List;

import exception_handler.*;


public class BORROW_RECORDTable extends Table{
    public static String  TABLE_NAME = "BORROW_RECORD";

    private BORROW_RECORDTable () {};

    //select method:
    private static Vector<BORROW_RECORD> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<BORROW_RECORD> ivec = new Vector<>();
        ResultSet rset = select(selectQuery, paramList);
        try {
            BORROW_RECORD r ;
            while (rset.next()) {
                r = new BORROW_RECORD();
                r.RECORD_ID = rset.getString(1);
                r.BOOK_ID = rset.getString(2);
                r.PATRON_ID = rset.getString(3);
                r.ISBN = rset.getString(4);
                r.START_TIME = rset.getString(5);
                r.END_TIME = rset.getString(6);
                ivec.add(r);
            }

        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table BORROW_RECORD.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }

    //selectByKey

    public static BORROW_RECORD selectByRECORD_ID(String RECORD_ID) throws SQLException {
        BORROW_RECORD br = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE RECORD_ID = ?";
        Object[] paramList = new Object[]{RECORD_ID};
        Vector<BORROW_RECORD> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            br = ivec.elementAt(0);
        }
        return br;
    }

    //selectByAttribute
    public static Vector<BORROW_RECORD> selectByPATRON_ID(String PATRON_ID) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{PATRON_ID};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> selectByISBN(String ISBN) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[]{ISBN};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> selectByBOOK_ID(String BOOK_ID) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{BOOK_ID};
        return selectByQuery(selectQuery, paramList);
    }


    //selectSingle



    //selectByCondition
    public static Vector<BORROW_RECORD> selectByPATRON_NAME(String name) throws SQLException { //nested condition.
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE " + "PATRON_ID = " +
                "(SELECT PATRON_ID FROM PATRON WHERE PATRON_NAME = ?)";
        Object[] paramList = new Object[]{name.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }


    public static Vector<String> selectRECORD_ID_of_laterThan(String date) throws SQLException {
        Vector<String> ivec = new Vector<>();
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE START_TIME < ?";
        Object[] paramList = new Object[]{date};
        Vector<BORROW_RECORD> temp = selectByQuery(selectQuery, paramList);

        for (int i = 0; i < temp.size(); ++i) {
            ivec.add(temp.elementAt(i).RECORD_ID);
        }
        return ivec;
    }
    public static Vector<BORROW_RECORD> select_in_progress_borrow() throws SQLException {
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE END_TIME IS NULL"; //? 能判断真的是null？
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> select_records_of_time_range(String startDate, String endDate) throws SQLException {
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE " + "END_TIME > ? AND END_TIME < ?";
        Object[] paramList = new Object[]{startDate, endDate};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> selectByBookName(String book_name) throws SQLException {
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE " + " BOOK_ID IN "
                            + "(SELECT BOOK_ID FROM COLLECTION WHERE ISBN IN "+"" +
                             "(SELECT ISBN FROM BOOK_INFORMATION WHERE BOOK_NAME = ?))"; //注意这里的sql执行。
        Object[] paramList = new Object[]{book_name};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> select_in_progress_borrow_ofPerson(String patron_id) throws SQLException {
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE " + "END_TIME IS NULL AND PATRON_ID = ?";
        Object[] paramList = new Object[]{patron_id};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BORROW_RECORD> selectHistoryRecord() throws SQLException {
        String selectQuery = "SELECT * FROM BORROW_RECORD WHERE " + "END_TIME IS NOT NULL";
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }



    ////Update method:
    public static void endTheBorrowByRECORD_ID(String id) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "END_TIME = ? WHERE RECORD_ID = ?";
        Object[] paramList = new Object[]{LocalDate.now().toString(), id};
        update(updateQuery, paramList);
    }



    //deleteByKey
    public static void deleteByRECORD_ID(String RECORD_ID) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE RECORD_ID = ?";
        Object[] paramList = new Object[]{RECORD_ID};
        update(updateQuery, paramList);
    }
    //deleteBycondition
    public static void deleteBR_before(String date) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE " + "END_TIME < ?";
        Object[] paramList = new Object[]{date};
        update(updateQuery, paramList);
    }
    public static void deleteByCollectionID(String book_id) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE " + "BOOK_ID = ?";
        Object[] paramList = new Object[]{book_id};
        update(updateQuery, paramList);
    }

    public static void deleteByPATRON_ID (String patron_id) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE " + " PATRON_ID = ?";
        Object[] paramList = new Object[] {patron_id};
        update(updateQuery, paramList);
    }


    //insert

    public static void insertTuple (BORROW_RECORD o) throws SQLException {
        String insertQuery;
        Object[] paramList ;
        if (o.END_TIME == null) {
            paramList = new Object[5];
            insertQuery = "INSERT INTO BORROW_RECORD(RECORD_ID, BOOK_ID, PATRON_ID, ISBN, START_TIME, END_TIME) VALUES(?,?,?,?,?,NULL)";
        } else {
            paramList = new Object[6];
            insertQuery = "INSERT INTO BORROW_RECORD(RECORD_ID, BOOK_ID, PATRON_ID, ISBN, START_TIME, END_TIME)  VALUES(?,?,?,?,?,?)";
            paramList[5] = o.END_TIME;
        }

        paramList[0] = o.RECORD_ID;
        paramList[1] = o.BOOK_ID;
        paramList[2] = o.PATRON_ID;
        paramList[3] = o.ISBN;
        paramList[4] = o.START_TIME;

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<BORROW_RECORD> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (BORROW_RECORD o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table BORROW_RECORD");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }


}

