package pokerspel;

import java.util.Scanner;

public class Dialogs {

    private static Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String prompt) {
        System.out.println("-".repeat(50));
        System.out.println(prompt);
        System.out.println("-".repeat(50));
        String input;
        while (true) {
            System.out.print(">> ");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Wrong input!");
            } else {
                break;
            }
        }
        return input;
    }

    public static int getIntInput(String... options) {
        System.out.println("-".repeat(50));
        if (options.length > 1) {
            for (int i = 1; i <= options.length; i++) {
                System.out.println(i+". "+options[i-1]);
            }
        } else {
            System.out.println(options[0]);
        }
        System.out.println("-".repeat(50));
        int choice;
        while (true) {
            System.out.print(">> ");
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
        System.out.println("-".repeat(50));
        if (options.length > 1) {
            for (int i = 1; i <= options.length; i++) {
                System.out.println(i+". "+options[i-1]);
            }
        } else {
            System.out.println(options[0]);
        }
        System.out.println("-".repeat(50));
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
