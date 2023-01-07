package myOracle;

import java.util.Scanner;
import java.util.Vector;

public class RESERVE {
    public String BOOK_ID;
    public String ISBN;
    public String PATRON_ID;
    public String DEADLINE;
    public boolean BOOK_STATE;

    private static String result;

    public RESERVE(String BOOK_ID, String ISBN, String PATRON_ID, String DEADLINE, boolean BOOK_STATE) {
        this.BOOK_ID = BOOK_ID;
        this.ISBN = ISBN;
        this.PATRON_ID = PATRON_ID;
        //
        this.DEADLINE = DEADLINE;
        //
        this.BOOK_STATE = BOOK_STATE;
    }

    public RESERVE(){};

    public static boolean isValidBOOK_ID(String BOOK_ID) {
        if(BOOK_ID.length()!=15){
            return false;
        }
        for(int i=0;i<15;i++){
            if(BOOK_ID.charAt(i)<'0'||BOOK_ID.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    public static String getFromInput_BOOK_ID() {
        System.out.println("input the BOOK_ID (a string with 10 number character e.g. 123456789012345):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidBOOK_ID(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
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

    public static boolean isValidPATRON_ID(String PATRON_ID) {
        if(!Tools.is_Valid_String(13,13,PATRON_ID,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_PATRON_ID() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the PATRON_ID such as 12345678p(input * to quit)");
        result=input.nextLine();
        while(!isValidPATRON_ID(result)){
            if(result=="*"){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidDEADLINE(String DEADLINE) {
        if(!Tools.is_Valid_String(1,10,DEADLINE,true)){
            return false;
        }
        if(Tools.is_valid_time(DEADLINE)){
            return true;
        }
        return false;
    }

    public static String getFromInput_DEADLINE() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the DEADLINE such as 2021-1-12(input * to quit)");
        result=input.nextLine();
        while(!isValidDEADLINE(result)){
            if(result=="*"){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidBOOK_STATE(String BOOK_STATE) {
        BOOK_STATE=BOOK_STATE.toLowerCase();
        if(BOOK_STATE.equals("true") || BOOK_STATE.equals("false") || BOOK_STATE.equals("*")){
            return true;
        }
        return false;
    }

    public static boolean getFromInput_BOOK_STATE() {
        System.out.println("input the BOOK_STATE (true / false):[input * quit]");
        Scanner input = new Scanner(System.in);
        result = input.nextLine();
        while(!isValidBOOK_STATE(result)){
            System.out.println("the input is invalid,please input again:(input * quit)");
            result = input.nextLine();
        }
        if(!result.equals("*")){
            return false;
        }
        boolean res = Boolean.parseBoolean(result);
        return res;
    }


    public static void printObject(RESERVE o) {
        String ISBN = String.format("%-15s",o.ISBN).replace(' ',' ');
        String BOOK_ID = String.format("%-12s",o.BOOK_ID).replace(' ',' ');
        String PATRON_ID= String.format("%-11s",o.PATRON_ID).replace(' ',' ');
        String DEADLINE;
        if(o.DEADLINE != null){
            DEADLINE= String.format("%-12s",o.DEADLINE).replace(' ',' ');
        }
        else{
            DEADLINE= String.format("%-12s", "null").replace(' ',' ');
        }
        String BOOK_STATE= String.format("%-12s",o.BOOK_STATE).replace(' ',' ');
        System.out.print(ISBN);
        System.out.print(BOOK_ID);
        System.out.print(PATRON_ID);
        System.out.print(DEADLINE);
        System.out.println(BOOK_STATE);
    }

    public static void printTable(Vector<RESERVE> ivec) {
        System.out.println("RESERVE");
        System.out.print("    ");
        System.out.print(String.format("%-15s","ISBN").replace(' ',' '));
        System.out.print(String.format("%-12s","BOOK_ID").replace(' ',' '));
        System.out.print(String.format("%-11s","PATRON_ID").replace(' ',' '));
        System.out.print(String.format("%-12s","DEADLINE").replace(' ',' '));
        System.out.println(String.format("%-12s","BOOK_STATE").replace(' ',' '));
        for(int i = 0, m = 1; i < ivec.size(); i++){
            System.out.println("(" + m++ + "" + ")");
            System.out.println(Tools.getNChar("-", Tools.LEN));
            System.out.print("    ");
            if(ivec.get(i) == null){
                System.out.println("This table is empty");
            }
            else{
                ivec.get(i).printObject(ivec.get(i));
            }
        }
    }
}
