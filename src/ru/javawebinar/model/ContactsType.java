package ru.javawebinar.model;

public enum ContactsType {
    MOBILENUMBER("тел.: "),
    SKYPE("Skype:"),
    EMAIL("Email:"),
    GITHUB("Github"),
    LINKEDIN("Linkedin");

    private String title;

    ContactsType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
