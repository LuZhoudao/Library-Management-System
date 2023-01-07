package myOracle;

import java.util.Scanner;
import java.util.Vector;

public class STAFF {
    public String STAFF_ID;
    public String PASSWORD;
    public String STAFF_NAME;
    public String POSITION;
    public String PHONE;
    public String E_MAIL;

    private static String result;

    public STAFF(String STAFF_ID, String PASSWORD, String STAFF_NAME, String POSITION, String PHONE, String e_MAIL) {
        this.STAFF_ID = STAFF_ID;
        this.PASSWORD = PASSWORD;
        this.STAFF_NAME = STAFF_NAME;
        this.POSITION = POSITION;
        this.PHONE = PHONE;
        E_MAIL = e_MAIL;
    }
    public STAFF() {};


    public static String generateSTAFF_ID() {
        String temp = Tools.Random_int(8);
        return temp+"s";
    }


    public static STAFF getFromInput_Instance() {
        STAFF staff = new STAFF();
        staff.STAFF_ID = generateSTAFF_ID();
        System.out.println("The id number of staff is : " + staff.STAFF_ID);
        System.out.println("Please input STAFF information (input nothing to represent the null value):");
        staff.PASSWORD = getFromInput_PASSWORD();
        if (staff.PASSWORD == "*"){ return null; }
        staff.STAFF_NAME = getFromInput_STAFF_NAME();
        if (staff.STAFF_NAME == "*"){ return null; }
        staff.POSITION = getFromInput_POSITION();
        if (staff.POSITION == "*") {return null;}
        staff.PHONE = getFromInput_PHONE();
        if (staff.PHONE == "*"){ return null; }
        staff.E_MAIL = getFromInput_E_MAIL();
        if(staff.E_MAIL == "*"){ return null; }
        return staff;
    }

    public static boolean isValidSTAFF_ID(String STAFF_ID) {
        if(STAFF_ID.length()!=9){
            return false;
        }
        for(int i=0;i<8;i++){
            if(STAFF_ID.charAt(i)<'0'||STAFF_ID.charAt(i)>'9'){
                return false;
            }
        }
        if(STAFF_ID.charAt(8)!='s'){
            return false;
        }
        return true;
    }

    public static String getFromInput_STAFF_ID() {
        System.out.println("input the STAFF_ID (a string with 9 character e.g. 12345678s):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidSTAFF_ID(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidPOSITION(String POSITION) {
        if (!Tools.is_Valid_String(0,15,POSITION,true)){
            return false;
        }
        return true;
    }

    public static String getFromInput_POSITION() {
        System.out.println("input the POSITION : [input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPOSITION(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }


    public static boolean isValidPASSWORD(String PASSWORD) {
        if (!Tools.is_Valid_String(6,15,PASSWORD,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_PASSWORD() {
        System.out.println("input the PASSWORD (a string has 6 to 15 character e.g.abcdefghijk):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPASSWORD(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidSTAFF_NAME(String STAFF_NAME) {
        if(!Tools.is_Valid_String(1,30,STAFF_NAME,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_STAFF_NAME() {
        System.out.println("input the STAFF_NAME (a string has 1 to 30 character e.g.abcdefghijk):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidSTAFF_ID(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidPHONE(String PHONE) {
        if(!Tools.is_Valid_String(8,8,PHONE,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_PHONE() {
        System.out.println("input the STAFF_NAME (a string has 8 character e.g. 88888888):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPHONE(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }

    public static boolean isValidE_MAIL(String E_MAIL) {
        if(!Tools.is_Valid_String(20,30,E_MAIL,false)){
            return false;
        }
        return true;
    }

    public static String getFromInput_E_MAIL() {
        System.out.println("input the STAFF_NAME (a string has 20 to 30 characters " +
                "e.g. 12345678s@polyu.edu.hk.com):[input * quit]");
        Scanner input=new Scanner(System.in);
        result=input.nextLine();
        while(!isValidPHONE(result)){
            if(result.equals("*")){
                System.out.println("ALREADY QUIT");
                return "*";
            }
            System.out.println("the input is invalid, please input again:(input * quit)");
            result=input.nextLine();
        }
        return result;
    }



    public static void printObject(STAFF o) {
        String STAFF_ID = String.format("%-11s",o.STAFF_ID).replace(' ',' ');
        String STAFF_NAME= String.format("%-32s",o.STAFF_NAME.strip()).replace(' ',' ');
        String PHONE= String.format("%-10s",o.PHONE).replace(' ',' ');
        String E_MAIL= String.format("%-32s",o.E_MAIL.strip()).replace(' ',' ');
        System.out.print(STAFF_ID);
        System.out.print(STAFF_NAME);
        System.out.print(PHONE);
        System.out.println(E_MAIL);
    }
    public static void printTable(Vector<STAFF> ivec) {
        System.out.println("STAFF");
        System.out.print("    ");
        System.out.print(String.format("%-11s","STAFF_ID").replace(' ',' '));
        System.out.print(String.format("%-32s","STAFF_NAME").replace(' ',' '));
        System.out.print(String.format("%-10s","PHONE").replace(' ',' '));
        System.out.println(String.format("%-32s","E_MAIL").replace(' ',' '));
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

