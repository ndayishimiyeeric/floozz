package Utils;

public class FancyPrint {
     static final String RESET = "\u001B[0m";
     static final String CYAN_TEXT = "\u001B[36m";
     static final String BLUE_BACKGROUND = "\u001B[44m";
     static final String WHITE_TEXT = "\u001B[37m";
     static final String GREEN_TEXT = "\u001B[32m";
     static final String YELLOW_BACKGROUND = "\u001B[43m";

    public static void blue_back__print(String str, boolean CRLF) {
        System.out.print(FancyPrint.BLUE_BACKGROUND + str + FancyPrint.RESET + (CRLF ? "\n" : ""));
    }

    public static void yellow_back__print(String str, boolean CRLF) {
        System.out.print(FancyPrint.YELLOW_BACKGROUND + str + FancyPrint.RESET+ (CRLF ? "\n" : ""));
    }

    public static void white_fore__print(String str, boolean CRLF) {
        System.out.print(FancyPrint.WHITE_TEXT + str + FancyPrint.RESET+ (CRLF ? "\n" : ""));
    }

    public static void cyan_fore__print(String str, boolean CRLF) {
        System.out.print(FancyPrint.CYAN_TEXT + str + FancyPrint.RESET+ (CRLF ? "\n" : ""));
    }

    public static void green_fore__print(String str, boolean CRLF) {
        System.out.print(FancyPrint.GREEN_TEXT + str + FancyPrint.RESET+ (CRLF ? "\n" : ""));
    }
}
