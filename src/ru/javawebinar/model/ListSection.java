package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> items;// = new ArrayList<>();

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }
//
//    public ListSection(String title) {
//        super(title);
//    }

    public void addTextToList(String text) {
        items.add(text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : items) {
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
