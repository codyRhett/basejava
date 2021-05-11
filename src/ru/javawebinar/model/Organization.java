package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    String organizationName;
    List<Experience> experienceList = new ArrayList<>();

    public Organization(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void addExperienceToList(Experience experience) {
        experienceList.add(experience);
    }

    public ArrayList<Experience> geExperienceList() {
        return (ArrayList<Experience>) experienceList;
    }

}
