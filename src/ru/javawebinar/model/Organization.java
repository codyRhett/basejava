package ru.javawebinar.model;

import ru.javawebinar.util.DateUtil;
import ru.javawebinar.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;

    private List<Position> positions = new ArrayList<>();

    public Organization() {

    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(String name, String url, List<Position> positions) {
        this(new Link(name, url), positions);
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        StringBuilder strOut = new StringBuilder();
        for (Position position : positions) {
            strOut.append(position.toString()).append("\n");
        }
        return strOut.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        public Position() {

        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(title, description, DateUtil.of(startYear, startMonth), DateUtil.NOW);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(title, description, DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth));
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description) &&
                    Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate);
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
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }
}
