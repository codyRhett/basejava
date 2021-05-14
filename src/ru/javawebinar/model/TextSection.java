package ru.javawebinar.model;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection(String title) {
        super(title);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
