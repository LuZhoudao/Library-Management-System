package myOracle;


import java.io.*;
import java.io.Console;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

import java.util.Scanner;
import java.util.Vector;
import java.util.List;

import exception_handler.*;

public class STAFFTable extends Table{
    public static String  TABLE_NAME = "STAFF";

    private STAFFTable () {};

    //select method:
    private static Vector<STAFF> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<STAFF> ivec = new Vector<>();
        try (ResultSet rset = select(selectQuery, paramList)) {
            STAFF r ;
            while (rset.next()) {
                r = new STAFF();
                r.STAFF_ID = rset.getString(1);
                r.PASSWORD = rset.getString(2);
                r.STAFF_NAME = rset.getString(3);
                r.POSITION = rset.getString(4);
                r.PHONE = rset.getString(5);
                r.E_MAIL = rset.getString(6);
                ivec.add(r);
            }

        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table STAFF.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }

    //selectByKey

    public static STAFF selectBySTAFF_ID(String STAFF_ID) throws SQLException {
        STAFF br = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE STAFF_ID = ?";
        Object[] paramList = new Object[]{STAFF_ID};
        Vector<STAFF> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            br = ivec.elementAt(0);
        }
        return br;
    }

    //selectByAttribute
    //input must not null
    public static Vector<STAFF> selectByNAME(String NAME) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = ?";
        Object[] paramList = new Object[]{NAME.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }


    //selectSingle



    //selectByCondition




    ////Update method:



    //deleteByKey
    public static void deleteBySTAFF_ID(String STAFF_ID) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE STAFF_ID = ?";
        Object[] paramList = new Object[]{STAFF_ID};
        update(updateQuery, paramList);
    }
    //deleteBycondition



    //insert

    public static void insertTuple (STAFF o) throws SQLException {
        String insertQuery = "INSERT INTO  STAFF(STAFF_ID, PASSWORD, STAFF_NAME, POSITION, PHONE, E_MAIL)  VALUES (?,?,?,?,?,?)";
        Object[] paramList = new Object[6];
        paramList[0] = o.STAFF_ID;
        paramList[1] = o.PASSWORD;
        paramList[2] = o.STAFF_NAME == null ? null : o.STAFF_NAME.toUpperCase();
        paramList[3] = o.POSITION == null ? null : o.POSITION;
        paramList[4] = o.PHONE;
        paramList[5] = o.E_MAIL;

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<STAFF> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (STAFF o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table STAFF");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
