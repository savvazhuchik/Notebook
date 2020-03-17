/*
*   Класс отвечает за поиск записок по параметрам
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    // Поиск записок по топику
    public static void searchByTopic() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList <Note> notes = FileWorker.getNotebook().getNotes();
        String nameOfTopic = null;

        System.out.println("Type in name of topic to search");

        try {
            nameOfTopic = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (Note x : notes) {
            if (nameOfTopic.equalsIgnoreCase(x.getTopic().replaceAll("(\\r|\\n)", ""))) { // убираем перевод строки,
                System.out.println(x.toString());                                                           // т.к. возвращает вместе с ним
            }
        }
    }

    // Поиск записок по дате
    public static void searchByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList <Note> notes = FileWorker.getNotebook().getNotes();

        String dateToSearch = null;

        System.out.println("Type in the date of topic in dd-MM-yyyy format (example: 29-11-1997): ");

        try {
            dateToSearch = bufferedReader.readLine();        // считываем дату в строку
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (Note note : notes) {       // пробегаемся по всем запискам
            String dateOfNote = dateFormat.format(note.getDateOfCreation()); // берем из записки дату и переводим её в строку в нужном нам формате
                                                                            // если строка даты совпадает с той, что ввёл пользователь -
            if (dateToSearch.equals(dateOfNote)) {                           // выводим записку в консоль
                System.out.println(note.toString());
            }
        }
    }

    // Поиск записок по тексту в них (в тексте самой записи (note.message))
    public static void searchByText() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Note> notes = FileWorker.getNotebook().getNotes();

        String textToSearch = null;

        System.out.println("Type in the text to search: ");

        try {
            textToSearch = bufferedReader.readLine();               // ЮЗЕР МОЖЕТ ВВЕСТИ ПУСТУЮ СТРОКУ, ИЗ-ЗА ЧЕГО
        } catch (IOException e) {                                   // МАТЧЕР НАЙДЕТ ВСЕ ЗАПИСКИ! ИСПРАВИТЬ!!!
            System.out.println(e.getMessage());
        }

        Pattern textPattern = Pattern.compile(textToSearch); // паттерн для поиска текста

        for (Note note : notes) {       // пробегаемся по всем запискам
            String messageOfNote = note.getMessage();
            Matcher matcher = textPattern.matcher(messageOfNote);   // поисковик текста

            if(matcher.find()) {    // если находит текст - выводим записку в консоль
                System.out.println(note.toString());
            }
        }
    }
}
