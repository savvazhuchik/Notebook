/*
*       Основной класс, который запускает приложение
* */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileWorker.importFromFile(); // считывание из файла в начале программы (как в ТЗ)

        new MainMenu(new Scanner(System.in)).start();

        FileWorker.saveToFile(); // сохранение в файл в конце программы (ТЗ)
    }

}
