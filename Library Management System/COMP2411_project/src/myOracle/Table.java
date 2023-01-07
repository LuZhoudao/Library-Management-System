package myOracle;

import oracle.jdbc.driver.OracleConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

 abstract class Table {
    protected static OracleConnection conn;

    public static void initializeTable(OracleConnection conn) {
        Table.conn = conn; //静态字段继承。
    }

    //insert模版：
    //input: 1， sql -- 待插入sql语句  "INSERT INTO ? VALUES (?,?,?,?)"   2， 插入的参数
    //output: 插入数据， 返回插入数据个数。 0 or 1
    protected static int insert(String sql, Object... paramList) throws SQLException {
        try {
//            String[] id_name = {(String) paramList[0]};

            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i < paramList.length+1; ++i) {
                if(paramList[i-1] != null)
                    ps.setObject(i, paramList[i-1]);
                else{
                    ps.setNull(i, Types.INTEGER);
                }

            }

            return ps.executeUpdate();



        } catch (SQLException e){
            SQLException e2 = e;
            System.out.println("Error in executing the sql code: " + sql);
            while(e2 != null){
                System.out.println("message: " + e2.getMessage());
                e2 = e2.getNextException();
            }
            throw e;
        }
    }

    //selection 模版：
    //input: 1, sql -- 待执行select语句， (SELECT ? FROM ? WHERE ...) 2, paramList -- 参数列表
    //ouput: ResultSet  case1: find -- rs  case2： not find -- empty rs rather than null !
    protected static ResultSet select (String sql, Object... paramList) {
        ResultSet rs = null;
        try  {
            PreparedStatement ps = conn.prepareStatement(sql);

            if (paramList != null) {
                for (int i = 0; i < paramList.length; ++i) {
                    ps.setObject(i+1, paramList[i]);
                }
            }
            rs = ps.executeQuery();

        } catch (SQLException e){
            System.out.println("Error in executing the sql code: " + sql);
            while(e != null){
                System.out.println("message: " + e.getMessage());
                e = e.getNextException();
            }
        }

        return rs;
    }

    //DELETE, UPDATE模版。
    protected static void update(String sql, Object... paramList){
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            if(paramList != null) {
                for (int i = 0; i < paramList.length; ++i) {
                    if(paramList[i] != null)
                        ps.setObject(i+1, paramList[i]);
                    else
                        ps.setObject(i+1, Types.NULL);
                }
            }
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error in executing the sql code: " + sql);
            while(e != null){
                System.out.println("message: " + e.getMessage());
                e = e.getNextException();
            }
        }
    }

    //translate模版
    protected static List<Object[]> translateAllAtr(ResultSet rset, int n) {
        List<Object[]> result = new ArrayList<>();
        try {
            while (rset.next()) {
                Object[] array = new Object[n];
                for (int i = 0; i < n; i++) {
                    array[i] = rset.getObject(i + 1);
                }
                result.add(array);
            }
            return result;

        } catch (SQLException e){
            System.out.println("Error in executing the tranlateAllAtr: ");
            while(e != null){
                System.out.println("message: " + e.getMessage());
                e = e.getNextException();
            }
        }
        return result;
    }
}
