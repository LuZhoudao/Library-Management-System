package controller;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Vector;

//import jdk.incubator.foreign.CLinker;
import myOracle.*;

public abstract class StaffPage {
    private static String staff_id = null;
    private static STAFF staff = null;

    private abstract static class Manage_book_information {
        //operation -- 先不传值（staff id）无参数，避免混乱
        //不定义新的内部类而是用子函数表达。 无需变细概念。
        private static void search() throws Exception{
            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }
                //2， 提示信息。
                String description = "You can add several conditions to search for book information";
                String choice = "1-- search by book name / 2-- search by ISBN  3-- search by comprehensive condition";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);

                switch (choose) {
                    case 1 -> SearchBookInformation.searchByBookName();
                    case 2 -> SearchBookInformation.searchByISBN();
                    case 3 -> SearchBookInformation.searchByComprehensiveCondition();
                    default -> {
                        Tools.printModuleOut("search for book information (Staff)");
                        return;
                    }
                }
            }
        }

        //update
        private static void changeDescription () throws Exception {
            Tools.printInputHint("Enter the ISBN of book:");
            String isbn = BOOK_INFORMATION.getFromInput_ISBN();
            if (isbn.equals("*")) {
                return;
            }
            BOOK_INFORMATION book = BOOK_INFORMATIONTable.selectByISBN(isbn);
            if (book == null) {
                System.out.println("The book informatin do not exist");
                return;
            }
            if (book.DESCRIPTION != null) {
                System.out.println("The original description is: " + book.DESCRIPTION);
            }
            Tools.printInputHint("Please input the new description:");
            String description = BOOK_INFORMATION.getFromInput_DESCRIPTION();
            if (! description.equals("*")) {
                BOOK_INFORMATIONTable.changeDescription(description, book.ISBN);
            }
        }

        private static void changeCategory () throws Exception{
            Tools.printInputHint("please enter the original category name: ");
            String original = BOOK_INFORMATION.getFromInput_CATEGORY();
            System.out.println("Please input the new category name: ");
            String newName = BOOK_INFORMATION.getFromInput_CATEGORY();
            BOOK_INFORMATIONTable.changeCategory(original, newName);
        }

        private static void updateAll () throws Exception{
            Tools.printInputHint("Enter the ISBN of book:");
            String isbn = BOOK_INFORMATION.getFromInput_ISBN();
            if (isbn.equals("*")) {
                return;
            }
            BOOK_INFORMATION book = BOOK_INFORMATIONTable.selectByISBN(isbn);
            if (book == null) {
                System.out.println("The book informatin do not exist");
                return;
            }
            BOOK_INFORMATION newBook = BOOK_INFORMATION.getFromInput_Instance();
            if (newBook == null) {
                return;
            } else if (! newBook.ISBN.equals(book.ISBN)) {
                System.out.println("The two ISBN is not the same ! Are you sure to change the ISBN of book?");
                Tools.printChooseHint("", "", "1--yes");
                if (Tools.getIntByInput(1) == 0) {
                    return;
                }
            }
//            BOOK_INFORMATIONTable.deleteByISBN(book.ISBN);
            BOOK_INFORMATIONTable.insertTuple(newBook);
            System.out.println("The book information has been updated.");
        }

        private static void update() throws Exception{

            while (true) {
                //1, wait 0.5 second


                //2， 提示信息。

                String description = "";
                String choice = "1-- change the description of a book 2--change category name into another 3--update all information of a book";
                Tools.printChooseHint("update the record", description, choice);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 2:
                        changeCategory();
                        break;

                    case 1:
                        changeDescription();
                        break;

                    case 3:
                        updateAll();
                        break;

                    default: {
                        Tools.printModuleOut("update the record");
                        return;
                    }
                }
            }

        }


        //delete
        private static void deleteByISBN () throws Exception{
            String isbn = BOOK_INFORMATION.getFromInput_ISBN();
            if (isbn.equals("*")) {
                return;
            }

            //11.18 add
            BOOK_INFORMATION book = BOOK_INFORMATIONTable.selectByISBN(isbn);
            if (book == null) {
                System.out.println("Sorry, We cannot find the book information in the system.");
                return;
            }
            System.out.println("The book with ISBN " + isbn + " is as follows.");
            BOOK_INFORMATION.printObject(book);
            if (!COLLECTIONTable.selectByISBN(isbn).isEmpty()) {
                System.out.println("The library saves the collections of this kind of book. You can not delete this book information.");
                return;
            }

            Tools.printChooseHint("", "Are you sure to delete these book information?", "1--yes");
            if (Tools.getIntByInput(1) == 1) {
                BOOK_INFORMATIONTable.deleteByISBN(isbn);
                System.out.println("deletion operation finishd");
            } else {
                System.out.println("The deletion are canceled");
            }
            //
        }

        private static void deleteByName () throws Exception {
            String name = BOOK_INFORMATION.getFromInput_BOOK_NAME();
            if (name.equals("*")) {
                return;
            }
            Vector<BOOK_INFORMATION> books = BOOK_INFORMATIONTable.selectByBOOK_NAME(name);
            if (books.isEmpty()) {
                System.out.println("Sorry, we cannot find the book information in the system.");
                return;
            }

            System.out.println("The book with name" + name + " is as follows.");
            BOOK_INFORMATION.printTable(books);
            for (BOOK_INFORMATION book : books) {
                if (!COLLECTIONTable.selectByISBN(book.ISBN).isEmpty()) {
                    System.out.println("The library saves the collections of "+ book.BOOK_NAME.strip() + ". You can not delete this book information.");
                    return;
                }
            }

            Tools.printChooseHint("", "Are you sure to delete these book information?", "1--yes");
            if (Tools.getIntByInput(1) == 1) {
                BOOK_INFORMATIONTable.deleteByBOOK_NAME(name);
                System.out.println("deletion operation finishd");
            } else {
                System.out.println("The deletion are canceled");
            }

        }

        private static void deleteByComprehensiveCondition () throws Exception {
            Tools.printInputHint("Search the book information first. only the last search will be recorded");
            Vector<BOOK_INFORMATION> ivec =  SearchBookInformation.searchByComprehensiveCondition();
            int n = ivec.size();
            if (n == 0) {
                return;
            }

            for (BOOK_INFORMATION book : ivec) {
                if (!COLLECTIONTable.selectByISBN(book.ISBN).isEmpty()) {
                    System.out.println("The library saves the collections of "+ book.BOOK_NAME.strip() + "  . You can not delete this book information.");
                    return;
                }
            }

            Tools.printChooseHint("", "Are you sure to delete these book information?", "1--yes");
            if (Tools.getIntByInput(1) == 1) {
                for (int i = 0; i < ivec.size(); i++) {
                    BOOK_INFORMATIONTable.deleteByISBN(ivec.get(i).ISBN);
                }
                System.out.println( n + " book information have been deleted");
            } else {
                System.out.println("The deletion are canceled");
            }
        }

        private static void delete() throws Exception{

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。

                String description = "";
                String choice = "1--delete by ISBN 2--delete by book name 3--delete by comprehensive conditions";
                Tools.printChooseHint("delete the book information", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1 -> deleteByISBN();
                    case 2 -> deleteByName();
                    case 3 -> deleteByComprehensiveCondition();
                    default -> {
                        Tools.printModuleOut("delete the book information");
                        return;
                    }
                }
            }


        }

        private static void insert() throws Exception {
            Tools.printInputHint("Add the new book information! If the insertion process have problems, all the insertions will be canceled");
            int flag = 1;
            Vector<BOOK_INFORMATION> ivec = new Vector<BOOK_INFORMATION>();
            while (flag == 1) {
                BOOK_INFORMATION book = BOOK_INFORMATION.getFromInput_Instance();
                if (book == null) {
                    Tools.printModuleOut("insert");
                    break;
                }
                ivec.add(book);

                Tools.printChooseHint("", "Do you want to add more book information?", "1-continue");
                flag = Tools.getIntByInput(1);
            }
            BOOK_INFORMATIONTable.insertObjects(ivec);
            Tools.printModuleOut("Finish the operation");
        }


        //module -- args[0] staff id
        public static void main(String[] args) throws Exception {

            while (true) {
                //1, wait 0.5 second


                //2， 提示信息。
                Tools.printModuleIn("manage_book_information");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                String description = "";
                String choice = "1--check the book information / 2--update book information / 3--add new book information / 4--delete book information";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 4;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        search();
                        break;

                    case 2:
                        update();
                        break;

                    case 3:
//
                        insert();
                        break;

                    case 4:
                        delete();
                        break;

                    default: {
                        Tools.printModuleOut("manage_book_information");
                        return;
                    }
                }
            }
        }
    }


    private abstract static class Manage_collections {
        //operation
        private static void selectModule() throws Exception{

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。

                String description = "";
                String choice = "1-- check the information about a collection by id 2--check the collections by ISBN  3--check the collections of a location 4-- check the unavailable collections";
                Tools.printChooseHint("check the collection information", description, choice);
                //3//选择
                int numberOfOperation = 4;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        String book_id = COLLECTION.getFromInput_BOOK_ID();
                        if (book_id.equals("*")) {
                            break;
                        }
                        COLLECTION book = COLLECTIONTable.selectByBOOK_ID(book_id);
                        if (book == null) {
                            System.out.println("The collection is not in the system.");
                            break;
                        }
                        BOOK_INFORMATION information = BOOK_INFORMATIONTable.selectByISBN(book.ISBN);
                        //11.18
                        System.out.println("The collection's information:");
                        Vector<COLLECTION> temp = new Vector<> ();
                        temp.add(book);
                        COLLECTION.printTable(temp);
                        System.out.println("The collection's related book information.");
                        Vector<BOOK_INFORMATION> temp1 = new Vector();
                        temp1.add(information);
                        BOOK_INFORMATION.printTable(temp1);
                        //
                        break;

                    case 2:
                        String ISBN = COLLECTION.getFromInput_ISBN();
                        if (ISBN.equals("*")) {
                            break;
                        }
                        Vector<COLLECTION> ivec = COLLECTIONTable.selectByISBN(ISBN);
                        System.out.println("The book with ISBN " + ISBN + " have " + ivec.size() + " copies.");
                        COLLECTION.printTable(ivec);
                        break;

                    case 3:
                        String location = COLLECTION.getFromInput_LOCATION();
                        if (location.equals("*")) {
                            break;
                        }
                        Vector<COLLECTION> ivec1 = COLLECTIONTable.selectByLOCATION(location);
                        System.out.println("The book at " + location + " : ");
                        COLLECTION.printTable(ivec1);
                        break;

                    case 4:
                        Vector<COLLECTION> books = COLLECTIONTable.select_unavailable_COLLECTION();
                        System.out.println("There are " + books.size() + " unavailable books now.");
                        COLLECTION.printTable(books);
                        break;

                    default: {
                        Tools.printModuleOut("check the collection information");
                        return;
                    }
                }
            }


        }

        private static void updateModule() throws Exception {

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。

                String description = "";
                String choice = "1-- change the location of collection / 2--activate the collection by book_id / 3--inactivate the collection by book_id";
                Tools.printChooseHint("update the collection information", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                if (choose == 0) {
                    Tools.printModuleOut("update the collection information");
                    return;
                }

                String book_id = COLLECTION.getFromInput_BOOK_ID();
                if (book_id.equals("*")) {
                    break;
                }
                COLLECTION book = COLLECTIONTable.selectByBOOK_ID(book_id);
                if (book == null) {
                    System.out.println("The book id is not valid.");
                    break;
                }
                switch (choose) {
                    case 1:
                        System.out.println("The original location of the book is: " + book.LOCATION);
                        String location = COLLECTION.getFromInput_LOCATION();
                        if (location.equals("*")) {
                            break;
                        }
                        COLLECTIONTable.changeLocation(book_id, location);
                        System.out.println("Successfully change the location of the book");
                        break;

                    case 2:
                        if (book.STATE) {
                            System.out.println("The collection is available now. No need to update.") ;
                            break;
                        }
                        COLLECTIONTable.set_BOOK_available(book_id);
                        System.out.println("Successfully update the state of book.");
                        break;

                    case 3:
                        if (! book.STATE) {
                            System.out.println("The collection is not available. No need to update.");
                            break;
                        }
                        COLLECTIONTable.set_BOOK_unavailable(book_id);
                        System.out.println("Successfully update the state of book.");
                        break;

                    default:
                        return;
                }
            }


        }


        private static void insertModule() throws Exception {
            Tools.printInputHint("Add new collection of books into library !");
            int flag = 1;
            int count  = 0;
            Vector<COLLECTION> newCollections = new Vector<>();
            while (flag == 1) {
                String ISBN = COLLECTION.getFromInput_ISBN();
                if (BOOK_INFORMATIONTable.selectByISBN(ISBN) == null) {
                    System.out.println("The book information have not been added to library. Please add the book information about this collection first!");
                } else {
                    COLLECTION book = COLLECTION.getFromInput_Instance(ISBN);
                    if (book == null) {
                        System.out.println("Cancel the operation");
                    } else {
                        newCollections.add(book);
                    }
                }

                System.out.println("Do you want to add more collections ? 1--yes 0 -- no") ;
                flag = Tools.getIntByInput(1);
            }

            try {
                COLLECTIONTable.insertObjects(newCollections);
                System.out.println("Successfully add " + newCollections.size() + " collections");
            } catch (SQLException ignored) {

            }
            Tools.printModuleOut("add collection information");
        }

        private static void deleteModule() throws Exception {

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。

                String description = "";
                String choice = "1-- delete by collection id / 2-- delete by ISBN";
                Tools.printChooseHint("delete the collection", description, choice);
                //3//选择
                int numberOfOperation = 2;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        String book_id = COLLECTION.getFromInput_BOOK_ID();
                        if (book_id.equals("*")) {
                            break;
                        }
                        COLLECTION book = COLLECTIONTable.selectByBOOK_ID(book_id);
                        if (book == null) {
                            System.out.println("We cannot find this book in the system. The operation have been canceled.");
                            return;
                        }
                        System.out.println("The collection information is as follows: ");
                        COLLECTION.printObject(book);


                        if (!RESERVETable.selectByBOOK_ID(book_id).isEmpty()) {
                            System.out.println("There are reserves on this book in progress. You cannot delete this collection. Please deal with these reserve first.");
                            return;
                        }
                        //11:17 -- borrow record foreign key
                        Vector<BORROW_RECORD> records = BORROW_RECORDTable.selectByBOOK_ID(book_id);
                        for (BORROW_RECORD br : records) {
                            if (br.END_TIME == null) {
                                System.out.println("This book is borrowed by patron now. You can not delete its information.");
                                return;
                            }
                        }

                        System.out.println("Are you sure to delete the information of this book? 1--yes, 0--no");
                        int choose1 = Tools.getIntByInput(1);
                        if (choose1 == 1) {
                            BORROW_RECORDTable.deleteByCollectionID(book_id);
                            COLLECTIONTable.deleteByBOOK_ID(book_id);
                            System.out.println("Finish the operation. The information of the book and all its history borrow record have been deleted.");
                        } else {
                            System.out.println("The operation have been canceled.");
                        }
                        //11.19 foreign key bug fixed
                        break;

                    case 2:
                        String ISBN = COLLECTION.getFromInput_ISBN();
                        if (ISBN.equals("*")) {
                            break;
                        }
                        Vector<COLLECTION> books = COLLECTIONTable.selectByISBN(ISBN);
                        if (books.isEmpty()) {
                            System.out.println("We cannot find the books with ISBN " + ISBN + " in the system. The operation have been canceled.");
                            return;
                        }
                        System.out.println("The collections information is as follows: ");
                        COLLECTION.printTable(books);
                        if (!RESERVETable.selectByISBN(ISBN).isEmpty()) {
                            System.out.println("There are reserves on this book in progress. You cannot delete this collection. Please deal with these reserve first.");
                            return;
                        }
                        Vector<BORROW_RECORD> records1 = BORROW_RECORDTable.selectByISBN(ISBN);
                        for (BORROW_RECORD br : records1) {
                            if (br.END_TIME == null) {
                                System.out.println("This book is borrowed by patron now. You can not delete its information.");
                                return;
                            }
                        }

                        System.out.println("Are you sure to delete these information of books? 1--yes, 0--no");
                        int choose2 = Tools.getIntByInput(1);
                        if (choose2 == 1) {
                            COLLECTIONTable.deleteByISBN(ISBN);
                            System.out.println("Successfully deleted "+ books.size() + " collection informations.");
                        } else {
                            System.out.println("The operation have been canceled.");
                        }
                        break;

                    default: {
                        Tools.printModuleOut("delete the collection");
                        return;
                    }
                }
            }



        }


        //module
        public static void main(String[] args) throws Exception {

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。
                Tools.printModuleIn("manage_collections");

                String description = "";
                String choice = "1--check the information of collections / 2-- update the information of collections / 3--add new collection / 4-- delete the collection information.";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 4;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1 -> selectModule();
                    case 2 -> updateModule();
                    case 3 -> insertModule();
                    case 4 -> deleteModule();
                    default -> {
                        Tools.printModuleOut("manage_collections");
                        return;
                    }
                }
            }
        }
    }


    private abstract static class Manage_patrons {

        private static void selectModule () throws SQLException{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // continue.
                }

                Tools.printChooseHint("check the patron information","","1 -- select by patron id 2 -- select by patron name 3 -- check the inactivated patron account.");
                int choose = Tools.getIntByInput(3);
                switch (choose) {
                    case 1:
                        String patron_id = PATRON.getFromInput_PATRON_ID();
                        if (patron_id.equals("*")) {
                            break;
                        }
                        PATRON br = PATRONTable.selectByPATRON_ID(patron_id);
                        if (br != null) {
                            System.out.println("The perssonal information of the person is as follows:");
                            PATRON.printObject(br);
                        } else {
                            System.out.println("The patron not exist.");
                        }
                        break;

                    case 2:
                        String patron_name = PATRON.getFromInput_PATRON_NAME();
                        if (patron_name.equals("*")) {
                            break;
                        }
                        Vector<PATRON> ivec = PATRONTable.selectByPATRON_NAME(patron_name);
                        if (ivec.isEmpty()) {
                            System.out.println("There are no patrons named as " + patron_name);
                            break;
                        }
                        System.out.print("The patron by patron with id " + patron_name + " is as follows:");
                        PATRON.printTable(ivec); //要注意落实print table 方法不打印出密码
                        break;
                    case 3:
                        Vector<PATRON> ivec1 = PATRONTable.select_error_patron(); //注意落实数据， ILLEGAL_TYPE 非空时activated  false。
                        System.out.println("The inactivated persons are as follows:");
                        System.out.println("PATRON_ID     PATRON_NAME   ILLEGAL_TYPE");
                        for (PATRON p : ivec1) {
                            System.out.println( String.format("%-12s",p.PATRON_ID.strip()).replace(' ',' ')+String.format("%-20s",p.PATRON_NAME.strip()).replace(' ',' ')+String.format("%-22s",(p.ILLEGAL_TYPE+"").strip()).replace(' ',' '));
                        }
                        break;

                    default:
                        return;
                }

            }
        }

        private static void deleteModule () throws SQLException{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //continue
                }

                Tools.printChooseHint("delete the patron", "","1 -- delete patron by patron_id. " );
                int choose = Tools.getIntByInput(1);
                switch (choose) {
                    case 1:
                        String patron_id = PATRON.getFromInput_PATRON_ID();
                        if (patron_id.equals("*")) {
                            break;
                        }
                        PATRON person = PATRONTable.selectByPATRON_ID(patron_id);
                        if (person == null) {
                            System.out.println("The patron do not exist in the system.");
                            break;
                        }
                        //11.19 fix foreign key bug. -- logic : hava borrow/reserve -- delete it before delete the patron information. (not cancel the operation)
                        System.out.println("The information of the patron is as follows. ");
                        PATRON.printObject(person);
                        System.out.println("Are you sure to remove it from database ? 1 -- yes 0 --no");
                        int choose1 = Tools.getIntByInput(1);
                        if (choose1 == 1) {
                            //delete the patron's record first
                            BORROW_RECORDTable.deleteByPATRON_ID(patron_id);
                            //delete the in progress reserve first.
                            RESERVETable.deleteByPATRON_ID(patron_id);
                            PATRONTable.deleteByPATRON_ID(person.PATRON_ID);
                            System.out.println("Successfully perform the deletion.  All of the borrow records and in progress reserve has also been cleared from the system.");
                        } else {
                            System.out.println("Cancel the operation.");
                        }
                        break;


                    default:
                        return;
                }

            }
        }

        private static void updateModule () throws Exception {

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。
//            Tools.printModuleIn("update the patron");

                String description = "";
                String choice = "1 -- record users violations. / 2-- activate the user account / 3--inactivate all abnormal borrow records and inactivate the patrons mading these borrow ";
                Tools.printChooseHint("update the patron", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        String patron_ID = PATRON.getFromInput_PATRON_ID();
                        if (patron_ID.equals("*")) {
                            break;
                        }
                        String illegalType = PATRON.getFromInput_ILLEGAL_TYPE();
                        if (illegalType.equals("*")) {
                            illegalType = "";
                        }
                        PATRONTable.inactivateThePatron(patron_ID, illegalType);
                        System.out.println("Finish the update");
                        break;

                    case 2:
                        String patron_ID1 = PATRON.getFromInput_PATRON_ID();
                        if (patron_ID1.equals("*")) {
                            break;
                        }
                        PATRONTable.activiateThePatron(patron_ID1);
                        System.out.println("Finish the update.");
                        break;

                    case 3:
                        Vector<BORROW_RECORD> ivec = manageAbnormalBR();
                        System.out.println("Are you sure to inactivate these persons account? 1--yes 0 -- no");
                        int choose2 = Tools.getIntByInput(1);
                        if (choose2 == 1) {
                            for (BORROW_RECORD record : ivec) {
                                PATRONTable.inactivateThePatron(record.PATRON_ID, "Overdue borrow");
                            }
                            System.out.println("Successfully inactivate these persons account.");
                        } else {
                            System.out.println("Cancel the operation.");
                        }
                        break;


                    default: {
                        Tools.printModuleOut("update the patron");
                        return;
                    }
                }
            }
        }


        public static void main(String[] args) throws Exception {


            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。
                Tools.printModuleIn("manage the patron information");

                String description = "";
                String choice = "1 -- check the patron information. 2 -- update the patron information. 3--delete the patron information";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        selectModule();
                        break;
                    case 2:
                        updateModule();
                        break;

                    case 3:
                        deleteModule();
                        break;

                    default: {
                        Tools.printModuleOut("manage the patron information");
                        return;
                    }
                }
    }


        }
    }

    private static Vector<BORROW_RECORD> manageAbnormalBR () throws SQLException {
        Vector<BORROW_RECORD> records = BORROW_RECORDTable.select_in_progress_borrow();
        String today = LocalDate.now().toString();
        HashSet<String> patrons = new HashSet<>();
        Vector<BORROW_RECORD> abnormal = new Vector<BORROW_RECORD>();
        for (BORROW_RECORD record : records) {
            if (Tools.time_difference(record.START_TIME, today) > 100) {
                abnormal.add(record);
                PATRON p = PATRONTable.selectByPATRON_ID(record.PATRON_ID);
                if (p != null) {
                    patrons.add(p.PATRON_NAME.strip());
                }
            }
        }
        System.out.println("The abnormal in progress records are as follows, which is started 100 days before.");
        BORROW_RECORD.printTable(abnormal);
        //待优化： 统一输出（查分printTable） or 在类体内实现
        System.out.println("These borrow is made my following person:");
        for (String name : patrons) {
            System.out.print(name + "  ");
        }
        //
        return abnormal;
    }


    private abstract static class  Manage_borrow_records {

        private static void selectModule () throws SQLException{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // continue.
                }

                Tools.printChooseHint("check the borrow record","","1 -- check the records by record_id  2 -- check the records made by a patron  3 -- check in progress record., 4 check the record of a particular time range 5--check the record about one collection 6--check the abnormal in process record");
                int choose = Tools.getIntByInput(6);
                switch (choose) {
                    case 1:
                        String record_id = BORROW_RECORD.getFromInput_RECORD_ID();
                        if (record_id.equals("*")) {
                            break;
                        }
                        BORROW_RECORD br = BORROW_RECORDTable.selectByRECORD_ID(record_id);
                        if (br != null) {
                            BORROW_RECORD.printObject(br);
                        } else {
                            System.out.println("The record not exist.");
                        }
                        break;
                    case 2:
                        System.out.println("You can choose to input either the patron id or the patron name:  (0 to quit)  1 -- name  2 -- id");
                        int choose1 = Tools.getIntByInput(2);
                        if (choose1 == 0) {
                            break;
                        } else if (choose1 == 1) {
                            String patron_name = PATRON.getFromInput_PATRON_NAME();
                            if (Objects.equals(patron_name, "*")) {
                                break;
                            }
                            Vector<BORROW_RECORD> ivec = BORROW_RECORDTable.selectByPATRON_NAME(patron_name);
                            System.out.print("The record by patron with name " + patron_name + " is as follows:");
                            BORROW_RECORD.printTable(ivec);

                        } else {
                            String patron_id = BORROW_RECORD.getFromInput_PATRON_ID();
                            if (patron_id.equals("*")) {
                                break;
                            }
                            Vector<BORROW_RECORD> ivec = BORROW_RECORDTable.selectByPATRON_ID(patron_id);
                            System.out.print("The record by patron with id " + patron_id + " is as follows:");
                            BORROW_RECORD.printTable(ivec);
                        }
                        break;
                    case 3:
                        Vector<BORROW_RECORD> ivec1 = BORROW_RECORDTable.select_in_progress_borrow();
                        System.out.println("The in progress borrow records is as follows: ");
                        BORROW_RECORD.printTable(ivec1);
                        break;

                    case 4:
                        Tools.printInputHint("Check the borrow records that end time between a particular time range.");
                        boolean valid = false;
                        String startTime = null;
                        String endTime =  null;
                        while (!valid) {
                            startTime = BORROW_RECORD.getFromInput_START_TIME();
                            endTime = BORROW_RECORD.getFromInput_END_TIME();
                            valid = startTime.compareTo(endTime) < 0 || startTime.equals("*") || endTime.equals("*");
                            if (! valid) {
                                System.out.println("The input is valid! This section do not allow quit and end time must later than start time");
                                break;
                            }
                        }
                        Vector<BORROW_RECORD> ivec2 = BORROW_RECORDTable.select_records_of_time_range(startTime, endTime);
                        System.out.println("The borrow records range from " + startTime  + " to " +  endTime + " are as follows:");
                        BORROW_RECORD.printTable(ivec2);
                        break;

                    case 5:
                        System.out.println("You can choose to input either the BOOK id or the BOOK name:  (0 to quit)  1 -- name  2 -- id");
                        int choose2 = Tools.getIntByInput(2);
                        if (choose2 == 0) {
                            break;
                        } else if (choose2 == 1) {
                            String BOOK_name = BOOK_INFORMATION.getFromInput_BOOK_NAME();
                            if (Objects.equals(BOOK_name, "*")) {
                                break;
                            }
                            Vector<BORROW_RECORD> ivec = BORROW_RECORDTable.selectByBookName(BOOK_name);
                            System.out.print("The BOOK by BOOK with name " + BOOK_name + " is as follows:");
                            BORROW_RECORD.printTable(ivec);

                        } else {
                            String BOOK_id = COLLECTION.getFromInput_BOOK_ID();
                            if (BOOK_id.equals("*")) {
                                break;
                            }
                            Vector<BORROW_RECORD> ivec = BORROW_RECORDTable.selectByBOOK_ID(BOOK_id);
                            System.out.print("The borrow record  about BOOK with id " + BOOK_id + " is as follows:");
                            BORROW_RECORD.printTable(ivec);
                        }
                        break;

                    case 6:
                        Vector<BORROW_RECORD> inProgress = BORROW_RECORDTable.select_in_progress_borrow();
                        Vector<BORROW_RECORD> overdue= new Vector<>();
                        String today = LocalDate.now().toString();
                        for (BORROW_RECORD br1  : inProgress) {
                            if (Tools.time_difference(br1.START_TIME, today) > 100) {
                                overdue.add(br1);
                            }
                        }
                        System.out.println("The overdue in progress borrow record are as follows: ");
                        BORROW_RECORD.printTable(overdue);
                        break;

                    default:
                        return;
                }

            }
        }

        private static void deleteModule () throws SQLException{
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //continue
                }

                Tools.printChooseHint("delete the borrow record", "","1 -- delete record before a particular time. 2 -- delete record by record id 3--delete records about a collection" );
                int choose = Tools.getIntByInput(3);
                switch (choose) {
                    case 1:
                        String end_time = BORROW_RECORD.getFromInput_END_TIME();
                        if (end_time.equals("*")) {
                            break;
                        }
                        BORROW_RECORDTable.deleteBR_before(end_time);
                        System.out.println("Successfully perform the deletion.");
                        break;

                    case 2:
                        String record_id = BORROW_RECORD.getFromInput_RECORD_ID();
                        if (record_id.equals("*")) {
                            break;
                        }
                        BORROW_RECORDTable.deleteByRECORD_ID(record_id);
                        System.out.println("Successfully perform the deletion.");
                        break;

                    case 3:
                        String book_id = BORROW_RECORD.getFromInput_BOOK_ID();
                        if (Objects.equals(book_id, "*")) {
                            break;
                        }
                        BORROW_RECORDTable.deleteByCollectionID(book_id);
                        System.out.println("Successfully perform the deletion.");

                    default:
                        return;
                }

            }
        }

        private static void updateModule () throws Exception {

            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。
//            Tools.printModuleIn("update the borrow record");

                String description = "";
                String choice = "1 -- end the in progress record.";
                Tools.printChooseHint("update the borrow record", description, choice);
                //3//选择
                int numberOfOperation = 1;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        String RECORD_ID = BORROW_RECORD.getFromInput_RECORD_ID();
                        if (RECORD_ID.equals("*")) {
                            break;
                        }

                        BORROW_RECORD br = BORROW_RECORDTable.selectByRECORD_ID(RECORD_ID);
                        if (br == null) {
                            System.out.println("The record is not in the database !");
                        } else if (br.END_TIME != null) {
                            System.out.println("The borrow has already terminated.");
                        } else {
                            BORROW_RECORDTable.endTheBorrowByRECORD_ID(RECORD_ID);
                            System.out.println("Successfully end the borrow.");
                        }
                        break;

                    default: {
                        Tools.printModuleOut("update the borrow record");
                        return;
                    }
                }
            }
        }


        public static void main(String[] args) throws Exception {


            while (true) {
                //1, wait 0.5 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {

                }

                //2， 提示信息。
                Tools.printModuleIn("manage the borrow record");

                String description = "";
                String choice = "1 -- check the record. 2 -- update the record. 3 -- delete the record ";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int numberOfOperation = 3;
                int choose = Tools.getIntByInput(numberOfOperation);
                switch (choose) {
                    case 1:
                        selectModule();
                        break;
                    case 2:
                        updateModule();
                        break;
                    case 3:
                        deleteModule();
                        break;

                    default: {
                        Tools.printModuleOut("manage the borrow record");
                        return;
                    }
                }
            }
        }
    }

    private abstract static class Generate_analysis_report {
        //operation

        //module
        public static void main(String[] args) throws Exception {
            AnalysisReport.main(null);
        }
    }


    //page
    public static void main(String[] args) throws Exception {
        //initialize
        if (args.length > 0) {
            staff_id = args[0];
            if (staff_id != null) {
                staff = STAFFTable.selectBySTAFF_ID(staff_id);
            }
        }

        boolean first_time = true;
        while (true) {
            try {
                //2， 提示信息。
                System.out.print("\n\n\n\n\n");
                Tools.printELine();
                System.out.println(Tools.staffPageIn); //in hint (graphical)
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }

                if (first_time) {
                    Tools.printWelcomeMessage(staff == null ? "" : staff.STAFF_NAME.strip());
                    first_time = false;
                }

                String description = "";
                String choice = "1--manage book information / 2--manage collections of book / 3--manage patrons information / 4--manage borrow records / 5--generate analysis reports";
                Tools.printChooseHint("", description, choice);
                //3//选择
                int choose = Tools.getIntByInput(5);
                switch (choose) {
                    case 1:
                        Manage_book_information.main(null);
                        break;

                    case 2:
                        Manage_collections.main(null);
                        break;

                    case 3:
                        Manage_patrons.main(null);
                        break;

                    case 4:
                        Manage_borrow_records.main(null);
                        break;

                    case 5:
                        Generate_analysis_report.main(null);
                        break;

                    default: {
                        System.out.println(Tools.staffPageOut);
                        return;
                    }
                }

            } catch (Exception e) {
//                System.out.println("\nSomething goes wrong in Staff page.");
//                //供debug使用， 产品发布前删除
//                e.printStackTrace();
                //
            }
        }
    }
}

