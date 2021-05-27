package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private final String urlPage;
    private final List<Position> positions;

    public Organization(String name, String urlPage, List<Position> positions) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.urlPage = urlPage;
        this.positions = positions;
    }

    public Organization(String name, String urlPage, Position... positions) {
        this(name, urlPage, Arrays.asList(positions));

    }

    public String getUrl() {
        return urlPage;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder strOut = new StringBuilder();
        for (Position position : positions) {
            strOut.append(position.toString()).append("\n");
        }
        return strOut.toString();
    }

    public static class Position {
        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public Position(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(description, "description must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(title, position.title) && Objects.equals(description, position.description) && Objects.equals(startDate, position.startDate) && Objects.equals(endDate, position.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(title).append(":");
            sb.append(description).append("\n");
            sb.append(startDate).append(" - ").append(endDate).append("\n");
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(urlPage, that.urlPage) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, urlPage, positions);
    }
}
