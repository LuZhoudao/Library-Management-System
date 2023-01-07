package controller;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Vector;

//import jdk.incubator.foreign.CLinker;
import myOracle.*;


public abstract class PatronPage {
    private static String patron_id = null;

    private static PATRON patron = null;

    private abstract static class Search_for_book {
        //处理过程： 由搜索结果到 预定， 到借阅。
        //input ： any form
        private static void searchToR_B (Vector<BOOK_INFORMATION> books) throws Exception{
            if (books.isEmpty()) {
                return;
            }
            System.out.println("Do you want to borrow or reserve a book from these books?  1--yes, 0--no");
            int choose = Tools.getIntByInput(1);
            if (choose == 1) {
                System.out.println("Enter 1 to borrow one, enter 0 to reserve one");
                choose = Tools.getIntByInput(1);
                if (choose == 1) {
                    Borrow.dealWithBorrow(books);
                } else {
                    Reserve.dealWithReserve(books);
                }
            }

        }

        //module
        public static void main(String[] args) throws Exception {
            Vector<BOOK_INFORMATION> ivec = new Vector<>();

            while (true) {
                //1, wait 0.5 second
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ignored) {
//
//                }
                Tools.printModuleIn("search for book information (patron)");

                //2， 提示信息。
                String description = "You can add several conditions to search for book information";
                String choice = "1-- search by book name / 2-- search by ISBN  3-- search by comprehensive condition";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);

                switch (choose) {
                    case 1 :
                        ivec = SearchBookInformation.searchByBookName();
                        searchToR_B(ivec);
                        break;
                    case 2 :
                        ivec = SearchBookInformation.searchByISBN();
                        searchToR_B(ivec);
                        break;

                    case 3 :
                        ivec = SearchBookInformation.searchByComprehensiveCondition();
                        searchToR_B(ivec);
                        break;

                    default : {
                        Tools.printModuleOut("search for book information (patron)");
                        return;
                    }
                }
                //搜索循环执行，系统对每一次搜索提供进一步处理选项，联系借书与预订功能。
                //ivec处理，业务逻辑联系。 -- borrow， reserve
            }
        }
    }

    private abstract static class Notification {
        //operation
        //得到信息并打印， 返回所取结果
        private static Vector<BORROW_RECORD> get_borrow_record_message() throws Exception{
            Vector<BORROW_RECORD> inProgressBR = BORROW_RECORDTable.select_in_progress_borrow_ofPerson(patron_id);
            int numberOfBR = inProgressBR.size();
            if (numberOfBR == 0) {
                System.out.println("You do not have any in progress borrow.");
                return inProgressBR;
            }
            //print
            Tools.printInputHint("You have " + numberOfBR + " in progress borrow.");
            int count = 1;
            for (BORROW_RECORD br : inProgressBR) {
                String book_name = BOOK_INFORMATIONTable.selectBNAME_from_BOOK_ID(br.BOOK_ID);
                int days = 100 - Tools.time_difference(br.START_TIME, LocalDate.now().toString());
                if (days < 0) {
                    System.out.printf("%d,  book name: %s   book id: %s  start from %s      This borrow is overdue, Please return it now!\n", count++, book_name.strip(), br.BOOK_ID, br.START_TIME);

                } else {
                    System.out.printf("%d,  book name: %s   book id: %s  start from %s      You still have %d days left to return the book\n", count++, book_name.strip(), br.BOOK_ID, br.START_TIME, days);
                }
            }
            return inProgressBR;
        }

        private static Vector<RESERVE> get_reserve_message() throws Exception{
//            Tools.printLine();
            System.out.println();


            Vector<RESERVE> reserves = RESERVETable.selectByPATRON_ID(patron_id);
//            System.out.println(reserves.size());

            Vector<RESERVE> now_available_reserves = new Vector<>();
            if (reserves.isEmpty()) {
                System.out.println("You do not have in progress reserve now.");
                return now_available_reserves;
            }
            //deal with the available reserve: case1 overdue -- remind and delete the record. case 2: in progress --remind.
            //deal with the unavailable reserve: case3 overdue -- remind and delete the record  case4 : in progress -- save and return: no! we should print the message about the reserve first and return.
            String today = LocalDate.now().toString();
            Tools.printInputHint("You have " + reserves.size() + " in progress reserves.");
            for (RESERVE r : reserves) {
                String bookName = BOOK_INFORMATIONTable.selectBNAME_from_ISBN(r.ISBN);
                //available
                if (r.BOOK_STATE) {
                    //case 1
                    if (Tools.time_difference(r.DEADLINE, today) >             0) { //overdue
                            System.out.println("Your reserve for book " + bookName.strip()+ "    have been canceled because you do not come to catch the book two days after reserve.");
                            RESERVETable.deleteByPI(patron_id, r.ISBN);
                            //case 2
                    } else { // in progress
                        System.out.println("You have a in progress reserve for book " + bookName.strip() + ".    You are supposed to come to catch the book before " + r.DEADLINE);
                    }

                } else { //unavailable
                    // case 3
                    if (Tools.time_difference(r.DEADLINE, today) < 0) { //overdue
                        System.out.println("Your reserve for " + bookName.strip() + "    have been canceled, because the system will only save the reserve record for 100 days.");
                        System.out.println("Sorry about that!");
                        RESERVETable.deleteByPI(patron_id, r.ISBN);
                        //case 4
                    } else { // in progress
                        //4(1) have copies now
                        if (!COLLECTIONTable.select_available_copies(r.ISBN).isEmpty()) {
                            System.out.println("The book " + bookName.strip()  + "    reserved by you is available now!");
                            now_available_reserves.add(r);
                        } else {
                            System.out.println("The book " + bookName.strip() + "    reserved by you is still unavailable. We will notify you when there is available copy.");
                        }
                    }

                }
            }
            return now_available_reserves;
        }

//        private static void dealWithBorrow (Vector<BORROW_RECORD> borrows) throws Exception{
//
//        }

        private static void dealWith_now_available_reserve(Vector<RESERVE> now_available_reserves) throws Exception {
            for (RESERVE r : now_available_reserves) {
                String bookName = BOOK_INFORMATIONTable.selectBNAME_from_ISBN(r.ISBN);
                System.out.println("The book " + bookName.strip() + " is available now. Do you want to come to catch the book in 2 days? 1--yes, 0--no");
                int choose = Tools.getIntByInput(1);
                if (choose == 1) {
                    String ISBN = r.ISBN;
                    Vector<COLLECTION> available_copies = COLLECTIONTable.selectByISBN(ISBN);
                    COLLECTION copy = available_copies.elementAt(0);
                    String ddl = Tools.time_days_later(LocalDate.now().toString(), 2);
                    RESERVE newR = new RESERVE(copy.BOOK_ID, ISBN, patron_id, ddl, true);
                    //加进表格
                    RESERVETable.deleteByPI(patron_id, ISBN);
                    RESERVETable.insertTuple(newR);
                    //lock for two days
                    System.out.println("We will lock the copy for two days for you come to catch. If you do not come to borrow within two days, this reserve will become invalid.");
                    COLLECTIONTable.set_BOOK_unavailable(copy.BOOK_ID);
                }
            }
        }

        //module
        public static void main(String[] args) throws Exception {
            Tools.printModuleIn("Notification");
            //
//            Tools.notification("Notification", "Here is a notification for you:");

            Vector<BORROW_RECORD> borrows = get_borrow_record_message();
            Vector<RESERVE>  now_available_reserves = get_reserve_message();

//            dealWithBorrow(borrows);
            System.out.println();
            Tools.printDownLine();
            //

            dealWith_now_available_reserve(now_available_reserves);
        }
    }

    //选择： 多 -- 选1or不选， 单--是否选 空--no   返回值： BI or null
    private static BOOK_INFORMATION chooseFrom (Vector<BOOK_INFORMATION> books) {
        BOOK_INFORMATION book = null;
        if (books.isEmpty()) {
            return null;
        }
        if (books.size() == 1) {
            book = books.elementAt(0);
            System.out.println("Are you sure to choose book " + book.BOOK_NAME.strip() + "?  1--yes 0 no");
            int choose = Tools.getIntByInput(1);
            if (choose == 1) {
                return book;
            } else {
                return null;
            }
        } else {
            System.out.println("Enter the index (start from 1)of book  that you want. If you want to cancel the borrow, enter 0.");
            int choose = Tools.getIntByInput(books.size());
            if (choose == 0) {
                return null;
            } else {
                book = books.elementAt(choose-1);
                return book;
            }
        }
    }


    private abstract static class Borrow {
        //operation
        private static void borrowByISBN() throws Exception {
            //find the book information
            Vector<BOOK_INFORMATION> ivec = new Vector<>();
            String ISBN = BOOK_INFORMATION.getFromInput_ISBN();
            if (ISBN.equals("*")) {
                return;
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
                return;
            }

            // deal with the borrow
            dealWithBorrow(ivec);
        }


        private static void borrowByName() throws Exception {

            Vector<BOOK_INFORMATION> ivec = new Vector<>();
            String bookName = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            if (bookName.equals("*")) {
                return;
            }
            ivec = BOOK_INFORMATIONTable.selectByBOOK_NAME(bookName);
            if (!ivec.isEmpty()) {
                System.out.println("The information of book " + bookName.strip() + " are as follows");
                BOOK_INFORMATION.printTable(ivec);
            } else {
                System.out.println("Sorry, we cannot find the book.");
                return;
            }

            dealWithBorrow(ivec);
        }


        //处理borrow。 对需要borrow的书籍范围进行处理。 不需要外部处理
        public static void dealWithBorrow(Vector<BOOK_INFORMATION> books) throws Exception {
            BOOK_INFORMATION book = chooseFrom(books);

            if (book == null) {
                System.out.println("Cancel the borrow.");
            } else {
                borrowOne(book);
            }
        }


        //对有效bi完成一次borrow
        private static void borrowOne(BOOK_INFORMATION book) throws Exception {
            if (book == null) {
                return;
            }

            if (book.available_copies == 0) {//无--转到预定
                System.out.println("Sorry. no copiye of these kind of book are available now.");
                System.out.println("Do you want to reserve this book ? 1--yes, 0--no");
                int choose = Tools.getIntByInput(1);
                if (choose == 1) {
                    Reserve.reserveOne(book);
                }
            } else {//有
                Vector<COLLECTION> available_copies = COLLECTIONTable.select_available_copies(book.ISBN); //not empty
                COLLECTION copy = available_copies.elementAt(0);
                //加入记录
                COLLECTIONTable.set_BOOK_unavailable(copy.BOOK_ID);
                //String RECORD_ID, String BOOK_ID, String PATRON_ID, String ISBN, String START_TIME, String END_TIME
                BORROW_RECORD br = new BORROW_RECORD(
                        BORROW_RECORD.generateRECORD_ID(), copy.BOOK_ID, patron_id, copy.ISBN,
                        LocalDate.now().toString(), null
                );
                BORROW_RECORDTable.insertTuple(br);
                //取消预订
                RESERVE reserve = RESERVETable.selectPI(patron_id, book.ISBN);
                if (reserve != null) {
                    System.out.println("Your reserve for this book have finished.");
                    RESERVETable.deleteByPI(patron_id, book.ISBN);
                }
                System.out.println("Successfully borrow the book.");
            }
        }


        //module
        public static void main(String[] args) throws Exception {

            while (true) {
                //1, wait 0.5 second
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ignored) {
//
//                }


                //2， 提示信息。
                Tools.printModuleIn("borrow_book");

                //处理最大借阅数量
                int numberOfBR = BORROW_RECORDTable.select_in_progress_borrow_ofPerson(patron_id).size();
                if (numberOfBR >= 3) {
                    System.out.println("Sorry, you are 3 in progress borrow now. Our library only allow 3 borrow at a time.");
                    Tools.printModuleOut("borrow_book");
                    return;
                }

                String description = "Make a new borrow here !";
                String choice = "1-- borrow by book ISBN  2--borrow by book name";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 2;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1 -> borrowByISBN();
                    case 2 -> borrowByName();
                    default -> {
                        Tools.printModuleOut("borrow_book");
                        return;
                    }
                }

                numberOfBR = BORROW_RECORDTable.select_in_progress_borrow_ofPerson(patron_id).size();
                if (numberOfBR >= 3) {
                    return;
                }
            }


        }

    }
    private abstract static class Return_book {
        //operation

        //处理还书过程： 输入正在进行的借阅， 进行交互，还书。
        public static void returnBooks(Vector<BORROW_RECORD> inProgressBR) throws SQLException {
            int numberOfBR = inProgressBR.size();
            if (numberOfBR == 0) { //check
                System.out.println("You do not have in progress borrow.");
                return;
            }
            int flag = 1;
            while (flag == 1) {
                //print
                Tools.printInputHint("You have " + numberOfBR + " in progress borrow.");
                int count = 1;
                for (BORROW_RECORD br : inProgressBR) {
                    String book_name = BOOK_INFORMATIONTable.selectBNAME_from_BOOK_ID(br.BOOK_ID);
                    System.out.printf("%d,  book name: %s   book id: %s  start from %s\n", count++, book_name.strip(), br.BOOK_ID, br.START_TIME);
                }
                //choose
                System.out.println("Please enter the index(start from 1) of book that you want to return. Enter 0 to cancel this operation");
                int choose = Tools.getIntByInput(numberOfBR);
                if (choose == 0) {
                    System.out.println("Cancel the return operation.");
                    return;
                }
                //return
                String RECORD_ID = inProgressBR.elementAt(choose - 1).RECORD_ID;
                BORROW_RECORDTable.endTheBorrowByRECORD_ID(RECORD_ID);
                System.out.println("The book have successfully returned.");
                numberOfBR--;
                inProgressBR.remove(choose - 1);
                //next
                if (numberOfBR > 0) {
                    System.out.println("Do you want to return another book? 1-- yes 0--no");
                    flag = Tools.getIntByInput(1);
                } else { //!!
                    System.out.println("All the books have been returned.");
                    flag = 0;
                }
                //
//                if (numberOfBR ==0) {
//                    System.out.println("All the books have been returned.");
//                }
//                flag = 0;
                //
            }
        }


        //module
        public static void main(String[] args) throws Exception {


            //1, wait 0.5 second
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {

            }

            //2， 提示信息。
            Tools.printModuleIn("return_book");

            //3,查询进行中借阅并打印
            Vector<BORROW_RECORD> inProgressBR = BORROW_RECORDTable.select_in_progress_borrow_ofPerson(patron_id);

            returnBooks(inProgressBR);

            Tools.printModuleOut("return book");

        }
    }

    private abstract static class Reserve {
        //operation
        private static void reserveByISBN () throws Exception {
            //find the book information
            Vector<BOOK_INFORMATION> ivec = new Vector<>();
            String ISBN = BOOK_INFORMATION.getFromInput_ISBN();
            if (ISBN.equals("*")) {
                return;
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
                return;
            }

            dealWithReserve(ivec);
        }

        private static void reserveByNAME() throws Exception {

            Vector<BOOK_INFORMATION> ivec = new Vector<>();
            String bookName = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            if (bookName.equals("*")) {
                return;
            }
            ivec = BOOK_INFORMATIONTable.selectByBOOK_NAME(bookName);
            if (!ivec.isEmpty()) {
                System.out.println("The information of book " + bookName.strip() + " are as follows");
                BOOK_INFORMATION.printTable(ivec);
            } else {
                System.out.println("Sorry, we cannot find the book.");
                return;
            }

            dealWithReserve(ivec);
        }

        public static void dealWithReserve(Vector<BOOK_INFORMATION> books) throws Exception{
            BOOK_INFORMATION book = chooseFrom(books);

            if (book == null) {
                System.out.println("Cancel the reserve.");
            } else {
                reserveOne(book);
            }
        }

        //!!
        // 完成一次new预定： 注意业务逻辑。 1,  available -- lock one copy. 2, unavailable -- wait
        private static void reserveOne (BOOK_INFORMATION book) throws Exception {
            if (RESERVETable.selectPI(patron_id, book.ISBN) != null) { //!
                System.out.println("You have already made the same reserve.");
                return;
            }

            Vector<COLLECTION> available_copies = COLLECTIONTable.select_available_copies(book.ISBN);
            if (available_copies.size() == 0) { // unavailable
                System.out.println("Sorry. There are no available copies of these kind of book. We will save your reserve record, and notify you when a copy is available.");
                //落实这个函数
                String ddl = Tools.time_days_later(LocalDate.now().toString(), 100);
                //落实book id 为null （1， 数据book id 为null 时 book state 为 false 2， 方法--打印）
                RESERVE newR = new RESERVE(null, book.ISBN, patron_id, ddl, false);

            } else {
                COLLECTION copy = available_copies.elementAt(0);
                String ddl = Tools.time_days_later(LocalDate.now().toString(), 2);
                RESERVE newR = new RESERVE(copy.BOOK_ID, book.ISBN, patron_id, ddl, true);
                //加进表格
                RESERVETable.deleteByPI(patron_id, book.ISBN);
                RESERVETable.insertTuple(newR);
                //lock for two days
                System.out.println("We will lock the copy for two days for you come to catch. If you do not come to borrow within two days, this reserve will become invalid.");
                COLLECTIONTable.set_BOOK_unavailable(copy.BOOK_ID);
            }
        }

        //结束预定： case1 预定后借到书
        public static void finishReserve (String ISBN) throws Exception{
            RESERVETable.deleteByPI(patron_id,ISBN);
        }

        //由等待状态到取书
        public static void changeWaitToCatch (String ISBN) throws Exception {
            RESERVE originalR = RESERVETable.selectPI(patron_id, ISBN);
            if (originalR == null) {
                return;
            }
            //String BOOK_ID, String ISBN, String PATRON_ID, String DEADLINE, boolean BOOK_STATE
            Vector<COLLECTION> copies = COLLECTIONTable.selectByISBN(originalR.ISBN);
            if (copies.isEmpty()) {
                return;
            }
            COLLECTION copy = copies.elementAt(0);
            String ddl = Tools.time_days_later(LocalDate.now().toString(), 2);
            RESERVE newR = new RESERVE(copy.BOOK_ID, ISBN, patron_id, ddl, true);
            //加进表格
            RESERVETable.deleteByPI(patron_id, ISBN);
            RESERVETable.insertTuple(newR);
            //lock for two days
            System.out.println("We will lock the copy for two days for you come to catch. If you do not come to borrow within two days, this reserve operation will be canceled.");
            COLLECTIONTable.set_BOOK_unavailable(copy.BOOK_ID);
        }


        //module
        public static void main(String[] args) throws Exception {

            while (true) {
                //1, wait 0.5 second
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ignored) {
//
//                }

                //2， 提示信息。
                Tools.printModuleIn("reserve the book");

                int numberOfReserve = RESERVETable.selectByPATRON_ID(patron_id).size();
                if (numberOfReserve >= 3) {
                    System.out.println("You are 3 in progress reserve. We only allow 3 reserve at most.");
                    Tools.printModuleOut("reserve the book");
                    return;
                }

                String description = "If the book is available, we will lock a collection for you. You are supposed to come to borrow that book within two days. If the book is not available now, we will notify you until a collection is available.";
                System.out.println(description);
                System.out.println("Enter an integer to make the choose (enter 0 to quit): 1--reserve by ISBN 2--reserve by book name");
                //3//选择
                int numberOfOperation = 2;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1 -> reserveByISBN();
                    case 2 -> reserveByNAME();
                    default -> {
                        Tools.printModuleOut("reserve the book");
                        return;
                    }
                }
            }

        }
    }


    //releaseCopies 自动调用。释放过期锁定的copy
    private static void releaseCopies () throws Exception {
        Vector<RESERVE> reserves = RESERVETable.select_overdue_available_reserve();
        for (RESERVE r : reserves) {
            COLLECTIONTable.set_BOOK_available(r.BOOK_ID);
        }
    }


    // page
    //args[0] is patron id
    public static void main(String[] args) throws Exception {
        //initialize
        releaseCopies();

        if (args.length > 0) {
            patron_id = args[0];
            if (patron_id != null) {
                patron = PATRONTable.selectByPATRON_ID(patron_id);
            }
        }

        //notification
        boolean first_time = true;
        while (true) {
            try {
                //1， 等待
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//
//                }

                //2， 提示信息。
                System.out.print("\n\n\n\n\n");
                Tools.printELine();
                System.out.println(Tools.patronPageIn); //in hint (graphical)

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }


                //显示通知
                if (first_time) {
                    Tools.printWelcomeMessage(patron == null ? "" : patron.PATRON_NAME.strip());
                    Notification.main(null);
                    first_time = false;
                }
                //
                String description = "";
                String choice = "1--search for book information / 2--borrow the book / 3--return the book / 4-- reserve a book";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int choose = Tools.getIntByInput(4);
                switch (choose) {
                    case 1:
                        Search_for_book.main(null);
                        break;

                    case 2:
                        Borrow.main(null);
                        break;

                    case 3:
                        Return_book.main(null);
                        break;

                    case 4:
                        Reserve.main(null);
                        break;

                    default:{
                        System.out.println(Tools.patronPageOut);
                        return;
                    }
                }

            } catch (Exception e) {
//                System.out.println("\nSomething goes wrong in Patron page.");
//                System.out.print(Tools.patronPageOut);
//                //供debug使用， 产品发布前删除
//                e.printStackTrace();
                //
            }
        }
    }
}