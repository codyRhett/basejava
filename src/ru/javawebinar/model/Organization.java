package ru.javawebinar.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private URL homePage;
    private final List<Experience> experienceList = new ArrayList<>();

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

    public void addExperienceToList(Experience experience) {
        experienceList.add(experience);
    }

    public ArrayList<Experience> geExperienceList() {
        return (ArrayList<Experience>) experienceList;
    }

    @Override
    public String toString() {
        String strOut = "";
        for (Experience experience : experienceList) {
            strOut += experience.toString() + "\n";
        }
        return strOut;
    }
}
