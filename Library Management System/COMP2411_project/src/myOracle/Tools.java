package myOracle;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Tools {
    public static boolean is_Valid_String(int min_length,int max_length,String res,boolean can_be_null){
        if(!can_be_null){
            if(res.split(" ").length==0||res==""){
                return false;
            }
        }
        if(min_length>res.length()||max_length<res.length()||res.equals("*")){
            return false;
        }
        return true;
    }
    public static boolean is_valid_time(String str) {
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        try{
            sd.setLenient(false);
            sd.parse(str);
        }
        catch (Exception e) {
            return false;
        }

        String[] time=str.split("-");
        LocalDate day = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
        LocalDate low = LocalDate.of(1500,1,1);
        LocalDate high = LocalDate.of(2023,12,31);
        Period dif1 = Period.between(low, day);
        Period dif2 = Period.between(high, day);


        return !(dif1.getYears() < 0||dif1.getMonths()<0||dif1.getDays()<0|| dif2.getYears() > 0 || dif2.getMonths() > 0 || dif2.getDays() > 0);
    }


    public static int time_difference(String time1, String time2){
        if(!is_valid_time(time1) || !is_valid_time(time2)){
            System.out.println("The input is error, the function is quited.");
            return 0;
        }

        String[] temp1 = time1.split("-"), temp2 = time2.split("-");
        LocalDate day1 = LocalDate.of(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1]), Integer.parseInt(temp1[2]));
        LocalDate day2 = LocalDate.of(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]));

        long diff = ChronoUnit.DAYS.between(day1, day2);
        return (int)diff;
    }

    //
    public static String time_days_later (String time1, int n) {
        if(!is_valid_time(time1)){
            System.out.println("The input is error, the function is quited.");
            return null;
        }

        String[] temp = time1.split("-");
        DateTimeFormatter sd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate day1 = LocalDate.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        LocalDate day2 = day1.plusDays(n);
        return day2.format(sd);
    }


    public boolean is_Valid_id(int length,char appendix,String ID){
        if(ID.length()!=length){
            return false;
        }
        if(ID.charAt(length-1)!=appendix){
            return false;
        }
        return true;
    }
    public static String Random_int(int digits){
        SecureRandom num = new SecureRandom();
        String answer = "";
        for(int i = 0; i < digits; i++){
            int number = num.nextInt(10);
            answer += (number + "");
        }
        return answer;
    }

    private static boolean isValidInt(String result, int n) {
        try {
            int num = Integer.parseInt(result);
            if (num >= 0 && num <= n) {
                return true;
            }
        } catch (NumberFormatException e) {

        }
        return false;
    }
    public static int getIntByInput (int n) {
        System.out.println("Enter a integer from 0 to " + n);
        Scanner input = new Scanner(System.in);
        String result = input.nextLine();
        while (! isValidInt(result, n)) {
            System.out.println("Invalid input! Please input the integer that from 0 to " + n);
            System.out.print("Input again: ");

            result = input.nextLine();
        }
//        input.close();
        return Integer.valueOf(result);
    }

    //
    public static final int LEN = 200;
    
    public static void printLine () {
        StringBuilder  sb = new StringBuilder();
        for  (int i = 0; i < LEN; ++i) {
            sb.append("-");
        }
        System.out.println(sb.toString());
    }
    public static String getNChar( String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void printInputHint (String hint) {
        String s1 = getNChar("◦", 50) + "  " + hint + "  " + getNChar("◦", LEN-50);
        System.out.println(s1.substring(0, LEN));
    }

    //每一个选择时的提示信息打印。
    //input: 功能名， 描述， 选项  output： 打印提示信息。 --所有行最大字符数为LEN
    //可以选择不输入description
    public static void notification (String name ,String description) {
        //1,停 0.5秒
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//
//        }
//        System.out.println("\n");
        //2， line
//        printLine();
        //3,name
//        if (name != null && ! name.isEmpty()) {
//            String s1 = getNChar("◦", 75) + "  " + name + "  " + getNChar("◦", LEN-75);
//            System.out.println(s1.substring(0, LEN));
//        }
        //4, description
        if (description != null && (!description.isEmpty())) {
            String s2 = getNChar("-", 55) + "  " + description + "  " + getNChar("-", 160);
            System.out.println(s2.substring(0, LEN));
        }
    }

    public static void printChooseHint( String name ,String description, String choice) {
        //1,停 0.5秒
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//
//        }
        System.out.println();
        //2， line
//        printLine();
        //3,name
        if (name != null && ! name.isEmpty()) {
            String s1 = getNChar("◦", 75) + "  " + name + "  " + getNChar("◦", LEN-75);
            System.out.println(s1.substring(0, LEN));
        }
        //4, description
        if (description != null && (!description.isEmpty())) {
            String s2 = getNChar("-", 55) + "  " + description + "  " + getNChar("-", 160);
            System.out.println(s2.substring(0, LEN));
        }
        //5, choice
        String s3 = "Enter an integer to make the choose (enter 0 to quit): " + choice ;
        System.out.println(s3);

    }

    //直接调用，不用预先空行
    public static void printModuleIn (String moduleName) {
//        //wait for one second //只进行打印信息，若想等待在module方法内实现。
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ignored) {
//
//        }
        //3line
        System.out.println("\n");
//        printELine();
        //
        String s = "                                                         ";
        String s1 = s.replace(" ", "◦");
        String s2 = "▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾▾".replace("▾", "◦");
        System.out.println(s + "   ╔━━━━━━━━━━━━━━━━━━━━━━╗\n" + s1 +
                               "   ║   ▿   ▿   ◈   ▿   ▿  ║ " + "  " + s2);
        System.out.println(s + "      " + moduleName);
        System.out.println(s1 + "   ║   ▿   ▿   ◈   ▿   ▿  ║" + "  " + s2 + "\n" +
                          s +  "   ╚━━━━━━━━━━━━━━━━━━━━━━╝\n" );

    }

    public static void printModuleOut (String moduleName) {
        System.out.println("\nLeave " + moduleName );
        printLine();
    }

    public static void printWelcomeMessage(String name) {
        System.out.println("\n\n");
        System.out.println(getNChar("◌", LEN));
        String s = getNChar("-", 45) + " ◅▯◊║◊▯▻    Hi, " + name + ".  Welcome to library system.   ◅▯◊║◊▯▻ " + getNChar("-",150);
        System.out.println(s.substring(0, LEN));
        System.out.println(getNChar("◌", LEN));
    }

    public static void printELine() {
        System.out.println(getNChar("▾", LEN));
    }

    public static void printDownLine () {
        System.out.println(getNChar("▴", LEN));
        System.out.println(getNChar("◌", LEN));
    }

    public static final String welcomePageIn = "                                                                                                                                                                  \n" +
            "        ●    ●    ●  ●●●●●●●  ●     ●●●●    ●●●●     ●   ●    ●●●●●●●         ●●●●●●     ●●        ●●●●   ●●●●●●                                                     \n" +
            "        ●●   ●    ●  ●        ●   ●●●      ●●  ●●    ●● ●●    ●               ●    ●     ●●       ●●      ●                                                          \n" +
            "         ●  ●●●  ●●  ●●●●●●●  ●  ●●        ●    ●    ●● ●●    ●●●●●●●         ●●●●●●    ●  ●     ●●       ●●●●●●                                                     \n" +
            "         ●● ● ●● ●   ●        ●  ●●        ●    ●   ●●●●●●●   ●               ●        ●●●●●●    ●   ●●   ●                                                          \n" +
            "          ●●●  ●●●   ●        ●  ●●●       ●●  ●●  ●●  ●  ●●  ●               ●       ●●    ●●   ●●  ●●   ●                                                          \n" +
            "           ●●   ●    ●●●●●●●  ●    ●●●●●    ●●●●   ●   ●   ●  ●●●●●●●         ●       ●     ●●   ●●●●●●   ●●●●●●                                                     ";

    public static final String welcomePageOut = "                                                                                                                                                                  \n" +
            "                                                                                                                                                                  \n" +
            "                                                                                                                                                                  \n" +
            "         ●         ●●●●●●     ●●     ●      ●   ●●●●●●       ●    ●    ●  ●●●●●●●  ●     ●●●●    ●●●●     ●   ●    ●●●●●●●         ●●●●●●     ●●        ●●●●   ●●●●●●\n" +
            "         ●         ●          ●●     ●●    ●●   ●            ●●   ●    ●  ●        ●   ●●●      ●●  ●●    ●● ●●    ●               ●    ●     ●●       ●●      ●     \n" +
            "         ●         ●●●●●●    ●  ●     ●   ●●    ●●●●●●        ●  ●●●  ●●  ●●●●●●●  ●  ●●        ●    ●    ●● ●●    ●●●●●●●         ●●●●●●    ●  ●     ●●       ●●●●●●\n" +
            "         ●         ●        ●●●●●●    ●● ●●     ●             ●● ● ●● ●   ●        ●  ●●        ●    ●   ●●●●●●●   ●               ●        ●●●●●●    ●   ●●   ●     \n" +
            "         ●         ●       ●●    ●     ●●●      ●              ●●●  ●●●   ●        ●   ●●●      ●●  ●●  ●●  ●  ●●  ●               ●       ●●    ●●   ●●  ●●   ●     \n" +
            "         ●●●●●●●   ●●●●●●  ●     ●      ●●      ●●●●●●          ●●   ●    ●●●●●●●  ●     ●●●●    ●●●●   ●   ●   ●  ●●●●●●●         ●       ●     ●●   ●●●●●●   ●●●●●●";

    public static final String patronPageIn = "                                                                                                                                                                  \n" +
            "                                                                                                                                                                  \n" +
            "      ●●●●●●     ●●    ●●●●●●●  ●●●●●●   ●●●●   ●    ●       ●●●●●●     ●●        ●●●●   ●●●●●●                                                                   \n" +
            "      ●    ●     ●●       ●     ●    ●  ●●  ●●  ●●   ●       ●    ●     ●●       ●●      ●                                                                        \n" +
            "      ●●●●●●    ●  ●      ●     ●●●●●●  ●    ●  ●●●  ●       ●●●●●●    ●  ●     ●●       ●●●●●●                                                                   \n" +
            "      ●        ●●●●●●     ●     ●●●●    ●    ●  ● ●● ●       ●        ●●●●●●    ●   ●●   ●                                                                        \n" +
            "      ●       ●●    ●     ●     ●  ●●   ●●  ●●  ●  ●●●       ●       ●●    ●●   ●●  ●●   ●                                                                        \n" +
            "      ●       ●     ●     ●     ●    ●   ●●●●   ●   ●●       ●       ●     ●    ●●●●●●   ●●●●●●                                                                   ";

    public static final String patronPageOut = "                                                                                                                                                                  \n" +
            "         ●         ●●●●●●     ●●     ●      ●   ●●●●●●        ●●●●●●     ●●    ●●●●●●●  ●●●●●●   ●●●●   ●    ●       ●●●●●●     ●●        ●●●●   ●●●●●●           \n" +
            "         ●         ●          ●●     ●●    ●●   ●             ●    ●     ●●       ●     ●    ●  ●●  ●●  ●●   ●       ●    ●     ●●       ●●      ●                \n" +
            "         ●         ●●●●●●    ●  ●     ●   ●●    ●●●●●●        ●●●●●●    ●  ●      ●     ●●●●●●  ●    ●  ●●●  ●       ●●●●●●    ●  ●     ●●       ●●●●●●           \n" +
            "         ●         ●        ●●●●●●    ●● ●●     ●             ●        ●●●●●●     ●     ●●●●    ●    ●  ● ●● ●       ●        ●●●●●●    ●   ●●   ●                \n" +
            "         ●         ●       ●●    ●     ●●●      ●             ●       ●●    ●●    ●     ●  ●●   ●●  ●●  ●  ●●●       ●       ●●    ●●   ●●  ●●   ●                \n" +
            "         ●●●●●●●   ●●●●●●  ●     ●      ●●      ●●●●●●        ●       ●     ●●    ●     ●    ●   ●●●●   ●   ●●       ●       ●     ●●   ●●●●●●   ●●●●●●           ";

    public static final String staffPageIn = "                                                                                                                                                                 \n" +
            "                                                                                                                                                                  \n" +
            "        ●●●●●●●   ●●●●●●●      ●●     ●●●●●●●  ●●●●●●●       ●●●●●●     ●●        ●●●●   ●●●●●●                                                                   \n" +
            "        ●            ●         ●●     ●        ●             ●    ●     ●●       ●●      ●                                                                        \n" +
            "        ●●●●●●●      ●        ●  ●    ●●●●●●●  ●●●●●●●       ●●●●●●    ●  ●     ●●       ●●●●●●                                                                   \n" +
            "              ●      ●       ●●●●●●   ●        ●             ●        ●●●●●●    ●   ●●   ●                                                                        \n" +
            "              ●      ●      ●●    ●   ●        ●             ●       ●●    ●●   ●●  ●●   ●                                                                        \n" +
            "        ●●●●●●●      ●      ●     ●   ●        ●             ●       ●     ●●   ●●●●●●   ●●●●●●                                                                   ";

    public static final String staffPageOut = "                                                                                                                                                                 \n" +
            "                                                                                                                                                                  \n" +
            "          ●         ●●●●●●     ●●     ●      ●   ●●●●●●        ●●●●●●●   ●●●●●●●      ●●     ●●●●●●●  ●●●●●●●       ●●●●●●     ●●        ●●●●   ●●●●●●            \n" +
            "          ●         ●          ●●     ●●    ●●   ●             ●            ●         ●●     ●        ●             ●    ●     ●●       ●●      ●                 \n" +
            "          ●         ●●●●●●    ●  ●     ●   ●●    ●●●●●●        ●●●●●●●      ●        ●  ●    ●●●●●●●  ●●●●●●●       ●●●●●●    ●  ●     ●●       ●●●●●●            \n" +
            "          ●         ●        ●●●●●●    ●● ●●     ●                   ●      ●       ●●●●●●   ●        ●             ●        ●●●●●●    ●   ●●   ●                 \n" +
            "          ●         ●       ●●    ●     ●●●      ●                   ●      ●      ●●    ●   ●        ●             ●       ●●    ●●   ●●  ●●   ●                 \n" +
            "          ●●●●●●●   ●●●●●●  ●     ●      ●●      ●●●●●●        ●●●●●●●      ●      ●     ●   ●        ●             ●       ●     ●●   ●●●●●●   ●●●●●●            ";


    public static void printLeaveMessage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }

        System.out.println("\n\n" +
                        "●●●●●●●●●●      ●●●        ●●●  ●●●●●●●●●●●       ▮▮▮▮▮\n" +
                        "●●●●●●●●●●●       ●●      ●●    ●●●●●●●●●●●       ▮▮▮▮▮\n" +
                        "●●       ●●        ●●●   ●●●    ●●                ▮▮▮▮▮\n" +
                        "●●       ●●          ●●●●●      ●●                ▮▮▮▮▮\n" +
                        "●●      ●●●           ●●●       ●●                ▮▮▮▮▮\n" +
                        "●●●●●●●●●●            ●●●       ●●●●●●●●●●●       ▮▮▮▮▮\n" +
                        "●●●●●●●●●●●           ●●●       ●●●●●●●●●●●       ▮▮▮▮▮\n" +
                        "●●       ●●           ●●●       ●●                ▮▮▮▮▮\n" +
                        "●●        ●●          ●●●       ●●                     \n" +
                        "●●      ●●●●          ●●●       ●●                  ◍◍◍ \n" +
                        "●●●●●●●●●●●           ●●●       ●●●●●●●●●●●        ◍◍◍◍◍\n" +
                        "●●●●●●●●●             ●●●       ●●●●●●●●●●●         ◍◍◍ ");

    }

    public static void printWelcomeMessage() {
        System.out.println(
                "                 ◇◇◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◇◇                                               \n" +
                        "               ◇◇                                                                       ◇◇◇                                             \n" +
                        "              ◇◇         ▥          ▥        ▥      ▥         ▥        ▥       ▥          ◇◇◇                                           \n" +
                        "            ◇◇ ┌───────────────────────────────────────────────────────────────────———─┐    ◇◇                                          \n" +
                        "           ◇◇  ┼                                                                       │     ◇◇                                         \n" +
                        "               │ ●     ●●      ●  ●●●●●●●  ●     ●●●●    ●●●●     ●     ●      ●●●●●●  │     Λ                                            \n" +
                        "          Λ    │ ●●    ●●●    ●●  ●        ●   ●●●      ●●  ●●    ●●●  ●●●     ●       │    ╱ ╲                                           \n" +
                        "         ╱ ╲   │  ●●   ● ●●   ●●  ●●●●●●●  ●  ●●       ●●    ●●  ●● ●●●● ●●    ●●●●●●  │   ▕   ▏                                         \n" +
                        "        ▕   ▏  │   ●● ●●  ●● ●●   ●        ●  ●●       ●●    ●●  ●●  ●●●  ●●   ●       │    ╲ ╱                                           \n" +
                        "         ╲ ╱   │    ●●●    ●●●●   ●        ●  ●●●      ●●    ●● ●●   ●●    ●●  ●       │     V                                            \n" +
                        "          V    │     ●●      ●    ●●●●●●●  ●    ●●●●●    ●●●●   ●     ●     ●  ●●●●●●  │                                                \n" +
                        "               │                                                                       │    ◇◇                                          \n" +
                        "          ◇◇   └───────────────────────────────────────────────────────────────────———─┘  ◇◇◇                                           \n" +
                        "           ◇◇◇          ▥          ▥         ▥       ▥         ▥      ▥       ▥         ◇◇◇                                             \n" +
                        "              ◇◇◇                                                                     ◇◇                                                \n" +
                        "                ◇◇◇◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◌◇◇                                                 ");
        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {

        }

    }

    public static void main(String[] args) {
//        String name = "check the record";
//        String description  = "you can check the borrow record within following choice";
//        String choice = "1 -- select by record id 2 -- select by patron id 3 -- check in progress record., 4 get in progress borrows's";
//        printChooseHint(name, description, choice);
        //长度，字符代改
        //通过测试。

        //测试printModule
//        printModuleIn("patron log in");
        //ok

        printWelcomeMessage("Mike");
    }
}
