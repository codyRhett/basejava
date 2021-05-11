package ru.javawebinar.model;

public class SectionText extends Section{
    String text;

    public SectionText(String title) {
        super(title);
    }

    void setText(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }
}
