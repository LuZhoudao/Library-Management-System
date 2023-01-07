package controller;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import myOracle.*;
import oracle.jdbc.driver.OracleConnection;



public abstract class AnalysisReport {
    private static String percentage (double number) {
        return String.format("%.2f", number*100) + "%";
    }
    //module
    public static void main(String[] args) throws Exception {
        //一,book information 分析：
        Tools.printModuleIn("Book information analysis");
        //
        //3. 统计热门书籍数量（超过2次判为当前热门书籍，短时间可尽快进货满足需求）
        List<Object[]> reserveNumberData = GeneralQuery.getReservNumberData();
        System.out.println("Statistical Table the Popular Books");
        System.out.println("Book name                      ISBN");
        System.out.println("------------------------------ ------------");
        for (Object[] array : reserveNumberData) {
            System.out.printf("%-30s %-13s\n", array[1], array[0]);
        }
        System.out.println();


        //二， collection 分析
        Tools.printModuleIn("book analysis");

        //unavailable  analysis
        Vector<COLLECTION> unavailable = COLLECTIONTable.select_unavailable_COLLECTION();
        Vector<COLLECTION> available = COLLECTIONTable.select_available_COLLECTION();
        int numOfUnavailable = unavailable.size();
        int numOfAvalaible = available.size();
        int total = numOfAvalaible  + numOfUnavailable;
        System.out.println("The library has " + total + " books in total");
        System.out.println(numOfAvalaible + " of them are available now. count for " + percentage(numOfAvalaible/(double) total));
        System.out.println(numOfUnavailable + " of them are unavailable. count for " + percentage((numOfUnavailable) / (double) total));
        System.out.println();



        //book_information collection 交互分析
        //2. 分析copy数量
        List<Object[]> copyData = GeneralQuery.getCopyCountAndName();

        System.out.println("Statistical Table of the Number of Copies of Books");
        System.out.println("Book Name                      ISBN          The Number of Copies");
        System.out.println("------------------------------ ------------- --------------------");
        for (Object[] array : copyData) {
            System.out.printf("%-30s %-13s %-20s\n", array[0], array[1], array[2]);
        }
        System.out.println();


        //5. 分析出版日期早于2010年但在2020年后未被借阅过的书籍，可能可以下架或更新版本
        List<Object[]> oldBookData = GeneralQuery.getOldBookData();

        System.out.println("Statistical Table of the Information of the Old Books Which Aren't Welcomed");
        System.out.println("Book name                      ISBN          Publish Time");
        System.out.println("------------------------------ ------------- ------------");
        for (Object[] array : oldBookData) {
            System.out.printf("%-30s %-13s %-12s\n", array[0], array[1], array[2]);
        }
        System.out.println();


        //三， patron 分析：
        Tools.printModuleIn("patron analysis");
        //state analysis
        Vector<PATRON> activateP = PATRONTable.select_good_patron();
        Vector<PATRON> inavtivateP = PATRONTable.select_error_patron();
        int num1 = activateP.size();
        int num2 = inavtivateP.size();
        int total1 = num1 + num2;
        System.out.println("There are " + total1 + " signed up patrons in total. ");
        System.out.printf("%d of them are activated. count for %s\n" , num1, percentage(num1/(double)total1));
        System.out.printf("%d of them are inactivated. count for %s\n", num2, percentage(num2/(double)total1) );
        System.out.println();

        //1. 分析图书馆的基本patron用户年龄构成
        List<Object[]> ages = GeneralQuery.getAge();
        ArrayList<Integer> ageNumList = new ArrayList<>();
        for (Object[] array : ages) {
            ageNumList.add(Integer.parseInt(((String) array[0]).strip()));
        }
        //0~12, 13~18, 19~30,31~40,41~60, > 60
        int[] ageData = new int[6];
        for (int i = 0; i < ageNumList.size(); i++) {
            int age = ageNumList.get(i);
            if (age < 13) ageData[0]++;
            else if (age <= 18) ageData[1]++;
            else if (age <= 30) ageData[2]++;
            else if (age <= 40) ;
            else if (age <= 60) ageData[4]++;
            else ageData[5]++;
        }
        System.out.println("Statistical Table of Patrons of Different Age Groups");
        System.out.println("Age Groups       quantity     percentage");
        System.out.println("---------------- --------     ----------");
        System.out.println(" 0  ~ 12        " + ageData[0] + "              " + percentage(ageData[0]/ (double) total1));
        System.out.println(" 13 ~ 18        " + ageData[1]+ "              " + percentage(ageData[1]/ (double) total1));
        System.out.println(" 19 ~ 30        " + ageData[2]+ "              " + percentage(ageData[2]/ (double) total1));
        System.out.println(" 31 ~ 40        " + ageData[3]+ "              " + percentage(ageData[3]/ (double) total1));
        System.out.println(" 41 ~ 60        " + ageData[4]+ "              " + percentage(ageData[4]/ (double) total1));
        System.out.println("   > 60         " + ageData[5]+ "              " + percentage(ageData[5]/ (double) total1));
        System.out.println();


        System.out.println("Statistical Table of Patrons of Different gender Groups");
        int numberOfFEMALE = PATRONTable.selectByGENDER("FEMALE").size();
        int numberOfMALE = PATRONTable.selectByGENDER("MALE").size() - numberOfFEMALE;
        System.out.println(numberOfMALE + " are MALE. Count for " + percentage(numberOfMALE  /(double) total1) );
        System.out.println(numberOfFEMALE + " are FEMALE. Count for " + percentage(numberOfFEMALE  /(double) total1) );
        System.out.println();



        //4. 统计活跃用户ID（借书3本以上（可改））,可以给与一定福利
        List<Object[]> activePatronData = GeneralQuery.getActivePatronData();

        System.out.println("Statistical Table information of the active patron who frequently borrowed the books these days.");
        System.out.println("Patron name                     patron ID");
        System.out.println("------------------------------- ---------");
        for (Object[] array : activePatronData) {
            System.out.printf("%-30s %-9s\n", array[0], array[1]);
        }
        System.out.println();


        // 四， borrow record 分析
        Tools.printModuleIn("borrow records analysis");
        //进行borrow
        Vector<BORROW_RECORD> inProgress = BORROW_RECORDTable.select_in_progress_borrow();
        System.out.println("There are " + inProgress + " in progress borrow now.");

        Vector<BORROW_RECORD> overdue= new Vector<>();
        String today = LocalDate.now().toString();
        for (BORROW_RECORD br1  : inProgress) {
            if (Tools.time_difference(br1.START_TIME, today) > 100) {
                overdue.add(br1);
            }
        }
        System.out.println(overdue.size() + " overdue records in total. The overdue in progress borrow record are as follows: ");
        BORROW_RECORD.printTable(overdue);
        System.out.println();


        //历史记录
        Vector<BORROW_RECORD> historyBR = BORROW_RECORDTable.selectHistoryRecord();
        int[] counts = new int[7];
        for (BORROW_RECORD br : historyBR) {
            int diff = Tools.time_difference(br.END_TIME, today);

            if (diff < 7) {
                counts[0]++;
            } else if (diff < 30) {
                counts[1]++;
            } else if (diff < 30*3) {
                counts[2]++;
            } else if (diff < 365) {
                counts[3]++;
            } else if (diff < 365*5) {
                counts[4]++;
            } else if (diff < 10 * 365) {
                counts[5]++;
            } else {
                counts[6]++;
            }
        }

        System.out.println("The system saves " + historyBR.size() + " history records. The time range of these history records are as follows");
        int total2 = historyBR.size();
        System.out.printf("within 1 week: %d records. Count for %s\n", counts[0], percentage(counts[0]/(double) total2));
        System.out.printf("1 week to 1 month: %d records. Count for %s\n", counts[1], percentage(counts[1]/(double) total2));
        System.out.printf("1 month to 1 quarter: %d records. Count for %s\n", counts[2], percentage(counts[2]/(double) total2));
        System.out.printf("1 quater to 1 year: %d records. Count for %s\n", counts[3], percentage(counts[3]/(double) total2));
        System.out.printf("1 year to 3 year: %d records. Count for %s\n", counts[4], percentage(counts[4]/(double) total2));
        System.out.printf("3 year to 10 year: %d records. Count for %s\n", counts[5], percentage(counts[5]/(double) total2));
        System.out.printf("10 years ago : %d records. Count for %s\n", counts[6], percentage(counts[6]/(double) total2));
        System.out.println();

        //borrow record 与collection交互分析
        System.out.println("According to the borrow records. The most popular 5 books in the library is as follows.");
        List<Object[]> bookBRData = GeneralQuery.bookBRData();
        System.out.println("Book Name                      ISBN          number of borrow records");
        System.out.println("------------------------------ ------------- ------------------------");
        for (int i = 0; i  < 5; ++i) {
            Object[] array = bookBRData.get(i);
            System.out.printf("%-30s %-13s %-20s\n", array[0], array[1], array[2]);
        }
        System.out.println();


        System.out.println("According to the borrow records. The most popular 3 categories of books is as follows.");
        List<Object[]> bookBRDataCategory = GeneralQuery.bookBRDataCategory();
        System.out.println("Category                         number of borrow records");
        System.out.println("-----------------------------    ------------------------");
        for (int i = 0; i  < 5; ++i) {
            Object[] array = bookBRDataCategory.get(i);
            System.out.printf("%-30s %-13s\n", array[0], array[1]);
        }
        System.out.println();



        //borrow record 与用户交互分析
        //逾期记录： 电话提醒用户还书。


       //用户借阅数量排行榜 前5。


        //平均借阅数量： 女性，男性。



        //五， reserve 分析
        Tools.printModuleIn("in progress reserve analysis");
        Vector<RESERVE> inprogressReserve = RESERVETable.select_all ();
        System.out.println("There are " + inprogressReserve.size() + " in progress reserves");
        Vector<RESERVE> unavailableReserve = RESERVETable.select_indue_unavailale_reserve();
        System.out.println(unavailableReserve.size() + " patrons are waiting for the books.");
        RESERVE.printTable(unavailableReserve);
        System.out.println();
        //有时间再改吧
    }
}





