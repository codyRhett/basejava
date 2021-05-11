package ru.javawebinar.model;

public enum ContactsType {
    MOBILENUMBER("bla-bla mobilenumber"),
    SKYPE("bla-bla skype"),
    EMAIL("bla-bla email"),
    GITHUB("bla-bla github"),
    LINKEDIN("bla-bla github");

    private String title;

    ContactsType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
