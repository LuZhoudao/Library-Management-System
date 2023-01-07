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

public class PATRONTable extends Table{
    public static String  TABLE_NAME = "PATRON";

    private PATRONTable () {};

    //select method:
    private static Vector<PATRON> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<PATRON> ivec = new Vector<>();
        ResultSet rset = select(selectQuery, paramList);
        try  {
            PATRON r ;
            while (rset.next()) {
                r = new PATRON();
                r.PATRON_ID = rset.getString(1);
                r.USER_PASSWORD = rset.getString(2);
                r.PATRON_NAME = rset.getString(3);
                r.AGE = rset.getString(4);
                r.ACTIVITY_STATE = rset.getInt(5) == 1;
                r.E_MAIL = rset.getString(6);
                r.GENDER = rset.getString(7);
                r.ADDRESS = rset.getString(8);
                r.ILLEGAL_TYPE = rset.getString(9);
                r.PHONE = rset.getString(10);
                ivec.add(r);
            }

        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table PATRON.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }


    //selectByKey

    public static PATRON selectByPATRON_ID(String PATRON_ID) throws SQLException {
        PATRON br = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{PATRON_ID};
        Vector<PATRON> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            br = ivec.elementAt(0);
        }
        return br;
    }

    //selectByAttribute
    public static Vector<PATRON> selectByGENDER(String GENDER ) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE  GENDER LIKE ?";
        Object[] paramList = new Object[]{"%" + GENDER.toUpperCase() + "%"};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<PATRON> selectByPATRON_NAME(String PATRON_NAME) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE PATRON_NAME = ?";
        Object[] paramList = new Object[]{PATRON_NAME.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }


    //selectSingle



    //selectByCondition
    public static Vector<PATRON> select_error_patron() throws SQLException {
        String selectQuery = "SELECT * FROM PATRON WHERE " + "ACTIVITY_STATE = 0";
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<PATRON> select_good_patron() throws SQLException {
        String selectQuery = "SELECT * FROM PATRON WHERE " + "ACTIVITY_STATE = 1";
        Object[] paramList = new Object[]{};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<PATRON> select_age_lowerThan(String age) throws SQLException {
        String selectQuery = "SELECT * FROM PATRON WHERE " + "AGE < ?";
        Object[] paramList = new Object[]{age};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<PATRON> select_age_largerThan(String age) throws SQLException {
        String selectQuery = "SELECT * FROM PATRON WHERE " + "AGE > ?";
        Object[] paramList = new Object[]{age};
        return selectByQuery(selectQuery, paramList);
    }




    ////Update method:
    public static void activiateThePatron(String patron_id) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "ACTIVITY_STATE = 1 WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{ patron_id};
        update(updateQuery, paramList);

        updateQuery = "UPDATE " + TABLE_NAME + " SET " + "ILLEGAL_TYPE = NULL WHERE PATRON_ID = ?";
        paramList = new Object[]{ patron_id};
        update(updateQuery, paramList);
    }

    public static void inactivateThePatron(String patron_id, String illegal_type) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "ACTIVITY_STATE = 0 WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{ patron_id};
        update(updateQuery, paramList);

        String updateQuery1 = "UPDATE " + TABLE_NAME + " SET " + "ILLEGAL_TYPE = ? WHERE PATRON_ID = ?";
        Object[] paramList1 = new Object[]{illegal_type, patron_id};
        update(updateQuery1, paramList1);
    }


    //deleteByKey
    public static void deleteByPATRON_ID(String PATRON_ID) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE PATRON_ID = ?";
        Object[] paramList = new Object[]{PATRON_ID};
        update(updateQuery, paramList);
    }
    //deleteBycondition



    //insert

    public static void insertTuple (PATRON o) throws SQLException {
        String insertQuery = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        Object[] paramList = new Object[9];
        if (o.ACTIVITY_STATE) {
            insertQuery = "INSERT INTO PATRON(PATRON_ID, USER_PASSWORD, PATRON_NAME, AGE,ACTIVITY_STATE,  E_MAIL, GENDER, ADDRESS, ILLEGAL_TYPE, PHONE) VALUES (?,?,?,?,1,?,?,?,?,?)";
        } else {
            insertQuery = "INSERT INTO PATRON(PATRON_ID, USER_PASSWORD, PATRON_NAME, AGE, ACTIVITY_STATE, E_MAIL, GENDER, ADDRESS, ILLEGAL_TYPE, PHONE) VALUES (?,?,?,?,0,?,?,?,?,?)";
        }
        paramList[0] = o.PATRON_ID;
        paramList[1] = o.USER_PASSWORD.toUpperCase();
        paramList[2] = o.PATRON_NAME.toUpperCase();
        paramList[3] = o.AGE;
//        paramList[4] = o.ACTIVITY_STATE ? 1 : 0;
        paramList[4] = o.E_MAIL.toUpperCase();
        paramList[5] = o.GENDER == null ? null : o.GENDER.toUpperCase();
        paramList[6] = o.ADDRESS == null ? null : o.ADDRESS.toUpperCase();
        paramList[7] = o.ILLEGAL_TYPE == null ? null : o.ILLEGAL_TYPE.toUpperCase();
        paramList[8] = o.PHONE;

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<PATRON> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (PATRON o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table PATRON");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
