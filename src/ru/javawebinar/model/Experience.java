package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private String position;
    private String responsibilities;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        Objects.requireNonNull(position, "position must not be null");
        this.position = position;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        Objects.requireNonNull(responsibilities, "responsibilities must not be null");
        this.responsibilities = responsibilities;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return position + ":  " + responsibilities + "\n" + dateStart + " - " + dateEnd + "\n";
    }
}
