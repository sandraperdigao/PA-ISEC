package pt.isec.pa.apoio_poe.src.utils;

import java.util.Objects;
import java.util.Scanner;

public final class PAInput {
    private PAInput() {}

    private static Scanner sc;

    static {
        resetScanner();
    }

    public static void resetScanner() {
        sc = new Scanner(System.in);
    }

    public static String readString(String title, boolean onlyOneWord) {
        String value;
        do {
            System.out.print(Objects.requireNonNullElse(title, "> "));
            value = sc.nextLine().trim();
        } while (value.isBlank());
        if (onlyOneWord) {
            Scanner auxsc = new Scanner(value);
            value = auxsc.next();
        }
        return value;
    }

    public static int readInt(String title) {
        while (true) {
            System.out.print(Objects.requireNonNullElse(title, "> "));
            if (sc.hasNextInt()) {
                int intValue = sc.nextInt();
                sc.nextLine();
                return intValue;
            } else
                sc.nextLine();
        }
    }

    public static double readNumber(String title) {
        while (true) {
            System.out.print(Objects.requireNonNullElse(title, "> "));
            if (sc.hasNextDouble()) {
                double doubleValue = sc.nextDouble();
                sc.nextLine();
                return doubleValue;
            } else
                sc.nextLine();
        }
    }

    public static int chooseOption(String title, String ... options) {
        int option = -1;
        do {
            if (title != null)
                System.out.println(System.lineSeparator()+title);
            System.out.println();
            for(int i = 0; i < options.length; i++) {
                System.out.printf("%3d - %s\n",i+1,options[i]);
            }
            System.out.print("\nOption: ");
            if (sc.hasNextInt())
                option = sc.nextInt();
            else
                System.out.println("\nPor favor introduza um número\n");
            sc.nextLine();
        } while (option < 1 || option > options.length);
        return option;
    }

}
