import java.util.Scanner;

public class SearchMenu {
    private final Scanner scanner;

    public SearchMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    private static void printMenu() {
        System.out.println("1. Search by topic");
        System.out.println("2. Search by date");
        System.out.println("3. Search by text in messages");
        System.out.println("0. Back");
    }

    public void start() {
        if (this.scanner != null) {
            int key;
            do {
                printMenu();
                key = scanner.nextInt();

                switch (key) {
                    case 1:
                        Search.searchByTopic();
                        break;
                    case 2:
                        Search.searchByDate();
                        break;
                    case 3:
                        Search.searchByText();
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
