/*
*       Класс с функциями приложения
*        (добавления, просмотры заметок...)
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    // Просмотр всех заметок
    public static void showNotes() {
        Notebook notebook = FileWorker.getNotebook();
        ArrayList<Note> notes = notebook.getNotes();

        for (Note x: notes) {
            System.out.println("Topic: " + x.getTopic() +
                                "\nDate: " + dateFormat.format(x.getDateOfCreation()) +
                                "\nemail: " + x.getEmail() +
                                "\n----------------------------------\n" +
                                x.getMessage() + "\n\n");
        }
    }

    // Добавление новой записки
    public static void addNote() {
        Notebook notebook = FileWorker.getNotebook();

        String topic = null;
        String email = null;
        String message = null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Type in a topic name:");
            topic = bufferedReader.readLine();
            System.out.println("Type in your email:");
            email = bufferedReader.readLine();
            System.out.println("Type in your message:");
            message = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Проверка введённой информации на валидность
        final String topicLengthRegex = "^.{1,35}$"; // длинна топика должна быть от 1 до 35 символов
        final String emailRegex = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+"; // проверка на email
        final String messageLengthRegex = "^.{1,240}$"; // длинна текста записки должна быть от 1 до 240 ( x2 от Twitter :) )

        final Pattern topicLengthPattern = Pattern.compile(topicLengthRegex);
        final Pattern emailPattern = Pattern.compile(emailRegex);
        final Pattern messageLengthPattern = Pattern.compile(messageLengthRegex);
        final Matcher topicLengthMatcher = topicLengthPattern.matcher(topic);
        final Matcher emailMatcher = emailPattern.matcher(email);
        final Matcher messageLengthMatcher = messageLengthPattern.matcher(message);

        boolean isValid = (topicLengthMatcher.find() && emailMatcher.find() && messageLengthMatcher.find()); // проверка на валидность

        if (isValid) {
            notebook.addNote(new Note(topic, email, message, new Date()));
            System.out.println("Note added");
        } else {
            System.out.println("The entered value is not correct");
        }

        FileWorker.setNotebook(notebook);
    }
}
