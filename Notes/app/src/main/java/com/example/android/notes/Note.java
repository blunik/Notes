package com.example.android.notes;

public class Note {
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public static String getDayAsString (int position){
        switch (position){
            case 1:
                return "Понедельник";
                case 2:
                return "Вторник";
                case 3:
                return "Среда";
                case 4:
                return "Четверг";
                case 5:
                return "Пятница";
                case 6:
                return "Суббота";
            default:
                return "Воскресенье";

        }
    }
}
