package pokerspel;

import java.util.Scanner;

public class Dialogs {

    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String... options) {
        System.out.println("-".repeat(20));
        if (options.length > 1) {
            for (int i = 1; i <= options.length; i++) {
                System.out.println(i+". "+options[i-1]);
            }
        } else {
            System.out.println(options[0]);
        }
        System.out.println("-".repeat(20));
        System.out.print(">> ");
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception ignore) {
                System.out.println("Wrong input!");
            }
        }
        return choice;
    }

    public static int[] getIntArrayInput(String... options) {
        System.out.println("-".repeat(20));
        if (options.length > 1) {
            for (int i = 1; i <= options.length; i++) {
                System.out.println(i+". "+options[i-1]);
            }
        } else {
            System.out.println(options[0]);
        }
        System.out.println("-".repeat(20));
        int[] choices = new int[options.length];
        int choice = -1;
        for (int i = 0; i < options.length; i++) {
            if (choice == 0) {
                break;
            }
            while (true) {
            System.out.println("Choose card to change or input 0 to skip");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 0 && choice <= 5) {
                        choices[i] = choice;
                        break;
                    } else {
                        System.out.println("Wrong input");
                    }
                } catch (Exception ignore) {
                    System.out.println("Wrong input!");
                }
            }
        }
        return choices;
    }
}
