package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> list = new ArrayList<>();

    public ListSection(String title) {
        super(title);
    }

    public void addTextToList(String text) {
        list.add(text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }
}
