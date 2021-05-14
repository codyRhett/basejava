package ru.javawebinar.model;

import java.time.LocalDate;

public class Experience {
    private String position;
    private String responsibilities;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return position + ":  " + responsibilities + "\n" + dateStart + " - " + dateEnd + "\n";
    }
}
