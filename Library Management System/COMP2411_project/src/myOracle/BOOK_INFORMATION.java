package myOracle;

import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class BOOK_INFORMATION {
    public String ISBN;
    public String BOOK_NAME;
    public String CATEGORY;
    public String AUTHOR_NAME;
    public String PUBLISHER;
    public String LANGUAGE;
    public String PUBLISH_TIME;
    public String DESCRIPTION;
    private static String result;

    //
    public int available_copies = 0;
    //

    public BOOK_INFORMATION(String ISBN, String BOOK_NAME, String CATEGORY, String AUTHOR_NAME,
                            String PUBLISHER, String LANGUAGE, String PUBLISH_TIME, String DESCRIPTION, int available_copies) {
        this.ISBN = ISBN;
        this.BOOK_NAME = BOOK_NAME;
        this.CATEGORY = CATEGORY;
        this.AUTHOR_NAME = AUTHOR_NAME;
        this.PUBLISHER= PUBLISHER;
        this.LANGUAGE = LANGUAGE;
        this.PUBLISH_TIME = PUBLISH_TIME;
        this.DESCRIPTION = DESCRIPTION;
        this.available_copies = available_copies;
    }
    public BOOK_INFORMATION() {}

    //不确定是否和预期相符合 -- ISBN相同就不可以重复加入。
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BOOK_INFORMATION that = (BOOK_INFORMATION) o;
        return ISBN.equals(that.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
    //

    public static BOOK_INFORMATION getFromInput_Instance() {
        BOOK_INFORMATION book=new BOOK_INFORMATION();
        System.out.println("Please input ISBN,BOOK_NAME,CATEGORY,AUTHOR_NAME,PUBLISHER," +
                "LANGUAGE,PUBLISH_TIME,DESCRIPTION(input no to represent the null value):");
        book.ISBN=getFromInput_ISBN();
        if(book.ISBN=="*"){return null;}
        book.BOOK_NAME=getFromInput_BOOK_NAME();
        if(book.BOOK_NAME=="*"){return null;}
        book.CATEGORY=getFromInput_CATEGORY();
        if(book.CATEGORY=="*"){return null;}
        book.AUTHOR_NAME=getFromInput_AUTHOR_NAME();
        if(book.AUTHOR_NAME=="*"){return null;}
        book.PUBLISHER=getFromInput_PUBLISHER();
        if(book.PUBLISHER=="*"){return null;}
        book.LANGUAGE=getFromInput_LANGUAGE();
        if(book.LANGUAGE=="*"){return null;}
        book.PUBLISH_TIME=getFromInput_PUBLISH_TIME();
        if(book.PUBLISH_TIME=="*"){return null;}
        book.DESCRIPTION=getFromInput_DESCRIPTION();
        if(book.DESCRIPTION=="*"){return null;}
        return book;
    }

    public static boolean isValidISBN(String ISBN) {
        if(!Tools.is_Valid_String(13,13,ISBN,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_ISBN() {
        System.out.println("input the ISBN (a string with 13 number character e.g. 1234567890123):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidISBN(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidBOOK_NAME(String BOOK_NAME) {
        if(!Tools.is_Valid_String(1,50,BOOK_NAME,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_BOOK_NAME() {
        System.out.println("input the BOOK_NAME (a string have 1 to 50 characters e.g. Harry Potter):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidBOOK_NAME(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidCATEGORY(String  CATEGORY) {
        if(!Tools.is_Valid_String(1,30,CATEGORY,true)){
            return false;
        }
        return true;
    }

    public static String getFromInput_CATEGORY() {
        System.out.println("input the CATEGORY (a string have 1 to 30 characters e.g. Scientific):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidCATEGORY(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidAUTHOR_NAME(String AUTHOR_NAME) {
        if(!Tools.is_Valid_String(1,30,AUTHOR_NAME,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_AUTHOR_NAME() {
        System.out.println("input the AUTHOR_NAME(a string have 1 to 30 characters CAO WEN XUAN,YANG JIANG):(input * quit)");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidAUTHOR_NAME(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidPUBLISHER(String PUBLISHER) {
        if(!Tools.is_Valid_String(1,30,PUBLISHER,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_PUBLISHER() {
        System.out.println("input the PUBLISHER(a string have 1 to 30 characters,Gannett, New Media/Gatehouse, and Condé Nast):(input * quit)");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPUBLISHER(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }


    public static boolean isValidLANGUAGE(String LANGUAGE) {
        if(!Tools.is_Valid_String(1,15,LANGUAGE,true)){
            return false;
        }
        return true;
    }

    public static String getFromInput_LANGUAGE() {
        System.out.println("input the LANGUAGE(a string have 1 to 30 characters,such as ENGLISH，CHINESE)(input * quit))");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidLANGUAGE(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidPUBLISH_TIME(String PUBLISH_TIME) {
        if(PUBLISH_TIME==""){
            return true;
        }
        return Tools.is_valid_time(PUBLISH_TIME);
    }

    public static String getFromInput_PUBLISH_TIME() {
        System.out.println("input the PUBLISHER_TIME(a string have 1 to 10 characters:2021/11/9,2012/2/14)(input * quit)");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPUBLISH_TIME(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidDESCRIPTION(String DESCRIPTION) {
        if(!Tools.is_Valid_String(1,500,DESCRIPTION,true)){
            return false;
        }
        return true;
    }

    public static String getFromInput_DESCRIPTION() {
        System.out.println("input the DESCRIPTION(a string have 1 to 500 characters:This is a nice book)(input * quit)");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidDESCRIPTION(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid,please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    //别忘了加入了available_copies字段打印。
    public static void printObject(BOOK_INFORMATION o) {
        String ISBN = String.format("%-15s",o.ISBN).replace(' ',' ');
        String BOOKNAME = String.format("%-22s",o.BOOK_NAME.strip()).replace(' ',' ');
        String a_c = String.format("%-16s",o.available_copies + "").replace(' ',' ');
        String category;
        if(o.CATEGORY != null){
            category= String.format("%-17s",o.CATEGORY.strip()).replace(' ',' ');
        }
        else{
            category= String.format("%-17s","null").replace(' ',' ');
        }
        String AUTHOR_NAME = String.format("%-32s",o.AUTHOR_NAME.strip()).replace(' ',' ');
        String publisher =String.format("%-32s",o.PUBLISHER.strip()).replace(' ',' ');
        String language;
        if(o.LANGUAGE != null){
            language= String.format("%-17s",o.LANGUAGE.strip()).replace(' ',' ');
        }
        else{
            language= String.format("%-17s","null").replace(' ',' ');
        }
        String publish_time;
        if(o.PUBLISH_TIME != null){
            publish_time = String.format("%-14s",o.PUBLISH_TIME).replace(' ',' ');
        }
        else{
            publish_time = String.format("%-14s","null").replace(' ',' ');
        }
        String description;
        if(o.DESCRIPTION != null){
            description = String.format("%-52s",o.DESCRIPTION.strip()).replace(' ',' ');
        }
        else{
            description = String.format("%-52s","null").replace(' ',' ');
        }
        System.out.print(ISBN);
        System.out.print(BOOKNAME);
        System.out.print(a_c);
        System.out.print(category);
        System.out.print(AUTHOR_NAME);
        System.out.print(publisher);
        System.out.print(language);
        System.out.print(publish_time);
        System.out.println(description);
    }

    public static void printTable(Vector<BOOK_INFORMATION> ivec) {
        System.out.println("BOOK_INFORMATION");
        System.out.print("    ");
        System.out.print(String.format("%-15s", "ISBN").replace(' ', ' '));
        System.out.print(String.format("%-22s", "BOOKNAME").replace(' ', ' '));
        System.out.print(String.format("%-16s", "AVAILABLE_COPY").replace(' ', ' '));
        System.out.print(String.format("%-22s", "CATEGORY").replace(' ', ' '));
        System.out.print(String.format("%-32s", "AUTHOR_NAME").replace(' ', ' '));
        System.out.print(String.format("%-32s", "PUBLISHER").replace(' ', ' '));
        System.out.print(String.format("%-17s", "LANGUAGE").replace(' ', ' '));
        System.out.print(String.format("%-14s", "PUBLISH_TIME").replace(' ', ' '));
        System.out.println(String.format("%-52s", "DESCRIPTION").replace(' ', ' '));
        for (int i = 0, m = 1; i < ivec.size(); i++) {
            System.out.print("(" + m++ + "" + ")");
            System.out.println(Tools.getNChar("-", Tools.LEN));
            System.out.print("    ");
            if (ivec.get(i) == null) {
                System.out.println("This table is empty");
            } else {
                printObject(ivec.get(i));
            }
        }
    }
    public static void main(String args[]){
        Vector<BOOK_INFORMATION> v=new Vector();
        for(int i=0;i<2;i++){
            v.add(getFromInput_Instance());
        }
        printTable(v);
    }
}
