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
        String strOut = "";
        for (Organization org : organizations) {
            strOut += org.getName();
            strOut += "\n";
            strOut += org.getUrl();
            strOut += "\n" + org.toString() + "\n";
        }
        return strOut;
    }
}
