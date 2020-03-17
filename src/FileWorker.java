/*
*       Класс, который будет загружать заметки из файлы и записывать их в файл
*       Этот класс хранит в себе объект Книжки (NoteBook)
* */

import com.sun.tools.javac.Main;
import org.w3c.dom.Text;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWorker {
    private static String fileName = "notes.txt";
    private static Notebook notebook = new Notebook();

    public static Notebook getNotebook() {
        return notebook;
    }

    public static void setNotebook(Notebook notebook) {
        FileWorker.notebook = notebook;
    }

    // Запись записок в файл
    public static void saveToFile() {
        ArrayList<Note> notes = notebook.getNotes();

        clearFile();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));

            for (Note x : notes) {
                bufferedWriter.write(x.toString());
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Чтение всего текста из файла
    private static String getTextFromFile() {
        String allInput = "";
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNext()) {
                allInput += scanner.nextLine() + "\r\n";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return allInput;
    }

    // Считывание записок из текста
    public static void importFromFile() {
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<String> topics = new ArrayList<>(); // массив топиков
        ArrayList<String> emails = new ArrayList<>(); // массив email-ов
        ArrayList<String> messages = new ArrayList<>(); // массив сообщений
        ArrayList<Date> dates = new ArrayList<>(); // массив дат

        final String topicRegex = "(?<=Topic: )([\\s\\S]+?)(?=Date: )"; // паттерн топика
        final String emailRegex = "(?<=email: )([\\s\\S]+?)(?=-------)"; // паттерн email-а
        final String messageRegex = "(?<=----------------------------------)([\\s\\S]+?)(?=<<<)"; // паттерн сообщения
        final String dateRegex = "(?<=Date: )([\\s\\S]+?)(?=email: )"; // паттерн даты
        final String string = FileWorker.getTextFromFile(); // импорт всех записок (с всяким мусором, разделяющим записки)

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

        final Pattern topicPattern = Pattern.compile(topicRegex);
        final Matcher topicMatcher = topicPattern.matcher(string);
        final Pattern emailPattern = Pattern.compile(emailRegex);
        final Matcher emailMatcher = emailPattern.matcher(string);
        final Pattern messagePattern = Pattern.compile(messageRegex);
        final Matcher messageMatcher = messagePattern.matcher(string);
        final Pattern datePattern = Pattern.compile(dateRegex);
        final Matcher dateMatcher = datePattern.matcher(string);

        while (topicMatcher.find()) {                                            // Выделяем текст каждого топика и добавляем в массив
            topics.add(string.substring(topicMatcher.start(), topicMatcher.end()-1));
        }

        while (emailMatcher.find()) {                                            // Выделяем текст каждого email-а и добавляем в массив
            emails.add(string.substring(emailMatcher.start(), emailMatcher.end()-1));
        }

        while (messageMatcher.find()) {                                             // Выделяем текст каждого сообщения и добавляем в массив
            messages.add(string.substring(messageMatcher.start()+2, messageMatcher.end()-1));
        }

        while (dateMatcher.find()) {
            try {
                String dateString = string.substring(dateMatcher.start(), dateMatcher.end()-1);
                Date date = dateFormat.parse(dateString);
                dates.add(date);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }

        for (int x = 0; x < topics.size(); x++) {
            Note note = new Note(topics.get(x), emails.get(x), messages.get(x), dates.get(x));
            notes.add(note);
        }

        notebook.setNotes(notes);
    }

    // Очистка файла
    private static void clearFile() {
        try {
            FileOutputStream writer = new FileOutputStream(fileName);
            writer.write(("").getBytes());                              // StackOverFlow
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
