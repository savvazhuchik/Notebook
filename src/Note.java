/*
*       Класс описывает Заметку
* */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String topic;
    private Date dateOfCreation;
    private String email;
    private String message;

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    public Note(String topic, String email, String message, Date dateOfCreation) {
        this.topic = topic;
        this.dateOfCreation = dateOfCreation;
        this.email = email;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ">>>\nTopic: " + this.topic +
                "\nDate: " + dateFormat.format(dateOfCreation) +
                "\nemail: " + this.email +
                "\n----------------------------------\n" +
                this.message + "\n<<<\n";
    }
}
