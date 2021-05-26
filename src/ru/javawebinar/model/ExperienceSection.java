package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {
    private final List<Organization> organizations;// = new ArrayList<>();

    public ExperienceSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public ExperienceSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be zero");
        this.organizations = organizations;
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
