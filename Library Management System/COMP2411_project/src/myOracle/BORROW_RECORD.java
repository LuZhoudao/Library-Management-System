package myOracle;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

public class BORROW_RECORD {
    public String RECORD_ID;
    public String BOOK_ID;
    public String PATRON_ID;
    public String ISBN;
    public String START_TIME;
    public String END_TIME;


    public BORROW_RECORD(String RECORD_ID, String BOOK_ID, String PATRON_ID, String ISBN, String START_TIME, String END_TIME) {
        this.RECORD_ID = RECORD_ID;
        this.BOOK_ID = BOOK_ID;
        this.PATRON_ID = PATRON_ID;
        this.ISBN = ISBN;
        this.START_TIME = START_TIME;
        this.END_TIME = END_TIME;
    }
    public BORROW_RECORD() {};

    public static String generateRECORD_ID() {
        return Tools.Random_int(10);
    }


    public static boolean isValidRECORD_ID(String RECORD_ID) {
        if(RECORD_ID.length()!=10){
            return false;
        }
        for(int i=0;i<10;i++){
            if(RECORD_ID.charAt(i)<'0'||RECORD_ID.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    public static String getFromInput_RECORD_ID() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the record_ID such as 1234567890(input * to quit)");
        String result=input.nextLine();
        while(!isValidRECORD_ID(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

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
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the BOOK_ID such as 123456789012345(input * to quit)");
        String result=input.nextLine();
        while(!isValidBOOK_ID(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidPATRON_ID(String PATRON_ID) {
        if(PATRON_ID.length()!=9){
            return false;
        }
        if(PATRON_ID.charAt(8)!='p'){
            return false;
        }
        for(int i=0;i<8;i++){
            if(PATRON_ID.charAt(i)<'0'||PATRON_ID.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    public static String getFromInput_PATRON_ID() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the PATRON_ID such as 12345678p(input * to quit)");
        String result=input.nextLine();
        while(!isValidPATRON_ID(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidISBN(String ISBN) {
        if(ISBN.length()!=13){
            return false;
        }
        if(ISBN.charAt(8)!='p'){
            return false;
        }
        for(int i=0;i<8;i++){
            if(ISBN.charAt(i)<'0'||ISBN.charAt(i)>'9'){
                return false;
            }
        }
        return true;
    }

    public static String getFromInput_ISBN() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the ISBN such as 1234567891011(input * to quit)");
        String result=input.nextLine();
        while(!isValidISBN(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidSTART_TIME(String START_TIME) {
        if(Tools.is_valid_time(START_TIME)){
            return true;
        }
        return false;
    }

    public static String getFromInput_START_TIME() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the START_TIME such as 2021-1-12(input * to quit) (input # to represent today)");
        String result=input.nextLine();
        if (result != null && result.equals("#")) {
            result = LocalDate.now().toString();
        }
        while(!isValidSTART_TIME(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidEND_TIME(String END_TIME) {
        if(!Tools.is_Valid_String(1,10,END_TIME,true)){
            return false;
        }
        if(Tools.is_valid_time(END_TIME)){
            return true;
        }
        return false;
    }

    public static String getFromInput_END_TIME() {
        Scanner input=new Scanner(System.in);
        System.out.println("Please input the END_TIME such as 2021-1-25(input * to quit) (input # to represent today!)");
        String result=input.nextLine();
        if (result != null && result.equals("#")) {
            result = LocalDate.now().toString();
        }
        while(!isValidEND_TIME(result)){
            if(result.equals("*")){
                return "*";
            }
            System.out.println("invalid input please input again");
            result=input.nextLine();
        }
        return result;
    }

    public static void printObject(BORROW_RECORD o) {
        String ISBN = String.format("%-15s",o.ISBN).replace(' ',' ');
        String BOOK_ID = String.format("%-17s",o.BOOK_ID).replace(' ',' ');
        String PATRON_ID= String.format("%-11s",o.PATRON_ID).replace(' ',' ');
        String RECORD_ID = String.format("%-12s",o.RECORD_ID).replace(' ',' ');
        String START_TIME;
        String END_TIME;
        if(o.START_TIME != null){
            START_TIME =String.format("%-12s",o.START_TIME).replace(' ',' ');
        }
        else{
            START_TIME =String.format("%-12s","null").replace(' ',' ');
        }
        if(o.END_TIME != null){
            END_TIME =String.format("%-12s",o.END_TIME).replace(' ',' ');
        }
        else{
            END_TIME =String.format("%-12s","null").replace(' ',' ');
        }
        System.out.print(ISBN);
        System.out.print(BOOK_ID);
        System.out.print(PATRON_ID);
        System.out.print(RECORD_ID);
        System.out.print(START_TIME);
        System.out.println(END_TIME);
    }

    public static void printTable(Vector<BORROW_RECORD> ivec) {
        System.out.println("BORROW_RECORD");
        System.out.print("    ");
        System.out.print(String.format("%-15s","ISBN").replace(' ',' '));
        System.out.print(String.format("%-17s","BOOK_ID").replace(' ',' '));
        System.out.print(String.format("%-11s","PATRON_ID").replace(' ',' '));
        System.out.print(String.format("%-12s","RECORD_ID").replace(' ',' '));
        System.out.print(String.format("%-12s","START_TIME").replace(' ',' '));
        System.out.println(String.format("%-12s","END_TIME").replace(' ',' '));
        for(int i = 0, m = 1; i < ivec.size(); i++){
            System.out.print("("+ m++ + "" + ")");
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

