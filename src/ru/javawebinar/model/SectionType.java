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

    protected String toHtml0(String value) {
        return value;
    }

    public String toHtml(String value) {
        return value == null ? "" : title + " : " + toHtml0(value);
    }
}
