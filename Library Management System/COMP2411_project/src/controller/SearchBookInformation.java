package controller;

import myOracle.BOOK_INFORMATION;
import myOracle.BOOK_INFORMATIONTable;
import myOracle.BORROW_RECORD;
import myOracle.Tools;

import java.util.HashSet;
import java.util.Vector;

public abstract class SearchBookInformation {
    private static String condition = "";


    //operation -- 执行操作与用户交互，搜索，1打印结果，2并返回。
    public static Vector<BOOK_INFORMATION> searchByBookName() throws Exception{

        int flag = 1;
        Vector<BOOK_INFORMATION> ivec = new Vector<>();
        while (flag == 1) {
            flag = 0;
            String bookName = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            if (bookName.equals("*")) {
                return ivec;
            }
            ivec = BOOK_INFORMATIONTable.selectByBOOK_NAME(bookName);
            if (!ivec.isEmpty()) {
                System.out.println("The information of book " + bookName.strip() + " are as follows");
                BOOK_INFORMATION.printTable(ivec);
            } else {
                System.out.println("Sorry, we cannot find the book.");
            }
        }
        return ivec;
    }

    public static Vector<BOOK_INFORMATION> searchByISBN ()  throws Exception{
//        Tools.printInputHint("Search book by book name");

        int flag = 1;
        Vector<BOOK_INFORMATION> ivec = new Vector<>();
        while (flag == 1) {
            flag = 0;
            String ISBN = BOOK_INFORMATION.getFromInput_ISBN();
            if (ISBN.equals("*")) {
//                Tools.printModuleOut("search by ISBN");
                return ivec;
            }
            //
            ivec = new Vector<BOOK_INFORMATION>();
            BOOK_INFORMATION bi = BOOK_INFORMATIONTable.selectByISBN(ISBN);
            if (bi != null) {
                ivec.add(bi);
            }
            //
            if (!ivec.isEmpty()) {
                System.out.println("The information of book with ISBN " + ISBN + " are as follows");
                BOOK_INFORMATION.printTable(ivec);
            } else {
                System.out.println("Sorry, we cannot find the book.");
            }

//            System.out.println("Do you want to do another search? Enter 1 to continue, 0 to quit");
//            flag = Tools.getIntByInput(1);
        }
//        Tools.printModuleOut("search by ISBN");
        return ivec;
    }

    //内部所需条件： 1， name contains, 2, author name, 3 publisher, 4, language 5, category, 6, time between.
    //子方法： searchBy__， 通过单一条件查询， 交互获取所需输入， 查询，输出向量不进行打印。  --不考虑用户中途退出情况。
    private static Vector<BOOK_INFORMATION> searchByBN () throws Exception {
            String subString = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            while (subString.equals("*")) {
                System.out.println("You are not allowed to quit here. finish your condition.");
                subString = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            }
            condition += "(Book name contains "  + subString.strip() + ")";

        return BOOK_INFORMATIONTable.selectNAME_contains(subString);
    }

    private static Vector<BOOK_INFORMATION> searchByAN () throws Exception {
        String subString = BOOK_INFORMATION.getFromInput_AUTHOR_NAME();
        while (subString.equals("*")) {
            System.out.println("You are not allowed to quit here. finish your condition.");
            subString = BOOK_INFORMATION.getFromInput_AUTHOR_NAME();
        }
        condition += "(Author name contains " + subString.strip() + ")";

        return BOOK_INFORMATIONTable.selectAN_contains(subString);
    }
    private static Vector<BOOK_INFORMATION> searchByPublisher () throws Exception {
        String subString = BOOK_INFORMATION.getFromInput_PUBLISHER();
        while (subString.equals("*")) {
            System.out.println("You are not allowed to quit here. finish your condition.");
            subString = BOOK_INFORMATION.getFromInput_PUBLISHER();
        }
        condition += "(Publisher Name contains contains " + subString.strip() + ")";

        return BOOK_INFORMATIONTable.selectPN_cantains(subString);
    }
    private static Vector<BOOK_INFORMATION> searchByLanguage () throws Exception {
        String language = BOOK_INFORMATION.getFromInput_LANGUAGE();
        while (language.equals("*")) {
            System.out.println("You are not allowed to quit here. finish your condition.");
            language = BOOK_INFORMATION.getFromInput_LANGUAGE();
        }
        condition += "(Languish is " + language.strip() + ")";

        return BOOK_INFORMATIONTable.selectByLANGUAGE(language);
    }
    private static Vector<BOOK_INFORMATION> searchByCategory () throws Exception {
        String category = BOOK_INFORMATION.getFromInput_CATEGORY();
        while (category.equals("*")) {
            System.out.println("You are not allowed to quit here. finish your condition.");
            category = BOOK_INFORMATION.getFromInput_CATEGORY();
        }
        condition += "(Category is " + category.strip() + ")";

        return BOOK_INFORMATIONTable.selectByCATEGORY(category);
    }
    private static Vector<BOOK_INFORMATION> searchByTime () throws Exception {
        boolean valid = false;
        String startTime = null;
        String endTime =  null;
        while (!valid) {
            startTime = BORROW_RECORD.getFromInput_START_TIME();
            endTime = BORROW_RECORD.getFromInput_END_TIME();
            valid = startTime.compareTo(endTime) < 0 || startTime.equals("*") || endTime.equals("*");
            if (! valid) {
                System.out.println("The input is valid! This section do not allow quit and end time must later than start time");
                System.out.println("Try again!");
            }
        }
        condition += "(Publish time between " + startTime + " and " + endTime + ")";

        return BOOK_INFORMATIONTable.select_publishTimeBetween(startTime, endTime);
    }

    //selectByMultiple condition --交互，完成一次查询
    public static Vector<BOOK_INFORMATION> searchByMultipleCondition () throws Exception {
        String choice = "1-- book name (contains..) / 2-- author name(cantains..) / 3--publisher / 4--language / 5-- category / 6--publish time";
        Tools.printChooseHint("", "Add one condition or quit.", choice);
        int choose = Tools.getIntByInput(6);
        while (choose == 0) {
            System.out.println("Sorry, you can not leave here please finish the condition.");
            choose = Tools.getIntByInput(6);
        }
        return switch (choose) {
            case 1 -> searchByBN();
            case 2 -> searchByAN();
            case 3 -> searchByPublisher();
            case 4 -> searchByLanguage();
            case 5 -> searchByCategory();
            case 6 -> searchByTime();
            default -> new Vector<BOOK_INFORMATION>();
        };
    }

    //不确定表达是否正确。
//    //update of condition and
//    public static void updateByAnd (HashSet<BOOK_INFORMATION> resultSet, Vector<BOOK_INFORMATION> ivec) {
//        resultSet.retainAll(ivec);
//    }
//    //update the resultSet
//    public static void updateByOr (HashSet<BOOK_INFORMATION> resultSet, Vector<BOOK_INFORMATION> ivec) {
//        resultSet.addAll(ivec);
//    }

    public static Vector<BOOK_INFORMATION> searchByComprehensiveCondition() throws Exception {
        Tools.printInputHint("Search book by comprehensive condition");

        int flag = 1;
        Vector<BOOK_INFORMATION> ivec = new Vector<>();
        HashSet<BOOK_INFORMATION> resultSet = new HashSet<>();

        while (flag == 1) {
            //build the condition
            condition = "";
            //
            flag = 0;
            //1, operation
            //first condition
            resultSet.addAll( searchByMultipleCondition());
            //other condition
            int flag1 = 1;
            while (flag1 == 1) {
                Tools.printChooseHint("", "You can add more contition", "1--And 2--Or");
                int choose = Tools.getIntByInput(2);
                switch (choose) {
                    case 1 :
                        condition += "  AND ";
                        resultSet.retainAll(searchByMultipleCondition());
                        break;

                    case 2 :
                        condition += "  OR ";
                        resultSet.addAll(searchByMultipleCondition());
                        break;

                    default : {
                        flag1 = 0;
                    }
                }
            }
            //translate and print out -- condition, table.
            System.out.println("Your requirement: books that " + condition);
            System.out.println("The books which meet your requirement are as follows");
            //转型可能出错
            for(BOOK_INFORMATION element:resultSet){
                ivec.add(element);
            }
            BOOK_INFORMATION.printTable(ivec);

            //2, next time
            //
//            System.out.println("Do you want to do another search? Enter 1 to continue, 0 to quit");
//            flag = Tools.getIntByInput(1);
            //
        }
        Tools.printModuleOut("search by comprehensive condition");
        return ivec;
    }
}
