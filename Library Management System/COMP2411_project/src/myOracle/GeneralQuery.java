package myOracle;

import java.util.ArrayList;
import java.util.List;

public class GeneralQuery extends Table {
    public static List<Object[]> getAge () {
        String agePatronSQL = "SELECT AGE FROM PATRON";
        Object[] ageList = new Object[]{};
        return translateAllAtr(select(agePatronSQL, ageList), 1);
    }

     public static List<Object[]> getCopyCountAndName () {
         String copySQL = "SELECT DISTINCT BOOK_NAME,C.ISBN,COUNT(BOOK_ID) FROM COLLECTION C,BOOK_INFORMATION B WHERE C.ISBN = B.ISBN GROUP BY C.ISBN,BOOK_NAME ORDER BY COUNT(BOOK_ID) DESC";
         Object[] copyList = new Object[]{};
         return translateAllAtr(select(copySQL, copyList), 3);
    }

     public static List<Object[]> getReservNumberData () {
         String reserveNumberSQL = "SELECT DISTINCT BOOK_NAME,R.ISBN FROM RESERVE R,BOOK_INFORMATION B WHERE R.ISBN = B.ISBN GROUP BY R.ISBN,BOOK_NAME HAVING COUNT(*) > ?";
         Object[] reserveNumberList = new Object[]{2};
         return translateAllAtr(select(reserveNumberSQL, reserveNumberList), 2);
     }

      public static List<Object[]> getActivePatronData () {
          String activePatronSQL = "SELECT DISTINCT PATRON_NAME, B.PATRON_ID FROM PATRON P,BORROW_RECORD B WHERE P.PATRON_ID = B.PATRON_ID AND START_TIME >= '2020-01-01' GROUP BY B.PATRON_ID,PATRON_NAME HAVING COUNT(*) > ?";
          Object[] activePatronList = new Object[]{2};
          return translateAllAtr(select(activePatronSQL, activePatronList), 2);
      }

       public static List<Object[]> getOldBookData () {
           String oldBookSQL = "SELECT DISTINCT BOOK_NAME,ISBN,PUBLISH_TIME FROM BOOK_INFORMATION WHERE PUBLISH_TIME <= '2010-01-01' AND ISBN NOT IN (SELECT ISBN FROM BORROW_RECORD WHERE START_TIME >= '2020-01-01')";
           Object[] oldBookList = new Object[]{};
           return translateAllAtr(select(oldBookSQL, oldBookList), 3);
       }

        public static List<Object[]> bookBRData () {
            String sqlQuery = "SELECT DISTINCT BOOK_NAME,C.ISBN,COUNT(RECORD_ID) FROM BORROW_RECORD C,BOOK_INFORMATION B WHERE C.ISBN = B.ISBN GROUP BY C.ISBN,BOOK_NAME ORDER BY COUNT(RECORD_ID) DESC";
            Object[] parameterList = new Object[]{};
            return translateAllAtr(select(sqlQuery, parameterList), 3);
        }

        public static List<Object[]> bookBRDataCategory () {
            String sqlQuery = "SELECT DISTINCT CATEGORY,COUNT(RECORD_ID) FROM BORROW_RECORD C,BOOK_INFORMATION B WHERE C.ISBN = B.ISBN GROUP BY CATEGORY ORDER BY COUNT(RECORD_ID) DESC";
            Object[] parameterList = new Object[]{};
            return translateAllAtr(select(sqlQuery, parameterList), 2);
        }
}



//        String sql = "SELECT ISBN, BOOK_ID FROM COLLECTION WHERE STATE = ?";
//        Object[] paramList = new Object[] {1};
//        ResultSet rset = select(sql, paramList);
//        List<Object[]> result = translateAllAtr(rset, 2);

//        String sql = "SELECT ISBN, BOOK_ID FROM COLLECTION WHERE STATE = TRUE"; //???
//        Object[] paramList = new Object[] {};
//        List<Object[]> result = translateAllAtr(select(sql, paramList), 2);
//
//
//        for (Object[] array : result) {
//            System.out.printf("ISBN %s, BOOK_ID %s\n", (String)array[0],  (String) array[1]);
//        }

//想好分析思路
//
//1, 准备sql语句， 参数列表（如果有的话）
//2， 按模版执行 （转换结果时传入取得的attribute数量）
//
//外部程序处理List<Object> 分析结果，输出。
