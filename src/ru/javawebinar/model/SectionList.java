package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class SectionList extends Section{
    List<String> list = new ArrayList<>();

    public SectionList(String title) {
        super(title);
    }

    public void addTextToList(String text) {
        list.add(text);
    }
}
