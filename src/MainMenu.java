/*
*       Класс описывает меню при запуске приложения
* */

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    private static void printHeader() {
        System.out.println("---------- NoteBook ----------");
    }

    private static void printMenu() {
        printHeader();

        System.out.println("1. Add a note");
        System.out.println("2. Show all notes");
        System.out.println("3. Search a note");
        System.out.println("0. Exit");
    }

    public void start() {
        if (this.scanner != null) {
            int key;
            do {
                printMenu();
                key = scanner.nextInt();

                switch (key) {
                    case 1:
                        Functions.addNote();
                        break;
                    case 2:
                        Functions.showNotes();
                        break;
                    case 3:
                        new SearchMenu(new Scanner(System.in)).start();
                        break;
                    case 0:
                        //
                        break;
                    default:
                        System.out.println("Wrong choice. Try again");
                }

            } while (key != 0);
        }
    }
}
