package myOracle;


import oracle.jdbc.driver.*;

import java.util.Scanner;
import java.util.Vector;
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

import javax.xml.stream.Location;

public class COLLECTIONTable extends Table{
    public static String  TABLE_NAME = "COLLECTION";

    private COLLECTIONTable () {};

    //select method:
    private static Vector<COLLECTION> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<COLLECTION> ivec = new Vector<>();
        ResultSet rset = select(selectQuery, paramList);
        try  {
            COLLECTION r ;
            while (rset.next()) {
                r = new COLLECTION();
                r.BOOK_ID = rset.getString(1);
                r.ISBN = rset.getString(2);
                r.STATE = (rset.getInt(3) == 1);
                r.LOCATION = rset.getString(4);
                ivec.add(r);
            }

        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table COLLECTION.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }

    //selectByKey

    public static COLLECTION selectByBOOK_ID(String BOOK_ID) throws SQLException {
        COLLECTION br = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{BOOK_ID};
        Vector<COLLECTION> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            br = ivec.elementAt(0);
        }
        return br;
    }

    //selectByAttribute
    public static Vector<COLLECTION> selectByISBN(String ISBN) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[]{ISBN};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<COLLECTION> selectByLOCATION(String LOCATION) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE LOCATION = ?";
        Object[] paramList = new Object[]{LOCATION};
        return selectByQuery(selectQuery, paramList);
    }


    //selectSingle


    //selectByCondition
    public static Vector<COLLECTION> select_unavailable_COLLECTION() throws SQLException {
        String selectQuery = "SELECT * FROM COLLECTION WHERE " + "STATE = 0"; //?
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<COLLECTION> select_available_copies(String ISBN) throws SQLException {
        String selectQuery = "SELECT * FROM COLLECTION WHERE " + "ISBN = ? AND STATE = 1" ;
        Object[] paramList = new Object[]{ISBN};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<COLLECTION> select_available_COLLECTION() throws SQLException {
        String selectQuery = "SELECT * FROM COLLECTION WHERE " + "STATE = 1"; //?
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }



    ////Update method:
    public static void set_BOOK_available(String BOOK_ID) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "STATE = 1 WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{BOOK_ID};
        update(updateQuery, paramList);
    }

    public static void set_BOOK_unavailable(String BOOK_ID) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "STATE = 0 WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{ BOOK_ID};
        update(updateQuery, paramList);
    }
    public static void changeLocation(String BOOK_ID, String newLocation) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "LOCATION = ? WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{newLocation.toUpperCase() , BOOK_ID};
        update(updateQuery, paramList);
    }


    //deleteByKey
    public static void deleteByBOOK_ID(String BOOK_ID) {
        String updateQuery = "DELETE FROM " + TABLE_NAME + " WHERE BOOK_ID = ?";
        Object[] paramList = new Object[]{BOOK_ID};
        update(updateQuery, paramList);
    }
    //deleteBycondition

    public static void deleteByISBN (String ISBN) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[] {ISBN};
        update(updateQuery, paramList);
    }



    //insert

    public static void insertTuple (COLLECTION o) throws SQLException {
        String insertQuery;
        if (o.STATE) {
            insertQuery = "INSERT INTO COLLECTION(BOOK_ID, ISBN, STATE, LOCATION) VALUES(?,?,1,?)";
        } else {
            insertQuery = "INSERT INTO COLLECTION(BOOK_ID, ISBN, STATE, LOCATION) VALUES(?,?,0,?)";
        }

        Object[] paramList = new Object[3];
        paramList[0] = o.BOOK_ID;
        paramList[1] = o.ISBN;
        paramList[2] = o.LOCATION == null ? null : o.LOCATION.toUpperCase();

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<COLLECTION> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (COLLECTION o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table COLLECTION");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
