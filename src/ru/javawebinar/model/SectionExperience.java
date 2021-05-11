package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class SectionExperience extends Section{
    List<Organization> organizations = new ArrayList<>();

    public SectionExperience(String title) {
        super(title);
    }

    public ArrayList<Organization> getOrganization() {
        return (ArrayList<Organization>) organizations;
    }

    public void addOrganizationToList(Organization organization) {
        organizations.add(organization);
    }
}
