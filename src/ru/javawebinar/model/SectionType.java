package ru.javawebinar.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    POSITION("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATION("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml() {
        return title + " : ";
    }
}
