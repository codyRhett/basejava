package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.List;

public class ExperienceSection extends AbstractSection {
    private final List<Organization> organizations = new ArrayList<>();

    public ExperienceSection(String title) {
        super(title);
    }

    public ArrayList<Organization> getOrganization() {
        return (ArrayList<Organization>) organizations;
    }

    public void addOrganizationToList(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org.getName());
            sb.append("\n");
            sb.append(org.getUrl());
            sb.append("\n" + org.toString() + "\n");
        }
        return sb.toString();
    }
}
