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
        String strOut = "";
        for (String str : list) {
            strOut += str + "\n";
        }
        return strOut;
    }
}
