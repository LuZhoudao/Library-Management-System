package myOracle;

import java.io.*;
import java.io.Console;
import java.sql.*;

import jdk.jfr.Category;
import oracle.jdbc.driver.*;
import oracle.sql.*;

import java.util.Scanner;
import java.util.Vector;
import java.util.List;

import exception_handler.*;

public class BOOK_INFORMATIONTable extends Table{
    public static String  TABLE_NAME = "BOOK_INFORMATION";

    private BOOK_INFORMATIONTable () {};

    //select method:
    private static Vector<BOOK_INFORMATION> selectByQuery (String selectQuery, Object[] paramList) throws SQLException {
        Vector<BOOK_INFORMATION> ivec = new Vector<>();
        ResultSet rset = select(selectQuery, paramList);
        try {
            BOOK_INFORMATION r ;
            while (rset.next()) {
                r = new BOOK_INFORMATION();
                r.ISBN = rset.getString(1);
                r.BOOK_NAME = rset.getString(2);
                r.CATEGORY = rset.getString(3);
                r.AUTHOR_NAME = rset.getString(4);
                r.PUBLISHER = rset.getString(5);
                r.LANGUAGE = rset.getString(6);
                r.PUBLISH_TIME = rset.getString(7);
                r.DESCRIPTION = rset.getString(8);
                //新增方法： 得到drive attribute--需要测试。 在取对象时赋值
                r.available_copies = COLLECTIONTable.select_available_copies(r.ISBN).size();
                //
                ivec.add(r);
            }

        } catch (NullPointerException e) {
            System.out.println("\n\nNullPointerException in selectQuery of table BOOK_INFORMATION.");
            e.printStackTrace();
            System.out.println("The return vector has length: " + ivec.size());
        }
        return ivec;
    }

    //selectByKey

    public static BOOK_INFORMATION selectByISBN(String ISBN) throws SQLException {
        BOOK_INFORMATION br = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[]{ISBN};
        Vector<BOOK_INFORMATION> ivec = selectByQuery(selectQuery, paramList);
        if (!ivec.isEmpty()) {
            br = ivec.elementAt(0);
        }
        return br;
    }

    //selectByAttribute
    public static Vector<BOOK_INFORMATION> selectByBOOK_NAME(String BOOK_NAME) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE BOOK_NAME = ?";
        Object[] paramList = new Object[]{BOOK_NAME.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BOOK_INFORMATION> selectByCATEGORY(String CATEGORY) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE CATEGORY = ?";
        Object[] paramList = new Object[]{CATEGORY.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BOOK_INFORMATION> selectByLANGUAGE(String LANGUAGE) throws SQLException {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE LANGUAGE = ?";
        Object[] paramList = new Object[]{LANGUAGE.toUpperCase()};
        return selectByQuery(selectQuery, paramList);
    }

    public static String selectBNAME_from_BOOK_ID(String book_id) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "ISBN IN " +
                             "(SELECT ISBN FROM COLLECTION WHERE BOOK_ID = ?)"; //!!sql
        Object[] paramList = new Object[]{book_id};
        Vector<BOOK_INFORMATION>  ivec =  selectByQuery(selectQuery, paramList);
        if (ivec.isEmpty()) {
            return null;
        } else {
            return ivec.elementAt(0).BOOK_NAME;
        }
    }

    public static String selectBNAME_from_ISBN(String book_id) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "ISBN = ?"; //!!sql
        Object[] paramList = new Object[]{book_id};
        Vector<BOOK_INFORMATION>  ivec =  selectByQuery(selectQuery, paramList);
        if (ivec.isEmpty()) {
            return null;
        } else {
            return ivec.elementAt(0).BOOK_NAME;
        }
    }


    //selectSingle


    //selectByCondition
//    内部所需条件： 1， name contains, 2, author name, 3 publisher, 4, language 5, category, 6, time between.
    public static Vector<BOOK_INFORMATION> selectNAME_contains(String subString) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "BOOK_NAME LIKE ?"; //!!!!!
        Object[] paramList = new Object[]{"%" + subString.toUpperCase() + "%"};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BOOK_INFORMATION> selectAN_contains(String subString) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "AUTHOR_NAME LIKE ?";
        Object[] paramList = new Object[]{"%" + subString.toUpperCase() + "%"};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BOOK_INFORMATION> selectPN_cantains(String subString) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "PUBLISHER LIKE ?";
        Object[] paramList = new Object[]{"%" + subString.toUpperCase() + "%"};
        return selectByQuery(selectQuery, paramList);
    }

    public static Vector<BOOK_INFORMATION> select_publishTimeBetween(String startTime, String endTime) throws SQLException {
        String selectQuery = "SELECT * FROM BOOK_INFORMATION WHERE " + "PUBLISH_TIME > ? AND PUBLISH_TIME < ?";
        Object[] paramList = new Object[]{startTime, endTime};
        return selectByQuery(selectQuery, paramList);
    }




    ////Update method:
    public static void changeDescription(String newDes, String isbn) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "DESCRIPTION = ? WHERE ISBN = ?";
        Object[] paramList = new Object[]{newDes, isbn};
        update(updateQuery, paramList);
    }

    //not allow input null
    public static void changeCategory (String name, String newName) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "CATEGORY = ? WHERE CATEGORY = ?";
        Object[] paramList = new Object[]{newName.toUpperCase(), name};
        update(updateQuery, paramList);
    }



    //deleteByKey
    public static void deleteByISBN(String ISBN) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE ISBN = ?";
        Object[] paramList = new Object[]{ISBN};
        update(updateQuery, paramList);
    }
    //deleteBycondition
    public static void deleteByBOOK_NAME(String name) {
        String updateQuery = "DELETE " + TABLE_NAME + " WHERE " + "BOOK_NAME = ?";
        Object[] paramList = new Object[]{name.toUpperCase()};
        update(updateQuery, paramList);
    }


    //insert
    public static void insertTuple (BOOK_INFORMATION o) throws SQLException {
        String insertQuery = "INSERT INTO BOOK_INFORMATION(ISBN, BOOK_NAME, CATEGORY, AUTHOR_NAME, PUBLISHER, LANGUAGE, PUBLISH_TIME, DESCRIPTION)  VALUES (?,?,?,?,?,?,?,?)";
        Object[] paramList = new Object[8];
        paramList[0] = o.ISBN;
        paramList[1] = o.BOOK_NAME.toUpperCase();
        paramList[2] = o.CATEGORY == null ? null :  o.CATEGORY.toUpperCase();
        paramList[3] = o.AUTHOR_NAME == null ? null : o.AUTHOR_NAME.toUpperCase();
        paramList[4] = o.PUBLISHER  == null ? null : o.PUBLISHER.toUpperCase();
        paramList[5] = o.LANGUAGE  == null ? null : o.LANGUAGE.toUpperCase();
        paramList[6] = o.PUBLISH_TIME == null ? null : o.PUBLISH_TIME;
        paramList[7] = o.DESCRIPTION == null ? null : o.DESCRIPTION;

        insert(insertQuery, paramList);
    }

    public static void insertObjects (Vector<BOOK_INFORMATION> objects) throws SQLException {
        try {
            conn.setAutoCommit(false);
            for (BOOK_INFORMATION o : objects) {
                insertTuple(o);
            }
            conn.commit();

        } catch (SQLException e) {
            System.out.println("\n\nError in insertObjects of table BOOK_INFORMATION");
            System.out.println("All the insert operation have been canceled.");
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}


