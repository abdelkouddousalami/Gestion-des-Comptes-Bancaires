package util;

import java.util.Scanner;

public class ConsoleUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre valide.");
            }
        }
    }

    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un nombre decimal valide.");
            }
        }
    }
}
