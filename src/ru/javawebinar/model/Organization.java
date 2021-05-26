package ru.javawebinar.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private URL homePage;
    private final List<Position> experienceList = new ArrayList<>();

    public Organization(String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
    }

    public void setUrl(String urlStr) {
        Objects.requireNonNull(urlStr, "urlStr must not be null");
        try {
            homePage = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        return homePage;
    }

    public String getName() {
        return name;
    }

    public void addExperienceToList(Position experience) {
        experienceList.add(experience);
    }

    public ArrayList<Position> geExperienceList() {
        return (ArrayList<Position>) experienceList;
    }

    @Override
    public String toString() {
        String strOut = "";
        for (Position experience : experienceList) {
            strOut += experience.toString() + "\n";
        }
        return strOut;
    }

    public static class Position {
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;

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
        return Objects.equals(name, that.name) && Objects.equals(homePage, that.homePage) && Objects.equals(experienceList, that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homePage, experienceList);
    }
}
